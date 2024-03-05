package com.example.trr_app.support

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.trr_app.R
import com.example.trr_app.model.QuickBooking
import com.example.trr_app.model.RoomReserve
import com.example.trr_app.model.User
import com.example.trr_app.view.ManageUI.UpComingBookingActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class QuickBookingEdite(//Strings
    private var bookingID: String?,private var pathNumber:Int
):BottomSheetDialogFragment(),OnClickListener {

    //dialog
    private lateinit var dialog : BottomSheetDialog
    private lateinit var dialogTitle : TextView
    //text views
    private lateinit var customerName : TextInputEditText
    private lateinit var headCount : TextInputEditText
    private lateinit var contactNumber : TextInputEditText
    private lateinit var specialNote : TextInputEditText

    //Text view
    private lateinit var successDialogText : TextView

    //Btn
    private lateinit var btnSubmit : MaterialButton
    private lateinit var btnDelete : MaterialButton
    private lateinit var successDialogBtn : MaterialButton

    //relative
    private lateinit var contentView : RelativeLayout
    private lateinit var quickSuccessLayout : RelativeLayout

    //linear
    private lateinit var bottomSpaceLayout : LinearLayout
    private lateinit var bottomButtonLayout : LinearLayout

    //Firebase
    val firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    val firebaseDatabaseReference : DatabaseReference = firebaseDatabase.reference.child("TRRApp")
    //database reference
    private var databaseReference : DatabaseReference = firebaseDatabaseReference

    //TAG Name
    private val TAG: String = QuickBookingEdite::class.java.name

    private var name: String? = null
    private var count: String? = null
    private var contact : String?  = null
    private var note: String? = null
    private var checkIn : String? = null
    private var checkOut : String? = null
    private var dateRange : String? = null
    private var bookingType : String? = null
    private var uniqueKey : String? = null
    //RoomsReserve
    private var roomReserve : RoomReserve? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog ->
            this.dialog = dialog as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<FrameLayout>(R.id.quickBookingFrameLayoutEdit)
            BottomSheetBehavior.from(bottomSheet!!).state =
                BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet!!).skipCollapsed = true
            BottomSheetBehavior.from(bottomSheet!!).isHideable = true
        }
        return bottomSheetDialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_quick_booking_edit,container,false)

        //Edite text
        customerName = view.findViewById(R.id.edite_quickCustomerName)
        headCount = view.findViewById(R.id.edite_headCount)
        contactNumber = view.findViewById(R.id.edite_customerContact)
        specialNote = view.findViewById(R.id.edite_quickSpecialNote)

        //Text view
        successDialogText = view.findViewById(R.id.successQuick_title)
        dialogTitle = view.findViewById(R.id.quickBookingBottomTitle_text)
        //btn
        btnDelete = view.findViewById(R.id.edite_btnQuickDelete)
        btnSubmit = view.findViewById(R.id.edite_btnQuickSave)
        successDialogBtn = view.findViewById(R.id.qe_goBackBtn)

        //content view
        contentView = view.findViewById(R.id.quickBookingRelative)

        //relative layout
        quickSuccessLayout = view.findViewById(R.id.successfullyLayoutQuick)
        bottomSpaceLayout = view.findViewById(R.id.quickBookingEditeSpaceLayout)
        bottomButtonLayout = view.findViewById(R.id.quickBookingEditeBtnLayout)

        //UI assign
        quickSuccessLayout.visibility = View.GONE
        contentView.visibility = View.VISIBLE

        btnDelete.setOnClickListener (this)
        btnSubmit.setOnClickListener(this)
        successDialogBtn.setOnClickListener(this)

        getReadValue(bookingID)

        if (pathNumber==2){
            bottomButtonLayout.isVisible = false
            bottomSpaceLayout.isVisible = true
            dialogTitle.text = "Already Booked By"
            componentEditable(false)

        }else{
            bottomButtonLayout.isVisible = true
            bottomSpaceLayout.isVisible = false
            dialogTitle.text = "Booking Details"
            componentEditable(true)
        }

        return view
    }

    private fun componentEditable(boolean: Boolean){
        if (!boolean){
            customerName.inputType = InputType.TYPE_NULL
            headCount.inputType = InputType.TYPE_NULL
            specialNote.inputType = InputType.TYPE_NULL
            contactNumber.inputType = InputType.TYPE_NULL
        }else{
            customerName.inputType = InputType.TYPE_CLASS_TEXT
            headCount.inputType = InputType.TYPE_CLASS_TEXT
            specialNote.inputType = InputType.TYPE_CLASS_TEXT
            contactNumber.inputType = InputType.TYPE_CLASS_TEXT
        }
    }

    private fun getReadValue(userID:String?){

        if (userID!=null){
            databaseReference = firebaseDatabaseReference.child("Booking Details").child("Appointment Reservation")
                .child(userID)

            databaseReference.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot!=null){
                        //get data
                        val data = snapshot.getValue(QuickBooking::class.java) as QuickBooking
                        Log.e(TAG, "User detect :"+data.uniqueKey)
                        //get data
                        customerName.setText(data.getCustomerName())
                        contactNumber.setText(data.getCustomerContact())
                        headCount.setText(data.getHeadCount())
                        specialNote.setText(data.getSpecialNote())

                        name = data.getCustomerName()
                        contact = data.getCustomerContact()
                        count = data.getHeadCount()
                        note = data.getSpecialNote()
                        checkIn = data.getCheckIn()
                        checkOut = data.getCheckOut()
                        bookingType = data.getBookingType()
                        uniqueKey = data.getUniqueKey()
                        dateRange = data.getBooking_dateRange()
                        roomReserve = data.getRoomReserve()

                    }else{
                        //userName.text = data.userName
                        Log.e(TAG, "Data not detect : data null")
                        Snackbar.make(contentView,"Please try again.",Snackbar.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, error.message)
                    Snackbar.make(contentView,"Please try again.",Snackbar.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

            })
        }
    }

    private fun readDataChangers(userID: String?){
        name = customerName.text.toString()
        contact = contactNumber.text.toString()
        count = headCount.text.toString()
        note = specialNote.text.toString()

        resubmitData(userID)
    }

    private fun resubmitData(userID: String?){
        if (name!=null && contact!= null){
            if (userID!=null){
                databaseReference = firebaseDatabaseReference.child("Booking Details").child("Appointment Reservation")
                    .child(userID)

                databaseReference.setValue(QuickBooking(name,dateRange,checkIn,checkOut,contact,count,note,"Temporary",uniqueKey,roomReserve)).addOnCompleteListener { task ->
                    if (task.isSuccessful){

                        //Snackbar.make(contentView,"Successfully updated.",Snackbar.LENGTH_SHORT).show()

                        //UI assign
                        quickSuccessLayout.visibility = View.VISIBLE
                        contentView.visibility = View.GONE

                        //dialog.dismiss()
                    }
                }.addOnFailureListener { OnFailureListener{
                    Snackbar.make(contentView,"Details uploading failed.",Snackbar.LENGTH_SHORT).show()
                } }
            }else{
                Snackbar.make(contentView,"Please try again.",Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }

    private fun removeData(userID: String?){
        if (userID!=null){
            databaseReference = firebaseDatabaseReference.child("Booking Details").child("Appointment Reservation")
                .child(userID)

            databaseReference.removeValue().addOnCompleteListener { Task ->
                if(Task.isSuccessful){
                    //Snackbar.make(contentView,"Successfully delete record.",Snackbar.LENGTH_SHORT).show()
                    //dialog.dismiss()

                    successDialogText.text = "Booking delete Successfully."
                    //UI assign
                    quickSuccessLayout.visibility = View.VISIBLE
                    contentView.visibility = View.GONE

                }
            }.addOnFailureListener { OnFailureListener{
                Snackbar.make(contentView,"Delete unsuccessfully.",Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
            } }
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
            removeData(bookingID)
        }

        builder.setView(view)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    private fun goBackToMenu(){
        //startActivity(Intent(this.requireContext(),UpComingBookingActivity::class.java))
        dialog.dismiss()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.qe_goBackBtn -> goBackToMenu()
            R.id.edite_btnQuickSave -> readDataChangers(bookingID)
            R.id.edite_btnQuickDelete -> alertApproveDialog("Are you Sure ?","If you delete this booking record you can't recover again. Please make sure do you want to delete it..!")
        }
    }
}


