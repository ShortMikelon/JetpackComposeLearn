package kz.asetkenes.learnandroid.data.users.room

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kz.asetkenes.learnandroid.data.settings.AppSettings
import kz.asetkenes.learnandroid.data.users.room.entities.UserDbEntity
import kz.asetkenes.learnandroid.domain.users.entities.User
import kz.asetkenes.learnandroid.domain.users.entities.UserSignUpData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

private const val PASSWORD = "password"

@OptIn(ExperimentalCoroutinesApi::class)
class RoomUsersRepositoryTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var usersDao: UsersDao

    @MockK
    lateinit var appSettings: AppSettings


    @Test
    fun createUserCallDao() = runTest {
        val source = createRepository()
        coEvery { usersDao.createUser(any()) } just runs
        val signUpData = UserSignUpData(
            name = "name",
            email = "email",
            password = "password",
            aboutMe = "aboutMe",
            dateOfBirthday = 1L,
        )

        source.signUp(signUpData)

        coVerify(exactly = 1) {
            usersDao.createUser(UserDbEntity.fromUserSignUpData(signUpData))
        }
    }

    @Test
    fun findUserByIdReturnUser() = runTest {
        val repository = createRepository()
        val userDbEntity = createUserDbEntity()
        coEvery { usersDao.findById(ofType(Long::class)) } returns userDbEntity

        val foundUser = repository.findUserById(123)

        coVerify(exactly = 1) { usersDao.findById(123) }
        assertEquals(userDbEntity, UserDbEntity.fromUser(foundUser!!, PASSWORD))
    }

    @Test
    fun findUserByIdUserNotFoundReturnNull() = runTest {
        val repository = createRepository()
        coEvery { usersDao.findById(ofType(Long::class)) } returns null

        val foundUser = repository.findUserById(123)

        coVerify(exactly = 1) { usersDao.findById(123) }
        assertNull(foundUser)
    }

    @Test
    fun getAllUserReturnUsersList() = runTest {
        val repository = createRepository()
        val userDbEntities = listOf(
            createUserDbEntity(1),
            createUserDbEntity(2),
            createUserDbEntity(3),
        )
        coEvery { usersDao.getAllUsers() } returns userDbEntities

        val users = repository.getAllUsers()

        val users2 = users.map { user -> UserDbEntity.fromUser(user, PASSWORD) }
        assertEquals(userDbEntities, users2)
        coVerify(exactly = 1) { usersDao.getAllUsers() }
    }

    private fun createUserEntity(id: Long = 1L): User = User(
        id = id,
        name = "name",
        email = "email $id",
        aboutMe = "aboutMe",
        dateBirthday = 1L,
        createdAt = 1L
    )

    private fun createUserDbEntity(id: Long = 1L): UserDbEntity =
        UserDbEntity(
            id = id,
            name = "name",
            email = "email $id",
            password = PASSWORD,
            aboutMe = "aboutMe",
            dateBirthday = 1L,
            createdAt = "2020-01-01 11:11:11"
        )

    private fun createRepository(): RoomUsersRepository =
        RoomUsersRepository(
            usersDao,
            appSettings,
            UnconfinedTestDispatcher()
        )
}