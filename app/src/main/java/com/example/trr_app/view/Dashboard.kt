package com.example.trr_app.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.support.QuickBookingDialog
import com.example.trr_app.view.ManageUI.ManageActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView


class Dashboard : BaseActivity(),OnClickListener {

    private val manageUIDashboard : RelativeLayout
        get() = findViewById(R.id.manageUILayout)

    private val quickBookingDashboard : RelativeLayout
        get() = findViewById(R.id.layoutQuickBooking)

    private val userLogoutDashboard : RelativeLayout
        get() = findViewById(R.id.userLogoutLayout)

    private val bookingHistoryCard : MaterialCardView
        get() = findViewById(R.id.historyCardView)

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        manageUIDashboard.setOnClickListener(this)
        quickBookingDashboard.setOnClickListener(this)
        bookingHistoryCard.setOnClickListener(this)
        userLogoutDashboard.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.manageUILayout -> startActivity(Intent(this,ManageActivity::class.java))
            R.id.layoutQuickBooking ->showBottomDialog()
            R.id.historyCardView -> loadingProgressDialog(this)
            R.id.userLogoutLayout -> userSignOut()
        }
    }

    private fun showBottomDialog(){
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.dialog_quick_booking)
////
////            //val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
////            //val share = bottomSheetDialog.findViewById<LinearLayout>(R.id.shareLinearLayout)
////            //val upload = bottomSheetDialog.findViewById<LinearLayout>(R.id.uploadLinearLayout)
////            //val download = bottomSheetDialog.findViewById<LinearLayout>(R.id.download)
////            //val delete = bottomSheetDialog.findViewById<LinearLayout>(R.id.delete)
        val standardBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetDialog.findViewById<FrameLayout>(R.id.quickBookingFrameLayout)!!)
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.show()

        //val bottomSheet = QuickBookingDialog()
        //bottomSheet.show(supportFragmentManager, "ModalBottomSheet")

    }

    private fun userSignOut(){
        firebaseAuth.signOut()
        finish()
        startActivity(Intent(this@Dashboard,SplashScreen::class.java))
    }
}