package com.nexabank.core

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NexaBankApp : Application(){

    companion object {
        lateinit var instance: NexaBankApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this // Assign the instance when the app starts
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}