# entityManager And Session

[Hibernate SessionFactory vs. JPA EntityManagerFactory](https://stackoverflow.com/questions/5640778/hibernate-sessionfactory-vs-jpa-entitymanagerfactory)

```java
EntityManagerFactory
    ^
    |
 extends
    |
SessionFactory

EntityManger
    ^
    |
 extends
    |
Session
```
-  All methods defined by the `EntityManager` are available in the Hibernate `Session`.
- The `Session` and the `EntityManager` translate entity state transitions into SQL statements.


## JPA to Hibernate

EntityManagerFactory to SessionFactory

Bootstrap JPA
```java 
@PersistenceUnit
private EntityManagerFactory entityManagerFactory;

SessionFactory sessionFactory = entityManagerFactory.upwrap(SessionFactory.class)
```

EntityManger to Session
```java
@PersistentContext
private EntityManager entityManager;

Session session = entityManager.unwrap(Session.class)
```


