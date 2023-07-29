package kz.asetkenes.learnandroid

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kz.asetkenes.learnandroid.data.articles.room.RoomArticlesRepository
import kz.asetkenes.learnandroid.data.room.AppDatabase
import kz.asetkenes.learnandroid.data.settings.AppSettings
import kz.asetkenes.learnandroid.data.settings.sharedpreferences.SharedPreferencesAppSettings
import kz.asetkenes.learnandroid.data.tags.room.RoomTagsRepository
import kz.asetkenes.learnandroid.data.users.room.RoomUsersRepository
import kz.asetkenes.learnandroid.domain.articles.ArticlesRepository
import kz.asetkenes.learnandroid.domain.tags.TagsRepository
import kz.asetkenes.learnandroid.domain.users.UsersRepository

object Repositories {

    private lateinit var applicationContext: Context

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .createFromAsset("initial_db.db")
            .build()
    }

    private val ioDispatcher = Dispatchers.IO

    val usersRepository by lazy<UsersRepository> {
        RoomUsersRepository(
            database.getUsersDao(),
            appSettings,
            ioDispatcher
        )
    }

    val articlesRepository by lazy<ArticlesRepository> {
        RoomArticlesRepository(
            database.getArticlesDao(),
            ioDispatcher
        )
    }

    val tagsRepository by lazy<TagsRepository> {
        RoomTagsRepository(
            database.getTagsDao(),
            ioDispatcher
        )
    }

    val appSettings by lazy<AppSettings> {
        SharedPreferencesAppSettings(applicationContext)
    }

    fun init(context: Context) {
        this.applicationContext = context
    }
}