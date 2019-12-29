package com.example.fleetmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminViewEditInventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_edit_inventory);
    }

    public void go_back(View view) {
        Intent intent = new Intent(AdminViewEditInventoryActivity.this, AdminMenuMainPageActivity.class);
        startActivity(intent);
    }

    public void view_mechanics(View view) {
        Intent intent = new Intent(AdminViewEditInventoryActivity.this, ViewMechanicsActivity.class);
        startActivity(intent);
    }
    public void view_closed_order(View view) {
        Intent intent = new Intent(AdminViewEditInventoryActivity.this, ViewClosedWorkOrderAdminActivity.class);
        startActivity(intent);
    }
    public void view_open_order(View view) {
        Intent intent = new Intent(AdminViewEditInventoryActivity.this, ViewOpenWorkOrderAdminActivity.class);
        startActivity(intent);
    }

    public void view_all_order(View view) {
        Intent intent = new Intent(AdminViewEditInventoryActivity.this, ViewAllWorkOrdersAdminActivity.class);
        startActivity(intent);
    }

    public void assign_driver(View view) {

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String u = sp.getString("admin_id2","");
        Intent intent = new Intent(AdminViewEditInventoryActivity.this, ViewDriverActivity.class);
        intent.putExtra("id_send",u);
        startActivity(intent);

    }

    public void vehicle_view(View view) {

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String u = sp.getString("admin_id2","");
        Intent intent = new Intent(AdminViewEditInventoryActivity.this, ViewVehicleActivity.class);
        intent.putExtra("id_send",u);
        startActivity(intent);

    }

    public void view_partsorder(View view) {
        Intent intent = new Intent(AdminViewEditInventoryActivity.this, ViewOrderedPartsAdminActivity.class);
        startActivity(intent);
    }
}
