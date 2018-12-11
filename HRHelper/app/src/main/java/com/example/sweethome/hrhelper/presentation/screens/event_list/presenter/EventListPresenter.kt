package com.example.sweethome.hrhelper.presentation.screens.event_list.presenter

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.domain.use_cases.GetEventListUseCase
import com.example.sweethome.hrhelper.presentation.screens.event.view.EventActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class EventListPresenter(
    private var myActivity: AppCompatActivity?,
    private var myEventListPresenterContract: EventListPresenterContract?,
    private var getEventListUseCase: GetEventListUseCase = GetEventListUseCase(),
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
) {

    fun attach(activity: AppCompatActivity?, eventListPresenterContract: EventListPresenterContract?) {
        myActivity = activity
        myEventListPresenterContract = eventListPresenterContract
    }

    fun detach() {
        myActivity = null
        myEventListPresenterContract = null
        compositeDisposable.clear()
    }

    fun onOptionsItemSelected(item: MenuItem?) {
        if (item?.itemId == R.id.update_event_item) {
            loadEventsListRx()
        }
    }

    fun loadEventsListRx() {
        compositeDisposable.add(getEventListUseCase.loadEventRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    myEventListPresenterContract?.getEventSuccess(it)
                },
                {
                    myEventListPresenterContract?.getEventFail()
                }
            ))
    }

    fun onEventClick(event: Event?) {
        val eventActivityIntent = EventActivity.newIntent(myActivity)
        eventActivityIntent.putExtra("CurrentEvent", event)
        myActivity?.startActivity(eventActivityIntent)
    }

    interface EventListPresenterContract {
        fun getEventFail()
        fun getEventSuccess(eventList: List<Event>?)
    }
}