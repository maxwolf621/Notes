## Tuple

```typescript
// Declare a tuple type
let x: [string, number];

// Initialize 
x = ["string", 1]; 

x[0] = "string";
x[1] = 1;
```

## Never

- `never` is not `void`

Built for function that "having not returned value" (e.g. throw Error) or infinity loop

```typescript
// Function returning never must not have a reachable end point
function error(message: string): never {
  throw new Error(message);
}

// Inferred return type is never
function fail() {
  return error("Something failed");
}

// Function returning never must not have a reachable end point
function infiniteLoop(): never {
  while (true) {}
}
```



## Unknown

- [unkown vs any](https://stackoverflow.com/questions/51439843/unknown-vs-any)

Unknown 與 Any 型別有一個共同點，就是: 所有都可以被歸類成 Unknown, Any 。

**`unknown` which is the type-safe counterpart of any**. 

Anything is assignable to unknown, but unknown isn't assignable to anything but itself and any without a type assertion or a control flow based narrowing. 

Likewise, **no operations are permitted on an unknown without first asserting or narrowing to a more specific type.**

```typescript
let value: unknown = 3;
value = "Ian";
value = undefined;
value = null;
value = true;
```

#### Difference btw `any` and `unknown`

至於與 Any 的差別到底在哪，就讓我們繼續往下看:

在 TypeScript 中，僅有 Any 與 Unknown 型別可以被存放任意值，作為更嚴謹的 Unknown 型別，就會有更嚴格的規範。

All the `unknown` declarations are only assignable to `any` or itself

```typescript
let value: unknown;            // declarations
let value1: unknown = value;   // OK
let value2: any = value;       // OK
let value3: number = value;   // Error 
let value4: boolean = value;  // Error
let value5: string = value;   // Error


let vAny: any = 10;          // We can assign anything to any
let vUnknown: unknown =  10; // We can assign anything to unknown just like any 
let s1: string = vAny;     // Any is assignable to anything 
let s2: string = vUnknown; // Invalid; we can't assign vUnknown to any other type (without an explicit assertion)
vAny.method();     // OK; anything goes with any
vUnknown.method(); // Not ok; we don't know anything about this variable

let value: unknown;
let value2: any;
value.substring(); // Error : Object is of type 'unknown'
value2.substring(); // OK
```

### typeof and instanceof


- 通常檢測原始型別都是為用 `typeof` 這個關鍵字。

```typescript
typeof value === ''
```

而通常廣義物件或類別（Class）建造出來的物件則是會用 `instanceof`
```typescript
someObject instanceof ObjectBelongingClass
```