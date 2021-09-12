###### tags: `Angular`
# Flow of Boost Angular Application
[TOC]

[Note](https://ithelp.ithome.com.tw/articles/10202823)


Application啟動FLOW

main.ts
1. check if production mode
2. call platformBrowserDynamic load root module
3. boost components according bootstrap: [ root_Components ] in root module
## main.ts
`main.ts`是所有程式的進入口(The Entry of the Angular Application Project)

```typescript
import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.log(err));
```

其中
```typescript
/**
 * 使用 Production Mode ?
 */
if (environment.production) {
  enableProdMode();
}
```
- 判斷是不是要在 production mode (在 `environment.prod.ts` 中可以設定)下，如果是的話，會呼叫 `enableProdMode()`，來確保整個 Angular 的運作是在 production mode 下，**在Production模式下將會無法使用斷言(assertions)相關的API，同時也會減少不必要的變更偵測，以增進運作效能。**


```typescript
// loads the first Module(`AppModule`) to activate the angular Application
platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.log(err));
```
- 這個Method可以想成**「Ready to Execute Angular Application's Plateform」**
- The Plateform代表的則是我們的Browser
    > 這個Method呼叫後會取得一個`PlatformRef`的object，接著便能呼叫其中的` bootstrapModule()`來指定要啟動的第一個Module，這個模組通常也被稱為「根模組(Root Module)」。在這個例子中，就是`AppModule`  

## `app.module.ts`
在 `app.module.ts`中宣告了一個 `AppModule`，並替這個類別掛上 `@NgModule`，**我們關注的是裡面的 `bootstrap: []`設定，代表要實際啟動的元件，我們可以看到在裡面有一個AppComponent，這個元件則被稱為「根元件(Root Component)」，在一個 Angular 應用程式中，所有的畫面都是從AppComponent開始的**

## `app.component.ts`

**在 `app.component.ts` 中，可以看到一個 `AppComponent` 類別，就是我們實際上整個程式的進入口**，而在 `@Component` 標籤的 selector ，代表的就是在`index.html`中，找到對應的標籤(例如`<app-root></app-root>`)，並將元件的對應 HTML 樣板取代過去！

## Boost(Load) multiple Root Modules in `main.ts` 

First create a Module `App2Module.module.ts`
```typescript
@NgModule({
  imports: [
    BrowserModule
  ],
  declarations: [App2Component],
  bootstrap: [App2Component]
})
export class App2Module { }
```

In `main.ts`
```typescript
import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule).then(ref => {
  // Ensure Angular destroys itself on hot reloads.
  if (window['ngRef']) {
    window['ngRef'].destroy();
  }
  window['ngRef'] = ref;

  // Otherwise, log the boot error
}).catch(err => console.error(err));

// 啟動第二個根模組
platformBrowserDynamic().bootstrapModule(App2Module);
```


- 之後只要在`index.html`中有放入`App2Component`指定的`@component.selector`，就能夠正確顯示

## 同時啟動多個Root Components
除了建立多個根模組以外，由於`@NgModule`中的`bootstrap: []` 設定是陣列的關係，因此要同時以多個元件作為根元件也完全不是問題
```typescript
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HelloComponent } from './hello.component';
import { AnotherComponent } from './another/another.component';

@NgModule({
  imports: [BrowserModule, FormsModule],
  declarations: [AppComponent, HelloComponent, AnotherComponent],
  //  Boost up Multiple Components 
  bootstrap: [AppComponent, AnotherComponent]
})
export class AppModule { }
```
- 一樣只需要在`index.html`中加入`AnotherComponent`指定的 `@component.selector`就能夠正確顯示

## 多個根模組或根元件的優勢
可以在`index.html`中使用不同的components, 能夠更快地顯示出基本的畫面架構，再慢慢等待JavaScript檔載入就好，能讓使用者更快感覺到畫面有所呈現  
- 壞處則是模組跟模組之間、元件跟元件之間是較難互相溝通的  