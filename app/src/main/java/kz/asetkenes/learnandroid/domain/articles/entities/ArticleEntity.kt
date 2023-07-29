package kz.asetkenes.learnandroid.domain.articles.entities

data class ArticleEntity(
    val id: Long,
    val name: String,
    val authorId: Long,
    val description: String,
    val body: String,
    val createdAt: Long
)


