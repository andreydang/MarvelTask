package ru.andreym.marveltask.di

import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ru.andreym.marveltask.viewmodel.MainViewModel

val viewModelModule = Kodein.Module("ViewModelModule") {

    bind<ViewModelProvider.Factory>() with singleton {
        ViewModelFactory(kodein.direct)
    }
    bindViewModel<MainViewModel>() with provider {
        MainViewModel(instance())
    }
}