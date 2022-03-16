package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MRTDao {
    @Insert
    fun addMRT(mrt: MRT)

    @Query("SELECT * FROM MRTList")
    fun getAllData():LiveData<List<MRT>>

    @Query("DELETE FROM MRTList")
    fun deleteAll()
}