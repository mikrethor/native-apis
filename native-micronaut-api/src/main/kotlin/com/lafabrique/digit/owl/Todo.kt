package com.lafabrique.digit.owl

import io.micronaut.data.annotation.Id
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue

@Entity
class Todo {

    @javax.persistence.Id
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: UUID? = null

    var title: String = ""
    var completed: Boolean = false
}
