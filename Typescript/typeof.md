# typeof Object & keyof TYPE
- [typeof Object & keyof TYPE](#typeof-object--keyof-type)
  - [reference](#reference)
  - [type of the object](#type-of-the-object)
  - [type of enums](#type-of-enums)
  - [type of object as const](#type-of-object-as-const)
  - [keyOf TypeAlias](#keyof-typealias)
    - [TypeAlias of index signature](#typealias-of-index-signature)

## reference
- [[Day13] TS：什麼！這個 typeof 和我想的不一樣？](https://ithelp.ithome.com.tw/articles/10274229)
- 
## type of the object 

```typescript
enum SEX {
  MALE = 'male',
  FEMALE = 'female'
}
const STUDENT = {
    name : 'john mayer',
    age  :　18,
    sex  : SEX.MALE,
} 

type literalTypes = typeof STUDENT;
```

type `literalTypes` is
```typescript
const STUDENT = {
 name : string ;
 age  : number ;
 sex  : SEX    ;
}
```

Key of literalTypes
```typescript 
type keyOfStudent = keyof typeof STUDENT;
```
type is the key of Student 
```typescript
"name" | "age" | "sex"
```

Get properties via Key
```typescript 
// type valuesOfStudent = string | number
type valuesOfStudent = literalTypes[keyOfStudent];

// ERROR
// keyof + TYPE only
type literalTypes = keyof STUDENT;
```

## type of enums

```typescript 
enum MANUFACTURE {
//   ket = value
  APPLE = 'apple',
  SAMSUNG = 'samsung',
  GOOGLE = 'google',
  SONY = 'sony',
}

// extract value
type Manufacture = `${MANUFACTURE}`; 
// "apple" | "samsung" | "google" | "sony"

// extract key
type ManufactureKeys = keyof typeof MANUFACTURE; 
// "APPLE" | "SAMSUNG" | "GOOGLE" | "SONY"
```

## type of object as const

```typescript
enum SEX {
  MALE = 'male',
  FEMALE = 'female'
}

const constObj = {
    name : 'john mayer',
    age :　18,
    sex : SEX.MALE,
} as const
```

Unlike object type the const object type can get the key of enum type
```typescript
type literalTypes = typeof constObj;

type keyOfconstObj = keyof typeof constObj;
// "name" | "age" | "sex"

type valuesOfStudent = literalTypes[keyOfconstObj];
// type valuesOfStudent = "john mayer" | 18 | SEX.MALE
```

## keyOf TypeAlias 

```typescript 
enum SEX {
  MALE = 'male',
  FEMALE = 'female'
}

type Arrayish = {
  name : "string"
  sex : SEX.MALE; 
};

// LITERAL TYPE OF KEYOF TYPE
type A = keyof Arrayish; 
// type A = keyof Arrayish
```

### TypeAlias of index signature

if index is `number` type
```typescript
type Arrayish = { 
  [n: number]: unknown
};

type A = keyof Arrayish; 
// type A = number
```

**JavaScript object keys are always coerced to a string, so `obj[0]` is always the same as `obj["0"]`.**
```typescript 
type Mapish = { 
   // properties are boolean types
  [k: string]: boolean  
};

type M = keyof Mapish; // type M = string | number
```

