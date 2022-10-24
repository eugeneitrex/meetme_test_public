package by.test.meetmetest.utils

import android.content.Context
import android.content.SharedPreferences
import by.test.meetmetest.data.entities.Game
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesUtils(context: Context) {
    private var preferences: SharedPreferences? = null

    init {
        preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    companion object {
        private const val PREFERENCES = "meet_me_test_app"
        private const val GAMES_STRING_MODEL = "games_string_model"
        private const val ALREADY_LOADED = "already_loaded"
    }

    /**
     * Save list of Games
     */
    fun saveListOfGames(list: ArrayList<Game>?) {
        val modelString = Gson().toJson(list)
        setToPreferences(GAMES_STRING_MODEL, modelString)
    }

    /**
     * Restore list of Games
     */
    fun restoreListOfGames(): List<Game> {
        val typeToken = object : TypeToken<List<Game>>() {}.type
        return Gson().fromJson(getFromPreferences(GAMES_STRING_MODEL, ""), typeToken)
    }

    /**
     * @return true if user data has been loaded from backend
     */
    fun restoreAlreadyLoadedFromBacked(): Boolean {
        return getFromPreferences(ALREADY_LOADED, false)
    }

    /**
     * Save 'true' if dat has been loaded from backend
     *
     * @param alreadyLoaded if data has been loaded
     */
    fun saveAlreadyLoadedFromBackend(alreadyLoaded: Boolean) {
        setToPreferences(ALREADY_LOADED, alreadyLoaded)
    }

    private fun setToPreferences(key: String, value: String) {
        val editor = preferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    private fun getFromPreferences(key: String, defValue: String): String {
        return preferences?.getString(key, defValue) ?: return defValue
    }

    private fun setToPreferences(key: String, value: Boolean) {
        val editor = preferences?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    private fun getFromPreferences(key: String, defValue: Boolean): Boolean {
        return preferences?.getBoolean(key, defValue) ?: return defValue
    }
}
