package com.example.sweethome.hrhelper.data.source.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.sweethome.hrhelper.data.dto.MemberDto
import io.reactivex.Single

@Dao
interface MemberDao {

    @Query("SELECT * FROM member WHERE event_id=:eventId")
    fun getMembers(eventId: Int): Single<List<MemberDto>>

    @get:Query("SELECT count(*) FROM member")
    val size: Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(memberDto: MemberDto)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllMembers(events: List<MemberDto>)

    @Query("UPDATE member SET isArrived = :myIsArrive  WHERE event_id=:eventId AND member_id=:memberId")
    fun changeMemberArrivedState(memberId: Int, eventId: Int, myIsArrive: Boolean): Int

    @Query("Select count(*) from member WHERE event_id=:eventId and isArrived = 1")
    fun getArrivedMemberCountEvent(eventId: Int): Int

    @Query("Select count(*) from member WHERE event_id=:eventId")
    fun getRegisteredMemberCountEvent(eventId: Int): Int
}