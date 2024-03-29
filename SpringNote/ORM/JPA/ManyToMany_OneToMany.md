# OneToMany vs ManyToMany
- [OneToMany vs ManyToMany](#onetomany-vs-manytomany)
  - [unidirectional and bidirectional ManyToOne/OneToMany](#unidirectional-and-bidirectional-manytooneonetomany)
  - [ManyToMany](#manytomany)

[Ref](https://stackoverflow.com/questions/3113885/difference-between-one-to-many-many-to-one-and-many-to-many)   


One-to-many and Many-to-one is a matter of perspective.   
Unidirectional and Bidirectional will not affect the mapping but will make difference on how you can access your data.
- *In Many-to-one the many(child) side will keep reference of the one(parent) side.*   

A good example is A State has Cities.     
In this case State is the one side and City is the many side. 
There will be a column `state_id`(FK) in the table cities.

## unidirectional and bidirectional ManyToOne/OneToMany 

In unidirectional, Person class will have `List<Skill> skills` but Skill will not have `Person person`.
```java
public class Person{
    //...
    @OneToMany
    private List<Skill> skills;
}
public class Skill{
    //...
}
```

In bidirectional, both properties are added and it allows you to access a Person given a skill( i.e. `skill.person`).   
```java
public class Person{
    //...
    @OneToMany
    private List<Skill> skills;
}
public class Skill{
    //...
    @ManyToOne
    private Person person;
}
```
- In One-to-Many the one(parent) side will be our point of reference (to be referenced).    

## ManyToMany

In Many-to-Many **members of each party can hold reference to arbitrary number of members of the other party.**  
![圖 1](../images/f1f82219830d04ccad29fad8571f977b690dcfe62218a94d78d2c138f87c30d7.png)  
- **To achieve this a look up table (join table) is used.**   

Example for this is the relationship between doctors and patients.
-  `A doctor can have many patients and vice versa.`