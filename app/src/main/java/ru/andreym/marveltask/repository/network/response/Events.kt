package ru.andreym.marveltask.repository.network.response

data class Events(
    val available: Int,
    val items: List<Item>
)