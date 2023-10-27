package com.example.trr_app.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trr_app.R;

public class BookingViewHolder extends RecyclerView.ViewHolder {

    public TextView bookingName,bookingType,bookingDate,bookingAddress,bookingContact,bookingSeeMore;
    public View v;
    public BookingViewHolder(@NonNull View itemView) {
        super(itemView);

        bookingName = itemView.findViewById(R.id.bookingPersonName);
        bookingType = itemView.findViewById(R.id.bookingType);
        bookingDate = itemView.findViewById(R.id.bookedDate);
        bookingAddress = itemView.findViewById(R.id.bookingAddress);
        bookingContact = itemView.findViewById(R.id.bookingContact);
        bookingSeeMore = itemView.findViewById(R.id.bookingSeeMore);

        v= itemView;

    }
}
