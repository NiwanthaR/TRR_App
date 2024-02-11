package com.example.trr_app.adaptor

import androidx.constraintlayout.motion.widget.KeyCycle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.trr_app.Fragment.AllBookingFragment
import com.example.trr_app.Fragment.AllPermanentBookingFragment
import com.example.trr_app.Fragment.AllQuickBookingFragment

class AllBookingAdaptor(
    fragmentManager: FragmentManager,
    lifecycle:Lifecycle
) :FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AllBookingFragment()
            1 -> AllQuickBookingFragment()
            2 -> AllPermanentBookingFragment()
            else -> {AllBookingFragment()}
        }
    }

}