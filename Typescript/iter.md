# Iterators(`in` & `of`) and Generators

Some built-in types like Array, Map, Set, String, Int32Array, Uint32Array, etc. have their `Symbol.iterator` property already implemented.

- [Iterators(`in` & `of`) and Generators](#iteratorsin--of-and-generators)
  - [for let value of Object](#for-let-value-of-object)
  - [for keys in Object](#for-keys-in-object)

```typescript
function toArray<X>(xs: Iterable<X>): X[] {
  return [...xs]
}
```
## for let value of Object
```typescript
for(let element of object_type){
    //...
}
```
## for keys in Object

```typescript
for(let index in object_type){
    //...
}
```

```typescript
let pets = new Set(["Cat", "Dog", "Hamster"]);

pets["species"] = 1;
     ^ Element implicitly has an 'any' type 
       because expression of type '"species"' 
       can't be used to index type 'Set<string>'.  
       Property 'species' does not exist on type 'Set<string>'.(7053)

pets["monster"] = 1;
     ^ Element implicitly has an 'any' type
       because expression of type '"species"' 
       can't be used to index type 'Set<string>'.  
       Property 'species' does not exist on type 'Set<string>'.(7053)

for (let pet in pets) {
  console.log(pet); // "species", "monster"
}
for (let pet of pets) {1
  console.log(pet); // "Cat", "Dog", "Hamster"
}
```

