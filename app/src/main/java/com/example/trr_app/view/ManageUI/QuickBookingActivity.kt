package com.example.trr_app.view.ManageUI

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.RelativeLayout
import androidx.core.util.Pair
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.holders.BookingViewHolder
import com.example.trr_app.model.RoomReserve
import com.example.trr_app.model.SubmitBooking
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class QuickBookingActivity : BaseActivity(),OnClickListener {

    private val TAG: String
            = ManageActivity::class.java.name

    //room flag
    private var roomFlag01 : Boolean = false
    private var roomFlag02 : Boolean = false
    private var roomFlag03 : Boolean = false
    private var roomFlag04 : Boolean = false
    private var roomFlag05 : Boolean = false
    private var roomFlag06 : Boolean = false
    private var roomFlag07 : Boolean = false

    //Material Card View
    private val rmCard01 : MaterialCardView
        get() = findViewById(R.id.rm01Card)
    private val rmCard02 : MaterialCardView
        get() = findViewById(R.id.rm02Card)
    private val rmCard03 : MaterialCardView
        get() = findViewById(R.id.rm03Card)
    private val rmCard04 : MaterialCardView
        get() = findViewById(R.id.rm04Card)
    private val rmCard05 : MaterialCardView
        get() = findViewById(R.id.rm05Card)
    private val rmCard06 : MaterialCardView
        get() = findViewById(R.id.rm06Card)
    private val rmCard07 : MaterialCardView
        get() = findViewById(R.id.rm07Card)

    private val searchDateInput : TextInputEditText
        get() = findViewById(R.id.tv_searchDates)

    //date string
    private var startDate:String? = null
    private var endDate:String? = null

    private val contentView : RelativeLayout
        get() = findViewById(R.id.quickBookLayout)

    private var options: FirebaseRecyclerOptions<SubmitBooking>? = null
    private var adapter: FirebaseRecyclerAdapter<SubmitBooking, BookingViewHolder>? = null

    private var databaseReference = firebaseDatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_booking)

        searchDateInput.setOnClickListener(this)

    }

    private fun showDatePicker(){

        loadingProgressDialog(this)

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val picker = MaterialDatePicker.Builder.dateRangePicker().setTheme(R.style.MaterialDatePickerTheme)
            .setTitleText("Select Check-in Check-out Date")
            .setSelection(
                Pair(
                    MaterialDatePicker.todayInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()+(1000*24*60*60))
            )
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        picker.show(this.supportFragmentManager,TAG)
        //close dialog
        loadingDialogClose()

        picker.addOnPositiveButtonClickListener {
            searchDateInput.setText(convertTimeToDate(it.first) +" - "+convertTimeToDate(it.second))
            startDate = convertTimeToDate(it.first)
            endDate = convertTimeToDate(it.second)

            if (startDate!=null && endDate!=null){
                loadBooking(startDate!!, endDate!!)
            }
        }
    }

    private fun loadBooking(startDate:String,endDate:String){
        databaseReference = firebaseDatabaseReference.child("Booking Details")
            .child("Appointment Reservation")

        var bookingList : ArrayList<SubmitBooking> = ArrayList<SubmitBooking>()
        var overLeapList : ArrayList<SubmitBooking> = ArrayList<SubmitBooking>()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clean list
                bookingList.clear()
                //load data
                for (postSnapshot in snapshot.children) {
                    val booking: SubmitBooking? = postSnapshot.getValue(SubmitBooking::class.java)
                    if (booking!=null){
                        Log.e(TAG, "Data loading success")
                        bookingList.add(booking)

                    }else{
                        Log.e(TAG, "Data loading failed. booking is empty")
                        Snackbar.make(contentView,R.string.booking_data_null, Snackbar.LENGTH_SHORT).show()
                    }
                }

                val listSize = bookingList.size

                for (i in 0..< listSize){
                    if (checkOverLeap(bookingList[i].checkIn,bookingList[i].checkOut)){
                        Log.d(TAG, "Date Overlapped add to array")
                        overLeapList.add(bookingList[i])
                    }else{
                        Log.e(TAG, "Date Overlapped isnt add array")
                    }
                }

                //send to check available rooms
                checkAvailabilityRooms(overLeapList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.message)
            }
        })
    }

    private fun checkAvailabilityRooms(overLapList: ArrayList<SubmitBooking>){
        val roomBookingList : ArrayList<RoomReserve> = ArrayList()
        val listSize = overLapList.size

        if (listSize!=0){
            for (i in 0..<listSize){
                roomBookingList.add(overLapList[i].roomReserve)
            }

            for (i in 0..<listSize){
                if (roomBookingList[i].room01==true){
                    roomFlag01 = true
                }else if (roomBookingList[i].room02==true){
                    roomFlag02 = true
                }else if (roomBookingList[i].room03==true){
                    roomFlag03 = true
                }else if (roomBookingList[i].room04==true){
                    roomFlag04 = true
                }else if (roomBookingList[i].room05==true){
                    roomFlag05 = true
                }else if (roomBookingList[i].room06==true){
                    roomFlag06 = true
                }else if (roomBookingList[i].room07==true){
                    roomFlag07 = true
                }
            }

            setAvailability()
        }
    }

    private fun setAvailability(){
        if (roomFlag01){
            rmCard01.visibility = View.GONE
        }else{
            rmCard01.visibility = View.VISIBLE
        }
        if (roomFlag02){
            rmCard02.visibility = View.GONE
        }else{
            rmCard02.visibility = View.VISIBLE
        }
        if (roomFlag03){
            rmCard03.visibility = View.GONE
        }else{
            rmCard03.visibility = View.VISIBLE
        }
        if (roomFlag04){
            rmCard04.visibility = View.GONE
        }else{
            rmCard04.visibility = View.VISIBLE
        }
        if (roomFlag05){
            rmCard05.visibility = View.GONE
        }else{
            rmCard05.visibility = View.VISIBLE
        }
        if (roomFlag06){
            rmCard06.visibility = View.GONE
        }else{
            rmCard06.visibility = View.VISIBLE
        }
        if (roomFlag07){
            rmCard07.visibility = View.GONE
        }else{
            rmCard07.visibility = View.VISIBLE
        }
    }

    private fun checkOverLeap(reserveStartDate:String,reserveEndDate:String):Boolean{
        val s = SimpleDateFormat("yyyy-MM-dd")
//        val s1 = s.parse(startDate!!)
//        val e1 = s.parse(reserveStartDate)
//        val s2 = s.parse(endDate!!)
//        val e2 = s.parse(reserveEndDate)

        val s1 = s.parse(reserveStartDate)
        val e1 = s.parse(startDate!!)
        val s2 = s.parse(reserveEndDate)
        val e2 = s.parse(endDate!!)

        print("Start date " + s.format(s1))
        println("  End date " + s.format(e1))

        print("Start date " + s.format(s2))
        println("  End date " + s.format(e2))
        if (s1.compareTo(s2) < 0 && e1.compareTo(s2) > 0 || s1.compareTo(e2) < 0 && e1.compareTo(e2) > 0 || s1.compareTo(
                s2
            ) < 0 && e1.compareTo(e2) > 0 || s1.compareTo(s2) > 0 && e1.compareTo(e2) < 0
        ) {
            println("They don't overlap")
            Log.d(TAG, "They don't overlap")
            return false
        } else {
            print("They overlap")
            Log.e(TAG, "They overlap")
            return true
        }

    }

    private fun convertTimeToDate(time:Long): String{
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = time
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(utc.time)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.tv_searchDates -> showDatePicker()
        }
    }

}