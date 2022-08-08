# Guard

-[[Angular 深入淺出三十天] Day 23 - 路由（六）](https://ithelp.ithome.com.tw/articles/10208485)

Guard allows us to implement policies governing possible **route transitions** in an Angular application.             
It is also a Service because it needs `@Injectable` to **restrict the access to certain path**        
一般常見使用路由守門員的時機大致上有兩種：   
1. `canActivate`   : 當使用者想要造訪某個路由時，透過路由守門員來判斷要不要讓使用者造訪。   
2. `canDeactivate` : 當使用者想要離開某個路由時，透過路由守門員來判斷要不要讓使用者離開。   

## CLI
CLI建立guard
```bash
# ng generate guard implementation_name
ng generate guard layout/layout
```

## Implementation (`CanActivate`)

Implementation to tell Angular router whether it can or cannot activate a particular route.
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
      state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
      return true;
  }
}
```

### Example

Guard a path `''` only allow user whose name is `John` to access 
```typescript
// ...
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

將Guard Implementation註冊在`const routes : Routes`
```typescript
const routes: Routes = [

  {
    path: '',
    component: LayoutComponent,
    canActivate: [LayoutGuard],  // HIER
    children: [...]
  }
  
  // ...
]
```

`login()`綁定`html`內的Event進行頁面轉換   
```typescript
export class LoginComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }
  
  // Router.navigate([path_name],{queryParams: { ... } ....} )
  login(): void{
    this.router.navigate([''], queryParams:{
       name : 'John'; 
    }
  });
}
```

For other case that we want to allow users to open this route(`/login`), only if they are not logged in. Otherwise, we redirect to `/secret-random-number`.
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

### `canLoad` property

The same approach applies to protecting other routes, with different policies implemented for given routes.   
`canLoad` property allows us to prevent a lazy-loaded route from being fetched from the server.    
Usually, `canLoad` guards implement the same policy as `canActivate` guards.    
```typescript
const routes: Routes = [
  { 
    path: '', 
    pathMatch: 'full', 
    redirectTo: '/login' },
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


## Implementation(`canDeactivate`)

Implement `CanDeactivate<XXXXcomponent>`來詢問使用者是否要離開當前頁面
```typescript
export class EnsureLoginGuard implements CanDeactivate<LoginComponent> {
  /**
   * 當使用者要離開該Route的canDeactivate時，會觸發該Method
   *
   * @param {LoginComponent} component : 該Route內的註冊Component
   * @param {ActivatedRouteSnapshot} currentRoute : 當前的Route
   * @param {RouterStateSnapshot} currentState : 當前Route狀態的快照
   * @param {RouterStateSnapshot} [nextState]  : 欲前往路由的路由狀態的快照
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

註冊到指定路由`canDeactivate [EnsureLoginGuard]`
```typescript
{
  path: 'login',
  component: LoginComponent,
  canDeactivate: [EnsureLoginGuard]
}
```

假設使用者有在當前頁面輸入資料(`name`)時,如果未完成需要Fill In的欄位`canDeactivate`會詢問是否要離開此頁面(如果都沒填任何欄位會直接離開不會詢問)    
```html
<input type="text" [(ngModel)]="name">
```

我們需要在`canDeactivate`內把欄位的`name`做檢查,看使用者沒有沒做填入的動作
```typescript
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
