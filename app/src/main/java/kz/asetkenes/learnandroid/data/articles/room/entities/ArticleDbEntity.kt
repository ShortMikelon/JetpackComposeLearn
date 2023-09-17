package kz.asetkenes.learnandroid.data.articles.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kz.asetkenes.learnandroid.domain.articles.entities.ArticleEntity
import kz.asetkenes.learnandroid.data.users.room.entities.UserDbEntity
import kz.asetkenes.learnandroid.utils.convertStringToTimestamp
import kz.asetkenes.learnandroid.utils.convertTimestampToString

@Entity(
    tableName = "articles",
    foreignKeys = [
        ForeignKey(
            entity = UserDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["author_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ArticleDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "author_id") val authorId: Long,
    @ColumnInfo(name = "description")val description: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
) {

    fun toArticle() = ArticleEntity(
        id = id,
        name = name,
        authorId = authorId,
        description = description,
        body = body,
        createdAt = convertStringToTimestamp(createdAt),
    )

    companion object {
        fun fromArticle(article: ArticleEntity) = ArticleDbEntity(
            id = article.id,
            name = article.name,
            authorId = article.authorId,
            description = article.description,
            body = article.body,
            createdAt = convertTimestampToString(article.createdAt, onlyDate = true),
        )
    }
}