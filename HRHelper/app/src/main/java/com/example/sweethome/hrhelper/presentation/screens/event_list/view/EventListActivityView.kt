package com.example.sweethome.hrhelper.presentation.screens.event_list.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.presentation.screens.event_list.presenter.EventListPresenter


class EventListActivityView(private var myActivity: AppCompatActivity?) :
    EventListPresenter.EventListPresenterContract,
    EventListAdapter.EventListAdapterContract {

    private lateinit var eventListPresenter: EventListPresenter
    private var myEventList: ArrayList<Event>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList("event_list", myEventList)
    }

    fun onCreate(savedInstanceState: Bundle?) {
        initToolbar()
        progressBar = myActivity?.findViewById(R.id.progress_bar_activity_event_list)!!
        eventListPresenter = EventListPresenter(myActivity, this)
        if (savedInstanceState?.getParcelableArrayList<Event>("event_list") != null) {
            myEventList = savedInstanceState.getParcelableArrayList<Event>("event_list")
        } else {
            myEventList = ArrayList()
            eventListPresenter.loadEventsListRx()
        }
        recyclerView = myActivity?.findViewById(R.id.recycler_view_activity_event_list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(myActivity)
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
        if (eventList != null) {
            myEventList?.clear()
            checkIsEmptyList(eventList)
            myEventList?.addAll(eventList)
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun checkIsEmptyList(eventList: List<Event>?) {
        if (eventList?.isEmpty()!!) {
            recyclerView.visibility = View.GONE
            val emptyTextView = myActivity?.findViewById(R.id.empty_event_recycler_text_view) as TextView
            emptyTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            val emptyTextView = myActivity?.findViewById(R.id.empty_event_recycler_text_view) as TextView
            emptyTextView.visibility = View.GONE
        }
    }

    override fun getEventFail() {
        Toast.makeText(myActivity, "Can't get event", Toast.LENGTH_SHORT).show()
    }

    override fun startProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun stopProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onEventClick(event: Event?) {
        eventListPresenter.onEventClick(event)
    }
}