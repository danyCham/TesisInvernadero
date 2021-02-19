package com.app.invernadero;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.app.invernadero.Model.Result;
import com.app.invernadero.Model.Usuario;
import com.app.invernadero.Utils.ApiAdaptar;

import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    GridLayout gridMenu;
    CardView cdPerfil, cdUsuarios, cdHumedad, cdLuz, cdTemperatura, cdEstadisticas;
    String user= "", rol_User= "", email= "";
    int idUser= 0;
    TextView txt_name;
    SharedPreferences preferencias = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_name = findViewById(R.id.txtUserPrincipal);
        gridMenu = findViewById(R.id.mainGrid);
        cdPerfil = findViewById(R.id.CardPerfil);
        cdUsuarios = findViewById(R.id.CardUsuario);
        cdHumedad = findViewById(R.id.CardHumedad);
        cdLuz = findViewById(R.id.CardLuz);
        cdTemperatura = findViewById(R.id.CardTemperatura);
        cdEstadisticas = findViewById(R.id.CardEstadisticas);

        cdUsuarios.setVisibility(View.INVISIBLE);

        preferencias = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
        idUser = preferencias.getInt(RegistroActivity.ID_USER,0);
        email = preferencias.getString(RegistroActivity.MAIL,"");
        user = preferencias.getString(RegistroActivity.USER,"");
        rol_User = preferencias.getString(RegistroActivity.ROL,"");

        if (rol_User.equalsIgnoreCase("Administrador")){
            cdUsuarios.setVisibility(View.VISIBLE);
        }

        if (email != null && !email.equalsIgnoreCase("")){
            txt_name.setText(user.trim());
        } else {
            Call<Usuario> call = ApiAdaptar.getApiService().obtenerUser("",email,2);
            call.enqueue(new UsuarioCallback());
        }

        cdPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PerfilActivity.class);
                startActivity(intent);
            }
        });
        cdUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,GaleryUser.class);
                startActivity(intent);
            }
        });
        cdHumedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencias = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString(GalerySensor.SENSOR,"Humedad");
                editor.commit();

                Intent intent = new Intent(MainActivity.this,GalerySensor.class);
                startActivity(intent);
            }
        });
        cdLuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencias = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString(GalerySensor.SENSOR,"Luz");
                editor.commit();

                Intent intent = new Intent(MainActivity.this,GalerySensor.class);
                startActivity(intent);
            }
        });
        cdTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencias = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString(GalerySensor.SENSOR,"Temperatura");
                editor.commit();

                Intent intent = new Intent(MainActivity.this,GalerySensor.class);
                startActivity(intent);
            }
        });
        cdEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EstadisticasActivity.class);
                startActivity(intent);
            }
        });
    }

    class UsuarioCallback implements Callback<Usuario> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
            Result responseUser = new Result();
            responseUser = response.body().getResult().get(0);

            if(responseUser.getCodigoError().equalsIgnoreCase("0000") ) {
                int id_user = responseUser.getIdUsuario();
                SharedPreferences preferencia = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencia.edit();
                editor.putInt(RegistroActivity.ID_USER, id_user);
                editor.putString(RegistroActivity.USER, responseUser.getNombre());
                editor.putString(RegistroActivity.MAIL, responseUser.getEmail());
                editor.putString(RegistroActivity.ROL, responseUser.getRol());

                txt_name.setText(responseUser.getNombre());

            } else {
                Toast.makeText(MainActivity.this, responseUser.getMensajeError() , Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Usuario> call, Throwable t) {
            Log.i("error", "error " + t.getLocalizedMessage());
            Toast.makeText(MainActivity.this, "Fallo la conexion "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}