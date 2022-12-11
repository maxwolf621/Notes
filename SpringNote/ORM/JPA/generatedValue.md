# Generated Value

- [Generated Value](#generated-value)
  - [AUTO](#auto)
  - [IDENTITY](#identity)
  - [SEQUENCE](#sequence)
  - [TABLE](#table)

Reference
- [Generate Identifiers Using JPA and Hibernate](https://thorben-janssen.com/jpa-generate-primary-keys/)


```java
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface GeneratedValue{
    GenerationType strategy() default AUTO;
    String generator() default "";
}
public enum GenerationType{
    TABLE,
    IDENTITY,
    AUTO,
    SEQUENCE
}
```

## AUTO

The GenerationType.AUTO is the default generation type and lets the persistence provider choose the generation strategy.

```java
@Id
@GeneratedValue(
    strategy = GenerationType.AUTO
)
private Long id;
```
From a database point of view, this is very efficient because the auto-increment columns are highly optimized, and it doesn’t require any additional statements.


## IDENTITY

The GenerationType.IDENTITY is the easiest to use but not the best one from a performance point of view.

 
```sql
id int not null auto_increment
primary key (id)
```

## SEQUENCE

It requires additional select statements to get the next value from a database sequence. 

But this has no performance impact for most applications. 

And if your application has to persist a huge number of new entities, you can use some Hibernate specific optimizations to reduce the number of statements

The `@SequenceGenerator` annotation lets you define the name of the generator, the name, and schema of the database sequence and the allocation size of the sequence.
- If you don’t provide any additional information, Hibernate will request the next value from its default sequence. 
```java
@Id 
@SequenceGenerator( 
    name = "userSeq",  // REFERENCE OF @GeneratedValue'S GENERATOR
    sequenceName = "user_id",
    allocationSize = 1,  // increment of PK
    initialValue = 1  // Initial value of PK
)  
@GeneratedValue( 
    strategy = GenerationType.SEQUENCE, 
    generator = "userSeq")
private int Id;
```

## TABLE

The `GenerationType.TABLE` gets only rarely used nowadays.  

不希望應用程式與某一種 Database Engine 綁死的時候，可以使用`GenerationType.TABLE`方法，**額外建立一個SQL 表格(which requires pessimistic locks) 來定義 ID**

Pessimistic locks put all transactions into **A SEQUENTIAL ORDER** which slow the application down.
```sql
CREATE TABLE APP_SEQ_STORE( 
    APP_SEQ_NAME VARCHAR(255) NOT NULL, 
    APP_SEQ_VALUE BIGINT NOT NULL, 
    PRIMARY KEY(APP_SEQ_NAME) 
);
```
利用 `@TableGenerator` 來決定 `@Id` 應該和哪個專門儲存 `Id` 的資料庫 Table 對應。
```java
@Id 
@TableGenerator(...) 
@GeneratedValue( 
    strategy = GenerationType.TABLE, 
    generator = "appSeqStore") 
private Long id;
```