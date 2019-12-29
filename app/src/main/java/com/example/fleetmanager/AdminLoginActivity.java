package com.example.fleetmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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
public class AdminLoginActivity extends AppCompatActivity {
    EditText user_name ,password_et;
    ArrayList<String> insDueList;
    String lPlatesList;

    TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        user_name = (EditText) findViewById(R.id.edt_name);
        password_et = (EditText) findViewById(R.id.et_pass);


    }

    public void adminLogin(View view) {

         final String adminUserName= user_name.getText().toString();

          String password = password_et.getText().toString();

        if(adminUserName.equals(""))
        {
            Toast.makeText(AdminLoginActivity.this , "Please enter your User Name" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals(""))
        {
            Toast.makeText(AdminLoginActivity.this , "Please enter your password" , Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject job = new JSONObject();

        try {
            job.put("adminid" ,adminUserName);
            job.put("passwordkey" ,password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/admin_login.php", job,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getString("key").equals("done"))
                            {
                                SharedPreferences.Editor sp =getSharedPreferences("admin_info",MODE_PRIVATE).edit();
                                sp.putString("admin_id",response.getString("admin_id"));
                                sp.putString("username",adminUserName);
                                sp.putString("admin_name",response.getString("Name"));
                                sp.putString("admin_mail",response.getString("Email"));
                                sp.putString("admin_id2",response.getString("admin_identity"));
////                                sp.putString("name_id",response.getString("Name"));


                                sp.apply();
                                NotifyInsurance(response.getString("admin_identity"));

                            }
                            else {
                                Toast.makeText(AdminLoginActivity.this , "Usernname or password incorrect" , Toast.LENGTH_SHORT).show();
                            }
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

        AppControllerActivity app = new AppControllerActivity(AdminLoginActivity.this);
        app.addToRequestQueue(jobjreq);
    }

    public void admin_register(View view) {
        Intent i = new Intent(AdminLoginActivity.this, AdminSignupActivity.class);
        startActivity(i);
    }

    public void Cancel(View view) {
        Intent i = new Intent(AdminLoginActivity.this, MainMenuActivity.class);
        startActivity(i);
    }

    public void forgot_pass(View view) {
        Intent i = new Intent(AdminLoginActivity.this, ForgotPasswordAdminActivity.class);
        i.putExtra("index",0);
        startActivity(i);
    }

//    public void NotifyInsurance(String id) {
//
//
//        JSONObject job = new JSONObject();
//
//        try {
//            job.put("admin_id", id);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        insDueList = new ArrayList<>();
//
//
//        JsonObjectRequest jobjreq = new JsonObjectRequest("http://" + Ipaddress.ip + "/fleet/insuranceInsurance.php", job,
//
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        JSONArray jarr = null;
//                        try {
//                            jarr = response.getJSONArray("result");
//                            for (int i = 0; i < jarr.length(); i++) {
//                                if (response.getString("key").equals("done")) {
//
//                                    JSONObject job = (JSONObject) jarr.get(i);
//                                    insDueList.add(("(" + (i + 1) + ") " + job.getString("LicensePlate") + " ==> " + job.getString("InsuranceEndDate").trim()).trim());
//                                } else {
//                                    Toast.makeText(AdminLoginActivity.this, "error", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            lPlatesList = "";
//
//                            for (int i = 0; i < insDueList.size(); i++) {
//                                lPlatesList += insDueList.get(i) + "\n ";
//                            }
//                            if(insDueList.isEmpty())
//                            {
//                                Intent i = new Intent(AdminLoginActivity.this, AdminMenuMainPageActivity.class);
//                                startActivity(i);
//                            }
//                            else
//                            {
//
//
//                            new AlertDialog.Builder(AdminLoginActivity.this, R.style.MyAlertDialogStyle)
//                                    .setTitle("Insurance Due Reminder!!")
//                                    .setIcon(R.drawable.message)
//                                    .setMessage("Insurance renewal due for the following vehicles:\n" +
//                                            "LIC. PLATE ==> DUE DATE\n" +
//                                            lPlatesList)
//
//                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                            Intent i = new Intent(AdminLoginActivity.this, AdminMenuMainPageActivity.class);
//                                            startActivity(i);
//                                        }
//                                    })
//                                    .show();
//
//                            }
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
//        jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 ,2));
//
//        AppControllerActivity app = new AppControllerActivity(AdminLoginActivity.this);
//        app.addToRequestQueue(jobjreq);
//    }

    public void NotifyInsurance(String id){


        JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("mechanic_info",MODE_PRIVATE);

        JSONObject job = new JSONObject();

        try {
            job.put("admin_id",id);

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
                            for (int i = 0; i < jarr.length(); i++) {
                                if (response.getString("key").equals("done")) {

                                    String[] columns={"LicensePlate","InsuranceEndDate"};

                                    tl = new TableLayout(AdminLoginActivity.this);
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tl.setLayoutParams(lp);
                                    tl.setGravity(Gravity.CENTER);

                                    JSONObject jobj;
                                    TableRow tr;
                                    TextView txt;

                                    String s;

                                    tl.removeAllViews();
                                    // creating the header
                                    tr = new TableRow(AdminLoginActivity.this);
                                    tr.setBackgroundResource(R.drawable.rectangle);
                                    tr.setGravity(Gravity.CENTER);
                                    TableLayout.LayoutParams lp1 =
                                            new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                                    TableLayout.LayoutParams.WRAP_CONTENT);

                                    tr.setLayoutParams(lp1);
                                    for (int y = 0; y < columns.length; y++) {
                                        txt = new TextView(AdminLoginActivity.this);

                                        txt.setText(columns[y]);
                                        txt.setTextSize(18);
                                        txt.setPadding(15, 4, 15, 4);
                                        txt.setTextColor(Color.BLACK);
                                        // txt.setLayoutParams(lp1);

                                        tr.addView(txt);
                                    }
                                    tl.addView(tr);
                                    for (int x = 0; x < jarr.length(); x++) {
                                        tr = new TableRow(AdminLoginActivity.this);
                                        tr.setLayoutParams(lp);
                                        tr.setBackgroundResource(R.drawable.backedit_black);
                                        tr.setGravity(Gravity.CENTER);
                                        jobj = jarr.getJSONObject(x);

                                        // getting the columns
                                        for (int y = 0; y < columns.length; y++) {
                                            txt = new TextView(AdminLoginActivity.this);

                                            txt.setTextSize(16);
                                            //  txt.setLayoutParams(lp1);
                                            //   txt.setBackgroundResource(R.drawable.backedit_black);
                                            txt.setTextColor(Color.WHITE);

                                            s = jobj.getString(columns[y]);

                                            txt.setText(s);
                                            txt.setPadding(15, 4, 15, 4);
                                            tr.addView(txt);
                                        }
                                        tl.addView(tr);
                                    }

                                }

                                if(jarr.length()==0)
                                {
                                    Intent x = new Intent(AdminLoginActivity.this, AdminMenuMainPageActivity.class);
                                    startActivity(x);
                                }
                                else {
                                    new AlertDialog.Builder(AdminLoginActivity.this, R.style.MyAlertDialogStyle)
                                            .setTitle("Insurance Due Reminder!!")
                                            .setIcon(R.drawable.workorder_new)
                                            .setMessage("Insurance due in the next 30 Days:")
                                            .setView(tl)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    Intent i = new Intent(AdminLoginActivity.this, AdminMenuMainPageActivity.class);
                                                    startActivity(i);
                                                }
                                            })
                                            .show();
                                }


                            }
                        }catch (JSONException e) {
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

        AppControllerActivity app = new AppControllerActivity(AdminLoginActivity.this);
        app.addToRequestQueue(jobjreq);

    }
}
