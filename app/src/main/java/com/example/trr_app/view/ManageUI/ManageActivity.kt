package com.example.trr_app.view.ManageUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.view.Dashboard
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ManageActivity : BaseActivity(), OnClickListener {

    private val createBooking : FloatingActionButton
        get() = findViewById(R.id.floating_action_addBooking)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        createBooking.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.floating_action_addBooking -> startActivity(Intent(this, AddBooking::class.java))
        }
    }
}