package com.nexabank.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nexabank.R
import com.nexabank.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        navController = supportFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment)!!.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboard_destination,
                R.id.transaction_destination,
                R.id.loan_destination,
                R.id.credit_card_destination,
                R.id.settings_destination,
                R.id.finance_destination,
                R.id.about_destination
            ), binding.drawerLayout
        )
        setupAppBar()
        setupNavViews()
    }

    private fun setupAppBar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupNavViews() {
        with(binding) {
            bottomNavView.setupWithNavController(navController)
            navigationView.setupWithNavController(navController)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}