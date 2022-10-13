# Dependency Injection

- [Dependency Injection](#dependency-injection)
  - [Reference](#reference)
  - [Providers](#providers)
    - [Providers in Component or Module](#providers-in-component-or-module)
  - [`provide`'s token type](#provides-token-type)
    - [Type Token](#type-token)
    - [String Token](#string-token)
  - [Provider's type](#providers-type)
    - [useClass](#useclass)
    - [useValue](#usevalue)
    - [`InjectionToken<T>` and `useValue`](#injectiontokent-and-usevalue)
    - [UseFactory](#usefactory)
  - [`useExisting` provider](#useexisting-provider)
  - [`@Optional()`](#optional)

The Angular creates an `Injector` for each component/directive it creates.   
There are two types Injector   
- A root-level `injector`, which has the app-level scope.    
- A Module level `Injector` for **Lazy Loaded Modules**.   

The Angular Components or Angular Services declare the dependencies they need in their constructor

1. The `Injector` reads the dependencies and looks for the provider in the providers array using the (given `provide`)Token. 
2. It then instantiates the dependency using the instructions provided by the provider.   
3. The `Injector` then injects the instance of the dependency into the Components/Services.  


## Reference

- [[Angular 大師之路] Day 21 - 在 @NgModule 的 providers: [] 自由更換注入內容 (2)](https://ithelp.ithome.com.tw/articles/10208240)
- [Angular Providers: useClass, useValue, useFactory & useExisting](https://www.tektutorialshub.com/angular/angular-providers/)
## Providers

Providers array contains two important elements `provide` and `provider`
```typescript
providers : [
  {provide : Token , provider : Inject-Instance }
]
```
- `provide`'s token helps `Injector` locate the `provider` in the Providers array. 

### Providers in Component or Module

- Providers of Module : **Inject an Singleton DI instance shared by All its declaration components**
- Providers of Component : Inject an DI instance shared by the component only **(each DI instance shared by that component instance itself only)**
```typescript
// Module
@NgModule({
  ...
  providers : [
    {provide : xxxService , provider : xxxService}
  ]
})
export class AppModule {}

// Component
@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  providers: [ProductService]
})
export class AppComponent {}
```


## `provide`'s token type 

### Type Token 

```typescript
// Create Provider
import { Injectable } from '@angular/core';

@Injectable()
export class ProductService {
  constructor() { }
}

// Register provider in module/component's providers array
providers :[{ provide: ProductService, useClass: ProductService }]

// DI 
export class xxxComponent{
  constructor(private productService : ProductService)
}
```
### String Token

```typescript
// Providers From Module
providers: [
 {provide:'PRODUCT_SERVICE', useClass: ProductService },
 // or {provide : `productService`, useClass: ProductService} 
 {provide:'USE_FAKE', useValue: true },   
 {provide:'APIURL', useValue: 'http://SomeEndPoint.com/api' },
]

// component
class ProductComponent {
  constructor(@Inject('PRODUCTSERVICE') private prdService:ProductService,
              @Inject('APIURL') private apiURL:string )
}
```
## Provider's type 
### useClass

Provider is class
```typescript
//  ProductService is the Token (or key) and it maps to the ProductService Class.
providers :[{ provide: ProductService, useClass: ProductService }]

// shortcut in cases where both token & class name matches as follows
providers: [ProductService]
```

Multiple Providers with the same token, it uses the last one
```typescript
providers: [
    { provide: ProductService, useClass: ProductService },
    { provide: ProductService, useClass: FakeProductService },
  ]
```
- `FakeProductService` will be used 


Switching Dependencies
```typescript 
providers :[{ provide: ProductService, useClass: otherProductService }]
```
- `productService`'s token will inject `otherProductService` DI instance


### useValue

Boolean Value
```typescript
// provide boolean value injectable dependency
providers :[ {provide:'USE_FAKE', useValue: true}]

export class AppComponent {
  constructor(
    @Inject('USE_FAKE') public useFake: string
  ) {}
```

Object Type
```typescript
// freeze : immutable
const APP_CONFIG =  Object.freeze({
  serviceURL: 'service1234.com',
  IsDevelopmentMode: true
});

providers: [
  { provide: 'APP_CONFIG', useValue: APP_CONFIG }
]
```

Function
```typescript
// providers in module
providers: [
    {
      provide: 'FUNC',
      useValue: () => {
        return 'hello';
      }
    }
]

// inject DI token in component
export class AppComponent {
  constructor(
    @Inject('FUNC') public someFunc: any
  ) {
    console.log(someFunc());
  }
}
```


### `InjectionToken<T>` and `useValue`

**The Angular provides `InjectionToken<T>(T initializeValue)` class so as to ensure that the Unique tokens for constant object type are created.**



Injection Token as a Class
```typescript
import { InjectionToken } from '@angular/core';

export const API_URL= new InjectionToken<string>('');

export const HERO_DI_CONFIG: AppConfig = {
  apiEndpoint: 'api.heroes.com',
  title: 'Dependency Injection'
};
export const APP_CONFIG = new InjectionToken<AppConfig>('app.config'); 
```
Register the token in the providers array.
```typescript

providers: [ 
    { provide: API_URL, useValue: 'http://SomeEndPoint.com/api' },
    { provide: APP_CONFIG, useValue: HERO_DI_CONFIG }
]
```
Using the `@Inject` inject the instance
```typescript
constructor(
  @Inject(API_URL) private apiURL: string,
  @Inject(APP_CONFIG) config: AppConfig) {
      this.title = config.title;
      this.apiURL= apiURL;
  }
```

### UseFactory

`useFactory` invokes the function and injects the returned value. 

We can also add optional arguments to the factory function using the deps array. 
```typescript
providers : [
  { 
    provide: token | 'tokenAlias', 
    useFactory: xxxFunction,
    deps: [tokenX, tokenY, ...] 
  }
]

// for example
providers: [
  { provide: LoggerService, useClass: LoggerService },

  { provide: 'USE_OTHER', useValue: true },

  {
    provide: ProductService,
    // use FakeProductService if USER_FAKE is true
    useFactory: (USE_OTHER, LoggerService) =>
      USE_FAKE ? new FakeProductService() : new ProductService(LoggerService),
    deps: ['USER_OTHER', LoggerService]
  }
]
```

The useFactory invokes the factory function and returns the result.
```java
// im module's ngModule providers array
providers: [
  {
    provide: 'FUNC',
    useFactory: () => {
      return 'hello';
    }
  }
]
 
// inject the dependency's token name
export class AppComponent {
  constructor(
    @Inject('FUNC') public someFunc: any
  ) {
    console.log(someFunc);
  }
}
```


## `useExisting` provider

Use Aliased Provider `useExisting` when you want to use the new provider in place of the old Provider.
```typescript
// useExisting : Class
providers: [
  // provide token productService 
  // using token named NewProductService
  { provide: ProductService, useExisting: NewProductService },
  { provide: NewProductService, useClass: NewProductService },
]

// or
providers: [
  NewProductService,
  { provide: ProductService, useExisting: NewProductService },
]

// useExisting : String Token Name
providers: [
  { provide: ProductService, useExisting: 'PRODUCT_SERVICE' },
  { provide: 'PRODUCT_SERVICE', useClass: NewProductService },
]
```
## `@Optional()`

`optional()` : If provider is not available , it returns `null` not error 
```typescript   
import { Optional } from '@angular/core';

/**
 *  該service DI is optional
 */
constructor(@Optional() private logger: Logger) {
  
  // if logger is not null
  if (this.logger) {
    this.logger.log(some_message);
  }
}
```

