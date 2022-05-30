###### tags: `Angular`
# Bind 
- [Code Example](https://ithelp.ithome.com.tw/articles/10241010)  

- [Bind](#bind)
  * [Interpolation](#interpolation)
  * [Event Binding](#event-binding)
  * [Property and Attribute Binding](#property-and-attribute-binding)
  * [Style Binding](#style-binding)
  * [Class Binding](#class-binding)
  * [Pass the value to another Component](#pass-the-value-to-another-component)
    + [`@Input`](#--input-)
    + [`@Output`](#--output-)

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
- For Example ::  
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

## Event Binding  
Class's methods are called via `<button ... (event) = "classMethod">` in html

當Button被Pressed後會觸發`(event)`，會呼叫`.ts`內指定的Method
```html
(event)="SPECIFIC_METHOD_In_Component.ts"
```

- For example even binds with `onSetTaskState` 
```typescript
import { TaskState } from "../../enum/task-state.enum";

export class TaskComponent implements OnInit {
  
  // ... 

  task : Task;
  TaskState = TaskState;
  
  /**
   * @Description
   *   To define State of the Task
   */
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
  
  <!-- Add Buttion Attirbute To implement event binding  -->
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

## Property and Attribute Binding 

*Component's Attribute* is Binding to HTML's *Attribute*

```html
<!-- 
(DOM) Property Binding 
-->
[property]="Field_In_Component"

<!--
(HTML) Attribute Binding 
-->
[attr.name]="Value_Passed_To_Attribute"
```
 
- Attribute Binding
  > [ ATTRIBUTE ] : the ATTRIBUTE is Defined By HTML

- Property Binding
  > [PROPERTY ] : the PROPERTY is Defined By 文件物件模型 (Document Object Model, DOM) 

- Attribute and Property並非是互相對應的，且名稱也不一定會相同  
  > 例如, `<td>`標籤內的 `colspan` 屬性 (Attribute) 所對應的 DOM 屬性 (Property) 是 `HTMLTableCellElement.colSpan`，因此在使用的時候還是先查詢一下MDN文件。


```html
<div class="content">
    <!-- Property Binding -->
    <button type="button"
    [disabled]="task.state === TaskState.Finish">
    A_Button_To_Start_Or_Close
    </button>
    
    <!-- Attribute Binding with `? :` -->
    <button type="button" 
    [attr.disabled]="task.state === TaskState.Finish ? 'disabled' : null">
    Edit
    </button>
</div>
```

## Style Binding 
Style Binding 是Component針對HTML中style屬性的`CSS`樣式進行Binding
```html
[style.Css_Property] = "Attribute_OR_Method_In_typescript"
```
- `style` : style屬性的css 
- `Css_Property`  該component `.css`內某個attribute 
- `attribute_OR_method_In_Component.ts` : 利用Component內的attribute或者method來達成對CSS內Attributes賦值

- FOR EXAMPLE :: 改變`[style.color]`利用`.ts`內的method
```typescript
getStateColor(): string {
    switch (this.task.state) {
          case TaskState.Doing:
            return "green";
          case TaskState.Finish:
            return "blue";
    }
}
```

```html
<div class="content">  
  <!-- To assign .color via getStateColor -->
  <span [style.color]="getStateColor()">
  {{ getStateDesc() }}
  </span>
</div>
```
## Class Binding
- [class Binding](https://www.youtube.com/watch?v=Y6OP-lPJxgs)

To bind different classes

```html
<!-- 
透過 Attribute in the Component  
Assign certain CSS_Attribute 
-->
[class.CSS_Attribute]="Attribute_In_This_Component"

<!--
use Method/Attribute defined in .ts file 
which can assign a CSS Attribute to .css

It's same as <tag class="css_attribute"> ... </tag>
--> 
[class] = "Method_OR_Attribute_IN_Component"

<!-- 
Assign multiple CSS attributes from Component's Array
-->
[ngclass] = "Array_Containing_Css_Attribute_IN_Component"
```

```typescript
@Component({
template:'
    <h2 class = "text-success"> Code Volution </h2>    
    
    /** 
      * Pass Attributes in .ts to .html 
      * for render css property 
      * (indirect change the css property)        
      */
    // this is equal to <h2 class ="text-success"> 
    <h2 [class]="successClass"> Code Volution </h2>
    
    /**
     * Using expression 
     *       if hasError is true 
     *       then render Code Volution with 
     *       css property `text-danger`
     */
    <h2 [class.text-danger] = "hasError"> Code Volution </h2>
    

    /** 
     * Using [ngClass] to 
     *       have multiple css attributes render 
     * The Code Volution will be 
     *       render with text-success and text-special  
     */
    <h2 [ngClass] = "messageClass"> Code Volution </h2>'
    ,
styles:['
    .text-success{
        color:green
    }
    .text-danger{
        color:red
    }
    .text-special{
        font-style:italic
    }
']
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

More Specific for `[ngClass]`
```html
<ul>
  <li *ngFor="let hero of heroes" 
      (click)="test(hero.fav = !hero.fav)">
    <div [ngClass]="hero.fav ? 'primary' : 'secondary'" 
         (click)="test(hero.id)">{{ hero.name }}</div>
  </li>
</ul>
```

```scss
.primary {
  color: red;
}
.secondary {
  color: green;
}
```


## Pass the value to another Component

### `@Input` 

It allows Property Binding with different components   
Base component can pass value to child component via `.html`

```html
<!-- Assign value to Child_Variable via Father's Method-->
<ChildComponent [Child_field] = BaseComponent.Method> ... </ChildComponent>

<!-- Assign value to CHild_Variable via Father's Attribute --> 
<ChildComponent [Child_field] = BaseComponent.Attribute> ... </ChildComponent>
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
- the attribute that will be assigned by base component shall have `@input()` annotation
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


We also can use setter to assign the value to variable
```typescript
// ... 
export class TaskComponent implements OnInit {

  _subject!: string;
  _state!: TaskState;
  
  @input()
  set subject(subject : string ){
    ....
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
@output() x = EventEmitter<T>();

filed(y : T){
  this.x = emit(y);
}
```

- Second,base component's (`.html`) to receive the value emitted by child ... 
```html    
<child>  
  (dield With @output() anootation in child) = "Method_of_Base($event)"
</child>
```
- `($event)` will be assigned the valued emitted from child

- For example :: 
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
  @Input()  name: string;

  // OnVoted emit the boolean type value to base component
  // Emitter 
  @Output() onVoted = new EventEmitter<boolean>();
  
  voted = false;
  
  // Emit the value to base
  vote(agreed: boolean) {

    this.onVoted.emit(agreed);
    this.voted = true;
  }
}

// Base
import { Component } from '@angular/core';

@Component({
  selector: 'app-vote-taker',
  template:'               
    <h2>Should mankind colonize the Universe?</h2>
    <h3>Agree: {{agreed}}, Disagree: {{disagreed}}</h3>
    <app-voter *ngFor="let voter of voters"
              [name]="voter"
              <!--@output annotation child varaible = father method($event)-->
              (onVoted)="onVoted($event)">
    </app-voter>'                 
})
export class VoteTakerComponent {
  agreed = 0;
  disagreed = 0;
  
  voters = ['Mr. IQ', 'Ms. Universe', 'Ms. Toshiro'];
 
  onVoted(agreed: boolean) {
    agreed ? this.agreed++ : this.disagreed++;
  }
}
```
