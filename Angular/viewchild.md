# Access Child Component Field 
- [Access Child Component Field](#access-child-component-field)
  - [View Child](#view-child)
    - [Metadata Properties](#metadata-properties)
    - [Injecting Component or Directive Reference](#injecting-component-or-directive-reference)
    - [Injecting TemplateRef of Component](#injecting-templateref-of-component)
    - [Injecting ng-template Using TemplateRef](#injecting-ng-template-using-templateref)
    - [Injecting HTML Element Using ElementRef](#injecting-html-element-using-elementref)
    - [Multiple Instances](#multiple-instances)
    - [`ViewChild` returns `Undefined`](#viewchild-returns-undefined)
    - [Static Option](#static-option)
    - [Read Token](#read-token)
    - [Injecting a Provider from the Child Component](#injecting-a-provider-from-the-child-component)
  - [`ViewChildren(selector, read)`](#viewchildrenselector-read)
    - [QueryList](#querylist)
    - [QueryList Method & Properties](#querylist-method--properties)
    - [Listening for QueryList Changes](#listening-for-querylist-changes)
    - [In Action](#in-action)

## View Child

The `ViewChild` query returns the first matching element from the DOM and updates the component's field on which we apply it.

### Metadata Properties

`@ViewChild(selector, static = ... , read = ...)`

**selector**: can be a string, a type or a function that returns a string or type.   
The change detector looks for the first element that matches the selector and updates the component property with the reference to the element. If the DOM changes and a new element matches the selector, the change detector updates the component property.
1. Any class with the `@Component` or `@Directive` decorator
2. A template reference variable as a `string`  
e.g. `query <my-component #cmp></my-component>` with `@ViewChild('cmp')`
3. Any provider defined in the child component tree of the current component  
e.g. `@ViewChild(SomeService) someService: SomeService`
4.  Any provider defined through a string token  
e.g. `@ViewChild('someToken') someTokenVal: any`
5. A `TemplateRef`  
e.g. query `<ng-template TemplateRefName> ... </ng-template>` with `@ViewChild('TemplateRefName') template;`


**opts**: has two options.  
`static` Determines when the query is resolved.   
- `True` is when the view is initialized (before the first change detection) for the first time.   
- `False` if you want it to be resolved after every change detection   

**read**: Use it to read the different token from the queried elements.
- Any class with the `@Component` or `@Directive` decorator
- Any provider defined on the injector of the component that is matched by the selector of this query
- Any provider defined through a string token  
`e.g. {provide: 'token', useValue: 'val'}`
- `TemplateRef`, `ElementRef`, and `ViewContainerRef`

### Injecting Component or Directive Reference

```typescript
// Child
import { Component } from '@angular/core';
 
@Component({
  selector: 'child-component',
  template: `
  <h2>Child Component</h2>
  current count is {{ count }}`
})
export class ChildComponent {
  count = 0;
 
  increment() {
    this.count++;
  }
  decrement() {
    this.count--;
  }
}
 

// Parent
import { Component, ViewChild } from '@angular/core';
import { ChildComponent } from './child.component';
 
@Component({
  selector: 'app-root',
  template: `
        <h1>{{title}}</h1>
        <p> current count is {{child.count}} </p>
        <button (click)="increment()">Increment</button>
        <button (click)="decrement()">decrement</button>
        <child-component></child-component>
        ` ,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Parent calls an @ViewChild()';
  
  @ViewChild(ChildComponent, {static:true}) child: ChildComponent;
 
  increment() {
    this.child.increment();
  }
 
  decrement() {
    this.child.decrement();
  }
 
}
```

### Injecting TemplateRef of Component

```typescript
<child-component #child></child-component>
@ViewChild("child", { static: true }) child: ChildComponent;
```
### Injecting ng-template Using TemplateRef

```typescript
<ng-template #sayHelloTemplate>
  <p> Say Hello</p>
</ng-template>

@ViewChild("sayHelloTemplate", { static: false }) tempRef: TemplateRef;
```


### Injecting HTML Element Using ElementRef

```typescript
@Component({
    selector: 'htmlelement',
    template: `
      <p #para>Some text</p>
    `,
})
export class HTMLElementComponent implements AfterViewInit {
 
    @ViewChild('para',{static:false}) para: ElementRef;
 
    ngAfterViewInit() {
      console.log(this.para.nativeElement.innerHTML);
      this.para.nativeElement.innerHTML="new text"
    }
}
```

### Multiple Instances

The `ViewChild` always returns the first component.

```typescript 
<child-component></child-component>
<child-component></child-component>

@ViewChild(ChildComponent, {static:true}) child: ChildComponent;
```

### `ViewChild` returns `Undefined`

For the error is due to the fact that we try to use the value, before the `ViewChild` initializes it.

the component's view is not yet initialized when the constructor is run. Hence, the Angular yet to update child variable with the reference to the `ChildComponent`.

The solution is to wait until the Angular Initializes the View. Angular raises the AfterViewInit life cycle hook once it completes the View Initialization. So we can use the ngAfterViewInit to access the child variable.

```typescript
ngAfterViewInit() {
  this.child.increment()
}
```

The above code will also work with the `ngOnInit` Life cycle hook.  
**But it is not guaranteed to work all the time as the Angular might not initialize all parts of the view, before raising the ngOnInit hook. Hence it is always better to use the ngAfterViewInit hook.**

### Static Option

The static option determines the timing of the ViewChild query resolution.   
**`static:true` will resolves ViewChild before any change detection is run.**   
**`static:false` will resolves it after every change detection run.**   

The value of the `static` becomes important when the child is rendered dynamically.     
e.g. inside a `ngIf` or `ngSwitch` etc.       

For Example consider the following code, where we have moved the child-component inside the `ngIf`.
```typescript
 import { Component, ViewChild, AfterViewInit, OnInit, ChangeDetectorRef } from '@angular/core';
import { ChildComponent } from './child.component';
 
@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html' ,
  styleUrls: ['./app.component.css']
})
export class AppComponent { 
  title = 'ViewChild Example';
 
  showCounter: boolean = true
 
  @ViewChild(ChildComponent, { static: true }) child: ChildComponent;
 
  increment() {
    this.child.increment();
  }
 
  decrement() {
    this.child.decrement();
  }
 
}
<h1>ViewChild Example</h1>
 
<input type="checkbox" id="showCounter" name="showCounter" [(ngModel)]="showCounter">
 
<ng-container *ngIf="showCounter">
 
  <p> current count is {{child?.count}} </p>
 
  <button (click)="increment()">Increment</button>
  <button (click)="decrement()">decrement</button>
  <!-- Child Component is in ngIf -->
  <child-component></child-component>
 
</ng-container>
```
The above code results in a `TypeError: Cannot read property 'increment' of undefined`.     
The error occurs even if we assign `true` to `showCounter`.    

Because Angular does not render the child component immediately.   
But after the first change detection which detects the value of `showCounter` and renders the child component.

**Since we used `static: true`, the angular will try to resolve the `ViewChild` before the first change detection is run.**   
**Hence the child variable always will be `undefined`.**

### Read Token

A Single Element can be associated with multiple types.

```html
<input #nameInput [(ngModel)]="name">
```

```typescript
// return instance of elementRef
@ViewChild('nameInput',{static:false}) nameVar;

// Instance of ngModel
@ViewChild('nameInput',{static:false, read: NgModel}) inRef;

// Instance of ElementRef
@ViewChild('nameInput',{static:false, read: ElementRef}) elRef;

// Instance of ViewContainerRef
@ViewChild('nameInput', {static:false, read: ViewContainerRef }) vcRef;
```
- **The ViewChild without read token always returns the component instance if it is a component. If not it returns the `elementRef`.**

### Injecting a Provider from the Child Component

```typescript
// Child
@Component({
  selector: 'app-child',
  template: `<h1>Child With Provider</h1>`,
  providers: [{ provide: 'Token', useValue: 'Value' }]
})
 
export class ChildComponent{
}

// Parent
import { ViewChild, Component } from '@angular/core';
 
@Component({
  selector: 'app-root',
  template: `<app-child></app-child>`,
})
 
export class AppComponent{
    @ViewChild(ChildComponent , { read:'Token', static:false } ) childToken: string;
}
```

## `ViewChildren(selector, read)`

**Use to get the QueryList of elements or directives from the view DOM**. 

Any time a child element is added, removed, or moved, the query list will be updated, and the changes observable of the query list will emit a new value.

**ViewChildren always returns all the elements as a `QueryList`. You can iterate through the list and access each element.**

View queries are set before the `ngAfterViewInit` callback is called.

```typescript
import {AfterViewInit, Component, Directive, QueryList, ViewChildren} from '@angular/core';

@Directive({selector: 'child-directive'})
class ChildDirective {
}

@Component({selector: 'someCmp', templateUrl: 'someCmp.html'})
class SomeCmp implements AfterViewInit {
  //             componentName   variableName : QueryLList<componentName>
  @ViewChildren(ChildDirective) viewChildren!: QueryList<ChildDirective>;

  ngAfterViewInit() {
    // viewChildren is set
  }
}
```

### QueryList


- The `QueryList` is the return type of `ViewChildren` and `contentChildren` . 
- QueryList stores the items returned by the `viewChildren` or `contentChildren` in a list.
- The Angular updates this list, whenever the state of the application change. It does it on each change detection.
- The `QueryList` also Implements an iterable interface. Which means you can iterate over it using  for `(var i of items)` or use it with `ngFor` in template `*ngFor="let i of items"`.
### QueryList Method & Properties   
`first`: returns the first item in the list.     
`last`: get the last item in the list.     
**`changes`: is an observable. It emits a new value, whenever the Angular adds, removes or moves the child elements.**     
`length`: get the length of the items.     


It also supports JavaScript array methods like `map()`, `filter()` , `find()`, `reduce()`, `forEach()`, `some()`. etc

### Listening for QueryList Changes

```typescript
import { ViewChild, Component, ViewChildren, QueryList, AfterViewInit } from '@angular/core';
import { NgModel } from '@angular/forms';
 
@Component({
  selector: 'app-viewchildren2',
  template: `
      <h1>ViewChildren Example</h1>
 
      <input *ngIf="showFirstName" name="firstName" [(ngModel)]="firstName">
      <input *ngIf="showMiddleName" name="middleName" [(ngModel)]="middleName">
      <input *ngIf="showLastName" name="lastName" [(ngModel)]="lastName">
 
      <input type="checkbox" id="showFirstName" name="showFirstName" [(ngModel)]="showFirstName">
      <input type="checkbox" id="showMiddleName" name="showMiddleName" [(ngModel)]="showMiddleName">
      <input type="checkbox" id="showLastName" name="showLastName" [(ngModel)]="showLastName">
 
      <button (click)="show()">Show</button>
  `,
})
 
export class ViewChildrenExample2Component implements AfterViewInit {
 
  firstName;
  middleName;
  lastName;
 
  showFirstName=true;
  showMiddleName=true;
  showLastName=true;
 
  @ViewChildren(NgModel) modelRefList: QueryList<NgModel>;
 
  ngAfterViewInit() {
    this.modelRefList.changes.subscribe(
      data => {
        console.log(data)
      }
    )
  }
  
  show() {
    this.modelRefList.forEach(element => {
      console.log(element)
      //console.log(element.value)
    });
 
  }
}
```



### In Action 

```typescript
// Directive
import {AfterViewInit, Component, Directive, Input, QueryList, ViewChildren} from '@angular/core';

@Directive({selector: 'pane'})
export class Pane {
  @Input() id!: string;
}

// Component
@Component({
    selector: 'example-app',
    template: `
        <pane id="1"></pane>
        <pane id="2"></pane>
        <pane id="3" *ngIf="shouldShow"></pane>

        <button (click)="show()">Show 3</button>

        <div>panes: {{serializedPanes}}</div>
    `,
})
export class ViewChildrenComp implements AfterViewInit {

    // QueryList of Directive
    @ViewChildren(Pane) panes!: QueryList<Pane>;
    
    serializedPanes: string = '';

    shouldShow = false;

    show() {
        this.shouldShow = true;
    }

    ngAfterViewInit() {
      this.calculateSerializedPanes();
        
      this.panes.changes.subscribe((r) => {
        this.calculateSerializedPanes();
      });
    }

    calculateSerializedPanes() {
      setTimeout(() => {
        this.serializedPanes = this.panes.map(p => p.id).join(', ');
      }, 0);
    }
}
```
