# Router
[[Angular 深入淺出三十天] Day 20 - 路由(三)](https://ithelp.ithome.com.tw/articles/10207918)    
[[Angular 深入淺出三十天] Day 25 - 路由總結(一)](https://ithelp.ithome.com.tw/articles/10209035)      

- [Router](#router)
  - [Angular的Router角色](#angular的router角色)
  - [Router Configuration](#router-configuration)
    - [create `app-routing.module.ts`](#create-app-routingmodulets)
    - [register (@ngModule.import[...])](#register-ngmoduleimport)
    - [Template's outlet `<router-outlet>`](#templates-outlet-router-outlet)
  - [Router In Action](#router-in-action)
  - [Directive `[routerLink]`](#directive-routerlink)
  - [萬用路由](#萬用路由)
  - [Child Router](#child-router)
    - [設定相對路徑](#設定相對路徑)
  - [Lazy Loading](#lazy-loading)
  - [Pre-loading](#pre-loading)
  - [Path query parameter](#path-query-parameter)
    - [Query String](#query-string)
      - [取得query string參數](#取得query-string參數)
    - [matrix URL](#matrix-url)
      - [取得Path的參數的方式：](#取得path的參數的方式)
  - [換頁效果](#換頁效果)

## Angular的Router角色
- **負責重新配置頁面中應該顯示哪些Components**
  - e.g. home page 會有那些components在該頁面顯示
- 負責儲存頁面中Component的路由狀態
  - **路由狀態定義了在某個路由的時候應該顯示哪些Component**
  - **路由狀態最重要的就是記錄路徑與Component之間的關係**

## Router Configuration 

### create `app-routing.module.ts`

Create Root Routing module `app-routing.module.ts`
```typescript
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

### register (@ngModule.import[...])

Sign Router module in the `@ngModule.import` of root module
```typescript
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

@NgModule({
  // ...
  imports: [
    BrowserModule,
    AppRoutingModule  
  ],
  
  // ...
})
export class AppModule { }
```

### Template's outlet `<router-outlet>`

Template In `app.component.html`
```html
<!-- 
Plug different component's templates via outlet
-->
<router-outlet></router-outlet> 
```
- **Angular的路由機制靠的是我們放在`.html`裡的`<router-outlet></router-outlet>`**

## Router In Action

For Example :: 假如有兩個Routers分別navigate到home或者about頁面
```typescript
import { AboutComponent } from './about/about.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'about',
    component: AboutComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
      enableTracing: true,
      userHash : true
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```
- `enableTracing : true` : log監聽Route的trace

## Directive `[routerLink]`

Router's directive for template `.html` 分成兩個
1. `<a></a>` 用的 RouterLinkWithHref 
2. 其他非`<a></a>`元素用的 RouterLink 

我們可以利用Directive在`.html`建立路徑達到轉頁
```html
<ul>
  <li><a [routerLink]="'/home'">Home</a></li>
  <li><a [routerLink]="'/about'">About</a></li>
</ul>
```

## 萬用路由

當所在的路徑不屬於`const routers`內任何路徑時則統一redirect到某一特定路徑
```typescript
const routers: Routers:[
    {
      // routers
    },
      // ...
    , 
    {
        path: '**', 
        redirectTo: 'home', 
        pathMatch: 'full'
    }
];
```
- **路由的路徑設定是有順序性的,如果萬用路由放在前面**, 其他在後面的routers會被略過,所以一般我們都把萬用路由放置`const routers : Routers : [ {..} , {...}, ... , {path : '**' ...} ]`

## Child Router
設定子路由的優點
- **可讀性高的url** ，e.g. `/articles/10207918`
- 延遲載入
- **減少撰寫重複的程式碼**
- 預處理層

在沒有子路由的情況下，**若瀏覽器重新導航到相同的元件時，會重新使用該元件既有的實體，而不會重新創建**  
因此在物件被重用的狀況下，**該元件的`ngOnInit`只會被呼叫一次，即使是要顯示不同內容資料**但是**被創建的元件實體會在離開頁面時被銷毀並取消註冊**   
- For example 由於在瀏覽某位Hero Detail(`HeroDetailComponent`)之後，一定要先回到選單List(`HeroListComponent`)，才能再進入另一位Hero Detail頁面，造成回到`HeroListComponent`時已把`HeroDetailComponent`實體銷毀，當再選擇另一個英雄查看細節時，又會再創立一個新的`HeroDetailComponent`實體因此每次選擇不同的英雄時，Component實體都會重新創建
```typescript
const heroesRoutes: Routes = [
  { path: 'heroes',  component: HeroListComponent },
  { path: 'hero/:id', component: HeroDetailComponent }
];
```

如果想要保留頁面的狀態，就可以改使用子路由的方式來定義, 如
```typescript
const crisisCenterRoutes: Routes = [
  {
    path: 'crisis-center', 
    component: CrisisCenterComponent,
    children: [
      {
        path: '',
        component: CrisisListComponent,
        children: [
          {
            path: ':id',
            component: CrisisDetailComponent
          },
          {
            path: '',
            component: CrisisCenterHomeComponent
          }
        ]
      }
    ]
  }
];
```
- `CrisisDetailComponent`與`CrisisCenterHomeComponent`都是`CrisisListComponent`的Child Component，因此在不同的Child Component內的實體狀態得以被保存，直到頁面切換離開`CrisisCenterComponent`時才會將元件實體刪除，這可以讓我們在瀏覽不同CrisisDetail時，得以使用到Router預設的重用設定

### 設定相對路徑

- `./` 是在目前的位置
- `../` 在上一層的位置

```typescript 
// Relative navigation back to the crises
this.router.navigate(['../', { id: crisisId, foo: 'foo' }], { relativeTo: this.route });
```

- For Examle 設計一個component將類似或者給特定使用者使用的components放入對應的component
![](https://i.imgur.com/WAdXz1e.png)

屆時我們可以將Router的路徑分成Base router以及Child router,如下
```typescript
{
  path: '',
  component: LayoutComponent,
  children: [
    {
      path: 'home',
      component: HomeComponent
    },
    {
      path: 'about',
      component: AboutComponent
    }
  ]
}
```
- 只要路由有設定children的route，再加上在Template裡有放`<router-outlet></router-outlet>`的話, Angular的路由機制就會自動幫你向下找

實際應用上，**通常我們會將相關的功能包裝成一個一個`NgModule` ，而每個`NgModule`，也其實都可以有自己的Routing Module** (e.g. 一個網站共同的頁面layout)   
- For example :: 建立一個Module以及FeatureRoutingModule給feature component使用   
```bash
# --routing 另外建立一個FeatureRoutingModule的路由  
ng generate module feature --routing
# generate feature component
ng generate component feature
```

對於child route (建立一個module以及FeatureRoutingModule給feature)
```typescript
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { FeatureComponent } from './feature.component';

const routes: Routes = [
  {
    path: 'feature',
    component: FeatureComponent
  }
];

@NgModule({
  /**
   * only forChild
   */
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeatureRoutingModule { }
```
- 整個系統只有`AppRoutingModule`才會使用`forRoot(..)` ，其他的子路由模組都是使用`forChild(...)`  

只要在`app.module.ts`內將`FeatureModule.ts`註冊到`@ngModule.import`內就可加入child的路由路徑,**由於路由是有順序性的所以不能放在root routing module之後**, For example ::
```typescript
imports: [
  BrowserModule,
  AppRoutingModule, // <-- 先跑這裡面的所有路由
  FeatureModule    // <-- 跑完AppRoutingModule所有路徑才會執行
]

imports: [
  BrowserModule,
  FeatureModule
  AppRoutingModule,
]
```
- 任何在`@ngModule import`的Child Module必須在`AppRoutingModule`之前


## Lazy Loading

```typescript 
loadChildren:()=>import('./yyyy/xxxx.module').then(module => module.XXXXModule) 
```

In `FeatureRoutingModule.ts` 
```typescript
const routes: Routes = [
  {
    path: '', // 將 feature 改成空字串
    component: FeatureComponent
  }
];
```

In `app-routing.module.ts` 
```typescript
// Angular 8+
{
  path: 'feature',
  loadChildren: () => import('./feature/feature.module').then(module => module.FeatureModule)
}
```

## Pre-loading

預先載入跟延遲載入很像，差別只在於，**延遲模組是要進入該路由的時候即時載入**；預先載入是在頁面初始化的時候就把所有可延遲載入的模組透過背景非同步地下載，不會影響畫面的顯示或使用者的操作。
```typescript
import { PreloadAllModules } from '@angular/router';

//....

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    useHash: true,
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule],
  providers: []
})
```

## Path query parameter 

有時候我們會需透過路由傳遞參數，而傳遞參數的方法有兩種
1. query string  `?`
2. matrix URL notation `;`

### Query String 

透過Query String表示路徑
- Ex: `http://localhost:4200/products?id=101`

In `.ts`
```typescript
constructor(private route: ActivatedRoute) { }

this.router.navigate(['products'], {
    queryParams: {
      id: 101
    }
});
```
In `.html`
```html
<a [routerLink]="['products']" [queryParams]="{ id: 101 }">Link For Product</a>
```

#### 取得query string參數

1. using `queryParams.subscribe((queryParams) => { ...})`
2. using `.snapshot.queryParams['..']`
```typescript
export class ProductsComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
  
      // recommend 
      this.route.queryParams.subscribe(
        (queryParams) => {
          console.log(queryParams['id']);
        }
      );
      
      // snapshot.queryParams
      console.log(this.route.snapshot.queryParams['id']);
  
  }

}
```

### matrix URL

```typescript
this.router.navigate(['path_name'], {queryParam_1 : xxx , queryParam_2 : yyy , ...});
```

path表示方式利用`;` 將Parameters隔開    
- Ex：`http://localhost:4200/products;id=101`
```typescript
this.router.navigate(['products', { id: 101 }]);
```
```html
<a [routerLink]="['products', { id: 101 }]">Products</a>
```

#### 取得Path的參數的方式：
```typescript
export class ProductsComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
  
      // firsrt one 
      console.log(this.route.params['id']);
      
      // second one
      console.log(this.route.snapshot.params['id']);
  }

}
```

## 換頁效果
- [添加換頁效果](https://ithelp.ithome.com.tw/articles/10195347)

需要`BrowserAnimationsModule`這個動態效果模組   

```typescript
import { animate, AnimationEntryMetadata, state, style, transition, trigger } from '@angular/core';

// Component transition animations
export const slideInDownAnimation: AnimationEntryMetadata =
  trigger('routeAnimation', [
    state('*',
      style({
        opacity: 1,
        transform: 'translateX(0)'
      })
    ),
    transition(':enter', [
      style({
        opacity: 0,
        transform: 'translateX(-100%)'
      }),
      animate('0.2s ease-in')
    ]),
    transition(':leave', [
      animate('0.5s ease-out', style({
        opacity: 0,
        transform: 'translateY(100%)'
      }))
    ])
  ]);
```

接著在`src/app/heroes/hero-detail.component.ts`增加使用這個動態效果的綁定
```typescript
@HostBinding('@routeAnimation') routeAnimation = true;
@HostBinding('style.display')   display = 'block';
@HostBinding('style.position')  position = 'absolute';
```

