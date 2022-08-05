# Iterators and Generators

Some built-in types like Array, Map, Set, String, Int32Array, Uint32Array, etc. have their Symbol.iterator property already implemented.

- [Iterators and Generators](#iterators-and-generators)
  - [for..of (iterate value)](#forof-iterate-value)
  - [for..in (iterate index)](#forin-iterate-index)

```typescript
function toArray<X>(xs: Iterable<X>): X[] {
  return [...xs]
}
```

## for..of (iterate value)

```typescript
for(let element of Array ){
    //...
}
```

## for..in (iterate index)

```typescript
for(let index in Array){
    //...
}
```

```typescript 
let pets = new Set(["Cat", "Dog", "Hamster"]);

pets["species"] = "mammals";
     Element implicitly has an 'any' type because expression of type '"species"' can't be used to index type 'Set<string>'.  
     Property 'species' does not exist on type 'Set<string>'.(7053)

for (let pet in pets) {
  console.log(pet); // "species"
}
for (let pet of pets) {
  console.log(pet); // "Cat", "Dog", "Hamster"
}
```


```typescript
let pets = new Set(["Cat", "Dog", "Hamster"]);
pets["species"] = 1;
     Element implicitly has an 'any' type because expression of type '"species"' can't be used to index type 'Set<string>'.  
     Property 'species' does not exist on type 'Set<string>'.(7053)

pets["monster"] = 1;
     Element implicitly has an 'any' type because expression of type '"species"' can't be used to index type 'Set<string>'.  
     Property 'species' does not exist on type 'Set<string>'.(7053)

for (let pet in pets) {
  console.log(pet); // "species", "monster"
}
for (let pet of pets) {1
  console.log(pet); // "Cat", "Dog", "Hamster"
}
```

