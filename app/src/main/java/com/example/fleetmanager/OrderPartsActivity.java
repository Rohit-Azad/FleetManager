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

public class OrderPartsActivity extends AppCompatActivity {

    EditText etDate, etVehicleName,etVinNum,etPartsOrdered,etTotal,etInvoice,etLPlate,etVid;
    Button btnSubmit,btnCancel;

    AutoCompleteTextView etCompany;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts_order);

        etDate=findViewById(R.id.etPartsDate);
        etVehicleName =findViewById(R.id.etVehicle);
        etVinNum=findViewById(R.id.etPartsVinNumber);
        etPartsOrdered=findViewById(R.id.etPartsOrdered);
        etTotal=findViewById(R.id.etPartsTotal);
        etInvoice=findViewById(R.id.etPartsInvoice);
        etLPlate=findViewById(R.id.etPartsLPlate);
        etCompany=findViewById(R.id.etCompany);
        etVid=findViewById(R.id.etVid);

        btnCancel=findViewById(R.id.btnPartsCancel);
        btnSubmit=findViewById(R.id.btnPartsSubmit);

        String [] companyArray={"Surrey Auto Parts","Ford Spares Ltd","Western Auto","NW Auto","North Van Auto","Queen's Auto","Star Garage"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, companyArray);
        //Getting the instance of AutoCompleteTextView
        etCompany.setThreshold(1);//will start working from first character
        etCompany.setAdapter(adapter);

        etVid.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                FetchDetails();

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                etVinNum.setText("");
                etVehicleName.setText("");
                etLPlate.setText("");

            }
        });

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
                new DatePickerDialog(OrderPartsActivity.this, date, myCalendar
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

    public void cancel(View view) {
        Intent i = new Intent(OrderPartsActivity.this, MechanicMenuActivity.class);

        startActivity(i);
    }

    public void submit_data(View view) {


        if(etDate.getText().toString().equals(""))
        {
            Toast.makeText(OrderPartsActivity.this,"Please enter date",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etVehicleName.getText().toString().equals(""))
        {
            Toast.makeText(OrderPartsActivity.this,"Please enter vehicle Name ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etPartsOrdered.getText().toString().equals(""))
        {
            Toast.makeText(OrderPartsActivity.this,"Please enter Parts ordered",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etTotal.getText().toString().equals(""))
        {
            Toast.makeText(OrderPartsActivity.this,"Please enter total charges",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etInvoice.getText().toString().equals(""))
        {
            Toast.makeText(OrderPartsActivity.this,"Please enter Invoice number",Toast.LENGTH_SHORT).show();
            return;
        }

        if(etLPlate.getText().toString().equals(""))
        {
            Toast.makeText(OrderPartsActivity.this,"Please enter License Plate #",Toast.LENGTH_SHORT).show();
            return;
        }if(etCompany.getText().toString().equals(""))
        {
            Toast.makeText(OrderPartsActivity.this,"Please enter company name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etVid.getText().toString().equals(""))
        {
            Toast.makeText(OrderPartsActivity.this,"Please enter Vehicle ID",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etDate.getText().toString().equals(""))
        {
            Toast.makeText(OrderPartsActivity.this,"Please enter Insurance End Date",Toast.LENGTH_SHORT).show();
            return;
        }
        //end
        JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("mechanic_info",MODE_PRIVATE);
        String mech_id =sp.getString("mechanic_id","");
        String admin_id =sp.getString("admin_id2","");

        try {
            jobj.put("etDate" , etDate.getText().toString());
            jobj.put("etPartsOrdered" , etPartsOrdered.getText().toString());
            jobj.put("etTotal",etTotal.getText().toString());
            jobj.put("etInvoice",etInvoice.getText().toString());
            jobj.put("etLPlate",etLPlate.getText().toString());
            jobj.put("etCompany",etCompany.getText().toString());
            jobj.put("etVid",etVid.getText().toString());
            jobj.put("mechanic_id",mech_id);
            jobj.put("a_id",admin_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("dfgvfv ","submit_data "+ jobj);
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/OrderPartsAdd.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d("fdbdsfb", "onResponse: "+response.getString("error"));
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(OrderPartsActivity.this ,"Order already exist" , Toast.LENGTH_SHORT).show();
                        Log.d("fdbdsfb", "onResponse: "+response.getString("error"));
                    }
                    else if(response.getString("key").equals("1")) {
                        Toast.makeText(OrderPartsActivity.this ,"Order Added Successfully" , Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(OrderPartsActivity.this, MechanicMenuActivity.class);

                        startActivity(i);
                        finish();
                    }

                    else {
                        Toast.makeText(OrderPartsActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

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
        AppControllerActivity app=new AppControllerActivity(OrderPartsActivity.this);
        app.addToRequestQueue(jobjreq);
    }

    public void FetchDetails()
    {
        if(etVid.getText().toString().equals(""))
        {
            Toast.makeText(OrderPartsActivity.this , "Please enter License plate" , Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject job = new JSONObject();

        try {
            job.put("v_id" ,etVid.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/fill_vehicledetail.php", job,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getString("key").equals("done"))
                            {
                                etVinNum.setText(response.getString("VinNumber"));
                                etLPlate.setText(response.getString("LicensePlate"));
                                etVehicleName.setText(response.getString("VehicleCompany")+" "+ response.getString("VehicleName"));
                                etInvoice.setText("FLM".concat(etLPlate.getText().toString().substring(3).concat(String.valueOf(Math.floor(Math.random() * 90 + 10)).substring(0,2)).concat(etVehicleName.getText().toString().substring(0,2))).toUpperCase());
                                Log.d("dsfdfg", "onResponse: "+etInvoice.getText().toString());
                            }
                            else {
                                Toast.makeText(OrderPartsActivity.this , "error" , Toast.LENGTH_SHORT).show();
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

        AppControllerActivity app = new AppControllerActivity(OrderPartsActivity.this);
        app.addToRequestQueue(jobjreq);
    }


}
