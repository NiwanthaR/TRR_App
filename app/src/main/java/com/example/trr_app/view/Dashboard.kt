package com.example.trr_app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.model.SubmitBooking
import com.example.trr_app.model.User
import com.example.trr_app.support.BookingSuccessDialog
import com.example.trr_app.view.ManageUI.AddQuickBooking
import com.example.trr_app.view.ManageUI.ManageBookingActivity
import com.example.trr_app.view.SettingsUI.UserSettings
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


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
    private val dashboardContentView : RelativeLayout
        get() = findViewById(R.id.dashboardContentView)
    private val quickBookingDashboard : RelativeLayout
        get() = findViewById(R.id.layoutQuickBooking)

    private val userLogoutDashboard : RelativeLayout
        get() = findViewById(R.id.userLogoutLayout)

    private val bookingHistoryCard : MaterialCardView
        get() = findViewById(R.id.historyCardView)

    private val settingUICard : MaterialCardView
        get() = findViewById(R.id.settings_card)

    private val checkInCountView : TextView
        get() = findViewById(R.id.roomCheckInCount)

    private val checkOutCountView : TextView
        get() = findViewById(R.id.roomCheckOutCount)

    //database reference
    private var databasereference = firebaseDatabaseReference
    //dialog
    private val alertDialog = BookingSuccessDialog()

    private var checkIn = 0
    private var checkOut = 0

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

        //load display data
        loadCheckInCheckOut()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.manageUILayout -> startActivity(Intent(this,ManageBookingActivity::class.java))
            R.id.layoutQuickBooking ->moveToQuickBooking()
            R.id.historyCardView -> alertDialog.showDialog(this@Dashboard,"SS")
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
                }else{
                    //userName.text = data.userName
                    Log.e(TAG, "User not detect : data null")
                }
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

    private fun loadCheckInCheckOut(){

        //get date
        val date = getCurrentDate()

        //load progress dialog
        loadingProgressDialog(this)

        val checkInList = ArrayList<SubmitBooking>()
        val checkOutList = ArrayList<SubmitBooking>()

        val bookingDatabaseReference = firebaseDatabaseReference.child("Booking Details").child("Appointment Reservation")

        val query1: Query = bookingDatabaseReference.ref.orderByChild("checkIn").startAt(date)
        val query2: Query = bookingDatabaseReference.ref.orderByChild("checkOut").startAt(date)

        query1.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                checkInList.clear()
                //load data
                for (postSnapshot in snapshot.children) {
                    val booking: SubmitBooking? = postSnapshot.getValue(SubmitBooking::class.java)
                    if (booking!=null){
                        Log.e(TAG, "Data loading success")
                        checkInList.add(booking)
                    }else{
                        Log.e(TAG, "Data loading failed. booking is empty")
                    }
                }
                //get count
                checkIn = checkInList.size
                checkInCountView.text = checkIn.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Check in Data loading Failed")
            }

        })

        query2.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                checkOutList.clear()
                //load data
                for (postSnapshot in snapshot.children){
                    val booking: SubmitBooking? = postSnapshot.getValue(SubmitBooking::class.java)
                    if (booking!=null){
                        Log.e(TAG, "Data loading success")
                        checkOutList.add(booking)
                    }else{
                        Log.e(TAG, "Data loading failed. booking is empty")
                    }
                }

                checkOut = checkOutList.size
                checkOutCountView.text = checkOut.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Check out Data loading Failed")
            }

        })

        //(adapter as FirebaseRecyclerAdapter<RoomDetails, RoomViewHolder>).startListening()
        //roomRecyclerView.adapter = adapter

        //close progress dialog
        loadingDialogClose()

    }
}