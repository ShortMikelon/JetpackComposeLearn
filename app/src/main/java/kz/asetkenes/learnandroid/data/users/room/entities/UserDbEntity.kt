package kz.asetkenes.learnandroid.data.users.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kz.asetkenes.learnandroid.domain.users.entities.User
import kz.asetkenes.learnandroid.domain.users.entities.UserSignUpData
import kz.asetkenes.learnandroid.utils.convertStringToTimestamp
import kz.asetkenes.learnandroid.utils.convertTimestampToString

@Entity(
    tableName = "users",
    indices = [
        Index("email", unique = true)
    ]
)
data class UserDbEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "about_me") val aboutMe: String,
    @ColumnInfo(name = "date_birthday") val dateBirthday: Long,
    @ColumnInfo(name = "created_at") val createdAt: String = "CURRENT_TIMESTAMP"
) {

    fun toUser(): User = User(
        id = id,
        name = name,
        email = email,
        aboutMe = aboutMe,
        dateBirthday = dateBirthday,
        createdAt = convertStringToTimestamp(createdAt),
    )

    companion object {
        fun fromUser(user: User, password: String): UserDbEntity =
            UserDbEntity(
                id = user.id,
                name = user.name,
                email = user.email,
                password = password,
                aboutMe = user.aboutMe,
                dateBirthday = user.dateBirthday,
                createdAt = convertTimestampToString(user.createdAt),
            )

        fun fromUserSignUpData(signUpData: UserSignUpData): UserDbEntity =
            UserDbEntity(
                id = 0,
                name = signUpData.name,
                email = signUpData.email,
                password = signUpData.password,
                aboutMe = signUpData.aboutMe,
                dateBirthday = signUpData.dateOfBirthday
            )
    }
}

