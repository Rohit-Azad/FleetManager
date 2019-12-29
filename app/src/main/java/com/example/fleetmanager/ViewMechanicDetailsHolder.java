package com.example.fleetmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewMechanicDetailsHolder extends RecyclerView.ViewHolder {

    TextView name,mob,email,username;
    Button btnEdit;
    public ViewMechanicDetailsHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.tvName);
        username = (TextView) itemView.findViewById(R.id.tvUsername);
        email = (TextView) itemView.findViewById(R.id.tvEmail);
        mob = (TextView) itemView.findViewById(R.id.tvMobile);
        btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
    }
}