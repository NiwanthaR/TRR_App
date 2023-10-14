package com.example.trr_app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.LinearLayout
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
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

    private val TAG: String
    = LoginScreen::class.java.name

    //User Info
    private var userName : String? = null
    //= userNameInput.text.toString()

    private var userPassword : String? = null
//    = userPasswordInput.text.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login_screen)

        //check available users
        checkUserAvailability()

        //login click
        btnLogin.setOnClickListener(this)
        

    }

    private fun checkUserAvailability(){
        if (firebaseUser != null) {
            finish()
            startActivity(Intent(this@LoginScreen, Dashboard::class.java))
            Log.d(TAG,"Existing User Available.")
        }
    }
//
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnUserLogin -> login()
        }
    }

    private fun login(){

        userName = userNameInput.text.toString()
        userPassword = userPasswordInput.text.toString()

        Log.d(TAG,"user :"+userName +"Password :"+userPassword)

        if (userName!=null || userPassword!=null){
            //send value
            validate(userName!!, userPassword!!)
            Log.d(TAG,"Details Read Successfully.")
        }else{
            Snackbar.make(contentView, R.string.details_incomplete, Snackbar.LENGTH_SHORT)
                .show()
            Log.e(TAG,"Details Read Not Full Fill.")
        }
    }

    private fun validate(email: String, password: String) {
        if (is_fill(email, password)) {
            if (is_Validmail(email)) {
                //progressDialog.setMessage("Your Details in Processing Please waite..!")
                loadingProgressDialog(this@LoginScreen)
                //progressDialog.show()
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            //progressDialog.dismiss()
                            //checkemail_verification();
                            startActivity(Intent(this@LoginScreen, Dashboard::class.java))
                            Log.d(TAG,"Login Successfully.")
                        } else {
                            //progressDialog.dismiss()
                            //wrong_details.setText("Login Failed Check your Details")
                            loadingDialogClose()
                            Snackbar.make(contentView,"Login Failed Check your Details",Snackbar.LENGTH_SHORT).show()
                            Log.e(TAG,"Login Failed.")
                        }
                    }
            } else {
                //wrong_details.setText("Please enter valid Email")
                Snackbar.make(contentView,"Please enter valid Email",Snackbar.LENGTH_SHORT).show()
                Log.d(TAG,"Wrong Email address type")
            }
        } else {
            //wrong_details.setText("Please Fill All Details..!")
            Snackbar.make(contentView,"Please Fill All Details..!",Snackbar.LENGTH_SHORT).show()
            Log.d(TAG,"Login details not full fill")
        }
    }

    private fun checkemail_verification() {
        val user = firebaseAuth.currentUser
        val emailflage = user!!.isEmailVerified
        if (emailflage) {
            finish()
            startActivity(Intent(this@LoginScreen, Dashboard::class.java))
        } else {
            //wrong_details.setText("Verify Your Email First..!")
            Snackbar.make(contentView,"Verify Your Email First..!",Snackbar.LENGTH_SHORT).show()
            firebaseAuth.signOut()
        }
    }


}