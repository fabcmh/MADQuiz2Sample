package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [MRT::class], version = 1)
abstract class MRTRoomDatabase : RoomDatabase() {
    abstract fun MRTDao(): MRTDao

    companion object {
        //Singleton Pattern
        @Volatile
        private var INSTANCE: MRTRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MRTRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MRTRoomDatabase::class.java,
                    "db"
                ).fallbackToDestructiveMigration().addCallback(MRTDatabaseCallback(scope)).build()

                INSTANCE = instance
                //return instance
                instance
            }
        }
    }

    private class MRTDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch { populateDatabase(database.MRTDao()) }
            }
        }

        suspend fun populateDatabase(MRTDao: MRTDao){
            MRTDao.deleteAll()
        }
    }
}