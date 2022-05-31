# Two way binding of `[(ngMOdel)]`, `@Input` and `Output`

- [[Angular 大師之路] Day 07 - 一個簡易實踐 two way binding 的方法](https://ithelp.ithome.com.tw/articles/10204241)

## Two way binding via `@Input` and `@Output`

We can use `Output()` to emit new value to `name` in `AppComponent` once value of `name` in `InfoComponent` is modified/changed
```typescript

// Sub Component
@Component({
  selector: 'app-info',
  template: `
    Name <input type="text" 
          [(ngModel)]="name" 
          (input)="nameChange.emit(name)">
  `
})
export class InfoComponent {
  @Input() name;  
  
  // emitter
  @Output() nameChange = new EventEmitter();;
}


// Base Component
@Component({
  selector: 'my-app',
  template: `
    <app-info 
    
    <!--
       pass value of name "Mike" to child component 
    -->
    [name]="name"

    <!-- 
       receive nameChange from child component 
    -->
    (nameChange)="name = $event"> 
    </app-info>
    
    Result: {{ name }}
  `
})
export class AppComponent  {
  name = 'Mike'
}
```

## Directive `[(sub_var)] = base_var`

```typescript
<sub_component_selector [(xxx)] ="xxx">
</sub_component_selector> 
```
`[(xxx)]`is counterpart of `@input xxx` and `@Output xxxChange`.  

It emits the value of `xxx` from subComponent to baseComponent Template(`.html`)'s `[(xxx)] = var_in_baseComponent`

- For example
```typescript
/**
  * Base  
  */
@Component({
  selector: 'my-app',
  template: `
    <app-info [(name)]="name"> </app-info>
    
    Result: {{ name }}
  `
})
export class AppComponent  {
  name = 'Mike'
}
```

## Directive `[(ngModel)]` with Even Binding

The above example showed that `[(ngModel)]` is actually counterpart of `@Input ngModel` and `@Output ngModelChange`

It creates a `FormControl` instance from a domain model and binds it to a form control element( `input`, `select` ...) of html
```typescript
// InfoComponent
@component({
  selector: 'app-info',
  template: `
    Name <input type="text" 
          [(ngModel)]="name">
  `
})
export class InfoComponent {
  @Input() name;
}


// AppComponent
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

Because `name` in InfoComponent and `name` in AppComponent has it own address.  

We can then use even binding to listen the binding value, for example
```html
<!-- 
  if ngModel is modified 
  then call doSomething()
-->
<input type="text" 
       [(ngModel)]="name" 
       (ngModelChange)="doSomething()">
```
