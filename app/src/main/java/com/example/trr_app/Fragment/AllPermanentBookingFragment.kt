package com.example.trr_app.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.adaptor.QuickBookingAdaptor
import com.example.trr_app.model.QuickBooking
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date

class AllPermanentBookingFragment: Fragment() {

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var allRecyclerView : RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_permanant_booking, container, false)

        //load recycler
        allRecyclerView = view.findViewById(R.id.allPermanentBookingRecycler)

        //load booking
        loadInCompleteBooking()

        // Inflate the layout for this fragment
        return view
    }

    private fun loadInCompleteBooking() {

        val date = getCurrentDate()

        val mChatDatabaseReference = firebaseDatabase.reference.child("TRRApp").child("Booking Details")
            .child("Appointment Reservation")
        val query = mChatDatabaseReference.ref.orderByChild("checkIn").startAfter(date).equalTo("bookingType","Permanent")
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                allRecyclerView!!.layoutManager = LinearLayoutManager(context)
                allRecyclerView!!.setHasFixedSize(true)
                val  quickBooking = ArrayList<QuickBooking>()
                if (snapshot!=null){
                    for (snapshot in snapshot.children){
                        val booking = snapshot.getValue(QuickBooking::class.java)
                        quickBooking.add(booking!!)
                    }
                    val adapter = QuickBookingAdaptor(context!!,quickBooking)
                    allRecyclerView!!.adapter = adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Snackbar.make(view!!,"Data loading failed.", Snackbar.LENGTH_SHORT).show()
                Log.d(this@AllPermanentBookingFragment.tag, "Data loading failed form firebase end")
            }
        })
    }

    private fun getCurrentDate():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }

}