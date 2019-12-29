package com.example.fleetmanager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewOpenWorkOrderAdminActivity extends AppCompatActivity {
    RecyclerView r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_open_work_order_admin);

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
        JsonObjectRequest jsonreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/viewOpenOrder_admin.php", job , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jarr = null;
                try {
                    jarr = response.getJSONArray("result");

                    ViewOWrkOrdrMecAdapter ad = new ViewOWrkOrdrMecAdapter(jarr , ViewOpenWorkOrderAdminActivity.this);
                    r.setLayoutManager(new LinearLayoutManager(ViewOpenWorkOrderAdminActivity.this , LinearLayoutManager.VERTICAL,false));
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

        AppControllerActivity app = new AppControllerActivity(ViewOpenWorkOrderAdminActivity.this);

        app.addToRequestQueue(jsonreq);
    }
}
