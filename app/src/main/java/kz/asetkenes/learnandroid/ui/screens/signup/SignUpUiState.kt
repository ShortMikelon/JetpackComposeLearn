package kz.asetkenes.learnandroid.ui.screens.signup

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val aboutMe: String = "",
    val dateBirthDay: Long = -1L,
    val isInProgress: Boolean = false,
) {
    val enabled get() = !isInProgress
}

