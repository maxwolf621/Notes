# Spring Note

- [Spring Note](#spring-note)
  - [XML Configuration](#xml-configuration)
  - [Design Pattern and Framework in Spring](#design-pattern-and-framework-in-spring)
  - [Annotation](#annotation)
  - [Spring Boot](#spring-boot)
  - [Spring boot Security](#spring-boot-security)
  - [Token](#token)
  - [ORM](#orm)
    - [Introduction](#introduction)
    - [JPA](#jpa)
    - [Hibernate](#hibernate)
    - [Cache](#cache)
  - [Swagger](#swagger)
  - [RxJAVA](#rxjava)

[Spring Boot Tutorial](https://morosedog.gitlab.io/categories/Spring-Boot/)  

## XML Configuration

- [Bean XML Configuration](SpringBase/xml.md)
- [Hibernate XML Configuration](SpringXMLHibernate/hinbernate.md)

## Design Pattern and Framework in Spring
- [Framework MVC, MVP and MVVM](framework.md)
- [loC](SpringBase/IoC.md)  
- [Design Pattern](SpringBase/DesignPatterns.md)

## Annotation
- [Annotation](annotation/Annotations.md)
- [Spring MVC HTTP](annotation/mvcAnnotation.md)
- [@Conditional](annotation/condition.md) 
- [Layer Annotation](annotation/layerAnnotation.md) 
- [AOP](annotation/AOP.md)
- [Security](annotation/Springsecurity.md)
- [JPA annotation](annotation/jap.md)
- [Java Validators](annotation/javaValidator.md)     
- [Annotations to run Spring Boot Application](annotation/SpringBootApplication.md)
## Spring Boot

![圖 1](images/32e22d4e31fc22fb6749ff5775fd64727a39c834f25ed100fb35a13c2e362a31.png)  

## Spring boot Security

![image](https://user-images.githubusercontent.com/68631186/172059135-570bfaa7-cc5b-4e95-ba24-eb8955e6545b.png)

- [Authentication](SpringBoot/Authentication.md)
- [Filter](SpringBoot/Filter.md)   
  - [UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter](SpringBoot/AuthenticationFilter.md)
- [Authorization](SpringBoot/Authorization.md)
- [WebSecurity](SpringBoot/WebSecurity.md)
  - [CORS setup](SpringBoot/CORS.md)   
  - [UserDetailService](UserdetailsService.md)
- [SpringBoot In Action Login](JWTAuth.md) 


## Token
- [KeyStore](SpringBoot/Keystore.md)
- [Java JWT](SpringBoot/JWT.md)
- [Jwt Bash Command](SpringBoot/KeystoreInCommand.sh)

## ORM 

### Introduction
- [JPA entityManger and Hibernate Session](ORM/persistence_layer.md)
- [Entity Object States](ORM/state.md)
- [CascadeType](ORM/CascadeType.md)
- [Spring Boot @Transaction](ORM/@Transaction.md) 

- **[Database Relationship](ORM/TableRelationship.md)**  
  - [Different btw OneToMany and ManyToMany ](ORM/ManyToMany&OneToMany.md)   


### JPA

[Spring Data JPA Query Method 方法名稱查詢範例](https://matthung0807.blogspot.com/2020/04/spring-data-jpa-query-method.html)   
[Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)    
[openhom.cc JPA](https://openhome.cc/Gossip/EJB3Gossip/)   

- [EntityManager](ORM/JPA/EntityManager.md)
- [JPA Lock](ORM/jpa_lock.md)
- [JPA life Cycle Events Annotations](ORM/JPA/life_cycle_events.md)
- [GeneratedValue ](ORM/JPA/generatedValue.md)
- [@Embedded & @Embeddable](ORM/JPA/AnnotationEmbeddedAndEmbeddable.md)
- [Composite Primary Key](ORM/JPA/composite_primary_key.md)
- [SpringBoot JPA & JPQL](ORM/JPA/JPA_JELQ.md)


Mapping of Entity And DB's Table
- [Table per concrete class](ORM/JPA/table_per_concrete_class.md)

### Hibernate 

[openhome.cc Hibernate](https://openhome.cc/Gossip/HibernateGossip/)    
[hibernate-tutorials](https://codejava.net/hibernate-tutorials)   

- **[HQL](ORM/Hibernate/HQL.md)**  
 
### Cache 
- [Cache Annotation](ORM/cache/Cache.md)
- [Caffeine](ORM/cache/Caffeine.md)
- [redis](ORM/cache/Redis.md)  
- [redisTemplate](ORM/cache/RedisMethod.md) 

## Swagger
- [Spring boot 自動產生 OpenAPI 3.0 文件 (Swagger UI) 教學](https://www.ruyut.com/2022/05/spring-boot-openapi-3-swagger-ui.html)
- [SpringBoot 使用 Springfox Swagger 3.0 ](http://www.mydlq.club/article/108/)

## RxJAVA
- [Reactive Programming](reactive/RxJava.md)
- [Spring WebClient](reactive/webclient.md)
