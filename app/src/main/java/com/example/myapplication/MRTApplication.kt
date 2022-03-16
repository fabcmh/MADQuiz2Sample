package com.example.myapplication

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MRTApplication:Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { MRTRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { MRTRepository(database.MRTDao()) }
}