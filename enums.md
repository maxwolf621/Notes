# Enums

Enums are one of the few features TypeScript has which is not a type-level extension of JavaScript.
- Enums allow a developer to define a set of named constants. 
- TypeScript provides both numeric and string-based enums.

- [Enums](#enums)
  - [Reference](#reference)
  - [Numeric enums](#numeric-enums)
  - [String enums](#string-enums)
  - [Heterogeneous enums (NOT RECOMMEND)](#heterogeneous-enums-not-recommend)
  - [Computed and constant members](#computed-and-constant-members)
  - [literal enum members](#literal-enum-members)
    - [Union Enum](#union-enum)
  - [Enums at runtime](#enums-at-runtime)
  - [Enums at compile time](#enums-at-compile-time)
  - [Reverse Mapping](#reverse-mapping)
  - [const enums](#const-enums)
  - [The Union Types vs Enum](#the-union-types-vs-enum)
  - [Ambient Enums](#ambient-enums)
  - [object with as const](#object-with-as-const)

## Reference

- [Typescript Enums are bad!!1!!!1!!one - Are they really?](https://dev.to/dvddpl/whats-the-problem-with-typescript-enums-2okj)
- [Inlining constants with const enums in TypeScript](https://www.growingwiththeweb.com/2021/01/typescript-inline-const-enums.html)
- [TypeScript string enums, and when and how to use them](https://blog.logrocket.com/typescript-string-enums-guide/)
- [Introduction to TypeScript Enums — Const and Ambient Enums](https://levelup.gitconnected.com/introduction-to-typescript-enums-const-and-ambient-enums-1fe686b67495)
## Numeric enums

If the first member has no initializer then they are auto-incremented by default from 0
```typescript
enum Direction {
  Up,    // 0
  Down,  // 1
  Left,  // 2
  Right, // 3
}
```

All of the following members are auto-incremented from the point (`1`) on.
```typescript
enum Direction {
  Up = 1,
  Down,   // 2
  Left,   // 3
  Right,  // 4
}
```


## String enums

While string enums don’t have auto-incrementing behavior, string enums have the benefit that they “serialize” well.

if you were debugging and had to read the runtime value of a numeric enum, the value is often opaque - it doesn’t convey any useful meaning on its own (though reverse mapping can often help).    

String enums allow you to give a meaningful and readable value when your code runs, independent of the name of the enum member itself.
```typescript
enum Direction {
  Up = "UP",
  Down = "DOWN",
  Left = "LEFT",
  Right = "RIGHT",
}
```

## Heterogeneous enums (NOT RECOMMEND)

```typescript
enum BooleanLikeHeterogeneousEnum {
  No = 0,
  Yes = "YES",
}
```

## Computed and constant members

A constant enum expression is a subset of TypeScript expressions that can be fully evaluated at compile time. If it is :
- a literal enum expression (basically a string literal or a numeric literal)
- a reference to previously defined constant enum member (which can originate from a different enum)
- a parenthesized constant enum expression
- one of the `+, -, ~` unary operators applied to constant enum expression
- `+, -, *, /, %, <<, >>, >>>, &, |, ^ ` binary operators with constant enum expressions as operands

It is a compile time error for constant enum expressions to be evaluated to NaN or Infinity.
```typescript
enum FileAccess {
  // constant members
  None,
  Read = 1 << 3,
  Write = 1 << 2,
  ReadWrite = Read | Write,
  // computed member
  G = "123".length,
}
```

## literal enum members

**A literal enum member is a constant enum member with no initialized value, or with values that are initialized to**
- any string literal (e.g. `"foo"`, `"bar`, `"baz"`)
- any numeric literal (e.g. `1`, `100`)
- a unary minus applied to any numeric literal (e.g. `-1`, `-100`)

The first is that enum members also become types as well! 
For example, we can say that certain members can only have the value of an enum member:
```typescript
enum ShapeKind {
  Circle,
  Square,
}
 
interface Circle {
  kind: ShapeKind.Circle;
  radius: number;
}
 
interface Square {
  kind: ShapeKind.Square;
  sideLength: number;
}
 
let c: Circle = {
  kind: ShapeKind.Square,
        Type 'ShapeKind.Square' is not assignable to type 'ShapeKind.Circle'
  radius: 100,
};
```


### Union Enum

**The other change is that enum types themselves effectively become a union of each enum member.**   
With union enums, the type system is able to leverage the fact that it knows the exact set of values that exist in the enum itself. 

Because of that, TypeScript can catch bugs where we might be comparing values incorrectly. For example:
```typescript
enum E {
  Foo,
  Bar,
}
 
// x can only be type of E (Foo | Bar)
// the enum type itself is the union of each member
// each member can be used as a type
function f(x: E) {
  if (x !== E.Foo || x !== E.Bar) {
        This condition will always return 'true' since the types 'E.Foo' and 'E.Bar' have no overlap.
    // ...
  }
}

// Instead
function f(x: E) {
  if (x != E.Foo && x!= E.Bar) {
    console.log("Passed Argument is not foo or bar");
  }else{
    console.log("`Hey " + x);
  }
}
f(E.Bar);
f(E.Foo)
```


## Enums at runtime

Enums are real objects that exist at runtime

```typescript
enum E {
  X,
  Y,
  Z,
}
 
function f(obj: { X: number }) {
  return obj.X;
}
 
// Works, since 'E' has a property named 'X' which is a number.
f(E);
```

## Enums at compile time

The drawback of using enums in typescript, complier transpiled codes to JS codes 
It makes codes get bigger and verbose. for example
```typescript
export enum EHttpStatusCode {
    Ok = 200,
    BadRequest = 400,
    Unauthorized = 401,
    Forbidden = 403,
    NotFound = 404,
    ServerError = 500,
}

// compiled
export var EHttptoStatusCode;
(function (EHttpStatusCode) {
    EHttpStatusCode[EHttpStatusCode["Ok"] = 200] = "Ok";
    EHttpStatusCode[EHttpStatusCode["BadRequest"] = 400] = "BadRequest";
    EHttpStatusCode[EHttpStatusCode["Unauthorized"] = 401] = "Unauthorized";
    EHttpStatusCode[EHttpStatusCode["Forbidden"] = 403] = "Forbidden";
    EHttpStatusCode[EHttpStatusCode["NotFound"] = 404] = "NotFound";
    EHttpStatusCode[EHttpStatusCode["ServerError"] = 500] = "ServerError";
})(EHttpStatusCode || (EHttpStatusCode = {}));
```


use `keyof typeof` to get a Type that represents all Enum keys as strings.
```typescript
enum LogLevel {
  ERROR,
  WARN,
  INFO,
  DEBUG,
}

/**
 * This is equivalent to:
 * type LogLevelStrings = 'ERROR' | 'WARN' | 'INFO' | 'DEBUG';
 */
type LogLevelStrings = keyof typeof LogLevel;

function printImportant(key: LogLevelStrings, message: string) {

  const num = LogLevel[key];
  
  if (num <= LogLevel.WARN) {
    console.log("Log level key is:", key);
    console.log("Log level value is:", num);
    console.log("Log level message is:", message);
  }
}

printImportant("DEBUG", "This is a message");
```

## Reverse Mapping

```typescript
enum Enum {
  A,
}
 
let a = Enum.A;
//            Enum[0]
let nameOfA = Enum[a]; // "A"

// Compile Time
"use strict";
var Enum;
(function (Enum) {
    Enum[Enum["A"] = 0] = "A";
})(Enum || (Enum = {}));
let a = Enum.A;
let nameOfA = Enum[a]; // "A"
```

In this generated code, an enum is compiled into an object that stores both forward `(name -> value)` and reverse `(value -> name)` mappings.   
References to other enum members are always emitted as property accesses and never inlined.

**Keep in mind that string enum members do not get a reverse mapping generated at all.**
- **String enums have no reverse mapping**
```typescript
enum Enum{
  A = "String"
}

let a = Enum.A;

let nameOfa = Enum[a];
                Element implicitly has an 'any' type because expression of type 'Enum' can't be used to index type 'typeof Enum'.
                Property '[Enum]' does not exist on type 'typeof Enum'
```


## const enums

Const enums are defined using the `const` modifier on our enums 
- **Const enums can only use constant enum expressions and unlike regular enums they are completely removed during compilation.**

```typescript
// TypeScript
enum Constants {
    First = 1,
    Second = 2
}
console.log(Constants.First + Constants.Second);

// Compiled
"use strict";
var Constants;
(function (Constants) {
    Constants[Constants["First"] = 1] = "First";
    Constants[Constants["Second"] = 2] = "Second";
})(Constants || (Constants = {}));
console.log(Constants.First + Constants.Second);

// TypeScript 
const enum Constants {
    First = 1,
    Second = 2
}
console.log(Constants.First + Constants.Second);

// Compiled
"use strict";
console.log(1 /* First */ + 2 /* Second */);

const enum Direction {
  Up,
  Down,
  Left,
  Right,
}
 
let directions = [
  Direction.Up,
  Direction.Down,
  Direction.Left,
  Direction.Right,
];

// Code will become
"use strict";
// 
let directions = [
    0 /* Direction.Up */,
    1 /* Direction.Down */,
    2 /* Direction.Left */,
    3 /* Direction.Right */,
];
```

## The Union Types vs Enum

The Enums increase the size of your code argument
The Enums are hard to map and compare argument

Instead of Enum, types are not compiled to javascript.
```typescript 
type UserStatus = "registered" | "inactive" | "notFound" | "banned" 

// compiled
```

String literals and union types
```typescript
type TimeDurations = 'hour' | 'day' | 'week' | 'month'; 

let time : TimeDurations; 
time = "hour"; // valid  
time = "day";  //  valid 

time = "dgdf"; // errors 
```

## Ambient Enums

To reference an enum that exists somewhere else in the code, we can use the declare keyword.

Ambient enums can’t have values assigned to any members and they won’t be included in compiled code since they’re supposed to reference enums that are defined somewhere else. For example, we can write:
```typescript 
declare enum Fruit {
  Orange,
  Apple,
  Grape
}
```
If we try to reference an ambient enum that’s not defined anywhere, we’ll get a run-time error since no lookup object is included in the compiled code.

Enum members can act as data types for variables, class members, and any other things that can be typed with TypeScript.    
An enum itself can also be a data type for these things. Therefore, anything typed with the enum type is a union type of all the member enum types.   

Enums are included or not depending on what keywords we use before the enum.   
If they’re defined with `const` or `declare` , then they won’t be included in the compiled code. 

**Enums are just objects when converted to JavaScript and the members are converted to properties when compiled to JavaScript.**   
This means that we can use member names as property names of objects in TypeScript.   

## object with as const

```typescript
const userStatus = {
// key      :  values
  REGISTERED: 'registered',
  INACTIVE: 'inactive',
  NOT_FOUND: 'notFound',
  BANNED: 'banned',
} as const;


type TypeUserStatus = typeof userStatus;
/* 
type TypeUserStatus = {
  readonly REGISTERED: "registered";
  readonly INACTIVE: "inactive";
  readonly NOT_FOUND: "notFound";
  readonly BANNED: "banned";
}

Note without `as const`:

type TypeUserStatus = {
    REGISTERED: string;
    INACTIVE: string;
    NOT_FOUND: string;
    BANNED: string;
}
*/


// to Key Part
type UserStatus = keyof TypeUserStatus;
/* 
type UserStatus = "REGISTERED" | "INACTIVE" | "NOT_FOUND" | "BANNED"
*/

// to value part  
type TypeUserStatus = typeof userStatus;
type UserStatus = TypeUserStatus[keyof TypeUserStatus];
/*
 type UserStatus = "registered" | "inactive" | "notFound" | "banned"
 */
```

For the case to change all value to uppercase, all we need to do now is
```typescript 
const userStatus = {
  REGISTERED: 'REGISTERED',
  INACTIVE: 'INACTIVE',
  NOT_FOUND: 'NOT_FOUND',
  BANNED: 'BANNED',
} as const;

type TypeUserStatus = typeof userStatus;
type UserStatus = TypeUserStatus[keyof TypeUserStatus];
/**
 * type UserStatus = "REGISTERED" | "INACTIVE" | "NOT_FOUND" | "BANNED" 
  */
```

