package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.repository.CommonRepository
import com.example.sweethome.hrhelper.di.App

class ChangeMemberStateUseCase(private val commonRepository: CommonRepository = App.component.createEventRepository()) {
    fun changeMemberState(memberId: Int, eventId: Int, myIsArrived: Boolean): Int {
        return commonRepository.changeMemberArrivedState(memberId, eventId, myIsArrived)
    }
}