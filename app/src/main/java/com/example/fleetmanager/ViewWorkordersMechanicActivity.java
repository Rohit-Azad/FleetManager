package com.example.fleetmanager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class ViewWorkordersMechanicActivity extends AppCompatActivity {

    TableLayout tlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workorders_mechanic);
        tlayout=findViewById(R.id.tableLayout);
        show();
    }

    public void show(){



        SharedPreferences sp = getSharedPreferences("mechanic_info",MODE_PRIVATE);
        String u = sp.getString("admin_id2","1");


        JSONObject job = new JSONObject();

        try {
            job.put("admin_id", sp.getString("admin_id2",""));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // making json array request to get json array data
        JsonObjectRequest jsonreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/view_workorders_mechanic.php", job , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // JSONArray jarr = null;
                try {
                    JSONArray jarr = response.getJSONArray("result");
                    String[] columns ={"Wid", "VehicleName", "LicensePlate", "VehicleType", "Issue", "Date","Status"};

                    JSONObject jobj;
                    TableRow tr;
                    TextView txt;

                    String s;

                    tlayout.removeAllViews();
                    // creating the header
                    tr = new TableRow(ViewWorkordersMechanicActivity.this);
                    TableLayout.LayoutParams lp =
                            new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                    TableLayout.LayoutParams.WRAP_CONTENT);

                    lp.setMargins(15,5,15,5); // left, top, right, bottom

                    tr.setLayoutParams(lp);
                    for (int y = 0; y < columns.length; y++) {
                        txt = new TextView(ViewWorkordersMechanicActivity.this);

                        txt.setText(columns[y]);
                        txt.setTextSize(20);
                        txt.setPadding(15, 4, 15, 4);
                        // txt.setLayoutParams(lp1);
                        txt.setBackgroundResource(R.drawable.rectangle);
                        txt.setTextColor(Color.BLACK);
                        // txt.setLayoutParams(lp1);

                        tr.addView(txt);
                    }
                    tlayout.addView(tr);
                    for (int x = 0; x < jarr.length(); x++) {
                        tr = new TableRow(ViewWorkordersMechanicActivity.this);
                        tr.setLayoutParams(lp);
                        tr.setBackgroundResource(R.drawable.backedit_black);
                        jobj = jarr.getJSONObject(x);

                        // getting the columns
                        for (int y = 0; y < columns.length; y++) {
                            txt = new TextView(ViewWorkordersMechanicActivity.this);

                            txt.setTextSize(18);
                            //  txt.setLayoutParams(lp1);
                            //   txt.setBackgroundResource(R.drawable.backedit_black);
                            txt.setTextColor(Color.WHITE);

                            s = jobj.getString(columns[y]);

                            txt.setText(s);
                            txt.setPadding(15, 4, 15, 4);
                            tr.addView(txt);
                        }
                        tlayout.addView(tr);


                    }
                }catch (JSONException e) {
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

        AppControllerActivity app = new AppControllerActivity(ViewWorkordersMechanicActivity.this);

        app.addToRequestQueue(jsonreq);
    }
}
