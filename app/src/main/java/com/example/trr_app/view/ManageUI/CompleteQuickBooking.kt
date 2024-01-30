package com.example.trr_app.view.ManageUI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RelativeLayout
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.model.FeatureReservation
import com.example.trr_app.model.MealChooser
import com.example.trr_app.model.QuickBooking
import com.example.trr_app.model.RoomReserve
import com.example.trr_app.model.SubmitBooking
import com.example.trr_app.view.Dashboard
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class CompleteQuickBooking : BaseActivity() {

    private val TAG: String
            = CompleteQuickBooking::class.java.name
    //relativeUI
    private val contentView : RelativeLayout
        get() = findViewById(R.id.completeBookingView)
    //text input edite texts
    private val customerFName : TextInputEditText
        get() = findViewById(R.id.cqb_fname)
    private val headCount : TextInputEditText
        get() = findViewById(R.id.cqb_HeadCount)
    private  val customerSName : TextInputEditText
        get() = findViewById(R.id.cqb_sname)
    private  val bookingNumber : TextInputEditText
        get() = findViewById(R.id.cqb_bookingNumber)
    private val bookingType : MaterialAutoCompleteTextView
        get() = findViewById(R.id.cqb_bookingType)
    private val bookingDateRange : TextInputEditText
        get() = findViewById(R.id.cqb_bookingDateRange)
    private val roomCount : TextInputEditText
        get() = findViewById(R.id.cqb_roomCount)
    private  val address : TextInputEditText
        get() = findViewById(R.id.cqb_address)
    private  val area : TextInputEditText
        get() = findViewById(R.id.cqb_Area)
    private val city : TextInputEditText
        get() = findViewById(R.id.cqb_city)
    private  val contact : TextInputEditText
        get() = findViewById(R.id.cqb_contact)
    private  val nic : TextInputEditText
        get() = findViewById(R.id.cqb_nic)
    private  val specialNote : TextInputEditText
        get() = findViewById(R.id.cqb_eti_specialNote)
    private val btnComplete : Button
        get() = findViewById(R.id.cqb_btn_complete)
    private val btnGoBack : Button
        get() = findViewById(R.id.cqb_btn_goBack)

    private var roomList : RoomReserve? = null
    private val featureReservation : FeatureReservation? = null
    private val mealChooser : MealChooser? = null

    //on off switches
    private val mealService : Button
        get() = findViewById(R.id.cqb_mealsDetailsSwitch)
    private val otherService : Button
        get() = findViewById(R.id.cqb_otherServiceSwitch)

    //db reference
    private var databaseReference : DatabaseReference = firebaseDatabaseReference

    //String
    private var bookingNo : String? = null

    //chip feature
    private var chipFeature1 : Boolean = false
    private var chipFeature2 : Boolean = false
    private var chipFeature3 : Boolean = false
    private var chipFeature4 : Boolean = false
    private var chipFeature5 : Boolean = false
    private var chipFeature6 : Boolean = false

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

    //meal chips
    private val chip01 : Chip
        get() = findViewById(R.id.cqb_chip_morningTea)
    private val chip02 : Chip
        get() = findViewById(R.id.cqb_chip_breakfast)
    private val chip03 : Chip
        get() = findViewById(R.id.cqb_chip_lunch)
    private val chip04 : Chip
        get() = findViewById(R.id.cqb_chip_eveningTea)
    private val chip05 : Chip
        get() = findViewById(R.id.cqb_chip_dinner)
    private val chip06 : Chip
        get() = findViewById(R.id.cqb_chip_bbq)

    //Meal status
    private var Cp1Status : Boolean = false
    private var Cp2Status : Boolean = false
    private var Cp3Status : Boolean = false
    private var Cp4Status : Boolean = false
    private var Cp5Status : Boolean = false
    private var Cp6Status : Boolean = false

    //Json
    private var mealOrderedGSON : String? = null
    private var featureReservationGSON : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_quick_booking)

        //get booking number
        bookingNo = intent.getStringExtra("BookingNumber")

        //load data
        if (bookingNo!=null){
            loadData(bookingNo)
        }else{
            Snackbar.make(contentView,"Please try again...!!",Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun loadData(bookingNo:String?){
        //load dialog
        loadingProgressDialog(this)

        databaseReference = firebaseDatabaseReference.child("Booking Details").child("Appointment Reservation").child(
            bookingNo!!)

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!=null){
                    val data = snapshot.getValue(QuickBooking::class.java) as QuickBooking
                    Log.d(TAG, "Data loading successfully.")

                    //display data
                    setData(data)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Data loading failed in firebase end.")
                Snackbar.make(contentView,"Data loading failed. Try again..!",Snackbar.LENGTH_SHORT).show()
                //go back
                onBackPressed()
            }

        })

        //close loading
        loadingDialogClose()
    }

    private fun setData(data:QuickBooking){
        bookingNumber.setText(bookingNo)
        customerFName.setText(data.customerName)
        bookingDateRange.setText(data.booking_dateRange)
        headCount.setText(data.headCount)
        contact.setText(data.customerContact)
        specialNote.setText(data.specialNote)
        //roomCount.setText()
        bookingType.setText(data.bookingType)
        roomList = data.roomReserve
    }

    private fun captureData(){
        val updateBooking = SubmitBooking()

        updateBooking.setFirstName(customerFName.text.toString())
        updateBooking.setSecondName(customerSName.text.toString())
        updateBooking.setBookingType(bookingType.text.toString())
        updateBooking.setBooking_dateRange(bookingDateRange.text.toString())
        updateBooking.setHeadCount(headCount.text.toString())
        updateBooking.setRoomsCount(roomCount.text.toString())
        updateBooking.setAddress(address.text.toString())
        updateBooking.setArea(area.text.toString())
        updateBooking.setCity(city.text.toString())
        updateBooking.setContact(contact.text.toString())
        updateBooking.setIdentity(nic.text.toString())
        updateBooking.setSpecialNote(specialNote.text.toString())


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


    }

    private fun countMeal(){
        val mealChooser = MealChooser(Cp1Status,Cp2Status,Cp3Status,Cp4Status,Cp5Status,Cp6Status)
        val gson = Gson()
        mealOrderedGSON = gson.toJson(mealChooser)
    }

    private fun countFeatures(){
        val featureReservation = FeatureReservation(chipFeature1,chipFeature2,chipFeature3,chipFeature4,chipFeature5,chipFeature6)
        val gson = Gson()
        featureReservationGSON = gson.toJson(featureReservation)
    }
}