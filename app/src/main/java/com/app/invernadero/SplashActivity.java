package com.app.invernadero;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    //Variables
    Animation topAnim, bottonAnim;
    ImageView logo;
    TextView nameEmpresa, nameApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //Animacion
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animition);
        bottonAnim = AnimationUtils.loadAnimation(this, R.anim.botton_animition);

        logo = findViewById(R.id.imagenLogo);
        nameApp = findViewById(R.id.NameApp);
        nameEmpresa = findViewById(R.id.NameEmpresa);

        logo.setAnimation(topAnim);
        nameApp.setAnimation(bottonAnim);
        nameEmpresa.setAnimation(bottonAnim);

        int SPLASH_SCREN = 5000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(logo, "imagen_logo");
                pairs[1] = new Pair<View, String>(logo, "text_logo");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        }, SPLASH_SCREN);

    }
}