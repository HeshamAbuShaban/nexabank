package com.nexabank.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.nexabank.R
import com.nexabank.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavView()
    }

    private fun setupBottomNavView() {
        val mainNavHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.main_nav_host_fragment)!!
        val navController = mainNavHostFragment.findNavController()
        val bnv = binding.bottomNavView
        bnv.setupWithNavController(navController)
    }
}