package com.example.trr_app.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.trr_app.R


class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //waiting time
        Handler(Looper.getMainLooper()).postDelayed({
            // Your Code
            val homeIntent = Intent(this@SplashScreen, LoginScreen::class.java)
            startActivity(homeIntent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}