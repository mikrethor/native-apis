package com.xavierbouclet.nativeapi;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import java.util.*;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
class MovieRepository implements PanacheRepositoryBase<Movie, UUID>{}