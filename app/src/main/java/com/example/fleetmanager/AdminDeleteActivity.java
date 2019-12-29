package com.example.fleetmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminDeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete);
    }

    public void go_back(View view) {
        Intent intent = new Intent(AdminDeleteActivity.this, AdminMenuMainPageActivity.class);
        startActivity(intent);
    }

    public void delete_mechanics(View view) {
        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String u = sp.getString("admin_id2","");
        Intent intent = new Intent(AdminDeleteActivity.this, DeleteGenericActivity.class);
        intent.putExtra("id_send",u);
        intent.putExtra("table","mechanic");
        intent.putExtra("heading","Remove a Mechanic");
        startActivity(intent);
    }


    public void delete_driver(View view) {

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String u = sp.getString("admin_id2","");
        Intent intent = new Intent(AdminDeleteActivity.this, DeleteGenericActivity.class);
        intent.putExtra("id_send",u);
        intent.putExtra("table","driver");
        intent.putExtra("heading","Remove a Driver");
        startActivity(intent);

    }
    public void delete_vehicle(View view) {

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String u = sp.getString("admin_id2","");
        Intent intent = new Intent(AdminDeleteActivity.this, DeleteGenericActivity.class);
        intent.putExtra("id_send",u);
        intent.putExtra("table","vehicle_table");
        intent.putExtra("heading","Remove Vehicles");
        startActivity(intent);

    }
}
