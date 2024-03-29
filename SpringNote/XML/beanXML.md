# Bean XML configuration

- [Bean XML configuration](#bean-xml-configuration)
  - [Bean and XML-file](#bean-and-xml-file)
    - [Bean XML Constructor Injection](#bean-xml-constructor-injection)
    - [Bean XML Setter Injection](#bean-xml-setter-injection)
    - [Spring ApplicationContext retrieve Dependencies](#spring-applicationcontext-retrieve-dependencies)

References   
[Code Java - Spring Dependency Injection Example with XML Configuration](https://www.codejava.net/frameworks/spring/spring-dependency-injection-example-with-xml-configuration)    
[Spring - Bean Definition](https://www.tutorialspoint.com/spring/spring_bean_definition.htm)


- `<bean>` tags to declare the dependencies in a XML file
- `ClassPathXmlApplicationContext` class to load the configuration from the XML file
    - `ClassPathXmlApplicationContext#getBean()` retrieves a bean instance from the container.

## Bean XML Constructor Injection 

Syntax
```xml
<!--
  Register Bean 
-->
<bean id="[BEAN_NAME]" class="[PACKAGE_PATH]">
    <constructor-arg ref="bean_id">     
</bean>

<!-- 
    For Example
-->
  
 <!--
  Register bean service1 ref to com.example.ServerImplementation
-->
<bean id="service1" class="com.example.ServerImplementation">
  
<!-- 
    Inject bean named service1 for bean named ClientImplementation
-->
<bean id="ClientImplementation" class="com.example.ClientImplementation">
    <constructor-arg ref="service1" />
</bean>
```

```java
interface Client{
    public void doSomething()
}
interface Server{
    public void connect()
}
public class ClientImplementation implements Client{
    Server server;

    ClientImplementation(Server server){
        this.server = server;
    }

    // ...
}
public class ServerImplementation implements Server{
    //...
}
```

## Bean XML Setter Injection

```xml
<bean id="[BEAN_NAME]" class="PACKAGE_PATH">
    <property name="[PROPERTY_NAME]" ref="[BEAN_ID_NAME]">
</bean>

<!-- 
    For Example 
-->
<bean id="ClientImplementation" class="com.example.ClientImplementation">
    <property name="service" ref="service1" />
</bean>
```

## Spring ApplicationContext retrieve Dependencies

Using `ApplicationContext#getBean()` is used to retrieve the reference of an instance managed by Spring's IoC Container.

```java
ApplicationContext appContext = new ClassPathApplicationContext("classpath:applicationContext.xml");
ObjectType ob = (ObjectType) appContext.getBean("Bean_ID_Name");

// For example 
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDependencyExample {
    public static void main(String[] args) {
        ApplicationContext appContext
            = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        Client client = (Client) appContext.getBean("");
        client.doSomething();
    }
 
}
```
