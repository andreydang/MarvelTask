package ru.andreym.marveltask.repository.database.entity

import io.realm.RealmList
import io.realm.RealmObject
import ru.andreym.marveltask.repository.network.response.*

open class CharacterThumbnailModel(
    var path: String? = "",
    var extension: String? = ""
) : RealmObject()

open class ComicsModel(
    var available: Int? = 0,
    var Items: RealmList<ItemModel>?= RealmList(ItemModel(""))
): RealmObject(){

    fun toItem(): Comics {
        return Comics(this.available!!,this.Items!!.map { it.toItem() })
    }

}

open class EventsModel(
    var available: Int? = 0,
    var items: RealmList<ItemModel>?= RealmList(ItemModel(""))
): RealmObject(){

    fun toItem(): Events {
        return Events(this.available!!,this.items!!.map { it.toItem() })
    }

}

open class SeriesModel(
    var available: Int? = 0,
    var items: RealmList<ItemModel>?= RealmList(ItemModel(""))
): RealmObject(){

    fun toItem(): Series {
        return Series(this.available!!,this.items!!.map { it.toItem() })
    }

}

open class StoriesModel(
    var available: Int? = 0,
    var items: RealmList<ItemModel>?= RealmList(ItemModel(""))
): RealmObject(){

    fun toItem(): Stories {
        return Stories(this.available!!,this.items!!.map { it.toItem() })
    }

}

open class ItemModel(
    var name: String?=""
): RealmObject(){

    fun toItem(): Item {
        return  Item(this.name!!)
    }

}