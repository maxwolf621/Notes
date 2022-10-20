# Syntax

- [Syntax](#syntax)
  - [Where ... NOT IN (Select ...)](#where--not-in-select-)
  - [CASE WHEN( ... , .... , ...) THEN](#case-when------then)
  - [Inner Join/Join](#inner-joinjoin)
  - [Round(expression,round to X places)](#roundexpressionround-to-x-places)
  - [Date Between X and Y](#date-between-x-and-y)
  - [Datediff(date_1 , date_2) `MIN(ID)`](#datediffdate_1--date_2-minid)
  - [Delete Duplicate](#delete-duplicate)
  - [Limit ... Offset ...](#limit--offset-)
  - [ORDER BY COUNT(*)](#order-by-count)
  - [Compare with itself](#compare-with-itself)
  - [Group by duplicate one column table](#group-by-duplicate-one-column-table)
  - [Group by two cols](#group-by-two-cols)
  - [Mode(分子,分母) = 餘數](#mode分子分母--餘數)
  - [count](#count)
  - [CheatSheet](#cheatsheet)
    - [XXX TABLE](#xxx-table)
  - [ALTER TABLE `[ADD/MODIFY COLUMN/CHANGE/ALTER]` ATTRIBUTE](#alter-table-addmodify-columnchangealter-attribute)
  - [CHANGE ATTRIBUTE](#change-attribute)
  - [INSERT INTO](#insert-into)
  - [Aggression FUNCTION](#aggression-function)
  - [CONDITION](#condition)
  - [SHOW (BROWSING)](#show-browsing)

## Where ... NOT IN (Select ...)

```sql
SELECT YYY
FROM TABLE
Where XXX NOT IN ( SELECT ... )
```

```sql 
-- Output **all** the names in the table salesperson, who didn’t have sales to company `RED`.

Salesperson table
+----------+------+--------+-----------------+-----------+
| sales_id | name | salary | commission_rate | hire_date |
+----------+------+--------+-----------------+-----------+
|   1      | John | 100000 |     6           | 4/1/2006  |
|   2      | Amy  | 120000 |     5           | 5/1/2010  |
|   3      | Mark | 65000  |     12          | 12/25/2008|
|   4      | Pam  | 25000  |     25          | 1/1/2005  |
|   5      | Alex | 50000  |     10          | 2/3/2007  |
+----------+------+--------+-----------------+-----------+
                                      ^
Company table                         |
+---------+--------+------------+     |
| com_id  |  name  |    city    |     |
+---------+--------+------------+     |
|   1     |  RED   |   Boston   |     |
|   2     | ORANGE |   New York |     |
|   3     | YELLOW |   Boston   |     |
|   4     | GREEN  |   Austin   |     |
+---------+--------+------------+     |
                              ^       |
Orders Table                  |       |
+--------------+------------+---------+----------+--------+
| (PK)order_id | order_date | com_id  | sales_id | amount |
+--------------+------------+---------+----------+--------+
| 1            |   1/1/2014 |    3    |    4     | 100000 |
| 2            |   2/1/2014 |    4    |    5     | 5000   |
| 3            |   3/1/2014 |    1    |    1     | 50000  |
| 4            |   4/1/2014 |    1    |    4     | 25000  |
+--------------+------------+---------+----------+--------+

Output
+------+
| name | 
+------+
| Amy  | 
| Mark | 
| Alex |
+------+ 



-- Nested Query

-- choose slaesperson name where the oder com'id is not red
SELECT name 
from salesperson 
WHERE (SELECT sales_id From orders WHERE com_id NOT IN 
      -- red
      (SELECT com_id FROM company WHERE CITY = 'RED')
GROUP BY sales_id);

-- INNER JOIN
SELECT name FROM salesperson
WHERE sales_id NOT IN ( SELECT b.sales_id 
                        FROM company      AS a
                        INNER JOIN orders AS b
                        ON a.com_id = b.com_id
                        WHERE a.name = 'RED');
```

## CASE WHEN( ... , .... , ...) THEN

- SWAP 

```sql
+----+----+----+
| x  | y  | z  |
|----|----|----|
| 13 | 15 | 30 |
| 10 | 20 | 15 |
+----+----+----+

+----+----+----+----------+
| x  | y  | z  | triangle |
|----|----|----|----------|
| 13 | 15 | 30 | No       |
| 10 | 20 | 15 | Yes      |
+----+----+----+----------+

CASE WHEN (x + y > z) AND (x + z > y) AND (y + z > x) 
          THEN 'Yes'
          ELSE 'No' 
          END 
FROM triangle

select *, 
    IF(x + y > z AND x + z > y AND y + z > x, 'Yes', 'No') as triangle 
    FROM triangle;
```

## Inner Join/Join

```sql
-- SYNTAX
FROM R 
JOIN S
ON R.C = S.C

-- is equal
FROM R
INNER JOIN S
ON R.C = S.C 

R
+---+---+---+
| A | B | C |
+---+---+---+
S
+---+---+---+
| C | D | E |
+---+---+---+

              S.C does not exist
+-----+-----+-----+-----+-----+
| R.A | R.B | R.C | S.D | S.E |
+-----+-----+-----+-----+-----+
```

## Round(expression,round to X places)

```sql
  +------------+------------+------------+--------+---------------+-------+
  | product_id | start_date | end_date   | price  | purchase_date | units |
  +------------+------------+------------+--------+---------------+-------+
  | 1          | 2019-02-17 | 2019-02-28 | 5      | 2019-02-25    | 100   |
  | 1          | 2019-03-01 | 2019-03-22 | 20     | 2019-03-01    | 15    |
  | 2          | 2019-02-01 | 2019-02-20 | 15     | 2019-02-10    | 200   |
  | 2          | 2019-02-21 | 2019-03-31 | 30     | 2019-03-22    | 30    |
  +------------+------------+------------+--------+---------------+-------+

Select ROUND(SUM(price*units)/SUM(units),2)
```

## Date Between X and Y

```sql
  Prices table : 
  +------------+------------+------------+--------+
  | product_id | start_date | end_date   | price  |
  +------------+------------+------------+--------+
  | 1          | 2019-02-17 | 2019-02-28 | 5      |
  | 1          | 2019-03-01 | 2019-03-22 | 20     |
  | 2          | 2019-02-01 | 2019-02-20 | 15     |
  | 2          | 2019-02-21 | 2019-03-31 | 30     |
  +------------+------------+------------+--------+

  UnitsSold table:
  +------------+---------------+-------+
  | product_id | purchase_date | units |
  +------------+---------------+-------+
  | 1          | 2019-02-25    | 100   |
  | 1          | 2019-03-01    | 15    |
  | 2          | 2019-02-10    | 200   |
  | 2          | 2019-03-22    | 30    |
  +------------+---------------+-------+

  FROM UnitsSold 
  INNER JOIN Prices 
  on UnitsSold.purchase_date BETWEEN Prices.start_date AND Prices.end_date 
  AND Prices.product_id = UnitsSOld.product_id 
  GROUP BY UnitsSold.product_id
  +------------+------------+------------+--------+---------------+-------+
  | product_id | start_date | end_date   | price  | purchase_date | units |
  +------------+------------+------------+--------+---------------+-------+
  | 1          | 2019-02-17 | 2019-02-28 | 5      | 2019-02-25    | 100   |
  | 1          | 2019-03-01 | 2019-03-22 | 20     | 2019-03-01    | 15    |
  | 2          | 2019-02-01 | 2019-02-20 | 15     | 2019-02-10    | 200   |
  | 2          | 2019-02-21 | 2019-03-31 | 30     | 2019-03-22    | 30    |
  +------------+------------+------------+--------+---------------+-------+
```

## Datediff(date_1 , date_2) `MIN(ID)`

## Delete Duplicate

```sql
+----+------------------+
| Id | Email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
| 3  | john@example.com |
+----+------------------+

DELETE FROM Person AS P
WHERE Id NOT IN
/**
TMP      P
+------+ +----+
|MinId | | Id | 
+------+ +----+
| 1    | | 1  | 
| 2    | | 2  | 
+------+ | 3  | 
         +----+
*/
(SELECT MinId 
 FROM (SELECT MIN(Id) AS MinId
       FROM Person 
       GROUP BY Email) AS TMP)
```

## Limit ... Offset ...

```sql
+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+

+---------------------+
| SecondHighestSalary |
+---------------------+
| 200                 |
+---------------------+

-- Return Null (S)
-- Nested Select
SELECT (
  -- Null Or Second Highest
  SELECT DISTINCT Salary
  FROM Employee
  ORDER BY Salary DESC
  LIMIT 1 OFFSET 1) 
AS SecondHighestSalary;
```

## ORDER BY COUNT(*)

```sql
+--------------+-----------------+
| order_number | customer_number |
|--------------|-----------------|
| 1            | 1               |         
| 2            | 2               |         
| 3            | 3               |         
| 4            | 3               |         
+--------------+-----------------+
SELECT customer_number 
FROM orders
GROUP BY customer_number 
ORDER BY COUNT(*) DESC
LIMIT 1
```

## Compare with itself

```sql
+-----+
| x   |
|-----|
| -1  |
| 0   |
| 2   |
+-----+


SELECT MIN(ABS(a.x - b.x)) AS shortest
+-----+-----+
| x   | x   |
|-----|-----|
|-1   | 0   |
|-1   | 2   |
| 0   |-1   |
| 0   | 2   |
| 2   |-1   |
| 2   | 0   |
+-----+-----+
FROM point AS a JOIN point AS b
ON a.x <> b.x;
```

## Group by duplicate one column table

The `COUNT(*)` function represents the count of all rows present in the table (including the NULL and NON-NULL values).

```sql
-- Biggest no Duplicate Number
+---+   +---+-----+
|num|   |num|count|
+---+   +---+-----+ +---+-----+
| 8 |   | 8 |  2  | |num|count|
| 8 |   | 3 |  2  | +---+-----+
| 3 |   | 1 |  1  | | 1 |  1  |
| 3 |   | 4 |  1  | | 4 |  1  | 
| 1 |   | 5 |  1  | | 5 |  1  |
| 4 |   | 6 |  1  | | 6 |  1  |
| 5 |   +---+-----+ +---+-----+
| 6 |   
+---+   

Select max(num)
From (
  SELECT num 
  from nums 
  group by num 
  having count(*) = 1) as E
)
```

## Group by two cols

```sql
+-------------+-------------+-------------+
| actor_id    | director_id | timestamp   |
+-------------+-------------+-------------+
| 1           | 1           | 0           |
| 1           | 1           | 1           |
| 1           | 1           | 2           |
| 1           | 2           | 3           |
| 1           | 2           | 4           |
| 2           | 1           | 5           |
| 2           | 1           | 6           |
+-------------+-------------+-------------+

/**
    +-------------+-------------+-------------+
    | actor_id    | director_id | timestamp   |
    +-------------+-------------+-------------+
    | 1           | 1           | 0           |
    |             |             | 1           |
    |             |             | 2           |
    |-----------------------------------------|
    | 1           | 2           | 3           |
    |             |             | 4           |
    |-----------------------------------------|
    | 2           | 1           | 5           |
    |             |             | 6           |
    +-------------+-------------+-------------+
*/
SELECT actor_id, 
       director_id 
FROM ActorDirector
GROUP BY actor_id, director_id
HAVING COUNT(timestamp) >= 3;
```

## Mode(分子,分母) = 餘數

Please write a SQL query to output movies with an odd numbered `id` and a description that is not `boring`. 
```sql
+---------+-----------+--------------+-----------+
|   id    | movie     |  description |  rating   |
+---------+-----------+--------------+-----------+
|   1     | War       |   great 3D   |   8.9     |
|   2     | Science   |   fiction    |   8.5     |
|   3     | Irish     |   boring     |   6.2     |
|   4     | Ice song  |   Fantasy    |   8.6     |
|   5     | House card|   Interesting|   9.1     |
+---------+-----------+--------------+-----------+

Where MOD(id,2) = 1 
  And Description <> 'boring'

```
Each Person's Team's size

  Employee Table:
  +-------------+------------+
  | employee_id | team_id    |
  +-------------+------------+
- |     1       |     8      |
- |     2       |     8      |
- |     3       |     8      |
  |     4       |     7      |
+ |     5       |     9      |
+ |     6       |     9      |
  +-------------+------------+
  
  Table Team
  +------------+-------------+
  | team_id    | team_size   |
  +------------+-------------+
- |     8      |     3       |
  |------------|-------------|
  |     7      |     1       |
  |--------------------------|
+ |     9      |     2       |
  +------------|-------------|
  SELECT team_id, 
        count(employee_id) AS team_size
  
  +------------+-------------+
  | team_id    | employee_id |
  +------------+-------------+
- |     8      |     1       |
- |            |     2       |
- |            |     3       |
  |------------|-------------|
  |     7      |     4       |
  |--------------------------|
+ |     9      |     5       |
+ |            |     6       |
  +------------+-------------+
  FROM Employee
  GROUP BY team_id
  AS Team


  SELECT e.employee_id,
        t.team_size 
  FROM Employee AS e
  Left Join Team AS t
  ON e.team_id = t.team_id 
  +-------------+------------+------------+
  | employee_id | team_id    | team_size  |
  +-------------+------------+------------+ 
- |     1       |     8      |     3      |
- |     2       |     8      |     3      |
- |     3       |     8      |     3      |
  |     4       |     7      |     1      |
+ |     5       |     9      |     2      |
+ |     6       |     9      |     2      |
  +-------------+------------+------------+

```sql 
  +------------+-------------+
  | sell_date  | product     |
  +------------+-------------+
  | 2020-05-30 | Headphone   |
  | 2020-06-01 | Pencil      |
  | 2020-06-02 | Mask        |
  | 2020-05-30 | Basketball  |
  | 2020-06-01 | Bible       |
  | 2020-06-02 | Mask        |
  | 2020-05-30 | T-Shirt     |
  +------------+-------------+

  Result table:
  +------------+----------+------------------------------+
  | sell_date  | num_sold | products                     |
  +------------+----------+------------------------------+
  | 2020-05-30 | 3        | Basketball,Headphone,T-shirt |
  | 2020-06-01 | 2        | Bible,Pencil                 |
  | 2020-06-02 | 1        | Mask                         |
  +------------+----------+------------------------------+
```

## count 

- [count(*) vs count(1)](https://stackoverflow.com/questions/2710621/count-vs-count1-vs-countpk-which-is-better)

```sql
insert into boss(boss_code, boss_name) values
('JN','John'),
('Paul','Paul');

insert into subordinate(boss_code, subordinate_code, subordinate_name) values
('JN','GO','George'),
('JN','RG','Ringo');
```

```sql
boss_code	boss_name	boss_code	subordinate_code	subordinate_name
JN	      John	    JN	      GO	              George
JN	      John	    JN	      RG	              Ringo
Paul	    Paul	    (null)	  (null)	          (null)
select *
from boss
left join subordinate on subordinate.boss_code = boss.boss_code

-- WRONG
boss_name	count
Paul	    1
John	    2  
select boss.boss_name, count(1)
from boss
left join subordinate on subordinate.boss_code = boss.boss_code
group by boss.boss_name

-- count not null and null value 
select boss.boss_name, count(1)
from boss
left join subordinate on subordinate.boss_code = boss.boss_code
group by boss.boss_name

-- count only not null row
boss_name	count
John	    2  
Paul	    0
select boss.boss_name, count(subordinate)
from boss
left join subordinate on subordinate.boss_code = boss.boss_code
group by boss.boss_code,boss.boss_name
order by boss.boss_name

-- this is different from count(*)
select boss.boss_name, count(subordinate.*)
from boss
left join subordinate on subordinate.boss_code = boss.boss_code
group by boss.boss_name
order by boss.boss_name
```

## CheatSheet

- [](https://gist.github.com/bradtraversy/c831baaad44343cc945e76c2e30927b3)

### XXX TABLE
```sql
-- INT 
-- CHAR    (1~255字元字串)
-- VARCHAR (不超過255字元不定長度字串)
-- TEXT    (不定長度字串最多65535字元)
CREATE TABLE tablename(
  FIELD_1 type,
  ...,
  ...,
  PRIMARY KEY(FIELD_1),
  FOREIGN KEY (FIELD_X) REFERENCES users(FIELD_X)
)

DROP TABLE [IF EXIST] tablename
```

## ALTER TABLE `[ADD/MODIFY COLUMN/CHANGE/ALTER]` ATTRIBUTE
```sql
ALTER TABLE tablename MODIFY field1 type1
ALTER TABLE tablename MODIFY field1 type1 NOT NULL ...
ALTER TABLE tablename CHANGE old_name_field1 new_name_field1 type1
ALTER TABLE tablename CHANGE old_name_field1 new_name_field1 type1 NOT NULL ...
ALTER TABLE tablename ALTER field1 SET DEFAULT ...
ALTER TABLE tablename ALTER field1 DROP DEFAULT
ALTER TABLE tablename ADD new_name_field1 type1

-- FIRST & AFTER : CHANGE THE ORDER
ALTER TABLE tablename ADD new_name_field1 type1 FIRST
ALTER TABLE tablename ADD new_name_field1 type1 AFTER another_field

ALTER TABLE tablename DROP field1
ALTER TABLE tablename ADD INDEX (field);
```
## CHANGE ATTRIBUTE

```sql
DELETE FROM tablename WHERE condition;
UPDATE tablename set field = new_value WHERE CONDITION
```
## INSERT INTO

```sql
INSERT INTO FILED, FIELD2, ... 
VALUES (value1, value2, ...)
```

## Aggression FUNCTION

```sql
SELECT COUNT(id) FROM users;
SELECT MAX(age) FROM users;
SELECT MIN(age) FROM users;
SELECT SUM(age) FROM users;
SELECT UCASE(first_name), LCASE(last_name) FROM users;
```

## CONDITION

```sql
field1 = value1
field1 <> value1
field1 LIKE 'value _ %'
field1 IS NULL
field1 IS NOT NULL
field1 IS IN (value1, value2)
field1 IS NOT IN (value1, value2)

-- 少用
condition1 AND condition2
condition1 OR condition2
```

## SHOW (BROWSING)

```sql
SHOW DATABASES;
SHOW TABLES;
SHOW FIELDS FROM table / DESCRIBE table;
SHOW CREATE TABLE table;
SHOW PROCESSLIST;
KILL process_number;
```