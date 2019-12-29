package com.example.fleetmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateMechanicProfileActivity extends AppCompatActivity {
    EditText Aname,Auname,Apass,Acpass,Aemail,Amobile,secQues,secAns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mechanic_profile);

        Aname=(EditText)findViewById(R.id.adName);
        Auname=(EditText)findViewById(R.id.id_uname);
        Aemail=(EditText)findViewById(R.id.id_email);
        Amobile=(EditText)findViewById(R.id.id_cont);
        Apass=(EditText)findViewById(R.id.id_pass);
        Acpass=(EditText)findViewById(R.id.id_cpass);
        secQues=(EditText)findViewById(R.id.etSecQues);
        secAns=(EditText)findViewById(R.id.etSecAns);

        get_values();
    }

    public  void get_values()
    {
        JSONObject jobj = new JSONObject();
        SharedPreferences sp = getSharedPreferences("mechanic_info",MODE_PRIVATE);
        String adm_id = sp.getString("admin_id2" , "");

        try {
            jobj.put("id_key" ,adm_id);
            jobj.put("mid" ,sp.getString("mechanic_id" , ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/fill_mechProfile.php", jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jarr =  response.getJSONArray("result");

                    JSONObject job_box = (JSONObject) jarr.get(0);
                    Aname.setText(  job_box.getString("Name") );
                    Auname.setText(  job_box.getString("Username") );
                    Aemail.setText( job_box.getString("Email"));
                    Amobile.setText( job_box.getString("Mobile"));
                    Apass.setText( job_box.getString("Password"));
                    secAns.setText(job_box.getString("SecurityAnswer"));
                    secQues.setText(job_box.getString("SecurityQuestion"));


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


        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000 ,  2 , 2));
        AppControllerActivity app = new AppControllerActivity(UpdateMechanicProfileActivity.this);
        app.addToRequestQueue(jobreq);
    }

    public void UpdateMechanic(View view) {

        String Name= Aname.getText().toString();
        String Username =Auname.getText().toString();
        final String Email= Aemail.getText().toString();
        String Mobile =Amobile.getText().toString();

        String Password= Apass.getText().toString();
        String Cpassword= Acpass.getText().toString();

        String secQuestion=secQues.getText().toString();
        String secAnswer=secAns.getText().toString();

        if(Name.equals(""))
        {
            Toast.makeText(UpdateMechanicProfileActivity.this,"Please enter Name ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Username.equals(""))
        {
            Toast.makeText(UpdateMechanicProfileActivity.this,"Please enter Username ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Email.equals(""))
        {
            Toast.makeText(UpdateMechanicProfileActivity.this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Mobile.equals(""))
        {
            Toast.makeText(UpdateMechanicProfileActivity.this,"Please enter mobile",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Password.equals(""))
        {
            Toast.makeText(UpdateMechanicProfileActivity.this,"Please enter Password",Toast.LENGTH_SHORT).show();
            return;
        }


        if(!Cpassword.equals(Password))
        {
            Toast.makeText(UpdateMechanicProfileActivity.this,"password do not match",Toast.LENGTH_SHORT).show();
            return;
        }
        //end
        final JSONObject jobj = new JSONObject();


        SharedPreferences sp = getSharedPreferences("mechanic_info",MODE_PRIVATE);
        String admin_id = sp.getString("admin_id2" , "");

        try {

            jobj.put("A_name" ,Name);

            jobj.put("A_Username" ,Username);

            jobj.put("A_mob",Mobile);
            jobj.put("A_pass",Password);
            jobj.put("A_email",Email);

            jobj.put("a_id",admin_id);
            jobj.put("mid" ,sp.getString("mechanic_id" , ""));
            jobj.put("secQues" ,secQuestion);
            jobj.put("secAns" ,secAnswer);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/update_mechProf.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(UpdateMechanicProfileActivity.this ,"Error " , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("2"))
                    {
                        Toast.makeText(UpdateMechanicProfileActivity.this ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(UpdateMechanicProfileActivity.this ," Updated Successfully" , Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor sp =getSharedPreferences("mechanic_info",MODE_PRIVATE).edit();
                        sp.putString("username",Auname.getText().toString());
                        sp.putString("mechanic_name",Aname.getText().toString());
                        sp.putString("mechanic_email",Aemail.getText().toString());
                        sp.apply();
                        Intent i = new Intent(UpdateMechanicProfileActivity.this, MechanicMenuActivity.class);
                        startActivity(i);
                        finish();
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
        AppControllerActivity app=new AppControllerActivity(UpdateMechanicProfileActivity.this);
        app.addToRequestQueue(jobjreq);
    }
}
