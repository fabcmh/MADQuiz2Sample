package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MRTList")
data class MRT(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID")
    val id:Int,
    @ColumnInfo(name = "StationCode")
    val stationCode: String,
    @ColumnInfo(name = "StationName")
    val stationName: String,
    @ColumnInfo(name = "LineName")
    val LineName: String

)