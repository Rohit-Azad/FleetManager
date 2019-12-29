package com.example.fleetmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewNotificationsActivity extends AppCompatActivity {
    RecyclerView r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notifications);

        r=findViewById(R.id.rvNotify);

        show();
    }

    public void show(){
        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");

        JSONObject job = new JSONObject();

        try {
            job.put("admin_id", admin_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // making json array request to get json array data
        JsonObjectRequest jsonreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/insuranceInsurance.php", job , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jarr = null;
                try {
                    jarr = response.getJSONArray("result");
                    Log.d("fgerfgewef", "onResponse: "+jarr);
                    // making object of adapter class and passing json array and Activity to adapter constructer
                    NotificationsAdapter ad = new NotificationsAdapter(jarr , ViewNotificationsActivity.this);


                    // setting properties for recycler view like how it scroll vertically , horizontally
                    r.setLayoutManager(new LinearLayoutManager(ViewNotificationsActivity.this , LinearLayoutManager.VERTICAL,false));

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

        AppControllerActivity app = new AppControllerActivity(ViewNotificationsActivity.this);

        app.addToRequestQueue(jsonreq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1: {
                Intent i=new Intent(ViewNotificationsActivity.this, AdminAddInventoryActivity.class);
                startActivity(i);
                break;
            }
            case R.id.menu2:{
                SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
                String admin_id =sp.getString("admin_id2","");
                Intent i=new Intent(ViewNotificationsActivity.this, ViewVehicleActivity.class);
                i.putExtra("id_send",admin_id);
                startActivity(i);
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
