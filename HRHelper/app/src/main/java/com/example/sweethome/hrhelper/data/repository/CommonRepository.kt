package com.example.sweethome.hrhelper.data.repository

import com.example.sweethome.hrhelper.data.dto.EventDto
import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.data.source.database.AppDatabase
import com.example.sweethome.hrhelper.data.source.database.EventDao
import com.example.sweethome.hrhelper.data.source.database.MemberDao
import com.example.sweethome.hrhelper.data.source.network.EventsApi
import com.example.sweethome.hrhelper.data.source.network.Example
import com.example.sweethome.hrhelper.di.App
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single


class CommonRepository(
    private val eventsApi: EventsApi = App.component.createEventApi(),
    private val roomDb: AppDatabase = App.component.createRoom(),
    private val memberDao: MemberDao = roomDb.memberDao(),
    private val eventDao: EventDao = roomDb.eventDao()
) {

    fun loadEventListRx(): Single<List<EventDto>> = loadEventListFromServer().onErrorResumeNext(getEventListFromDb())

    private fun loadEventListFromServer(): Single<List<EventDto>> =
        eventsApi.loadEventListRx().doOnSuccess { list -> saveEventList(list) }.flatMap { getEventListFromDb() }


    private fun getEventListFromDb(): Single<List<EventDto>> =
        eventDao.all


    private fun saveEventList(eventList: List<EventDto>) =
        eventDao.insertAllEvents(eventList)

    fun loadMemberListRx(myEventId: Int): Single<List<MemberDto>> =
        eventsApi.loadEventMemberListRx(myEventId)
            .doOnSuccess { saveMemberList(it, myEventId) }
            .onErrorResumeNext(getMemberListFromDb(myEventId)).flatMap { getMemberListFromDb(myEventId) }

    private fun getMemberListFromDb(myEventId: Int): Single<List<MemberDto>> =
        memberDao.getMembers(myEventId)


    private fun saveMemberList(memberList: List<MemberDto>, eventId: Int) {
        memberList.forEach {
            it.eventId = eventId
        }
        memberDao.insertAllMembers(memberList)
    }

    fun changeMemberArrivedState(memberId: Int, eventId: Int, myIsArrived: Boolean): Int =
        memberDao.changeMemberArrivedState(memberId, eventId, myIsArrived)

    fun getMemberStatistic(eventId: Int): List<Int> {
        val statisticList = ArrayList<Int>()
        statisticList.add(memberDao.getRegisteredMemberCountEvent(eventId))
        statisticList.add(memberDao.getArrivedMemberCountEvent(eventId))
        return statisticList
    }

    fun confirmMembersArrived(eventId: Int, memberList: List<MemberDto>): Observable<Example> {
        val memberJsonArray = JsonArray()
        memberList.forEach {
            val currentJSONMember = JsonObject()
            currentJSONMember.addProperty("id", it.id)
            currentJSONMember.addProperty("isVisited", it.isArrived)
            memberJsonArray.add(currentJSONMember)
        }
        return eventsApi.sendConfirmationMemberArrived(eventId, memberJsonArray)
    }

}