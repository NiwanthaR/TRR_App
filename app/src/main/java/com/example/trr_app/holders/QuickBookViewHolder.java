package com.example.trr_app.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trr_app.R;

public class QuickBookViewHolder extends RecyclerView.ViewHolder {

    public TextView dateRange,customerName,customerContact;
    public View view;
    public QuickBookViewHolder(@NonNull View itemView) {
        super(itemView);

        dateRange = itemView.findViewById(R.id.dateRange_txt);
        customerName = itemView.findViewById(R.id.customerName_txt);
        customerContact = itemView.findViewById(R.id.customerContact_txt);

        view = itemView;
    }
}
