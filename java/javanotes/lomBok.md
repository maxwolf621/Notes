# LomBok

- [LomBok](#lombok)
  - [Reference](#reference)
  - [Maven](#maven)
  - [Annotations](#annotations)
    - [@ToString](#tostring)
    - [@EqualAndHashCode](#equalandhashcode)
  - [@NoArgsConstructor](#noargsconstructor)
  - [@AllArgsConstructor](#allargsconstructor)
  - [@RequiredArgsConstructor](#requiredargsconstructor)
  - [@Data](#data)
  - [@Value](#value)
  - [@Slf4j](#slf4j)
  - [@Builder](#builder)
    - [Customizing Lombok Builders](#customizing-lombok-builders)
  - [Making Multiple @Builders Coexist](#making-multiple-builders-coexist)
  - [SuperBuilder](#superbuilder)
    - [three-tier hierarchy](#three-tier-hierarchy)

`LomBok` reduces boilerplate code, for example : 
![](https://i.imgur.com/inZrWpM.png)  


## Reference
[kucw java-lombok](https://kucw.github.io/blog/2020/3/java-lombok/)
[Codes Example](https://github.com/eugenp/tutorials/tree/master/lombok-modules/lombok)

## Maven
[Maven Version](https://mvnrepository.com/artifact/org.projectlombok/lombok)

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version> <!--look up office page to pick up--> </version>
</dependency>
```

## Annotations
![](https://i.imgur.com/H0vjt5y.png)


If there is a class 
```java
public class User{
    private Integer id  ;
    private String name ;
    
    // setter and getter methods
    // toString method
}
```

### @ToString

This equals
```java
public String toString{
    return "User(id =" this.id + ", name = " this.name ")";
}
```

### @EqualAndHashCode

this equals
```java
public boolean equals(Object o)
{
    //...
}

public int hashCode()
{
    // attributes id and name
    return Object.hash(id,name)
}
```

`@EqualAndHashCode(exclude = "id")` equals  

Exclude attribute `id` from `equal` and `HashCode`
```java
public boolean equals(Object o)
{
    //...
}
public int hashCode
{
    // attribute name only
    return Object.hash(name)
}
```

- [For inheritance same hashcode problem](https://blog.csdn.net/zhanlanmg/article/details/50392266)  
**By using `@EqualsAndHashCode(callSuper=true)` to solve the problem when derived and base has same hashcode.** 

## @NoArgsConstructor

equals
```java
public User(){}
```

##  @AllArgsConstructor  
equals
```java
// all fields
public User(id, name){
    this.id = id;
    this.name = name;
}
```

If we don't create a constructor, java will create a no args Constructor by itself.    
so do make sure add annotation `@NoArgsConstructor` while there is `@AllArgsConstructor`     

## @RequiredArgsConstructor

set the required attribute as `final` type 
![](https://i.imgur.com/hcyzMlD.png)

```java
public User(id, name){
    this.id = id;
}
```

## @Data

`@Data` generates all the boilerplate that is normally associated with a simple POJO : 
```java
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
```

## @Value

All Attributes with `final` keyword by default.

`@value` is equivalent to
```java
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
```


```java
@Value
final class ImmutableClient { // Read Only Class
    private int id;
    private String name;
}
```
- `@Data` 適合用在 **POJO 或 DTO**上，`@Value`則是適合加在值不希望被改變的Class上，像是某個類的值當創建後就不希望被更改，只能被讀
- lombok 的注解 `@Value` 和另一個 Spring 的注解 `@Value` 撞名  

## @Slf4j

Log the console's information

![](https://i.imgur.com/rGbxUUo.png)


## @Builder

- [Using Lombok’s @Builder Annotation](https://www.baeldung.com/lombok-builder)
- [Lombok Builder with Default Value](https://www.baeldung.com/lombok-builder-default-value)
To represent with setters 
![](https://i.imgur.com/P9u4632.png)

### Customizing Lombok Builders

we write the parts of the builder that we want to customize and the Lombok `@Builder` annotation will simply not generate those parts


For example add method to test fields `text` and `file`
```java
@Builder
@Data
public class Message {
    private String sender;
    private String recipient;

    // fields with custom builder
    private String text;
    private File file;

    public static class MessageBuilder {
        private String text;
        private File file;

        public MessageBuilder text(String text) {
            this.text = text;
            verifyTextOrFile();
            return this;
        }

        public MessageBuilder file(File file) {
            this.file = file;
            verifyTextOrFile();
            return this;
        }

        private void verifyTextOrFile() {
            if (text != null && file != null) {
                throw new IllegalStateException("Field text and file should be null.");
            }
        }
    }
}

// if we build with text and file 
// it will throw exception
Message message = Message.builder()
  .sender("user@somedomain.com")
  .recipient("someuser@otherdomain.com")
  .text("How are you today?")
  .file(new File("/path/to/file"))
  .build();
```

## Making Multiple @Builders Coexist

- [Inheritance with Lombok](https://blog.knoldus.com/how-to-deal-with-inheritance-while-using-lombok-builder/)

```java
import lombok.Builder;

@Builder
public class Person {
    private final String firstName;
    private final String lastName;
    private final String middleName;
}
```

The following configuration is wrong.
```java
@Builder
public final class Student extends Person {
    
    private final String rollNumber;
}
```

Instead we must set up which properties should be built by base class (`Person`).
```java
public final class Student extends Person {
    private final String rollNumber;

    @Builder(builderMethodName = "studentBuilder")
    public Student(final String firstName, 
                   final String lastName, 
                   final String middleName, 
                   final String rollNumber) {

        super(firstName, lastName, middleName);

        this.rollNumber = rollNumber;
    }
}

Student std = Student
              .studentBuilder()
              .firstNamer("John")
              .lastName("Mayer")
              .middleName("clapton")
              .rollNumber(12345678)
              .build()
```

## SuperBuilder 

[`.Builder()` method used by inheritance](https://stackoverflow.com/questions/44948858/lombok-builder-on-a-class-that-extends-another-class)  

```java
@SuperBuilder
public class Child extends Parent {
   private String a;
   private int b;
   private boolean c;
}

@SuperBuilder
public class Parent {
    private double d;
    private float e;
}

Child instance = Child.builder().b(7).e(6.3).build();
```
### three-tier hierarchy 
- [Advanced](https://www.baeldung.com/lombok-builder-inheritance)

```java
@Getter
@SuperBuilder
public class Parent {
    int parentAgd;
]
@Getter
@SuperBuilder
public class Child extends Parent {
    int childAge ;
}
@Getter
@SuperBuilder
public class Student extends Child {
    int studentAge;
}

Student std = Student.builder()
              .parentAge(55)
              .childAge(13)
              .studentAge(23)
              .build()
```
- Note that we have to annotate all classes with `@SuperBuilder`
- `@SuperBuilder` cannot be mixed with `@Builder` within the same class hierarchy. Doing so will result in a compilation error.

