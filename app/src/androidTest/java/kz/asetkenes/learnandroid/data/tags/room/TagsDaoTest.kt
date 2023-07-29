package kz.asetkenes.learnandroid.data.tags.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kz.asetkenes.learnandroid.data.articles.room.ArticlesDao
import kz.asetkenes.learnandroid.data.articles.room.entities.ArticleDbEntity
import kz.asetkenes.learnandroid.data.room.AppDatabase
import kz.asetkenes.learnandroid.data.tags.room.entities.ArticleTagDbEntity
import kz.asetkenes.learnandroid.data.tags.room.entities.TagDbEntity
import kz.asetkenes.learnandroid.data.users.room.UsersDao
import kz.asetkenes.learnandroid.data.users.room.entities.UserDbEntity
import kz.asetkenes.learnandroid.utils.convertTimestampToString
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class TagsDaoTest {

    private lateinit var database: AppDatabase

    private lateinit var tagsDao: TagsDao
    private lateinit var usersDao: UsersDao
    private lateinit var articlesDao: ArticlesDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .addCallback(TestDataCallback())
            .build()
        tagsDao = database.getTagsDao()
        usersDao = database.getUsersDao()
        articlesDao = database.getArticlesDao()
    }

    @After
    fun clearDatabase() {
        database.close()
    }

    @Test
    fun getAllTagsReturnAllTags() = runTest {
        val expected = getTags()

        val actual = tagsDao.getAllTags()

        assertEquals(expected.size, actual.size)
        assertTrue(expected.containsAll(actual))
    }

    private fun getTags(): List<TagDbEntity> = listOf (
        TagDbEntity(1 ,"android" ),
        TagDbEntity(2 ,"web" ),
        TagDbEntity(3 ,"Java" ),
        TagDbEntity(4 ,"Kotlin" ),
        TagDbEntity(5 ,"HTML" ),
        TagDbEntity(6 ,"CSS" ),
        TagDbEntity(7 ,"JavaScript" ),
        TagDbEntity(8 ,"PHP" ),
        TagDbEntity(9 ,"C#" ),
        TagDbEntity(10 ,"desktop" ),
        TagDbEntity(11 ,"HTTP" ),
        TagDbEntity(12 ,"C++" )
    )

//    private fun getUsers(): List<UserDbEntity> {
//        return listOf(
//            UserDbEntity(
//                1,
//                "Aset",
//                "aset@google.com",
//                "password",
//                "android developer",
//                0,
//                convertTimestampToString(0)
//            ),
//            UserDbEntity(
//                2,
//                "Erasyl",
//                "erasyl@google.com",
//                "password",
//                "react/doc.net developer",
//                0,
//                convertTimestampToString(0)
//            ),
//            UserDbEntity(
//                3,
//                "Rasul",
//                "rasul@google.com",
//                "password",
//                "java spring boot developer",
//                0,
//                convertTimestampToString(0)
//            ),
//            UserDbEntity(
//                4,
//                "Bekzat",
//                "beka@google.com",
//                "password",
//                "??? developer",
//                0,
//                convertTimestampToString(0)
//            ),
//            UserDbEntity(
//                5,
//                "Ali",
//                "ali@google.com",
//                "password",
//                "flutter developer",
//                0,
//                convertTimestampToString(0)
//            )
//        )
//    }
//
//    private fun getArticles(): List<ArticleDbEntity> {
//        return listOf(
//            ArticleDbEntity(1, "Room", 1, "Learn Room part 1", "Learn Room part 1", 0),
//            ArticleDbEntity(2, "Room", 1, "Learn Room part 2", "Learn Room part 2", 0),
//            ArticleDbEntity(3, "Room", 1, "Learn Room part 2", "Learn Room part 2", 0),
//            ArticleDbEntity(4, "Kotlin", 1, "Learn Kotlin part 1", "Learn Kotlin part 1", 0),
//            ArticleDbEntity(5, "Kotlin", 1, "Learn Kotlin part 2", "Learn Kotlin part 2", 0),
//            ArticleDbEntity(6, "Kotlin", 1, "Learn Kotlin part 2", "Learn Kotlin part 2", 0),
//            ArticleDbEntity(7, "Java", 1, "Learn Java part 1", "Learn Java part 1", 0),
//            ArticleDbEntity(8, "Java", 1, "Learn Java part 2", "Learn Java part 2", 0),
//            ArticleDbEntity(9, "Java", 1, "Learn Java part 2", "Learn Java part 2", 0)
//        )
//    }
//
//    private fun getArticleTags(): List<ArticleTagDbEntity> {
//        return listOf(
//            ArticleTagDbEntity(1, 1),
//            ArticleTagDbEntity(2, 1),
//            ArticleTagDbEntity(3, 1),
//            ArticleTagDbEntity(4, 4),
//            ArticleTagDbEntity(5, 4),
//            ArticleTagDbEntity(5, 3),
//            ArticleTagDbEntity(6, 1),
//            ArticleTagDbEntity(6, 4),
//            ArticleTagDbEntity(7, 3),
//            ArticleTagDbEntity(8, 3),
//            ArticleTagDbEntity(8, 1),
//            ArticleTagDbEntity(9, 4),
//            ArticleTagDbEntity(9, 3)
//        )
//    }
}

