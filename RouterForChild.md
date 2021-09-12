
## [子路由](https://ithelp.ithome.com.tw/articles/10209259)   


## 子路由內的模組設定為

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

在`app-module.tx`引入位置要擺在 AppRoutingModule 之前
```typescript
imports: [
  BrowserModule,
  FeatureModule, // 放在這裡
  AppRoutingModule
],
```

## [Guard](Router_Guard.md)

一般常見使用路由守門員的時機大致上有兩種：   
`canActivate` － 當使用者想要造訪某個路由時，透過路由守門員來判斷要不要讓使用者造訪。   
`canDeactivate` － 當使用者想要離開某個路由時，透過路由守門員來判斷要不要讓使用者離開。   

## `Resolve` 傳遞複雜資料   
```typescript
 const routes: Routes = [
  {
    path: 'products',
    component: ProductListComponent,
    children: [
      {
        path: ':id',
        component: ProductDetailComponent
        resolve: {
          product: ProductDetailResolverService
        }
      }
    ]
  },
  // ...
];
```

ProductDetailResolverService 大概會長這樣：
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

  resolve(
      route: ActivatedRouteSnapshot, 
      state: RouterStateSnapshot): Observable<Product> | Observable<never>
      {    
          const id = route.paramMap.get('id');
          return this.productService.getProduct(id)
                              // fetch procession only do one time
                                    .pipe(take(1),mergeMap(product => {
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


最後在 ProductDetailComponent 的頁面我們就透過這樣的方式來接值：
```typescript
ngOnInit() {
  this.route.data.subscribe((data: { product: Product }) => {
      this.name= data.product.name;
    });
    
}
```