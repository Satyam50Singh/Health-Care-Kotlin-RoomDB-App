package satya.app.healthcareapproomdb.utils

import android.content.Context
import android.content.SharedPreferences
import satya.app.healthcareapproomdb.utils.Constants.PREF_EMAIL
import satya.app.healthcareapproomdb.utils.Constants.PREF_PASSWORD
import satya.app.healthcareapproomdb.utils.Constants.PREF_REMEMBER_ME
import satya.app.healthcareapproomdb.utils.Constants.PREF_TAG
import satya.app.healthcareapproomdb.utils.Constants.PREF_USERNAME

object PreferenceManager {

    lateinit var sharedPreferenceManager: SharedPreferences

    fun setSharedPreferences(context: Context, key: String, value: String) {
        sharedPreferenceManager = context.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE)
        sharedPreferenceManager.edit().putString(key, value).apply()
    }

    fun setSharedPreferences(context: Context, key: String, value: Boolean) {
        sharedPreferenceManager = context.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE)
        sharedPreferenceManager.edit().putBoolean(key, value).apply()
    }

    fun setSharedPreferences(context: Context, key: String, value: Int) {
        sharedPreferenceManager = context.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE)
        sharedPreferenceManager.edit().putInt(key, value).apply()
    }

    fun setSharedPreferences(context: Context, key: String, value: Float) {
        sharedPreferenceManager = context.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE)
        sharedPreferenceManager.edit().putFloat(key, value).apply()
    }

    fun getSharedPreferences(context: Context, key: String): String? {
        sharedPreferenceManager = context.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE)
        return sharedPreferenceManager.getString(key, "")
    }

    fun getSharedPreferencesIntValues(context: Context, key: String): Int {
        sharedPreferenceManager = context.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE)
        return sharedPreferenceManager.getInt(key, 0)
    }

    fun getSharedPreferencesBooleanValue(context: Context, key: String): Boolean {
        sharedPreferenceManager = context.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE)
        return sharedPreferenceManager.getBoolean(key, false)
    }

    fun clearSharedPreference(context: Context, key: String) {
        sharedPreferenceManager =
            context.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE)
        sharedPreferenceManager.edit().remove(key).apply()
    }

    fun clearAllSharedPreferences(context: Context) {
        if (getSharedPreferencesBooleanValue(context, PREF_REMEMBER_ME)) {
            val allEntries: Map<String, *> = sharedPreferenceManager.all
            for (entry: Map.Entry<String, *> in allEntries) {
                if (!entry.key.equals(PREF_REMEMBER_ME, ignoreCase = true)
                    && !entry.key.equals(PREF_EMAIL, ignoreCase = true)
                    && !entry.key.equals(PREF_PASSWORD, ignoreCase = true)
                    && !entry.key.equals(PREF_USERNAME, ignoreCase = true)
                ) {
                    sharedPreferenceManager =
                        context.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE)
                    sharedPreferenceManager.edit().remove(entry.key).apply()
                }
            }
        } else {
            sharedPreferenceManager =
                context.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE)
            sharedPreferenceManager.edit().clear().apply()
        }
    }
}