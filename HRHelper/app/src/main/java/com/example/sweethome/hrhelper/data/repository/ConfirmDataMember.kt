package com.example.sweethome.hrhelper.data.repository

data class ConfirmDataMember(
    var id: Int = -1,
    var isVisited: Boolean = false


) {
    override fun toString(): String {
        return "[{\"id\":$id, \"isVisited\":$isVisited}]"
    }
}