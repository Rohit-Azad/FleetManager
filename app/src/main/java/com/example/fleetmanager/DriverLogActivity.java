package com.example.fleetmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class DriverLogActivity extends AppCompatActivity {

    EditText etDate,etMaterial,etDistance,etFuelCard,etOdometer;
    AutoCompleteTextView etTo,etFrom;
    Button btnSubmit,btnCancel;
    Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_log);

        etDate=findViewById(R.id.etLogDate);
        etTo=findViewById(R.id.etLogTo);
        etFrom=findViewById(R.id.etLogFrom);
        etMaterial=findViewById(R.id.etLogMaterial);
        etFuelCard=findViewById(R.id.etLogFuelCard);
        etDistance=findViewById(R.id.etLogDistance);
        etOdometer=findViewById(R.id.etLogOdometer);

        btnCancel=findViewById(R.id.btnLogCancel);
        btnSubmit=findViewById(R.id.btnLogSubmit);

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

        String [] locations={"New Westminster, BC","Vancouver, BC","Calgary, AB","Winnipeg, MB","Seattle, WA","New York, NY","Los Angeles, CA","Las Vegas, NV","Toronto, ON","Atlantic City, NJ","Prince George, BC","Edmonton, AB","Saskatoon, BC","Barrow, Alaska"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, locations);
        //Getting the instance of AutoCompleteTextView
        etTo.setThreshold(1);//will start working from first character
        etTo.setAdapter(adapter);
        etFrom.setThreshold(1);//will start working from first character
        etFrom.setAdapter(adapter);


        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DriverLogActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void submitLog(View view) {
        String to =etTo.getText().toString();

        String from= etFrom.getText().toString();
        String Date = etDate.getText().toString();
        String material= etMaterial.getText().toString();
        String fuelCard= etFuelCard.getText().toString();
        String distance= etDistance.getText().toString();
        String odometer= etOdometer.getText().toString();

        if(to.equals(""))
        {
            Toast.makeText(DriverLogActivity.this,"Please enter WID ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Date.equals(""))
        {
            Toast.makeText(DriverLogActivity.this,"Please enter date ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(odometer.equals(""))
        {
            Toast.makeText(DriverLogActivity.this,"Please enter Work hour",Toast.LENGTH_SHORT).show();
            return;
        }
        if(material.equals(""))
        {
            Toast.makeText(DriverLogActivity.this,"Please ENTER License ",Toast.LENGTH_SHORT).show();
            return;
        }

        if(fuelCard.equals(""))
        {
            Toast.makeText(DriverLogActivity.this,"Please ENTER License ",Toast.LENGTH_SHORT).show();
            return;
        }

        if(distance.equals(""))
        {
            Toast.makeText(DriverLogActivity.this,"Please ENTER License ",Toast.LENGTH_SHORT).show();
            return;
        }

        if(from.equals(""))
        {
            Toast.makeText(DriverLogActivity.this,"Please ENTER License ",Toast.LENGTH_SHORT).show();
            return;
        }


        //end
        final JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("driver_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");
        String dr_id =sp.getString("d_id","");
        try {

            jobj.put("dr_id" , dr_id);
            jobj.put("dr_date" , Date);
            jobj.put("dr_to",to);
            jobj.put("dr_from",from);
            jobj.put("dr_material",material);
            jobj.put("dr_fuel",fuelCard);
            jobj.put("dr_distance",distance);
            jobj.put("dr_odometer",odometer);
            jobj.put("a_id",admin_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/driverLog_add.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
             //  Log.d("vfgdgdrevg", "onResponse: "+response.getString("resz"));

                try {
                    Log.d("vfgdgdrevg", "onResponse: "+response.getString("resz"));
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(DriverLogActivity.this ,"Log already exist" , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("1")) {
                        Toast.makeText(DriverLogActivity.this ,"Log Added Successfully" , Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(DriverLogActivity.this, DriverMenuActivity.class);

                        startActivity(i);
//                        finish();
                    }

                    else {
                        Toast.makeText(DriverLogActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

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
        AppControllerActivity app=new AppControllerActivity(DriverLogActivity.this);
        app.addToRequestQueue(jobjreq);
    }
}
