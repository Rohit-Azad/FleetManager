package com.example.fleetmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class EditVehicleInfoActivity extends AppCompatActivity {
    EditText etNameEdit,etLicenseEdit,etCompanyEdit,etTypeEdit,etVinEdit,etEngNumEdit,etStart,etEnd;

    Spinner spType;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle_info);
        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        etNameEdit=findViewById(R.id.etVNameEdit);
        etLicenseEdit=findViewById(R.id.etLicenseEdit);
        etCompanyEdit=findViewById(R.id.etVCompEdit);
        etVinEdit=findViewById(R.id.etVinEdit);
        etEngNumEdit=findViewById(R.id.etEngNumEdit);
        etStart=findViewById(R.id.etStart);
        etEnd=findViewById(R.id.etEnd);
        spType=findViewById(R.id.spinnerType);

        String[] trucks={"Straight Truck","Semi Truck","Tanker Truck","Pickup Truck"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.custom_spinner_layout,trucks);

        spType.setAdapter(adapter);

        if(sp.getString("editType","").equals("Straight Truck"))
        {
            spType.setSelection(0);
        }
        else if(sp.getString("editType","").equals("Semi Truck"))
        {
            spType.setSelection(1);
        }
        else if(sp.getString("editType","").equals("Tanker Truck"))
        {
            spType.setSelection(2);
        }
        else
        {
            spType.setSelection(3);
        }

        etNameEdit.setText(sp.getString("editName",""));
        etLicenseEdit.setText(sp.getString("editLicense",""));
        etCompanyEdit.setText(sp.getString("editComp",""));
        etVinEdit.setText(sp.getString("editVin",""));
        etEngNumEdit.setText(sp.getString("editEngNum",""));
        etEnd.setText(sp.getString("editInsEnd",""));
        etStart.setText(sp.getString("editInsStart",""));

        final String v_id= sp.getString("v_id","");
        final String a_id=sp.getString("a_id","");


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

        etStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditVehicleInfoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditVehicleInfoActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void EditVehicleInfo(View view) {

        if(etNameEdit.getText().toString().equals(""))
        {
            Toast.makeText(EditVehicleInfoActivity.this,"Please enter Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etLicenseEdit.getText().toString().equals(""))
        {
            Toast.makeText(EditVehicleInfoActivity.this,"Please enter License Plate",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etCompanyEdit.getText().toString().equals(""))
        {
            Toast.makeText(EditVehicleInfoActivity.this,"Please enter Company",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etVinEdit.getText().toString().equals(""))
        {
            Toast.makeText(EditVehicleInfoActivity.this,"Please enter Vin Number",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etEngNumEdit.getText().toString().equals(""))
        {
            Toast.makeText(EditVehicleInfoActivity.this,"Please Engine Number",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etEnd.getText().toString().equals(""))
        {
            Toast.makeText(EditVehicleInfoActivity.this,"Please Insurance End Date",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etStart.getText().toString().equals(""))
        {
            Toast.makeText(EditVehicleInfoActivity.this,"Please Insurance Start Date",Toast.LENGTH_SHORT).show();
            return;
        }

        final JSONObject jobj = new JSONObject();


        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);

        final String v_id= sp.getString("v_id","");
        final String a_id=sp.getString("a_id","");

        try {
            jobj.put("ev_editName" ,etNameEdit.getText().toString());
            jobj.put("ev_editLicense" ,etLicenseEdit.getText().toString());
            jobj.put("ev_editComp" ,etCompanyEdit.getText().toString());
            jobj.put("ev_editType" ,spType.getSelectedItem().toString());
            jobj.put("v_id" ,sp.getString("v_id",""));
            jobj.put("ev_editVin",etVinEdit.getText().toString());
            jobj.put("a_id",sp.getString("a_id",""));
            jobj.put("ev_editEngNum",etEngNumEdit.getText().toString());
            jobj.put("ev_editInsEnd",etEnd.getText().toString());
            jobj.put("ev_editInsStart",etStart.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("dfgdfbgv", "update_work_order: "+jobj);
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/EditVehicleInfo_admin.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("key").equals("0"))
                    {
                        Log.d("fdgdfgv", "onResponse: "+response.getString("error"));
                        Toast.makeText(EditVehicleInfoActivity.this ,"Error " , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("2"))
                    {
                        Toast.makeText(EditVehicleInfoActivity.this ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(EditVehicleInfoActivity.this ," Updated Successfully" , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditVehicleInfoActivity.this, AdminViewEditInventoryActivity.class);
                        startActivity(i);
                        finish();
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
        AppControllerActivity app=new AppControllerActivity(EditVehicleInfoActivity.this);
        app.addToRequestQueue(jobjreq);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etStart.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etEnd.setText(sdf.format(myCalendar.getTime()));
    }
}
