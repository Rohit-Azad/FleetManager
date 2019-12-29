package com.example.fleetmanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Drvr_ViewWrkO_Adapter extends RecyclerView.Adapter<Drvr_ViewWrkO_ViewHolder> {

    JSONArray jsarr;
    JSONObject job;

    // creating Activity variable globally
    Activity a;

    public Drvr_ViewWrkO_Adapter(JSONArray jsarr , Activity a)
    {

        this.jsarr = jsarr;

        this.a = a;
    }
    @NonNull
    @Override
    public Drvr_ViewWrkO_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new Drvr_ViewWrkO_ViewHolder(LayoutInflater.from(a).inflate(R.layout.drvrworkorder_cell,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Drvr_ViewWrkO_ViewHolder viewOWrkOrdrMecViewHolder, int position) {


        try {
            // iterating for each json object in json array
            job = (JSONObject) jsarr.get(position);

            Log.d("vcdfferfew", "onBindViewHolder: "+job);

            // binding values from json object to cell layout via view holder

            viewOWrkOrdrMecViewHolder.Id.setText(job.getString("Wid"));
            viewOWrkOrdrMecViewHolder.mke.setText(job.getString("VCompany"));
            viewOWrkOrdrMecViewHolder.modl.setText(job.getString("VehicleName"));
            viewOWrkOrdrMecViewHolder.vinNumber.setText(job.getString("VinNumber"));
            viewOWrkOrdrMecViewHolder.vehicltype.setText(job.getString("VehicleType"));
            viewOWrkOrdrMecViewHolder.licnseNum.setText(job.getString("LicensePlate"));

            viewOWrkOrdrMecViewHolder.dissue.setText(job.getString("Issue"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public int getItemCount() {
        return jsarr.length();
    }
}


