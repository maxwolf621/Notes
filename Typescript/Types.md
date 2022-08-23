# Types

- [Types](#types)
  - [Reference](#reference)
  - [Types In TS](#types-in-ts)
  - [Type Annotations && Type Inference](#type-annotations--type-inference)
  - [Structural Type System (duck typing/structural typing)](#structural-type-system-duck-typingstructural-typing)
  - [Any type](#any-type)
    - [`strictNullChecks` Mode](#strictnullchecks-mode)
    - [Non-null Assertion Operator (Postfix `!`)](#non-null-assertion-operator-postfix-)
  - [Anonymous Functions](#anonymous-functions)
  - [Object Types](#object-types)
  - [Intersection Types `&`](#intersection-types-)
  - [Union Type (`|`)](#union-type-)
  - [Type Aliases](#type-aliases)
  - [Interface](#interface)
    - [Compiler can unit the same separate interface](#compiler-can-unit-the-same-separate-interface)
    - [interface vs `type`](#interface-vs-type)
    - [interface vs intersection](#interface-vs-intersection)
  - [Type Assertions (`as`)](#type-assertions-as)
    - [`(expr as any) as T`](#expr-as-any-as-t)
    - [Type Declaration VS Type Assertion](#type-declaration-vs-type-assertion)
    - [Genetics VS Type Assertion](#genetics-vs-type-assertion)
  - [Literal Types](#literal-types)
    - [`boolean` literals](#boolean-literals)
    - [Literal Inference](#literal-inference)
    - [`as const`](#as-const)
  - [Less Common Primitives](#less-common-primitives)
  - [`Symbol`](#symbol)
## Reference 
[EveryDayType](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html)  
[Type Inference & Annotation](https://learn.appointment-bot.webzyno.com/docs/typescript/type-inference-annotation)

## Types In TS

- Primitive Types： `number`, `string`, `boolean`, `undefined`, `null`, ES6 `symbol`, and `void`
- Object Types : `JSON`,`Array<T>` or `T[]`, Class, Enum, Tuple, and Interface
- Function Types
- Literal Types
- `any`、`never` and `unknown`
- union (`|`) 與 intersection (`&`) 
- Generic Types

## Type Annotations && Type Inference

you can optionally add a type annotation to explicitly specify the type of the variable via `let` or `const`:
```typescript
let myName: string = "Alice";
```
- TypeScript doesn’t use _types on the left_-style declarations like `int x = 0;` Type annotations will always go after the thing being typed (`variable : type`).

**Wherever possible, TypeScript tries to automatically infer the types.**   
For example, the type of a variable is inferred based on the type of its initializer:
```typescript
// Typescript knows that
// 'myName' inferred as type string
let myName = "Alice";
```

## Structural Type System (duck typing/structural typing)
One of TypeScript’s core principles is that type checking focuses on the shape that values have. 

In a structural type system, if two objects have the same shape, they are considered to be of the same type.
```typescript 
interface Point {
  x: number;
  y: number;
}
 
function logPoint(p: Point) {
  console.log(`${p.x}, ${p.y}`);
}
 
// logs "12, 26"
const point = { x: 12, y: 26 }; // same shape as point
logPoint(point);
```
## Any type

you can use `any` whenever you don’t want a PARTICULAR value to cause typechecking errors.

> When a value is of type `any`, you can access any properties of it (which will in turn be of type `any`), call it like a function, assign it to (or from) a value of any type, or pretty much anything else that’s syntactically legal

```typescript
let obj: any = { x: 0 };

// None of the following lines of code will throw compiler errors.
obj.foo();
obj();
obj.bar = 100;
obj = "hello";
const n: number = obj;
```
- Using `any` disables all further type checking, and it is assumed you know the environment better than TypeScript.
- The `any` type is useful **when you don’t want to write out a long type just to convince TypeScript that a particular line of code is okay.**
- When you don’t specify a type, and TypeScript can’t infer it from context, the compiler will typically default to `any`.


If programmer doesn't initialize the variable, typescript infers it as `any` type
```typescript
// let without initializing
// you can assign any value to variable 
let messageToSend; // let messageToSend: any
anuVariable = 'NineFiveTwoSeven';
anyVariable = 9527;

// with type annotation
let absoluteNothing: undefined = undefined;
let literallyAbsoluteNothing: null = null;

// Warning (but you can run it)
absoluteNothing = 123;
    Type '123' is not assignable to type 'undefined'.(2322)
literallyAbsoluteNothing = "I can't live in this variable now...";
    Type '"I can't live in this variable now..."' is not assignable to type 'null'.(2322)

// typescript infers them as any type
let notingUndefined = undefined; 
let nothingNull = null;
```

Variable with Type Annotation when it has no value to be initialized to prevent from assigning it to other variable as `any` type
```typescript
// With Type Annotation 
let canBeNullableString: string;

canBeNullableString = 'hello';

canBeNullableString = undefined;    // error
canBeNullableString = null;         // error

let canBeNullableString: string;

// error
let myString = canBeNullableString;
canBeNullableString = 'hello';
```

### `strictNullChecks` Mode

**With strictNullChecks on, when a value is `null` or `undefined`, you will need to test for those values before using methods or properties on that value.**

we can use narrowing to check for values that might be `null`
```typescript
function doSomething(x: string | null) {
  if (x === null) {
    // do nothing
  } else {
    console.log("Hello, " + x.toUpperCase());
  }
}
```

### Non-null Assertion Operator (Postfix `!`)

TypeScript also has a special syntax for removing `null` and `undefined` from a type without doing any explicit checking. Writing `!` after any expression is effectively a type assertion that the value isn’t `null` or `undefined`
```typescript
function liveDangerously(x?: number | null) {
  // No error
  console.log(x!.toFixed());
}
```
- **It’s important to only use `!` when you know that the value can’t be `null` or `undefined`.**

## Anonymous Functions

When a function appears in a place where TypeScript can determine how it’s going to be called, the parameters of that function are automatically given types.

This process is called _contextual typing_ because the context that the function occurred within informs what type it should have.   
```typescript
// No type annotations here, but TypeScript can spot the bug
const names = ["Alice", "Bob", "Eve"];
 
// Contextual typing for function
names.forEach(function (s) {
  console.log(s.toUppercase());
    Property 'toUppercase' does not exist on type 'string'. 
    Did you mean 'toUpperCase'?
});
 
// Contextual typing also applies to arrow functions
names.forEach((s) => { console.log(s.toUppercase());
    Property 'toUppercase' does not exist on type 'string'. 
    Did you mean 'toUpperCase'?
});
```
## Object Types

The most common sort of type is an object type. 

To define an object type, we simply list its properties and their types `property : type`
```typescript 
//         |object type|
type obj = { x : number , 
             y : number ,}
```
- **The type part of each property is also optional. If you don’t specify a type, it will be assumed to be `any`.**
- You can use `,` or `;` to separate the properties, and the last separator is optional either way.

```typescript
// The parameter's type annotation is an object type
function printCoord(pt : obj){
  console.log("The coordinate's x value is " + pt.x);
  console.log("The coordinate's y value is " + pt.y);
}
printCoord({ x: 3, y: 7 });
```

## Intersection Types `&`

An intersection type represents an entity that is of all types. 
```typescript
function extend <A, B> (a: A, b: B): A & B {  
  Object.keys(b).forEach(key => {
    a[key] = b[key]
  })
  return a as A & B
}
```

TypeScript provides another construct called intersection types that is mainly used to combine existing object types.
```typescript 
interface Colorful {
  color: string;
}
interface Circle {
  radius: number;
}
 
type ColorfulCircle = Colorful & Circle;
function draw(circle: ColorfulCircle) {
  console.log(`Color was ${circle.color}`);
  console.log(`Radius was ${circle.radius}`);
}
 // okay
draw({ color: "blue", 
       radius: 42 }); 
```
## Union Type (`|`)

A union type is a type formed from two or more other types, representing values that may be any one of those types. 
```typescript
function printId(id: number | string) {
  console.log("Your ID is: " + id);
}
printId(101); // OK
printId("202"); // OK

printId({ myID: 22342 });
  object type is not number | string typ
```

**TypeScript will only allow an operation if it is valid for every member of the union.**    
- For example, if you have the union `string | number`, you can’t use methods that are only available on string:
```typescript
function printId(id: number | string) {
  console.log(id.toUpperCase());
  // Property 'toUpperCase' does not exist on type 'string | number'.
  // Property 'toUpperCase' does not exist on type 'number'.
}
```

Using Narrowing occurs when TypeScript can deduce a more specific type for a value based on the structure of the code.
```typescript
function printId(id: number | string) {
  if (typeof id === "string") {
    // In this branch, id is of type 'string'
    console.log(id.toUpperCase());
  } else {
    // Here, id is of type 'number'
    console.log(id);
  }
}

// narrowing with array and non-array
function welcomePeople(x: string[] | string) {
  if (Array.isArray(x)) {
    // Here: 'x' is 'string[]'
    console.log("Hello, " + x.join(" and "));
  } else {
    // Here: 'x' is 'string'
    console.log("Welcome lone traveler " + x);
  }
}
```

If every member in a union has a property in common, you can use that property without narrowing. 

For example, both arrays and strings have a slice method
```typescript 
// Return type is inferred as number[] | string
function getFirstThree(x: number[] | string) {
  return x.slice(0, 3);
}
```
- The union `number | string` is composed by taking the union of the values from each type. 

Notice that given two sets with corresponding facts about each set, only the intersection of those facts applies to the union of the sets themselves. 
## Type Aliases 

A type alias is exactly that - a name for any type or group of types.   

**Aliases are only aliases - you can't use type aliases to create _different/distinct versions_ of the same type.**

```typescript
// type alias (object)
type Point = {
  x: number;
  y: number;
};
 
function printCoord(pt: Point) {
  console.log("The coordinate's x value is " + pt.x);
  console.log("The coordinate's y value is " + pt.y);
}
 printCoord({ x: 100, y: 100 });


type Name = string;
type NameResolver = () => string;
type NameOrResolver = Name | NameResolver;
type Human = Male | Female
type PetList = [Dog, Pet] // tuple

// using type instead getName(n : string | NameResolver)   
function getName(n: NameOrResolver): Name {
    if (typeof n === 'string') {
        return n;
    } else {
        return n();
    }
}
```

When you use the alias, it's exactly as if you had written the aliased type.  
```typescript
type UserInputSanitizedString = string;
 
function sanitizeInput(str: string): UserInputSanitizedString {
  return sanitize(str);
}
 
// Create a sanitized input
let userInput = sanitizeInput(getInput());
 
// Can still be re-assigned with a string though
userInput = "new input";
```
## Interface 

**Interface uses `extends` keyword that allowsing us to effectively COPY members from other named types, and add whatever new members we want.**    

```typescript
interface Colorful {
  color: string;
} 
interface Circle {
  radius: number;
}

interface ColorfulCircle extends Colorful, Circle {}
 
const cc: ColorfulCircle = {
  color: "red",
  radius: 42,
};
```

### Compiler can unit the same separate interface

```typescript
interface Cat {
    name: string;
}
interface Cat {
    age: number;
}
// compiled
interface Cat {
    name: string;
    age: number;
}
```

### interface vs `type`

Almost all features of an interface are available in type, **the key distinction is that a `type` cannot be re-opened to add new properties vs an interface which is always extendable.**

      
```typescript
Extending an interface(extends)         Extending a type via intersections(x &)
-------------------------------------------------------------------------
interface Animal {                      type Animal = {  
  name: string                            name: string
}                                       }

interface Bear extends Animal {         type Bear = Animal & { 
  honey: boolean                          honey: boolean 
}                                       }

const bear = getBear()                 const bear = getBear();
bear.name                              bear.name;
bear.honey                             bear.honey;    
```   

```typescript
Adding new fields to                   type cannot be changed 
an existing interface                  after being created
-----------------------------------------------------------------
interface Window {                      type Window = {        
  title: string                           title: string          
}                                       }      
interface Window {                      type Window = {
  ts: TypeScriptAPI                       ts: TypeScriptAPI
}                                       }  
const src = 'const a = "Hello World"';    ^ Duplicate identifier 'Window'.
window.ts.transpileModule(src, {});
```        

### interface vs intersection

The principle difference between the two is how conflicts are handled, and that difference is typically one of the main reasons why you’d pick one over the other between an interface and a type alias of an intersection type.

## Type Assertions (`as`)

**Sometimes you will have information about the type of a value that TypeScript can’t know about.**

**Like a type annotation, type assertions are removed by the compiler and won’t affect the runtime behavior of your code.**

For example, if you’re using `document.getElementById`,TypeScript only knows that this will return some kind of `HTMLElement`, but you might know that your page will always have an `HTMLCanvasElement` with a given ID.        
In this situation, you can use a type assertion to specify a more specific type:  
```typescript 
const myCanvas = 
  // typescript doesn't know "main_canvas"
  document.getElementById("main_canvas") as HTMLCanvasElement;
```
You can also use the angle-bracket syntax (except if the code is in a `.tsx` file), which is equivalent:
```typescript
const myCanvas = 
  <HTMLCanvasElement>document.getElementById("main_canvas");
```
Because type assertions are removed at compile-time, there is no runtime checking associated with a type assertion.  
There won’t be an exception or `null` generated if the type assertion is wrong.

**TypeScript only allows type assertions which convert to a more specific or less specific version of a type.**

This rule, it prevents impossible coercions like:
```typescript
const x = "hello" as number;
      ^ Conversion of type 'string' to type 'number' 
        may be a mistake because neither type 
        sufficiently overlaps with the other. 
        If this was intentional, 
        convert the expression to 'unknown' first.
```

### `(expr as any) as T`

you can use two assertions, first to `any` (or `unknown`), then to the desired type:
```typescript
const a = (expr as any) as T;
```
### Type Declaration VS Type Assertion

```typescript 
interface Animal {
    name: string;
}

interface Cat {
    name: string;
    run(): void;
}

const animal: Animal = {
    name: 'tom'
};

let tom: Cat = animal;
         ^ Property 'run' is missing in type 
           'Animal' but required in type 'Cat'
```
### Genetics VS Type Assertion

```typescript
// Assertion
function getCacheData(key: string): any {
    return (window as any).cache[key];
}
interface Cat {
    name: string;
    run(): void;
}
const tom = getCacheData('tom') as Cat;
tom.run();

// generic
function getCacheData<T>(key: string): T {
    return (window as any).cache[key];
}
interface Cat {
    name: string;
    run(): void;
}
const tom = getCacheData<Cat>('tom');
tom.run();
```

## Literal Types

**In addition to the general types `string` and `number`, we can refer to specific string`s` and number`s` in type positions**.

```typescript
// let and var represent any possible string
let changingString = "Hello World";
changingString = "Olá Mundo";

// What you cant do
changingString = 1 
  ^Type 'number' is not assignable to type 'string'.(2322)

let changingString: string
  ^ Cannot redeclare block-scoped variable 'changingString'.

// allow only one value ("hello") assignable
let x: "hello" = "hello";
x = "hello";

x = "howdy";
^ Type '"howdy"' is not assignable to type '"hello"'.

// const has a literal type representation 
const constantString = "Hello World";

const constantString: "Hello World"
^ 'const' declarations must be initialized.(1155)

const constantString : "Hello World" = "Hello World"
```

By combining literals into unions, you can express a much more useful concept. 
```typescript 
// Alignment only allows three value (left, right or center)
function printText(s: string, alignment: "left" | "right" | "center") {}
printText("Hello, world", "left");

printText("G'day, mate", "centre");
  Argument of type '"centre"' is not assignable to 
  parameter of type '"left" | "right" | "center"'.

// Numeric literal types work the same way:
function compare(a: string, b: string): -1 | 0 | 1 {
  return a === b ? 0 : a > b ? 1 : -1;
}

interface Options {width: number;}
function configure(x: Options | "auto") {}
configure({ width: 100 });
configure("auto");

configure("automatic"); 
  Argument of type '"automatic"' is not assignable to parameter of type 'Options | "auto"'.
```

### `boolean` literals

There are only two boolean literal types, and as you might guess, they are the types `true` and `false`. 

The type boolean itself is actually just an alias for the union `true | false.`

### Literal Inference

When you initialize a variable with an object, TypeScript assumes that the properties of that object might change values later. For example ::
```typescript
// Typescript knows obj.counter type is number 
const obj = { counter: 0 };
//    ^? const obj: {counter: number;}

if (someCondition) {
  // properties of obj changed 
  obj.counter = 1;
}
```

```typescript
const req = { url: "https://example.com", method: "GET" };
      ^ const req: {url: string;method: string;}

//            url:string req : "GET" | "POST"
handleRequest(req.url, req.method);
     ^ Argument of type 'string' is 
       not assignable to parameter of type '"GET" | "POST"'.
```
- here `req.method` is inferred to be `string`, not `"GET"`.   
   The call of `handleRequest` which could assign a new string like `"GUESS"` to `req.method`, TypeScript considers this code to have an error.

There are two ways to work around this.        

You can change the inference by adding a TYPE ASSERTION in either location:
```typescript
// Change 1 : (preffered)
const req = { url: "https://example.com", method: "GET" as "GET" };

// Change 2
handleRequest(req.url, req.method as "GET");
```
- Change 1 means _I intend for `req.method` to always have the literal type `"GET"`_, preventing the possible assignment of `"GUESS"` to that field after. 
- Change 2 means _I know for other reasons that `req.method` has the value `"GET"`_. 

### `as const`

You can use `as const` to convert the entire object to be type literals:
```typescript
const req = { url: "https://example.com", method: "GET" } as const;
      ^ const req: {
          readonly url: "https://example.com";
          readonly method: "GET";
      }
  
handleRequest(req.url, req.method);
```
- The `as const` suffix acts like const but for the type system, ensuring that all properties are assigned the literal type instead of a more general version like `string` or `number`.

## Less Common Primitives

From ES2020 onwards, there is a primitive in JavaScript used for very large integers, `BigInt`:
```typescript
// Creating a bigint via the BigInt function
const oneHundred: bigint = BigInt(100);
 
// Creating a BigInt via the literal syntax
const anotherHundred: bigint = 100n;
```

## `Symbol`
There is a primitive in JavaScript used to create a globally **unique reference** via the function `Symbol()`

```typescript
// difference reference 
const firstName = Symbol("name");
const secondName = Symbol("name");
 
if (firstName === secondName) {
  /**
    * This condition will always return 'false' 
    * since the types 'typeof firstName' 
    * and 'typeof secondName' have no overlap.
    */
}
```