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

public class EditWorkOrderAdminActivity extends AppCompatActivity {

    EditText etDriver,etWorkOrderID,etLicensePlate,etIssue,etDate;
    Spinner spStatus;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work_order_admin);

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        etDriver=findViewById(R.id.etDriverID);
        etWorkOrderID=findViewById(R.id.etWorkOrderID);
        etLicensePlate=findViewById(R.id.etLicensePlate);
        etIssue=findViewById(R.id.etIssue);
        etDate=findViewById(R.id.etDate);
        spStatus=findViewById(R.id.spinnerStatus);
        String[] statusArray={"Open","Closed","in Progress"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.custom_spinner_layout,statusArray);

        spStatus.setAdapter(adapter);
        Log.d("gdrefv", "onCreate: "+sp.getString("editStatus",""));

        if(sp.getString("editStatus","").trim().equals("Open"))
        {
            spStatus.setSelection(0);
        }
        else if((sp.getString("editStatus","").trim().equals("Closed")))
        {
            spStatus.setSelection(1);
        }
        else
        {
            spStatus.setSelection(2);
        }

        etDriver.setText(sp.getString("dr_id",""));
        etWorkOrderID.setText(sp.getString("editWid",""));
        etLicensePlate.setText(sp.getString("editLicense",""));
        etIssue.setText(sp.getString("editIssue",""));
        etDate.setText(sp.getString("editDate",""));

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
                new DatePickerDialog(EditWorkOrderAdminActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void update_work_order(View view) {

        String issue =etIssue.getText().toString();

        String date= etDate.getText().toString();
        String status= spStatus.getSelectedItem().toString();

        if(issue.equals(""))
        {
            Toast.makeText(EditWorkOrderAdminActivity.this,"Please enter Issue ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(date.equals(""))
        {
            Toast.makeText(EditWorkOrderAdminActivity.this,"Please enter Date ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(status.equals(""))
        {
            Toast.makeText(EditWorkOrderAdminActivity.this,"Please enter Status",Toast.LENGTH_SHORT).show();
            return;
        }

        final JSONObject jobj = new JSONObject();


        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");

        try {
            jobj.put("A_issue" ,issue);
            jobj.put("A_date" ,date);
            jobj.put("A_status",status);
            jobj.put("a_id",admin_id);
            jobj.put("w_id",etWorkOrderID.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("dfgdfbgv", "update_work_order: "+jobj);
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/EditWorkOrder_admin.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("key").equals("0"))
                    {
                        Log.d("fdgdfgv", "onResponse: "+response.getString("error"));
                        Toast.makeText(EditWorkOrderAdminActivity.this ,"Error " , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("2"))
                    {
                        Toast.makeText(EditWorkOrderAdminActivity.this ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(EditWorkOrderAdminActivity.this ," Updated Successfully" , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditWorkOrderAdminActivity.this, AdminViewEditInventoryActivity.class);
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
        AppControllerActivity app=new AppControllerActivity(EditWorkOrderAdminActivity.this);
        app.addToRequestQueue(jobjreq);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }

}
