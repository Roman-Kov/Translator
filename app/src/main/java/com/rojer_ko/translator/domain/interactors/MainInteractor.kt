package com.rojer_ko.translator.domain

import com.rojer_ko.translator.Contract
import com.rojer_ko.translator.data.model.DataModel
import com.rojer_ko.translator.data.model.SearchResult
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: Contract.Repository<List<SearchResult>>,
    private val localRepository: Contract.Repository<List<SearchResult>>):
    Contract.Interactor<DataModel>{
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<DataModel> {
        return if(fromRemoteSource){
            remoteRepository.getData(word).map { DataModel.Success(it)}
        } else{
            localRepository.getData(word).map { DataModel.Success(it)}
        }
    }
}