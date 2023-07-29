package kz.asetkenes.learnandroid.data.users.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.asetkenes.learnandroid.data.users.room.entities.UserDbEntity
import kz.asetkenes.learnandroid.data.users.room.entities.UserSignUpTuple

@Dao
interface UsersDao {

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserDbEntity>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun findById(id: Long): UserDbEntity?

    @Query("SELECT users.id, users.email, users.password FROM users " +
            "   WHERE users.email = :email")
    suspend fun findByEmail(email: String): UserSignUpTuple?

    @Insert(entity = UserDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UserDbEntity)

}