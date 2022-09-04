# ngTemplateOutlet

[Reference :: [Angular 大師之路] Day 12 - *ngTemplateOutlet 與 ng-template 的完美組合](https://ithelp.ithome.com.tw/articles/10205829)

- [ngTemplateOutlet](#ngtemplateoutlet)
    - [Syntax](#syntax)
  - [透過Outlet傳參數給Template](#透過outlet傳參數給template)
  - [Outlet傳入多個參數到Template (`context : { ... }`)](#outlet傳入多個參數到template-context----)
  - [In Action](#in-action)

The ngTemplateOutlet, is a structural directive, which renders the template.
`*ngTemplateOutlet` 用來放置 `<ng-template>`，概念就與 `<router-outlet>` 雷同，在 Angular 的命名中，當我們看到 `....Outlet`結尾，都能想像成一個Container儲存特定的物件
透過 `*ngTemplateOutlet="Template's名稱"` 來顯示某個特定Template，減少Template
重複
### Syntax

```html 
<ng-template #NameOfTemplate>
  ...                ^
  ....                \
</ng-template>         \ 
                        \
<div *ngTemplateOutlet = "NameOfTemplate">
</div>


<!-- 
  for example 
-->
<ng-template #data>
  Hello World
</ng-template>
<div *ngTemplateOutlet="data"></div>
```

## 透過Outlet傳參數給Template

在使用 `*ngTemplateOutlet` 時，可以加上 `context : {..}` 代表要傳入 <ng-template> 的參數，例如
```html
<ng-template #data let-input="$implicit">
  {{ input | json }}
</ng-template>

<div *ngTemplateOutlet="data; context: {$implicit: {value: 1}}"></div>
```
- 呼叫`data`樣板後，使用 `context`，並帶入一個物件 `{$implicit: {value: 1}}`，`$implicit` 是一個固定用法，當使用帶入一個物件並有個`$implicit`的屬性時，後面的內容就會被當作帶入`<ng-template>`的預設參數。
- `<ng-template let-參數名=來自ngTemplateOutlet參數>` : 該Template接受來
自ngTemplateOutlet的參數
- `{{input | json }}` : 參數以json方式呈現

## Outlet傳入多個參數到Template (`context : { ... }`)

當我們有其他的參數時，也可以直接放到 context 裡面
```html
<ng-template #data let-input let-another="another">
  <div>{{ input | json }}</div>
  <div>{{ another | json }}</div>
</ng-template>

<div *ngTemplateOutlet="data; context: {$implicit: {value: 1}, another: {value: the other parameter}}"></div>
```

## In Action

我們可以建立一個directive，並掛在每個`<ng-template>` HTML ELEMENT上，之後在程式內就可以使用 `@ViewChildren`的方式，取得`<ng-template>`的QueryList
```typescript
/**
 *  For Host Element , 
 * <Host-Ellement CarouselPageDirective></Host-Element>
 */

@Directive({
  selector: '[appCarouselPage]'
})
export class CarouselPageDirective {
  
  constructor(public templateRef: TemplateRef<any>) { }
}
```
- 這裡的 `templateRef` 表示 `每個CarouselPageDirective`所屬的Host Element，並將它設為`public`，以便利用`@ViewChildren`拿到 Directive 時同時可以拿到特定Host Element


```html
<ng-template appCarouselPage>
  Page 1
</ng-template>
<ng-template appCarouselPage>
  Page 2
</ng-template>
<ng-template appCarouselPage>
  Page 3
</ng-template>
<div *ngTemplateOutlet="displayPage"></div>
```

在 `*ngTemplateOutlet` 內放置了一個 `displayPage` 變數，利用`@ViewChildren`指定特定Template給`displayPage`
```typescript
export class AppComponent implements AfterViewInit {

  @ViewChildren(CarouselPageDirective)
  carouselPages: QueryList<CarouselPageDirective>;

  displayPage: TemplateRef<any>;

  // initial page
  index = 0;

  backgroundColor;

  setDisplayPage(index) {
    this.displayPage = this.carouselPages.find(
      (item, index) => index === this.index
    ).templateRef;
  }

  ngAfterViewInit() {
    //設定給要顯示資料的`displayPage`
    this.setDisplayPage(this.index);
  }

  next() {

    // go next page 
    this.index = (this.index + 1) % this.carouselPages.length;

    this.setDisplayPage(this.index);
  }

  setBackground() {
    if (this.backgroundColor) {
      this.backgroundColor = '';
    } else {
      this.backgroundColor = 'blue';
    }
  }
}

```

```html
<ng-template appCarouselPage let-bg="background">
  <span [style.background-color]="bg">Page 1</span>
</ng-template>
<ng-template appCarouselPage let-bg="background">
  <span [style.background-color]="bg">Page 2</span>
</ng-template>
<ng-template appCarouselPage let-bg="background">
  <span [style.background-color]="bg">Page 3</span>
</ng-template>

<div *ngTemplateOutlet="displayPage; 
                        context: {background: backgroundColor}"></div>

<br/>
<button (click)="next()">Next</button>

<br/>
<button (click)="setBackground()">Set Blue Background</button>
```

