package com.example.trr_app.view.Users

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.RelativeLayout
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
import java.io.IOException


class CreateUserActivity : BaseActivity(), OnClickListener {

    private val userImage: ImageView
        get() = findViewById(R.id.newUserProfileImg)
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
        get() = findViewById(R.id.at_userCategory)
    private val btn_Back: MaterialButton
        get() = findViewById(R.id.btn_cancelCreateUser)
    private val btn_Create: MaterialButton
        get() = findViewById(R.id.btn_acceptCreateUser)
    private val contentView: RelativeLayout
        get() = findViewById(R.id.newUserCreateView)

    private val TAG: String = LoginScreen::class.java.name

    //profile image URI
    private var profileImageURI: Uri? = null

    //Image Code
    private val PICK_IMG: Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        //set click views
        btn_Create.setOnClickListener(this)
        btn_Back.setOnClickListener(this)
        userImage.setOnClickListener(this)

        //for user category
        setOptionsToDropDown()

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_acceptCreateUser -> verifyDetails()
            R.id.newUserProfileImg -> selectImage()
            R.id.btn_cancelCreateUser -> goBack()
        }
    }

    private fun goBack(){
        onBackPressed()
        this.finish()
    }

    private fun verifyDetails() {
        //start progress bar
        loadingProgressDialog(this)

        if (userName.text != null || verifyContact(userContact.text.toString())) {
            if (verifyEmail(userEmail.text.toString())) {
                if (equalPassword(userPassword.text.toString(), userRePassword.text.toString())) {
                    if (verifyPassword(userPassword.text.toString())) {
                        if (userCategory.text!=null){
                            //start create user
                            createNewUser(userEmail.text.toString(), userPassword.text.toString())
                        }else{
                            Log.d(TAG, "category not select")

                            //stop progress dialog
                            loadingDialogClose()

                            Snackbar.make(
                                contentView,
                                R.string.category_error,
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                        }
                    } else {
                        Log.d(TAG, "Password is not match for your organization")
                        //stop progress dialog
                        loadingDialogClose()

                        Snackbar.make(
                            contentView,
                            R.string.password_type_error,
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    Log.d(TAG, "Password not equal.")
                    //stop progress dialog
                    loadingDialogClose()

                    Snackbar.make(contentView, R.string.password_not_equal, Snackbar.LENGTH_SHORT)
                        .show()
                }
            } else {
                Log.d(TAG, "Please enter valid Email.")
                //stop progress dialog
                loadingDialogClose()

                Snackbar.make(contentView, R.string.email_not_valid, Snackbar.LENGTH_SHORT)
                    .show()
            }
        } else {
            Log.d(TAG, "Please Fill Required Details.")
            //stop progress dialog
            loadingDialogClose()

            Snackbar.make(contentView, R.string.incomplete_details, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun setOptionsToDropDown(){
       try {
           //val items = listOf("Admin", "Staff", "Customer")

           val categoryList = ArrayList<String>()
           categoryList.add("Admin")
           categoryList.add("Staff")
           categoryList.add("Customer")

           val adapter = ArrayAdapter(this, R.layout.list_item,categoryList)
           userCategory.setAdapter(adapter)

       }catch (e:Exception){
           Log.d(TAG, "Error" +e.message)
           Log.d(TAG, "Please couldn't able to load dropdown")
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
                //stop progress dialog
                loadingDialogClose()

                Snackbar.make(contentView, R.string.new_user_create_decline, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun uploadImage() {
        val userImageUpload =
            firebaseStorageReference.child(getString(R.string.users_data_location))
                .child(getString(R.string.users_profile_location)).child(firebaseUser!!.uid)
        try {
            //backup
            if (profileImageURI != null) {
                userImageUpload.putFile(profileImageURI!!)
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Profile picture uploaded successfully.")
                            Snackbar.make(
                                contentView,
                                R.string.new_user_create_success,
                                Snackbar.LENGTH_SHORT
                            )
                                .show()

                            //move to login
                            moveToLoginScreen()

                        } else {
                            Log.e(TAG, "Profile picture upload Failed.")
                        }
                    })
            } else {
                Log.e(TAG, "User haven't profile picture.")
                //without profile image
                Log.d(TAG, "Please couldn't able to load dropdown")
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

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data

                //check null
                if (data!=null){

                    var bitmap : Bitmap? = null
                    try {
                        profileImageURI = data!!.data!!
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, profileImageURI)
                        userImage.setImageBitmap(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.d(TAG,"Error "+R.string.image_error)
                    }
                }
            }
        }

    private fun userDataBackup() {
        val userData = User(
            userName.text.toString(),
            userCategory.text.toString(),
            userContact.text.toString(),
            userEmail.text.toString()
        )
         firebaseUser = firebaseAuth.currentUser

        if (firebaseUser!=null){
            //data backup
            userDatabaseReference =
                firebaseDatabaseReference.child(getString(R.string.users_data_location))
                    .child(getString(R.string.users_profile_location)).child(
                        firebaseUser!!.uid
                    )

            userDatabaseReference!!.setValue(userData).addOnCompleteListener { task ->
                val result = task.result
                if (task.isSuccessful) {
                    //upload Image
                    if (profileImageURI != null) {
                        uploadImage()
                    } else {
                        Log.d(TAG, "Profile data upload successfully.")
                        Log.e(TAG, "No profile user")
                        //stop progress dialog
                        loadingDialogClose()

                        Snackbar.make(
                            contentView,
                            R.string.new_user_create_success,
                            Snackbar.LENGTH_SHORT
                        )
                            .show()

                        //login dashboard
                        moveToLoginScreen()
                    }
                }else{
                    Log.d(TAG, "Profile data upload Fail."+task.result)

                    //stop progress dialog
                    loadingDialogClose()

                    Snackbar.make(contentView, R.string.data_upload_error03, Snackbar.LENGTH_SHORT)
                        .show()
                }

            }.addOnFailureListener {
                OnFailureListener {
                    Log.d(TAG, "Profile data upload Failed.")

                    //stop progress dialog
                    loadingDialogClose()

                    Snackbar.make(contentView, R.string.data_upload_error02, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }else{
            Log.e(TAG, "Profile data upload Failed. Firebase user Null")
            //stop progress dialog
            loadingDialogClose()

            Snackbar.make(contentView, R.string.data_upload_error, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun moveToLoginScreen(){
        startActivity(Intent(this@CreateUserActivity,LoginScreen::class.java))
        this.finish()
    }

}
