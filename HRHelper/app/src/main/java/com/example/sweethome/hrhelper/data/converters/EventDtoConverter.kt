package com.example.sweethome.hrhelper.data.converters

import com.example.sweethome.hrhelper.data.dto.EventDto
import com.example.sweethome.hrhelper.data.utils.Constants.KEY_DATE_FORMAT
import com.example.sweethome.hrhelper.domain.entity.Event
import java.text.SimpleDateFormat
import java.util.*

fun EventDto.toEvent() = Event(
    id = this.id ?: -1,
    title = this.title ?: "",
    description = this.description ?: "",
    beginDate = SimpleDateFormat(KEY_DATE_FORMAT, Locale.US).parse(this.date?.start).time,
    endDate = SimpleDateFormat(KEY_DATE_FORMAT, Locale.US).parse(this.date?.end).time
)