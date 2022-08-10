# this type

[this-based type guards](https://www.typescriptlang.org/docs/handbook/2/classes.html#this-types)
- [this type](#this-type)
    - [as type annotation](#as-type-annotation)
    - [`this` at Runtime in Classes (javascript peculiar runtime behaviors)](#this-at-runtime-in-classes-javascript-peculiar-runtime-behaviors)
  - [Arrow Functions instead of `function fn(this.field, ...)`](#arrow-functions-instead-of-function-fnthisfield-)

this refers dynamically to the type of the current class. Let’s see how this is useful
```typescript
class Box {
  contents: string = "";
  set(value: string) {
  
(method) Box.set(value: string): this
    this.contents = value;
    return this;
  }
}
```

### as type annotation

```typescript
class Box {
  content: string = "";
  sameAs(other: this) {
    return other.content === this.content;
  }
}
```
This is different from writing `other: Box` — if you have a derived class, its `sameAs` method will now only accept other instances of that same derived class:
```typescript
class Box {
  content: string = "";
  sameAs(other: this) {
    return other.content === this.content;
  }
}
 
class DerivedBox extends Box {
  otherContent: string = "?";
}
 
const base = new Box();
const derived = new DerivedBox();
derived.sameAs(base);
        ' Argument of type 'Box' is not assignable to parameter of type 'DerivedBox'.
          Property 'otherContent' is missing in type 'Box' but required in type 'DerivedBox'.

const derived2 = new DerivedBox();
derived.sameAs(derived2);
```


### `this` at Runtime in Classes (javascript peculiar runtime behaviors)

by default, the value of `this` inside a function depends on how the function was called. 

In this example, because the function was called through the obj reference, its value of this was `obj` rather than the class instance.
```typescript
class MyClass {
  name = "MyClass";
  getName() {
    return this.name;
  }
}

const c = new MyClass();
const obj = {
  name: "obj",
  getName: c.getName,
};
// Prints "obj", not "MyClass"
console.log(obj.getName());
```
This is rarely what you want to happen! TypeScript provides some ways to mitigate or prevent this kind of error.

## Arrow Functions instead of `function fn(this.field, ...)`

**If you have a function that will often be called in a way that loses its this context**, it can make sense to use an arrow function property instead of a method definition:

This has some trade-offs權衡:
- The `this` value is guaranteed to be correct at runtime, even for code not checked with TypeScript
- This will use more memory, because each class instance will have its own copy of each function defined this way
- You can’t use super.getName in a derived class, because there’s no entry in the prototype chain to fetch the base class method from `this` parameters

**In a method or function definition, an initial parameter named `this` has special meaning in TypeScript.** 
**These parameters are erased during compilation:**
```typescript
// TypeScript input with 'this' parameter
function fn(this: SomeType, x: number) {
  /* ... */
}

// JavaScript output
function fn(x) {
  /* ... */
}
```
TypeScript checks that calling a function with a this parameter is done so with a correct context.  

Instead of using an arrow function, we can add a this parameter to method definitions to statically enforce that the method is called correctly:
```java
class MyClass {
  name = "MyClass";
  
  getName(this: MyClass) {
    return this.name;
  }
}
const c = new MyClass();

// OK
c.getName();
 
// Error, would crash
const g = c.getName;
console.log(g());
        ' The 'this' context of type 'void' is not assignable to method's 'this' of type 'MyClass'.
```

This method makes the opposite trade-offs of the arrow function approach:
- JavaScript callers might still use the class method incorrectly without realizing it
- Only one function per class definition gets allocated, rather than one per class instance
- Base method definitions can still be called via super.


