# JWT in Angular

[TOC]

## JWT Http interceptor
Once we have our Access Token (JWT) persisted after user logs into the application, we want to use it to authorize outgoing requests.  

One approach could be to simply update every service that communicates with API to enrich requests with additional HTTP Header.   
This will result in a lot of duplicated code comparing to approach with HTTP Interceptor.  
The goal of HTTP Interceptor is to apply some processing logic to every outgoing request in the application.

Creating an HTTP interceptor is quite similar to creating a Router Guard.  
We need to have a class that implements a specific interface with the required method.  
In this case, it is HttpInterceptor with intercept method.  


```typescript
@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  
  constructor(public authService: AuthService) { }
 
  /**
   * First, we want to check if the token is available with this.authService.getJwtToken().  
   *    If we have a token, we set an appropriate HTTP header.
   */
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (this.authService.getJwtToken()) {
      // get token from the request
      request = this.addToken(request, this.authService.getJwtToken());
    }

    return next.handle(request).pipe(catchError(error => {
      if (error instanceof HttpErrorResponse && error.status === 401) {
        return this.handle401Error(request, next);
      } else {
        return throwError(error);
      }
    }));
  }

  private addToken(request: HttpRequest<any>, token: string) {
    return request.clone({
      setHeaders: {
        'Authorization': `Bearer ${token}`
      }
    });
  }
}
```

it is necessary to register it as a provider with HTTP_INTERCEPTORS token in Angular module.
```
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
@NgModule({
  // declarations...
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ]
  // imports...
})
export class AuthModule { }
```

## Refresh Token

If someone other than us gets into possession of the token there is very little we can do about it. 
That’s why it is a good idea to always give the token short time of validity. 

There are no strict rules of how long a token should live and it depends on the system requirements. 
**A good starting point could be to have a token that is only valid for 15 minutes.** 
After that time server would not consider this token valid and would not authorize requests with it.

let’s say, every 15 minutes. The solution to this problem is a Refresh Token. 
This kind of token lives somewhere on the server side (database, in-memory cache, etc) and is associated with the particular user’s session. 
It is important to notice that this token differs from JWT in many ways. 

First, it is not self-contained - it can be as simple as a unique random string. 
Second, we need to have it stored to be able to verify if user’s session is still alive. 
This gives us an ability to invalidate the session by simply removing the associated pair of `[user, refresh_token]`. 
When there is an incoming request with Access Token that has become invalid, the application can send a Refresh Token to obtain a new Access Token. 

If the user’s session is still alive, the server would respond with a new valid JWT. 

In our example, we will be sending Refresh Token transparently for the user, so that he is not aware of the refreshing process.

Here comes a tricky part - we want to queue all HTTP requests in case of refreshing. 
This means that if the server responds with 401 Error, we want to start refreshing, block all requests that may happen during refreshing, 
and release them once refreshing is done.  
- To be able to block and release requests during the refreshing, we will use `BehaviorSubject` as a semaphore.

First, we check if refreshing has not already started and set isRefreshing variable to true and populate null into refreshTokenSubject behavior subject. 
Later, the actual refreshing request starts. In case of success, isRefreshing is set to false and received JWT token is placed into the refreshTokenSubject. 
Finally, we call next.handle with the addToken method to tell interceptor that we are done with processing this request. 

In case the refreshing is already happening (the else part of the if statement), we want to wait until `refreshTokenSubject` contains value other than null. 
Using filter(token => token != null) will make this trick! Once there is some value other than null (we expect new JWT inside) we call take(1) to complete the stream. 
Finally, we can tell the interceptor to finish processing this request with next.handle.

```typescript

private isRefreshing = false;
private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

// 401 Error means that token is unauthorized
private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
  // check if the refresing process is on or not
  if (!this.isRefreshing) {
    this.isRefreshing = true;
    this.refreshTokenSubject.next(null);

    return this.authService.refreshToken().pipe(
      switchMap((token: any) => {
        this.isRefreshing = false;
        this.refreshTokenSubject.next(token.jwt);
        return next.handle(this.addToken(request, token.jwt));
      }));

  } else {
    return this.refreshTokenSubject.pipe(
      filter(token => token != null),
      take(1),
      switchMap(jwt => {
        return next.handle(this.addToken(request, jwt));
      }));
  }
}
```

## AuthService
AuthService will be the place where we implement all the logic to handle logging in and out. 

Here we use `HttpClient` to execute post call to the server and apply some operators with `pipe()` method. 
By using `tap()` operator we are able to execute the desired side effect. 

- On successful post method execution, we should receive Access Token and Refresh Token.  
- The side effect we want to execute is to store these tokens calling `doLoginUser`.  

In this example, we make use of `localstorage`.  
Once stored, the value in the stream is mapped to true in order for the consumer of that stream to know that the operation succeeded.  
Finally, in case of error, we show the alert and return observable of false.

#### Side effect 
It is a term used in Functional Programming.  
This concept is opposite to functional purity which means that there are no state changes in the system and the function always returns the result based on its inputs (regardless of the system state).  
If there is a state change (for example variable change) we call it side effect.


Implementation of the logout method is basically the same, apart from the fact, that inside of the request’s body we send refreshToken.  
This will be used by the server to identify who is attempting to log out. 
Then, the server will remove the pair of `[user, refresh_token]` and refreshing will not be possible anymore.  
Yet, **Access Token will still be valid until it expires, but we remove it from the localstorage.**  

```typescript
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private readonly JWT_TOKEN = 'JWT_TOKEN';
  private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
  
  private loggedUser: string;

  constructor(private http: HttpClient) {}

  login(user: { username: string, password: string }): Observable<boolean> 
  {
    // post to server
    return this.http.post<any>(`${config.apiUrl}/login`, user)
      .pipe(
        tap(tokens => this.doLoginUser(user.username, tokens)),
        mapTo(true),
        catchError(error => {
          alert(error.error);
          return of(false);
        }));
  }

  logout() {
    return this.http.post<any>(`${config.apiUrl}/logout`, {
      'refreshToken': this.getRefreshToken()
    }).pipe(
      tap(() => this.doLogoutUser()),
      mapTo(true),
      catchError(error => {
        alert(error.error);
        return of(false);
      }));
  }

  isLoggedIn() {
    return !!this.getJwtToken();
  }

  
  refreshToken() {
    return this.http.post<any>(`${config.apiUrl}/refresh`, {
      'refreshToken': this.getRefreshToken()
    }).pipe(tap((tokens: Tokens) => {
      this.storeJwtToken(tokens.jwt);
    }));
  }

  // get jwt Token form localStorage
  getJwtToken() {
    return localStorage.getItem(this.JWT_TOKEN);
  }

  // store the token from backend
  private doLoginUser(username: string, tokens: Tokens) {
    this.loggedUser = username;
    this.storeTokens(tokens);
  }

  private doLogoutUser() {
    this.loggedUser = null;
    this.removeTokens();
  }

  // get RefreshToken from localStorage
  private getRefreshToken() {
    return localStorage.getItem(this.REFRESH_TOKEN);
  }

  private storeJwtToken(jwt: string) {
    localStorage.setItem(this.JWT_TOKEN, jwt);
  }

  private storeTokens(tokens: Tokens) {
    localStorage.setItem(this.JWT_TOKEN, tokens.jwt);
    localStorage.setItem(this.REFRESH_TOKEN, tokens.refreshToken);
  }

  // remove jtw and refresh token in localstorage
  private removeTokens() {
    localStorage.removeItem(this.JWT_TOKEN);
    localStorage.removeItem(this.REFRESH_TOKEN);
  }
}
```
