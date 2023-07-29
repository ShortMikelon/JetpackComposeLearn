package kz.asetkenes.learnandroid.data.articles.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kz.asetkenes.learnandroid.domain.articles.entities.ArticleEntity
import kz.asetkenes.learnandroid.data.users.room.entities.UserDbEntity

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
    @PrimaryKey val id: Long,
    val name: String,
    @ColumnInfo(name = "author_id")
    val authorId: Long,
    val description: String,
    val body: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
) {

    fun toArticle() = ArticleEntity(
        id = id,
        name = name,
        authorId = authorId,
        description = description,
        body = body,
        createdAt = createdAt,
    )

    companion object {
        fun fromArticle(article: ArticleEntity) = ArticleDbEntity(
            id = article.id,
            name = article.name,
            authorId = article.authorId,
            description = article.description,
            body = article.body,
            createdAt = article.createdAt,
        )
    }
}