# Database table relationships  
- [Database table relationships](#database-table-relationships)
  - [Difference btw One-To-Many and Many-To-Many](#difference-btw-one-to-many-and-many-to-many)
  - [Attribute of the Relationship](#attribute-of-the-relationship)
  - [`@OneToMany`](#onetomany)
  - [Father(MappedBy) and Child(Owning)](#fathermappedby-and-childowning)
    - [Unidirectional `@OneToMany`](#unidirectional-onetomany)
  - [`@JoinColumn`](#joincolumn)
    - [JoinColumn vs Column](#joincolumn-vs-column)
  - [Unidirectional `@OneToMany` with `@JoinColumn`](#unidirectional-onetomany-with-joincolumn)
  - [Unidirectional` @OneToOne` with `@JoinColumn`](#unidirectional-onetoone-with-joincolumn)
  - [Unidirectional `@OneToMany` with `@JoinTable`](#unidirectional-onetomany-with-jointable)
  - [Bidirectional `@OneToMany()`](#bidirectional-onetomany)
    - [`mappedBy` and `@JoinColumn`](#mappedby-and-joincolumn)
    - [Two one way relationship](#two-one-way-relationship)
  - [Efficiency for `@OneToMany` and `@ManyToOne`](#efficiency-for-onetomany-and-manytoone)

References
- [Difference Between One-to-Many, Many-to-One and Many-to-Many?](https://stackoverflow.com/questions/3113885/difference-between-one-to-many-many-to-one-and-many-to-many)  
- [The best way to map a @OneToMany relationship with JPA and Hibernate](https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/)  
- [In which case do you use the JPA @JoinTable annotation?](https://stackoverflow.com/questions/5478328/in-which-case-do-you-use-the-jpa-jointable-annotation)
- [Synchronize](https://vladmihalcea.com/jpa-hibernate-synchronize-bidirectional-entity-associations/)  
- [EagerFetching](https://vladmihalcea.com/eager-fetching-is-a-code-smell/)  
- [MappedBy](https://stackoverflow.com/questions/9108224/can-someone-explain-mappedby-in-jpa-and-hibernate#:~:text=MappedBy%20signals%20hibernate%20that%20the,constraint%20to%20the%20other%20table.)  
- [best-practices-many-one-one-many-associations-mappings](https://thorben-janssen.com/best-practices-many-one-one-many-associations-mappings/)  
- [How to define unidirectional OneToMany relationship in JPA](https://stackoverflow.com/questions/12038380/how-to-define-unidirectional-onetomany-relationship-in-jpa)
- [JPA JoinColumn vs mappedBy](https://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby)
- [many to one](https://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby)
- [@JoinColumn](https://blog.csdn.net/u010588262/article/details/76667283)  

## Difference btw One-To-Many and Many-To-Many  

`reusability` 
                  
One-to-Many: One Person Has Many Skills, a Skill is not **reused** between Person(s)  
- Unidirectional: A Person can directly reference Skills via its Set  
- Bidirectional: Each "child" Skill has a single pointer back up to the Person (which is not shown in your code)  

Many-to-Many: One Person Has Many Skills, a Skill is **reused** between Person(s)
- Unidirectional: A Person can directly reference Skills via its Set
- Bidirectional: A Skill has a Set of Person(s) which relate to it.

---

In a One-To-Many relationship, one object is the parent and one is the child.  
- **The parent controls the existence of the child.**      

In a Many-To-Many, the existence of either type is dependent on something outside the both of them (in the larger application context).  
- Many-To-Many Bidirectional relationship does not need to be symmetric!  
That is, a bunch of People could point to a skill, but the skill needs not relate back to just those people.    
Typically it would, but such symmetry is not a requirement.  

## Attribute of the Relationship

Orphan Removal    
JPA 2 supports an additional and more aggressive remove cascading mode which can be specified using the `orphanRemoval` element of the `@OneToOne` and `@OneToMany` annotations  
- If `orphanRemoval=true` is specified the disconnected Address instance is automatically removed.     
**The attribute is useful for cleaning up dependent objects** (e.g. Address) that should not exist without a reference from an owner object (e.g. Employee).   

For example A Post can have many comments
The relationship is based on the Foreign Key column (e.g. `post_id`) in the child table.  
![](https://i.imgur.com/Db6bn7z.png)  

## `@OneToMany`

Type of `@OneToMany`
1. a unidirectional `@OneToMany `association
2. a bidirectional `@OneToMany` association

## Father(MappedBy) and Child(Owning)

Concept  
- If Father doesn't exist then Child will not exist which means child must be dependent on father   
- The Foreign Key in Child Entity (which references to Father entity's Primary key)

### Unidirectional `@OneToMany`

```bash
father is mapped by side <-> mapping-table <-> child owning side
```
- two table with extra mapping-table storing FK referencing to both table
```java
/**
 * MappedBy Side
 */
@Entity(name = "Post")
@Table(name = "post")
public class Post {
    
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    
    /** A post can have many Comments
     *   {@code orphanRemoval} :　Each comment should reference to post
     *   {@code cascadeType.all}　: If post does not exists then comments should be deleted
     */
    @OneToMany(cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();
 
    public List<PostComment> getComments(){
        return comments;
    }
    // other setter and getter ...
}

/** 
 * Owning Side 
 */
@Entity(name = "PostComment")
@Table(name = "post_comment")
public class PostComment {
    @Id
    @GeneratedValue
    private Long id;
    private String review;
    
    //...
}
```

To persist 1 Post and 3 PostComments
```java
Post post = new Post("first Post");
post.getComments().add(new PostComment("My first review"));
post.getComments().add(new PostComment("My Second review"));
post.getComments().add(new PostComment("My Third review"));
```

Hibernate will execute SQL statements like this
```sql
/* Create A New Post */
insert into post (title, id)
values ('First post', 1)

/* Create 3 Comments  */
insert into post_comment (review, id)
values ('My first review', 2)
insert into post_comment (review, id)
values ('My second review', 3)
insert into post_comment (review, id)
values ('My third review', 4)

/** 
 * Create A Relation Mapping Table
 * which references to ownning and mappedby entity's PK
 */
insert into post_post_comment (Post_id, comments_id)
values (1, 2)
insert into post_post_comment (Post_id, comments_id)
values (1, 3)
insert into post_post_comment (Post_id, comments_id)
values (1, 4)
```

![image](https://i.imgur.com/935wP0Z.png)  
- We got a extra table to link other two tables with extra two of Foreign Keys  
  >　Hibernate needs to create post and comments tables and then mapping these two tables together

## `@JoinColumn` 

`@joinColumn` in **`@OneToOne`, `@ManyToMany` and `@OneToMany` has different definition**

```java
// In mappedBy Side 

//         from owningSide's fk
@JoinColum(name = OwningSideFK_Name , 
//         from mappedBy side
           referencedColumnName = "mappedBySide_attribute" )
private this_table attribute(){}

// for example 
@OneToOne
@JoinColumn(name = "addr_id")
public AddressEO getAddress() {
         return address;
}
```

### JoinColumn vs Column

`@JoinColumn` and `@Column` are almost the same.   
The difference is `@JoinColumn` describes the **attribute columns btw tables (entities)** and **`@Column` describes attribute in a table**

## Unidirectional `@OneToMany` with `@JoinColumn`

**When using a unidirectional one-to-many association, only the parent side maps the association.**  

For example, a Company can have lot of branches
```java
/* MappedBy side */
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;


    @OneToMany(targetEntity= Branch.class,
               fetch = FetchType.LAZY, 
               orphanRemoval = true)
    /**
      * Each Branch entity has 
      * a column named companyId as FK 
      * referencing to 
      * a column named id in this entity (Company)
      */
    //          from owning's FK    from mappedBy side
    @JoinColumn(name = "companyId", referencedColumnName = "id")
    private List<Branch> branches = new ArrayList<>();
    
    //...
}

/* Owning Side*/
@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    
    private string name;
    // ...
}
```
- The Entity inside `@JoinColum` , attribute `name` refers to foreign key column name which is `companyId` and attribute `referencedColumnName` indicates the primary key `id` of the entity company to which the foreign key `companyId` refers  

---


Ohter example, given post and comment entities.    
In the post and comments relationship, it means only the `Post` entity will define a `@OneToMany` association to the child `PostComment` entity       
```java
/**
 * Add {@code @JoinColumn = mappedby PK} to our 
 * mappedby side and owning side stays the same
 * literally it means 
 * each comment needs update their row called post_id (FK) to this post 
 */
@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
@JoinColumn(name = "post_id")
private Set<PostComment> comments = new ArrayList<>();
```

Hibernate will execute like this way
```sql
insert into post (title, id)
values ('First post', 1)

/** 
 * Note that 
 *   post_comment doesn't set up its foreign key 
 */
insert into post_comment (review, id)
values ('My first review', 2)
insert into post_comment (review, id)
values ('My second review', 3)
insert into post_comment (review, id)
values ('My third review', 4)
 
/**
 * Because of 
 * @JoinColum(name = "post_id")
 * Hibernate will join a new column post_id as ForeignKey
 * In post_comment referecning to specific post_id value
 */
update post_comment set post_id = 1 where id = 2
update post_comment set post_id = 1 where id = 3
update post_comment set post_id = 1 where id = 4
```
- Hibernate inserts the child records first without the Foreign Key since the child entity does not store this information (`post_id`).  
  > During the collection handling phase, the Foreign Key column is updated accordingly.

If we need to delete a record in the child entity
```java
post.getComments().remove(0);
```

Hibernate will delete the fk in post_comment(owning side)
```sql
update post_comment 

-- post_comment's id=2's foreign key references to null */
set post_id = null 
where post_id = 1 and id = 2

-- delete row of post_comment's id=2's
delete from post_comment where id=2
```
## Unidirectional` @OneToOne` with `@JoinColumn`

For example, given two tables(address and customer) in one-to-one situation

Attribute `address_id` in table `customer` references to attribute `pk_id` in table `address` 
```java
@OneToOne
@JoinColumn(name = "address_id", referencedColumnName="pk_id")
public AddressEO getAddress() {
         return address;
}
```

## Unidirectional `@OneToMany` with `@JoinTable`

```java
@JoinTable(
        name = "MY_JT",
        joinColumns = @JoinColumn(
                name = "PROJ_ID",
                referencedColumnName = "PID"
        ),
        inverseJoinColumns = @JoinColumn(
                name = "TASK_ID",
                referencedColumnName = "TID"
        )
)
@OneToMany
private List<Task> tasks;
```
![](https://i.imgur.com/0B7yVLD.png)

## Bidirectional `@OneToMany()`

Using `mappedBy` element defines a bidirectional relationship.  
- **This attribute allows you to refer the associated entities from both sides**
- **Every bidirectional association must have one owning side only, the other one being referred to as the inverse(mappedBy) side.**

 
![](https://i.imgur.com/k6ZZIgv.png)   
- `@OneToMany` with the `mappedBy` attribute set, you have a bidirectional association.  
In this case, both the Post entity has a collection of `PostComment` child entities, and the child `PostComment` entity has a reference back to the parent Post entity
- With `@JoinColumn` we indicate hibernate that child entity (`PostComment`) **must have two keys (PK and FK)**
```java
@Entity(name = "Post")
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
 
    /**
     * this given column is maintained by the another entity
     * whose attribute/column named `post` 
     */
    @OneToMany(
        mappedBy = "post",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<PostComment> comments = new ArrayList<>();
 
    // To synchronize both sides of the bidirectional association.
    public void addComment(PostComment comment) {
        comments.add(comment);
        comment.setPost(this);
    }
    public void removeComment(PostComment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
} 
@Entity(name = "PostComment")
@Table(name = "post_comment")
public class PostComment {
    @Id
    @GeneratedValue
    private Long id;
    private String review;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id") // Post entity has Fk refer to Post Comment
    private Post post;
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostComment )) return false;
        return id != null && id.equals(((PostComment) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    //...
}
```

By property of `mappedBy` , the hibernate will do like 
```sql
/* Create Post */
insert into post (title, id)
values ('First post', 1)

/**
  *　post will maintain post_comment one at time\
  */
insert into post_comment (post_id, review, id)
values (1, 'My first review', 2)
insert into post_comment (post_id, review, id)
values (1, 'My second review', 3)
insert into post_comment (post_id, review, id)
values (1, 'My third review', 4)
```
### [`mappedBy` and `@JoinColumn`](https://www.baeldung.com/jpa-join-column)   

[Reference](https://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby)   

- The annotation `@JoinColumn(name = x , referencedColumnName = y) private entity this_entity` indicates that this entity is the owner of the relationship **(that is: the corresponding table has a column with a foreign key `x` to the referenced table's attribute `y`)**
  > this entity has fk named `x` and reference to pk `y` from another table
- The attribute `@..To..(mappedBy = entity_1 ) private entity entity_instance` indicates that `entity_instance` is the inverse of the relationship, and **the owner(控制權) resides in the other entity (entity_1).**
  > it also means that you can access the other table (`entity_1`) from the class which you've annotated with `mappedBy` (fully bidirectional relationship).

### Two one way relationship 

By specifying the `@JoinColumn` on both models you don't have a two way relationship.   
You have two one way relationships, and a very confusing mapping of it at that.    

You're telling both models that they "own" the identical column.  
Really only one of them actually should!   



## Efficiency for `@OneToMany` and `@ManyToOne`

The JPA specification defines `FetchType.EAGER` as the default for to-one relationships. 

It tells Hibernate to initialize the association, when it loads the entity. 
That is not a big deal, if you just load one entity. It requires just 1 additional query if you use JPQL query and Hibernate creates an INNER JOIN when you use the `EntityManager.find` method.

But that dramatically changes when you select multiple Item entities.
```sql
select
    item0_.id as id1_0_,
    item0_.name as name2_0_,
    item0_.fk_order as fk_order4_0_,
    item0_.version as version3_0_ 
from
    Item item0_

select
    purchaseor0_.id as id1_1_0_,
    purchaseor0_.version as version2_1_0_ 
from
    PurchaseOrder purchaseor0_ 
where
    purchaseor0_.id=?


select
    purchaseor0_.id as id1_1_0_,
    purchaseor0_.version as version2_1_0_ 
from
    PurchaseOrder purchaseor0_ 
where
    purchaseor0_.id=?

select
    purchaseor0_.id as id1_1_0_,
    purchaseor0_.version as version2_1_0_ 
from
    PurchaseOrder purchaseor0_ 
where
    purchaseor0_.id=?

select
    purchaseor0_.id as id1_1_0_,
    purchaseor0_.version as version2_1_0_ 
from
    PurchaseOrder purchaseor0_ 
where
    purchaseor0_.id=?
```