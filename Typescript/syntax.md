```typescript
type name<literalType> = {
    // mapped type
    // get the key (properties) in literalType
    [key in keyof literalType] :

    // indexed signature
    [k : string ] : boolean | number ;

    // remove readonly (keys)properties in literalType
    // as optional properties
    -readonly [key in keyof literalType]+? : literalType[key];

    // key Remapping 
    [Property in keyof Type as `get${Capitalize<string & Property>}`]: 
    () => Type[Property]

    // key exclude 
    [key in keyof Type as Exclude<Property, "kind">]: Type[key];

    // remapping value as key
    [Key in literalType as literalType[keyName]] : (K : Key) => void
}
```


```typescript
type Flatten<T> = T extends any[] ? T[number] : T;
// same as
type Flatten<Type> = Type extends Array<infer Item> ? Item : Type;
```


## indexed type
```typescript
type typeFromObjectB = ObjectB[propertyName];
```

```typescript
type Person = { age : number; name : string;};
// or via interface 
Person { age: number, name: string, alive: boolean };

type age = person["age"]; // number
type I1 = Person["age" | "name"]; // string | number
type I2 = Person[keyof Person]; // string | number | boolean
type AliveOrName = "alive" | "name";
type I3 = Person[AliveOrName];   // string | boolean

--------------------------------------------------------------------

const MyArray = [
  { name: "Alice", age: 15 },
  { name: "Bob", age: 23 },
  { name: "Eve", age: 38 },
];
 
type Person = typeof MyArray[number]; // { name: string; age: number;}
type Age = typeof MyArray[number]["age"]; // number
type Age2 = Person["age"]; // number      

// YOU CANT DO THESE
type age = typeof person["age"];
const key = "age";
type age = Person[key]; 
```
