package com.example.trr_app.view.ManageUI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.holders.BookingViewHolder
import com.example.trr_app.holders.QuickBookViewHolder
import com.example.trr_app.model.QuickBooking
import com.example.trr_app.model.RoomDetails
import com.example.trr_app.model.RoomReserve
import com.example.trr_app.model.SubmitBooking
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class QuickBookingActivity : BaseActivity(),OnClickListener {

    private val TAG: String
            = QuickBookingActivity::class.java.name
    val recyclerView: RecyclerView
        get() = findViewById(R.id.quickBookRecycle)

    private var options: FirebaseRecyclerOptions<QuickBooking>? = null
    private var adapter: FirebaseRecyclerAdapter<QuickBooking, QuickBookViewHolder>? = null

    var databaseReference:DatabaseReference = firebaseDatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_booking)

        loadQuickBookingData()


    }

    private fun loadQuickBookingData() {

        loadingProgressDialog(this)

        databaseReference = firebaseDatabaseReference.child("Booking Details").child("Appointment Reservation")
        val quary = databaseReference.ref.equalTo("bookingType","Temporary")

        options = FirebaseRecyclerOptions.Builder<QuickBooking>().setQuery(quary,QuickBooking::class.java).build()

        adapter = object : FirebaseRecyclerAdapter<QuickBooking,QuickBookViewHolder>(options!!){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickBookViewHolder {
                val v: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.single_quick_booking, parent, false)
                return QuickBookViewHolder(v)
            }

            override fun onBindViewHolder(
                holder: QuickBookViewHolder,
                position: Int,
                model: QuickBooking
            ) {
                holder.dateRange.setText(model.bookingDateRange)
                holder.customerName.setText(model.customerName)
                holder.customerContact.setText(model.customerContact)

                holder.view.setOnClickListener(OnClickListener {

                })
            }

        }

        (adapter as FirebaseRecyclerAdapter<QuickBooking, QuickBookViewHolder>).startListening()
        recyclerView.adapter = adapter

        loadingDialogClose()
    }


    override fun onClick(view: View) {
        when(view.id){

        }
    }


}