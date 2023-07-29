package kz.asetkenes.learnandroid.domain.tags

import kz.asetkenes.learnandroid.domain.articles.entities.ArticleEntity
import kz.asetkenes.learnandroid.domain.tags.entities.TagEntity

interface TagsRepository {

    suspend fun getAllTags(): List<TagEntity>

    suspend fun getTagsByArticle(articleId: Long): List<TagEntity>

    suspend fun getArticlesByTag(tagId: Long): List<ArticleEntity>

}