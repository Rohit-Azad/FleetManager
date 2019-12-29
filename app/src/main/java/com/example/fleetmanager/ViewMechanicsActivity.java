package com.example.fleetmanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewMechanicsActivity extends AppCompatActivity {
    RecyclerView r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mechanics);

        r = (RecyclerView) findViewById(R.id.recyclerr_id);
        show();
    }

    public void show(){
        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String u = sp.getString("admin_id2","");

        JSONObject job = new JSONObject();

        try {
            job.put("admin_id_here", u);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // making json array request to get json array data
        JsonObjectRequest jsonreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/view_mechanic.php", job , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jarr = null;
                try {
                    jarr = response.getJSONArray("result");
                    Log.d("fvdfgv", "onResponse: "+response.getString("error"));
                    // making object of adapter class and passing json array and Activity to adapter constructer
                    ViewMechanicDetailsAdapter ad = new ViewMechanicDetailsAdapter(jarr , ViewMechanicsActivity.this);


                    // setting properties for recycler view like how it scroll vertically , horizontally
                    r.setLayoutManager(new LinearLayoutManager(ViewMechanicsActivity.this , LinearLayoutManager.VERTICAL,false));

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

        AppControllerActivity app = new AppControllerActivity(ViewMechanicsActivity.this);

        app.addToRequestQueue(jsonreq);
    }
}
