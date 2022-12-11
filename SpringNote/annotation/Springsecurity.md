# Security Annotations

- [Security Annotations](#security-annotations)
  - [`@EnableWebSecurityConfig`](#enablewebsecurityconfig)
  - [@EnableWebSecurity](#enablewebsecurity)
  - [@EnableGlobalMethodSecurity class](#enableglobalmethodsecurity-class)
  - [@DenyAll/@PermitAll Method](#denyallpermitall-method)
  - [@Secured method](#secured-method)
  - [@RolesAllowed method](#rolesallowed-method)
  - [Post/Pre-Authorize](#postpre-authorize)
    - [@PreAuthorize Method](#preauthorize-method)
    - [:star: @PostAuthorize Method](#star-postauthorize-method)
  - [pre/post-filter](#prepost-filter)
  - [@PreFilter Method](#prefilter-method)
  - [@PostFilter Method](#postfilter-method)

References
- [Spring Security Methods](https://www.baeldung.com/spring-security-method-security)
- [Spring Security – @PreFilter and @PostFilter](https://www.baeldung.com/spring-security-prefilter-postfilter)
- [Intro to Spring Security Expressions](https://www.baeldung.com/spring-security-expressions)
- [Spring Expression-based](https://docs.spring.io/spring-security/reference/servlet/authorization/expression-based.html#el-common-built-in)
- [spring-expression-language](https://www.baeldung.com/spring-expression-language)

## `@EnableWebSecurityConfig` 

It should be annotated with `@Configuration`. 

This Annotation will be used for class related with `WebSecurityConfigurer`

## @EnableWebSecurity

`@EnableWebSecurity` is used for class extending `WebSecurityConfigurerAdapter`

```java
@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter{
    //...
}

// or 
@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    ...
}
```

## @EnableGlobalMethodSecurity class

The Annotation is used for the class extending `GlobalMethodSecurityConfiguration`
```java 
@Configuration
@EnableGlobalMethodSecurity(
  prePostEnabled = true, 
  securedEnabled = true, 
  jsr250Enabled = true)
public class MethodSecurityConfig 
  extends GlobalMethodSecurityConfiguration {
}
```
The `prePostEnabled` property enables Spring Security pre/post annotations.
The `securedEnabled` property determines if the `@Secured` annotation should be enabled.
The `jsr250Enabled` property allows us to use the `@RoleAllowed` annotation.

## @DenyAll/@PermitAll Method

## @Secured method

The `@Secured` annotation is used to specify a list of roles on a method
- **it doesn't support Spring Expression Language (SpEL).**
```java
// Allow ROLE_VIEW and ROLE_EDITOR to call this method
@Secured({ "ROLE_VIEWER", "ROLE_EDITOR" })
public boolean isValidUsername(String username) {
    return userRoleRepository.isValidUsername(username);
}
```

## @RolesAllowed method

The `@RolesAllowed` annotation is the JSR-250’s equivalent annotation of the `@Secured` annotation.

```java
@RolesAllowed({ "ROLE_VIEWER", "ROLE_EDITOR" })
public boolean isValidUsername2(String username) {
    //...
}
```

## Post/Pre-Authorize

Both `@PreAuthorize`e and` @PostAuthorize` annotations provide expression-based access control

The `@PreAuthorize` annotation checks the given expression before entering the method, whereas the `@PostAuthorize` annotation verifies it after the execution of the method and could alter the result.

### @PreAuthorize Method

`@PreAuthorize` has same function as `@secured`

```java
// before getMyRoles is executed
@PreAuthorize("#username == authentication.principal.username")
public String getMyRoles(String username) {
    //...
}
```
- Here a user can invoke the `getMyRoles` method only if the value of the argument username is the same as current principal's username.
### :star: @PostAuthorize Method

It's worth noting that `@PreAuthorize` expressions can be replaced by `@PostAuthorize` ones. **However the `@PostAuthorize` annotation provides the ability to access the method result.**

```java
@PostAuthorize("#username == authentication.principal.username")
public String getMyRoles2(String username) {
    //...
}
```


```java
@PostAuthorize
  ("returnObject.username == authentication.principal.nickName")
public CustomUser loadUserDetail(String username) {
    return userRoleRepository.loadUserByUserName(username);
}
```
- the `loadUserDetail` method would only execute successfully if the username of the returned CustomUser is equal to the current authentication principal's nickname.

## pre/post-filter

Spring Security provides the `@PreFilter` annotation to **filter a COLLECTION argument before executing the method**

## @PreFilter Method

We use the name `filterObject` to represent the current object in the collection.

However, if the method has more than one argument that is a collection type, **we need to use the `filterTarget` property to specify which argument we want to filter:**
```java
@PreFilter
  (value = "filterObject != authentication.principal.username",
  filterTarget = "usernames")
public String joinUsernamesAndRoles(
  List<String> usernames, List<String> roles) {
 
    return usernames.stream().collect(Collectors.joining(";")) 
      + ":" + roles.stream().collect(Collectors.joining(";"));
}
```

- **`@PreFilter` is really cool and easy to use, but it can be inefficient when dealing with very large lists since the fetching operation will retrieve all the data and apply the filter afterward.**


## @PostFilter Method
- Often used to get data (via repo) from database
- Filter the returned collection of a method by using the `@PostFilter` annotation

```java
// filterObject is return value of
// userRoleRepository.getAllUsernames
@PostFilter("filterObject != authentication.principal.username")
public List<String> getAllUsernamesExceptCurrent() {

    return userRoleRepository.getAllUsernames();
}
```