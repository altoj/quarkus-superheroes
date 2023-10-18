# Superheroes Quarkus Villain Microservice

Original Villain Microservice (rest-villains)
from [Quarkus Super-Heroes](https://github.com/quarkusio/quarkus-super-heroes)

This is the Villain REST API microservice. It is a classical HTTP microservice exposing CRUD operations on Villains.
Villain information is stored in a PostgreSQL database. This service is implemented
using [RESTEasy Reactive](https://quarkus.io/guides/resteasy-reactive) with blocking endpoints
and [Quarkus Hibernate ORM with Panache's active record pattern](https://quarkus.io/guides/hibernate-orm-panache#solution-1-using-the-active-record-pattern)
.

Additionally, this application favors field injection of beans (i.e. `@Inject` annotation) over constructor injection.
