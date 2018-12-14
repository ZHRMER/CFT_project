package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.data.repository.CommonRepository
import com.example.sweethome.hrhelper.data.source.network.Example
import com.example.sweethome.hrhelper.di.App
import io.reactivex.Observable

class ConfirmArrivedMembersUseCase(private val commonRepository: CommonRepository = App.component.createEventRepository()) {
    fun confirmArrivedMembers(memberList: List<MemberDto>, eventId: Int): Observable<Example> {
        return commonRepository.confirmMembersArrived(eventId, memberList)
    }
}