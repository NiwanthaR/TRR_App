package com.example.trr_app.view.ManageUI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import androidx.core.util.Pair
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.view.LoginScreen
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class AddBooking : BaseActivity(), OnClickListener {

    private val otherServiceSwitch : SwitchMaterial
        get() = findViewById(R.id.otherServiceSwitch)

    private val mealsDetailsSwitch : SwitchMaterial
        get() = findViewById(R.id.mealsDetailsSwitch)

    private val otherServiceLayout : LinearLayout
        get() = findViewById(R.id.otherServiceLayout)

    private val mealDetailsLayout : LinearLayout
        get() = findViewById(R.id.mealDetailsLayout)

    private val bookingDate : TextInputEditText
        get() = findViewById(R.id.tv_bookingDate)

    private val TAG: String
            = LoginScreen::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_booking)

        //other service layout visibility
        otherServiceSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                otherServiceLayout.visibility = View.VISIBLE
            } else {
                otherServiceLayout.visibility = View.GONE
            }
        }

        //meals details layout visibility
        mealsDetailsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                mealDetailsLayout.visibility = View.VISIBLE
            }else{
                mealDetailsLayout.visibility = View.GONE
            }
        }

        //booking details capture
        bookingDate.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_bookingDate -> showDatePicker()
        }
    }

    private fun showDatePicker(){

        loadingProgressDialog(this)

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val picker = MaterialDatePicker.Builder.dateRangePicker().setTheme(R.style.MaterialDatePickerTheme)
            .setTitleText("Select Check-in Check-out Date")
            .setSelection(Pair(MaterialDatePicker.todayInUtcMilliseconds(),MaterialDatePicker.todayInUtcMilliseconds()+(1000*24*60*60)))
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        picker.show(this.supportFragmentManager,TAG)


    }

    private fun convertTimeToDate(time:Long): String{
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = time
        val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return format.format(utc.time)
    }

}