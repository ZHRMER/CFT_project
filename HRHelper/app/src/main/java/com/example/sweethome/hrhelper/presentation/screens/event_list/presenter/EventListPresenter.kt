package com.example.sweethome.hrhelper.presentation.screens.event_list.presenter

import android.view.MenuItem
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.domain.use_cases.GetEventListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class EventListPresenter(
    private var myEventListPresenterContract: EventListPresenterContract?
) {
    private val getEventListUseCase: GetEventListUseCase = GetEventListUseCase()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun attach(eventListPresenterContract: EventListPresenterContract?) {
        myEventListPresenterContract = eventListPresenterContract
    }

    fun detach() {
        myEventListPresenterContract = null
        compositeDisposable.clear()
    }

    fun onOptionsItemSelected(item: MenuItem?) {
        if (item?.itemId == R.id.update_event_item) {
            loadEventsListRx()
        }
    }

    fun loadEventsListRx() {
        myEventListPresenterContract?.showProgressBar()
        compositeDisposable.add(getEventListUseCase.loadEventRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    myEventListPresenterContract?.loadEventSuccess(it)
                    myEventListPresenterContract?.hideProgressBar()
                },
                {
                    myEventListPresenterContract?.loadEventFail()
                    myEventListPresenterContract?.hideProgressBar()
                }
            ))
    }

    fun onEventClick(event: Event?) =
        myEventListPresenterContract?.startEventActivity(event)


    interface EventListPresenterContract {
        fun loadEventFail()
        fun loadEventSuccess(eventList: List<Event>?)
        fun showProgressBar()
        fun hideProgressBar()
        fun startEventActivity(event: Event?)
    }
}