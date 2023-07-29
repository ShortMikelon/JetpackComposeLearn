package kz.asetkenes.learnandroid.domain.users.entities

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val aboutMe: String,
    val dateBirthday: Long,
    val createdAt: Long
)