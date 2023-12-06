package com.example.trr_app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.model.User
import com.example.trr_app.view.ManageUI.AddQuickBooking
import com.example.trr_app.view.ManageUI.ManageActivity
import com.example.trr_app.view.ManageUI.ManageBookingActivity
import com.example.trr_app.view.ManageUI.QuickBookingActivity
import com.example.trr_app.view.SettingsUI.UserSettings
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.Calendar


class Dashboard : BaseActivity(),OnClickListener {

    private val TAG: String
            = Dashboard::class.java.name
    private val userName : TextView
        get() = findViewById(R.id.dashboardUserName)
    private val userProfile : ImageView
        get() = findViewById(R.id.userProfilePic)
    private val userWish :TextView
        get() = findViewById(R.id.dashboardWish)
    private val manageUIDashboard : RelativeLayout
        get() = findViewById(R.id.manageUILayout)

    private val quickBookingDashboard : RelativeLayout
        get() = findViewById(R.id.layoutQuickBooking)

    private val userLogoutDashboard : RelativeLayout
        get() = findViewById(R.id.userLogoutLayout)

    private val bookingHistoryCard : MaterialCardView
        get() = findViewById(R.id.historyCardView)

    private val settingUICard : MaterialCardView
        get() = findViewById(R.id.settings_card)

    //database reference
    private var databasereference = firebaseDatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        manageUIDashboard.setOnClickListener(this)
        quickBookingDashboard.setOnClickListener(this)
        bookingHistoryCard.setOnClickListener(this)
        userLogoutDashboard.setOnClickListener(this)
        settingUICard.setOnClickListener(this)

        //load Data
        loadData()
    }

    private fun loadData(){
        loadingProgressDialog(this@Dashboard)
        //set Wish
        setWishMessage()
        //load profile date
        loadUserData()
        //load image
        getProfileImage()
        loadingDialogClose()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.manageUILayout -> startActivity(Intent(this,ManageBookingActivity::class.java))
            R.id.layoutQuickBooking ->moveToQuickBooking()
            R.id.historyCardView -> loadingProgressDialog(this)
            R.id.userLogoutLayout -> userSignOut()
            R.id.settings_card -> loadSettingsCard()
        }
    }

    private fun moveToQuickBooking(){
        startActivity(Intent(this@Dashboard,AddQuickBooking::class.java))
    }

    private fun loadSettingsCard(){
        startActivity(Intent(this@Dashboard,UserSettings::class.java))
    }

    private fun userSignOut(){
        firebaseAuth.signOut()
        finish()
        startActivity(Intent(this@Dashboard,SplashScreen::class.java))
    }

    private fun loadUserData(){
        databasereference = firebaseDatabaseReference.child(getString(R.string.users_data_location))
            .child(getString(R.string.users_profile_location)).child(
                firebaseUser!!.uid
            )

        databasereference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot!=null){
                    //get data
                    val data = snapshot.getValue(User::class.java) as User
                    Log.e(TAG, "User detect :"+data.userName)
                    //set use name
                    userName.text = data.userName
                }
                //userName.text = data.userName
                Log.e(TAG, "User not detect : data null")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "User loading failed")
            }

        })
    }

    private fun getProfileImage(){
        val storageReference = firebaseStorage.reference.child(getString(R.string.users_data_location))
            .child(getString(R.string.users_profile_location)).child(
                firebaseUser!!.uid
            )

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get().load(uri).fit().centerCrop().into(userProfile)
        }
    }

    private fun setWishMessage(){
        val currentHour = Calendar.getInstance().get(Calendar.HOUR)
        if (currentHour > 20 && currentHour < 4){
            userWish.text = "Good Evening."
        }else{
            userWish.text = "Good Morning"
        }
    }
}