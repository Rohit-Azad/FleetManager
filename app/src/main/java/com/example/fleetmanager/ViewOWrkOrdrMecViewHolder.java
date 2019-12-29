package com.example.fleetmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewOWrkOrdrMecViewHolder extends RecyclerView.ViewHolder{

    TextView wId,make,model,licenseNum,type,vinNum,issue;

    public ViewOWrkOrdrMecViewHolder(@NonNull View itemView) {
        super(itemView);


        wId = (TextView) itemView.findViewById(R.id.wrkID);
        make = (TextView) itemView.findViewById(R.id.vmake);
        model = (TextView) itemView.findViewById(R.id.vmodel);
        licenseNum = (TextView) itemView.findViewById(R.id.lPlate);

        type = (TextView) itemView.findViewById(R.id.VType);

        vinNum = (TextView) itemView.findViewById(R.id.vinNum);

        issue = (TextView) itemView.findViewById(R.id.vIssue);
    }
}
