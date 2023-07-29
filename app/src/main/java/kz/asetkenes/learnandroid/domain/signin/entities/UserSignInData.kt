package kz.asetkenes.learnandroid.domain.signin.entities

import kz.asetkenes.learnandroid.domain.signin.exceptions.EmptyFieldException

data class UserSignInData(
    val email: String,
    val password: String
)

fun UserSignInData.isValide() {
    if (this.email.isEmpty()) throw EmptyFieldException(SignInField.EMAIL)
    if (this.password.isEmpty()) throw EmptyFieldException(SignInField.PASSWORD)
}

