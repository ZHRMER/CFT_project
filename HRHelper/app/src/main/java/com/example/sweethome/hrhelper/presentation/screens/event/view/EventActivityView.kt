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
import com.example.sweethome.hrhelper.data.utils.Constants.KEY_CURRENT_EVENT
import com.example.sweethome.hrhelper.data.utils.Constants.KEY_CURRENT_MEMBER
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.presentation.screens.event.presenter.EventPresenter
import com.example.sweethome.hrhelper.presentation.screens.member.view.MemberInfoActivity
import com.example.sweethome.hrhelper.presentation.screens.settings.view.SettingsActivity

class EventActivityView(
    private var myActivity: AppCompatActivity?,
    private val myEvent: Event
) :
    EventPresenter.EventPresenterContract,
    MemberListAdapter.MemberListAdapterContract {
    private val KEY_MEMBER_LIST = "member_list"
    private var myMemberList: ArrayList<MemberDto>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var memberAdapter: MemberListAdapter
    private lateinit var emptyTextView: TextView
    private lateinit var myEventPresenter: EventPresenter

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(KEY_MEMBER_LIST, myMemberList)
    }

    fun onCreate(savedInstanceState: Bundle?) {
        initToolbar()
        emptyTextView = myActivity?.findViewById(R.id.empty_member_recycler_text_view) as TextView
        progressBar = myActivity?.findViewById(R.id.progress_bar_activity_event) as ProgressBar
        myEventPresenter = EventPresenter(this, myEvent)
        if (savedInstanceState?.getParcelableArrayList<Event>(KEY_MEMBER_LIST) != null
            && savedInstanceState.getParcelableArrayList<Event>(KEY_MEMBER_LIST)?.size!! > 0
        ) {
            myMemberList = savedInstanceState.getParcelableArrayList<MemberDto>(KEY_MEMBER_LIST)
            hideProgressBar()
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
        myEventPresenter.attach(this)
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

    fun onOptionsItemSelected(menuItem: MenuItem?) =
        myEventPresenter.onOptionsItemSelected(menuItem)


    override fun loadMembersSuccess(memberList: List<MemberDto>?) {
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
            emptyTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        }
    }

    override fun loadMembersFail() =
        Toast.makeText(myActivity, myActivity?.getString(R.string.warning_load_members), Toast.LENGTH_SHORT).show()


    override fun onMemberClick(member: MemberDto?) {
        myEventPresenter.onMemberClick(member)
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
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

    override fun showMessageSuccess(message: String) =
        Toast.makeText(
            myActivity,
            myActivity?.getString(R.string.send_confirm_success) + message,
            Toast.LENGTH_SHORT
        ).show()

    override fun showMessageFail(message: String) {
        Toast.makeText(
            myActivity,
            myActivity?.getString(R.string.send_confirm_fail) + message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onMemberArrivedStateChanged(member: MemberDto) =
        myEventPresenter.onMemberArrivedStateChanged(member)

    override fun startSettingsActivity() {
        val settingsActivityIntent = SettingsActivity.newIntent(myActivity)
        settingsActivityIntent.putExtra(KEY_CURRENT_EVENT, myEvent)
        myActivity?.startActivity(settingsActivityIntent)
    }

    override fun startMemberInfoActivity(member: MemberDto?) {
        val memberInfoActivityIntent = MemberInfoActivity.newIntent(myActivity)
        memberInfoActivityIntent.putExtra(KEY_CURRENT_MEMBER, member)
        myActivity?.startActivity(memberInfoActivityIntent)
    }
}