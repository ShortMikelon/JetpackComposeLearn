package kz.asetkenes.learnandroid.domain.users

import kz.asetkenes.learnandroid.domain.users.entities.User
import kz.asetkenes.learnandroid.domain.signin.entities.UserSignInData
import kz.asetkenes.learnandroid.domain.users.entities.UserSignUpData

interface UsersRepository {

    suspend fun signUp(signUpData: UserSignUpData)

    suspend fun findUserById(id: Long): User?

    suspend fun getAllUsers(): List<User>

    fun isSignIn(): Boolean

    suspend fun signIn(signInData: UserSignInData)

}