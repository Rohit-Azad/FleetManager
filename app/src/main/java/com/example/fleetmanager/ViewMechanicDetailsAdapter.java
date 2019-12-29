package com.example.fleetmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class ViewMechanicDetailsAdapter extends RecyclerView.Adapter<ViewMechanicDetailsHolder>{
    JSONArray jsarr;
    JSONObject job;

    // creating Activity variable globally
    Activity a;

    public ViewMechanicDetailsAdapter(JSONArray jsarr , Activity a)
    {

        this.jsarr = jsarr;
        this.a = a;
    }
    @NonNull
    @Override
    public ViewMechanicDetailsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewMechanicDetailsHolder vd = new ViewMechanicDetailsHolder(LayoutInflater.from(a).inflate(R.layout.mechanic_details_cell,viewGroup,false));
        return vd;
    }

    @Override
    public void onBindViewHolder(final ViewMechanicDetailsHolder vh, int position) {
        try {
            // iterating for each json object in json array
            job = (JSONObject) jsarr.get(position);

            // binding values from json object to cell layout via view holder


            vh.name.setText(job.getString("Name"));
            vh.email.setText(job.getString("Email"));
            vh.username.setText(job.getString("Username"));
            vh.mob.setText(job.getString("Mobile"));

            final String securityQuestion=job.getString("SecurityQuestion");
            final String password=job.getString("Password");
            final String securityAnswer=job.getString("SecurityAnswer");
            final String m_id=job.getString("Mid");


            vh.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences.Editor sp =a.getSharedPreferences("admin_info",MODE_PRIVATE).edit();

                    sp.putString("m_editName", vh.name.getText().toString());
                    sp.putString("m_editEmail", vh.email.getText().toString());
                    sp.putString("m_editUsername", vh.username.getText().toString());
                    sp.putString("m_editMobile", vh.mob.getText().toString());
                    sp.putString("m_editSecurityAnswer", securityAnswer);
                    sp.putString("m_editSecurityQuestion", securityQuestion);
                    sp.putString("m_editPassword", password);
                    sp.putString("m_id", m_id);


                    sp.apply();
                    Intent i = new Intent(a,EditMechanicInfoActivity.class);
                    a.startActivity(i);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsarr.length();
    }
}
