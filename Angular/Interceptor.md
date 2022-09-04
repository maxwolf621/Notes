# Interceptor


- [Interceptor](#interceptor)
  - [Reference](#reference)
  - [URL (`req.clone`)](#url-reqclone)
  - [Loader](#loader)
  - [Converting](#converting)
  - [Headers](#headers)
  - [Notifications (`ToastrService`)](#notifications-toastrservice)
  - [error and retry](#error-and-retry)
      - [Things to consider here are](#things-to-consider-here-are)
  - [Profiling](#profiling)
  - [Fake backend](#fake-backend)
  - [Caching](#caching)
  - [Authentication](#authentication)

## Reference 
[Top 10 ways to use interceptors In Angular](https://medium.com/angular-in-depth/top-10-ways-to-use-interceptors-in-angular-db450f8a62d6)   
[Angular 7 jwt](https://dev-academy.com/angular-jwt/)     
[httpHeaders](https://www.tektutorialshub.com/angular/angular-httpheaders/) 
  
---

`HttpInterceptor` was introduced with Angular 4.3. 

- It provides a way to intercept HTTP requests and responses to transform or handle them before passing them along.
- **It also can process the request and response together.**
- Although interceptors are capable of mutating requests and responses, the `HttpRequest` and `HttpResponse` instance properties are `read-only`, rendering them largely immutable. 
  > This is because we might want to retry a request if it does not succeed at first. And immutability ensures that the interceptor chain can re-process the same request multiple times.

**If you provide interceptors A, then B, then C, requests will flow in A->B->C and responses will flow out C->B->A.**  
![](https://i.imgur.com/Rwxv0Y0.png)
- You cannot change the order or remove interceptors later.  
- `Enable` and `Disable` an `interceptor` dynamically, you'll have to build that capability into the interceptor itself.   


## URL (`req.clone`)

We could, for example, change `HTTP` to `HTTPS.`  
It’s as easy as cloning the request and replacing` http://` with `https://` at the same time.  
Then we send the cloned, HTTPS request to the next handler.
```typescript
/**
 * @description 
 * clone request and 
 * replace {@code http://} with {@code https://} 
 * at the same time
 */
const httpsReq = req.clone({
  url: req.url.replace("http://", "https://") 
});

return next.handle(httpsReq);
```
- **If it is not the request we are looking for, we can pass it on to the next interceptor with `next.handle(req)`.**

After this...
![](https://i.imgur.com/WFhcNDd.png)


```typescript
constructors(private http : httpClient){}
//...
const url = "http://jsonplaceholder.typicode.com/todos/1";
this.response = this.http.get(url);
```

or switch between HTTP and HTTPS in development use the CLI:
```bash
ng serve -ssl
```

Similarly, we could change a bit more of the URL and call it an API prefix interceptor:
```typescript
req.clone({ 
  url: environment.serverUrl + request.url 
});
```

Or you could again do it with the CLI:
```bash
ng serve — serve-path=<path> — base-href <path>/
```

## Loader
To see the spinning wheel of fortune when we are waiting for a response.   

Use a loader service that has a `show` and a `hide` function/method.  
Before we handle the request, we call the `show` method and through `finalize` we can hide the loader when done.
```typescript

// A loader in the interceptor
const loaderService = this.injector.get(LoaderService);

// show spinning wheel
loaderService.show();

return next.handle(req).pipe(
  // Spinning wheel display delays 5000 seconds
  delay(5000),
  
  // hide the spinning wheel 
  finalize(() => loaderService.hide())
);
```
![](https://miro.medium.com/max/388/1*w3-uzJSRJ78ljVszm0ZqDg.gif)  

In a real solution, we should take into account that there could be multiple HTTP calls intercepted.  

This could be solved by having a counter for requests (+1) and responses (-1).  
- You might want to have more specificity for your loaders especially(non global loaders) if you are loading more than one thing at the same time.

> What would happen if you use a switchMap that cancels the request?

## Converting

**When the API returns a format we do not agree with, we can use an `interceptor` to format it the way we like it.**

For example converting from `XML` to `JSON` or like in this example property names from `PascalCase` to `CamelCase`. 


```typescript
/**
 * If the backend doesn’t care about `JSON/JS` conventions 
 * we can use an interceptor to rename all the property names to `camelCase`.
 */
return next.handle(req).pipe(
  map(
    (event: HttpEvent<any>) => {
      // check the event is Response or not
      if (event instanceof HttpResponse) {

        // To format event/property the way we like 
        let camelCaseObject = mapKeys(event.body, (v, k) => camelCase(k));
        
        // clone the response body
        const modEvent = event.clone({ body: camelCaseObject });
        
        return modEvent;
    }
  })
);
```
![](https://miro.medium.com/max/465/1*1YDt0BlO15QHNNXwvLIEoQ.gif)
- Add it to your arsenal so that you have it ready when you need it.

## Headers
We can do a lot by manipulating headers. 
- Authentication/authorization
- Caching behavior; for example, If-Modified-Since
- `XSRF` protection

To add headers to the request in the interceptor.
```typescript

/**
 * add new header
 */
const modified = req.clone({ 
  setHeaders: { "X-Man": "Wolverine" } 
});

/**
 * @description Handle the {@code modified} to next interceptor
 */
return next.handle(modified);
```

Angular uses interceptors for protection against Cross-Site Request Forgery (`XSRF`).     
It does this by reading the `XSRF-TOKEN` from a cookie and setting it as the `X-XSRF-TOKEN` HTTP header.   

**Since only code that runs on your domain could read the cookie(cookie's domain), the backend can be sure that the HTTP request came from your client application and not an attacker.**

## Notifications (`ToastrService`)

To show/display notification (e.g. `Object created`) each time when the interceptor handles a (response) _201 created status_ back from the server.

```typescript
/**
 * @description 
 * handle the request to next interceptor 
 * and pipe the response from the server
 */
return next.handle(req).pipe(
  tap((event: HttpEvent<any>) => {
    if (event instanceof HttpResponse && event.status === 201) {
    
      // show the notification
      this.toastr.success("Object created.");
    }
  })
);
```
![](https://miro.medium.com/max/527/1*MUS9L-uKw3RucLOO7o_lKA.gif)

- We could also show notifications from the interceptor when errors occur with `this.toastr.error(...)`

## error and retry

There are two use cases for errors that we can implement in the interceptor.    

For example, network interruptions are frequent in mobile scenarios, and trying again may produce a successful result.  



#### Things to consider here are 
1. how many times to `retry` before giving up.  
2. Should we wait before retrying or do it immediately?  

For this, we use the `retry` method from `RxJS` to resubscribe to the observable.  
- Resubscribing to the result of an `HttpClient` method call has the effect of reissuing the HTTP request.

We also can check the status of the exception via `CatchError`
Depending on the status, we can decide what we should do.   

```typescript
return next.handle(req).pipe(
  // retry(reissuing the http request) twice before checking the error status. 
  retry(2),
  catchError((error: HttpErrorResponse) => {
    // check the status of the exception
    // if the status is not 401 then we do the following
    if (error.status !== 401) {
      // 401 handled in auth.interceptor
      this.toastr.error(error.message);      
    }
    return throwError(error);
  })
);
```
- [retryWhen method](https://www.learnrxjs.io/learn-rxjs/operators/error_handling/retrywhen)

## Profiling
**Because interceptors can process the request and response together, they can do things like time and log an entire HTTP operation.**  
So we can capture the time of the request and the response and log the outcome with the elapsed time.

```typescript
const started = Date.now();
let ok: string;

return next.handle(req).pipe(
  tap(
    (event: HttpEvent<any>) => ok = event instanceof HttpResponse ? 'succeeded' : '',
    (error: HttpErrorResponse) => ok = "failed"
  ),
  
  // Log when response observable either completes or errors
  finalize(() => {
    const elapsed = Date.now() - started;
    const msg = `${req.method} "${req.urlWithParams}" ${ok} in ${elapsed} ms.`;
    console.log(msg);
  })
);
```

There are a lot of possibilities here, and we could log the profiles to the database to get some statistics for example. In the example, we log to the console.

## Fake backend
**A mock or fake backend can be used in development when you do not have a backend yet.**

We mock the response depending on the request. And then return an observable of HttpResponse.
```typescript
const body = { 
  firstName: "Mock", 
  lastName: "Faker" 
};

return of(new HttpResponse(
  { status: 200, body: body }
));
```
![](https://miro.medium.com/max/403/1*deQ0kt_y5XL0THbLagv3SQ.gif)

## Caching 

Since interceptors can handle requests by themselves, without forwarding to `next.handle()`, we can use it for caching requests.

If we find a response in the map, we can return an `observable` of that response, by-passing the next handler.  
```typescript
Observable.of()
Observable.from()
Observable.mergeMap()
```


CacheInterceptor
```typescript
import { Injectable } from '@angular/core';
import { HttpEvent, HttpRequest, HttpHandler, HttpInterceptor, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, shareReplay } from 'rxjs/operators';

@Injectable()
export class CacheInterceptor implements HttpInterceptor {

  // cache
  private cache = new Map<string, any>();

  // only intercept request with get
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
 
    // if request.method is sending information 
    // to backend (not HTTP.GET method)
    if (request.method !== 'GET') {
      return next.handle(request);
    }

    // create cacheResponse
    const cachedResponse = this.cache.get(request.url);

    if (cachedResponse) {
      // create response observables
      return of(cachedResponse);
    }

    return next.handle(request).pipe( 
      tap(event => {
          if (event instanceof HttpResponse) 
          {
            this.cache.set(request.url, event);
          }
      })
    );
  }
}
```
- **Caching increases performance since you don’t have to go all the way to the backend when you already have the response cached.**

If we run the request, clear the response and then run again we will be using the cache.
![](https://miro.medium.com/max/461/1*xLhYIHm8ggkYMN6pKpsD2w.gif)
If we check the network tab in DevTools, we can see that we only make the request once.
![](https://i.imgur.com/5RnJMmS.png)

## Authentication

There are several things connected to authentication.  

We can do:
1. Add **bearer token**
2. **Refresh Token**
3. **Redirect** (url) to the login page

We should also have some filtering for when we send the bearer token.  
If we don’t have a token yet, then we are probably logging in and should not add the token.  
And if we are doing calls to other domains, then we would also not want to add the token.  

For example, if we send errors into Slack.  
```typescript
import { Injectable } from "@angular/core";

import { 
  HttpEvent, HttpInterceptor, HttpHandler,
  HttpRequest, HttpErrorResponse
} from "@angular/common/http";

import { throwError, Observable, BehaviorSubject, of } from "rxjs";
import { catchError, filter, take, switchMap } from "rxjs/operators";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
 
  private AUTH_HEADER = "Authorization";
  private token = "secrettoken";
  
  private refreshTokenInProgress = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  // intercept `req` and handle it the `next`
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // check the header of request 
    //   if request don't have `Content-Type` attributes inside the header
    if (!req.headers.has('Content-Type')) {
      req = req.clone({
        headers: req.headers.set('Content-Type', 'application/json')
      });
    }

    req = this.addAuthenticationToken(req);

    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error && error.status === 401) {
          // 401 errors are most likely going to be 
          //     because we have an expired token that we need to refresh.
          if (this.refreshTokenInProgress) {
            // If refreshTokenInProgress is true, we will wait until refreshTokenSubject has a non-null value
            //         which means the new token is ready and we can retry the request again
            return this.refreshTokenSubject.pipe(
              filter(result => result !== null),
              take(1),
              switchMap(() => next.handle(this.addAuthenticationToken(req)))
            );
          } else {
            this.refreshTokenInProgress = true;

            // Set the refreshTokenSubject to null so that subsequent API calls will wait until the new token has been retrieved
            this.refreshTokenSubject.next(null);
            
            return this.refreshAccessToken().pipe(
              switchMap((success: boolean) => {               
                this.refreshTokenSubject.next(success);
                return next.handle(this.addAuthenticationToken(req));
              }),
              // When the call to refreshToken completes we reset the refreshTokenInProgress to false
              // for the next time the token needs to be refreshed
              finalize(() => this.refreshTokenInProgress = false)
            );
          }
        } else {
          return throwError(error);
        }
      })
    );
  }

  private refreshAccessToken(): Observable<any> {
    return of("secret token");
  }

  private addAuthenticationToken(request: HttpRequest<any>): HttpRequest<any> {
    // If we do not have a token yet then we should not set the header.
    //      Here we could first retrieve the token from where we store it.
    if (!this.token) {
      return request;
    }
    // If you are calling an outside domain then do not add the token.
    if (!request.url.match(/www.mydomain.com\//)) {
      return request;
    }
    return request.clone({
      headers: request.headers.set(this.AUTH_HEADER, "Bearer " + this.token)
    });
  }
}
```
- [switchMap](https://ithelp.ithome.com.tw/articles/10188387)  

