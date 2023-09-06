# Functions

References
[Function overload](https://bobbyhadz.com/blog/typescript-overload-signature-not-compatible-implementation)

```typescript 
/************************
 * Named functions      *
 ************************/
function add(x, y) {
  //..
}
 
/************************
 * Anonymous functions  *
 ************************/
//         "--- function type expression ---"
let myAdd: (x: number, y: number) => number = function (
  x: number,
  y: number
): number {
  return x + y;
};

// simplied
let myAdd = (x: number, y: number) : number => {
    return x + y
}

```
## Function type expressions 

Pass a function as parameter 

```typescript 
// Function_name      :  type
functionName          : ( parameters ) => returnType
```

The simplest way to describe a function is with a function type expression.
```typescript 
function greeter(fn : (s : string) => void){
    fn("it's my life");
}

function printToConsole(s: string) {
  console.log(s);
}

// pass a function as parameter
greeter(printToConsole;
```
- The syntax `(a: string) => void` means _a function with one parameter, named `a`, of type `string`, that doesn’t have a return value (`void`)_.  
- Just like with function declarations, if a parameter type isn’t specified, it’s implicitly `any`.  that is `(string) => void` is same as `(string : any) => void`

## Function Type Alias

Make a Function type expression readable with `type` keyword

```typescript
type GreetFunction = (a: string) => void;
function greeter(fn: GreetFunction) {
  // ...
}
```
## Signatures

### Call Signatures

Functions can have properties in addition to being callable.    
However, the function type expression syntax doesn't allow for declaring properties. 

If we want to describe something callable with properties, we can write a call signature in an object type
```typescript
// Create Object-Type 
type DescribableFunction = {
  description: string;
  // Call Signatures (function without name)
  (someArg: number): boolean;
};

// Callable object type 
function doSomething(fn: DescribableFunction) {
  console.log(fn.description + " returned " + fn(6));
}
```
### Construct Signatures 

You can write a construct signature by adding the `new` keyword in front of a call signature
```typescript
type SomeConstructor = {
  new (s: string): SomeObject;
};

function fn(ctor: SomeConstructor) {
  return new ctor("hello");
}
```

Some objects, like JavaScript’s Date object, can be called with/without `new`
```typescript
interface CallOrConstruct {
  new (s: string): Date;
  (n?: number): number;
}
```

## Generic Functions


Generics are used when we want to describe a correspondence between two values.  

Instead of the return type `any`
```typescript 
function firstElement(arr: any[]) {
  // return input's first element as output
  return arr[0];
}
```

It’d be better using generics type to represent array's element type
```typescript 
function firstElement<Type>(arr: Type[]): Type | undefined {
    // return Specific Type or undefined
  return arr[0];
}

/*********************************************
 * the type will be inferred automatically   *
 *********************************************/
// s is of type 'string'
const s = firstElement(["a", "b", "c"]);
// n is of type 'number'
const n = firstElement([1, 2, 3]);
// u is of type undefined
const u = firstElement([]);
```

### (type) Inference
    
>>> **The type was inferred - chosen automatically - by TypeScript.**  

```typescript 
function map<Input, Output>(arr: Input[], func: (arg: Input) => Output): Output[] {
  return arr.map(func);
}

// infer the type automatically
//     'n' is of type 'string' 
//     'parsed' is of type 'number[]'
const parsed = map(["1", "2", "3"], (n) => parseInt(n));
```
- TypeScript could infer both the type of the Input type parameter (from the given `string` array), as well as the Output type parameter based on the return value of the function expression (`number`).

### Constraints

**We can constrain the type parameter to the specific type by writing an `extends` clause :**  
```typescript=
function longest<Type extends { length: number }> (a: Type, b: Type) 
{
  if (a.length >= b.length) {
    return a;
  } else {
    return b;
  }
}
 
// longerArray is of type 'number[]'
const longerArray = longest([1, 2], [1, 2, 3]);

// longerString is of type 'alice' | 'bob'
const longerString = longest("alice", "bob");

// Error
const notOK = longest(10, 100);
Argument of type 'number' is not assignable to parameter of type '{ length: number; }'.
```
- Because we constrained `Type` to `{ length: number }`, we were allowed to access the `.length` property of the `a` and `b` parameters.
- Without the type constraint, we wouldn’t be able to access those properties because the values might have been some other type without a length property.
- the call to `longest(10, 100)` is rejected because the `number` type doesn’t have a `.length` property.

### Working with Constrained Values

```typescript 
function minimumLength<Type extends { length: number }>( obj: Type, minimum: number): Type 
{
  if (obj.length >= minimum) {
    return obj;
  } else {
    return { length : minimum };
     ^ Type '{ length: number; }' is not assignable to type 'Type'.
       '{ length: number; }' is assignable to the constraint of type 'Type', 
       but 'Type' could be instantiated 
       with a different subtype of constraint '{ length: number; }'.
  }
}
```
- `Type` is constrained to `{ length: number }`, and the function either returns `Type` or a value matching that constraint.  

The problem is that the function promises to return the same kind of object as was passed in, not just some object matching the constraint.    
If this code were legal, you could write the following code that definitely wouldn’t work:
```typescript 
const arr = minimumLength([1, 2, 3], 6);

console.log(arr.slice(0));
```
- `arr` gets value `{ length: 6 }` minimum Length 
- it will crashes because arrays have a `slice` method, but not the returned object (`arr`)!

### Specifying Type Arguments instead of the constraints

>>> **When possible, use the type parameter itself rather than _constraining_ it**

TypeScript can usually infer the intended type arguments in a generic call, **BUT NOT ALWAYS.**

For example 
```typescript 
// Combine two arrays
function combine<Type>(arr1: Type[], arr2: Type[]): Type[] {
  return arr1.concat(arr2);
}

const arr = combine([1, 2, 3], ["hello"]);
      ^ Type 'string' is not assignable to type 'number'.
```

If you intended to do this, however, you could manually specify Type via `|`
```typescript
// <Type> : string | number 
const arr = combine<string | number>([1, 2, 3], ["hello"]);
```

**Having too many type parameter using constraints where they aren't needed can make inference less successful, frustrating callers of your function.**     
Here are two ways of writing a function with type parameter and `any` type
```typescript 
function firstElement1<Type>(arr: Type[]) {
  return arr[0];
}

// This is bad
function firstElement2<Type extends any[]>(arr: Type) {
  return arr[0];
}
 
// a has definite type
const a = firstElement1([1, 2, 3]);

// b could be any type
const b = firstElement2([1, 2, 3]);
```
`firstElement1` is a much better way to write this function.  
Its inferred return type is `Type`, but `firstElement2`'s inferred return type is `any` because TypeScript has to resolve the `arr[0]` expression using the constraint type, rather than waiting to resolve the element during a call.

## Guidelines for Writing Good Generic Functions

### Use Fewer Type Parameters

Always use as few type parameters as possible   
```typescript
// one parameter and one function parameter
function filter1 <Type> (arr: Type[], func : (arg: Type) => boolean): Type[] {
  return arr.filter(func);
}

function filter2<Type, Func extends (arg: Type) => boolean>(arr: Type[], func: Func): Type[] {
  return arr.filter(func);
}
```
 means callers wanting to specify type arguments have to manually specify an extra type argument for no reason.   
`Func` doesn’t do anything but make the function harder to read and reason about!

### Type Parameters Should Appear Twice   

If a type parameter only appears in one location, strongly reconsider if you actually need it
```typescript 
function greet<Str extends string>(s: Str) {
  console.log("Hello, " + s);
}
greet("world");
```

This could just as easily have been written a simpler version:
```typescript 
function greet(s: string) {
  console.log("Hello, " + s);
}
```

**Type parameters are for relating the types of multiple values. If a type parameter is only used once in the function signature, it’s not relating anything.**

## Optional Parameters

```typescript 
function f(x?: number) {
  console.log(n.toFixed());  // 0 arguments
  console.log(n.toFixed(3)); // 1 argument
}
f(); // OK
f(10); // OK
```
- Although the parameter is specified as type `number`, the `x` parameter will actually have the type `number | undefined` because unspecified parameters in JavaScript get the value `undefined`. (we need to narrow the undefined case)

**Note that when a parameter is optional, callers can always pass `undefined`, as this simply simulates a _missing_ argument**:
```typescript 
declare function f(x?: number): void;

// All OK
f();
f(10);
f(undefined);
```

### Default parameter 

Provide a default parameter instead of using Optional Parameter
```typescript 
function f(x = 10) {
  // ...
}
```
- Now in the body of `f`, `x` will have type `number` because any `undefined` argument will be replaced with `10`.  

### Optional Parameters in Callbacks

**When writing a function type for a `callback`, never write an optional parameter (`?`) unless you intend to call the function without passing that argument**   

This is very easy to make the following mistakes when writing functions that invoke callbacks with optional Parameters:
```typescript 
function myForEach(arr: any[], 
                   callback: (arg: any, index?: number) => void) {
  for (let i = 0; i < arr.length; i++) {
    callback(arr[i], i);
  }
}
```
What people usually intend when writing `index?` as an optional parameter is that they want both of these calls to be legal:
```typescript 
myForEach([1, 2, 3], (a) => console.log(a));
myForEach([1, 2, 3], (a, i) => console.log(a, i));
```

if `callback` gets invoked with one argument.     
```typescript 
function myForEach(arr: any[], callback: (arg: any, index?: number) => void) {
  for (let i = 0; i < arr.length; i++) {
    callback(arr[i]);
  }
}
```
In turn, TypeScript will enforce this meaning and issue errors that aren’t really possible:
```typescript
//                       index
myForEach([1, 2, 3], (a, index) => {
  console.log(i.toFixed());
  ^ Object is possibly 'undefined'.
});
```
- In JavaScript, if you call a function with more arguments than there are parameters, the extra arguments are simply ignored. TypeScript behaves the same way.   

- Functions with fewer parameters (of the same types) can always take the place of functions with more parameters.    

## Function Overloads

In TypeScript, **the implementation signature of the function cannot be called directly, you have to call one of the overload signatures**
```typescript 
// overload signatures
function makeDate(timestamp: number): Date;
// overload signatures
function makeDate(m: number, d: number, y: number): Date;

// implementation signature
function makeDate(mOrTimestamp: number, d?: number, y?: number): Date {
  if (d !== undefined && y !== undefined) {
    return new Date(y, mOrTimestamp, d);
  } else {
    return new Date(mOrTimestamp);
  }
}
const d1 = makeDate(12345678);
const d2 = makeDate(5, 5, 5);

// ERROR
const d3 = makeDate(1, 3);
No overload expects 2 arguments, 
but overloads do exist that expect either 1 or 3 arguments.
```
- we wrote two overloads:   
one accepting one argument, and another accepting three arguments.  
These first two signatures are called the overload signatures.  
Then, we wrote a function implementation with a compatible signature.  
- **Functions have an implementation signature, but this signature can't be called directly.**   
**Even though we wrote a function with two optional parameters after the required one, it can’t be called with two parameters!**

### Overload Signatures and the Implementation Signature

Often people will write code like this and not understand why there is an error:
```typescript 
// Overload Signature
function fn(x: string): void;

// Implementation Signature
function fn() {}

// Expected to be able to call with zero arguments
fn();
"Expected 1 arguments, but got 0.
```

1. **When writing an overloaded function, you should always have two or more signatures above the implementation of the function.**
2. The implementation signature must also be compatible with the overload signatures.

the following functions have errors because the implementation signature doesn’t match the overloads in a correct way
```typescript
function fn(x: boolean): void;

// parameter isn't compatible with the first overload signature.
function fn(x: string): void; 
This overload signature is not compatible with its implementation signature.

function fn(x: boolean) {}

function fn(x: string): string;

// Return type isn't right should be boolean
function fn(x: number): boolean;
This overload signature is not compatible with its implementation signature.

function fn(x: string | number) {
  return "oops";
}
```

### Writing Good Overloads

Like generics, there are a few guidelines you should follow when using function overloads. 

**Always prefer parameters with union types instead of overloads when possible.**

```typescript
function len(s: string): number;
function len(arr: any[]): number;

function len(x: any) {
  return x.length;
}
```

we can’t invoke it with A value that MIGHT BE(Ambiguous) A string or AN array, because TypeScript can only resolve a function call to a single overload:
```typescript 
len("");  // ok
len([0]); // OK
```

```typescript
// Ambiguous
len(Math.random() > 0.5 ? "hello" : [0]);
```
the above code gets the following error message
```typescript
No overload matches this call.
Overload 1 of 2, '(s: string): number', gave the following error.
Argument of type 'number[] | "hello"' is not assignable to parameter of type 'string'.
Type 'number[]' is not assignable to type 'string'.
Overload 2 of 2, '(arr: any[]): number', gave the following error.
Argument of type 'number[] | "hello"' is not assignable to parameter of type 'any[]'.
Type 'string' is not assignable to type 'any[]'.
```

Because **both overloads have the same argument count and same return type**, we can instead write a non-overloaded version of the function:
```typescript 
function len(x: any[] | string) {
  return x.length;
}
```
### Declaring `this` in a Function

TypeScript will infer what the `this` should be in a function via code flow analysis, for example
```typescript 
const user = {
  id: 123,
  admin: false,
  
  becomeAdmin: function () {
    this.admin = true;
  },
};
```
- TypeScript understands that the function `user.becomeAdmin` has a corresponding `this` which is the outer object user. 

**The JavaScript specification states that you cannot have a parameter called `this`, and so TypeScript uses that syntax space to let you declare the type for `this` in the function body.**
```typescript 
interface DB {
  filterUsers(filter: (this: User) => boolean): User[];
}

const db = getDB();
const admins = db.filterUsers(function (this: User) {
  return this.admin;
});
```
This pattern is common with callback-style APIs, where another object typically controls when your function is called. 

**Note that you need to use function and not arrow functions to get this behavior:**
```typescript
interface DB {
  filterUsers(filter: (this: User) => boolean): User[];
}
 
const db = getDB();
const admins = db.filterUsers(() => this.admin);
  ^The containing arrow function captures the global value of 'this'.
   Element implicitly has an 'any' type because type 'typeof globalThis' has no index signature.
```
