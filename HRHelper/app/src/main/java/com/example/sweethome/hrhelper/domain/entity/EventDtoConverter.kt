package com.example.sweethome.hrhelper.domain.entity

import com.example.sweethome.hrhelper.data.dto.EventDto
import java.text.SimpleDateFormat
import java.util.*

fun EventDto.toEvent() = Event(
    id = this.id ?: -1,
    title = this.title ?: "",
    description = this.description ?: "",
    beginDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(this.date?.start).time,
    endDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(this.date?.end).time
)