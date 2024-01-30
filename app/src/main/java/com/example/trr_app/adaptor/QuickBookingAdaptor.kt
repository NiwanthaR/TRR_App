package com.example.trr_app.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.model.QuickBooking
import com.example.trr_app.view.ManageUI.CompleteQuickBooking
import com.google.android.material.card.MaterialCardView

class QuickBookingAdaptor(var context: Context,var items : ArrayList<QuickBooking>):RecyclerView.Adapter<QuickBookingAdaptor.ViewHolder>() {
    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var dateRange: TextView? = null
        var customerName: TextView? = null
        var customerContact:TextView? = null
        var singleBookingUI:MaterialCardView? = null

        init {
            this.dateRange = row?.findViewById(R.id.dateRange_txt)
            this.customerName = row?.findViewById(R.id.customerName_txt)
            this.customerContact = row?.findViewById(R.id.customerContact_txt)
            this.singleBookingUI = row?.findViewById(R.id.quickBookingSingleUI)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.single_quick_booking, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var userDto = items[position]
        holder?.dateRange?.text = userDto.getBooking_dateRange()
        holder?.customerName?.text = userDto.getCustomerName()
        holder?.customerContact?.text = userDto.getCustomerContact()

        holder.singleBookingUI?.setOnClickListener {
            val intent = Intent(context, CompleteQuickBooking::class.java)
            intent.putExtra("BookingNumber", userDto.uniqueKey)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
