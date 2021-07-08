```
app
_components
  alert.component.html
  alert.component.ts
  index.ts
_helpers
  auth.guard.ts
  error.interceptor.ts
  fake-backend.ts
  jwt.interceptor.ts
  index.ts
_models
  alert.ts
  user.ts
  index.ts
_services
  account.service.ts
  alert.service.ts
  index.ts
account
  account-routing.module.ts
  account.module.ts
  layout.component.html
  layout.component.ts
  login.component.html
  login.component.ts
  register.component.html
  register.component.ts
  index.ts
home
  home.component.html
  home.component.ts
  index.ts
users
  add-edit.component.html
  add-edit.component.ts
  layout.component.html
  layout.component.ts
  list.component.html
  list.component.ts
  users-routing.module.ts
  users.module.ts
  index.ts
app-routing.module.ts
app.component.html
app.component.ts
app.module.ts
```



## alert component 

**Alerts are cleared when an alert with an empty message is received from the alert service.**  

- The `ngOnInit` method subscribes to the observable returned from the `alertService.onAlert()` method, this enables the alert component to be notified whenever an alert message is sent to the alert service and add it to the alerts array for display. 
- The `ngOnInit` method also calls `router.events.subscribe()` to subscribe to route change events so it can automatically clear alerts on route changes.
- The `ngOnDestroy()` method unsubscribes from the alert service and router when the component is destroyed to prevent memory leaks from orphaned subscriptions.
- The `removeAlert()` method removes the specified alert object from the array, which allows individual alerts to be closed in the UI.
- The `cssClass()` method returns a corresponding bootstrap alert class for each of the alert types, if you're using something other than bootstrap you can change the CSS classes returned to suit your application.

```typescript
import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { Subscription } from 'rxjs';

import { Alert, AlertType } from '@app/_models';
import { AlertService } from '@app/_services';

@Component({ selector: 'alert', templateUrl: 'alert.component.html' })
export class AlertComponent implements OnInit, OnDestroy {
    @Input() id = 'default-alert';
    @Input() fade = true;

    alerts: Alert[] = [];
    alertSubscription: Subscription;
    routeSubscription: Subscription;

    constructor(private router: Router, private alertService: AlertService) { }

    ngOnInit() {
        // subscribe to new alert notifications
        this.alertSubscription = this.alertService.onAlert(this.id)
            .subscribe(alert => {
                // clear alerts when an empty alert is received
                if (!alert.message) {
                    // filter out alerts without 'keepAfterRouteChange' flag
                    this.alerts = this.alerts.filter(x => x.keepAfterRouteChange);

                    // remove 'keepAfterRouteChange' flag on the rest
                    this.alerts.forEach(x => delete x.keepAfterRouteChange);
                    return;
                }

                // add alert to array
                this.alerts.push(alert);

                // auto close alert if required
                if (alert.autoClose) {
                    setTimeout(() => this.removeAlert(alert), 3000);
                }
           });

        // clear alerts on location change
        this.routeSubscription = this.router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                this.alertService.clear(this.id);
            }
        });
    }

    ngOnDestroy() {
        // unsubscribe to avoid memory leaks
        this.alertSubscription.unsubscribe();
        this.routeSubscription.unsubscribe();
    }

    removeAlert(alert: Alert) {
        // check if already removed to prevent error on auto close
        if (!this.alerts.includes(alert)) return;

        if (this.fade) {
            // fade out alert
            alert.fade = true;

            // remove alert after faded out
            setTimeout(() => {
                this.alerts = this.alerts.filter(x => x !== alert);
            }, 250);
        } else {
            // remove alert
            this.alerts = this.alerts.filter(x => x !== alert);
        }
    }

    cssClass(alert: Alert) {
        if (!alert) return;

        const classes = ['alert', 'alert-dismissable', 'mt-4', 'container'];
                
        const alertTypeClass = {
            [AlertType.Success]: 'alert alert-success',
            [AlertType.Error]: 'alert alert-danger',
            [AlertType.Info]: 'alert alert-info',
            [AlertType.Warning]: 'alert alert-warning'
        }

        classes.push(alertTypeClass[alert.type]);

        if (alert.fade) {
            classes.push('fade');
        }

        return classes.join(' ');
    }
}
```

###  Alert Model and Alert Type Enum

The Alert model defines the properties of each alert object, and the AlertType enum defines the types of alerts allowed in the application.

```typescirpt
/**
 *  Properties of the alert
 */
export class Alert {
    id: string;
    type: AlertType;
    message: string;
    autoClose: boolean;
    keepAfterRouteChange: boolean;
    fade: boolean;

    constructor(init?:Partial<Alert>) {
        Object.assign(this, init);
    }
}

/**
 * Type of the Alert
 */
export enum AlertType {
    Success,
    Error,
    Info,
    Warning
}
```

## Auth Guard
Path: /src/app/_helpers/auth.guard.ts

**The auth guard is an angular route guard that's used to prevent unauthenticated users from accessing restricted routes**.  
By implementing the `CanActivate` interface which allows the guard to decide if a route can be activated with the `canActivate()` method.  
If the method returns true the route is activated (allowed to proceed), otherwise if the method returns false the route is blocked.  

The auth guard uses the `AccountService` to check if the user is logged in, if they are logged in it returns `true` from the` canActivate()` method, otherwise it returns `false` and redirects the user to the login page along with the returnUrl in the query parameters.

```typescript
import { Injectable } from '@angular/core';

// Angular route guards are attached to routes in the router config
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

// check if user is logged in
import { AccountService } from '@app/_services';

/**
 * this auth guard is used in app-routing.module.ts to protect the home page route.
 */
@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private accountService: AccountService
    ) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const user = this.accountService.userValue;
        
        // the User is a authenticated User then 
        if (user) {
            return true;
        }

        // The user is not a authenticated User then redirects to /account/login
        this.router.navigate(['/account/login'], { queryParams: { returnUrl: state.url }});
        return false;
    }
}
```

## Error Interceptor

The Error Interceptor intercepts http responses from the api to check if there were any errors. 

- It's implemented using the Angular `HttpInterceptor` interface included in the `HttpClientModule`, by implementing the `HttpInterceptor` interface you can create a custom interceptor to catch all error responses from the server in a single location.

- Http interceptors are added to the request pipeline in the providers section of the app.module.ts file.  
```typescript 
import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AccountService } from '@app/_services';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private accountService: AccountService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        return next.handle(request).pipe(catchError(err => {
            /**
             * If there is a 401 Unauthorized or 403 Forbidden response 
             *    the user is automatically logged out of the application.
             */
            if ([401, 403].includes(err.status) && this.accountService.userValue) {
                // auto logout if 401 or 403 response returned from api
                this.accountService.logout();
            }
            
            /**
             * all other errors are re-thrown up to the calling service 
             *     so an alert with the error can be displayed on the screen.
             */
            const error = err.error?.message || err.statusText;
            console.error(err);
            return throwError(error);
        }))
    }
}
```


## Server

### Account Service
The account service handles communication between the Angular app and the backend api for everything related to accounts.  
It contains methods for the login, logout and registration, as well as and standard CRUD methods for retrieving and modifying user data.  

- On successful login the returned user is stored in browser __local storage__ to keep the user logged in between page refreshes and browser sessions
- **The user property exposes an RxJS observable (`Observable<User>`) so any component can subscribe to be notified when a user logs in, logs out or updates their profile. **
- The notification is triggered by the call to `this.userSubject.next()` from each of those methods.
- The userValue getter method allows other components to easily get the current value of the logged in user without having to subscribe to the user observable.
```typescript
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '@environments/environment';
import { User } from '@app/_models';

@Injectable({ providedIn: 'root' })
export class AccountService {
    private userSubject: BehaviorSubject<User>;
    public user: Observable<User>;

    constructor(
        private router: Router,
        private http: HttpClient
    ) {
        this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')));
        
        /expose the user / 
        this.user = this.userSubject.asObservable();
    }

    public get userValue(): User {
        return this.userSubject.value;
    }

    login(username, password) {
        return this.http.post<User>(`${environment.apiUrl}/users/authenticate`, { username, password })
            .pipe(map(user => {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('user', JSON.stringify(user));
                this.userSubject.next(user);
                return user;
            }));
    }

    logout() {
        // remove user from local storage and set current user to null
        localStorage.removeItem('user');
        this.userSubject.next(null);
        this.router.navigate(['/account/login']);
    }

    register(user: User) {
        return this.http.post(`${environment.apiUrl}/users/register`, user);
    }

    getAll() {
        return this.http.get<User[]>(`${environment.apiUrl}/users`);
    }

    getById(id: string) {
        return this.http.get<User>(`${environment.apiUrl}/users/${id}`);
    }

    update(id, params) {
        return this.http.put(`${environment.apiUrl}/users/${id}`, params)
            .pipe(map(x => {
                // update stored user if the logged in user updated their own record
                if (id == this.userValue.id) {
                    // update local storage
                    const user = { ...this.userValue, ...params };
                    localStorage.setItem('user', JSON.stringify(user));

                    // publish updated user to subscribers
                    this.userSubject.next(user);
                }
                return x;
            }));
    }

    delete(id: string) {
        return this.http.delete(`${environment.apiUrl}/users/${id}`)
            .pipe(map(x => {
                // auto logout if the logged in user deleted their own record
                if (id == this.userValue.id) {
                    this.logout();
                }
                return x;
            }));
    }
}
```


### Alert Service
Path: /src/app/_services/alert.service.ts

- The alert service acts as the bridge between any component in an Angular application and the alert component that actually displays the alert messages. It contains methods for sending, clearing and subscribing to alert messages.

- The service uses the RxJS Observable and Subject classes to enable communication with other components
```
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { filter } from 'rxjs/operators';

import { Alert, AlertType } from '@app/_models';

@Injectable({ providedIn: 'root' })
export class AlertService {
    private subject = new Subject<Alert>();
    private defaultId = 'default-alert';

    // enable subscribing to alerts observable
    onAlert(id = this.defaultId): Observable<Alert> {
        return this.subject.asObservable().pipe(filter(x => x && x.id === id));
    }

    // convenience methods
    success(message: string, options?: any) {
        this.alert(new Alert({ ...options, type: AlertType.Success, message }));
    }

    error(message: string, options?: any) {
        this.alert(new Alert({ ...options, type: AlertType.Error, message }));
    }

    info(message: string, options?: any) {
        this.alert(new Alert({ ...options, type: AlertType.Info, message }));
    }

    warn(message: string, options?: any) {
        this.alert(new Alert({ ...options, type: AlertType.Warning, message }));
    }

    // main alert method    
    alert(alert: Alert) {
        alert.id = alert.id || this.defaultId;
        this.subject.next(alert);
    }

    // clear alerts
    clear(id = this.defaultId) {
        this.subject.next(new Alert({ id }));
    }
}
```

## Account Component/Module/Router

### Account Router (For Authenticating User or Registering)
The account routing module defines the routes for the account feature module. 
```
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutComponent } from './layout.component';
import { LoginComponent } from './login.component';
import { RegisterComponent } from './register.component';

/**
 * It includes routes for user login and registration, 
 *    and a parent route ('') for the layout component 
 *    which contains the common layout code for the account section.
 */
const routes: Routes = [
    {
        path: '', component: LayoutComponent,
        children: [
            { path: 'login', component: LoginComponent },
            { path: 'register', component: RegisterComponent }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AccountRoutingModule { }
```


### Account Module

The account module defines the feature module for the account section of the tutorial application along with metadata about the module. 

The imports specify which other angular modules are required by this module, and the declarations state which components belong to this module. For more info about angular 10 modules see https://angular.io/docs/ts/latest/guide/ngmodule.html.

The account module is hooked into the main app inside the app routing module with lazy loading.
```
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { AccountRoutingModule } from './account-routing.module';
import { LayoutComponent } from './layout.component';
import { LoginComponent } from './login.component';
import { RegisterComponent } from './register.component';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        AccountRoutingModule
    ],
    declarations: [
        LayoutComponent,
        LoginComponent,
        RegisterComponent
    ]
})
export class AccountModule { }
```


### Account Layout Component Template

The account layout component template is the root template of the account feature `/` section of the app, it contains the outer HTML for all account pages and a `<router-outlet>` for rendering the currently routed component.

```typescript 
<div class="col-md-6 offset-md-3 mt-5">
    <router-outlet></router-outlet>
</div>
``` 


### Account Layout Component

The account layout component is the root component of the account feature / section of the app, it binds the component to the account layout template with the templateUrl property of the angular @Component decorator, and automatically redirects the user to the home page if they are already logged in.

```typescript
import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AccountService } from '@app/_services';

@Component({ templateUrl: 'layout.component.html' })
export class LayoutComponent {
    constructor(
        private router: Router,
        private accountService: AccountService
    ) {
        // redirect to home if already logged in
        if (this.accountService.userValue) {
            this.router.navigate(['/']);
        }
    }
}
```


## Home Component Template

```
<div class="p-4">
    <div class="container">
        <h1>Hi {{user.firstName}}!</h1>
        <p>You're logged in with Angular 10!!</p>
        <p><a routerLink="/users">Manage Users</a></p>
    </div>
</div>
```
 
## Home Component

The home component defines an angular 10 component that gets the current logged in user from the account service and makes it available to the template via the user object property.

```typescript
import { Component } from '@angular/core';

import { User } from '@app/_models';
import { AccountService } from '@app/_services';

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent {
    user: User;
    // get user's information from account service
    constructor(private accountService: AccountService) {
        this.user = this.accountService.userValue;
    }
}
``` 


## Users Add/Edit

### Users Add/Edit Component Template

The users add/edit component template contains a dynamic form that supports both creating and updating users. 

```htmlembedden
<!-- isAddMode is true then display Add User header -->
<!-- isAddmode is false then display Edit User header -->
<h1 *ngIf="isAddMode">Add User</h1>
<h1 *ngIf="!isAddMode">Edit User</h1>
<form [formGroup]="form" (ngSubmit)="onSubmit()">
    <div class="form-row">
        <div class="form-group col">
            <label for="firstName">First Name</label>
            <input type="text" formControlName="firstName" class="form-control" [ngClass]="{ 'is-invalid': submitted && f.firstName.errors }" />
            <div *ngIf="submitted && f.firstName.errors" class="invalid-feedback">
                <div *ngIf="f.firstName.errors.required">First Name is required</div>
            </div>
        </div>
        <div class="form-group col">
            <label for="lastName">Last Name</label>
            <input type="text" formControlName="lastName" class="form-control" [ngClass]="{ 'is-invalid': submitted && f.lastName.errors }" />
            <div *ngIf="submitted && f.lastName.errors" class="invalid-feedback">
                <div *ngIf="f.lastName.errors.required">Last Name is required</div>
            </div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col">
            <label for="username">Username</label>
            <input type="text" formControlName="username" class="form-control" [ngClass]="{ 'is-invalid': submitted && f.username.errors }" />
            <div *ngIf="submitted && f.username.errors" class="invalid-feedback">
                <div *ngIf="f.username.errors.required">Username is required</div>
            </div>
        </div>
        <div class="form-group col">
            <label for="password">
                Password
                <em *ngIf="!isAddMode">(Leave blank to keep the same password)</em>
            </label>
            <input type="password" formControlName="password" class="form-control" [ngClass]="{ 'is-invalid': submitted && f.password.errors }" />
            <div *ngIf="submitted && f.password.errors" class="invalid-feedback">
                <div *ngIf="f.password.errors.required">Password is required</div>
                <div *ngIf="f.password.errors.minlength">Password must be at least 6 characters</div>
            </div>
        </div>
    </div>
    <div class="form-group">
        <button [disabled]="loading" class="btn btn-primary">
            <span *ngIf="loading" class="spinner-border spinner-border-sm mr-1"></span>
            Save
        </button>
        <a routerLink="/users" class="btn btn-link">Cancel</a>
    </div>
</form>
```

### Users Add/Edit Component

The users add/edit component is used for both adding and editing users in the angular tutorial app, the component is in "add mode" when there is no user id route parameter, otherwise it is in "edit mode". 

On submit a user is either created or updated by calling the account service, and on success you are redirected back to the users list page with a success message.
```typescript
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
// received User Inpu data 
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AccountService, AlertService } from '@app/_services';

@Component({ templateUrl: 'add-edit.component.html' })
export class AddEditComponent implements OnInit {
    form: FormGroup;
    id: string;
    isAddMode: boolean;
    loading = false;
    submitted = false;
    
    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private accountService: AccountService,
        private alertService: AlertService
    ) {}
 

    ngOnInit() {
        this.id = this.route.snapshot.params['id'];
        this.isAddMode = !this.id;
        
        // password not required in edit mode
        const passwordValidators = [Validators.minLength(6)];
        
        
        // In add Mode service requires password 
        if (this.isAddMode) {
            passwordValidators.push(Validators.required);
        }
        
        /**
         *  Initilize the Form
         */
        this.form = this.formBuilder.group({
            firstName:['', Validators.required],
            lastName: ['', Validators.required],
            username: ['', Validators.required],
            password: ['', passwordValidators]
        });

        /**
         * In Edit Mode we need current user's information
         */
        if (!this.isAddMode) {
            this.accountService.getById(this.id)
                .pipe(first())
                .subscribe(x => this.form.patchValue(x));
        }
    }

    // convenience getter for easy access to form fields
    get f() { return this.form.controls; }

    onSubmit() {
        this.submitted = true;

        // reset alerts on submit
        this.alertService.clear();

        // stop here if form is invalid
        if (this.form.invalid) {
            return;
        }

        this.loading = true;
        if (this.isAddMode) {
            this.createUser();
        } else {
            this.updateUser();
        }
    }

    private createUser() {
        this.accountService.register(this.form.value)
            .pipe(first())
            .subscribe({
                next: () => {
                    this.alertService.success('User added successfully', { keepAfterRouteChange: true });
                    this.router.navigate(['../'], { relativeTo: this.route });
                },
                error: error => {
                    this.alertService.error(error);
                    this.loading = false;
                }
            });
    }

    private updateUser() {
        this.accountService.update(this.id, this.form.value)
            .pipe(first())
            .subscribe({
                next: () => {
                    this.alertService.success('Update successful', { keepAfterRouteChange: true });
                    this.router.navigate(['../../'], { relativeTo: this.route });
                },
                error: error => {
                    this.alertService.error(error);
                    this.loading = false;
                }
            });
    }
}
```

## Users (When User is log in) Routing Module
Path: /src/app/users/users-routing.module.ts

The add and edit routes are different but both load the same component (AddEditComponent) which modifies its behaviour based on the route.
- **A parent route for the layout component which contains the common layout code for the users section.**
```
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutComponent } from './layout.component';
import { ListComponent } from './list.component';
import { AddEditComponent } from './add-edit.component';

const routes: Routes = [
    {
        path: '', component: LayoutComponent,
        children: [
            { path: '', component: ListComponent },
            { path: 'add', component: AddEditComponent },
            { path: 'edit/:id', component: AddEditComponent }
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class UsersRoutingModule { }
``` 

## Users Module

The users module defines the feature module for the users section of the tutorial application along with metadata about the module.  

The imports specify which other angular modules are required by this module, and the declarations state which components belong to this module.

The users module is hooked into the main app inside the app routing module with lazy loading.
```typescript
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { LayoutComponent } from './layout.component';
import { ListComponent } from './list.component';
import { AddEditComponent } from './add-edit.component';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        UsersRoutingModule
    ],
    declarations: [
        LayoutComponent,
        ListComponent,
        AddEditComponent
    ]
})
export class UsersModule { }
```

## App Routing Module
The app routing module defines the top level routes for the angular application and generates a root routing module by passing the array of routes to the `RouterModule.forRoot()` method.  

- The home route maps the root path of the app to the home component, the users route maps to the users module and the account route maps to the account module, both feature module routes (`/users` and `/account`) are lazy loaded.
- The home `'/'` and users `'/users` routes are secured by passing the auth guard to the `canActivate` property of each route.

```
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home';
import { AuthGuard } from './_helpers';

const accountModule = () => import('./account/account.module').then(x => x.AccountModule);
const usersModule = () => import('./users/users.module').then(x => x.UsersModule);

const routes: Routes = [
    
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'users', loadChildren: usersModule, canActivate: [AuthGuard] },
    { path: 'account', loadChildren: accountModule },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
```


[Codes](https://jasonwatmore.com/post/2020/07/18/angular-10-user-registration-and-login-example-tutorial)
