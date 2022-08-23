# validator 

- [validator](#validator)
  - [Reference](#reference)
  - [@Valid](#valid)
  - [@DecimalMax](#decimalmax)
  - [@DecimalMin](#decimalmin)
  - [@Pattern(regexp)](#patternregexp)
  - [@AssertTrue Boolean & @AssertFalse Boolean](#asserttrue-boolean--assertfalse-boolean)
  - [@Max int and @Min int](#max-int-and-min-int)
  - [@Size String method/field/constructor](#size-string-methodfieldconstructor)
  - [@NotNull](#notnull)
  - [@Null](#null)
  - [Date](#date)
    - [@Past Date](#past-date)
    - [@Future Date](#future-date)

## Reference
- [oracle](https://docs.oracle.com/javaee/7/tutorial/bean-validation001.htm)
- [@valid example](http://www.mydlq.club/article/49/)

## @Valid

- Annotation is used entity within an entity , controller POST Method

```java
package com.application.entity;

@Data
public class User {

    // ...

    @Valid
    @NotNull(message = "userInfo cant be null")
    private UserInfo userInfo;
}

@Data
public class UserInfo {
    // ...
}

@RestController
public class Controller {

    @Validated
    @GetMapping("/user/{username}")
    public ResponseResult findUserInfo(@PathVariable String username) {
        //....
    }


    // Check the user 
    // @Valid is only available post method
    @PostMapping("/user")
    public ResponseResult addUserInfo(@Valid @RequestBody User user) {
        // ...
    }

}
```
- If data is not valid, it throws `MethodArgumentNotValidException`

## @DecimalMax

The value of the field or property must be a decimal value lower than or equal to the number in the value element.
```java
@DecimalMax("30.00")
BigDecimal discount;
```

## @DecimalMin

The value of the field or property must be a decimal value greater than or equal to the number in the value element.
```java
@DecimalMin("5.00")
BigDecimal discount;
```
## @Pattern(regexp)


The value of the field or property must match the regular expression defined in the regexp element.

```java
@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}")
String phoneNumber;

@Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
private String ipAddress;
```

## @AssertTrue Boolean & @AssertFalse Boolean
	
```java
// The value of the field or property must be true.
@AssertTrue
boolean isActive;

// The value of the field or property must be false.
@AssertFalse
boolean isUnsupported;
```

## @Max int and @Min int
```java
// 18 <= age =< 150
@Min(value = 18, message = "Age should not be less than 18")
@Max(value = 150, message = "Age should not be greater than 150")
private int age;
```

## @Size String method/field/constructor
```java
@Size(min = 10, 
      max = 200, 
      message = "AboutMe : must be between 10 and 200 characters")
private String aboutMe;
```

## @NotNull

```java
// str cant be null val
@NotNull
private String str;
```

## @Null

```java
@Null(message = "id must be null")
private Long id;
```

## Date 

### @Past Date
被註解的元素必須是一個過去的日期

```java
@Past
private Date createDt;
```

### @Future Date
The value of the field or property must be a date in the future.

```java
@Future
Date eventDate;
```