package com.example.trr_app.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.support.LoadingDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText


class LoginScreen : BaseActivity(), OnClickListener {

    private val userNameInput : TextInputEditText
        get() = findViewById(R.id.etUserEmail)

    private val userPasswordInput : TextInputEditText
        get()= findViewById(R.id.etUserPassword)

    private val btnLogin : Button
        get()= findViewById(R.id.btnUserLogin)

    private val contentView :LinearLayout
        get()= findViewById(R.id.userLoginView)

    //User Info
//    private val userName : String
//    = userNameInput.text.toString()
//
//    private val userPassword : String
//    = userPasswordInput.text.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login_screen)

        //login click
        btnLogin.setOnClickListener(this)
        

    }
//
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnUserLogin -> startActivity(Intent(this,Dashboard::class.java))
        }
    }

    private fun login(){
        if (userNameInput.text!=null || userPasswordInput.text!=null){
            //send value
            //validate(userName,userPassword)
        }else{
            Snackbar.make(contentView, R.string.details_incomplete, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

//    private fun validate(email: String, password: String) {
//        if (is_fill(email, password)) {
//            if (is_Validmail(email)) {
//                //progressDialog.setMessage("Your Details in Processing Please waite..!")
//                loadingProgressDialog(this@LoginScreen)
//                //progressDialog.show()
//                firebaseAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            //progressDialog.dismiss()
//                            checkemail_verification();
//                            //checkUsertype()
//                        } else {
//                            //progressDialog.dismiss()
//                            //wrong_details.setText("Login Failed Check your Details")
//                            loadingDialogClose()
//                            Snackbar.make(contentView,"Login Failed Check your Details",Snackbar.LENGTH_SHORT).show()
//                        }
//                    }
//            } else {
//                //wrong_details.setText("Please enter valid Email")
//                Snackbar.make(contentView,"Please enter valid Email",Snackbar.LENGTH_SHORT).show()
//            }
//        } else {
//            //wrong_details.setText("Please Fill All Details..!")
//            Snackbar.make(contentView,"Please Fill All Details..!",Snackbar.LENGTH_SHORT).show()
//        }
//    }

//    private fun checkemail_verification() {
//        val user = firebaseAuth.currentUser
//        val emailflage = user!!.isEmailVerified
//        if (emailflage) {
//            finish()
//            //startActivity(Intent(this@LoginScreen, mechanic_dashboard::class.java))
//        } else {
//            //wrong_details.setText("Verify Your Email First..!")
//            Snackbar.make(contentView,"Verify Your Email First..!",Snackbar.LENGTH_SHORT).show()
//            firebaseAuth.signOut()
//        }
//    }


}