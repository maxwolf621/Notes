# Interface 

Refs  
[[Golang] interfaces](https://pjchender.dev/golang/interfaces/#tldr)  
[Go by Example: Interfaces](https://gobyexample.com/interfaces)  

---

[TOC]


Definition of interface and struct
```go 
type InterfaceX interface{
    doSomething() T
}

type StructX struct{
    // ..
}
```

Apply a struct type to implement interface (`StructX` IMPLEMENTS `InterfaceX`)  
```go 
func (structX *StructX) doSomething() T{
    // IMPLEMENTATION
}
```

## Implementation

>>> A struct type can implement multiple interface. [_Type Switch](#Type-Switch)
```go  
// A Tour of Go: Exercise: Stringers
// https://tour.golang.org/methods/18

type Stringer interface {
  String() string
}

type IPAddr [4]byte

// HERE MEANS : 
// type IPAddr [4]byte 
//     implements 
// type Stringer interface
func (ip IPAddr) String() string {
    var ips []string
    for _, ipNumber := range ip {
        ips = append(ips, strconv.Itoa(int(ipNumber)))
    }
    return strings.Join(ips, ",")
}

func main() {
    hosts := map[string]IPAddr{
        "loopback":  {127, 0, 0, 1},
        "googleDNS": {8, 8, 8, 8},
    }

    for name, ip := range hosts {
        fmt.Printf("%v: %v\n", name, ip)
    }
}
```

## Polymorphism

The struct data type which implements an interface type can be directly assigned to interface data type variable.  
```go  
structVariable := structType{...}
var interfaceVariable interfaceType = structVariable 
```

:arrow_down: For example 
```go 
// https://medium.com/rungo/structures-in-go-76377cc106a2
type Salaried interface {
    getSalary() int
}

// Becuase `type Salary struct` implements `type Salaried interface` 
// SO type Salary struct cam be assign to type Salaried interface （polymorphism）
type Salary struct {
    basic, insurance, allowance int
}

func (s Salary) getSalary() int { // IMPLEMENTATION
    return s.basic + s.insurance + s.allowance
}

type Employee struct {
    firstName, lastName string
    salary              Salaried
}

func main() {
    ross := Employee{
        firstName: "Ross",
        lastName:  "Geller",
        // here kinda like
        // salary Salaried := Salary{ 1100, 50, 50,}
        salary: Salary{
            1100, 50, 50,
        },
    }

    fmt.Println("Ross's salary is", ross.salary.getSalary())
}
```

## Embedding interfaces

```go
// Source COde ：
//     https://medium.com/rungo/interfaces-in-go-ab1601159b3a

type Shape interface {
    Area() float64
}

type Object interface {
    Volume() float64
}

// Embedding Interfaces : 
//         Merging Shape & Object interfaces
type Material interface {
    Shape
    Object
}
```

## Empty Interface

```go 
// definition 
interface{}
```

:arrow_down: For example 
```go 
var empTyp interface{} = "ok"

func doSomething(interface{}){    
    // implementation ...
}
```
- No Methods 
- **An empty interface may hold values of any type**. (Every type implements at least zero methods.)

>>> Empty interfaces are used by code that handles values of unknown type e.g. `fmt.Print`



## Type Assertion

```go
resultValue , isTypeCorrect = <variableName>.(*<TYPE>)
```

```go 
package main

import "fmt"

func main() {
    // empty interface
    var i interface{} = "hello"
    
    s := i.(string) 
    fmt.Println(s)  // hello  

    s, ok := i.(string)
    fmt.Println(s, ok) // hello true
    
    f, ok := i.(float64)
    fmt.Println(f, ok)  // 0 false
    
    // panic
    f = i.(float64) 
    fmt.Println(f)
}

```

## Type Switch

```go
// variable : i and v
// .(type) is a keyword not variable 
switch v := i.(type) {
case T:
    // here v has type T
case S:
    // here v has type S
default:
    // no match; here v has the same type as i
}
```

:arrow_down: For example 
```go 
func do(i interface{}) {
	switch v := i.(type) {
	case int:
		fmt.Printf("Twice %v is %v\n", v, v*2)
	case string:
		fmt.Printf("%q is %v bytes long\n", v, len(v))
	default:
		fmt.Printf("I don't know about type %T!\n", v)
	}
}

func main() {
	do(21)
	do("hello")
	do(true)
}
```

## Json & Type Switch

Refs  
[Golang Type switches](https://medium.com/@kevinbai/golang-type-switches-df2afacc903e)  

Type Assertion for unknown json data

```go 
m := f.(map[string]interface{})
for k, v := range m {
    switch vv := v.(type) {
    case string:
        fmt.Println(k, "is string", vv)
    case float64:
        fmt.Println(k, "is float64", vv)
    case []interface{}:
        fmt.Println(k, "is an array:")
        for i, u := range vv {
            fmt.Println(i, u)
        }
    default:
        fmt.Println(k, "is of a type I don't know how to handle")
    }
}

```



---

- [:arrow_left: GO METHOD](https://hackmd.io/@Edixon/r1Aq6BhC2)    
- [GO STRUCT :arrow_right:](https://hackmd.io/@Edixon/B1DUYp_02)

