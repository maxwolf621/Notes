# this type

[Typescript裡的This](https://zhuanlan.zhihu.com/p/104565681)   
[this-based type guards](https://www.typescriptlang.org/docs/handbook/2/classes.html#this-types)  
- [this type](#this-type)
  - [method returns `this`](#method-returns-this)
  - [this in method block](#this-in-method-block)
  - [`this` as type annotation](#this-as-type-annotation)
  - [`this : ClassName`](#this--classname)
  - [`this : { log : string}`](#this---log--string)
  - [`& ThisType<T>`](#-thistypet)
  - [Arrow Function returns `this` type](#arrow-function-returns-this-type)
    - [Object Type](#object-type)
    - [Class](#class)
  - [Prototype Function](#prototype-function)
  - [`this.method` is called by Base and Derived Class](#thismethod-is-called-by-base-and-derived-class)
  - [Derived Class calls method from Base Class with return `this` type](#derived-class-calls-method-from-base-class-with-return-this-type)
  - [Overridden Function](#overridden-function)
  - [`thisParameterType<typeof NAMED_FUNCTION>`](#thisparametertypetypeof-named_function)


## method returns `this`

`this` refers dynamically to the type of the current class.

```typescript
class B {
  contents: string = "";

  // set(value: string): this
  set(value: string) { 
    this.contents = value;
    return this; // type => this : this
  }
}

let b = new B()
console.log(b.set("new content"));

// Console prints
[LOG]: B: {
  "contents": "new content"
} 
```


## this in method block

Without `return`
```typescript
// this = itself 
let foo = {
  x: "hello",
  y: this, //this: typeof globalThis
  f(n: number) {
    this 
  },
}
```

`this` type is
```typescript 
this: {
    x: string;
    y: typeof globalThis;
    f(n: number): void;
}
```


with `return`
```typescript
let foo = {
  x: "hello",
  n : 0,
  f(n: number) {
    this.n = n;
    return this
  },
}
console.log(foo.f(123));
```

`this` types is
```typescript
/**
   this: {
      x: string;
      n: number;
      f(n: number): ...;
  }
*/
```

Console prints
```typescript
[LOG]: {
  "x": "hello",
  "n": 123
} 
```


## `this` as type annotation

**`xxx : this` is different from writing `xxx : Class`.**

`xxx : this` means `xxx` only accept the type `>=` itself. 

For example, if you have a derived class, its `sameAs` method **will now only accept other instances of that same derived class**. 
```typescript
class Box {
  content: string = "";

  sameAs(other: this) {
    return other.content === this.content;
  }
}
 
class DerivedBox extends Box {
  otherContent: string = "1";
}
class DerivedBox2 extends Box{
}
 
const base = new Box();
const derived = new DerivedBox();
const derived3 = new DerivedBox2();   

console.log(derived.sameAs(derived)); // true
console.log(derived.sameAs(derived3)); //true

// Compiler Error
// base does not have property of derived 
derived.sameAs(base);
        ^ Argument of type 'Box' is not assignable to parameter of type 'DerivedBox'.
        ^ Property 'otherContent' is missing in type 'Box' but required in type 'DerivedBox'.
```     


But if Base has its derived class's properties.
```typescript
class Box {
  content: string = "";

  // derived class's properties
  otherContent : string = "love";

  sameAs(other: this) {
    return other.content === this.content;
  }
}

class DerivedBox extends Box {

  otherContent: string = "1";
}

// derived class
const base = new Box();
const derived = new DerivedBox();

console.log(derived.sameAs(base)); // true
```

## `this : ClassName`

```typescript
class MyClass {
  name = "MyClass";
  age = 10;
  sex = "male";
  getName(this: MyClass) {
    return this.name;
  }
  getAllField(this : MyClass){
    return this; 
  }
}
const c = new MyClass();
console.log(c.getName());
console.log(c.getAllField());

// console prints
[LOG]: "MyClass" 

[LOG]: MyClass: {
  "name": "MyClass",
  "age": 10,
  "sex": "male"
} 
```

via object type
```typescript
const obj  = {
  name : "objType",
  age : 10,
  sex : "male",
  getName() {
    return this // { name:string, getName():string}
  },
  getAllField(){
    return this; 
  }
}
console.log(obj.getAllField())

//console prints
[LOG]: {
  "name": "objType",
  "age": 10,
  "sex": "male"
} 
```

## `this : { log : string}`

`this : { log : string}` returns current value of each field

```typescript
type Point = {
  x: number
  y: number
  moveBy(dx: number, dy: number): void
}

let p: Point = {
  x: 10,
  y: 20,
  moveBy(this : { log : string, x : number , y : number} ,dx, dy) {
    this.x += dx;
    this.y += dy;

    console.log(this);
  }
}

p.moveBy(10,20);
```

Console Prints
```typescript
[LOG]: {
  "x": 20,
  "y": 50
} 
```

or just
```typescript
type Point = {
  x: number
  y: number
  moveBy(dx: number, dy: number): void
}

let p: Point = {
  x: 10,
  y: 20,
  moveBy(dx, dy) {
    this.x += dx;
    this.y += dy
    console.log(this);
  },
}
```
## `& ThisType<T>`

```typescript 
type Point = {
  x: number
  y: number
  moveBy: (dx: number, dy: number) => void
} & ThisType<{ message: string, x : number , y : number }>

let p: Point = {
  x: 10,
  y: 20,
  moveBy(dx, dy) {
    this.x += dx;
    this.y += dy;
    console.log(this)
  },
}
p.moveBy(2,2)
console.log(`p.x : ${p.x}`)
console.log(`p,y : ${p.y}`)
```

## Arrow Function returns `this` type

### Object Type 
```typescript
// object type 
const obj2 = {
  name: "myObject",
  getName: () => {
    return this.name
            ^ Property 'name' does not exist 
            ^ on type 'typeof globalThis'.(2339)
  },
}
obj2.getName() // runtime error 
```

### Class 
```typescript
// class
class MyClass {
  name = "MyClass";
  getName = () => {
    return this.name;
    // return this : return MyClass Type
  };
}
const c = new MyClass();
const g = c.getName;

// Prints "MyClass"
console.log(g());
```
- `const g = c.getName` uses more memory, because each class instance will have its **own copy of each function** defined this way.
- You also can't use `super.getName` in a derived class, because there’s no entry in the prototype chain to fetch the base class method from


## Prototype Function

```typescript
interface People {
  name: string
  getName(): string
}
interface PeopleConstructor {
  // constructor(name : string) : People
  new (name: string): People
  prototype: People 
}
```

```typescript
function People(name: string) {
  this.name = name // check error
}
People.prototype.getName = function() {
  return this.name
}

const people = new People() // check error
```

```typescript
const ctor = (
  function(this: People, name: string) {
  this.name = name
} as unknown) as PeopleConstructor // Two times type-assertion

ctor.prototype.getName = function() {
  return this.name
}

const people = new ctor("yj")
console.log("people:", people)
console.log(people.getName())
```


```typescript
class Test {
  name = 1
  
  namedFunction() {
    return this.name
  }
  anonymousFunction = function() {
    return this.name // complier error (this is any)
  }
  arrowFunction = () => {
    // create new instance each call 
    return this.name
  }
}

const test = new Test()

console.log(test.namedFunction()) // 1
console.log(test.anonymousFunction()) // 1
console.log(test.arrowFunction()) // 1
```

## `this.method` is called by Base and Derived Class
在處理繼承的時候，如果Base類別透過this呼叫instance member function `(anonymous function)`而非原型方法，Derived類別無法透過Base的this呼叫該function被Derived類別Override後的操作,例如
```typescript
class Parent {
  constructor() {
    this.setup()
  }
  setup = () => {
    console.log("parent")
  }
}

class Child extends Parent {
  constructor() {
    super()
  }
  setup = () => {
    console.log("child")
  }
}

// 無法透過base的this呼叫被overridden的function
const child = new Child() // parent
```


```typescript
class Parent {
  constructor() {
    this.setup()
  }

  setup() {
    console.log("parent")
  }
}

class Child extends Parent {
  /*
  constructor() {
    super()
  }
  */

  setup() {
    console.log("child")
  }
}

const child2 = new Child2() // child
```

## Derived Class calls method from Base Class with return `this` type

```typescript
class A {
  A1() {
    return this
  }
  A2() {
    return this
  }
}
class B extends A {
  B1() {
    return this
  }
  B2() {
    return this
  }
}

const b = new B()
const a = new A()
b.A1().B1() // A.A1:B

// Base is not allow to call method from 
a.A1().B1() 
        ^ Property 'B1' does not exist on type 'A'.(2339)

type M1 = ReturnType<typeof b.A1> // B
type M2 = ReturnType<typeof a.A1> // A

b.A1().B1().A2().B2() 
```

## Overridden Function

```typescript
class Parent {
  name: string = "default"
  constructor(name ?: string){
    if(name != undefined){
      this.name = name;
    }
  }
}

class Child extends Parent {
  age?: number
  constructor(name ?: string, age ?: number){
    super(name);
    this.age = age;
  }
}


class A {
  filedA = "ImFiledA"
  A1() {
    return this.A2(new Parent())
  }
  A3(arg: string) : string {
    return "A.A3";
  }
  A2(arg: Parent) {
    console.log(arg);
    console.log(this);
  }
}

class B extends A {

  filedB = "ImFieldB";

  A1() {
    return this.A2(new Parent())
  }
  // Allow base/derived type
  A2(arg: Child) {
    super.A2(arg)
  } 
}


let a = new A();
let b = new B();

a.A2(new Parent("ImParentField"));
b.A2(new Parent("ImChildField"));
b.A2(new Child()) // if optional ? is not declare then compiler error
b.A2(new Child("ImChildField", 1234))
```

## `thisParameterType<typeof NAMED_FUNCTION>`

`thisParameterType` Extracts the type of the `this` parameter of a function type or `unknown` if the function type has no `this` parameter.

```typescript
interface People {
  name: string
}
function ctor(this: People) {}

type ThisArg = ThisParameterType<typeof ctor> 
```