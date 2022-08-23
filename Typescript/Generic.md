# Generic
- [Generic](#generic)
  - [Syntax](#syntax)
  - [Generic Constraints (`extends`)](#generic-constraints-extends)
    - [Multiple generic](#multiple-generic)
  - [Generic Function](#generic-function)
  - [Using Type Parameters in Generic Constraints](#using-type-parameters-in-generic-constraints)
  - [Using Class Types in Generics `c : { new () : Type}`](#using-class-types-in-generics-c---new---type)
  - [Generic Object Types](#generic-object-types)
  - [Generic Class](#generic-class)
    - [Type Parameter in Static Member isn’t legal](#type-parameter-in-static-member-isnt-legal)


```typescript
function createArray <T> (length: number, value: T): Array<T> {
    let result: T[] = [];
    for (let i = 0; i < length; i++) {
        result[i] = value;
    }
    return result;
}

// These declarations are available
createArray<string>(3, 'x'); 
createArray<number>(3, 5); 
createArray(3, 'x'); 
```

## Syntax

```typescript
interface name<T>{
  //...
}

class name<T>{
  //...
}

function name<T> (/* parameters */){
  // ...
}

interface name {
    // function
    <T> (/*parameter*/): Type;
}

createArray = function <T> (length: number, value: T): Array<T> {
  let result: T[] = [];
  for (let i = 0; i < length; i++) {
    result[i] = value;
  }
  return result;
}
```


## Generic Constraints (`extends`)

Not all type `T` has the `length()` methods
```typescript
function loggingIdentity<Type>(arg: Type): Type {
  console.log(arg.length);
                ^ Property 'length' does not 
                  exist on type 'Type'.
  return arg;
}
```

We cans `<T extends CONSTRAINT>` tells compiler that `T` type must be sub-class of CONSTRAINT (inherit CONSTRAINT's properties)
```typescript
interface Lengthwise {
    length: number;
}

function loggingIdentity<T extends Lengthwise>(arg: T): T {
    console.log(arg.length);
    return arg;
}
```

### Multiple generic

```typescript
function copyFields<T extends U, U>(target: T, source: U): T {
    for (let id in source) {
        target[id] = (<T>source)[id];
    }
    return target;
}

let x = { a: 1, b: 2, c: 3, d: 4 };

copyFields(x, { b: 10, d: 20 });
```

## Generic Function

```typescript
interface CreateArrayFunc {
    // function
    <T> (length: number, value: T): Array<T>;
}

let createArray: CreateArrayFunc;

createArray = function <T> (length: number, value: T): Array<T> {
    let result: T[] = [];
    for (let i = 0; i < length; i++) {
        result[i] = value;
    }
    return result;
}

createArray(3, 'x'); // ['x', 'x', 'x']
```

## Using Type Parameters in Generic Constraints

```typescript
function getProperty<Type, Key extends keyof Type>(obj: Type, key: Key) {
  return obj[key];
}
 
let x = { a: 1, b: 2, c: 3, d: 4 };
 
getProperty(x, "a");
```

## Using Class Types in Generics `c : { new () : Type}`

constructor function
```typescript
function create<Type>(c: { new (): Type }): Type {
  return new c();
}

class BeeKeeper {
  hasMask: boolean = true;
}
 
class ZooKeeper {
  nameTag: string = "Mike";
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
 
createInstance(Lion).keeper.nameTag;
createInstance(Bee).keeper.hasMask;
```

## Generic Object Types

Use `unknown` instead of `any`, but that would mean that in cases where we already know the type of contents,assertions.
```typescript 
interface Box {
  contents: unknown;
}
 
let x: Box = {
  contents: "hello world",
};
 
// precautionary checks
if (typeof x.contents === "string") {
  console.log(x.contents.toLowerCase());
}
 
// or a type assertion
console.log((x.contents as string).toLowerCase());
```

One type safe approach would be to instead scaffold out different Box types for every type of contents.   
But that means we’ll have to create different functions, or overloads of functions, to operate on these types.

```typescript 
interface NumberBox {
  contents: number;
}
 
interface StringBox {
  contents: string;
}
 
interface BooleanBox {
  contents: boolean;
}

// Overloads
function setContents(box: StringBox, newContents: string): void;
function setContents(box: NumberBox, newContents: number): void;
function setContents(box: BooleanBox, newContents: boolean): void;
function setContents(box: { contents: any }, newContents: any) {
  box.contents = newContents;
}
```

we can make a generic Box type which declares a generic type parameter to reduce excessive boilerplate code    
```typescript
// define generic type 
interface Box<Type> {
  contents: Type;
}

let boxA: Box<string> = { contents: "hello" };
// (property) Box<string>.contents: string
 
let boxB: StringBox = { contents: "world" };
// (property) StringBox.contents: string

interface Apple {
  // ....
}

// Same as '{ contents: Apple }'.
type AppleBox = Box<Apple>;
```

**Type Parameter also means that we can avoid overloads entirely by instead using generic functions** :
```typescript 
/**
 * function setContents(box: StringBox, newContents: string): void;
 * function setContents(box: NumberBox, newContents: number): void;
 * function setContents(box: BooleanBox, newContents: boolean): void;
 * function setContents(box: { contents: any }, newContents: any);
 * in to only one function 
 */
function setContents<T>(
  box: Box<T>, newContents: T) 
{
  box.contents = newContents;
}
```

It is worth noting that `type` aliases can also be generic.  
```typescript 
    interface             |         type 
interface Box<Type> {     |   type Box<Type> = {
  contents: Type;         |      contents: Type; 
}                         |   };  
```

**Since type aliases, unlike interfaces, can describe more than just object types, we can also use them to write other kinds of generic helper types.**
```typescript 
type OrNull<Type> = Type | null;
type OneOrMany<Type> = Type | Type[];
type OneOrManyOrNull<Type> = OrNull<OneOrMany<Type>>;
type OneOrManyOrNull<Type> = OneOrMany<Type> | null
type OneOrManyOrNullStrings = OneOrManyOrNull<string>;
type OneOrManyOrNullStrings = OneOrMany<string> | null;
```

## Generic Class

```typescript
class Box<Type> {
  contents: Type;
  constructor(value: Type) {
    this.contents = value;
  }
}

const b = new Box("hello!");
```

### Type Parameter in Static Member isn’t legal

```typescript
class Box<Type> {
  static defaultValue: Type;
            ^Static members cannot reference class type parameters.
}
```
