###### tags: `DataBase`  
# VIEWS (Ansichten)

- [VIEWS (Ansichten)](#views-ansichten)
  - [Create View with Multiple base tables](#create-view-with-multiple-base-tables)
  - [Create View with new Alias of Attribute](#create-view-with-new-alias-of-attribute)
  - [Insert \& Update View](#insert--update-view)
  - [Alter the view](#alter-the-view)
  - [Drop The Views](#drop-the-views)
  - [Type of VIEW tables](#type-of-view-tables)
    - [Row-Column Subset VIEWs](#row-column-subset-views)
    - [Equi-Join VIEWs](#equi-join-views)
    - [Statistic Summary VIEWs](#statistic-summary-views)

**A view is a virtual table** based on the result-set of an SQL statement.   
The fields in a view are fields from one or more real tables in the database.  

You can add SQL statements and functions to a view and present the data as if the data were coming from one single table as the following   
![](https://i.imgur.com/DeRrWon.png)   


Pros and Cons

1. **Using view is good for hiding the information to keep it inaccessible for specific users**   
2. Make records (information in database) more readable for the users    
3. For programmer it also makes Structure of Database maintainable   
4. **Efficiency sucks**

General Syntax
```sql
CREATE VIEW View_Name 
AS
```

## Create View with Multiple base tables 

```sql
create view StudentGrade
AS
SELECT name,courseName,grade
FROM Student as S, Course AS C, StudentCourse as SC
WHERE 
    S.ID = SC.ID 
    AND 
    SC.CourseID = C.CourseID
```

## Create View with new Alias of Attribute

```sql
CREATE VIEW Alias(StudentID,StudentName)
AS
SELECT ID, Name
FROM Student
```

##  Insert & Update View

The Base Table would also be modified 
```sql
INSERT INTO VIEW
VALUES
(
    UPDATE VIEW
    SET  ...
    WHERE ...
)
```

## Alter the view 

Alter the way of Display for the information of the table. 

General Syntax
```sql
/* [ .. ] : OPTIONAL */
ALTER VIEW Name_View[Attribute1,Attribute2,...]
AS  ... 
SELECT ...
FROM   ...
[WHERE] 
[GROUP BY]
[HAVING]
```

## Drop The Views
```sql
DROP VIEW View_Table1,View_Table2, ... 
```

## Type of VIEW tables

### Row-Column Subset VIEWs

The information of views would always `<=` base table.
For example, to create a view from base table Employee without showing the Salary

### Equi-Join VIEWs

Select who is the first three starting with hightest points of the subjects
```sql
CERATE VIEW First3 AS
SELECT Name,CourseName, Grade
FROM Student as S, Course as C, StudentCourses as SC
WHERE 
    C.CourseID = SC.CourseID 
    AND 
    C.CourseName = 'C001' 
    AND 
    S.ID = SC.ID
ORDER BY Grade DESC
LIMIT 3;

-- Using View 
-- Find out whose grade >= 90
SELECT *
FROM First3
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
