package io.ajurasz

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@Entity
class Post(val title: String,
           val content: String,
           val createDate: LocalDateTime = LocalDateTime.now()
) : PersistableEntity()

@MappedSuperclass
sealed class PersistableEntity {
    @Id
    @GeneratedValue
    private var id: Long? = null

    fun getId(): Long? = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersistableEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
