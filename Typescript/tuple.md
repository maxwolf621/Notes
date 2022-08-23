
# Tuple Types
- [Tuple Types](#tuple-types)
  - [Destructuring](#destructuring)
    - [objects with descriptive property names](#objects-with-descriptive-property-names)
    - [With optional properties](#with-optional-properties)
    - [With rest elements](#with-rest-elements)
    - [`readonly` Tuple Types](#readonly-tuple-types)


**A tuple type is another sort of Array type that knows exactly how many elements it contains, and exactly which types it contains at specific positions.**  

Unlike Array it contains different data type.
```typescript 
let StringNumberPair = [string, number];
```
- `StringNumberPair` is a tuple type of `string` and `number`.  
- To the type system, StringNumberPair describes arrays whose `0` index contains a `string` and whose `1` index contains a `number`.


Tuple Types Like `ReadonlyArray`, it has no representation at runtime, but is significant to TypeScript.  
```typescript 
function doSomething(pair: [string, number]) {
  const a = pair[0];    // const a: string
  const b = pair[1];    // const b: number
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

## Destructuring

We can also destructure tuples using JavaScript’s array destructuring.
```typescript
function doSomething(stringHash: [string, number]) {
  const [inputString, hash] = stringHash;
 
  console.log(inputString); // const inputString: string
  console.log(hash); // const hash: number
}
```

### objects with descriptive property names 

**Tuple types are useful in heavily convention-based APIs, where each element’s meaning is obvious.**  
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

### With optional properties

Another thing you may be interested in is that tuples can have optional properties by writing out a question mark (`?` after an element’s type).     
Optional tuple elements can only come at the end, and also affect the type of length.   
```typescript 
type Either2dOr3d = [number, number, number?];   

function setCoordinate(coord: Either2dOr3d) {   
  const [x, y, z] = coord; 
  //           const z: number | undefined
  console.log(`Provided coordinates had ${coord.length} dimensions`);  
  //                                    (property) length: 2 | 3
}
```
### With rest elements

Tuples can also have rest elements, which have to be an array/tuple type.
```typescript 
type StringNumberBooleans = [string, number, ...boolean[]];
type StringBooleansNumber = [string, ...boolean[], number];
type BooleansStringNumber = [...boolean[], string, number];
```
1. `StringNumberBooleans` describes a tuple whose first two elements are `string` and `number` respectively, but which may have any number of `booleans` following.
2. `StringBooleansNumber` describes a tuple whose first element is string and then any number of `booleans` and ending with a `number`.
3. `BooleansStringNumber` describes a tuple whose starting elements are any number of `booleans` and ending with a `string` then a `number`.

A tuple with a rest element has no set length     
it only has a set of well-known elements in different positions.   
```typescript 
const a: StringNumberBooleans = ["hello", 1];
const b: StringNumberBooleans = ["beautiful", 2, true];
const c: StringNumberBooleans = ["world", 3, true, false, true, false, true];
```

Tuples types can be used in rest parameters and arguments, so that the following:
```typescript 
function readButtonInput(...args: [string, number, ...boolean[]]) {
  const [name, version, ...input] = args;
  // ...
}

// is basically equivalent to:

function readButtonInput(name: string, version: number, ...input: boolean[]) {
  // ...
}
```
This is handy when you want to take a variable number of arguments with a rest parameter, and you need a minimum number of elements, but you don’t want to introduce intermediate variables.

### `readonly` Tuple Types

tuples types have `readonly` variants, and can be specified by sticking a `readonly` modifier in front of them.
```typescript 
function doSomething(pair: readonly [string, number]) {}   
function doSomething(pair: readonly [string, number]) {   
  pair[0] = "hello!" ;
    ^ Cannot assign to '0' 
      because it is a read-only property.
}
```

**Tuples tend to be created and left un-modified in most code, so annotating types as readonly tuples when possible is a good default.**        
This is also important given that array literals with const assertions will be inferred with `readonly` tuple types.   
```typescript 
let point = [3, 4] as const;
 
// here `distanceFromOrigin` never modifies its elements, but expects a mutable tuple.  
function distanceFromOrigin([x, y]: [number, number]) {
  return Math.sqrt(x ** 2 + y ** 2);
}

// But we assign a const parameter  
distanceFromOrigin(point);
  Argument of type 'readonly [3, 4]' is not assignable to parameter of type '[number, number]'.
  The type 'readonly [3, 4]' is 'readonly' and cannot be assigned to the mutable type '[number, number]'.
```