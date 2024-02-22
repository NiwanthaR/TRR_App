package com.example.trr_app.support

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.trr_app.R
import com.example.trr_app.adaptor.RoomAdaptor
import com.example.trr_app.model.QuickBooking
import com.example.trr_app.model.RoomReserve
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class QuickBookingDialog(roomAdaptor: RoomAdaptor?,startDate:String?,endDate:String?,dateRange:String?): BottomSheetDialogFragment(),OnClickListener {

    //Edite text
    private lateinit var addQuickBooking: Unit
    private lateinit var customerName : TextInputEditText
    private lateinit var headCount : TextInputEditText
    private lateinit var contactNumber : TextInputEditText
    private lateinit var specialNote : TextInputEditText
    private lateinit var btnSubmit : MaterialButton

    //strings
    private var name: String? = null
    private var count: String? = null
    private var contact : String?  = null
    private var note: String? = null

    //Firebase
    val firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    val firebaseDatabaseReference : DatabaseReference = firebaseDatabase.reference.child("TRRApp")
    //database reference
    private var databaseReference : DatabaseReference = firebaseDatabaseReference

    //TAG Name
    private val TAG: String = QuickBookingDialog::class.java.name

    //alert dialog
    private var successAlertDialog = BookingSuccessDialog()

    //Date
    private var startDate: String? = startDate
    private var endtDate: String? = endDate
    private var dateRange: String? = dateRange

    //room flag
    private var R001 : Boolean = false
    private var R002 : Boolean = false
    private var R003 : Boolean = false
    private var R004 : Boolean = false
    private var R005 : Boolean = false
    private var R006 : Boolean = false
    private var R007 : Boolean = false

    //dialog
    private var roomAdaptorList = roomAdaptor

    //layout
    private lateinit var quickRelative : RelativeLayout
    private lateinit var quickSuccessDialog: RelativeLayout

    //btn
    private lateinit var quickGoBackBtn : MaterialButton

    //textview
    private lateinit var quickSuccessTitle : TextView

    private lateinit var dialog : BottomSheetDialog

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
        //return super.onCreateDialog(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_quick_booking, container, false)


        customerName = view.findViewById(R.id.quickCustomerName)
        contactNumber = view.findViewById(R.id.customerContact)
        headCount = view.findViewById(R.id.headCount)
        specialNote = view.findViewById(R.id.quickSpecialNote)
        btnSubmit = view.findViewById(R.id.btnQuickSubmit)
        //val courseButton = view.findViewById<Button>(R.id.course_button)

        //layout
        quickRelative = view.findViewById(R.id.quickBookingRelative)
        quickSuccessDialog = view.findViewById(R.id.successfullyLayoutNewQuick)

        //btn
        quickGoBackBtn = view.findViewById(R.id.nq_goBackBtn)

        //Textview
        quickSuccessTitle = view.findViewById(R.id.successNewQuick_title)

        //navigate layout
        btnSubmit.setOnClickListener(View.OnClickListener {
            readValue()
        })
//        algoButton.setOnClickListener {
//            Toast.makeText(activity, "Algorithm Shared", Toast.LENGTH_SHORT).show()
//            dismiss()
//        }
        quickGoBackBtn.setOnClickListener(this)
//
//        courseButton.setOnClickListener {
//            Toast.makeText(activity, "Course Shared", Toast.LENGTH_SHORT).show()
//            dismiss()
//        }

        //navigate ui
        quickRelative.visibility = View.VISIBLE
        quickSuccessDialog.visibility = View.GONE

        return view
    }

    private fun readValue(){
        name = customerName.text.toString()
        contact = contactNumber.text.toString()
        count = headCount.text.toString()
        note = specialNote.text.toString()



        if (contact!=null){
            val room = getSelectRooms()
            //addQuickBooking = AddQuickBooking().captureQuickBookData(name,contact,count,note,room,startDate,endtDate,dateRange)
            //dialog.dismiss()

            submitQuickBooking(name,contact,count,note,room,startDate,endtDate,dateRange)

        }
    }
    private fun getSelectRooms(): RoomReserve {

        clearRM()
        if (roomAdaptorList!=null){

            val selectItem = roomAdaptorList!!.getSelectedList()
            println(selectItem)

            //navigate to Quick Booking
            var count = selectItem.size
            val roomList = ArrayList<String>()

            for (i in 0..<count) {
                roomList.add(selectItem[i].room_unic_code)
            }

            if (roomList.contains("R001")){R001=true}
            if (roomList.contains("R002")){R002=true}
            if (roomList.contains("R003")){R003=true}
            if (roomList.contains("R004")){R004=true}
            if (roomList.contains("R005")){R005=true}
            if (roomList.contains("R006")){R006=true}
            if (roomList.contains("R007")){R007=true}

//            for (i in count downTo 1) {
//                if (roomList[i-1]=="R001"){
//                    R001 = true
//                    roomList.remove("R001")
//                }
//                else if (roomList[i-1]=="R002"){
//                    R002 = true
//                    roomList.remove("R002")
//                }
//                else if ( roomList[i-1]=="R003"){
//                    R003 = true
//                    roomList.remove("R003")
//                }
//                else if ( roomList[i-1]=="R004"){
//                    R004 = true
//                    roomList.remove("R004")
//                }
//                else if (roomList[i-1]=="R005"){
//                    R005 = true
//                    roomList.remove("R005")
//                }
//                else if (roomList[i-1]=="R006"){
//                    R006 = true
//                    roomList.remove("R006")
//                }
//                else if (roomList[i-1]=="R007"){
//                    R007 = true
//                    roomList.remove("R007")
//                }
//                println("detect$i")
//            }

//            for (i in 0..<count) {
//                if (roomList[i]=="R001"){
//                    R001 = true
//                    roomList.remove("R001")
//                }
//                else if (roomList[i]=="R002"){
//                    R002 = true
//                    roomList.remove("R002")
//                }
//                else if ( roomList[i]=="R003"){
//                    R003 = true
//                    roomList.remove("R003")
//                }
//                else if ( roomList[i]=="R004"){
//                    R004 = true
//                    roomList.remove("R004")
//                }
//                else if (roomList[i]=="R005"){
//                    R005 = true
//                    roomList.remove("R005")
//                }
//                else if (roomList[i]=="R006"){
//                    R006 = true
//                    roomList.remove("R006")
//                }
//                else if (roomList[i]=="R007"){
//                    R007 = true
//                    roomList.remove("R007")
//                }
//                println("detect$i")
//            }
            println("X")
            return RoomReserve(R001,R002,R003,R004,R005,R006,R007)
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bottomSheetBehavior = BottomSheetBehavior.from((view))
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED


    }

    private fun submitQuickBooking(name:String?,contact:String?,headCount:String?,specialNote:String?,room:RoomReserve?,startDate: String?,endDate:String?,dateRange:String?){
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

                        //Toast.makeText(this@QuickBookingDialog.context,R.string.booking_successfully, Toast.LENGTH_SHORT).show()

                        //navigate ui
                        quickRelative.visibility = View.GONE
                        quickSuccessDialog.visibility = View.VISIBLE

                        //successAlertDialog.showDialog(this@QuickBookingDialog.activity,"")
                        //dialog.dismiss()

                    }else{
                        Log.e(TAG, "Data upload not success.")
                        Toast.makeText(this.requireContext(),R.string.data_upload_failed, Toast.LENGTH_SHORT)
                            .show()
                    }

                }.addOnFailureListener { OnFailureListener{
                    Log.e(TAG, "Data upload not success.")
                    Toast.makeText(this.requireContext(),R.string.data_upload_failed, Toast.LENGTH_SHORT)
                        .show()
                }
                }
        }else{
            Log.e(TAG, "Selected room null")
            Toast.makeText(this.requireContext(),R.string.data_upload_failed, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.nq_goBackBtn -> dialog.dismiss()
        }
    }
}