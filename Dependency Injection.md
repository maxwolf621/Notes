###### Tags: `Angular` 
# Dependency Injection

## Service's DI
```typescript
import { Injectable } from '@angular/core';
/**
 * <p> @Injectable()是angular的service使用做依賴注入的裝飾詞,
 *     ，可以使該Service成為可被Components 注入的實體
 * </p>
 */
@Injectable()
export class HeroService {
  constructor() { }
}
```

當我們在provider設定這個服務給這個module使用，如下：
```typescript
providers: [
  UserService
],
```

Service使用`@Injectable()`表示此service會變成是Singleton，只要我們在某個Component的Constructor加入此service的宣告則會自動注入Instance
```typescript
export class HeroListComponent {
  heroes: Hero[];
  
  /**
    * Inject Instance of HeroService
    *   Shared by EveryOne
    */
  constructor(heroService: HeroService) {
    this.heroes = heroService.getHeroes();
  }
}
```

## component's provider
**在`ngModule`裡provider設定的服務可以在該模組下任一元件裡去從建構子取得同一個物件的實體而不需要再在元件中額外去在provider宣告。**
如果有一個服務只需要在某個元件下被使用，則可以直接在`@Component.providers`裡面Register
**Component Di是相互獨立的**，每個組件都創建它自己的組件提供服務的實例。
> 故當Angular銷毀其中一個組件實體時，它也會銷毀組件的注入器和注入器的服務實體。

## 更新注入Instance的實現類別

當注入一個Logger類型的Instance
```typescript
providers: [Logger]
```
此時angular在注入這個服務時會利用`constructor`或者`providers:[{ ... }]`的宣告來取的實體,如下
```typescript
/**
  * 我們稱Logger為token
  */
constructor(private logger: Logger) {}

/**
  * 在這個例子中，Logger就是angular在做這個服務依賴注入的token，也可以寫成下面這樣
  * userClass:Logger -> 實現該注入的instance是class Logger
  * provide:Logger -> 注入的instance的類型是Logger
  */
providers: [{ provide: Logger, useClass: Logger }]
```

假如我們需要更西一些Logger類別的內容則可以建立了一個繼承Logger並覆寫其部份功能的類別BetterLogger
```typescript
/**
  * userClass:Logger -> 實現provide:LoggerBetterLogger(Which implements Logger)
  * provide:Logger -> 注入的instance的類型是Logger
  * 亦即對所有自動注入Logger類型的instances都會使用class LoggerBetterLogger內的methods實現
  */
providers: [{ provide: Logger, useClass: BetterLogger }]
```

## Aliased class providers
以上面的例子，如果我們在**根模組設定Logger為token，實際創建的物件卻為BetterLogger**，會造成其他的**子元件也都無法直接在constructor取得BetterLogger這類型的物件**。 
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
  // 使用OldLogger時會去取得NewLogger這個類別的實體
  { provide: OldLogger, useExisting: NewLogger}]
```

## 直接使用一個實體物件利用 `useValue`

在provide提供現成的物件實體, 有的時候我們會希望能夠直接使用一個已建立好的物件實體，可以用下面的方式：
```typescript
// An object in the shape of the logger service
export function SilentLoggerFn() {}

// A instance
const silentLogger = {
  logs: ['Silent logger says "Shhhhh!". Provided via "useValue"'],
  log: SilentLoggerFn
};
```
下面的宣告方式，可以讓我們在元件下的建構子可以直接使用Logger來取得上面所建的物件實體
```typescript
[{ provide: Logger, useValue: silentLogger }]
```

## 在provide srvice時使用工廠模式(更彈性的依造不同Components使用不同的類別實現注入的instance)

**如果我們希望針對客戶的層級不同提供不同的服務，可以使用Factory providers。**

下面是src/app/heroes/hero.service.ts這個檔案的截錄內容
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

`hero.service.provider.ts`裡定義了factory的物件
```typescript
let heroServiceFactory = (logger: Logger, userService: UserService) => {
  return new HeroService(logger, userService.user.isAuthorized);
};
```
然後在providers的宣告是這樣的
```typescript
export let heroServiceProvider =
  { provide: HeroService,
    useFactory: heroServiceFactory,
    deps: [Logger, UserService]
  };
```
useFactory可讓angular知道provider是一個工廠函數，其實現是heroServiceFactory。
而deps屬性是這個工廠需要用到的服務。angular會將服務中的Logger和UserService注入符合的Factory function參數。

## 常數注入
如果有時我們要注入的對象不是物件時，例如要注入下面這個常數
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
- 這是因為javascript是一個弱型別的語言，即便typescript是強型別的，但最終會被轉回弱型別的javascript。 因此用這種方式angular會無法找到這個型別。

正確的使用方式如下：
```typescript
import { InjectionToken } from '@angular/core';

export const APP_CONFIG = new InjectionToken<AppConfig>('app.config');
```

然後用剛剛建的InjectionToken來註冊這個程序
```typescript
providers: [{ provide: APP_CONFIG, useValue: HERO_DI_CONFIG }]
```
使用這串字串的方式如下：
```typescript
/**
  * with annotation `@Inject(provider)`
  */
constructor(@Inject(APP_CONFIG) config: AppConfig) {
  this.title = config.title;
}
```

## `@Optional()`表示不一定會被注入的Service 
有些service可以不一定要被建立，這時可以用這個方式宣告
```typescript   
import { Optional } from '@angular/core';
constructor(@Optional() private logger: Logger) {
  if (this.logger) {
    this.logger.log(some_message);
  }
}
```
如果使用`@Optional()`，並且找不到相應的服務時，就不會跳錯誤，而logger的值會是`null`。 

## 分層注入系統
angular的服務是這樣的，每一次在providers裡註冊一次這個類型，就會產生一次物件實體, 我們可以利用這個特性來讓服務裡的資訊彼此間隔離不受干擾  

例如下圖的HeroesListComponent下面有三個HeroTaxReturnComponent實體，裡面各字會有一個這個Hero的稅單資料（HeroTaxReturnService）
![image](https://user-images.githubusercontent.com/68631186/125828144-9f481a78-0b21-43ae-bb8e-a9a8e36d1d7b.png)

但若我們不希望彼此間的服務會受到彼此的干擾，則可以在HeroTaxReturnComponent再宣告一次HeroTaxReturnService，則可以達到這個目的
```typescript
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { HeroTaxReturn }        from './hero';
import { HeroTaxReturnService } from './hero-tax-return.service';

@Component({
  selector: 'app-hero-tax-return',
  templateUrl: './hero-tax-return.component.html',
  styleUrls: [ './hero-tax-return.component.css' ],
  /**
    * I have my own Insatnce of HeroTaxReturnService
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
