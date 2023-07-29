package kz.asetkenes.learnandroid.data.tags.room

import androidx.room.Dao
import androidx.room.Query
import kz.asetkenes.learnandroid.data.articles.room.entities.ArticleDbEntity
import kz.asetkenes.learnandroid.data.tags.room.entities.TagDbEntity

@Dao
interface TagsDao {

    @Query("SELECT * FROM tags ORDER BY tags.name")
    suspend fun getAllTags(): List<TagDbEntity>

    @Query(
        "SELECT tags.id, tags.name FROM tags " +
                "LEFT JOIN article_tags " +
                "ON tags.id = article_tags.tag_id WHERE article_tags.article_id=:articleId"
    )
    suspend fun getTagsByArticleId(articleId: Long): List<TagDbEntity>

    @Query(
            "SELECT articles.id, articles.name, " +
                    "articles.author_id, articles.description, " +
                    "articles.body, articles.created_at FROM articles" +
                    "   LEFT JOIN article_tags ON articles.id = article_tags.article_id" +
                    "       WHERE article_tags.tag_id = :tagId"
    )
    suspend fun getArticlesByTag(tagId: Long): List<ArticleDbEntity>

}

