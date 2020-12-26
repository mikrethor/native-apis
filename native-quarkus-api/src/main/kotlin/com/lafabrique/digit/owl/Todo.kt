package com.lafabrique.digit.owl

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Todo : PanacheEntityBase(),Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: UUID? = null

    var title: String = ""
    var completed: Boolean = false

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val todo: Todo = o as Todo
        return completed == todo.completed &&
                title == todo.title
    }

    override fun hashCode(): Int {
        return Objects.hash(title, completed)
    }
}
