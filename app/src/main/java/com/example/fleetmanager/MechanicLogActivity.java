package com.example.fleetmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MechanicLogActivity extends AppCompatActivity {

    EditText etDate,etWorkOrderID,etPartsOrdered,etWorkHours,etLPlate;
    Button btnSubmit,btnCancel;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_log);

        etDate=findViewById(R.id.etMechLogDate);
        etWorkOrderID=findViewById(R.id.etMechLogWorkID);
        etPartsOrdered=findViewById(R.id.etMechLogPartsOrdered);
        etWorkHours=findViewById(R.id.etMechLogWorkHours);
        etLPlate=findViewById(R.id.etMechLogLPlate);

        btnCancel=findViewById(R.id.btnMechLogCancel);
        btnSubmit=findViewById(R.id.btnMechLogSubmit);

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

        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MechanicLogActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etWorkOrderID.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                FetchDetails();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                etLPlate.setText("");
                etDate.setText("");

            }
        });

//checking ids
        SharedPreferences sp = getSharedPreferences("mechanic_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");
        String mec_id =sp.getString("mechanic_id","");
        System.out.println(" hello"+admin_id+" "+mec_id);
    }


    public void submitLog(View view) {
        String WorkOrderID =etWorkOrderID.getText().toString();

        String License= etLPlate.getText().toString();
        String Date = etDate.getText().toString();
        String WorkHour= etWorkHours.getText().toString();


        if(WorkOrderID.equals(""))
        {
            Toast.makeText(MechanicLogActivity.this,"Please enter WID ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Date.equals(""))
        {
            Toast.makeText(MechanicLogActivity.this,"Please enter date ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(WorkHour.equals(""))
        {
            Toast.makeText(MechanicLogActivity.this,"Please enter Work hour",Toast.LENGTH_SHORT).show();
            return;
        }
        if(License.equals(""))
        {
            Toast.makeText(MechanicLogActivity.this,"Please ENTER License ",Toast.LENGTH_SHORT).show();
            return;
        }


        //end
        final JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("mechanic_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");
        String mec_id =sp.getString("mechanic_id","");
        try {

            jobj.put("m_id" , mec_id);
            jobj.put("m_date" , Date);
            jobj.put("m_workOrderID",WorkOrderID);
            jobj.put("m_license",License);
            jobj.put("m_workHour",WorkHour);
            jobj.put("a_id",admin_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/mecLog_add.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("vfgdgdrevg", "onResponse: "+jobj);

                try {
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(MechanicLogActivity.this ,"Log already exist" , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("1")) {
                        Toast.makeText(MechanicLogActivity.this ,"Log Added Successfully" , Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(MechanicLogActivity.this, MechanicMenuActivity.class);

                        startActivity(i);
                        finish();
                    }

                    else {
                        Toast.makeText(MechanicLogActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

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
        AppControllerActivity app=new AppControllerActivity(MechanicLogActivity.this);
        app.addToRequestQueue(jobjreq);
    }

    public void cancel(View view) {

        this.finish();
    }


    public void FetchDetails()
    {
        String WorkOrderID =etWorkOrderID.getText().toString();

        final JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("mechanic_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");

        try {
            jobj.put("m_workOrderID",WorkOrderID);
            jobj.put("a_id",admin_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/FillWorkOrderDetails.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(MechanicLogActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("1")) {
                        Toast.makeText(MechanicLogActivity.this ,"Fetched Successfully" , Toast.LENGTH_SHORT).show();
                        etDate.setText(response.getString("Date"));
                        etLPlate.setText(response.getString("LicensePlate"));
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
        AppControllerActivity app=new AppControllerActivity(MechanicLogActivity.this);
        app.addToRequestQueue(jobjreq);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }

}
