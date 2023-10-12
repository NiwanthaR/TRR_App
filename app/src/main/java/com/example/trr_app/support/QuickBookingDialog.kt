package com.example.trr_app.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trr_app.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class QuickBookingDialog: BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_quick_booking, container, false)

        //val algoButton = view.findViewById<Button>(R.id.algo_button)
        //val courseButton = view.findViewById<Button>(R.id.course_button)

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
}