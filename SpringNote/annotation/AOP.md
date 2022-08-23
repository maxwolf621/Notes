# AOP
- [AOP](#aop)
  - [Reference](#reference)
  - [Enable Aspect-JS Annotation](#enable-aspect-js-annotation)
    - [@EnableAspectJAutoProxy class/interface](#enableaspectjautoproxy-classinterface)
    - [@Aspect class/interface](#aspect-classinterface)
  - [Point-cut Configuration](#point-cut-configuration)
    - [Pointcut designators](#pointcut-designators)
      - [@target](#target)
      - [@args](#args)
      - [this("target_class_givenType") and target("target_class_givenType")](#thistarget_class_giventype-and-targettarget_class_giventype)
      - [@annotation(AnnotationNameThatAnnotatesOnMethods)](#annotationannotationnamethatannotatesonmethods)
      - [@within(Class With Specific Annotation)](#withinclass-with-specific-annotation)
      - [execution(visibility returnType methodName(parameters))](#executionvisibility-returntype-methodnameparameters)
      - [within](#within)
    - [SystemArchitecture](#systemarchitecture)
  - [Advises Configuration](#advises-configuration)
      - [@Around methodX](#around-methodx)
      - [@After(YY) methodX](#afteryy-methodx)
      - [@Before(YY) methodX](#beforeyy-methodx)
      - [@AfterReturning(YY) methodX](#afterreturningyy-methodx)
      - [@AfterThrowing(YY) methodX](#afterthrowingyy-methodx)
  - [Advise uses Point-Cut arguments](#advise-uses-point-cut-arguments)
  - [@annotation Method](#annotation-method)

## Reference
- [Spring AOP](https://segmentfault.com/a/1190000020904086) 
- [PointCut表示式](https://openhome.cc/Gossip/Spring/Pointcut.html)
- [PointCut designators](https://tedblob.com/pointcut-designators-of-spring-aop/)  
- [[Day 27] 遠征 Kotlin × Spring Boot 介紹 Spring AOP 機制](https://ithelp.ithome.com.tw/articles/10249999)

![圖 1](../images/3a4dc5e03c11633f1915e3c1fc8ab1e111fe132322530864d06b8ee432317dbc.png)  
Aspect 切面：由 切入點（PointCut）與 通知（Advice）組成，主要就是用來設定切入點（PointCut）與切入特定動作（Advice）   
PointCut 切點：設定要被 AOP 切入的位置，例如某個類別或函數     
JoinPoint 連接點：為 PointCut 切入後的實際切入點，通常是一個函數   
Advice 通知：為 Joint Point 切入點實際要執行的動作，通常會將 Advice 模擬為一個攔 截器（Interceptor），並且會在連接點（Join Point）上維護多個 Advice 進行層層攔截  
## Enable Aspect-JS Annotation

- `@enableAspectJAutoProxy` + `@Configuration`
- `@Component` + `@Aspect` (Point-Cut and Advise Setup )

### @EnableAspectJAutoProxy class/interface

Enable the aspect setup for spring application

```java 
@Configuration
@EnableAspectJAutoProxy
public class Config {
    // ...
}
```

### @Aspect class/interface

Define a `@Component` class is a cross-cutting concern
```java 
@Aspect
@Component // @aspect must annotate with component 
public class ExampleAspect {
    // ...
}
```

## Point-cut Configuration

Set an point-cut for packages, methods, `@annotations method`, and beans

### Pointcut designators

#### @target

it limits matching to join points where the class of the executing object has an ANNOTATION of the given type
```java
@Pointcut("@target(org.springframework.stereotype.Repository)")
```
#### @args

```java
@Retention(RUNTIME)
public @interface Country {
}
@Country
public enum CountryCode {
    US, UK, IN, JA
}

public String getCountryCode(CountryCode countryCode) {
    return countryCode;
}

// Pointcut and Advise
@Pointcut("@args(com.tedblob.annotations.Country)")
public void countryPointcut() {}

@After("countryPointcut()")
public void afterCountryAdvice(JoinPoint joinPoint) {
    System.out.println("After - countryPointcut" + 
                        joinPoint.getSignature().getName());
 }
```

#### this("target_class_givenType") and target("target_class_givenType")

`this` limits matching to join points (the method executions) where the bean reference (Spring AOP proxy) **is an instance of the given type.**

`target` limits matching to join points (the method executions) where the target object (application object being proxied) **is an instance of the given type**.

```java
@Component
public class CountriesRepositoryImpl implements CountryRepository {
    @Override
    public List<String> getCountries() {
       return countries;
    }
}


@Component
@Aspect
public class CurrenciesRepositoryAspect {

    // target class should match given type which is CountryRepository
    @Before("target(com.tedbob.bls.CountryRepository)")
    public void beforeTargetCountriesRepository() {
        //...
    }
 
    @After("this(com.tedbob.bls.CountryRepository)")
    public void afterCountriesRepository() {
        //...
    }
}
```
- the advice matches target class `CountriesRepositoryImpl` and it executes

#### @annotation(AnnotationNameThatAnnotatesOnMethods)

it limits matching to method executions **where the METHOD has the given annotation**.

#### @within(Class With Specific Annotation)

```java
@Secured
public class Security {
    
    public String getSecurityAlgorithm() {
        return algorithm;
    }
    public int getSecurityScore() {
        return securityScore;
    }
}


// This pointcut matches 
// all the methods inside the class annotated with @Secured.
// ( getSecurityAlgorithm and getSecurityScore method in class Security)
@Pointcut("@within(com.tedblob.annotations.Secured)")
 public void securedClassPointcut() {
}
```

#### execution(visibility returnType methodName(parameters))

```java
execution(visibility returnType methodName(parameters))
execution(visibility packages.methods(parameters))
```


- * : any
- `.*(..)` : any method

any `public` methods with any return types with `>=` parameters
```java 
execution(public * *(..))
```
any methods starting with `accountBy` with `>=` parameters
```java
execution(* accountBy*(..))
```

any methods starting with `accountBy` with `String` type parameter
```java
// could be accountByXyc or accountByVXXX 
execution(* accountBy*(String))
```

any methods named with `get..` having two parameters and the second one should be `String` type 
```java
execution(* get*(*,String))
```

any methods named with `get..` and first parameter should be String type
```java
execution(* get*(String, ..))
```

Any Methods under `cc.openhome.model/*` 
```java 
// .*(..) : any methods
execution(* cc.openhome.model.*.*(..))
```

any methods in cc.openhome.model package or sub-package
```java
execution(* cc.openhome.model.service..*.*(..))
```

#### within 
```java
within(cc.openhome.model.AccountDAO) 
//相當於 
execution(* cc.openhome.model.AccountDAO.*(..))

within(cc.openhome.model.*) 
//相當於 
execution(* cc.openhome.model.*.*(..))

within(cc.openhome.model..*) 
//相當於 
execution(* cc.openhome.model.service..*.*(..))
```

```java
// Pointcut this method with * testExecution(..)
// *  : any return type
// .. : 0 or many parameters
@Pointcut("execution(* testExecution(..))") method

// pointcut this method 
// within ric.study.demo.aop.svc..*
// .. : sub-packages
@Pointcut("within(ric.study.demo.aop.svc..*)") method

// pointcut this method 
// with ric.study.demo.aop.HaveAop
@Pointcut("@annotation(ric.study.demo.aop.HaveAop)") method 

// pointcut this method with specific bean name 
@Pointcut("bean(testController)") method 
```

### SystemArchitecture

When you often want to refer to modules of the application and particular sets of operations from within several aspects. 

Spring recommends defining a class named `SystemArchitecture` aspect that captures common pointcut expressions for this purpose. 

A typical such aspect would look as follows:
```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemArchitecture {

  /**
   * A join point is in the web layer 
   * if the method is defined 
   * in a type in the com.xyz.someapp.web package 
   * or any sub-package
   * under that.
   */
  @Pointcut("within(com.xyz.someapp.web..*)")
  public void inWebLayer() {}

  /**
   * A join point is in the service layer 
   * if the method is defined
   * in a type in the com.xyz.someapp.service package or any sub-package
   * under that.
   */
  @Pointcut("within(com.xyz.someapp.service..*)")
  public void inServiceLayer() {}

  /**
   * A join point is in the data access layer if the method is defined
   * in a type in the com.xyz.someapp.dao package 
   * or any sub-package
   * under that.
   */
  @Pointcut("within(com.xyz.someapp.dao..*)")
  public void inDataAccessLayer() {}

  /**
   * A business service is the execution of any methods defined on a service
   * interface. 
   * This definition assumes that interfaces are placed in the
   * "service" package, and that implementation types are in sub-packages.
   * 
   * If you group service interfaces by functional area (for example, 
   * in packages com.xyz.someapp.abc.service and com.xyz.def.service) then
   * the pointcut expression 
   * "execution(* com.xyz.someapp..service.*.*(..))"
   * could be used instead.
   */
  @Pointcut("execution(* com.xyz.someapp.service.*.*(..))")
  public void businessService() {}
  
  /**
   * A data access operation is the execution of any method defined on a 
   * dao interface. This definition assumes that interfaces are placed in the
   * "dao" package, and that implementation types are in sub-packages.
   */
  @Pointcut("execution(* com.xyz.someapp.dao.*.*(..))")
  public void dataAccessOperation() {}

}
```

## Advises Configuration

Giving Advises to pointCuts

```java
Advises = {@Around, @Before, @After, @AfterReturning, @AfterThrowing}
```

#### @Around methodX
around advice means we are adding extra code both `@before` and `@after` target method execution. 

#### @After(YY) methodX
methodX會在目標方法YY返回或拋出異常後呼叫
#### @Before(YY) methodX
methodX會在目標方法YY呼叫之前被呼叫
#### @AfterReturning(YY) methodX
methodX會在目標方法YY返回後呼叫
#### @AfterThrowing(YY) methodX
通知方法會在目標方法YY拋出異常後呼叫


```java
/**
 * @author Richard_yyf
 * @version 1.0 2019/10/28
 */
@Aspect
@Component
public class GlobalAopAdvice {

    @Before("ric.study.demo.aop.SystemArchitecture.dataAccessOperation()")
    public void doAccessCheck() {
        // ... 
    }

    @Before("...") 
    public void logArgs(JoinPoint joinPoint) {
        System.out.println("Target Method's parameters" + 
                            Arrays.toString(joinPoint.getArgs()));
    }

    // intercept after target method returns val without throwing exception
    @AfterReturning( pointcut="...", returning="returnVal")
    public void logReturnVal(Object returnVal) {
        System.out.println("Target method return value：" + returnVal));
    }

    // Only intercept the exception thrown by target method
    @AfterThrowing("ric.study.demo.aop.SystemArchitecture.dataAccessOperation()")
    public void doRecoveryActions() {
        // ... 
    }

    // Intercept Both Exception/Return value 
    // from target method
    @After("ric.study.demo.aop.SystemArchitecture.dataAccessOperation()")
    public void doReleaseLock() {
        // after method is kinda like finally in try-catch-finally
    }

    // around =  @Before + @AfterReturning 
    @Around("ric.study.demo.aop.SystemArchitecture.businessService()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        /** ********************************************
          * Code Block 
          * Before target method
          * ------------------------------------------ */
        Object retVal = pjp.proceed();
        /** -----------------------------------------------------
          * Code Block 
          * after target method
          * -----------------------------------------------------*/
        return retVal;
    }
}
```
## Advise uses Point-Cut arguments

```java
// package xxx.com.PointCutConfig;
@Pointcut("execution(* ric.study.demo.dao.*.*(..))")
public void daoLayer() {}

// package xxx.com.AdviseConfig
@Before("xxx.com.PointCutConfig.daoLayer()")
    public void doBeforedaoLayer(){
        //....
}
```

Instead  we can configure point cut in advise
```java 
// without configuring Point cut
// with point-cut argument
@Before("execution(* ric.study.demo.dao.*.*(..))")
public void doAccessCheck() {
    // ...
}
```


## @annotation Method

- [source code](https://www.baeldung.com/spring-aop-annotation)


Custom Annotation
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
    //..
}
```

Advises
```java
@Aspect
@Component
public class adviseConfig {

    //..

    //                    annotation name
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

            long start = System.currentTimeMillis();
            
            //-------------- end of before ------------------------
            
            /* Target Method is proceeding       */ 
            Object proceed = joinPoint.proceed();
            /*-----------------------------------*/

            // ------------- start of after ------------------------
            long executionTime = System.currentTimeMillis() - start;
            
            System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
            
            return proceed;
    }

    // ...
}
```

Somewhere `@LogExecutionTime` annotation is used
```java
@LogExecutionTime
public void serve() throws InterruptedException {
    Thread.sleep(2000);
}

// console : 
// void org.baeldung.Service.serve() executed in 2030ms
```
