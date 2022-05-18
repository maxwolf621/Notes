# Generic 


```typescript
function createArray<T>(length: number, value: T): Array<T> {
    let result: T[] = [];
    for (let i = 0; i < length; i++) {
        result[i] = value;
    }
    return result;
}

createArray<string>(3, 'x'); // ['x', 'x', 'x']
createArray<number>(3, 5); 
// this works too
createArray(3, 'x');
```


## Generic Constraints 

It is forbidden for 
```typescript
function loggingIdentity<T>(arg: T): T {
    // 
    console.log(arg.length);
    return arg;
}   
```
- not all `T` has the `length()` methods
 
prevent from such problem by using `<T extends Class_Name>`
```
interface Lengthwise {
    length: number;
}

function loggingIdentity<T extends Lengthwise>(arg: T): T {
    console.log(arg.length);
    return arg;
}
```

### multiple generic

```typescript
function copyFields<T extends U, U>(target: T, source: U): T {
    for (let id in source) {
        target[id] = (<T>source)[id];
    }
    return target;
}

let x = { a: 1, b: 2, c: 3, d: 4 };

copyFields(x, { b: 10, d: 20 });
```

## Generic Interface

```typescript
interface CreateArrayFunc {
    <T> (length: number, value: T): Array<T>;
}

let createArray: CreateArrayFunc;

createArray = function<T>(length: number, value: T): Array<T> {
    let result: T[] = [];
    for (let i = 0; i < length; i++) {
        result[i] = value;
    }
    return result;
}

createArray(3, 'x'); // ['x', 'x', 'x']
```
