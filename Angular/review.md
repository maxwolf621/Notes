# Review

- [Review](#review)
  - [ngTemplateOutlet](#ngtemplateoutlet)
  - [Directive](#directive)
    - [Attribute directives `[ngXXXXX]`](#attribute-directives-ngxxxxx)
      - [ngStyle & ngClass](#ngstyle--ngclass)
  - [ElementRef & TemplateRef & View Ref & ViewContainerRef](#elementref--templateref--view-ref--viewcontainerref)
  - [ContentChild(ren) / ViewChild(ren)](#contentchildren--viewchildren)
  - [Router](#router)
    - [queryParams & Fragment](#queryparams--fragment)
    - [queryParams`Handling` & `preserve`Fragment](#queryparamshandling--preservefragment)
    - [named router (secondary router-outlet) & clear named-router](#named-router-secondary-router-outlet--clear-named-router)
    - [Resolve: pre-fetching component data](#resolve-pre-fetching-component-data)
    - [Lazy Loading](#lazy-loading)
    - [`canLoad()`](#canload)
    - [Preloading](#preloading)
    - [Custom Preloading](#custom-preloading)
    - [inspect the router configuration](#inspect-the-router-configuration)

## ngTemplateOutlet 

`ngTemplateOut = TemplateRef + @ViewChild + viewCOntainerRef + ComponentFactoryResolver`

```html 
<ng-template #TemplateReference DIRECTIVE let-input="$Implicit" let-variable="variable">
  ...                ^           ^
  ....                \         /
</ng-template>         \       / 
                        \     /
<ele *ngTemplateOutlet = "OBJ; context: obj" ></ele>
```
```typescript
let obj = {
    // variable : values
    $implicit : {     
        fieldA : a
        fieldB : b
        fieldC : C },
    variable : "otherProperty"
}
```

## Directive

**You can change the appearance, behavior, or layout of a DOM element using the Directives.**

Dynamical Attribute Binding : Bind html element's properties to properties in Component.  

Static Attribute Binding : Bind html element's properties with string values directly


### Attribute directives `[ngXXXXX]`

#### ngStyle & ngClass

ngClass : bind HTML element with CSS class    
ngStyle : bind HTML element with CSS properties   

Static Binding 
```html
<element [ngClass] = "'cssClass1 cssClass2 ... cssClassN"> String </element>
<element [ngClass] = 'cssClass1 cssClass2 ... cssClassN'>String </element>
<element [ngClass] = "['cssClass1', 'cssClass2']"> Array </element>
<element [ngClass] = "{'cssClass1': true, 'cssClass2': true}"> Object </element>
```

Dynamically Binding
```html
<element [ngClass]= obj></element>
```
```typescript
let obj = {
    'cssClass1',
    'cssClass1',
    ...
}
```

## ElementRef & TemplateRef & View Ref & ViewContainerRef

`ElementRef` is an reference to HTML element `<element> .. </element>`   
`TemplateRef` is an reference to HTML element with `#` `<ng-template #xyz></ng-template>`   
`ViewContainerRef` is an reference to `@Directive` component

Directive of ViewContainerRef
```typescript
@Directive({
  selector: '[appDynamicComponentHost]'
})
export class DynamicComponentHostDirective {
  constructor(public viewContainerRef: ViewContainerRef) { }
}
```

Usage 
```html
<element DynamicComponentHostDirective></element>
```

Inject ng-template
```html
<ng-container #vc></ng-container>
<ng-template #tpRef>
  <span>I am span in template</span>
</ng-template>
```

```typescript
@ViewChild('tpRef') tplRef!: TemplateRef<any>;
@ViewChild('vc', { read: ViewContainerRef }) vc!: ViewContainerRef;

ngAfterViewInit() {
  const elem = this.tplRef.createEmbeddedView(null);
  this.vc.insert(elem)
```

## ContentChild(ren) / ViewChild(ren)

- `<ng-content>` : Child Template 內的`<ng-content></ng-content>` = Parent Template要嵌入的地方     

- `@ViewChild` : 從View裡取得特定Component/Template variable Reference的實體給Component用      

- `@ContentChild` : Child Component可以操作`<ng-content></ng-content>` Parent Template嵌入的元素  


```typescript 
<input #nameInput [(ngModel)]="name">

@ContentChild('nameInput',{static:false, read: NgModel}) 
nameVarAsNgModel;

@ContentChild('nameInput',{static:false, read: ElementRef})
nameVarAsElementRef;

@ContentChild('nameInput', {static:false, read: ViewContainerRef })
nameVarAsViewContainerRef;
```


## Router


### queryParams & Fragment

You can use query parameters to get **optional parameters available to ALL routes**.  
**Fragments refer to certain elements on the page identified with an id attribute.**

```typescript
// AuthGuard.ts

// Create a dummy session id
const sessionId = 123456789;
// Set our navigation extras object
// that contains our global query params and fragment
const navigationExtras: NavigationExtras = {
  queryParams: { session_id: sessionId },
  fragment: 'anchor',
};
// Navigate to the login page with extras
this.router.navigate(['/login'], navigationExtras);
return false;
```

### queryParams`Handling` & `preserve`Fragment

In the LoginComponent, you'll add an object as the second argument in the `router.navigate()` function and provide the `queryParamsHandling` and `preserveFragment` to pass along the current query parameters and fragment to the next route.

```typescript
// LoginComponent.ts 

const navigationExtras: NavigationExtras = {
  queryParamsHandling: 'preserve',
  preserveFragment: true
};

// Redirect the user
this.router.navigate([redirectUrl], navigationExtras);
```

### named router (secondary router-outlet) & clear named-router

```html
<router-outlet></router-outlet>
...
...
<router-outlet name="outletPropertyName"></router-outlet>
```

```typescript
const appRoutes: Routes = [
  {
    path: 'xxx',
    component: xxxComponent,
    // This route now targets the popup outlet 
    // and the ComposeMessageComponent will display there.
    outlet: 'outletPropertyName',
  },
```

```typescript
closePopup() {
  //                          named-outlet:null 
  this.router.navigate([{ outlets: { popup: null }}]);
}
```

### Resolve: pre-fetching component data

```typescript
export class CrisisDetailResolverService implements Resolve<Crisis> {
  constructor(private crisisService: CrisisService, private router: Router) {}

  // Event to be subscribed
  resolve(route: ActivatedRouteSnapshot, 
          state: RouterStateSnapshot):
          Observable<Crisis> | Observable<never> 
  {
    const id = route.paramMap.get('id')!;

    return this.crisisService.getCrisis(id).pipe(
      mergeMap(crisis => {
        if (crisis) {
          return of(crisis);
        } else { // id not found
          this.router.navigate(['/crisis-center']);
          return EMPTY;
        }
      })
    );
  }
}
```
```typescript
const routes : Routes = [
  {
    path: ':id',
    component: CrisisDetailComponent,
    canDeactivate: [CanDeactivateGuard],
    resolve: { // <----- subscribe resolve<data>
      crisis: CrisisDetailResolverService
    }
  },
]
```

### Lazy Loading

```typescript
// xxx.routing.module.ts
const xxxRoutes: Routes = [
  {
    path: '', // <-- Change the lazy one with empty path
    component: AdminComponent,
    canActivate: [AuthGuard],
    children: [
      {
        //....
      }
    ]
  }
];

// App.routing.module.ts
{
  path: 'xxx',
  loadChildren: () => import('./xxx/xxx.module').then(m => m.xxxModule),
},
```

### `canLoad()`

CanLoad : canActivate for loadChildren property.

```typescript
// src/app/auth/auth.guard.ts (CanLoad guard)
canLoad(route: Route): boolean {
  const url = `/${route.path}`;

  return this.checkLogin(url);
}
```
Add it in `loadChildren` route
```typescript
{
  path: 'xxx',
  loadChildren: () => import('./xxx/xxx.module').then(m => m.xxxModule),
  canLoad: [AuthGuard]
},
```

### Preloading

The Router offers two preloading strategies:

No preloading(default) : Lazy loaded feature areas are still loaded on-demand.
Preloading All lazy loaded feature areas are preloaded.

```typescript
RouterModule.forRoot(
  appRoutes,
  {
    // Add preLoadAllModules Property
    preloadingStrategy: PreloadAllModules
  }
)
```

### Custom Preloading
```typescript
// routes set preload flag true
{
  path: '...',
  loadChildren: ...
  data: { preload: true }
}
// service for custom preload strategy
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

### inspect the router configuration

`JSON.stringify(router.config, (key,value) => (typeof key == 'function') ? value.name : value; , 2`)

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