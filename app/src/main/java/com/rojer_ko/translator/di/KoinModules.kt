package com.rojer_ko.translator.di

import androidx.room.Room
import com.rojer_ko.translator.data.datasource.retrofit.RetrofitImpl
import com.rojer_ko.translator.data.datasource.room.HistoryDataBase
import com.rojer_ko.translator.data.datasource.room.RoomDataBaseImpl
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.data.repository.Repository
import com.rojer_ko.translator.data.repository.RepositoryImpl
import com.rojer_ko.translator.data.repository.RepositoryImplLocal
import com.rojer_ko.translator.data.repository.RepositoryLocal
import com.rojer_ko.translator.domain.interactors.HistoryInteractor
import com.rojer_ko.translator.domain.interactors.MainInteractor
import com.rojer_ko.translator.presentation.history.HistoryViewModel
import com.rojer_ko.translator.presentation.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    single {Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build()}
    single {get<HistoryDataBase>().historyDao()}
    single<Repository<List<SearchResult>>>(){RepositoryImpl(RetrofitImpl())}
    single<RepositoryLocal<List<SearchResult>>>() {
        RepositoryImplLocal(RoomDataBaseImpl(get()))
    }
}

val mainScreen = module {
    factory {MainInteractor(get(), get())}
    viewModel {MainViewModel(get())}
}

val historyScreen = module {
    factory {HistoryInteractor(get(), get())}
    viewModel {HistoryViewModel(get()) }
}