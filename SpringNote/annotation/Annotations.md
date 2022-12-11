# Basic Annotations

- [Basic Annotations](#basic-annotations)
  - [Reference](#reference)
  - [@Configuration](#configuration)
    - [@Bean](#bean)
  - [@EnableAutoConfiguration](#enableautoconfiguration)
  - [@Component](#component)
  - [@ComponentScan](#componentscan)
  - [Configuration vs Component](#configuration-vs-component)
      - [@Import](#import)
    - [@import vs @ComponentScan](#import-vs-componentscan)
  - [DI-Related Annotations](#di-related-annotations)
    - [@Autowired (DI)](#autowired-di)
    - [@ImportResource class/interface](#importresource-classinterface)
  - [Inject properties from `application.properties`](#inject-properties-from-applicationproperties)
    - [@ConfigurationProperties (class/interface/method)](#configurationproperties-classinterfacemethod)
    - [@ConfigurationPropertiesScan("package\_path")](#configurationpropertiesscanpackage_path)
    - [@PropertySource class/interface](#propertysource-classinterface)
    - [@Value("${propertyName}")](#valuepropertyname)
  - [Inject the exact bean](#inject-the-exact-bean)
    - [@Primary](#primary)
    - [@Autowire @Qualifier(componentName | BeanName)](#autowire-qualifiercomponentname--beanname)
    - [@Resource(beanName) method/field](#resourcebeanname-methodfield)
      - [@Resource(beanName) field](#resourcebeanname-field)
      - [@Resource(name = "beanName") Method](#resourcename--beanname-method)
  - [@Scope class](#scope-class)


## Reference

- [Properties Injection](https://www.baeldung.com/properties-with-spring)
- [Spring @Configuration作用](https://matthung0807.blogspot.com/2019/04/spring-configuration_28.html)   

## @Configuration

`@Configuration` Classes are Metadata and replace XML configuration.   

![](https://i.imgur.com/CpZdLGY.png)
- **Configuration Classes register the beans in Spring Container**
- this annotation is same as using Component Class with `@ComponentScan`
- Configuration Class indicates that **A class declares one or more `@Bean`  methods**.      
```java
@Configuration
public class AppConfig {

    @Bean
    public FoodService foodService() {
        return new FoodService();
    }

    @Bean
    public DrinkService drinkService(){
       return new drinkService(foodService());
    }

}
```
- Configuration classes are processed by the **Spring Container** to generate bean **definitions** and **server requests at run-time**
- **It's recommended that class with `@Configuration` using `@ImportResource` loads xml Configuration file** for third party class that requires xml configuration files
### @Bean

`@Bean` is one of the most used and important spring annotation.    

Beans are created in `@Configuration` class and stored in spring Container 
```java
@Bean 
public object methodX(...)
```   
- It Indicates that a `methodX` be managed by the Spring container.  


The annotation also can be used with parameters like **name, initMethod and destroyMethod.**
- `@bean(name = "name_Of_This_Bean", init method = "method_name" , destroyMethod = "method_name")`   

For example indicate bean method from a bean class (class Computer) in configuration Class
```java
@Configuration
public class AppConfig {

    @Bean(name = "comp", initMethod = "turnOn", destroyMethod = "turnOff")
    Computer computer(){
        return new Computer();
    }
}

//  Bean Class Computer
public class Computer {

    // init Method and destroy Method
    public void turnOn(){
       //..
    }
    public void turnOff(){
        //...
    }
}
```

**Using `@PreDestroy` and `@PostConstruct` annotation is recommended to init Method or destroy Method** instead of `initMethod` and `destroyMethod`
```java
 public class Computer {
    @PostConstruct
    public void turnOn(){
        //..
    }
    @PreDestroy
    public void turnOff(){
        //..
    }
}
```

## @EnableAutoConfiguration

**Automatically configure Spring Application Project According to (your spring application project) JAR packages dependencies**

If there exists HSQLDB in Spring Application Project's class path, Programmer has no any configuration of database to connect beans, Spring will auto configure a in-memory database     

`@EnableAutoConfiguration(exclude : xxx.class)` : 不要被自動配置的(xxx.class)的Configuration

## @Component

It Indicates that an annotated class is a **component considered as candidates for auto-detection** when using annotation-based configuration and class path scanning.


**Components are registered in Application Context because they themselves are annotated with `@Component`**
![](https://i.imgur.com/ULOm9bX.png)
- Spring only picks up and *registers* beans with `@Component` and doesn't look for `@Service` and `@Repository` in general.  

## @ComponentScan

[baeldung spring-component-scanning](https://www.baeldung.com/spring-component-scanning)

`@ComponentScan` annotation along with the `@Configuration` annotation to specify the packages that we want to be scanned.

If `ComponentScan` without any arguments it **tells Spring to scan the current package and all of its sub-packages.**, for example ::
```java
// Packages 
// theses components will be scanned
@Component
public class Dog { ... }

@Component 
public class Cat { ... }

@Component  
public class Spider { ... }


@Configuration
@ComponentScan // without any arguments
public class SpringComponentScanApp {
    private static ApplicationContext applicationContext;

    @Bean  // <--- will be scanned 
    public ExampleBean exampleBean() {
        return new ExampleBean();
    }

    public static void main(String[] args) {
        applicationContext = new AnnotationConfigApplicationContext(SpringComponentScanApp.class);
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
    }
}
/**
springComponentScanApp
cat
dog
spider
exampleBean
*/
```

## Configuration vs Component

[Configuration vs Component](https://stackoverflow.com/questions/39174669/what-is-the-difference-between-configuration-and-component-in-spring)

```java
@Configuration
public class MyConfig {

    @Bean
    public ServiceA aService(){
        return new ServiceA();
    }

    @Bean
    public ServiceB bService(){
        return new ServiceB(aService());
    }

}
```
Note that `ServiceB` bean has a dependency on `ServiceA` and this is not autowired.   
**Instead, the way it's written implies that a new instance is created, which is not actually created by Spring. You, the programmer, did it with the `new` keyword instead.**

So, if we do use `@Configuration`, then it uses CGLIB proxying, and in this situation it creates a singleton bean managed by the Spring context. 
- **Spring creates a CGLIB proxy around the `@Configuration` classes. The calls are intercepted and then Spring checks the container before creating a new bean for us.**


If you invoke it multiple times, it returns the same bean that was created by Spring - sort of autowiring effect.

**Whereas if you use @Component, it won't do CGLIB proxying and will simply return a new instance every time the method is invoked**, instead of providing the Spring managed instance. (Remember that a Spring bean is something that is managed by the Spring container, and, as a developer, it's your job is to pull them in, e.g. with @Autowired.

The same `@Component` effect can be achieved with `@Configuration(proxyEnabled= false)`
```java
@Configuration(proxyEnabled = false) // Lite mode, same effect as @Component
public class MyConfig {

  //...   
  
  @Autowired // using autowire to inject 
  @Bean
  public ServiceB bService(ServiceA aServiceBean){
      return new ServiceB(aServiceBean);
  }

}
```

#### @Import
[baeldung @import](https://www.baeldung.com/spring-import-annotation)

Import specific Configurations instead of controlling dozen of configuration classes within different sources

```java
@Configuration
// Grouping 
@Import({ DogConfig.class, CatConfig.class })
class MammalConfiguration {
  // ...
}
```

### @import vs @ComponentScan

Both annotations can accept any `@Component` or `@Configuration` class.

**Typically, we start our applications using `@ComponentScan` in a root package so it can find all components for us.**
- If we're using Spring Boot, then `@SpringBootApplication` already includes `@ComponentScan`

If we need to deal with beans from all different places, like components, different package structures, and modules built by ourselves and third parties.    

In this case, adding everything into the context risks starting conflicts about which bean to use. Besides that, we may get a slow start-up time.

## DI-Related Annotations

### @Autowired (DI)

Spring `@Autowired` annotation is used for automatic injection of **beans** in the container.

`@Autowired(required=false)` : 表示就算找不到此bean也不拋出Exception

### @ImportResource class/interface

Load the XML configuration

## Inject properties from `application.properties`

### @ConfigurationProperties (class/interface/method)
```java
database.url=jdbc:postgresql:/localhost:5432/instance
database.username=foo
database.password=bar
```
```java
// Assign set of vals of Properties
@ConfigurationProperties(prefix = "database")
@Data
public class Database {
    String url; // url = database.url
    String username; // username = database.username
    String password; // password = database.password
}
```
### @ConfigurationPropertiesScan("package_path")

```java
// Scan Properties
@SpringBootApplication
@ConfigurationPropertiesScan("com.xxx.yyy.ItemConfigProperties")
public class EnableConfigurationDemoApplication { 
    public static void main(String[] args) {   
        SpringApplication.run(EnableConfigurationDemoApplication.class, args); 
    } 
}
```

```java
@Data
public class Item {
    private String name;
    private int size;
}

@Configuration
public class ItemConfigProperties {
    @Bean
    // item.name = cola
    // item.size = 50*50
    @ConfigurationProperties(prefix = "item")
    public Item item() {
        return new Item();
    }
}
```

### @PropertySource class/interface

- This annotation is used with `@Configuration` classes.
- It Provides a simple declarative mechanism for adding a property source to Spring’s Environment.  
- There is a similar annotation for adding an array of property source **FILES** i.e `@PropertySources`

### @Value("${propertyName}")

Assign val of property to field 

```java
// jdbc.url = 1111.2222.3333
@Value( "${jdbc.url}" )
private String jdbcUrl;
```

## Inject the exact bean 
### @Primary

To specify which bean of a certain type should be injected by default.

```java
@Configuration
public class Config {
 
    @Bean
    public Employee johnEmployee() {
        return new Employee("John");
    }
 
    @Bean
    @Primary
    public Employee tonyEmployee() {
        return new Employee("Tony");
    }
}
```

### @Autowire @Qualifier(componentName | BeanName)


**It is suggested to use @Resource for fields and setter injection. Stick with @Qualifier and @Autowired for constructor or a multi-argument method injection.**

Inject the exact bean/component in the parameter, method, or field,
- `@Autowire @Qualifier(componentName | BeanName)` : Inject the componentName | BeanName 


```java
// Beans with same name
@Component("fooFormatter")
public class FooFormatter implements Formatter {
  //..
}

@Component("barFormatter")
public class BarFormatter implements Formatter {
  //...
}

@Component
public class FooService {
     
    @Autowired
    // Inject FooForMatter.class 
    @Qualifier("fooFormatter")
    private Formatter formatter;
}
```
same as
```java
@Component
@Primary
public class FooFormatter implements Formatter {
    //...
}

@Component
public class BarFormatter implements Formatter {
    //...
}
```

### @Resource(beanName) method/field

This annotation helps to extract specific bean name from the container
`@Resource` Match by Type with arguments
#### @Resource(beanName) field
```java
@Configuration
public class resourceConfig{
  @bean(name = "bangalcat")
  public Cat bengalCat{
    //...
  }
  @bean(name = "tabby")
  public Cat tabby{
    //...
  }
}

public class catService{
  @Resource(name = "bangalcat")
  private Cat catDependency1 ;
  
  @Resource(name = "tabbycat")
  private Cat catDependency2;

}
```

#### @Resource(name = "beanName") Method
```java
public class catService{
  
  private Cat tabbyCat;

  // Setter Injection
  // Inject the bean name tabbycat
  @Resource(name = "tabbycat" )
  protected void setCatDependency(Cat catDependency){
    this.tabbyCat = catDependency;
  }
}
```

## @Scope class
- Singleton by default : share the same bean 
- Prototype : each time the new bean is created 
- Session : each Session the new bean is created
- Request : each Request the new bean is created