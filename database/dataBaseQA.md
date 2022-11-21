# Database Concept

- [Database Concept](#database-concept)
  - [Transaction](#transaction)
  - [ACID](#acid)
    - [Redo Log & UndoLog](#redo-log--undolog)
    - [Relationship of ACID](#relationship-of-acid)
  - [AUTOCOMMIT](#autocommit)
  - [Concurrency Problems](#concurrency-problems)
  - [Isolation Level (SET TRANSACTION ISOLATION LEVEL)](#isolation-level-set-transaction-isolation-level)
    - [READ UNCOMMITTED 我在操作時候你只能讀](#read-uncommitted-我在操作時候你只能讀)
    - [READ COMMITTED 等我資料COMMIT你才能讀](#read-committed-等我資料commit你才能讀)
    - [REPEATABLE READ 不管我讀幾次都結果都一樣](#repeatable-read-不管我讀幾次都結果都一樣)
    - [SERIALIZABLE 我用這資源你就得等我用完](#serializable-我用這資源你就得等我用完)
  - [Granularity of Locks](#granularity-of-locks)
  - [Shared and Exclusive Locks](#shared-and-exclusive-locks)
  - [MySQL Deadlock](#mysql-deadlock)
    - [Foreign Key的Share Lock](#foreign-key的share-lock)
    - [READ COMMITTED VS REPEATABLE READ](#read-committed-vs-repeatable-read)
    - [Non Unique Index's Gap Lock](#non-unique-indexs-gap-lock)
    - [Conclusion](#conclusion)
  - [Innodb. Multi-Version Concurrency Control, MVCC](#innodb-multi-version-concurrency-control-mvcc)
    - [Undo-log](#undo-log)
    - [Snapshot Read](#snapshot-read)
    - [Current Read](#current-read)
    - [Innodb Next-Key Locks](#innodb-next-key-locks)
  - [優化Index](#優化index)


## Transaction

[Database Transaction](https://stackoverflow.com/questions/974596/what-is-a-database-transaction)

**A transaction is a unit of work that you want to treat series operations as a whole. It has to either happen in full or not at all.**

transactions are there to ensure, that no matter what happens, the data you work with will be in a sensible state. 
For example 
```sql
Begin Transaction --------------------
accountA -= 100;  --  operation#1  
accountB += 100;  --  operation#2
End               -------------------

Begin Transaction --------------------
accountB += 100;  --  operation#1
accountA -= 100;  --  operation#2
End               -------------------
```
- If something goes wrong between the first and the second operation in the pair you have a problem - either 100 bucks have disappeared, or they have appeared out of nowhere.

## ACID

Atomicity(描述Transaction的單位，回滾機制)
- **Transaction 被視為不可分割的最小單元，Transaction的所有操作要只有分兩種結果全部成功提交，或者全部失敗做Rollback。**
- Rollback可以用Undo Log來實現，Undo Log記錄著Transaction所執行的Latest Changes，在Rollback時**反向UNDO**這些修改操作。

Consistency (描述Transaction`s`讀同一個資料)
- Database在**Transaction執行前**後都保持一致性狀態。  
  在一致性狀態下，**所有Transactions對同一個資料讀取結果都是相同的**。

Isolation (描述每個Transaction的可見關係)
- 一個Transaction所做的修改在**最終提交以前，對其它Transactions是不可見的**。

持久性Durability (描述成功提交的Transaction資料能應付系統崩壞)
- 一旦`提交`Transaction，則其所做的修改將會永遠保存到Database中。  
  即使系統發生崩潰，Transaction執行的結果也不會丟失。
- **系統發生崩潰可以用Redo Log進行恢復，從而實現持久性。**
  與Undo Log邏輯不同，Redo Log是物理修改(the redo log is physically represented on disk by two files named `ib_logfile0` and` ib_logfile1`. )。

### Redo Log & UndoLog

redo log : disk-based data structure used during crash recovery to correct data written by incomplete transactions

undo log : record contains information about how to undo the latest change by a transaction to a clustered index record

### Relationship of ACID

1. 只有滿足一致性(執行前一樣)，Transaction的執行資料結果才是正確的。 
2. (Isolation->Atomicity->Consistency)在無Concurrency的情況下，Transaction執行，隔離性一定能夠滿足。
   此時只要能滿足原子性，就一定能滿足一致性。 
3. 在Concurrency的情況下，多個Transactions並行執行(concurrent computing)，Transaction不僅要滿足原子性，還需要滿足隔離性，才能滿足一致性。 
4. Transaction滿足持久化是為了能應對系統崩潰的情況。

![圖 14](images/8022a431f9851e659e7f36c28e68219c237e3f5da3656709a37722912bb2536c.png)  

## AUTOCOMMIT
mysql使用AUTOCOMMIT，所以不用特別使用`START TRANSACTION`語句開啟一個Transaction

## Concurrency Problems

[skill-learning-mysql-transactions-concurrency-problems](https://shirleysong.life/skills/skill-learning-mysql-transactions-concurrency-problems/)

1. Lost updates  (T1跟T2同時操作同一個ROW)
Occur when two different transactions are trying to update the same column on the same row within a database at the same time.

2. Dirty Reads (T1跟T2有不一樣的ROW值)
   occurs when Transaction 2 (T2) is permitted to read data that is being modified by another running transaction (T1) before this transaction (T1) is committed.
![圖 15](images/2af82ce8a68cca07b6ed51030fdaae18aa1e638fb18f14ff105d47523d12ca7a.png)  

3. Non-repeatable reads(讀兩次records不同)
   Occur when we read some records twice in one transaction but the values are not the same.

4. Phantom Reads (每次讀row會減少/增加)
   occurs when, during a transaction, new rows are added (or deleted) by another transaction to the records being read. 
   A Phantom row means a ghost row that appears where it is not visible before. 
   ![圖 16](images/7379ed2c78233299d418d49e839072aa0e5a92e70cf54157393823570e78d908.png)  

## Isolation Level (SET TRANSACTION ISOLATION LEVEL)


為解決Concurrency Problems，資料庫管理系統提供了Transaction的隔離級別，讓用戶以更輕鬆的方式處理Concurrency Problems


```sql
SET TRANSACTION ISOLATION LEVEL
{ READ UNCOMMITTED  | READ COMMITTED | REPEATABLE READ | SERIALIZABLE }
```

### READ UNCOMMITTED 我在操作時候你只能讀

Transaction A更新但未COMMIT資料，Transaction B 不能更新只能讀取(TILL Transaction A Committed)，確保交易更新資料不會有問題。

如果A更新失敗Rollback，B可能會讀到更新的資料造成髒讀

### READ COMMITTED 等我資料COMMIT你才能讀

Transaction在更新並確認資料前，其他交易不能讀取該資料

### REPEATABLE READ 不管我讀幾次都結果都一樣
**容易造成Deadlock**

保證在同一個Transaction中多次讀取同一數據的結果是一樣的。
- **讀取中資料會被鎖定，確保同一筆交易中的讀取資料必須相同。**

### SERIALIZABLE 我用這資源你就得等我用完

**容易造成Deadlock**
Transaction A 讀取時，Transaction B 更新要排隊；  
Transaction A 更新時，Transaction B 讀取與更新都需要排隊  

- 該隔離級別需要加鎖實現，因為要使用加鎖機制**保證同一時間只有一個Transaction執行。**


## Granularity of Locks

MySQL has three lock levels: 
1. row-level locking
2. ~~page-level locking (were available in older engines of MySQL)~~
3. table-level locking

應該盡量只鎖定需要修改的那部分資料，而不是所有的資料。  
**鎖定的資料量越少，發生race condition的可能就越小，系統的Concurrency效率提高。**

**加鎖需要消耗資源，鎖的各種操作（包括獲取鎖、釋放鎖、以及檢查鎖狀態）都會增加System開銷。因此Granularity越小，系統開銷就越大**。故在選擇Granularity時，需要在鎖開銷和Concurrency效率間做衡量。

## Shared and Exclusive Locks

- [MySQL InnoDB: Difference Between `FOR UPDATE` and `LOCK IN SHARE MODE`](https://stackoverflow.com/questions/32827650/mysql-innodb-difference-between-for-update-and-lock-in-share-mode)

互斥鎖（Exclusive, X）
- **一個Transaction對資料對象 A 加了 X 鎖，就可以對 A 進行讀取和更新，這期間其它Transactions不能對 資料A 加任何鎖。**

共享鎖（Shared, S）
- 一個Transaction對資料對象 A 加了 S 鎖，可以對 A 進行**讀取**操作，但是不能進行更新操作，期間其它Transactions能對 資料A 加 S 鎖，但是不能加 X 鎖。

For example, updating counters, where you read value in 1 statement and update the value in another. 

Here using LOCK IN SHARE MODE will allow 2 transactions to read the same initial value. 

So if the counter was incremented by 1 by both transactions, the ending count might increase only by 1 - since both transactions initially read the same value.

Using `FOR UPDATE` would have locked the **2nd transaction from reading the value till the first one is done.** This will ensure the counter is incremented by 2.


## MySQL Deadlock

- [MySQL Deadlock 問題排查與處理](https://yuanchieh.page/posts/2020/2020-12-26_mysql-deadlock-%E5%95%8F%E9%A1%8C%E6%8E%92%E6%9F%A5%E8%88%87%E8%99%95%E7%90%86/)
- [【MySQL】Lock 與 Index 關係和 Deadlock 分析](https://yuanchieh.page/posts/2022/2022-04-25-mysqllock-%E8%88%87-index-%E9%97%9C%E4%BF%82%E5%92%8C-deadlock-%E5%88%86%E6%9E%90/)
- [My SQL Deadlock 如何最小化和處理死鎖](https://yingchenchung.medium.com/my-sql-deadlock-%E6%AD%BB%E9%8E%96-transaction-556c869bde7)

Deadlock 主要是多個 Transactions 手上握有對方需要的資源，在等待資源釋放的同時卻也不會釋放手上的資源

- 盡可能減少 Update / Delete 在單一 Transaction 中的數量
- Lock 時請依照同樣的順序 (例如 `select … for update`)
- 降低 Lock 的層級，避免 lock tables 的操作
- 警慎選擇 index，因為過多的 index 可能會造成 deadlock
- 考慮降低 isolation level


SQL lock的設定
- `innodb_deadlock_detect`
  是否偵測 deadlock，預設開啟
- `innodb_lock_wait_timeout`
  如果沒有開啟 Deadlock detect，建議設定較短的 wait timeout 
- `innodb_print_all_deadlocks`
  將所有 Deadlock 錯誤輸出至 error log

>>> 由於Deadlock Detection 預設是開啟，但如果在High Concurrency下會有效能的影響，如果預期 Deadlock 狀況不多可以改透過 `innodb_deadlock_detect` 選項關閉，用 `innodb_lock_wait_timeout` 一直等不到 lock 發生 timeout 而觸發 rollback 取代


### Foreign Key的Share Lock

If a FOREIGN KEY constraint is defined on a table, any insert, update, or delete that requires the constraint condition to be checked sets shared record-level locks
```sql
Begin transaction -- transaction#1
-- product_id是FK 
insert into orders (product_id) values (1) -- Block
end 

Begin Transaction -- transaction#2
update product set sold=1 where id = 1 -- Waiting
End 
```

### READ COMMITTED VS REPEATABLE READ

當`WHERE` condition 是範圍搜尋使用RC時條件不符合的row 會 release，在做大規模的 the lock，大規模的UPDATE/DELETE建議使用   

使用RR不管有沒有符合條件都會BLOCK   

### Non Unique Index's Gap Lock

GAP LOCK : 鎖加在不存在的空閒空間，可以是兩個索引記錄之間，也可能是第一個索引記錄之前或最後一個索引之後的空間
```
+---------+
|    1    |  
+---------+ <---- BLOCK  (GAP)
|    2    | <---- BLOCK
+---------+
|    3    |
+---------+
|  ....   |
+---------+
```

造成某個Transaction要插入的鎖如果處於GAP LOCK的區間會造成DEAD LOCK

### Conclusion

- 如果是用 ORM，記得檢查 Query
- 如果需要用 Secondary Index 改變欄位，建議可以用批次 (RoR 就是 find_in_batch) 或是先篩選出 Primary Key (預設 select 不會有 lock)，再使用 Primary Key 當作修改條件避免 Gap Lock
- 如果由於死鎖而失敗，需Retry。
- **保持交易小巧且持續時間短**，以使交易不易發生衝突。
- **如果使用鎖定讀取（SELECT … FOR UPDATE或SELECT … FOR SHARE），請嘗試使用較低的隔離級別，例如READ COMMITTED**。
- 修改交易中的多個表或同一表中的不同行集時，每次都要以一致的順序執行這些操作。 若交易正確排隊的話，並且不會死鎖。 例如，將數據庫操作組織到應用程序內的函數中，或調用存儲的例程，而不是在不同位置編碼多個類似的INSERT，UPDATE和DELETE語句序列。
- 增加 Index 要仔細評估，Secondary Index 會造成寫入效能下降，使用EXPLAIN SELECT來確定MySQL服務器認為哪個索引，使查詢需要掃描更少的索引記錄，從而設置更少的鎖。
- 使用較少的鎖定。 如果有能力允許SELECT從舊快照返回數據，則不要在其中添加FOR UPDATE或FOR SHARE子句。 **在這裡使用READ COMMITTED隔離級別是件好事**，因為同一事務中的每個一致讀取均從其自己的新快照讀取。

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


## 優化Index

A clustered index (SQL Server, MySQL/InnoDB) is a table stored in an index B-Tree structure. There is no second data structure (heap-table) for the table. 

A non-clustered index is an index that refers to another data structure containing further table columns. e.g non-primary key columns

[Rails 效能優化 資料庫索引 Database Indexing](https://reurl.cc/KXXvWR)
>>> 大部分的 DBSM 都會預設把主鍵（Primary Key / ID）加上索引，現在假設有一個資料表（table）A，A 的 ID 欄位有被加上索引，則實際上在資料庫中，有另外一張表（我們叫它 A’）裡面儲存了 A 裡面的所有 ID，而 A’ 每一個資料（ID）都對應到 A 中的完整資料。所以今天我們想要找 A 中一個 ID = 3 的資料，資料庫會用 Binary Search Algorithm（或是其他也很快的演算法，通常時間複雜度會是 O(log(N)）在 A’ 中快速找到 ID = 3 這筆資料，然後再從這筆資料連到 A 中對應的資料。
>>> 實際情況下，A’ 這個擁有 A 的 ID 資料的儲存結構通常會以 Hash 或是 B-tree 實作。Hash 在搜尋不能重複的資料時，效率會比較好，因此適合用在主索引鍵（Primary Index）和唯一索引（Unique Index）；B-tree 適合用在可以允許重複資料的一般索引（Non-Unique Index）。

索引其實是一種資料結構（data structure）B-tree 結構

- 索引可以分成叢集（Clustered）與非叢集（Non-Clustered）兩種類型。

通常會加上索引的欄位
- 主鍵 Primary Key（通常是預設）
- 外部鍵 Foreign Key
- 常被放在查詢子句中（ORDER, WHERE, GROUP）的欄位