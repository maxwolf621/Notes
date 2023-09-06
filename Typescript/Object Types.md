# Object Types 

[TOC]

References  
[`unknown` vs `any`](https://stackoverflow.com/questions/51439843/unknown-vs-any)  

---

Object Types :arrow_right: **GROUP** and **PASS** around data that is through objects, for example :
```typescript 

/************
 * Object   *
 ************/
const std = {
  name : string;
  age : number;
} 
interface std = {
  name : string;
  age : number;
}

/*******************************
 * Object-Type                 *
 * Represent A Type Literally  *
 *******************************/
type std = {
    name : string;
    age : number;
}


// We Say person parameter is object type

function greet(person: { name: string; age: number }) {
  return "Hello " + person.name;
}

// better
function greet(person: std) {
  return "Hello " + person.name;
}
```

## Optional Properties (`?`) in Object Types
when we do under `strictNullChecks = ON`, TypeScript will tell us they’re potentially `undefined`.
- For example
```typescript 
interface PaintOptions {
  shape: Shape;
  xPos?: number; // xPos is optional
  yPos?: number; // yPos is optional
}

function paintShape(opts: PaintOptions) {
  let xPos = opts.xPos; // (property) PaintOptions.xPos?: number | undefined
  let yPos = opts.yPos; // (property) PaintOptions.yPos?: number | undefined
  
  // ...
}
```

Optional properties must be narrowed for handling `undefine` case
```typescript 
// Handle `undefine` specifically
function paintShape(opts: PaintOptions) {
  let xPos = opts.xPos === undefined ? 0 : opts.xPos; // let xPos: number
  let yPos = opts.yPos === undefined ? 0 : opts.yPos; // let yPos: number
  // ...
}

// setting defaults for unspecified values 
function paintShape({ shape, xPos = 0, yPos = 0 }: PaintOptions) {
  console.log("x coordinate at", xPos);// (parameter) xPos: number
  console.log("y coordinate at", yPos); // (parameter) yPos: number
  // ...
}
```

## `readonly` Properties

>>> **`readonly` does NOT imply that a value is totally immutable**

- Object Type : Address Can Not Be Modified
- Primitive Type : Read Only

```typescript
/**
  * Allow Update the value not the address 
  */
interface Home {
    readonly resident: { name: string; age: number };
}

 

// We can read and update properties from 'home.resident'.
function visitForBirthday(home: Home) {
    console.log(`Happy birthday ${home.resident.name}!`);
    home.resident.age++;
}
```

But we can't write to the 'resident' property itself on a 'Home'. It created a new address for the object
```typescript 
function evict(home: Home) {
    home.resident = { 
        name: "Victor the Evictor",
        age: 42,
    };
//     ^ Cannot assign to 'resident' because it is a read-only property.
}
```

## Index Signatures

**Sometimes you don’t know all the names of a type’s properties ahead of time(in advance), but YOU DO KNOW THE SHAPE OF THE VALUES.**  
In those cases you can use an index signature to describe the types of possible values   

>>> **An index signature property type must be either `string` or `number`.**   
### array is indexed with `number`

```typescript
type valueType = number | string;

interface c {
    [key : string] : valueType | undefined;
    name ?: string;
    age  ?: number;
}

const obj : c = {
    "love" : "is verb",                            // indexed signature
    "neon" : "color blind",                        // indexed signature            
    "my stupid mouth" : "getting me in trouble",   // indexed signature
    name : "jian",
    age : 18,
}
for(let key in obj){
    console.log(`${key}: ${obj[key]}`);
}
```
### array is indexed with `string`

While `string` index signatures are a powerful way to describe the ***dictionary*** pattern, they also enforce that all properties match their return type.    
This is because a `string` index declares that `obj.property` is also available as `obj["property"]`.       
```typescript 
interface NumberDictionary {
  [index: string]: number;
  length: number; // ok  
  name: string;
        // ^ Property 'name' of type 'string' 
        // ^ is not assignable to 'string' index type 'number'.
}
```
### array is indexed with `number | string`

It's acceptable if the index signature is a union of the property types:
```typescript 
interface NumberOrStringDictionary {
  // indexed signature can be number or string
  [index: string]: number | string;
  length: number; // ok, length is a number
  name: string;   // ok, name is a string
}
```

### readonly index signature

Index signatures `readonly` in order to prevent assignment to their indices
```typescript 
interface ReadonlyStringArray {
  readonly [index: number]: string;
}
 
let myArray: ReadonlyStringArray = getReadOnlyStringArray();
myArray[2] = "Mallory";
        // ^ Index signature in type 'ReadonlyStringArray' only permits reading.
```

## The Array Type

Generic object types are often some sort of container type that work independently of the type of elements they contain.   

>>> The Array type. **Whenever we write out types like `number[]` or `string[]`, that's really just a shorthand for `Array<number>` and `Array<string>`.**
```typescript
function doSomething(value: Array<string>) {
  // ...
}
 
let myArray: string[] = ["hello", "world"];
 
// either of these work!
doSomething(myArray);
doSomething(new Array("hello", "world"));
```

Array itself is a generic type.
```typescript 
interface Array<Type> {
  /**
   * Gets or sets the length of the array.
   */
  length: number;
 
  /**
   * Removes the last element from an array and returns it.
   */
  pop(): Type | undefined;
 
  /**
   * Appends new elements to an array, and returns the new length of the array.
   */
  push(...items: Type[]): number;
 
  // ...
}
```

## The ReadonlyArray Type

The `ReadonlyArray` is a special type that describes arrays that shouldn’t be changed.
```typescript 
function doStuff(values: ReadonlyArray<string>) {
  
  // We can read from 'values'...
  const copy = values.slice();
  console.log(`The first value is ${values[0]}`);
 
  // ...but we can't mutate 'values'.
  values.push("hello!");
  // Property 'push' does not exist on type 'readonly string[]'.
}
```
**Much like the readonly modifier for properties, it’s mainly a tool we can use for intent**. 

1. When we see a function that _RETURNS_ `ReadonlyArrays`, it tells us we’re not meant to change the contents at all.
2. when we see a function that _CONSUMES_ `ReadonlyArrays`, it tells us that we can pass any array into that function without worrying that it will change its contents.

Unlike Array, there isn’t a `ReadonlyArray` constructor that we can use.
- `ReadonlyArray` only refers to a type, but is being used as a value here.
```typescript 
new ReadonlyArray("red", "green", "blue");
```

Instead, we can assign regular Arrays to `ReadonlyArrays` type variable.
```typescript
const roArray: ReadonlyArray<string> = ["red", "green", "blue"];
```


>>> **TypeScript provides a shorthand syntax for `Array<Type>` with `Type[]`, it also provides a shorthand syntax for `ReadonlyArray<Type>` with `readonly Type[]`.**
```typescript 
function doStuff(values: readonly string[]) {
  // We can read from 'values'...
  const copy = values.slice();
  console.log(`The first value is ${values[0]}`);
 
  // ...but we can't mutate 'values'.
  values.push("hello!");
  // Property 'push' does not exist on type 'readonly string[]'.
}
```

### readonly cant be assigned to mutable 

Unlike the `readonly` property modifier, assignability isn’t bidirectional between regular Arrays and `ReadonlyArrays`.
```typescript 
let x: readonly string[] = [];
let y: string[] = [];
 
x = y;
y = x;
// ^ The type 'readonly string[]' is 
//   readonly' and cannot be assigned 
//   to the mutable type 'string[]'.
```