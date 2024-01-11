{%hackmd @Edixon/dracula %}
# Module

[TOC]

In TypeScript, just as **in ECMAScript 2015, any file containing a `top-level import` or `export` is considered a module**.   

Conversely, a file without any top-level import or export declarations is treated as a script whose contents are available in the global scope (and therefore to modules as well).   

```typescript 
/**
 * export module -> allow to be used as module
 */
export [variable | function | class | type alias | interface]

/**
 * import module -> use the module
 */
import {variable | function | class | type alias | interface} from filename
```


For example 
```typescript  
/**
 * export 
 * @filename : StringValidator.ts
 */
export interface StringValidator {
  isAcceptable(s: string): boolean;
}

/**
 * import StringValidator.ts 
 * @filename : ZipCodeValidator.ts
 */
import { StringValidator } from "./StringValidator";

export const numberRegexp = /^[0-9]+$/;

export class ZipCodeValidator implements StringValidator {
  isAcceptable(s: string) {
    return s.length === 5 && numberRegexp.test(s);
  }
}
```

### export `type` & `interface`
```typescript
// @filename: animals.ts
export type Cat = { 
  breed: string; 
  yearOfBirth: number 
};

export interface Dog {
  breeds: string[];
  yearOfBirth: number;
}
 
// @filename: zoo.ts
import { Cat, Dog } from "./animals";
type Animals = Cat | Dog;
```

### Re-export

**Often** modules extend other modules, and **partially** expose some of their features. 

```typescript
export * from "./StringValidator"; 
// exports 'StringValidator' interface

export * from "./ZipCodeValidator"; 
// exports 'ZipCodeValidator' class and 'numberRegexp' constant value

export * from "./ParseIntBasedZipCodeValidator"; 
// exports the 'ParseIntBasedZipCodeValidator' class
// and re-exports 'RegExpBasedZipCodeValidator' as alias
// of the 'ZipCodeValidator' class from 'ZipCodeValidator.ts'
// module.
```

### `export default` 

you can have only one `export default` keyword in a module



```typescript
// @filename : MyClass.ts
export default class MyClass { /** implementation **/ }

// @filename : MyFunc.ts
export default function getThing() {
  return "thing";
}
```

Default import with Alias Name
```typescript
// app.ts
import c from "./MyClass"; 
import f from "./MyFunc";

let x = new c();
console.log(f());
```

## Import 

### Inline type imports

```typescript
// @filename: animal.ts
export type Cat = { breed: string; yearOfBirth: number };
export type Dog = { breeds: string[]; yearOfBirth: number };
export const createCatName = () => "fluffy";
 
// animal.ts imports to @filename: app.ts
import { Cat, createCatName, Dog } from "../InlineTypeImport/animal";

export type Animals = Cat | Dog;
const name = createCatName();
```

### Import as alias name (namespace)

#### Scenario 1

```typescript 
// @filename: maths.ts
export var pi = 3.14;
export let squareTwo = 1.41;
export const phi = 1.61;

export class RandomNumberGenerator {}
 
export function absolute(num: number) {
    if (num < 0) return num * -1;return num;
}


// @filename: print.ts
import { pi as π } from "./maths.js";
console.log(π);
```

#### Scenario 2
Use the namespace import pattern if you're importing a large number of things
```typescript
// @filename : MyLargeModule.ts
export class Dog { ... }
export class Cat { ... }
export class Tree { ... }
export class Flower { ... }

// @filename : Consumer.ts
import * as myLargeModule from "./MyLargeModule.ts";
let x = new myLargeModule.Dog();
```

#### Scenario 3

No augment any objects in the original module.

```typescript 
/**
 * @filename : Calculator.ts
 */
export class Calculator {
    // ...
}
export function test(c: Calculator, input: string) {
  for (let i = 0; i < input.length; i++) {
    c.handleChar(input[i]);
  }
  console.log(`result of '${input}' is '${c.getResult()}'`);
}

/**
 * @filenmae : ProgrammerCalculator.ts
 * ProgrammerCalculator THAT EXTENDS calculator
 */
import { Calculator } from "./Calculator";
export { test } from "./Calculator"; 
// Also, export the helper function

class ProgrammerCalculator extends Calculator {
    //..
}   
// Export the new extended calculator as Calculator
export { ProgrammerCalculator as Calculator };
```
- The new module `ProgrammerCalculator` exports an API shape similar to that of the original Calculator module, but does not augment any objects in the original module.

```typescript
/**
 * @filename : run.ts
 */
import { Calculator, test } from "./ProgrammerCalculator";

let c = new Calculator(2);
test(c, "001+010="); // prints 3
```


## `export = module` & `import = require('module')`

When exporting a module using `export =`, TypeScript-specific import `[moduleAliasName] = require("[fileName]")` must be used to import the module.

```typescript
/**
 * @filename : ZipCodeValidator.ts
 */
let numberRegexp = /^[0-9]+$/;

class ZipCodeValidator {
  isAcceptable(s: string) {
    return s.length === 5 && numberRegexp.test(s);
  }
}

export = ZipCodeValidator;


/**
 * @filename : Test.ts
 */
import zip = require("./ZipCodeValidator");

let strings = ["Hello", "98052", "101"];

let validator = new zip();

// Show whether each string passed each validator
strings.forEach((s) => {
  console.log(
    `"${s}" - ${validator.isAcceptable(s) ? "matches" : "does not match"}`
  );
});
```

## (CommonJS) `module.exports` & `reuiqre`

```typescript 
/** 
 * @filename : maths.ts
 */
function absolute(num: number) {
  if (num < 0) return num * -1;
  return num;
}
 
module.exports = {
  pi: 3.14,
  squareTwo: 1.41,
  phi: 1.61,
  absolute,
};

/**
 * @filename : run.ts
 */

// kidda like giving a namespace
const maths = require("maths");
maths.pi;

const { squareTwo } = require("maths");
squareTwo;
```

## Structuring modules tips

- **If you’re only exporting a single `class` or `function`, use `export default`.**  
- If you’re exporting multiple objects, put them all at top-level. Explicitly list imported names.
    ```typescript
    // @filename : multipleObjects.ts
    export class SomeType {
      /* ... */
    }
    export function someFunc() {
      /* ... */
    }

    // @filename : app.ts
    // list object names
    import { SomeType, someFunc } from "./multipleObjects";

    let x = new SomeType();
    let y = someFunc();
    ```

---
References
[Day27 :【TypeScript 學起來】Module 模組](https://ithelp.ithome.com.tw/articles/10280543)
