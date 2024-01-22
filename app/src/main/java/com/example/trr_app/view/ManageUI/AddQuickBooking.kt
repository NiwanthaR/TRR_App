package com.example.trr_app.view.ManageUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.NonNull
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.adaptor.RoomAdaptor
import com.example.trr_app.adaptor.UserAdaptor
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.holders.BookingViewHolder
import com.example.trr_app.holders.RoomViewHolder
import com.example.trr_app.model.QuickBooking
import com.example.trr_app.model.Room
import com.example.trr_app.model.RoomDetails
import com.example.trr_app.model.RoomReserve
import com.example.trr_app.model.SubmitBooking
import com.example.trr_app.model.User
import com.example.trr_app.support.QuickBookingDialog
import com.example.trr_app.view.Dashboard
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class AddQuickBooking : BaseActivity(),OnClickListener {

    //date range input
    private val dateRangeInput : TextInputEditText
        get() = findViewById(R.id.et_quickSearchDates)
    private val roomRecyclerView : RecyclerView
        get() = findViewById(R.id.quickRoomList)

    private val addNewQuickBook : ExtendedFloatingActionButton
        get() = findViewById(R.id.addNewQuickBooking)

    //TAG Name
    private val TAG: String = BaseActivity::class.java.name

    private val contentView : RelativeLayout
        get() = findViewById(R.id.addQuickBookingLayoutContent)

    //database Reference
    private var bookingDatabaseReference  = firebaseDatabaseReference
    private var roomDatabaseReference = firebaseDatabaseReference

    //recycler Option
    private var firebaseRecyclerOptions : FirebaseRecyclerOptions<RoomDetails>? = null
    private var adapter: FirebaseRecyclerAdapter<RoomDetails, RoomViewHolder>? = null

    //database reference
    private var databaseReference : DatabaseReference = firebaseDatabaseReference

    private var roomAdaptor : RoomAdaptor? = null

    private var bookingList : ArrayList<SubmitBooking> = ArrayList<SubmitBooking>()
    private var overLeapList : ArrayList<SubmitBooking> = ArrayList<SubmitBooking>()

    //dates
    private var startDate : String? =null
    private var endDate : String? =null
    private var today : String? =null
    private var selectDateRange : String? = null

    //room list
    val roomList : ArrayList<Room> = ArrayList()
    val bookedList = ArrayList<String>()

    var selectedRoomList : ArrayList<Room> = ArrayList()


    //room flag
    private var R001 : Boolean = false
    private var R002 : Boolean = false
    private var R003 : Boolean = false
    private var R004 : Boolean = false
    private var R005 : Boolean = false
    private var R006 : Boolean = false
    private var R007 : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quick_booking)

       //load
        loadEssentialData()
        //set Onclick
        dateRangeInput.setOnClickListener(this)
        addNewQuickBook.setOnClickListener(this)
    }

    private fun loadEssentialData(){
        //load room
        //loadDataRoom()
        //hide floating
        //addNewQuickBook.isVisible = false

        Snackbar.make(contentView, R.string.booking_successfully, Snackbar.LENGTH_SHORT).show()
        startActivity(Intent(this,Dashboard::class.java))
    }

    fun showBookingBtn(show:Boolean){
        addNewQuickBook.isVisible = show
    }

    private fun loadDataRoom(){
        //start loading
        loadingProgressDialog(this)

        roomDatabaseReference = firebaseDatabaseReference.child("Room Details")
            .child("Room Profile")

        roomDatabaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clean room
                roomList.clear()
                for (postSnapshot in snapshot.children){
                    val room: Room? = postSnapshot.getValue(Room::class.java)
                    //loadData
                    if (room!=null){
                        roomList.add(room)
                        Log.e(TAG, "room is added")
                    }else{
                        Log.e(TAG, "Data loading failed. room is empty")
                    }
                }

                val roomListSize = roomList.size

                if (roomListSize!=0){
                    roomRecyclerView.layoutManager = LinearLayoutManager(this@AddQuickBooking)
                    val emptyRoomList = RoomReserve(false,false,false,false,false,false,false)
                    // Access the RecyclerView Adapter and load the data into it
                    bookedList.add("empty")
                    roomAdaptor = RoomAdaptor(roomList, this@AddQuickBooking,bookedList){ show->showBookingBtn(show)}
                    roomRecyclerView.adapter = roomAdaptor
                }

                //dismiss
                loadingDialogClose()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Data loading failed. room is empty"+error.details)
            }

        })

    }

    private fun getSelectRooms():RoomReserve?{

        //roomAdaptor = RoomAdaptor(roomList, this@AddQuickBooking,bookedList){ show->showBookingBtn(show)}
        //clear
        clearRM()
        if (roomAdaptor!=null){

            val selectItem = roomAdaptor!!.getSelectedList()
            println(selectItem)

            //navigate to Quick Booking
            val count = selectItem.size
            val roomList = ArrayList<String>()

            for (i in 0..<count) {
                roomList.add(selectItem[i].room_unic_code)
            }

            for (i in 0..<count) {
                if ( roomList[i]=="R001"){
                    R001 = true
                    roomList.removeAt(i)
                }
                if ( roomList[i]=="R002"){
                    R002 = true
                    roomList.removeAt(i)
                }
                if ( roomList[i]=="R003"){
                    R003 = true
                    roomList.removeAt(i)
                }
                if ( roomList[i]=="R004"){
                    R004 = true
                    roomList.removeAt(i)
                }
                if ( roomList[i]=="R005"){
                    R005 = true
                    roomList.removeAt(i)
                }
                if ( roomList[i]=="R006"){
                    R006 = true
                    roomList.removeAt(i)
                }
                if ( roomList[i]=="R007"){
                    R007 = true
                    roomList.removeAt(i)
                }
            }
            return RoomReserve(R001,R002,R003,R004,R005,R006,R007)
            println("X")
        }else{
            return RoomReserve(false,false,false,false,false,false,false)
            println("y")
        }
    }

    private fun clearRM(){
        R001=false
        R002=false
        R003=false
        R004=false
        R005=false
        R006=false
        R007=false
    }
    private fun loadDataBooking(startDate :String, endDate:String,toDay:String){

        //load progress dialog
        loadingProgressDialog(this)

        bookingDatabaseReference = firebaseDatabaseReference.child("Booking Details").child("Appointment Reservation")

        val query:Query = bookingDatabaseReference.ref.orderByChild("checkIn").startAt(toDay)

        query.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
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
                    if (checkOverLeapOrNot(startDate,endDate,bookingList[i].checkIn,bookingList[i].checkOut)){
                        Log.d(TAG, "Date Overlapped add to array")
                        overLeapList.add(bookingList[i])
                    }else{
                        Log.e(TAG, "Date Overlapped isnt add array")
                    }
                }

                setAvailability(overLeapList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //(adapter as FirebaseRecyclerAdapter<RoomDetails, RoomViewHolder>).startListening()
        //roomRecyclerView.adapter = adapter

        //close progress dialog
        loadingDialogClose()

    }

    private fun setAvailability(overLeapList : ArrayList<SubmitBooking>){
        val s = overLeapList

        val roomBookingList : ArrayList<RoomReserve> = ArrayList()
        val listSize = overLeapList.size

        if (listSize!=0) {
            for (i in 0..<listSize) {
                roomBookingList.add(overLeapList[i].roomReserve)
            }

            //clear list
            bookedList.clear()

            for (i in 0..<listSize) {
                if (roomBookingList[i].room01 == true) {
                    R001 = true
                    bookedList.add("R001")
                }
                if (roomBookingList[i].room02 == true) {
                    R002 = true
                    bookedList.add("R002")
                }
                if (roomBookingList[i].room03 == true) {
                    R003 = true
                    bookedList.add("R003")
                }
                if (roomBookingList[i].room04 == true) {
                    R004 = true
                    bookedList.add("R004")
                }
                if (roomBookingList[i].room05 == true) {
                    R005 = true
                    bookedList.add("R005")
                }
                if (roomBookingList[i].room06 == true) {
                    R006 = true
                    bookedList.add("R006")
                }
                if (roomBookingList[i].room07 == true) {
                    R007 = true
                    bookedList.add("R007")
                }
            }

            //getDisplay Tabs
            if (roomList.size!=0){
                roomRecyclerView.layoutManager = LinearLayoutManager(this@AddQuickBooking)
                roomAdaptor = RoomAdaptor(roomList, this@AddQuickBooking,bookedList){ show->showBookingBtn(show)}
                roomRecyclerView.adapter = roomAdaptor
            }
        }
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
            dateRangeInput.setText(convertTimesToDates(it.first) +" - "+convertTimesToDates(it.second))
            //selectDateRange = convertTimesToDates(it.first) +" - "+convertTimesToDates(it.second)
            startDate = convertTimesToDates(it.first)
            endDate = convertTimesToDates(it.second)
            today = convertTimesToDates(MaterialDatePicker.todayInUtcMilliseconds())

            if (startDate!=null && endDate!=null && today!=null){
                loadDataBooking(startDate!!, endDate!!, today!!)
            }else{
                Log.e(TAG, "Date details not correctly full fill")
                Snackbar.make(contentView,R.string.dateNotFixed, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.et_quickSearchDates -> showDatePicker()
            R.id.addNewQuickBooking -> showBottomDialog()
        }
    }

    fun captureQuickBookData(name:String?,contact:String?,headCount:String?,specialNote:String?,room:RoomReserve?,startDate: String?,endDate:String?,dateRange:String?){
        //get unique que
        val postUniqueKey = firebaseDatabaseReference.push().key.toString()
        Log.d(TAG, "Post Key : $postUniqueKey")

        //set path
        databaseReference = firebaseDatabaseReference.child("Booking Details").child("Appointment Reservation")
            .child(postUniqueKey)

        if (room!=null){
            //saveData
            databaseReference.setValue(QuickBooking(name,dateRange,startDate,endDate,contact,headCount,specialNote,"Temporary",room))
                .addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        //Snackbar.make(contentView, R.string.booking_successfully, Snackbar.LENGTH_SHORT)
                           // .show()

                        //reset page
                        loadEssentialData()

                    }else{
                        Log.e(TAG, "Data upload not success.")
                        Snackbar.make(contentView,R.string.data_upload_failed, Snackbar.LENGTH_SHORT)
                            .show()
                    }

                }.addOnFailureListener {OnFailureListener{
                    Log.e(TAG, "Data upload not success.")
                    Snackbar.make(contentView,R.string.data_upload_failed, Snackbar.LENGTH_SHORT)
                        .show()
                }
                }
        }else{
            Log.e(TAG, "Selected room null")
            Snackbar.make(contentView,R.string.data_upload_failed, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun showBottomDialog(){

        sharedPreferences.saves(this,"QUICK_BOOK_START_DATE",startDate)
        sharedPreferences.saves(this,"QUICK_BOOK_START_END",endDate)
        val dateRange = dateRangeInput.text.toString()

        val bottomSheet = QuickBookingDialog(roomAdaptor,startDate,endDate,dateRange)
        bottomSheet.show(supportFragmentManager, "ModalBottomSheet")

    }
}