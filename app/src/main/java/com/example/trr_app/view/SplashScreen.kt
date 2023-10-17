package com.example.trr_app.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RelativeLayout
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.google.android.material.snackbar.Snackbar


class SplashScreen : BaseActivity() {

    private val SPLASH_TIME_OUT = 3000

    private val contentView : RelativeLayout
        get() = findViewById(R.id.splashLayout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //waiting time
        Handler(Looper.getMainLooper()).postDelayed({
            if (isOnline(this@SplashScreen)){
                // Your Code
                val homeIntent = Intent(this@SplashScreen, LoginScreen::class.java)
                startActivity(homeIntent)
                finish()
            }else{
                Snackbar.make(contentView, R.string.no_connection, Snackbar.LENGTH_LONG)
                    .show()
            }
        }, SPLASH_TIME_OUT.toLong())
    }

    fun checkUserAvailability(){

    }
}