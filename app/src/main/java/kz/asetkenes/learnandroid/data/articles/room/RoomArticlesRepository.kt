package kz.asetkenes.learnandroid.data.articles.room

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.asetkenes.learnandroid.domain.articles.ArticlesRepository
import kz.asetkenes.learnandroid.domain.articles.entities.ArticleEntity
import kz.asetkenes.learnandroid.data.articles.room.entities.ArticleDbEntity

class RoomArticlesRepository(
    private val articlesDao: ArticlesDao,
    private val ioDispatcher: CoroutineDispatcher
) : ArticlesRepository {

    override suspend fun createArticle(article: ArticleEntity) = withContext(ioDispatcher) {
        val dbEntity = ArticleDbEntity.fromArticle(article)
        articlesDao.createArticle(dbEntity)
    }

    override suspend fun getArticleById(id: Long): ArticleEntity = withContext(ioDispatcher) {
        val articleDb = articlesDao.getArticleById(id)
        articleDb.toArticle()
    }

    override suspend fun getAllArticles(): List<ArticleEntity> = withContext(ioDispatcher) {
        articlesDao.getAllArticles().map { it.toArticle() }
    }

    override suspend fun getAllArticlesByAuthorId(authorId: Long): List<ArticleEntity> =
        withContext(ioDispatcher) {
            articlesDao.getAllArticlesByAuthorId(authorId).map { it.toArticle() }
        }

}