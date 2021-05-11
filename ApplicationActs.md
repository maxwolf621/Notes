###### tags: `Angular`
# How Angular Application Activate
[TOC]


[Note](https://ithelp.ithome.com.tw/articles/10202823)
## main.ts
main.ts 是所有程式的進入口
- The Entry of the Angular Application Project
```typescript=
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

```typescript=
if (environment.production) {
  enableProdMode();
}
```
- 判斷是不是要在 production mode (在 environment.prod.ts 中可以設定)下，如果是的話，會呼叫 `enableProdMode()`，來確保整個 Angular 的運作是在 production mode 下，在此模式下將會無法使用斷言(assertions)相關的API，同時也會減少不必要的變更偵測，以增進運作效能。


```typescript=
// loads the Modules (Activate The Application)
platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.log(err));
```
這個Method可以想成「Ready to Execute Angular Application's Plateform」
- The Plateform代表的則是Browser
    > 這個方法呼叫後會取得一個 `PlatformRef` object，接著便能call其中的` bootstrapModule()`來指定要啟動的第一個Module，這個模組通常也被稱為「根模組(Root Module)」。在這個例子中，就是 AppModule。

## app.module.ts
在 app.module.ts 中宣告了一個 `AppModule` Class ，並替這個類別掛上 `@NgModule`，**我們關注的是裡面的 `bootstrap: []`設定，代表要實際啟動的元件，我們可以看到在裡面有一個 AppComponent，這個元件則被稱為「根元件(Root Component)」，在一個 Angular 應用程式中，所有的畫面都是從AppComponent開始的，也就是這個原因**

## app.component.ts
在 app.component.ts 中，可以看到一個 AppComponent 類別，就是我們實際上整個程式的進入口，而在 `@Component` 標籤的 selector ，代表的就是在index.html 中，找到對應的標籤(例如 <app-root></app-root>)，並將元件的對應 HTML 樣板取代過去！



## Boost(load) multiple Root Module in main.ts 


First create An App2Module.module.ts
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


add `platformBrowserDynamic().bootstrapModule(AppModule)` into `main.ts`
- 之後只要在 index.html 中有放入 App2Component 指定的 selector，就能夠正確顯示

## 同時啟動多個根元件
除了建立多個根模組以外，由於 `@NgModule`中的 `bootstrap: []` 設定是陣列的關係，因此要同時以多個元件作為根元件也完全不是問題，如下：
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
  // 設定多個根元件
  bootstrap: [AppComponent, AnotherComponent]
})
export class AppModule { }
```
- 一樣只需要在 index.html 中加入 AnotherComponent 指定的 selector 就能夠正確顯示

多個根模組或根元件的使用情境
雖然這樣設計感覺很有趣，但通常我們也很少使用這種設計方式，那麼多個根模組或根元件同時使用到底有什麼好處呢？比較明顯的優點是當 index.html 中本來就有預期放入一個固定的 layout ，且畫面上會有兩個互不相關的程式時，就可以使用多個根模組或根元件來處理，而不是將整個 layout 放到單一個模組內，能夠更快地顯示出基本的畫面架構，再慢慢等待 JavaScript 檔載入就好，能讓使用者更快感覺到畫面有所呈現。

當然壞處明顯的就是模組跟模組之間、元件跟元件之間是較難互相溝通的，所以在設計的時候就要仔細考量囉！