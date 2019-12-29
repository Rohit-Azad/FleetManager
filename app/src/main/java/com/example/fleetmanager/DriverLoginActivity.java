package com.example.fleetmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverLoginActivity extends AppCompatActivity {
    EditText user_name ,password_et;

    TextView forgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_loginpage);
        user_name = (EditText) findViewById(R.id.edt_name);
        password_et = (EditText) findViewById(R.id.et_pass);

        forgotPassword=findViewById(R.id.forgetpass);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DriverLoginActivity.this, ForgotPasswordAdminActivity.class);
                i.putExtra("index",1);
                startActivity(i);
            }
        });
    }


    public void DrLogin(View view){

        final String DrUserName= user_name.getText().toString();

        String password = password_et.getText().toString();

        if(DrUserName.equals(""))
        {
            Toast.makeText(DriverLoginActivity.this , "Please enter your User Name" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals(""))
        {
            Toast.makeText(DriverLoginActivity.this , "Please enter your password" , Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject job = new JSONObject();

        try {
            job.put("drvr_id" ,DrUserName);
            job.put("passwordkey" ,password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/driver_login.php", job,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getString("key").equals("done"))
                            {
                                SharedPreferences.Editor sp =getSharedPreferences("driver_info",MODE_PRIVATE).edit();
                                sp.putString("d_id",response.getString("driver_id"));
                                sp.putString("username",DrUserName);
                                sp.putString("admin_id2",response.getString("admin_identity"));
                                sp.putString("driver_name",response.getString("Name"));
                                sp.putString("driver_mobile",response.getString("Mobile"));
                                sp.putString("lPlate",response.getString("vehicle"));
////                                sp.putString("name_id",response.getString("Name"));

                                sp.commit();
                                Intent i = new Intent(DriverLoginActivity.this, DriverMenuActivity.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(DriverLoginActivity.this , "error" , Toast.LENGTH_SHORT).show();
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

        AppControllerActivity app = new AppControllerActivity(DriverLoginActivity.this);
        app.addToRequestQueue(jobjreq);


    }

    public void Cancel(View view) {
        Intent i = new Intent(DriverLoginActivity.this, MainMenuActivity.class);
        startActivity(i);
    }
}
