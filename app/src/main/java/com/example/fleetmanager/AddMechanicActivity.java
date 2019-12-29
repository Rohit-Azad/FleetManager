package com.example.fleetmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMechanicActivity extends AppCompatActivity {

    EditText name1,mob,emaill,usrname,pass,cpass,secq,secas;
    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__mechanic);

        name1= (EditText)findViewById(R.id.Mname1);

        mob= (EditText)findViewById(R.id.mobile);
        emaill= (EditText)findViewById(R.id.email);
        usrname= (EditText)findViewById(R.id.edtuname);
        pass= (EditText)findViewById(R.id.edtpass);

        cpass = (EditText)findViewById(R.id.edtcpass);
        secq= (EditText)findViewById(R.id.SecQues);
        secas= (EditText)findViewById(R.id.SecAns);
    }

    public void addMechanic(View view) {


        String Name =name1.getText().toString();
        String Mobile =mob.getText().toString();
        String UsrName= usrname.getText().toString();
        String Email= emaill.getText().toString();
        String Password= pass.getText().toString();
        String Cpass= cpass.getText().toString();
        String sec= secq.getText().toString();

        String secA= secas.getText().toString();
        if(Name.equals(""))
        {
            Toast.makeText(AddMechanicActivity.this,"Please enter Name ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Mobile.equals(""))
        {
            Toast.makeText(AddMechanicActivity.this,"Please enter Mobile ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(UsrName.equals(""))
        {
            Toast.makeText(AddMechanicActivity.this,"Please enter Username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(secA.equals(""))
        {
            Toast.makeText(AddMechanicActivity.this,"Please enter Sec ANSWER",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Password.matches(PASSWORD_PATTERN))
        {
            Toast.makeText(AddMechanicActivity.this,"Password should Be between 8 and 40 characters long\n" +
                    "Contain at least one digit.\n" +
                    "Contain at least one lower case character.\n" +
                    "Contain at least one upper case character.\n" +
                    "Contain at least on special character from [ @ # $ % ! . ].  ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Cpass.equals(""))
        {
            Toast.makeText(AddMechanicActivity.this,"Please enter confirm password",Toast.LENGTH_SHORT).show();
            return;
        }

        if(sec.equals(""))
        {
            Toast.makeText(AddMechanicActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        //end
        JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String admin_id =sp.getString("admin_id2","");

        try {

            jobj.put("m_name" , Name);

            jobj.put("m_username" ,UsrName);
            jobj.put("m_email",Email);
            jobj.put("m_password",Password);
            jobj.put("m_mobile",Mobile);
            jobj.put("m_security",sec);
            jobj.put("m_securityA",secA);
            jobj.put("a_id",admin_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TAGvodsvv", "addMechanic: "+jobj);
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/fleet/mechanic_add.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(AddMechanicActivity.this ,"Mechanic already exist" , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("1")) {
                        Toast.makeText(AddMechanicActivity.this ,"Mechanic Added Successfully" , Toast.LENGTH_SHORT).show();
                     //   Log.d("efrgre", "onResponse: "+response.getString("test"));
                        Intent i = new Intent(AddMechanicActivity.this, AdminAddInventoryActivity.class);

                        startActivity(i);
                        finish();
                    }

                    else {
                        Toast.makeText(AddMechanicActivity.this ,"Error" , Toast.LENGTH_SHORT).show();

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
        AppControllerActivity app=new AppControllerActivity(AddMechanicActivity.this);
        app.addToRequestQueue(jobjreq);
    }
}
