package com.example.fleetmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class Drvr_ViewWrkO_ViewHolder extends RecyclerView.ViewHolder{


    TextView Id,mke,modl,licnseNum,vehicltype,vinNumber,dissue;






    public Drvr_ViewWrkO_ViewHolder(@NonNull View itemView) {
        super(itemView);


        Id = (TextView) itemView.findViewById(R.id.wrkOID);
        mke = (TextView) itemView.findViewById(R.id.Vemake);
        modl = (TextView) itemView.findViewById(R.id.Vemodel);
        licnseNum = (TextView) itemView.findViewById(R.id.lcnPlate);

        vehicltype = (TextView) itemView.findViewById(R.id.VehclType);

        vinNumber = (TextView) itemView.findViewById(R.id.tvVinNumber);

        dissue = (TextView) itemView.findViewById(R.id.DIssue);
    }
}
