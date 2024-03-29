###### tags: `Angular`
# Bind 

- [Bind](#bind)
  - [Reference](#reference)
  - [Interpolation](#interpolation)
  - [Event Binding `(event) = expr`](#event-binding-event--expr)
    - [`$event` Payload](#event-payload)
    - [Template reference variable](#template-reference-variable)
    - [Key event filtering (with key.enter)](#key-event-filtering-with-keyenter)
  - [Property and Attribute Binding](#property-and-attribute-binding)
    - [Property binding](#property-binding)
  - [Class and Style binding](#class-and-style-binding)
  - [Class Binding `[Class]` and `[Class.classNameInCss]`](#class-binding-class-and-classclassnameincss)
  - [Style Binding `[style.xxxx]`](#style-binding-stylexxxx)
    - [Binding to a single CSS style](#binding-to-a-single-css-style)
    - [Binding to multiple styles](#binding-to-multiple-styles)
  - [Injecting `@attribute` values](#injecting-attribute-values)
  - [Component pass value to Child component](#component-pass-value-to-child-component)
    - [`@Input`](#input)
    - [`@Output`](#output)

## Reference

Update `@input` property for both child and parent component
- [[StackOverflow] how to update a component without refreshing full page](https://stackoverflow.com/questions/46047854/how-to-update-a-component-without-refreshing-full-page-angular)    
- [[StackOverflow] detect changes in an array input property](https://stackoverflow.com/questions/42962394/angular-2-how-to-detect-changes-in-an-array-input-property)    
- [Code Example](https://ithelp.ithome.com.tw/articles/10241010)  
- [angular.io](https://angular.io/guide/attribute-binding)

---

Angular Component主要有三種檔案
1. `.css`(`scss`) 
2. `.html`
3. `.ts`
透過利用Bind(綁定)的方式讓值在這三種文件中傳遞交流

## Interpolation 

Bind class's methods or attributes from`.ts`with`.html`  

```typescript
{{ component.method }}  
{{ component.attribute }}
```

```typescript
import { TaskState } from "../../enum/task-state.enum";
export class TaskComponent implements OnInit {
  task: Task;

  constructor(){}

  // after constructor
  ngOnInit(): void {
    this.task = new Task("頁面需要顯示待辦事項主旨");
  }

  getStateDesc(): string {
    switch (this.task.state) {
      case TaskState.None:
        return "UnComplete";
      case TaskState.Doing:
        return "Doing";
      case TaskState.Finish:
        return "Finish";
    }
  }
}
```

Bind the attribute and field in `.ts` with `.html`
```html
<div class="card">
  <div class="content">
    <!-- display 頁面需要顯示待辦事項主旨 -->
    <span>{{ task.subject }}</span>
    
    <!-- call getStateDec() from .ts -->
    <span>{{ getStateDesc() }}</span>
  </div>
</div>
```

## Event Binding `(event) = expr`

- [Event Binding](https://www.tektutorialshub.com/angular/event-binding-in-angular/)

```typescript
import { TaskState } from "../../enum/task-state.enum";

export class TaskComponent implements OnInit {
  // ... 
  task : Task;
  TaskState = TaskState;
  
  // define State of the Task
  onSetTaskState(state: TaskState): void {
    this.task.state = state;
  }
}
```

```html
<div class="card">
  <div class="content">
    <!-- Interpolation -->
    <span>{{ task.subject }}</span>
    <span>{{ getStateDesc() }}</span>
  </div>
  
  <!-- Add Button Attribute To implement event binding  -->
  <div class="button">
    <span>
      <button type="button" (click)="onSetTaskState(TaskState.None)">
        Unfinished
      </button>
      <button type="button" (click)="onSetTaskState(TaskState.Doing)">
        On Processing
      </button>
      <button type="button" (click)="onSetTaskState(TaskState.Finish)">
        Finished
      </button>
    </span>
  </div>
</div>
```

### `$event` Payload

The properties of a `$event` object vary depending on the type of DOM event   
[source code](https://stackblitz.com/edit/event-binding-in-angular-ex-3?file=src%2Fapp%2Fapp.component.ts)   

```html
<input (input)="handleInput($event)">
<!--    ^                     ^
        '---------------------'
-->

<p>You have entered {{value}}</p>
```

```typescript
// .ts file
value=""

handleInput(event) {
  this.value = (event.target as HTMLInputElement).value;
}
```

### Template reference variable

```html
<input #el (input)="handleInput1(el)">
<p>You have entered {{val}}</p>
```
```typescript
val="";
handleInput1(element) {
  this.val=element.value;
}
```
### Key event filtering (with key.enter)

using `keyup`/`keydown` events to listen for keystrokes.

```typescript
// value1 will be updated of any value is passed
<input (keyup)="value1= $any($event.target).value" />
<p>You entered {{value1}}</p>

// pass enter => value2 will be displayed
<input (keyup.enter)="value2=$any($event.target).value">
<p>You entered {{value2}}</p>

// pass enter => value2 will be displayed
<input (keyup.enter)="value3=$any($event.target).value" (keyup.escape)="$any($event.target).value='';value3=''">
<p>You entered {{value3}}</p>

<input (keyup.control.shift.enter)="value4=$any($event.target).value">
<p>You entered {{value4}}</p>
```

## Property and Attribute Binding 

[DOM properties and HTML attributes](domPropHTMLattr.md) 
- Attribute Binding : the ATTRIBUTE is Defined By HTML  
- Property Binding : the PROPERTY is Defined By Document Object Model, DOM 

```html 
<!--
(HTML) Attribute Binding 
-->
<tag [attr.attribute-you-are-targeting]="expression"></tag>

<tr> 
  <td [attr.colspan]="1 + 1"> 2 </td>
</tr>
```
Sometimes there are differences between the name of property and an attribute. `colspan` is an attribute of `<td>`, while `colSpan` with a capital `S` is a property.  

- When using attribute binding, use colspan with a lowercase `s`.    
- When the expression resolves to `null` or `undefined`, Angular removes the attribute altogether.   

```html
<div class="content">
    <!-- Property Binding -->
    <button type="button"
    [disabled]="task.state === TaskState.Finish">
    A_Button_To_Start_Or_Close
    </button>
    
    <!-- Attribute Binding -->
    <button type="button" 
    [attr.disabled]="task.state === TaskState.Finish ? 'disabled' : null">
    Edit
    </button>
</div>
```

### Property binding

**To bind value in `.ts` file to an element's property, enclose it in square brackets, `[]`, which identifies the property as a target property.**

In most cases, the target name is the name of a property, even when it appears to be the name of an attribute.

```html
<!--
  app.component.html 
-->
<img alt="item" [src]="itemImageUrl">
```
- `src` is the name of the `<img>` element property.   

```typescript
// app.component.ts
itemImageUrl = '../assets/phone.png';
```


**Without the brackets `[]`, Angular treats the right-hand side as a string literal and sets the property to that static value. For example**
```html
<app-item-detail childItem="parentItem"></app-item-detail>
```
- Omitting the brackets renders the string parentItem, not the value of parentItem.

To use property binding using colSpan, type the following:
```html
<!-- Notice the colSpan property is camel case -->
<tr><td [colSpan]="1 + 1">Three-Four</td></tr>
```

To disable a button when the component says that it `isUnchanged`, type the following:
```html
<!-- Bind button disabled state to `isUnchanged` property -->
<button type="button" [disabled]="isUnchanged">Disabled Button</button>
```
```typescript 
isUnchanged = true;
```
- Angular disables the button if `isUnchanged` is `true`


To set a property of a directive, type the following:
```html
<p [ngClass]="classes">
  [ngClass] binding to the classes property making this blue
</p>
```

To set the model property of a custom component for parent and child components to communicate with each other, type the following:
```html
<app-item-detail [childItem]="parentItem"></app-item-detail>
```

## Class and Style binding

```html
<h3>Dynamic vs static</h3>

<!-- If `classExpression` has a value for the `special` class, this value overrides the `class="special"` below -->
<div class="special" [class]="classExpression">Some text.</div>

<!-- If `styleExpression` has a value for the `border` property, this value overrides the `style="border: dotted darkblue 3px"` below -->
<div style="border: dotted darkblue 3px" [style]="styleExpression">Some text.</div>
```

## Class Binding `[Class]` and `[Class.classNameInCss]`

```html
<hostElement [class]="'className'"></hostElement>
<hostElement [Class.className]="booleanValue"></hostElement>
```


```css
.bgYellow {
  background-color: yellow;
}

.colorBlue {
  color: blue;
}

.beYB {
  color: yellow;
  background-color: blue;
}
```
```html
<h1 class="bgYellow colorBlue" [class]="'beYB'" >
    Welcome to {{title}}!
</h1>
```

---

```css
.bgYellow {
  background-color: yellow;
}

.colorBlue {
  color: blue;
}
```
```html
<!-- boolean Value controls .bgYellow to render or not -->
<h1 class="bgYellow colorBlue" [class.bgYellow]="booleanValue" >
    Welcome to {{title}}!
</h1>
```

---

```css
.bgYellow {
    background-color: yellow;
}
.colorBlue {
    color: blue;
}
```
```html
<!-- 
  getAttributesFromComponent() 
  returns {'bgYellow': true,'colorBlue':false} -->
<h1 [ngClass]="getAttributesFromComponent()" >
  Welcome to {{title}}!
</h1>

<!-- same as -->
<h1 [class]="{'bgYellow': true,'colorBlue':false}" >
  Welcome to {{title}}!
</h1>
```

---

```typescript
@Component({
template:'
  <h2 class = "text-success"> Code Volution </h2>    
  <h2 [class]="successClass"> Code Volution </h2>
  
  <h2 [class.text-danger] = "hasError"> Code Volution </h2>
  
  <h2 [ngClass] = "messageClass"> Code Volution </h2>',
styles:['
    .text-success{
        color:green
    }
    .text-danger{
        color:red
    }
    .text-special{
        font-style:italic
    }']
})
export class test implements OnInit{

    public name = "Code Volution";
   
    public successClass = "text-success";
    
    public hasError = true ;
    
    public isSpecial = true;

    public MessageClass ={
        "text-success" = hasError;
        "text-danger"  = !hasError;
        "text-special" = isSpecial;
    }
}
```

## Style Binding `[style.xxxx]`
### Binding to a single CSS style

To create a single style binding, use the prefix style followed by a dot and the name of the CSS style.
```html
<div [style.width]="width"> ... </div>
<!-- binding with units -->
<div [style.width.px]="width">...</div>
```

Angular sets the property to the value of the bound expression, which is usually a string. 

Optionally, you can add a unit extension like `em `or `%`  which requires a number type.
```html
<!-- dash case -->
<nav [style.background-color]="expression"></nav>
<!-- camel case-->
<nav [style.backgroundColor]="expression"></nav>
```
### Binding to multiple styles

```html
<nav [style]="stringListOfStyle">	
  stringListOfStyle could be "width: 100px; height: 100px"
</nav>

<nav [style]="anObjectWithStyleNames">
    An object with style names as the keys and style values as the values, 
    such as 
    {width: '100px', height: '100px', backgroundColor: 'cornflowerblue'}
</nav>		
```

```typescript
@Component({
  selector: 'app-nav-bar',
  template: `
  <nav [style]='navStyle'>
    <a [style.text-decoration]="activeLinkStyle">Home Page</a>
    <a [style.text-decoration]="linkStyle">Login</a>
  </nav>`
})
export class NavBarComponent {
  navStyle = 'font-size: 1.2rem; color: cornflowerblue;';
  linkStyle = 'underline';
  activeLinkStyle = 'overline';
  /* . . . */
}
```
## Injecting `@attribute` values

**Use `@Attribute()` when you want to inject the value of an HTML attribute to a `@component` or `@directive` constructor.**

There are cases where you need to differentiate the behavior of a Component or Directive based on a shared **STATIC VALUE** set on the host element as an HTML attribute.   


**The `@Attribute` parameter decorator is great for passing the value of an HTML attribute to `@component` or `@directive` component's constructor using dependency injection.**   
```typescript
import { Component, Attribute } from '@angular/core';

export type ButtonType = 'primary' | 'secondary';

@Component({
  selector: 'app-button',
  template: `
    <button [ngClass]="type">
      <ng-content></ng-content>
    </button>
  `
})
export class ButtonComponent {
  constructor(@Attribute('type') public type: ButtonType = 'primary') { }

}
```
- The injected value captures the value of the specified HTML attribute at that moment. **Future updates to the HTML attribute value are not reflected in the injected value.** 

```html
<app-button type="secondary">Action Button</app-button>
```


---

## Component pass value to Child component

### `@Input` 

Base component can pass value to child component via `.html`

**Use `@Input()` when you want to keep track of the attribute value and update the associated property.** 

```typescript 
@Input() field : dataType;
```
- the above means this field's value is assigned by father component


```html
<!-- Assign value to Child_Variable via Father's Method-->
<childSelector [Child_field] = BaseComponent.Method> ... </childSelector>

<!-- Assign value to CHild_Variable via Father's Attribute --> 
<childSelector [Child_field] = BaseComponent.Attribute> ... </childSelector>
```

Base component    
```typescript
import { Task } from "./model/task";

export class AppComponent implements OnInit {
  task: Task;
  
  ngOnInit(): void {
    this.task = new Task("......");
  }
}
```

Child component
```typescript
export class TaskComponent implements OnInit {
  
  /**
    * {@code subject} and {@code state} 
    * will receive the value 
    * from Base Component app.component.ts
    */
  @Input() subject: string;
  @Input() state: TaskState;

  constructor() {}

  ngOnInit(): void {}
}
```

Via Setter
```typescript
export class TaskComponent implements OnInit {

  _subject!: string;
  _state!: TaskState;
  
  @input()
  set subject(subject : string ){
    /**
     * setter
     */
  }
  
  //...
}
```

Base Component's `.html`
```html
<app-task [subject]="task.subject" 
          [state]="task.state">
</app-task>
```


### `@Output`

In contrast with `@input` we can also pass the value from child component to Base component

Firstly the child component shall declare a `EventEmitter<T>()` type variable with `@output()` annotation to emit the value to Base component
```typescript
@output() emitter = EventEmitter<T>();

filed(y : T){
  this.emitter.emit(y);
}
```

Base component's (`.html`) to receive the value emitted by child ... 
```html    
<childSelector>  
  Field_With_@output()_annotation_in_child) = "Method_of_Base($event)"
  <!-- '++++++++++++++++++++++++++++++++++++++++++++++++++++++++^     
  -->
</childSelector>
```
- `($event)` is the value from the emitter

```typescript
// Child 
import { Component, EventEmitter, Input, Output } from '@angular/core'; 

@Component({
  selector: 'app-voter',
  template: '
    <h4>{{name}}</h4>
    <button (click)="vote(true)"  [disabled]="voted">Agree</button>
    <button (click)="vote(false)" [disabled]="voted">Disagree</button>
    '
})
export class VoterComponent {
  
  //receive the value from the Base 
  @Input() name: string;

  // OnVoted emit the boolean type value to base component
  // Emitter 
  @Output() voteEmitter = new EventEmitter<boolean>();
  
  voted = false;
  
  // Emit the value to base
  vote(agreed: boolean) {

    this.voteEmitter.emit(agreed);
    this.voted = true;
  }
}

// Base
import { Component } from '@angular/core';

@Component({
  selector: 'app-vote-taker',
  template:'               
    <h2>Should mankind colonize the Universe?</h2>
    <h3>Agree: {{agreedCount}}, Disagree: {{disagreedCount}}</h3>
    <app-voter *ngFor="let voter of voters"
              [name]="voter"
              <!--@output annotation child variable = father method($event)-->
              (voteEmitter)="onVoted($event)">
    </app-voter>'                 
})
export class VoteTakerComponent {
  agreedCount = 0;
  disagreedCount = 0;
  
  voters = ['Mr. IQ', 'Ms. Universe', 'Ms. Toshiro'];
 
  onVoted(agreed: boolean) {
    agreed ? this.agreedCount++ : this.disagreedCount++;
  }
}
```
