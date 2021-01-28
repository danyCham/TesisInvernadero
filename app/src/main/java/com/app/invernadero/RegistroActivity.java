package com.app.invernadero;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.invernadero.Model.Result;
import com.app.invernadero.Model.Usuario;
import com.app.invernadero.Model.UsuarioPost;
import com.app.invernadero.Utils.ApiAdaptar;
import com.app.invernadero.Utils.RSA;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {
    Button btn_reg_r, btn_log_r;
    ImageView logo;
    TextView nameBV, nameDesc;
    EditText edt_nombre, edt_apellido, edt_direccion, edt_fecha, edt_email, edt_telefono, edt_pass, edt_pass_conf;

    private int dia,mes,anio;
    private String fecha = "";

    final java.util.Date utilDate = new java.util.Date(); //fecha actual
    long lnMilisegundos = utilDate.getTime();
    java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilisegundos);//fecha en el formato deseado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registro);

        btn_reg_r = findViewById(R.id.btn_reg_reg);
        btn_log_r = findViewById(R.id.btn_log_reg);
        logo = findViewById(R.id.Logo_reg);
        nameBV = findViewById(R.id.tv_registrate_log);
        nameDesc = findViewById(R.id.tv_des_logo);
        edt_nombre = findViewById(R.id.edt_name_reg);
        edt_apellido = findViewById(R.id.edt_ape_reg);
        edt_direccion = findViewById(R.id.edt_dir_reg);
        edt_fecha = findViewById(R.id.fechaNac_reg);
        edt_email = findViewById(R.id.edt_email_reg);
        edt_telefono = findViewById(R.id.edt_phone_reg);
        edt_pass = findViewById(R.id.edt_pass_reg);
        edt_pass_conf = findViewById(R.id.edt_pass_conf_reg);

        btn_reg_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int id_usuario = 0;
                final int id_usuario_reg = 0;
                final String nombre = edt_nombre.getText().toString().trim();
                final String apellido = edt_apellido.getText().toString().trim();
                final String email = edt_email.getText().toString().trim();
                final String direccion = edt_direccion.getText().toString().trim();
                final String telefono = edt_telefono.getText().toString().trim();
                final String fecha_nacimiento = edt_fecha.getText().toString().trim();
                /*Date fecha_nac = null;
                if (!TextUtils.isEmpty(fecha)) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String strDate = fecha;
                        fecha_nac = new Date(sdf.parse(strDate).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                final Date fecha_nacimiento = fecha_nac;*/
                final String password = edt_pass.getText().toString().trim();
                final String password_conf = edt_pass_conf.getText().toString().trim();
                final int id_rol = 2;
                final String estado = "A";
                final int opcion = 1;

                Pattern patronNomApe = Pattern.compile("^[a-zA-Z ]+$");
                Pattern correo = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

                if((!TextUtils.isEmpty(nombre) && patronNomApe.matcher(nombre).matches() && nombre.length() <= 10 )
                        && (!TextUtils.isEmpty(apellido) && patronNomApe.matcher(apellido).matches() && apellido.length() <= 10 )
                        && (!TextUtils.isEmpty(email) && correo.matcher(email).matches() && email.length() >= 10)
                        && (!TextUtils.isEmpty(direccion) && direccion.length() >= 10)
                        && (!TextUtils.isEmpty(telefono) && telefono.length() == 10)
                        && (!TextUtils.isEmpty(fecha))
                        && (!TextUtils.isEmpty(password) && password.length() >= 8)
                        && (!TextUtils.isEmpty(password_conf) && password_conf.length() >= 8)
                        && (password.equals(password_conf))) {

                    String passCrypt = crypt(password);

                    UsuarioPost usuarioRegistro = new UsuarioPost();
                    usuarioRegistro.setPIdUsuario(id_usuario);
                    usuarioRegistro.setPIdRegistra(id_usuario_reg);
                    usuarioRegistro.setPNombre(nombre);
                    usuarioRegistro.setPApellido(apellido);
                    usuarioRegistro.setPEmail(email);
                    usuarioRegistro.setPDireccion(direccion);
                    usuarioRegistro.setPFechaNacimiento(fecha_nacimiento);
                    usuarioRegistro.setPTelefono(telefono);
                    usuarioRegistro.setPClave(password);
                    usuarioRegistro.setPIdRol(id_rol);
                    usuarioRegistro.setPEstado(estado);
                    usuarioRegistro.setPOpcion(opcion);

                    Log.i("error", "Clave encriptada: " + passCrypt);

                    Call<Usuario> call = ApiAdaptar.getApiService().registrarUsuario(usuarioRegistro);
                    call.enqueue(new UsuarioCallback());

                } else if (TextUtils.isEmpty(nombre) || !patronNomApe.matcher(nombre).matches() || nombre.length() > 11 ){

                    if (TextUtils.isEmpty(nombre)) {
                        Toast.makeText(RegistroActivity.this, "Favor ingrese el nombre", Toast.LENGTH_LONG).show();

                    } else if (!patronNomApe.matcher(nombre).matches()){
                        Toast.makeText(RegistroActivity.this, "El formato del nombre no es correcto", Toast.LENGTH_LONG).show();

                    } else if (nombre.length() > 11){
                        Toast.makeText(RegistroActivity.this, "Excedio el tamaño del campo nombre", Toast.LENGTH_LONG).show();
                    }

                } else if (TextUtils.isEmpty(apellido) || !patronNomApe.matcher(apellido).matches() || apellido.length() > 11){

                    if (TextUtils.isEmpty(apellido)) {
                        Toast.makeText(RegistroActivity.this, "Favor ingrese el apellido", Toast.LENGTH_LONG).show();
                    } else if (!patronNomApe.matcher(apellido).matches()){
                        Toast.makeText(RegistroActivity.this, "El formato del apellido no es correcto", Toast.LENGTH_LONG).show();
                    } else if (apellido.length() > 11){
                        Toast.makeText(RegistroActivity.this, "Excedio el tamaño del campo apellido", Toast.LENGTH_LONG).show();
                    }

                } else if (TextUtils.isEmpty(direccion) || direccion.length() < 11){

                    if (TextUtils.isEmpty(direccion)) {
                        Toast.makeText(RegistroActivity.this, "Favor ingrese la direccion", Toast.LENGTH_LONG).show();
                    } else if (direccion.length() < 11){
                        Toast.makeText(RegistroActivity.this, "El tamaño del campo direccion como minimo debe ser 10 caracteres", Toast.LENGTH_LONG).show();
                    }

                } else if (TextUtils.isEmpty(fecha)){

                    Toast.makeText(RegistroActivity.this, "Favor ingrese la fecha de nacimiento", Toast.LENGTH_LONG).show();

                } else if (TextUtils.isEmpty(email) || !correo.matcher(email).matches() || email.length() < 10){

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(RegistroActivity.this, "Favor ingrese el email", Toast.LENGTH_LONG).show();
                    }  else if (!correo.matcher(email).matches()){
                        Toast.makeText(RegistroActivity.this, "No es un formato valido para el correo", Toast.LENGTH_LONG).show();
                    }  else {
                        Toast.makeText(RegistroActivity.this, "El tamaño del campo email como minimo debe ser 10 caracteres", Toast.LENGTH_LONG).show();
                    }

                } else if (TextUtils.isEmpty(telefono) || telefono.length() != 10){

                    if (TextUtils.isEmpty(telefono)) {
                        Toast.makeText(RegistroActivity.this, "Favor ingrese el telefono", Toast.LENGTH_LONG).show();
                    } else if (telefono.length() != 10 ){
                        Toast.makeText(RegistroActivity.this, "El tamaño del campo telefono debe ser 10 caracteres", Toast.LENGTH_LONG).show();
                    }

                } else if (TextUtils.isEmpty(password) || password.length() < 9){

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(RegistroActivity.this, "Favor ingrese el password", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegistroActivity.this, "El tamaño campo password como minimo debe ser 8 caracteres", Toast.LENGTH_LONG).show();
                    }
                } else if (TextUtils.isEmpty(password_conf)){

                    if (TextUtils.isEmpty(password_conf)) {
                        Toast.makeText(RegistroActivity.this, "Favor ingrese el password de confirmacion", Toast.LENGTH_LONG).show();
                    }

                } else if (!password.equals(password_conf)){

                    Toast.makeText(RegistroActivity.this, "No coinciden las contraseñas", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_log_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(logo, "imagen_logo");
                pairs[1] = new Pair<View, String>(nameBV, "text_logo");
                pairs[2] = new Pair<View, String>(nameDesc, "text_logo_2");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegistroActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        edt_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistroActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if (monthOfYear < 9 && monthOfYear >= 0 && dayOfMonth <= 9 && dayOfMonth >0){
                                    fecha = year + "-" + "0"+(monthOfYear + 1) + "-" + "0"+dayOfMonth;

                                } else if (monthOfYear < 9 && monthOfYear >= 0){
                                    fecha = year + "-" + "0"+(monthOfYear + 1) + "-" +dayOfMonth;

                                } else if (dayOfMonth <= 9 && dayOfMonth >0){
                                    fecha = year + "-" +(monthOfYear + 1) + "-" + "0"+dayOfMonth;

                                } else {
                                    fecha = year + "-" +(monthOfYear + 1) + "-" + dayOfMonth;

                                }
                                edt_fecha.setText(fecha);
                            }
                        },
                        anio,
                        mes,
                        dia);
                datePickerDialog.show();
            }
        });
    }

    class UsuarioCallback implements Callback<Usuario>{

        @Override
        public void onResponse(Call<Usuario> call, Response<Usuario> response) {

            Result responseUser = new Result();
            responseUser = response.body().getResult().get(0);

            if(responseUser.getCodigoError().equalsIgnoreCase("0000") ) {

                if(responseUser.getMensajeError().indexOf("Usuario creado exitosamente") == 0 ){
                    Toast.makeText(RegistroActivity.this, responseUser.getMensajeError(), Toast.LENGTH_LONG).show();
                    Intent intentHome = new Intent(RegistroActivity.this, MainActivity.class);
                    startActivity(intentHome);
                } else {
                    Toast.makeText(RegistroActivity.this, "Fallo el envio de los datos...", Toast.LENGTH_LONG).show();
                }

            } else{
                Toast.makeText(RegistroActivity.this, responseUser.getMensajeError(), Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onFailure(Call<Usuario> call, Throwable t) {
            Log.i("error", "error " + t.getLocalizedMessage());
            Toast.makeText(RegistroActivity.this, "Fallo la conexion "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public String crypt(String clave) {
        String encode_text= null;
        try {

            RSA rsa = new RSA();

            //le asignamos el Contexto
            rsa.setContext(getBaseContext());

            //Generamos un juego de claves
            rsa.genKeyPair(1024);

            //Guardamos en la memoria las claves
            rsa.saveToDiskPrivateKey("rsa.pri");
            rsa.saveToDiskPublicKey("rsa.pub");

            //Ciframos
            encode_text = rsa.Encrypt(clave);
        } catch (Exception e) {

        }
        return encode_text;
    }
}