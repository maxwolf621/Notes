# Dictionary

- [Dictionary](#dictionary)
  - [Reference](#reference)
  - [Object Type Indexed Signature](#object-type-indexed-signature)
  - [Dictionary via Map Class](#dictionary-via-map-class)
  - [Record](#record)
  - [Map VS Record](#map-vs-record)

## Reference 
- [TypeScript 中的 Hashmap 和字典介面](https://www.delftstack.com/zh-tw/howto/typescript/hashmap-or-dictionary-interface-in-typescript/)
- [TypeScript hashmap/dictionary interface](https://stackoverflow.com/questions/42211175/typescript-hashmap-dictionary-interface)

## Object Type Indexed Signature

`[index: string] : string | number` :
1. tell TypeScript that the object can have any string-based key
2. For all key entries the value MUST be a `string | number` type.
```typescript
Interface IHash{
    [index : string] : string | number;
}

let dictionary : IHash = {
    name : "john"
    age : 18;
}
console.log(dictionary);
```
console prints :
```bash
[LOG]: {
  "name": "john",
  "age": 18
} 
```


## Dictionary via Map Class 

```typescript 
let mapValue = new Map<object, string>();

let key = new Object();

mapValue.set(key, "Ibrahim Alvi");

console.log(mapValue.get(key));
```

## Record

```typescript
interface CatInformation {
    age: number;
    breed: string;
}
type CatName = "Muhammad" | "Ibrahim" | "Alvi";

const cats: Record<CatName, CatInformation> = {
    Muhammad: { age: 10, breed: "Persian" },
    Ibrahim: { age: 5, breed: "Maine Coon" },
    Alvi: { age: 16, breed: "British Shorthair" },
};

console.log(`Muhammad's cat details are :
            ${JSON.stringify(cats.Muhammad)}
            ,Ibrahim cat details are :
            ${JSON.stringify(cats.Ibrahim)},
            Alvi cat details are :${JSON.stringify(cats.Alvi)},`);

```
console shows :
```bash
[LOG]: "Muhammad's cat details are : {"age":10,"breed":"Persian"}
            ,Ibrahim cat details are : {"age":5,"breed":"Maine Coon"},
            Alvi cat details are :{"age":16,"breed":"British Shorthair"}," 

```


## Map VS Record
- [Map vs Object in JavaScript](https://stackoverflow.com/questions/18541940/map-vs-object-in-javascript)

A Map object can iterate its elements in insertion order - a for..of loop will return an array of [key, value] for each iteration which means it requires more memory in order to maintain the insertion order. 

Record is defined as
```typescript 
type Record<K extends keyof any, T> = {
    [P in K]: T;
}
```


While Map is a native JS ES6 data structure.    
Record is merely a representative way of saying, **this object is going to be used a key, value map of a specific data type**" 

It's a plain object created using `{}`. The Map object on the other hands has some unique characteristics described here and needs to be instantiated as `new Map()`