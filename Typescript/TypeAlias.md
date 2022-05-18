# Type Alias (`type`)

```typescript
type Name = string;
type NameResolver = () => string;
type NameOrResolver = Name | NameResolver;
type Human = Male | Female
type PetList = [Dog, Pet]

/**
 * using type instead getName(n : string | NameResolver)   
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

Interface can merge the same interface   
Interface can extend Type
- for example
```typescript
interface Cat {
    name: string;
}
interface Cat {
    age: number;
}

// compiler
interface Cat {
    name: string;
    age: number;
}

type Biological = {
  name: string
  age: number
};
interface Human extends Biological { 
  IQ: number; 
}
```

