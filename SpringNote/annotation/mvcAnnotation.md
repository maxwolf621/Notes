# Spring MVC HTTP

- [Spring MVC HTTP](#spring-mvc-http)
  - [@EnableWebMvc](#enablewebmvc)
  - [@ResponseBody](#responsebody)
  - [@RequestBody](#requestbody)
  - [@RestController](#restcontroller)
  - [@RequestMapping(value, param, headers, method, consumes, produces)](#requestmappingvalue-param-headers-method-consumes-produces)
      - [`@RequestMapping( ..., headers = {"key=val" , "key=val2"}, ...)`](#requestmapping--headers--keyval--keyval2-)
      - [`@RequestMapping( ..., produces = { "..." , "...." }, ...)`](#requestmapping--produces-------)
      - [`@RequestMapping( ... , params = { "x", "y" , ...}) , ...)`](#requestmapping---params---x-y----)
      - [`@RequestMapping(value = { "xx/yy/freak", "xx/yy/far"}), ...)`](#requestmappingvalue---xxyyfreak-xxyyfar-)
      - [`@RequestMapping(  ..,  method = { RequestMethod.PUT, RequestMethod.POST} , ... )`](#requestmapping----method---requestmethodput-requestmethodpost---)
  - [@PathVariable](#pathvariable)


```java
@RestController
@RequestMapping("users")
public class UserController {
  @Autowired
  UserService userService;

  @GetMapping("/status/check")
  public String status(){
      //..
  }

  @GetMapping("/{id}")
  public String getUser(@PathVariable String id){
    // ...
  }

  @PostMapping
  public String createUser(@RequestBody UserDetailsRequestModel requestUserDetails){
    return "HTTP POST was called";
  }

  @DeleteMapping("/{userId}")
  public String deleteUser(@PathVariable String userId){
    return "HTTP DELETE was called";
  }

  @PutMapping("/{userId}")
  public String updateUser(
    @PathVariable String userId, 
    @RequestBody UserDetailsRequestModel requestUserDetails){
      //..
  }
}
```


## @EnableWebMvc
Enable WebMVC configuration (e.g. ViewResolver, Message Converter)

## @ResponseBody 
Spring bind a method's return **value to the HTTP response body**  

(REST api) **It tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object**

## @RequestBody

Allow Request parameter in the Request body

## @RestController

It equals `@ResponseBody` + `@Controller`

## @RequestMapping(value, param, headers, method, consumes, produces)

For Class, Interface and Method annotation

The annotation specifies a method in the controller that should be responsible for serving the HTTP request to the given path.

Spring will work the implementation details of how it's done. 

You simply specify the path value on the annotation and Spring will route the requests into the correct action methods.

- value : Specify request's URL，URL可以是`URI Template`模式  
- method  : `GET`、`POST`、`PUT`、`DELETE` .. etc

#### `@RequestMapping( ..., headers = {"key=val" , "key=val2"}, ...)`
```bash
curl -i -H "key1:val1" -H "key2:val2" http://localhost:8080/spring-rest/ex/foos
```

#### `@RequestMapping( ..., produces = { "..." , "...." }, ...)`

Starting From Spring 3.1

`consumes` : Invocation this method if HTTP request's Content-Type is e.g. `application/json`,`text/html`     

`produces` : **指定response's returned Type**，Request's header中的`accept`有包含response's returned type才會返回   
```java
@RequestMapping(
 ...,
 produces = {"application/json", ... }
 ...
)
// same as
@RequestMapping(
  ...,
  header = {"Accept=application/json", ...}
  ....
)
```

For example , return the correct data representation based on the Accepts header supplied in the request.
```java
@GetMapping(value = "foos/duplicate", produces = MediaType.APPLICATION_XML_VALUE)
public String duplicateXml() {
    return "<message>Duplicate</message>";
}
    
@GetMapping(value = "foos/duplicate", produces = MediaType.APPLICATION_JSON_VALUE)
public String duplicateJson() {
    return "{\"message\":\"Duplicate\"}";
}
```

#### `@RequestMapping( ... , params = { "x", "y" , ...}) , ...)`

Narrowing the request mapping
```java
                                           '      '
http://localhost:8080/spring-rest/ex/bars?id=100&second=something
```
```java
@RequestMapping(
  value = "/ex/bars", // mapping http request path 
  params = { "id", "second" },  // params 
  method = GET) // get request
@ResponseBody // to json body
public String getBarBySimplePathWithExplicitRequestParams(@RequestParam("id") long id) {
    return "Narrow Get a specific Bar with id=" + id;
}
```

#### `@RequestMapping(value = { "xx/yy/freak", "xx/yy/far"}), ...)`

The method accepts multiple mappings, not just a single one:
```bash
curl -i http://localhost:8080/spring-rest/xx/yy/freak
curl -i http://localhost:8080/spring-rest/xx/yy/far
```

#### `@RequestMapping(  ..,  method = { RequestMethod.PUT, RequestMethod.POST} , ... )`

```bash
curl -i -X POST http://localhost:8080/....
curl -i -X PUT http://localhost:8080/....
```

## @PathVariable
```java
@RequestMapping(value = "/ex/foos/{id}/{number}", method = GET)
@ResponseBody // to Json body
public String getFoosBySimplePathWithPathVariable(@PathVariable("id") long id, 
                                                  @pathVariable("number") int number){
    return "Get a specific Foo with id=" + id + "with number" + number;
}
```
