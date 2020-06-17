package com.rojer_ko.translator.data.repository

interface Repository<T>{

    suspend fun getData(word: String): T
}