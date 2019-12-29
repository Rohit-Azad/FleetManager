package com.example.fleetmanager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

public class DrvrWorkViewTwo extends AppCompatActivity {
    RecyclerView r;

    String tdatee,Fdate;

    TableLayout tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drvr_work_view_two);

        tl=findViewById(R.id.tableLayout);
        r = (RecyclerView) findViewById(R.id.recyclerr_id);
        show();
    }

    public void show(){


        final JSONObject job = new JSONObject();

        try {

            SharedPreferences sp = getSharedPreferences("driver_info",MODE_PRIVATE);
            String admin_id =sp.getString("admin_id2","");

            String drvr_id =sp.getString("d_id","");

            job.put("admin_id_here", admin_id);
            job.put("drid", drvr_id);
            job.put("todate",getIntent().getStringExtra("Wtodate"));
            job.put("fromdate",getIntent().getStringExtra("Wfromdate"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("refdfvgd", "onResponse: "+job);
        // making json array request to get json array data
        JsonObjectRequest jsonreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/viewDriver_workOrder.php", job , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jarr = null;
                try {
                    jarr = response.getJSONArray("result");
                    if(getIntent().getStringExtra("choice").equals("Tabbed Form")) {
                        Drvr_ViewWrkO_Adapter ad = new Drvr_ViewWrkO_Adapter(jarr, DrvrWorkViewTwo.this);
                        r.setLayoutManager(new LinearLayoutManager(DrvrWorkViewTwo.this, LinearLayoutManager.VERTICAL, false));
                        r.setAdapter(ad);
                    }
                    else{
                        try {
                            r.setVisibility(View.INVISIBLE);


                            jarr = response.getJSONArray("result");
                            String [] columns={"Wid","VehicleType","VCompany","VehicleName","LicensePlate","Date","Issue","Status"};
                            JSONObject jobj;
                            TableRow tr;
                            TextView txt;

                            String s;

                            tl.removeAllViews();
                            // creating the header
                            tr = new TableRow(DrvrWorkViewTwo.this);
                            TableLayout.LayoutParams lp =
                                    new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                            TableLayout.LayoutParams.WRAP_CONTENT);

                            lp.setMargins(15,5,15,5); // left, top, right, bottom

                            tr.setLayoutParams(lp);
                            for (int y = 0; y < columns.length; y++) {
                                txt = new TextView(DrvrWorkViewTwo.this);

                                txt.setText(columns[y]);
                                txt.setTextSize(20);
                                txt.setPadding(15, 4, 15, 4);
                                // txt.setLayoutParams(lp1);
                                txt.setBackgroundResource(R.drawable.rectangle);
                                txt.setTextColor(Color.BLACK);
                                // txt.setLayoutParams(lp1);

                                tr.addView(txt);
                            }
                            tl.addView(tr);
                            for (int x = 0; x < jarr.length(); x++) {
                                tr = new TableRow(DrvrWorkViewTwo.this);
                                tr.setLayoutParams(lp);
                                tr.setBackgroundResource(R.drawable.backedit_black);
                                jobj = jarr.getJSONObject(x);

                                // getting the columns
                                for (int y = 0; y < columns.length; y++) {
                                    txt = new TextView(DrvrWorkViewTwo.this);

                                    txt.setTextSize(18);
                                    //  txt.setLayoutParams(lp1);
                                    //   txt.setBackgroundResource(R.drawable.backedit_black);
                                    txt.setTextColor(Color.WHITE);

                                    s = jobj.getString(columns[y]);

                                    txt.setText(s);
                                    txt.setPadding(15, 4, 15, 4);
                                    tr.addView(txt);
                                }
                                tl.addView(tr);


                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("dfgevg", "onErrorResponse: "+error);
            }
        });


        jsonreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 ,2));

        AppControllerActivity app = new AppControllerActivity(DrvrWorkViewTwo.this);

        app.addToRequestQueue(jsonreq);
    }




}
