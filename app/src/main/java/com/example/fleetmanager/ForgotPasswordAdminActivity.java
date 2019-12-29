package com.example.fleetmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordAdminActivity extends AppCompatActivity {
        EditText id,mobileNumber,newPassword,passwordConfirm,answer;
        Spinner role;

        Button submit,check;

        TextView result,ques;

        String roleField,id_field,tableName,secAnswerString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_admin);
        id=findViewById(R.id.etID);
        mobileNumber=findViewById(R.id.etMobile);

        newPassword=findViewById(R.id.etNewPassword);
        passwordConfirm=findViewById(R.id.etConfirm);
        result=findViewById(R.id.txtResult);
        ques=findViewById(R.id.tvQues);
        answer=findViewById(R.id.etAnswer);
        submit=findViewById(R.id.btnSubmit);
        check=findViewById(R.id.btnCheck);
        role=(Spinner)findViewById(R.id.spinnerRole);



        String[] rolesArray={"Administrator","Driver","Mechanic"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.custom_spinner_layout,rolesArray);

        role.setAdapter(adapter);
        role.setSelection(getIntent().getIntExtra("index",0));

        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                switch (position)
                {
                    case 2:
                    {
                        answer.setVisibility(View.VISIBLE);
                        ques.setVisibility(View.VISIBLE);
                        check.setVisibility(View.VISIBLE);
                        break;
                    }
                    default:
                    {
                        answer.setVisibility(View.INVISIBLE);
                        ques.setVisibility(View.INVISIBLE);
                        check.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }


    public void go_back(View view) {
        Intent intent = new Intent(ForgotPasswordAdminActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }


    public void submit_details(View view) {

        if(id.getText().toString().equals("") ||
                mobileNumber.getText().toString().equals("") ||
                newPassword.getText().toString().equals("") ||
                passwordConfirm.getText().toString().equals("") ||
                !passwordConfirm.getText().toString().equals(newPassword.getText().toString()))
        {
            Toast.makeText(this , "Fill in the required fields" , Toast.LENGTH_SHORT).show();
        }
        else {

            if (role.getSelectedItem().toString().equals("Mechanic") && answer.getText().toString().equals(secAnswerString)) {
                new AlertDialog.Builder(ForgotPasswordAdminActivity.this)
                        .setTitle("Answer Correct")
                        .setMessage("Your  security answer is correct. Please press ok to continue")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(ForgotPasswordAdminActivity.this, MainMenuActivity.class);
                                startActivity(i);
                            }
                        })
                        .show();
            } else if (role.getSelectedItem().toString().equals("Mechanic") && !answer.getText().toString().equals(secAnswerString)) {
                new AlertDialog.Builder(ForgotPasswordAdminActivity.this)
                        .setTitle("Answer Incorrect")
                        .setMessage("Please try again")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                answer.getText().clear();
                            }
                        })
                        .show();
            }

            if (role.getSelectedItem().toString().equals("Mechanic") && answer.getText().toString().equals("")) {
                Toast.makeText(this, "Answer not given", Toast.LENGTH_SHORT).show();
            } else {
                get_values();
            }
        }
//        if(mobileNumber.getText().toString().trim().length()==10)
//        {
//            sendSMS(contact1.trim() , "SOS ALERT!! "+ name1 +"\nFrom: "+userName+ "\nLocation:\n"+ mAddress.getText().toString()
//                    +"\nLatitude: "+mLatitude+"\nLongitude: "+mLongitude+"\nTimeStamp: "+mTimestamp+"\n<FLEET MANAGER ALERT SERVICE>");
        //    sendSMS(mobileNumber.getText().toString().trim() , "Password Reset. Here's your new password:\n "+newPassword.getText().toString()+"\n<FLEET MANAGER APP>");
//        }

      //  Toast.makeText(this , "Alert message sent" , Toast.LENGTH_SHORT).show();

    }

    public  void get_values()
        {
            if (role.getSelectedItem().toString().equals("Administrator")) {
                roleField = "Admin";
                id_field = "id";
                tableName = "admin_signup";
            } else if (role.getSelectedItem().toString().equals("Driver")) {
                roleField = "Driver";
                id_field = "Did";
                tableName = "driver";
            } else if (role.getSelectedItem().toString().equals("Mechanic")) {
                roleField = "Mechanic";
                id_field = "Mid";
                tableName = "mechanic";
            }

            JSONObject jobj = new JSONObject();

            try {
                jobj.put("id_key", id.getText().toString());
                jobj.put("mobile", mobileNumber.getText().toString());
                jobj.put("role", role.getSelectedItem());
                jobj.put("id_field", id_field);
                jobj.put("pass", newPassword.getText().toString());
                jobj.put("tName", tableName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("sfvdef", "get_values: " + jobj);
            JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Ipaddress.ip + "/fleet/PasswordReset_Admin.php", jobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    try {
                        if (response.getString("key").equals("1")) {


//                        new AlertDialog.Builder(ForgotPasswordAdminActivity.this)
//                                .setTitle("Your Password")
//                                .setMessage("Write down this password: \n" +
//                                        response.getString("Password"))
//
//                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                })
//                                .show();

                            Toast.makeText(ForgotPasswordAdminActivity.this, "Success", Toast.LENGTH_SHORT).show();

//                        result.setText(response.getString("Password"));

                        } else {
                            Toast.makeText(ForgotPasswordAdminActivity.this, "error", Toast.LENGTH_SHORT).show();
                            result.setText("");
                            Log.d("fverfeer", "onResponse: " + response.getString("error"));
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


            jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));
            AppControllerActivity app = new AppControllerActivity(ForgotPasswordAdminActivity.this);
            app.addToRequestQueue(jobreq);

    }


    public  void get_values_mech(View view)
        {

            if (role.getSelectedItem().toString().equals("Administrator")) {
                roleField = "Admin";
                id_field = "id";
                tableName = "admin_signup";
            } else if (role.getSelectedItem().toString().equals("Driver")) {
                roleField = "Driver";
                id_field = "Did";
                tableName = "driver";
            } else if (role.getSelectedItem().toString().equals("Mechanic")) {
                roleField = "Mechanic";
                id_field = "Mid";
                tableName = "mechanic";
            }

            JSONObject jobj = new JSONObject();
            SharedPreferences sp = getSharedPreferences("mechanic_info", MODE_PRIVATE);
            String adm_id = sp.getString("admin_id2", "");

            try {
                jobj.put("id_key", id.getText().toString());
                jobj.put("mobile", mobileNumber.getText().toString());
                jobj.put("role", role.getSelectedItem());
                jobj.put("id_field", id_field);
                jobj.put("pass", newPassword.getText().toString());
                jobj.put("tName", tableName);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Ipaddress.ip + "/fleet/fetchMech.php", jobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jarr = response.getJSONArray("result");

                        JSONObject job_box = (JSONObject) jarr.get(0);
                        ques.setText(job_box.getString("SecurityQuestion"));
                        secAnswerString = job_box.getString("SecurityAnswer");

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


            jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));
            AppControllerActivity app = new AppControllerActivity(ForgotPasswordAdminActivity.this);
            app.addToRequestQueue(jobreq);

    }
}
