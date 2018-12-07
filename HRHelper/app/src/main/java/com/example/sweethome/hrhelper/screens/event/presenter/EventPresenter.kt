package com.example.sweethome.hrhelper.screens.event.presenter

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.common_models.EventsApi
import com.example.sweethome.hrhelper.common_models.User
import com.example.sweethome.hrhelper.screens.settings.view.SettingsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventPresenter(
    private var myActivity: AppCompatActivity?,
    private var myEventPresenterContract: EventPresenterContract?,
    private val myEventId: Int
) {

    fun attach(activity: AppCompatActivity?, eventListPresenterContract: EventPresenterContract?) {
        myActivity = activity
        myEventPresenterContract = eventListPresenterContract
        loadMembersList()
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

    private fun loadMembersList() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://beta-team.cft.ru/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val eventsListApi = retrofit.create<EventsApi>(EventsApi::class.java)

        val eventsList = eventsListApi.getEventMembers(myEventId)

        eventsList.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    myEventPresenterContract?.getEventSuccess(response.body())
                } else {
                    myEventPresenterContract?.getEventFail()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                myEventPresenterContract?.getEventFail()
            }
        })
    }

    interface EventPresenterContract {
        fun getEventFail()
        fun getEventSuccess(memberList: List<User>?)
    }
}