package com.example.sweethome.hrhelper.presentation.screens.event_list.view

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.sweethome.hrhelper.R

class EventListActivity : AppCompatActivity() {
    private lateinit var myEventListActivityView: EventListActivityView

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        myEventListActivityView.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        myEventListActivityView =
                EventListActivityView(this)
        myEventListActivityView.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        myEventListActivityView.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        myEventListActivityView.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.events_list_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        myEventListActivityView.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }
}
