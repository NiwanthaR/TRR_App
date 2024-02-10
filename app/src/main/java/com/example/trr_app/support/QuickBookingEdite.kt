package com.example.trr_app.support

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.example.trr_app.R
import com.example.trr_app.model.QuickBooking
import com.example.trr_app.model.RoomReserve
import com.example.trr_app.model.User
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
    private var userID: String?
):BottomSheetDialogFragment() {

    //dialog
    private lateinit var dialog : BottomSheetDialog
    //text views
    private lateinit var customerName : TextInputEditText
    private lateinit var headCount : TextInputEditText
    private lateinit var contactNumber : TextInputEditText
    private lateinit var specialNote : TextInputEditText
    private lateinit var btnSubmit : MaterialButton
    private lateinit var btnDelete : MaterialButton

    //relative
    private lateinit var contentView : RelativeLayout

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
                dialog.findViewById<FrameLayout>(R.id.quickBookingFrameLayout)
            BottomSheetBehavior.from(bottomSheet!!).state =
                BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet!!).skipCollapsed = true
            BottomSheetBehavior.from(bottomSheet!!).isHideable = true
        }
        return bottomSheetDialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_quick_booking_edit,container,false)

        customerName = view.findViewById(R.id.edite_quickCustomerName)
        headCount = view.findViewById(R.id.edite_headCount)
        contactNumber = view.findViewById(R.id.edite_customerContact)
        specialNote = view.findViewById(R.id.edite_quickSpecialNote)

        btnDelete = view.findViewById(R.id.edite_btnQuickDelete)
        btnSubmit = view.findViewById(R.id.edite_btnQuickSave)

        contentView = view.findViewById(R.id.quickBookingRelative)


        btnDelete.setOnClickListener {
            removeData(userID)
        }

        btnSubmit.setOnClickListener {
            resubmitData(userID)
        }

        getReadValue(userID)

        return view
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

    private fun resubmitData(userID: String?){
        if (name!=null && contact!= null){
            if (userID!=null){
                databaseReference = firebaseDatabaseReference.child("Booking Details").child("Appointment Reservation")
                    .child(userID)

                databaseReference.setValue(QuickBooking(name,dateRange,checkIn,checkOut,contact,count,note,"Temporary",uniqueKey,roomReserve)).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Snackbar.make(contentView,"Successfully updated.",Snackbar.LENGTH_SHORT).show()
                        dialog.dismiss()
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
                    Snackbar.make(contentView,"Successfully delete record.",Snackbar.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }.addOnFailureListener { OnFailureListener{
                Snackbar.make(contentView,"Delete unsuccessfully.",Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
            } }
        }
    }
}

