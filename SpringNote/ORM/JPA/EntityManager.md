# EntityManger

每個EntityManager都與一個 Persistence Context 關聯，EntityManager不直接維護Entity，而是將之委托給 Persistence Context，Persistence Context中會維護一組Entity實例，Entity實例在Persistence Context中為Managed狀態。

Entity物件的生命週期、與資料表格的對應、資料庫的存取，都與EntityManager息息相關

![](https://i.imgur.com/ueO4FzQ.png)  
- Only Objects in Managed State are MANAGED BY EntityManager and Persistence Context

## EntityManager Type

Two Types
1. Application-Managed EntityManager
2. Container-Managed EntityManager


## Application-Managed EntityManager

Application-Managed EntityManager : 透過 EntityManagerFactory 管理 EntityManager。

Persistence Context(所有被管理的Entities)隨著 EntityManager的關閉而失效，在Persistence Context內的Entities的狀態皆不是 Managed State。

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