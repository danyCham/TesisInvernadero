package com.app.invernadero;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.app.invernadero.Model.Result;
import com.app.invernadero.Model.Usuario;
import com.app.invernadero.Model.UsuarioPost;
import com.app.invernadero.Utils.ApiAdaptar;
import com.google.android.material.textfield.TextInputLayout;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {
    SharedPreferences preferencias = null;
    String UserConsulta="", user = "", rol_User = "", email ="";
    int idUser = 0;
    Button btn_actualiza, btn_principal;
    EditText edt_nombre, edt_apellido, edt_direccion, edt_fecha, edt_email, edt_telefono, edt_pass;
    CheckBox  checkBox_Act,checkBox_Inac,checkBox_Sus,checkBox_Elim;
    RadioButton radioButtonAdm, radioButtonUser;
    LinearLayout groupEstado;
    RadioGroup groupRol;

    private int dia,mes,anio;
    private String fecha = "";

    final java.util.Date utilDate = new java.util.Date(); //fecha actual
    long lnMilisegundos = utilDate.getTime();
    java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilisegundos);//fecha en el formato deseado

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        btn_actualiza = findViewById(R.id.btn_actualiza);
        btn_principal = findViewById(R.id.btn_principal);
        edt_nombre = findViewById(R.id.edt_name_perfil);
        edt_apellido = findViewById(R.id.edt_ape_perfil);
        edt_direccion = findViewById(R.id.edt_dir_perfil);
        edt_fecha = findViewById(R.id.fechaNac_perfil);
        edt_email = findViewById(R.id.edt_email_perfil);
        edt_telefono = findViewById(R.id.edt_phone_perfil);
        edt_pass = findViewById(R.id.edt_pass_perfil);
        checkBox_Act = findViewById(R.id.cb_activo);
        checkBox_Inac = findViewById(R.id.cb_inactivo);
        checkBox_Sus = findViewById(R.id.cb_eliminado);
        checkBox_Elim = findViewById(R.id.cb_suspendido);
        radioButtonAdm = findViewById(R.id.edt_rol_perfil_uno);
        radioButtonUser = findViewById(R.id.edt_rol_perfil_dos);
        groupRol = findViewById(R.id.groupRadioRol);
        groupEstado = findViewById(R.id.groupCheckEstado);

        edt_email.setEnabled(false);
        groupRol.setVisibility(View.INVISIBLE);
        groupEstado.setVisibility(View.INVISIBLE);

        preferencias = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
        idUser = preferencias.getInt(RegistroActivity.ID_USER,0);
        user = preferencias.getString(RegistroActivity.USER,"");
        email = preferencias.getString(RegistroActivity.MAIL,"");
        rol_User = preferencias.getString(RegistroActivity.ROL,"");

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            UserConsulta = extras.getString(UserAdaptador.USER_Consulta);
        }

        if (!UserConsulta.equalsIgnoreCase("") ){
            Call<Usuario> call = ApiAdaptar.getApiService().obtenerUser("null",UserConsulta,2);
            call.enqueue(new UsuarioCallback());
        } else {
            Call<Usuario> call = ApiAdaptar.getApiService().obtenerUser("null",email,2);
            call.enqueue(new UsuarioCallback());
        }

        if (rol_User.trim().equalsIgnoreCase("Administrador")){
            groupRol.setVisibility(View.VISIBLE);
            groupEstado.setVisibility(View.VISIBLE);
            if(UserConsulta != null && !UserConsulta.equalsIgnoreCase("")) {
                if (!UserConsulta.equalsIgnoreCase(email)) {
                    edt_nombre.setEnabled(false);
                    edt_apellido.setEnabled(false);
                    edt_direccion.setEnabled(false);
                    edt_fecha.setEnabled(false);
                    edt_telefono.setEnabled(false);
                    edt_pass.setEnabled(false);
                }
            }
        }
        btn_actualiza.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                final int id_usuario_reg = idUser;
                final String nombre = edt_nombre.getText().toString().trim();
                final String apellido = edt_apellido.getText().toString().trim();
                final String email = edt_email.getText().toString().trim();
                final String direccion = edt_direccion.getText().toString().trim();
                final String telefono = edt_telefono.getText().toString().trim();
                final String fecha_nacimiento = edt_fecha.getText().toString().trim();
                final String password = edt_pass.getText().toString().trim();
                int rolselect = 2;
                String estadoSelect = "A";
                if (rol_User.equalsIgnoreCase("Administrador")){
                    if (radioButtonAdm.isChecked()){
                        rolselect = 1;
                    } else {
                        rolselect = 2;
                    }

                    if (checkBox_Inac.isChecked()){
                        estadoSelect = "I";
                    } else if (checkBox_Elim.isChecked()){
                        estadoSelect = "E";
                    } else if (checkBox_Sus.isChecked()){
                        estadoSelect = "S";
                    } else {
                        estadoSelect = "A";
                    }
                }

                final int id_rol = rolselect;
                final String estado = estadoSelect;
                final int opcion = 3;

                Pattern patronNomApe = Pattern.compile("^[a-zA-Z ]+$");
                Pattern correo = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

                if((!TextUtils.isEmpty(nombre) && patronNomApe.matcher(nombre).matches() && nombre.length() <= 10 )
                        && (!TextUtils.isEmpty(apellido) && patronNomApe.matcher(apellido).matches() && apellido.length() <= 10 )
                        && (!TextUtils.isEmpty(email) && correo.matcher(email).matches() && email.length() >= 10)
                        && (!TextUtils.isEmpty(direccion) && direccion.length() >= 10)
                        && (!TextUtils.isEmpty(telefono) && telefono.length() == 10)
                        && (!TextUtils.isEmpty(password) && password.length() >= 8)) {

                    String passCrypt = cifra(password);

                    UsuarioPost usuarioRegistro = new UsuarioPost();
                    usuarioRegistro.setPIdUsuario(0);
                    usuarioRegistro.setPIdRegistra(id_usuario_reg);
                    usuarioRegistro.setPNombre(nombre);
                    usuarioRegistro.setPApellido(apellido);
                    usuarioRegistro.setPEmail(email);
                    usuarioRegistro.setPDireccion(direccion);
                    usuarioRegistro.setPFechaNacimiento(fecha_nacimiento);
                    usuarioRegistro.setPTelefono(telefono);
                    usuarioRegistro.setPClave(passCrypt);
                    usuarioRegistro.setPIdRol(id_rol);
                    usuarioRegistro.setPEstado(estado);
                    usuarioRegistro.setPOpcion(opcion);

                    Call<Usuario> call = ApiAdaptar.getApiService().registrarUsuario(usuarioRegistro);
                    call.enqueue(new UsuarioActualizarCallback());

                } else if (TextUtils.isEmpty(nombre) || !patronNomApe.matcher(nombre).matches() || nombre.length() > 11 ){

                    if (TextUtils.isEmpty(nombre)) {
                        Toast.makeText(PerfilActivity.this, "Favor ingrese el nombre", Toast.LENGTH_LONG).show();

                    } else if (!patronNomApe.matcher(nombre).matches()){
                        Toast.makeText(PerfilActivity.this, "El formato del nombre no es correcto", Toast.LENGTH_LONG).show();

                    } else if (nombre.length() > 11){
                        Toast.makeText(PerfilActivity.this, "Excedio el tamaño del campo nombre", Toast.LENGTH_LONG).show();
                    }

                } else if (TextUtils.isEmpty(apellido) || !patronNomApe.matcher(apellido).matches() || apellido.length() > 11){

                    if (TextUtils.isEmpty(apellido)) {
                        Toast.makeText(PerfilActivity.this, "Favor ingrese el apellido", Toast.LENGTH_LONG).show();
                    } else if (!patronNomApe.matcher(apellido).matches()){
                        Toast.makeText(PerfilActivity.this, "El formato del apellido no es correcto", Toast.LENGTH_LONG).show();
                    } else if (apellido.length() > 11){
                        Toast.makeText(PerfilActivity.this, "Excedio el tamaño del campo apellido", Toast.LENGTH_LONG).show();
                    }

                } else if (TextUtils.isEmpty(direccion) || direccion.length() < 11){

                    if (TextUtils.isEmpty(direccion)) {
                        Toast.makeText(PerfilActivity.this, "Favor ingrese la direccion", Toast.LENGTH_LONG).show();
                    } else if (direccion.length() < 11){
                        Toast.makeText(PerfilActivity.this, "El tamaño del campo direccion como minimo debe ser 10 caracteres", Toast.LENGTH_LONG).show();
                    }

                } else if (TextUtils.isEmpty(fecha_nacimiento)){

                    Toast.makeText(PerfilActivity.this, "Favor ingrese la fecha de nacimiento", Toast.LENGTH_LONG).show();

                } else if (TextUtils.isEmpty(email) || !correo.matcher(email).matches() || email.length() < 10){

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(PerfilActivity.this, "Favor ingrese el email", Toast.LENGTH_LONG).show();
                    }  else if (!correo.matcher(email).matches()){
                        Toast.makeText(PerfilActivity.this, "No es un formato valido para el correo", Toast.LENGTH_LONG).show();
                    }  else {
                        Toast.makeText(PerfilActivity.this, "El tamaño del campo email como minimo debe ser 10 caracteres", Toast.LENGTH_LONG).show();
                    }

                } else if (TextUtils.isEmpty(telefono) || telefono.length() != 10){

                    if (TextUtils.isEmpty(telefono)) {
                        Toast.makeText(PerfilActivity.this, "Favor ingrese el telefono", Toast.LENGTH_LONG).show();
                    } else if (telefono.length() != 10 ){
                        Toast.makeText(PerfilActivity.this, "El tamaño del campo telefono debe ser 10 caracteres", Toast.LENGTH_LONG).show();
                    }

                } else if (TextUtils.isEmpty(password) || password.length() < 9){

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(PerfilActivity.this, "Favor ingrese el password", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(PerfilActivity.this, "El tamaño campo password como minimo debe ser 8 caracteres", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btn_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        edt_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PerfilActivity.this, new DatePickerDialog.OnDateSetListener() {
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
    class UsuarioCallback implements Callback<Usuario> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
            Result responseUser = new Result();
            responseUser = response.body().getResult().get(0);

            String passCrypt = descifra(Base64.getDecoder().decode(responseUser.getClave()));

            if(responseUser.getCodigoError().equalsIgnoreCase("0000") ) {
                edt_nombre.setText(responseUser.getNombre());
                edt_apellido.setText(responseUser.getApellido());
                edt_direccion.setText(responseUser.getDireccion());
                edt_fecha.setText(responseUser.getFechaNacimiento().substring(0,10));
                edt_email.setText(responseUser.getEmail());
                edt_telefono.setText(responseUser.getTelefono());
                edt_pass.setText(passCrypt);
                if (rol_User.equalsIgnoreCase("Administrador")){
                    if (responseUser.getRol().equalsIgnoreCase("Administrador")) {
                        radioButtonAdm.setChecked(true);
                    } else {
                        radioButtonUser.setChecked(true);
                    }
                    if (responseUser.getEstado().equalsIgnoreCase("I")){
                        checkBox_Inac.setChecked(true);
                    } else if (responseUser.getEstado().equalsIgnoreCase("S")){
                        checkBox_Sus.setChecked(true);
                    } else if (responseUser.getEstado().equalsIgnoreCase("E")){
                        checkBox_Elim.setChecked(true);
                    } else {
                        checkBox_Act.setChecked(true);
                    }
                }

            } else {
                Toast.makeText(PerfilActivity.this, responseUser.getMensajeError() , Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Usuario> call, Throwable t) {
            Log.i("error", "error " + t.getLocalizedMessage());
            Toast.makeText(PerfilActivity.this, "Fallo la conexion "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    class UsuarioActualizarCallback implements Callback<Usuario>{

        @Override
        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
            Result responseUser = new Result();
            responseUser = response.body().getResult().get(0);

            if(responseUser.getCodigoError().equalsIgnoreCase("0000") ) {

                if(responseUser.getMensajeError().indexOf("Usuario actualizado exitosamente") == 0 ){
                    Toast.makeText(PerfilActivity.this, responseUser.getMensajeError(), Toast.LENGTH_LONG).show();
                    Intent intentHome = new Intent(PerfilActivity.this, MainActivity.class);
                    startActivity(intentHome);
                } else {
                    Toast.makeText(PerfilActivity.this, "Fallo el envio de los datos...", Toast.LENGTH_LONG).show();
                }

            } else{
                Toast.makeText(PerfilActivity.this, responseUser.getMensajeError(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Usuario> call, Throwable t) {
            Log.i("error", "error " + t.getLocalizedMessage());
            Toast.makeText(PerfilActivity.this, "Fallo la conexion "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String cifra(String sinCifrar) {
        byte[] cifrado= null ;
        try {
            final byte[] bytes = sinCifrar.getBytes("UTF-8");
            final Cipher aes = obtieneCipher_1();
            cifrado = aes.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(cifrado);
    }

    public String descifra(byte[] cifrado){
        String sinCifrar = null;
        try {
            final Cipher aes = obtieneCipher();
            final byte[] bytes = aes.doFinal(cifrado);
            sinCifrar = new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.getStackTrace();
        }
        return sinCifrar;
    }

    private Cipher obtieneCipher() throws Exception {
        final String frase = "FraseLargaConDiferentesLetrasNumerosYCaracteresEspeciales_áÁéÉíÍóÓúÚüÜñÑ1234567890!#%$&()=%_NO_USAR_ESTA_FRASE!_";
        final MessageDigest digest = MessageDigest.getInstance("SHA");
        digest.update(frase.getBytes("UTF-8"));
        final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
        final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, key);
        return aes;
    }

    private Cipher obtieneCipher_1() throws Exception {
        final String frase = "FraseLargaConDiferentesLetrasNumerosYCaracteresEspeciales_áÁéÉíÍóÓúÚüÜñÑ1234567890!#%$&()=%_NO_USAR_ESTA_FRASE!_";
        final MessageDigest digest = MessageDigest.getInstance("SHA");
        digest.update(frase.getBytes("UTF-8"));
        final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

        final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aes.init(Cipher.ENCRYPT_MODE, key);
        return aes;
    }


}