# Other Types to Know About

- [Other Types to Know About](#other-types-to-know-about)
  - [`void`](#void)
  - [`object`](#object)
  - [`unknown`](#unknown)
    - [never](#never)
    - [`Function`](#function)
  - [Rest Parameters](#rest-parameters)
  - [Rest Arguments](#rest-arguments)
  - [Parameter Destructuring](#parameter-destructuring)
  - [Assignability of Functions](#assignability-of-functions)
    - [`void` return type](#void-return-type)

## `void`

`void` represents the return value of functions which don’t return a value.  

**In JavaScript, a function that doesn’t return any value will implicitly return the value `undefined`.**
**However, `void` and `undefined` are not the same thing in TypeScript.**

It’s the inferred type any time a function doesn’t have any return statements, or doesn’t return any explicit value from those return statements:
```typescript
// The inferred return type is void
function noop() {
  return;
}
```

## `object`

`Object` with an uppercase `O` is the type of all instances of class Object:
```typescript
let obj1: Object;

const obj1 = {};
obj1 instanceof Object
// true
```

`object` with a lowercase `o` is the type of all non-primitive values:   
**(refers to any value that isn’t a primitive (`string`, `number`, `bigint`, `boolean`, `symbol`, `null`, or `undefined`).**
- This is different from the empty object type `{ }`, and also different from the global type Object.  
```typescript 
let obj2: object;
```
- Note that in JavaScript, function values are objects.   
They have properties, have `Object.prototype` in their prototype chain, are `instanceof Object`, you can call `Object.keys` on them, and so on.  

For this reason, ***function types are considered to be objects in TypeScript.***

## `unknown`

The `unknown` type represents any value just like `any` type
- Anything is assignable to `unknown`, but `unknown` isn't assignable to anything but itself and `any` without a type assertion or a control flow based narrowing.   
Likewise, **no operations are permitted on an `unknown` without first asserting or narrowing to a more specific type.**

What is the Difference btw `any` and `unknown`
- **`unknown` which is the type-safe counterpart of `any`**. 

- **`unknown` type can be assigned to other types but itself can not be assigned to other types**

```typescript
// Anything is assignable to unknown
let value: unknown = 3;
value = "Ian";
value = undefined;
value = null;
value = true;


let value1: unknown = value;   // anything is assignable to unknown
let value2: any = value;       // anything is assignable to any

// Only unknown is assignable to itself
let value3: number = value;   // Error 
let value4: boolean = value;  // Error
let value5: string = value;   // Error

let vAny: any = 10;          // We can assign anything to any
let vUnknown: unknown =  10; // We can assign anything to unknown just like any 

let s1: string = vAny;       // Any is assignable to anything 
let s2: string = vUnknown;   // Invalid; we can't assign vUnknown to any other type (without an explicit assertion)
vAny.method();     // OK; anything goes with any
vUnknown.method(); // Not Ok ; we don't know anything about this variable

let value: unknown;
let value2: any;
value2.substring(); // OK (may cause accidents)

value.substring();  // Error 
Object is of type 'unknown'

function f1(a: any) {
  a.b(); // OK
}

function f2(a: unknown) {
  a.b();
  Object is of type 'unknown'.
}
```
**This is useful when describing function types because you can describe functions that accept any value without having any values in your function body.**

Conversely, you can describe a function that returns a value of `unknown` type:
```typescript 
function safeParse(s: string): unknown {
  return JSON.parse(s);
}
 
// Need to be careful with 'obj'!
const obj = safeParse(someRandomString);
```

### never

- The `never` type represents values which are never observed.
- `never` is not `void` and it built for function that **having not returned value** (e.g. throw Error) or **infinity loop**

**In a return type, this means that the function throws an exception or terminates execution of the program.**
```typescript
// Not have a reachable end point
function error(message: string): never {
  throw new Error(message);
}

// error inferred return type is never
function fail() {
  return error("Something failed");
}

// Not have a reachable end point
function infiniteLoop(): never {
  while (true) {}
}
```

`never` also appears when TypeScript determines there’s nothing left in a `union`.
```typescript 
function fn(x: string | number) {
  if (typeof x === "string") {
    // do something
  } else if (typeof x === "number") {
    // do something else
  } else {
    x; // has type 'never'!
  }
}
```

### `Function`

The global type `Function` describes properties like bind, call, apply, and others present on all function values in JavaScript.  

It also has the special property that values of type `Function` can always be called; these calls return `any`:
```typescript 
function doSomething(f: Function) {
  return f(1, 2, 3);
}
```
- **This is an untyped function call and is generally best avoided because of the unsafe any return type.**

If you need to accept an arbitrary function but don’t intend to call it, the type `() => void` is generally safer.

**In addition to using optional parameters or overloads to make functions that can accept a variety of fixed argument counts, _we can also define functions that take an unbounded number of arguments using rest parameters_.**  

A rest parameter appears after all other parameters, and uses the `...` syntax:
```typescript 
function multiply(n: number, ...m: number[]) {
  return m.map((x) => n * x);
}
// 'a' gets value [10, 20, 30, 40]
const a = multiply(10, 1, 2, 3, 4);
```
- In TypeScript, the type annotation on these parameters is implicitly `any[]` instead of `any`, and `any` type annotation given must be of the form `Array<T>` or `T[]`, or a tuple type

## Rest Parameters

A rest parameter appears after all other parameters, and uses the ... syntax:
```typescript
function multiply(n: number, ...m: number[]) {
  return m.map((x) => n * x);
}
// 'a' gets value [10, 20, 30, 40]
const a = multiply(10, 1, 2, 3, 4);
```
In TypeScript, the type annotation on these parameters is implicitly `any[]` instead of `any`, and any type annotation given must be of the form `Array<T>`or `T[]`.

## Rest Arguments

Conversely, we can provide a variable number of arguments from an array using the spread syntax. 
For example, the `push` method of arrays takes any number of arguments:
```typescript 
const arr1 = [1, 2, 3];
const arr2 = [4, 5, 6];
arr1.push(...arr2);
```

**In general, TypeScript does not assume that arrays are IMMUTABLE.**  
- For the following example, Typescript inferred type (of `args`) is `number[]` an array with zero or more numbers, not specifically two numbers
```typescript 
const args = [8, 5];
const angle = Math.atan2(...args);
A spread argument must either have a tuple type or be passed to a rest parameter.
```


In general a const context is the most straightforward solution
- [`as const`](https://stackoverflow.com/questions/66993264/what-does-the-as-const-mean-in-typescript-and-what-is-its-use-case)
```typescript 
// Inferred as "2-length" tuple via as const
const args = [8, 5] as const;

// OK
const angle = Math.atan2(...args); // of type readonly[8,5]
```
- Using rest arguments may require turning on downlevelIteration when targeting older runtimes.

## Parameter Destructuring

You can use ***parameter destructuring to conveniently unpack objects provided as an argument into one or more local variables in the function body.***
```typescript 
function sum({ a, b, c }) {
  console.log(a + b + c);
}

sum({ a: 10, b: 3, c: 9 });
```

The type annotation for the object goes after the destructuring syntax:
```typescript 
function sum({ a, b, c }: { a: number; b: number; c: number }) {
  console.log(a + b + c);
}
```

This can look a bit verbose, but you can use a NAME TYPE here as well:
```typescript 
// Same as prior example
type ABC = { a: number; b: number; c: number };
function sum({ a, b, c }: ABC) {
  console.log(a + b + c);
}
```

## Assignability of Functions

### `void` return type

The `void` return type for functions can produce some unusual, but expected behavior.   
Contextual typing with a return type of `void` does not force functions to not return something.       
Another way to say this is a contextual function type with a `void` return type (`type vf = () => void`), when implemented, can **return any other value, but it will be ignored.**   

Thus, the following implementations of the `type () => void` are valid:
```typescript   
type voidFunc = () => void;
 
const f1: voidFunc = () => {
  return true;
};
 
const f2: voidFunc = () => true;
 
const f3: voidFunc = function () {
  return true;
};
```

And when the return value of one of these functions is assigned to another variable, it will retain the type of `void`:
```typescript 
const v1 = f1(); 
const v2 = f2();
const v3 = f3();
```

This behavior exists so that the following code is valid even though `Array.prototype.push` returns a number (new array length) and the `Array.prototype.forEach` method expects a function with a return type of `void`.
```typescript 
const src = [1, 2, 3];
const dst = [0];
 
src.forEach((el) => dst.push(el));
```

There is one other special case to be aware of, when a literal function definition(Anonymous function) has a `void` return type, that function must not return anything.
```typescript 
function f2(): void {
  // @ts-expect-error
  return true;
}
 
const f3 = function (): void {
  // @ts-expect-error
  return true;
};
```

```typescript
const src = [1, 2, 3];
const f1 = function (): void {
  src.push(2);
  return 1234;
  Type 'number' is not assignable to type 'void'.(2322)
};
const fff = f1();   // fff is type of void not number
console.log(fff); // but it is still return 1234

-----------------------------------------------------

const f2 = function (): void {
  return ;
};   // return undefined

const f3 = function (): void {
    src.push(2);
}; // return undefined

-----------------------------------------------------

const f4 = function () : void{
  return src.push(2);
  Type 'number' is not assignable to type 'void'.(2322)
};  

const ffff = f4();  // return 4 (array new length after new element is pushed)
src.forEach( e => console.log(e)); // return 1,3,4,2

-----------------------------------------------------
let src = [1,3,4];

const f3 = function (a : number[]): void {
  a.push(2);
}; 

const res = f3(src); // return undefined
src.forEach( e => console.log(e)); // [1,3,4,2]

-----------------------------------------------------

let src = [1,3,4];

const f3 = function (a : number[]): void {
  a.push(2);
  return true;
}; 

const ffff = f3(src);
src.forEach( e => console.log(e));
console.log(ffff);
```