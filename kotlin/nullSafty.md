
One of the most common pitfalls in many programming languages, including Java, is that accessing a member of a null reference will result in a null reference exception(`NullPointerException`, or an NPE for short)  

>>> In Kotlin, the type system distinguishes between references that can hold null (nullable references) and those that cannot (non-null references

## Assign as Nullable & Safe Calls

```kotlin
// String type can not hold null
var s : String = "Cant be null"

s = null // compilation error
```

To Allow a `s` as a `nullable` variable by adding a `?` keyword
```kotlin
var s : String? = "abc" // s can be null
s = null // safe 

print(s);
// null

val len = s.length // error : varaible `s` can be null

val len = s?.length // SAFE CALLS
```


**Safe calls are useful in chains.** a chain returns null if any of the properties in it is `null`.

For example, Bob is an employee who may be assigned to a department (or not). That department may in turn have another employee as a department head. 
```kotlin
bob?.department?.head?.name
```


To perform a certain operation only for non-null values. safe call with `let` together can be used together
```kotlin

// List can contain null value element
val listWithNulls: List<String?> = listOf("Kotlin", null)

for (item in listWithNulls) {
    item?.let { println(it) } 
    // with let it prints Kotlin and ignores null
}
```


A safe call can also be placed on the left side of an assignment. 

Then, if one of the receivers in the safe calls chain is `null`, the assignment is skipped and the expression on the right is not evaluated at all:
```kotlin
// If either `person` or `person.department` is null, 
// the function is not called:
person?.department?.head = managersPool.getManager()
```


## Checking for null in conditions

```kotlin
val b: String? = "Kotlin"

if (b != null && b.length > 0) {
    print("String of length ${b.length}")
} else {
    print("Empty string")
}
```

`b` must be immutable (meaning it is a local variable that is not modified between the check and its usage or it is a member val that has a backing field and is not overridable), 
because otherwise it could be the case that b changes to null after the check.


## Nullable receiver

