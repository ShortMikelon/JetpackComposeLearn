package kz.asetkenes.learnandroid.domain.signup.entities

import kz.asetkenes.learnandroid.domain.signup.exceptions.DateOfBirthdayNotSelectedException
import kz.asetkenes.learnandroid.domain.signup.exceptions.EmptyFieldException
import kz.asetkenes.learnandroid.domain.signup.exceptions.PasswordMismatchException

const val DATE_BIRTHDAY_NOT_SELECTED = -1L

data class SignUpEntity(
    val name: String,
    val email: String,
    val password: String,
    val repeatPassword: String,
    val aboutMe: String,
    val dateOfBirthday: Long
)

fun SignUpEntity.validate() {
    if (name.isEmpty()) throw EmptyFieldException(SignUpField.NAME)
    if (email.isEmpty()) throw EmptyFieldException(SignUpField.EMAIL)
    if (password.isEmpty()) throw EmptyFieldException(SignUpField.PASSWORD)
    if (password != repeatPassword) throw PasswordMismatchException()
    if (dateOfBirthday == DATE_BIRTHDAY_NOT_SELECTED) throw DateOfBirthdayNotSelectedException()
}

