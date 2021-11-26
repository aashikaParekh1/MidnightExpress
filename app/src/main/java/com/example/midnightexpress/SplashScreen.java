
package com.example.midnightexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.midnightexpress.MainActivity;
import com.example.midnightexpress.R;

public class SplashScreen extends AppCompatActivity {
    ImageView viewS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewS = findViewById(R.id.SplashScreenImage);
        Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide);
        viewS.startAnimation((slideAnimation));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }
}
