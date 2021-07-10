package com.example.searchkodepos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    //Untuk mengatur lama waktu loading splash screen 5 detik = 5000
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Setelah loading tampilan splash screen langsung pindah ke mainactivity(tampilan home/Search)
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            //Waktu loading
        }, SPLASH_TIME_OUT);
    }
}
