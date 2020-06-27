package com.rojer_ko.translator.domain.interactors

import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.data.repository.Repository
import com.rojer_ko.translator.data.repository.RepositoryLocal

class HistoryInteractor(private val repositoryRemote: Repository<List<SearchResult>>,
                        private val repositoryLocal: RepositoryLocal<List<SearchResult>>
) : Interactor<AppState> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource){repositoryRemote}
            else {repositoryLocal
            }.getData(word))
    }
}