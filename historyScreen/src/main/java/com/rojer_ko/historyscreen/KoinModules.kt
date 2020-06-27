package com.rojer_ko.historyscreen

import com.rojer_ko.translator.domain.interactors.HistoryInteractor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


fun injectDependencies() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    viewModel { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}