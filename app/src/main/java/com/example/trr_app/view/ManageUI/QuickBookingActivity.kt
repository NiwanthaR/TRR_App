package com.example.trr_app.view.ManageUI

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.util.Pair
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.holders.BookingViewHolder
import com.example.trr_app.model.RoomDetails
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
import com.google.firebase.database.ktx.getValue
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

    //room name
    private val roomName01 : TextView
        get() = findViewById(R.id.roomName1)
    private val roomType01 : TextView
        get() = findViewById(R.id.roomType01)
    private val roomCapacity01 : TextView
        get() = findViewById(R.id.roomCapacity01)
    private val roomCost01 : TextView
        get() = findViewById(R.id.roomCost01)

    private val roomName02 : TextView
        get() = findViewById(R.id.roomName2)
    private val roomType02 : TextView
        get() = findViewById(R.id.roomType02)
    private val roomCapacity02 : TextView
        get() = findViewById(R.id.roomCapacity02)
    private val roomCost02 : TextView
        get() = findViewById(R.id.roomCost02)

    private val roomName03 : TextView
        get() = findViewById(R.id.roomName3)
    private val roomType03 : TextView
        get() = findViewById(R.id.roomType03)
    private val roomCapacity03 : TextView
        get() = findViewById(R.id.roomCapacity03)
    private val roomCost03 : TextView
        get() = findViewById(R.id.roomCost03)

    private val roomName04 : TextView
        get() = findViewById(R.id.roomName4)
    private val roomType04 : TextView
        get() = findViewById(R.id.roomType04)
    private val roomCapacity04 : TextView
        get() = findViewById(R.id.roomCapacity04)
    private val roomCost04 : TextView
        get() = findViewById(R.id.roomCost04)

    private val roomName05 : TextView
        get() = findViewById(R.id.roomName5)
    private val roomType05 : TextView
        get() = findViewById(R.id.roomType05)
    private val roomCapacity05 : TextView
        get() = findViewById(R.id.roomCapacity05)
    private val roomCost05 : TextView
        get() = findViewById(R.id.roomCost05)

    private val roomName06 : TextView
        get() = findViewById(R.id.roomName6)
    private val roomType06 : TextView
        get() = findViewById(R.id.roomType06)
    private val roomCapacity06 : TextView
        get() = findViewById(R.id.roomCapacity06)
    private val roomCost06 : TextView
        get() = findViewById(R.id.roomCost06)

    private val roomName07 : TextView
        get() = findViewById(R.id.roomName7)
    private val roomType07 : TextView
        get() = findViewById(R.id.roomType07)
    private val roomCapacity07 : TextView
        get() = findViewById(R.id.roomCapacity07)
    private val roomCost07 : TextView
        get() = findViewById(R.id.roomCost07)

    private val roomLoadLayout : LinearLayout
        get() = findViewById(R.id.roomLoadingLayout)

    //date string
    private var startDate:String? = null
    private var endDate:String? = null

    private val contentView : RelativeLayout
        get() = findViewById(R.id.quickBookingGuid)

    private var options: FirebaseRecyclerOptions<SubmitBooking>? = null
    private var adapter: FirebaseRecyclerAdapter<SubmitBooking, BookingViewHolder>? = null

    private var databaseReference = firebaseDatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_booking)

        searchDateInput.setOnClickListener(this)

        //load room data
        loadRoomData()
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

    private fun loadRoomData(){
        //database reference
        databaseReference = firebaseDatabaseReference.child("Room Details").child("Room Profile")

        val roomList : ArrayList<RoomDetails> = ArrayList()

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clean array
                roomList.clear()

                for(postSnapshot in snapshot.children){
                    val roomDetails:RoomDetails? = postSnapshot.getValue(RoomDetails::class.java)
                    if (roomDetails!=null){
                        Log.e(TAG, "Data loading success")
                        roomList.add(roomDetails)
                    }else{
                        Log.e(TAG, "Data loading failed. room is empty")
                    }
                }

                val roomListSize = roomList.size
                if (roomListSize==7){
                    setData(roomList)
                }else{
                    Log.e(TAG, "Data loading failed. room data is empty")
                    Snackbar.make(contentView,R.string.booking_data_null, Snackbar.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun setData(roomList: ArrayList<RoomDetails>){
        roomName01.text = roomList[0].room_name
        roomName02.text = roomList[1].room_name
        roomName03.text = roomList[2].room_name
        roomName04.text = roomList[3].room_name
        roomName05.text = roomList[4].room_name
        roomName06.text = roomList[5].room_name
        roomName07.text = roomList[6].room_name

        roomCapacity01.text = roomList[0].room_capacity
        roomCapacity02.text = roomList[1].room_capacity
        roomCapacity03.text = roomList[2].room_capacity
        roomCapacity04.text = roomList[3].room_capacity
        roomCapacity05.text = roomList[4].room_capacity
        roomCapacity06.text = roomList[5].room_capacity
        roomCapacity07.text = roomList[6].room_capacity

        roomCost01.text = roomList[0].room_cost
        roomCost02.text = roomList[1].room_cost
        roomCost03.text = roomList[2].room_cost
        roomCost04.text = roomList[3].room_cost
        roomCost05.text = roomList[4].room_cost
        roomCost06.text = roomList[5].room_cost
        roomCost07.text = roomList[6].room_cost

        roomType01.text = roomList[0].room_condition
        roomType02.text = roomList[1].room_condition
        roomType03.text = roomList[2].room_condition
        roomType04.text = roomList[3].room_condition
        roomType05.text = roomList[4].room_condition
        roomType06.text = roomList[5].room_condition
        roomType07.text = roomList[6].room_condition
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
            roomLoadLayout.visibility = View.GONE
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