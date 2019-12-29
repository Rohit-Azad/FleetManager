package com.example.fleetmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewOrderedPartsHolder extends RecyclerView.ViewHolder {

    TextView opId, orderedParts, licensePlate, company, total, date, invoice;

    Button edit;

    public ViewOrderedPartsHolder(@NonNull View itemView) {
        super(itemView);

        opId = (TextView) itemView.findViewById(R.id.tvOrderID);
        orderedParts = (TextView) itemView.findViewById(R.id.tvOrderedParts);
        licensePlate = (TextView) itemView.findViewById(R.id.tvLPlate);
        company = (TextView) itemView.findViewById(R.id.tvCompany);
        total = (TextView) itemView.findViewById(R.id.tvTotal);
        date = (TextView) itemView.findViewById(R.id.tvDate);
        invoice = (TextView) itemView.findViewById(R.id.tvInvoice);

        edit=itemView.findViewById(R.id.btnEdit);
    }
}
