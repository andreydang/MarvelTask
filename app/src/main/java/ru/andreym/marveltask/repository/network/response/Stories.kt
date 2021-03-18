package ru.andreym.marveltask.repository.network.response



data class Stories(
    val available: Int,
    val items: List<Item>
)