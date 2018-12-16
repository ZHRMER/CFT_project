package com.example.sweethome.hrhelper.data.source.database

import android.arch.persistence.room.*
import com.example.sweethome.hrhelper.data.dto.MemberDto
import io.reactivex.Single

@Dao
interface MemberDao {

    @Query("SELECT * FROM member WHERE event_id=:eventId")
    fun getMembers(eventId: Int): Single<List<MemberDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllMembers(events: List<MemberDto>)

    @Update
    fun updateMember(member: MemberDto): Int

    @Query("Select count(*) from member WHERE event_id=:eventId and isArrived = 1")
    fun getArrivedMemberCountEvent(eventId: Int): Int

    @Query("Select count(*) from member WHERE event_id=:eventId")
    fun getRegisteredMemberCountEvent(eventId: Int): Int
}