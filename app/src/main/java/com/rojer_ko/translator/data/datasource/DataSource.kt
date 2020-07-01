package com.rojer_ko.translator.data.datasource

interface DataSource<T>{
    suspend fun getData(word: String): T
}