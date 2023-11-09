package com.example.trr_app.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trr_app.R;
import com.google.android.material.button.MaterialButton;

public class UserViewHolder extends RecyclerView.ViewHolder{

    public TextView userName,userType,userEmail;
    public View v;
    public MaterialButton btnRemove,btnEdite;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        userName = itemView.findViewById(R.id.textUserName);
        userType = itemView.findViewById(R.id.userPosition);
        userEmail = itemView.findViewById(R.id.userLastLogin);

        btnRemove = itemView.findViewById(R.id.btn_removeUser);
        btnEdite = itemView.findViewById(R.id.btn_editeUser);


        v = itemView;
    }
}
