package com.example.sweethome.hrhelper.screens.event.view

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.common_models.Event
import com.example.sweethome.hrhelper.common_models.User
import com.example.sweethome.hrhelper.screens.event.presenter.EventPresenter

class EventActivityView(
    private var myActivity: AppCompatActivity?,
    private val myEvent: Event
) :
    EventPresenter.EventPresenterContract {
    private lateinit var myEventPresenter: EventPresenter
    private lateinit var myMemberList: ArrayList<User>
    private lateinit var recyclerView: RecyclerView
    fun onCreate() {
        initToolbar()
        myEventPresenter = EventPresenter(myActivity, this, myEvent.id!!)
        recyclerView = myActivity?.findViewById(R.id.recycler_view_member_list_activity_event) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(myActivity)
        myMemberList = ArrayList()
        val adapter = MemberListAdapter(myMemberList)
        recyclerView.adapter = adapter
    }

    fun onResume(activity: AppCompatActivity) {
        myActivity = activity
        myEventPresenter.attach(myActivity, this)
    }

    fun onPause() {
        myActivity = null
        myEventPresenter.detach()
    }

    private fun initToolbar() {
        val toolbar = myActivity?.findViewById<Toolbar>(R.id.toolbar_activity_event)
        myActivity?.setSupportActionBar(toolbar)
        myActivity?.supportActionBar?.title = myEvent.title
        myActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun onOptionsItemSelected(menuItem: MenuItem?) {
        myEventPresenter.onOptionsItemSelected(menuItem)
    }

    override fun getEventSuccess(memberList: List<User>?) {
        myMemberList.clear()
        if (memberList != null) {
            myMemberList.addAll(memberList)
        }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun getEventFail() {
        Toast.makeText(myActivity, "Can't get member list", Toast.LENGTH_SHORT).show()
    }
}