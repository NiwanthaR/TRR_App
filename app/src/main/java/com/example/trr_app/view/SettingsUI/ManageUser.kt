package com.example.trr_app.view.SettingsUI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.adaptor.UserAdaptor
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.holders.BookingViewHolder
import com.example.trr_app.holders.UserViewHolder
import com.example.trr_app.model.SubmitBooking
import com.example.trr_app.model.User
import com.example.trr_app.view.LoginScreen
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class ManageUser : BaseActivity() {

    private val recyclerView : RecyclerView
        get() = findViewById(R.id.usersRecycler)

    private val TAG: String = ManageUser::class.java.name

    private val contentView : RelativeLayout
        get() = findViewById(R.id.userDashboardLayout)

    //1firebase
    private var options: FirebaseRecyclerOptions<User>? = null
    private var adapter: FirebaseRecyclerAdapter<User,UserViewHolder>? = null

    //firebase
    private var userRef = firebaseDatabaseReference
    private var DataRef = firebaseDatabaseReference
    private var ref = firebaseDatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_user)

        //load Data
        loadUserList()

    }


    private fun loadUserList(){
        val dataRef = firebaseDatabaseReference.child(getString(R.string.users_data_location))
            .child(getString(R.string.users_profile_location))

        val profileList : ArrayList<User> = ArrayList()

        dataRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                profileList.clear()

                for (postSnapshot in snapshot.children) {
                    val profile: User? = postSnapshot.getValue(User::class.java)
                    if (profile!=null){
                        Log.e(TAG, "Data loading success")
                        profileList.add(profile)

                    }else{
                        Log.e(TAG, "Data loading failed. booking is empty")
                        Snackbar.make(contentView,R.string.booking_data_null, Snackbar.LENGTH_SHORT).show()
                    }
                }

                val profileListSize = profileList.size

                if (profileListSize!= 0){

                    // Creates a vertical Layout Manager
                    recyclerView.layoutManager = LinearLayoutManager(this@ManageUser)

                    // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
//        rv_animal_list.layoutManager = GridLayoutManager(this, 2)

                    // Access the RecyclerView Adapter and load the data into it
                    recyclerView.adapter = UserAdaptor(profileList, this@ManageUser)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Data loading failed. booking is empty")
            }

        })
    }
}