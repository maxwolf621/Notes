# JPQL JPA

- [JPQL JPA](#jpql-jpa)
  - [EntityManager & JPQL & HQL](#entitymanager--jpql--hql)
    - [DML](#dml)
    - [Entity CRUD Operation](#entity-crud-operation)
      - [Persist vs Merge](#persist-vs-merge)
      - [executeUpdate](#executeupdate)
  - [CascadeType.REMOVE](#cascadetyperemove)
  - [@Query](#query)
    - [Paginator](#paginator)
    - [Integer Parameter (`?`)](#integer-parameter-)
    - [Named Parameter (`:`)](#named-parameter-)
    - [Collection Parameter](#collection-parameter)
    - [Sort parameter](#sort-parameter)
    - [PageRequest](#pagerequest)
    - [@Modifying for sql update query](#modifying-for-sql-update-query)
      - [clearAutomatically & flushAutomatically](#clearautomatically--flushautomatically)
    - [Custom Repositories](#custom-repositories)
  - [JPA Many To Many](#jpa-many-to-many)
  - [Remove Query for to-many associations](#remove-query-for-to-many-associations)
  - [Fetch Join in JPQL](#fetch-join-in-jpql)
    - [Fetch Join in Criteria API](#fetch-join-in-criteria-api)
  - [Fetch Spring Data JPA DTO Projection](#fetch-spring-data-jpa-dto-projection)
  - [Join Unrelated entities](#join-unrelated-entities)
  - [Many to Many & One to Many](#many-to-many--one-to-many)

[](https://thorben-janssen.com/best-practices-many-one-one-many-associations-mappings/)   
[](https://www.baeldung.com/spring-data-jpa-query)   
[](https://www.baeldung.com/hibernate-criteria-queries)   

- Model associations as a **java.util.Set**.
- Provide utility methods to add or remove an entity from an association.
- Always use **FetchType.LAZY**, which is the default, to avoid performance problems.
- Apply query-specific fetching to avoid n+1 select issues.
- Don’t use the CascadeTypes REMOVE and ALL.
- Don’t use **unidirectional one-to-many** associations
- Avoid the **mapping of huge to-many** associations
- Use **orphanRemoval=true** when modeling parent-child associations
- Implement helper methods to update bi-directional associations
- Define **FetchType.LAZY for @ManyToOne** association avoid n+1 select issue



## EntityManager & JPQL & HQL
- [使用 Query 物件](https://openhome.cc/Gossip/EJB3Gossip/Query.html)
- **[JPA EntityManager example in Spring Boot](https://www.bezkoder.com/jpa-entitymanager-spring-boot/)**
- [Spring Data JPA EntityManager Examples](https://www.codejava.net/frameworks/spring-boot/spring-data-jpa-entitymanager-examples)   


new `EntityManager` to create (database) entity
- Create & Remove persistent entity instances
- Find entities by their primary key
- Query over entities
- entity is an object of a class that is mapped to a table in database. It should be a new, un-managed entity instance. 
```java
@PersistenceContext 
private EntityManager entityManager;

/**
 * Session session = sessionFactory.openSession();
 * // Model 
 * Query query = session.createQuery(
 *  "select user.name from User as * user");
 *
 */
```
- The `entityManager` object would be `LocalContainerEntityManagerFactoryBean` which wraps a **Hibernate’s Session object.**

### DML 
```java
// Query
Query query = entityManager.createQuery("SELECT user FROM User user");
Iterator users = query.getResultList().iterator();

// Integer Parameter
Query query = entityManager.createQuery(
                "SELECT user.age FROM User user WHERE user.name = ?1");
query.setParameter(1, "Justin Lin");
Long age = (Long) query.getSingleResult();

// Named Parameter
Query query = entityManager.createQuery(
                "SELECT user.age FROM User user WHERE user.name = :userName");
query.setParameter("userName", "Justin Lin")
Long age = (Long) query.getSingleResult();
```

### Entity CRUD Operation 

- `persist(Object entity)`: make given entity object managed and persistent. Its properties change will be tracked by the Entity Manager, for synchronizing with the database.
- find: search for an entity of the specified class and primary key. If the entity instance is contained in the persistence context, it is returned from there.
- merge: merge the state of the given entity into the current Persistence Context.
- remove: remove the entity instance.

```java
public class TutorialRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public Tutorial save(Tutorial tutorial) {
    entityManager.persist(tutorial);
    return tutorial;
  }

  public Tutorial findById(long id) {
    Tutorial tutorial = (Tutorial) entityManager.find(Tutorial.class, id);
    return tutorial;
  }

  @Transactional
  public Tutorial update(Tutorial tutorial) {
    entityManager.merge(tutorial);
    return tutorial;
  }

  @Transactional
  public Tutorial deleteById(long id) {
    Tutorial tutorial = findById(id);
    if (tutorial != null) {
      entityManager.remove(tutorial);
    }

    return tutorial;
  }

  @Transactional
  public int deleteAll() {
    Query query = entityManager.createQuery("DELETE FROM Tutorial");
    return query.executeUpdate();
  }
}
```


#### Persist vs Merge

[jpa-entitymanager-persist-vs-merge](https://www.codejava.net/frameworks/spring-boot/jpa-entitymanager-persist-vs-merge)

After calling `persist`
- a new row would be inserted into the corresponding table in database.
- the entity object becomes a managed instance or persistent object.
- **the entity manager track changes of persistent objects. Any changes made to the mapped fields of the entity object will be synchronized with the database, provided that the changes happen within the transaction boundary.**
```java
// Save
@Transactional
public void tryPersistDetachedObject() {
    Contact contact = new Contact();
    contact.setId(13);
    contact.setName("simon");
    contact.setEmail("maxwolf@gmail.com");
    contact.setAddress("New York, USA");
    contact.setPhone("9527-9487");   
     
    entityManager.persist(contact);
}

// persist(detached entity object) 
@Transactional
public void tryPersistDetachedObject() {
    Contact contact = new Contact();
    contact.setId(13);
    contact.setName("Tom");
    contact.setEmail("maxwolf@gmail.com");    
    contact.setAddress("Essen, Deutschland");
    contact.setPhone("9527-9487");   
     
    entityManager.persist(contact);
}
// Error
org.hibernate.PersistentObjectException: 
detached entity passed to persist: ....
```
- **You cant's use persist operation to save an existing entity object**


`<T> T merge(T entity)`: Merge the state of the given entity into the current persistence context
- entity can be an un-managed or persistent entity instance. 
- **a new row would be inserted, or an existing one would be updated.**
- **the returned object is different than the passed object.**
- the entity manager tracks changes of the returned, managed object within transaction boundary.

```java
@Transactional
public void testUpdateExistingObject() {
    Contact existContact = new Contact();
    existContact.setId(13); // Already Exist
    existContact.setName("simon");
    existContact.setEmail("namhm@codejava.net");
    existContact.setAddress("Tokyo, Japan");
    existContact.setPhone("123456-2111");  
     
    Contact updatedContact = entityManager.merge(existContact);
     
    boolean passedObjectManaged =  entityManager.contains(existContact);       
    System.out.println("Passed object managed: " + passedObjectManaged);
     
    boolean returnedObjectManaged = entityManager.contains(updatedContact);    
    System.out.println("Returned object managed: " + returnedObjectManaged);
}
```



#### executeUpdate
```java
// update
Query query = entityManager.createQuery(
            "UPDATE User user SET user.age = :userAge WHERE user.name = :userName");
query.setParameter("userAge", 35);
query.setParameter("userName", "Justin Lin");
query.executeUpdate();
```

## CascadeType.REMOVE

Hibernate needs to execute proper lifecycle transitions for all entities. So, Hibernate needs to select all associated Item entities and remove them one by one.

```java 
@OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE,orphanRemoval = true)
private List<Item> items = new ArrayList<Item>();

// use JPQL instead
em.flush();
em.clear();
 
Query q = em.createQuery("DELETE Item i WHERE i.order.id = :orderId");
q.setParameter("orderId", orderId);
q.executeUpdate();
 
order = em.find(PurchaseOrder.class, orderId);
em.remove(order);
```

## @Query

- [Spring JPA @Query example](https://www.bezkoder.com/spring-jpa-query/)
- [JPA Native Query example with Spring Boot](https://www.bezkoder.com/jpa-native-query/)
- [JPQL – How to Define Queries in JPA and Hibernate](https://thorben-janssen.com/jpql/)

```java
@Query(
    "SELECT u FROM User u where u.status = 1"
)
Set<User> FindUser()

@Query(
    value = "SELECT * FROM USERS u WHERE u.status = 1", 
    nativeQuery = true)
)
Set<User> FindUser()

@Query(value = "SELECT u FROM User u")
List<User> findAllUsers(Sort sort);
// sort by name 
userRepository.findAllUsers(Sort.by("name"));
```

### Paginator

```java
@Query(value = "SELECT u FROM User u ORDER BY id")
Page<User> findAllUsersWithPagination(Pageable pageable);

@Query(
  value = "SELECT * FROM Users ORDER BY id", 
  countQuery = "SELECT count(*) FROM Users", 
  nativeQuery = true)
Page<User> findAllUsersWithPagination(Pageable pageable);
```

### Integer Parameter (`?`)

```java
@Query("SELECT u FROM User u WHERE u.status = ?1")
User findUserByStatus(Integer status);

@Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
User findUserByStatusAndName(Integer status, String name);

@Query(
  value = "SELECT * FROM Users u WHERE u.status = ?1", 
  nativeQuery = true)
User findUserByStatusNative(Integer status);
```
### Named Parameter (`:`)

```java
column = :NAME_PARAMETER
```

```java
@Query('Select u From User u Where u.status = :status and u.name = :name')
User findUserByStatusAndName(
    @Param("status") Integer userStatus,
    @Param("name") String userName
)
```

### Collection Parameter

```java
@Query("Select u From User u where u.name In :names")
List<User> findByUserList(
    @Query(names) Collection<String> userNames
)
```

### Sort parameter

- [](https://stackoverflow.com/questions/25486583/how-to-use-orderby-with-findall-in-spring-data)

```java
@Override
public List<Student> findAll() {
    //     studentDao.findAll(new Sort(Sort.Direction.ASC, "id"))
    return studentDao.findAll(sortByIdAsc());
}
private Sort sortByIdAsc() {
    //                  ASC/DSEC        column-id
    return new Sort(Sort.Direction.ASC, "id");
}
```

```java
// dynamically 
@Override
public List<Student> findAll() {
    return studentDao.findAll(Sort sort);
}

repository.findAll(Sort.by(Sort.Direction.DESC, "colName"));
```

### PageRequest

`PageRequest(pageName, size, ?Sort)

```java
// with PageRequest.of
List<Passenger> findByLastName(String lastName, Sort sort);
Page<Passenger> page = repository.findAll(
    PageRequest.of(
        0, 1, Sort.by(Sort.Direction.ASC, "seatNumber")
    ));
```

### @Modifying for sql update query

`UPDATE/INSERT VALUE/DELETE FROM`

```java 
@Modify
@Query(
    "Update user u set u.status = :status where u.name = :name"
)int updateUserSetStatusForName(
    @Param("status") Integer userStatus,
    @Param("name") String userName
)

// native

```

#### clearAutomatically & flushAutomatically

[](https://stackoverflow.com/questions/43665090/why-do-we-have-to-use-modifying-annotation-for-queries-in-data-jpa)

`clearAutomatically(true/false)` - Defines whether we should clear the underlying persistence context(drop any pending updates) after executing the modifying query. (Default -false)

`flushAutomatically(true/false)` - Defines whether we should flush the (entities) underlying persistence context before executing the modifying query. (Default -false)

```java
public void fetchAndDeleteActiveUser {
    // these two transaction in the same persistence context
    User johnUser = userRepo.findById(1); // store in first level cache
    repo.deleteInActiveUsers(); // you think that john is deleted now 
    System.out.println(userRepo.findById(1).isPresent()) // TRUE!!!
    System.out.println(userRepo.count()) // 1 !!!
       
    // JOHN still exist since in this transaction persistence context
    // John's object was not cleared upon @Modifying query execution, 
    // John's object will still be fetched from 1st level cache 
    // `clearAutomatically` takes care of doing the 
    // clear part on the objects being modified for current 
    // transaction persistence context
}
```
To connect things together if you have multiple isolated transactions (e.g not having a transactional annotation on the service) hence you would have multiple sessions following the way spring data works hence you have multiple persistence contexts that mean you might delete/modify an entity in a persistence context even with using `flushAutomatically` the same deleted/modified entity might was fetched and cached in another transaction's persistence context already that would cause wrong business decisions due to wrong or un-synced data

### Custom Repositories 

- [The best way to write a custom Spring Data Repository](https://vladmihalcea.com/custom-spring-data-repository/)

```java
public interface UserRepositoryCustom {
    List<User> findUserByEmails(Set<String> emails);
}
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findUserByEmails(Set<String> emails) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);

        Path<String> emailPath = user.get("email");

        List<Predicate> predicates = new ArrayList<>();
        for (String email : emails) {
            predicates.add(cb.like(emailPath, email));
        }
        query.select(user)
            .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
            .getResultList();
    }
}

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    //  query methods from section 2 - section 7
}

Set<String> emails = new HashSet<>();
// filling the set with any number of items

userRepository.findUserByEmails(emails);
```


## JPA Many To Many

- Model associations as a `java.util.Set`.
- Avoid the CascadeTypes `REMOVE` and `ALL` (it might remove more records than you intended.)
- Always use `FetchType.LAZY`, which is the default (for to-many association), to avoid performance problems.
- If you’re using `FetchType.LAZY`, you need to know about query-specific fetching avoiding n+1 select issues.
- Provide utility methods to add or remove an entity from an association(perform the required operations on both entities.)

```java
@Entity
public class Book {
 
    @ManyToMany
    @JoinTable(name = "book_author", 
            joinColumns = { @JoinColumn(name = "fk_book") }, 
            inverseJoinColumns = { @JoinColumn(name = "fk_author") })
    private Set<Author> authors = new HashSet<Author>();
     
    ...
}
@Entity
public class Author {
    @ManyToMany(mappedBy = "authors") // mapped by authors attribute from Book
    private Set<Book> books = new HashSet<Book>();
 
    //...
     
    public void addBook(Book book) {
        this.books.add(book);
        book.getAuthors().add(this);
    }
 
    public void removeBook(Book book) {
        this.books.remove(book);
        book.getAuthors().remove(this);
    }
}

@Query(
    "Select a From Author a Join Fetch a.books Where a.id =? id"
)
Author findAuthorById(@Param("id") long authorId);
Author a = em.createQuery(
    "SELECT a FROM Author a JOIN FETCH a.books WHERE a.id = 1", 
    Author.class).getSingleResult();
```


## Remove Query for to-many associations

- [Why you should avoid CascadeType.REMOVE for to-many associations and what to do instead](https://thorben-janssen.com/avoid-cascadetype-delete-many-assocations/)

When your association contains a lot of entities, it’s better to remove them with a few queries. 

But it needs a fixed number of queries to remove an Author with all associated Books and performs much better for huge associations.

```java
Author a = em.find(Author.class, 1);
         
// get all books that this author wrote alone
Query q = em.createNativeQuery("SELECT ba.bookId FROM BookAuthor ba JOIN Book b ON ba.bookId = b.id JOIN BookAuthor ba2 ON b.id = ba2.bookId WHERE ba2.authorId = ? GROUP BY ba.bookId HAVING count(ba.authorId) = 1");
q.setParameter(1, a.getId());
List<Integer> bookIds = (List<Integer>)q.getResultList();
         
// remove all associations for this author
q = em.createNativeQuery("DELETE FROM BookAuthor ba WHERE ba.authorId = ?");
q.setParameter(1, a.getId());
q.executeUpdate();
         
// remove all books that this author wrote alone
q = em.createNativeQuery("DELETE FROM Book b WHERE b.id IN (:ids)");
q.setParameter("ids", bookIds);
q.executeUpdate();
     
// remove author
em.remove(a);
```


## Fetch Join in JPQL

- [Other Fetch Concept](https://thorben-janssen.com/5-ways-to-initialize-lazy-relations-and-when-to-use-them/)
- [Spring JPA: when to use "Join Fetch"](https://medium.com/javarevisited/spring-jpa-when-to-use-join-fetch-a6cec898c4c6)

`fetch join` allows associations or collections of values to be initialized along with their parent objects using a single select. 

Using fetch joins in JPQL statements can require a huge number of queries, which will make it difficult to maintain the codebase. So before you start to write lots of queries, you should think about the number of different fetch join combinations you might need. If the number is low, then this is a good approach to limit the number of performed queries.

```sql
SELECT p FROM Parent p JOIN FETCH p.children children WHERE ...
```

For example
```java

@Query(
    "SELECT o FROM Order o JOIN FETCH o.items i WHERE o.id = :id"
)
Order FindOrderById(
    @Param("id") Long id
)
```

### Fetch Join in Criteria API

```java
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery q = cb.createQuery(Order.class);
Root o = q.from(Order.class);
o.fetch("items", JoinType.INNER); 
q.select(o);
q.where(cb.equal(o.get("id"), orderId));
 
Order order = (Order)this.em.createQuery(q).getSingleResult();
```

## Fetch Spring Data JPA DTO Projection

- [The best way to fetch a Spring Data JPA DTO Projection](https://vladmihalcea.com/spring-jpa-dto-projection/)

## Join Unrelated entities

- [How to join unrelated entities with JPA and Hibernate](https://thorben-janssen.com/how-to-join-unrelated-entities/)

## Many to Many & One to Many

- [Best Practices for Many-To-One and One-To-Many Association Mappings](https://thorben-janssen.com/best-practices-many-one-one-many-associations-mappings/#Think_twice_before_using_CascadeTypeRemove)