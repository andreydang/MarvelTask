package ru.andreym.marveltask.repository.network.response

data class Character(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: CharacterThumbnail,
    val comics: Comics,
    val series: Series,
    val stories: Stories,
    val events: Events
)
