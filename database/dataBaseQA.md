# Database Concept

- [Database Concept](#database-concept)
  - [Transaction](#transaction)
  - [ACID](#acid)
  - [Relationship of ACID](#relationship-of-acid)
  - [AUTOCOMMIT](#autocommit)
  - [Concurrency Problems](#concurrency-problems)
  - [Granularity](#granularity)
  - [Shared and Exclusive Locks](#shared-and-exclusive-locks)
  - [Isolation Level (SET TRANSACTION ISOLATION LEVEL)](#isolation-level-set-transaction-isolation-level)
    - [READ UNCOMMITTED `*`](#read-uncommitted-)
    - [READ COMMITTED `**`](#read-committed-)
    - [REPEATABLE READ `***`](#repeatable-read-)
    - [SERIALIZABLE `*****`](#serializable-)
  - [Innodb. Multi-Version Concurrency Control, MVCC](#innodb-multi-version-concurrency-control-mvcc)
    - [Undo-log](#undo-log)
    - [Snapshot Read](#snapshot-read)
    - [Current Read](#current-read)
    - [Innodb Next-Key Locks](#innodb-next-key-locks)


## Transaction

[Database Transaction](https://stackoverflow.com/questions/974596/what-is-a-database-transaction)

A transaction is a unit of work that you want to treat as "a whole." It has to either happen in full or not at all.

transactions are there to ensure, that no matter what happens, the data you work with will be in a sensible state. 

For example 
```vim
# operation 1
accountA -= 100; # transaction 1
accountB += 100; # transaction 2

# operation 2
accountB += 100; # transaction 3
accountA -= 100; # transaction 4
```
- If something goes wrong between the first and the second operation in the pair you have a problem - either 100 bucks have disappeared, or they have appeared out of nowhere.

**A transaction is a mechanism that allows you to mark a group of operations and execute them in such a way that either they all execute (commit), or the system state will be as if they have not started to execute at all (rollback).**
```vim
beginTransaction;
accountB += 100; transaction 1
accountA -= 100; transaction 2
commitTransaction;
```
- It guarantees that there will NOT be a situation where money is withdrawn from one account, but not deposited to another.

## ACID

Atomicity : 
- **Transaction被視為不可分割的最小單元，Transaction的所有操作要只有分兩種結果全部成功提交，或者全部失敗做Rollback。**
- Rollback可以用Undo Log來實現，Undo Log記錄著Transaction所執行的Latest Changes，在Rollback時**反向**undo這些修改操作。
> undo log : record contains information about how to undo the latest change by a transaction to a clustered index record

一致性（Consistency）
- Database在**Transaction執行前**後都保持一致性狀態。  
在一致性狀態下，**所有Transaction對同一個資料讀取結果都是相同的**。

隔離性（Isolation）
- 一個Transaction所做的修改在**最終提交(Commit)以前，對其它Transaction是不可見的**。

持久性（Durability）
- 一旦Commit Transaction，則其所做的修改將會永遠保存到Database中。即使系統發生崩潰，Transaction執行的結果也不會丟失。
- **系統發生崩潰可以用Redo Log進行恢復，從而實現持久性。與Undo Log邏輯不同，Redo Log是物理修改(the redo log is physically represented on disk by two files named `ib_logfile0` and` ib_logfile1`. )。**

> redo log : disk-based data structure used during crash recovery to correct data written by incomplete transactions

## Relationship of ACID

- 只有滿足一致性(執行前一樣)，Transaction的執行結果才是正確的。 
- 在無Concurrency的情況下，Transaction執行，隔離性一定能夠滿足。此時只要能滿足原子性，就一定能滿足一致性。 
- 在Concurrency的情況下，多個Transactions並行執行，Transaction不僅要滿足原子性，還需要滿足隔離性，才能滿足一致性。 
- Transaction滿足持久化是為了能應對系統崩潰的情況。

![圖 14](images/8022a431f9851e659e7f36c28e68219c237e3f5da3656709a37722912bb2536c.png)  

## AUTOCOMMIT
mysql使用AUTOCOMMIT，所以不用特別使用`START TRANSACTION`語句開啟一個Transaction

## Concurrency Problems

[](https://shirleysong.life/skills/skill-learning-mysql-transactions-concurrency-problems/)

為解決Concurrency Problems，資料庫管理系統提供了Transaction的隔離級別，讓用戶以更輕鬆的方式處理Concurrency Problems

1. Lost updates  
Occur when two different transactions are trying to update the same column on the same row within a database at the same time.

2. Dirty Reads
   occurs when Transaction 1 (T2) is permitted to read data that is being modified by another running transaction (T1) before this transaction (T1) is committed.
![圖 15](images/2af82ce8a68cca07b6ed51030fdaae18aa1e638fb18f14ff105d47523d12ca7a.png)  

3. Non-repeatable reads  
   Occur when we read some records twice in one transaction but the values are not the same.

4. Phantom Reads
   occurs when, during a transaction, new rows are added (or deleted) by another transaction to the records being read. A Phantom row means a ghost row that appears where it is not visible before. 
   ![圖 16](images/7379ed2c78233299d418d49e839072aa0e5a92e70cf54157393823570e78d908.png)  


## Granularity

MySQL has three lock levels: 
1. row-level locking
2. page-level locking (were available in older engines of MySQL)
3. table-level locking

應該盡量只鎖定需要修改的那部分資料，而不是所有的資料。  
> 鎖定的資料量越少，發生鎖競爭的可能就越小，系統的Concurrency效率提高。

但是加鎖需要消耗資源，鎖的各種操作（包括獲取鎖、釋放鎖、以及檢查鎖狀態）都會增加系統開銷。因此Granularity越小，系統開銷就越大。

在選擇Granularity時，需要在鎖開銷和Concurrency效率間做衡量。

## Shared and Exclusive Locks

- [MySQL InnoDB: Difference Between `FOR UPDATE` and `LOCK IN SHARE MODE`](https://stackoverflow.com/questions/32827650/mysql-innodb-difference-between-for-update-and-lock-in-share-mode)


互斥鎖（Exclusive, X）
- **一個Transaction對資料對象 A 加了 X 鎖，就可以對 A 進行讀取和更新，這期間其它Transaction不能對 A 加任何鎖。**

共享鎖（Shared, S）
- 一個Transaction對資料對象 A 加了 S 鎖，可以對 A 進行**讀取**操作，但是不能進行更新操作，期間其它Transaction能對 A 加 Shared Lock S 鎖，但是不能加 X 鎖。

For example, updating counters, where you read value in 1 statement and update the value in another. 

Here using `LOCK IN SHARE MODE` will allow 2 transactions to read the same initial value. 

So if the counter was incremented by 1 by both transactions, the ending count might increase only by 1 - since both transactions initially read the same value.

Using `FOR UPDATE` would have locked the **2nd transaction from reading the value till the first one is done.** This will ensure the counter is incremented by 2.

## Isolation Level (SET TRANSACTION ISOLATION LEVEL)

```sql
SET TRANSACTION ISOLATION LEVEL
{ READ UNCOMMITTED  | READ COMMITTED | REPEATABLE READ | SERIALIZABLE }
```

### READ UNCOMMITTED `*`

Transaction A更新但未Commit資料，Transaction B 不能更新只能讀取(直到A交易Committed後)，確保交易更新資料不會有問題。

### READ COMMITTED `**`

Transaction在更新並確認資料前，其他交易不能讀取該資料

### REPEATABLE READ `***`

保證在同一個Transaction中多次讀取同一數據的結果是一樣的。
- **讀取中資料會被鎖定，確保同一筆交易中的讀取資料必須相同。**

### SERIALIZABLE `*****`

Transaction A 讀取時，Transaction B 更新要排隊；  
Transaction A 更新時，Transaction B 讀取與更新都需要排隊

- 該隔離級別需要加鎖實現，因為要使用加鎖機制**保證同一時間只有一個Transaction執行。**

## Innodb. Multi-Version Concurrency Control, MVCC

**在實際場景中讀操作往往多於寫操作**，因此又引入了讀寫鎖來避免不必要的加鎖操作，例如讀和讀沒有互斥關係。讀寫鎖中讀和寫操作仍然是互斥的，**MVCC 建立多個版本快照，寫操作更新最新的版本快照，而讀操作去讀舊版本快照**

### Undo-log
```java
INSERT INTO t(id, x) VALUES(1, "a");
UPDATE t SET x="b" WHERE id=1;
UPDATE t SET x="c" WHERE id=1
```

根據 MySQL 的 AUTOCOMMIT，每個操作都會被當成一個Transaction來執行
![圖 3](images/fe84741c6a78129f081d99ee7076f221932d6e10da564257412d685d7f8b6912.png)  
- `INSERT`、`UPDATE`、`DELETE` 操作會創建一個日誌，Transaction版本以`TRX_ID`表示。 `DELETE` 可以看成是一個特殊的 UPDATE，還會額外將 `DEL` 置為 `1`。

### Snapshot Read

You don't need any lock for `select` OP
```sql
SELECT * FROM table ...;
```
### Current Read

For Ops `INSERT`、`UPDATE`、`DELETE` ops which needs lock to access latest data。

You can also add x/s lock to `SELECT` OP manually
```sql
SELECT * FROM table WHERE ? lock in share mode;
SELECT * FROM table WHERE ? for update;
```

### Innodb Next-Key Locks

MVCC不能解決Phantom Reads，Next-Key Locks 就是為了解決這個問題而存在的。

在REPEATABLE READ iso-level下，使用 MVCC + Next-Key Locks 決Phantom Reads問題。