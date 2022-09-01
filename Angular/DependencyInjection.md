###### Tags: `Angular` 

# Dependency Injection of The Service

- [Dependency Injection of The Service](#dependency-injection-of-the-service)
  - [Reference](#reference)
  - [Providers Array](#providers-array)
  - [Token (`provide`)](#token-provide)
    - [Type Token](#type-token)
    - [String Token](#string-token)
    - [Singleton String Token](#singleton-string-token)
  - [The Types of Provider](#the-types-of-provider)
    - [useClass](#useclass)
      - [Switching Dependencies](#switching-dependencies)
    - [useValue](#usevalue)
    - [UseFactory](#usefactory)
  - [Aliased class providers (useExisting)](#aliased-class-providers-useexisting)
  - [常數注入`InjectionToken<T>`](#常數注入injectiontokent)
  - [`@Optional()`表示不一定會被注入的Service](#optional表示不一定會被注入的service)
  - [(Component's Provider)分層注入系統](#components-provider分層注入系統)

The Angular creates an Injector for each component/directive it creates. 
It also creates a root-level injector, which has the app-level scope.    
It also creates a Module level Injector for **Lazy Loaded Modules**.   

The Angular Components or Angular Services declare the dependencies they need in their constructor

The Injector reads the dependencies and looks for the provider in the providers array using the Token. 

It then instantiates the dependency using the instructions provided by the provider.   
The Injector then injects the instance of the dependency into the Components/Services.  


## Reference

- [[Angular 大師之路] Day 21 - 在 @NgModule 的 providers: [] 自由更換注入內容 (2)](https://ithelp.ithome.com.tw/articles/10208240)
- [Angular Providers: useClass, useValue, useFactory & useExisting](https://ithelp.ithome.com.tw/articles/10209130)
## Providers Array

可分為兩種注入方式一種是從Module內NgModule的Provider Array另一種是Component的Provider Array    

**在某個Module內的`ngModule`'s provider array提供特定Service注入可以在該Module下任一Components裡去從Constructor取得同一個注入的Service實體而不需要再在每個Component的Provider Array額外宣告**,從Module注入到Component內的Service實體為Singleton
```typescript
@NgModule({
  // ...
  providers : [
    {provide : xxxService , provider : xxxService}
  ]
})
export class AppModule {}
```

如果需要某個Service只需要在特定Component下被使用，則可以直接在`@Component.providers`裡面Register.    
**Component DI是相互獨立的**，每個組件都創建它自己的Component提供服務的實例。故當Angular Destroy其中一個Component實體時，它也會Destroy Component的注入器和注入器的服務實體  
```typescript
@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  providers: [ProductService]
})
export class AppComponent {}
```


## Token (`provide`)

Register the injector in the `provider` array to allow the service cna be injected in the needing of module/component 
The first property is Provide holds the Token or DI Token. 

The Injector uses the token to locate the provider in the Providers array. 
- The Token can be either a type, a `string` or an instance of InjectionToken.

### Type Token 

Create an Injectable Service (be injected its singleton instance into components)
```typescript
import { Injectable } from '@angular/core';

@Injectable()
export class ProductService {
  constructor() { }
}
```
- `@Injectable()`表示此Service會變成是Singleton


```typescript 
providers :[{ provide: ProductService, useClass: ProductService }]
// provide and useClass has same name then 
providers : [ ProductService]
```

Inject the Service to components, for example 
```typescript
export class xComponent{

  constructor(private productService : ProductService)
}
```
### String Token

```typescript
 {provide:'PRODUCT_SERVICE', useClass: ProductService }, 
 {provide:'USE_FAKE', useValue: true },   
 {provide:'APIURL', useValue: 'http://SomeEndPoint.com/api' },

class ProductComponent {
  
  constructor(@Inject('PRODUCTSERVICE') private prdService:ProductService,
              @Inject('APIURL') private apiURL:string )
}
```

### Singleton String Token

To avoid different developers use the same string token at a different part of the app.   
**The Angular provides `InjectionToken<T>(T initializeValue)` class so as to ensure that the Unique tokens are created.**
The Injection Token is created by creating a new instance of the InjectionToken class.

Injection Token Class
```typescript
export const API_URL= new InjectionToken<string>(''); 
```
Register the token in the providers array.
```typescript
providers: [ 
    { provide: API_URL, useValue: 'http://SomeEndPoint.com/api' }
]
```
It is then injected using the `@Inject` in the constructor of the service/component.
```typescript
constructor(@Inject(API_URL) private apiURL: string) { 
}
```

## The Types of Provider 
### useClass

Provide an instance of the provided class.

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
    { provide: ProductService, useClass: FakeProductService }, // FakeProductService will be used 
  ]
```

#### Switching Dependencies

```typescript 
providers :[{ provide: ProductService, useClass: otherProductService }]
```

### useValue

```typescript
// provide boolean value injectable dependency
providers :[ {provide:'USE_FAKE', useValue: true}]

export class AppComponent {
  constructor(
    @Inject('USE_FAKE') public useFake: string
  ) {}


// Object 
const APP_CONFIG =  Object.freeze({
  serviceURL: 'www.serviceUrl.comapi',
  IsDevleomentMode: true
});

providers: [
  { provide: 'APP_CONFIG', useValue: APP_CONFIG }
]

// Function
providers: [
    {
      provide: 'FUNC',
      useValue: () => {
        return 'hello';
      }
    }
]
export class AppComponent {
  constructor(
    @Inject('FUNC') public someFunc: any
  ) {
    console.log(someFunc());
  }
}
 
```
### UseFactory

The Factory Provider useFactory expects us to provide a function. It invokes the function and injects the returned value. We can also add optional arguments to the factory function using the deps array. The deps array specifies how to inject the arguments.

We usually use the useFactory when we want to return an object based on a certain condition, for example
```typescript
constructor(
  private logger: Logger,
  private isAuthorized: boolean) { }

getHeroes() {
  let auth = this.isAuthorized ? 'authorized ' : 'unauthorized';
  
  this.logger.log(`Getting heroes for ${auth} user.`);

  return HEROES.filter(hero => this.isAuthorized || !hero.isSecret);
}
```

Create the provider 
```typescript
let heroServiceFactory = (logger: Logger, userService: UserService) => {
  return new HeroService(logger, userService.user.isAuthorized);
};
```

Register token/provider
```typescript
/**
 *  heroServiceProvider <--- factory
 *  provide : HeroService <---- service
 *  dep : factory 
 */
export let heroServiceProvider ={
    provide: HeroService,
    useFactory: heroServiceFactory,
    deps: [Logger, UserService] 
  };
```
- `useFactory`可讓angular知道`provider`是一個Factory，透過`heroServiceFactory`作實現
- `deps` : 工廠需要用到的Services, 有順序性，會將Service中的`Logger`和`UserService`注入符合的Factory Function  


```typescript
providers: [
  { provide: LoggerService, useClass: LoggerService },

  { provide: 'USE_FAKE', useValue: true },

  {
    provide: ProductService,
    // use FakeProductService if USER_FAKE is true
    useFactory: (USE_FAKE, LoggerService) =>
      USE_FAKE ? new FakeProductService() : new ProductService(LoggerService),
    deps: ['USE_FAKE', LoggerService]
  }
]
```


The useFactory invokes the factory function and returns the result.   
Hence in the component, you will receive the value of the function and not the function itself.
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


## Aliased class providers (useExisting)

Use Aliased Provider `useExisting` when you want to use the new provider in place of the old Provider.
```typescript
providers: [
  // token named ProductService inject NewProductService instance
  { provide: ProductService, useExisting: NewProductService },
  { provide: NewProductService, useClass: NewProductService },
]

// or
providers: [
  NewProductService,
  { provide: ProductService, useExisting: NewProductService },
]

// String Token
providers: [
  { provide: ProductService, useExisting: 'PRODUCT_SERVICE' },
  { provide: 'PRODUCT_SERVICE', useClass: NewProductService },
]
```

## 常數注入`InjectionToken<T>`

**如果有時我們要注入的對象不是物件(Object)時**，例如要注入下面這個常數(Constant)
```typescript
export const HERO_DI_CONFIG: AppConfig = {
  apiEndpoint: 'api.heroes.com',
  title: 'Dependency Injection'
};
```

我們不能使用下面這個方式來注入
```typescript
[{ provide: AppConfig, useValue: HERO_DI_CONFIG })]
```
- 這是因為javascript是一個弱型別的語言，即便typescript是強型別的，但最終會被轉回弱型別的javascript, 因此用這種方式angular會無法找到這個型別


正確的使用方式是使用`export const NAME = new InjectionToken<ProvideName>(ConstantValue);` 
```typescript
import { InjectionToken } from '@angular/core';
export const APP_CONFIG = new InjectionToken<AppConfig>('app.config');
```

註冊該Constant在`@ngModel`的provider Array內
```typescript
/**
 *  Provide APP_CONFIG
 */
providers: [{ provide: APP_CONFIG, useValue: HERO_DI_CONFIG }]
```

當Component的Constructor要使用常數注入時則要利用annotation `@InJect(CONST_VARIABLE) provide : Provide`
```typescript
/**
  * with annotation `@Inject(provider)`
  */
constructor(@Inject(APP_CONFIG) config: AppConfig) {
  this.title = config.title;
}
```

## `@Optional()`表示不一定會被注入的Service 

如果想要有些services不要被自動注入，這時可以用這個方式宣告
```typescript   
import { Optional } from '@angular/core';

/**
 *  該service DI is optional
 */
constructor(@Optional() private logger: Logger) {
  
  // if logger不是null
  if (this.logger) {
    this.logger.log(some_message);
  }
}
```
- 如果使用`@Optional()`，並且找不到相應的服務時，就不會跳錯誤，而logger的值會是`null`

## (Component's Provider)分層注入系統

有別於在Module provider array內register token&provider，在Component的providers array內register，則每當Component創建一個新的實體，所註冊的Token也會new一個新的(對應的Provider/Service)實體，我們可以利用這個特性來讓Token所映射的Service裡的Properties資源彼此間不受干擾。

例如將Token註冊在Component's provider array使三個`HeroTaxReturnComponent`實體內的Service注入資料不被共享
![image](https://user-images.githubusercontent.com/68631186/125828144-9f481a78-0b21-43ae-bb8e-a9a8e36d1d7b.png)

```typescript
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { HeroTaxReturn }        from './hero';
import { HeroTaxReturnService } from './hero-tax-return.service';

@Component({
  selector: 'app-hero-tax-return',
  templateUrl: './hero-tax-return.component.html',
  styleUrls: [ './hero-tax-return.component.css' ],
  /**
    * I have my own Instance of HeroTaxReturnService
    */ 
  providers: [ HeroTaxReturnService ]
})
export class HeroTaxReturnComponent {
  message = '';
  @Output() close = new EventEmitter<void>();

  get taxReturn(): HeroTaxReturn {
    return this.heroTaxReturnService.taxReturn;
  }
  @Input()
  set taxReturn (htr: HeroTaxReturn) {
    this.heroTaxReturnService.taxReturn = htr;
  }

  constructor(private heroTaxReturnService: HeroTaxReturnService ) { }

  onCanceled()  {
    this.flashMessage('Canceled');
    this.heroTaxReturnService.restoreTaxReturn();
  };

  onClose()  { this.close.emit(); };

  onSaved() {
    this.flashMessage('Saved');
    this.heroTaxReturnService.saveTaxReturn();
  }

  flashMessage(msg: string) {
    this.message = msg;
    setTimeout(() => this.message = '', 500);
  }
}
```
