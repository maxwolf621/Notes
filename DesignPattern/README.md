###### tags: `Design Pattern`

# Design Pattern

- [Design Pattern](#design-pattern)
  - [Basics](#basics)
  - [Principle](#principle)
  - [Creational Pattern](#creational-pattern)
  - [Structural Pattern](#structural-pattern)
  - [Behavioral Pattern](#behavioral-pattern)

## Basics
* [UML Diagram](_UMLDiagram.md)
* [深入淺出依賴反向原則](https://www.jyt0532.com/2020/03/24/dip/)
* [Dependency Injection](_DependencyInjection.md)

## Principle
- [:star:Principle](_Principle.md)
  - SRP(Single Responsibilities Principle)
  - ISP(Interface Segregation Principle)
  - DIP(Dependence Inversion Principle)
  - LSK(Liskov Substitution Principle)
  - OCP(Open Close Principle)
  - CARP Composite/Aggregate Reuse Principle
  - LKP	Least Knowledge Principle/ DP (Demeter Principle)

## Creational Pattern

Creational patterns provide various object creation mechanisms, which increase flexibility and reuse of existing code.  

This Pattern is concerned with the way of creating objects. to avoid creating a object like such way  
```java
ObjectX s1 = new ObjectX();  
```

- [Factory](C_Factory.md)
- ***[Abstract Factory](C_AbstractFactory.md)***
- ***[Singleton](C_Singleton.md)***
- ***[Builder](C_Builder.md)***
- **[Prototype](C_Prototype.md)**

## Structural Pattern
Structural design patterns are concerned with how classes and objects can be composed, to form larger structures.  

**These patterns focus on, how the classes inherit from each other and how they are composed from other classes.**

- ***[Adapter](S_Adapter.md)***
- [Bridge](S_Bridge.md) 
- [Facade](S_Facade.md)
- [Composite](B_Composite.md)
- [Flyweight](S_Flyweight.md)
- **[Proxy](S_Proxy.md)**
- **[Decoration](S_Decoration.md)**

## Behavioral Pattern

In these design patterns, the interaction between the objects should be in such a way that they can easily talk to each other and still should be loosely coupled.   

That means **the implementation and the client should be loosely coupled in order to avoid hard coding and dependencies.**   
  
- [Iterator](B_Iterator.md)
- [Command](B_Command.md)
- [Strategy](B_Strategy.md)
- **:star:[Observer](B_Observer.md)**
- [Memento](B_Memento.md)  
- **[Template](B_Template.md)**
- [State](B_State.md)
- **:star:[Chain of Responsibility](B_ChainOfResponsibility.md)**
- [Interpreter]
- [Mediator](B_Mediator.md)
- **[Null Object](B_NullObject.md)**
- [Visitor](B_Visitor.md)
