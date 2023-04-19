# Class 
- [Class](#class)
  - [Class Expressions](#class-expressions)
  - [Field of Class](#field-of-class)
    - [field with readOnly](#field-with-readonly)
  - [constructor](#constructor)
  - [Accessor](#accessor)
  - [Index Signatures](#index-signatures)
  - [implements Class](#implements-class)
    - [pitfalls](#pitfalls)
      - [Always add type annotation of parameter in override function](#always-add-type-annotation-of-parameter-in-override-function)
      - [Implementing an interface with an optional property doesn't create that property](#implementing-an-interface-with-an-optional-property-doesnt-create-that-property)
    - [override](#override)
  - [Initialization Order](#initialization-order)
  - [Abstract Class](#abstract-class)
  - [Cross-hierarchy protected access](#cross-hierarchy-protected-access)
  - [Cross-instance private access](#cross-instance-private-access)
    - [Caveats](#caveats)
  - [Abstract Construct Signatures](#abstract-construct-signatures)
  - [Relationships Between Classes](#relationships-between-classes)

[Type-only Field Declarations](https://www.typescriptlang.org/docs/handbook/2/classes.html#type-only-field-declarations)   
[Inheriting Built-in Types, version below ES6/ES2015](https://www.typescriptlang.org/docs/handbook/2/classes.html#inheriting-built-in-types)   
[Member Visibility](https://www.typescriptlang.org/docs/handbook/2/classes.html#member-visibility)   
 
## Class Expressions

Class expressions are very similar to class declarations.   
The only real difference is that class expressions don’t need a name, though we can refer to them via whatever identifier they ended up bound to:
```typescript
const someClass = class<Type> {
  content: Type;
  constructor(value: Type) {
    this.content = value;
  }
};
 
const m = new someClass("Hello, world"); // const m: someClass<string>;
``` 
## Field of Class

```typescript
class OKGreeter {
  // Not initialized, but no error
  name!: string;
}
```

### field with readOnly

This prevents assignments to the field outside of the constructor.

```typescript
class Greeter {
  readonly name: string = "world";
 
  constructor(otherName?: string) {
    if (otherName !== undefined) {
      this.name = otherName;
    }
  }
 
  err() {
    this.name = "not ok";
        ' Cannot assign to 'name' because it is a read-only property.
  }
}
const g = new Greeter();
g.name = "also not ok";
    'Cannot assign to 'name' because it is a read-only property.

const c = new Greeter("hello");
console.log(c.name); // hello
```
## constructor

No type parameter and return type annotation allowing 
```typescript
class Point {
  // Overloads
  constructor(x: number, y: string);
  constructor(s: string);
  constructor(xs: any, y?: any) {
    // TBD
  }
}
```

TypeScript offers special syntax for turning a constructor parameter into a class property (field) with the same name and value. 
- These are called parameter properties and are created by prefixing a constructor argument with one of the visibility modifiers `public`, `private`, `protected`, or `readonly`. 
```typescript
class Params {
  constructor(
    public readonly x: number,
    protected y: number,
    private z: number
  ) {
    // No body necessary
  }
}
const a = new Params(1, 2, 3);
console.log(a.x);
```

## Accessor

TypeScript has some special inference rules for accessors:
- If `get` exists but no `set`, the property is automatically `readonly`
- If the type of the setter parameter is not specified, it is inferred from the return type of the getter
- Getters and setters must have the same Member Visibility

```typescript
class Thing {
  _size = 0;
 
  get size(): number {
    return this._size;
  }
 
  set size(value: string | number | boolean) {
    let num = Number(value);
 
    // Don't allow NaN, Infinity, etc
 
    if (!Number.isFinite(num)) {
      this._size = 0;
      return;
    }
 
    this._size = num;
  }
}
```

## Index Signatures

Because the index signature type needs to also capture the types of methods, it’s not easy to usefully use these types. 

**Generally it’s better to store indexed data in another place instead of on the class instance itself.**
```typescript 
class MyClass {
  [s: string]: boolean | ((s: string) => boolean);
 
  check(s: string) {
    return this[s] as boolean;
  }
}
```

## implements Class

allowing multiple implementing
```typescript
class Animal {
    public name: string;
    constructor(name: string) {
        this.name = name;
    }    
    sayHi() {
        return `My name is ${this.name}`;
    }
}

interface Meow{
    sayMeow(): any;
}
interface Hi{
    sayHi() :any;
}
class Cat extends Animal implements Meow, Hi{
    constructor(name: string) {
        super(name); 
        console.log(this.name);    
    }

    // Implementations
    sayHi() {
        return 'Meow, ' + super.sayHi(); 
    }
    sayMeow(){
        return 'Meow~';
    }
}

let c = new Cat('Tom'); // Tom
console.log(c.sayHi()); // Meow, My name is Tom
```

### pitfalls 

#### Always add type annotation of parameter in override function

Here we may assume `s`'s type would be influenced by the `name : string` parameter of check, but it is not.
```typescript
interface Checkable {
  check(name: string): boolean;
}
 
class NameChecker implements Checkable {
  check(s) {
        ^ Parameter 's' implicitly has an 'any' type.
        
    // Notice no error here
    // It could be any type
    return s.toLowercase() === "ok";
  }
}
```
#### Implementing an interface with an optional property doesn't create that property

```java
interface A {
  x: number;
  y?: number;
}
class C implements A {
  x = 0;
}
const c = new C();
c.y = 10;
  ^ Property 'y' does not exist on type 'C'.    
```

### override 

```typescript
class Base {
  greet() {
    console.log("Hello, world!");
  }
}
 
class Derived extends Base {
  greet(name?: string) {
    if (name === undefined) {
      super.greet();
    } else {
      console.log(`Hello, ${name.toUpperCase()}`);
    }
  }
}
 
// Derived
const d = new Derived();
d.greet("mother");

// Base 
const b : Base = d;
b.greet("love");
        ^ Expected 0 arguments, but got 1.(2554)
```

## Initialization Order

The order of class initialization, as defined by JavaScript, is:
1. The base class fields are initialized
2. The base class constructor runs
3. The derived class fields are initialized
4. The derived class constructor runs

## Abstract Class

An abstract method or abstract field is one that hasn’t had an implementation provided. 

These members must exist inside an abstract class, **which cannot be directly instantiated**.

```typescript
abstract class Animal {
    
    public name: string;
    
    constructor(name: string) {
        this.name = name;
    }    
    
    sayHi() {
        return `My name is ${this.name}`;
    }
}
class Cat extends Animal{
    constructor(name: string) {
        super(name); 
        console.log(this.name);    
    }
    // override 
    sayHi() {
        return 'Meow, ' + super.sayHi();  
    }
    sayMeow(){
        return 'Meow~';
    }
}
let c = new Cat('Tom'); // Tom
console.log(c.sayHi()); // Meow, My name is Tom
```

## Cross-hierarchy protected access

```typescript
class Base {
  protected x: number = 1;

  valueX(){
    return this.x;
  }
}


class Derived1 extends Base {
  protected x: number = 5;

}

const d = new Derived1;
const b = new Base;

console.log(b.valueX()); //1
console.log(d.valueX()); //5
```

---

```typescript
class Base {
  protected x: number = 1;
}
class Derived1 extends Base {
  protected x: number = 5;
}
class Derived2 extends Base {
  f1(other: Derived2) {
    other.x = 10;
  }
  f2(other: Base) {
    other.x = 10;
          ^ Property 'x' is protected and only accessible 
            through an instance of class 'Derived2'. 
            This is an instance of class 'Base'.
  }
}
```
- Java, for example, considers this to be legal. On the other hand, C# and C++ chose that this code should be illegal.  
TypeScript sides with C# and C++ here, because **accessing `x` in `Derived2` should only be legal from `Derived2`’s subclasses**, and `Derived1` isn't one of them.   
Moreover, if accessing `x` through a `Derived1` reference is illegal, then accessing it through a base class reference should never improve the situation.

## Cross-instance private access

```typescript
class A {
  private x = 10;
 
  public sameAs(other: A) {
    // No error
    return other.x === this.x;
  }
}
```

### Caveats

`private` and `protected` are only enforced during type checking.     
If you need to protect values in your class from malicious actors, you should use mechanisms that offer hard runtime privacy, such as closures, WeakMaps, or `private` fields. 

**Note that these added privacy checks during runtime could affect performance**

## Abstract Construct Signatures
 
Sometimes you want to accept some class constructor function that produces an instance of a class which derives from some abstract class.
```typescript
abstract class Base {
  abstract getName(): string;
  printName() {
    console.log("Hello, " + this.getName());
  }
}

// instead  
class Derived extends Base {
  getName() {
    return "world";
  }
}

function greet(ctor: typeof Base) {
  const instance = new ctor();
                        ' Cannot create an instance of an abstract class.
  instance.printName();
}
```

TypeScript is correctly telling you that you’re trying to instantiate an abstract class. After all, given the definition of greet, it’s perfectly legal to write this code, which would end up constructing an abstract class:
```typescript
// Bad!
greet(Base);
```

Instead, you want to write a function that accepts something with a construct signature:
```typescript
function greet(ctor: new () => Base) {
  const instance = new ctor();
  instance.printName();
}

greet(Derived);
greet(Base);
      ' Argument of type 'typeof Base' is not assignable to parameter of type 'new () => Base'.
        Cannot assign an abstract constructor type to a non-abstract constructor type.
```

## Relationships Between Classes

In most cases, classes in TypeScript are compared structurally, the same as other types.

```typescript
class Point1 {      class Point2 {
  x = 0;              x = 0;
  y = 0;              y = 0;
}                   }
 
const p: Point1 = new Point2(); // OK

class Employee {                    class Person {                            
  name: string;                       name: string;                                   
  age: number;                        age: number;                            
  salary: number;                   }                               
}                                   

// OK
const p: Person = new Employee();
```

