package com.example.sweethome.hrhelper.data.data_base_source

import android.arch.persistence.room.*
import com.example.sweethome.hrhelper.data.model.Event


@Dao
interface EventDao {

    @get:Query("SELECT * FROM event")
    val all: List<Event>

    @get:Query("Select count(*) from event")
    val size: Int

    @Insert
    fun insert(event: Event)

    @Update
    fun update(event: Event)

    @Delete
    fun delete(event: Event)

}