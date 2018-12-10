package com.example.sweethome.hrhelper.presentation.screens.settings.presenter

import android.support.v7.app.AppCompatActivity

class SettingsPresenter(private var myActivity: AppCompatActivity?) {

    fun attach(activity: AppCompatActivity?) {
        myActivity = activity
    }

    fun detach() {
        myActivity = null
    }
}