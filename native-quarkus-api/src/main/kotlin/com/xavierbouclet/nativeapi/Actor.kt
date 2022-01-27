package com.xavierbouclet.nativeapi

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator::class, property="id")
class Actor {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: UUID? = null

    @Column(name="full_name")
    var fullName: String = ""

    @JsonIgnoreProperties("actors")
    @ManyToMany
    @JoinTable(
            name = "actor_movie_mapping",
    joinColumns = [JoinColumn(name = "actor_id", referencedColumnName = "id")],
    inverseJoinColumns = [JoinColumn(name = "movie_id", referencedColumnName = "id")])
    var movies: MutableSet<Movie>? = mutableSetOf()
}
