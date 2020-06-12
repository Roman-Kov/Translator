package com.rojer_ko.translator.domain.interactors

import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.data.repository.Repository
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: Repository<List<SearchResult>>,
    private val localRepository: Repository<List<SearchResult>>):
    Interactor<AppState>{
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if(fromRemoteSource){
            remoteRepository.getData(word).map { AppState.Success(it)}
        } else{
            localRepository.getData(word).map { AppState.Success(it)}
        }
    }
}