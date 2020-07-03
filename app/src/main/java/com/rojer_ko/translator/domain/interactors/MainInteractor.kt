package com.rojer_ko.translator.domain.interactors

import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.data.repository.Repository
import com.rojer_ko.translator.data.repository.RepositoryLocal

class MainInteractor (
    private val repositoryRemote: Repository<List<SearchResult>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResult>>
) : Interactor<AppState>{

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState

        if(fromRemoteSource){
            appState = AppState.Success(repositoryRemote.getData(word))
            repositoryLocal.saveToDB(appState)
        }
        else{
            appState = AppState.Success(repositoryLocal.getData(word))
        }
        return appState
    }
}