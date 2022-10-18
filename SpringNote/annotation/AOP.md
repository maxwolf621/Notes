# AOP
- [AOP](#aop)
  - [Reference](#reference)
  - [Enable Aspect-JS Annotation](#enable-aspect-js-annotation)
    - [@EnableAspectJAutoProxy class/interface](#enableaspectjautoproxy-classinterface)
    - [@Aspect class/interface](#aspect-classinterface)
  - [Pointcut designators](#pointcut-designators)
    - [execution, args, within](#execution-args-within)
      - [within vs execution](#within-vs-execution)
      - [this(proxy) target(object)](#thisproxy-targetobject)
      - [@args](#args)
      - [@target(A_CLASS_WITH_ANNOTATION)](#targeta_class_with_annotation)
      - [@annotation(METHOD_WITH_ANNOTATION)](#annotationmethod_with_annotation)
      - [@within(Class With Specific Annotation)](#withinclass-with-specific-annotation)
      - [bean](#bean)
    - [Combining the pointcut expression](#combining-the-pointcut-expression)
    - [Sharing common pointcut definitions](#sharing-common-pointcut-definitions)
  - [Advice Configuration](#advice-configuration)
    - [Parameter In Advices](#parameter-in-advices)
      - [@Around methodX](#around-methodx)
      - [@After(YY) methodX](#afteryy-methodx)
      - [@Before(YY) methodX](#beforeyy-methodx)
      - [@AfterReturning(YY) methodX](#afterreturningyy-methodx)
      - [@AfterThrowing(YY) methodX](#afterthrowingyy-methodx)
  - [In-place pointcut](#in-place-pointcut)
  - [Passing parameters to advice from pointcut](#passing-parameters-to-advice-from-pointcut)
  - [Determining argument names](#determining-argument-names)
  - [Introductions](#introductions)
  - [Retried](#retried)

## Reference
- [Spring AOP](https://segmentfault.com/a/1190000020904086) 
- [PointCut表示式](https://openhome.cc/Gossip/Spring/Pointcut.html)
- [PointCut designators](https://tedblob.com/pointcut-designators-of-spring-aop/)  
- [[Day 27] 遠征 Kotlin × Spring Boot 介紹 Spring AOP 機制](https://ithelp.ithome.com.tw/articles/10249999)
- [Spring AOP白話文，淺談Spring AOP的學習分享](https://medium.com/appxtech/spring-aop%E7%99%BD%E8%A9%B1%E6%96%87-%E6%B7%BA%E8%AB%87spring-aop%E7%9A%84%E5%AD%B8%E7%BF%92%E5%88%86%E4%BA%AB-1985489d008)  
- [Expression](https://www.eclipse.org/aspectj/doc/released/progguide/semantics-pointcuts.html)

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
## Pointcut designators

### execution, args, within

|    *   |   `.*(..)`   | `..*` |
| ------ | -------------| --- |
|  any   | any methods with `>=0` parameters | sub-package

Expression
```java
// ? means optional
execution(modifiers-pattern? 
          return-type-pattern 
          declaring-type-pattern? 
          Method-name-pattern(param-pattern)
          throws-pattern?)

// any public method with any
// return types having `>= 0` parameters
execution(public * *(..))

// any method with 
// name beginning with `accountBy` 
// having `>= 0`  parameters
execution(* accountBy*(..))

// any method 
// with name beginning with accountBy
// having String type parameter
// e.g. accountByXyc or accountByVoo
execution(* accountBy*(String))

// the first parameter can be of any type
// the second parameter must be `String` type 
execution(* get*(*,String))

// first parameter should be String type
// rest parameters can be of any types
execution(* get*(String, ..))

// the execution of any method defined 
// by the AccountService interface:
execution(* com.xyz.service.AccountService.*(..)) 

// the execution of any method 
// defined in the service package:
execution(* com.xyz.service.*.*(..))

// the execution of any method 
// defined in the service package or a sub-package:
execution(* com.xyz.service..*.*(..))
```

any join point (method execution only in Spring AOP) which takes a single parameter, and where the argument passed at runtime is Serializable:
```java
// matches if the ARGUMENT passed at runtime is Serializable
args(java.io.Serializable)

// the execution matches if the target method signature
// declares a single PARAMETER of type Serializable
execution(* *(java.io.Serializable))
```


any join point (method execution only in Spring AOP) within the service package(`.*`):
```java
within(com.xyz.service.*)
```
any join point (method execution only in Spring AOP) within the service package or a sub-package(`..*`):
```java
within(com.xyz.service..*)
```

#### within vs execution
```java
within(cc.openhome.model.AccountDAO) 
//equals 
execution(* cc.openhome.model.AccountDAO.*(..))

within(cc.openhome.model.*) 
//equals 
execution(* cc.openhome.model.*.*(..))

within(cc.openhome.model..*) 
// equals
execution(* cc.openhome.model.service..*.*(..))
```

```java
// Pointcut this method with * testExecution(..)
// *  : any return type
// .. : 0 or many parameters
@Pointcut("execution(* testExecution(..))") method

// pointcut this method 
// within ric.study.demo.aop.svc..*
// ..* : sub-packages
@Pointcut("within(ric.study.demo.aop.svc..*)") method

// pointcut this method 
// with ric.study.demo.aop.HaveAop
@Pointcut("@annotation(ric.study.demo.aop.HaveAop)") method 

// pointcut this method with specific bean name 
@Pointcut("bean(testController)") method 
```

#### this(proxy) target(object)

**Spring AOP will use the JDK-based proxy,** and we should use the target PCD because the proxied object will be an instance of the Proxy class and implement the `B` interface:
```java
public class IMPLEMENTATION implements INTERFACE {
  //....
}
```

```java
@Pointcut("target(com.xxx.pointcutadvice.dao.INTERFACE)")
```

On the other hand, if `IMPLEMENTATION` doesn't implement any interface, or the `proxyTargetClass` property is set to `true`, then the proxied object will be a SUBCLASS of `IMPLEMENTATION` and we can use the this PCD:
```java
@Pointcut("this(com.xxx.pointcutadvice.dao.IMPLEMENTATION)")
```


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

    // Target : All implementation and interface of CountryRepository
    @Before("target(com.xxx.repo.CountryRepository)")
    public void beforeTargetCountriesRepository() {
        //...
    }
 
    @After("this(com.xxx.repo.CountryRepository)")
    public void afterCountriesRepository() {
        //...
    }
}
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

// arg countryCode has the @Country annotation
public String getCountryCode(CountryCode countryCode) {
    return countryCode;
}

// any join point (method execution only in Spring AOP) 
// which takes a single parameter, 
// and where the argument passed at runtime 
// has the @Country annotation:
@Pointcut("@args(com.xxxx.annotations.Country)")
public void countryPointcut() {}

// giving pointcut advise
@After("countryPointcut()")
public void afterCountryAdvice(JoinPoint joinPoint) {
    System.out.println("After - countryPointcut" + 
                        joinPoint.getSignature().getName());
}
```

#### @target(A_CLASS_WITH_ANNOTATION)

It limits matching to join points where the **TARGET CLASS** of the executing object has an ANNOTATION of the given type
```java
// Class with Annotation @Repository
@Pointcut("@target(org.springframework.stereotype.Repository)")
```
#### @annotation(METHOD_WITH_ANNOTATION)

- [source code](https://www.baeldung.com/spring-aop-annotation)

It limits matching to method executions where the **TARGET METHOD** has the given annotation.

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
    //..
}

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
// void org.pttClone.Service.serve() executed in 2030ms
```


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
@Pointcut("@within(com.xxx.annotations.Secured)") 
public void securedClassPointcut() {}
```


#### bean

any join point (method execution only in Spring AOP) on a Spring `@bean` named `tradeService`:
```java
bean(tradeService)
```
any join point (method execution only in Spring AOP) on Spring `@beans` having names that match the wildcard expression `*Service`:
```java
bean(*Service)
```


### Combining the pointcut expression

Pointcut expressions can be combined using `&&`, `||` and `!`. 

```java
@Pointcut("execution(public * *(..))")
 private void anyPublicOperation() {}
 
@Pointcut("within(com.xyz.someapp.trading..*)")
private void inTrading() {}
```

`tradingOperation` matches if a method execution represents any public method in the trading module
```java
@Pointcut("anyPublicOperation() && inTrading()")
private void tradingOperation() {}
```

### Sharing common pointcut definitions

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

## Advice Configuration

Giving Advises to pointCuts

```java
Advices = {@Around, @Before, @After, @AfterReturning, @AfterThrowing}
```
### Parameter In Advices

`JoinPoint` : `@Before, @After, @AfterThrowing, and @AfterReturning`


`ProceedingJoinPoint` : is an extension of the JoinPoint that exposes the additional `proceed()` method

#### @Around methodX
around advice means we are adding extra code both `@before` and `@after` target method execution. 

#### @After(YY) methodX
通知(advice)方法methodX會在target method返回或拋出異常後呼叫, 類似例外中的finally
- It is typically used for releasing resources

#### @Before(YY) methodX
通知(advice)方法methodX會在target method呼叫之前被呼叫
#### @AfterReturning(YY) methodX
通知(advice)方法methodX會在target method `com.xxx.applicationName.SystemArchitecture.dataAccessOperation()`返回後呼叫

```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;

@Aspect
public class AfterReturningExample {

  @AfterReturning(
    pointcut="com.xxx.applicationName.SystemArchitecture.dataAccessOperation()",
    // return value of Join Cut
    returning="retVal")
  public void doAccessCheck(Object retVal) {
    // ...
  }
  
}
```
- The name used in the returning attribute must correspond to the name of a parameter in the advice method.
#### @AfterThrowing(YY) methodX
通知(advice)方法(methodX)會在`com.xyz.myapp.SystemArchitecture.dataAccessOperation()`拋出異常後被呼叫
```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterThrowing;

@Aspect
public class AfterThrowingExample {

  @AfterThrowing(
    pointcut="com.xyz.myapp.SystemArchitecture.dataAccessOperation()",
    throwing="ex")
  public void doRecoveryActions(DataAccessException ex) {
    // ...
  }

}
```

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
## In-place pointcut 

```java
package xxx.com.PointCutConfig;
@Pointcut("execution(* ric.study.demo.dao.*.*(..))")
public void daoLayer() {}

package xxx.com.AdviseConfig;
@Before("xxx.com.PointCutConfig.daoLayer()")
    public void doBeforedaoLayer(){
        //....
}
```

or using an in-place pointcut expression
```java 
// without configuring Point cut
// with point-cut argument
@Before("execution(* ric.study.demo.dao.*.*(..))")
public void doAccessCheck() {
    // ...
}
```

## Passing parameters to advice from pointcut

Suppose you want to advise the execution of dao operations that take an Account object as the first parameter, and you need access to the account in the advice body :
```typescript 
@Pointcut("execution(* com.xyz.someapp.dao.*.*(..))")
public void dataAccessOperation() {}

@Before("com.xyz.myapp.SystemArchitecture.dataAccessOperation() &&" + 
        "args(account,..)")
public void validateAccount(Account account) {
  // ...
}


@Pointcut("com.xyz.myapp.SystemArchitecture.dataAccessOperation() &&" + 
          "args(account,..)")
private void accountDataAccessOperation(Account account) {}

@Before("accountDataAccessOperation(account)")
public void validateAccount(Account account) {
  // ...
}
```

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auditable {
	AuditCode value();
}

// pointcut : @auditable anyPublicMethod()
// extract the AuditCode value
@Before("com.xyz.lib.Pointcuts.anyPublicMethod() && " + 
        "@annotation(auditable)")
public void audit(Auditable auditable) {
  AuditCode code = auditable.value();
  // ...
}
```

## Determining argument names
If the parameter names have been specified by the user explicitly, then the specified parameter names are used
```java
@Before(
   value="com.xyz.lib.Pointcuts.anyPublicMethod() && target(bean) && @annotation(auditable)",
   argNames="bean,auditable")
public void audit(JoinPoint jp,Object bean, Auditable auditable) {
  AuditCode code = auditable.value();
  // ... use code and bean
}
```
- If the first parameter is of the `JoinPoint`, `ProceedingJoinPoint`, or `JoinPoint.StaticPart` type, you may leave out the name of the parameter from the value of the `argNames` attribute


```java
@Around("execution(List<Account> find*(..)) &&" +
        "com.xyz.myapp.SystemArchitecture.inDataAccessLayer() && " +
        "args(accountHolderNamePattern)")		
public Object preProcessQueryPattern(ProceedingJoinPoint pjp, String accountHolderNamePattern)
throws Throwable {
  String newPattern = preProcess(accountHolderNamePattern);
  return pjp.proceed(new Object[] {newPattern});
}        
```


## Introductions

- [Source Code](https://www.cnblogs.com/lcngu/p/6346777.html)

Introductions (known as inter-type declarations in AspectJ) **enable an aspect to declare that advised objects implement a given interface, and to provide an implementation of that interface on behalf of those objects.**

- An introduction is made using the `@DeclareParents` annotation. 

For example, given an interface `Verifier`, and an implementation of that interface `VerifierImpl`, the following aspect declares that all implementors of service interfaces also implement the `Verifier` interface.
```java
@Aspect
public class UsageTracking {
    // All the service types objects are now also
    // implementing VerifierImpl
    @DeclareParents(value="com.xzy.myapp.service.*+",
                  defaultImpl=VerifierImpl.class)
    public static Verifier mixin;
  
    @Before("com.xyz.myapp.SystemArchitecture.businessService() &&" +
            "this(Verifier)")
    public void recordUsage(Verifier verifier) {
        Verifier.incrementUseCount();
  }
}
```
- Note that in the before advice of the above example, service beans can be directly used as implementations of the `Verifier` interface. 

To verify the Usr POJO 
```java
Verifier v = (Verifier) context.getBean("myService");
if(v.validate(user)){
    // ...
}
```

## Retried

The execution of business services can sometimes fail due to concurrency issues (for example, deadlock loser). 

If the operation is retried, it is quite likely to succeed next time round. 

For business services where it is appropriate to retry in such conditions (idempotent operations that don't need to go back to the user for conflict resolution), we'd like to transparently retry the operation to avoid the client seeing a `PessimisticLockingFailureException`. 

This is a requirement that clearly cuts across multiple services in the service layer, and hence is ideal for implementing via an aspect.

Because we want to retry the operation, we will need to use around advice so that we can call proceed multiple times : 
```java 
@Aspect
public class ConcurrentOperationExecutor implements Ordered {
    private static final int DEFAULT_MAX_RETRIES = 2;

    private int maxRetries = DEFAULT_MAX_RETRIES;

    // Higher Order than transaction advise
    private int order = 1;

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }
   
    public int getOrder() {
        return this.order;
    }
   
    public void setOrder(int order) {
        this.order = order;
    }
   
    // applying the retry logic to all businessService()s.
    @Around("com.xyz.myapp.SystemArchitecture.businessService()")
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable { 
        
        int numAttempts = 0;
        
        PessimisticLockingFailureException lockFailureException;
        do {
            numAttempts++;
            try { 
                return pjp.proceed();
            }catch(PessimisticLockingFailureException ex){
                lockFailureException = ex;
            }
        }
        while(numAttempts <= this.maxRetries);
        throw lockFailureException;
    }
}
```
- Note that the aspect implements the Ordered interface so we can set the precedence of the aspect higher than the transaction advice (we want a fresh transaction each time we retry).

the maxRetries and order properties will both be configured by Spring.  
The corresponding Spring configuration is:
```html
<aop:aspectj-autoproxy/>
<bean id="concurrentOperationExecutor"
  class="com.xyz.myapp.service.impl.ConcurrentOperationExecutor">
     <property name="maxRetries" value="3"/>
     <property name="order" value="100"/>  
</bean>
```
To refine the aspect so that it only retries idempotent operations, we might define an Idempotent annotation:
```java
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
  // marker annotation
}
```
and use the annotation to annotate the implementation of service operations.

The change to the aspect to only retry idempotent operations simply involves refining the pointcut expression so that only `@Idempotent` operations match:
```java
@Around("com.xyz.myapp.SystemArchitecture.businessService() && " + 
        "@annotation(com.xyz.myapp.service.Idempotent)")
public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable { 
  // ...	
}
```