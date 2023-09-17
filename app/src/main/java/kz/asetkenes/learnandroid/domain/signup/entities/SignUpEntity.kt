package kz.asetkenes.learnandroid.domain.signup.entities

import kz.asetkenes.learnandroid.domain.signup.exceptions.DateOfBirthdayNotSelectedException
import kz.asetkenes.learnandroid.domain.signup.exceptions.EmptyFieldsException
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
    val emptyFields = mutableListOf<SignUpField>()
    if (name.isEmpty()) emptyFields.add(SignUpField.NAME)
    if (email.isEmpty()) emptyFields.add(SignUpField.EMAIL)
    if (password.isEmpty()) emptyFields.add(SignUpField.PASSWORD)
    if (emptyFields.isNotEmpty()) throw EmptyFieldsException(emptyFields)

    if (password != repeatPassword) throw PasswordMismatchException()
    if (dateOfBirthday == DATE_BIRTHDAY_NOT_SELECTED) throw DateOfBirthdayNotSelectedException()
}

