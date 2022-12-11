# `@Embedded` and `@Embeddable`

[JPA `@Embedded` and `@Embeddable`](https://www.baeldung.com/jpa-embedded-embeddable)  

`@Embeddable` annotation declares that a class will be embedded by other entities.  
`@Embedded` is used to embed a entity with `@Embeddable` annotation.   

```java
@Embeddable
public class ContactPerson {

    private String firstName;

    private String lastName;

    private String phone;
    // getters, setters
}

/**
  * <p> To have our entity Company, 
  *     embedding contact person detail
  *     and mapping to a single database table </p>
  */
@Entity
public class Company {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String address;

    private String phone;

    /**
      * Use @Embedded annotation to embed @Embeddable Entity
      */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride( name = "firstName", column = @Column(name = "contact_first_name")),
        @AttributeOverride( name = "lastName", column = @Column(name = "contact_last_name")),
        @AttributeOverride( name = "phone", column = @Column(name = "contact_phone"))
    })
    private ContactPerson contactPerson;

    // getter and setter
}
```
- `@AttributeOverride` to tell JPA how to map these fields to database columns.



```java
UserPK pk = new UserPK();
pk.setName("bush");
pk.setPhone("0970123456");
User user = new User();
user.setUserPK(pk);
user.setAge(new Long(35));
EntityManager entityManager =
        JPAUtil.getEntityManagerFactory().createEntityManager();
EntityTransaction etx = entityManager.getTransaction();
etx.begin();
entityManager.persist(user);
etx.commit();
entityManager.close();

// 要載入User，則使用UserPK實例進行查詢：
UserPK pk = new UserPK();
pk.setName("bush");
pk.setPhone("0970123456");
EntityManager entityManager =
        JPAUtil.getEntityManagerFactory().createEntityManager();
EntityTransaction etx = entityManager.getTransaction();
etx.begin();
User user = entityManager.find(User.class, pk);
etx.commit();
entityManager.close();
```