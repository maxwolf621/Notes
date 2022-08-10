# Kotlin && Typescript

Kotlin and Typescript both has Type Interference

- the variable must either have a type annotation or be initialized
```java
//         DataType is always uppercase
var name : DataType

// var name ; <-- wrong
```

```typescript
let   name : type ; 
const name : dataType[];
```


## void Type

```typescript
// With strictNullChecks set to true
let a: void = undefined; // Ok
let b: void = null; // Error
let c: void = 3; // Error
let d: void = "apple"; // Error
 
// With strictNullChecks set to false
let a: void = undefined; // Ok
let b: void = null; // Ok
let c: void = 3; // Error
let d: void = "apple";  // Error
```

## variable with null type

Typescript
```typescript
// With strictNullChecks set to true
let a: null = null; // Ok
let b: undefined = null; // Error
let c: number = null; // Error
let d: void = null; // Error
 
// With strictNullChecks set to false
let a: null = null; // Ok
let b: undefined = null; // Ok
let c: number = null; // Ok
let d: void = null; // Ok

// With strictNullChecks set to true
let a: undefined = undefined; // Ok
let b: undefined = null; // Error
let c: number = undefined; // Error
let d: void = undefined; // Ok
 
// With strictNullChecks set to false
let a: undefined = undefined; // Ok
let b: undefined = null; // Ok
let c: number = undefined; // Ok
let d: void = undefined; // Ok
```

kotlin
```

```


## Destructing Declaration


```java 
val person = Person("Adam", 100)
val (name, age) = person

val pair = Pair(1, 2)
val (first, second) = pair

val coordinates = arrayOf(1, 2, 3)
val (x, y, z) = coordinates
```

## flow 

for (i in 0..10) { } // 1 - 10
for (i in 0 until 10) // 1 - 9
(0..10).forEach { }
for (i in 0 until 10 step 2) // 0, 2, 4, 6, 8


### kotlin special control flow `when`

kidda like `switch`

```java
when (direction) {
    NORTH -> {
        print("North")
    }
    SOUTH -> print("South")
    EAST, WEST -> print("East or West")
    "N/A" -> print("Unavailable")
    else -> print("Invalid Direction")
}
```


## function construction


## Constructor

```typescript
class Person constructor(firstName: String) { /*...*/ }

```


## getter and setter


```typescript 


get( ...){
    //...
}
set( .. ){
    //...
}
```