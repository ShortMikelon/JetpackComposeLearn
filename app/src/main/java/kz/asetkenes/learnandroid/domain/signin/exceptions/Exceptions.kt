package kz.asetkenes.learnandroid.domain.signin.exceptions

import kz.asetkenes.learnandroid.domain.signin.entities.SignInField

class EmptyFieldException(val field: SignInField) : RuntimeException()