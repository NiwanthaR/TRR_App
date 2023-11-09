package com.example.trr_app.adaptor

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
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

        holder.btnRemove.setOnClickListener(View.OnClickListener {

            val layoutInflater = LayoutInflater.from(holder.itemView.context)
            val view = layoutInflater.inflate(R.layout.custom_alert,null)
            val builder: AlertDialog.Builder = AlertDialog.Builder(context,R.style.CustomAlertDialog)
            builder.setTitle("Warning....!")
            builder.setMessage("Do you want to remove this user..?")
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
            })
            builder.setNegativeButton("Cancel", null)
            builder.show()
        })
    }



}