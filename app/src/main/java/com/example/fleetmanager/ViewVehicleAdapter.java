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

public class ViewVehicleAdapter extends RecyclerView.Adapter<ViewVehicleViewHolder> {

    JSONArray jsarr;
    JSONObject job;

    // creating Activity variable globally
    Activity a;

    public ViewVehicleAdapter(JSONArray jsarr , Activity a)
    {

        this.jsarr = jsarr;

        this.a = a;
    }
    @NonNull
    @Override
    public ViewVehicleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewVehicleViewHolder vd = new ViewVehicleViewHolder(LayoutInflater.from(a).inflate(R.layout.vehicle_details_cell,viewGroup,false));
        return vd;
    }

    @Override
    public void onBindViewHolder(final ViewVehicleViewHolder vh, int position) {
        try {
            // iterating for each json object in json array
            job = (JSONObject) jsarr.get(position);

            // binding values from json object to cell layout via view holder


            vh.name.setText(job.getString("VehicleName"));
            vh.vinNum.setText(job.getString("VinNumber"));
            vh.company.setText(job.getString("VehicleCompany"));
            vh.licenseNum.setText(job.getString("LicensePlate"));
            vh.engNum.setText(job.getString("EngineNumber"));
            vh.type.setText(job.getString("VehicleType"));
            final String insuranceStart=job.getString("InsuranceStartDate");
            final String insuranceEnd=job.getString("InsuranceEndDate");
            final String v_id=job.getString("Vid");
            final String a_id=job.getString("Aid");

            vh.btnEditVehicle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences.Editor sp =a.getSharedPreferences("admin_info",MODE_PRIVATE).edit();

                    sp.putString("editName",vh.name.getText().toString());
                    sp.putString("editVin",vh.vinNum.getText().toString());
                    sp.putString("editComp",vh.company.getText().toString());
                    sp.putString("editLicense",vh.licenseNum.getText().toString());
                    sp.putString("editEngNum",vh.engNum.getText().toString());
                    sp.putString("editType",vh.type.getText().toString());
                    sp.putString("editInsEnd",insuranceEnd);
                    sp.putString("editInsStart",insuranceStart);
                    sp.putString("v_id",v_id);
                    sp.putString("a_id",a_id);

                    sp.apply();
                    Intent i = new Intent(a,EditVehicleInfoActivity.class);
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
