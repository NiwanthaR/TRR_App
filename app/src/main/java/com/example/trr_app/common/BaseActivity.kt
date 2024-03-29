package com.example.trr_app.common

import android.app.AlertDialog
import android.content.ClipDescription
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer.OnCompletionListener
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trr_app.R
import com.example.trr_app.SharedPreference.SharedPreference
import com.example.trr_app.model.Room
import com.example.trr_app.support.LoadingDialog
import com.example.trr_app.view.ManageUI.ManageActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
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
    var firebaseUser : FirebaseUser? = firebaseAuth.currentUser
    //Firebase database reference
    val firebaseDatabaseReference : DatabaseReference = firebaseDatabase.reference.child("TRRApp")
    //OrderID Reference
    val orderIDReference : DatabaseReference = firebaseDatabaseReference.child("Store Value").child("OrderID")
    //Firebase Storage reference
    val firebaseStorageReference : StorageReference = firebaseStorage.reference
    //Firebase User Data Location
    var userDatabaseReference : DatabaseReference? = null
    //loading progressBar Dialog
    private lateinit var loadingDialog : LoadingDialog
    //Sharedpereferance
    val sharedPreferences : SharedPreference = SharedPreference.getInstance()
    //order id
    var customerOrderID: Int = 0
    //TAG Name
    private val TAG: String
            = BaseActivity::class.java.name

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

    fun getOrderID():Int{

        orderIDReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value!=null){
                    customerOrderID =
                    Log.e(TAG,"Ongoing order id is."+snapshot.value)

                }
            }
            override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG,"Order id couldnt load.")
            }

        })
        return customerOrderID
    }

    fun increaseOrderID():Int{
        orderIDReference.setValue(customerOrderID).addOnCompleteListener { OnCompletionListener{

        } }.addOnFailureListener { OnFailureListener{

        } }

        return customerOrderID
    }

    fun removeOrderID():Boolean{
        val state = false
        orderIDReference.removeValue().addOnCompleteListener { OnCompletionListener{

        } }.addOnFailureListener { OnFailureListener{

        } }
        return state
    }

    fun alertApproveDialog(context: Context,title: String,description: String,pathNumber:Int){

        val builder = AlertDialog.Builder(context,R.style.CustomAlertDialog2)
            .create()
        val view = layoutInflater.inflate(R.layout.custom_alert_dialog_approve,null)

        //set title
        val titleText = view.findViewById<TextView>(R.id.alertTitleText)
        titleText.text = title

        //set description
        val textDescription = view.findViewById<TextView>(R.id.alertDescriptionText)
        textDescription.text = description

        //set button
        val  negativeButton = view.findViewById<Button>(R.id.btnDialogNegative)
        negativeButton.setOnClickListener {
            builder.dismiss()

        }
        val positiveButton = view.findViewById<Button>(R.id.btnDialogPositive)
        positiveButton.setOnClickListener{
            builder.dismiss()
        }
        builder.setView(view)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    fun getCurrentDate():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }

    fun areDateRangesOverlapping(startDate1: String, endDate1: String, startDate2: String, endDate2: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Parse the date strings to Date objects
        val start1: Date = dateFormat.parse(startDate1) ?: return false
        val end1: Date = dateFormat.parse(endDate1) ?: return false
        val start2: Date = dateFormat.parse(startDate2) ?: return false
        val end2: Date = dateFormat.parse(endDate2) ?: return false

        // Check if the ranges overlap
        return !(end1.before(start2) || start1.after(end2))
    }

    fun checkOverLeapOrNot(startDate:String, endDate:String, reserveStartDate:String,reserveEndDate:String):Boolean{
        val s = SimpleDateFormat("yyyy-MM-dd")
//        val s1 = s.parse(startDate!!)
//        val e1 = s.parse(reserveStartDate)
//        val s2 = s.parse(endDate!!)
//        val e2 = s.parse(reserveEndDate)

        val s1 = s.parse(reserveStartDate)
        val e1 = s.parse(startDate!!)
        val s2 = s.parse(reserveEndDate)
        val e2 = s.parse(endDate!!)

        print("Start date " + s.format(s1))
        println("  End date " + s.format(e1))

        print("Start date " + s.format(s2))
        println("  End date " + s.format(e2))
        if (s1.compareTo(s2) < 0 && e1.compareTo(s2) > 0 || s1.compareTo(e2) < 0 && e1.compareTo(e2) > 0 || s1.compareTo(
                s2
            ) < 0 && e1.compareTo(e2) > 0 || s1.compareTo(s2) > 0 && e1.compareTo(e2) < 0
        ) {
            println("They don't overlap")
            Log.d(TAG, "They don't overlap")
            return false
        } else {
            print("They overlap")
            Log.e(TAG, "They overlap")
            return true
        }

    }
    fun convertTimesToDates(time:Long): String{
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = time
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(utc.time)
    }

    fun customLogout(title:String,description: String,accept:Boolean,decline:Boolean){
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.custom_alert,null)

        val acceptBtnLayout = view.findViewById<LinearLayout>(R.id.acceptLayout)
        val declineBtnLayout = view.findViewById<LinearLayout>(R.id.declineLayout)

        if (accept){
            acceptBtnLayout.visibility = View.GONE
        }

        if (decline){
            declineBtnLayout.visibility = View.GONE
        }

        val textTitle = view.findViewById<TextView>(R.id.customAlertTitle)
        textTitle.text = title
        val textDescription = view.findViewById<TextView>(R.id.customAlertDescription)
        textDescription.text = description

        val  buttonDecline = view.findViewById<MaterialButton>(R.id.dialogDismiss_button)
        builder.setView(view)
        buttonDecline.setOnClickListener {
            builder.dismiss()
        }
        val buttonAccept = view.findViewById<MaterialButton>(R.id.dialogAgree_button)
        buttonAccept.setOnClickListener(View.OnClickListener {

        })
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

}
