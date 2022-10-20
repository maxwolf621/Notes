# DataBaseNote
### Index

- [Linux Command](LinuxSql.sh)
- [Basic SQL](BASIC_SQL.md)
- **[MORE DML](MoreDMLofSQL.md)**
- [ER model](ER_model.md)
- **[Normalization](Normalization.md)**
- **[RelationAlgebra](RelationalAlgebra.md)**
- [VIEW](VIEW.md)
- **[Data Base Questions](DataBaseLeetCode\solution.md)**


#### [Data Base Transaction](https://stackoverflow.com/questions/974596/what-is-a-database-transaction)


A transaction is a unit of work that you want to treat as "a whole." It has to either happen in full or not at all.

transactions are there to ensure, that no matter what happens, the data you work with will be in a sensible state. 

For example 
```vim
# operation 1
accountA -= 100;
accountB += 100;

# operation 2
accountB += 100;
accountA -= 100;
```
- If something goes wrong between the first and the second operation in the pair you have a problem - either 100 bucks have disappeared, or they have appeared out of nowhere.

**A transaction is a mechanism that allows you to mark a group of operations and execute them in such a way that either they all execute (commit), or the system state will be as if they have not started to execute at all (rollback).**
```vim
beginTransaction;
accountB += 100;
accountA -= 100;
commitTransaction;
```
- It guarantees that there will NOT be a situation where money is withdrawn from one account, but not deposited to another.