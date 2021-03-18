package ru.andreym.marveltask.repository.network.response

data class Comics(
    val available: Int,
    val items: List<Item>
)