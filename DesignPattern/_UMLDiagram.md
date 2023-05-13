###### tags : `Design Pattern`
# UML diagram

- [UML diagram](#uml-diagram)
  - [UML](#uml)
  - [Association and Dependency](#association-and-dependency)
  - [Composition and Aggregation](#composition-and-aggregation)
      - [Strong/Weak Association](#strongweak-association)
  - [Implementation \& Inheritance](#implementation--inheritance)
  - [Degree of Coupling](#degree-of-coupling)

References
- [Introduction of UML](https://spicyboyd.blogspot.com/2018/07/umlclass-diagram-relationships.html)  
- [Composition and aggregation](https://www.tutorialspoint.com/difference-between-composition-and-aggregation)  


## UML

![image](https://user-images.githubusercontent.com/68631186/126488408-65750f74-b5f5-431e-8a81-0fda94b6f206.png)  
| dotted line arrow | solid line arrow |
| ----------------- | ---------------- |
| (decoupled) you USE it        | (Coupled) you HAVE it |

## Association and Dependency

```plantuml
class Customer{
        - address : String
        - contactNumber : String
        - name : String
}
class Car{
        - modelNumber : String
        - owner : Customer
}
Car --> "owner" Customer
```

```plantuml
class Order{
    + paymentProduce(Payment payment) : void
}
class Payment{
}
Order ..> Payment
```

[According to this Post](https://stackoverflow.com/questions/1230889/difference-between-association-and-dependency)   
- An association almost always implies that one object has the other object as a **field/property/attribute** (terminology differs).  
- **A dependency typically (but not always) implies that an object accepts another object as a method parameter, instantiates, or uses another object.** 

>>> A dependency is very much implied by an association.  

## Composition and Aggregation

**Composition and aggregation are two types of association** which is used to represent relationships between two classes.  

In Aggregation , **parent and child entity maintain `Has-A` relationship but both can also exist independently**.   
Any modification in the parent entity will not impact the child entity or vice versa. 

Which means aggregation's Children can have their own life time   


```plantuml
class Car{
    - engine : Engine
    - door : Door
    - wheel : Wheel
}
Car o.left. "4" Wheel
Car o.right. "4" Door
Car o.. "1" Engine
```

In Composition, **parent owns child entity so child entity can't exist without parent entity**. We can’t directly or independently access child entity.   
In the UML diagram, composition is denoted by a filled diamond.   

**Composition's Children don't have their own life time (they are dependent on parent side)**   
```plantuml
class Hand{
    
}
Hand *-- finger
```

> - Composition(MIXTURE) is a way to wrap simple objects or data types into a single unit   
> - Aggregation(COLLECTION) differs from ordinary composition in that it does not imply ownership    


#### Strong/Weak Association

![image](https://user-images.githubusercontent.com/68631186/126583718-b070baaa-2090-4b58-9c3b-1da935138176.png)
- Composition is a strong association  
- Aggregation is a weak association   

## Implementation & Inheritance

Child `uses`/implements parent's methods  
![image](https://user-images.githubusercontent.com/68631186/126583945-e7bb0a51-86a1-42a1-8070-f608579dd95a.png)  

Child `has`/inherits parent's methods  
![image](https://user-images.githubusercontent.com/68631186/126583992-3c0bc38c-37d2-4e5b-b353-f80047bd9b88.png) 


## Degree of Coupling 

![image](https://user-images.githubusercontent.com/68631186/126583854-150d63bc-3bd5-4dd1-b0fe-7f7138de1e71.png)
