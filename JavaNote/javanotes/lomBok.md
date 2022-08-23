# LomBok
[kucw java-lombok](https://kucw.github.io/blog/2020/3/java-lombok/)

- [LomBok](#lombok)
  - [Maven](#maven)
  - [Annotations](#annotations)
    - [@ToString](#tostring)
    - [@EqualAndHashCode](#equalandhashcode)
  - [@NoArgsConstructor](#noargsconstructor)
  - [@AllArgsConstructor](#allargsconstructor)
  - [@RequiredArgsConstructor](#requiredargsconstructor)
  - [@Data](#data)
  - [@Value](#value)
  - [@Builder](#builder)
  - [@Slf4j](#slf4j)
  - [Making Multiple @Builders Coexist](#making-multiple-builders-coexist)
  - [SuperBuilder](#superbuilder)
    - [three-tier hierarchy](#three-tier-hierarchy)

`LomBok` makes code elegant  
for example 
![](https://i.imgur.com/inZrWpM.png)  

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

this will exclude attribute `id` from `equal` and `HashCode`
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
// ...
public User(){}
```

##  @AllArgsConstructor  
equals
```java
//..
public User(id, name){
    this.id = id;
    this.name = name;
}
```

If we dont create a constructor, java will create a No args Constructor by itself. 
so do make sure add annotation `@NoArgsConstructor` while there is `@AllArgsConstrcutor`  

## @RequiredArgsConstructor

set the required attribute as `final` type 
![](https://i.imgur.com/hcyzMlD.png)

```java
public User(id, name){
    this.id = id;
}
```

## @Data

`@Data` represents
> `@Getter/@Setter`, `@ToString`, `@EqualsAndHashCode`, `@RequiredArgsConstructor`

## @Value

The annotation gives the Attributes with `final` keyword

- `@Data` 適合用在 **POJO 或 DTO**上，而這個 `@Value` 注解，則是適合加在值不希望被改變的類上，像是某個類的值當創建後就不希望被更改，只希望我們讀它而已，就適合加上 `@Value` 注解
- lombok 的注解 `@Value` 和另一個 Spring 的注解 `@Value` 撞名  


## @Builder

To represent with setters 
![](https://i.imgur.com/P9u4632.png)

## @Slf4j

Log the console's information

![](https://i.imgur.com/rGbxUUo.png)

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

The following Configuration is wrong
```java
@Builder
public final class Student extends Person {

    private final String rollNumber;
}

```

We must set up which properties should be built by Base
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

Student std = Student
              .builder()
              .parentAge(55)
              .childAge(13)
              .studentAge(23)
              .build()
```
- Note that we have to annotate all classes.  
`@SuperBuilder` cannot be mixed with `@Builder` within the same class hierarchy. Doing so will result in a compilation error.

