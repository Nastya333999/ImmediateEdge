package com.app.immediateedge.presenter

import android.app.Application
import android.util.Log
import com.app.immediateedge.base.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter(private val app: Application) {

    fun saveUrl(url: String) {
        Observable.fromRunnable<Unit> {
            val dao = (app as App).dataBase.dataUrlDao()

            val dataUrls = dao.getAll()
            if (dataUrls.isEmpty()) {
                dao.insert(DataUrl(0, url))
            } else {
                val data = dataUrls.first()
                val updatedData = data.copy(uri = url)
                dao.update(updatedData)
            }
        }.subscribeOn(Schedulers.io())
            .subscribe {}
    }

    companion object {
        private const val BASE_URL = "goldtree.store/"
        const val BASE_API_URL = BASE_URL + "immediateedge.php"
    }


}