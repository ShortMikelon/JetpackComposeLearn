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
import kz.asetkenes.learnandroid.domain.signup.entities.SignUpEntity
import kz.asetkenes.learnandroid.domain.signup.exceptions.EmptyFieldException
import kz.asetkenes.learnandroid.domain.signup.exceptions.PasswordMismatchException
import kz.asetkenes.learnandroid.testutils.wellDone
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
    fun signUpValidDataCreateUser() = runTest {
        val source = createRepository()
        coEvery { usersDao.createUser(any()) } just runs
        val signUpData = SignUpEntity(
            name = "name",
            email = "email",
            password = "password",
            repeatPassword = "password",
            aboutMe = "aboutMe",
            dateOfBirthday = 1L,
        )

        source.signUp(signUpData)

        coVerify(exactly = 1) {
            usersDao.createUser(UserDbEntity.fromUserSignUpData(signUpData))
        }
    }

    @Test(expected = EmptyFieldException::class)
    fun signUpEmptyFieldInSignUpDataThrowEmptyFieldException() = runTest {
        val source = createRepository()
        val signUpData = SignUpEntity(
            name = "",
            email = "",
            password = "password",
            repeatPassword = "password",
            aboutMe = "aboutMe",
            dateOfBirthday = 1L,
        )

        source.signUp(signUpData)

        wellDone()
    }

    @Test(expected = PasswordMismatchException::class)
    fun signUpMismatchPasswordInSignUpDataThrowEmptyFieldException() = runTest {
        val source = createRepository()
        val signUpData = SignUpEntity(
            name = "name",
            email = "email",
            password = "password",
            repeatPassword = "pasdsfsword",
            aboutMe = "aboutMe",
            dateOfBirthday = 1L,
        )

        source.signUp(signUpData)

        wellDone()
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