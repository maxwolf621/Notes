# this type

[Typescript裡的This](https://zhuanlan.zhihu.com/p/104565681)   
[this-based type guards](https://www.typescriptlang.org/docs/handbook/2/classes.html#this-types)  
- [this type](#this-type)
  - [return this in class](#return-this-in-class)
  - [this as type annotation in method](#this-as-type-annotation-in-method)
  - [named funciton of object type vs class return this](#named-funciton-of-object-type-vs-class-return-this)
  - [this for Arrow function (object Type & Class)](#this-for-arrow-function-object-type--class)
  - [function contructor](#function-contructor)
  - [literal Type](#literal-type)
  - [literal Type with type annotation](#literal-type-with-type-annotation)
  - [`method(this : type)` in literal type](#methodthis--type-in-literal-type)
  - [`method(this : type )` in literal type with type annotation](#methodthis--type--in-literal-type-with-type-annotation)
  - [literal with type annotation `& ThisType<T>`](#literal-with-type-annotation--thistypet)
  - [`method(this : T)` in literal type](#methodthis--t-in-literal-type)
  - [`thisParameterType<typeof NAMED_FUNCTION>`](#thisparametertypetypeof-named_function)


## return this in class 

`this` refers dynamically to the type of the current class.
```typescript
class Box {
  contents: string = "";
  set(value: string) { // (method) Box.set(value: string): this
    this.contents = value;
    return this;
  }
}
```

## this as type annotation in method

```typescript
class Box {
  content: string = "";
  sameAs(other: this) {
    return other.content === this.content;
  }
}
```

It is different from writing `other: Box`.
for example, if you have a derived class, its `sameAs` method will now only accept other instances of that same derived class 
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
derived.sameAs(base);
        ' Argument of type 'Box' is not assignable to parameter of type 'DerivedBox'.
        ' Property 'otherContent' is missing in type 'Box' but required in type 'DerivedBox'.
```     


If Base has its derived class's properties
```typescript
class Box {
  content: string = "";
  otherContent : string = "love";
  sameAs(other: this) {
    return other.content === this.content;
  }
}

// derived class

const base = new Box();
const derived = new DerivedBox();
console.log(derived.sameAs(base)); // true
```

## named funciton of object type vs class return this
```typescript
// class
const obj = {
  name: "this.name",
  getName() {
    return this.name // { name:string, getName():string}
  },
}
console.log(obj.getName());

const fn1 = obj.getName();
fn1(); // Runtime Error
[ERR]: "Executed JavaScript Failed:" 
[ERR]: Cannot read properties of undefined (reading 'name') 


// Class
class MyClass {
  name = "MyClass";
  
  getName(this: MyClass) {
    return this.name;
  }
}
const c = new MyClass();
// OK
c.getName();
 
const g = c.getName;

// compiler erro instead of runtime error
console.log(g());
        ' The 'this' context of type 'void' is not assignable to method's 'this' of type 'MyClass'.
```

## this for Arrow function (object Type & Class)

```typescript
// object type 
const obj2 = {
  name: "myObject",
  getName: () => {
    return this.name
            ' Property 'name' does not exist on type 'typeof globalThis'.(2339)
  },
}
obj2.getName() // runtime error 

// class
class MyClass {
  name = "MyClass";
  getName = () => {
    return this.name;
  };
}
const c = new MyClass();
const g = c.getName;

// Prints "MyClass" instead of crashing
console.log(g());
```
- This will use more memory, because each class instance will have its own copy of each function defined this way
- You can’t use super.getName in a derived class, because there’s no entry in the prototype chain to fetch the base class method from
## function contructor

```typescript
function People(name: string) {
  this.name = name // check error
}

People.prototype.getName = function() {
  return this.name
}

const people = new People() // check error


interface People {
  name: string
  getName(): string
}

interface PeopleConstructor {
  // constructor(name : string) : People
  new (name: string): People
  prototype: People 
}

const ctor = (function(this: People, name: string) {
  this.name = name
} as unknown) as PeopleConstructor // 类型不兼容，二次转型

ctor.prototype.getName = function() {
  return this.name
}

const people = new ctor("yj")
console.log("people:", people)
console.log(people.getName())
```


```typescript 
class People {
  name: string
  constructor(name: string) {
    this.name = name // check ok
  }
  getName() {
    return this.name
  }
}
const people = new People("this name");
console.log(people.getName());

class Test {
  name = 1
  namedFunction() {
    return this.name
  }
  Anonymousfunction = function() {
    return this.name // complier error (this is any)
  }
  arrowFunction = () => {
    // create new instance each call 
    return this.name
  }
}

const test = new Test()

console.log(test.namedFunction()) // 1
console.log(test.Anonymousfunction()) // 1
console.log(test.arrowFunction()) // 1
```

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

const child = new Child() // parent

class Parent2 {
  constructor() {
    this.setup()
  }
  setup() {
    console.log("parent")
  }
}

class Child2 extends Parent2 {
  constructor() {
    super()
  }
  setup() {
    console.log("child")
  }
}

const child2 = new Child2() // child
```
在處理繼承的時候，如果 superclass 調用了示例方法而非原型方法，那麼是無法在 subclass 裡進行 override 的，這與其他語言處理繼承的 override 的行為向左，很容出問題。因此更加合理的方式應該是不要使用實例方法，但是如何處理 this 的綁定問題呢。目前較為合理的方式要么手動 bind，或者使用 decorator 來做 bind


## literal Type 

`this` is literal type itself 

```typescript
// this = itself 
let foo = {
  x: "hello",
  // named method
  f(n: number) {
    this // this: {x: string; f(n: number):v oid }
  },
}
```

## literal Type with type annotation

`this` is the type annotation

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
    this // Point
  },
}
```
## `method(this : type)` in literal type

```java
let bar = {
  x: "hello",
  f(this: { message: string }) {
    this // { message: string }
  },
}
```

## `method(this : type )` in literal type with type annotation

```typescript
type Point = {
  x: number
  y: number
  moveBy(dx: number, dy: number): void
}

let p: Point = {
  x: 10,
  y: 20,
  moveBy(this: { message: string }, dx, dy) {
    this // {message:string} 
  },
}
```

## literal with type annotation `& ThisType<T>`

`this` is T

```typescript 
type Point = {
  x: number
  y: number
  moveBy: (dx: number, dy: number) => void
} & ThisType<{ message: string }>

let p: Point = {
  x: 10,
  y: 20,
  moveBy(dx, dy) {
    this // {message:string}
  },
}
```

## `method(this : T)` in literal type

`this` is T

```typescript
type Point = {
  x: number
  y: number
  moveBy(this: { message: string }, dx: number, dy: number): void
}

let p: Point = {
  x: 10,
  y: 20,
  moveBy(dx, dy) {
    this // { message:string}
  },
}
```


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
b.A1().B1() // 不报错
a.A1().B1() // 报错
type M1 = ReturnType<typeof b.A1> // B
type M2 = ReturnType<typeof a.A1> // A
```
仔细观察上述代码发现，在不同的情况下，A1 的返回类型实际上是和调用对象有关的而非固定，只有这样才能支持如下的链式调用，保证每一步调用都是类型安全
```typescript
b.A1().B1().A2().B2() // check ok
```
this 的处理还有其特殊之处，大部分语言对 this 的处理，都是将其作为隐式的参数处理，但是对于函数来讲其参数应该是逆变的，但是 this 的处理实际上是当做协变处理的。考虑如下代码
```typescript
class Parent {
  name: string
}

class Child extends Parent {
  age: number
}
class A {
  A1() {
    return this.A2(new Parent())
  }
  A2(arg: Parent) {}
  A3(arg: string) {}
}

class B extends A {
  A1() {
    // 不报错，this特殊处理，视为协变
    return this.A2(new Parent())
  }
  A2(arg: Child) {} // flow下报错，typescript没报错
  A3(arg: number) {} // flow和typescript下均报错
}
```

## `thisParameterType<typeof NAMED_FUNCTION>`

`thisPramaeterType` Extracts the type of the `this` parameter of a function type or `unknown` if the function type has no `this` parameter.

```typescript
interface People {
  name: string
}
function ctor(this: People) {}

type ThisArg = ThisParameterType<typeof ctor> 
```