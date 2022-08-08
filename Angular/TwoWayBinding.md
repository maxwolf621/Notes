# Two way binding of `[(ngMOdel)]`, `@Input` and `Output`

- [[Angular 大師之路] Day 07 - 一個簡易實踐 two way binding 的方法](https://ithelp.ithome.com.tw/articles/10204241)

Two-way binding combines property binding with event binding :
```html
<app-sizer [(size)]="fontSizePx"></app-sizer>
```

----

`sizer.component.ts`
```typescript
export class SizerComponent {

  @Input()  size!: number | string;
  @Output() sizeChange = new EventEmitter<number>();

  dec() { this.resize(-1); }
  inc() { this.resize(+1); }

  resize(delta: number) {
    this.size = Math.min(40, Math.max(8, +this.size + delta));
    this.sizeChange.emit(this.size);
  }
}
```

`src/app/sizer.component.html`
```html
<div>
  <button type="button" (click)="dec()" title="smaller">-</button>
  <button type="button" (click)="inc()" title="bigger">+</button>
  <span [style.font-size.px]="size">FontSize: {{size}}px</span>
</div>
```

In the AppComponent template, `fontSizePx` is two-way bound to the SizerComponent.

`src/app/app.component.html`
```html
<app-sizer [(size)]="fontSizePx"></app-sizer>
<div [style.font-size.px]="fontSizePx">Resizable Text</div>
```

In the AppComponent, fontSizePx establishes the initial SizerComponent.size value by setting the value to 16.

`src/app/app.component.ts`
```typescript
fontSizePx = 16;
```
- Clicking the buttons updates the `AppComponent.fontSizePx.` 
The revised 1AppComponent.fontSizePx1 value updates the style binding, which makes the displayed text bigger or smaller.

The two-way binding syntax is shorthand for a combination of property binding and event binding. The SizerComponent binding as separate property binding and event binding is as follows.

`src/app/app.component.html`
```html
<app-sizer [size]="fontSizePx" (sizeChange)="fontSizePx=$event"></app-sizer>
```
The `$event` variable contains the data of the `SizerComponent.sizeChange` event.   
Angular assigns the `$event` value to the `AppComponent.fontSizePx` when the user clicks the buttons.

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
`[(xxx)]` is counterpart of `@input xxx` and `@Output xxxChange`.  

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

## Directive `[(ngModel)]` with Event Binding

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
