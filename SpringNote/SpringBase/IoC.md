# Spring Bean and Ioc

- [Spring Bean and Ioc](#spring-bean-and-ioc)
  - [Inversion of Control (IoC)](#inversion-of-control-ioc)
  - [Spring Bean's Scopes](#spring-beans-scopes)
## Inversion of Control (IoC)

![](https://i.imgur.com/qj21dCd.png)
- A principle in software engineering which transfers the control of objects or portions of a program to a **container or framework**.

- [Ioc(控制反轉)/DI(依賴注入)](https://welson327.gitbooks.io/java-spring/content/spring/ioc_di.html)


Spring的設計目標之一是為了解隅，**利用依賴抽象而非依賴實例的方式**，因此設計了依賴注入(DI)，透過Dependency Injection實現IoC   

**控制反轉就是在軟體工程中decouple的思維 把物件的創建這個責任丟給IOC容器當物件運行的時候再讓容器將具體的物件動態的注入到需要使用的類別裡**
IoC : `new`的控制權從Programmer反轉(交給)Spring了   
```java
class Zoo {
    @Resource(name="tiger")
    Animal tiger;

    @Resource(name="zebra")
    Animal zebra;

    public Zoo() {
        tiger.eat();
        zebra.eat();
    }
}
interface Animal {
    public void eat(); 
}
@Component("tiger")
class Tiger implements Animal {
    public void eat() {
        System.out.println("老虎吃肉");
    }
}
@Component("zebra")
class Zebra implements Animal {
    public void eat() {
        System.out.println("斑馬吃草");
    }
}
```

## Spring Bean's Scopes

Singleton 
> Only one instance of the bean will be created for each container

Prototype 
> A new bean instance will be created **every time the bean is requested**

Request (HTTP)
> Same as prototype but for HTTP application 

Session (HTTP)
> A new bean will be created for each HTTP session by the container

Global-session
> used to create global session beans for PROTECT applications

