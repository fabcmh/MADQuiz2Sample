package com.example.myapplication

import androidx.lifecycle.LiveData

class MRTRepository (private val MRTDao:MRTDao){
    val getAllData: LiveData<List<MRT>> = MRTDao.getAllData()

    fun addMRT(mrt:MRT){
        MRTDao.addMRT(mrt)
    }

    fun deleteAll(){
        MRTDao.deleteAll()
    }

}