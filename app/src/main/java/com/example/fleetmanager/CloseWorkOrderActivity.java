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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CloseWorkOrderActivity extends AppCompatActivity {
    EditText edtwrk;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_work_order);
        edtwrk=(EditText)findViewById(R.id.wwid);
        sp=(Spinner)findViewById(R.id.Sclose);

        String[] statusArray={"Select Status","Closed","in Progress"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.custom_spinner_layout,statusArray);

        sp.setAdapter(adapter);

    }

    public void cancel(View view) {
        Intent i=new Intent(CloseWorkOrderActivity.this,MechanicMenuActivity.class);
        startActivity(i);
    }

    public void Submit(View view) {



        String WID= edtwrk.getText().toString();
        String stat =sp.getSelectedItem().toString();


        if(WID.equals(""))
        {
            Toast.makeText(CloseWorkOrderActivity.this,"Please enter Work Order ID ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(stat.equals("Select"))
        {
            Toast.makeText(CloseWorkOrderActivity.this,"Please select Status ",Toast.LENGTH_SHORT).show();
            return;
        }


        //end
        final JSONObject jobj = new JSONObject();


        SharedPreferences sp = getSharedPreferences("mechanic_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");
        String mn_id =sp.getString("mechnic_id","");
        System.out.print("Helooo"+admin_id);
        System.out.print("Helooo"+mn_id);
        try {

            jobj.put("M_wid" ,WID);

            jobj.put("m_status" ,stat);

            jobj.put("m_mid",mn_id);


            jobj.put("a_id",admin_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/update_WorderStatus.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(CloseWorkOrderActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("1")) {
                        Toast.makeText(CloseWorkOrderActivity.this ,"Updated Successfully" , Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(CloseWorkOrderActivity.this, MechanicMenuActivity.class);

                        startActivity(i);
                        finish();
                    }
                    else if(response.getString("key").equals("2")) {
                        Toast.makeText(CloseWorkOrderActivity.this ,"No Change made" , Toast.LENGTH_SHORT).show();
                    }


                    else {
                        Toast.makeText(CloseWorkOrderActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

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
        AppControllerActivity app=new AppControllerActivity(CloseWorkOrderActivity.this);
        app.addToRequestQueue(jobjreq);
    }
}
