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

public class EditMechanicInfoActivity extends AppCompatActivity {
    EditText etName, etSecurityQuestion,etMobile,etSecurityAnswer,etUsername,etPassword,etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mechanic_info);

        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);
        String u = sp.getString("editName","");

        etName=findViewById(R.id.etName);
        etSecurityQuestion =findViewById(R.id.etSecurityQues);
        etSecurityAnswer=findViewById(R.id.etSecurityAns);
        etMobile=findViewById(R.id.etMobile);
        etUsername=findViewById(R.id.etUserName);
        etPassword=findViewById(R.id.etPassword);
        etEmail=findViewById(R.id.etEmail);

        etName.setText(sp.getString("m_editName",""));
        etSecurityQuestion.setText(sp.getString("m_editSecurityQuestion",""));
        etSecurityAnswer.setText(sp.getString("m_editSecurityAnswer",""));
        etMobile.setText(sp.getString("m_editMobile",""));
        etUsername.setText(sp.getString("m_editUsername",""));
        etPassword.setText(sp.getString("m_editPassword",""));
        etEmail.setText(sp.getString("m_editEmail",""));

      //  sp.putString("m_id", m_id);
    }

    public void EditMechanicInfo(View view) {

        if(etName.getText().toString().equals(""))
        {
            Toast.makeText(EditMechanicInfoActivity.this,"Please enter Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etMobile.getText().toString().equals(""))
        {
            Toast.makeText(EditMechanicInfoActivity.this,"Please enter mobile number ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etSecurityQuestion.getText().toString().equals(""))
        {
            Toast.makeText(EditMechanicInfoActivity.this,"Please enter security answer",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etSecurityAnswer.getText().toString().equals(""))
        {
            Toast.makeText(EditMechanicInfoActivity.this,"Please enter security answer",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etEmail.getText().toString().equals(""))
        {
            Toast.makeText(EditMechanicInfoActivity.this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etUsername.getText().toString().equals(""))
        {
            Toast.makeText(EditMechanicInfoActivity.this,"Please enter username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etPassword.getText().toString().equals(""))
        {
            Toast.makeText(EditMechanicInfoActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        final JSONObject jobj = new JSONObject();


        SharedPreferences sp = getSharedPreferences("admin_info",MODE_PRIVATE);

        try {
            jobj.put("editMechanc_name" ,etName.getText().toString());
            jobj.put("editMechanc_secques" ,etSecurityQuestion.getText().toString());
            jobj.put("editMechanc_secans" ,etSecurityAnswer.getText().toString());
            jobj.put("editMechanc_mobile" ,etMobile.getText().toString());
            jobj.put("m_id" ,sp.getString("m_id",""));
            jobj.put("editMechanc_pass",etPassword.getText().toString());
            jobj.put("a_id",sp.getString("admin_id2",""));
            jobj.put("editMechanc_username",etUsername.getText().toString());
            jobj.put("editMechanc_email",etEmail.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("dfgdfbgv", "update_work_order: "+jobj);
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ Ipaddress.ip+"/fleet/EditMechanicInfo_admin.php",jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("key").equals("0"))
                    {
                        Log.d("fdgdfgv", "onResponse: "+response.getString("error"));
                        Toast.makeText(EditMechanicInfoActivity.this ,"Error " , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("2"))
                    {
                        Toast.makeText(EditMechanicInfoActivity.this ,"No Changes Made" , Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(EditMechanicInfoActivity.this ," Updated Successfully" , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditMechanicInfoActivity.this, AdminViewEditInventoryActivity.class);
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
        AppControllerActivity app=new AppControllerActivity(EditMechanicInfoActivity.this);
        app.addToRequestQueue(jobjreq);
    }
}
