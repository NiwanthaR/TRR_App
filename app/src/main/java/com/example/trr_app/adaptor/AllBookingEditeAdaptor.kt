package com.example.trr_app.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.model.QuickBooking
import com.example.trr_app.support.PermanentBookingEdite
import com.example.trr_app.support.QuickBookingEdite
import com.example.trr_app.view.ManageUI.CompleteQuickBooking
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class AllBookingEditeAdaptor(var context: Context, var items : ArrayList<QuickBooking>): RecyclerView.Adapter<AllBookingEditeAdaptor.ViewHolder>()  {

    class ViewHolder(row : View):RecyclerView.ViewHolder(row) {
        var dateRange: TextView? = null
        var customerName: TextView? = null
        var customerContact:TextView? = null
        var singleBookingUI:MaterialCardView? = null
        var bookingTypeIcon:ImageView? = null

        var btnedit:MaterialButton? = null
        var btncontinue:MaterialButton? = null

        //booking category
        var bookingCategoryCard : MaterialCardView? = null
        var bookingCategoryName : TextView? = null

        //bottom sheet
        var bottomSheet : QuickBookingEdite? = null
        var permanentBottomSheet : PermanentBookingEdite? = null

        var submitBtnLayout : LinearLayout? = null

        init {
            this.dateRange = row?.findViewById(R.id.ab_dateRange_txt)
            this.customerName = row?.findViewById(R.id.ab_customerName_txt)
            this.customerContact = row?.findViewById(R.id.ab_customerContact_txt)
            this.singleBookingUI = row?.findViewById(R.id.ab_quickBookingSingleUI)
            this.bookingTypeIcon = row.findViewById(R.id.img_bookingType_icon)

            //material
            this.bookingCategoryCard = row?.findViewById(R.id.ab_bookingCategoryCard)
            this.bookingCategoryName = row?.findViewById(R.id.ab_bookingCategoryText)

            //btn
            this.btnedit = row?.findViewById(R.id.ab_btn_edite)
            this.btncontinue = row?.findViewById(R.id.ab_btn_complete)

            //layout
            this.submitBtnLayout = row.findViewById(R.id.completeBtnLayout)
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.all_booking_single_unite, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var userDto = items[position]
        holder?.dateRange?.text = userDto.getBooking_dateRange()
        holder?.customerName?.text = userDto.getCustomerName()
        holder?.customerContact?.text = userDto.getCustomerContact()
        holder?.bookingCategoryName?.text = userDto.getBookingType()

        if (userDto.getCustomerContact().isNullOrEmpty()){
            holder?.customerContact?.text = userDto.getContact()
        }
        if (userDto.getCustomerName().isNullOrEmpty()){
            holder?.customerName?.text = userDto.getFirstName()+" "+userDto.getSecondName()
        }

        if (userDto.bookingType=="Permanent"){
//            holder.bookingCategoryCard?.setCardBackgroundColor(context.getColor(R.color.light_green))
//            holder.bookingCategoryCard?.strokeColor = context.getColor(R.color.light_green)
            holder.bookingTypeIcon?.setImageDrawable(context.getDrawable(R.drawable.icon_booking_green))
            holder.bookingCategoryName?.setTextColor(context.getColor(R.color.booking_category_permanent))
        }else{
//            holder.bookingCategoryCard?.setCardBackgroundColor(context.getColor(R.color.light_red))
//            holder.bookingCategoryCard?.strokeColor = context.getColor(R.color.light_red)
            holder.bookingTypeIcon?.setImageDrawable(context.getDrawable(R.drawable.icon_booking_red))
            holder.bookingCategoryName?.setTextColor(context.getColor(R.color.booking_category_temporary))
        }

        holder.btnedit?.setOnClickListener {
            if (userDto.bookingType != "Permanent") {
                holder.bottomSheet = QuickBookingEdite(userDto.getUniqueKey(),1)
                val fragmentManager = (context as FragmentActivity).supportFragmentManager
                holder.bottomSheet!!.show(fragmentManager, "ModalBottomSheet")
            }else{
                holder.permanentBottomSheet = PermanentBookingEdite(context,userDto.getUniqueKey())
                val fragmentManager = (context as FragmentActivity).supportFragmentManager
                holder.permanentBottomSheet!!.show(fragmentManager, "ModalBottomSheet")
            }
        }

        if (userDto.bookingType=="Permanent"){
            //holder.btncontinue?.visibility = View.GONE
            holder.submitBtnLayout?.visibility  = View.GONE
        }else{
            holder.btncontinue?.setOnClickListener {
                val intent = Intent(context, CompleteQuickBooking::class.java)
                intent.putExtra("BookingNumber", userDto.uniqueKey)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}