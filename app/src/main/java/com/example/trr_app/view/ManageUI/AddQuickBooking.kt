package com.example.trr_app.view.ManageUI

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.adaptor.RoomAdaptor
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.holders.RoomViewHolder
import com.example.trr_app.model.BookingDate
import com.example.trr_app.model.QuickBooking
import com.example.trr_app.model.Room
import com.example.trr_app.model.RoomBookingDetails
import com.example.trr_app.model.RoomBookingList
import com.example.trr_app.model.RoomDetails
import com.example.trr_app.model.RoomReserve
import com.example.trr_app.model.SubmitBooking
import com.example.trr_app.support.BookingDateCalender
import com.example.trr_app.support.QuickBookingDialog
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddQuickBooking : BaseActivity(),OnClickListener {

    //date range input
    private val dateRangeInput : TextInputEditText
        get() = findViewById(R.id.et_quickSearchDates)
    private val roomRecyclerView : RecyclerView
        get() = findViewById(R.id.quickRoomList)

    private val selectDateLayout : RelativeLayout
        get() = findViewById(R.id.selectDateFirstLayout)

    private val houseFullLayout : RelativeLayout
        get() = findViewById(R.id.houseFullLayout)

    private val roomDataListLayout : ScrollView
        get() = findViewById(R.id.dataLoadedLayout)

    //floating action button
    private val addNewQuickBook : ExtendedFloatingActionButton
        get() = findViewById(R.id.addNewQuickBooking)

    private val availabilitySearchBtn : ExtendedFloatingActionButton
        get() = findViewById(R.id.btnAvailability)
    private val aRoomBtn : ExtendedFloatingActionButton
        get() = findViewById(R.id.btnAIcon)
    private val bRoomBtn : ExtendedFloatingActionButton
        get() = findViewById(R.id.btnBIcon)
    private val cRoomBtn : ExtendedFloatingActionButton
        get() = findViewById(R.id.btnCIcon)
    private val dRoomBtn : ExtendedFloatingActionButton
        get() = findViewById(R.id.btnDIcon)
    private val eRoomBtn : ExtendedFloatingActionButton
        get() = findViewById(R.id.btnEIcon)
    private val fRoomBtn : ExtendedFloatingActionButton
        get() = findViewById(R.id.btnFIcon)
    private val gRoomBtn : ExtendedFloatingActionButton
        get() = findViewById(R.id.btnGIcon)

    private val backBtn : ImageView
        get() = findViewById(R.id.backIconQuickDashboard)

    //TAG Name
    private val TAG: String = AddQuickBooking::class.java.name

    private val contentViewRelative : RelativeLayout
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
    val roomBookingList : ArrayList<RoomBookingList> = ArrayList()
    val bookingDetails = RoomBookingDetails()

    var selectedRoomList : ArrayList<Room> = ArrayList()

    //bottom sheet
    private var bottomSheet : QuickBookingDialog? = null

    //,clicked function
    private var clicked = false

    //room flag
    private var R001 : Boolean = false
    private var R002 : Boolean = false
    private var R003 : Boolean = false
    private var R004 : Boolean = false
    private var R005 : Boolean = false
    private var R006 : Boolean = false
    private var R007 : Boolean = false

    //booking flag
    private lateinit var R001Key : String
    private lateinit var R002Key : String
    private lateinit var R003Key : String
    private lateinit var R004Key : String
    private lateinit var R005Key : String
    private lateinit var R006Key : String
    private lateinit var R007Key : String

    //add room availability animation
    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(this@AddQuickBooking,R.anim.rote_open_anime) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(this@AddQuickBooking,R.anim.rote_close_anime) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(this@AddQuickBooking,R.anim.from_bottom_anime) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(this@AddQuickBooking,R.anim.to_bottom_anime) }

    //array set DateRange
    private var aRoomList = ArrayList<BookingDate>()
    private var bRoomList = ArrayList<BookingDate>()
    private var cRoomList = ArrayList<BookingDate>()
    private var dRoomList = ArrayList<BookingDate>()
    private var eRoomList = ArrayList<BookingDate>()
    private var fRoomList = ArrayList<BookingDate>()
    private var gRoomList = ArrayList<BookingDate>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quick_booking)

        //hide Ui
        roomDataListLayout.visibility = GONE
        houseFullLayout.visibility = GONE

       //load
        loadEssentialData()
        //set Onclick
        dateRangeInput.setOnClickListener(this)
        addNewQuickBook.setOnClickListener(this)
        backBtn.setOnClickListener(this)

        //floating btn
        availabilitySearchBtn.setOnClickListener(this)
        aRoomBtn.setOnClickListener(this)
        bRoomBtn.setOnClickListener(this)
        cRoomBtn.setOnClickListener(this)
        dRoomBtn.setOnClickListener(this)
        eRoomBtn.setOnClickListener(this)
        fRoomBtn.setOnClickListener(this)
        gRoomBtn.setOnClickListener(this)
    }

    private fun loadEssentialData(){
        //load room
        loadDataRoom()
        //hide floating
        addNewQuickBook.isVisible = false

        //load Availability Booking
        checkAvailabilityBooking()

//        Snackbar.make(contentView, R.string.booking_successfully, Snackbar.LENGTH_SHORT).show()
//        startActivity(Intent(this,Dashboard::class.java))
    }

    fun resetSegment(){
        onResume()
        //hide Ui
        addNewQuickBook.isVisible = false
        availabilitySearchBtn.isVisible = true
//        roomDataListLayout.visibility = GONE
//        houseFullLayout.visibility = GONE

        //btn visibility
//        availabilitySearchBtn.visibility = VISIBLE
//        loadEssentialData()
    }

    fun showBookingBtn(show:Boolean){
        addNewQuickBook.isVisible = show
        availabilitySearchBtn.isVisible = !show
        println("")
    }

    private fun loadDataRoom(){
        //start loading
        //loadingProgressDialog(this)

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
                sharedPreferences.saves(this@AddQuickBooking,"RoomCount",roomListSize.toString())

                if (roomListSize!=0){
                    roomRecyclerView.layoutManager = LinearLayoutManager(this@AddQuickBooking)
                    val emptyRoomList = RoomReserve(false,false,false,false,false,false,false)
                    // Access the RecyclerView Adapter and load the data into it
                    bookedList.add("empty")
                    roomAdaptor = RoomAdaptor(roomList, this@AddQuickBooking,bookedList,bookingDetails){ show->showBookingBtn(show)}
                    roomRecyclerView.adapter = roomAdaptor
                }

                //dismiss
                //loadingDialogClose()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Data loading failed. room is empty"+error.details)
            }

        })

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

        val query:Query = bookingDatabaseReference.ref.orderByChild("checkIn").startAt(startDate)

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
                        Snackbar.make(contentViewRelative,R.string.booking_data_null, Snackbar.LENGTH_SHORT).show()
                    }
                }

                val listSize = bookingList.size
                overLeapList.clear()

                if (listSize!=0){
                    for (i in 0..< listSize){
                        if (areDateRangesOverlapping(startDate,endDate,bookingList[i].checkIn,bookingList[i].checkOut)){
                            Log.d(TAG, "Date Overlapped add to array")
                            overLeapList.add(bookingList[i])
                        }else{
                            Log.e(TAG, "Date not Overlapped add array")
                        }
                    }

                    setAvailability(overLeapList)
                }else{
                    roomDataListLayout.visibility = VISIBLE
                    selectDateLayout.visibility = GONE
                    houseFullLayout.visibility = GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Data loading Failed")
            }

        })

        //(adapter as FirebaseRecyclerAdapter<RoomDetails, RoomViewHolder>).startListening()
        //roomRecyclerView.adapter = adapter

        //close progress dialog
        loadingDialogClose()

    }

    private fun setAvailability(overLeapList : ArrayList<SubmitBooking>){

        //val roomBookingList : ArrayList<RoomReserve> = ArrayList()

        val listSize = overLeapList.size

        if (listSize!=0) {
            for (i in 0..<listSize) {
                //roomBookingList.add(overLeapList[i].roomReserve)
                val list : RoomBookingList = RoomBookingList()
                list.roomReserve = overLeapList[i].roomReserve
                list.bookedId = overLeapList[i].uniqueKey

                //add element
                roomBookingList.add(list)
            }

            //get count
            println("result size : "+roomBookingList.size.toString())

            //clear list
            bookedList.clear()


            for (i in 0..<listSize) {
                if (roomBookingList[i].roomReserve.room01 == true) {
                    R001 = true
                    bookedList.add("R001")
                    //set key
                    bookingDetails.reserveRoom("R001",roomBookingList[i].getBookedId())
                    //R001Key = roomBookingList[i].getBookedId()
                }
                if (roomBookingList[i].roomReserve.room02 == true) {
                    R002 = true
                    bookedList.add("R002")
                    //set key
                    bookingDetails.reserveRoom("R002",roomBookingList[i].bookedId)
                    //R002Key = roomBookingList[i].getBookedId()
                }
                if (roomBookingList[i].roomReserve.room03 == true) {
                    R003 = true
                    bookedList.add("R003")
                    //set key
                    bookingDetails.reserveRoom("R003",roomBookingList[i].bookedId)
                    //R003Key = roomBookingList[i].getBookedId()
                }
                if (roomBookingList[i].roomReserve.room04 == true) {
                    R004 = true
                    bookedList.add("R004")
                    //set key
                    bookingDetails.reserveRoom("R004",roomBookingList[i].bookedId)
                    //R004Key = roomBookingList[i].getBookedId()
                }
                if (roomBookingList[i].roomReserve.room05 == true) {
                    R005 = true
                    bookedList.add("R005")
                    //set key
                    bookingDetails.reserveRoom("R005",roomBookingList[i].bookedId)
                    //R005Key = roomBookingList[i].getBookedId()
                }
                if (roomBookingList[i].roomReserve.room06 == true) {
                    R006 = true
                    bookedList.add("R006")
                    //set key
                    bookingDetails.reserveRoom("R006",roomBookingList[i].bookedId)
                    //R006Key = roomBookingList[i].getBookedId()
                }
                if (roomBookingList[i].roomReserve.room07 == true) {
                    R007 = true
                    bookedList.add("R007")
                    //set key
                    bookingDetails.reserveRoom("R007",roomBookingList[i].bookedId)
                    //R007Key = roomBookingList[i].getBookedId()
                }
            }

            if (bookedList.contains("R007")){
                //change UI
                selectDateLayout.visibility = GONE
                houseFullLayout.visibility = VISIBLE
            }
            //getDisplay Tabs
            else if (roomList.size!=0){
                //change UI
                houseFullLayout.visibility = GONE
                selectDateLayout.visibility = GONE
                roomDataListLayout.visibility = VISIBLE

                //load Data
                roomRecyclerView.layoutManager = LinearLayoutManager(this@AddQuickBooking)
                roomAdaptor = RoomAdaptor(roomList, this@AddQuickBooking,bookedList,bookingDetails){ show->showBookingBtn(show)}
                roomRecyclerView.adapter = roomAdaptor

                //close loading
                loadingDialogClose()
            }
        }
        else if (listSize==0){
            //change UI
            houseFullLayout.visibility = GONE
            selectDateLayout.visibility = GONE
            roomDataListLayout.visibility = VISIBLE

            //no more booked
            bookedList.clear()

            //load Data
            roomRecyclerView.layoutManager = LinearLayoutManager(this@AddQuickBooking)
            roomAdaptor = RoomAdaptor(roomList, this@AddQuickBooking,bookedList,bookingDetails){ show->showBookingBtn(show)}
            roomRecyclerView.adapter = roomAdaptor

            //close loading
            loadingDialogClose()
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

//        picker.show(this.supportFragmentManager,TAG)
        //close dialog
        //loadingDialogClose()

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
                Snackbar.make(contentViewRelative,R.string.dateNotFixed, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        picker.addOnCancelListener {
            // Close the progress dialog when the date picker is fully shown
            loadingDialogClose()
        }

        picker.addOnDismissListener {
            // Close the progress dialog when the date picker is fully shown
            loadingDialogClose()
        }

        picker.show(this.supportFragmentManager,TAG)
        //close dialog
        loadingDialogClose()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.et_quickSearchDates -> showDatePicker()
            R.id.addNewQuickBooking -> showBottomDialog()
            //R.id.backIconQuickDashboard -> loadBookingAvailability("R002")

            //floating Action Bnt
            R.id.btnAIcon -> loadAvailabilityCalender(aRoomList,"Araliya Residents")
            R.id.btnBIcon -> loadAvailabilityCalender(bRoomList,"Sunflower Residents")
            R.id.btnCIcon -> loadAvailabilityCalender(cRoomList,"Orchid Residents ")
            R.id.btnDIcon -> loadAvailabilityCalender(dRoomList,"Nelum Residents")
            R.id.btnEIcon -> loadAvailabilityCalender(eRoomList,"Olu Residents")
            R.id.btnFIcon -> loadAvailabilityCalender(fRoomList,"Kumudu Residents")
            R.id.btnGIcon -> loadAvailabilityCalender(gRoomList,"Full Villa")

            R.id.btnAvailability -> onAddButtonClick()
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
            databaseReference.setValue(QuickBooking(name,dateRange,startDate,endDate,contact,headCount,specialNote,"Temporary",postUniqueKey,room))
                .addOnCompleteListener {task ->
                    if (task.isSuccessful){

                        //reset page
                        //loadEssentialData()
                        Toast.makeText(applicationContext,R.string.booking_successfully,Toast.LENGTH_SHORT).show()
                        bottomSheet?.dismiss()
                        addNewQuickBook.isVisible = false
                        //startActivity(Intent(applicationContext,Dashboard::class.java))

                    }else{
                        Log.e(TAG, "Data upload not success.")
                        Toast.makeText(applicationContext,R.string.data_upload_failed, Toast.LENGTH_SHORT)
                            .show()
                    }

                }.addOnFailureListener {OnFailureListener{
                    Log.e(TAG, "Data upload not success.")
                    Toast.makeText(applicationContext,R.string.data_upload_failed, Toast.LENGTH_SHORT)
                        .show()
                }
                }
        }else{
            Log.e(TAG, "Selected room null")
            Toast.makeText(applicationContext,R.string.data_upload_failed, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showBottomDialog(){

        //sharedPreferences.saves(this,"QUICK_BOOK_START_DATE",startDate)
        //sharedPreferences.saves(this,"QUICK_BOOK_START_END",endDate)
        val dateRange = dateRangeInput.text.toString()

        bottomSheet = QuickBookingDialog(roomAdaptor,startDate,endDate,dateRange)
        bottomSheet!!.show(supportFragmentManager, "ModalBottomSheet")

    }

    private fun convertStringToDate(dateString: String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.parse(dateString) ?: Date()
    }

    private fun checkAvailabilityBooking() {

        //start loading
        loadingProgressDialog(this)

        val date = getCurrentDate()

        val mChatDatabaseReference = firebaseDatabase.reference.child("TRRApp").child("Booking Details")
            .child("Appointment Reservation")
        val query = mChatDatabaseReference.ref.orderByChild("checkIn").startAfter(date)
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val  bookingList = ArrayList<QuickBooking>()
                bookingList.clear()
                if (snapshot!=null){
                    for (snapshot in snapshot.children){
                        val booking = snapshot.getValue(QuickBooking::class.java)
                        bookingList.add(booking!!)
                    }
                }

                val listSize = bookingList.size

                for (i in 0..< listSize){
                    if (bookingList[i].roomReserve.room01==true){
                        aRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                        gRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                    }
                    if (bookingList[i].roomReserve.room02==true){
                        bRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                        gRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                    }
                    if (bookingList[i].roomReserve.room03==true){
                        cRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                        gRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                    }
                    if (bookingList[i].roomReserve.room04==true){
                        dRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                        gRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                    }
                    if (bookingList[i].roomReserve.room05==true){
                        eRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                        gRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                    }
                    if (bookingList[i].roomReserve.room06==true){
                        fRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                        gRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                    }else{
                        gRoomList.add(BookingDate(convertStringToDate(bookingList[i].checkIn),convertStringToDate(bookingList[i].checkOut)))
                    }
                }

                aRoomList
                Log.d(this@AddQuickBooking.TAG,aRoomList.toString())

                bRoomList
                Log.d(this@AddQuickBooking.TAG,bRoomList.toString())

                cRoomList
                Log.d(this@AddQuickBooking.TAG,cRoomList.toString())

                dRoomList
                Log.d(this@AddQuickBooking.TAG,dRoomList.toString())

                eRoomList
                Log.d(this@AddQuickBooking.TAG,eRoomList.toString())

                fRoomList
                Log.d(this@AddQuickBooking.TAG,fRoomList.toString())

                gRoomList
                Log.d(this@AddQuickBooking.TAG,gRoomList.toString())

               loadingDialogClose()
            }
            override fun onCancelled(error: DatabaseError) {
                //close dialog
                loadingDialogClose()

                Snackbar.make(contentViewRelative,"Data loading failed.", Snackbar.LENGTH_SHORT).show()
                Log.d(this@AddQuickBooking.TAG, "Data loading failed form firebase end")
            }
        })
    }

    private fun loadAvailabilityCalender(booking:ArrayList<BookingDate>,roomName:String){
        //load data
//        loadingProgressDialog(this@AddQuickBooking)
//        val fragment = BookingDateCalender.newInstance(booking)
//        fragment.show(supportFragmentManager, "Availability Calender")
//        loadingDialogClose()


        GlobalScope.launch(Dispatchers.Main) {
            val fragment = BookingDateCalender.newInstance(booking,roomName)
            fragment.show(supportFragmentManager, "Availability Calender")
        }
    }

    private fun onAddButtonClick(){
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked:Boolean){
        if (!clicked){
            aRoomBtn.visibility = INVISIBLE
            bRoomBtn.visibility = INVISIBLE
            cRoomBtn.visibility = INVISIBLE
            dRoomBtn.visibility = INVISIBLE
            eRoomBtn.visibility = INVISIBLE
            fRoomBtn.visibility = INVISIBLE
            gRoomBtn.visibility = INVISIBLE
        }else{
            aRoomBtn.visibility = GONE
            bRoomBtn.visibility = GONE
            cRoomBtn.visibility = GONE
            dRoomBtn.visibility = GONE
            eRoomBtn.visibility = GONE
            fRoomBtn.visibility = GONE
            gRoomBtn.visibility = GONE
        }
    }

    private fun setAnimation(clicked:Boolean){
        if (!clicked){
            aRoomBtn.startAnimation(fromBottom)
            bRoomBtn.startAnimation(fromBottom)
            cRoomBtn.startAnimation(fromBottom)
            dRoomBtn.startAnimation(fromBottom)
            eRoomBtn.startAnimation(fromBottom)
            fRoomBtn.startAnimation(fromBottom)
            gRoomBtn.startAnimation(fromBottom)

            availabilitySearchBtn.startAnimation(rotateOpen)
        }else{
            aRoomBtn.startAnimation(toBottom)
            bRoomBtn.startAnimation(toBottom)
            cRoomBtn.startAnimation(toBottom)
            dRoomBtn.startAnimation(toBottom)
            eRoomBtn.startAnimation(toBottom)
            fRoomBtn.startAnimation(toBottom)
            gRoomBtn.startAnimation(toBottom)

            availabilitySearchBtn.startAnimation(rotateClose)
        }
    }
}