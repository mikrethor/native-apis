package com.xavierbouclet.nativeapi

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase

import java.util.UUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ActorRepository : PanacheRepositoryBase<Actor, UUID>
