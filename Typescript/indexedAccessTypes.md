# Indexed Access Type (`objectName[indexName]`)

- [Indexed Access Type (`objectName[indexName]`)](#indexed-access-type-objectnameindexname)
  - [`array[number]`](#arraynumber)
  - [`type` only can be use when indexing not `const`](#type-only-can-be-use-when-indexing-not-const)

Use an indexed access type to look up a specific property on another type:
```typescript
type typeFromObjectB = ObjectB[propertyName];
```

for example 
```typescript
// declaration
type Person = { age : number; name : string;};

// or via interface 
Person { age: number, name: string, alive: boolean };

// get the key/property types
type age = person["age"]; 
     ^ type age = number

type I1 = Person["age" | "name"]; 
// type I1 = string | number

// keyof type
type I2 = Person[keyof Person]; 
// type I2 = string | number | boolean

// type alias
type AliveOrName = "alive" | "name";
type I3 = Person[AliveOrName];  
// type I3 = string | boolean

// ERROR
type age = typeof person["age"];
                  ^ 'person' only refers to a type, 
                  ^  but is being used as a value here.

```

## Object types within Array

Another example of indexing with an arbitrary type is using `number` to get the type of an array’s elements. 

We can combine this with typeof to conveniently capture the element type of an array literal:
```typescript
const MyArray = [
  { name: "Alice", age: 15 },
  { name: "Bob", age: 23 },
  { name: "Eve", age: 38 },
];
 
type Person = typeof MyArray[number]; 
// type Person = { name: string; age: number;}

// array[number]["keyName"]
type Age = typeof MyArray[number]["age"]; 
// type Age = number

type Age2 = Person["age"]; 
// type Age2 = number      
```

## index can not be used as `const` type

```typescript
const key = "age";
type age = Person[key];
     ^ Type 'key' cannot be used as an index type.
       'key' refers to a value, but is being used 
       as a type here. Did you mean 'typeof key'?
```


