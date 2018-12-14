package com.example.sweethome.hrhelper.data.source.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.sweethome.hrhelper.data.dto.EventDto
import io.reactivex.Single


@Dao
interface EventDao {

    @get:Query("SELECT * FROM event")
    val all: Single<List<EventDto>>

    @get:Query("SELECT count(*) FROM event")
    val size: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: EventDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEvents(events: List<EventDto>)
}