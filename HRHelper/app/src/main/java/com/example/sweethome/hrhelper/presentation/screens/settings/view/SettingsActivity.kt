package com.example.sweethome.hrhelper.presentation.screens.settings.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.domain.entity.Event

class SettingsActivity : AppCompatActivity() {
    private val KEY_CURRENT_EVENT = "current_event"
    private lateinit var mySettingsActivityView: SettingsActivityView
    private lateinit var myEvent: Event

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        myEvent = intent.getParcelableExtra(KEY_CURRENT_EVENT)
        mySettingsActivityView =
                SettingsActivityView(this, myEvent)
        mySettingsActivityView.onCreate()
    }

    override fun onResume() {
        super.onResume()
        mySettingsActivityView.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        mySettingsActivityView.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
