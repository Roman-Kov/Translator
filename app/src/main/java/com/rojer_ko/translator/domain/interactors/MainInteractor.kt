package com.rojer_ko.translator.domain.interactors

import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.data.repository.Repository

class MainInteractor (
    private val repositoryRemote: Repository<List<SearchResult>>,
    private val repositoryLocal: Repository<List<SearchResult>>
) : Interactor<AppState>{

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if(fromRemoteSource){
            repositoryRemote.getData(word)
        } else{
            repositoryLocal.getData(word)
        })
    }
}