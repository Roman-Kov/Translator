package com.rojer_ko.translator.data.datasource

import io.reactivex.Observable

interface DataSource<T>{
    fun getData(word: String): Observable<T>
}