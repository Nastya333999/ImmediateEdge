package com.app.immediateedge.base

import android.app.Application
import androidx.room.Room

class App : Application() {

    lateinit var dataBase : AppDatabase

    override fun onCreate() {
        super.onCreate()
        dataBase = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "DataUrl"
        ).build()



    }

}