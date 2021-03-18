package ru.andreym.marveltask.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.andreym.marveltask.repository.database.DBHelper
import ru.andreym.marveltask.repository.database.MarvelDao

val databaseModule = Kodein.Module("DatabaseModule") {

    bind() from singleton {  DBHelper(instance()) }
    bind() from singleton {  MarvelDao(instance()) }
}