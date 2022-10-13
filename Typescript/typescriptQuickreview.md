# QuickReview
- [QuickReview](#quickreview)
  - [(`?`) Optional](#-optional)
    - [Union Type (`|`)](#union-type-)
  - [narrowing `undefined`](#narrowing-undefined)
  - [readonly (address)](#readonly-address)
  - [readonlyArray](#readonlyarray)
  - [Index Signatures](#index-signatures)
  - [Index signature vs `Record<Keys, Type>`](#index-signature-vs-recordkeys-type)
  - [Intersection Types `&`](#intersection-types-)
  - [Generic Type](#generic-type)
  - [assertions (`as`)](#assertions-as)
  - [Tuple Type](#tuple-type)
  - [Special Types](#special-types)
    - [`never`](#never)
    - [`unknown`](#unknown)
    - [`typeof variableName` and `instanceof className`](#typeof-variablename-and-instanceof-classname)
  - [Literal Types](#literal-types)
- [Function](#function)
  - [Function Type Expressions and function type alias](#function-type-expressions-and-function-type-alias)
  - [call Signature && Construct Signature](#call-signature--construct-signature)
    - [overload](#overload)
  - [Enum](#enum)
    - [Literal Enum](#literal-enum)
    - [object with as const](#object-with-as-const)
    - [Computed and constant members](#computed-and-constant-members)
    - [Sting enums](#sting-enums)
    - [const enmu](#const-enmu)
  - [Using constructor Function in Generics `c : { new () : Type}`](#using-constructor-function-in-generics-c---new---type)
  - [`Array<T>` and `T[]`](#arrayt-and-t)
  - [string or number index signature](#string-or-number-index-signature)

## (`?`) Optional 

```typescript
class getShape{
  //...
}
interface/type PaintOptions {
  shape: Shape;
  xPos?: number; // xPos is optional
  yPos?: number; // yPos is optional
}
 
function paintShape(opts: PaintOptions) {
  // ...
}
 
const shape = getShape();
paintShape({ shape });
paintShape({ shape, xPos: 100 });
paintShape({ shape, yPos: 100 });
paintShape({ shape, xPos: 100, yPos: 100 });
```

### Union Type (`|`) 

**TypeScript will only allow an operation if it is valid for every member of the union.**    

```typescript
// Methods can be used only if 
// these are defined in each member of the union 
function printId(id: number | string) {
  console.log(id.toUpperCase());
               'Property 'toUpperCase' does not exist on type 'string | number'.
               'Property 'toUpperCase' does not exist on type 'number'.
}
```

## narrowing `undefined`

Optional member or union member cause `undefined`. 
```typescript
function paintShape(opts: PaintOptions) {
  let xPos = opts.xPos === undefined ? 0 : opts.xPos; // let xPos: number
  let yPos = opts.yPos === undefined ? 0 : opts.yPos; // let yPos: number
  // ...
}
```

## readonly (address)

**`readonly` doesn't imply that a value is totally immutable**

```typescript
interface Home {
  readonly resident: { name: string; age: number };
}

// ok! change the value not address
function visitForBirthday(home: Home) {
  home.resident.age++;
}
 
function evict(home: Home) {
  
  // It created a new address 
  home.resident = { 
    name: "Victor the Evictor",
            'Cannot assign to 'resident' because it is a read-only property.
    age: 42,
  };
}
```

## readonlyArray

Unlike Array, there isn’t a `ReadonlyArray` constructor that we can use.
```typescript 
new ReadonlyArray("red", "green", "blue");
```
Instead, we can assign regular Arrays to `ReadonlyArrays` type variable.
```typescript
const roArray: ReadonlyArray<string> = ["red", "green", "blue"];
```

## Index Signatures
- **This index signature states that when a `StringArray` is indexed with a `number`, it will return a `string`.**   
- **An index signature property type must be either `string` or `number`.**   

A `string` index declares that `obj.property`(`NumberDictionary.xxxx`) is also available as `obj["property"]`(`NumberDictionary["xxxx"]`).     
```typescript 
interface NumberDictionary {
//    member     :  type 
  [index: string]: number;
  
  length: number; // ok
  name: string;
        ' Property 'name' of type 'string' is not assignable to 'string' index type 'number'.
}

// union index signature
interface NumberOrStringDictionary {
  [index: string]: number | string;
  
  // index Signatures
  length: number; // ok, length is a number
  name: string;   // ok, name is a string
}

type valueType = number | string;

interface c {
    [key : string] : valueType | undefined;
    name ?: string;
    age  ?: number;
}

const obj : c = {
    "love" : "is verb",
    "neon" : "color blind",
    "my stupid mouth" : "getting me in trouble",
    name : "jian",
    age : 18,
    };


for(let key in obj){
    console.log(`${key}: ${obj[key]}`);
}
```

## Index signature vs `Record<Keys, Type>`

Index Signature is not allow literal type

```typescript
interface Salary {
  [key: 'yearlySalary' | 'yearlyBonus']: number
          ' An index signature parameter type cannot be a literal type or generic type. 
          ' Consider using a mapped object type instead.
}
type SpecificSalary = Record<'yearlySalary'|'yearlyBonus', number>
 
const salary1: SpecificSalary = { 
  'yearlySalary': 120_000,
  'yearlyBonus': 10_000
}; // OK
```

## Intersection Types `&`

```typescript
interface Colorful {
  color: string;
}
interface Circle {
  radius: number;
}
 

type ColorfulCircle = Colorful & Circle;
function draw(circle: ColorfulCircle) {
    //....
}
 
// okay
draw({ color: "blue", 
       radius: 42 }); 
draw({ color: "red", 
        radius: 42 });
```


## Generic Type

```typescript 
function setContents<T>(box: Box<T>, newContents: T) {
  box.contents = newContents;
}

type OrNull<Type> = Type | null;
type OneOrMany<Type> = Type | Type[];
 
type OneOrManyOrNull<Type> = OrNull<OneOrMany<Type>>;
           
type OneOrManyOrNull<Type> = OneOrMany<Type> | null
 
type OneOrManyOrNullStrings = OneOrManyOrNull<string>;
               
type OneOrManyOrNullStrings = OneOrMany<string> | null;

function serContent<OrNull>(a : orNull){
    //...
}
```

## assertions (`as`)

using `as` if you already know the variable type (the type you know but typescript doesn't know)

## Tuple Type 

- A tuple with a rest element has no set length     
- It can has rest elements `...`
it only has a set of well-known elements in different positions. 
```typescript
type StringNumberBooleans = [string, number, ...boolean[]];
type StringBooleansNumber = [string, ...boolean[], number];
type BooleansStringNumber = [...boolean[], string, number];
```

**Tuples tend to be created and left un-modified in most code**, so annotating types as `readonly` tuples when possible is a good default.
```typescript
function doSomething(pair: readonly [string, number]) {
    pair[0] = "hello!" ;
    // ^?Cannot assign to '0' because it is a read-only property.
}
```

## Special Types 

### `never`

`never` is not `void` and it built for function that **having not returned value** (e.g. throw Error) or **infinity loop**
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

### `unknown`

**`unknown` is the type-safe counterpart of `any`**. 
- **Anything is assignable to `unknown`, but `unknown` isn't assignable to anything but itself** and `any` without a type assertion or a control flow based narrowing.   

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
value.substring();  // Error : Object is of type 'unknown'
```

### `typeof variableName` and `instanceof className`

```typescript
typeof value === ''
someObject instanceof ObjectBelongingClass
```

## Literal Types

By combining literals into unions, you can express a much more useful concept. For example, **functions that only accept a certain set of known (literal) values**:
```typescript 
// Alignment only allows three value (left, right or center)
function printText(s: string, alignment: "left" | "right" | "center") {
  // ...
}
printText("Hello, world", "left");
printText("G'day, mate", "centre");

// error
Argument of type '"centre"' is not assignable to 
parameter of type '"left" | "right" | "center"'.
```

# Function

```typescript 
// Named function
function add(x, y) {
  //..
}
 
// Anonymous function
let myAdd = function (x, y) {
  // ..
};
```
## Function Type Expressions and function type alias

```typescript 
function greeter(fn: (a: string) => void) {
  fn("Hello, World");
}

function printToConsole(s: string) {
  console.log(s);
}

greeter(printToConsole);

type GreetFunction = (a: string) => void;
function greeter(fn: GreetFunction) {
  // ...
}
```
## call Signature && Construct Signature


### overload

**the implementation signature of the function cannot be called directly, you have to call one of the overload signatures**

```typescript
function example(str: string): string;
function example(num: number): number;

// implementation should has types of signature
function example(arg: string | number): number | string {}


function example(str: string): void;
function example(num: number, num2: number): void;
function example(strOrNum: string | number, num2?: number) {}
```



## Enum
### Literal Enum

**A literal enum member is a constant enum member with no initialized value, or with values that are initialized to**

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
```

### object with as const

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

### Computed and constant members

**Enums without initializers either required to be first or have to come after the numeric enums initialized with numeric constants or other constant enum members.**   

```typescript
// Error
enum Millie {
  x = 19,
  z = fetchValue('Eleven'),
  y
}

// No Error
enum Millie {
  x = 19,
  y,
  z = fetchValue('Eleven'),
}

enum Millie {
  y,
  x = 19,
  z = fetchValue('Eleven'),
}
```

### Sting enums


Unlike numeric enum string enums don’t have auto-incrementing behavior, but string enums have the benefit that they serialize well.

```typescript
enum StrangerThings {
  Character = "Eleven",
  Father,
   Enum member must have initializer.(1061)
  Power = "Telekenesis",
  Town = "Hawkins"
}

// instead 
//using with empty string 
enum StrangerThings {
  Character = "Eleven",
  Father = "",
  Power = "Telekenesis",
  Town = "Hawkins"
}
```

### const enmu

```typescript
const enum Dark { Mikkel, Jonas }

console.log(Dark.Mikkel)    // 0
console.log(Dark['Mikkel']) // 0

console.log(Dark.Jonas)   // 1
console.log(Dark['Jonas'] // 1
```

```typescript
// Error
console.log(Dark.[1])
```

## Using constructor Function in Generics `c : { new () : Type}`

constructor function
```typescript
function create<Type>(c: { new (): Type }): Type {
  return new c();
}

class BeeKeeper {
  hasMask: boolean = true;
}
 
class ZooKeeper {
  nametag: string = "Mikle";
}
 
class Animal {
  numLegs: number = 4;
}
 
class Bee extends Animal {
  keeper: BeeKeeper = new BeeKeeper();
}
 
class Lion extends Animal {
  keeper: ZooKeeper = new ZooKeeper();
}
 
function createInstance<A extends Animal>(c: new () => A): A {
  return new c();
}
 
createInstance(Lion).keeper.nametag;
createInstance(Bee).keeper.hasMask;
```


## `Array<T>` and `T[]`

In TypeScript, the type annotation on these parameters is implicitly `any[]` instead of `any`, and `any` type annotation given must be of the form `Array<T>` or `T[]`, or a tuple type

## string or number index signature

JavaScript object keys are always coerced to a string, so `obj[0]` is always the same as `obj["0"]`.

```java
type Mapish = { [k: string]: boolean };
type M = keyof Mapish; // type M = string | number
```
