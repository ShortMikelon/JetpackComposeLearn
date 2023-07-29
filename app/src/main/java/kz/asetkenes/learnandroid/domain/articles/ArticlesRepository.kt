package kz.asetkenes.learnandroid.domain.articles

import kz.asetkenes.learnandroid.domain.articles.entities.ArticleEntity

interface ArticlesRepository {

    suspend fun createArticle(article: ArticleEntity)

    suspend fun getArticleById(id: Long): ArticleEntity

    suspend fun getAllArticles(): List<ArticleEntity>

    suspend fun getAllArticlesByAuthorId(authorId: Long): List<ArticleEntity>

}


