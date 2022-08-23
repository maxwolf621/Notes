# mapped Types
- [mapped Types](#mapped-types)
  - [index signature](#index-signature)
  - [`[Proeprty keyOf Type]`](#proeprty-keyof-type)
  - [Mapping Modifiers (`-` & `+`)](#mapping-modifiers----)
  - [Key Remapping via `as`](#key-remapping-via-as)
    - [working with conditional types](#working-with-conditional-types)

Mapped types build on the syntax for index signatures, which are used to **declare the types of properties** which have not been declared ahead of time:

## index signature

```typescript
type Horse = {};

type OnlyBoolsAndHorses = {
  [key: string]: boolean | Horse;
};

const conforms: OnlyBoolsAndHorses = {
  del: true,
  rodney: false,
};
```

## `[Proeprty keyOf Type]`

```typescript
type OptionsFlags<Type> = {
    // take key from TYPE
    [Property in keyof Type]: boolean;
};

type FeatureFlags = {
  darkMode: () => string;
  newUserProfile: () => void;
};
 
type FeatureOptions = OptionsFlags<FeatureFlags>;
     ^ type FeatureOptions = {
        darkMode: boolean;
        newUserProfile: boolean;
      }
```

## Mapping Modifiers (`-` & `+`)

There are two additional modifiers which can be applied during mapping: readonly and `?` which affect mutability and optionality respectively.

You can remove or add these modifiers by prefixing with `-` or `+`.     
If you don’t add a prefix, then `+` is assumed.

```typescript 
// Removes 'readonly' attributes from a type's properties
type CreateMutable<Type> = {
  -readonly [key in keyof Type]: Type[key];
};
 
type LockedAccount = {
  readonly id: string;
  readonly name: string;
};
 
type UnlockedAccount = CreateMutable<LockedAccount>;
/** 
 * readonly properties are removeed
 * 
 * type UnlockedAccount = {
 *     id: string;
 *     name: string;
 * }
 */
```

## Key Remapping via `as`

```typescript 
type Getters<Type> = {
    [Property in keyof Type as `get${Capitalize<string & Property>}`]: () => Type[Property]
};

interface Person {
    name: string;
    age: number;
    location: string;
}
 
type LazyPerson = Getters<Person>;
     ^ Type LazyPerson = {
        getName: () => string;
        getAge: () => number;
        getLocation: () => string;
      }


// Remove the 'kind' property
type RemoveKindField<Type> = {
    [key in keyof Type as Exclude<Property, "kind">]: Type[key]
};
 
interface Circle {
    kind: "circle";
    radius: number;
}
 
type KindlessCircle = RemoveKindField<Circle>;
/**           
    type KindlessCircle = {
        radius: number;
    }
*/


type EventConfig<Events extends { kind: string }> = {
    [E in Events as E["kind"]]: (event: E) => void;
}
 
type SquareEvent = { kind: "square", x: number, y: number };
type CircleEvent = { kind: "circle", radius: number };
 
type Config = EventConfig<SquareEvent | CircleEvent>
       
type Config = {
    square: (event: SquareEvent) => void;
    circle: (event: CircleEvent) => void;
}
```

### working with conditional types

for example here is a mapped type using a conditional type which returns either a `true` or `false` depending on whether an object has the property `pii` set to the literal `true`:
```typescript 
type DBFields = {
  id: { format: "incrementing" };
  name: { type: string; pii: true };
};

type ExtractPII<Type> = {
  [Property in keyof Type]: Type[Property] extends { pii: true } ? true : false;
};
/**
 * {format : "incrementing"} extends {pii : true}
 * {type : string; pii : ture} extends {pii : true}
 */


type ObjectsNeedingGDPRDeletion = ExtractPII<DBFields>;
      ^ type ObjectsNeedingGDPRDeletion = {
          id: false;
          name: true;
      }
```
