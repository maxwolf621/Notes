# Relational Algebra/Calculus

- [Relational Algebra/Calculus](#relational-algebracalculus)
  - [Relationship of SQL instructor and Relational Algebra](#relationship-of-sql-instructor-and-relational-algebra)
    - [Operations of relational Algebra](#operations-of-relational-algebra)
  - [RESTRICT `σ`](#restrict-σ)
  - [PROJECT `π`](#project-π)
  - [UNION](#union)
  - [CARTESIAN PRODUCT `R X S`](#cartesian-product-r-x-s)
  - [Difference](#difference)
  - [JOIN](#join)
    - [JOIN TYPES](#join-types)
    - [THETA JOIN `θ`](#theta-join-θ)
    - [NATURAL JOIN](#natural-join)
    - [EQUI-JOIN/JOIN](#equi-joinjoin)
    - [Inner Join vs Equi-Join](#inner-join-vs-equi-join)
    - [OUTER JOIN](#outer-join)
    - [LEFT OUTER JOIN](#left-outer-join)
    - [RIGHT OUTER JOIN](#right-outer-join)
    - [FULL OUTER JOIN](#full-outer-join)
  - [Interaction](#interaction)
  - [Division](#division)
  - [REPLACEMENT WITH OPERATIONS](#replacement-with-operations)
      - [Implementation of JION via `SELECTION` and `PRODUCT`](#implementation-of-jion-via-selection-and-product)
      - [INTERACTION via DIFFERENCE](#interaction-via-difference)
      - [DIVISION (HARD)](#division-hard)

Two Concepts for Storing and retrieving data (of query language) 
1. Relational Algebra 
2. Relational Calculus

## Relationship of SQL instructor and Relational Algebra
![](https://i.imgur.com/J63JAJp.png)


### Operations of relational Algebra
![](https://i.imgur.com/NGpS6jH.png)

> Complete Set
>: 1. SELECT(σ)
>: 2. Projection(π)
>: 3. Rename (ρ)
>: 4. Union operation (υ)
>: 5. Set Difference (-)



## RESTRICT `σ`

- `σ` : the condition

For example  
![](https://i.imgur.com/BOvT5uf.png)  

![](https://i.imgur.com/ytw1zi0.png)  

It can be presented  
$$σ_{height<170 \ AND \ weight < 60 (學生資料表)}$$

```mysql
FROM Student
WHERE Height < 179 AND Weight < 60
```


## PROJECT `π`

To Create A New Table (projection) **with specified attributes from the old one**

> A new Table with Attribute A and B from table R  
> ![](https://i.imgur.com/7dG9aBv.png)  

- Relation Algebra `πA(R)`   
  > A : Attribute from table R   

- Priority of selection and projection depend on SITUATION

## UNION

Merge two tables and remove duplicate data    

![](https://i.imgur.com/QK08qqb.png)      

> Relation Algebra : $R \cup S$    
> : ![](https://i.imgur.com/otdaoWf.png)    

## CARTESIAN PRODUCT `R X S`

Merge a set of n attributes from table R with m attributes from table S   

![](https://i.imgur.com/4jC4AD6.png)  
- We get a merged Table with `n+m = 6` attributes  and `X * Y = 9` data  

```mysql
SELECT *
FROM 學生表 , 課程表
--- OR ---
SELECT *
FROM 學生表 cross join 課程表
```
![](https://i.imgur.com/eJKvDpu.png)   
![](https://i.imgur.com/hQ4LOY1.png)    

## Difference   
Remove the common part of R and S    
![](https://i.imgur.com/lVlsJRj.png)    

> `R - S` : get the part belonging to R AND no S   
> `S - R` : get the part belonging to S AND no R   
>> ![](https://i.imgur.com/tPm9yMo.png)  

For Example
```diff
IF
- A = {1,2,4,5}
- B = {3,4,5,6}
THEN
! A AND B = {4,5}
! A - B ={1,2}
```

## JOIN 
[Reference](https://www.gushiciku.cn/dc_hk/201216734)

![](https://i.imgur.com/IglA41f.png)  

**Join operation is essentially a CARTESIAN PRODUCT followed by a selection criterion.**   

- Relational Algebra : $R ⨝pS$    

### JOIN TYPES

![image](https://user-images.githubusercontent.com/68631186/127828038-954c84aa-f5c1-459c-bc87-a8cb8e583997.png)   

OUTER JOIN TYPES
1. LEFT OUTER JOIN  
2. RIGHT OUTER JOIN
3. Full Outer Join

INNER JOIN (Condition Join)   
An inner join, only those tuples that satisfy the matching criteria are included, while the rest are EXCLUDED      
- `THETA JOIN`,`EQUI JOIN`, `NATURAL JOIN`

### THETA JOIN `θ`
The GENERAL case of JOIN operation is called a Theta join.

- Merging two tables with operation `=,＜,≦,＞,≧,≠`
- `θ` are represented one of these operations

```diff
(A X B) where A.X θ B.Y 
+ X : attribute of table A 
+ Y : attribute of table B 
```

### NATURAL JOIN

```sql
FROM A 
NATURAL JOIN B

-- is equal
FROM A 
INNER JOIN B
ON A.c = B.c 
```
- It's recommended to use `INNER JOIN` instead of using `NATURAL JOIN` 

![](https://i.imgur.com/Y2dDe14.png)  

### EQUI-JOIN/JOIN

When a theta join uses only equivalence condition (`=`), it becomes a equi join.   
It merges Attributes from table A `=` Attributes from table B to form a desired table
- A EQUI-JOIN TABLE may have contain duplicates

```sql
SELECT column_list 
FROM table1, table2....
WHERE table1.column_name =
table2.column_name; 

-- or

SELECT *
FROM table1 
JOIN table2
[ON (join_condition)]
```
![](https://i.imgur.com/qO74Xdb.png)  
![](https://i.imgur.com/WdmmXj7.png)  
- It contain duplicates columns (兩個班級代號欄位)

### Inner Join vs Equi-Join

Inner join can have equality (`=`) and other operators (like `<,>,<>`) in the join condition.

Equi join only have equality (`=`) operator in the join condition.
- Equi join can be an Inner join,Left Outer join, Right Outer join



### OUTER JOIN
In an outer join, along with tuples that satisfy the matching criteria, we also include first (left outer join), second (right outer join) or all **tuples that do not match the criteria.**

- **If tuples are not satisfied with the matching criteria , they will be set as `NULL` by default**

The reason for using OUTER JOIN is because we don't want to miss any of information as merging two different tables

```sql
SELECT *
FROM TableA 
[RIGHT|LEFT] [OUTER JOIN] TableB
ON TableA.PK = TableB.FK
```

### LEFT OUTER JOIN
![](https://i.imgur.com/iaiKGHH.png)

```sql
SELECT *
FROM 老師資料表 AS A 
LEFT OUTER JOIN 課程資料表 AS B
ON A.老師編號 = B.老師編號
```
- IF `老師資料表` can not reference the matching records => set `NULL` by default  

![image](https://user-images.githubusercontent.com/68631186/111078928-bff07d00-8532-11eb-9d77-aa6443b9e8f6.png)   

If we want to query for a certain teacher who has no any lectures.    
We can use `LEFT OUTER JOIN EXCLUDING INNER JOIN`   
```sql
SELECT *
FROM 老師資料表 AS A 
LEFT OUTER JOIN 課程資料表 AS B
ON A.老師編號 = B.老師編號
WHERE B.老師編號 IS NULL /* Filter the Table that have been "left join" */
```

### RIGHT OUTER JOIN  

```diff
  +----+---------+
  | PK | Value   |
  +----+---------+
- |  1 | both ab |<---+
  |  2 | only a  |    |
  +----+---------+    |
-                     |    ? return null
  +----+---------+    |    |
  | PK | Value   |    |    |
  +----+---------+    |    |
- |  1 | both ab |----+    |
- |  3 | only b  |---------+
  +----+---------+

 +------+------+---------+---------+
 | A_PK | B_PK | A_Value | B_Value |
 +------+------+---------+---------+
 |    1 |    1 | both ab | both ba |
 | NULL |    3 | NULL    | only b  |
 +------+------+---------+---------+
```

```sql
SELECT A.PK, B.PK, A.Value, B.Value
FROM Table_A RIGHT JOIN Table_B    
ON A.PK = B.PK;
```

### FULL OUTER JOIN

![image](https://user-images.githubusercontent.com/68631186/127832877-ae5fb919-7bff-4ff6-a57a-b5109c922975.png)  

```sql
SELECT A.PK AS A_PK, B.PK AS B_PK,
       A.Value AS A_Value, B.Value AS B_Value
FROM Table_A A
FULL OUTER JOIN Table_B B
ON A.PK = B.PK;
```
## Interaction 
Section belonging to both R and S   
![](https://i.imgur.com/gJiMj1e.png)    

> Relational Algebra : $R \cap S=R-(R-S）$   
> ![](https://i.imgur.com/71LOY0u.png)   

Fro example
```sql
SELECT *
FROM 老師資料表 AS A RIGHT OUTER JOIN 課程資料表 AS B
ON A.老師編號 = B.老師編號
ORDER BY B.課程代碼
```
- In B.老師編號 we get only two teachers which are `T0001` and `T0002`    
- so the 老師編號 from A will be set NULL except `T0001` and `T0002`    

![image](https://user-images.githubusercontent.com/68631186/111079068-52911c00-8533-11eb-8d60-2a2b160d41df.png)   

## Division 

Remove the duplicates from the two tables (R and S)      
![](https://i.imgur.com/h5XOOMM.png)    

Relational Algebra : $R\div S   

Using nested query to implement division   

For Example  
```mysql
/* main */
SELECT A.ID
FROM Student AS A, StudentCourse AS B
WHERE A.ID = B.ID 
AND B.CourseID = 
( /* Sub to get specified CourseId from C */
 SELECT C.CourseID FROM Course AS C
 WHERE C>CourseName = 'Data Base'
)
```

Algo of division
1. Taking out whole row in table R that is responding to table S
2. and then remove these data related to table S

For Example      
![](https://i.imgur.com/n7jefxR.png)

![](https://i.imgur.com/Fuy0DGy.png)

![](https://i.imgur.com/UUdCMoR.png)

![](https://i.imgur.com/1BvYwR2.png)   


![](https://i.imgur.com/2FnLbOi.png)   
![](https://i.imgur.com/i29LNom.png)  
![](https://i.imgur.com/IIo3vKt.png)  
![](https://i.imgur.com/tSO31sY.png)  
![](https://i.imgur.com/Aybv42G.png)  
![](https://i.imgur.com/lkUsJHD.png)  

This is how we do using nested query concept to implement division  
![image](https://user-images.githubusercontent.com/68631186/111079672-e49a2400-8535-11eb-88c0-6b81629ccbc6.png)   

For Example   
- To list all students' optional Courses
```mysql
/* We need student to get which `optional Courses` they choose */

SELECT 課名
/* Informations for Course is in the table 課程檔 */ 
FROM 課程檔 AS C
WHERE NOT EXISTS
(
  SELECT *
  /* DIVIDED */
  FROM 學生檔 AS A
  WHERE NOT EXISTS
  (
    SELECT *
    /* DIVIDEND */
    FROM 選課檔 AS B
    /* CONDITION */
    WHERE C.課號 = B.課號 AND A.學號 = B.學號
  )
)
```

## REPLACEMENT WITH OPERATIONS   

#### Implementation of JION via `SELECTION` and `PRODUCT`
![](https://i.imgur.com/71c0IOb.png)

#### INTERACTION via DIFFERENCE
![](https://i.imgur.com/i1zhXEw.png)

#### DIVISION (HARD)
![](https://i.imgur.com/Twjwka1.png)
