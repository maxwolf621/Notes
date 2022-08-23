# JPA and JSON Annotations

- [JPA and JSON Annotations](#jpa-and-json-annotations)
  - [Reference](#reference)
  - [Json Usage](#json-usage)
    - [JsonManagedReference and JsonBackReference](#jsonmanagedreference-and-jsonbackreference)
    - [@JsonIgnore and @JsonIgnoreProperties](#jsonignore-and-jsonignoreproperties)
  - [@MappedSuperClass](#mappedsuperclass)
  - [`@NoRepositoryBean`](#norepositorybean)
  - [@Transient](#transient)
  - [@Column](#column)

## Reference 

- [Hibernate Inheritance Mapping](https://www.baeldung.com/hibernate-inheritance#mappedsuperclass) 
- [Jackson – Bidirectional Relationships](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)
- [Json tutorialspoint](https://www.tutorialspoint.com/jackson_annotations/index.htm)
## Json Usage

### JsonManagedReference and JsonBackReference

- [`@JsonManagedReference` and `@JsonBackReference`](https://stackoverflow.com/questions/31319358/jsonmanagedreference-vs-jsonbackreference)

`@JsonManagedReferences` and `@JsonBackReferences` are used to display objects with parent child relationship. 

`@JsonManagedReferences` is used to refer to parent object.
`@JsonBackReferences` is used to mark child objects.

```java
public class JacksonTester {
   public static void main(String args[]) throws IOException, ParseException {
      ObjectMapper mapper = new ObjectMapper();     
      
      Student student = new Student(1, "Mark");
      Book book1 = new Book(1,"Learn HTML", student);
      Book book2 = new Book(1,"Learn JAVA", student);

      student.addBook(book1);
      student.addBook(book2);

      String jsonString = mapper 
         .writerWithDefaultPrettyPrinter()
         .writeValueAsString(book1);
      System.out.println(jsonString);
   }
}


class Student {
   public int rollNo;
   public String name;

   // Child
   @JsonBackReference
   public List<Book> books;

   Student(int rollNo, String name){
      this.rollNo = rollNo;
      this.name = name;
      this.books = new ArrayList<Book>();
   }

    public void addBook(Book book){
      books.add(book);
   }
}

@Data
class Book {
   public int id;
   public String name

   @JsonManagedReference
   public Student owner;
}
```

```json
{
"id" : 1,
"name" : "Learn HTML",
"owner" : {
    "rollNo" : 1,
        "name" : "Mark"
}
}
```

`@RepositoryRestResourcepublic`
- 配合spring-boot-starter-data-rest使用

### @JsonIgnore and @JsonIgnoreProperties

```java
@Entity
public class User{

    @JsonProperty("user_name")
    private String username;

    private int age;

    @JsonIgnore
    private int password;

    // ...

    // updateTime's format in JSON 
    // should be "yyyy-MM-dd HH:mm:ss"
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
```
- `password` will be ignored in JSON format

```json
{
    "username" : "bon vier",
    "age" : 23,
}
```

```java
@JsonIgnoreProperties({ "id", "systemId" })
class Student {
   public int id;
   public String systemId;
   public int rollNo;
   public String name;

   Student(int id, int rollNo, String systemId, String name){
      this.id = id;
      this.systemId = systemId;
      this.rollNo = rollNo;
      this.name = name;
   }
}

// Serializer
ObjectMapper mapper = new ObjectMapper();
Student student = new Student(1,11,"1ab","Mark");       
         String jsonString = mapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(student);
```

```json
{
"rollNo" : 11,
"name" : "Mark"
}
```

## @MappedSuperClass
- It is allow Model Class to be inherited
- **This strategy, ANCESTORS cannot contain associations with other entities.**
- no longer has an `@Entity` annotation, as it won't be persisted in the database by itself

```java 
@MappedSuperclass
public class Person {

    @Id
    private long personId;
    private String name;

    // constructor, getters, setters
}
```

```java
@Entity
public class MyEmployee extends Person {
    private String company;
    // constructor, getters, setters 
}
```
In the database, this will correspond to one MyEmployee table with three columns for the declared and inherited fields of the subclass.

## `@NoRepositoryBean`

- [understanding norepositorybean](https://stackoverflow.com/questions/11576831/understanding-the-spring-data-jpa-norepositorybean-interface)

As a base class, the spring will not instantiate this class 


## @Transient

With this annotation it tells ORM do not map this field to database
Each field in ORM set default by `@Base`

```java
public enum Gender { MALE, FEMALE, UNKNOWN }

@Entity
public Person {
    private Gender g;
    private long id;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Gender getGender() { return g; }    
    public void setGender(Gender g) { this.g = g; }

    // return boolean val will be decided by field
    @Transient
    public boolean isMale() {
        return Gender.MALE.equals(g);
    }

    @Transient
    public boolean isFemale() {
        return Gender.FEMALE.equals(g);
    }
}
```

## @Column
Attribute in `@Column` 
1. `name` : field's name in database  
2. `unique`：Whether the column is a unique key. @see also `@Table`注解中的`@UniqueConstraint`  
3. `nullable` : default is `true`  
4. `insertable` :  allow this column to be inserted new value  
5. `updateable` ： allow this column to be updated
6. **`columnDefinition`: Customize the column,不交由ORM Framework創建自動表格的資料類型(The SQL fragment that is used when generating the DDL for the column)**
   - e.g. we need to manually set up the field (e.g `String date`) which datatype in database should maps to, `TIME` or `TIMESTAMP`
   - e.g. `String` in java maps to `varchar` in database by default if we need to `Blob` or `Text` then we need this attributes to specify.
7. `table`：The name of the table that contains this column.
8. `length`：length for each record of this column, records data type must be `varchar` (`length = 255 (chars)` as default)
9. `(int) precision` and `(int) scale` : Applies only if a (database's datatype) decimal column is used
    - The `precision` for a decimal(該record的數值位總長exact numeric)column.
    - The `scale` for a decimal(資料顯示到小數點第幾位) column.
      -  `double` in java maps to `double` in database : precision and scale not allow. but If our field that mapped to database's column is `double` but set up `columnDefinition`'s datatype as `decimal`, then the column which will be mapped define a datatype as `decimal` not `double`   
      - `BigDecimal` in java maps to `decimal` in database
