package com.example.trr_app.view.ManageUI

import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.adaptor.QuickBookingAdaptor
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
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class QuickBookingActivity : BaseActivity(),OnClickListener {

    private val TAG: String
            = QuickBookingActivity::class.java.name
    val recyclerView: RecyclerView
        get() = findViewById(R.id.quickBookRecycle)
    private val view:RelativeLayout
        get() = findViewById(R.id.quickBookingMain)

    private var options: FirebaseRecyclerOptions<QuickBooking>? = null
    private var adapter: FirebaseRecyclerAdapter<QuickBooking, QuickBookViewHolder>? = null

    var databaseReference:DatabaseReference = firebaseDatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_booking)

        //loadQuickBookingData()
        loadInCompleteBooking()

    }
//    -------  Not working ------------------------------------------------------------------------------------------------------
//    private fun loadQuickBookingData() {
//
//        loadingProgressDialog(this)
//
//        databaseReference = firebaseDatabaseReference.child("Booking Details")
//            .child("Appointment Reservation")
//        val quary = databaseReference.ref
//
//        options = FirebaseRecyclerOptions.Builder<QuickBooking>().setQuery(quary,QuickBooking::class.java).build()
//
//        adapter = object : FirebaseRecyclerAdapter<QuickBooking,QuickBookViewHolder>(options!!){
//
//            override fun onBindViewHolder(
//                holder: QuickBookViewHolder,
//                position: Int,
//                model: QuickBooking
//            ) {
//                holder.dateRange.setText(model.booking_dateRange)
//                holder.customerName.setText(model.customerName)
//                holder.customerContact.setText(model.customerContact)
//
//                holder.view.setOnClickListener(OnClickListener {
//                    val intent = Intent(this@QuickBookingActivity,CompleteQuickBooking::class.java)
//                    //intent.putExtra("BookingNumber",)
//                })
//            }
//
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickBookViewHolder {
//                val v: View = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.single_quick_booking, parent, false)
//                return QuickBookViewHolder(v)
//            }
//
//        }
//
//        (adapter as FirebaseRecyclerAdapter<QuickBooking, QuickBookViewHolder>).startListening()
//        recyclerView.adapter = adapter
//
//        loadingDialogClose()
//    }

    private fun loadInCompleteBooking() {

        val date = getCurrentDate()
        loadingProgressDialog(this@QuickBookingActivity)

        val mChatDatabaseReference = firebaseDatabaseReference.child("Booking Details")
            .child("Appointment Reservation")
        val query = mChatDatabaseReference.ref.orderByChild("checkIn").startAfter(date)
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                recyclerView.layoutManager = LinearLayoutManager(this@QuickBookingActivity)
                recyclerView.setHasFixedSize(true)
                val  quickBooking = ArrayList<QuickBooking>()
                if (snapshot!=null){

                    for (snapshot in snapshot.children){
                        val booking = snapshot.getValue(QuickBooking::class.java)
                        quickBooking.add(booking!!)
                    }
                    val adapter = QuickBookingAdaptor(this@QuickBookingActivity,quickBooking)
                    recyclerView.adapter = adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Snackbar.make(view,"Data loading failed.",Snackbar.LENGTH_SHORT).show()
                Log.d(TAG, "Data loading failed form firebase end")
            }
        })

        loadingDialogClose()
    }


    override fun onClick(view: View) {
        when(view.id){

        }
    }

    fun getCurrentDate():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }
}