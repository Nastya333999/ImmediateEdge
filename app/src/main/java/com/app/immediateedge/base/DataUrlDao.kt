package com.app.immediateedge.base

import androidx.room.*
import com.app.immediateedge.base.DataUrl

@Dao
interface DataUrlDao {

    @Query("SELECT * FROM DataUrl ")
    fun getAll(): List<DataUrl>

    @Insert
    fun insert(data: DataUrl)

    @Update
    fun update(data: DataUrl)

    @Query("DELETE FROM DataUrl")
    fun deleteAll()
}