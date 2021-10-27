package com.example.samiwogdev.trustfund;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;
    Animation topAnim, bottomAnim; //variables

   ImageView logo;
   TextView slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_main);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        logo = findViewById(R.id.logo);
        slogan = findViewById(R.id.slogan);


        //Add Animation
        logo.setAnimation(topAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed( () -> {
            Intent intent = new Intent( MainActivity.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
              Pair[] pairs = new Pair[2];
              pairs[0] = new Pair<View, String>(logo, "logo_image");
              pairs[1] = new Pair<View, String>(slogan, "logo_text");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( MainActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }, SPLASH_SCREEN);


    }
}