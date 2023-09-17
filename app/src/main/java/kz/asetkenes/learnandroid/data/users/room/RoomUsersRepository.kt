package kz.asetkenes.learnandroid.data.users.room

import android.database.sqlite.SQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.asetkenes.learnandroid.common.core.HashCoder
import kz.asetkenes.learnandroid.data.room.StorageException
import kz.asetkenes.learnandroid.data.settings.AppSettings
import kz.asetkenes.learnandroid.data.users.room.entities.UserDbEntity
import kz.asetkenes.learnandroid.domain.users.UsersRepository
import kz.asetkenes.learnandroid.domain.users.entities.User
import kz.asetkenes.learnandroid.domain.signin.entities.UserSignInData
import kz.asetkenes.learnandroid.domain.signup.entities.SignUpEntity
import kz.asetkenes.learnandroid.domain.signin.entities.isValide
import kz.asetkenes.learnandroid.domain.signup.entities.validate
import kz.asetkenes.learnandroid.domain.users.exceptions.AuthException

class RoomUsersRepository(
    private val usersDao: UsersDao,
    private val appSettings: AppSettings,
    private val ioDispatcher: CoroutineDispatcher,
    private val hashCoder: HashCoder
) : UsersRepository {

    override fun isSignIn(): Boolean =
        appSettings.getCurrentUserId() != AppSettings.NO_USER_ID

    override suspend fun signIn(signInData: UserSignInData) {
        val signInDataWithHashCode = signInData.copy(
            password = hashCoder.md5(signInData.password)
        )
        signInData.isValide()

        val user = usersDao.findByEmail(signInDataWithHashCode.email) ?: throw AuthException()
        if (user.password != signInDataWithHashCode.password) throw AuthException()

        appSettings.setCurrentUserId(user.id)
    }

    override suspend fun signUp(signUpData: SignUpEntity) = withContext(ioDispatcher) {
        try {
            val signUpDataWithHashPassword =
                signUpData.copy(
                    password = hashCoder.md5(signUpData.password),
                    repeatPassword = hashCoder.md5(signUpData.repeatPassword)
                )
            signUpDataWithHashPassword.validate()
            val dbEntity = UserDbEntity.fromUserSignUpData(signUpDataWithHashPassword)
            usersDao.createUser(dbEntity)
        } catch (ex: SQLiteException) {
            throw StorageException(ex)
        }
    }

    override suspend fun findUserById(id: Long): User? = withContext(ioDispatcher) {
        usersDao.findById(id)?.toUser()
    }

    override suspend fun getAllUsers(): List<User> = withContext(ioDispatcher) {
        usersDao.getAllUsers().map { it.toUser() }
    }

}