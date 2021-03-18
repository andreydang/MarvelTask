package ru.andreym.marveltask.di


import androidx.lifecycle.ViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels

inline fun <reified VM: ViewModel, T> T.mainViewModel(): Lazy<VM> where T: KodeinAware, T: Fragment =
    viewModels(factoryProducer = { direct.instance()})

inline fun <reified VM : ViewModel, T> T.mainViewModel(): Lazy<VM> where T : KodeinAware, T : FragmentActivity =
    viewModels(factoryProducer = { direct.instance() })

inline fun <reified VM : ViewModel> Kodein.Builder.bindViewModel(overrides: Boolean? = null): Kodein.Builder.TypeBinder<VM> =
    bind<VM>(VM::class.java.simpleName, overrides)