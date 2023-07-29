package kz.asetkenes.learnandroid.data.tags.room

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.asetkenes.learnandroid.domain.articles.entities.ArticleEntity
import kz.asetkenes.learnandroid.domain.tags.TagsRepository
import kz.asetkenes.learnandroid.domain.tags.entities.TagEntity

class RoomTagsRepository(
    private val tagsDao: TagsDao,
    private val ioDispatcher: CoroutineDispatcher
) : TagsRepository {

    override suspend fun getAllTags(): List<TagEntity> = withContext(ioDispatcher) {
        tagsDao.getAllTags().map { tagDb -> tagDb.toTag() }
    }

    override suspend fun getTagsByArticle(articleId: Long): List<TagEntity> =
        withContext(ioDispatcher) {
            tagsDao.getTagsByArticleId(articleId).map { tagDb -> tagDb.toTag() }
        }

    override suspend fun getArticlesByTag(tagId: Long): List<ArticleEntity> =
        withContext(ioDispatcher) {
            tagsDao.getArticlesByTag(tagId).map { articleDb -> articleDb.toArticle() }
        }

}