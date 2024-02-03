package com.example.trr_app.view.ManageUI

import android.os.Bundle
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.trr_app.R
import com.example.trr_app.adaptor.AllBookingAdaptor
import com.example.trr_app.common.BaseActivity
import com.google.android.material.tabs.TabLayout

class UpComingBookingActivity : BaseActivity() {

    private val tabLayout : TabLayout
        get() = findViewById(R.id.selectingTabBar)
    private val viewPager : ViewPager2
        get() = findViewById(R.id.viewPager2)

    private lateinit var adaptor : AllBookingAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_up_comming_booking)

        adaptor = AllBookingAdaptor(supportFragmentManager , lifecycle)

        //add tabs
        //tabLayout.addTab(tabLayout.newTab().setText("All"))
        //tabLayout.addTab(tabLayout.newTab().setText("Quick"))
        //tabLayout.addTab(tabLayout.newTab().setText("Permanent"))

        //add adaptor
        viewPager.adapter = adaptor

        //on click
        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}