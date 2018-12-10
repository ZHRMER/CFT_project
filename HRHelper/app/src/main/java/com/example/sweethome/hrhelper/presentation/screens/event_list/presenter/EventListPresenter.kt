package com.example.sweethome.hrhelper.presentation.screens.event_list.presenter

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.data.model.Event
import com.example.sweethome.hrhelper.domain.use_cases.GetEventListUseCase
import com.example.sweethome.hrhelper.presentation.callbacks.Carry
import com.example.sweethome.hrhelper.presentation.screens.event.view.EventActivity


class EventListPresenter(
    private var myActivity: AppCompatActivity?,
    private var myEventListPresenterContract: EventListPresenterContract?
) {

    fun attach(activity: AppCompatActivity?, eventListPresenterContract: EventListPresenterContract?) {
        myActivity = activity
        myEventListPresenterContract = eventListPresenterContract
    }

    fun detach() {
        myActivity = null
        myEventListPresenterContract = null
    }

    fun onOptionsItemSelected(item: MenuItem?) {
        if (item?.itemId == R.id.update_event_item) {
            loadEventsList()
        }
    }

    fun loadEventsList() {
        val getEventListUseCase = GetEventListUseCase()
        getEventListUseCase.getEventList(object : Carry<List<Event>> {

            override fun onSuccess(result: List<Event>) {
                myEventListPresenterContract?.getEventSuccess(result)
            }

            override fun onFailure(throwable: Throwable) {
                myEventListPresenterContract?.getEventFail()
            }
        })
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