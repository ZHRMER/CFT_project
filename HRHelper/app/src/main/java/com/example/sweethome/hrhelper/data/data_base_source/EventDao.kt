package com.example.sweethome.hrhelper.data.data_base_source

import android.arch.persistence.room.*
import com.example.sweethome.hrhelper.data.dto.EventDto


@Dao
interface EventDao {

    @get:Query("SELECT * FROM event")
    val all: List<EventDto>

    @get:Query("Select count(*) from event")
    val size: Int

    @Insert
    fun insert(event: EventDto)

    @Update
    fun update(event: EventDto)

    @Delete
    fun delete(event: EventDto)

}