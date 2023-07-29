package kz.asetkenes.learnandroid.data.settings.sharedpreferences

import android.content.Context
import kz.asetkenes.learnandroid.data.settings.AppSettings

class SharedPreferencesAppSettings(
    applicationContext: Context
) : AppSettings {

    private val sharedPreferences = applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun setCurrentUserId(id: Long) {
        sharedPreferences.edit()
            .putLong(CURRENT_ID, id)
            .apply()
    }

    override fun getCurrentUserId(): Long = sharedPreferences.getLong(CURRENT_ID, AppSettings.NO_USER_ID)

    companion object {
        const val CURRENT_ID = "id"
    }

}

