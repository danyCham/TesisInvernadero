package com.app.invernadero;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.invernadero.Model.Result;
import com.app.invernadero.Model.Usuario;
import com.app.invernadero.Utils.ApiAdaptar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GaleryUser extends AppCompatActivity {

    private RecyclerView mRecyclerUser;
    private UserAdaptador mAdapter;
    TextView tv_mensaje;
    EditText tv_buscar;
    Button bn_buscar;
    Spinner lista;
    String[] datos = {"Sin filtro","Nombre", "Apellido", "Email", "Rol"};
    SharedPreferences preferencia;

    public static final String OPCION ="opcion";
    public static final String CONSULTA ="consulta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery_user);

        preferencia = getSharedPreferences("ArchivoSP", 0);
        preferencia.edit().remove(OPCION).commit();
        preferencia.edit().remove(CONSULTA).commit();

        mRecyclerUser = findViewById(R.id.recycle_view_usuario);
        lista = findViewById(R.id.listaUser);
        tv_buscar = findViewById(R.id.edt_buscarUser);
        tv_mensaje = findViewById(R.id.txt_mensaje);
        bn_buscar  = findViewById(R.id.bn_buscar);

        mRecyclerUser.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(GaleryUser.this, 1);
        mRecyclerUser.setLayoutManager(layoutManager);

        mAdapter = new UserAdaptador();
        mRecyclerUser.setAdapter(mAdapter);

        Call<Usuario> call = ApiAdaptar.getApiService().obtenerUser("null","null",4);
        call.enqueue(new UserCallback());

        ArrayAdapter<String> adapterList = new ArrayAdapter<>(GaleryUser.this,android.R.layout.simple_list_item_activated_1,datos);
        lista.setAdapter(adapterList);

        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preferencia = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencia.edit();
                switch (position){
                    case 0:
                        editor.putString(OPCION,"");
                        editor.commit();
                        break;
                    case 1:
                        editor.putString(OPCION,"nombre");
                        editor.commit();
                        break;
                    case 2:
                        editor.putString(OPCION,"apellido");
                        editor.commit();
                        break;
                    case 3:
                        editor.putString(OPCION,"email");
                        editor.commit();
                        break;
                    case 4:
                        editor.putString(OPCION,"rol");
                        editor.commit();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencia = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
                String opcion = preferencia.getString(OPCION,"");
                String consulta = tv_buscar.getText().toString().trim();
                SharedPreferences.Editor editor = preferencia.edit();
                editor.putString(CONSULTA,consulta);

                Log.i("opcion", "OPCION ESCOGIDA: "+opcion);
                Log.i("opcion", "CONSULTA: "+consulta);

                if(!opcion.equalsIgnoreCase("") && !consulta.equalsIgnoreCase("")){
                    Call<Usuario> call = ApiAdaptar.getApiService().obtenerUser("null","null",4);
                    call.enqueue(new UsuarioCallback());

                } else if(opcion.equalsIgnoreCase("")){
                    Toast.makeText(GaleryUser.this,"Ingrese la opcion de busqueda",Toast.LENGTH_LONG).show();
                } else if (consulta.equalsIgnoreCase("")){
                    Toast.makeText(GaleryUser.this,"Ingrese la valor a consultar",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    class UserCallback implements Callback<Usuario>{
        @Override
        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
            if (response.isSuccessful()) {
                ArrayList<Result> usuarioList = (ArrayList<Result>) response.body().getResult();
                Result usuarioResp = response.body().getResult().get(0);

                if(usuarioResp.getCodigoError().equalsIgnoreCase("0000") ) {
                    mAdapter.setDataSet(usuarioList);
                    Toast.makeText(GaleryUser.this, "Cantidad de registrar son: "+usuarioList.size(), Toast.LENGTH_LONG).show();
                    Log.d("onResponse Usuarios", "Size de usuarios " + usuarioList.size());
                } else {
                    tv_mensaje.setText("No hay datos que mostrar");
                }
            }
        }
        @Override
        public void onFailure(Call<Usuario> call, Throwable t) {
            Log.i("error", "error " + t.getLocalizedMessage());
            Toast.makeText(GaleryUser.this, "Fallo la conexion "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    class UsuarioCallback implements Callback<Usuario> {
        @Override
        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
            preferencia = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
            String opcion = preferencia.getString(OPCION,"");
            String consulta = tv_buscar.getText().toString().trim();
            int contador = 0;

            Log.d("array", "opcion a consultar " + opcion);
            Log.d("array", "valor: " + consulta);

            ArrayList<Result> usuarioList = (ArrayList<Result>) response.body().getResult();
            Result usuarioResp = response.body().getResult().get(0);

            if(usuarioResp.getCodigoError().equalsIgnoreCase("0000") ) {
                ArrayList<Result> usuarioPresentar = new ArrayList<>();

                for (Result usuario : usuarioList  ) {

                    if(opcion.equalsIgnoreCase("nombre")){
                        if(usuario.getNombre().equalsIgnoreCase(consulta)){
                            usuarioPresentar.add(usuario);
                            Log.d("array", "Entro x aqui nombre: " + contador++);
                            Log.d("array", "elemento agregado" + usuarioPresentar.toString());
                        }

                    } else if(opcion.equalsIgnoreCase("apellido")){
                        if(usuario.getApellido().equalsIgnoreCase(consulta)){
                            usuarioPresentar.add(usuario);
                            Log.d("array", "Entro x aqui apellido: " + contador++);
                            Log.d("array", "elemento agregado" + usuarioPresentar.toString());
                        }

                    } else if (opcion.equalsIgnoreCase("email")){
                        if(usuario.getEmail().equalsIgnoreCase(consulta)){
                            usuarioPresentar.add(usuario);
                            Log.d("array", "Entro x aqui email: " + contador++);
                            Log.d("array", "elemento agregado" + usuarioPresentar.toString());
                        }

                    } else if (opcion.equalsIgnoreCase("Rol")){
                        if(usuario.getRol().equalsIgnoreCase(consulta)){
                            usuarioPresentar.add(usuario);
                            Log.d("array", "Entro x aqui rol: " + contador++);
                            Log.d("array", "elemento agregado" + usuarioPresentar.toString());
                        }
                    } else {
                        Log.d("array", "Entro x aqui: " + contador++);
                        usuarioPresentar.add(usuario);
                    }
                }
                if (usuarioPresentar.size() > 0) {
                    mAdapter.setDataSet(usuarioPresentar);
                    tv_mensaje.setText("");
                    Toast.makeText(GaleryUser.this, "Cantidad de registrar son: " + usuarioPresentar.size(), Toast.LENGTH_LONG).show();
                } else {
                    mAdapter = new UserAdaptador();
                    mRecyclerUser.setAdapter(mAdapter);
                    tv_mensaje.setText("No hay datos que mostrar");
                }

            } else {
                tv_mensaje.setText( usuarioResp.getMensajeError());
            }
        }

        @Override
        public void onFailure(Call<Usuario> call, Throwable t) {
            Log.i("error", "error " + t.getLocalizedMessage());
            Toast.makeText(GaleryUser.this, "Fallo la conexion "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }
    }
}