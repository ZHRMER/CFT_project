package com.example.sweethome.hrhelper.screens.event_list.presenter

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.common_models.Event
import com.example.sweethome.hrhelper.common_models.EventsApi
import com.example.sweethome.hrhelper.screens.event.view.EventActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EventListPresenter(
    private var myActivity: AppCompatActivity?,
    private var myEventListPresenterContract: EventListPresenterContract?
) {

    fun attach(activity: AppCompatActivity?, eventListPresenterContract: EventListPresenterContract?) {
        myActivity = activity
        myEventListPresenterContract = eventListPresenterContract
        loadEventsList()
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

    private fun loadEventsList() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://beta-team.cft.ru/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val eventsListApi = retrofit.create<EventsApi>(EventsApi::class.java)

        val eventsList = eventsListApi.events()

        eventsList.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    myEventListPresenterContract?.getEventSuccess(response.body())
                } else {
                    myEventListPresenterContract?.getEventFail()
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
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