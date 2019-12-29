package com.example.fleetmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * code by gurman and Rohit*/
public class AddDriverActivity extends AppCompatActivity {
    public static final String JSON_ARRAY = "result";
    public static final String vEHIVLEaRRAY = "LicensePlate";
    private JSONArray result;
    Spinner spinner;

    private ArrayList<String> arrayList;
    EditText dname,dusername,dpas,dcp,dDl,dmob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);
        spinner = (Spinner) findViewById(R.id.spinner);
        dname=(EditText) findViewById(R.id.dname1);
        dusername=(EditText) findViewById(R.id.edtuname);
        dpas=(EditText) findViewById(R.id.edtpass);


        dDl =(EditText) findViewById(R.id.edtDL);
        dmob =(EditText) findViewById(R.id.mobile);

        arrayList = new ArrayList<String>();
        getdata();
    }
    private void getdata() {

        StringRequest stringRequest = new StringRequest("http://"+Ipaddress.ip+"/fleet/getdata.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        JSONObject j = null;


                        try {

                          j = new JSONObject(response);

                            result = j.getJSONArray(JSON_ARRAY);
                            empdetails(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void empdetails(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                arrayList.add(json.getString(vEHIVLEaRRAY));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
         arrayList.add(0,"Select Vehicle");
        spinner.setAdapter(new ArrayAdapter<String>(AddDriverActivity.this, R.layout.custom_spinner_layout, arrayList));
    }

    public void addDriver(View view) {


        String drName =dname.getText().toString();
        String drUsername =dusername.getText().toString();
        String License= dDl.getText().toString();
        String mobile= dmob.getText().toString();
        String dpassword= dpas.getText().toString();
        String LicensePlate= spinner.getSelectedItem().toString();

        if(drName.equals(""))
        {
            Toast.makeText(AddDriverActivity.this,"Please enter Name ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(drUsername.equals(""))
        {
            Toast.makeText(AddDriverActivity.this,"Please enter Username ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mobile.equals(""))
        {
            Toast.makeText(AddDriverActivity.this,"Please enter Mobile",Toast.LENGTH_SHORT).show();
            return;
        }
        if(License.equals(""))
        {
            Toast.makeText(AddDriverActivity.this,"Please ENTER DL ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(LicensePlate.equals("Select Vehicle"))
        {
            Toast.makeText(AddDriverActivity.this,"Please Select vehicle",Toast.LENGTH_SHORT).show();
            return;
        }

       if(dpassword.equals(""))
        {
            Toast.makeText(AddDriverActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        //end
        JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");

        try {

            jobj.put("d_name" , drName);

            jobj.put("d_license" , License);
            jobj.put("d_username",drUsername);
            jobj.put("d_password",dpassword);
            jobj.put("d_mobile",mobile);
            jobj.put("d_vehicle",LicensePlate);
            jobj.put("a_id",admin_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/driver_add.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(AddDriverActivity.this ,"Driver already exist" , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("1")) {
                        Toast.makeText(AddDriverActivity.this ,"Vehicle Added Successfully" , Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(AddDriverActivity.this, AdminAddInventoryActivity.class);

                        startActivity(i);
                        finish();
                    }

                    else {
                        Toast.makeText(AddDriverActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

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
        AppControllerActivity app=new AppControllerActivity(AddDriverActivity.this);
        app.addToRequestQueue(jobjreq);
    }
}