# Types
- [EveryDayType](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html)

## Any 

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

## Type Annotations on Variables

you can optionally add a type annotation to explicitly specify the type of the variable via `let` or `const`:
```typescript
let myName: string = "Alice";
```
- TypeScript doesn’t use _types on the left_-style declarations like `int x = 0`; Type annotations will always go after the thing being typed (`variable : type`).

**Wherever possible, TypeScript tries to automatically infer the types.**   
For example, the type of a variable is inferred based on the type of its initializer:
```typescript
// 'myName' inferred as type string
let myName = "Alice";
```

## Functions

TypeScript allows you to specify the types of both the input and output values of functions.

```typescript
// Parameter type annotation
function greet(name: string) {
  console.log("Hello, " + name.toUpperCase() + "!!");
}
```

When a parameter has a type annotation, arguments (of types) to that function will be checked:
```typescript
// This would be a runtime error if executed!
greet(42);
```

### Anonymous Functions

Anonymous functions are a little bit different from function declarations. 

When a function appears in a place where TypeScript can determine how it’s going to be called, the parameters of that function are automatically given types.
```typescript
// No type annotations here, 
// but TypeScript can spot the bug
const names = ["Alice", "Bob", "Eve"];
 
// `Contextual typing` for function
names.forEach(function (s) {
  console.log(s.toUppercase());
  // Property 'toUppercase' does not exist on type 'string'. 
  // Did you mean 'toUpperCase'?
});
 
// Contextual typing 
// also applies to arrow functions
names.forEach((s) => {
  console.log(s.toUppercase());
  // Property 'toUppercase' does not exist on type 'string'. /// Did you mean 'toUpperCase'?
});
```
- TypeScript used the types of the `forEach` function, along with the inferred type of the array, to determine the type s will have.   
- This process is called _contextual typing_ because the context that the function occurred within informs what type it should have.   

## Object Types

The most common sort of type is an object type. 
> To define an object type, we simply list its properties and their types.

For example
```typescript
// The parameter's type annotation (x,y) is an object type
function printCoord(pt: { x: number; y: number }) {
  console.log("The coordinate's x value is " + pt.x);
  console.log("The coordinate's y value is " + pt.y);
}
printCoord({ x: 3, y: 7 });
```
- The type part of each property is also optional. If you don’t specify a type, it will be assumed to be `any`.

### Optional Properties

Object types can also specify that some or all of their properties are optional. 

> To do this, add a `?` after the property name:

```typescript 
function printName(obj: { first: string; last?: string }) {
  // ...
}
// Both OK
printName({ first: "Bob" });
printName({ first: "Alice", last: "Alisson" });
```

In JavaScript, if you access a property that doesn’t exist, you’ll get the value `undefined` rather than a runtime error.   
Because of this, when you read from an optional property, you’ll have to check for `undefined` before using it.
```typescript
function printName(obj: { first: string; last?: string }) {
  // Error - might crash if 'obj.last' wasn't provided!
  console.log(obj.last.toUpperCase());
  // Object is possibly 'undefined'.
  
  if (obj.last !== undefined) {
    // OK
    console.log(obj.last.toUpperCase());
  }
 
  // A safe alternative using modern JavaScript syntax:
  console.log(obj.last?.toUpperCase());
}
```

## Union Type

A union type is a type formed from two or more other types, representing values that may be any one of those types. 

```typescript
function printId(id: number | string) {
  console.log("Your ID is: " + id);
}
// OK
printId(101);
// OK
printId("202");
// Error
printId({ myID: 22342 });
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
- For example, both arrays and strings have a slice method
```typescript 
// Return type is inferred as number[] | string
function getFirstThree(x: number[] | string) {
  return x.slice(0, 3);
}
```
- The union `number | string` is composed by taking the union of the values from each type. 

Notice that given two sets with corresponding facts about each set, only the intersection of those facts applies to the union of the sets themselves. 

For example, if we had a room of tall people wearing hats, and another room of Spanish speakers wearing hats, after combining those rooms, the only thing we know about every person is that they must be wearing a hat.

## Type Aliases

**A type alias is exactly that - a name for any type.**   
The syntax for a type alias is:
```typescript 
// Type named Point
type Point = {
  x: number;
  y: number;
};
 
function printCoord(pt: Point) {
  console.log("The coordinate's x value is " + pt.x);
  console.log("The coordinate's y value is " + pt.y);
}
 
printCoord({ x: 100, y: 100 });
```

You can actually use a type `alias` to give a name to any type at all, not just an object type.    
- For example, a type alias can name a union type:    
```typescript
type ID = number | string;
```
**Note that aliases are only aliases - you cannot use type aliases to create _different/distinct versions_ of the same type.**

When you use the alias, it’s exactly as if you had written the aliased type.    
In other words, this code might look illegal, but is OK according to TypeScript because both types are aliases for the same type:
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

Differences Between Type Aliases and Interfaces.  
Almost all features of an interface are available in type, **the key distinction is that a `type` cannot be re-opened to add new properties vs an interface which is always extendable.**

Extending an interface
```typescript
interface Animal {
  name: string
}

interface Bear extends Animal {
  honey: boolean
}

const bear = getBear() 
bear.name
bear.honey
```   

Extending a type via intersections(`&`)
```typescript
type Animal = {
  name: string
}

type Bear = Animal & { 
  honey: boolean 
}

const bear = getBear();
bear.name;
bear.honey;
```     

Adding new fields to an existing interface
```typescript
interface Window {
  title: string
}

interface Window {
  ts: TypeScriptAPI
}

const src = 'const a = "Hello World"';
window.ts.transpileModule(src, {});
```        

A type cannot be changed after being created
```typescript 
type Window = {
  title: string
}

type Window = {
  ts: TypeScriptAPI
}

 // Error: Duplicate identifier 'Window'.
```
        


## Type Assertions

**Sometimes you will have information about the type of a value that TypeScript can’t know about.**

For example, if you’re using `document.getElementById`,TypeScript only knows that this will return some kind of `HTMLElement`, but you might know that your page will always have an `HTMLCanvasElement` with a given ID.        
In this situation, you can use a type assertion to specify a more specific type:  
```typescript 
const myCanvas = 
  document.getElementById("main_canvas") as HTMLCanvasElement;
```
- **Like a type annotation, type assertions are removed by the compiler and won’t affect the runtime behavior of your code.**

You can also use the angle-bracket syntax (except if the code is in a `.tsx` file), which is equivalent:
```typescript
const myCanvas = 
  <HTMLCanvasElement>document.getElementById("main_canvas");
```

> Because type assertions are removed at compile-time, there is no runtime checking associated with a type assertion. There won’t be an exception or null generated if the type assertion is wrong.

**TypeScript only allows type assertions which convert to a more specific or less specific version of a type.**
This rule prevents “impossible” coercions like:
```typescript
const x = "hello" as number;
// Conversion of type 'string' to type 'number' may be a mistake because neither type sufficiently overlaps with the other. If this was intentional, convert the expression to 'unknown' first.
```

Sometimes this rule can be too conservative and will disallow more complex coercions that might be valid.   
If this happens, you can use two assertions, first to `any` (or `unknown`), then to the desired type:
```typescript
const a = (expr as any) as T;
```

## Intersection Types `&`

An intersection type represents an entity that is of all types. For example:

```typescript
function extend <A, B> (a: A, b: B): A & B {
  
  Object.keys(b).forEach(key => {
    a[key] = b[key]
  })

  return a as A & B
}
```

## Literal Types

**In addition to the general types `string` and `number`, we can refer to specific strings and numbers in type positions(自定義型別)**.
 
```typescript
// let/var represents any possible string
let changingString = "Hello World";
changingString = "Olá Mundo";
changingString;
      
let changingString: string

// const has a literal type representation 
const constantString = "Hello World";

const constantString: "Hello World"

// By themselves, literal types aren’t very valuable:
let x: "hello" = "hello";
// OK (allow only one value ("hello") assignable  )
x = "hello";

x = "howdy";
// Type '"howdy"' is not assignable to type '"hello"'.
```
- It’s not much use to have a variable that can only have one value

But by combining literals into unions, you can express a much more useful concept 
- for example, **functions that only accept a certain set of known values**:
```typescript 
// alignment only allows three value (left, right or center)
function printText(s: string, alignment: "left" | "right" | "center") {
  // ...
}

printText("Hello, world", "left");
printText("G'day, mate", "centre");
// Argument of type '"centre"' is not assignable to parameter of type '"left" | "right" | "center"'.
```

Numeric literal types work the same way:
```typescript
function compare(a: string, b: string): -1 | 0 | 1 {
  return a === b ? 0 : a > b ? 1 : -1;
}

// it also can be combined with non-literal types:
interface Options {
  width: number;
}
function configure(x: Options | "auto") {
  // ...
}
configure({ width: 100 });
configure("auto");

configure("automatic");
// Argument of type '"automatic"' is not assignable to parameter of type 'Options | "auto"'.
```

### `boolean` literals

There are only two boolean literal types, and as you might guess, they are the types `true` and `false`. 

The type boolean itself is actually just an alias for the `union true | false.`

## Literal Inference

When you initialize a variable with an object, TypeScript assumes that the properties of that object might change values later. 
- For example
```typescript
// Typescript knows obj.counter type is number 
const obj = { counter: 0 };
if (someCondition) {
  // properties of obj changed 
  obj.counter = 1;
}

const req = { url: "https://example.com", method: "GET" };
handleRequest(req.url, req.method);
// Argument of type 'string' is not assignable to parameter of type '"GET" | "POST"'.
```
- `req.method` is inferred to be `string`, not `"GET"`.   
   The call of `handleRequest` which could assign a new string like `"GUESS"` to `req.method`, TypeScript considers this code to have an error.

There are two ways to work around this.     

You can change the inference by adding a type assertion in either location:
```typescript
// Change 1:
const req = { url: "https://example.com", method: "GET" as "GET" };

// Change 2
handleRequest(req.url, req.method as "GET");
```
- Change 1 means _I intend for `req.method` to always have the literal type `"GET"`_, preventing the possible assignment of `"GUESS"` to that field after. 

- Change 2 means _I know for other reasons that `req.method` has the value `"GET"`_.
You can use `as const` to convert the entire object to be type literals:
```typescript
const req = { url: "https://example.com", method: "GET" } as const;
handleRequest(req.url, req.method);
```
The `as const` suffix acts like const but for the type system, ensuring that all properties are assigned the literal type instead of a more general version like `string` or `number`.

## `null` and `undefined`
 
To signal absent or uninitialized value (`null` and `undefined`)

### `strictNullChecks` on

With `strictNullChecks` on, when a value is `null` or `undefined`, you will need to test for those values before using methods or properties on that value.

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

It’s important to only use `!` when you know that the value can’t be `null` or `undefined`.


## Less Common Primitives

From ES2020 onwards, there is a primitive in JavaScript used for very large integers, `BigInt`:
```typescript
// Creating a bigint via the BigInt function
const oneHundred: bigint = BigInt(100);
 
// Creating a BigInt via the literal syntax
const anotherHundred: bigint = 100n;
```

## symbol
There is a primitive in JavaScript used to create a globally **unique reference** via the function `Symbol()`:
```typescript
const firstName = Symbol("name");
const secondName = Symbol("name");
 
if (firstName === secondName) {
// This condition will always return 'false' since the types 'typeof firstName' and 'typeof secondName' have no overlap.
// Can't ever happen
}
```
