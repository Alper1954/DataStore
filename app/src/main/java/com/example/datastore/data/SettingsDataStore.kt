package com.example.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class SettingsDataStore(preference_datastore: DataStore<Preferences>) {

    private val OPTION1_CHECKED = booleanPreferencesKey("option1Checked")
    private val OPTION2_CHECKED = booleanPreferencesKey("option2Checked")

    suspend fun saveOption1ToPreferencesStore(option1Checked: Boolean, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[OPTION1_CHECKED] = option1Checked
        }
    }
    suspend fun saveOption2ToPreferencesStore(option2Checked: Boolean, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[OPTION2_CHECKED] = option2Checked
        }
    }

    val option1Flow: Flow<Boolean> = preference_datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[OPTION1_CHECKED] ?: false }

    val option2Flow: Flow<Boolean> = preference_datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[OPTION2_CHECKED] ?: false }

}