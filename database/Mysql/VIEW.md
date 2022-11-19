###### tags: `DataBase`  
# VIEWS (Ansichten)

**A view is a virtual table** based on the result-set of an SQL statement.   
The fields in a view are fields from one or more real tables in the database.  

You can add SQL statements and functions to a view and present the data as if the data were coming from one single table as the following   
![](https://i.imgur.com/DeRrWon.png)   


Good Sides  
> **Using view is good for hiding the information to keep it inaccessible for sepcidied users**   
> Make records (information in database) more readable for the users    
> For programmer it also makes Structure of Database maintainable   

Bad Sides
> Efficiency sucks

General Syntax
```sql
CREATE VIEW View_Name 
AS
SELECT column1, column2, ...
FROM Base_Table
WHERE condition;
```

## Create View with multiple base tables 

```sql
create view StudentGrade
AS
select Name,CourseName,Grade
FROM Student as S, Course AS C, StudentCourse as SC
WHERE S.ID = SC.ID AND SC.CourseID = C.CourseID
```

## Create View with new Alias of Attribute

```sql
create view Alias(StudentID,StudentName)
AS
select ID,Name
FROM Student
```

##  Insert New Values Or Update the Values

The Base Table would also be modified 

```sql
INSERT INTO VIEW
VALUES
(
UPDATE VIEW
SET
WHERE
)
```


## Alter the view 

Alter the way of Display for the informations of the table 

General Syntax
```sql
/* [ .. ] : OPTIONAL */
ALTER VIEW Name_View[Attribute1,Attribute2,...]
AS
SELECT
FROM 
[WHERE] 
[GROUP BY]
[HAVING]
```

## Delete the View

```sql
DROP VIEW View_Table1,View_Table2, ... 
```

## Type of VIEW tables

### Row-Column Subset VIEWs
>  The informations of views would (Row and Col) would always $ <=$ bast table

example : to create a view from base table Employee without showing the Salary

### Join VIEWs

to merge with multiple base tables together

select who is the first three starting with heighest points of the subjects
```sql
CERATE VIEW First3
AS
SELECT Name,CourseName, Grade
FROM Student as S, Course as C, StudentCourses as SC
WHERE 
C.CourseID = SC.CourseID 
AND C.CourseName = 'C001' 
AND S.ID = SC.ID
/* Heightst */
ORDER BY Grade DESC
LIMIT 3

/* Find out who got >= 90 */
SELECT *
FROM `First3`
WHERE Grade >=90
```
### Statistic Summary VIEWs

VIEW with `AVG()` , `COUNT()` , ... etc

Each person's average grade
```sql
CREATE VIEW TermGrade(ID,Average)
AS
SELECT ID,AVG(Grade)
FROM StudentCourse
GROUP BY ID
```
