
# Router For Child

- [[Angular 深入淺出三十天] Day 26 - 路由總結（二）](https://ithelp.ithome.com.tw/articles/10209259)   

## 子路由內的模組設定

CLI創建子路由
```bash
`ng g m feature --routing`
```

CLI創建的子路疣會自動設定
```typescript
const routes: Routes = [
  // ...
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeatureRoutingModule { }
```

把子路由加入至`app-module.tx`的`@ngModule.imports : [ .., FeatureRoutingModule , AppRoutingModule]`內
```typescript
imports: [
  BrowserModule,
  FeatureModule,  // before AppRoutingModule
  AppRoutingModule
],
```

## Routes's `resolve` property and Implementation from `Resolve<T>`

利用`Resolve<T>`來傳遞前後端的複雜資料
```typescript
@Injectable({
  providedIn: 'root',
})
                                                  // Resolve<資料>
export class ProductDetailResolverService implements Resolve<xxxInterface> {
  constructor(private xxxService: xxxService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, 
          state: RouterStateSnapshot): Observable<Product> | Observable<never>{
            // 設定前後端如何接收資料
          }
```

假釋我們要從Produc List頁面查詢某一特定Product Detail可以透過Resolve來進行前後端資料交流

在Route內設定`ProductListComponent`,`ProductDetailComponent`跟resolver`ProductDetailResolverService`
```typescript
 const routes: Routes = [
  {
    path: 'products',
    component: ProductListComponent,
    children: [
      {
        path: ':id',
        component: ProductDetailComponent
        // resolve fetch btw backend and frontend
        resolve: {
          product: ProductDetailResolverService
        }
      }
    ]
  },
  // ...
];
```
`ProductDetailResolverService` 大概會長這樣：
```typescript
import { Injectable } from '@angular/core';
import {
  Router, 
  Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';

// Class
import { Product } from './product';

// Service
import { ProductService }  from './product.service';

// RxJS
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap, take } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ProductDetailResolverService implements Resolve<Product> {
  constructor(private productService: productService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, 
          state: RouterStateSnapshot): Observable<Product> | Observable<never>{    
          const id = route.paramMap.get('id');
          return this.productService
                     .getProduct(id)
                     // fetch procession only execute one time
                     .pipe(take(1),
                           mergeMap(product => {
                               if (product) { 
                                   return of(product);
                                } else {
                                    this.router.navigate(['products']);
                                    return EMPTY;
                                }})
                     );
     }
}
```

在Product Detail的頁面(`ProductDetailComponent`)我們就透過這樣的方式來接值：
```typescript
// ProductDetailComponent
ngOnInit() {
  this.route.data.subscribe((data: { product: Product }) => {
      this.name= data.product.name;
    });
    
}
```
