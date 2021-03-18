package ru.andreym.marveltask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.andreym.marveltask.repository.Repository
import ru.andreym.marveltask.repository.network.response.Character
import ru.andreym.marveltask.repository.network.response.MainResponse
import ru.andreym.marveltask.utils.Result
import timber.log.Timber

class MainViewModel(private val repository: Repository) : ViewModel() {

    val characters = MutableLiveData<Result<MainResponse<Character>>>()
    val charactersRealmFromDb = repository.marvelDao.getCharactersRealm()


    fun fetchCharactersById(id: Long): Character? {
        return repository.marvelDao.getCharacterById(id)
    }

    fun fetchCharacters(offset: Int, limit: Int) {
        viewModelScope.launch {
            repository.fetchCharacters(offset, limit).collect { result ->
                characters.value = result
                result?.let {
                    when (it.status) {
                        Result.Status.SUCCESS -> {
                            result.data?.let { response ->
                                val list = response.data.results
                                repository.marvelDao.insertCharacters(list)
                            }
                        }

                        Result.Status.ERROR -> {
                            Timber.d("${result.message} ${result.error}")
                        }

                        Result.Status.LOADING -> {

                        }
                    }
                }


            }
        }
    }

}