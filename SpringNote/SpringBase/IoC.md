# Spring Bean and Ioc

- [Spring Bean and Ioc](#spring-bean-and-ioc)
  - [Inversion of Control (IoC)](#inversion-of-control-ioc)
  - [Initialize for Controller Class](#initialize-for-controller-class)
  - [Spring Bean's Scopes](#spring-beans-scopes)
## Inversion of Control (IoC)

![](https://i.imgur.com/qj21dCd.png)
- A principle in software engineering which transfers the control of objects or portions of a program to a **container or framework**.

## Initialize for Controller Class

![](https://i.imgur.com/8mbxEiD.png)
- Any object in the Spring framework that we initialize through Spring container is called **Spring Bean**.

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

