package com.example.fleetmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * code by gurman and Rohit*/
public class AdminAddInventoryActivity extends AppCompatActivity {
//    private DrawerLayout drawerLayout;
//    public static Activity main_activity;
    ArrayList<String> insDueList;
    String lPlatesList;

    TextView nm,unm,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_inventory);
//        drawerLayout = findViewById(R.id.drawer_activity);
//        main_activity = AdminAddInventoryActivity.this;
//        nm= (TextView) findViewById(R.id.txtnname);
//        email= (TextView) findViewById(R.id.txtmail);
//        unm= (TextView) findViewById(R.id.txtusrm);
//
//        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
//        nm.setText(sp.getString("admin_name",""));
//        email.setText(sp.getString("admin_mail",""));
//        unm.setText(sp.getString("username",""));
//        NotifyInsurance();
    }

//    public void open_drawer(View view) {
//        drawerLayout.openDrawer(GravityCompat.START);
//    }


    public void add_driver(View view) {
        Intent intent = new Intent(AdminAddInventoryActivity.this, AddDriverActivity.class);
        startActivity(intent);
    }

    public void add_vehicle(View view) {
        Intent intent = new Intent(AdminAddInventoryActivity.this, AddVehicleActivity.class);
        startActivity(intent);
    }

    public void add_mechanic(View view) {
        Intent intent = new Intent(AdminAddInventoryActivity.this, AddMechanicActivity.class);
        startActivity(intent);
    }

    public void go_back(View view) {
        Intent intent = new Intent(AdminAddInventoryActivity.this, AdminMenuMainPageActivity.class);
        startActivity(intent);
    }

//    public void view_mechanics(View view) {
//        Intent intent = new Intent(AdminAddInventoryActivity.this, ViewMechanicsActivity.class);
//        startActivity(intent);
//    }
//    public void view_closed_order(View view) {
//        Intent intent = new Intent(AdminAddInventoryActivity.this, ViewClosedWorkOrderAdminActivity.class);
//        startActivity(intent);
//    }
//    public void view_open_order(View view) {
//        Intent intent = new Intent(AdminAddInventoryActivity.this, ViewOpenWorkOrderAdminActivity.class);
//        startActivity(intent);
//    }
//
//    public void view_all_order(View view) {
//        Intent intent = new Intent(AdminAddInventoryActivity.this, ViewAllWorkOrdersAdminActivity.class);
//        startActivity(intent);
//    }
//
//    public void assign_driver(View view) {
//
//        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
//        String u = sp.getString("admin_id2","");
//        Intent intent = new Intent(AdminAddInventoryActivity.this, ViewDriverActivity.class);
//        intent.putExtra("id_send",u);
//        startActivity(intent);
//
//    }
//
//    public void vehicle_view(View view) {
//
//        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
//        String u = sp.getString("admin_id2","");
//        Intent intent = new Intent(AdminAddInventoryActivity.this, ViewVehicleActivity.class);
//        intent.putExtra("id_send",u);
//        startActivity(intent);
//
//    }
//
//    public void view_workorder(View view) {
//    }
//
//    public void view_partsorder(View view) {
//        Intent intent = new Intent(AdminAddInventoryActivity.this, PartsOrderedActivity.class);
//        startActivity(intent);
//    }
//
//    public void shareapp(View view) {
//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("text/plain");
//        String shareBody = "Download the app via play store now...";
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//        startActivity(Intent.createChooser(sharingIntent, "Share via"));
//    }
//
//    public void rating(View view) {
//        final String appPackageName = "com.example.fleetmanager";
//
//        try {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//        } catch (android.content.ActivityNotFoundException anfe) {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
//        }
//    }
//
//    public void about_us(View view) {
//    }
//
//    public void open_Contactus(View view) {
//    }
//
//    public void log_out(View view) {
//        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.clear();
//        editor.commit();
//        Intent intent = new Intent(AdminAddInventoryActivity.this, AdminLoginActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    public void update_profile(View view) {
//
//        Intent intent = new Intent(AdminAddInventoryActivity.this, UpdateAdminProfileActivity.class);
//        startActivity(intent);
//    }

    public void NotifyInsurance(){


        JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");



        JSONObject job = new JSONObject();

        try {
            job.put("admin_id" ,admin_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        insDueList =new ArrayList<>();


        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/insuranceInsurance.php", job,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jarr = null;
                        try {
                            jarr = response.getJSONArray("result");
                            for(int i=0;i<jarr.length();i++)
                            {  if(response.getString("key").equals("done")) {

                                JSONObject job = (JSONObject) jarr.get(i);
                                insDueList.add(job.getString("LicensePlate")+"                  " + job.getString("InsuranceEndDate"));
                            }
                            else {
                                Toast.makeText(AdminAddInventoryActivity.this , "error" , Toast.LENGTH_SHORT).show();
                            }
                            }

                            lPlatesList="";

                            for(int i = 0; i< insDueList.size(); i++)
                            {
                                lPlatesList+= insDueList.get(i)+"\n ";
                            }

                            new AlertDialog.Builder(AdminAddInventoryActivity.this)
                                    .setTitle("Insurance Due Reminder!!")
                                    .setMessage("Insurance renewal due for the following vehicles:\n" +
                                            "LICENSE PLATE    DUE DATE\n" +
                                            lPlatesList)

                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);

            }
        });

        jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 ,2));

        AppControllerActivity app = new AppControllerActivity(AdminAddInventoryActivity.this);
        app.addToRequestQueue(jobjreq);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1: {
                Intent i=new Intent(AdminAddInventoryActivity.this, MainMenuActivity.class);
                startActivity(i);
              //  Toast.makeText(this, "Clicked Menu 1", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu2:{
                Intent i=new Intent(AdminAddInventoryActivity.this,ViewNotificationsActivity.class);
                startActivity(i);
             //   Toast.makeText(this, "Clicked Menu 2", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}


