package com.example.sweethome.hrhelper.presentation.screens.event.presenter

import android.view.MenuItem
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.domain.use_cases.ChangeMemberStateUseCase
import com.example.sweethome.hrhelper.domain.use_cases.ConfirmArrivedMembersUseCase
import com.example.sweethome.hrhelper.domain.use_cases.GetMemberListUseCase
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EventPresenter(
    private var myEventPresenterContract: EventPresenterContract?,
    private val myEvent: Event
) {
    private val getMemberListUseCase: GetMemberListUseCase = GetMemberListUseCase()
    private val changeMemberStateUseCase: ChangeMemberStateUseCase = ChangeMemberStateUseCase()
    private val confirmArrivedMembersUseCase: ConfirmArrivedMembersUseCase = ConfirmArrivedMembersUseCase()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun attach(eventListPresenterContract: EventPresenterContract?) {
        myEventPresenterContract = eventListPresenterContract
    }

    fun detach() {
        myEventPresenterContract = null
        compositeDisposable.clear()
    }

    fun onOptionsItemSelected(item: MenuItem?) {
        when (item?.itemId) {
            R.id.item_settings -> onSettingItemClick()
            R.id.update_member_list_item -> loadMembersListRx()
            R.id.confirm_member_list_item -> sendConfirmation()
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
                    myEventPresenterContract?.showMessageSuccess(it.message.toString())
                }, {
                    myEventPresenterContract?.showMessageFail(it.message.toString())
                })
        )
    }

    private fun onSettingItemClick() =
        myEventPresenterContract?.startSettingsActivity()


    fun loadMembersListRx() {
        myEventPresenterContract?.showProgressBar()
        compositeDisposable.add(
            getMemberListUseCase.loadEventMemberListRx(myEvent.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    myEventPresenterContract?.loadMembersSuccess(it)
                    myEventPresenterContract?.hideProgressBar()
                }, {
                    myEventPresenterContract?.loadMembersFail()
                    myEventPresenterContract?.hideProgressBar()
                })
        )
    }

    fun onMemberClick(member: MemberDto?) =
        myEventPresenterContract?.startMemberInfoActivity(member)


    fun onMemberArrivedStateChanged(member: MemberDto) {
        member.visitedDate = System.currentTimeMillis()
        compositeDisposable.add(
            Single.create(SingleOnSubscribe<Int> { emitter ->
                emitter.onSuccess(changeMemberStateUseCase.changeMemberState(member))
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    interface EventPresenterContract {
        fun loadMembersFail()
        fun loadMembersSuccess(memberList: List<MemberDto>?)
        fun showProgressBar()
        fun hideProgressBar()
        fun getMembersArrivedList(): List<MemberDto>
        fun showMessageSuccess(message: String)
        fun showMessageFail(message: String)
        fun startSettingsActivity()
        fun startMemberInfoActivity(member: MemberDto?)
    }
}