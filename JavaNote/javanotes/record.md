# Record 

- [Record](#record)
    - [Constructor](#constructor)
    - [With Static Member](#with-static-member)
  - [Record vs LomBok](#record-vs-lombok)
    - [final field](#final-field)
    - [Getter Method](#getter-method)
    - [lombok builder constructor supports](#lombok-builder-constructor-supports)
    - [Optional Fields Support](#optional-fields-support)
    - [Extendable](#extendable)

In many cases, this data is immutable, since immutability ensures the validity of the data without synchronization.

data classes must have the following condition
1. `private`, `final` `field` for each piece of data
2. getter for each field
3. `public` constructor with a corresponding argument for each field
`equals` method that returns `true` for objects of the same class when all fields match
3. `hashCode` method that returns the same value when all fields match
toString method that includes the name of the class and the name of each field and its corresponding value

```java
public class Person {

    // immutable variable
    private final String name;
    private final String address;

    // constructor
    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // hash code and equals
    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Person)) {
            return false;
        } else {
            Person other = (Person) obj;
            return Objects.equals(name, other.name)
              && Objects.equals(address, other.address);
        }
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", address=" + address + "]";
    }

    // standard getters
}
```

Creating immutable data in this way has some deficient problem
1. boilerplate code
2. obscure the purpose of the class 
3. Fail to automatically update our classes when a new field(property) is added(e.g. update equals method )

As of JDK 14, we can replace our repetitious data classes with records.  
`Records` are immutable data classes that require only the type and name of fields.    

```java
public record Person(String name,String address) {}
```

Using `records` keyword, a public constructor, with an argument for each field, is generated for us : 
```java
// complied 
public final class Person extends java.lang.Record{
    // all fields are private final
    private final java.lang.String name;
    private final java.lang.String address;

    public Student(java.lang.String name, java.lang.String address){
        //...
    }

    // automatically generate toString, 
    // hashCode, equals, getter methods
    public java.lang.String toString() {
        // ...
    }
    public final int hashCode(){
        // ...
    }
    public final boolean equals(java.lang.Object o){
        //..
    }


    // Setters
    public java.lang.String name(){
        // ...
    }
    public java.lang.String address(){
        //..
    }

}
```

### Constructor

we can ensure that parameter provided to our Person record aren't `null` using the following constructor implementation with `Objects.requireNonNull(field_name)`
```java
public record Person(String name, String address) {
    public Person {
        Objects.requireNonNull(name);
        Objects.requireNonNull(address);
    }
}
```

---

Create new constructor with specific fields
```java
public record Person(String name, String address) {
    public Person(String name) {
        this(name, "Unknown");
    }
}
```

---

Creating a constructor with the same arguments as the generated public constructor is valid, but this requires that each field be manually initialized:
```java
public record Person(String name, String address) {
    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
```

---

**Declaring a no-argument constructor and one with an argument list matching the generated constructor results in a compilation error**
```java
public record Person(String name, String address) {
    public Person {
        Objects.requireNonNull(name);
        Objects.requireNonNull(address);
    }
    
    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }
}

// Throwing Compiler Error
```

### With Static Member

```java
// static member
public record Person(String name, String address) {
    public static String UNKNOWN_ADDRESS = "Unknown";
}

// static method
public record Person(String name, String address) {
    public static Person unnamed(String address) {
        return new Person("Unnamed", address);
    }
}

// Person.UNKNOWN_ADDRESS
// Person.unnamed("100 Linda Ln.");
```

## Record vs LomBok

### final field

`final` field via lombok is optional
```java
// use @value set all fields with final keyword
@Value
public class Car {
    private String brand;
    private String model;
    private int year;
}
```

### Getter Method

`record` Class getter method is not named with `getFiledName`

### lombok builder constructor supports 

```java

@Builder
public class Car {
    private String brand;
    private String model;
    private int year;

    public static void main(String[] args) {
        Car myCamaro = Car.builder().brand("Chevrolet")
                                    .model("Camaro")
                                    .year(2022)
                                    .build();
    }
}
```

### Optional Fields Support

When a constructor of `record` class has numerous fields indicates that a new structure should be created to wrap the numerous parameters or that the function is doing too many things.
```java 
public record DetailedCar(
    String brand, 
    String model, 
    int year,
    String engineCode, 
    String engineType, 
    String requiredFuel,
    String fuelSystem, 
    String maxHorsePower, 
    String maxTorque,
    float fuelCapacity){
        // ...
    }
```

With annotation `@NonNull` to choose required fields via Lombok 
```java
import lombok.Builder;
import lombok.NonNull;

@Builder
public class DetailedCar {
    @NonNull
    private String brand;
    @NonNull
    private String model;
    @NonNull
    private int year;

    // optional
    private String engineCode;
    private String engineType;
    private String requiredFuel;
    private String fuelSystem;
    private String maxHorsePower;
    private String maxTorque;
    private float fuelCapacity;

    public static void main(String[] args) {
        DetailedCar camaroIncomplete = 
                DetailedCar.builder().brand("Chevrolet").model("Camaro")
                                                        .year(2022)
                                                        .build();
    }
}
```

### Extendable

Record Class is `final` class, you cant extend a record class.

lombok class is extendable : 
```java
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Car extends MotorVehicle {
    private String brand;
    private String model;
    private int year;
}
```