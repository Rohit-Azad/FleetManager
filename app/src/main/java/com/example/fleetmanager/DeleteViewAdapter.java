package com.example.fleetmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class DeleteViewAdapter extends RecyclerView.Adapter<DeleteViewHolder> {

    JSONArray jsarr;
    JSONObject job;
    Activity a;

    public DeleteViewAdapter(JSONArray jsarr , Activity a)
    {
        this.a = a;
        this.jsarr = jsarr;


    }
    @NonNull
    @Override
    public DeleteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        DeleteViewHolder vd = new DeleteViewHolder(LayoutInflater.from(a).inflate(R.layout.delete_generic,viewGroup,false));
        return vd;
    }

    @Override
    public void onBindViewHolder(@NonNull final  DeleteViewHolder vh, int position) {

        SharedPreferences sp = a.getSharedPreferences("tableInfo",MODE_PRIVATE);
        final String u = sp.getString("table","");


        String[] vehicleArray={"LicensePlate","Vid","VehicleType"};
        String[] mechArray={"Name","Mid","Mobile"};
        String[] driverArray={"Name","Did","Mobile"};
        String[] workorderArray={"LicensePlate","Wid","Status"};
        String[] OrderedPartsArray={"Company","Invoice","Total"};

        try {
            // iterating for each json object in json array
            job = (JSONObject) jsarr.get(position);

            // binding values from json object to cell layout via view holder
            if(u.equals("vehicle_table")) {
                vh.name.setText(job.getString(vehicleArray[0]));
                vh.tv1.setText("Vehicle ID");
                vh.tv1Value.setText(job.getString(vehicleArray[1]));
                vh.tv2.setText("Type");
                vh.tv2Value.setText(job.getString(vehicleArray[2]));

                vh.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final JSONObject jobj = new JSONObject();


                        SharedPreferences sp = a.getSharedPreferences("admin_info",MODE_PRIVATE);

                        try {
                            jobj.put("table" ,u);
                            jobj.put("id" ,"Vid");
                            jobj.put("id_value" ,vh.tv1Value.getText().toString());
                            jobj.put("aid_value",sp.getString("admin_id2",""));
                            jobj.put("aid","Aid");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("dfgdfbgv", "update_work_order: "+jobj);
                        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/delete_confirm.php",jobj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getString("key").equals("0"))
                                    {
                                    //    Log.d("fdgdfgv", "onResponse: "+response.getString("test"));
                                        Toast.makeText(a ,"Error " , Toast.LENGTH_SHORT).show();

                                    }
                                    else if(response.getString("key").equals("2"))
                                    {
                                   //     Log.d("fdgdfgv", "onResponse: "+response.getString("test"));
                                        Toast.makeText(a ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(a," Updated Successfully" , Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(a, DeleteGenericActivity.class);
                                        SharedPreferences sp = a.getSharedPreferences("admin_info",MODE_PRIVATE);
                                        String u = sp.getString("admin_id2","");
                                        Intent intent = new Intent(a, DeleteGenericActivity.class);
                                        intent.putExtra("id_send",u);
                                        intent.putExtra("table","vehicle_table");
                                        a.startActivity(intent);
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
                        AppControllerActivity app=new AppControllerActivity(a);
                        app.addToRequestQueue(jobjreq);



                    }
                });
            }
            else if(u.equals("driver")) {
                vh.name.setText(job.getString(driverArray[0]));
                vh.tv1.setText("Driver ID");
                vh.tv1Value.setText(job.getString(driverArray[1]));
                vh.tv2.setText(job.getString("Mobile"));
                vh.tv2Value.setText(job.getString(driverArray[2]));

                vh.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final JSONObject jobj = new JSONObject();


                        SharedPreferences sp = a.getSharedPreferences("admin_info",MODE_PRIVATE);

                        try {
                            jobj.put("table" ,u);
                            jobj.put("id" ,"Did");
                            jobj.put("id_value" ,vh.tv1Value.getText().toString());
                            jobj.put("aid_value",sp.getString("admin_id2",""));
                            jobj.put("aid","Aid");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("dfgdfbgv", "update_work_order: "+jobj);
                        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/delete_confirm.php",jobj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getString("key").equals("0"))
                                    {
                                        Log.d("fdgdfgv", "onResponse: "+response.getString("test"));
                                        Toast.makeText(a ,"Error " , Toast.LENGTH_SHORT).show();

                                    }
                                    else if(response.getString("key").equals("2"))
                                    {
                                        Log.d("fdgdfgv", "onResponse: "+response.getString("test"));
                                        Toast.makeText(a ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(a," Updated Successfully" , Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(a, DeleteGenericActivity.class);
                                        SharedPreferences sp = a.getSharedPreferences("admin_info",MODE_PRIVATE);
                                        String u = sp.getString("admin_id2","");
                                        Intent intent = new Intent(a, DeleteGenericActivity.class);
                                        intent.putExtra("id_send",u);
                                        intent.putExtra("table","driver");
                                        a.startActivity(intent);
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
                        AppControllerActivity app=new AppControllerActivity(a);
                        app.addToRequestQueue(jobjreq);



                    }
                });
            }
            else if(u.equals("orderparts")) {
                vh.name.setText(job.getString(OrderedPartsArray[0]));
                vh.tv1.setText("Invoice");
                vh.tv1Value.setText(job.getString(OrderedPartsArray[1]));
                vh.tv2.setText(job.getString("Total"));
                vh.tv2Value.setText(job.getString(OrderedPartsArray[2]));

                final String opID=job.getString("Opid");

                vh.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final JSONObject jobj = new JSONObject();


                        SharedPreferences sp = a.getSharedPreferences("admin_info",MODE_PRIVATE);

                        try {
                            jobj.put("table" ,u);
                            jobj.put("id" ,"Opid");
                            jobj.put("id_value" ,opID);
                            jobj.put("aid_value",sp.getString("admin_id2",""));
                            jobj.put("aid","Aid");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("dfgdfbgv", "update_work_order: "+jobj);
                        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/delete_confirm.php",jobj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getString("key").equals("0"))
                                    {
                                        Log.d("fdgdfgv", "onResponse: "+response.getString("test"));
                                        Toast.makeText(a ,"Error " , Toast.LENGTH_SHORT).show();

                                    }
                                    else if(response.getString("key").equals("2"))
                                    {
                                        Log.d("fdgdfgv", "onResponse: "+response.getString("test"));
                                        Toast.makeText(a ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(a," Updated Successfully" , Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(a, DeleteGenericActivity.class);
                                        SharedPreferences sp = a.getSharedPreferences("admin_info",MODE_PRIVATE);
                                        String u = sp.getString("admin_id2","");
                                        Intent intent = new Intent(a, DeleteGenericActivity.class);
                                        intent.putExtra("id_send",u);
                                        intent.putExtra("table","orderparts");
                                        a.startActivity(intent);
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
                        AppControllerActivity app=new AppControllerActivity(a);
                        app.addToRequestQueue(jobjreq);



                    }
                });
            }
            else if(u.equals("workorder")) {
                vh.name.setText(job.getString(workorderArray[0]));
                vh.tv1.setText("W_ID");
                vh.tv1Value.setText(job.getString(workorderArray[1]));
                vh.tv2.setText(job.getString("Status"));
                vh.tv2Value.setText(job.getString(workorderArray[2]));

                vh.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final JSONObject jobj = new JSONObject();


                        SharedPreferences sp = a.getSharedPreferences("admin_info",MODE_PRIVATE);

                        try {
                            jobj.put("table" ,u);
                            jobj.put("id" ,"Wid");
                            jobj.put("id_value" ,vh.tv1Value.getText().toString());
                            jobj.put("aid_value",sp.getString("admin_id2",""));
                            jobj.put("aid","Aid");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("dfgdfbgv", "update_work_order: "+jobj);
                        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/delete_confirm.php",jobj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getString("key").equals("0"))
                                    {
                                        Log.d("fdgdfgv", "onResponse: "+response.getString("test"));
                                        Toast.makeText(a ,"Error " , Toast.LENGTH_SHORT).show();

                                    }
                                    else if(response.getString("key").equals("2"))
                                    {
                                        Log.d("fdgdfgv", "onResponse: "+response.getString("test"));
                                        Toast.makeText(a ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(a," Updated Successfully" , Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(a, DeleteGenericActivity.class);
                                        SharedPreferences sp = a.getSharedPreferences("admin_info",MODE_PRIVATE);
                                        String u = sp.getString("admin_id2","");
                                        Intent intent = new Intent(a, DeleteGenericActivity.class);
                                        intent.putExtra("id_send",u);
                                        intent.putExtra("table","workorder");
                                        a.startActivity(intent);
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
                        AppControllerActivity app=new AppControllerActivity(a);
                        app.addToRequestQueue(jobjreq);



                    }
                });
            }
            else if(u.equals("mechanic")) {
                vh.name.setText(job.getString(mechArray[0]));
                vh.tv1.setText("Mechanic ID");
                vh.tv1Value.setText(job.getString(mechArray[1]));
                vh.tv2.setText("Mobile");
                vh.tv2Value.setText(job.getString(mechArray[2]));

                vh.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final JSONObject jobj = new JSONObject();


                        SharedPreferences sp = a.getSharedPreferences("admin_info",MODE_PRIVATE);

                        try {
                            jobj.put("table" ,u);
                            jobj.put("id" ,"Mid");
                            jobj.put("id_value" ,vh.tv1Value.getText().toString());
                            jobj.put("aid_value",sp.getString("admin_id2",""));
                            jobj.put("aid","Aid");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("dfgdfbgv", "update_work_order: "+jobj);
                        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/delete_confirm.php",jobj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getString("key").equals("0"))
                                    {
                                        Log.d("fdgdfgv", "onResponse: "+response.getString("test"));
                                        Toast.makeText(a ,"Error " , Toast.LENGTH_SHORT).show();

                                    }
                                    else if(response.getString("key").equals("2"))
                                    {
                                        Log.d("fdgdfgv", "onResponse: "+response.getString("test"));
                                        Toast.makeText(a ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(a," Updated Successfully" , Toast.LENGTH_SHORT).show();
//                                        SharedPreferences sp = a.getSharedPreferences("admin_info",MODE_PRIVATE);
                                        Intent i = new Intent(a, DeleteGenericActivity.class);
                                        SharedPreferences sp = a.getSharedPreferences("admin_info",MODE_PRIVATE);
                                        String u = sp.getString("admin_id2","");
                                        Intent intent = new Intent(a, DeleteGenericActivity.class);
                                        intent.putExtra("id_send",u);
                                        intent.putExtra("table","mechanic");
                                        a.startActivity(intent);

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
                        AppControllerActivity app=new AppControllerActivity(a);
                        app.addToRequestQueue(jobjreq);



                    }
                });
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public int getItemCount() {
        return jsarr.length();
    }
}
