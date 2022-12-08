# Caffeine

- [Caffeine](#caffeine)
  - [Reference](#reference)
  - [(CacheConfiguration) Server Configuration](#cacheconfiguration-server-configuration)
    - [Configuration with JAVA](#configuration-with-java)
    - [Application.Properties Configuration](#applicationproperties-configuration)
    - [Parameters of Caffeine Cache Configuration](#parameters-of-caffeine-cache-configuration)
    - [Weak and Soft Reference](#weak-and-soft-reference)
  - [Notes](#notes)
  - [Caffeine Method](#caffeine-method)
  - [Caffeine Cache with Annotations](#caffeine-cache-with-annotations)

There are two methods to use Caffeine 
1. Via Caffeine class methods
2. Via Spring Cache annotation
## Reference

[SpringBoot 使用 Caffeine 本地快取](http://www.mydlq.club/article/56/)    
[guava Cache Classes](https://skyao.gitbooks.io/learning-guava/content/cache/code/interface_Cache.html)    
[Sprboot + spring cache implements two-level caching (redis + caffeine)](https://programmer.help/blogs/sprboot-spring-cache-implements-two-level-caching-redis-caffeine.html)     
[springboot-implement-caffeine-cache](https://sunitc.dev/2020/08/27/springboot-implement-caffeine-cache/)    
[Class `CaffeineCacheManager`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/cache/caffeine/CaffeineCacheManager.html)     
## (CacheConfiguration) Server Configuration 

### Configuration with JAVA
```java
/*1*/Configure Caffeine<Object,Object>  // Cache Configuration
/*2*/Configure Caffeine CacheManager    // CacheManger 
/*3*/CacheManager.Caffeine(CaffeineCacheManager)  
```


```java
@Configuration
@EnableCaching
public class CaffeineCacheConfig {


    //  ---------------------------------------
    // (CacheConfiguration) Caffeine Server   +
    // ----------------------------------------
    @Bean
    Public Caffeine<Object,Object> caffeine(){
        return  Caffeine.newBuilder()
                        .expireAfterWrite(10, TimeUnit.SECONDS)
                        .initialCapacity(100)
                        .maximumSize(1000);
    }

    @Bean
    public CacheManager cacheManager(){
        
        // create manager
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        // setCacheNames(Collection<String> cacheNames)
        cacheManager.setCacheNames(
            Arrays.asList(
                "cacheName_1",
                "cacheName_2"
        ));

                                            
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
```

or wrapping CacheConfiguration directly inside `CaffeineCacheManager#setCaffeine`
```java
public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager();
    cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterAccess(60, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000));
    return cacheManager;
}
```

### Application.Properties Configuration

```java
spring.cache.cache-names=people
spring.cache.caffeine.spec= initialCapacity=50, maximumSize=500, expireAfterWrite=10s, refreshAfterWrite=5s

@SpringBootApplication
@EnableCaching // Enable Cache Server
public class SpringBootApplication {
    // ...
}

@Configuration
public class CacheConfig {

    /**
     * <p> For Bean of CacheLoader </p>
     * <p> We must configuration caffeine with attribute 'refreshAfterWrite' </p>
     */
    @Bean
    public CacheLoader<Object, Object> cacheLoader() {
        CacheLoader<Object, Object> cacheLoader = new CacheLoader<Object, Object>() {

            @Override
            public Object load(Object key) throws Exception {
                return null;
            }
            // return value and refresh
            @Override
            public Object reload(Object key, Object oldValue) throws Exception {
                return oldValue;
            }
        };

        return cacheLoader;
    }

}
```

### Parameters of Caffeine Cache Configuration

| Parameter | Type |              |
| --------  | ---- | -------------|
|`initialCapacity1` | integer |Initial Capacity of Cache
|`maximumSize`| long| Maximum Size of Caches
|`maximumWeight` |long | Maximum Weight of Caches
|`expireAfterAccess`| duration| duration for caches to be expired after your last access
|`expireAfterWrite` | duration | duration for caches to be expired after Write
|`refreshAfterWrite`|duration |interval of refresh the caches after Write
|`recordStats`| |Strategy mode 

- `maximumSize` and `maximumWeight` cant be configured at the same time
- Priority of `expireAfterWrite` is higher than `expireAfterAccess`
- If `expireAfterWrite` or `expireAfterAccess` is requested entries may be evicted on each cache modification, on occasional cache accesses, or on calls to `Cache.cleanUp()`. 
- **Expired entries may be counted by `Cache.size()`, but will never be visible to read or write operations.**

### Weak and Soft Reference

```java
Caffeine.newBuilder().softValues().build();
Caffeine.newBuilder().weakKeys().weakValues().build();
```
To manage the data stored in Cache we have
| key-value management|
|---------------------|
|weakKeys             |
|softValues           |
|weakValues           |

By default, the returned cache uses equality comparisons `==` (the equals method) to determine equality for keys or values. 
- If `weakKeys()` was specified, the cache uses identity (`==`) comparisons instead for keys.  
  Likewise, if `weakValues()` or `softValues()` was specified, the cache uses identity(`==`)comparisons for values.
- `weakValues` and `softValues` can't be configured at the same time
- You wouldn't want to use `weakKeys()` or `softKeys()` because they both use `==` identity, which would cause problems for you

**If `weakKeys`, `weakValues`, or `softValues` are requested, it is possible for a key or value present in the cache to be reclaimed by the garbage collector(GC)**.
- **Use `weakValues()` when you want entries whose values are weakly reachable to be garbage collected (Delete the cache while GC scan it)**
- **`softValues()` is good for caching... if you have a `Map<Integer, Foo>` and you want entries to to be removable in response to memory demand(delete the caches if the memory capacity reaches limit)**.

## Notes  

- Entries are automatically evicted from the cache when any of `maximumSize`, `maximumWeight`, `expireAfterWrite`, `expireAfterAccess`, `weakKeys`, `weakValues`, or `softValues` are requested  
If `maximumSize` or `maximumWeight` is requested entries may be evicted on each cache modification.

- The `Cache.cleanUp()` method of the returned cache will also perform maintenance, but calling it should not be necessary with a high throughput cache.  
Only caches built with `removalListener`, `expireAfterWrite`, `expireAfterAccess`, `weakKeys`, `weakValues`, or `softValues` perform periodic maintenance.

- **The caches produced by `CacheBuilder` are serializable, and the deserialized caches retain all the configuration properties of the original cache.** 

- The serialized form does not include cache contents, but only configuration.

## Caffeine Method

```java
@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserRepository userRepo;

    // use Caffeine Methods 
    // with key : string  - value : Object pair
    @Autowired
    Cache<String, Object> caffeineCache;

    @Override
    public void addUserInfo(UserInfo userInfo) {

        userRepo.save(userInfo);

        // put data to cache
        // key : user_id , value userInfo : object
        caffeineCache.put(String.valueOf(userInfo.getId()),userInfo);
    }

    @Override
    public UserInfo getByName(Integer id) {

        // get from cache if present 
        caffeineCache.getIfPresent(id);
        UserInfo userInfo = (UserInfo) caffeineCache.asMap().get(String.valueOf(id));
        if (userInfo != null){
            return userInfo;
        }

        UserInfo userInfo = userRepo.findById(id)
                                    .map(user ->{
                                            caffeineCache.put(String.valueOf(userInfo.getId()),userInfo) })
                                    .orElseThrow(()-> new RuntimeException("Not Found"));
        )
        
        return userInfo;
    }

    @Override
    public UserInfo updateUserInfo(UserInfo updateUserInfo) {
        //...
        // update the data in cache
        
        caffeineCache.put(String.valueOf(updateUserInfo.getId()),updateUserInfo);
        
        return oldUserInfo;
    }

    @Override
    public void deleteById(Integer id) {
        
        userRepo.findById(id).ifPResentOrElse(
            () -> caffeineCache.asMap().remove(String.valueOf(id)),
            () -> new RuntimeException("Not Found");
        )
        userRepo.deleteById(id)    
    }

}
```

## Caffeine Cache with Annotations

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```
```java 
@Slf4j
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")
public class UserInfoServiceImpl implements UserInfoService {

    private HashMap<Integer, UserInfo> userInfoMap = new HashMap<>();


    @Override
    @CachePut(key = "#userInfo.id")
    public void addUserInfo(UserInfo userInfo) {
        
        userInfoMap.put(userInfo.getId(), userInfo);
    }


    @Override
    @Cacheable(key = "#id")
    public UserInfo getByName(Integer id) {
        log.info("get");
        return userInfoMap.get(id);
    }

    @Override
    @CachePut(key = "#userInfo.id")
    public UserInfo updateUserInfo(UserInfo userInfo) {

        if (!userInfoMap.containsKey(userInfo.getId())) {
            return null;
        }

        UserInfo oldUserInfo = userInfoMap.get(userInfo.getId());

        if (!StringUtils.isEmpty(oldUserInfo.getAge())) {
            oldUserInfo.setAge(userInfo.getAge());
        }
        if (!StringUtils.isEmpty(oldUserInfo.getName())) {
            oldUserInfo.setName(userInfo.getName());
        }
        if (!StringUtils.isEmpty(oldUserInfo.getSex())) {
            oldUserInfo.setSex(userInfo.getSex());
        }

        userInfoMap.put(oldUserInfo.getId(), oldUserInfo);

        return oldUserInfo;
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteById(Integer id) {

        userInfoMap.remove(id);
    }

}
```

```java
@ConfigurationProperties(prefix = "caching")  
@Configuration  
@Slf4j  
@Data  
public class CacheConfig {
    
    @Data  
    public static class LocalCacheSpec {  
        private Integer timeout;  
        private Integer max = 500;  
    }
    
    @Bean  
    public Ticker ticker() {  
        return Ticker.systemTicker();  
    }  

    // These two fields map to "caching.xxxx" in the application.properties
    private Map<String, LocalCacheSpec> localCacheSpecs;
    private Map<String,Integer> redisCacheSpecs;

    
    @Bean
    public CacheManager caffeineCacheManager(Ticker ticker) {  
        SimpleCacheManager manager = new SimpleCacheManager();  
 
        if (localCacheSpecs != null) { 
            List<CaffeineCache> caches = 
                localCacheSpecs.entrySet()
                                .stream()  
                                .map(entry -> 
                                    buildCache(entry.getKey(), entry.getValue(), ticker)
                                ).collect(Collectors.toList());  
            manager.setCaches(caches);  
    
        }
        return manager;  
    }

    private CaffeineCache buildCache(String name, 
                                    LocalCacheSpec cacheSpec, 
                                    Ticker ticker) {
        
        log.info("Cache {} specified timeout of {} min, max of {}", 
                 name, cacheSpec.getTimeout(), 
                 cacheSpec.getMax());  
        
        final Caffeine<Object, Object> caffeineBuilder  = 
                                        Caffeine.newBuilder()
                                                .expireAfterWrite(cacheSpec.getTimeout(), TimeUnit.SECONDS)  
                                                .maximumSize(cacheSpec.getMax())  
                                                .ticker(ticker);  
    
        return new CaffeineCache(name, caffeineBuilder.build());  
    }

    // REDIS
    @Bean  
    public CacheManager redisCacheManager(
        RedisConnectionFactory redisConnectionFactory) 
    {
            // cacheDefaults
            var redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()  
                                        .entryTtl(
                                            Duration.ofHours(4))  
                                        .prefixKeysWith(
                                            "test:")　
                                        .serializeValuesWith(
                                            RedisSerializationContext.SerializationPair
                                            .fromSerializer(
                                                    new GenericJackson2JsonRedisSerializer()
                                        );

        // Configurations of Redis From Application.Properties
        Map<String, RedisCacheConfiguration> redisCacheConfigMap = new HashMap<>(redisCacheSpecs.size());
  
        for (Map.Entry<String, Integer> entry : redisCacheSpecs.entrySet()) {  
                    redisCacheConfigMap.put(entry.getKey(), 
                    redisCacheConfiguration.entryTtl(Duration.ofSeconds(entry.getValue())));  
        }
        return RedisCacheManager.builder(RedisCacheWriter
                    .nonLockingRedisCacheWriter(redisConnectionFactory))
                    .initialCacheNames(redisCacheSpecs.keySet()) // <-- cache names 
                    .withInitialCacheConfigurations(redisCacheConfigMap)  // <-- configuration
                    .cacheDefaults(redisCacheConfiguration)
                    .build();  
    }
}
```
