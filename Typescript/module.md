# Module

- [Module](#module)
  - [Reference](#reference)
  - [Exporting declaration](#exporting-declaration)
    - [export type & interface](#export-type--interface)
    - [`as` (alias-name)](#as-alias-name)
    - [Re-export](#re-export)
    - [`export default`](#export-default)
  - [Import](#import)
    - [Inline type imports](#inline-type-imports)
    - [Import `* as alias`](#import--as-alias)
  - [`export = module` and `import = require('module')`](#export--module-and-import--requiremodule)
  - [(CommonJS Syntax) module.exports](#commonjs-syntax-moduleexports)
  - [Structuring modules](#structuring-modules)

In TypeScript, just as **in ECMAScript 2015, any file containing a top-level import or export is considered a module**. 

Conversely, a file without any top-level import or export declarations is treated as a script whose contents are available in the global scope (and therefore to modules as well).

## Reference
- [Day27 :【TypeScript 學起來】Module 模組](https://ithelp.ithome.com.tw/articles/10280543)

## Exporting declaration 

```typescript 
export + variable | function | class | type alias | interface
```

You can have multiple `export` per `.ts` file, for example
```typescript 
// StringValidator.ts
export interface StringValidator {
  isAcceptable(s: string): boolean;
}

// ZipCodeValidator.ts
import { StringValidator } from "./StringValidator";
export const numberRegexp = /^[0-9]+$/;
export class ZipCodeValidator implements StringValidator {
  isAcceptable(s: string) {
    return s.length === 5 && numberRegexp.test(s);
  }
}
```

### export type & interface
```typescript
// @filename: animal.ts
export type Cat = { 
  breed: string; 
  yearOfBirth: number 
};
 
export interface Dog {
  breeds: string[];
  yearOfBirth: number;
}
 
// @filename: app.ts
import { Cat, Dog } from "./animal.js";
type Animals = Cat | Dog;
```

### `as` (alias-name)

```typescript
class ZipCodeValidator implements StringValidator {
    //..
}
export { ZipCodeValidator };
export { ZipCodeValidator as mainValidator };
```

### Re-export

Often modules extend other modules, and **partially** expose some of their features. 

```typescript
export * from "./StringValidator"; // exports 'StringValidator' interface
export * from "./ZipCodeValidator"; // exports 'ZipCodeValidator' class and 'numberRegexp' constant value

export * from "./ParseIntBasedZipCodeValidator"; 
// exports the 'ParseIntBasedZipCodeValidator' class
// and re-exports 'RegExpBasedZipCodeValidator' as alias
// of the 'ZipCodeValidator' class from 'ZipCodeValidator.ts'
// module.
```

### `export default` 

If a module’s primary **purpose is to house one specific** export, then you should consider exporting it as a default export. 
This makes both importing and actually using the import a little easier. 

**for `export default` you can only have one default export per file.**
```typescript
// MyClass.ts -- using default export
export default class MyClass { /* ... */ }

// MyFunc.ts
export default function getThing() {
  return "thing";
}
```

Default import with Alias Name
```typescript
// app.ts
import t from "./MyClass"; 
import f from "./MyFunc";

let x = new t();
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

### Import `* as alias`

`*` : all exports declarations , no `{ .. }` 
```typescript
import * as validator from "./ZipCodeValidator";
let myValidator = new validator.ZipCodeValidator();
```

## `export = module` and `import = require('module')`

When exporting a module using `export =`, TypeScript-specific import `module = require("module") `must be used to import the module.

```typescript
// ZipCodeValidator.ts
let numberRegexp = /^[0-9]+$/;

class ZipCodeValidator {
  isAcceptable(s: string) {
    return s.length === 5 && numberRegexp.test(s);
  }
}

export = ZipCodeValidator;


// Test.ts
import zip = require("./ZipCodeValidator");

let strings = ["Hello", "98052", "101"];

// new zip()
let validator = new zip();

// Show whether each string passed each validator
strings.forEach((s) => {
  console.log(
    `"${s}" - ${validator.isAcceptable(s) ? "matches" : "does not match"}`
  );
});
```

## (CommonJS Syntax) module.exports

```typescript 
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

const maths = require("maths");
maths.pi;

// structure
const { squareTwo } = require("maths");
squareTwo;

```

## Structuring modules

If you’re only exporting a single `class` or `function`, use `export default`.  

If you’re exporting multiple objects, put them all at top-level. Explicitly list imported names.
```typescript
// multipleObjects.ts
export class SomeType {
  /* ... */
}
export function someFunc() {
  /* ... */
}

// app.ts
// list object names
import { SomeType, someFunc } from "./MyThings";
let x = new SomeType();
let y = someFunc();
```

Use the namespace import pattern if you’re importing a large number of things
```typescript
// MyLargeModule.ts
export class Dog { ... }
export class Cat { ... }
export class Tree { ... }
export class Flower { ... }

// Consumer.ts
import * as myLargeModule from "./MyLargeModule.ts";
let x = new myLargeModule.Dog();
```

```typescript
export class Calculator {
    // ...
}
export function test(c: Calculator, input: string) {
  for (let i = 0; i < input.length; i++) {
    c.handleChar(input[i]);
  }
  console.log(`result of '${input}' is '${c.getResult()}'`);
}
```


export name 
```typescript
// ProgrammerCalculator THAT EXTENDS calculator
import { Calculator } from "./Calculator";
export { test } from "./Calculator"; // Also, export the helper function

class ProgrammerCalculator extends Calculator {
    //..
}   
// Export the new extended calculator as Calculator
export { ProgrammerCalculator as Calculator };
```
- The new module `ProgrammerCalculator` exports an API shape similar to that of the original Calculator module, but does not augment any objects in the original module.

```typescript
import { Calculator, test } from "./ProgrammerCalculator";
let c = new Calculator(2);
test(c, "001+010="); // prints 3
```


