package com.example.trr_app.view.Users

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.model.User
import com.example.trr_app.view.LoginScreen
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.lang.reflect.Type


class CreateUserActivity : BaseActivity(), OnClickListener {

    private val userImage: ImageView
        get() = findViewById(R.id.userProfileImg)
    private val userName: TextInputEditText
        get() = findViewById(R.id.tie_newUsername)
    private val userContact: TextInputEditText
        get() = findViewById(R.id.tie_userContact)
    private val userEmail: TextInputEditText
        get() = findViewById(R.id.tie_userEmail)
    private val userPassword: TextInputEditText
        get() = findViewById(R.id.tie_userPassword)
    private val userRePassword: TextInputEditText
        get() = findViewById(R.id.tie_reUserPassword)
    private val userCategory: AutoCompleteTextView
        get() = findViewById(R.id.userCategory)
    private val btn_Back: TextInputEditText
        get() = findViewById(R.id.btn_cancelCreateUser)
    private val btn_Create: MaterialButton
        get() = findViewById(R.id.btn_acceptCreateUser)
    private val contentView: RelativeLayout
        get() = findViewById(R.id.newUserCreateView)

    private val TAG: String = LoginScreen::class.java.name

    //profile image URI
    private val profileImageURI: Uri? = null

    //Image Code
    private val PICK_IMG: Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        btn_Create.setOnClickListener(this)
        userImage.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_acceptCreateUser -> verifyDetails()
            R.id.userProfileImg -> uploadImage()
        }
    }

    private fun verifyDetails() {
        if (userName.text != null || verifyContact(userContact.text.toString())) {
            if (verifyEmail(userEmail.text.toString())) {
                if (equalPassword(userPassword.text.toString(), userRePassword.text.toString())) {
                    if (verifyPassword(userPassword.text.toString())) {
                        //start create user
                        createNewUser(userEmail.text.toString(), userPassword.text.toString())
                    } else {
                        Log.d(TAG, "Password is not match for your organization")
                        Snackbar.make(
                            contentView,
                            R.string.password_type_error,
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    Log.d(TAG, "Password not equal.")
                    Snackbar.make(contentView, R.string.password_not_equal, Snackbar.LENGTH_SHORT)
                        .show()
                }
            } else {
                Log.d(TAG, "Please enter valid Email.")
                Snackbar.make(contentView, R.string.email_not_valid, Snackbar.LENGTH_SHORT)
                    .show()
            }
        } else {
            Log.d(TAG, "Please Fill Required Details.")
            Snackbar.make(contentView, R.string.incomplete_details, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun createNewUser(email: String, password: String) {
        Log.d(TAG, "Start user creating -" + email + "Password -" + password)

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "New user create successfully.")
                //navigate to data backup
                userDataBackup()
            } else {
                Log.e(TAG, "New user create failed.")
                Snackbar.make(contentView, R.string.new_user_create_decline, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun uploadImage() {
        val userImageUpload =
            firebaseStorageReference.child(getString(R.string.users_data_location))
                .child(getString(R.string.users_profile_location))
        try {
            //backup
            if (profileImageURI != null) {
                userImageUpload.putFile(profileImageURI)
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Profile picture upload successfully.")
                            Snackbar.make(
                                contentView,
                                R.string.new_user_create_success,
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Log.e(TAG, "Profile picture upload Failed.")
                        }
                    })
            } else {
                Log.e(TAG, "User haven't profile picture.")
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error " + e.message.toString())
        }

    }

    private fun selectImage() {
        val intent = Intent()
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT)
        resultLauncher.launch(Intent.createChooser(intent, "Select Profile Picture"))
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
            }
        }

    private fun userDataBackup() {
        val userData = User(
            userName.text.toString(),
            userCategory.text.toString(),
            userContact.text.toString(),
            userEmail.text.toString()
        )
        //data backup
        val userDatabaseReference =
            firebaseDatabaseReference.child(getString(R.string.users_data_location))
                .child(getString(R.string.users_profile_location)).child(
                    firebaseUser!!.uid
                )

        userDatabaseReference.setValue(userData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //upload Image
                if (profileImageURI != null) {
                    uploadImage()
                } else {
                    Log.d(TAG, "Profile data upload successfully.")
                    Log.e(TAG, "No profile user")
                    Snackbar.make(
                        contentView,
                        R.string.new_user_create_success,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }

        }.addOnFailureListener {
            OnFailureListener {
                Log.d(TAG, "Profile data upload Failed.")
                Snackbar.make(contentView, R.string.new_user_create_failed, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

}
