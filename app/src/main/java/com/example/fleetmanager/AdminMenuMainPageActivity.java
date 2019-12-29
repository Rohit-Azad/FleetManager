package com.example.fleetmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AdminMenuMainPageActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    public static Activity main_activity;

    ArrayList<String> insDueList;
    String lPlatesList;

    TextView nm,unm,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_main_page);

        drawerLayout = findViewById(R.id.drawer_activity);
        main_activity = AdminMenuMainPageActivity.this;
        nm= (TextView) findViewById(R.id.txtnname);
        email= (TextView) findViewById(R.id.txtmail);
        unm= (TextView) findViewById(R.id.txtusrm);

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        nm.setText(sp.getString("admin_name",""));
        email.setText(sp.getString("admin_mail",""));
        unm.setText(sp.getString("username",""));

//        NotifyInsurance();
    }

    public void add_inventory(View view) {
        Intent i=new Intent(AdminMenuMainPageActivity.this,AdminAddInventoryActivity.class);
        startActivity(i);
    }

    public void view_edit(View view) {
        Intent i=new Intent(AdminMenuMainPageActivity.this,AdminViewEditInventoryActivity.class);
        startActivity(i);
    }

    public void delete(View view) {
        Intent i=new Intent(AdminMenuMainPageActivity.this,AdminDeleteActivity.class);
        startActivity(i);
    }

    public void view_msg(View view) {

        Intent i=new Intent(AdminMenuMainPageActivity.this,ViewNotificationsActivity.class);
        startActivity(i);
    }

    public void view_sos_logs(View view) {

        Intent i=new Intent(AdminMenuMainPageActivity.this,AdminViewSOSLogsActivity.class);
        startActivity(i);
    }

    public void view_closed_order(View view) {
        Intent intent = new Intent(AdminMenuMainPageActivity.this, ViewClosedWorkOrderAdminActivity.class);
        startActivity(intent);
    }
    public void view_open_order(View view) {
        Intent intent = new Intent(AdminMenuMainPageActivity.this, ViewOpenWorkOrderAdminActivity.class);
        startActivity(intent);
    }

    public void view_all_order(View view) {
        Intent intent = new Intent(AdminMenuMainPageActivity.this, ViewAllWorkOrdersAdminActivity.class);
        startActivity(intent);
    }

    public void open_drawer(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
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
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(AdminMenuMainPageActivity.this, AdminLoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void update_profile(View view) {

        Intent intent = new Intent(AdminMenuMainPageActivity.this, UpdateAdminProfileActivity.class);
        startActivity(intent);
    }

    public void view_logs(View view) {

        Intent intent = new Intent(AdminMenuMainPageActivity.this, ViewLogsActivity.class);
        startActivity(intent);
    }
//
//    public void NotifyInsurance(){
//
//        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
//        String admin_id =sp.getString("admin_id2","");
//
//
//
//        JSONObject job = new JSONObject();
//
//        try {
//            job.put("admin_id" ,admin_id);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        insDueList =new ArrayList<>();
//
//
//        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/insuranceInsurance.php", job,
//
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        JSONArray jarr = null;
//                        try {
//                            jarr = response.getJSONArray("result");
//                            for(int i=0;i<jarr.length();i++)
//                            {  if(response.getString("key").equals("done")) {
//
//                                JSONObject job = (JSONObject) jarr.get(i);
//                                insDueList.add("["+(i+1)+"} "+job.getString("LicensePlate")+" ==> " + job.getString("InsuranceEndDate"));
//                            }
//                            else {
//                                Toast.makeText(AdminMenuMainPageActivity.this , "error" , Toast.LENGTH_SHORT).show();
//                            }
//                            }
//
//                            lPlatesList="";
//
//                            for(int i = 0; i< insDueList.size(); i++)
//                            {
//                                lPlatesList+= insDueList.get(i)+"\n ";
//                            }
//
//                            new AlertDialog.Builder(AdminMenuMainPageActivity.this,R.style.MyAlertDialogStyle)
//                                    .setTitle("Insurance Due Reminder!!")
//                                    .setIcon(R.drawable.message)
//                                    .setMessage("Insurance renewal due for the following vehicles:\n" +
//                                            "L. PLATE ==> DUE DATE\n" +
//                                            lPlatesList)
//
//                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                        }
//                                    })
//                                    .show();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                System.out.println(error);
//
//            }
//        });
//
//        jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 ,2));
//
//        AppControllerActivity app = new AppControllerActivity(AdminMenuMainPageActivity.this);
//        app.addToRequestQueue(jobjreq);
//
//    }
}
