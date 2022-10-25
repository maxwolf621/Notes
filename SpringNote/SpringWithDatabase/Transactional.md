# Introduction Transaction of Spring Data

- [Introduction Transaction of Spring Data](#introduction-transaction-of-spring-data)
  - [Spring Boot JPA&Session The Entity States](#spring-boot-jpasession-the-entity-states)
      - [Reference](#reference)
    - [JPA and Hibernate Transaction](#jpa-and-hibernate-transaction)
    - [New Transient](#new-transient)
    - [Persistent (Managed)](#persistent-managed)
    - [Detached](#detached)
    - [Removed](#removed)
  - [HQL Begin Transaction](#hql-begin-transaction)
    - [Transaction Catch Exception And RollBack](#transaction-catch-exception-and-rollback)

## Spring Boot JPA&Session The Entity States

Once an entity is actively managed by Hibernate, all changes are going to be automatically propagated to the database.     
#### Reference
[Beginners guide to jpa hibernate entity state transitions](https://vladmihalcea.com/a-beginners-guide-to-jpa-hibernate-entity-state-transitions/)  

### JPA and Hibernate Transaction
![](https://i.imgur.com/ueO4FzQ.png)  
![](https://i.imgur.com/BAd8i1q.png)  
 

### New Transient
A newly created object that hasn’t **ever been associated with a Hibernate Session** (a.k.a Persistence Context) and is not **mapped to any database table row** is considered to be in the New (Transient) state.

To become persisted we need to either explicitly call the `EntityManager#persist` method or make use of the transitive persistence mechanism.

### Persistent (Managed)
**A persistent entity has been associated with a database table row and it’s being managed by the current running Persistence Context.**

During the Session flush-time  
- Any change made to such entity is going to be detected and propagated to the database. 

With Hibernate, we no longer have to execute `INSERT/UPDATE/DELETE` statements. 

Hibernate employs a transactional write-behind working style and changes are synchronized at the very last responsible moment, during the current Session flush-time. 

### Detached
**Once the current running Persistence Context is closed all the previously managed entities become detached.**

Successive changes will no longer be tracked and no automatic database **synchronization** is going to happen.  

To associate a detached entity to an active Hibernate Session,  
you can choose one of the following options: 
1. Reattaching  
Hibernate (but not JPA 2.1) supports reattaching through the `Session#update` method.  

**A Hibernate Session can only associate one Entity object for a given database row.**  
This is because the Persistence Context acts as an in-memory cache (first level cache) and only one value (entity) is associated to a given key (entity type and database identifier).  

**An entity can be reattached only if there is no other JVM object (matching the same database row) already associated to the current Hibernate Session.**

1. Merging **The merging operation is going to copy the detached entity state to a managed entity instance.**    
If the merging entity has no equivalent in the current Session, one will be fetched from the database.  
The detached object instance will continue to remain detached even after the merge operation.   

### Removed

Although JPA demands that managed entities only are allowed to be removed, Hibernate can also delete detached entities (but only through a `Session#delete` method call).  

**A removed entity is only scheduled for deletion and the actual database DELETE statement will be executed during Session flush-time.**  
## HQL Begin Transaction 

![](https://i.imgur.com/Nzdy9nA.png)

```java
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.journaldev.hibernate.util.HibernateUtil;
public class LoadExample {
   public static void main(String[] args) {
      //get session factory to start transaction
      SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
      Session session = sessionFactory.openSession();
      
      Transaction tx = session.beginTransaction();
      // A transaction called tx will access the database
      //    To operate something with database
      
      tx.commit() // commit a transaction
      session.close();
      sessionFactory.close();
    }
}
```

### Transaction Catch Exception And RollBack

```java
try {  
    session = sessionFactory.openSession();  
    tx = session.beginTransaction();  
    //some action  

    tx.commit();  
  
}catch (Exception ex) {  
    ex.printStackTrace();  
    tx.rollback();  
}  
finally {
    session.close();
}  
```


