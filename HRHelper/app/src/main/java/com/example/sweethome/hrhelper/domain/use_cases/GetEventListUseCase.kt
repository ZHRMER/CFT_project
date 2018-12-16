package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.converters.toEvent
import com.example.sweethome.hrhelper.data.repository.EventListRepositoryImpl
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.domain.interfaces.EventListRepository
import io.reactivex.Single

class GetEventListUseCase(private val eventListRepository: EventListRepository = EventListRepositoryImpl()) {

    fun loadEventRx(): Single<List<Event>> =
        eventListRepository.loadEventListRx()
            .map { list ->
                list.map {
                    it.toEvent()
                }
            }

}