# Question

- [Question](#question)
  - [Reference](#reference)
  - [What is typescript](#what-is-typescript)
    - [Properties](#properties)
  - [Typescript Vs Javascript](#typescript-vs-javascript)
  - [Disadvantage](#disadvantage)
  - [Can we combine multiple `.ts `files into a single `.js` file?](#can-we-combine-multiple-ts-files-into-a-single-js-file)
  - [Typescript Class](#typescript-class)
  - [What are modules in TypeScript?](#what-are-modules-in-typescript)
  - [What are Mixins?](#what-are-mixins)
  - [How can you debug a TypeScript file?](#how-can-you-debug-a-typescript-file)

## Reference

- [Top 50 TypeScript Interview Questions](https://www.edureka.co/blog/interview-questions/typescript-interview-questions/)

## What is typescript 

TypeScript is a typed superset of JavaScript that compiles to plain JavaScript. 
**It is pure object-oriented with classes, interfaces and statically typed programming languages like C# or Java.** You will need a compiler to compile and generate the code in the JavaScript file. Basically, **TypeScript is the ES6 version of JavaScript with some additional features.**

- **A statically-typed language is a language (such as Java, C, or C++) where variable types are known at compile time**.

### Properties

- **Object-Oriented Language**    
TypeScript provides features like Classes, Interfaces, and Modules. Thus, it can write object-oriented code for client-side as well as server-side development.


- **Static Typing**  
JavaScript is dynamically typed and does not know what type a variable is until it is actually instantiated at run-time.  
**This feature makes everything stay the way we define it. Need a variable to always be a `number`? It’ll always be a `number`, then.**

- **Type Inference**  
TypeScript makes typing a bit easier and a lot less explicit by the usage of type inference. Even if you don’t explicitly type the types, they are still there to save you from doing something which otherwise would result in a run-time error.

- **Type annotations**
A handy way of saying explicitly what type should be used.

- **Strict Null Checking**  (`never`, `undefined` keyword)
Errors, like cannot read property `x` of `undefined`, is common in JavaScript programming.   
*You can avoid most of these kinds of errors since one cannot use a variable that is not known to the TypeScript compiler.*

---

- **Using new features of ECMAScript**  
TypeScript supports new ECMAScript standards including most features of planned ECMAScript 2015 (ES 6, 7) such as class, interface, Arrow functions, etc.

- **Better IDE Support && Cross Platform**  
There is a wide range of IDEs that have excellent support for TypeScript, like Visual Studio & VS code, Atom, Sublime, and IntelliJ/WebStorm.     
The TypeScript compiler can be installed on any Operating System such as Windows, MacOS, and Linux.

- **DOM Manipulation**  
You can use TypeScript to manipulate the DOM for adding or removing elements.

- **Interoperability**  
TypeScript is closely related to JavaScript so it has great interoperability capabilities, but some extra work is required to work with JavaScript libraries in TypeScript.

## Typescript Vs Javascript

| Typescript  | Javascript |
| ----------  | ---------- |
|TypeScript is an **Object-Oriented** language | JavaScript is a Scripting language
| It has a feature known as **Static typing** |It does not have static typing
| TypeScript gives support for **modules**     | JavaScript does not support modules
| It supports **optional parameter (`?`)** function  |It does not support optional parameter function

## Disadvantage

- TypeScript takes a long time to compile the code. while JavaScript doesn't.   
    - False sense of security   
    TypeScript performs type checks only during compilation.   
    Afterwards, we’re dealing with pure JavaScript that doesn’t do that.     
    This means we may still encounter some bugs that the compiler didn’t find, though admittedly there’s going to be far fewer of them than if we hadn’t used TypeScript.
- **If we run the TypeScript application in the browser, a compilation step is required to transform TypeScript into JavaScript.**
  - Overly complicated typing system  
  This isn’t strictly a disadvantage of TypeScript, though, but rather a downside that stems from it being fully interoperable with JavaScript, which itself leaves even more room for complications. 
- **To use any third party library, the definition file is a must.**
- **Quality of type definition files is a concern**



## Can we combine multiple `.ts `files into a single `.js` file?
Yes, we can combine multiple files. While compiling.
```bash
tsc --outFile [outPutFileName.js] compileFileName1.ts compileFileName2, ... compileFileNameN

# This will compile all 3 “.ts” file and output into a single "outPutFile.js" file. 
tsc --outFile outputFile.js file1.ts file2.ts file3.ts

# without providing output file 
# file2.ts and file3.ts will be compiled and the output will be placed in file1.ts
tsc --outFile file1.ts file2.ts file3.ts
```

## Typescript Class

A class includes the following:
- Constructor
- Properties
- Methods

the features of a class are:
- Inheritance
- Encapsulation
- Polymorphism
- Abstraction

access modifiers supported by TypeScript?
- Public

- Protected  
All the members of the class and its child classes can access them. But the instance of the class can not access.

- Private  
Only the members of the class can access them.

## What are modules in TypeScript?

A module is a powerful way of creating a group of related variables, functions, classes, and interfaces, etc. It can be executed within its own scope, but not in the global scope. 

In TypeScript, just as in ECMAScript 2015, any file containing a top-level `import` or `export` is considered a module

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

##  What are Mixins?
In Javascript, Mixins are a way of building up classes from reusable components and then build them by combining simpler partial classes.

The idea is simple, instead of a class A extending class B to get its functionality, **function B takes class A and returns a new class with this added functionality.** Here, function B is a mixin.

## How can you debug a TypeScript file?
To debug any TypeScript file, you need a `.js` source map file. So, you have to compile the `.ts` file with the `–sourcemap` flag to generate a source map file.

- [Day 33. 戰線擴張・專案除錯 X 源碼對照 - TypeScript Compiler Debug Techniques](https://ithelp.ithome.com.tw/articles/10223348)

tsconfig.json
```json
{
  "compilerOptions": {
    // ...
    "sourceMap": true
    // ...
  }
}
```
