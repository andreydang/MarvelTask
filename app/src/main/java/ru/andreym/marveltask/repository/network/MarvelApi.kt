package ru.andreym.marveltask.repository.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.andreym.marveltask.repository.network.response.Character
import ru.andreym.marveltask.repository.network.response.MainResponse

interface MarvelApi {


    @GET("/v1/public/characters")
    suspend fun getCharacterList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ):  Response<MainResponse<Character>>
}