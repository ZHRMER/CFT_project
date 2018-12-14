package com.example.sweethome.hrhelper.presentation.screens.event.presenter

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.domain.use_cases.ChangeMemberStateUseCase
import com.example.sweethome.hrhelper.domain.use_cases.ConfirmArrivedMembersUseCase
import com.example.sweethome.hrhelper.domain.use_cases.GetMemberListUseCase
import com.example.sweethome.hrhelper.presentation.screens.member.view.MemberInfoActivity
import com.example.sweethome.hrhelper.presentation.screens.settings.view.SettingsActivity
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EventPresenter(
    private var myActivity: AppCompatActivity?,
    private var myEventPresenterContract: EventPresenterContract?,
    private val myEvent: Event,
    private var getMemberListUseCase: GetMemberListUseCase = GetMemberListUseCase(),
    private var changeMemberStateUseCase: ChangeMemberStateUseCase = ChangeMemberStateUseCase(),
    private var confirmArrivedMembersUseCase: ConfirmArrivedMembersUseCase = ConfirmArrivedMembersUseCase(),
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

) {
    private val KEY_CURRENT_EVENT = "current_event"
    private val KEY_CURRENT_MEMBER = "current_member"
    fun attach(activity: AppCompatActivity?, eventListPresenterContract: EventPresenterContract?) {
        myActivity = activity
        myEventPresenterContract = eventListPresenterContract
    }

    fun detach() {
        myActivity = null
        myEventPresenterContract = null
        compositeDisposable.clear()
    }

    fun onOptionsItemSelected(item: MenuItem?) {
        when (item?.itemId) {
            R.id.item_settings -> onSettingItemClick()
            R.id.update_member_list_item -> loadMembersListRx()
            else -> sendConfirmation()
        }
    }

    private fun sendConfirmation() {
        compositeDisposable.add(
            confirmArrivedMembersUseCase.confirmArrivedMembers(
                myEventPresenterContract?.getMembersArrivedList()!!,
                myEvent.id
            ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    myEventPresenterContract?.showToast("Send confirmation success: ${it.message}")
                }, {
                    myEventPresenterContract?.showToast("Send confirmation failed: ${it.message}")
                })
        )
    }

    private fun onSettingItemClick() {
        val settingsActivityIntent = SettingsActivity.newIntent(myActivity)
        settingsActivityIntent.putExtra(KEY_CURRENT_EVENT, myEvent)
        myActivity?.startActivity(settingsActivityIntent)
    }

    fun loadMembersListRx() {
        myEventPresenterContract?.startProgressBar()
        compositeDisposable.add(
            getMemberListUseCase.loadEventMemberListRx(myEvent.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    myEventPresenterContract?.getMembersSuccess(it)
                    myEventPresenterContract?.stopProgressBar()
                }, {
                    myEventPresenterContract?.getMembersFail()
                    myEventPresenterContract?.stopProgressBar()
                })
        )
    }

    fun onMemberClick(member: MemberDto?) {
        val memberInfoActivityIntent = MemberInfoActivity.newIntent(myActivity)
        memberInfoActivityIntent.putExtra(KEY_CURRENT_MEMBER, member)
        myActivity?.startActivity(memberInfoActivityIntent)
    }

    fun onMemberArrivedStateChanged(memberId: Int, myIsArrived: Boolean) {
        compositeDisposable.add(
            Single.create(SingleOnSubscribe<Int> { emitter ->
                emitter.onSuccess(changeMemberStateUseCase.changeMemberState(memberId, myEvent.id, myIsArrived))
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    interface EventPresenterContract {
        fun getMembersFail()
        fun getMembersSuccess(memberList: List<MemberDto>?)
        fun startProgressBar()
        fun stopProgressBar()
        fun getMembersArrivedList(): List<MemberDto>
        fun showToast(message: String)
    }
}