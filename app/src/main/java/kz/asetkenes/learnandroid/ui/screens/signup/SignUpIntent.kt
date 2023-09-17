package kz.asetkenes.learnandroid.ui.screens.signup

import kz.asetkenes.learnandroid.domain.signup.entities.SignUpField

sealed class SignUpIntent {

    object SignUpClicked : SignUpIntent()
    data class OnValueChanged(val field: SignUpField, val newValue: String) : SignUpIntent()
    data class OnDateChanged(val date: Long) : SignUpIntent()
    data class OnFieldClicked(val field: SignUpField) : SignUpIntent()

}