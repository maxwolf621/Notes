# Type Alias

```typescript
type Name = string;
type NameResolver = () => string;
type NameOrResolver = Name | NameResolver;
/**
 * instead using getName(n : string | NameResolver)
 * via type alias   
 */
function getName(n: NameOrResolver): Name {
    if (typeof n === 'string') {
        return n;
    } else {
        return n();
    }
}
```


## Interface VS Type

Interface
- Merge the same interface

If we have repeat declaration
```typescript
interface Cat {
    name: string;
}
interface Cat {
    age: number;
}
```
For compiler (merge them)
```typescript
interface Cat {
    name: string;
    age: number;
}
```

type 
- 可以只用來宣告基本的類型型別

這點是只有 type 能夠做到的，因為只要使用了 Interface 就必定會看到 {} ，而 type 可以被用來為型別換個新別名:
```typescript
type Name = string

type Human = Male | Female
type PetList = [Dog, Pet]
```

### `typeof(Instance)`

To get Instance's type

```typescript
let div = document.createElement('div');
type Div = typeof div
```

Interface extends from Type
```typescript
type Biological = {
  name: string
  age: number
};
interface Human extends Biological { 
  IQ: number; 
}
```