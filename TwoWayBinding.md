###### tags: `Angular`
# Two Way Binding

在 Angular 中，通常我們會使用` [(ngModel)] `來實現 two way binding，這樣做基本上沒有什麼問題，但 ngModel 只能 binding 在常見的表單控制項上，如 input、select 等，難以自行在元件上時作出 two way binding，雖然還是可以做到，但相對麻煩了一點，今天就先來講一個簡單的實現 two way binding 的技巧吧！


```typescript
@component({
  selector: 'app-info',
  template: `
    Name <input type="text" [(ngModel)]="name">
  `
})
export class InfoComponent {
  // Attributes or Properties
  //    can be a tag's attribute 
  @Input() name;
}

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

- 可以發現當 InfoComponent 內的資料變動時，外部的變數並不會跟著異動，這很正常，因為在 @Input() 收到資料後，就已經是另外一個記憶體位置了，因此變動時不會同步變動原來的變數內容，這時候我們可以簡單的加個 @Output 來解決，完成類似 two way binding 的效果：

```typescript
@Component({
  selector: 'app-info',
  template: `
    Name <input type="text" [(ngModel)]="name" (input)="nameChange.emit(name)">
  `
})
export class InfoComponent {
  @Input() name;
  @Output() nameChange = new EventEmitter();;
}

@Component({
  selector: 'my-app',
  template: `
    <app-info [name]="name" (nameChange)="name = $event"></app-info>
    Result: {{ name }}
  `
})
export class AppComponent  {
  name = 'Mike'
}
```
執行結果如下：

![](https://imgur.com/download/bYmH1X3)


不過這樣寫必須先 binding 一個屬性，在 binding 一個事件，還是稍微麻煩一點，實際上在 Angular 中，當有一個名為 xxx 的 @Input 時，只需要有一個對應的 xxxChange 的 @Output，即可使用 `[(xxx)]` 的方式，來完成 two way binding！
所以上面程式在使用 `InfoComponent` 時，只需要改成：
```typescript=
<app-info [(name)]="name"></app-info>
```

同樣的原理我們再來看看`[(ngModel)]` ，其實就是一個 ngModel 的 @Input 以及一個 ngModelChange 的 @Output，因此反過來我們如果想知道 binding 的值有變化時，只需要用 event binding 的方式，處理 ngModelChange 就可以了，如下：
```javascript
<input type="text" [(ngModel)]="name" (ngModelChange)="doSomething()">
```
