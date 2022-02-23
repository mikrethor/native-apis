## Feature http-client documentation

- [Micronaut Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature testcontainers documentation

- [https://www.testcontainers.org/](https://www.testcontainers.org/)

## Feature jdbc-hikari documentation

- [Micronaut Hikari JDBC Connection Pool documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc)


https://guides.micronaut.io/latest/micronaut-creating-first-graal-app-maven-kotlin.html

sdk use java 21.3.0.r17-grl                                                                                                                                   
export  GRAALVM_HOME=~/.sdkman/candidates/java/21.3.0.r17-grl/

POSTGRES_USER=compose-postgres -e POSTGRES_PASSWORD=compose-postgres -e POSTGRES_DB=compose-postgres

export DATASOURCE_URL=jdbc:postgresql://localhost:5432/compose-postgres
export DATASOURCE_USERNAME=compose-postgres
export DATASOURCE_PASSWORD=compose-postgres

mn create-app com.xavierbouclet.native-micronaut-api --build=maven --lang=java --java-version=17 -f=graalvm,assertj,postgres,logback,data-jpa

 add problem zalando

./mvnw clean package

./mvnw clean package -Dpackaging=native-image