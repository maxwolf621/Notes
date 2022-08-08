# Content Loading via Router

we can use router and child router to design our template layout    
the good is the it reuses the same component reloading.   

## Note taking

[jonny-huang.github.io](https://jonny-huang.github.io/angular/training/09_angular_router2/)   
[Josip Bojčić](https://indepth.dev/posts/1235/how-to-reuse-common-layouts-in-angular-using-router-2)     
[side-bar , header and content with router](https://dimitr.im/content-sidebar-angular-routing)    

Some Good point in the article
- Angular 在執行時只有一個根路由當作起點，相關其他路由會子路由的身分以附加到根路由內
- 路由模組是透過 Angular 幫我們整合，正常情況下它會以Root Router為主，在依Child Router模組加入的順序依次加進去，所以如果根路由包含了萬用路由的規則，那路由規則比對時可能就會在此就被攔截，後續路由規則可能就會不再比對。
- 當使用route中`children` property時可以設定在某main component,某路徑下的子路徑該被哪種component做render


## Template with Main Layout and can load different contents(component)

```html
<app-header fxLayout="column"></app-header>

<div fxLayout="row" fxFlex="100">
 <app-sidebar fxLayout="column" fxFlex="300px"></app-sidebar>
 <div class="content" fxLayout="column" fxFlex>
   <router-outlet></router-outlet>  <!-- this allows us can display different templates -->
 </div>
</div>

<app-footer fxLayout="column"></app-footer>
```

![image](images/bc03da98bace55ee8c1460650da91d03dc4ff50ef48942588c161e8eafb983b3.png)  
- `MainLayoutComponent` equals `SideBarComponent` + `HeaderComponent` + `FooterComponent` 
- `<router-outlet></router-outer>` takes it as a socket (for router help us redirect different path)

```
main layout 
+---------------------------+
|         Header            |    
+---------------------------+
|       |                   |  
| Side  |   Content         |    
| Bar   |                   |
|       |                   |
|-------+-------------------+
|           footer          |
+---------------------------+

footer only layout
+---------------------------+
|         Header            |
+---------------------------+
|                           |
|         Content           |
|                           |
|                           |
|---------------------------+
|           footer          |
+---------------------------+
```

## Main layout

```
├─account-setting
|  ├─account-setting-routing.module.ts
|  ├─account-setting.component.ts
|  ├─account-setting.component.html
|  ├─account-setting.component.css
|  └─account-setting.module.ts
├─dashboard
|  ├─dashboard-routing.module.ts
|  ├─dashboard.component.ts
|  ├─dashboard.component.html
|  ├─dashboard.component.css
|  └─dashboard.module.ts
|
├─registration
|  ├─registration-routing.module.ts
|  ├─registration.component.ts
|  ├─registration.component.html
|  ├─registration.component.css
|  └─registration.module.ts
|
├─users
|  ├─users-routing.module.ts
|  ├─users.component.ts
|  ├─users.component.html
|  ├─users.component.css
|  ├─users.module.ts
|
├─login
|  ├─login-routing.module.ts
|  ├─login.component.ts
|  ├─login.component.html
|  ├─login.component.css
|  └─login.module.ts
|
├─layout
│  ├─footer
|  | ├─footer.component.ts
|  | ├─footer.component.html
|  | ├─footer.component.css
│  ├─header
|  | ├─header.component.ts
|  | ├─header.component.html
|  | ├─header.component.css
|  | 
|  ├─sidebar
|  | ├─sidebar.component.ts
|  | ├─sidebar.component.html
|  | ├─sidebar.component.css
|  |
│  ├─main-layout    
|  | ├─main-layout.component.ts
|  | ├─main-layout.component.html
|  | └─main-layout.component.css
│  |
|  └─layout.module.ts
│ 
├─ app-routing.module.ts
├─ app.component.css
├─ app.component.html
├─ app.component.ts 
└─ app.module.ts
```

[Code snippet](https://stackblitz.com/edit/github-uy2zea-dq1yut?file=src%2Fapp%2Fapp.component.html)  

