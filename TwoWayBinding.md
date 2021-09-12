###### tags: `Angular`
# [Two Way Binding via `@input` and `output`](https://ithelp.ithome.com.tw/articles/10204241)

在 Angular 中，通常會使用` [(ngModel)] `來實現Two way binding，這樣做基本上沒有什麼問題，**但`ngModel`只能 binding 在常見的表單控制項上**，如`input`、`select` ... etc ...

如果自行在元件上時作出two way binding，相對麻煩了一點  
```typescript
/**
  * InfoComponent
  */
@component({
  selector: 'app-info',
  template: `
    Name <input type="text" [(ngModel)]="name">
  `
})
export class InfoComponent {
  /**
    * [ngModel]="name"
    */
  @Input() name;
}

/**
  * AppComponent
  */
@Component({
  selector: 'app-root',
  template: `
    <app-info [name]="name"></app-info>
    Result: {{ name }}
  `
})
export class AppComponent  {
  name = 'Mike'
}
```
- 當`InfoComponent`內的資料變動時，外部的變數並不會跟著異動, **因為在`@Input() xxx`收到資料後，`xxx`就已經是另外一個記憶體位置了，因此變動時不會同步變動原來的變數內容**，這時候我們可以簡單的加個`@Output xxxChange`來解決，完成類似two way binding的效果


```typescript
/**
  * InfoComponent
  */
@Component({
  selector: 'app-info',
  template: `
    Name <input type="text" [(ngModel)]="name" (input)="nameChange.emit(name)">
  `
})
export class InfoComponent {
  @Input() name;  

  /**
   * 將name改變後的value傳給base component
   */
  @Output() nameChange = new EventEmitter();;
}

/**
  * Base
  * we assign nameChange from child component to name in this component 
  */
@Component({
  selector: 'my-app',
  template: `
    <app-info 
    [name]="name"                  <!-- pass name to child component -->
    (nameChange)="name = $event">  <!-- receive namechange from child component -->
    </app-info>
    
    Result: {{ name }}
  `
})
export class AppComponent  {
  name = 'Mike'
}
```

執行結果如下  
![](https://imgur.com/download/bYmH1X3)    

不過這樣寫必須先 binding 一個屬性，在 binding 一個事件，還是稍微麻煩一點，實際上在 Angular 中，當有一個名為`xxx`的`@Input`時，只需要有一個對應的`xxxChange`的`@Output`，即可使用 `[(xxx)]` 的方式，來完成 two way binding


在`@Input xxx` and `@output xxxChange` in child Component 則 我們的base component's template此時就可以使用 `[(xxx)] = Base's member`表示需要得到value傳遞給child component後改變的值

```typescript
/**
  * AppComponent 
  */
@Component({
  selector: 'my-app',
  template: `
    <app-info [(name)]="name">
    </app-info>
    Result: {{ name }}
  `
})
export class AppComponent  {
  name = 'Mike'
}
```

透過上面的`[{name}]="name"`, 我們可以知道`[(ngModel)]`, 其實就是一個`ngModel`的`@Input`以及一個`ngModelChange`的`@Output`，因此如果想知道binding的值是否有變化時，只需要用`event binding`的方式，處理`ngModelChange`就可以了，如下：

```typescript
<-- 
利用ngModelChange監聽如果two way binding variable name有變化的話就call doSomething()
-->
<input type="text" [(ngModel)]="name" (ngModelChange)="doSomething()">
```