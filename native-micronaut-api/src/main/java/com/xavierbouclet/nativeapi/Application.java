package com.xavierbouclet.nativeapi;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.TypeHint;
import io.micronaut.runtime.Micronaut;

import javax.persistence.Entity;
import java.util.*;

//@TypeHint(
//    value = {
//            UUID.class
//        },
//    accessType = {TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS}
//)
@Introspected(packages="com.xavierbouclet.nativeapi", includedAnnotations= Entity.class)
public class Application{
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}


