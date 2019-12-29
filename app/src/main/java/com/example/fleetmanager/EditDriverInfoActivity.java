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

import org.json.JSONException;
import org.json.JSONObject;

public class EditDriverInfoActivity extends AppCompatActivity {
    EditText etName,etDLicense,etMobile,etTruck,etUsername,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_driver_info);

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String u = sp.getString("editName","");

        etName=findViewById(R.id.etName);
        etDLicense=findViewById(R.id.etDLicense);
        etTruck=findViewById(R.id.etTruck);
        etMobile=findViewById(R.id.etMobile);
        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);

        etName.setText(sp.getString("editName",""));
        etDLicense.setText(sp.getString("editLicense",""));
        etTruck.setText(sp.getString("editVehicle",""));
        etMobile.setText(sp.getString("editMobile",""));
        etUsername.setText(sp.getString("editUsername",""));
        etPassword.setText(sp.getString("editPassword",""));

    }

    public void EditDriverInfo(View view) {

        String name =etName.getText().toString();
        String dLicenseNumber= etDLicense.getText().toString();
        String vehicle= etTruck.getText().toString();
        String mobile= etMobile.getText().toString();

        if(name.equals(""))
        {
            Toast.makeText(EditDriverInfoActivity.this,"Please enter Issue ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(dLicenseNumber.equals(""))
        {
            Toast.makeText(EditDriverInfoActivity.this,"Please enter Date ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(vehicle.equals(""))
        {
            Toast.makeText(EditDriverInfoActivity.this,"Please enter Status",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mobile.equals(""))
        {
            Toast.makeText(EditDriverInfoActivity.this,"Please enter Status",Toast.LENGTH_SHORT).show();
            return;
        }

        final JSONObject jobj = new JSONObject();


        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");

        try {
            jobj.put("editDriver_name" ,name);
            jobj.put("editDriver_dLicenseNumber" ,dLicenseNumber);
            jobj.put("editDriver_vehicle" ,vehicle);
            jobj.put("editDriver_mobile" ,mobile);
            jobj.put("dr_id" ,sp.getString("dr_id",""));
            jobj.put("editPassword",etPassword.getText().toString());
            jobj.put("a_id",admin_id);
            jobj.put("editUsername",etUsername.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("dfgdfbgv", "update_work_order: "+jobj);
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/EditDriverInfo_admin.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("key").equals("0"))
                    {
                        Log.d("fdgdfgv", "onResponse: "+response.getString("error"));
                        Toast.makeText(EditDriverInfoActivity.this ,"Error " , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("2"))
                    {
                        Toast.makeText(EditDriverInfoActivity.this ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(EditDriverInfoActivity.this ," Updated Successfully" , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditDriverInfoActivity.this, AdminViewEditInventoryActivity.class);
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
        AppControllerActivity app=new AppControllerActivity(EditDriverInfoActivity.this);
        app.addToRequestQueue(jobjreq);
    }
}
