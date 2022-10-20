###### tags: `DataBase`
# Normalization
[TOC]

正規化(Normalization)主要是**對表格做分割**的動作做處理  
- 在資料正規化的過程中,每個階段都是以不同類型的相依性(Dependence)做為表格處理與切割的依據。
- 將Attributes/Tuples之間的關係,分散到不同表格。

## 目的 
- 降低資料重覆性(Data Redundancy)
- 避免產生插入、刪除或更新可能的異常(Anomalies)  
### Anomalies

新增異常 Insert Anomalies
- Must insert new Value into different entities at same time 

修改異常 Update Anomalies  
- Other information need to update while updating some specified information  

刪除異常 Delete Anomalies  
- Deleting one row of the Entity causes other entities to lose their information 

### For Example  

#### EX1
**新增**記錄時 (輸入資料時,必須等主鍵輸入才可進行)  
- 當有一新的分公司加入時,但未有任何交易經歷時,將造成產品代號為NULL 

**更新**資料時 (需要一起修改許多相關的值組)
- 若單一分公司有多筆記錄在檔案中,此分公司若有資料異動,則必須更動多筆資料。如此不僅浪費空間,而且更新時亦浪費時間,也容易造成資料不一致的情況。

**刪除**記錄時 (刪除資料時,會把過多的資訊刪掉)  
- 若某分公司只有一筆交易經歷,若我們要刪除此分公司的交易資料時,則將連帶刪除該分公司資訊

### EX2
![](https://i.imgur.com/RSsomyI.png)    
1. Insert Anomalies   
    - Add A new Course C004  
    ![](https://i.imgur.com/KXQ2iDh.png)    
2. Update Anomalies    
    - Update Cost of Course C002 from 4000 to 4500  
    ![](https://i.imgur.com/9NkPgI2.png)  
3. Delete Anomalies
    - Delete Course C003  
    ![](https://i.imgur.com/BiWvmHT.png)  
    - Because only one student takes the course. so if we delete `C003`, we will lose all the information of `C003`  

## Functional Dependency  
![](https://i.imgur.com/OWTo0fw.png)

> Def   
> : 給定一個關聯R,R的屬性子集Y功能相依於R的屬性子集X,   
> 則: 若且唯若(if and only if) 無論何時R的兩個Tuples若有相同的X值  
時,則必有相同的Y **(對於關聯R中的每個X值,均有唯一的Y值來對應)**    
> 
> X為Determinate,Y為Dependent  
![](https://i.imgur.com/gEJ9kia.png)  

1. `X->Y` in R 成立,不代表`Y->X`也成立  
2. Functional Dependency是`One To One`的關係  
3. 關聯R中的X若為主鍵(候選鍵),則關聯R中所有其它屬性必功能相依於X  

#### For example 
一個關聯表格中的數個屬性間有功能相依性可能歲是
- 如所在地可決定分公司電話區碼 (即: 所在地->區碼)
- 如地址所在之區域可決定郵遞區號
- 如員工編號可決定員工姓名 (即: 員工編號->姓名)


### 價值性 Trivial or Non-Trivial

#### Trivial  
若功能相依右邊(Non-Key Attribute)之相依因素，為左邊決定因素的部份(partial)集合時，稱此功能相依為沒價值的。  
例如: `{供應商代號，產品代號}→產品名稱`  

#### Non-trivial
若功能相依右邊(Non-Key Attribute)之相依因素，不為左邊Composite PK決定因素的部份(Partial)集合時，稱此功能相依為非沒價值的  
`{產品號,公司號} -> 單價`  

## Functional Dependency 相依型態
- 完全功能相依 (Full Functional Dependency)  
- 部份功能相依 (Partial Functional Dependency)  
- 遞移相依 (Transitive Dependency)  

## Key Attribute 
在實務上,鍵值屬性(e.g, Human ID)通常是指主鍵,能構成主鍵或候選鍵的所有屬性  
反之,則稱為非鍵值屬性(Non-Key Attribute)  
![](https://i.imgur.com/yZeLbAD.png)  

### 完全功能相依（缺一不可)  
1. 若**Primary Key是由多個屬性組成**,且某Non-Key Attribute依賴主鍵之全部而非部分時  
    > ![](https://i.imgur.com/jNnosf5.png)    
    > 例如 (學號,課號)->Z 如果把其中一個PK拿掉則使得相依性不存在   
2. **主鍵僅由一個屬性所組成**,則任一Non-Key Attributes必**完全相依**於主鍵  
    > 例如 (X)->Y 如果把X拿掉則相依性不存在  

### 部份功能相依(少你沒差)  
- 若**主鍵是由多個屬性**組成,而**某NON-鍵值屬性是依賴主鍵之部分pk屬性時**,則稱該非鍵值屬性部份相依於主鍵  
    > ![](https://i.imgur.com/waM6a58.png)  
    > (身份證,學號)->Z 如果把其中一個PK拿掉則相依性還存在   
### 遞移相依(熱力學第0定律) 
- 若存在一個**Non-Key Attributes T,使得 I->T 且 T->J**的功能相依性均成立,則稱屬性 I與屬性J之間具有遞移相依  
    > ![](https://i.imgur.com/p9H33O9.png)  
    > 身份證->郵遞區號, 郵遞區號->縣市路段, 身份證->縣市路段  

## Armstrong's Axioms
利用已知的功能相依性,透過一些性質,推導出  
- Implicit dependencies  
- Candidate or Primary Key  
- **Irreducible Functional Dependence**  

### Irreducible Functional Dependence
Properties of IFD符合  
1. 相依因素僅有一個屬性  
2. 沒有多餘的決定因素  
3. 沒有多餘的功能相依性  

反身性(Reflexivity)
```
若B是A的子集合,則A->B   
```
擴張性(Augmentation)
```
若A ->B,則AC ->BC  
```
**遞移性(Transitivity)**
```
若A ->B且B ->C,則A->C
```
自身決定性(Self-determination)
```
A->A
```
**分解性(Decomposition)**
```
若A →BC,則A->B且A->C
```
聯集性(Union)
```
若A->B且A->C,則A->BC
```
合成性(Composition)
```
若A->B且C->D,則AC->BD  
```
虛擬遞移性(Pseudo-transitivity)
```
若A->B且BC->D,則AC->D  
```

For Example  
![](https://i.imgur.com/nFpa0wn.png) 
![](https://i.imgur.com/QhgoLkN.png)   
![](https://i.imgur.com/aXMMRtp.png)  

## Complement Normalization 無失真壓縮  
![](https://i.imgur.com/Rj5dLWf.png)  
- Normalization定義  
    > 將一個關聯表所有資訊**分解後**數個新關聯表在**合併**仍可以得到**原來未分解時的資訊（Lossless Decomposition）**   

## Normalization類型
![](https://i.imgur.com/vvdwzEQ.png)  
- 低階Normalization
- 第一正規化型式 (1NF; First Normal Form)
- 第二正規化型式 (2NF; Second Normal Form)
- 第三正規化型式 (3NF; Third Normal Form)
- BC正規化型式 (BCNF; Boyce-Codd Normal Form)
- 高階Normalization **(Not Common)**
- 第四正規化型式 (4NF; Forth Normal Form)
- 第五正規化型式 (5NF; Fifth Normal Form)

表格經由正規化分割得愈細,愈可避免資料重覆及一些更新上的異常現象, 但在實務上,分割太細的表格,可能會產生一些問題,例如; 
> 在查詢資料時,可能會用到許多合併 (Join) 運算,此運算易造成查詢動作非常沒有效率可能產生有損分解  
- 所以在實作上,**為保持執行效率與避免異常間的平衡,通常正規化進行到3NF (最多至BCNF)**   

## Normalization過程
![](https://i.imgur.com/5ObNvsn.png)

## 第一正規化, First Normal Form
第一正規化的資料表中的所有Tuples(Each Row)的Values都是Atomic Value(不能在分割),其他**所有Attribute都相依於PK**(沒有PK這筆資料就沒意義了)

為符合第一正化我們需要將Table中的`多值屬性`分解成多個`Tuples`以及`複合屬性`拆成`簡單屬性` 
 
#### EX1  
分割日期成Atom Value  
將複合屬性`月日`分成`月,日`  
將多值屬性`3.18`分成`3,18`  
![](https://i.imgur.com/XciaBot.png)  

#### EX2
![](https://i.imgur.com/xEoCV2n.png)  
分割`產品`成Atom Value  
![](https://i.imgur.com/KXarqOT.png)  

## 第二正規化, Second Normal Form
由於執行First Normal Form後造成資料重複性違反正規化法則,則需要在繼續進行Second Normal Form  
第二正規化需要滿足
1. 符合1NF  
2. 每一個Table's Non-Key Attribute**皆完全功能相依**Primary Key  
    > 消除部分相依（Partial Dependency） 部分相依是指在複合主鍵的情況下，有些資料只跟部分主鍵有直接關係，而不是跟每個主鍵都有關聯  
    > e,g. `(學號,課號)->(成績)`則`(課程)->(成績)`或者`(學號)->(成績)`拿掉其中一個PK則相依性消失  


第二正規化(2NF)是指資料表中所有欄位(non-key attribute)的資料都必須和該資料表的PK（or Composite PK）有完全依賴關係，  
如果在複合主鍵的情況下，Non-Key attribute只和部分主鍵(partial PK)有關，則必須獨立出來成為一個資料表，或歸類到該主鍵的資料表。  

#### EX1
`(等級,所在城市)`完全功能相依相依於`(分公司代號)`  
`（銷量）`完全功能相依於`(產品代號,分公司代號)`  
![](https://i.imgur.com/SfIomor.png) 

#### EX2
![image](https://user-images.githubusercontent.com/68631186/125705932-04d1d6ca-de7b-4b5d-9de1-54f5a0b11b5f.png)  
`(姓名,性別）`完全功能相依於`(學號)`  
`(成績)`完全功能相依於`(學號,課號)`  
`(課程名稱,學分,授課老師,教師電話)`完全功能相依於`(課號)`  
![image](https://user-images.githubusercontent.com/68631186/125705965-e71457bf-d15a-422b-aaae-ea321b914261.png)  
![image](https://user-images.githubusercontent.com/68631186/125705982-e5c22d3f-5bb8-4886-a983-590016ba53dd.png)
![image](https://user-images.githubusercontent.com/68631186/125706028-89b4613d-0f36-495c-9fae-d24d0bf995a0.png) 


## 第三正規化, Third Normal Form 
1. 符合2NF
2. 每一個Non Key Attribute皆與PK彼此**無遞移相依**  

![image](https://user-images.githubusercontent.com/68631186/125706383-d7179030-e2d1-43c7-aba6-24db6669c34d.png)  

`(老師電話)` -> `（授課老師）` -> `(課號)`  
![image](https://user-images.githubusercontent.com/68631186/125705746-13bea0c7-beb2-46dd-836c-b1bdc811da02.png)  
![image](https://user-images.githubusercontent.com/68631186/125705782-cbbcf794-c140-417d-8ead-ad9f91707edc.png)  
![image](https://user-images.githubusercontent.com/68631186/125705821-131c15ca-022f-4215-bfe2-7b9489c56b8c.png)  

如果資料表的**PK是由多個欄位組成(composite key)**  
以及Composit pks的組合有重疊`(A,B)`跟`(B,D)`![](https://i.imgur.com/osbo8cm.png)  
則需要利用BNCF正規化在分割  

## Boyce-Codd Normal Form, **BNCF**
1. 符合3NF
2. Every functional dependency X->Y, X should be the super key of the table.y  
    - **不可以有部份相依存在**  

![](https://i.imgur.com/BYnMYfK.png)
- 不存在部份功能相依 (符合2NF)  
- Non-Key Attirbutes不存在遞移相依(符合3NF)  

問題  
- 新增(Insert)一名新顧問為吳小雄,專長為金融。但由於他目前沒有客戶,所以Client欄為NULL。然而,因為Client是主鍵的一部份,不允許為NULL,因此違反了個體完整性限制 (Entity Integrity Constraint)。  
- 刪除時會把過多資訊刪掉。因為呂小蓮撤回委託的業務,因此必須刪除(Delete)這筆記錄。然而,如果陳火扁只有呂小蓮這一個客戶,會使得陳火扁的資料一併被刪除,造成資料的遺失。  
- 修改時需要一起修改許多相關值組。如果黃小州顧問覺得自已的名字與八字不合,因此更改(Update)名字成黃小洲。然而在表格中他有多筆記錄存在,也必須一併更改,若不小心只更改了部份資料,便會造成資料不一致的情況。  

進行BNFC步驟
1. 選定non-key Attribute 
2. 決定FK
![](https://i.imgur.com/natkync.png)

### 是否需要BNFC
![](https://i.imgur.com/CZKxHCp.png)
首先確定關聯
- 符合2nf
- 符合3nf
則在推導是否有Implicit Relationship  
- 利用反身性,則: `{Client, Consultant} -> Consultant`
- 已知`Consultant -> Problem `
- 利用遞移性,則: `{Client, Consultant} -> Consultant` 與 `Consultant -> Problem` 可推得`{Client, Consultant} -> Problem`
此推導存在著部份相依故需要BNFC分割

## 第四正規化, Fourth Normal Form
1. 符合BCNF
2. 且無**多值相依存在**

## 第五正規化, Fifth Normal Form
1. 符合4NF
2. 無合併相依

## Denormalization必要性
過度分割表格反而導致資料存取效率下降,所以當發生過度正規化時則需要適時地反正規化(e.g. 3NF->2NF, 2NF->1NF)  

### Normalization與Denormalization優勢分析

#### 資料異動觀點
Normalization愈來愈多層時,有利於資料異動(Update,delete,insert ...)  
- 因為異動時只需要針對較小的表格  

#### 資料查詢
Normalization愈來愈多層時,愈不利於資料查詢    
- 因此需要Denormalization**合併**多個Tables  
 
#### 例如
正規化分割
> ![](https://i.imgur.com/zNuTH1t.png)  

反正規化合併
> ![](https://i.imgur.com/yDD2A4f.png)  


## ER Model 正規化過程

### ER Model
![](https://i.imgur.com/hXynYdc.png)  


### ER Model -> Relation
![](https://i.imgur.com/TIkUYjl.png)  

### Relation -> Normalization 
- 消除Data Redundancy
- 消除Anomalies
![](https://i.imgur.com/PTlAaLl.png)  


