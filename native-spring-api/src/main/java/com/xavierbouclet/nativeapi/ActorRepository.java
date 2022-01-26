package com.xavierbouclet.nativeapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
interface ActorRepository extends JpaRepository<Actor, UUID> {

//    public findByFullName(title: String): List<Actor>
}
