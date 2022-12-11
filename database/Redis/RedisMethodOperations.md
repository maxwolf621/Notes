# Redis

- [Redis](#redis)
  - [SCAN (Cursor Pagination)](#scan-cursor-pagination)
  - [String](#string)
    - [Set/Get \& Length(key's val)](#setget--lengthkeys-val)
    - [Set/Get-Range](#setget-range)
    - [Append](#append)
    - [lock NX \& EX](#lock-nx--ex)
    - [INCR \& DECR](#incr--decr)
  - [List](#list)
    - [Pop \& Push](#pop--push)
    - [Block Pop \& Push](#block-pop--push)
    - [Length \& Insert | Set \& Get \& Remove \& Range \& Trim](#length--insert--set--get--remove--range--trim)
  - [Set](#set)
    - [Add \& Remove \& Get Randoms \& Pop](#add--remove--get-randoms--pop)
    - [Length \& Contains(Member)](#length--containsmember)
    - [Inter \& Differ \& Union](#inter--differ--union)
    - [SScan](#sscan)
  - [ZSort](#zsort)
    - [Insertion(Add) | Length(Card) | Member Score(Score) | INCREMENT(Incr)](#insertionadd--lengthcard--member-scorescore--incrementincr)
    - [INCR](#incr)
    - [(Score/Member) Count | LexCount](#scoremember-count--lexcount)
    - [(GET) Rank | RevRank](#get-rank--revrank)
    - [(GET) ZRange- ByScore | ByLex](#get-zrange--byscore--bylex)
    - [(Remove) Member,RemRangeByLex, RemRangeByRank,RemRangeByScore](#remove-memberremrangebylex-remrangebyrankremrangebyscore)
    - [(Inter \& Union)](#inter--union)
    - [ZScan](#zscan)
  - [Hash](#hash)
    - [INCR](#incr-1)
    - [Vals \& Keys](#vals--keys)
    - [Hset \& HsetNX](#hset--hsetnx)
    - [HGet \& HGetAll Operation](#hget--hgetall-operation)
    - [HMGet \& HMSet](#hmget--hmset)
    - [HScan](#hscan)

## SCAN (Cursor Pagination)

Iteration based on Cursor
```java 
  Scan Cursor [MATCH pattern] [LIMIT count]
> scan 13912   match key99*   count 1000
```

Keys VS Scan
- Same Complexity , But Scan using `Cursor` to avoid Blocking
- 提供 LIMIT 參數，控制每次返回值的最大資料數，返回的结果可多可少
- Server不需要保存Cursor，Cursor的唯一狀態就是 SCAN 返回给客户端的Integer Type;
- **返回的結果可能會有Duplicates，Client要自己處理**
- Iteration的過程中如果有資料修改，Modified的資料能不能Iteration不确定的
- **單次返回的結果是NILL的不表示着Iteration結束，而要看返回的Cursor是否為零**

>>> SScan、 HScan 和 ZScan 第一參數一定要為KEY，而 SCAN 不必提供任何KEY，SCAN的Iteration該Redis Server的所有KEY。

## String 

[Redis' String](https://www.runoob.com/redis/redis-strings.html)

### Set/Get & Length(key's val)

```java
StrLen key 

Set key value
Get key

GetSet key NewValue // get old value set new value
> GetSet phone Iphone
(nil)
> GetSet phone Samsung
"Iphone"

MGet key1 [key2..]
MSet key value [key value ...]

SetBit key offset value
GetBit key offset
```

### Set/Get-Range

```java
SetRange key offset value
> SET key1 "Hello World"
OK
> SetRange key1 6 "Redis"
(integer) 11
> GET key1
"Hello Redis"

GetRange key start_Inclusive end_Inclusive
```

### Append

```java
> EXISTS description         
(integer) 0

> APPEND description "Item" // same as set description Item 
4              

> APPEND description " GoPro"
10

> get Description
"Item GoPro"
```


### lock NX & EX

```java
SetEX key seconds value
PSetEX key milliseconds value

SetNX key value 
MSetNX key value [key value]
```

### INCR & DECR
```java

Incr key
IncrBy key increment
IncrByFloat key increment 。

Decr key
DecrBy key decrement
```
## List

### Pop & Push
```java
RPopLPush source destination
RPush key value1 [value2]
RPushX key value

LPop key 
LPush key value1 [value2]
LPushX key value

RPpo key
```
### Block Pop & Push

```java
BLpop key1 key ... keyN timeout
BRpopLPush source destination timeout
```

### Length & Insert | Set & Get & Remove & Range & Trim

```java
LLen key

LRange key start stop

LRem key count value

LTrim key start stop // keep index[start]-index[stop] element

LIndex key index // key[index]

LSet key index value

// key[BEFORE] --- key[pivot] --- key[AFTER]
LInsert key BEFORE|AFTER pivot value 
```

## Set

### Add & Remove & Get Randoms & Pop

```java
SAdd key value value2 ...
> SAdd mySet post1 post2 post3
(integer) 3

SPop key
> SPop mySet
"post1"

SRem key value [valueN ...]

SRandMember KEY COUNT
> SRandMember mySet 10
1) "post3"
2) "post1"
3) "post2"
```

### Length & Contains(Member)
```java
SCard key 
> SCard lan
3

SisMember key member
> SisMember mySet post3
(integer) 1
```

### Inter & Differ & Union

```java



// k1.retainALL(k2)
Sinter k1 k2
> Sadd mySet2 post2 post3 post5
(integer) 3
> Sinter mySet mySet2
1) "post3"
2) "post2"

SInterStore result k1 k2
> SinterStore interSet mySet mySet2
2

SDiff Key1 Key2
SDiffStore Result Key1 Key2

SUnion Key1 Key2
> SUnion mySet mySet2
1) "post3"
2) "post5"
3) "post2"

SUnionStore UnionSet Key1 Key2
```

### SScan

```java
> SAdd sites Google Reddit Amazon Ruby
(integer) 3
> SScan sites 0 match R*
1) "0"
2) 1) "Ruby"
2) "Reddit"
```

## ZSort

### Insertion(Add) | Length(Card) | Member Score(Score) | INCREMENT(Incr)

```java
// Update or insert new member with score
ZAdd key score1 member1 [score2 member2]
> ZAdd hotPost 10 post1 12 post2 13 post3 14 post5
(integer) 4

// Return score of member in the key 
ZScore key member
> ZScore hotPost post5
14.0

ZCard key // key.stream().count()
> ZCard hotPost
4
```

### INCR

```java
// Set.get(Member) += incrementNumber
ZIncrBy key incrementNumber member
```

### (Score/Member) Count | LexCount

```java
ZCount KEY lowerBound_OfScore upperBound_OfScore
> ZCount hotPost 0 12
2  // post1 : 10 , post2 : 12

// Count members By Lexicology 
ZLexCount KEY lowerBound_OfLexiCology upperBound_OfLexiCology
> ZLexCount hotPost [post1 [post3
(integer) 3
```
### (GET) Rank | RevRank

```java
// rank of member in key
ZRank KEY member
> ZRank hotPost post1
0

ZRevRank KEY member  // find rank of member
> ZRevRank hotPost post5
0

ZRevRange KEY lowerBound upperBound
```
### (GET) ZRange- ByScore | ByLex
```java
ZRange key start end
> ZRange hotPost 0 -1 // All Range
1) "post1"
2) "post2"
3) "post3"
4) "post5"

ZRangeByLex KEY min max // Member
> ZRangeByLex hotPost - [post5
1) "post1"
2) "post2"
3) "post3"
4) "post5"
> ZRangeByLex hotPost - (post5
1) "post1"
2) "post2"
3) "post3"
> ZRangeByLex hotPost [asdf (post5
1) "post1"
2) "post2"
3) "post3"

ZRangeByScore KEY // Score
> ZRangeByScore hotPost 10 15
1) "post1"
2) "post2"
3) "post3"
4) "post5"

ZRevRangeByScore KEY upperBound lowerBound
> ZRevRangeByScore hotPost 12 0
1) "post2"
2) "post1"
```

### (Remove) Member,RemRangeByLex, RemRangeByRank,RemRangeByScore

```java
// remove member or multiple members in key
ZRem key member [member ...] 

ZRemRangeByLex KEY lowerBound upperBound
// remove members by range of rank
ZRemRangeByRank KEY lowerBound_OfRank upperBound_OfRank
// remove member by range of score
ZRemRangeByScore KEY lowerBound_OfScore upperBound_OfScore

20	ZScan KEY cursor [MATCH pattern] [COUNT count]
迭代有序集合中的元素（包括元素成员和元素分值）
```

### (Inter & Union)

```java
ZInterStore store numkeys key [key ...]
```

### ZScan 

```java
> ZScan hotPost 0 match p*3
1) "0"
2) 1) "post3"
2) 13.0
```

## Hash

H : Hash
M : Multiple [FIELDS]

```java
// delete multiple field
HDel key field1 [field2 ... filedN] 
// hashMap.contains((key,field))
HExists key field 
// hashMap.keySet().stream().count()
HLen key 
```

### INCR

```java
//hashMap.get(key) += integer_increment 
HIncrBy key field increment 

// hashMap.get(key) += float_increment
HIncrByFloat key field increment 
```

### Vals & Keys

```java
// hashMap.keySet()
HKeys key 
// hashMap.values()
HVals key 
```
### Hset & HsetNX

```java
/* Insertion and Fetch  **/
Hset key field value   // put( (key, (field,value))
Hset key field value // putIfAbsent(key, (field,value)))
```


### HGet & HGetAll Operation

```java
// get specific field-value pair in key
HGet key field 
// get All field-value pairs in specific key
HGetAll key 

> HMset postVote user1 1 user2 -1 user3 1 user4 1
OK

> HGetAll postVote
1) "user1"
2) "1"
3) "user2"
4) "-1"
5) "user3"
6) "1"
7) "user4"
8) "1"

> HGet postVote user2
"-1"
```

### HMGet & HMSet
```java
//HashMap<key, HashMap<field,value>>
HMSet key field1 value1 [field2 value2] ... [fieldN valueN]
> HMset postVote user1 1 user2 -1 user3 1 user4 1
OK

// get(key, List<Map<field,value>>)
HMGet key field1 [field2 .. fieldN] 
> HMget postVote user1 user2 user3
1) "1"
2) "-1"
3) "1"
```

### HScan

```java
> HMSet postVote:post1 user1 1 user2 -1 user3 0 user4 -1 user5 1
OK

> HScan postVote:post1 0 match user1
1) "0"
2) 1) "user1"
2) "1"
```