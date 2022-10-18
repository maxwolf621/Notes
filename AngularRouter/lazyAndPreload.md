# Lazy Loading & Pre-Loading

- [Lazy Loading & Pre-Loading](#lazy-loading--pre-loading)
  - [Lazy Loading route configuration](#lazy-loading-route-configuration)
  - [CanLoad](#canload)
  - [Preloading: background loading of feature areas](#preloading-background-loading-of-feature-areas)
  - [Custom Preloading Strategy](#custom-preloading-strategy)
  - [Migrating URLs with redirects](#migrating-urls-with-redirects)
    - [Changing `/heroes` to `/superheroes`](#changing-heroes-to-superheroes)
  - [Inspect the router's configuration (Router#config)](#inspect-the-routers-configuration-routerconfig)

Lazy loading has multiple benefits.
1. Load feature areas **only when requested** by the user
2. Speed up load time for users that only visit certain areas of the application
3. **Continue expanding lazy loaded feature areas without increasing the size of the initial load bundle**


## Lazy Loading route configuration

```typescript
// xxx.routing.module.ts
const adminRoutes: Routes = [
  {
    path: '', // <--- should be empty string
    component: AdminComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        canActivateChild: [AuthGuard],
        children: [
          { path: 'crises', component: ManageCrisesComponent },
          { path: 'heroes', component: ManageHeroesComponent },
          { path: '', component: AdminDashboardComponent }
        ]
      }
    ]
  }
];

```

Give `app-routing.module.ts` a loadChildren property instead of a children property. 
```typescript
// app-routing.module.ts
{
  path: 'xxx',
  loadChildren: () => import('./xxx/xxx.module').then(m => m.xxxModule),
},
```

In `app.module.ts`, remove the `xxxModule` import statement from the top of the file and remove the `xxxModule` from the NgModule's imports array.


The `loadChildren` property takes a function that returns a `promise` using the browser's built-in syntax for lazy loading code using dynamic imports `import('./xxx/xxx.module')`. 

The path is the location of the `xxxModule` (relative to the application root). 

After the code is requested and loaded, the `Promise` resolves an object that contains the NgModule, in this case the `xxxModule`.

**When the router navigates to this route, it uses the `loadChildren` string to dynamically load the `xxxModule`.   
Then it adds the `xxxModule` routes to its current route configuration.**


## CanLoad

**CanLoad : canActivate for `loadChildren` property.**

Avoid the route loads the `xxxModule` if the user can't visit any of its components.
Only load AdminModule once if the user is logged in
```typescript
// src/app/auth/auth.guard.ts (CanLoad guard)
canLoad(route: Route): boolean {
  const url = `/${route.path}`;

  return this.checkLogin(url);
}
```
Add it in loadChildren routes
```typescript
{
  path: 'admin',
  loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
  canLoad: [AuthGuard]
},
```

## Preloading: background loading of feature areas

The Router offers two preloading strategies:
1. No preloading(default) : Lazy loaded feature areas are still loaded on-demand.
2. Preloading	All lazy loaded feature areas are preloaded.

PS. **The Router also supports custom preloading strategies for fine control over which modules to preload and when.**

In addition to loading modules on-demand, you can load modules asynchronously with preloading.

The `AppModule` is eagerly loaded when the application starts, meaning that it loads right away. 

**Preloading lets you load modules in the background so that the data is ready to render when the user activates a particular route**. 

---

For example, consider the Crisis Center. It isn't the first view that a user sees.  
By default, the Heroes are the first view. 
The following example guides you through updating the `CrisisCenterModule` to load lazily by default and use the `PreloadAllModules` strategy to load all lazy loaded modules.


To enable preloading of all lazy loaded modules, import the `PreloadAllModules` token from the Angular router package.
- The second argument in the `RouterModule.forRoot()` method takes an object for additional configuration options. 
- This configures the Router preloader to immediately load all lazy loaded routes (routes with a `loadChildren` property).
```typescript
RouterModule.forRoot(
  appRoutes,
  {
    enableTracing: true, // <-- debugging purposes only
    preloadingStrategy: PreloadAllModules
  }
)
```
When you visit `http://localhost:4200`, the `/heroes` route loads immediately upon launch and the router starts loading the `CrisisCenterModule`(<- lazy load module) right after the `HeroesModule` loads.  
Currently, the `AdminModule` does not preload because `CanLoad` is blocking it.

**The `PreloadAllModules` strategy does not load feature areas protected by a `CanLoad` guard.**

If you want to preload a module as well as guard against unauthorized access, remove the `canLoad()` guard method and rely on the `canActivate()` guard alone.


## Custom Preloading Strategy

Preloading every lazy loaded module works well in many situations. 

However, in consideration of things such as low bandwidth and user metrics, you can use a custom preloading strategy for specific feature modules.

Set the `data.preload` flag in the `crisis-center` route in the `AppRoutingModule`.
```typescript
// src/app/app-routing.module.ts (route data preload)
{
  path: 'crisis-center',
  loadChildren: () => import('./crisis-center/crisis-center.module').then(m => m.CrisisCenterModule),
  data: { preload: true }
},
```

Generate a new `SelectivePreloadingStrategy` service.
```bash
ng generate service selective-preloading-strategy
```
```typescript
// src/app/selective-preloading-strategy.service.ts
import { Injectable } from '@angular/core';
import { PreloadingStrategy, Route } from '@angular/router';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SelectivePreloadingStrategyService implements PreloadingStrategy {
  preloadedModules: string[] = [];

  preload(route: Route, load: () => Observable<any>): Observable<any> {
    if (route.data?.['preload'] && route.path != null) {
      // add the route path to the preloaded module array
      this.preloadedModules.push(route.path);

      // log the route path to the console
      console.log('Preloaded: ' + route.path);

      return load();
    } else {
      return of(null);
    }
  }
}
```

```typescript
SelectivePreloadingStrategyService implements PreloadingStrategy#preload()
```
The router calls the `preload(route: Route, load: () => Observable<any>) : Observable<any>` method with two arguments:

1. The route to consider.
2. A loader function that can load the routed module asynchronously.

An implementation of preload must return an Observable. 
- If the route does preload, it returns the observable returned by calling the loader function. 
- If the route does not preload, it returns an Observable of `null`.

In this sample, the `preload()` method loads the route if the route's `data.preload` flag is truthy.

As a side effect, SelectivePreloadingStrategyService logs the path of a selected route in its public preloadedModules array.

Shortly, you'll extend the AdminDashboardComponent to inject this service and display its preloadedModules array.

But first, make a few changes to the AppRoutingModule.
1. Import `SelectivePreloadingStrategyService` into `AppRoutingModule`.
2. Replace the `PreloadAllModules` strategy in the call to `forRoot()` with this `SelectivePreloadingStrategyService`.

Now edit the AdminDashboardComponent to display the log of preloaded routes.

1. Import the `SelectivePreloadingStrategyService`.
2. Inject it into the dashboard's constructor.
3. Update the template to display the strategy service's preloadedModules array.
```typescript
@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  sessionId!: Observable<string>;
  token!: Observable<string>;
  modules: string[] = [];

  constructor(
    private route: ActivatedRoute,
    // CUSTOM PRELOAD STRATEGY
    preloadStrategy: SelectivePreloadingStrategyService
  ) {
    this.modules = preloadStrategy.preloadedModules;
  }

  ngOnInit() {
    // Capture the session ID if available
    this.sessionId = this.route
      .queryParamMap
      .pipe(map(params => params.get('session_id') || 'None'));

    // Capture the fragment if available
    this.token = this.route
      .fragment
      .pipe(map(fragment => fragment || 'None'));
  }
}
```
Once the application loads the initial route, the `CrisisCenterModule` is preloaded. 

Verify this by logging in to the Admin feature area and noting that the crisis-center is listed in the Preloaded Modules. It also logs to the browser's console.

## Migrating URLs with redirects

You've set up the routes for navigating around your application and used navigation imperatively and declaratively. 

But like any application, requirements change over time. 

You've setup links and navigation to `/heroes` and `/hero/:id` from the `HeroListComponent` and `HeroDetailComponent` components. 

If there were a requirement that links to heroes become superheroes, you would still want the previous URLs to navigate correctly. 

**You also don't want to update every link in your application, so redirects makes refactoring routes trivial.**

### Changing `/heroes` to `/superheroes`

Migrating the Hero routes to new URLs. 

The Router checks for redirects in your configuration before navigating, so each redirect is triggered when needed. 

To support this change, **add redirects from the old routes to the new routes in the heroes-routing.module.**

```typescript
const heroesRoutes: Routes = [
  // Add redirectTo to Migrating router to new URLs
  { path: 'heroes', redirectTo: '/superheroes' },
  { path: 'hero/:id', redirectTo: '/superhero/:id' },
  { path: 'superheroes',  component: HeroListComponent, data: { animation: 'heroes' } },
  { path: 'superhero/:id', component: HeroDetailComponent, data: { animation: 'hero' } }
];

@NgModule({
  imports: [
    RouterModule.forChild(heroesRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class HeroesRoutingModule { }
```


The Router also supports query parameters and the fragment when using redirects.
- When using absolute redirects, the Router uses the query parameters and the fragment from the `redirectTo` in the route config
- When using relative redirects, the Router use the query params and the fragment from the source URL

Currently, the empty path `''` route redirects to `/heroes`, which redirects to `/superheroes`.   
This won't work because the Router handles redirects once at each level of routing configuration. 

This prevents chaining of redirects, which can lead to endless redirect loops.
Instead, update the empty path route in `app-routing.module.ts` to redirect to `/superheroes`.
```typescript
const appRoutes: Routes = [
  // ...
  ,
  { path: '',   redirectTo: '/superheroes', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];
```

**A `routerLink` isn't tied to route configuration**, so update the associated router links to remain active when the new route is active.   
Update the app.component.ts template for the `/heroes` routerLink.
```html
<div class="wrapper">
  <h1 class="title">Angular Router</h1>
  <nav>
    <a routerLink="/crisis-center" routerLinkActive="active" ariaCurrentWhenActive="page">Crisis Center</a>
    <!-- update the router-link from hero to superhero -->
    <a routerLink="/superheroes" routerLinkActive="active" ariaCurrentWhenActive="page">Heroes</a>
    <a routerLink="/admin" routerLinkActive="active" ariaCurrentWhenActive="page">Admin</a>
    <a routerLink="/login" routerLinkActive="active" ariaCurrentWhenActive="page">Login</a>
    <a [routerLink]="[{ outlets: { popup: ['compose'] } }]">Contact</a>
  </nav>
  <div [@routeAnimation]="getRouteAnimationData()">
    <router-outlet></router-outlet>
  </div>
  <router-outlet name="popup"></router-outlet>
</div>
```

Update the `goToHeroes()` method in the `hero-detail.component.ts` to navigate back to `/superheroes` with the optional route parameters.

```typescript
gotoHeroes(hero: Hero) {
  const heroId = hero ? hero.id : null;
  this.router.navigate(['/superheroes', { id: heroId, foo: 'foo' }]);
}
```
With the redirects setup, all previous routes now point to their new destinations and both URLs still function as intended.

## Inspect the router's configuration (Router#config)

To determine if your routes are actually evaluated in the proper order, you can inspect the router's configuration.

Do this by injecting the router and logging to the console its config property. 

For example, update the `AppModule` as follows and look in the browser console window to see the finished route configuration.
```typescript
export class AppModule {
  // Diagnostic only: inspect router configuration
  constructor(router: Router) {
    // Use a custom replacer to display function names in the route configs
    const replacer = (key, value) => (typeof value === 'function') ? value.name : value;

    console.log('Routes: ', JSON.stringify(router.config, replacer, 2));
  }
}
```