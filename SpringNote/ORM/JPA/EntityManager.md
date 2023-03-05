# EntityManger

- [EntityManger](#entitymanger)
  - [EntityManager Type](#entitymanager-type)
    - [Container-Managed EntityManager (推薦使用)](#container-managed-entitymanager-推薦使用)
    - [Application-Managed EntityManager](#application-managed-entitymanager)
  - [Persistence Context Scope](#persistence-context-scope)
  - [Persistence Unit](#persistence-unit)


每個EntityManager都與一個 Persistence Context 關聯，EntityManager不直接維護Entity，而是將之委托給 Persistence Context，Persistence Context中會維護一組Entity實例，Entity實例在Persistence Context中為Managed狀態。

被 EntityManager 映射到資料庫中的對象，或者從資料庫映射至記憶體空間中的對象，同時關聯到一個 Persistence Context 管理。

這些被管理的對象統稱 Managed Object，每個受管對像都有一個唯一的id。 

**EntityManager 和 Persistence Context之間的關係，一般可以是多對一的**，即多個EntityManager 可以同時指向一個 Persistence Context，以此保證了多個 EntityManager 所管理Managed State Entity 擁有的 ID 是唯一的。

Entity 物件的生命週期、與資料表格的對應、資料庫的存取，都與EntityManager息息相關。

![](https://i.imgur.com/ueO4FzQ.png)  
- Only Objects in Managed State are MANAGED BY EntityManager and Persistence Context


Reference
[JPA EntityManager詳解](https://www.jianshu.com/p/091360c47e6b)  
[Guide to the Hibernate EntityManager](https://www.baeldung.com/hibernate-entitymanager)  
[Spring Data JPA EntityManager Examples (CRUD Operations)](https://www.codejava.net/frameworks/spring-boot/spring-data-jpa-entitymanager-examples)

## EntityManager Type

Two Types
1. Application-Managed EntityManager
2. Container-Managed EntityManager

### Container-Managed EntityManager (推薦使用)

the container creates the `EntityManager` from the `EntityManagerFactory` for us:

Via `@PersistenceContext` annotation
```java
@PersistenceContext
private EntityManager em;
```
- The container is in charge of beginning the transaction, as well as committing or rolling it back.
- The container is responsible for closing the `EntityManager`, so it's safe to use without manual cleanups
### Application-Managed EntityManager

透過 EntityManagerFactory 管理 EntityManager(`EntityManagerFactory#createEntityManager()`)，需要手動操控 EntityManager 關閉 及 連接、控制 Transaction 等。

優點是，Application-Managed EntityManager可以在EJB，也可以使 JPA 脫離 EJB，與任何的Java環境集成，如 Web容器以及J2SE環境等、、、。

Persistence Context 隨著 EntityManager 的關閉而失效，此時在Persistence Context內的Entities 狀態皆不是 Managed State。

```java
package onlyfun.caterpillar;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory entityManagerFactory;

    // 在 Persistence Layer 建立 EntityManagerFactory
    static {
        try {
            entityManagerFactory = 
                Persistence.createEntityManagerFactory("demo");
        }
        catch(Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
 
    public static void closeFactory() {
        getEntityManagerFactory().close();
    }
}

EntityManager entityManager = 
    JPAUtil.getEntityManagerFactory().createEntityManager();

EntityTransaction etx = entityManager.getTransaction();
etx.begin();

entityManager.persist(user);

etx.commit();
entityManager.close();    

JPAUtil.closeFactory();
```


## Persistence Context Scope

Persistence Context預設為Transaction-scoped(`PersistenceContextType.TRANSACTION`)，Persistence Context的存活範圍在`EntityTransaction#begin()`到`EntityTransaction#close()`，也就是 Entity實例 在交易完成之後，將會不受 EntityManager 的管理，將不在是Managed狀態，而處於Detached狀 態。

可以透過 `@PersistenceContext` 時，指定type attribute為 `PersistenceContextType.EXTENDED` 或 `PersistenceContextType.TRANSACTION`。

當 Persistence Context 為 **Extended Persistence Context，EntityManager存活期間，Entity始終受到Persistence Context的管理**，Extended Persistence Context只能用於Stateful Session Bean中，Entity會一直受EntityManager的PersistenceContext管理，直到Stateful Session Bean結束而`EntityManager#close()`為止。

舉個例子來說，當您使用預設的Transaction-scoped Persistence Context，則要更新資料表中的對應資料時，Detached狀態的Entity必須先使其回到Managed狀態，也就是您也許會在Session Bean中設計像以下的一些方法：


```java
    public User updateUser(User user) { // 若 user 已被變更
        User user1 = entityManager.merge(user);
        return user1;
    }

    public User updateUser(User user, String name) { 
        User user1 = entityManager.merge(user);
        user1.setName(name);
        return user1;
    }

    public User deleteUser(User user) {
        User user1 = entityManager.merge(user);
        entityManager.remove(user1);
        return user1;
    }

您`可以想像指定type屬性為PersistenceContextType.EXTENDED 時，一旦進入EntityManager的管理，Entity一直處於Managed的狀態，若如此，則以上的程式片段中，有些不再需要：
...
    public User updateUser(User user) { // 若 user 已被變更
        User user1 = entityManager.merge(user);
        return user1;
    }

    public User updateUser(User user, String name) { 
        User user1 = entityManager.merge(user);
        user.setName(name);
        return user;
    }

    public User deleteUser(User user) {
        User user1 = entityManager.merge(user);
        entityManager.remove(user);
        return user;
    }
....
```

嚴格說來，本頁標題名稱應該叫作PersistenceContext範圍，因為type屬性設定的正是EntityManager的Persistence Context有效範圍，不過一般也常稱為Transaction-scoped EntityManager或Extended-scoped EntityManager。



## Persistence Unit

透過 `META-INF/persistence.xml` 配置Persistence Unit
```xml
<persistence>
    <persistence-unit name="jpa-1" transaction-type="...">

        <description>Hibernate EntityManager Demo</description>

        <!--
            ORM Management
        -->
        <provider>org.hibernate.ejb.HibernatePersistence</provider>

        <!-- 
            Class Entity 
        -->
        <class>com.example.model.User</class>
        <exclude-unlisted-classes>true</exclude-unlisted-classes>
        <!-- 
            DataSource Configuration
        -->
        <properties>
            <property name="javax.persistence.jdbc.dirver" value="..."/>
            <property name="javax.persistence.jdbc.url" value=".."/>
            <property name="javax.persistence.jdbc.user" value="..." />
            <property name="javax.persistence.jdbc.password" value="..." />

            <property name="hibernate.show_sql" value="true" />
            // ... other configurations
        </<properties>
    </persistence-unit>
</persistence>
```

Java EE可以透過`@PersistenceUnit` Annotation
```java
// Java EE
@PersistenceUnit(unitName="jpa-1")
private EntityManagerFactory emf;
```

Java SE透過`Persistence.createEntityManagerFactory("jpa-1")`
```java
// Java SE
EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-1");
```