package com.example.trr_app.view.ManageUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.holders.BookingViewHolder
import com.example.trr_app.model.SubmitBooking
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.Query
import com.squareup.picasso.Picasso


class ManageActivity : BaseActivity(), OnClickListener {

    private val TAG: String
            = ManageActivity::class.java.name
    private val createBooking : FloatingActionButton
        get() = findViewById(R.id.floating_action_addBooking)
    private val contentView : RelativeLayout
        get() = findViewById(R.id.pendingBookingLayout)
    private val recyclerView : RecyclerView
        get() = findViewById(R.id.recycleBooking)

    private var options: FirebaseRecyclerOptions<SubmitBooking>? = null
    private var adapter: FirebaseRecyclerAdapter<SubmitBooking, BookingViewHolder>? = null

    private var DataRef = firebaseDatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        createBooking.setOnClickListener(this)

        recyclerView.layoutManager = LinearLayoutManager(applicationContext);
        recyclerView.setHasFixedSize(true);

        //load data
        loadPendingBooking()

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.floating_action_addBooking -> navigateToAddBooking()
        }
    }

    private fun navigateToAddBooking(){
        startActivity(Intent(this, AddBooking::class.java))
    }

    private fun loadPendingBooking() {
        //start loading
        loadingProgressDialog(this)

        DataRef = firebaseDatabaseReference.child("Booking Details")
            .child("Appointment Reservation")

        if (DataRef!=null){
            val query: Query = DataRef.ref
            options = FirebaseRecyclerOptions.Builder<SubmitBooking>().setQuery(query, SubmitBooking::class.java)
                .build()
            adapter = object : FirebaseRecyclerAdapter<SubmitBooking, BookingViewHolder>(options!!) {
                protected override fun onBindViewHolder(
                    @NonNull holder: BookingViewHolder,
                    position: Int,
                    @NonNull model: SubmitBooking
                ) {
                    holder.bookingName.setText( model.getFirstName())
                    holder.bookingDate.setText(model.getBooking_dateRange())
                    holder.bookingAddress.setText(model.getAddress())
                    holder.bookingType.setText(model.getBookingType())
                    holder.bookingContact.setText(model.getContact())

                    //click event
                    holder.v.setOnClickListener(OnClickListener {
                        //val intent = Intent(this@mechanic_add_wall, mechanic_post_view_dash::class.java)
                        //intent.putExtra("Item_Key", getRef(position).key)
                        //startActivity(intent)
                    })
                }

                @NonNull
                override fun onCreateViewHolder(
                    @NonNull parent: ViewGroup,
                    viewType: Int
                ): BookingViewHolder {
                    val v: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.single_booking_ui, parent, false)
                    return BookingViewHolder(v)
                }
            }
            (adapter as FirebaseRecyclerAdapter<SubmitBooking, BookingViewHolder>).startListening()
            recyclerView.adapter = adapter

            //close progress dialog
            loadingDialogClose()
        }else{
            Log.e(TAG, "Data reference is nll")
            Snackbar.make(contentView,R.string.user_data_reference_null_error, Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}