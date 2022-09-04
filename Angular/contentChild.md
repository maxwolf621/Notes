# ContentChild

[Reference](https://www.tektutorialshub.com/angular/contentchild-and-contentchildren-in-angular/)

- [ContentChild](#contentchild)
  - [Selector or Query Selector](#selector-or-query-selector)
  - [ContentChild's `static`](#contentchilds-static)
  - [ContentChild's Read Token](#contentchilds-read-token)
  - [ContentChildren](#contentchildren)


Syntax
```typescript
ContentChild(selector: string | Function | Type<any>, opts: { read?: any; static: boolean; }): any
```

## Selector or Query Selector

The change detector looks for the first element that matches the selector and updates the component property with the reference to the element. 

If the DOM changes and a new element matches the selector, the change detector updates the component property

**The query selector can be a string, a type, or a function that returns a string or type. The following selectors are supported.**

```typescript
//Using a Template Reference Variable
@ContentChild("header") cardContentHeader: ElementRef;

//Using component/directive as type
@ContentChild(childComponent) cardChildComponent: childComponent;
```


```typescript
import { Component } from '@angular/core';
 
@Component({
  selector: 'card-list',
  template: `
  
  <h1> Card List</h1>
 
      <card>
        <header><h1 #header>Angular</h1></header>
        <content>One framework. Mobile & desktop.</content>
        <footer><b>Super-powered by Google </b></footer>
      </card>
        
      <card>
        <header><h1 #header style="color:red;">React</h1></header>
        <content>A JavaScript library for building user interfaces</content>
        <footer><b>Facebook Open Source </b></footer>
      </card>
 
      <card>
        <header> <h1 #header>Typescript</h1> </header>
        <content><a href="https://www.tektutorialshub.com/typescript-tutorial/"> Typescript</a> is a javascript for any scale</content>
        <footer><i>Microsoft </i></footer>
      </card>
 
  `,
})
export class CardListComponent {}
```

```typescript
@ContentChild("header") cardContentHeader: ElementRef;
```


Because of Component lifecycle hooks. 
The angular initializes the component first.     
It then raises the `ngOnChanges`, `ngOnInit` & `ngDoCheck` hooks.   
The projected components are initialized next.    

And then Angular raises the `AfterContentInit` & `AfterContentChecked` hooks. 
**Hence the `cardContentHeader` is available to use only after the `AfterContentInit` hook.**

Once, we have reference to the DOM Element, we can use the `renderor2` to manipulate its styles etc
```typescript
ngAfterContentInit() {
    this.renderor.setStyle(this.cardContentHeader.nativeElement,"font-size","20px")
}
```

## ContentChild's `static`

Determines when the query is resolved. 
- `True` is when the view is initialized (before the first change detection) for the first time. 
- `False` if you want it to be resolved after **every change detection**


## ContentChild's Read Token

```html
<input #nameInput [(ngModel)]="name">
```

The following code returns the `input` element as `elementRef`
```typescript
@ContentChild('nameInput',{static:false}) nameVar;
```
Make use of the read token, to ask ContentChild to return the correct type Instead
```typescript
@ContentChild('nameInput',{static:false, read: NgModel}) nameVarAsNgModel;
@ContentChild('nameInput',{static:false, read: ElementRef}) nameVarAsElementRef;
@ContentChild('nameInput', {static:false, read: ViewContainerRef }) nameVarAsViewContainerRef;
```



## ContentChildren

```typescript
ContentChildren(selector: string | Function | Type<any>, 
                opts: {descendants?:boolean, read?: any; }): any
```
Use the `ContentChildren` decorator to get the list of element references from the projected content.

**ContentChildren always returns all the matching elements as a `QueryList`. You can iterate through the list and access each element.**

