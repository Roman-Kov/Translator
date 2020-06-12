package com.rojer_ko.translator.data.repository

import io.reactivex.Observable

interface Repository<T>{
    fun getData(word: String): Observable<T>
}