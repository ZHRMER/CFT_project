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
import com.example.sweethome.hrhelper.data.utils.Constants.KEY_CURRENT_EVENT
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.presentation.screens.event.view.EventActivity
import com.example.sweethome.hrhelper.presentation.screens.event_list.presenter.EventListPresenter


class EventListActivityView(private var myActivity: AppCompatActivity?) :
    EventListPresenter.EventListPresenterContract,
    EventListAdapter.EventListAdapterContract {
    private val KEY_EVENT_LIST: String = "event_list"
    private lateinit var eventListPresenter: EventListPresenter
    private var myEventList: ArrayList<Event>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyTextView: TextView

    fun onSaveInstanceState(outState: Bundle?) =
        outState?.putParcelableArrayList(KEY_EVENT_LIST, myEventList)


    fun onCreate(savedInstanceState: Bundle?) {
        initToolbar()
        emptyTextView = myActivity?.findViewById(R.id.empty_event_recycler_text_view) as TextView
        progressBar = myActivity?.findViewById(R.id.progress_bar_activity_event_list)!!
        eventListPresenter = EventListPresenter(this)
        if (savedInstanceState?.getParcelableArrayList<Event>(KEY_EVENT_LIST) != null) {
            myEventList = savedInstanceState.getParcelableArrayList<Event>(KEY_EVENT_LIST)
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
        eventListPresenter.attach(this)
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

    fun onOptionsItemSelected(menuItem: MenuItem?) =
        eventListPresenter.onOptionsItemSelected(menuItem)


    override fun loadEventSuccess(eventList: List<Event>?) {
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
            emptyTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        }
    }

    override fun loadEventFail() =
        Toast.makeText(myActivity, myActivity?.getString(R.string.load_event_warning), Toast.LENGTH_SHORT).show()


    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onEventClick(event: Event?) {
        eventListPresenter.onEventClick(event)
    }

    override fun startEventActivity(event: Event?) {
        val eventActivityIntent = EventActivity.newIntent(myActivity)
        eventActivityIntent.putExtra(KEY_CURRENT_EVENT, event)
        myActivity?.startActivity(eventActivityIntent)
    }
}