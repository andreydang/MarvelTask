package ru.andreym.marveltask

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.andreym.marveltask.di.*
import ru.andreym.marveltask.repository.Repository
import timber.log.Timber

class App : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@App))
        import(networkModule)
        import(viewModelModule)
        import(databaseModule)
        bind() from singleton { Repository(instance(),instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}