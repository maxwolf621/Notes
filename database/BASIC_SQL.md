# Basic SQL

- [Basic SQL](#basic-sql)
  - [DDL](#ddl)
    - [CREATE DATABASE](#create-database)
    - [ALTER DATABASE](#alter-database)
    - [CREATE TABLE](#create-table)
    - [ALTER TABLE](#alter-table)
  - [DML](#dml)
    - [INSERT INTO](#insert-into)
    - [UPDATE _table SET](#update-_table-set)
    - [DELETE FROM](#delete-from)
  - [DCL](#dcl)
    - [GRANT `{DML_OPS}` ON `{DATABASE}` TO `{USER}`](#grant-dml_ops-on-database-to-user)
    - [REVOKE `{DML}` ON `{DATABASE}` FROM `{USER}`](#revoke-dml-on-database-from-user)

![](https://i.imgur.com/v6rAbRL.png)  

DDL : To Define Structure of Database, variable's datatype and length     
DML : To modify (manipulate) the data    
DCL : To give authority of the users     

## DDL
![](https://i.imgur.com/SGIfZsA.png)

### CREATE DATABASE

To Create A Database
```sql
/* Syntax*/
create Database [IF NOT EXISTS] 資料庫名稱
/* FOR EXAMPLE */
create Database Courses
```

### ALTER DATABASE
```sql
Alter Database 資料庫名稱
CHARACTER SET Encode_Name
COLLATE Collation Name

/* FOR EXAMPLE */
Alter Database Courses
CHARACTER SET utf8
```

### CREATE TABLE

Create A Table
```sql
Create table tableX
(Attribute{Datatype|Domain}[NULL|NOTNULL][DEFAULT VALUE][RESTRICTION])

RRIMARY KEY(attributeX) --Cant not be NULL 
UNIQUE(attributeY)      --Can  be NULL

/**
  * attributeX in tableX is  
  * dependent on(reference to)
  * attributeL in tableJ
  */
FOREIGN KEY(attributeZ) REFERENCE tableJ(attributeL) [ON DELETE option] [ON UPDATE option]
```

For example  
![](https://i.imgur.com/VfZCdk4.png)  
```sql
/**
  * Order of creating tables
  *  Student --> Courses first -> CourseSelection second
  */

/**
 * create table student 
 *  use ENGINE=InnoDB
 *  default encode latin1
 */
CREATE TABLE `Student` (
  `ID` char(8) NOT NULL,
  `Name` char(20) DEFAULT NULL,
  `Mobile` char(12) DEFAULT NULL,
  `Address` char(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Mobile` (`Mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

-- create table Courses 
CREATE TABLE `Course` (
  `CourseID` char(8) NOT NULL,
  `CourseName` char(20) DEFAULT NULL,
  `Piont` int(11) DEFAULT 3,
  `Option` char(2) DEFAULT NULL,
  PRIMARY KEY (`CourseID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

/** 
 * table CoursesSelection
 *  With <pre> ON UPDATE CASCADE </pre> and 
 *  <pre> ON DELETE CASCADE </pre> means
 *  when data in table <pre> Student <pre> have been modified (update or delete ..etc) 
 *  then corresponding data in table <pre> CourseSelection </pre> will also be modified
 */
CREATE TABLE `StudentCourse` (
  `ID` char(8) NOT NULL,
  `CourseID` char(8) NOT NULL,
  `Grade` int(11) NOT NULL,
  PRIMARY KEY (`ID`,`CourseID`),
  KEY `CourseID` (`CourseID`),
  CONSTRAINT `StudentCourse_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `Student` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `StudentCourse_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `Course` (`CourseID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1
```

### ALTER TABLE

with `ALTER TABLE Name_Of_TableX`, we can do   
```sql 
/**
  * <li> ADD new Attribute </li>
  * <li> MODIFY the Attribute </li>
  * <li> DROP the Attribute </li>
  */
ADD Name_OF_Attribute {dataType | Domain} [NULL|NOT NULL]
MODIFY Name_OF_Attribute{dataType | Domain} [NULL | NOT NULL]
DROP Name_OF_Attribute
```

For example   
add new attribute to table Student   
```sql
ALTER TABLE student 
ADD Email CHAR(50)
ADD Sex CHAR(1) Default 'M'
``` 
modify attribute in table Student   
```sql
ALTER TABLE student
/* Address from CHAR(20) TO CHAR(50) NOT NULL */
MODIFY Address CHAR(50) NOT NULL
```
Drop the Attribute in table Student   
```sql
ALTER TABLE student
DROP Email
``` 
When executing the `DROP` DO MAKE SURE THERE ARE NO REFERENCE EXISTING (SAME CONCEPT AS CONSTRUCTOR IN CPP or JAVA)    
So the order of DROP is    
1. CourseSelection  first   
2. Courses, Student after   


## DML

BASIC COMMAND   
> INSERT  
> UPDATE  
> DELETE  
> SELECT  


### INSERT INTO 
```sql  
INSERT INTO Name_OF_Table<attribute>  
VALUES(<attribute> | <SELECT>)  
/* for example */   
INSERT INTO Student  
VALUES   
('001','John','1111','USA'),  
('002','Mai','2222','JAPAN')  
```

Insert data in the table a to other table b
![](https://i.imgur.com/qEUimbZ.png)

```sql
INSERT INTO Student

-- Select ALL from OldStudent
SELECT * FROM OldStudent
```

### UPDATE _table SET
```sql
UPDATE Name_OF_Table
SET {AttributeY=valueY, AttributeX=valueX , .... , AttributeN=valueN }
[WHERE<CONDITION>]

/* for example */
UPDATE Student
SET Mobile='1111'
WHERE Mobile IS NULL and ID='S002'
```

Update multiple records at same time
```sql
UPDATE Courses
SET Point='4', Option='N'
WHERE CourseName='DataStructure'
```

Update grade
```sql
UPDATE CourseSelection
SET Grade = Grade*1.2
WHERE Grade<70
```

### DELETE FROM
```sql
DELETE FROM `Student`
WHERE Name LIKE 'John'
```

## DCL

### GRANT `{DML_OPS}` ON `{DATABASE}` TO `{USER}`
Grant who has right to access database
```sql
GRANT {INSERT|UPDATE|DELETE|SELECT} 
ON NameOfDataBase
TO NameOfTheUser

/** FOR EXAMPLE **/
--These two users can modify data Using SELECT,INSERT
GRANT SELECT,INSERT ON GUESTS
TO TIM,SIMON  

-- EVERYONE can modify data Using SELECT 
GRANT SELECT ON GUESTS
TO PUBLIC  
```

### REVOKE `{DML}` ON `{DATABASE}` FROM `{USER}`

Revoke the authority to access the database from User
```sql
REVOKE {INSERT|UPDATE|DELETE|SELECT} 
ON NameOfDataBase
FROM NameOfTheUser

/** FOR EXAMPLE **/
-- USER TIM cant not modify data with INSERT
REVOKE INSERT ON GUESTS
FROM TIM 
```
