package com.example.trr_app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.RelativeLayout
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.view.ManageUI.ManageActivity

class Dashboard : BaseActivity(),OnClickListener {

    private val manageUIDashboard : RelativeLayout
        get() = findViewById(R.id.manageUILayout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        manageUIDashboard.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.manageUILayout -> startActivity(Intent(this,ManageActivity::class.java))
        }
    }
}