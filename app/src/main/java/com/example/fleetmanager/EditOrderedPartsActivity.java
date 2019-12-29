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

public class EditOrderedPartsActivity extends AppCompatActivity {
    EditText etMechID, etOrderID, etVehicleID, etPartsOrdered,etDate,etCompany,etTotal;
    Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ordered_parts);

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        etMechID =findViewById(R.id.etMID);
        etOrderID =findViewById(R.id.etOrderID);
        etVehicleID =findViewById(R.id.etVID);
        etPartsOrdered =findViewById(R.id.etPartsOrdered);
        etDate=findViewById(R.id.etDate);
        etCompany=findViewById(R.id.etCompany);
        etTotal=findViewById(R.id.etTotal);

        etMechID.setText(sp.getString("m_id",""));
        etOrderID.setText(sp.getString("opId",""));
        etVehicleID.setText(sp.getString("v_id",""));
        etPartsOrdered.setText(sp.getString("orderedParts",""));
        etDate.setText(sp.getString("date",""));
        etCompany.setText(sp.getString("company",""));
        etTotal.setText(sp.getString("total",""));




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
                new DatePickerDialog(EditOrderedPartsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void update_order(View view) {

        String issue = etPartsOrdered.getText().toString();

        String date= etDate.getText().toString();

        if(etPartsOrdered.getText().toString().equals(""))
        {
            Toast.makeText(EditOrderedPartsActivity.this,"Please enter Issue ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etDate.getText().toString().equals(""))
        {
            Toast.makeText(EditOrderedPartsActivity.this,"Please enter Date ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etCompany.getText().toString().equals(""))
        {
            Toast.makeText(EditOrderedPartsActivity.this,"Please enter Status",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etTotal.getText().toString().equals(""))
        {
            Toast.makeText(EditOrderedPartsActivity.this,"Please enter Status",Toast.LENGTH_SHORT).show();
            return;
        }

        final JSONObject jobj = new JSONObject();


        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");

        try {
            jobj.put("editParts" ,etPartsOrdered.getText().toString());
            jobj.put("editDate" ,etDate.getText().toString());
            jobj.put("editCompany",etCompany.getText().toString());
            jobj.put("editTotal",etTotal.getText().toString());
            jobj.put("op_id", etOrderID.getText().toString());
            jobj.put("a_id", admin_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("dfgdfbgv", "update_work_order: "+jobj);
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/EditPartsOrder_admin.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("key").equals("0"))
                    {
                        Log.d("fdgdfgv", "onResponse: "+response.getString("error"));
                        Toast.makeText(EditOrderedPartsActivity.this ,"Error " , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("2"))
                    {
                        Toast.makeText(EditOrderedPartsActivity.this ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(EditOrderedPartsActivity.this ," Updated Successfully" , Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(EditOrderedPartsActivity.this, AdminViewEditInventoryActivity.class);
//                        startActivity(i);
//                        finish();
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
        AppControllerActivity app=new AppControllerActivity(EditOrderedPartsActivity.this);
        app.addToRequestQueue(jobjreq);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }
}
