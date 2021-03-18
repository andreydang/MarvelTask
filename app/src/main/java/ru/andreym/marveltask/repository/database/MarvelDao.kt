package ru.andreym.marveltask.repository.database



import io.realm.RealmResults
import ru.andreym.marveltask.repository.database.entity.CharacterModel

import ru.andreym.marveltask.repository.network.response.Character


class MarvelDao(private val dbHelper: DBHelper) {


    fun insertCharacters(characters: List<Character>) {
        dbHelper.getDBInstance().let {
            val charactersModels = characters.map { character -> CharacterModel.from(character) }
            it.executeTransaction { realm -> realm.insertOrUpdate(charactersModels) }
        }
    }


    fun getCharactersRealm(): RealmResults<CharacterModel>? {
        var list: RealmResults<CharacterModel>? = null
        dbHelper.getDBInstance().let {
            it.beginTransaction()
            list = it.where(CharacterModel::class.java).findAll()
            it.commitTransaction()
        }
        return list
    }

    fun getCharacterById(id:Long): Character? {
        var character:CharacterModel? = null
        dbHelper.getDBInstance().let {
            it.executeTransaction { realm ->
                character = realm.where(CharacterModel::class.java).equalTo("id", id).findFirst()
            }
        }
        return character?.toCharacter()
    }

    fun deleteAllCharacters() {
        dbHelper.getDBInstance().let {
            it.executeTransaction { realm ->
                val result = it.where(CharacterModel::class.java).findAll()
                result.deleteAllFromRealm()
            }
        }
    }


}

