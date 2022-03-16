package com.example.myapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.UserPreferencesRepository.PreferenceKeys.CODEKEY
import com.example.myapplication.UserPreferencesRepository.PreferenceKeys.LINEKEY
import com.example.myapplication.UserPreferencesRepository.PreferenceKeys.NAMEKEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val context: Context
) {

    // TODO Create the Preferences DataStore
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)

    // TODO Create a private object for defining PreferencesKeys
    private object PreferenceKeys {
        val CODEKEY = stringPreferencesKey("latestMRTCode")
        val NAMEKEY = stringPreferencesKey("latestMRTName")
        val LINEKEY = intPreferencesKey("latestMRTLine")
    }

    // TODO Add code to get the user preferences flow
    //READ DATA
    val userPreferencesFlowCode: Flow<String> = context.dataStore.data.map { preferences ->
        val latestMRT = preferences[CODEKEY] ?: ""
        latestMRT
    }
    val userPreferencesFlowName: Flow<String> = context.dataStore.data.map { preferences ->
        val latestMRT = preferences[NAMEKEY] ?: ""
        latestMRT
    }
    val userPreferencesFlowLine: Flow<Int> = context.dataStore.data.map { preferences ->
        val latestMRT = preferences[LINEKEY] ?: 0
        latestMRT
    }

    //UPDATE DATA
    suspend fun update(latestMRTCode: String,latestMRTName: String,latestMRTLine:Int) {
        context.dataStore.edit { preferences ->
            preferences[CODEKEY] = latestMRTCode
            preferences[NAMEKEY] = latestMRTName
            preferences[LINEKEY] = latestMRTLine
        }
    }

    companion object {
        // Constant for naming our DataStore - you can change this if you want
        private const val USER_PREFERENCES_NAME = "user_preferences"

        // The usual for debugging
        private val TAG: String = "UserPreferencesRepository"

        // Boilerplate-y code for singleton: the private reference to this self
        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        /**
         * Boilerplate-y code for singleton: to ensure only a single copy is ever present
         * @param context to init the datastore
         */
        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}