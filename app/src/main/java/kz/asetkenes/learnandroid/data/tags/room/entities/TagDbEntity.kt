package kz.asetkenes.learnandroid.data.tags.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kz.asetkenes.learnandroid.domain.tags.entities.TagEntity

@Entity(tableName = "tags")
data class TagDbEntity(
    @PrimaryKey val id: Long,
    val name: String
) {

    fun toTag(): TagEntity = TagEntity(
        id = this.id,
        name = this.name
    )

    companion object {
        fun fromTagEntity(tag: TagEntity): TagDbEntity = TagDbEntity(
            id = tag.id,
            name = tag.name
        )
    }
}

