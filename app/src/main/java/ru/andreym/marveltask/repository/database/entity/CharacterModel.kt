package ru.andreym.marveltask.repository.database.entity


import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.andreym.marveltask.repository.network.response.*


open class CharacterModel(
    @PrimaryKey
    var id: Long? = 0,
    var name: String? = "",
    var description: String? = "",
    var thumbnail: CharacterThumbnailModel? = CharacterThumbnailModel("", ""),
    var comics: ComicsModel? = ComicsModel(0, RealmList(ItemModel(""))),
    var series: SeriesModel? = SeriesModel(0, RealmList(ItemModel(""))),
    var stories: StoriesModel? = StoriesModel(0, RealmList(ItemModel(""))),
    var events: EventsModel? = EventsModel(0, RealmList(ItemModel("")))
) : RealmObject() {

    companion object {
        fun from(character: Character): CharacterModel =
            CharacterModel(
                character.id,
                character.name,
                character.description,
                CharacterThumbnailModel(character.thumbnail.path, character.thumbnail.extension),
                ComicsModel(character.comics.available,
                    if ( character.comics.items == null) null else RealmList<ItemModel>().also { realmList ->
                    character.comics.items.forEach {
                        realmList.add(
                            ItemModel(it.name)
                        )
                    }
                }),
                SeriesModel(
                    character.series.available,
                    if (character.series.items == null) null else RealmList<ItemModel>().also { realmList ->
                        character.series.items.forEach {
                            realmList.add(ItemModel(it.name))
                        }
                    }),
                StoriesModel(
                    character.stories.available,
                    if (character.stories.items == null) null else RealmList<ItemModel>().also { realmList ->
                        character.stories.items.forEach {
                            realmList.add(ItemModel(it.name))
                        }
                    }),
                EventsModel(
                    if (character.events.items == null) null else character.events.available,
                    RealmList<ItemModel>().also { realmList ->
                        character.events.items.forEach {
                            realmList.add(ItemModel(it.name))
                        }
                    })
            )
    }

    fun toCharacter(): Character {
        return Character(
            this.id!!,
            this.name!!,
            this.description!!,
            CharacterThumbnail(this.thumbnail!!.path!!, this.thumbnail!!.extension!!),
            this.comics!!.toItem(),
            this.series!!.toItem(),
            this.stories!!.toItem(),
            this.events!!.toItem(),
        )
    }
}

