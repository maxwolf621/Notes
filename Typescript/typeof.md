# typeof & keyof

- [typeof & keyof](#typeof--keyof)
  - [reference](#reference)
  - [type of object](#type-of-object)
  - [type of enums](#type-of-enums)
  - [type of object as const](#type-of-object-as-const)

## reference

- [[Day13] TS：什麼！這個 typeof 和我想的不一樣？](https://ithelp.ithome.com.tw/articles/10274229)

## type of object 

```typescript
enum SEX {
  MALE = 'male',
  FEMALE = 'female'
}

const STUDENT = {
    name : 'john mayer',
    age :　18,
    sex : SEX.MALE,
} 
/**
 * const STUDENT = {
 *  name : string;
 *  age :　number;
 *  sex : SEX    ;
 * }
 */
type literalTypes = typeof STUDENT;

// "name" | "age" | "sex"
type keyOfStudent = keyof typeof STUDENT;

// type valuesOfStudent = string : number
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
type Manufacture = `${MANUFACTURE}`; // "apple" | "samsung" | "google" | "sony"

// extract key
type ManufactureKeys = keyof typeof MANUFACTURE; // "APPLE" | "SAMSUNG" | "GOOGLE" | "SONY"
```


## type of object as const

```typescript
enum SEX {
  MALE = 'male',
  FEMALE = 'female'
}

const STUDENT = {
    name : 'john mayer',
    age :　18,
    sex : SEX.MALE,
} as const
/**
 * const STUDENT = {
 *  name : string;
 *  age :　number;
 *  sex : SEX    ;
 * }
 */
type literalTypes = typeof STUDENT;

// "name" | "age" | "sex"
type keyOfStudent = keyof typeof STUDENT;
// type valuesOfStudent = "john mayer" | 18 | SEX.MALE
type valuesOfStudent = literalTypes[keyOfStudent];
```