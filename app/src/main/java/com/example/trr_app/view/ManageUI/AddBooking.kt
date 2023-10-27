package com.example.trr_app.view.ManageUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.util.Pair
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.model.FeatureReservation
import com.example.trr_app.model.MealChooser
import com.example.trr_app.model.RoomReserve
import com.example.trr_app.model.SubmitBooking
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class AddBooking : BaseActivity(), OnClickListener {

    private val otherServiceSwitch : SwitchMaterial
        get() = findViewById(R.id.otherServiceSwitch)

    private val mealsDetailsSwitch : SwitchMaterial
        get() = findViewById(R.id.mealsDetailsSwitch)

    private val otherServiceLayout : LinearLayout
        get() = findViewById(R.id.otherServiceLayout)

    private val mealDetailsLayout : LinearLayout
        get() = findViewById(R.id.mealDetailsLayout)

    private val bookingDate : TextInputEditText
        get() = findViewById(R.id.tv_bookingDate)

    private val headCount : TextInputEditText
        get() = findViewById(R.id.eti_HeadCount)

    private val roomCount : AutoCompleteTextView
        get() = findViewById(R.id.ac_roomCount)

    private val bookingType : AutoCompleteTextView
        get() = findViewById(R.id.ac_bookingType)

    private val userFName : TextInputEditText
        get() = findViewById(R.id.eti_fname)

    private val userSName : TextInputEditText
        get() = findViewById(R.id.eti_sname)

    private val userAddress : TextInputEditText
        get() = findViewById(R.id.eti_address)

    private val userArea : TextInputEditText
        get() = findViewById(R.id.eti_Area)

    private val userCity : TextInputEditText
        get() = findViewById(R.id.eti_city)

    private val userNIC : TextInputEditText
        get() = findViewById(R.id.eti_nic)

    private val userContact : TextInputEditText
        get() = findViewById(R.id.eti_contact)

    private val userSpecialNote : TextInputEditText
        get() = findViewById(R.id.eti_specialNote)

    private val cbRoom1 : CheckBox
        get() = findViewById(R.id.cbRoom01)

    private val cbRoom2 : CheckBox
        get() = findViewById(R.id.cbRoom02)

    private val cbRoom3 : CheckBox
        get() = findViewById(R.id.cbRoom03)

    private val cbRoom4 : CheckBox
        get() = findViewById(R.id.cbRoom04)

    private val cbRoom5 : CheckBox
        get() = findViewById(R.id.cbRoom05)

    //meal chips
    private val chip01 : Chip
        get() = findViewById(R.id.chip_morningTea)
    private val chip02 : Chip
        get() = findViewById(R.id.chip_breakfast)
    private val chip03 : Chip
        get() = findViewById(R.id.chip_lunch)
    private val chip04 : Chip
        get() = findViewById(R.id.chip_eveningTea)
    private val chip05 : Chip
        get() = findViewById(R.id.chip_dinner)
    private val chip06 : Chip
        get() = findViewById(R.id.chip_bbq)

    //features chip
    private val chipFeature01 : Chip
        get() = findViewById(R.id.chipF_1)
    private val chipFeature02 : Chip
        get() = findViewById(R.id.chipF_2)
    private val chipFeature03 : Chip
        get() = findViewById(R.id.chipF_3)
    private val chipFeature04 : Chip
        get() = findViewById(R.id.chipF_4)
    private val chipFeature05 : Chip
        get() = findViewById(R.id.chipF_5)

    private val contentView : RelativeLayout
        get() = findViewById(R.id.addBookingLayout)
    private val btnSubmit : MaterialButton
        get() = findViewById(R.id.btn_submitBooked)
    private val btnCancel : MaterialButton
        get() = findViewById(R.id.btn_cancelBooked)


    //Room Availability
    private var RM1Status : Boolean = false
    private var RM2Status : Boolean = false
    private var RM3Status : Boolean = false
    private var RM4Status : Boolean = false
    private var RM5Status : Boolean = false

    //Meal status
    private var Cp1Status : Boolean = false
    private var Cp2Status : Boolean = false
    private var Cp3Status : Boolean = false
    private var Cp4Status : Boolean = false
    private var Cp5Status : Boolean = false
    private var Cp6Status : Boolean = false

    //chip feature
    private var chipFeature1 : Boolean = false
    private var chipFeature2 : Boolean = false
    private var chipFeature3 : Boolean = false
    private var chipFeature4 : Boolean = false
    private var chipFeature5 : Boolean = false
    private var chipFeature6 : Boolean = false

    private val TAG: String
            = AddBooking::class.java.name

    //set check in check out
    private var checkInDate : String? = null
    private var checkOutDate : String? = null

    private var roomArray = ArrayList<String>()

    //user data
    private var firstName : String? = null
    private var secondName : String? = null
    private var address : String? = null
    private var area : String? = null
    private var city : String? = null
    private var NIC : String? = null
    private var contact : String? = null
    private var specialNote : String? = null

    private var dateRangeTxt : String? = null
    private var headCountTxt : String? = null
    private var roomCountTxt : String? = null
    private var bookingTypeTxt : String? = null

    private var roomReservationGSON : String? = null
    private var mealOrderedGSON : String? = null
    private var featureReservationGSON : String? = null

    private var addBookingReference : DatabaseReference? = null

    private var postUniqueKey : String? = null

    //reserved rooms
    private var roomReserve : RoomReserve? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_booking)

        //go back
        btnCancel.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

        //set room drop down
        setOptionsToRoomDropDown()
        //set booking type
        setOptionsToTypeDropDown()

        //other service layout visibility
        otherServiceSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d(TAG,"Other service TAB load")
            if (isChecked) {
                otherServiceLayout.visibility = View.VISIBLE
            } else {
                otherServiceLayout.visibility = View.GONE
            }
        }

        //meals details layout visibility
        mealsDetailsSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d(TAG,"Meals details TAB load")
            if (isChecked){
                mealDetailsLayout.visibility = View.VISIBLE
            }else{
                mealDetailsLayout.visibility = View.GONE
            }
        }

        //booking details capture
        bookingDate.setOnClickListener(this)

        //select room listeners
        cbRoom1.setOnCheckedChangeListener { compoundButton, isChecked ->
            RM1Status = isChecked
        }
        cbRoom2.setOnCheckedChangeListener { compoundButton, isChecked ->
            RM2Status = isChecked
        }
        cbRoom3.setOnCheckedChangeListener { compoundButton, isChecked ->
            RM3Status = isChecked
        }
        cbRoom4.setOnCheckedChangeListener { compoundButton, isChecked ->
            RM4Status = isChecked
        }
        cbRoom5.setOnCheckedChangeListener { compoundButton, isChecked ->
            RM5Status = isChecked
        }

        //chip meals feedback
        chip01.setOnCheckedChangeListener { chip, ischecked ->
            Cp1Status = ischecked
        }
        chip02.setOnCheckedChangeListener { chip, ischecked ->
            Cp2Status = ischecked
        }
        chip03.setOnCheckedChangeListener { chip, ischecked ->
            Cp3Status = ischecked
        }
        chip04.setOnCheckedChangeListener { chip, ischecked ->
            Cp4Status = ischecked
        }
        chip05.setOnCheckedChangeListener { chip, ischecked ->
            Cp5Status = ischecked
        }
        chip06.setOnCheckedChangeListener { chip, ischecked ->
            Cp6Status = ischecked
        }

        //chip features
        chipFeature01.setOnCheckedChangeListener { chip, ischecked ->
            chipFeature1 = ischecked
        }
        chipFeature02.setOnCheckedChangeListener { chip, ischecked ->
            chipFeature2 = ischecked
        }
        chipFeature03.setOnCheckedChangeListener { chip, ischecked ->
            chipFeature3 = ischecked
        }
        chipFeature04.setOnCheckedChangeListener { chip, ischecked ->
            chipFeature4 = ischecked
        }
        chipFeature05.setOnCheckedChangeListener { chip, ischecked ->
            chipFeature5 = ischecked
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun countFeatures(){
        val featureReservation = FeatureReservation(chipFeature1,chipFeature2,chipFeature3,chipFeature4,chipFeature5,chipFeature6)
        val gson = Gson()
        featureReservationGSON = gson.toJson(featureReservation)
    }

    private fun countRoom(){
        roomReserve = RoomReserve(RM1Status,RM2Status,RM3Status,RM4Status,RM5Status)
        val gson = Gson()
        roomReservationGSON = gson.toJson(roomReserve)
    }

    private fun countMeal(){
        val mealChooser = MealChooser(Cp1Status,Cp2Status,Cp3Status,Cp4Status,Cp5Status,Cp6Status)
        val gson = Gson()
        mealOrderedGSON = gson.toJson(mealChooser)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_bookingDate -> showDatePicker()
            R.id.btn_cancelBooked -> onBackPressed()
            R.id.btn_submitBooked -> validateData()
        }
    }

    private fun showDatePicker(){

        loadingProgressDialog(this)

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val picker = MaterialDatePicker.Builder.dateRangePicker().setTheme(R.style.MaterialDatePickerTheme)
            .setTitleText("Select Check-in Check-out Date")
            .setSelection(Pair(MaterialDatePicker.todayInUtcMilliseconds(),MaterialDatePicker.todayInUtcMilliseconds()+(1000*24*60*60)))
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        picker.show(this.supportFragmentManager,TAG)
        //close dialog
        loadingDialogClose()

        picker.addOnPositiveButtonClickListener {
            bookingDate.setText(convertTimeToDate(it.first) +" - "+convertTimeToDate(it.second))
            checkInDate = convertTimeToDate(it.first)
            checkOutDate = convertTimeToDate(it.second)
        }
    }

    private fun convertTimeToDate(time:Long): String{
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = time
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(utc.time)
    }

    private fun setOptionsToRoomDropDown(){
        try {
            //val items = listOf("Admin", "Staff", "Customer")

            val categoryList = ArrayList<String>()
            categoryList.add("1")
            categoryList.add("2")
            categoryList.add("3")
            categoryList.add("4")
            categoryList.add("5")
            categoryList.add("All")

            val adapter = ArrayAdapter(this, R.layout.list_item,categoryList)
            roomCount.setAdapter(adapter)

        }catch (e:Exception){
            Log.d(TAG, "Error" +e.message)
            Log.d(TAG, "Please couldn't able to load dropdown")
        }
    }

    private fun setOptionsToTypeDropDown(){
        try {
            //val items = listOf("Admin", "Staff", "Customer")

            val categoryList = ArrayList<String>()
            categoryList.add("Couple")
            categoryList.add("Family")
            categoryList.add("Organization")
            categoryList.add("Pilgrimage")

            val adapter = ArrayAdapter(this, R.layout.list_item,categoryList)
            bookingType.setAdapter(adapter)

        }catch (e:Exception){
            Log.d(TAG, "Error" +e.message)
            Log.d(TAG, "Please couldn't able to load dropdown")
        }
    }

    private fun captureData(){
        dateRangeTxt = bookingDate.text.toString()
        headCountTxt = headCount.text.toString()
        roomCountTxt = roomCount.text.toString()
        bookingTypeTxt = bookingType.text.toString()
        firstName = userFName.text.toString()
        secondName = userSName.text.toString()
        address = userAddress.text.toString()
        area = userArea.text.toString()
        city = userCity.text.toString()
        contact = userContact.text.toString()
        NIC = userNIC.text.toString()
        specialNote = userSpecialNote.text.toString()

        //count room
        countRoom()

        //count meal
        countMeal()

        //count Features
        countFeatures()

        //make database reference
        if (makeDataBaseReference()){
            addBookingReference?.setValue(uploadData(roomReserve!!))
                ?.addOnCompleteListener { task ->
                    val result = task.result
                    if (task.isSuccessful){

                        //close dialog
                        loadingDialogClose()

                        Log.e(TAG, "Booking Successfully")
                        Snackbar.make(contentView, R.string.booking_successfully, Snackbar.LENGTH_SHORT)
                            .show()
                        //move to manage booking
                        navigateToManageBooking()

                    }else{
                        //close dialog
                        loadingDialogClose()

                        Log.e(TAG, "Failed to create booking.")
                        Snackbar.make(contentView, R.string.booking_data_upload_fail, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
                ?.addOnFailureListener { task ->
                    if (task.message!=null){
                        //close dialog
                        loadingDialogClose()
                        Log.e(TAG, "data upload fail in addOnFailureListener")
                        Snackbar.make(contentView,task.message.toString(), Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
        }else{
            //close dialog
            loadingDialogClose()
            Log.e(TAG, "data upload fail in database reference null")
            Snackbar.make(contentView,R.string.booking_data_upload_fail, Snackbar.LENGTH_SHORT)
                .show()
        }

    }

    private fun navigateToManageBooking(){
        this.finish()
        startActivity(Intent(this@AddBooking,ManageActivity::class.java))
    }

    private fun validateData(){
        //start loading dialog
        loadingProgressDialog(this)

        if (bookingDate.text!=null){
            if (userFName.text!=null){
                if (userContact.text!=null && verifyContact(userContact.text.toString())){
                    if (roomCount.text!=null){
                        //ready to submit
                        captureData()
                    }else{
                        //close dialog
                        loadingDialogClose()
                        Log.e(TAG, "Please input room count.")
                        Snackbar.make(contentView, R.string.data_upload_error, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }else{
                    //close dialog
                    loadingDialogClose()
                    Log.e(TAG, "Please input valid contact.")
                    Snackbar.make(contentView, R.string.data_upload_error, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }else{
                //close dialog
                loadingDialogClose()
                Log.e(TAG, "Please input valid user name.")
                Snackbar.make(contentView, R.string.data_upload_error, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }else{
            //close dialog
            loadingDialogClose()
            Log.e(TAG, "Please input valid date.")
            Snackbar.make(contentView, R.string.data_upload_error, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun makeDataBaseReference(): Boolean{
        return if (firebaseAuth.currentUser != null) {
            postUniqueKey = firebaseDatabaseReference.push().key.toString()
            Log.d(TAG, "Post Key : $postUniqueKey")

            return if (postUniqueKey != null) {
                addBookingReference = firebaseDatabaseReference.child("Booking Details")
                    .child("Appointment Reservation").child(postUniqueKey!!)
                true
            } else {
                Log.e(TAG, "Database reference key null")
                false
            }
        } else {
            Log.e(TAG, "Firebase User Null")
            false
        }
    }
    private fun uploadData(roomReserve: RoomReserve): SubmitBooking {
       val submitBooking = SubmitBooking(dateRangeTxt,checkInDate,checkOutDate,headCountTxt,roomCountTxt,bookingTypeTxt,firstName,secondName,address,area,city,contact,NIC,specialNote,mealOrderedGSON,featureReservationGSON,roomReservationGSON,roomReserve)
        return submitBooking
    }
}