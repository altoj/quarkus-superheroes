# Superheroes Quarkus Hero Microservice

This is the Hero REST API microservice. It is a reactive HTTP microservice exposing CRUD operations on Heroes. Hero
information is stored in a PostgreSQL database. This service is implemented
using [RESTEasy Reactive](https://quarkus.io/guides/resteasy-reactive) with reactive endpoints
and [Quarkus Hibernate Reactive with Panache's repository pattern](https://quarkus.io/guides/hibernate-reactive-panache#solution-2-using-the-repository-pattern)
.

Additionally, this application favors constructor injection of beans over field injection (i.e. `@Inject` annotation).