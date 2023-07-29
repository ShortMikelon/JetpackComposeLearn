package kz.asetkenes.learnandroid.data.articles.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kz.asetkenes.learnandroid.data.articles.room.entities.ArticleDbEntity
import kz.asetkenes.learnandroid.data.room.AppDatabase
import kz.asetkenes.learnandroid.data.users.room.UsersDao
import kz.asetkenes.learnandroid.data.users.room.entities.UserDbEntity
import kz.asetkenes.learnandroid.utils.convertTimestampToString
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ArticlesDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var articlesDao: ArticlesDao
    private lateinit var usersDao: UsersDao

    @Before
    fun setupDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        articlesDao = database.getArticlesDao()
        usersDao = database.getUsersDao()
    }

    @After
    fun clearDatabase() {
        database.close()
    }

    @Test
    fun getAllArticlesEmptyDbReturnEmptyList() = runTest {
        val articlesFromDb = articlesDao.getAllArticles()

        assertTrue(articlesFromDb.isEmpty())
    }

    @Test
    fun getAllArticlesFilledDbReturnArticlesList() = runTest {
        usersDao.createUser(createUser(1))
        val articles = listOf(
            createArticle(1, 1),
            createArticle(2, 1),
            createArticle(3, 1),
        )
        articles.forEach { article -> articlesDao.createArticle(article) }

        val articlesFromDb = articlesDao.getAllArticles()

        assertEquals(articles, articlesFromDb)
    }

    @Test
    fun getAllArticlesByAuthorIdHaveArticlesByAuthorReturnArticles() = runTest {
        usersDao.createUser(createUser(1))
        usersDao.createUser(createUser(2))
        val articles = listOf(
            createArticle(1, 1),
            createArticle(2, 2),
            createArticle(3, 1),
            createArticle(4, 2),
            createArticle(3, 2)
        )
        articles.forEach { article -> articlesDao.createArticle(article) }

        val articlesByAuthor = articlesDao.getAllArticlesByAuthorId(2)

        val actual = articlesByAuthor.filter { it.authorId == 2L }
        assertEquals(articlesByAuthor, actual)
    }

    @Test
    fun getAllArticlesByAuthorIdNoArticlesByAuthorReturnEmptyList() = runTest {
        usersDao.createUser(createUser(1))
        usersDao.createUser(createUser(2))
        val articles = listOf(
            createArticle(1, 1),
            createArticle(2, 2),
            createArticle(3, 1),
            createArticle(4, 2),
            createArticle(5, 2)
        )
        articles.forEach { article -> articlesDao.createArticle(article) }

        val articlesByAuthor = articlesDao.getAllArticlesByAuthorId(3)

        assertTrue(articlesByAuthor.isEmpty())
    }

    @Test
    fun getArticleByIdHaveArticleReturnArticles() = runTest {
        usersDao.createUser(createUser(1))
        usersDao.createUser(createUser(2))
        val expectedArticle = createArticle(2, 2)
        val articles = listOf(
            createArticle(1, 1),
            expectedArticle,
            createArticle(3, 1),
            createArticle(4, 2),
            createArticle(3, 2)
        )
        articles.forEach { article -> articlesDao.createArticle(article) }

        val articleById = articlesDao.getArticleById(2)

        assertEquals(expectedArticle, articleById)
    }

    @Test
    fun getArticleByIdNoArticlesReturnNull() = runTest {
        usersDao.createUser(createUser(1))
        usersDao.createUser(createUser(2))
        val articles = listOf(
            createArticle(1, 1),
            createArticle(2, 2),
            createArticle(3, 1),
            createArticle(4, 2),
            createArticle(5, 2)
        )
        articles.forEach { article -> articlesDao.createArticle(article) }

        val articleById = articlesDao.getArticleById(-321)

        assertNull(articleById)
    }

    private fun createUser(id: Long = 1L): UserDbEntity = UserDbEntity(
        id = id,
        name = "name",
        email = "email $id",
        password = "password",
        aboutMe = "about me",
        dateBirthday = 0L,
        createdAt = convertTimestampToString(0L)
    )

    private fun createArticle(id: Long = 1L, authorId: Long = 1L): ArticleDbEntity =
        ArticleDbEntity(
            id,
            name = "name",
            authorId = authorId,
            description = "description",
            body = "body",
            createdAt = 0L
        )
}