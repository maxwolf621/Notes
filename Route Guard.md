
To achieve the goal of restricting access to `/secret-random-number` and redirecting back to the login page, in case the user is not logged in,  
we can make use of Angular’s built-in mechanism called __Router Guards__. 

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
