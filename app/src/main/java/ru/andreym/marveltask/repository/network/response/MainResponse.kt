package ru.andreym.marveltask.repository.network.response

data class MainResponse<T>(
    val code: Any,
    val status: String,
    val message: String,
    val data: DataResponse<T>
)
