package com.example.fleetmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MechanicLoginActivity extends AppCompatActivity {
    EditText user_name ,password_et;

    ArrayList<String> insDueList;
    String lPlatesList;
    TableLayout tl;
    TextView forgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_login);

        user_name = (EditText) findViewById(R.id.edt_Mname);
        password_et = (EditText) findViewById(R.id.et_Mpass);

        forgotPassword=findViewById(R.id.Mforgetpass);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MechanicLoginActivity.this, ForgotPasswordAdminActivity.class);
                i.putExtra("index",2);
                startActivity(i);
            }
        });



    }

    public void MLogin(View view){

        final String mechUserName= user_name.getText().toString();

        String password = password_et.getText().toString();

        if(mechUserName.equals(""))
        {
            Toast.makeText(MechanicLoginActivity.this , "Please enter your User Name" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals(""))
        {
            Toast.makeText(MechanicLoginActivity.this , "Please enter your password" , Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject job = new JSONObject();

        try {
            job.put("mech_id" ,mechUserName);
            job.put("passwordkey" ,password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/Mechanic_Login.php", job,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getString("key").equals("done"))
                            {
                                SharedPreferences.Editor sp =getSharedPreferences("mechanic_info",MODE_PRIVATE).edit();
                                sp.putString("mechanic_id",response.getString("mechanic_id"));
                                sp.putString("username",mechUserName);
                                sp.putString("mechanic_email",response.getString("mec_mail"));
                                sp.putString("mechanic_name",response.getString("mec_nm"));
                                sp.putString("admin_id2",response.getString("admin_identity"));


                                sp.apply();

                                NotifyInsurance(response.getString("admin_identity"));

                            }
                            else {
                                Toast.makeText(MechanicLoginActivity.this , "error" , Toast.LENGTH_SHORT).show();
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

        AppControllerActivity app = new AppControllerActivity(MechanicLoginActivity.this);
        app.addToRequestQueue(jobjreq);


    }

    public void Cancel(View view) {
        Intent i = new Intent(MechanicLoginActivity.this, MainMenuActivity.class);
        startActivity(i);
    }

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


        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/workorder_notify.php", job,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jarr = null;
                        try {
                            jarr = response.getJSONArray("result");
                            for (int i = 0; i < jarr.length(); i++) {
                                if (response.getString("key").equals("done")) {

                                    String[] columns={"LicensePlate","Issue","Date"};

                                    tl = new TableLayout(MechanicLoginActivity.this);
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tl.setLayoutParams(lp);

                                    JSONObject jobj;
                                    TableRow tr;
                                    TextView txt;

                                    String s;

                                    tl.removeAllViews();
                                    // creating the header
                                    tr = new TableRow(MechanicLoginActivity.this);
                                    tr.setBackgroundResource(R.drawable.rectangle);
                                    TableLayout.LayoutParams lp1 =
                                            new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                                    TableLayout.LayoutParams.WRAP_CONTENT);

                                    tr.setLayoutParams(lp1);
                                    for (int y = 0; y < columns.length; y++) {
                                        txt = new TextView(MechanicLoginActivity.this);

                                        txt.setText(columns[y]);
                                        txt.setTextSize(18);
                                        txt.setPadding(15, 4, 15, 4);
                                        txt.setTextColor(Color.BLACK);
                                        // txt.setLayoutParams(lp1);

                                        tr.addView(txt);
                                    }
                                    tl.addView(tr);
                                    for (int x = 0; x < jarr.length(); x++) {
                                        tr = new TableRow(MechanicLoginActivity.this);
                                        tr.setLayoutParams(lp);
                                        tr.setBackgroundResource(R.drawable.backedit_black);
                                        jobj = jarr.getJSONObject(x);

                                        // getting the columns
                                        for (int y = 0; y < columns.length; y++) {
                                            txt = new TextView(MechanicLoginActivity.this);

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
                                    Intent x = new Intent(MechanicLoginActivity.this, MechanicMenuActivity.class);
                                    startActivity(x);
                                }
                                else {
                                    new AlertDialog.Builder(MechanicLoginActivity.this, R.style.MyAlertDialogStyle)
                                            .setTitle("Work Orders Due Reminder!!")
                                            .setIcon(R.drawable.workorder_new)
                                            .setMessage("Work Orders due in the next 7 Days:")
                                            .setView(tl)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    Intent i = new Intent(MechanicLoginActivity.this, MechanicMenuActivity.class);
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

        AppControllerActivity app = new AppControllerActivity(MechanicLoginActivity.this);
        app.addToRequestQueue(jobjreq);

    }
}
