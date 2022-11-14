package com.app.immediateedge.base

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [DataUrl::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataUrlDao(): DataUrlDao
}