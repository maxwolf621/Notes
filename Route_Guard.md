## Guard
[](https://ithelp.ithome.com.tw/articles/10208485)  

- Guard 也是 Service 的一種，因為它也有 @Injectable 的裝飾器
- To Restrict the access to certain path 


建立guard
```
ng generate guard layout/layout
```

實作Interface CanActivate內的`canActivate`
```typescript
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LayoutGuard implements CanActivate {
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return true;
  }
}
```
- next 與 state ，其所會對應到的資料型別是`ActivatedRouteSnapshot`與`RouterStateSnapshot`
- 利用`next`以及`state`設定路徑的權限


For example 需要guard幫我們過濾`path:''`這個路徑,任何人在access該路徑之前都得經過guard檢查

```typescript
canActivate(
  next: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): Observable<boolean> | Promise<boolean> | boolean {
  
  const canActivate = next.queryParams.name === 'John';

  if (!canActivate) { 
    alert('Only Allow John Access');
  }

  return canActivate;
  
}
```

將Guard註冊在`const routes`內
```typescript
const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    canActivate: [LayoutGuard], <---------------------- HIER
    children: [...]
  }
  // ...
]
```

## `Router.navigate([path_name],{ queryParams:{... } ....} ) `

利用typescript的方法綁定`html`內的event進行頁面轉換
```typescript
export class LoginComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  login(): void{
    this.router.navigate([''], queryParams:{
       name : 'John'; 
    }
  });
}
```
## Ask User to redirect to other page

利用Guard內的`CanDeactivate`詢問使用者是否要離開當前頁面

```typescript
export class EnsureLoginGuard implements CanDeactivate<LoginComponent> {

  /**
   * 當使用者要離開這個 Guard 所防守的路由時，會觸發這個函式
   *
   * @param {LoginComponent} component - 該路由的 Component
   * @param {ActivatedRouteSnapshot} currentRoute - 當前的路由
   * @param {RouterStateSnapshot} currentState - 當前路由狀態的快照
   * @param {RouterStateSnapshot} [nextState] - 欲前往路由的路由狀態的快照
   * @returns {(boolean | Observable<boolean> | Promise<boolean>)}
   * @memberof EnsureLoginGuard
   */
  canDeactivate(
    component: LoginComponent,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState?: RouterStateSnapshot
  ): boolean | Observable<boolean> | Promise<boolean> {
    return false;
  }

}
```

並將該guard加入到路由`canDeactivate`內
```typescript
{
  path: 'login',
  component: LoginComponent,
  canDeactivate: [EnsureLoginGuard]
}
```

### 互動式Deactivate

當使用者有在當前頁面輸入資料時,如果使用者在未完成該網頁需要fill in的欄位會詢問是否要離開此頁面(如果都沒填任何欄位會直接離開不會詢問)

```html
<input type="text" [(ngModel)]="name">
```
- 雙向綁定了`name`這個屬性，所以到LoginComponent 裡新增一下這個屬性：

```typescript
name = '';
最後調整一下 EnsureLoginGuard 的程式碼：

canDeactivate(
  component: LoginComponent,
  currentRoute: ActivatedRouteSnapshot,
  currentState: RouterStateSnapshot,
  nextState?: RouterStateSnapshot
): boolean | Observable<boolean> | Promise<boolean> {

  if (component.name.trim()) {
    return confirm('Leaving?');
  }

  return true;

}
```



These guards allow us to implement policies governing possible route transitions in an Angular application. 

Imagine a situation when a user tries to open a page that he has no access rights to. 
In such a case application should not allow this route transition. 
To achieve this goal we can make use of `CanActivate` guard.  

```typescript
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate() {
    if (this.authService.isLoggedIn()) {
      // redirect to page /sercret-random-number
      this.router.navigate(['/secret-random-number']);
    }
    // return /login page
    return !this.authService.isLoggedIn();
  }
}
```

AuthGuard implements `canActivate()` which tells Angular router whether it can or cannot activate a particular route.   
To attach given guard to the route that it should protect, we just need to place its reference in` canActivate` property of that route as presented below.   
In our case, we want to protect the `/login` route. We want to allow users to open this route, only if they are not logged in. Otherwise,   
we redirect to `/secret-random-number`.  
The same approach applies to protecting other routes, with different policies implemented for given routes.   
Also, we can notice the `canLoad` property in below routes configuration.  


This kind of protection allows us to prevent a lazy-loaded route from being fetched from the server.  
Usually, `canLoad` guards implement the same policy as canActivate guards.\
```typescript
const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/login' },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'secret-random-number',
    loadChildren: './random/random.module#RandomModule',
    canActivate: [RandomGuard],
    canLoad: [RandomGuard]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule { }
```
