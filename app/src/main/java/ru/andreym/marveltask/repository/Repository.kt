package ru.andreym.marveltask.repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import ru.andreym.marveltask.repository.database.MarvelDao
import ru.andreym.marveltask.repository.network.MarvelApi
import ru.andreym.marveltask.repository.network.response.Character
import ru.andreym.marveltask.repository.network.response.MainResponse
import ru.andreym.marveltask.utils.Result

class Repository(val api: MarvelApi, val marvelDao: MarvelDao) {


    suspend fun fetchCharacters(offset: Int, limit: Int): Flow<Result<MainResponse<Character>>?> =
        fetch { api.getCharacterList(offset = offset, limit = limit) }


    private suspend fun <T> fetch(request: suspend () -> Response<T>): Flow<Result<T>?> {
        return flow {
            emit(Result.loading())
            val result = getResponse(request)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                Result.error(result.errorBody().toString(), "Status code is ${result.code()}")
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", e.message)
        }
    }


}