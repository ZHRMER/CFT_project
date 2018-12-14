package com.example.sweethome.hrhelper.presentation.screens.event.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
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
    private val KEY_MEMBER_LIST = "member_list"
    private lateinit var myEventPresenter: EventPresenter
    private var myMemberList: ArrayList<MemberDto>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var memberAdapter: MemberListAdapter

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(KEY_MEMBER_LIST, myMemberList)
    }

    fun onCreate(savedInstanceState: Bundle?) {
        initToolbar()
        progressBar = myActivity?.findViewById(R.id.progress_bar_activity_event) as ProgressBar
        myEventPresenter = EventPresenter(myActivity, this, myEvent)
        if (savedInstanceState?.getParcelableArrayList<Event>(KEY_MEMBER_LIST) != null
            && savedInstanceState.getParcelableArrayList<Event>(KEY_MEMBER_LIST)?.size!! > 0
        ) {
            myMemberList = savedInstanceState.getParcelableArrayList<MemberDto>(KEY_MEMBER_LIST)
            stopProgressBar()
        } else {
            myMemberList = ArrayList()
            myEventPresenter.loadMembersListRx()
        }
        recyclerView = myActivity?.findViewById(R.id.recycler_view_member_list_activity_event) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(myActivity)
        memberAdapter = MemberListAdapter(myMemberList, this)
        recyclerView.adapter = memberAdapter
        initMemberSearch()
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

    private fun initMemberSearch() {
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
                    memberAdapter.updateMemberList(tempMemberList)
                }
            }
        })
    }

    fun onOptionsItemSelected(menuItem: MenuItem?) {
        myEventPresenter.onOptionsItemSelected(menuItem)
    }

    override fun getMembersSuccess(memberList: List<MemberDto>?) {
        if (memberList != null) {
            checkIsEmptyList(memberList)
            myMemberList?.clear()
            myMemberList?.addAll(memberList)
            memberAdapter.updateMemberList(memberList)
        }
    }

    private fun checkIsEmptyList(memberList: List<MemberDto>?) {
        if (memberList?.isEmpty()!!) {
            recyclerView.visibility = View.GONE
            val emptyTextView = myActivity?.findViewById(R.id.empty_member_recycler_text_view) as TextView
            emptyTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            val emptyTextView = myActivity?.findViewById(R.id.empty_member_recycler_text_view) as TextView
            emptyTextView.visibility = View.GONE
        }
    }

    override fun getMembersFail() {
        Toast.makeText(myActivity, myActivity?.getString(R.string.warning_load_members), Toast.LENGTH_SHORT).show()
    }

    override fun onMemberClick(member: MemberDto?) {
        myEventPresenter.onMemberClick(member)
    }

    override fun startProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun stopProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun getMembersArrivedList(): List<MemberDto> {
        val arrivedList = ArrayList<MemberDto>()
        myMemberList?.forEach {
            if (it.isArrived) {
                arrivedList.add(it)
            }
        }
        return arrivedList
    }

    override fun showToast(message: String) {
        Toast.makeText(myActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMemberArrivedStateChanged(memberId: Int, myIsArrived: Boolean) {
        myEventPresenter.onMemberArrivedStateChanged(memberId, myIsArrived)
    }
}