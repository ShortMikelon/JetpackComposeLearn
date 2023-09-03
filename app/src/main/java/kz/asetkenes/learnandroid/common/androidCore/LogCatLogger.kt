package kz.asetkenes.learnandroid.common.androidCore

import android.util.Log
import kz.asetkenes.learnandroid.common.core.Logger

class LogCatLogger : Logger {

    override fun debug(message: String) {
        Log.d(TAG, message)
    }

    override fun error(exception: Exception) {
        Log.e(TAG, exception.message, exception)
    }

    companion object {
        private const val TAG = "MyApplication2 TAG"
    }
}