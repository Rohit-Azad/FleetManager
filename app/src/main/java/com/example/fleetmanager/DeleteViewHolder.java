package com.example.fleetmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeleteViewHolder extends RecyclerView.ViewHolder {

    TextView name,tv1,tv1Value,tv2,tv2Value;
    Button delete;
    // Button btnEditVehicle;
    public DeleteViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.tvName);
        tv1 = (TextView) itemView.findViewById(R.id.tv_1);
        tv1Value = (TextView) itemView.findViewById(R.id.tv_1_value);
        tv2 = (TextView) itemView.findViewById(R.id.tv_2);
        tv2Value = (TextView) itemView.findViewById(R.id.tv_2_value);
        delete = itemView.findViewById(R.id.btnDelete);
    }
}
