package com.example.fleetmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/*
 * code by gurman and Rohit*/
public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

    }

    public void login(View view) {

        Intent i = new Intent(MainMenuActivity.this, AdminLoginActivity.class);
        startActivity(i);
    }

    public void Dlogin(View view) {
        Intent i = new Intent(MainMenuActivity.this, DriverLoginActivity.class);
        startActivity(i);
    }

    public void Mlogin(View view) {
        Intent i = new Intent(MainMenuActivity.this, MechanicLoginActivity.class);
        startActivity(i);
    }

}
