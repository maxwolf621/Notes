###### Tags: `Angular` 
# Dependency Injection

## Service's DI
 
```typescript
import { Injectable } from '@angular/core';
/**
 * @Injectable()是angular的service使用做依賴注入的裝飾詞,
 *     ，可以使該Service成為被Components注入(USE)的實體
 */
@Injectable()
export class HeroService {
  constructor() { }
}
```
- Service使用`@Injectable()`表示此service會變成是Singleton

註冊在`provider`讓這個服務可以給xxx module使用，如下：
```typescript
providers: [
  UserService
],
```

這時只要我們在xxx module某個component的Constructor加入此Service的宣告就會自動注入(USE)的Instance
```typescript
export class HeroListComponent {
  heroes: Hero[];
  
  /**
    * Inject Instance of HeroService
    * And Shared by EveryBody in Project
    */
  constructor(heroService: HeroService) {
    this.heroes = heroService.getHeroes();
  }
}
```

## component's provider

**在`ngModule`裡provider設定的服務可以在該模組下任一元件裡去從建構子取得同一個物件的實體而不需要再在元件中額外去在provider宣告**

- 如果需要某個Service只需要在某個元件下被使用，則可以直接在`@Component.providers`裡面註冊(Register)而不而不適再module's `provider`
- **Component DI是相互獨立的**，每個組件都創建它自己的Component提供服務的實例。
  > 故當Angular Destroy其中一個Component實體時，它也會Destroy Component的注入器和注入器的服務實體  

## 更新注入Instance的Implementation類別

當注入一個Logger類型的Instance
```typescript
providers: [Logger]
```

此時Angular在Module注入這個Service時會利用`constructor`或者`providers:[{ ... }]`的宣告來使用該Service的實體
```typescript
/**
  * 我們稱Logger為token
  */
constructor(private logger: Logger) {}

/**
  * Service Logger就是Angular在做這個Service依賴注入的token，也可以寫成下面這樣
  * userClass:Logger -> 實現該注入的instance是class Logger
  * provide:Logger -> 注入的instance的類型是Logger
  */
providers: [{ provide: Logger, useClass: Logger }]
```

假如我們需要更新一些Logger類別的內容則可以建立了一個繼承Logger並覆寫其部份功能的類別BetterLogger(OPEN CLOSE Principle)
```typescript
/**
  * userClass: BetterLogger -> 實現 provide: LoggerBetterLogger(Which implements Logger)
  * provide: Logger -> 注入的instance的類型是Logger
  * 亦即對所有會自動注入Logger類別的component都會使用LoggerBetterLogger內的methods實現而不是Logger
  */
providers: [{ provide: Logger, useClass: BetterLogger }]
```
## Aliased class providers

以上面的例子，如果我們在**根模組(Root)的`@NgModule.providers[ ... ]`設定Logger為token，實際創建的物件卻為BetterLogger**，會造成其他的**子元件也都無法直接在constructor取得BetterLogger這類型的物件**   
```typescript
/**
  * Wrong Usage
  */
[ NewLogger,
  { provide: OldLogger, useClass: NewLogger}]

/**
  * Legitimate Usage
  */
[ NewLogger,
  // 任何Component使用(舊的)OldLogger時會去取得NewLogger這個類別的實體
  // 將NewLogger提供給OldLogger
  { provide: OldLogger, useExisting: NewLogger}]
```

## 直接使用一個Instance利用 `useValue`

https://ithelp.ithome.com.tw/articles/10208240

有的時候我們會希望能夠直接使用一個已建立好的物件實體(instance)而不是(class)提供(provides)給其他的Component自動注入(DI)
在`provider[ provide : xxx , useValue : yyy]`提供現成的物件`yyy`實體(instance)給`provide : xxx`使用

```typescript
// service 
export function SilentLoggerFn() {}

// An object will be used for provide in @ngModule.Provider
const silentLogger = {
  logs: ['Silent logger says "Shhhhh!". Provided via "useValue"'],
  log: SilentLoggerFn
};
```

下面的在`@NgModule.providers`宣告方式，可以讓我們在元件的Constructor直接使用Logger類別來取得上面所建的物件實體
```typescript
[{ provide: Logger, useValue: silentLogger }]
```
- Provide Logger service uses value of object silentLogger

## 在`@NgModule.Provider`內使用工廠模式

https://ithelp.ithome.com.tw/articles/10208240

- 更有彈性客製化不同Components使用不同類別實現自動注入(DI)

**如果我們希望針對客戶的層級不同提供不同的服務，可以使用Factory providers。**


For example :: We got two service provides for Factory for components 
### service
In `src/app/heroes/hero.service.ts`  
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

### service provider
In `hero.service.provider.ts`裡定義了factory的物件
```typescript
let heroServiceFactory = (logger: Logger, userService: UserService) => {
  return new HeroService(logger, userService.user.isAuthorized);
};
```

### register in the root module 
然後在`app-module`的`@NgModule.providers`
```typescript
/**
 *  heroServiceProvider <--- factory
 *  provide : HeroService <---- service
 * dep : factory 
 */
export let heroServiceProvider =
  { provide: HeroService,
    useFactory: heroServiceFactory,
    deps: [Logger, UserService]
  };
```
- `useFactory`可讓angular知道`provider`是一個Factory，其實現是`heroServiceFactory`
- `deps`屬性是這個工廠需要用到的Services  
- angular會將Service中的`Logger`和`UserService`注入(DI)符合的Factory Function  

## 常數注入   

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
- 這是因為javascript是一個弱型別的語言，即便typescript是強型別的，但最終會被轉回弱型別的javascript
  > 因此用這種方式angular會無法找到這個型別。


正確的使用方式是使用`export const NAME = new InjectionToken<ProvideName>(ConstantValue);` 
```typescript
import { InjectionToken } from '@angular/core';
export const APP_CONFIG = new InjectionToken<AppConfig>('app.config');
```

把該Constant註冊在`@ngModel.provider`內
```typescript
/**
 *  Provide APP_CONFIG
 */
providers: [{ provide: APP_CONFIG, useValue: HERO_DI_CONFIG }]
```

當Component的Constructor要使用常數注入時則要利用annotation `@InJect(CONST_VARIABLE) DI_variable : Provide`
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
如果使用`@Optional()`，並且找不到相應的服務時，就不會跳錯誤，而logger的值會是`null`

## 分層注入系統
Angular的Service是這樣的，每一次在`providers`裡註冊(register)一次，就會產生一次物件實體, 我們可以利用這個特性來讓服務裡的資訊彼此間隔離不受干擾     

例如下圖的HeroesListComponent下面有三個`HeroTaxReturnComponent`實體，裡面各字會有一個這個Hero的稅單資料（HeroTaxReturnService）  
![image](https://user-images.githubusercontent.com/68631186/125828144-9f481a78-0b21-43ae-bb8e-a9a8e36d1d7b.png)

但若我們不希望彼此間的服務會受到彼此的干擾，則可以在`HeroTaxReturnComponent`再宣告一次`HeroTaxReturnService`，則可以達到這個目的
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
