package com.xavierbouclet.nativeapi

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.Id
import java.util.*
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.ManyToMany

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator::class, property="id")
class Movie {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: UUID? = null

    var title: String = ""
    var year: Int = 0

    @JsonIgnoreProperties("movies")
    @ManyToMany(mappedBy = "movies")
    var actors: MutableSet<Actor>? = mutableSetOf()
}
