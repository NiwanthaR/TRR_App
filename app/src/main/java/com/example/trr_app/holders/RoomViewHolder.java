package com.example.trr_app.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trr_app.R;
import com.google.android.material.card.MaterialCardView;

public class RoomViewHolder extends RecyclerView.ViewHolder{

    public TextView roomName,roomType,roomCapacity,roomCost;
    public View view;
    public MaterialCardView roomView;
    public LinearLayout alreadyLayout;
    public RelativeLayout roomLayout;
    public ImageView navigateArrow,doneIcon;
    public RoomViewHolder(@NonNull View itemView) {
        super(itemView);
        roomName = itemView.findViewById(R.id.quickRMName);
        roomType = itemView.findViewById(R.id.quickRMtype);
        roomCapacity = itemView.findViewById(R.id.quickRMcapacity);
        roomCost = itemView.findViewById(R.id.quickRMcost);

        navigateArrow = itemView.findViewById(R.id.navigateIcon);
        doneIcon = itemView.findViewById(R.id.doneIcon);
        roomView = itemView.findViewById(R.id.rmCard);

        alreadyLayout = itemView.findViewById(R.id.alreadyBookLayout);
        roomLayout = itemView.findViewById(R.id.roomLayout);

        view = itemView;
    }
}
