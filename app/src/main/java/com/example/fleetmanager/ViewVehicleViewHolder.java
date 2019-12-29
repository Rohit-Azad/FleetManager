package com.example.fleetmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewVehicleViewHolder extends RecyclerView.ViewHolder{


    TextView type,company,name,licenseNum,engNum,vinNum;
    Button btnEditVehicle;
    public ViewVehicleViewHolder(@NonNull View itemView) {
        super(itemView);
        type = (TextView) itemView.findViewById(R.id.vType);
        company = (TextView) itemView.findViewById(R.id.vCompany);
        name = (TextView) itemView.findViewById(R.id.vName);
        licenseNum = (TextView) itemView.findViewById(R.id.lPlate);

        engNum = (TextView) itemView.findViewById(R.id.engineNum);

        vinNum = (TextView) itemView.findViewById(R.id.vinNum);
        btnEditVehicle=(Button) itemView.findViewById(R.id.btnEditVehicle);
    }
}
