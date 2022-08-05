# Namespaces

- [Reference](https://www.typescriptlang.org/docs/handbook/namespaces.html)

- [Namespaces](#namespaces)
  - [Multi-file namespaces](#multi-file-namespaces)
  - [Aliases](#aliases)

Group the code
```typescript 
// Gather the codes
namespace Validation {
  export interface StringValidator {
    isAcceptable(s: string): boolean;
  }
  const lettersRegexp = /^[A-Za-z]+$/;
  const numberRegexp = /^[0-9]+$/;
  
  export class LettersOnlyValidator implements StringValidator {
    isAcceptable(s: string) {
      return lettersRegexp.test(s);
    }
  }
  export class ZipCodeValidator implements StringValidator {
    isAcceptable(s: string) {
      return s.length === 5 && numberRegexp.test(s);
    }
  }
}

// Some samples to try
let strings = ["Hello", "98052", "101"];
// Validators to use
let validators: { [s: string]: Validation.StringValidator } = {};
validators["ZIP code"] = new Validation.ZipCodeValidator();
validators["Letters only"] = new Validation.LettersOnlyValidator();

// Show whether each string passed each validator
for (let s of strings) {
  for (let name in validators) {
    console.log(
      `"${s}" - ${
        validators[name].isAcceptable(s) ? "matches" : "does not match"
      } ${name}`
    );
  }
}
```

## Multi-file namespaces

Even though the files are separate, they can each contribute to the same namespace and can be consumed as if they were all defined in one place. Because there are dependencies between files, **we’ll add reference tags `////  <reference path="Validation.ts" />` to tell the compiler about the relationships between the files. Our test code is otherwise unchanged.**

```typescript
// Validation.ts
namespace Validation {
  export interface StringValidator {
    isAcceptable(s: string): boolean;
  }
}

// LettersOnlyValidator.ts
/// <reference path="Validation.ts" />
namespace Validation {
  
  // ...
  
  export class LettersOnlyValidator implements StringValidator {
    isAcceptable(s: string) {
        //...
    }
  }
}


// ZipCodeValidator.ts
/// <reference path="Validation.ts" />
namespace Validation {
  
  // ...
  export class ZipCodeValidator implements StringValidator {
    isAcceptable(s: string) {
        // ...    
    }
  }
}
```

Once there are multiple files involved, we’ll need to make sure all of the compiled code gets loaded

```typescript
tsc --outFile sample.js Test.ts
```

The compiler will automatically order the output file based on the reference tags present in the files. You can also specify each file individually:
```typescript
tsc --outFile sample.js Validation.ts LettersOnlyValidator.ts ZipCodeValidator.ts Test.ts
```

## Aliases

we can also use `import q = x.y.z` to create shorter names for commonly-used objects. 

Not to be confused with the import `x = require("name")` syntax used to load modules, this syntax simply creates an alias for the specified symbol. 

You can use these sorts of imports (commonly referred to as aliases) for any kind of identifier, including objects created from module imports.
```typescript
namespace Shapes {
  export namespace Polygons {
    export class Triangle {}
    export class Square {}
  }
}

import polygons = Shapes.Polygons;

// Alias
let sq = new polygons.Square(); // Same as 'new Shapes.Polygons.Square()'
```
