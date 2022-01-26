package com.xavierbouclet.nativeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

//[
//{
//    "name": "org.hibernate.dialect.PostgreSQL10Dialect",
//    "allDeclaredFields": true,
//    "allDeclaredConstructors": true,
//    "allDeclaredMethods": true
//}
//]

//@TypeHint(types = [FluentQuery.FetchableFluentQuery.class], access = [TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.DECLARED_METHODS, TypeAccess.DECLARED_CLASSES])
//@TypeHint(types = [ActorService.class, MovieService.class], access = [TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.DECLARED_METHODS])
//@TypeHint(types = [Actor.class, Movie.class], access = [TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.DECLARED_METHODS])
//@TypeHint(types = [CrudMethodMetadata.class, SpringProxy.class,Advised.class, DecoratingProxy.class], access = [TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.DECLARED_METHODS])
//@TypeHint(
//    types = [
//        CrudRepository.class], access = [TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.DECLARED_METHODS]
//)
@SpringBootApplication

//@EnableJpaRepositories(basePackageClasses = [ActorRepository.class, MovieRepository.class])
@Configuration(proxyBeanMethods = false)
public class JvmApiApplication{

    public static void main(String[] args) {
        SpringApplication.run(JvmApiApplication.class, args);
    }
}
