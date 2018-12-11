package com.example.sweethome.hrhelper.data.repository

import com.example.sweethome.hrhelper.data.EventsApi
import com.example.sweethome.hrhelper.data.data_base_source.AppDatabase
import com.example.sweethome.hrhelper.data.data_base_source.EventDao
import com.example.sweethome.hrhelper.data.data_base_source.MemberDao
import com.example.sweethome.hrhelper.data.dto.EventDto
import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.di.App
import com.example.sweethome.hrhelper.extension.warning
import io.reactivex.Observable
import io.reactivex.Single


class EventRepository(
    private val eventsApi: EventsApi = App.component.createEventApi(),
    private val roomDb: AppDatabase = App.component.createRoom(),
    private val memberDao: MemberDao = roomDb.memberDao(),
    private val eventDao: EventDao = roomDb.eventDao()
) {

    fun loadEventListRx(): Observable<List<EventDto>> =
        loadEventListFromServer().onErrorResumeNext(getEventListFromDb()).toObservable()


    private fun loadEventListFromServer(): Single<List<EventDto>> {
        return eventsApi.loadEventListRx().doOnSuccess { las -> saveEventList(las) }
    }

    private fun getEventListFromDb(): Single<List<EventDto>> =
        eventDao.all


    private fun saveEventList(eventList: List<EventDto>) {
        eventList.forEach { eventDao.insert(it) }
        warning(eventDao.size.toString())
    }

    fun loadMemberListRx(myEventId: Int): Single<List<MemberDto>> =
        eventsApi.loadEventMemberListRx(myEventId)
            .doOnSuccess { saveMemberList(it, myEventId) }
            .onErrorResumeNext(getMemberListFromDb(myEventId))

    private fun getMemberListFromDb(myEventId: Int): Single<List<MemberDto>> =
        memberDao.getMembers(myEventId)


    private fun saveMemberList(eventList: List<MemberDto>, eventId: Int) {
        eventList.forEach {
            it.eventId = eventId
            memberDao.insert(it)
        }
    }

    fun changeMemberArrivedState(memberId: Int, eventId: Int, myIsArrived: Boolean): Int =
        memberDao.changeMemberArrivedState(memberId, eventId, myIsArrived)

}