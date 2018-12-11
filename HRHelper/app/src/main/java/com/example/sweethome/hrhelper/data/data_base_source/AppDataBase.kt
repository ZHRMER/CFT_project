package com.example.sweethome.hrhelper.data.data_base_source

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.sweethome.hrhelper.data.dto.EventDto
import com.example.sweethome.hrhelper.data.dto.MemberDto

@Database(entities = [EventDto::class, MemberDto::class], version = 1, exportSchema = false)
@TypeConverters(EventConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun memberDao(): MemberDao
}