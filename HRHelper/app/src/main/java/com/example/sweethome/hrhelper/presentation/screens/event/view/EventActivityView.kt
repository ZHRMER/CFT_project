package com.example.sweethome.hrhelper.presentation.screens.event.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.presentation.screens.event.presenter.EventPresenter

class EventActivityView(
    private var myActivity: AppCompatActivity?,
    private val myEvent: Event
) :
    EventPresenter.EventPresenterContract,
    MemberListAdapter.MemberListAdapterContract {
    private lateinit var myEventPresenter: EventPresenter
    private var myMemberList: ArrayList<MemberDto>? = null
    private lateinit var recyclerView: RecyclerView

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList("member_list", myMemberList)
    }

    fun onCreate(savedInstanceState: Bundle?) {
        initToolbar()
        myEventPresenter = EventPresenter(
            myActivity,
            this,
            myEvent.id!!
        )
        if (savedInstanceState?.getParcelableArrayList<Event>("member_list") != null) {
            myMemberList = savedInstanceState.getParcelableArrayList<MemberDto>("member_list")
        } else {
            myMemberList = ArrayList()
            myEventPresenter.loadMembersListRx()
        }
        recyclerView = myActivity?.findViewById(R.id.recycler_view_member_list_activity_event) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(myActivity)
        val adapter = MemberListAdapter(myMemberList, this)
        recyclerView.adapter = adapter


        val searchEditText = myActivity?.findViewById<EditText>(R.id.search_member_edit_text)
        searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val tempMemberList = ArrayList<MemberDto>()
                val fullMemberList = myMemberList
                if (fullMemberList != null) {
                    for (user in fullMemberList) {
                        val fullName = user.firstName + " " + user.lastName
                        if (fullName.startsWith(p0!!, true)) {
                            tempMemberList.add(user)
                        }
                    }
                    val tempAdapter =
                        MemberListAdapter(tempMemberList, this@EventActivityView)
                    recyclerView.adapter = tempAdapter
                }
            }
        })
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

    override fun getMembersSuccess(memberList: List<MemberDto>?) {
        myMemberList?.clear()
        if (memberList != null) {
            myMemberList?.addAll(memberList)
        }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun getMembersFail() {
        Toast.makeText(myActivity, "Can't get member list", Toast.LENGTH_SHORT).show()
    }

    override fun onMemberClick(member: MemberDto?) {
        myEventPresenter.onMemberClick(member)
    }

    override fun onMemberArrivedStateChanged(memberId: Int, myIsArrived: Boolean) {
        myEventPresenter.onMemberArrivedStateChanged(memberId, myIsArrived)
    }
}