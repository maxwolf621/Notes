# Type VS Class

Typescript has two different universes that come into contact in some points: Value space and Type space.   
- **Type space is where types are defined and types get erased completely and don't exist at runtime.** 
- Value space contains values and will obviously exist at runtime.  

## What is a value? 
Value literals, variables, constants and parameters are obviously values.    
Functions and class declarations are also values as they do have a runtime object backing them up, namely the function object and the class constructor (also a function).   
Enums are also values as they are backed up by an object at runtime.   

## What is a type? 

Any definition with a `type` keyword is a type as well as interfaces, class declarations and enums

Classes exist in both type space, and value space. 

This is why we can use them both in type annotations (let foo: ClassName) and in expressions (ex new ClassName()).

Enums also span both worlds, they also represent a type we can use in an annotation, but also the runtime object that will hold the enum.

Names in type space and value space don't collide, this is why we can define both a type and a variable with the same name:
```java
type Foo = { type: true }
var Foo = { value : true } // No error, no relation to Foo just have the same name in value space 
```

Class declarations and enums, since they span both spaces will 'use up' the name in both spaces and thus we can't define a variable or a type with the same name as a class declaration or enum (although we can do merging but that is a different concept)

Here `Point` is just a type, something we can use in type annotations, not something we can use in expressions that will need to have a runtime presence. 

In this case the type is useful as it allows the compiler to structurally check that the object literal is assignable to the Point type:
```typescript
let p: Point = { x: 10, y: 15 }; // OK
let p: Point = { x: 10, y: 15, z: 10 }; // Error
```

If you want to create a class, you will need to do that with the `class` keyword, as that will create a **runtime value** that is not just a type:
```typescript 
class Point{
    constructor(public x: number, public y: number){}
}
let p = new Point(10,10)
```