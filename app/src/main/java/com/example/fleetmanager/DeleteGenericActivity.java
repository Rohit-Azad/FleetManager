package com.example.fleetmanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeleteGenericActivity extends AppCompatActivity {
    RecyclerView r;
    String tableToUSe;

    TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_generic);

        tableToUSe=getIntent().getStringExtra("table");

        heading=findViewById(R.id.tvHeading);
        heading.setText(getIntent().getStringExtra("heading"));

        SharedPreferences.Editor sp =getSharedPreferences("tableInfo",MODE_PRIVATE).edit();
        sp.putString("table",tableToUSe);
        sp.apply();

        r = (RecyclerView) findViewById(R.id.recyclerr_id);
        show();
    }

    public void show(){


        JSONObject job = new JSONObject();

        try {
            job.put("admin_id_here", getIntent().getStringExtra("id_send"));
            job.put("table",tableToUSe);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // making json array request to get json array data
        JsonObjectRequest jsonreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/viewtodelete.php", job , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jarr = null;
                try {
                    jarr = response.getJSONArray("result");
                    // making object of adapter class and passing json array and Activity to adapter constructer
                    DeleteViewAdapter ad = new DeleteViewAdapter(jarr , DeleteGenericActivity.this);


                    // setting properties for recycler view like how it scroll vertically , horizontally
                    r.setLayoutManager(new LinearLayoutManager(DeleteGenericActivity.this , LinearLayoutManager.VERTICAL,false));

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

        AppControllerActivity app = new AppControllerActivity(DeleteGenericActivity.this);

        app.addToRequestQueue(jsonreq);
    }
}
