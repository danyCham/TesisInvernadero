package com.app.invernadero;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.app.invernadero.Model.Result;
import com.app.invernadero.Model.Usuario;
import com.app.invernadero.Utils.ApiAdaptar;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btn_reg, btn_log, btn_new_contrasenia;
    ImageButton btn_face, btn_google;
    ImageView logo;
    TextView nameBV, nameDesc;
    EditText username, password;
    String tv_usuario, tv_password;
    private static String  ENCRYPT_KEY="clave-compartida-no-reveloar-nunca";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        btn_reg = findViewById(R.id.btn_reg_ini);
        btn_log = findViewById(R.id.btn_log_ini);
        //btn_new_contrasenia = findViewById(R.id.btn_olg_cont);
        logo = findViewById(R.id.Logo_login);
        nameBV = findViewById(R.id.bienvenida_logo);
        nameDesc = findViewById(R.id.tv_descripcion_logo);
        username = findViewById(R.id.editTextUserName);
        password = findViewById(R.id.editTextPassword);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);

                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(logo, "imagen_logo");
                pairs[1] = new Pair<View, String>(nameBV, "text_logo");
                pairs[2] = new Pair<View, String>(nameDesc, "text_logo_2");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_usuario = username.getText().toString().trim();
                tv_password = password.getText().toString().trim();

                if(!TextUtils.isEmpty(tv_usuario) && !TextUtils.isEmpty(tv_password)) {
                    int opcion = 2;

                    Call<Usuario> call = ApiAdaptar.getApiService().obtenerUser(tv_password,tv_usuario,opcion);
                    call.enqueue(new UsuarioCallback());

                } else if (TextUtils.isEmpty(tv_usuario)){
                    Toast.makeText(LoginActivity.this, "Favor ingrese el correo", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(tv_password)){
                    Toast.makeText(LoginActivity.this, "Favor ingrese el password", Toast.LENGTH_LONG).show();
                }
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

                if (responseUser.getEstado().equalsIgnoreCase("A")) {

                    String passCrypt = descifra(Base64.getDecoder().decode(responseUser.getClave()));

                    if (passCrypt.trim().equalsIgnoreCase(tv_password.trim())) {

                        int id_user = responseUser.getIdUsuario();
                        SharedPreferences preferencia = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencia.edit();
                        editor.putInt(RegistroActivity.ID_USER, id_user);
                        editor.putString(RegistroActivity.USER, responseUser.getNombre());
                        editor.putString(RegistroActivity.MAIL, responseUser.getEmail());
                        editor.putString(RegistroActivity.ROL, responseUser.getRol());
                        editor.commit();

                        //Toast.makeText(LoginActivity.this, "Bienvenid@ " + responseUser.getNombre().trim() + " a la app Greenhouse", Toast.LENGTH_LONG).show();
                        Intent intentHome = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intentHome);

                    } else {
                        Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Su estado no le permite ingresar al sistema, contactese con el administrador", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, responseUser.getMensajeError() , Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Usuario> call, Throwable t) {
            Log.i("error", "error " + t.getLocalizedMessage());
            Toast.makeText(LoginActivity.this, "Fallo la conexion "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public String descifra(byte[] cifrado){
        String sinCifrar = null;
        try {
            final Cipher aes = obtieneCipher();
            final byte[] bytes = aes.doFinal(cifrado);
            sinCifrar = new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
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
}