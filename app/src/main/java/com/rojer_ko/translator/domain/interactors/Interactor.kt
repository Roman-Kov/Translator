package com.rojer_ko.translator.domain.interactors

import io.reactivex.Observable

interface Interactor<T>{
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}