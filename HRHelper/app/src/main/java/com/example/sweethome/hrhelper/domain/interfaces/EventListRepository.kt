package com.example.sweethome.hrhelper.domain.interfaces

import com.example.sweethome.hrhelper.data.dto.EventDto
import io.reactivex.Single

interface EventListRepository {
    fun loadEventListRx(): Single<List<EventDto>>
}