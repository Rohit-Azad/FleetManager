package com.example.fleetmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewAllWorkOrdersAdminHolder extends RecyclerView.ViewHolder {

    TextView wId,make,model,licenseNum,type,vinNum,issue,status;
    Button btnEdit;

    public ViewAllWorkOrdersAdminHolder(@NonNull View itemView) {
        super(itemView);

        status=itemView.findViewById(R.id.tvStatus);
        wId = (TextView) itemView.findViewById(R.id.wrkID);
        make = (TextView) itemView.findViewById(R.id.vmake);
        model = (TextView) itemView.findViewById(R.id.vmodel);
        licenseNum = (TextView) itemView.findViewById(R.id.lPlate);

        type = (TextView) itemView.findViewById(R.id.VType);

        vinNum = (TextView) itemView.findViewById(R.id.vinNum);

        issue = (TextView) itemView.findViewById(R.id.vIssue);

        btnEdit=itemView.findViewById(R.id.btnEdit);
    }
}
