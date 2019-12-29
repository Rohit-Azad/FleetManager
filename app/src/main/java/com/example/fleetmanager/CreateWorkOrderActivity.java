package com.example.fleetmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateWorkOrderActivity extends AppCompatActivity {

    EditText edt1,Edt2,edt3,edt4,edt5,edt6,comp;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_work_order);

        edt1=(EditText)findViewById(R.id.lplate);
        Edt2=(EditText)findViewById(R.id.Vin);
        edt3=(EditText)findViewById(R.id.VType);
        edt4=(EditText)findViewById(R.id.VName);
        edt5= (EditText)findViewById(R.id.Date);
        edt6= (EditText)findViewById(R.id.issue);

        comp= (EditText)findViewById(R.id.Vcomp);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edt5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateWorkOrderActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    //getting admin ID

        SharedPreferences sp = getSharedPreferences("driver_info",MODE_PRIVATE);
        String a_id =sp.getString("admin_id2","");

        String d_id =sp.getString("d_id","");
        System.out.println("helo  "+d_id);

        edt1.setText(sp.getString("lPlate",""));


    }

    public void find(View view) {

        String lplateNo = edt1.getText().toString();




        if(lplateNo.equals(""))
        {
            Toast.makeText(CreateWorkOrderActivity.this , "Please enter License plate" , Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sp = getSharedPreferences("driver_info",MODE_PRIVATE);
        String dr_id =sp.getString("admin_id2","");

        JSONObject job = new JSONObject();

        try {
            job.put("licenPlate" ,lplateNo);
            job.put("drvr_id" ,dr_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/fill_vehicledetail_forWorkOrder.php", job,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getString("key").equals("done"))
                            {
//                                SharedPreferences.Editor sp =getSharedPreferences("admin_info",MODE_PRIVATE).edit();
//                                sp.putString("admin_id",response.getString("admin_id"));
//                                sp.putString("username",adminUserName);
//                                sp.putString("admin_id2",response.getString("admin_identity"));
//////                                sp.putString("name_id",response.getString("Name"));


                                // setText

                                Edt2.setText(response.getString("VinNumber"));

                                edt3.setText(response.getString("VehicleType"));

                                edt4.setText(response.getString("VehicleName"));

                                comp.setText(response.getString("VehicleCompany"));
                            }
                            else {
                                Toast.makeText(CreateWorkOrderActivity.this , "error" , Toast.LENGTH_SHORT).show();
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

        jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 ,2));

        AppControllerActivity app = new AppControllerActivity(CreateWorkOrderActivity.this);
        app.addToRequestQueue(jobjreq);


    }

    public void addwork(View view) {
        String LicensePlate= edt1.getText().toString();
        String vin =Edt2.getText().toString();
        String Vtype= edt3.getText().toString();
        String Vname =edt4.getText().toString();

        String date= edt5.getText().toString();
        String issue= edt6.getText().toString();
        String status ="Open";
        String Vcomp = comp.getText().toString();
        if(vin.equals(""))
        {
            Toast.makeText(CreateWorkOrderActivity.this,"Please enter Vin ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Vtype.equals(""))
        {
            Toast.makeText(CreateWorkOrderActivity.this,"Please enter Vehicle Type ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Vname.equals(""))
        {
            Toast.makeText(CreateWorkOrderActivity.this,"Please enter Vehicle Name",Toast.LENGTH_SHORT).show();
            return;
        }

        if(LicensePlate.equals("Select Vehicle"))
        {
            Toast.makeText(CreateWorkOrderActivity.this,"Please Select vehicle",Toast.LENGTH_SHORT).show();
            return;
        }

        if(date.equals(""))
        {
            Toast.makeText(CreateWorkOrderActivity.this,"Please enter date",Toast.LENGTH_SHORT).show();
            return;
        }
        if(issue.equals(""))
        {
            Toast.makeText(CreateWorkOrderActivity.this,"Please enter issue",Toast.LENGTH_SHORT).show();
            return;
        }
        //end
        JSONObject jobj = new JSONObject();


        SharedPreferences sp = getSharedPreferences("driver_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");

        String d_id1 =sp.getString("d_id","");

        try {

            jobj.put("v_name" , Vname);

            jobj.put("v_license" ,LicensePlate);

            jobj.put("v_vin",vin);
            jobj.put("v_vtype",Vtype);
            jobj.put("datte",date);
            jobj.put("issues",issue);
            jobj.put("status",status);
            jobj.put("a_id",admin_id);
            jobj.put("d_did",d_id1);
            jobj.put("v_comp",Vcomp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TAGTAG", "addwork: "+ jobj);
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/workorder_add.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(CreateWorkOrderActivity.this ,"WorkOrder already exist" , Toast.LENGTH_SHORT).show();
                        Log.d("fdsfdsfsd", "onErrorResponse: "+ response.getString("key"));
                    }
                    else if(response.getString("key").equals("1")) {

                        Log.d("fdsfdsfsd", "onErrorResponse: "+ response.getString("key"));
                        Toast.makeText(CreateWorkOrderActivity.this ," Added Successfully" , Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(CreateWorkOrderActivity.this, DriverMenuActivity.class);

                        startActivity(i);
                        finish();
                    }

                    else {
                        Toast.makeText(CreateWorkOrderActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);

                Log.d("cffsfsfsfs", "onErrorResponse: "+  error);

            }
        });

        jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000,3,2))  ;
        AppControllerActivity app=new AppControllerActivity(CreateWorkOrderActivity.this);
        app.addToRequestQueue(jobjreq);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edt5.setText(sdf.format(myCalendar.getTime()));
    }
}
