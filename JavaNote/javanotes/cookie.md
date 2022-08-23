[Same Cookie Name problem](https://stackoverflow.com/questions/3193163/is-calling-httpservletresponse-addcookie-with-the-same-cookie-name-safe)   

Tomcat will handle like this 
```diff
Set-Cookie: foo=bar
Set-Cookie: foo=42

Here foo gets overwritten. Reading the cookie later gives you 42.
```


[Spring cookie](https://beginnersbook.com/2013/05/servlet-cookies/)    
[Cookie Methods](https://www.geeksforgeeks.org/javax-servlet-http-cookie-class-java/)     
[Vorstellung von cookies](https://ithelp.ithome.com.tw/articles/10217955)    

# Cookie 

Cookie,which is applied for HTTP stateless protocol, carries the STATE information of the client for Server    
- e.g. my password, my account_name, my name ... etc ...

**The disadvantage of the cookie is that it is stored in client's browser or device which means we can easily fake the cookie and also the cookie contains bulk data will affect the efficiency.**
Instead we use [session](Session.md) to deal with these problems 

- [Difference btw Session, Token, JWT, Cookie](https://blog.yyisyou.tw/5d272c64/)    

## How cookies functions
![](https://media.geeksforgeeks.org/wp-content/uploads/cookies.jpg)    
![image](https://user-images.githubusercontent.com/68631186/122122215-18e7ee00-ce5f-11eb-8168-2f75bc710740.png)    

For example    
![image](https://user-images.githubusercontent.com/68631186/122121919-bee72880-ce5e-11eb-8bef-13f04ef9300a.png)   

## Types of Cookie
- Non-persistent cookie (**Browser Cache**)   
  > It is valid for SINGLE session only.     
  > It is removed each time when user closes the browser which means the cookies do not have expiration time(It lives in the browser memory)     
- Persistent cookie (**Stored in user Hard Driver**)   
  > It is valid for MULTIPLE sessions.   
  > It is not removed each time when user closes the browser.    
  > **It is removed only if user LOGOUT or SIGN OUT.** or **get destroyed based on the expiry time**    

## Advantage of Cookies
- **Simplest technique of maintaining the state.**  
- Cookies are **maintained at client side.**  

## Disadvantage of Cookies
- It will not work if cookie is disabled (e.g Forbidded By user ) from the Browser.  
- **Only String type paramater (textual information) can be set in Cookie object.**  

## Form and Attributes

Each Cookie is a name-value pair (e.g. `name : value`)
- name-value pair depicts the actual information stored within the cookie. 
**Neither the name nor the value should contain white space or any of the following characters: `[ ] ( ) = , ” / ? @ : ;`**  

A cookie that contain the `seesion_id` from servlet, for example 
```json
Set-Cookie : session-id = 187-4969589-3049309
```

### Domain of the Cookie
[Cookies Domain](https://stackoverflow.com/questions/1062963/how-do-browser-cookie-domains-work)   

By default, a cookie applies to the server it came from.  
- **If a cookie is originally set by `www.foo.example.com`, the browser will only send the cookie back to `www.foo.example.com`.** However, **a site can also indicate that a cookie applies within an _ENTIRE SUBDOMAIN_, not just at the original server.**   


- The origin domain of a cookie is the domain of the originating request.  
- **The cookie's domain must not be a TLD, a public suffix, or a parent of a public suffix.**  
- **If the origin domain is an IP, the cookie's domain attribute must not be set.**  
  - If a cookie's domain attribute is not set, the cookie is only applicable to its origin domain.   
- If a cookie's domain attribute is set,   
  > the cookie is applicable to that **domain and all its subdomains**  
  > the cookie's domain must be the same as, or a parent of, the origin domain  
  
[Top-Level Domain TLD](https://raventools.com/marketing-glossary/top-level-domain/) : A Top-Level Domain is the suffix tied to a website, such as as `com`、 `net`、`org` 、`edu`、`uk`、 `co.uk`、 `blogspot.com` or `compute.amazonaws.com` ... etc ...  
   
### Domain 跨域訪問

For example, the request set a user cookie as the following  
```json
Set-Cookie: user = geek ;Domain = x.y.z.com
```
- We can set a cookie domain to ITSELF or PARENTS `x.y.z.com`, `y.z.com`, `z.com`. But not `com`, which is a public suffix(TLD)

Another example 
```diff
a cookie with domain `y.z.com` is applicable to
- y.z.com 
- x.y.z.com 
- a.x.y.z.com 
```

### Path of the Cookie 

The **scope** of each cookie is limited to **a set of paths**, controlled by the Path attribute.   

**If the server omits the Path attribute, the user agent will use the directory of the request-uri's path component as the default value.**
```json
Set-Cookie: user = geek; Path =/ restricted
```
- `asp.net`默認`/`為root 

同一個服務器(Server)上有以下Directories, For example
```diff
+ /test/
- /test/cd/
- /test/dd/
```
現設一個cookie1的path為`/test/`，cookie2的path為`/test/cd/`， 那麽`test`下的所有頁面(`../cd/`,`../dd/`,`../abcd/` ...)都可以訪問(ACCESS) cookie1，而`/test/`和`/test/dd/`的子頁面不能ACESS cookie2  
> 這是因為cookie設定path的SCOPE讓其能在特定path下的頁面訪問

### Expiration 

The browser should remove the cookie from its cache after that date (of the cookie) has passed. 

```json
                         expires = 星期,日期         時間      IST
Set-Cookie: user = geek; expires = Wed, 21-Feb-2017 15:23:00 IST
```

###  Max-Age (Seconds) 
**This attribute sets the cookie to expire after a certain number of seconds** have passed instead of at a specific moment.  

For instance, this cookie expires one hour (`3,600` seconds) after it’s first set. 
```json
Set-Cookie: user = "geek"; Max-Age = 3600
```

## Methods to set up the Cookies Attributes in java

### Create a Cookie object:
```java
/**
  * new Cookie(String name , String value)  
  *   Specifies a path for the cookie to which the client should return the cookie
  */
Cookie c = new Cookie("userName","JianMayer"); 
```
### Set the maximum Age:
```java
Cookie cookie = new Cookie("userName","JianMayer"); 
cookie.setMaxAge(-1);    //Expire this cookie as browser is closed
cookie.setMaxAge(0);     //Expire this cookie ONCE browser receives 
cookie.setMaxAge(1800);  //Expires After (60*3*10) 1800 seconds, half an hour
```
### Place the Cookie in HTTP response header:

```java
Cookie c = new Cookie("userName","JianMayer"); 
response.addCookie(c);
```

### (servlet) Read cookies 

```java
/**
  * {@code getCookies}
  * {@code getName}
  * {@code getValue}
  */
Cookie c[]=request.getCookies(); 
// c.length returns total numbers of cookies 
for(int i=0;i<c.length;i++){  
 out.print("Name: "+c[i].getName()+" & Value: "+c[i].getValue());
}
```

### setters

```java
/**
 * @Description
 *   Sets the domain in which this cookie is visible. 
 *   Domains are explained in detail 
 *   in the attributes of cookie part previously.
 * @param pattern : 
 *   the domain in which this cookie is visible.
**/
setDomain(String Pattern) 

/**
  * @Description 
  *  Specifies the purpose of this cookie.
  * @param purpose 
  *  The purpose of this cookie.
  */
setComment(String purpose) 

/**
  * @Description : 
  *   Specifies a path for the cookie 
  *   to which the client should return the cookie.
  * @param path : path where this cookie is returned
  */
setPath(string path)

/**
  * @description 
  *   Indicated if secure protocol 
  *   (for example <pre> https </pre>) 
  *   to be used while sending this cookie. 
  *   {@code false} by default
  * @param secure 
  *    If {@code true}, 
  *    the cookie can only be sent 
  *    over a secure protocol like 
  *    <pre> https <pre>. 
  *    If {@code false}, 
  *    it can be sent over any protocol.
**/
setSecure(boolean secure)

/**
  * @return
  *   <li> 0 if the cookie complies with 
  *        the original Netscape specification; </li> 
  *   <li> 1 if the cookie complies with 
  *        RFC 2965/2109 </li>
  */
public int getVersion() 

/** 
  * @Description 
  *   Used to set the version of the cookie protocol this cookie uses.
  * @param v : 
  *   0 for original Netscape specification
  *   1 for RFC 2965/2109
  */
setVersion(int v) 

/**
  * @return a copy of this cookie.
  */
public Cookie clone()
```

### Client asks for Cookie and Send Cookie
```java 
/**
  * MyServlet1.java
  * When a new client sent a request (to require a cookie)
  * And MyServlet1 fetches this request
  */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class MyServlet1 extends HttpServlet 
{
   public void doGet(HttpServletRequest request,
                     HttpServletResponse response) {
      try{
          response.setContentType("text/html");
          
          PrintWriter pwriter = response.getWriter();

          //Get Information from request 
          String name = request.getParameter("userName");
          String password = request.getParameter("userPassword");
          
          pwriter.print("Hello " + name);
          pwriter.print("Your Password is: "+password);

          //Creating(SET UP) two cookies
          Cookie c1 =  new Cookie("userName",name);
          Cookie c2 =  new Cookie("userPassword",password);
          
          //Adding the cookies to response header
          response.addCookie(c1);
          response.addCookie(c2);
          
          pwriter.print("<br><a href='welcome'>View Details</a>");
          pwriter.close();

   }catch(Exception exp){
       System.out.println(exp);
    }
  }
}
```

```java
/**
  * MyServlet2.java
  * Client sent the request with cookie 
  * Server fetched its cookie
  */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class MyServlet2 extends HttpServlet {
   public void doGet(HttpServletRequest request, 
    HttpServletResponse response){
    try{
       response.setContentType("text/html");
       PrintWriter pwriter = response.getWriter();
     
       //Reading cookies 
       Cookie c[]=request.getCookies(); 
       
       //Displaying User name value
       pwriter.print("Name: "+c[1].getValue()); 
       
       //Displaying user password value from cookie
       pwriter.print("Password: "+c[2].getValue());
    
       pwriter.close();
    }catch(Exception exp){
       System.out.println(exp);
     }
  }
}
```

## HttpServletRequest and HttpServletResponse

HttpServlet handles these things while fetching a request from the client
1. Get Cookie from request
2. modify Cookie from request 
3. Delete Cookie from request

### get Cookie
```java 
protected void createCookie(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

  /**
    * <p> create Cookie </p>
    */
  Cookie cookie = new Cookie("cookie-name", "cookie-Value");
  Cookie cookie2 = new Cookie("cookie-name2", "cookie-Value2");

  /**
    * <p> add the cookies in the response payload </p>
    */
  response.addCookie(cookie);
  response.addCookie(cookie2);
  
  response.getWriter().write("Created……");
```

### Modify cookie's value

```java
protected void updateCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
  /**
    * client sent the request with cookie named cookie-name then 
    * <p> Server creates 
    *     a cookie object 
    *     whose cookie name 
    *     is cookie-name </p>
    */
  Cookie cookie = new Cookie("cookie-name", null);
  
  /**
    * <p> modify cookie's value </p>
    */
  cookie.setValue("this is new value");
  
  /**
    * <p> Add it in the response payload 
    *     and the modified cookie in client 
    *     will be updated </p>
    */
  response.addCookie(cookie);
}
```

### Delete cookie
[To remove the cookie](https://stackoverflow.com/questions/890935/how-do-you-remove-a-cookie-in-a-java-servlet)   
```java
/**
  * @apinote
  *   To delete the cookie (stored in local storage) 
  *   we need set each cookie's 
  *   {@code setPath}   parameter   <pre> "/" </pre>
  *   {@code setMaxAge} parameter   <pre> 0  </pre>
  *   {@code setValue}  parameter   <pre> "" </pre> 
  */
private void eraseCookie(HttpServletRequest req, HttpServletResponse res) {
    Cookie[] cookies = req.getCookies();
    if (cookies != null)
        for (Cookie cookie : cookies) {
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            res.addCookie(cookie);
        }
}
```
