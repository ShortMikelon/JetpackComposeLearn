package kz.asetkenes.learnandroid.data.users.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kz.asetkenes.learnandroid.data.room.AppDatabase
import kz.asetkenes.learnandroid.data.users.room.entities.UserDbEntity
import kz.asetkenes.learnandroid.utils.convertTimestampToString
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersDaoTest {

    private lateinit var database: AppDatabase

    private lateinit var usersDao: UsersDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        usersDao = database.getUsersDao()
    }

    @After
    fun clear() {
        database.close()
    }

    @Test
    fun createUserAddUserToDb() = runTest {
        val user = createUser()

        usersDao.createUser(user)
        val userFromDb = usersDao.findById(1)

        assertEquals(user, userFromDb)
    }

    @Test
    fun findByIdUserNotExistReturnNull() = runTest {
        val user = usersDao.findById(-123)

        assertEquals(null, user)
    }

    @Test
    fun getAllUserFilledDbReturnUsersList() = runTest {
        val users1 = listOf(
            createUser(1),
            createUser(2),
            createUser(3)
        )
        users1.forEach { usersDao.createUser(it) }

        val users2 = usersDao.getAllUsers()

        assertEquals(users1, users2)
    }

    @Test
    fun getAllUserEmptyDbReturnEmptyList() = runTest {
        val users = usersDao.getAllUsers()

        assertEquals(0, users.size)
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

}