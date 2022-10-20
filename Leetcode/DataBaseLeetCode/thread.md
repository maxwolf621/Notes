

### Nested `SELECT ... WHERE ....`
```sql
Select a.COLUMNS
FROM a
WHERE ( SELECT b.COLUMNS from b WHERE b.CONDITION 
        NOT IN (SELECT c.COLUMNS from c WHERE c.CONDITION )  )
```

### `IF( exp, xxx,  yyy)` VS `CASE WHEN exp THEN xxx ELSE yyyy END`


### COMPARISON TWO SAME TABLES 

```
A table
+-----+
| x   |
|-----|
| -1  |
| 0   |
| 2   |
+-----+

B table
+-----+ 
| x   |
|-----|
| -1  |
| 0   |
| 2   |
+-----+
```

```sql
SELECT MIN(ABS(A.x - B.x)) 
FROM A
JOIN B
ON A.x <> B.x
```

It works like this
```
a.x = -1 => join table 0,2 of b.x
a.x = 0  => join table -1,2 of b.x
a.x = 2  => join table -1,0 of b.x
```

Join Table will look like
```
-1 - 0
-1 - 2
 0 - -1
 0 - 2
 2 - -1
 2 - 0
```
- It will pick up min one

## `HAVING COUNT(*) = 1`

## Odd Number 

```sql
MOD(ATTR, integer) = q 
MODE(id, 2) = 1  -- id could be 1,2,3,4,5,6,7, ...
```

## GROUP BY TWO ATTRIBUTES

```
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

GROUP BY actor_id, director_id
+-------------+-------------+-------------+
| actor_id    | director_id | timestamp   |
+-------------+-------------+-------------+
| 1           | 1           | 0           |
| 1           | 1           | 1           |
| 1           | 1           | 2           |
|-------------|-------------|-------------|
| 1           | 2           | 3           |
| 1           | 2           | 4           |
|-------------|-------------|-------------|
| 2           | 1           | 5           |
| 2           | 1           | 6           |
+-------------+-------------+-------------+
```

## ORDER BY NULL


