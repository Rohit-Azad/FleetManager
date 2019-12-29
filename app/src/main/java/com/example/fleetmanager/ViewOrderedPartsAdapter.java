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

public class ViewOrderedPartsAdapter extends RecyclerView.Adapter<ViewOrderedPartsHolder> {

        JSONArray jsarr;
        JSONObject job;
        // creating Activity variable globally
        Activity a;

    public ViewOrderedPartsAdapter(JSONArray jsarr , Activity a)
    {

        this.jsarr = jsarr;

        this.a = a;
    }
    @NonNull
    @Override
    public ViewOrderedPartsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            ViewOrderedPartsHolder vd = new ViewOrderedPartsHolder(LayoutInflater.from(a).inflate(R.layout.ordered_parts,viewGroup,false));
            return vd;
            }

    @Override
    public void onBindViewHolder(@NonNull final ViewOrderedPartsHolder vh, int position) {
            try {
            // iterating for each json object in json array
            job = (JSONObject) jsarr.get(position);

            // binding values from json object to cell layout via view holder
            vh.opId.setText(job.getString("Opid"));
            vh.orderedParts.setText(job.getString("PartsOrdered"));
            vh.invoice.setText("Invoice#: "+job.getString("Invoice"));
            vh.company.setText(job.getString("Company"));
            vh.total.setText(job.getString("Total"));
            vh.licensePlate.setText(job.getString("LicensePlate"));
            vh.date.setText(job.getString("Date"));

            final String invoiceSt=job.getString("Invoice");
                final String mId=job.getString("Mid");
                final String vId=job.getString("Vid");
                vh.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SharedPreferences.Editor sp =a.getSharedPreferences("admin_info",MODE_PRIVATE).edit();

                        sp.putString("opId", vh.opId.getText().toString());
                        sp.putString("orderedParts", vh.orderedParts.getText().toString());
                        sp.putString("invoice", invoiceSt);
                        sp.putString("company", vh.company.getText().toString());
                        sp.putString("licensePlate", vh.licensePlate.getText().toString());
                        sp.putString("date", vh.date.getText().toString());
                        sp.putString("total", vh.total.getText().toString());
                        sp.putString("m_id", mId);
                        sp.putString("v_id", vId);


                        sp.apply();
                        Intent i = new Intent(a,EditOrderedPartsActivity.class);
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


