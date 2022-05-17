# Object Types 

- [Object Types](#object-types)
  * [Optional Properties (`?`)](#optional-properties------)
  * [`readonly` Properties](#-readonly--properties)
  * [Index Signatures](#index-signatures)
  * [Extending Types](#extending-types)
    + [Intersection Types (`&`)](#intersection-types------)
    + [Interfaces vs. Intersections](#interfaces-vs-intersections)
  * [Generic Object Types](#generic-object-types)
  * [The Array Type](#the-array-type)
    + [The ReadonlyArray Type](#the-readonlyarray-type)
  * [Tuple Types](#tuple-types)
    + [readonly Tuple Types](#readonly-tuple-types)

Object Types GROUP and PASS around data that is through objects   
- For example
```typescript 
// Using Object Types `{ object1, object2, ... , ... }`
function greet(person: { name: string; age: number }) {
  return "Hello " + person.name;
}

// via Interface
interface Person {
  name: string;
  age: number;
}
 
function greet(person: Person) {
  return "Hello " + person.name;
}

// via Type Alias 
type Person = {
  name: string;
  age: number;
};
 
function greet(person: Person) {
  return "Hello " + person.name;
}
```

## Optional Properties (`?`)

```typescript 
interface PaintOptions {
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

when we do under `strictNullChecks`, TypeScript will tell us they’re potentially `undefined`.
- For example
```typescript
function paintShape(opts: PaintOptions) {
  let xPos = opts.xPos; // (property) PaintOptions.xPos?: number | undefined
  let yPos = opts.yPos; // (property) PaintOptions.yPos?: number | undefined
  
  // ...
}
```


To handle `undefine` 
```typescript 
// Handle `undefine` specifally 
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

```typescript
interface SomeType {
  readonly prop: string;
}
 
function doSomething(obj: SomeType) {
  // We can read from 'obj.prop'.
  console.log(`prop has the value '${obj.prop}'.`);
 
  // But we can't re-assign it.
  obj.prop = "hello";
  // Cannot assign to 'prop' because it is a read-only property.
}
```

**`readonly` doesn't imply that a value is totally immutable**
- For example
```typescript
/**
  * update the value not the address 
  */
interface Home {
  readonly resident: { name: string; age: number };
}
 
function visitForBirthday(home: Home) {
  // We can read and update properties from 'home.resident'.
  console.log(`Happy birthday ${home.resident.name}!`);
  home.resident.age++;
}
 
function evict(home: Home) {
  // But we can't write to the 'resident' property itself on a 'Home'.
  // It created a new address 
  home.resident = { 
    // Cannot assign to 'resident' because it is a read-only property.
    name: "Victor the Evictor",
    age: 42,
  };
}
```

## Index Signatures

Sometimes you don’t know all the names of a type’s properties ahead of time(in advance), but you do know the shape of the values.    
In those cases you can use an index signature to describe the types of possible values   
- For example
```typescript
interface StringArray {
  // index : is number type
  // StringArray's porperties : must be string type 
  [index: number]: string;
}
 
const myArray: StringArray = getStringArray();
const secondItem = myArray[1];
const secondItem: string
```
- **This index signature states that when a `StringArray` is indexed with a `number`, it( will return a `string`.**   
- **An index signature property type must be either `string` or `number`.**   

It is possible to support both types of indexers.   
While `string` index signatures are a powerful way to describe the ***dictionary*** pattern, they also enforce that all properties match their return type.  

This is because a `string` index declares that `obj.property` is also available as `obj["property"]`.     
```typescript 
interface NumberDictionary {
  [index: string]: number;
  length: number; // ok
  
  name: string;
  // Property 'name' of type 'string' is not assignable to 'string' index type 'number'.
}
```

It's acceptable if the index signature is a union of the property types:
```typescript 
interface NumberOrStringDictionary {
  // indexed signature can be number or string
  [index: string]: number | string;
  length: number; // ok, length is a number
  name: string;   // ok, name is a string
}
```

Index signatures `readonly` in order to prevent assignment to their indices
```typescript 
interface ReadonlyStringArray {
  readonly [index: number]: string;
}
 
let myArray: ReadonlyStringArray = getReadOnlyStringArray();
myArray[2] = "Mallory";
// Index signature in type 'ReadonlyStringArray' only permits reading.
```

## Extending Types 

**The extends keyword on an interface allows us to effectively copy members from other named types, and add whatever new members we want.** 

This can be useful for cutting down the amount of type declaration boilerplate we have to write, and for signaling intent that several different declarations of the same property might be related. 

Interfaces can also extend from multiple types.
```typescript
interface Colorful {
  color: string;
}
 
interface Circle {
  radius: number;
}

// exend multiple types
interface ColorfulCircle extends Colorful, Circle {}
 
const cc: ColorfulCircle = {
  color: "red",
  radius: 42,
};
```

### Intersection Types (`&`)

TypeScript provides another construct called intersection types that is mainly used to combine existing object types.

```typescript 
interface Colorful {
  color: string;
}
interface Circle {
  radius: number;
}
 
type ColorfulCircle = Colorful & Circle;

function draw(circle: Colorful & Circle) {
  console.log(`Color was ${circle.color}`);
  console.log(`Radius was ${circle.radius}`);
}
 
// okay
draw({ color: "blue", radius: 42 });
 

draw({ color: "red", raidus: 42 });
// Argument of type '{ color: string; raidus: number; }' is not assignable to parameter of type 'Colorful & Circle'.
// Object literal may only specify known properties, but 'raidus' does not exist in type 'Colorful & Circle'. Did you mean to write 'radius'?
```

### Interfaces vs. Intersections

The principle difference between the two is how conflicts are handled, and that difference is typically one of the main reasons why you’d pick one over the other between an interface and a type alias of an intersection type.

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
That’s a lot of boilerplate.   

Instead, we can make a generic Box type which declares a `type` parameter.  
```typescript
interface Box<Type> {
  contents: Type;
}
interface StringBox {
  contents: string;
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

This also means that we can avoid overloads entirely by instead using generic functions.
```typescript 
function setContents<Type>(box: Box<Type>, newContents: Type) {
  box.contents = newContents;
}
```

It is worth noting that `type` aliases can also be generic.  
```typescript 
interface Box<Type> {
  contents: Type;
}
// by using a type alias instead:
type Box<Type> = {
  contents: Type;
};
```

**Since type aliases, unlike interfaces, can describe more than just object types, we can also use them to write other kinds of generic helper types.**
```typescript 
type OrNull<Type> = Type | null;
 
type OneOrMany<Type> = Type | Type[];
 
type OneOrManyOrNull<Type> = OrNull<OneOrMany<Type>>;
           
type OneOrManyOrNull<Type> = OneOrMany<Type> | null
 
type OneOrManyOrNullStrings = OneOrManyOrNull<string>;
               
type OneOrManyOrNullStrings = OneOrMany<string> | null
```

## The Array Type

**Generic object types are often some sort of container type that work independently of the type of elements they contain.**     

the Array type. Whenever we write out types like `number[]` or `string[]`, that’s really just a shorthand for `Array<number>` and `Array<string>`.
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

### The ReadonlyArray Type

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
```typescript 
new ReadonlyArray("red", "green", "blue");
// 'ReadonlyArray' only refers to a type, but is being used as a value here.
```

Instead, we can assign regular Arrays to ReadonlyArrays.
```typescript
const roArray: ReadonlyArray<string> = ["red", "green", "blue"];
```

Just as TypeScript provides a shorthand syntax for `Array<Type>` with `Type[]`, it also provides a shorthand syntax for `ReadonlyArray<Type>` with readonly `Type[]`.
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

Unlike the `readonly` property modifier, assignability isn’t bidirectional between regular Arrays and `ReadonlyArrays`.
```typescript 
let x: readonly string[] = [];
let y: string[] = [];
 
x = y;
y = x;
//  The type 'readonly string[]' is 'readonly' and cannot be assigned to the mutable type 'string[]'.
```

## Tuple Types

**A tuple type is another sort of Array type that knows exactly how many elements it contains, and exactly which types it contains at specific positions.**
```typescript 
pe StringNumberPair = [string, number];
```
- `StringNumberPair` is a tuple type of `string` and `number`.  
- To the type system, StringNumberPair describes arrays whose 0 index contains a `string` and whose 1 index contains a `number`.

Tuple Types Like `ReadonlyArray`, it has no representation at runtime, but is significant to TypeScript.  
```typescript 
function doSomething(pair: [string, number]) {
  const a = pair[0];
       
  const a: string

  const b = pair[1];
       
  const b: number
  // ...
}
 
doSomething(["hello", 42]);
```

If we try to index past the number of elements, we’ll get an error.
```typescript 
function doSomething(pair: [string, number]) {
  // ...
 
  const c = pair[2];
  // Tuple type '[string, number]' of length '2' has no element at index '2'.
}
```

We can also destructure tuples using JavaScript’s array destructuring.
```typescript
function doSomething(stringHash: [string, number]) {
  const [inputString, hash] = stringHash;
 
  console.log(inputString);
  const inputString: string
 
  console.log(hash);
  const hash: number
}
```

Tuple types are useful in heavily convention-based APIs, where each element’s meaning is “obvious”.  
This gives us flexibility in whatever we want to name our variables when we destructure them.  

In the above example, we were able to name elements 0 and 1 to whatever we wanted.

However, since not every user holds the same view of what’s obvious, it may be worth reconsidering whether using objects with descriptive property names may be better for your API.

Other than those length checks, simple tuple types like these are equivalent to types which are versions of Arrays that declare properties for specific indexes, and that declare length with a numeric literal type.
```typescript 
interface StringNumberPair {
  // specialized properties
  length: 2;
  0: string;
  1: number;
 
  // Other 'Array<string | number>' members...
  slice(start?: number, end?: number): Array<string | number>;
}
```
Another thing you may be interested in is that tuples can have optional properties by writing out a question mark (? after an element’s type). Optional tuple elements can only come at the end, and also affect the type of length.
```typescript 
type Either2dOr3d = [number, number, number?];
 
function setCoordinate(coord: Either2dOr3d) {
  const [x, y, z] = coord;
              
const z: number | undefined
 
  console.log(`Provided coordinates had ${coord.length} dimensions`);
                                                  
(property) length: 2 | 3
}
```
Tuples can also have rest elements, which have to be an array/tuple type.
```typescript 
type StringNumberBooleans = [string, number, ...boolean[]];
type StringBooleansNumber = [string, ...boolean[], number];
type BooleansStringNumber = [...boolean[], string, number];
```
1. `StringNumberBooleans` describes a tuple whose first two elements are `string` and `number` respectively, but which may have any number of `booleans` following.
2. `StringBooleansNumber` describes a tuple whose first element is string and then any number of `booleans` and ending with a `number`.
3. `BooleansStringNumber` describes a tuple whose starting elements are any number of `booleans` and ending with a `string` then a `number`.

A tuple with a rest element has no set “length” - it only has a set of well-known elements in different positions.
```typescript 
const a: StringNumberBooleans = ["hello", 1];
const b: StringNumberBooleans = ["beautiful", 2, true];
const c: StringNumberBooleans = ["world", 3, true, false, true, false, true];
```

Why might optional and rest elements be useful? Well, it allows TypeScript to correspond tuples with parameter lists. 

Tuples types can be used in rest parameters and arguments, so that the following:
```typescript 
function readButtonInput(...args: [string, number, ...boolean[]]) {
  const [name, version, ...input] = args;
  // ...
}
```
is basically equivalent to:
```typescript 
function readButtonInput(name: string, version: number, ...input: boolean[]) {
  // ...
}
```
This is handy when you want to take a variable number of arguments with a rest parameter, and you need a minimum number of elements, but you don’t want to introduce intermediate variables.

### readonly Tuple Types

tuples types have `readonly` variants, and can be specified by sticking a `readonly` modifier in front of them - just like with array shorthand syntax.
```typescript 
function doSomething(pair: readonly [string, number]) {
  // ...
}

function doSomething(pair: readonly [string, number]) {
  pair[0] = "hello!" ;
  // Cannot assign to '0' because it is a read-only property.
}
```

**Tuples tend to be created and left un-modified in most code, so annotating types as readonly tuples when possible is a good default.** 

This is also important given that array literals with const assertions will be inferred with `readonly` 
tuple types.   
```typescript 
let point = [3, 4] as const;
 
function distanceFromOrigin([x, y]: [number, number]) {
  return Math.sqrt(x ** 2 + y ** 2);
}
 
distanceFromOrigin(point);
// Argument of type 'readonly [3, 4]' is not assignable to parameter of type '[number, number]'.
// The type 'readonly [3, 4]' is 'readonly' and cannot be assigned to the mutable type '[number, number]'.
```
- `distanceFromOrigin` never modifies its elements, but expects a mutable tuple.  
