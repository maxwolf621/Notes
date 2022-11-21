# UML diagram

- [UML diagram](#uml-diagram)
  - [Association and Dependency](#association-and-dependency)
  - [Composition and Aggregation](#composition-and-aggregation)
      - [Life cycle](#life-cycle)
      - [Strong/Weak Association](#strongweak-association)
  - [Realization Implementation vs Inheritance](#realization-implementation-vs-inheritance)
  - [Degree of Coupling](#degree-of-coupling)

References
- [Introduction of UML](https://spicyboyd.blogspot.com/2018/07/umlclass-diagram-relationships.html)  
- [Composition and aggregation](https://www.tutorialspoint.com/difference-between-composition-and-aggregation)  

---

![image](https://user-images.githubusercontent.com/68631186/126488408-65750f74-b5f5-431e-8a81-0fda94b6f206.png)  
| dotted line arrow | solid line arrow |
| ----------------- | ---------------- |
| you USE it        | you HAVE it |

## Association and Dependency

Association : HAVE the object
![image](https://user-images.githubusercontent.com/68631186/126490747-f2af8080-a6e5-45d9-b948-3e299086dd18.png)

Dependency : USE the object
![image](https://user-images.githubusercontent.com/68631186/126490824-cc757e91-15d9-4cc8-a912-91e6f7960e62.png)

[According to this Post](https://stackoverflow.com/questions/1230889/difference-between-association-and-dependency)   
- An association almost always implies that one object has the other object as a **field/property/attribute** (terminology differs).  
- **A dependency typically (but not always) implies that an object accepts another object as a method parameter, instantiates, or uses another object.** 

>>> A dependency is very much implied by an association.  

## Composition and Aggregation

**Composition and aggregation are two types of association** which is used to represent relationships between two classes.  

In Aggregation , **parent and child entity maintain `Has-A` relationship but both can also exist independently**. 

We can use parent and child entity independently. Any modification in the parent entity will not impact the child entity or vice versa. 

![image](https://user-images.githubusercontent.com/68631186/126583667-5f2562b1-add7-4e2e-a265-ac0a490efdc5.png)

In Composition, **parent owns child entity so child entity can't exist without parent entity**. We can’t directly or independently access child entity.   
In the UML diagram, composition is denoted by a filled diamond.   
![image](https://user-images.githubusercontent.com/68631186/126583753-714eed3e-417f-454f-af68-47ce310719a4.png)


Basic 
Composition(mixture) is a way to wrap simple objects or data types into a single unit   

 Aggregation(collection) differs from ordinary composition in that it does not imply ownership  

#### Life cycle   

**Composition's Children don't have their own life time (they are dependent on parent side)**   

Aggregation's Children can have their own life time   

#### Strong/Weak Association

![image](https://user-images.githubusercontent.com/68631186/126583718-b070baaa-2090-4b58-9c3b-1da935138176.png)
- Composition is a strong association  
- Aggregation is a weak association   

## Realization Implementation vs Inheritance

Child `uses`/implements parent's methods  
![image](https://user-images.githubusercontent.com/68631186/126583945-e7bb0a51-86a1-42a1-8070-f608579dd95a.png)  

Child `has`/inherits parent's methods  
![image](https://user-images.githubusercontent.com/68631186/126583992-3c0bc38c-37d2-4e5b-b353-f80047bd9b88.png) 


## Degree of Coupling 

![image](https://user-images.githubusercontent.com/68631186/126583854-150d63bc-3bd5-4dd1-b0fe-7f7138de1e71.png)
