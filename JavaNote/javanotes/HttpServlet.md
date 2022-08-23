# HTTP
[http, session and cookie](https://tw.alphacamp.co/blog/cookie-session-difference)   
[http security](https://tw.alphacamp.co/blog/http-https-difference)  

- [HTTP](#http)
  - [Session And Cookie](#session-and-cookie)
    - [Stateless Protocol](#stateless-protocol)
  - [Session for stateless HTTP](#session-for-stateless-http)
  - [HTTP METHOD](#http-method)
    - [HTTP敏感資訊](#http敏感資訊)
    - [發送的資料長度](#發送的資料長度)
    - [書籤(bookmark)設置考量](#書籤bookmark設置考量)
    - [瀏覽器快取（Cache）](#瀏覽器快取cache)
  - [安全(SAFE METHOD), 等冪(IDEMPOTENT METHOD) AND 副作用(SIDE-EFFECT)](#安全safe-method-等冪idempotent-method-and-副作用side-effect)
    - [Safe Method](#safe-method)
    - [Idempotent Method](#idempotent-method)
      - [DIFFERENCE BTW `POST` AND `PUT`](#difference-btw-post-and-put)
    - [CONCLUSION](#conclusion)
    - [REST Api](#rest-api)
  - [SPA and MVC Structure](#spa-and-mvc-structure)
      - [MVC](#mvc)
      - [SPA](#spa)
  - [Java Methods for Servlet](#java-methods-for-servlet)
    - [FLOW](#flow)
    - [Servlet life cycle](#servlet-life-cycle)
  - [Response Mehods](#response-mehods)
    - [HttpServletRequest Method](#httpservletrequest-method)
    - [LIFE TIME OF SERVERLETCONTEXT AND REQUEST](#life-time-of-serverletcontext-and-request)
## Session And Cookie

### Stateless Protocol
- [HTTP METHODS](https://ithelp.ithome.com.tw/articles/10227433)  

**A stateless protocol does not require the server to retain information or status about each user for the duration of multiple requests.**  
But some web applications may have to track the user's progress from page to page 

For Example   
- WHEN a Web Server is **required to customize the content** of a web page for a user   
  > Solutions for these cases include (THESE are important for `Oauth2` concept):
    - THE USE of HTTP cookies. (重點)  
    - Server side sessions     (重點)  
    - Hidden variables (WHEN the current page contains a form)  
    - URL-rewriting using URI-encoded parameters, e.g. `/index.php?session_id=some_unique_session_code`.  

## Session for stateless HTTP

讓stateless的HTTP能得知使用者狀態的方法；簡單來說，就是Servlet透過Header的屬性`Set-Cookie`，把使用者的狀態(STATE)紀錄成儲存在使用者電腦(LOCAL STORAGE或BROWSER CACHE)裡的Cookie，當(Client's)Browser在每一次發送Request時，都會在Header設定Cookie屬性，把Cookie帶上，(Web Server)Servlet就能藉由Header檢視Cookie的內容，得知瀏覽器該使用者的狀態STATE；而像是「從登入到登出」、「從開始瀏覽網頁到 Cookie 失效」，或是**任何伺服器能認出使用者狀態的時間區間，就叫做Session**

Browser's response
```json 
HTTP/1.0 200 OK
Content-type: text/html
Set-Cookie: yummy_cookie=choco
Set-Cookie: tasty_cookie=strawberry
[page content]
```

After browser receives the response from server and stores the cookie in local storage.  
```json 
GET /sample_page.html HTTP/1.1
Host: www.example.org
Cookie: yummy_cookie=choco; tasty_cookie=strawberry
```
## HTTP METHOD
- [POST AND GET](https://medium.com/%E5%B0%8F%E5%B0%8F%E8%AA%AA%E6%9B%B8%E4%BA%BA/%E7%B6%B2%E9%A0%81get-%E8%88%87-post-%E5%B7%AE%E7%95%B0-%E7%A7%91%E6%99%AE%E5%A3%B9%E9%BB%9E%E9%80%9A-94cbaa666fdb)

在`HTTP 1.1`的版本中定義了八種 Method (方法)
```diff
OPTIONS
GET
HEAD
POST
PUT
DELETE
TRACE
CONNECT
```
- POST : 信封(地址) + 信(內容) **安全**   
- GET  : 明信片(地址+內容)     **不安全**  

![image](https://user-images.githubusercontent.com/68631186/129292506-74e75a8a-d9c9-46dd-91bb-f5fa73d314a5.png)  

[HTTP CONTENT TYPE](https://medium.com/hobo-engineer/ricky%E7%AD%86%E8%A8%98-postman-%E5%B8%B8%E8%A6%8B%E7%9A%84-content-type-b17a75396668)

|  | GET | POST |
|--|-----|------|
|bookmark | Y | N |
|Cache    | Y | N |
|CONTENT TYPE   | `application/x-www-form-urlencoded` | `application/x-www-form-urlencoded` or `multiple/form-data`
|HISTORY  | Parameters stored in browser |  |
|MAXLEN OF URL | char : 2048(MAXLEN of `URL`) | unrestricted | 
|ENCODE      | ASCII       | No Constraint |
|VISIBILITY    | **INFORMATION WILL BE DISPLAYED IN URL** | **INFORMATION WILL BE CONTAINED IN THE PAYLOAD** |

[POST AND PUT](https://progressbar.tw/posts/53)   
- POST : (複製人)不同編號(ID), 每POST一次就新增(ADD)一個新編號(ID)    
- PUT  : 同一人同編號(ID), 每PUT就更新(UPDATE)此人資訊    

### HTTP敏感資訊

就`HTTP/1.1`對`GET`的規範來說，是從指定的`URI`「取得」想要的資訊，指定的`URI`包括了請求查詢（Query）部份，  
- 例如 `GET /?id=0093` 瀏覽器會將指定的`URI`顯示在網址列上。  
  > 所以如果是`password`、`session_id` 等敏感資訊，就不適合使用`GET`發送

![image](https://user-images.githubusercontent.com/68631186/127016945-91a33030-5363-4147-b895-d0e3103691aa.png)   

另一個問題在於`HTTP`的 `Referer`標頭，這是用來表示從(source)哪裡連結到目前的網頁(destination)，如果你的網址列出現了敏感資訊，之後連接到網站B，該網址就有可能透過`Referer`標頭得到敏感資訊  
`HTTP/1.1` 對`POST`的規範，是要求指定的`URI`「接受」請求(REQUEST)中附上的實體（`Entity`），像是儲存為檔案、新增為資料庫中的一筆資料等... 
由於要求伺服器接受的資訊是附在請求本體（`Body`如下圖）而不是在`URI`，瀏覽器網址列不會顯示附上的資訊，傳統上敏感資訊也因此常透過`POST`發送  
![image](https://user-images.githubusercontent.com/68631186/127017382-aa7cbe05-8493-425e-b2fa-17a7ac85d411.png)  

### 發送的資料長度 
雖然`HTTP`標準中沒有限制`URI`長度，然而各家瀏覽器對網址列的長度限制不一，伺服器對`URI`長度也有限制，因此資料長度過長的話，就不適用`GET`請求。
- **`POST`的資料是附在請求本體（`Body`）而不是在`URI`，不會受到網址列長度限制，因而 POST 在過去常被用來發送檔案等大量資訊**

### 書籤(bookmark)設置考量 
**由於瀏覽器書籤功能是針對網址列**，因此想讓使用者可以針對查詢結果設定書籤的話，可以使用`GET`, `POST`後新增的資源不一定會有個`URI`作為識別，基本上無法讓使用者設定書籤。

### 瀏覽器快取（Cache）  
**只要符合`HTTP/1.1`對Cache的要求，`GET`的回應是可以被快取**的，最基本的就是指定的`URI`沒有變化時，許多瀏覽器會從快取中取得資料，不過，Server可以指定適當的Cache-Control Header來避免`GET`METHOD response被快取的問題   

`POST` METHOD Response，許多瀏覽器（不是全部）並不會快取，不過`HTTP/1.1`中規範，如果伺服端指定適當的Cache-Control或 Expires Header，仍可以對`POST`的回應進行快取。

[HTTP CACHE](https://blog.techbridge.cc/2017/06/17/cache-introduction/)

## 安全(SAFE METHOD), 等冪(IDEMPOTENT METHOD) AND 副作用(SIDE-EFFECT)

- [Definition of IDEMPOTENT METHOD](https://stackoverflow.com/questions/45016234/what-is-idempotency-in-http-methods)

由於傳統上發送敏感資訊時，並不會透過`GET`，因而會有人誤解為`GET`不安全，這其實是個誤會，或者說對安全的定義不同，   
就 `HTTP/1.1` 而言，區分了安全方法（Safe Method）與等冪方法（Idempotent Method）   

### Safe Method

**Request methods are considered safe if their defined semantics are essentially read-only**; i.e., the client does not request, and does not expect, any state change on the origin server as a result of applying a safe method to a target resource.
> 是指在實作應用程式時，使用者採取的動作必須避免有他們非預期的結果  

慣例上,`GET`與`HEAD`(與`GET`同為取得資訊，不過僅取得回應Header)對使用者來說就是**取得GET**資訊，不應該被用來**修改MODIFY**與使用者相關的資訊，像是進行轉帳之類的動作，它們是安全方法，這與傳統印象中 `GET`比較不安全相反  

相對之下，`POST`、`PUT` 與 `DELETE` 這些METHODS，代表著對使用者來說可能會產生**不安全**的操作
- e.g. 刪除使用者的資料 ... etc 

> **安全與否並不是指METHOD對伺服端是否產生副作用（Side Effect），而是指對使用者來說該METHOD是否安全**,`GET`也有可能在伺服端產生Side Effect  

### Idempotent Method

A request method is considered idempotent if the intended effect on the server of multiple identical requests with that method is the same as the effect for a single such request. **And it's worthwhile to mention that idempotency is about the effect produced on the state of the resource on the server and not about the response status code received by the client.**  

**The requests with idempotent methods can be repeated automatically if a communication failure occurs before the client is able to read the server's response.**  

**The client knows that repeating the request will have the same intended effect**, even if the original request succeeded, though the response might be different.

對於Side Effect的進一步規範是在方法的等冪特性，`GET`、`HEAD`、`PUT`、`DELETE`以及safe request methods 是等冪方法，也就是**單一Request產生的Side Effect，與同樣請求進行多次的Effect必須是相同的**, `OPTIONS`與`TRACE`本身就不該有副作用，所以他們也是等冪方法  

For Example

`GET /pageX HTTP/1.1` is idempotent.     

Called several times in a row, the client gets the **same results**:
```
GET /pageX HTTP/1.1
GET /pageX HTTP/1.1
GET /pageX HTTP/1.1
GET /pageX HTTP/1.1
```

`POST /add_row HTTP/1.1` is not idempotent;   

If it is called several times, it adds several rows **(Change the state)**:
```
POST /add_row HTTP/1.1
POST /add_row HTTP/1.1   -> Adds a 2nd row
POST /add_row HTTP/1.1   -> Adds a 3rd row
```

`DELETE /idX/delete HTTP/1.1` is idempotent, even if the returned status code may change between requests:

1. The server processes the request, the resource gets deleted and the server returns `204` or `202`.  
2. Then the client repeats the same DELETE request and, as the resource has already been deleted, the server returns `404`.   
   > Despite the different status code received by the client, the effect produced by a single `DELETE` request is the same effect of multiple DELETE requests to the same URI.
```json
DELETE /idX/delete HTTP/1.1   -> Returns 200 if idX exists
DELETE /idX/delete HTTP/1.1   -> Returns 404 as it just got deleted
DELETE /idX/delete HTTP/1.1   -> Returns 404
```

#### DIFFERENCE BTW `POST` AND `PUT`

我們可以利用Idempotent來細分`POST`以及`PUT`之區別

e.g. 新增新的使用者, 違反了Idempotent的概念, 也是區別`POST`與`PUT`的特性之一，在`HTTP/1.1` Specification中,`PUT`方法要求將附加的Entity儲存於指定的`URI`，如果指定的`URI`下已存在資源，則附加的實體是用來進行資源的更新(UPDATE)，如果資源不存在，則將實體儲存下來並使用指定的`URI`來代表它(ADD)，這亦符合等冪特性，例如用`PUT`來更新使用者基本資料，只要附加於請求的資訊相同，一次或多次請求的副作用都會是相同，也就是使用者資訊保持為指定的最新狀態,而`POST`每一筆Request則會造成Server上的資源進行變動  

### CONCLUSION

Information State will be changed => Not Safe  
Information State stays the same after duplicates HTTP methods request/response => Idempotent  
| Method  | Safe | Idempotent |
|---------|------|------------|
| CONNECT | no   | no         |
| DELETE  | no   | yes        |
| GET     | yes  | yes        |
| HEAD    | yes  | yes        |
| OPTIONS | yes  | yes        |
| POST    | no   | no         |
| PUT     | no   | yes        |
| TRACE   | yes  | yes        |

我們將需要利用Safe Method以及Idempotent Method概念考量選用特定METHOD作為Client-Server之間的資源傳輸  


### REST Api 

- Keyword :  
`減少重複性`,    
`使HTTP protocol語意話`,   
`資源對應的URL`,   
`Stateless`  

現在不少 Web 服務或框架支援 REST 風格的架構，REST 全名 Representational State Transfer，**REST 架構由客戶端／伺服端組成，兩者間通訊機制是無狀態的(Stateless)**，在許多概念上，與 HTTP 規範不謀而合（REST 架構基於 `HTTP 1.0`，與 `HTTP1.1` 平行發展，但不限於HTTP）。

- 符合 REST 架構原則的系統稱其為RESTful，
  > 以上的描述，並不是說`PUT`只能用於更新(UPDATE)資源，也沒有說要新增(ADD)資源只能用`POST`, `PUT`在指定的`URI`下不存在資源時，也會新建請求中附上的資源。等冪性是在選用`POST`或 `PUT`時考量的要素之一  

- `HTTP/1.1`中也有規範，也就是請求時指定的`URI`之作用
  > `POST`中請求的`URI`，是要求其背後資源必須處理附加的實體(`Entity`)，而不是代表處理後實體(`Entity`)的`URI`；然而`PUT`時請求的`URI`，就代表請求REQUEST中附加實體(`Entity`)的`URI`，無論是更新或是新增實體。

透過動詞(HTTP Methods)、名詞(URI/URL，代表目標資源)、內容型態(回應的內容，HTML、XML、JSON、etc.), **讓Stateless HTTP protocol能藉由 REST 的語意化設計，攜帶所有的狀態資訊降低對網路通訊的重複請求資源消耗   

透過RESTful API的設計風格，**每個資源(Resource)都會得到一個到對應的位置（URL），並能透過 HTTP 語意化的方法**
```diff
   動詞     名詞
+ [POST] /bookmarks 
<!-- 是用來新增一筆資 -->

! [GET] /bookmarks/1 
<!-- 用來取得 ID 為 1 的書籤-->

! [PUT] /bookmarks/1 
<!-- 用來更新 ID 為 1 的書籤資料-->

! [DELETE] /bookmarks/1  
<!-- 用來刪除 ID 為 1 的書籤資料-->

! [GET] http://mytube.com/v1/videos/            
<!-- [GET]取得 video list -->

+ [POST] http://mytube.com/v1/videos/           
<!-- [POST]新增 video -->

! [GET] http://mytube.com/v1/videos/MgphHyGgeQU 
<!-- [GET]取得 指定ID[MgphHyGgeQU] 的video -->

! [PUT] http://mytube.com/v1/videos/MgphHyGgeQU 
<!-- [PUT]修改 指定ID[MgphHyGgeQU] 的video -->

! [DELETE] http://mytube.com/v1/videos/MgphHyGgeQU 
<!--[DELETE]刪除 指定ID[MgphHyGgeQU] 的video -->
```

[OAuth2User Endpoints with HTTP methods](https://darutk.medium.com/diagrams-and-movies-of-all-the-oauth-2-0-flows-194f3c3ade85)  
[HTTP SEO](https://ithelp.ithome.com.tw/articles/10225117)  


## [SPA and MVC Structure](https://ithelp.ithome.com.tw/articles/10224772)  

#### MVC
![](https://ithelp.ithome.com.tw/upload/images/20200908/20111380cEB9Gr0Y4q.png)  
#### SPA
![](https://ithelp.ithome.com.tw/upload/images/20200908/20111380vhVNWYCggj.png)   

## Java Methods for Servlet
[Servlet](https://openhome.cc/Gossip/ServletJSP/ServletGenericServletHttpServlet.html)

![image](https://user-images.githubusercontent.com/68631186/125268238-18495700-e33a-11eb-9503-0ac4d33835fc.png)
- HttpServlet took care content of `HttpServletRequest` and `HttpServletResponse`  
- Servlet Container _creates_ HttpServletRequest/Response Instance,*resolves*,*encapsulates* HttpServletRequest and *sends* HttpServletResponse back to client 

### FLOW
1. Web Client **SENDS** HttpServletRequest to Servlet Container
2. Servlet Container **RESOLVES** Web client's HttpServletRequest
   1. Servlet Container **CREATES** an instance of HttpServletRequest to encapsulate the HttServletRequest provided by web client  
   2. Servlet Container **CREATES** an instance of HttpServletResponse  
   3. Servlet Container **CALLS** HttpServlet's service methods，Pass HttpRequest/HttpResponse instance as parameterS of HttpServlet's methods  
3. HttpServlet **CALLS** HttpServletRequest's methods to get HTTP request's information ( header, attribute, ... etc )  
   > 3-1 HttpServlet **CALLS** HttpServletResponse's methods to configure response payload that should send back to web client  
4. Servlet Container **SENDS** HttpServletResponse to Web Client  

> Servlet最重要的工作在於處理request, 只會產生一個instance,由Container透過多個thread, 來處理一個Servlet所發出的多個request
 
### Servlet life cycle
```java
/**
  * @apinote
  * 在完成 Servlet 初始化{@code init}後，
  * 如果有請求將由某個 Servlet 處理，則容器會呼叫該 Servlet 的{@cpde service}方法，
  * 傳入 <pre> ServletRequest、ServletResponse <pre> 實例
  * <p> 沒有HttpServelt的原因 這是由於最初設計Servlet時，並不限定它只能用在 HTTP 上</p>
  * <p> 實作 Servlet 介面的類別是{@code GenericServlet}類別，這是一個abstract類別 </p>
  * <p> 
  * {@code GenericServlet} 對 Servlet 介面的 {@code service} 方法沒有實作，
  * 僅標示為 abstract，{@code service}方法的實作由子類別 {@code HttpServlet}來完成
  * </p>
  */
import java.io.IOException;

public interface Servlet {
    /**
      * @Description
      * Container建立Servlet instance後,會執行{@code init}進行初始化工作
      * 如果有需要其他初始工作(註冊物件或是DB設定),可以去Override,只會執行一次
      */
    public void init(ServletConfig config) throws ServletException;
    /**
      * @Description
      * 當client傳送第一個request時,Container會啟動一個新的thread,去執行此方法
      * 根據傳進的HTTP去呼叫對應的Servlet Method,通常{@code service}是不會去Override的,
      * {@code service} 只是讓HTTP找到該執行方法(for example {@code doGet})
      */
    public ServletConfig getServletConfig();
    public void service(ServletRequest req, ServletResponse res) 
                   throws ServletException, IOException;
    public String getServletInfo();
    /**
      * @Description
      * 同{@code init}只會執行一次
      * 當Servlet準備GC(垃圾回收機制)前,會進行相關的清理工作
      */
    public void destroy();
}

/**
  * {@code HttpServlet}實作了{@code GenericServlet}未實作的{@code service}方法，
  * 將傳入的 {@code ServletRequest}、{@code ServletResponse} 轉換為 
  * {@code HttpServletRequest} and {@code HttpServletResponse}，
  * 並Call {@code HttpServlet}自己定義以HttpServletRequest、HttpServletResponse作為參數的{@code service}方法
  * 這個{@code service}方法中，根據HTTP請求METHOD決定該呼叫{@code doGet} or {@code doPost}等方法
  * For More details about Methods {@link https://openhome.cc/Gossip/ServletJSP/DoGetDoPost.html}
  */
public abstract class HttpServlet extends GenericServlet {
    public HttpServlet() {}

   //...
  
   /**
     * @Description
     *   根據HTTP請求METHOD決定該呼叫{@code doGet} or {@code doPost}等方法
     */
   protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String method = req.getMethod();

        if (method.equals(METHOD_GET)) {
            long lastModified = getLastModified(req);
            if (lastModified == -1) {
                doGet(req, resp);
            } else {
                //...        
    }

    //...

    /**
      * @Description
      *   Convert(cast) ServletRequestRequest/ServletResponse to 
      *   HttpServletRequest/HttpServletResponse
      */
    @Override
    public void service(ServletRequest req, ServletResponse res)
        throws ServletException, IOException {

        HttpServletRequest  request;
        HttpServletResponse response;

        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) res;
        } catch (ClassCastException e) {
            throw new ServletException("non-HTTP request or response");
        }
        // call doGet or doPost method
        service(request, response);
    }
}

```

[HttpServlet](https://blog.csdn.net/qq_41007534/article/details/99696559)
[HttpServletRequest](https://blog.csdn.net/jiangyu1013/article/details/56840191)
[HttpServletResponse](https://blog.csdn.net/q547550831/article/details/50445516)
[HttpServletRequest/Response](https://blog.csdn.net/Jsagacity/article/details/80143842)

## Response Mehods
```java
/**
  * @return Staus
  * <p> for example 404, 401 ... etc. </p>
  */
setStatus(int sc)

/**
  * @Description
  *   Handle for Header for response payload
  *   <pre> add </pre> put attributes in header
  *   <pre> set </pre> set up attributes
  */
addHeader(String name, String value)
addIntHeader(String name, int value)
addDateHeader(String name, long date)

setHeader(String name, String value)
setDateHeader(String name, long date)
setIntHeader(String name, int value)

/**
  * @Description 
  *   Via {@code Write(String str)} writes {@param str} in String buffer
  *   Tomcat will 
  *   put {@param str} from String buffer into {@code HttpServeltResponse}
  *   and send to Browser
  */
PrintWriter getWriter()
/** 
  * @Description
  *   via {@code write(byte[] bytes)} in BufferedStream
  *   Tomcat will put @{param bytes} from BufferedStream into {@code HttpServeltResponse}
  *   and send to Browser
  */
ServletOutputStream getOutputStream()

/**
  * @Description
  *   In case we use the language like Chinese (to avoid error)
  *   then we can use this method to set up Encode as UTF-8
  */
setCharacterEncoding(String charset) 

/**
  * @Description
  *   To set up the browser's Encode
  *   this is recommended that uses 
  *   {@code setContentType} instead of {@code setCharacterEncoding}
  */
response.setContentType(“text/html;charset=UTF-8”);
```

### HttpServletRequest Method
```java
/**
  * @Description
  *   ask for [GET, POST, DELETE ... ETC] methods in this request
  */
String getMethod()

/**
  * @Description
  *   ask for URL from client's request
  */
String getRequestURI()
StringBuffer getRequestURL()
String getContextPath() 

/**
  * @Description
  *   Get QueryParameter in URL
  *   For example 
  *   <pre> ?username=zhangsan&password=123 </pre>
  */
String getQueryString()

/**
  * @Description
  *  get client's ip addr
  */
getRemoteAddr() 

/**
  * @Description
  *   get header's attributes
  */
long getDateHeader(String name)
String getHeader(String name)
Enumeration getHeaderNames()
Enumeration getHeaders(String name)
int getIntHeader(String name)

/** 
  * <p> 
  * Get Queryparameter in {@code getRequestURL} 
  * For example <li> username=zhangsan&password=123&hobby=football&hobby=basketball </li>
  * |Parameter|Parameter Value       |
  * |---------|----------------------|
  * | username|[zhangsan]            |
  * | password|[123]                 |
  * | hobby	  |[football,basketball] |
  * </p>
  * <p> We can get queryparameters by use the following methods </p>
  */
String getParameter(String name)
String[] getParameterValues(String name)
Enumeration getParameterNames()
Map< String,String[]> getParameterMap()

/**
  * @Description
  *  Encode for GET METHOD
  */
request.setCharacterEncoding(“UTF-8”);
/**
  * @Description
  *  Encode for POST METHOD
  */
parameter = new String(parameter.getbytes(“iso8859-1”),”utf-8”);

setAttribute(String name, Object o)
getAttribute(String name)
removeAttribute(String name)

/**
  * @Description 
  *  {@code forward(req,res)} is used by server side
  *  <li> 定義在RequestDispatcher的介面,由request.getRequestDispatcher呼叫 </li>
  *  <li> 因是內部轉址,可以透過setAttribute傳遞參數 </li>
  *  <li> 內部轉址,URL不會顯示程式名稱(可設定成參數) </li>
  *  <li> 適用於權限管理轉頁時使用 </li>
  *  <li> 由於不用在ASK一次request from client 故效率比{@code sendRedirect(String path)}高 </li>
  */
RequestDispatcher getRequestDispatcher(String path)
request.getRequestDispatcher.forward(ServletRequest request, ServletResponse response)

/**
  * @Description 
  *   {@code sendRedirect(req,res)} is used by server side
  *   <li> it will be called by <pre> HttpServletResponse </pre> </li>
  *   <li> 效率較低(client will send the request again) </li>
  *   <li> 適用於跳至外部網站或回主畫面使用 </li>
  *
  */
response.sendRedirect("/Longin.jsp");
```

### LIFE TIME OF SERVERLETCONTEXT AND REQUEST
ServletContext：
- INITIALIZED：SNICE SERVER STARTS
- DESTRUCTION：WHEN SERVER CLOSES 
- SCOPE : WHOLE WEB APPLICATION

request：
- INITIALIZED：ASK FOR A ACCESS TO A SERVER
- DESTRUCTION：RECEIVE A RESPONSE
- SCOPE : BTW CLIENT SENDS REQUEST AND RECEIVE RESPONSE FROM SERVER

