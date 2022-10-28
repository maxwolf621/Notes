# RedisTemplate

- [RedisTemplate](#redistemplate)
  - [BoundXXXXOps](#boundxxxxops)
  - [opsForXXXX](#opsforxxxx)
    - [opsForHash](#opsforhash)
    - [opsForSet](#opsforset)
    - [opsForList](#opsforlist)
  - [Create `RedisUtil` Class](#create-redisutil-class)
    - [Override `opsForValue`](#override-opsforvalue)
    - [Override `opsForHash`](#override-opsforhash)
    - [Override `opsForSet()`](#override-opsforset)
    - [Override `opsForList()`](#override-opsforlist)


`value` :(key-value pairs)
`hash`  : (key -> (hashkey-value pairs))
`list`  : (key -> values)
`Set`  un-sorted set : (key -> values)
`ZSet` sorted set : (key -> values)

[Spring.io redisTemplate specification](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html)
[RedisTemplate操作Redis](https://blog.csdn.net/lydms/article/details/105224210)
[SpringBoot redis 基礎篇](https://zhuanlan.zhihu.com/p/139528556)   
[講六：SpringBoot整合Redis詳解](https://zhuanlan.zhihu.com/p/336033293)    
[(METHODS) How to use Redis-Template in Java Spring Boot?](https://medium.com/@hulunhao/how-to-use-redis-template-in-java-spring-boot-647a7eb8f8cc)   
**[springboot-redis-demo](https://github.com/MiracleTanC/springboot-redis-demo)**   

## BoundXXXXOps 

BoundXXXXops generates `key` instance

```java
boundGeoOps(K key)    
boundHashOps(K key)       
boundListOps(K key)      
boundSetOps(K key)       
boundStreamOps(K key)   
boundValueOps(K key)     
boundZSetOps(K key)     
``` 

```java
@Autowired
RedisTemplate<String,Object> redisTemplate;
​
// Instance of key 
BoundValueOperations keyInstance = redisTemplate.boundValueOps(String key);

key.set(String value); 

ValueOperations ops = redisTemplate.opsForValue();
ops.set(String value); 

redisTemplate.boundValueOps(String key).set(String value);
redisTemplate.boundValueOps(String key).set(String value , time, TimeUnit.SECONDS);
```

## opsForXXXX

```java
redisTemplate.opsForValue(); 
redisTemplate.opsForHash();  
redisTemplate.opsForList();  
redisTemplate.opsForSet();   
redisTemplate.opsForZSet();  
```

```java
Boolean	hasKey(key);
Boolean expire(K key,long timeout,TimeUnit unit)
Boolean delete(K key)
Long getExpire(K key)
```

```java
String key = "example";
Boolean exist = redisTemplate.hasKey(key);
long time = 60;
redisTemplate.expire(key, time, TimeUnit.SECONDS); 
BoundValueOperations boundValueOps = redisTemplate.boundValueOps(key)
                                                  .expire(1, TimeUnit.SECONDS);

// get expire time
Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);

// Delete value via key
redisTemplate.delete(key);
```


### opsForHash
- [opsForHash](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/HashOperations.html)

```java
size(H key)

bucket#num | entries(HashKey, value)
    key1   +-- hashKey1 : value1
           +-- hashKey2 : value2
           +-- hashKey3 : value3
    key2   +--- ....  : .....

String key = "key1"
String hashKey = "hashKey1";      
String value = "value1";    
RedisTemplate#opsForHash().put(key, hashKey, value);

// Entries to put in the buckets
Map<String, String> entries = new Map<String, String>();
entries.put("map_key_1", "map_value_1");
entries.put("map_key_2", "map_value_2");
redisTemplate.putAll(key, entries);

putIfAbsent(H key, HK hashKey, HV value)

// get entries
String key = "bucket_name"
Map<String, String> entries = redisTemplate.opsForHash().entries(key);

// get values
List<String> values = redisTemplate.opsForHash.values(key);

// get specific value
String key = "key_forCache";
String hashKey = "map_key_1";
Object value = redisTemplate.opsForHash().get(key, hashKey); 

// delete(H key, Object... hashKeys)
redisTemplate.opsForHash().delete(key, hashKey, hashkey2);

// check if entry exists via bucket#key 
Boolean exist = redisTemplate.opsForHash().hasKey(key, hashKey);

// get hashKeySet in bucket#key
Set<Object> hashKeySet = redisTemplate.opsForHash().keys(key);  

multiGet(H key, Collection<HK> hashKeys)

increment(H key, HK hashKey, long delta)
increment(H key, HK hashKey, double delta)
```

### opsForSet

```java
Long add(K key, V... values)
String key = "cacheName";
String value1 = "2";
String value2 = "1";
redisTemplate.opsForSet().add(key, value1, value2);


// Check if set#key contains value.
Boolean	isMember(K key, Object o)
// is value exists in set#key
String value = "2";
Boolean member = redisTemplate.opsForSet().isMember(key, value);

Map<Object,Boolean>	isMember(K key, Object... objects)


// Get all elements of set at key.
Set<V> members(K key)
// -- Get set of values [1,2]
Set<Object> members = redisTemplate.opsForSet().members(key);

// Move value from key to destKey
Boolean	move(K key, V value, K destKey)

// Remove and return a random member
// from set at key.
V pop(K key)

// Remove and 
// return count random members
// from set#key.
List<V>	pop(K key, long count)

// Get random element from set at key.
V randomMember(K key)
// Get count random elements from set at key.
List<V> randomMembers(K key, long count)

// Remove given values from set#key 
// and return the number of removed elements.
Long remove(K key, Object... values)

// Use a Cursor to iterate over entries set at key.
Cursor<V> scan(K key, ScanOptions options)

Long size(K key)
```

### opsForList

- [opsForList](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ListOperations.html)

```java
/**
  * {@code range(key, start, end)} range of array list
  * {@code size(key)} : array list len
  */
if (redisTemplate.hasKey("test")) {

    // assume key test has [4,3,2,1]
    System.out.println(redisTemplate.opsForList().range("test", 0, 0)); // [4]
    System.out.println(redisTemplate.opsForList().range("test", 0, 1)); // [4, 3]
    System.out.println(redisTemplate.opsForList().range("test", 0, 2)); // [4, 3, 2]
    System.out.println(redisTemplate.opsForList().range("test", 0, 3)); // [4, 3, 2, 1]
    System.out.println(redisTemplate.opsForList().range("test", 0, 4)); // [4, 3, 2, 1]
    System.out.println(redisTemplate.opsForList().range("test", 0, 5)); // [4, 3, 2, 1]
    
    System.out.println(redisTemplate.opsForList().range("test", 0, -1)); // [4, 3, 2, 1] 
    System.out.println(redisTemplate.opsForList().size("test")); // 4

}

/**
  * {@code delete(key)}
  */
List<String> test = new ArrayList<>();
test.add("1");
test.add("2");
test.add("3");
test.add("4");

redisTemplate.opsForList().rightPushAll("test", test);
System.out.println(redisTemplate.opsForList().range("test", 0, -1)); // [1, 2, 3, 4]

redisTemplate.delete("test");
System.out.println(redisTemplate.opsForList().range("test", 0, -1)); // []


/**
  * leftPush or rightPush       
  *                             |
  *  leftPush the element <- container -> rightPush the element
  */
redisTemplate.opsForList().leftPushAll("test", test);
System.out.println(redisTemplate.opsForList().range("test", 0, -1)); // [4, 3, 2, 1]

for (int i = 0; i < 4; i++) {
    Integer value = i + 1;
    redisTemplate.opsForList().leftPush("test", value.toString());
    System.out.println(redisTemplate.opsForList().range("test", 0, -1));
}

// [1]
// [2, 1]
// [3, 2, 1]
// [4, 3, 2, 1]

/**
  * {@code leftPushIfPresent(key, value)}
  * The same operation as leftPush, 
  *     the only difference is that the value of the key is 
  *     updated if and only if the key exists. 
  * If the key does not exist, 
  *     no action will be taken on the data.redisTemplate.delete("test");
  */
redisTemplate.opsForList().leftPushIfPresent("test", "1");
redisTemplate.opsForList().leftPushIfPresent("test", "2");
System.out.println(redisTemplate.opsForList().range("test", 0, -1)); // []


/**
  * leftPop
  */
redisTemplate.opsForList().leftPop("test"); // [2, 3, 4] pop 1
redisTemplate.opsForList().leftPop("test"); // [3, 4] pop 2
redisTemplate.opsForList().leftPop("test"); // [4] pop 3
redisTemplate.opsForList().leftPop("test"); // [] pop 4
redisTemplate.opsForList().leftPop("test"); // [] nothing to pop

/**
  * {@code trim(key, start, end)}
  */
redisTemplate.opsForList().rightPushAll("test", test); // [1, 2, 3, 4]
redisTemplate.opsForList().trim("test", 0, 2); // [1, 2, 3]

/**
  * {@code remove}
  * Used to remove the element specified in the key. 
  * Accepts 3 parameters, which are the hash key name, count, and the value to be removed. 
  * There are three values that can be passed to the count, which are -1, 0, 1.
  * -1 means starting from the right side of the storage container, 
  *     deleting a single value that matches the value to be removed; 
  * 0 means deleting all data matching value; 
  * 1 means starting from the left side of the storage container , 
  *     delete a single data that matches the value you want to remove.
  */
List<String> test = new ArrayList<>();
test.add("1");
test.add("2");
test.add("3");
test.add("4");
test.add("4");
test.add("3");
test.add("2");
test.add("1");

// -1 : delete single value starting from right side 
redisTemplate.opsForList().remove("test", -1, "1"); // [1, 2, 3, 4, 4, 3, 2]
// 1 : delete single value starting from left side
redisTemplate.opsForList().remove("test", 1, "1"); // [2, 3, 4, 4, 3, 2]
// 0 : delete value which is 4 
redisTemplate.opsForList().remove("test", 0, "4"); // [2, 3, 3, 2]

/**
  * {@code rightPopAndLeftPush}
  */
List<String> test = new ArrayList<>();
test.add("1");
test.add("2");
test.add("3");
test.add("4");

List<String> test2 = new ArrayList<>();
test2.add("1");
test2.add("2");
test2.add("3");

redisTemplate.opsForList().rightPushAll("test", test); // [1, 2, 3, 4]
redisTemplate.opsForList().rightPushAll("test2", test2); // [1, 2, 3]

redisTemplate.opsForList().rightPopAndLeftPush("test", "test2");

System.out.println(redisTemplate.opsForList().range("test", 0, -1)); // [1, 2, 3]
System.out.println(redisTemplate.opsForList().range("test2", 0, -1)); // [4, 1, 2, 3]
```

## Create `RedisUtil` Class

```java
/**
 * Custom RedisTemplate Methods 
 */
@Component
public final class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Cache Expired Time
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * Get expired time corresponding the key
     * @param key can't be null
     * @return seconds (0 means live forever)
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    /**
     * Check key if exists
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Delete the caches
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
}
```


### Override `opsForValue`
```java
/**
 * Get cache via {@code key}
 */
public Object get(String key) {
    return key == null ? null : redisTemplate.opsForValue().get(key);
}

/**
 * save the data in the cache
 * @return true if successes
 */
public boolean set(String key, Object value) {
    try {
        redisTemplate.opsForValue().set(key, value);
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
/**
 * Set cache with TTL
 * @param time  seconds if 0 or <0 then this cache live forever
 * @return true | false 
 */
public boolean set(String key, Object value, long time) {
    try {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
/**
 * Increment the certain cache correspond to key
 * @param delta bigger than 0
 */
public long incr(String key, long delta) {
    if (delta < 0) {
        throw new RuntimeException("delta can not be less then or equal to 0");
    }
    return redisTemplate.opsForValue().increment(key, delta);
}
/**
 * decrement
 * @param delta
 */
public long decr(String key, long delta) {
    if (delta < 0) {
        throw new RuntimeException("delta must be bigger than 0");
    }
    return redisTemplate.opsForValue().increment(key, -delta);
}
```

### Override `opsForHash`

```java
/**
 * Override opsForHash().get(key, item)
 */
public Object hget(String key, String item) {
    return redisTemplate.opsForHash().get(key, item);
}
/**
 * {@code entries(key)}
 * Get Entries for certain bucket
 */
public Map<Object, Object> hmget(String key) {
    return redisTemplate.opsForHash().entries(key);
}
/**
 * (hset) Insert new entries
 */
public boolean hmset(String key, Map<String, Object> map) {
    try {
        redisTemplate.opsForHash().putAll(key, map);
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
/**
 * HashSet with TTL
 */
public boolean hmset(String key, Map<String, Object> map, long time) {
    try {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
/**
 * hset : key -> [item , value] -> [item , value] -> ....
 */
public boolean hset(String key, String item, Object value) {
    try {
        redisTemplate.opsForHash().put(key, item, value);
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
/**
 * hset with TTL
 */
public boolean hset(String key, String item, Object value, long time) {
    try {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
/**
 * delete entries via items corresponding to specific key
 *
 * @param key  Can Not Be NULL
 * @param item Can Not Be NULL
 */
public void hdel(String key, Object... item) {
    redisTemplate.opsForHash().delete(key, item);
}
/**
 * Search specific item via key
 */
public boolean hHasKey(String key, String item) {
    return redisTemplate.opsForHash().hasKey(key, item);
}
/**
 * increment 
 * @param by > 0
 */
public double hincr(String key, String item, double by) {
    return redisTemplate.opsForHash().increment(key, item, by);
}
/**
 * decrement
 * @param by > 0
 */
public double hdecr(String key, String item, double by) {
    return redisTemplate.opsForHash().increment(key, item, -by);
}
```

### Override `opsForSet()`
```java
/**
 * {@code members(key)}
 * get all value in Set via key
 */
public Set<Object> sGet(String key) {
    try {
        return redisTemplate.opsForSet().members(key);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
/**
 * {@code isMember(key, value)}
 * search the value in Set via key
 */
public boolean sHasKey(String key, Object value) {
    try {
        return redisTemplate.opsForSet().isMember(key, value);
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
/**
 * {@code add(key, value)}
 * Insert new values
 */
public long sSet(String key, Object... values) {
    try {
        return redisTemplate.opsForSet().add(key, values);
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}
/**
 * {@code add(key,  ...values)}
 * Insert new values with TTL
 */
public long sSetAndTime(String key, long time, Object... values) {
    try {
        Long count = redisTemplate.opsForSet().add(key, values);
        if (time > 0)
            expire(key, time);
        return count;
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}
/**
 *{@code size(key)}
 * get Set Size via Key
 */
public long sGetSetSize(String key) {
    try {
        return redisTemplate.opsForSet().size(key);
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}
/**
 * {@code remove(key, value)}
 * Delete values in Set via Key
 */
public long setRemove(String key, Object... values) {
    try {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count;
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}
```

### Override `opsForList()`

```java
    /**
     * {@code range(key, start, end)}
     * get List Contents
     * @param start 
     * @param end   
     * <p> start : 0  and end : -1 means fetch all contents of this list </p>
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * get List.size via Key
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * {@code index(key, index)} Get content in the list via key and index of this list
     * @param index -1 : last one , -2 second to last , ...
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * {@code rightPush} add new content in list 
     *  leftPush <- value -> rightPush
     */
    public boolean rightPush(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * add new content in list with TTL w
     */
    public boolean leftPush(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * add List of values in the list
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * add value in the list with TTL
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * edit content via index of list and key
     */

    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * delete n values in this list
     * @param count number of deleted items
     * @param value 
     */

    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
```
