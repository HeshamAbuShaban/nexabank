package com.nexabank.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nexabank.R
import com.nexabank.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply the splash screen theme before calling super.onCreate()
        setTheme(R.style.Theme_App_Starting)
        super.onCreate(savedInstanceState)
        // Keep the installSplashScreen() call for splash screen behavior
        installSplashScreen()

        // Switch to the main theme after a delay (or other conditions)
        Handler(Looper.getMainLooper()).postDelayed({
            setTheme(R.style.Theme_NexaBank) // Your main theme
            binding = ActivityAuthBinding.inflate(layoutInflater)
            setContentView(binding.root)
            // ... (rest of your activity logic)
        }, 3000) // Delay in milliseconds
    }
}