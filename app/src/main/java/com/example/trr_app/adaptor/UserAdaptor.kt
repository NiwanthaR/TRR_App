package com.example.trr_app.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.holders.UserViewHolder
import com.example.trr_app.model.User

class UserAdaptor(val items : ArrayList<User>, val context: Context) : RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.userName.text = items[position].userName
        holder.userType.text = items[position].userCategory
        holder.userEmail.text =items[position].userEmail
    }
}