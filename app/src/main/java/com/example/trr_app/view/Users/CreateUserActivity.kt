package com.example.trr_app.view.Users

import android.R.attr
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.view.LoginScreen
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText


class CreateUserActivity : BaseActivity(),OnClickListener {

    private val  userName : TextInputEditText
        get() = findViewById(R.id.tie_newUsername)
    private val  userContact : TextInputEditText
        get() = findViewById(R.id.tie_userContact)
    private val  userEmail : TextInputEditText
        get() = findViewById(R.id.tie_userEmail)
    private val  userPassword : TextInputEditText
        get() = findViewById(R.id.tie_userPassword)
    private val  userRePassword : TextInputEditText
        get() = findViewById(R.id.tie_reUserPassword)
    private val  btn_Back : TextInputEditText
        get() = findViewById(R.id.btn_cancelCreateUser)
    private val  btn_Create : TextInputEditText
        get() = findViewById(R.id.btn_acceptCreateUser)
    private val contentView : RelativeLayout
        get()= findViewById(R.id.newUserCreateView)

    private val TAG: String
            = LoginScreen::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        btn_Create.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_acceptCreateUser -> verifyDetails()
        }
    }

    private fun verifyDetails(){
        if (userName.text!=null || verifyContact(userContact.text.toString())){
            if (verifyEmail(userEmail.text.toString())){
                 if (equalPassword(userPassword.text.toString(),userRePassword.text.toString())){
                    if (verifyPassword(userPassword.text.toString())){
                        true
                    }else{
                        Log.d(TAG,"Password is not match for your organization")
                        Snackbar.make(contentView, R.string.password_type_error, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }else{
                    Log.d(TAG,"Password not equal.")
                     Snackbar.make(contentView, R.string.password_not_equal, Snackbar.LENGTH_SHORT)
                         .show()
                }
            }else{
                Log.d(TAG,"Please enter valid Email.")
                Snackbar.make(contentView, R.string.email_not_valid, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }else{
            Log.d(TAG,"Please Fill Required Details.")
            Snackbar.make(contentView, R.string.incomplete_details, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun createNewUser(email:String,password:String){
        Log.d(TAG,"Start user creating -"+email +"Password -" +password)

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"New user create successfully.")
                    //navigate to data backup

                } else {
                    Log.d(TAG,"New user create failed.")
                }
            }

        }
    }

    private fun userDataBackup(){

}