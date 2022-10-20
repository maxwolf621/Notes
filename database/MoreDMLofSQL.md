# More DML of SQL
###### tags: `DataBase`
[TOC]

## SELECT
```sql
SELECT Attribute1, Attribute2 , ... AttributeN  # SELECT 指定 Attributes

SELECT *                                
FROM Table1, Table2, ... , TableN       # FROM 指定 tables
WHERE CONDITION                         # REQUIRED condition for the fetch data
GROUP BY Attribute1, ... , AttributeN   # Group the qualifizierte ressourcen
ORDER BY Attribute1, ... , AttributeN   # ORDER
LIMIT 
```

## AS

```sql
/*
 * Alias of <pre> CourseName <pre> 
 * is <pre> CP <pre>
 */
SELECT CP AS CourseName,point
FROM Courses
```

### Comparison Operation with `WHERE` syntax

| OP  | FUNCTION  | EXAMPLE      |
| -   |  -------  | -------------|
|**=**| **Equal** |**Grade = 60**|
| <>  | Not Equal | Grade <>60   |
| <   | Less Than | Grade < 60   |
| <=  | Less Equal| Grade <=60   |
| >   | Large Than| Grade > 60   |
| >=  | LargeEqual| Grade >=60   |

```mysql
/** 
  * It would show up a table with 
  * attributes ID, CourseID and Grade 
  * corresponding with the condition 
  */
SELECT ID,CourseID,Grade
FROM CourseSelection
WHERE Grade < 60
```

## Logical Comparison with `WHERE` 
```sql
WHERE Grade >= 60 AND CourseID='C005'
WHERE CourseID='C004' OR CourseID='C005'
WHERE NOT Grade >=60
```

## `IS NULL`
```sql
use ex_db;
SELECT ID,Name.Grade
From CourseSelection
/**
  * <pre> IS </pre> can not
  * replace with 
  * <pre> = </pre>
  */
WHERE Grade IS NULL

```

### Wildcard operation with `LIKE`, `IN(...)`, `BETWEEN ... IN ...`

Wildcard Characters in MS Access
| Symbol | Description| Example |
| -----  |  ----------| ------  |
|*       |	Represents zero or more characters	                |bl* finds 任何bl開頭的名稱
|?       |  Represents a single character	                      | h?t finds hot, hat, and h[a~z]t
|[]      |	Represents any single character within the brackets |h[oa]t finds hot and hat, but not hit
|!       |  Represents any character not in the brackets	      |h[!oa]t finds hit, but not hot and hat
|-       |  Represents a range of characters                    |c[a-b]t finds cat and cbt
|#       |	Represents any single numeric character	            |2#5 finds 2[0到0]5

Wildcard Characters in SQL Server
| Symbol | Description| Example |
| -----  |  ----------| ------  |
| `%`      |	Represents zero or more characters                        | bl`%` finds 任何bl開頭名稱   
|`[]`      |  Represents any single character within the brackets	      | h`[oa]`t finds hot and hat only 
|`^`	     |  Represents any character not in the brackets              | h`[^oa]`t finds data whose name is not `hot` and `hat`
|`-`       |  Represents a range of characters                          | c`[a-c]`t finds cat,cbt and cct
| `_`      |  Represents a single character	                            | h_t finds h`[a-z]`t   


### wildcard with `WHERE` attribute `LIKE ... `  
```sql
WHERE Name LIKE 'J_HN'
WHERE Name LIKE '%Y%'
```

### `WHERE` attribute `IN(... , ... )`
```sql
/**
  * The IN operator allows you 
  * to specify multiple values in a <pre> WHERE </pre> clause.
  * 
  * The IN operator is a shorthand 
  * for multiple <pre> OR </pre> conditions
  */
SELECT column_name(s)
FROM table_name
WHERE column_name IN (value1, value2, ...);

/**
  * use <pre> OR <pre> instead
  */
WHERE column_name1=value1 or column_name2=value2 or column_name3 = ... ;

/**
  * <pre> IN </pre> use expression
  */
SELECT column_name(s)
FROM table_name
WHERE column_name IN (SELECT STATEMENT);
```

### `Where` attribute `BETWEEN ... AND ...`
```mysql
use ex_db;
SELECT Name, ID, Grade
FROM CourseSelection 
WHERE Grade BETWEEN 60 AND 90 
/* or */
WHERE Grade >= 60 AND Grade <=90
```


## AVG(),SUM(),MIN(),MAX(),COUNT()

[MINandMAX](https://www.w3schools.com/sql/sql_min_max.asp)
[SUM,COUNT,AVG](https://www.w3schools.com/sql/sql_count_avg_sum.asp)

## SORT

### `ORDER BY`
```sql
/**
  * ASC (default) : Ascending
  * DESC : Descending
  */
ORDER BY ID ASC, Grade DESC
```

![](https://i.imgur.com/tYINXRX.png)
### `GROUP BY`
The `GROUP BY` statement is often used with aggregate functions (`COUNT()`, `MAX()`, `MIN()`, `SUM()`, `AVG()`) to **group the result-set by one or more columns**.
```sql
SELECT column_name(s)
FROM table_name
WHERE condition
GROUP BY column_name(s)
ORDER BY column_name(s)

/**
  * GROUP(SORT) BY Country
  */
SELECT COUNT(CustomerID), Country
FROM Customers
GROUP BY Country;
```
![](https://i.imgur.com/U1EJCc1.png)

## LIMIT
```sql
/**
  * <p> 
  * limit display number of column_name records
  * </p>
  */
SELECT column_name(s)
FROM table_name
WHERE condition
LIMIT number;

/** FOR EXAMPLE **/
SELECT 學號,課號,成績
FROM 選課表
WHERE 課號='C005'
ORDER BY 成績 DESC
LIMIT 2     #ONLY SHOW UP 2 RECORDS
```
![](https://i.imgur.com/Ig4xCFO.png)

## HAVING 
The `HAVING` clause was added to SQL because the `WHERE` keyword cannot be used with aggregate functions.
```sql 
SELECT column_name(s)
FROM table_name
WHERE condition
GROUP BY column_name(s)
HAVING condition
ORDER BY column_name(s);

/**
  * list the number of customers in each country. 
  * Only include countries with more than 5 customers
  */
SELECT COUNT(CustomerID), Country
FROM Customers
GROUP BY Country # Sort By Attribute Country
HAVING COUNT(CustomerID) > 5 # Group that has customerId > 5 
ORDER BY COUNT(CustomerID) DESC;
```

## SELECT DISTINCT

Forbid showing up duplicate records
It can't work with `COUNT(*)` , `MIN()`, `MAX()`

[EXAMPLE](https://www.w3schools.com/sql/sql_distinct.asp)
