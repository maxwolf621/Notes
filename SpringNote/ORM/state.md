# Hibernate Transaction and Session

- [Hibernate Transaction and Session](#hibernate-transaction-and-session)
  - [Hibernate Session](#hibernate-session)
  - [Spring Boot JPA \& Session The Entity States](#spring-boot-jpa--session-the-entity-states)
    - [Transient State](#transient-state)
    - [Persistent (Managed) State](#persistent-managed-state)
    - [Detached](#detached)
      - [Update](#update)
      - [Merging](#merging)
    - [Removed](#removed)
    - [Conclusion](#conclusion)

Reference
[Beginners guide to jpa hibernate entity state transitions](https://reurl.cc/bGNgLd)  
[Entity 生命週期]( https://openhome.cc/Gossip/EJB3Gossip/EntityLifeCycle.html)   

![](https://i.imgur.com/Nzdy9nA.png)
```java
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.implementation.hibernate.util.HibernateUtil;
public class LoadExample {
   public static void main(String[] args) {

    try{
      SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
      Session session = sessionFactory.openSession();
      Transaction tx = session.beginTransaction();
      /****************************************************
       *              Transactions Logics                 *
       *                                                  *
       ****************************************************/
      tx.commit()
    }catch(Exception ex){
      ex.printStackTrace();  
      tx.rollback();  
    }finally{
      session.close();
      sessionFactory.close();
    }
}
```
## Hibernate Session 

**The Session interface is the main tool used to communicate with Hibernate.** 

It provides an API enabling us to CREATE, READ, UPDATE, and DELETE persistent objects. 

The session has a simple lifecycle.   
We open it, perform some operations, and then close it.

When we operate on the objects during the session, they get attached to that session. 

The changes we make are DETECTED and SAVE upon closing.    

After closing, Hibernate breaks the connections between the objects and the session.   

## Spring Boot JPA & Session The Entity States

Once an entity is actively managed by Hibernate, all changes are going to be automatically propagated to the database.     

![](https://i.imgur.com/ueO4FzQ.png)  
![](https://i.imgur.com/BAd8i1q.png)    
![](../../../images/fe29f05022e65ce80bbdcb2fa9ac588c93ace8fbb229684b6900a090c1b6daed.png)  

### Transient State

It's a newly created object that **hasn't ever been associated with a Hibernate Session** and is not **mapped to any database table row** is considered to be in the New Transient state.

### Persistent (Managed) State

**A persistent entity/object has been associated with a database table row and it's being managed by the current running Persistence Context.**

During the Session flush-time any change made to such entity is going to be detected and propagated to the database. 

With Hibernate, we no longer have to execute `INSERT/UPDATE/DELETE` statements. 

Hibernate employs a transactional write-behind working style and changes are synchronized at the very last responsible moment, during the current Session flush-time. 

### Detached
**Once the current running Persistence Context(e.g. `session.close()` or `entityManager.close()`) is closed all the previously managed entities become detached.**

Successive changes(of entity objects) will no longer be tracked and no automatic database synchronization is going to happen.  

To associate a detached entity to an active Hibernate Session, you can choose one of the following options: 

#### Update

Hibernate (but not JPA 2.1) supports reattaching through the `Session#update` method.  

**A Hibernate Session can only associate one Entity object for a given database row.**  

This is because the Persistence Context acts as an in-memory cache (first level cache) and only one value (entity) is associated to a given key (entity type and database identifier).  

**An entity can be reattached only if there is no other JVM object (matching the same database row) already associated to the current Hibernate Session.**

#### Merging 

**The merging operation is going to COPY THE DETACHED ENTITY STATE to a managed entity instance(Persistance Context).**    

If the merging entity has no equivalent in the current Session, one will be fetched from the database.  

The detached object instance will continue to remain detached even after the merge operation.   

### Removed

Although JPA demands that managed entities only are allowed to be removed, Hibernate can also delete detached entities (but only through a `Session#delete` method call).  

**A removed entity is only scheduled for deletion and the actual database DELETE statement will be executed during Session flush-time.**  


### Conclusion

[實體物件生命週期](https://openhome.cc/Gossip/HibernateGossip/EntityObjectLifeCycle.html)   

Any Changes of Transient and Detached State wont affect data stored in database.

Persistent State is managed by hibernate's persistence layer, each session and transaction, any changes mapped to associated database table row will be flushed

>>> **如果Persistent Object/Entity的Attributes發生變化，且尚未提交之前，物件所攜帶的資料稱之為Dirty Data**，Hibernate會在Persistence Context維護物件的最近讀取版本，並在資料提交時檢查兩個版本的屬性是否有變化，如果有的話，則將資料庫中的資料進行更新(update)。

