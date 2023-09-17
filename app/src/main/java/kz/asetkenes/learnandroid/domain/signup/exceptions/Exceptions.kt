package kz.asetkenes.learnandroid.domain.signup.exceptions

import kz.asetkenes.learnandroid.domain.signup.entities.SignUpField

class EmptyFieldsException(val fields: List<SignUpField>) : RuntimeException()

class PasswordMismatchException : RuntimeException()

class DateOfBirthdayNotSelectedException : RuntimeException()