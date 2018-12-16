package com.example.sweethome.hrhelper.data.repository

import com.example.sweethome.hrhelper.data.dto.EventDto
import com.example.sweethome.hrhelper.data.source.database.AppDatabase
import com.example.sweethome.hrhelper.data.source.database.EventDao
import com.example.sweethome.hrhelper.data.source.network.EventsApi
import com.example.sweethome.hrhelper.di.App
import com.example.sweethome.hrhelper.domain.interfaces.EventListRepository
import io.reactivex.Single


class EventListRepositoryImpl : EventListRepository {
    private val eventsApi: EventsApi = App.component.createEventApi()
    private val roomDb: AppDatabase = App.component.createRoom()
    private val eventDao: EventDao = roomDb.eventDao()

    override fun loadEventListRx(): Single<List<EventDto>> =
        loadEventListFromServer().onErrorResumeNext(getEventListFromDb())

    private fun loadEventListFromServer(): Single<List<EventDto>> =
        eventsApi.loadEventListRx().doOnSuccess { list -> saveEventList(list) }.flatMap { getEventListFromDb() }


    private fun getEventListFromDb(): Single<List<EventDto>> =
        eventDao.all


    private fun saveEventList(eventList: List<EventDto>) =
        eventDao.insertAllEvents(eventList)

}