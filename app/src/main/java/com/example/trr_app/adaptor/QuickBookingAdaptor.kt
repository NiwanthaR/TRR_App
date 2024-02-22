package com.example.trr_app.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.model.QuickBooking
import com.example.trr_app.view.ManageUI.CompleteQuickBooking
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class QuickBookingAdaptor(var context: Context,var items : ArrayList<QuickBooking>):RecyclerView.Adapter<QuickBookingAdaptor.ViewHolder>() {
    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var dateRange: TextView? = null
        var customerName: TextView? = null
        var customerContact:TextView? = null
        var singleBookingUI:MaterialCardView? = null
        var bookingTypeIcon: ImageView? = null

        //booking category
        var bookingCategoryCard : MaterialCardView? = null
        var bookingCategoryName : TextView? = null
        var btnComplete : MaterialButton? = null
        var btnEdite : MaterialButton? = null
        var completeLayout : LinearLayout? = null
        var editeLayout: LinearLayout? = null

        init {
            this.dateRange = row?.findViewById(R.id.dateRange_txt)
            this.customerName = row?.findViewById(R.id.customerName_txt)
            this.customerContact = row?.findViewById(R.id.customerContact_txt)
            this.singleBookingUI = row?.findViewById(R.id.quickBookingSingleUI)
            this.bookingTypeIcon = row.findViewById(R.id.img_completeBooking_icon)

            //btn
            this.btnComplete = row.findViewById(R.id.btnCompleteQuick)
            this.btnEdite = row.findViewById(R.id.btnEditeQuickinSingle)

            //layout
            this.completeLayout = row.findViewById(R.id.btn_completeLayout)
            this.editeLayout = row.findViewById(R.id.btn_editeLayout)

            //material
            this.bookingCategoryCard = row?.findViewById(R.id.bookingCategoryCard)
            this.bookingCategoryName = row?.findViewById(R.id.bookingCategoryText)
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
        holder?.bookingCategoryName?.text = userDto.getBookingType()

        if (userDto.bookingType=="Permanent"){
//            holder.bookingCategoryCard?.setCardBackgroundColor(context.getColor(R.color.light_green))
//            holder.bookingCategoryCard?.strokeColor = context.getColor(R.color.light_green)
            holder.bookingTypeIcon?.setImageDrawable(context.getDrawable(R.drawable.icon_booking_green))
            holder.bookingCategoryName?.setTextColor(context.getColor(R.color.booking_category_permanent))
            holder.completeLayout?.visibility = View.GONE

        }else{
//            holder.bookingCategoryCard?.setCardBackgroundColor(context.getColor(R.color.light_red))
//            holder.bookingCategoryCard?.strokeColor = context.getColor(R.color.light_red)
            holder.bookingTypeIcon?.setImageDrawable(context.getDrawable(R.drawable.icon_booking_red))
            holder.bookingCategoryName?.setTextColor(context.getColor(R.color.booking_category_temporary))
            holder.editeLayout?.visibility = View.GONE
        }

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
