package com.rojer_ko.translator

import com.rojer_ko.translator.data.model.DataModel
import io.reactivex.Observable

class Contract {

    interface View {
        fun renderData(dataModel: DataModel)
    }

    interface Presenter<T: DataModel, V: View> {
        fun attachView(view: V)
        fun detachView(view: V)
        fun getData(word: String, isOnline: Boolean)
    }

    interface Interactor<T>{
        fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
    }

    interface Repository<T>{
        fun getData(word: String): Observable<T>
    }

    interface DataSource<T>{
        fun getData(word: String): Observable<T>
    }

}