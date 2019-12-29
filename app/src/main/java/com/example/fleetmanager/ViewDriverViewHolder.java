package com.example.fleetmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/*
 * code by gurman and Rohit*/
public class ViewDriverViewHolder extends RecyclerView.ViewHolder {

    TextView name,mob,truck,dl;
    Button btnEdit;
    public ViewDriverViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.driverName);
        truck = (TextView) itemView.findViewById(R.id.tplate);
        dl = (TextView) itemView.findViewById(R.id.DlicenseN);
        mob = (TextView) itemView.findViewById(R.id.dmobb);
        btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
    }
}
