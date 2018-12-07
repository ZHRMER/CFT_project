package com.example.sweethome.hrhelper.screens.event_list.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.sweethome.hrhelper.R

class EventListActivity : AppCompatActivity() {
    private lateinit var eventListActivityView: EventListActivityView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        eventListActivityView = EventListActivityView(this)
        eventListActivityView.onCreate()
    }

    override fun onResume() {
        super.onResume()
        eventListActivityView.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        eventListActivityView.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.events_list_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        eventListActivityView.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }
}
