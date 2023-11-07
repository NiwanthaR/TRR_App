package com.example.trr_app.view.SettingsUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class UserSettings : BaseActivity(), OnClickListener {

    private val btnChangePassword : MaterialCardView
        get() = findViewById(R.id.changePassword_Layout)

    private val btnManageUser : MaterialCardView
        get() = findViewById(R.id.manageUserCard)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        btnChangePassword.setOnClickListener(this)
        btnManageUser.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            //R.id.changePassword_Layout -> displayPasswordTab()
            R.id.manageUserCard -> displayManageUserLayout()
        }
    }

    private fun displayPasswordTab(){

            // on below line we are creating a new bottom sheet dialog.
            val dialog = BottomSheetDialog(this)

            // on below line we are inflating a layout file which we have created.
            val view = layoutInflater.inflate(R.layout.password_bottom_sheet_dialog, null)

            // on below line we are creating a variable for our button
            // which we are using to dismiss our dialog.
            val btnClose = view.findViewById<Button>(R.id.btn_changePassword_Cancel)

            val btnApprove = view.findViewById<Button>(R.id.btn_changePassword_Approve)

            // on below line we are adding on click listener
            // for our dismissing the dialog button.
            btnClose.setOnClickListener {
                // on below line we are calling a dismiss
                // method to close our dialog.
                dialog.dismiss()
            }

            btnApprove.setOnClickListener {
                //need to be create
            }

            // closing of dialog box when clicking on the screen.
            dialog.setCancelable(false)

            // content view to our view.
            dialog.setContentView(view)

            // a show method to display a dialog.
            dialog.show()
    }

    private fun displayManageUserLayout(){
        startActivity(Intent(this@UserSettings,ManageUser::class.java))
    }


}