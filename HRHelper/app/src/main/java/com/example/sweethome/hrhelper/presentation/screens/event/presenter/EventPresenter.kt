package com.example.sweethome.hrhelper.presentation.screens.event.presenter

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.domain.use_cases.GetMemberListUseCase
import com.example.sweethome.hrhelper.extension.warning
import com.example.sweethome.hrhelper.presentation.callbacks.Carry
import com.example.sweethome.hrhelper.presentation.screens.member.view.MemberInfoActivity
import com.example.sweethome.hrhelper.presentation.screens.settings.view.SettingsActivity

class EventPresenter(
    private var myActivity: AppCompatActivity?,
    private var myEventPresenterContract: EventPresenterContract?,
    private val myEventId: Int,
    private var getMemberListUseCase: GetMemberListUseCase = GetMemberListUseCase()
) {
    init {
        warning("EventPresenter created")
    }

    fun attach(activity: AppCompatActivity?, eventListPresenterContract: EventPresenterContract?) {
        myActivity = activity
        myEventPresenterContract = eventListPresenterContract
    }

    fun detach() {
        myActivity = null
        myEventPresenterContract = null
    }

    fun onOptionsItemSelected(item: MenuItem?) {
        if (item?.itemId == R.id.item_settings) {
            onSettingItemClick()
        } else {
            loadMembersList()
        }
    }

    private fun onSettingItemClick() {
        val settingsActivityIntent = SettingsActivity.newIntent(myActivity)
        myActivity?.startActivity(settingsActivityIntent)
    }

    fun loadMembersList() {
        getMemberListUseCase.getMemberList(myEventId, object : Carry<List<MemberDto>> {

            override fun onSuccess(result: List<MemberDto>) {
                myEventPresenterContract?.getEventSuccess(result)
            }

            override fun onFailure(throwable: Throwable) {
                myEventPresenterContract?.getEventFail()
            }
        })
    }

    fun onMemberClick(member: MemberDto?) {
        val memberInfoActivityIntent = MemberInfoActivity.newIntent(myActivity)
        memberInfoActivityIntent.putExtra("CurrentMember", member)
        myActivity?.startActivity(memberInfoActivityIntent)
    }

    interface EventPresenterContract {
        fun getEventFail()
        fun getEventSuccess(memberList: List<MemberDto>?)
    }
}