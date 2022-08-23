# Conditional Types

- [Conditional Types](#conditional-types)
  - [Reference](#reference)
  - [`extends` keyword for Interface](#extends-keyword-for-interface)
  - [Condition Types](#condition-types)
    - [array](#array)
  - [`infer` keyword](#infer-keyword)
  - [Distributive Conditional Types](#distributive-conditional-types)
    - [never extends](#never-extends)
  - [Exclude](#exclude)
  - [Extract](#extract)
  - [Pick](#pick)

## Reference

- [TS關鍵字extends用法總結](https://juejin.cn/post/6998736350841143326#heading-5)

## `extends` keyword for Interface

```typescript
interface T1 {
  name: string
}

interface T2 {
  sex: string
}

interface T3 extends T1,T2 {
  age: number
}
  
const t3: T3 = {
  name: 'Harry',
  sex: male,
  age: 18
}
```


## Condition Types

`x extends y` in conditional type meaning `x contains y`


```typescript
interface Animal {
  eat(): void
}

interface Dog extends Animal {
  bite(): void
}

// if dog extends animal
type A = Dog extends Animal ? string : number
```

```typescript 
type MessageOf<T> = T extends { message: unknown } ? T["message"] : never;
 
interface Email {
  message: string;
}
 
interface Dog {
  bark(): void;
}
 
type EmailMessageContents = MessageOf<Email>;
              // type EmailMessageContents = string
 
type DogMessageContents = MessageOf<Dog>;
             // type DogMessageContents = never
```

### array 

```typescript 
type Flatten<T> = T extends any[] ? T[number] : T;
 
// Extracts out the element type.
type Str = Flatten<string[]>;
     
type Str = string
 
// Leaves the type alone.
type Num = Flatten<number>;
     
type Num = number
```

## `infer` keyword

Conditional types provide us with a way to infer from types we compare against in the true branch using the `infer` keyword. 

For example, we could have inferred the element type in `Flatten` instead of fetching it out manually with an indexed access type:
```typescript 
type Flatten<T> = T extends any[] ? T[number] : T;
// same as
type Flatten<Type> = Type extends Array<infer Item> ? Item : Type;
```
- the `infer` keyword declaratively introduces a new generic type variable named `Item` instead of specifying how to retrieve the element type of T within the true branch. 
  

We can write some useful helper type aliases using the `infer` keyword. For example, for simple cases, we can extract the return type out from function types:
```typescript
type GetReturnType<Type> = Type extends (...args: never[]) => infer Return
  ? Return
  : never;
 
type Num = GetReturnType<() => number>;
     
type Num = number
 
type Str = GetReturnType<(x: string) => string>;
     
type Str = string
 
type Bools = GetReturnType<(a: boolean, b: boolean) => boolean[]>;
      
type Bools = boolean[]
```

- When inferring from a type with multiple call signatures (such as the type of an overloaded function), inferences are made from the last signature (which, presumably, is the most permissive catch-all case). It is not possible to perform overload resolution based on a list of argument types.
```typescript
declare function stringOrNum(x: string): number;
declare function stringOrNum(x: number): string;
declare function stringOrNum(x: string | number): string | number;
 
type T1 = ReturnType<typeof stringOrNum>;
     ^ type T1 = string | number
```

## Distributive Conditional Types

If we plug a union type into ToArray, then the conditional type will be applied to each member of that union.
```typescript
type ToArray<Type> = Type extends any ? Type[] : never;
```

StrArrOrNumArr maps over each member type of the union distributivity
```typescript
type StrArrOrNumArr = ToArray<string | number>;
// as 
type StrArrOrNumArr = ToArray<string>;
type StrArrOrNumArr = ToArray<number>;
```

```typescript
type StrArrOrNumArr = string[] | number[]
```

Typically, distributivity is the desired behavior.  
To avoid that behavior, you can surround each side of the extends keyword with square brackets.
```typescript 
type ToArrayNonDist<Type> = [Type] extends [any] ? Type[] : never;
 
// 'StrArrOrNumArr' is no longer a union.
type StrArrOrNumArr = ToArrayNonDist<string | number>;
        ^ type StrArrOrNumArr = (string | number)[]

type P<T> = [T] extends ['x'] ? string : number;
type A1 = P<'x' | 'y'> // number
type A2 = P<never> // string
```

### never extends

```typescript
// never is a subtype of and assignable to every type
type A1 = never extends 'x' ? string : number; // string

type P<T> = T extends 'x' ? string : number;
type A2 = P<never> // never
```

## Exclude 

```typescript 
type Exclude<T, U> = T extends U ? never : T
```

keep `key1` exclude others
```typescript 
type A = Exclude<'key1' | 'key2', 'key2'> // 'key1'
```

## Extract

Extract T from U
```typescript 
type Extract<T, U> = T extends U ? T : never
type A = Extract<'key1' | 'key2', 'key1'> // 'key1'
```

## Pick 

`Pick<T, K>` : pick the same properties from T as same as K
```typescript
type Pick<T, K extends keyof T> = {
    [Property in K]: ObjectType[Property]
}

interface A {
    name: string;
    age: number;
    sex: number;
}

type A1 = Pick<A, 'name'|'age'>
// Pick<A, 'name'>
// Puck<A, 'age>

// ERROR , there is noSuckKey property i
type A2 = Pick<A, 'name'|'noSuchKey'>
```
