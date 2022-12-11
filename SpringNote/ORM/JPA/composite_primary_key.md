# Composite Primary Key

- [Composite Primary Key](#composite-primary-key)
  - [Via `@embeddedId`](#via-embeddedid)
  - [Via `@IdClass`](#via-idclass)

References
- [jpa-composite-primary-keys](https://www.baeldung.com/jpa-composite-primary-keys)

## Via `@embeddedId` 

```java
@Embaddable
class User{
    private String name;
    private String number;

    // No args constructor is needed 
    User user(){
        // ...
    }

    // getter and setter
    // equal and hashcode
}


@Entity
class Account{
    
    @EmbeddedId
    private User user;

    private String accountType;

    // other fields
    // constructors 
    // setter and getter
    // equal and hashcode
}
```

If we're going to access parts of the composite key individually, we can make use of @IdClass, but in places where we frequently use the complete identifier as an object, @EmbeddedId is preferred.

```java
// insert new row 
User pk = new User();
pk.setName("bush");
pk.setNumber("12345678")

Account account = new Account();
account.setUserPK(pk);
account.setAccountType("GOLD");

EntityManager entityManager =
        JPAUtil.getEntityManagerFactory().createEntityManager();
EntityTransaction etx = entityManager.getTransaction();
etx.begin();

entityManager.persist(user);

etx.commit();
entityManager.close();


// query
EntityManager entityManager =
        JPAUtil.getEntityManagerFactory().createEntityManager();

EntityTransaction etx = entityManager.getTransaction();
etx.begin();

Account account = entityManager.find(Account.class, pk);

etx.commit();
entityManager.close();
```

## Via `@IdClass`


`@IdClass` approach can be quite useful in places where we are using a composite key class that we can't modify.

```java
@Entity
@IdClass(User.class)
class Account{
    
    @Id
    private int id;
    @Id
    private String name;

    // setter and getter
    // constructors
    // equal and hashcode
}
```
- Annotate fields from `@IdClass(MODEL.class)` with `@Id`.

```java 
// Insert new Entity
EntityManager entityManager =
        JPAUtil.getEntityManagerFactory().createEntityManager();

EntityTransaction etx = entityManager.getTransaction();
etx.begin();

User pk = new User();
pk.setName("bush");
pk.setNumber("12345678")
Account account = new Account();
account.setUserPK(pk);
account.setAccountType("Normal");

entityManager.persist(user);

etx.commit();
entityManager.close();

// Query 
EntityManager entityManager =
        JPAUtil.getEntityManagerFactory().createEntityManager();

EntityTransaction etx = entityManager.getTransaction();
etx.begin();

User user = entityManager.find(User.class, pk);

etx.commit();
entityManager.close();
```