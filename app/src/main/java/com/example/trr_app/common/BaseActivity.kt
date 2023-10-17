package com.example.trr_app.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.trr_app.support.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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
    //Firebase database reference
    val firebaseDatabaseReference : DatabaseReference = firebaseDatabase.reference.child("TRRApp")
    //Firebase Storage reference
    val firebaseStorageReference : StorageReference = firebaseStorage.reference

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

    open fun equalPassword(password: String, rePassword: String): Boolean {
        return password == rePassword
    }

    open fun verifyPassword(password: String): Boolean {
        val p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])")
        val m = p.matcher(password)
        return m.find() && password.length > 7
    }

    open fun verifyEmail(email: String): Boolean {
        val p =
            Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
        val m = p.matcher(email)
        return m.find()
    }

    open fun verifyContact(contact: String): Boolean {
        val p =
            Pattern.compile("(^1300\\d{6}$)|(^0[1|3|7|6|8]{1}[0-9]{8}$)|(^13\\d{4}$)|(^04\\d{2,3}\\d{6}$)")
        val m = p.matcher(contact)
        return m.find()
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}
