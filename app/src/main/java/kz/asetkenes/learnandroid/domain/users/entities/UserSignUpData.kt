package kz.asetkenes.learnandroid.domain.users.entities

data class UserSignUpData(
    val name: String,
    val email: String,
    val password: String,
    val aboutMe: String,
    val dateOfBirthday: Long
)