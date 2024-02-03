package com.example.trr_app.view.ManageUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.google.android.material.card.MaterialCardView

class ManageBookingActivity : BaseActivity(),OnClickListener {

    private val addNewBookingUI : RelativeLayout
        get() = findViewById(R.id.layoutAddBooking)
    private val completeQuickBooking : RelativeLayout
        get() = findViewById(R.id.layoutCompleteQuickBooking)
    private val upCommingBooking : RelativeLayout
        get() = findViewById(R.id.layoutUpCommingBooking)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_booking)

        addNewBookingUI.setOnClickListener(this)
        completeQuickBooking.setOnClickListener(this)
        upCommingBooking.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id){
            R.id.layoutAddBooking -> moveToAddBooking()
            R.id.layoutCompleteQuickBooking -> moveToCompleteBooking()
            R.id.layoutUpCommingBooking -> moveToUpComingBooking()
        }
    }

    private fun moveToManageBooking(){
        startActivity(Intent(this@ManageBookingActivity,ManageActivity::class.java))
    }
    private fun moveToCompleteBooking(){
        startActivity(Intent(this@ManageBookingActivity,QuickBookingActivity::class.java))
    }
    private fun moveToAddBooking(){
        startActivity(Intent(this@ManageBookingActivity,AddBooking::class.java))
    }
    private fun moveToUpComingBooking(){
        startActivity(Intent(this@ManageBookingActivity,UpComingBookingActivity::class.java))
    }
}