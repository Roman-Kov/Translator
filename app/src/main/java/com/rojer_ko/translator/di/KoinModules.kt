package com.rojer_ko.translator.di

import com.rojer_ko.translator.data.datasource.retrofit.RetrofitImpl
import com.rojer_ko.translator.data.datasource.room.RoomDataBaseImpl
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.data.repository.Repository
import com.rojer_ko.translator.data.repository.RepositoryImpl
import com.rojer_ko.translator.domain.interactors.MainInteractor
import com.rojer_ko.translator.presentation.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<SearchResult>>>(named(NAME_REMOTE)){
        RepositoryImpl(RetrofitImpl())
    }

    single<Repository<List<SearchResult>>>(named(NAME_LOCAL)) {
        RepositoryImpl(RoomDataBaseImpl())
    }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL)))}
    viewModel { MainViewModel(get())}
}