package com.dicoding.asclepius.view

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.asclepius.view.cancer_detection.CancerDetectionFragment
import com.dicoding.asclepius.view.health_news.HealthNewsFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = CancerDetectionFragment()
            1 -> fragment = HealthNewsFragment()
        }
        return fragment as Fragment
    }

}