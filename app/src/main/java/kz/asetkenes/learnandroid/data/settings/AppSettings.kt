package kz.asetkenes.learnandroid.data.settings

interface AppSettings {

    fun setCurrentUserId(id: Long)

    fun getCurrentUserId(): Long

    companion object {
        const val NO_USER_ID = -1L
    }
}