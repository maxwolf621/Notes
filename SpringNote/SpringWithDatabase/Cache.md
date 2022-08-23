# Spring Cache 

[TOC]

## Reference

[Getting Started With Spring Data Redis](https://frontbackend.com/spring-boot/getting-started-with-spring-data-redis)  
[Spring Boot Cache with Redis](https://www.baeldung.com/spring-boot-redis-cache)  
[Spring boot caching tutorial with example](https://howtodoinjava.com/spring-boot2/spring-boot-cache-example/)  
[Spring Boot Cache使用與整合](https://www.cnblogs.com/ejiyuan/p/11014765.html)     
[用 Caffeine 和 Redis 管理 Cache 案例分析](https://medium.com/fcamels-notes/%E7%94%A8-caffeine-%E5%92%8C-redis-%E7%AE%A1%E7%90%86-cache-%E6%A1%88%E4%BE%8B%E5%88%86%E6%9E%90-23e88291b289)  
[Spring Boot 2.X(七)：Spring Cache 使用](https://juejin.cn/post/6844903966615011335)       
[SpringBoot - 第二十四章 | 緩存支持註解配置與EhCache使用(一)](https://morosedog.gitlab.io/springboot-20190411-springboot24/)   
[Spring Cache，從入門到真香](https://juejin.cn/post/6882196005731696654)    
[Create Custom `CacheErrorHandler`](https://hellokoding.com/spring-caching-custom-error-handler/)


## SCENARIO (Why using Cache)

當重開Server後需要一些時間才能填回local cache，這段時間會增加database Server的負載    
**故可以將`local cache`的資料寫入`external cache`，未來server reboot後可從`external cache`讀回來以此增加效率**   

- 若有partition，等於來自同時間全部 servers 收到同一個 key 的查詢，總共只會發一次查詢到 external cache/database。 
    - e.g. **假設一次重開十台機器，十台機器每秒收到 100 筆同樣的查詢，等於將 1000 筆對 database 的查詢降為對 external cache 的 1 筆查詢。**


## Relationship In Spring Framework
- Server：web server 或 API server。
- Local cache：server 的 in-memory cache。 (`Caffeine`)
- External cache：多台 servers 共用的 cache server (`Redis`)
  - 為降低 external cache 的負載，可以在 local cache 發出請求時多過一層`Caffeine` 的async load，用來統合同一個key多筆查詢成一筆對 external cache (`Redis`) 以及 database (`MySQL`) 的查詢。
- Database：儲存原始資料的 database server (`MySQL`)

## Cache Annotation 
### `@EnableCaching` class CachingConfig

A class annotations that Is used with `@Configuration`

Annotate it in Caching Configuration Class
```java
@Configuration
@EnableCaching
public class CachingConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("addresses");
    }
}
```

Annotate it in Application 
```java
@SpringBootApplication
@EnableCaching
public class SpringBootApplication{
    public static void main(String[] args){
        SpringApplication.run(SpringBootApplication.class, args);
    }
}
```


![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/7dee92b136f04551b3e0061c3c36c056~tplv-k3u1fbpfcp-watermark.awebp) 
- Different `cacheNames` map to specific Cache Objects
- Use `cacheNames` instead of `value` is recommended
### @Cacheable(value , cacheNames, key, keyGenerator, cacheManager, cacheResolver, condition, unless, sync)

| Element |  Description | 
|---------|--------------|
|`String[] cacheNames`| Names of the caches in which method invocation results are stored.
|`String condition` | Support Spring Expression Language (SpEL) expression for caching condition
|`String key`       | Support SpEl exp computing the key dynamically.
|`String keyGenerator`| Set up Bean name of custom KeyGenerator to generate an key
|`boolean sync`| Synchronize the invocation of the underlying method if several threads are attempting to load a value for the same key.
|`String unless` | SpEL expression used to veto method caching.

```java
@Override
@Cacheable(cacheNames = "books", condition = "#id > 1", sync = true)
// only if id is bigger than 1 => do caching
public Book getById(Long id) {
    return new Book(String.valueOf(id), "some book");
}

_________________________________
| books                          |
|--------------------------------|
| key   | value                  |
|--------------------------------|
|   1   | {game of nothing, 1989}|
| ....  | ...... ..... ..... ... |
| ....  | .....  ....... .... .. |
|_______|________________________|
```
- parameter `id` : is default-Key name for `@Cacheable`'s attribute `Key`  
  `@Cacheable(cacheNames = "books", key = "#id" ,condition = "#id > 1", sync = true)` 

#### KeyGenerator

Default `SimpleKeyGenerator` implementation (**uses parameters in method provided to generate a key.)**  
This means that if we have two methods that **use the same cache name and set of parameter types**, then there's a high probability that it will result in a collision.
####  `KeyGenerator` Class

This is responsible for generating every key for each data item in the cache, which would be used to lookup the data item on retrieval.   

The default implementation here is the `SimpleKeyGenerator`   
The caching abstraction uses a `simpleKeyGenerator` based on the following algorithm     
1. If no params are given, return `SimpleKey.EMPTY`.
2. If only one param is given, return that instance.
3. If more the one param is given, return a `SimpleKey` containing all parameters.

```java

@Override
public Object generate(Object target, 
                       Method method, 
                       Object... params) {
    return generateKey(params);
}

/**
 * Default Key Generator Implementation 
 * Generate a key based on the specified parameters.
 */
public static Object generateKey(Object... params) {
    if (params.length == 0) {
        return SimpleKey.EMPTY;
    }
    if (params.length == 1) {
        Object param = params[0];
        if (param != null && !param.getClass().isArray()) {
            return param;
        }
    }
    return new SimpleKey(params);
}
```

For example :: Two Different methods with same parameter and same `cacheNames`
```java
@Override
@Cacheable(cacheNames = "books", sync = true)
public Book getByIsbn(String isbn) {
    simulateSlowService();
    return new Book(isbn, "Some book");
}

@Override
@Cacheable(cacheNames = "books", sync = true)
public String test(String test) {
    return test;
}


logger.info("test getByIsbn -->" + bookRepository.getByIsbn("test")); // key : test , return Book object
logger.info("test test -->" + bookRepository.test("test")); // key : test , but return String
```

Both methods are looking for key `test` and returning the different object type, it causes `ClassCastException`
```bash
Caused by: java.lang.ClassCastException: class com.example.caching.Book cannot be cast to class java.lang.String (com.example.caching.Book is in unnamed module of loader 'app'; java.lang.String is in module java.base of loader 'bootstrap')
	at com.sun.proxy.$Proxy33.test(Unknown Source) ~[na:na]
	at com.example.caching.AppRunner.run(AppRunner.java:23) ~[main/:na]
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:795) ~[spring-boot-2.3.2.RELEASE.jar:2.3.2.RELEASE]
	... 5 common frames omitted
```

To handle the exception we must define custom implementation of `KeyGenerator`
```java
@Component
public class MyKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getName() + method.getName() + 
                Stream.of(params).map(Object::toString).collect(Collectors.joining(","));
    }
}

// or
@Configuration
public class CachConfig{
    @Bean(name =  "cacheKeyGenerators")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator(){
            @Override
            public Object generate(Object target, Method method, Object ... params){
                return method.getName() + "[" + Arrays.asList(params).toString() + "]";
            }
        }
    }
}

@Override
@Cacheable(cacheNames = "books", sync = true, keyGenerator = "cacheKeyGenerators")
public Book getByIsbn(String isbn) {
    simulateSlowService();
    return new Book(isbn, "Some book");
}

@Override
@Cacheable(cacheNames = "books", sync = true, keyGenerator = "cacheKeyGenerators")
public String test(String test) {
    return test;
}
```

#### unless

```java
@Override
@Cacheable(cacheNames = "books", unless = "#id > 1", sync = true)
// if id is bigger than 1 => do not caching
public Book getById(Long id) {
    return new Book(String.valueOf(id), "some book");
}
```
### @CachePut(value, cacheNames, key, keyGenerator, cacheManager, cacheResolver, condition, unless)

Difference btw `@CachePut` and `@Cacheable`
- `@Cacheable` : If an item is found in the cache , Method code is not executed .
- `@CachePut` : **Always execute method code , and update the cache after the method is executed** (goo for updating).
### @CacheEvict(value, cacheNames, key, keyGenerator, cacheManager, cacheResolver, condition, allEntries, beforeInvocation

| Parameter |  Description |
| --------  | ------------ |
| `allEntries` | IF `true` then cache will be deleted ONCE method invocation
| `beforeInvocation` | IF `true` then cache will be deleted BEFORE method invocation  

By default `@CacheEvict`, runs after method invocation.
```java
@CacheEvict(
   cacheNames = "persons", 
   key = "#person.emailAddress")
public void deletePerson(Person person) {
    //...
}

@CacheEvict(
   cacheNames = "persons", 
   allEntries = true, 
   beforeInvocation = true)
public void importPersons(){
    //..
}
```
### @Caching Method

It contains `@Cacheable` , `@CachePut` and `@CacheEvict`
```java
public @interface Caching {
	Cacheable[] cacheable() default {};
	CachePut[] put() default {};
	CacheEvict[] evict() default {};
}


@Caching(evict = {  
 @CacheEvict("deleting"),  
 @CacheEvict(value="directory", key="#customer.id") })
public String getName(Customer customer) {…}
```
### @CacheConfig class
- it allows to share the cache names (`KeyGenerator`、`CacheManager` and `CacheResolver`)
- Priority of CacheConfig's `cacheNames` is lower than method cache annotations' `cacheNames` or `value`

```java
// All data are stored in the CustomerCache
@CacheConfig(cacheNames={"customerCache"})
public class CustomerData {
   @Cacheable
   public String getName(Customer customer) {
        //...
   }
}
```
## CacheManager Configuration via `CachingConfigurerSupport`

CacheManager configures Cache-Providers  (e.g `Caffeine`, `Redis` , ... etc)  to be used in Spring Boot

[](https://www.javadevjournal.com/spring-boot/3-ways-to-configure-multiple-cache-managers-in-spring-boot/)  

We can override these methods to configure our custom `CacheManager` configuration

1. `CacheManager cacheManager()` : Return the cache manager bean to use for annotation-driven cache management.
2. `cacheResolver()` in interface `CacheResolver` : Return the CacheResolver bean to use to resolve regular caches for annotation-driven cache management.
3. `errorHandler()`of `CacheErrorHandler` class: Return the CacheErrorHandler to use to handle cache-related errors.

## CacheResolver (Use different cache provider)

implement it by overriding `public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context)` when 
1. Pick the cache provider on **case by case.**
2. Pick the cache provider **at runtime based on type of request**.


```java
@Override method resolveCaches interface CacheResolver 

```

```java
public class MultipleCacheResolver implements CacheResolver {

    // Cache Manager Fields 
    private final CacheManager simpleCacheManager;
    private final CacheManager caffeineCacheManager;

    // CACHE NAMES
    private static final String ORDER_CACHE = "orders";    
    private static final String ORDER_PRICE_CACHE = "orderPrice";

    // Method Names in Service
    private static final String GETORDERDETAIL = "getOrderDetail";


    // (SETTER) Inject Cache Managers
    public MultipleCacheResolver(CacheManager simpleCacheManager,
                                 CacheManager caffeineCacheManager) {
        this.simpleCacheManager = simpleCacheManager;
        this.caffeineCacheManager = caffeineCacheManager;
    }

    /**
      * Specify Each Cache Name that responses to certain Cache Provides
      * @return the cache(s) to use for the specified invocation. 
      * (execution of a program or methods)
      */
    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {

        Collection<Cache> caches = new ArrayList<Cache>();
        
        if (GETORDERDETAIL.equals(context.getMethod().getName())) {

            caches.add(caffeineCacheManager.getCache(ORDER_CACHE));
        } else {

            caches.add(simpleCacheManager.getCache(ORDER_PRICE_CACHE));
        }

        return caches;
    }
}

// Add Bean of `CacheResolver` in implementations of `CachingConfigurerSupport` 
// for applying multiple caches managers for our application
@Configuration
@EnableCaching
public class MultipleCacheManagerConfig extends CachingConfigurerSupport {


    // Cache Managers
    @Bean
    @Override
    public CacheManager cacheManager() {

        // CaffeineCacheManager(String... cacheNames)
        // Construct a static CaffeineCacheManager, managing caches for the specified cache customer and orders
        // e.g. @Cacheable(cacheNames = customers , ...) or @Cacheable(cacheNames = orders , ...)
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("customers", "orders");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                    .initialCapacity(200)
                    .maximumSize(500)
                    .weakKeys()
                    .recordStats());
        
        return cacheManager;
    }

    // 
    @Bean
    @Override
    public CacheManager alternateCacheManager() {
        return new ConcurrentMapCacheManager("customerOrders", "orderPrice");
    }

    // Bean of Resolver
    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new MultipleCacheResolver(alternateCacheManager(), cacheManager());
    }
}

/**
*  use different cache providers
*/
@Component
public class OrderDetailBO {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Cacheable(cacheNames = "orders", cacheResolver = "cacheResolver")
    public Order getOrderDetail(Integer orderId) {
        return orderDetailRepository.getOrderDetail(orderId);
    }

    @Cacheable(cacheNames = "orderPrice", cacheResolver = "cacheResolver")
    public double getOrderPrice(Integer orderId) {
        return orderDetailRepository.getOrderPrice(orderId);
    }
}
```
## Cache Error Handler 

```java
class CustomCacheErrorHandler implements CacheErrorHandler
class CachingConfiguration extends CachingConfigurerSupport
```

To set up custom exception handler for CacheGet, CachePut, CacheEvict, and CacheClear 
```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
public class CustomCacheErrorHandler implements CacheErrorHandler {
    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
        log.error(e.getMessage(), e);
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
        log.error(e.getMessage(), e);
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
        log.error(e.getMessage(), e);
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        log.error(e.getMessage(), e);
    }
}
```
#### Register `CacheErrorHandler` in `CachingConfigurerSupport`

```java
import xxx.yyy.CustomCacheErrorHandler;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;   
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachingConfiguration extends CachingConfigurerSupport {  
    
    //...

    @Override
    public CacheErrorHandler errorHandler() {
        return new CustomCacheErrorHandler();
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        //...
    }

}
```

## Cache Method

```java

```