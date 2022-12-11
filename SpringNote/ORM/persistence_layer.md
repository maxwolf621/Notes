# Persistence Layer

- [Persistence Layer](#persistence-layer)
  - [Persistent Context](#persistent-context)
    - [Hibernate](#hibernate)
    - [JPA](#jpa)
  - [Session Data Identity](#session-data-identity)
    - [via (`==`)](#via-)
    - [Override equals and hashCode](#override-equals-and-hashcode)
  - [Flush Mode](#flush-mode)
    - [Hibernate Flush Mode](#hibernate-flush-mode)
    - [batch\_size](#batch_size)
    - [JPA flush mode](#jpa-flush-mode)

## Persistent Context

### Hibernate 

Each (Managed State) Persistent Object is managed by a Persistence Context for operations such as Cache, Dirty Check, Flush, etc., which also might affects efficiency in the persistence layer

Persistence Context holds copies of Persistent Object for comparing with current Persistent object state aka dirty check.   
These copies wont be removed automatically, once session loads bulk data application system might have `OutOfMemoryException`.  

### JPA

Persistence Context containing managed state objects is managed by `EntityManager` 

There are two type EntityManager
1. Container-Managed EntityManager
2. Application-Managed EntityManager

- Container-Managed EntityManager's Persistence Context has two type managed scope `PersistenceContextType.TRANSACTION`(default) and `PersistenceContextType.EXTENDED`
- Application-Managed EntityManage's Persistence Context's managed scope in EntityManager建立與關閉之間

Persistence Context管理 Managed狀態Entity Object，此時對Entity Object內的任何Attributes更動(Modified)，Persistence Context 會將更動對應至資料庫的變動，不過每次在程式中一旦變動Entity就進行 Data Synchronization 會影響效能，因此實際上對Entity Object的 Modified，都會等到 `EntityManager#flush()` 之後，在Flush之前任何對Entity Object's Modified，都會先被收集起來，`EntityManager#flush()` 時候再一次進行資料庫的變更。

Persistence Scope 存活期間會管理 Entity Object，故大量儲存 Entity Object 時，Persistence Context 中管理的 Entity Objects 會不斷的增加，甚至最後導致Exception `OutOfMemoryError`，所以應主動每隔一段 Interval 使用 `EntityManager#flush()` 與 Database 進行 Data Synchronization，再使用 `EntityManager#clear()` 清除在Persistence Context內的 Entity Object。

## Session Data Identity

Data Identity fetched from Database 

### via (`==`)
```java
Session session = sessions.openSession();
// same reference
Object obj_1 = session.load(Model.class, new Integer(1));
Object obj_2 = session.load(Model.class, new Integer(1));
session.close();

// different reference
Session session1 = sessions.openSession();
Object obj_1 = session.load(Model.class, new Integer(1));session1.close();
session1.close();

Session session2 = sessions.openSession();
Object obj_2 = session.load(Model.class, new Integer(1));
session2.close();
```

Database use primary key to check record identity

Also via `Session#getIdentifier()` to get persistent object's identifier property

如果要結合equals()、hashCode()來實作物件相等，一個根據資料庫的識別屬性的實作方式，是透過識別屬性的getter方法取得物件的識別屬性值並加以比較， 例如若id的型態是String，一個實作的例子如下：

### Override equals and hashCode

It also calls database identity equality.
```java
public class User {
    // ...
    public boolean equals(Object o) {
        // same reference
        if(this == o) return true;
        
        if(id == null || !(o instanceof User)) return false;

        // Compare both objects properties
        final User user == (User) o;
        return this.id.equals(user.getId());
    }
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashcode();
    }
}
```
**該方法無法用於一個物件剛被new出來還沒有`save()`時，因為此時的物件並不會被賦予id值**，例如物件若會被加入`Set`之中，該物件在被儲存至資料庫前與後，在Set中的判斷將有所不同，導致明明是同一個物件，卻使得程式出現不同的行為。

## Flush Mode

### Hibernate Flush Mode

透過使用`Session#setFlushMode()`來改變Flush Mode，設定對於經常進行更新、查詢、更新、查詢這種重複動作的操作，將是否flush的檢查工作交給Hibernate來作。

**Hibernate預設行為為FlushMode的預設是`FlushMode.AUTO`，在下一次的查詢之前，Hibernate會檢查持久物件目前的狀態是否影響下一次的查詢結果，如果沒有變更，則不會先行flush，只有在有變更的情況下，才會先flush再進行查詢。**

`FlushMode.COMMIT`:在下一次查詢之前，Hibernate不會主動檢查Persistence Object狀態並確定是否要flush，**只有在Programmer主動commit一個Transaction**，才會進行flush。

`FlushMode.MANUAL`:必須明確呼叫`Session#flush()`，才會進行flush。

主動呼叫`Session#flush()`方法時機之一為，當Session在使用`save()`儲存物件時，會將要儲存的物件納入Session level快取管理，在進行大量數據儲存時，快取中的實例大量增加，最後會導致OutOfMemoryError，可以主動每隔一段時間使用Session的flush()強制儲存物件，並使用`clear()`清除快取，例如：
```java
Session session = sessionFactory.openSession();
Transaction tx = session.beginTransaction();

while(....) { // 大量載入物件時的迴圈示意
    // ...
    session.save(someObject);
    if(count % 100 == 0) { // 每100筆資料
        session.flush(); // 送入資料庫
        session.clear(); // 清除快取
    }
}

tx.commit();
session.close();
```
### batch_size

在SQL Server、Oracle等資料庫中(MySQL不支援這個功能)，可以在Hibernate設定檔中設定屬性`hibernate.jdbc.batch_size` 來控制每多少筆資料就送至資料庫
```xml
<hibernate-configuration>
    <session-factory>
        ....
        <property name="hibernate.jdbc.batch_size">100</property>
        ....
    </session-factory>
<hibernate-configuration>
```


### JPA flush mode

