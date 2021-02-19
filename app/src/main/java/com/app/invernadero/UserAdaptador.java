package com.app.invernadero;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.app.invernadero.Model.Result;
import com.app.invernadero.Model.Usuario;
import com.app.invernadero.Model.UsuarioPost;
import com.app.invernadero.Utils.ApiAdaptar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdaptador extends RecyclerView.Adapter<UserAdaptador.ViewHolder>{

    private ArrayList<Result> mDataSet;
    public static final String USER_Consulta ="usuario_consulta";

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        public TextView tvNombre;
        public TextView tvApellido;
        public TextView tvEmail;
        public TextView tvDireccion;
        public TextView tvFecha;
        public TextView tvRol;
        public TextView tvTelefono;
        public TextView tvEstado;
        public Button btn_editar;
        public Button btn_eliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            this.tvNombre = itemView.findViewById(R.id.tvNombreUser);
            this.tvApellido = itemView.findViewById(R.id.tvApellidoUser);
            this.tvEmail = itemView.findViewById(R.id.tvEmail);
            this.tvDireccion = itemView.findViewById(R.id.tvDireccion);
            this.tvFecha = itemView.findViewById(R.id.tvFechaNac);
            this.tvRol = itemView.findViewById(R.id.tvRol);
            this.tvTelefono = itemView.findViewById(R.id.tvTelefono);
            this.tvEstado = itemView.findViewById(R.id.tvEstado);
            this.btn_editar = itemView.findViewById(R.id.btn_edit);
            this.btn_eliminar = itemView.findViewById(R.id.btn_elimiar);
        }
        void setOnclickListener(){
            btn_editar.setOnClickListener(this);
            btn_eliminar.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_edit:
                    Intent intent = new Intent(context,PerfilActivity.class);
                    intent.putExtra(USER_Consulta,tvEmail.getText());
                    context.startActivity(intent);
                    break;
                case R.id.btn_elimiar:

                    new AlertDialog.Builder(context)
                            .setTitle("Eliminar Usuario")
                            .setMessage("Esta seguro que desea eliminar al usuario "+tvNombre.getText())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    UsuarioPost usuarioRegistro = new UsuarioPost();
                                    usuarioRegistro.setPIdUsuario(0);
                                    usuarioRegistro.setPIdRegistra(1);
                                    usuarioRegistro.setPNombre(tvNombre.getText().toString().trim());
                                    usuarioRegistro.setPApellido(tvApellido.getText().toString().trim());
                                    usuarioRegistro.setPEmail(tvEmail.getText().toString().trim());
                                    usuarioRegistro.setPDireccion(tvDireccion.getText().toString().trim());
                                    usuarioRegistro.setPFechaNacimiento(tvFecha.getText().toString().trim());
                                    usuarioRegistro.setPTelefono(tvTelefono.getText().toString().trim());
                                    usuarioRegistro.setPClave("");
                                    usuarioRegistro.setPIdRol(2);
                                    usuarioRegistro.setPEstado("E");
                                    usuarioRegistro.setPOpcion(5);

                                    Call<Usuario> call = ApiAdaptar.getApiService().registrarUsuario(usuarioRegistro);
                                    call.enqueue(new Callback<Usuario>() {
                                        @Override
                                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                            Result responseUser = new Result();
                                            responseUser = response.body().getResult().get(0);

                                            if(responseUser.getCodigoError().equalsIgnoreCase("0000") ) {

                                                if(responseUser.getMensajeError().indexOf("Usuario eliminado exitosamente") == 0 ){
                                                    Toast.makeText(context, responseUser.getMensajeError(), Toast.LENGTH_LONG).show();
                                                    Intent intentHome = new Intent(context, MainActivity.class);
                                                    context.startActivity(intentHome);
                                                } else {
                                                    Toast.makeText(context, responseUser.getMensajeError(), Toast.LENGTH_LONG).show();
                                                }

                                            } else{
                                                Toast.makeText(context, responseUser.getMensajeError(), Toast.LENGTH_LONG).show();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Usuario> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                    break;
            }
        }
    }

    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public UserAdaptador() {
        mDataSet = new ArrayList<>();
    }

    public void setDataSet(ArrayList<Result> myDataSet){
        mDataSet = myDataSet;
        notifyDataSetChanged();
    }

    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public UserAdaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creamos una nueva vista
        View tv = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_views, parent, false);
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - obtenemos un elemento del dataset según su posición
        // - reemplazamos el contenido de los views según tales datos
        Result usuario = mDataSet.get(position);

        String estado = "Activo";
        if(usuario.getEstado().equalsIgnoreCase("I")){
            estado = "Inactivo";
        } else if (usuario.getEstado().equalsIgnoreCase("E")){
            estado = "Eliminado";
        } else if (usuario.getEstado().equalsIgnoreCase("S")){
            estado = "Suspendido";
        } else {
            estado = "Activo";
        }

        holder.tvNombre.setText(usuario.getNombre());
        holder.tvApellido.setText(usuario.getApellido());
        holder.tvEmail.setText(usuario.getEmail());
        holder.tvDireccion.setText(usuario.getDireccion());
        holder.tvFecha.setText(usuario.getFechaNacimiento().substring(0,10));
        holder.tvTelefono.setText(usuario.getTelefono());
        holder.tvRol.setText(usuario.getRol());
        holder.tvEstado.setText(estado);

        holder.setOnclickListener();
    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo (por ejemplo si implementamos filtros o búsquedas)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
