# JPA life Cycle Events Annotations

- [JPA life Cycle Events Annotations](#jpa-life-cycle-events-annotations)
  - [Configuration](#configuration)
  - [Listener and Entity Configuration](#listener-and-entity-configuration)
  - [Persistent.xml setup](#persistentxml-setup)

References
- [baeldung JPA Entity Lifecycle Events](https://www.baeldung.com/jpa-entity-lifecycle-events)

`@PrePersist`  : before persist is called for a new entity  
`@PostPersist` : after persist is called for a new entity   

`@PreRemove`  : before an entity is removed  
`@PostRemove` : after an entity has been deleted  

`@PreUpdate`  : before the update operation  
`@PostUpdate` : after an entity is updated   

`@PostLoad` : after an entity has been loaded  


## Configuration

There are two approaches for using the lifecycle event annotations: 

Add life cycle event annotations methods in the entity (model.class)
```java
class modelEntity{

    // ... other attributes

    @PostPersist
    @PrePersist
    @PostLoad
    @PreUpdate
    @PostUpdate
    @PreRemove
    @PostRemove
    public void monitor(Object o) {
        User user = (User) o;
        System.out.println(user.getName());
    }
}

```

Creating an EntityListener with annotated callback methods. 
```java
public class Listener {
    @PostPersist
    @PrePersist
    @PostLoad
    @PreUpdate
    @PostUpdate
    @PreRemove
    @PostRemove
    public void monitor(Object o) {
        User user = (User) o;
        System.out.println(user.getName());
    }
}
```

Thees approaches can also be used both at the same time.   
Each Life Cycle Annotation **callback methods are required to have a `void` return type**. For example 
```java
@PrePersist
public void insertNewRecord(){
    log.info("Insert New Record");
}
```

## Listener and Entity Configuration

```java
@Entity
@ExcludeDefaultListeners // exclude default Listener
@ExcludeSuperClassListeners // exclude listener from base class
@EntityListeners(com.application.xxxListener.class)
class child extends base{
    // ...
}
```
  
You can add more than one listener in `@EntityListeners`
```java
@EntityListeners(
    com.application.ListenerA.class, 
    com.application.ListenerB.class, 
    com.application.ListenerC.class)
```

## Persistent.xml setup

```xml
<persistence-unit name="">
    ...
    <default-entity-listeners>
        com.application.defaultListener.class
    </default-entity-listeners>
    ...
</persistence-unit>
```