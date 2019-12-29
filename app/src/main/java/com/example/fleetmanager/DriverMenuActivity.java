package com.example.fleetmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DriverMenuActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    TextView nm,unm,email;
    public static Activity main_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmenu);

        nm= (TextView) findViewById(R.id.txtnname);
        email= (TextView) findViewById(R.id.txtmail);
        unm= (TextView) findViewById(R.id.txtusrm);

        drawerLayout = findViewById(R.id.drawer_activity);

        main_activity = DriverMenuActivity.this;

        SharedPreferences sp = getSharedPreferences("driver_info",MODE_PRIVATE);

        String u = sp.getString("username","");
        unm.setText(u);
        nm.setText(sp.getString("driver_name",""));
        email.setText(sp.getString("driver_mobile",""));
    }

    public void SOS_start(View view) {

        Intent intent = new Intent(DriverMenuActivity.this, SOSActivity.class);
        startActivity(intent);
    }

    public void open_drawer(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void worder(View view) {
        Intent intent = new Intent(DriverMenuActivity.this, CreateWorkOrderActivity.class);
        startActivity(intent);
    }

    public void LogDetails(View view) {
        Intent intent = new Intent(DriverMenuActivity.this, DriverLogActivity.class);
        startActivity(intent);
    }

    public void updateProfile(View view) {
        Intent intent = new Intent(DriverMenuActivity.this, UpdateDriverProfileActivity.class);
        startActivity(intent);
    }

    public void shareapp(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Download the app via play store now...";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void rating(View view) {
        final String appPackageName = "com.example.fleetmanager";

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public void log_out(View view) {
        Intent intent = new Intent(DriverMenuActivity.this, DriverLoginActivity.class);
        startActivity(intent);
    }
    public void ViewWorkOrder(View view) {

        Intent intent = new Intent(DriverMenuActivity.this, DrvrWorkViewOne.class);
        startActivity(intent);

    }
}
