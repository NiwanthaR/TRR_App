package com.example.trr_app.support

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.trr_app.R
import com.example.trr_app.adaptor.RoomAdaptor
import com.example.trr_app.model.RoomReserve
import com.example.trr_app.view.ManageUI.AddQuickBooking
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class QuickBookingDialog(roomAdaptor: RoomAdaptor?,startDate:String?,endDate:String?,dateRange:String?): BottomSheetDialogFragment() {

    private lateinit var addQuickBooking: Unit
    private lateinit var customerName : TextInputEditText
    private lateinit var headCount : TextInputEditText
    private lateinit var contactNumber : TextInputEditText
    private lateinit var specialNote : TextInputEditText
    private lateinit var btnSubmit : MaterialButton

    private var name: String? = null
    private var count: String? = null
    private var contact : String?  = null
    private var note: String? = null

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

    private var roomAdaptorList = roomAdaptor

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

        btnSubmit.setOnClickListener(View.OnClickListener {
            readValue()
        })
//        algoButton.setOnClickListener {
//            Toast.makeText(activity, "Algorithm Shared", Toast.LENGTH_SHORT).show()
//            dismiss()
//        }
//
//        courseButton.setOnClickListener {
//            Toast.makeText(activity, "Course Shared", Toast.LENGTH_SHORT).show()
//            dismiss()
//        }

        return view
    }

    private fun readValue(){
        name = customerName.text.toString()
        contact = contactNumber.text.toString()
        count = headCount.text.toString()
        note = specialNote.text.toString()



        if (contact!=null){
            val room = getSelectRooms()
            addQuickBooking = AddQuickBooking().captureQuickBookData(name,contact,count,note,room,startDate,endtDate,dateRange)
            dialog.dismiss()
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
}