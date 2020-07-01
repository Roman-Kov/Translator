package com.rojer_ko.translator.domain.interactors

interface Interactor<T>{
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}