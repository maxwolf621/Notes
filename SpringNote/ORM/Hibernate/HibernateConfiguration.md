# Hibernate Configuration

- [Hibernate Configuration](#hibernate-configuration)
  - [Session Factory `org.hibernate.SessionFactory`](#session-factory-orghibernatesessionfactory)
    - [`getCurrentSession` & `openSession`](#getcurrentsession--opensession)
    - [`openStatelessSession` method](#openstatelesssession-method)
    - [The Session `get` and `load` method](#the-session-get-and-load-method)
    - [Persist entity](#persist-entity)
    - [Delete](#delete)
  - [Build the Database Connection With Session Factory Dynamically](#build-the-database-connection-with-session-factory-dynamically)
  - [Configuration Before Hibernate 4.x](#configuration-before-hibernate-4x)
  - [Configuration After Hibernate 4.x `StandardServiceRegistryBuilder`](#configuration-after-hibernate-4x-standardserviceregistrybuilder)
  - [HibernateUtil](#hibernateutil)
    - [Spring Boot](#spring-boot)

**Hibernate is a popular Object Relational Mapping (ORM) framework that aims at simplifying database programming for developers**

## Session Factory `org.hibernate.SessionFactory`

![](https://i.imgur.com/MYDYdd1.png)   

- A thread Per Session

```java
Configuration config = new Configuration().configure();
SessionFactory sessionFactory = config.buildSessionFactory();

Session session = sessionFactory.openSession();
Transaction tx = session.beginTransaction();
/********************************************/
/*            Transaction Logics            */
/*            Transaction Logics            */
/*            Transaction Logics            */
/*            Transaction Logics            */
/*            Transaction Logics            */
/*            Transaction Logics            */
/********************************************/
tx.commit();
session.close();
```


### `getCurrentSession` & `openSession`

**Hibernate Session Object are not thread safe, so we should use `getCurrentSession()` in multi-threaded environment**

```java
Session currentSession = sessionFactory.getCurrentSession();
```

```java
// method always opens a new session.
Session newSession = sessionFactory.openSession();
//...

newSession.close()
```
- Remember to close the session while we are done with all the database operations
- For web application Frameworks(**multi-threaded environment**), we can choose **to open a new session for each request or for each session based on the requirement**

### `openStatelessSession` method

To reduce the load of the caches we can use this method

```java
StatelessSession statelessSession = sessionFactory.openStatelessFactory()

// ... using session to query with database ...
// ............................................

statelessSession.close();
```
- The Operation performed through a stateless session **bypass Hibernate's event model and interceptor.**
- An instance of StatelessSession does not implement first-level cache and does not interact with any second-level cache  
  > This is good fit in certain situation for loading bulk data into database and **to avoid hibernate session holding huge data in first-level cache memory**. 

- We can also use an object of `java.sql.Connection` to get a stateless object from hibernate.

### The Session `get` and `load` method

Session provides different methods to fetch Data from Database.

`get()` and `load()` are the most common method

`get` : loads the data as soon as it’s called.
Use **get** method when we want to make sure data exists in the database.  

- load method : returns a **proxy object** and loads data only when it’s actually required. **`load()` is better than `get()` because it support lazy loading.  It throws exception when data is not found.**
```java
// Consider we get Class employee
public class ex{
  public static void main(String[] args){
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.getSessionFactory();
    Transaction tx = session.beginTransaction();
    
    Employee empG = (Employee) session.get(Employee.class, new Long(2));
    Employee empL = (Employee) session.load(Employee.class,new Long(1));
    
    tx.commit();
    sessionFactory.close();
  }
}
```
 - [More Details of Get And Load](https://www.tutorialspoint.com/difference-between-get-and-load-in-hibernate#:~:text=In%20hibernate%2C%20get()%20and,throws%20object%20not%20found%20exception.)

### Persist entity

to Save Entity to database and can be invoked outside a transaction

**We should avoid saving outside transaction boundary(No exception throwing/No Rollback)**, otherwise mapped entities will not be saved causing data inconsistency. 

- `save()` returns the generated id immediately, this is possible because primary object is saved as soon as save method is invoked.

- If there are other objects mapped from the primary object, they get saved at the time of committing transaction or when we flush the session.

- For objects that are in persistent state, save updates the data through update query. Notice that it happens when transaction is committed. 
  > If there are no changes in the object, there wont be any query fired. 

- **Hibernate saves loaded entity object to persistent context**, if you will update the object properties after the save call but before the transaction is committed, it will be saved into database.


- `SessionFactory` is a factory class for Session objects. 
  > It is available for the whole application while a Session is only available for particular transaction.**
- `Session` is short-lived while `SessionFactory` objects are long-lived.
- **`SessionFactory` provides a second level cache and `Session` provides a first level cache.**
  > ![](https://i.imgur.com/Hx1qzrX.png)

### Delete

```java
Serializable id = new Long(17);
Object persistentInstance = session.load(Model.class, id);
// check if id : 17 is in the database
if(persistentInstance != null){
    session.delete(persistentInstance)
}
```

## Build the Database Connection With Session Factory Dynamically

[Session Factory From Service Registry and Configuration](/3xYG4oxDQHq9u3BHlL8qsg)

Dynamically configure Database hibernate session factory configuration


Template
```java
/**
Configuration config = new Configuration().addClass(model.class)
                                          .setProperty(...)
                                          .setProperty(...)
**/
Configuration config = new Configuration();
config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/newsdb");
config.setProperty("hibernate.connection.username", "root");
config.setProperty("hibernate.connection.password", "password");
config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

config.addAnnotatedClass(Model1.class);
config.addAnnotatedClass(Model2.class);

// Specify XML Mapping File
config.addResource("com/aaa/bbb/User.hbm.xml")
// Tell Hibernate to find Class Mapping File
config.addClass(com.aaa.bbb.User.class);
```


Session Factory
```java
public static SessionFactory getSessionFactory(String databaseName) {
    Configuration config = new Configuration();
    
    config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
    config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/" + databaseName);
    config.setProperty("hibernate.connection.username", "root");
    config.setProperty("hibernate.connection.password", "password");
    config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
     
    config.addAnnotatedClass(Users.class);
    
    // Create Factory
    SessionFactory sessionFactory = config.buildSessionFactory();
     
    return sessionFactory;
}
```


Create A Session ( POJO -> Persistence -> Database)

Assume we have a entity (Custom),  and now we add a new Custom to our Database via hibernate
```java
public class HibernateDemo1 {
	@Test
	public void demo1() {
		
        // load Hibernate Configuration 
		Configuration configuration = new Configuration().configure();
		
        // Manually load up configuration file
		//configuration.addResource("com/PROJECTNAME/hibernate/demo01/Customer.hbm.xml");
		
		// Build Factory (related JDBC pool)
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		
		// Create A Session Object 
        // via sessionFactory (related to JDBC中的Connection)
		Session session = sessionFactory.openSession();
		
		// Start A `transaction`
		Transaction transaction = session.beginTransaction();
		
		//5 Set up Transaction content
		Customer customer = new Customer();
		customer.setCust_name("John Mayer");
		
        // Save it in Session
		session.save(customer);
		
        // Commit transaction to Database
		transaction.commit();
		
        // Close
		session.close();
		sessionFactory.close();
	}
}
```
## Configuration Before Hibernate 4.x

![](https://i.imgur.com/VxKsEk9.png)
- Configure a object of `hibernate.cfg.xml` using `Configuration()`


To Build A Session Factory
```java
SessionFactory sessionFactory =
    new Configuration().configure().buildSessionFactory();
```

Pull out a session from A Session Factory
```java
Session session = sessionFactory.openSession();
```

## Configuration After Hibernate 4.x `StandardServiceRegistryBuilder`

- `ServiceRegistryBuilder` has been Deprecated instead we use `StandardServiceRegistryBuilder`

Hibernate Configuration
```java
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

// Create Configuration 
Configuration configuration = new Configuration().configure();

// Register Service
StandardServiceRegistryBuilder registry = new StandardServiceRegistryBuilder();

// Register Service apply hibernate setting
registry.applySettings(configuration.getProperties()).build();
```

Build the Session Factory and pull out a Session from Factory
```java
import org.hibernate.SessionFactory;
// Build A Factory
SessionFactory sessionFactory =  configuration.buildSessionFactory(serviceRegistry);
// Create A session
Session session = sessionFactory.openSession();
```

## HibernateUtil 

`hibernateUtil` to build the Session Factory.
```java
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
 
public class HibernateUtil {
    private static SessionFactory sessionFactory;
     
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            // loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            
            // Register Service
            ServiceRegistry serviceRegistry = 
                            new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
             
            // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
        }
         
        return sessionFactory;
    }
}
```


Generate SessionFactory & Session
```java
// import HibernateUtil;
//...

// build a factory for Sessions
SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

// pull out a session from factory
Session session = sessionFactory.openSession();

session.beginTransaction(); // begin Transaction

//  ... Operations ..

session.getTransaction().commit
session.close();
```

### Spring Boot

- [Hibernate-criteria](https://www.baeldung.com/hibernate-criteria-queries)

Maven (Configuration)
```xml
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>   
    <version>5.3.2.Final</version>
</dependency>
```

Create Session
```java
Session session = HibernateUtil.getHibernateSession();
CriteriaBuilder cb = session.getCriteriaBuilder();
```

Query
```java
CriteriaQuery<Item> cr = cb.createQuery(Item.class);
Root<Item> root = cr.from(Item.class);
cr.select(root);

Query<Item> query = session.createQuery(cr);
List<Item> results = query.getResultList();
```
