package com.tgd.tgd_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tgd.multigame_design.general.Main;


/**
 * Crée par Jerome le 02/10/2017 pour le projet TestAndroidSlick
 */

public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_launch_screen);
        findViewById(R.id.image).setAlpha(0);
        findViewById(R.id.image).animate().alpha(1).setDuration(1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.image).animate().alpha(0).setDuration(1500);

            }},3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchScreenActivity.this,Main.class));
                finish();
            }
        },4000);
    }
}
