package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.repository.EventRepository
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.domain.entity.toEvent
import io.reactivex.Observable

class GetEventListUseCase(private val eventRepository: EventRepository = EventRepository()) {

    fun loadEventRx(): Observable<List<Event>> =
        eventRepository.loadEventListRx()
            .flatMap { list ->
                Observable.fromIterable(list)
                    .map { it -> it.toEvent() }
                    .toList().toObservable()
            }

}