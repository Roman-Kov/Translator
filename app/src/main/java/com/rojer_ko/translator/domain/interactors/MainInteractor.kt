package com.rojer_ko.translator.domain.interactors

import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.data.repository.Repository
import com.rojer_ko.translator.di.NAME_LOCAL
import com.rojer_ko.translator.di.NAME_REMOTE
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val repositoryRemote: Repository<List<SearchResult>>,
    @Named(NAME_LOCAL) val repositoryLocal: Repository<List<SearchResult>>
) : Interactor<AppState>{
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if(fromRemoteSource){
            repositoryRemote.getData(word).map { AppState.Success(it)}
        } else{
            repositoryLocal.getData(word).map { AppState.Success(it)}
        }
    }
}