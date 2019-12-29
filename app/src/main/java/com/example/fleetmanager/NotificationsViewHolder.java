package com.example.fleetmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotificationsViewHolder extends RecyclerView.ViewHolder {

    TextView subject,lPlate,dueDate;
   // Button btnEditVehicle;
    public NotificationsViewHolder(@NonNull View itemView) {
        super(itemView);
        subject = (TextView) itemView.findViewById(R.id.txtSubject);
        lPlate = (TextView) itemView.findViewById(R.id.txtPlate);
        dueDate = (TextView) itemView.findViewById(R.id.txtDate);
    }
}
