package com.example.trr_app.support

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.model.FeatureReservation
import com.example.trr_app.model.MealChooser
import com.example.trr_app.model.QuickBooking
import com.example.trr_app.model.RoomReserve
import com.example.trr_app.model.SubmitBooking
import com.example.trr_app.view.ManageUI.UpComingBookingActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class PermanentBookingEdite(//Strings
    private var context: Context,
    private var bookingID: String?):BottomSheetDialogFragment(),OnClickListener{

    //dialog
    private lateinit var dialog : BottomSheetDialog

    //relative
    private lateinit var contentView : FrameLayout

    //Firebase
    private val firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseDatabaseReference : DatabaseReference = firebaseDatabase.reference.child("TRRApp")
    //database reference
    private var databaseReference : DatabaseReference = firebaseDatabaseReference

    //TAG Name
    private val TAG: String = PermanentBookingEdite::class.java.name

    //component
    private lateinit var etBookingDateRange : TextInputEditText
    private lateinit var etRoomCount : AutoCompleteTextView
    private lateinit var etHeadCount : TextInputEditText
    private lateinit var etFName : TextInputEditText
    private lateinit var etSName : TextInputEditText
    private lateinit var etAddress : TextInputEditText
    private lateinit var etArea : TextInputEditText
    private lateinit var etCity : TextInputEditText
    private lateinit var etNIC : TextInputEditText
    private lateinit var etContact : TextInputEditText
    private lateinit var etSpecialNote : TextInputEditText

    //final dialog title
    private lateinit var finalDialogTitle : TextView

    //Strings
    private lateinit var fName:String
    private lateinit var sName:String
    private lateinit var city:String
    private lateinit var area:String
    private lateinit var address:String
    private lateinit var contact:String
    private lateinit var dateRange :String
    private lateinit var NIC:String
    private lateinit var specialNote:String
    private lateinit var selectedRooms : RoomReserve
    private lateinit var checkIn : String
    private lateinit var checkOut : String
    private lateinit var headCount : String
    private lateinit var roomCount : String
    private lateinit var bookingType : String
    private lateinit var bookingBackGround:String
    private lateinit var bookingCategory : String

    //booking
    private var addBookingReference : DatabaseReference? = null

    //switch
    private lateinit var serviceSwitch: SwitchMaterial
    private lateinit var mealSwitch: SwitchMaterial

    //service Strings
    private lateinit var serviceString : String
    private lateinit var mealStrings : String

    //hidden layout
    private lateinit var serviceLayout : LinearLayout
    private lateinit var mealLayout : LinearLayout
    private lateinit var successLayout : RelativeLayout
    private lateinit var fillingLayout : NestedScrollView

    //chip
    private lateinit var chipMeal01 : Chip
    private lateinit var chipMeal02 : Chip
    private lateinit var chipMeal03 : Chip
    private lateinit var chipMeal04 : Chip
    private lateinit var chipMeal05 : Chip
    private lateinit var chipMeal06 : Chip

    private lateinit var chipFeatures01 : Chip
    private lateinit var chipFeatures02 : Chip
    private lateinit var chipFeatures03 : Chip
    private lateinit var chipFeatures04 : Chip
    private lateinit var chipFeatures05 : Chip
    private lateinit var chipFeatures06 : Chip


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

    //Button
    private lateinit var btnSaveChangers : MaterialButton
    private lateinit var btnRemoveBooking : MaterialButton
    private lateinit var btnGoUpComingMenu : MaterialButton

    //Gson
    private var roomReservationGSON : String? = null
    private var mealOrderedGSON : String? = null
    private var featureReservationGSON : String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog ->
            this.dialog = dialog as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<FrameLayout>(R.id.permanentBookingFrameLayout)
            BottomSheetBehavior.from(bottomSheet!!).state =
                BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet!!).skipCollapsed = true
            BottomSheetBehavior.from(bottomSheet!!).isHideable = false
        }
        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_permanent_booking_edite,container,false)

        //layout
        contentView = view.findViewById(R.id.permanentBookingFrameLayout)
        serviceLayout = view.findViewById(R.id.pe_otherServiceLayout)
        mealLayout = view.findViewById(R.id.pe_mealDetailsLayout)
        successLayout = view.findViewById(R.id.pm_successfullyLayout)
        fillingLayout = view.findViewById(R.id.pm_scrollView)

        //Material Btn
        btnSaveChangers = view.findViewById(R.id.editBtn_submitBooked)
        btnRemoveBooking = view.findViewById(R.id.editBtn_cancelBooked)
        btnGoUpComingMenu = view.findViewById(R.id.pe_btnGoQuick)

        //chip meal
        chipMeal01 = view.findViewById(R.id.editChip_morningTea)
        chipMeal02 = view.findViewById(R.id.editChip_eveningTea)
        chipMeal03 = view.findViewById(R.id.editChip_breakfast)
        chipMeal04 = view.findViewById(R.id.editChip_lunch)
        chipMeal05 = view.findViewById(R.id.editChip_dinner)
        chipMeal06 = view.findViewById(R.id.editChip_bbq)

        //chip feature
        chipFeatures01 = view.findViewById(R.id.chipFedit_1)
        chipFeatures02 = view.findViewById(R.id.chipFedit_2)
        chipFeatures03 = view.findViewById(R.id.chipFedit_3)
        chipFeatures04 = view.findViewById(R.id.chipFedit_4)
        chipFeatures05 = view.findViewById(R.id.chipFedit_5)

        //switch
        serviceSwitch = view.findViewById(R.id.editOtherServiceSwitch)
        mealSwitch = view.findViewById(R.id.editMealsDetailsSwitch)

        //Edit Text
        etBookingDateRange = view.findViewById(R.id.tv_editBookingDate)
        etHeadCount = view.findViewById(R.id.et_editHeadCount)
        etRoomCount = view.findViewById(R.id.et_editRoomCount)
        etFName = view.findViewById(R.id.eti_editFname)
        etSName = view.findViewById(R.id.eti_editSname)
        etAddress = view.findViewById(R.id.eti_editAddress)
        etArea = view.findViewById(R.id.eti_editArea)
        etCity = view.findViewById(R.id.eti_editCity)
        etNIC = view.findViewById(R.id.eti_editNIC)
        etSpecialNote = view.findViewById(R.id.editEti_specialNote)
        etContact = view.findViewById(R.id.eti_editContactNumber)

        //text view
        finalDialogTitle = view.findViewById(R.id.pe_titleMessage)

        //btn click
        btnSaveChangers.setOnClickListener(this)
        btnRemoveBooking.setOnClickListener(this)
        btnGoUpComingMenu.setOnClickListener(this)

        //load data
        loadBookingDetails(bookingID)

        //layout change
        fillingLayout.visibility = View.VISIBLE
        successLayout.visibility = View.GONE

        //other service layout visibility
        serviceSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d(TAG,"Other service TAB load")
            if (isChecked) {
                serviceLayout.visibility = View.VISIBLE
            } else {
                serviceLayout.visibility = View.GONE
            }
        }

        //meals details layout visibility
        mealSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d(TAG,"Meals details TAB load")
            if (isChecked){
                mealLayout.visibility = View.VISIBLE
            }else{
                mealLayout.visibility = View.GONE
            }
        }


        //chip meals feedback
        chipMeal01.setOnCheckedChangeListener { chip, ischecked ->
            Cp1Status = ischecked
        }
        chipMeal02.setOnCheckedChangeListener { chip, ischecked ->
            Cp2Status = ischecked
        }
        chipMeal03.setOnCheckedChangeListener { chip, ischecked ->
            Cp3Status = ischecked
        }
        chipMeal04.setOnCheckedChangeListener { chip, ischecked ->
            Cp4Status = ischecked
        }
        chipMeal05.setOnCheckedChangeListener { chip, ischecked ->
            Cp5Status = ischecked
        }
        chipMeal06.setOnCheckedChangeListener { chip, ischecked ->
            Cp6Status = ischecked
        }

        //chip features
        chipFeatures01.setOnCheckedChangeListener { chip, ischecked ->
            chipFeature1 = ischecked
        }
        chipFeatures02.setOnCheckedChangeListener { chip, ischecked ->
            chipFeature2 = ischecked
        }
        chipFeatures03.setOnCheckedChangeListener { chip, ischecked ->
            chipFeature3 = ischecked
        }
        chipFeatures04.setOnCheckedChangeListener { chip, ischecked ->
            chipFeature4 = ischecked
        }
        chipFeatures05.setOnCheckedChangeListener { chip, ischecked ->
            chipFeature5 = ischecked
        }

        return view
    }

    private fun updateBooking(){
        if (fName.isNotEmpty()||contact.isNotEmpty()||NIC.isNotEmpty()||dateRange.isNotEmpty()){
            //read data
            captureData()
        }else{
            Snackbar.make(contentView,"Please full-fill all required details..!",Snackbar.LENGTH_SHORT).show()
            Log.e(TAG,"User not full fill all data")
        }
    }


    private fun captureData(){
        dateRange = etBookingDateRange.text.toString()
        headCount = etHeadCount.text.toString()
        roomCount = etRoomCount.text.toString()
        bookingCategory = "Permanent"
        bookingBackGround
        fName = etFName.text.toString()
        sName = etSName.text.toString()
        address = etAddress.text.toString()
        area = etArea.text.toString()
        city = etCity.text.toString()
        contact = etContact.text.toString()
        NIC = etNIC.text.toString()
        specialNote = etSpecialNote.text.toString()

        //count room
        //countRoom()
        selectedRooms

        //count meal
        countMeal()

        //count Features
        countFeatures()


        val data = uploadData(selectedRooms)

        Log.e(TAG, "data - $data")
        Log.e(TAG, "data - $data")

        //make database reference
        if (makeDataBaseReference()){
            addBookingReference?.setValue(data)
                ?.addOnCompleteListener { task ->
                    val result = task.result
                    if (task.isSuccessful){

                        //close dialog
                        //loadingDialogClose()

                        //Log.e(TAG, "Booking Successfully")
                        Snackbar.make(contentView, R.string.booking_successfully, Snackbar.LENGTH_SHORT)
                            .show()

                        //layout change
                        fillingLayout.visibility = View.GONE
                        successLayout.visibility = View.VISIBLE

                        //move to manage booking
                        //navigateToManageBooking()

                    }else{
                        //close dialog
                        //loadingDialogClose()

                        Log.e(TAG, "Failed to create booking.")
                        Snackbar.make(contentView, R.string.booking_data_upload_fail, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
                ?.addOnFailureListener { task ->
                    if (task.message!=null){
                        //close dialog
                        //loadingDialogClose()
                        Log.e(TAG, "data upload fail in addOnFailureListener")
                        Snackbar.make(contentView,task.message.toString(), Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
        }else{
            //close dialog
            //loadingDialogClose()
            Log.e(TAG, "data upload fail in database reference null")
            Snackbar.make(contentView,R.string.booking_data_upload_fail, Snackbar.LENGTH_SHORT)
                .show()
        }

    }

    private fun removeBookingRecord(bookingID: String?){
        if (bookingID!=null){
            databaseReference = firebaseDatabaseReference.child("Booking Details").child("Appointment Reservation")
                .child(bookingID)

            databaseReference.removeValue().addOnCompleteListener { Task ->
                if(Task.isSuccessful){
                    Snackbar.make(contentView,"Successfully delete record.",Snackbar.LENGTH_SHORT).show()

                    //layout change
                    fillingLayout.visibility = View.GONE
                    successLayout.visibility = View.VISIBLE

                    finalDialogTitle.setText("Delete SuccessFully.")
                }
            }.addOnFailureListener { OnFailureListener{
                Snackbar.make(contentView,"Delete unsuccessfully.",Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
            } }
        }
    }

    private fun makeDataBaseReference():Boolean{
        addBookingReference = firebaseDatabaseReference.child("Booking Details")
            .child("Appointment Reservation").child(bookingID!!)

        return true
    }

    private fun loadBookingDetails(userID:String?){

        if (userID!=null){
            databaseReference = firebaseDatabaseReference.child("Booking Details").child("Appointment Reservation")
                .child(userID)

            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot!=null){
                        //get data
                        val data = snapshot.getValue(SubmitBooking::class.java) as SubmitBooking
                        Log.e(TAG, "User detect :"+data.uniqueKey)
                        //get data
                        etBookingDateRange.setText(data.getBooking_dateRange())
                        etFName.setText(data.getFirstName())
                        etSName.setText(data.getSecondName())
                        etRoomCount.setText(data.getRoomsCount())
                        etHeadCount.setText(data.getHeadCount())
                        etAddress.setText(data.getAddress())
                        etArea.setText(data.getArea())
                        etCity.setText(data.getCity())
                        etNIC.setText(data.getIdentity())
                        etContact.setText(data.getContact())
                        etSpecialNote.setText(data.getSpecialNote())

                        //get other data
                        if (data.bookingBackground!=null){
                            bookingBackGround = data.getBookingBackground()
                        }

                        fName = data.getFirstName()
                        contact = data.getContact()
                        NIC = data.getIdentity()
                        checkIn = data.getCheckIn()
                        checkOut = data.getCheckOut()
                        dateRange = data.getBooking_dateRange()
                        bookingType = data.getBookingType()
                        mealStrings = data.getMealList()
                        serviceString = data.getFeatureList()
                        selectedRooms = data.getRoomReserve()

                        //display features
                        if (data.getFeatureList()!=null){
                            setChipFeature(data.getFeatureList())
                        }

                        if (data.getMealList()!=null){
                            setMealDetails(data.getMealList())
                        }


                    }else{
                        //userName.text = data.userName
                        Log.e(TAG, "Data not detect : data null")
                        Snackbar.make(contentView,"Please try again.", Snackbar.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, error.message)
                    Snackbar.make(contentView,"Please try again.", Snackbar.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

            })
        }
    }

    private fun uploadData(roomReserve: RoomReserve): SubmitBooking {
        return SubmitBooking(
            dateRange,
            checkIn,
            checkOut,
            headCount,
            roomCount,
            bookingType,
            bookingBackGround,
            fName,
            sName,
            address,
            area,
            city,
            contact,
            NIC,
            specialNote,
            mealOrderedGSON,
            featureReservationGSON,
            roomReservationGSON,
            bookingID,
            roomReserve
        )
    }


    private fun countFeatures(){
        val featureReservation = FeatureReservation(chipFeature1,chipFeature2,chipFeature3,chipFeature4,chipFeature5,chipFeature6)
        val gson = Gson()
        featureReservationGSON = gson.toJson(featureReservation)
    }

    private fun countMeal(){
        val mealChooser = MealChooser(Cp1Status,Cp2Status,Cp3Status,Cp4Status,Cp5Status,Cp6Status)
        val gson = Gson()
        mealOrderedGSON = gson.toJson(mealChooser)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.editBtn_submitBooked -> updateBooking()
            R.id.pe_btnGoQuick -> goToUpComingBooking()
            R.id.editBtn_cancelBooked ->alertApproveDialog("Are you Sure ?","If you delete this booking record you can't recover again. Please make sure do you want to delete it..!")
        }
    }

    private fun goToUpComingBooking(){
        startActivity(Intent(this.requireContext(),UpComingBookingActivity::class.java))
        Log.d(TAG,"Move to Upcoming Activity")
    }


    private fun setChipFeature(chipString:String){
        val gson = Gson()
        val featureChips = gson.fromJson(chipString,FeatureReservation::class.java)

        //set chips
        if (featureChips!=null){
            if (featureChips.Chip01==true){
                chipFeatures01.isChecked = true
                chipFeature1 = true
            }else if (featureChips.Chip02==true){
                chipFeatures02.isChecked = true
                chipFeature2 = true
            }else if(featureChips.chip03==true){
                chipFeatures03.isChecked = true
                chipFeature3 = true
            }else if(featureChips.Chip04==true){
                chipFeatures04.isChecked = true
                chipFeature4 = true
            }else if (featureChips.Chip05==true){
                chipFeatures05.isChecked = true
                chipFeature5 = true
            }else if (featureChips.Chip06==true){
                chipFeatures06.isChecked = true
                chipFeature6 = true
            }
        }
    }

    private fun setMealDetails(mealString:String){
        val gson = Gson()
        val mealChips = gson.fromJson(mealString,MealChooser::class.java)

        if (mealChips!=null){
            if (mealChips.Chip01==true){
                chipMeal01.isChecked = true
                Cp1Status = true
            }else if (mealChips.Chip02==true){
                chipMeal02.isChecked = true
                Cp2Status = true
            }else if (mealChips.Chip03){
                chipMeal03.isChecked = true
                Cp3Status = true
            }else if (mealChips.Chip04){
                chipMeal04.isChecked = true
                Cp4Status = true
            }else if (mealChips.Chip05==true){
                chipMeal05.isChecked = true
                Cp5Status = true
            }else if (mealChips.Chip06==true){
                chipMeal05.isChecked = true
                Cp6Status = true
            }
        }
    }

    private fun alertApproveDialog(title: String,description: String){

        val builder = AlertDialog.Builder(context,R.style.CustomAlertDialog2)
            .create()
        val view = layoutInflater.inflate(R.layout.custom_alert_dialog_approve,null)

        //set title
        val titleText = view.findViewById<TextView>(R.id.alertTitleText)
        titleText.text = title

        //set description
        val textDescription = view.findViewById<TextView>(R.id.alertDescriptionText)
        textDescription.text = description

        //set negative button
        val  negativeButton = view.findViewById<Button>(R.id.btnDialogNegative)
        negativeButton.setOnClickListener {
            builder.dismiss()

        }
        //set positive
        val positiveButton = view.findViewById<Button>(R.id.btnDialogPositive)
        positiveButton.setOnClickListener{
            removeBookingRecord(bookingID)
        }

        builder.setView(view)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }
}