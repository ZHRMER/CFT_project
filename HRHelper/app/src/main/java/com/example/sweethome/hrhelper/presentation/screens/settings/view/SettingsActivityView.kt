package com.example.sweethome.hrhelper.presentation.screens.settings.view

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.presentation.screens.settings.presenter.SettingsPresenter

class SettingsActivityView(
    private var myActivity: AppCompatActivity?,
    private val myEvent: Event
) :
    SettingsPresenter.SettingsPresenterContract {

    private lateinit var mySettingsPresenter: SettingsPresenter

    fun onCreate() {
        initToolbar()
        mySettingsPresenter =
                SettingsPresenter(myActivity, this)
        mySettingsPresenter.updateStatistics(myEvent.id!!)
    }

    fun onResume(activity: AppCompatActivity) {
        myActivity = activity
        mySettingsPresenter.attach(myActivity, this)
    }

    fun onPause() {
        myActivity = null
        mySettingsPresenter.detach()
    }

    private fun initToolbar() {
        val toolbar = myActivity?.findViewById<Toolbar>(R.id.toolbar_activity_settings)
        myActivity?.setSupportActionBar(toolbar)
        myActivity?.supportActionBar?.title = "${myEvent.title}:${myActivity?.getString(R.string.settings_title)}"
        myActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun updateViewStatistics(allMember: Int, registerMember: Int) {
        val registeredTextView = myActivity?.findViewById(R.id.registered_number_value_text_view) as TextView
        val arrivedTextView = myActivity?.findViewById(R.id.arrived_number_value_text_view) as TextView
        registeredTextView.text = allMember.toString()
        arrivedTextView.text = registerMember.toString()
    }
}
