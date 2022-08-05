# Module

- [Module](#module)
  - [Export](#export)
    - [Exporting a declaration (variable, function, class, type alias, or interface)](#exporting-a-declaration-variable-function-class-type-alias-or-interface)
    - [Export `as` statements](#export-as-statements)
    - [Re-export](#re-export)
    - [export default and default import](#export-default-and-default-import)
  - [Import](#import)
    - [Import a single export from a module](#import-a-single-export-from-a-module)
    - [Import the entire module into a single variable](#import-the-entire-module-into-a-single-variable)
  - [export = and import = require()](#export--and-import--require)
  - [Guidance for structuring modules](#guidance-for-structuring-modules)

In TypeScript, just as in ECMAScript 2015, any file containing a top-level import or export is considered a module. Conversely, a file without any top-level import or export declarations is treated as a script whose contents are available in the global scope (and therefore to modules as well).

## Export

### Exporting a declaration (variable, function, class, type alias, or interface)


You can have multiple `export` per `.ts` file
```typescript 
// StringValidator.ts
export interface StringValidator {
  isAcceptable(s: string): boolean;
}


// ZipCodeValidator.ts
import { StringValidator } from "./StringValidator";

// multiple exports
export const numberRegexp = /^[0-9]+$/;
export class ZipCodeValidator implements StringValidator {
  isAcceptable(s: string) {
    return s.length === 5 && numberRegexp.test(s);
  }
}
```

### Export `as` statements

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
export * from "./ParseIntBasedZipCodeValidator"; //  exports the 'ParseIntBasedZipCodeValidator' class
// and re-exports 'RegExpBasedZipCodeValidator' as alias
// of the 'ZipCodeValidator' class from 'ZipCodeValidator.ts'
// module.
```

### export default and default import 

If a module’s primary purpose is to house one specific export, then you should consider exporting it as a default export. 
This makes both importing and actually using the import a little easier. 

you can only have one default export per file.
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
import t from "./MyClass"; // same as import MyClass from "./MyClass";
import f from "./MyFunc";
let x = new t();
console.log(f());
```

## Import 


### Import a single export from a module

```typescript 
import { ZipCodeValidator } from "./ZipCodeValidator";
let myValidator = new ZipCodeValidator();
```

imports can also be renamed via `as aliasName` 
```typescript 
import { ZipCodeValidator as ZCV } from "./ZipCodeValidator";
let myValidator = new ZCV();
```

### Import the entire module into a single variable

Use it to access the module exports
```typescript
import * as validator from "./ZipCodeValidator";
let myValidator = new validator.ZipCodeValidator();
```

## export = and import = require()

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

## Guidance for structuring modules


If you’re only exporting a single `class` or `function`, use `export default`.

If you’re exporting multiple objects, put them all at top-level. 
- Explicitly list imported names
```typescript
// multipleObjects.ts
export class SomeType {
  /* ... */
}
export function someFunc() {
  /* ... */
}

// app.ts
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


// ProgrammerCalculator
import { Calculator } from "./Calculator";
class ProgrammerCalculator extends Calculator {
    //..
}   
// Export the new extended calculator as Calculator
export { ProgrammerCalculator as Calculator };
// Also, export the helper function
export { test } from "./Calculator";
```
- The new module `ProgrammerCalculator` exports an API shape similar to that of the original Calculator module, but does not augment any objects in the original module.

```typescript
import { Calculator, test } from "./ProgrammerCalculator";
let c = new Calculator(2);
test(c, "001+010="); // prints 3
```


