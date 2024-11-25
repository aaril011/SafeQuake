package com.dicoding.safequake.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.safequake.R
import com.dicoding.safequake.ViewModelFactory
import com.dicoding.safequake.ViewPagerAdapter
import com.dicoding.safequake.databinding.ActivityMainBinding
import com.dicoding.safequake.ui.setting.SettingPreferences
import com.dicoding.safequake.ui.setting.SettingViewModel
import com.dicoding.safequake.ui.setting.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var settingViewModel: SettingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        binding.navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    binding.viewPager.currentItem = 0
                    true
                }
                R.id.navigation_history -> {
                    binding.viewPager.currentItem = 1
                    true
                }
                R.id.navigation_evaluation -> {
                    binding.viewPager.currentItem = 2
                    true
                }
                R.id.navigation_education -> {
                    binding.viewPager.currentItem = 3
                    true
                }
                R.id.navigation_setting -> {
                    binding.viewPager.currentItem = 4
                    true
                }
                else -> false
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.navView.selectedItemId = R.id.navigation_home
                    1 -> binding.navView.selectedItemId = R.id.navigation_history
                    2 -> binding.navView.selectedItemId = R.id.navigation_evaluation
                    3 -> binding.navView.selectedItemId = R.id.navigation_education
                    4 -> binding.navView.selectedItemId = R.id.navigation_setting
                }
            }
        })
    }
}