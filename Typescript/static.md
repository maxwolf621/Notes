# static

- [static](#static)
  - [static member](#static-member)
  - [static names can't be used](#static-names-cant-be-used)
  - [Why No Static Classes?](#why-no-static-classes)
  - [Blocks in Classes](#blocks-in-classes)
## static member

- static member can use public, protected, and private visibility modifiers
- Static members are also inherited
```typescript
class MyClass {
  static x = 0;
  static printX() {
    console.log(MyClass.x);
  }
}

console.log(MyClass.x);
MyClass.printX();

----

class MyClass {
  private static x = 0;
}
console.log(MyClass.x);

---

class Base {
  static getGreeting() {
    return "Hello world";
  }
}
class Derived extends Base {
  myGreeting = Derived.getGreeting();
}
```


## static names can't be used

certain static names can’t be used. Function properties like name, length, and call aren’t valid to define as static members:
```java
class S {
  static name = "S!";
         ' Static property 'name' conflicts with built-in property 'Function.name' of constructor function 'S'.
}
```

## Why No Static Classes?
TypeScript (and JavaScript) don’t have a construct called static class the same way as, for example, C# does.

Those constructs only exist because those languages force all data and functions to be inside a class; because that restriction doesn’t exist in TypeScript, there’s no need for them. 

A class with only a single instance is typically just represented as a normal object in JavaScript/TypeScript.

For example, we don’t need a “static class” syntax in TypeScript because a regular object (or even top-level function) will do the job just as well:
```typescript 
// Unnecessary "static" class
class MyStaticClass {
  static doSomething() {}
}
 
// Preferred (alternative 1)
function doSomething() {}
 
// Preferred (alternative 2)
const MyHelperObject = {
  dosomething() {},
};
```

## Blocks in Classes

Static blocks allow you to write a sequence of statements with their own scope `{ ... }` that can access private fields within the containing class. 

**This means that we can write initialization code with all the capabilities of writing statements, no leakage of variables, and full access to our class’s internals.**
```typescript 
class Foo {
    static #count = 0;
 
    get count() {
        return Foo.#count;
    }
 
    static {
        try {
            const lastInstances = loadLastInstances();
            Foo.#count += lastInstances.length;
        }
        catch {}
    }
}
```

