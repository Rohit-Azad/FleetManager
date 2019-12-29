package com.example.fleetmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
/*
 * code by gurman and Rohit*/
public class AdminSignupActivity extends AppCompatActivity {

    EditText usernameBox , emailBox,passwordBox,cpasswordBox,mob,name1;
    Button buttonBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsignup);
        name1 = (EditText)findViewById(R.id.name);
        usernameBox = (EditText)findViewById(R.id.user);

        mob=(EditText)findViewById(R.id.Mobile);
        emailBox=(EditText)findViewById(R.id.email);
        passwordBox=(EditText)findViewById(R.id.pass);
        buttonBox=(Button)findViewById(R.id.register);
        cpasswordBox=(EditText)findViewById(R.id.cpass) ;
        View.OnClickListener btn_click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name =name1.getText().toString();
                String userNM =usernameBox.getText().toString();
                String mobile =mob.getText().toString();
                String email=emailBox.getText().toString();
                String password=passwordBox.getText().toString();
                String cpassword=cpasswordBox.getText().toString();

                if(name.equals(""))
                {
                    Toast.makeText(AdminSignupActivity.this,"please enter name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userNM.equals(""))
                {
                    Toast.makeText(AdminSignupActivity.this,"please enter username",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.equals("")) {
                    Toast.makeText(AdminSignupActivity.this, "please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(AdminSignupActivity.this, "please enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mobile.equals("")) {
                    Toast.makeText(AdminSignupActivity.this, "please enter mobile", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (password.equals("")) {
                    Toast.makeText(AdminSignupActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cpassword.equals(""))
                {Toast.makeText(AdminSignupActivity.this,"please confirm password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!cpassword.equals(password))
                {
                    Toast.makeText(AdminSignupActivity.this,"password do not match",Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject jobj = new JSONObject();

                try {
                    jobj.put("name" , name);
                    jobj.put("username" , userNM);
                    jobj.put("mobile" , mobile);
                    jobj.put("email" , email);
                    jobj.put("pass",password);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(jobj);
// JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/admin_signup.php"
                JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/admin_signup.php",jobj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getString("key").equals("0"))
                            {
                                Toast.makeText(AdminSignupActivity.this ,"User already exist" , Toast.LENGTH_SHORT).show();

                            }
                            else if(response.getString("key").equals("1")) {
                                Toast.makeText(AdminSignupActivity.this ,"Account created" , Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(AdminSignupActivity.this, MainMenuActivity.class);

                                startActivity(i);
                                finish();
                            }

                            else {
                                Toast.makeText(AdminSignupActivity.this ,"error" , Toast.LENGTH_SHORT).show();

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

                jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000,3,2))  ;
                AppControllerActivity app=new AppControllerActivity(AdminSignupActivity.this);
                app.addToRequestQueue(jobjreq);
            }
        };
        buttonBox.setOnClickListener(btn_click);
    }
}
