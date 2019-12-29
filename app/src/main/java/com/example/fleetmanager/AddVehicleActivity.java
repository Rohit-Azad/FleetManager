package com.example.fleetmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

/*
 * code by gurman and Rohit*/
public class AddVehicleActivity extends AppCompatActivity {
    EditText vehComp,vehName,vehLicenPlate,vehVinNum,vEngine,VehStart,VehEnd;
    Button addvehicle;
    Calendar myCalendar = Calendar.getInstance();

    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvehicle);

        vehComp = (EditText)findViewById(R.id.edt_vehicleComp);

        vehName=(EditText)findViewById(R.id.edt_VehicleName);
        vehLicenPlate=(EditText)findViewById(R.id.edt_VehicleLicense);
        vehVinNum=(EditText)findViewById(R.id.edt_VehicleVin);
        vEngine=(EditText)findViewById(R.id.edt_VehicleEngineNumber);
        VehStart=(EditText)findViewById(R.id.edt_InsuranceStart);
        VehEnd=(EditText)findViewById(R.id.edt_InsuranceEnd);
        addvehicle=(Button)findViewById(R.id.register);

        sp=(Spinner)findViewById(R.id.spinner2);

        String[] trucks={"Straight Truck","Semi Truck","Tanker Truck","Pickup Truck"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.custom_spinner_layout,trucks);

        sp.setAdapter(adapter);



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
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };

        VehStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddVehicleActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        VehEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddVehicleActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void addvehicle(View view) {

        String Type =sp.getSelectedItem().toString();
        String Comp =vehComp.getText().toString();
        String Name =vehName.getText().toString();
        String License=vehLicenPlate.getText().toString();
        String VinNumber=vehVinNum.getText().toString();
        String EngineNum=vEngine.getText().toString();
        String InsuranceStart = VehStart.getText().toString();
        String InsuranceEnd=VehEnd.getText().toString();

        if(Type.equals(""))
        {
            Toast.makeText(AddVehicleActivity.this,"Please enter vehicle Type ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Comp.equals(""))
        {
            Toast.makeText(AddVehicleActivity.this,"Please enter vehicle Company ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Name.equals(""))
        {
            Toast.makeText(AddVehicleActivity.this,"Please enter vehicle Name ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(License.equals(""))
        {
            Toast.makeText(AddVehicleActivity.this,"Please enter vehicle license plate ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(VinNumber.equals(""))
        {
            Toast.makeText(AddVehicleActivity.this,"Please enter Vin Number",Toast.LENGTH_SHORT).show();
            return;
        }

        if(VinNumber.equals(""))
        {
            Toast.makeText(AddVehicleActivity.this,"Please enter Vin Number",Toast.LENGTH_SHORT).show();
            return;
        }if(EngineNum.equals(""))
        {
            Toast.makeText(AddVehicleActivity.this,"Please enter Engine Number",Toast.LENGTH_SHORT).show();
            return;
        }
        if(InsuranceStart.equals(""))
        {
            Toast.makeText(AddVehicleActivity.this,"Please enter Insurance Start Date",Toast.LENGTH_SHORT).show();
            return;
        }
        if(InsuranceEnd.equals(""))
        {
            Toast.makeText(AddVehicleActivity.this,"Please enter Insurance End Date",Toast.LENGTH_SHORT).show();
            return;
        }
        //end
        JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");

        try {
            jobj.put("v_type" , Type);
            jobj.put("v_comp" , Comp);
            jobj.put("v_name" , Name);
            jobj.put("v_license" , License);
            jobj.put("v_vin",VinNumber);
            jobj.put("v_engine",EngineNum);
            jobj.put("v_insurancestart",InsuranceStart);
            jobj.put("v_insuranceend",InsuranceEnd);
            jobj.put("a_id",admin_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/vehicle_add.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(AddVehicleActivity.this ,"Vehicle already exist" , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("1")) {
                        Toast.makeText(AddVehicleActivity.this ,"Vehicle Added Successfully" , Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(AddVehicleActivity.this, AdminAddInventoryActivity.class);

                        startActivity(i);
                        finish();
                    }

                    else {
                        Toast.makeText(AddVehicleActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

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
        AppControllerActivity app=new AppControllerActivity(AddVehicleActivity.this);
        app.addToRequestQueue(jobjreq);

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        VehStart.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        VehEnd.setText(sdf.format(myCalendar.getTime()));
    }
}
