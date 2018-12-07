package com.example.sweethome.hrhelper.screens.event_list.view

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.common_models.Event
import com.example.sweethome.hrhelper.screens.event_list.presenter.EventListPresenter


class EventListActivityView(private var myActivity: AppCompatActivity?) :
    EventListPresenter.EventListPresenterContract, EventListAdapter.EventListAdapterContract {

    private lateinit var eventListPresenter: EventListPresenter
    private lateinit var myEventList: ArrayList<Event>
    private lateinit var recyclerView: RecyclerView
    fun onCreate() {
        initToolbar()
        eventListPresenter = EventListPresenter(myActivity, this)

        recyclerView = myActivity?.findViewById(R.id.recycler_view_activity_event_list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(myActivity)
        myEventList = ArrayList()
        val adapter = EventListAdapter(myEventList, this)
        recyclerView.adapter = adapter
    }

    fun onResume(activity: AppCompatActivity) {
        myActivity = activity
        eventListPresenter.attach(myActivity, this)
    }

    fun onPause() {
        myActivity = null
        eventListPresenter.detach()
    }

    private fun initToolbar() {
        val toolbar = myActivity?.findViewById<Toolbar>(R.id.toolbar_activity_event_list)
        myActivity?.setSupportActionBar(toolbar)
        myActivity?.supportActionBar?.setTitle(R.string.event_list_title)
    }

    fun onOptionsItemSelected(menuItem: MenuItem?) {
        eventListPresenter.onOptionsItemSelected(menuItem)
    }

    override fun getEventSuccess(eventList: List<Event>?) {
        myEventList.clear()
        if (eventList != null) {
            myEventList.addAll(eventList)
        }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun getEventFail() {
        Toast.makeText(myActivity, "Can't get event", Toast.LENGTH_SHORT).show()
    }

    override fun onEventClick(event: Event?) {
        eventListPresenter.onEventClick(event)
    }
}