package com.example.sweethome.hrhelper.screens.event.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.common_models.Event

class EventActivity : AppCompatActivity() {
    private lateinit var myEventActivityView: EventActivityView
    private lateinit var myEvent: Event

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, EventActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        myEvent = intent.getParcelableExtra("CurrentEvent")
        myEventActivityView = EventActivityView(this, myEvent)
        myEventActivityView.onCreate()
    }

    override fun onResume() {
        super.onResume()
        myEventActivityView.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        myEventActivityView.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.event_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        myEventActivityView.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
