package com.dicoding.safequake

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.safequake.ui.education.EducationFragment
import com.dicoding.safequake.ui.evaluation.EvaluationFragment
import com.dicoding.safequake.ui.history.HistoryFragment
import com.dicoding.safequake.ui.home.HomeFragment
import com.dicoding.safequake.ui.setting.SettingFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> HistoryFragment()
            2 -> EvaluationFragment()
            3 -> EducationFragment()
            4 -> SettingFragment()
            else -> HomeFragment()
        }
    }
}