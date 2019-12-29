package com.example.fleetmanager;

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

public class MechanicMenuActivity extends AppCompatActivity {
    TextView nm,unm,email;
    private DrawerLayout drawerLayout;

    ArrayList<String> insDueList;
    String lPlatesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic__menu);

        drawerLayout = findViewById(R.id.drawer_activity);
        nm= (TextView) findViewById(R.id.txtnname);
        email= (TextView) findViewById(R.id.txtmail);
        unm= (TextView) findViewById(R.id.txtusrm);

        SharedPreferences sp = getSharedPreferences("mechanic_info",MODE_PRIVATE);
        String uname =sp.getString("username","");
        String emaill =sp.getString("mechanic_email","");
        String nm1 =sp.getString("mechanic_name","");

        nm.setText(nm1);
        email.setText(emaill);
        unm.setText(uname);
//        NotifyInsurance();
    }

    public void open_drawer(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void WorkDetails(View view) {
        Intent intent = new Intent(MechanicMenuActivity.this, MechanicLogActivity.class);
        startActivity(intent);
    }

    public void order_parts(View view) {
        Intent intent = new Intent(MechanicMenuActivity.this, OrderPartsActivity.class);
        startActivity(intent);
    }

    public void close_work_order(View view) {

        Intent intent = new Intent(MechanicMenuActivity.this, CloseWorkOrderActivity.class);
        startActivity(intent);
    }



    public void open_work_orders(View view) {
        Intent intent = new Intent(MechanicMenuActivity.this, ViewOpenWorkOrderMechanicActivity.class);
        startActivity(intent);
    }

    public void log_out(View view) {
        Intent intent = new Intent(MechanicMenuActivity.this, MechanicLoginActivity.class);
        startActivity(intent);
    }

    public void update_profile(View view) {
        Intent intent = new Intent(MechanicMenuActivity.this, UpdateMechanicProfileActivity.class);
        startActivity(intent);
    }

    public void view_workorders(View view) {
        Intent intent = new Intent(MechanicMenuActivity.this, ViewWorkordersMechanicActivity.class);
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

//    public void NotifyInsurance(){
//
//
//        JSONObject jobj = new JSONObject();
//
//        SharedPreferences sp = getSharedPreferences("mechanic_info",MODE_PRIVATE);
//
//        JSONObject job = new JSONObject();
//
//        try {
//            job.put("admin_id", sp.getString("admin_id2",""));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        insDueList =new ArrayList<>();
//
//
//        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/workorder_notify.php", job,
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
//                                insDueList.add(job.getString("LicensePlate")+"         " +
//                                        job.getString("Issue")+"         " +
//                                        job.getString("Date") );
//                            }
//                            else {
//                                Toast.makeText(MechanicMenuActivity.this , "error" , Toast.LENGTH_SHORT).show();
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
//                            new AlertDialog.Builder(MechanicMenuActivity.this, R.style.MyAlertDialogStyle)
//                                    .setTitle("Work Orders Due Reminder!!")
//                                    .setIcon(R.drawable.workorder_new)
//                                    .setMessage("Work Orders due in the next 7 Days:\n" +
//                                            ("LIC. PLATE     ISSUE           DUE DATE\n") +
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
//        AppControllerActivity app = new AppControllerActivity(MechanicMenuActivity.this);
//        app.addToRequestQueue(jobjreq);
//
//    }
}
