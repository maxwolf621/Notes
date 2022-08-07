###### tags: `Angular`
# Life Hooks

- [Life Hooks](#life-hooks)
  - [Reference](#reference)
  - [Introduction of Life hooks](#introduction-of-life-hooks)
  - [宣告Lifecycle Hooks](#宣告lifecycle-hooks)
  - [`OnChanges` (`@Input`)](#onchanges-input)
    - [`ngOnChanges(changes : SimpleChanges)`](#ngonchangeschanges--simplechanges)
  - [`OnInit`](#oninit)
  - [`DoCheck`](#docheck)
  - [`AfterContentInit` and `AfterContentChecked`](#aftercontentinit-and-aftercontentchecked)
  - [`AfterViewInit` 與 `AfterViewChecked` for `@ViewChild` in 父Component](#afterviewinit-與-afterviewchecked-for-viewchild-in-父component)
  - [`OnDestroy`](#ondestroy)

## Reference

- [[Angular 大師之路] Day 04 - 認識 Angular 的生命週期]((https://ithelp.ithome.com.tw/articles/10203203))
## Introduction of Life hooks

一個Component完整的Life Hooks順序  
![](https://i.imgur.com/qQNg86Y.png)  


當Angular核心(core) Application建立元件並進入初始化的生命週期時，會判斷其中是否存在其Life Hooks，若有則依序執行Life Hook(Method), 便能在Component初始化時執行某些行為  

## 宣告Lifecycle Hooks
Angular 也提供了所有生命週期的`Interface`宣告，放在 `@angular/core` 中
```typescript
import { Component, OnInit } from '@angular/core';
@Component({
  ...
})
export class AppComponent implements OnInit {
  ngOnInit() {
    // do something...
  }
}
/**
 * <pre>
 * export interface OnInit {
 *    ngOnInit(): void;
 * }
 * </pre>
 */
```
- 我們加入了`OnInit`的介面，在宣告元件類別時，加上了 implements OnInit，讓 TypeScript 在處理程式時，知道我們需要實作 `OnInit`介面中宣告的方法


## `OnChanges` (`@Input`)

**An life hook that keeps eyes on the `@Input` variable**

當元件有 `@Input()` 且從外部有透過**Property Binding**的方式將資料傳入時，當Component初始化時在 `ngOnInit()` 前呼叫 `ngOnChanges()` 方法；**且每當 `@Input()` 的值有變化時，都會呼叫此方法，藉此得知資料被改變了**  
```
value is passed to this component 
-> @Input 
-> ngOnChanges 
-> ngOnInit
-> ...
```

For example, `Onchange` will call `ngOnchanges` method as long as `@input price` is changed  
```typescript
import { Component, Input, OnChanges, OnInit } from '@angular/core';

/**
 * 子PriceComponent 
 */
@Component({
  selector: 'app-price',
  template: `<span>{{ price }}</span>`
})
export class PriceComponent implements OnInit, OnChanges {
  /**
   * <pre> ngOnChanges() </pre>
   *       will keep eyes on it
   */
  @Input() price;

  /**
   *  RUN after ngOnChanges
   */
  ngOnInit() {
    console.log('Init Price Component ');
  }

  /**
   * IF @input()  
   * is changed 
   * it runs
   */
  ngOnChanges() 
    console.log('Price Component Input Changed ');
  }
}

/**
 *  父App Component
 */
@Component({
  selector: 'my-app',
  /**
   * Property Binding 
   * <child_selector [Child_variable] = "Father_variable"> ... </child_selector>
   * As value is updated 
   * ngOnChanges will be called
   */
  template: `<app-price [price]="value"></app-price`
})
export class AppComponent{
  value = 100;
}
```

### `ngOnChanges(changes : SimpleChanges)`

`ngOnChanges`的Parameter，為一個`SimpleChanges`型別的參數，`SimpleChanges`是一個key-value(`changes['keyName'] =  value`)
- key 代表的是每個 `@Input() variable`中的`variable`

SimpleChanges中三個key為
```typescript
firstChange：boolean // 只有在第一次呼叫時為true，之後都是 false
previousValue        // 上次key's value
currentValue         // key's updated value
```

For Example 我們可以利用`SimpleChanges`將一個將賠率/股價等類型的資訊，放到`PriceComponent`中，並依照資訊的增減呈現不同變化的Project
```typescript
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-price',
  /**
   * Class Binding [ngClass] : 決定顯示 {{price}}顏色是red or green
   *       red 如果目前價格高於上次的價格 => 選擇Css styles attribute .increase
   *       green 如果目前價格低於上次的價格 => 選擇Css styles attribute .decrease
   * firstChange : 決定是否顯示'↑'或'↓' 還是 '' 
   */
  template: `
    <span class="price" [ngClass]="{increase: price > lastPrice, decrease: price < lastPrice }">
      {{ price }
    <span>{{ firstChange ? '' : (price > lastPrice ? '↑' : '↓') }}</span>
  </span>`
  ,
  styles: [
    ` .price { background: black;  color: white; }

      // dynamically control <span class"price"> element  via [ngClass]
      .increase { color: red; }
      .decrease { color: green; }`
  ]
})
export class PriceComponent implements OnInit, OnChanges {
  
  firstChange = true;
 
  /**
   * Assigned in the ngOnchange
   * If @input price is changed from Base Component
   */
  lastPrice;
  
  /**
   * Assigned By Base
   */
  @Input() price;

  ngOnInit() {
    console.log('Init Price Component ');
  }

  /**
   * @description Keep Listening OnChanges 
   */
  ngOnChanges(changes: SimpleChanges) {
    console.log('Price Component Input Changed ');
    
    /** 
     * @description (changes['price']) key : `price` 是否第一次做改變 只有第一次時會是true 
      **/
    this.firstChange = changes['price'].firstChange;
    
    /** 
     * @description (changes[price]) key : price 上次的值
     * **/
    this.lastPrice = changes['price'].previousValue;
  }
}
```
![](https://wellwind.idv.tw/blog/2018/10/19/mastering-angular-04-life-cycles/02.gif)

## `OnInit`

在第一次執行完`ngOnChanges`後(如果有的話)，就會進入`ngOnInit`週期，**大部分的初始化程式都建議在`ngOnInit()`週期中執行，而非在Constructor做處理，尤其是比較複雜程式或`ajax`呼叫，建議都在 `ngOnInit`中執行。**
 
- 放在Constructor中初始化明顯的缺點是：撰寫單元測試時，由於Constructor本身對外部程式的依賴(Dependency)太重(Import的Package都在Constructor進行DI)，**容易造成測試程式難以撰寫**  

## `DoCheck`

**`ngDoCheck`會在Angular核心程式執行變更偵測後呼叫，我們可以在這裡面額外撰寫程式來處理變更偵測所無法偵測到的部分**  

若我們把原來的傳入child component `priceComponent`的`price`值的資料型態改為`Object` (`price = { value = 100 }`)  
```typescript
/**
 * @description ----------Child Component
 */
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-price',
  template: `
  <span class="price" [ngClass]="{increase: price > lastPrice, decrease: price < lastPrice }">
    {{ price.value }}
    <span>{{ firstChange ? '' : (price > lastPrice ? '↑' : '↓') }}</span>
  </span>
  `,
  styles: [
    ` .price { background: black;  color: white; }
      .increase { color: red; }
      .decrease { color: green; }
    `
  ]
})
export class PriceComponent implements OnInit, OnChanges {
  firstChange = true;
  
  lastPrice;
  
  @Input() price;

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges) {
    console.log('Price Component Input Changed ');
    this.firstChange = changes['price'].firstChange;
    this.lastPrice = changes['price'].previousValue;
  }
}


/**
 *  @description --------------Base Component 
 * 
 */
@Component({
  selector: 'my-app',
  template: `
  <app-price [price]="priceObj"></app-price>
  <button (click)="increase()">Increase</button>
  <button (click)="decrease()">Decrease</button>
  `
})
export class AppComponent {
  
  /**
   * @description 
   * 當資料時態如果為Object就會有Reference/Copy的問題了
   */
  priceObj = { value: 100 };
  increase() {
    this.priceObj.value += 2;
  }
  decrease() {
    this.priceObj.value -= 2;
  }
}
```
- **由於是`@input`的資料型態為`Object`所以除了第一次以外，該`@Input`完全不再進入`pricecomponent`'s `OnChanges`生命週期中，因此偵測不到資料是否真的有變化了，這是因為在變更偵測時，我們的`priceObj`本身的**參考位置(Reference)**沒有改變的關係**，因此在變更偵測時Angular認為`priceObj`這個`Input`並沒有變更。  

要改變這個結果有兩種方式，一種是複製一個新的物件再改變新物件的內容，並把`price`指派為新的物件(Object)，此時因為物件的參考位置修改了，變更偵測就能夠認得；當然每次都建立新物件是有成本的，另一個方法則是利用`DoCheck`週期進行判斷
```typescript
import { Component, DoCheck, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-price',
  template: `
  <span class="price" [ngClass]="{increase: priceIncrease, decrease: priceDecrease }">
    {{ price.value }}
    <span>{{ firstChange ? '' : (priceIncrease ? '↑' : '↓') }}</span>
  </span>
  `,
  styles: [
    ` .price { background: black;  color: white; }
      .increase { color: red; }
      .decrease { color: green; }
    `
  ]
})
export class PriceComponent implements OnInit, DoCheck {
  firstChange = true;
  lastPriceValue:number;
  
  priceIncrease: boolean;
  priceDecrease: boolean;
  
  @Input() price: { value: number };

  ngOnInit() {}

  ngDoCheck() {
    // 在這裡主動判斷資料是否有變更 
    // (ngDocheck判斷 ngOnChanges 所無法判斷的部分)
    if (this.price && this.lastPriceValue && this.price.value !== this.lastPriceValue) {
      this.firstChange = false;
      this.priceIncrease = this.price.value > this.lastPriceValue;
      this.priceDecrease = this.price.value < this.lastPriceValue;
    }
    this.lastPriceValue = this.price.value;
  }
}
```
## `AfterContentInit` and `AfterContentChecked`

在設計Component時，我們會使用`<ng-content>`來允許使用元件的時候放置更多的HTML內容，我們常使用`<ng-content>`來設計類似tabs功能的Component  
例如, `BlockComponent`中的`template`透過了`<ng-content>`的方式，**讓顯示的內容改為由使用父元件來決定，增加元件的彈性**

- `<ng-content>` : 子元件的`Template`交給父元件`@ContentChild`或`@ContentChildren`的成員來決定

```typescript
/**
 * ---------- Child Component
 */
import { Component } from '@angular/core';

@Component({
  selector: 'app-block',
  template: `
  <div class="block">

    <!-- ng-content表示顯示的內容交給父元件 -->
    <ng-content></ng-content>
  
  </div>
  `,   
  styles: [
    `.block { border: 1px solid black; }`
  ]
})
export class BlockComponent { }

/**
 * ---------- Base Component
 */
@Component({
  selector: 'my-app',
  template: `
  <button (click)="tab = 1">Tab 1</button>
  <button (click)="tab = 2">Tab 2</button>
  <button (click)="tab = 3">Tab 3</button>
  <app-block *ngIf="tab === 1">
    Tab 1
  </app-block>
  <app-block *ngIf="tab === 2">
    Tab 2
  </app-block>
  <app-block *ngIf="tab === 3">
    Tab 3
  </app-block>`
})
export class AppComponent{
  tab = 1;
}
```
- **每偵測到變更時，`AfterContentChecked`總會在`DoCheck`後觸發**  

在使用`<ng-content>`的元件內，我們可以使用`@ContentChild`來取得某個Template參考(reference)變數實體或子元件，若父元件在使用時有加入符合`@ContentChild`設定的條件時，在`AfterContentInit`週期就可以取得其實體，若想取得多個實體，則可以使用`@ContentChildern`來取得一個包含所有實體的`QueryList`

```diff
兩種模式
父 : @ContentChild
       '
     (bind)   
       '---> 子的AfterContentInit contains Element
父 : @ContentChildren
       '
     (bind)
       '---> 子的AfterContentInit contains QueryList
```

For example
```typescript
import { AfterContentChecked, AfterContentInit,  ContentChild, ContentChildren, Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-block',
  template: `
  <div class="block">
    <ng-content></ng-content>
  </div>`,
  styles: [
    `.block { border: 1px solid black; }`
  ]
})
export class BlockComponent {
  @ContentChild('button') button;
  @ContentChildren('button') buttons;
  
  ngOnInit() {
    // 此時還得不到 <ng-content> 裡面的內容
    console.log('OnInit - The Button is', this.button);
    console.log('OnInit - The Buttons are', this.buttons);
  }

  ngAfterContentInit() {
    // 此時才能取得 <ng-content> 裡面的內容
    console.log('AfterContentInit - The Button is ', this.button);
    console.log('AfterContentInit - The Buttons are ', this.buttons);
  }

  ngAfterContentChecked() {
    // 在 <ng-content> 內變更偵測都完成後觸發
    console.log('AfterContentChecked - The Button is ', this.button);
    console.log('AfterContentChecked - The Buttons are ', this.buttons);
  }
}
```

```typescript
@Component({
  selector: 'my-app',
  template: `
  <button (click)="tab = 1">Tab 1</button>
  <button (click)="tab = 2">Tab 2</button>
  <button (click)="tab = 3">Tab 3</button>
  <app-block *ngIf="tab === 1">
    Tab 1
    <button #button (click)="tab = 2">Next</button>
  </app-block>
  <app-block *ngIf="tab === 2">
    Tab 2
    <button #button (click)="tab = 3">Next</button>
  </app-block>
  <app-block *ngIf="tab === 3">
    Tab 3
    <button #button (click)="tab = 1">Tab 1</button>
    <button #button (click)="tab = 2">Tab 2</button>
  </app-block>
  `
})
export class AppComponent{
  tab = 1;
}
```

## `AfterViewInit` 與 `AfterViewChecked` for `@ViewChild` in 父Component

**在開發Component時，我們常常會使用`@ViewChild`取得父`@Component.template`上的某個子Component宣告**
```typescript
@ViewChild(Type) variable;
```
- `Type` can be HTML's tag (e.g. `'button'`) or component (e.g. `AppComponent`) 

如果想取得`@Component.template`上指定的某個子元件的所有宣告，則可以使用`@ViewChildren`取得一個包含所有子Component的`QueryList`，這些子Component在其父元件的`OnInit`週期時還不會產生實體，必須在`AfterViewInit`之後，才能正確取得實體

```typescript
/**
 * 子元件
 */
import { AfterViewChecked, 
         AfterViewInit, 
         Component, 
         OnInit, 
         Input, 
         ViewChild, 
         ViewChildren, 
         QueryList } from '@angular/core';

@Component({
  selector: 'app-child',
  template: `<div>Child {{ value }}</div>`,

})
export class ChildComponent {
  @Input() value

/**
 * ---- 父元件
 */
@Component({
  selector: 'my-app',
  // View child-component 
  template: `         
  
  <button (click)="create()" #button>Create New Child</button>
  
  <app-child *ngFor="let item of list" [value]="item"></app-child>      

  `
})
export class AppComponent implements OnInit, AfterViewInit, AfterViewChecked {
  
  // 對應 html的 #button
  @ViewChild('button') button;
  
  @ViewChild(ChildComponent) child;
  @ViewChildren(ChildComponent) children: QueryList<ChildComponent>;

  list = [0];

  ngOnInit() {
    // 在這裡可以使用 @ViewChild 取得某個原生的 DOM
    // 但取不到子元件實體
    console.log('Button in OnInit', this.button);
    console.log('Child in OnInit', this.child);
    console.log('Children in OnInit', this.children);
  }

  ngAfterViewInit() {
    // 在 AfterViewInit 中可以取得子元件實體
    // 使用 @ViewChild 時，永遠只會取到第一個子元件
    console.log('Child in AfterViewInit', this.child);
    console.log('Children in AfterViewInit', this.children);
  }

  ngAfterViewChecked() {
    // 在每次樣板上元件的變更偵測都完成後觸發
    console.log('Child in AfterViewChecked', this.child);
    console.log('Children in AfterViewChecked', this.children);
  }

  create() {
    this.list = [...this.list, this.list.length];
  }

}
```

## `OnDestroy`

`OnDestroy`會在元件不需要被使用時，觸發`ngOnDestroy`方法，通常用來處理一些清理資料行為，若有些程式是不會在元件消失時被清除的，則需要在這個週期內額外處理，最常見的就是使用`RxJS`且有`subscribe`行為時，可能需要額外處理退訂的動作，以免重複訂閱或產生預期外的行為

```typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { interval, Subscription } from 'rxjs';

@Component({
  selector: 'app-timer',
  template: `{{ counter }}`
})
export class TimerComponent implements OnInit, OnDestroy {
  counter = 0;
  subscription: Subscription;

  ngOnInit() {
    // observer 
    this.subscription = interval(1000).subscribe(val => {
      this.counter = val;
      console.log(this.counter);
    })
  }
  
  ngOnDestroy() {
    // Destroy subscribe 
    this.subscription.unsubscribe();
  }
}


@Component({
  selector: 'my-app',
  template: `
  Timer: <app-timer *ngIf="displayTimer"></app-timer>
  <button (click)="displayTimer = !displayTimer">Toggle Display Timer</button>`
})
export class AppComponent{
  displayTimer = true;
}
```
