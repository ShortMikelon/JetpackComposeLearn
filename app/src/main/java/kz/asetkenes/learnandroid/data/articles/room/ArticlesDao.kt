package kz.asetkenes.learnandroid.data.articles.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.asetkenes.learnandroid.data.articles.room.entities.ArticleDbEntity

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<ArticleDbEntity>

    @Query("SELECT * FROM articles WHERE author_id = :authorId")
    suspend fun getAllArticlesByAuthorId(authorId: Long): List<ArticleDbEntity>

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getArticleById(id: Long): ArticleDbEntity

    @Insert(entity = ArticleDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun createArticle(article: ArticleDbEntity)

}