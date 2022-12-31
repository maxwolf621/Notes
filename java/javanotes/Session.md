# Session

[資安這條路 15 - [Session] Session 劫持、 Session 固定](https://ithelp.ithome.com.tw/articles/10246787)  
[Day14-Session與Cookie差別](https://medium.com/tsungs-blog/day14-session%E8%88%87cookie%E5%B7%AE%E5%88%A5-eb7b4035a382)


- [Session](#session)
	- [Create A Session](#create-a-session)
	- [Set Up Servlet's request and response attributes](#set-up-servlets-request-and-response-attributes)
	- [Life time of the session](#life-time-of-the-session)
	- [Cookies & Session](#cookies--session)

It stores the **valid and important** information of the Client and will be used by Server 
- e.g. how Server authenticates the valid user against the Request the client gave

## Create A Session  

```java
public class SessionServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public SessionServlet() {
	}

	protected void getSession(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getHeader("Cookie"));
		
		/**
		  * <p>
		  * <p> If call this {@code getSession} first time 
		  *     then create a new session </p>
		  * <p> Else if the session already exists 
		  *     Get the Session </p>
		  */
		HttpSession session = request.getSession();
		
		/**
		  * @return 
		  * {@code session.getId()} : Unique ID of session
		  * {@code session.isNew()} : Is a fresh new session ?
		  */
		response.getWriter().write(
				"session ID:" + session.getId() + "<br/>is a fresh new session ? \n" + session.isNew());
	}
}
```

## Set Up Servlet's request and response attributes

via `setAttribute(key, paire)` and `getAttribute(key)`

```java  
/**
  * {@code setAttribute} 
  */
protected void setAttribute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// set up the attribute of payload  
		session.setAttribute("A", "Avalue");
		
		// now response will include "A" : "Avalue"
}

/**
  * {@code getAttribute}
  */
protected void getAttribute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  HttpSession session = request.getSession();
  
  // get the attribute A from session
  String value = (String) session.getAttribute("A");
  
  // we can print out the value ... etc ...
}
```


## Life time of the session

```java
HttpSession session = request.getSession();

/**
  * <p> After get the session from the request 
  * 	Uses {@code setMaxInactiveInterval} 
  * 	to set up life time of this seesion </p>
  * @param : seconds for life cycle of session 
  */
session.setMaxInactiveInterval(3); 		    //  3 seconds 
session.setMaxInactiveInterval(60); 	            // One minute
session.setMaxInactiveInterval(60 * 60); 	    // One hours
session.setMaxInactiveInterval(60 * 60 * 24);       // One day
session.setMaxInactiveInterval(60 * 60 * 24 * 7);   // One week

session.setMaxInactiveInterval(-1); 	            // LONG LIVE FOREVER
session.invalidate(); 			            // Set session InActive 
```

## Cookies & Session

- [How Cookie and Session works](http://aliyunzixunbucket.oss-cn-beijing.aliyuncs.com/csdn/41176c45-a016-4779-b312-12aeff91e3c8?x-oss-process=image/resize,p_100/auto-orient,1/quality,q_90/format,jpg/watermark,image_eXVuY2VzaGk=,t_100,g_se,x_0,y_0)

1. Client sent the request
2. servlet created a `session_id` and sent back a response contained with cookie whose name is `session_id`
```diff
+ client  -request : xxx.example.com/[queryparams]-> servlet
- servlet -response ( contained cookie for session_id )-> client
+ client  -request  ( contained cookie for session_id )-> servlet
```

After client receives the cookie from response and saves it in local storage/browser   
so then the servlet can compare the `session_id` with the one sent by client within the expiration of the cookie
