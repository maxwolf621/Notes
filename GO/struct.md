###### Tags : `GO`

[TOC]

```go 
var Dict = Map[key_type]value_type{
    key1 : value1

}

Dict[key2]=value2
```

For Loop of Map

```go  
for key, value := range Dict{
    // ...
}

```

# struct

## Declaration & Creation
```go 
/***************
 * declaration *
 ***************/

// apply it if struct contains primitive type
type Student struct{ 
    name string
    major string
}

// or 
type Student struct{
    name string, major string
}


/***************
 * Creation    *
 ***************/

// Recommend 
std := Student{
    name : "Jiang",
    major: "CS", // HERE TAIL COMMA IS NEEDED
}


std := Student{"Jiang", "CS"}

// pointer
std := &Student{"Jiang", "CS"}

// declaration then assign
var std Student
std.name = "Jiang"
std.major = "CS"
```



## Anonymous struct

```go 
std := struct {
    name string
    major string
}{
    name : "jiang",
    major: "cs",
}
```


## struct pointer

```go 
// &Student -> allocate a address referred by std
std := &Student{ 
    name : "Jiang",
    major : "CS",
}

// Syntctic Sugar
std.name = "Dome" // instead of using (*std).name
```

### New a Memory Space Or Refer to the same Address
```go 
// allow new address in memory space for std2 
std2 := std
```
- `std2` and `std` refer to different address

```go 
std3 := &std
```
- `std3` refers to address where `std` refers 



## Nested struct

```go 
import "fmt"

type Student struct {
	name        string
	major       string
	contactInfo ContactInfo
}

type ContactInfo struct {
	email  string
	mobile string
}

func main() {
	std := Student{
		name:  "Jiang",
		major: "CS",
		contactInfo: ContactInfo{
			email:  "Jiang@gmail.com",
			mobile: "1234578901",
		},
	}

	fmt.Println(std)
}

```

## struct Methods

Refs
[Go 語言內 struct methods 該使用 pointer 或 value 傳值?](https://blog.wu-boy.com/2017/05/go-struct-method-pointer-or-value/)

```go 
// Call By Value
// ONLY FOR GETTER
func (structName StructName) functionaName(p1 t, p2 t, ...){
    //...
}

// Pass By 
// APPLY FOR GETTER & SETTER METHOD
func (structName *StructName) functionName(p1 t, p2 t, ...){
    //...
}
```

![](https://hackmd.io/_uploads/HyJ6JFoRh.png)


## Field Type

### Meta Data - struct field tag

```go 
type Name struct {
//            field tag
    givenAValue type `json:"givenAValue"`
    omitIfEmpty type `json:"omitIfEmpty,omitempty"`
    omit type        `json:"-"`
}
```

```go 
type Student struct{
    name string `json:"name"`
    // omit this value if `nickname` value is empty
    nickname string `json:"nickname,omitempty"`
    // omit address
    address string `josn:-`
}

```

### Function Field

Steps :  
1. Function Declaration 
2. Function Declaration in struct
3. implementation function while struct Creation
```go 
// 1
type GetNameAndAge func(string, int) string
// 2
type Student struct {
    name          string
    age           int
    getNameAndAge GetNameAndAge
}
func main() {
    // 3
    std := Student{
        name: "Jiang",
        age:  22,
        getNameAndAge: func(name string, age int) string {
            return name + "'s age : " + strconv.Itoa(age)
        },
    }
    fmt.Println(std.getNameAndAge(std.name, std.age))
}
```

### Anonymous Field

```go 
type AnnoymousFields struct{
    string// same as string string
    bool  // same as bool bool
    int   // same as int int
}
func main() {
    anonymousField := AnnoymousFields{
        "Student", true, 180,
    }

    fmt.Printf("%+v", anonymousField) 
}
```
Result :arrow_down: 
```go 
{string:Student bool:true int:180}
```

### Embedded Fields (Promoted Fields)


Refs :  
[Golang struct嵌入欄位 embedded field](https://matthung0807.blogspot.com/2021/07/go-struct-embedded-field.html)  
[Day28 .[心得與討論篇] embedded 嵌入](https://ithelp.ithome.com.tw/articles/10227592)  

Go supports embedding of structs and interfaces to express a more seamless composition of types.

>> **Promoted/Embedded Fields : Embed a (base)struct to other (derived)struct** 

Embedded Field Type
- Interface : `type name interface { funNmae(paramaters) returnType }`
- Implementation method
- Object type & primitive type

For example, a container embeds a base. An embedding looks like a field without a name. :arrow_down: 
```go 
package main

import "fmt"

type base struct {
    description string
}

// Struct Method
func (b base) describe() string {
    return fmt.Sprintf("base with description=%v", b.description)
}

type container struct {
    base
    str string
}
func main() {

    co := container{
        base: base{
            num: 1,
        },
        str: "this is derived struct",
    }

    //...
}
```

:star2: Derived struct can be directly call fields of Base struct. :arrow_down: 
```go 
    fmt.Printf("co={num: %v, str: %v}\n", co.num, co.str)

    fmt.Println("also num:", co.base.num)

    fmt.Println("describe:", co.describe())
```

Embedding structs with methods may be used to bestow(assign) interface implementations onto other structs. 

Here we see that a container now implements the describer interface because it embeds base.
```go  
    type describer interface {
        describe() string
    }

    var d describer = co
    fmt.Println("describer:", d.describe())
```


### Interface Fields

### Anonymousely Nested Interface


## Comparison

Refs [How to compare if two structs, slices or maps are equal?](https://reurl.cc/GKV8G3)

>>> Slice and Map can not be compared directly.  
```go   
import (
	"fmt"

	"github.com/google/go-cmp/cmp"
)

type T struct {
	X int
	Y string
	Z []int
	M map[string]int
}

func main() {
	t1 := T{
		X: 1,
		Y: "lei",
		Z: []int{1, 2, 3},
		M: map[string]int{
			"a": 1,
			"b": 2,
		},
	}

	t2 := T{
		X: 1,
		Y: "lei",
		Z: []int{1, 2, 3},
		M: map[string]int{
			"a": 1,
			"b": 2,
		},
	}

    // only in test
    // reflect.DeepEqual()
	fmt.Println(cmp.Equal(t1, t2))

}
```

## reflect.TypeOf & reflect.ValueOf().Kind()

