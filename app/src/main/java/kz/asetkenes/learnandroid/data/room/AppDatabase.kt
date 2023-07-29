package kz.asetkenes.learnandroid.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.asetkenes.learnandroid.data.articles.room.entities.ArticleDbEntity
import kz.asetkenes.learnandroid.data.articles.room.ArticlesDao
import kz.asetkenes.learnandroid.data.tags.room.TagsDao
import kz.asetkenes.learnandroid.data.tags.room.entities.ArticleTagDbEntity
import kz.asetkenes.learnandroid.data.tags.room.entities.TagDbEntity
import kz.asetkenes.learnandroid.data.users.room.UsersDao
import kz.asetkenes.learnandroid.data.users.room.entities.UserDbEntity

@Database(
    version = 2,
    entities = [
        UserDbEntity::class,
        ArticleDbEntity::class,
        TagDbEntity::class,
        ArticleTagDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UsersDao

    abstract fun getArticlesDao(): ArticlesDao

    abstract fun getTagsDao(): TagsDao

}