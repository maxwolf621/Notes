# Map Interface 

- [Map Interface](#map-interface)
  - [Reference](#reference)
  - [Hsshtable LoadFactor && Threshold](#hsshtable-loadfactor--threshold)
  - [ConcurrentHashMap](#concurrenthashmap)
  - [LinkedHashMap](#linkedhashmap)
  - [Difference btw HashTable and HashMap](#difference-btw-hashtable-and-hashmap)
    - [`synchronized`](#synchronized)
    - [null-key or null-value](#null-key-or-null-value)
    - [HashMap Iteration is fail-fast](#hashmap-iteration-is-fail-fast)
  - [HashMap](#hashmap)
  - [`loadFactor * initial capacity = Threshold`](#loadfactor--initial-capacity--threshold)
    - [Structure of HashMap](#structure-of-hashmap)
  - [How HashMap Functions](#how-hashmap-functions)

## Reference

- [GeekForGeeks HashMap](https://www.geeksforgeeks.org/java-util-hashmap-in-java-with-examples/)
- [Difference btw HashTable and HashMap](https://www.geeksforgeeks.org/differences-between-hashmap-and-hashtable-in-java/)
- [HashMap](https://techmastertutorial.in/java-collection-internal-hashmap.html)  
- [HashMap SourceCode](https://www.gushiciku.cn/pl/gVdy/zh-hk)  
- [Calculate Hash Code and index of bucket](https://www.geeksforgeeks.org/internal-working-of-hashmap-java/)  

## Hsshtable LoadFactor && Threshold

- [Source Code](https://www.twblogs.net/a/5b7c023d2b71770a43d8decb)

The value of the threshold = `(int)(capacity * loadFactor)`

If element counts in Hashtable is exceed threshold, it calls `reHash()` to expand the size of the table
```java
public Hashtable(int initialCapacity, float loadFactor) {  
        // check Table Capacity
        if (initialCapacity < 0)  
            throw new IllegalArgumentException("Illegal Capacity: "+  
                                               initialCapacity);  
        // Check loadFactor
        if (loadFactor <= 0 || Float.isNaN(loadFactor))  
            throw new IllegalArgumentException("Illegal Load: "+loadFactor);  
  
        if (initialCapacity==0)  
            initialCapacity = 1;  
          
        this.loadFactor = loadFactor;  
          
        //Initialize Hash table Capacity 
        table = new Entry[initialCapacity];  
        //Calculate Threshold
        threshold = (int)Math.min(initialCapacity * loadFactor, MAX_ARRAY_SIZE + 1);  
        
        //Initialize HashSeed   
        initHashSeedAsNeeded(initialCapacity);  
      }  
```

```java
public synchronized V put(K key, V value) {  
        
        // 確保value不爲null  
        if (value == null) {  
            throw new NullPointerException();  
        }  
  
        /* 
         * 確保key在table[]是不重複的 
         * 處理過程： 
         * 1、Calculate key的hash值，確認在table[]中的索引位置 
         * 2、Iterate index索引位置，如果該位置處的鏈表中存在一個一樣的key，則替換其value，返回舊值 
         */  
        Entry tab[] = table;  

         //計算key的hash值 
        int hash = hash(key);    

        //確認該key的索引位置  
        int index = (hash & 0x7FFFFFFF) % tab.length;     
        
        // Iteration ，尋找該key，替換  
        for (Entry<K,V> e = tab[index] ; e != null ; e = e.next) {  
            if ((e.hash == hash) && 
                 e.key.equals(key)) {  
                
                V old = e.value;  
                e.value = value;  
                return old;  
            }  
        }  
  
        modCount++;  

        if (count >= threshold) {  
        //如果容器中的元素數量已經達到閥值，則進行擴容操作  
            rehash();  
            tab = table;  
            hash = hash(key);  
            index = (hash & 0x7FFFFFFF) % tab.length;  
        }  
  
        // 在索引位置處插入一個新的節點  
        Entry<K,V> e = tab[index];  
        tab[index] = new Entry<>(hash, key, value, e);  
        
        //容器中元素+1  
        count++;  
        return null;  
    }  
```

```java
protected void rehash() {  
        int oldCapacity = table.length;  
        //元素  
        Entry<K,V>[] oldMap = table;  
  
        //新容量=舊容量 * 2 + 1  
        int newCapacity = (oldCapacity << 1) + 1;  
        if (newCapacity - MAX_ARRAY_SIZE > 0) {  
            if (oldCapacity == MAX_ARRAY_SIZE)  
                return;  
            newCapacity = MAX_ARRAY_SIZE;  
        }  
          
        //新建一個size = newCapacity 的HashTable  
        Entry<K,V>[] newMap = new Entry[];  
  
        modCount++;  
        //重新計算閥值  
        threshold = (int)Math.min(newCapacity * loadFactor, MAX_ARRAY_SIZE + 1);  
        //重新計算hashSeed  
        boolean rehash = initHashSeedAsNeeded(newCapacity);  
  
        table = newMap;  
        //將原來的元素拷貝到新的HashTable中  
        for (int i = oldCapacity ; i-- > 0 ;) {  
            for (Entry<K,V> old = oldMap[i] ; old != null ; ) {  
                Entry<K,V> e = old;  
                old = old.next;  
  
                if (rehash) {  
                    e.hash = hash(e.key);  
                }  
                int index = (e.hash & 0x7FFFFFFF) % newCapacity;  
                e.next = newMap[index];  
                newMap[index] = e;  
            }  
        }  
    }  
```


## ConcurrentHashMap

- [ConcurrentHashMap](https://github.com/CyC2018/CS-Notes/blob/master/notes/Java%20%E5%AE%B9%E5%99%A8.md#concurrenthashmap)

```java
static final class HashEntry<K,V> {
    final int hash;
    final K key;
    volatile V value;
    volatile HashEntry<K,V> next;
}
```

## LinkedHashMap
```java 
public class LinkedHashMap<K,V> extends HashMap<K,V> implements Map<K,V>
```

## Difference btw HashTable and HashMap


### `synchronized`

Hashtable is `synchronized`    
```java
public synchronized V put(K key, V value)
{ 
    // .. 
}
```

### null-key or null-value

HashMap allows Entry of `null` key  
```java
if (key == null)  
            return putForNullKey(value);  
```

```java
map.put("D", null);

// null
Object value = map.get("D");
```

### HashMap Iteration is fail-fast  

- Fail-fast means when you try to modify the content when you are iterating thru it, it will fail and throw `ConcurrentModificationException`.
- HashMap is unordered but will always have same result for same set of keys.
```java
Set keys = hashMap.keySet();
for (Object key : keys) {
    hashMap.put(someObject, someValue); 
    "-> the ConcurrentModificationException is thrown"
} 

// For HashTable enumeration:
Enumeration keys = hashTable.keys();
while (keys.hasMoreElements()) {
        hashTable.put(someKey, someValue);  // is ok
  }
```

## HashMap

- **Not thread-safe**
- **HashMap's key can be assigned `null` only at the index 0 (bucket)**   
    ```java
    private V putForNullKey(V value) {
    for (Entry<K,V> e = table[0]; e != null; e = e.next) {
        if (e.key == null) {
            V oldValue = e.value;
            e.value = value;
            e.recordAccess(this);
            return oldValue;
        }
    }
    modCount++;
    addEntry(0, null, value, 0);
    return null;
    }
    ```
- **Each new entry will be inserted at the head of bucket not at the tail.**  
`bucket_#num -> new Entry -> old Entry -> ...`
- A bucket#number : `int number = indexFor(hash, table.length);` 

 
 HashMap's internal Hash Table is created only after the first use.  
Initially the hash table is `null`.   
Hash Table is initialized only when the first insertion call is made for the HashMap instance.    
```java
transient Node<K,V>[] table;
```

- **Since JDK 1.8，if number of entries in a `bucket > 8` then convert the linked-lists to black red tree(instead of linked list).**
The advantage of self-balancing bst is, we get the worst case (when every key maps to the same slot) search time is `O(Log n)` 

---

```java
// Array INDEX 
Bucket#hashCode = fn(Key)` 
```
![image](https://user-images.githubusercontent.com/68631186/125174975-b71b6980-e1fb-11eb-8803-54ca5fe54bb9.png)
- `bucket#hashCode` is calculated for the keys.  
- Using the hashcode, index is identified in the bucket array.  
- In the array, **each array index is mapped to the hashcode derived from the key**.   
Each array index ( or we can say each bucket ) contains a reference to a linked list in which we store the key-value pair entry.

---

![image](https://user-images.githubusercontent.com/68631186/125176665-dfa96080-e207-11eb-8b2e-15088e3b721e.png)  
- For a key-value pair, first identify the hashode for the key using the `hashcode` method.  (`index = hashCode(key) & (n-1).`)
    > Once we have the hash code, index (of the array) is calculated to identify the bucket in which the key-value pair will be stored.   
    >>  Once we have the bucket index (of the array) , check for the entry (linked) list.
- Check For entry list existing, IF
    1. we do not have the already existing (linked)list corresponding to that (bucket) index then we create a (linked)list and add the key-value pair entry to this (linked)list. 
    2. there is already a (linked)list of the key-value entries then we go through(search) the list check for key object 
- Check For Key , IF
    1. the key already exist then we override(update) the value.    
    2. **the key is not there then a new entry(key-value pair) is created**
        > New entry is then added to head of the (linked)list

## `loadFactor * initial capacity = Threshold`

1. **Initial Capacity**  
It is the capacity of HashMap at the time of its creation (It is the number of buckets a HashMap can hold when the HashMap is instantiated). In java, it is `2^4=16` (key-value pairs) initially

2. **Load Factor**  
is a measure of how full the hash table is allowed to get before its capacity is automatically increased. In java, it is `0.75f` by default, meaning the rehashing takes place after filling 75% of the capacity.

3. **Threshold**  
is the product of Load Factor and Initial Capacity. In java, by default, it is `(16 * 0.75 = 12)`. That is, Rehashing takes place after inserting `12` key-value pairs into the HashMap.

4. **Rehashing**  
is the process of doubling the capacity of the HashMap after it reaches its Threshold.  
In java, HashMap continues to rehash(by default) in the following sequence – `2^4`, `2^5`, `2^6`, `2^7`, …. so on. 
 
If the initial capacity is kept higher then rehashing will never be done. **But by keeping it higher increases the time complexity of iteration.**  
The expected number of values should be taken into account to set the initial capacity.  

**The most generally preferred load factor value is `0.75` which provides a good deal between time and space costs.**
- The load factor’s value varies between `0` and `1`. 

```java
/**
  * Using the initial capacity and load factor, threshold is calculated. 
  */
public HashMap(int initialCapacity, float loadFactor) {  
       if (initialCapacity < 0)  
           throw new IllegalArgumentException("Illegal initial capacity: " +  initialCapacity);  
       
       if (initialCapacity > MAXIMUM_CAPACITY)  
           initialCapacity = MAXIMUM_CAPACITY;  
       
       if (loadFactor <= 0 || Float.isNaN(loadFactor))  
           throw new IllegalArgumentException("Illegal load factor: " +  loadFactor);  
       
       this.loadFactor = loadFactor;  
       
       // Expand Size of the Hash Map
       this.threshold = tableSizeFor(initialCapacity);  
   }  
```

### Structure of HashMap

```java
static class Entry<K,V> implements Map.Entry<K,V> {

    final K key;
    V value;

    Entry<K,V> next;
    
    int hash;

    Entry(int h, K k, V v, Entry<K,V> n) {
        value = v;
        next = n;
        key = k;
        hash = h;
    }

    // other methods  getKey() , setValue, getValue

    public final boolean equals(Object o) {
        if (!(o instanceof Map.Entry)) return false;

        Map.Entry e = (Map.Entry) o;
        
        Object k1 = getKey();
        Object k2 = e.getKey();

        if (k1 == k2 || (k1 != null && k1.equals(k2))) {
            
            // compare values from entry
            Object v1 = getValue();
            Object v2 = e.getValue();

            if (v1 == v2 || (v1 != null && v1.equals(v2)))
                return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(getKey());
    }
```

## How HashMap Functions

`GenerateHashCode(key) => hashCode => bucket#num : hashCode mode table_size`

Get the `bucket#num`
```java
int hash = hash(key);
int i = indexFor(hash, table.length);

static int indexFor(int h, int length) {
    return h & (length-1);
}
```

```java
// Assume Size of HashMap (size of buckets) is 16
HashMap<String, String> map = new HashMap<>();
map.put("K1", "V1");  
// K1's hashcode is 115 => bucket#num : 115%16 = 3

map.put("K2", "V2"); 
//  K2 is 118 => 118%16 = 6

map.put("K3", "V3"); 
//  K3 is 118 => 118%16 = 6 , collision => move forward or backward of bucket
```