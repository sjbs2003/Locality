package com.example.locality

import android.app.Application
import com.example.locality.model.AppContainer
import com.example.locality.model.DefaultAppContainer

class LocalityApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}