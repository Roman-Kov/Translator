package com.rojer_ko.translator.di

import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.data.repository.Repository
import com.rojer_ko.translator.domain.interactors.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<SearchResult>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<SearchResult>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}