# Pre-Defined Conditions

Bean is loaded depending on Condition
- [Pre-Defined Conditions](#pre-defined-conditions)
  - [Reference](#reference)
  - [@ConditionalOnBean](#conditionalonbean)
  - [@ConditionalOnMissingBean](#conditionalonmissingbean)
  - [@ConditionalOnProperty (重要)](#conditionalonproperty-重要)
    - [`havingValue` attribute](#havingvalue-attribute)
    - [`value` attribute](#value-attribute)
    - [`matchIfMissing` attribute](#matchifmissing-attribute)
  - [@ConditionalOnExpression](#conditionalonexpression)
  - [@ConditionalOnJava](#conditionalonjava)
  - [@ConditionalOnClass and @ConditionalOnMissingClass](#conditionalonclass-and-conditionalonmissingclass)
  - [@ConditionalOnNotWebApplication](#conditionalonnotwebapplication)
  - [@ConditionalOnResource(resource = "classpath: ...")](#conditionalonresourceresource--classpath-)
  - [@Profile](#profile)

## Reference 

- [Pre-Defined Conditions](https://reflectoring.io/spring-boot-conditionals/#pre-defined-conditions)

## @ConditionalOnBean 

Load a bean only if a certain other bean is available in the application context:
```java
// B is loaded if A is available in the application context
@Configuration
@ConditionalOnBean(A.class)
class B {
  ...
}
```

## @ConditionalOnMissingBean

Load a bean only if a certain other bean is not in the application context

```java
// InMemoryDataSource is injected into the application context
// if there is not already a dataSource bean available
@Configuration
class OnMissingBeanModule {

  @Bean
  @ConditionalOnMissingBean
  DataSource dataSource() {
    return new InMemoryDataSource();
  }
}
```

## @ConditionalOnProperty (重要)

**It may be the most commonly used conditional annotation in Spring Boot projects**
```java 
@Bean(name = "emailNotification")
// if notification.service is defined in the application.properties 
@ConditionalOnProperty(prefix = "notification", name = "service")
public NotificationSender notificationSender() {
    return new EmailNotification();
}
```

`notificationSender()` is only loaded if the property `notification.service` is defined in in the `application.properties` file
```bash
#prefix       name   =havingValue 
 notification.service=email
```

### `havingValue` attribute

it defines the value that a property must have in order for a specific bean to be added to the Spring container.

```java
@ConditionalOnProperty(prefix = "notification",
                       name = "service", 
                       havingValue = "sms")
```
- `notificationSender()` is only loaded if the `property notification.service` has the value `sms`
  ```bash
  notification.service=sms
  ```

### `value` attribute

it defines the `prefix.value` in the application.properties
```bash
# prefix             value  =havingValue
spring.http.encoding.enabled=true
```

### `matchIfMissing` attribute
- [StackOverflow Ref](https://stackoverflow.com/questions/26394778/what-is-purpose-of-conditionalonproperty-annotation)


If `module.enabled=true` is not defined or property `module.enabled` havingValue other than `false` then class `CrossCuttingConcernModule` is loaded by default.(`module.enabled`只要不是`false`就載入)

```java
@Configuration
@ConditionalOnProperty(
    value="module.enabled", 
    havingValue = "true", 
    matchIfMissing = true)
class CrossCuttingConcernModule {
  ...
}
```

[Follow Up Problem](https://stackoverflow.com/questions/47561048/spring-condiitonalonproperty-how-to-match-only-if-missing)
```java
@Bean
@ConditionalOnProperty("some.property.text")
public Apple createAppleX() {}

@Bean
@ConditionalOnProperty("some.property.text", matchIfMissing=true, havingValue="value_that_never_appears")
public Apple createAppleY() {}
```

## @ConditionalOnExpression
```java
@ConditionalOnExpression(
  "  ${logging.enabled:true} and 
    '${logging.level}'.equals('DEBUG')
  "
)
public class LoggingService {
    // ...
}
```

## @ConditionalOnJava

Allow the class only to be loaded for the specific java version

```java
@Service
@ConditionalOnJava(JavaVersion.EIGHT)
public class java8Service{
    //..
}
```

## @ConditionalOnClass and @ConditionalOnMissingClass

Load this bean if the certain class is on classPath
```java
@Configuration
@ConditionalOnClass(name = "this.class.does.not.Exist")
class OnClassModule {
  ...
}
```

## @ConditionalOnNotWebApplication
Load a bean only if we’re not running inside a web application


## @ConditionalOnResource(resource = "classpath: ...")

Create a bean if specified resource found otherwise it will not create a bean.

```java
@ConditionalOnResource(resources = {"classpath:example.json","classpath:example1.json"})
@Bean
CatService catService(){
  // ... 
}
```

## @Profile

[Don't use profile](https://reflectoring.io/dont-use-spring-profile-annotation/)