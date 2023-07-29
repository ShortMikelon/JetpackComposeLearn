package kz.asetkenes.learnandroid.data.tags.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kz.asetkenes.learnandroid.data.articles.room.entities.ArticleDbEntity

@Entity(
    tableName = "article_tags",
    primaryKeys = ["article_id", "tag_id"],
    foreignKeys = [
        ForeignKey(
            entity = ArticleDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["article_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TagDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("tag_id")
    ]
)
data class ArticleTagDbEntity(
    @ColumnInfo(name = "article_id") val articleId: Long,
    @ColumnInfo(name = "tag_id") val tagId: Long
)
