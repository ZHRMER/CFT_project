package com.example.sweethome.hrhelper.presentation.screens.settings.view

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.presentation.screens.settings.presenter.SettingsPresenter

class SettingsActivityView(private var myActivity: AppCompatActivity?) {

    private lateinit var mySettingsPresenter: SettingsPresenter

    fun onCreate() {
        initToolbar()
        mySettingsPresenter =
                SettingsPresenter(myActivity)
    }

    fun onResume(activity: AppCompatActivity) {
        myActivity = activity
        mySettingsPresenter.attach(myActivity)
    }

    fun onPause() {
        myActivity = null
        mySettingsPresenter.detach()
    }

    private fun initToolbar() {
        val toolbar = myActivity?.findViewById<Toolbar>(R.id.toolbar_activity_settings)
        myActivity?.setSupportActionBar(toolbar)
        myActivity?.supportActionBar?.setTitle(R.string.settings_title)
        myActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
