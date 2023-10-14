package com.example.trr_app.common

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.trr_app.support.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.regex.Matcher
import java.util.regex.Pattern


open class BaseActivity : AppCompatActivity() {

    //Firebase Auth
    val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    //Firebase Database
    val firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    //Firebase Storage
    val firebaseStorage : FirebaseStorage = FirebaseStorage.getInstance()
    //Firebase User
    val firebaseUser : FirebaseUser? = firebaseAuth.currentUser
    //Firebase User

    //loading progressBar Dialog
    private lateinit var loadingDialog : LoadingDialog

    open fun is_ValidNic(nic: String): Boolean {
        val p: Pattern = Pattern.compile("([0-9]{9}[a-z]{1})")
        val m: Matcher = p.matcher(nic)
        return m.find() && nic.length == 10
    }

    open fun is_Validmail(email: String): Boolean {
        val p =
            Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
        val m = p.matcher(email)
        return m.find()
    }

    open fun is_fill(email: String, password: String): Boolean {
        return !(email.isEmpty() || password.isEmpty())
    }

    open fun loadingProgressDialog(context : Context){
        loadingDialog = LoadingDialog(context)
        loadingDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loadingDialog.show()
    }

    open fun loadingDialogClose(){
        loadingDialog.dismiss()
    }
}
