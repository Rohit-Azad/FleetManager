package com.example.fleetmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class UpdateDriverProfileActivity extends AppCompatActivity {

    EditText Aname,Auname,Apass,Acpass, vehicle,Amobile,dLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_driver_profile);

        Aname=(EditText)findViewById(R.id.adName);
        Auname=(EditText)findViewById(R.id.id_uname);
        vehicle =(EditText)findViewById(R.id.id_vehicle);
        Amobile=(EditText)findViewById(R.id.id_cont);
        Apass=(EditText)findViewById(R.id.id_pass);
        Acpass=(EditText)findViewById(R.id.id_cpass);
        dLicense=findViewById(R.id.etDLicense);

        get_values();
    }

    public  void get_values()
    {
        JSONObject jobj = new JSONObject();
        SharedPreferences sp = getSharedPreferences("driver_info",MODE_PRIVATE);
        String adm_id = sp.getString("admin_id2" , "");


        try {
            jobj.put("id_key" ,adm_id);
            jobj.put("did",sp.getString("d_id",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/fill_driverProfile.php", jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jarr =  response.getJSONArray("result");

                    JSONObject job_box = (JSONObject) jarr.get(0);
                    Aname.setText(  job_box.getString("Name") );
                    Auname.setText(  job_box.getString("Username") );
                    vehicle.setText( job_box.getString("vehicle"));
                    Amobile.setText( job_box.getString("Mobile"));
                    Apass.setText( job_box.getString("Password"));
                    dLicense.setText(job_box.getString("DLicense"));


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
        AppControllerActivity app = new AppControllerActivity(UpdateDriverProfileActivity.this);
        app.addToRequestQueue(jobreq);
    }

    public void updateAdmin(View view) {

        String Name= Aname.getText().toString();
        String Username =Auname.getText().toString();
        final String vehiclePlate= vehicle.getText().toString();
        String Mobile =Amobile.getText().toString();
        String dLicenseNum=dLicense.getText().toString();
        String Password= Apass.getText().toString();
        String Cpassword= Acpass.getText().toString();

        if(Name.equals(""))
        {
            Toast.makeText(UpdateDriverProfileActivity.this,"Please enter Name ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Username.equals(""))
        {
            Toast.makeText(UpdateDriverProfileActivity.this,"Please enter Username ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(vehiclePlate.equals(""))
        {
            Toast.makeText(UpdateDriverProfileActivity.this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Mobile.equals(""))
        {
            Toast.makeText(UpdateDriverProfileActivity.this,"Please enter mobile",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Password.equals(""))
        {
            Toast.makeText(UpdateDriverProfileActivity.this,"Please enter Password",Toast.LENGTH_SHORT).show();
            return;
        }


        if(!Cpassword.equals(Password))
        {
            Toast.makeText(UpdateDriverProfileActivity.this,"password do not match",Toast.LENGTH_SHORT).show();
            return;
        }
        //end
        final JSONObject jobj = new JSONObject();


        SharedPreferences sp = getSharedPreferences("driver_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");

        try {

            jobj.put("A_name" ,Name);
            jobj.put("A_mob",Mobile);
            jobj.put("A_pass",Password);
            jobj.put("A_vehicle",vehiclePlate);
            jobj.put("A_dLicense",dLicenseNum);
            jobj.put("a_id",admin_id);
            jobj.put("did",sp.getString("d_id",""));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("dvgrefgerer", "updateAdmin: "+jobj);
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/update_driverProf.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(UpdateDriverProfileActivity.this ,"Error " , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("2"))
                    {
                        Toast.makeText(UpdateDriverProfileActivity.this ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(UpdateDriverProfileActivity.this ," Updated Successfully" , Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor sp =getSharedPreferences("driver_info",MODE_PRIVATE).edit();
                        sp.putString("username",Auname.getText().toString());
                        sp.putString("driver_name",Aname.getText().toString());
                        sp.putString("driver_mobile",Amobile.getText().toString());
                        sp.apply();
                        Intent i = new Intent(UpdateDriverProfileActivity.this, DriverMenuActivity.class);
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
        AppControllerActivity app=new AppControllerActivity(UpdateDriverProfileActivity.this);
        app.addToRequestQueue(jobjreq);
    }
}
