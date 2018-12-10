package com.example.sweethome.hrhelper.data.model

import android.app.Application
import com.example.sweethome.hrhelper.data.data_base_source.AppDatabase


class App : Application() {

    var database: AppDatabase? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = AppDatabase.getInstance(this)
    }

    companion object {
        lateinit var instance: App
    }
}