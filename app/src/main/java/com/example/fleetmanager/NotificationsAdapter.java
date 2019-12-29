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

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsViewHolder> {

    JSONArray jsarr;
    JSONObject job;

    // creating Activity variable globally
    Activity a;

    public NotificationsAdapter(JSONArray jsarr , Activity a)
    {

        this.jsarr = jsarr;

        this.a = a;
    }
    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new NotificationsViewHolder(LayoutInflater.from(a).inflate(R.layout.notifications_admin,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(NotificationsViewHolder vh, int position) {
        try {
            // iterating for each json object in json array
            job = (JSONObject) jsarr.get(position);
            Log.d("vfkepofekrp", "onBindViewHolder: "+jsarr);
            // binding values from json object to cell layout via view holder

            vh.subject.setText("Subject: Insurance Due!!");
            vh.lPlate.setText(job.getString("LicensePlate"));
            vh.dueDate.setText(job.getString("InsuranceEndDate"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsarr.length();
    }
}

