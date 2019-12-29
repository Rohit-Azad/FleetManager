package com.example.fleetmanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewClosedWorkOrderAdminActivity extends AppCompatActivity {
    RecyclerView r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_closed_work_order_admin);


        r = (RecyclerView) findViewById(R.id.recyclerr_id);
        show();
    }

    public void show(){


        JSONObject job = new JSONObject();

        try {

            SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
            String admin_id =sp.getString("admin_id2","");


            job.put("admin_id_here", admin_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // making json array request to get json array data
        JsonObjectRequest jsonreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/viewClosedOrder_admin.php", job , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jarr = null;
                try {
                    jarr = response.getJSONArray("result");
                    // making object of adapter class and passing json array and Activity to adapter constructer
                    ViewOWrkOrdrMecAdapter ad = new ViewOWrkOrdrMecAdapter(jarr , ViewClosedWorkOrderAdminActivity.this);


                    // setting properties for recycler view like how it scroll vertically , horizontally
                    r.setLayoutManager(new LinearLayoutManager(ViewClosedWorkOrderAdminActivity.this , LinearLayoutManager.VERTICAL,false));

                    // setting adapter ad to recycler view r
                    r.setAdapter(ad);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        jsonreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 ,2));

        AppControllerActivity app = new AppControllerActivity(ViewClosedWorkOrderAdminActivity.this);

        app.addToRequestQueue(jsonreq);
    }

}
