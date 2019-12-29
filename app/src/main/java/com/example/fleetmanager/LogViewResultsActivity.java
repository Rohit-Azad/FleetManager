package com.example.fleetmanager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
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

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogViewResultsActivity extends AppCompatActivity {
        TableLayout tlayout;
        String tableName,id;

        String[] columns;
        TextView tv,tvSub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view_results);

        tv=findViewById(R.id.tvHeading);
        tvSub=findViewById(R.id.tvSubHeading);
        tlayout=findViewById(R.id.tableResult);
        show();
    }

    public void show(){



        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String u = sp.getString("admin_id2","1");

        if(getIntent().getStringExtra("logTableDecider").equals("Driver"))
        {
            tableName="driver_log";
            id="a_id";
            columns =new String[]{"dLog_id", "FromLocation", "ToLocation", "Material", "Distance", "FuelCard", "Odometer", "Date"};
            tv.setText("Driver Logs");
            tvSub.setText("From: "+ getIntent().getStringExtra("FromDate")+" To: "+ getIntent().getStringExtra("toDate"));

        }

        else if(getIntent().getStringExtra("logTableDecider").equals("Mechanic"))
        {
            tableName="mechanic_log";
            id="Aid";
            columns =new String[] {"MLid", "VehiclePlate", "NumofHours", "Date"};
            tv.setText("Mechanic Logs");
            tvSub.setText("From: "+ getIntent().getStringExtra("FromDate")+" To: "+ getIntent().getStringExtra("toDate"));


        }

        JSONObject job = new JSONObject();

        try {
            job.put("admin_id_here", u);
            job.put("FromDate", getIntent().getStringExtra("FromDate"));
            job.put("toDate", getIntent().getStringExtra("toDate"));
            job.put("tableName", tableName);
            job.put("id", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // making json array request to get json array data
        JsonObjectRequest jsonreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/view_logs_generic.php", job , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

               // JSONArray jarr = null;
                try {


                    JSONArray jarr = response.getJSONArray("result");

                    JSONObject jobj;
                    TableRow tr;
                    TextView txt;

                    String s;

                    tlayout.removeAllViews();
                    // creating the header
                    tr = new TableRow(LogViewResultsActivity.this);
                TableLayout.LayoutParams lp =
                        new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT);

                lp.setMargins(15,5,15,5); // left, top, right, bottom

                tr.setLayoutParams(lp);
                    for (int y = 0; y < columns.length; y++) {
                        txt = new TextView(LogViewResultsActivity.this);

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
                        tr = new TableRow(LogViewResultsActivity.this);
                        tr.setLayoutParams(lp);
                        tr.setBackgroundResource(R.drawable.backedit_black);
                        jobj = jarr.getJSONObject(x);

                        // getting the columns
                        for (int y = 0; y < columns.length; y++) {
                            txt = new TextView(LogViewResultsActivity.this);

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

        AppControllerActivity app = new AppControllerActivity(LogViewResultsActivity.this);

        app.addToRequestQueue(jsonreq);
    }
}
