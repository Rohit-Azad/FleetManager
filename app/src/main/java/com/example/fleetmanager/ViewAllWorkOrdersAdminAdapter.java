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

public class ViewAllWorkOrdersAdminAdapter extends RecyclerView.Adapter<ViewAllWorkOrdersAdminHolder> {

        JSONArray jsarr;
        JSONObject job;

        // creating Activity variable globally
        Activity a;

public ViewAllWorkOrdersAdminAdapter(JSONArray jsarr , Activity a)
        {

        this.jsarr = jsarr;

        this.a = a;
        }
@NonNull
@Override
public ViewAllWorkOrdersAdminHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

    ViewAllWorkOrdersAdminHolder vd = new ViewAllWorkOrdersAdminHolder(LayoutInflater.from(a).inflate(R.layout.work_order_all,viewGroup,false));
        return vd;
        }

@Override
public void onBindViewHolder(@NonNull final ViewAllWorkOrdersAdminHolder vh, int position) {


        try {
        // iterating for each json object in json array
        job = (JSONObject) jsarr.get(position);

        // binding values from json object to cell layout via view holder

        vh.wId.setText(job.getString("Wid"));
        vh.make.setText(job.getString("VCompany"));
        vh.model.setText(job.getString("VehicleName"));
        vh.vinNum.setText(job.getString("VinNumber"));
        vh.type.setText(job.getString("VehicleType"));
        vh.licenseNum.setText(job.getString("LicensePlate"));
        vh.status.setText("Status: "+job.getString("Status"));
        final String date=job.getString("Date");
        final String d_id=job.getString("Did");
        final String status=job.getString("Status");

        vh.issue.setText(job.getString("Issue"));

        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor sp =a.getSharedPreferences("admin_info",MODE_PRIVATE).edit();

                sp.putString("editWid", vh.wId.getText().toString());
                sp.putString("editLicense", vh.licenseNum.getText().toString());
                sp.putString("editStatus", status);
                sp.putString("editIssue", vh.issue.getText().toString());
                sp.putString("editDate", date);
                sp.putString("dr_id", d_id);

                sp.apply();
                Intent i = new Intent(a,EditWorkOrderAdminActivity.class);
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
