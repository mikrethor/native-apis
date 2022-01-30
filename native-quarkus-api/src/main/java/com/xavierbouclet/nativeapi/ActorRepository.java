package com.xavierbouclet.nativeapi;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
class ActorRepository implements PanacheRepositoryBase<Actor, UUID> {
}
