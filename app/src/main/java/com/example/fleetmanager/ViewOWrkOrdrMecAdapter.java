package com.example.fleetmanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewOWrkOrdrMecAdapter extends RecyclerView.Adapter<ViewOWrkOrdrMecViewHolder> {

    JSONArray jsarr;
    JSONObject job;

    // creating Activity variable globally
    Activity a;

    public ViewOWrkOrdrMecAdapter(JSONArray jsarr , Activity a)
    {

        this.jsarr = jsarr;

        this.a = a;
    }
    @NonNull
    @Override
    public ViewOWrkOrdrMecViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewOWrkOrdrMecViewHolder vd = new ViewOWrkOrdrMecViewHolder(LayoutInflater.from(a).inflate(R.layout.open_work_cell,viewGroup,false));
        return vd;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOWrkOrdrMecViewHolder viewOWrkOrdrMecViewHolder, int position) {


        try {
            // iterating for each json object in json array
            job = (JSONObject) jsarr.get(position);

            // binding values from json object to cell layout via view holder

            viewOWrkOrdrMecViewHolder.wId.setText(job.getString("Wid"));
            viewOWrkOrdrMecViewHolder.make.setText(job.getString("VCompany"));
            viewOWrkOrdrMecViewHolder.model.setText(job.getString("VehicleName"));
            viewOWrkOrdrMecViewHolder.vinNum.setText(job.getString("VinNumber"));
            viewOWrkOrdrMecViewHolder.type.setText(job.getString("VehicleType"));
            viewOWrkOrdrMecViewHolder.licenseNum.setText(job.getString("LicensePlate"));

            viewOWrkOrdrMecViewHolder.issue.setText(job.getString("Issue"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public int getItemCount() {
        return jsarr.length();
    }
}


