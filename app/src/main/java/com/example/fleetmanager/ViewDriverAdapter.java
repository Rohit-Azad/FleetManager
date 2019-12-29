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

/*
 * code by gurman and Rohit*/
public class ViewDriverAdapter extends RecyclerView.Adapter<ViewDriverViewHolder> {

    JSONArray jsarr;
    JSONObject job;

            // creating Activity variable globally
    Activity a;

    public ViewDriverAdapter(JSONArray jsarr , Activity a)
    {

        this.jsarr = jsarr;
        this.a = a;
    }
    @NonNull
    @Override
    public ViewDriverViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewDriverViewHolder vd = new ViewDriverViewHolder(LayoutInflater.from(a).inflate(R.layout.driverdetails_cell,viewGroup,false));
        return vd;
    }

    @Override
    public void onBindViewHolder(final ViewDriverViewHolder viewDriverViewHolder, int position) {
        try {
            // iterating for each json object in json array
            job = (JSONObject) jsarr.get(position);

            // binding values from json object to cell layout via view holder


           viewDriverViewHolder.name.setText(job.getString("Name"));
            viewDriverViewHolder.truck.setText(job.getString("vehicle"));
            viewDriverViewHolder.dl.setText(job.getString("DLicense"));
            viewDriverViewHolder.mob.setText(job.getString("Mobile"));

            final String userName=job.getString("Username");
            final String password=job.getString("Password");
            final String dr_id=job.getString("Did");


            viewDriverViewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences.Editor sp =a.getSharedPreferences("admin_info",MODE_PRIVATE).edit();

                        sp.putString("editName", viewDriverViewHolder.name.getText().toString());
                        sp.putString("editVehicle", viewDriverViewHolder.truck.getText().toString());
                        sp.putString("editLicense", viewDriverViewHolder.dl.getText().toString());
                        sp.putString("editMobile", viewDriverViewHolder.mob.getText().toString());
                        sp.putString("editUsername", userName);
                        sp.putString("editPassword", password);
                        sp.putString("dr_id", dr_id);


                    sp.apply();
                    Intent i = new Intent(a,EditDriverInfoActivity.class);
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
