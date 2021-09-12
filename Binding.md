###### tags: `Angular`
# [Binding](https://ithelp.ithome.com.tw/articles/10241010)  

Angular Component主要有三種檔案`.css`(`scss`), `.html`以及`.ts`, 利用Bind(綁定)的方式達成value傳遞的交流

## Interpolation `{{ ... }}`

Bind(pass) class's methods or attributes from `.ts` with(to) `.html`  

```html
{{ component.method }}  
{{ component.attribute }}
```

FOR EXAMPLE  
```typescript
import { TaskState } from "../../enum/task-state.enum";
export class TaskComponent implements OnInit {
  task: Task;

  constructor(){}

  /**
   * Initiate after constructor
   */ 
  ngOnInit(): void {
    this.task = new Task("頁面需要顯示待辦事項主旨");
  }

  // string getStateDesc()
  getStateDesc(): string {
    switch (this.task.state) {
      case TaskState.None:
        return "UnCompelte";
      case TaskState.Doing:
        return "Doing";
      case TaskState.Finish:
        return "Finish";
    }
  }
}
```

Pass the attribute and field in `.ts` to `.html`
```html
<div class="card">
  <div class="content">
    <!-- display 頁面需要顯示待辦事項主旨 -->
    <span>{{ task.subject }}</span>
    
    <!-- return 目前狀態 -->
    <span>{{ getStateDesc() }}</span>
  </div>
</div>
```

## Event Binding  `(event) = "class_method"`
Class's methods are called via `<button ... (event) = "class_method">` in html

```html
(event)="SPECIFIC_METHOD_In_Component.ts"
```
- (event)指定目標事件被觸發後，呼叫等號右邊該Component內指定的Function

FOR EXAMPLE
```typescript
import { TaskState } from "../../enum/task-state.enum";

export class TaskComponent implements OnInit {
  
  // ... 

  task : Task;
  TaskState = TaskState;
  
  /**
   * @Description
   *   To define State of the Task
   *   Will be used as Event Binding
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

## Property and Attribute Binding `[ ... ] = "class_attribute"`

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


## Style Binding and Class Binding 
Style Binding 是Component針對HTML中style屬性的`CSS`樣式進行Binding

```html
<!-- 
style
指的是css 

Css_Property
指的是該`.css`內某個attribute 

attirbute_OR_method_In_Component.ts
利用Component內的attribute或者method 
來達成對CSS內Attributes賦值
-->
[style.Css_Property] = "Attribute_OR_Method_In_typescript"
```

FOR EXAMPLE
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

## [class Binding](https://www.youtube.com/watch?v=Y6OP-lPJxgs)

To bind different classes use
```html
<!-- 
透過 Attribute in the Component  
Assign certain CSS_Attribute 
-->
[class.CSS_Attribute]="Attribute_In_This_Component"

<!--
use Method/Attribute defined in .ts 
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
    <h2 class = "text-success"> Codevolution </h2>    
    
    /** 
      * Pass Attributes in .ts to .html for render
      * css property (indirect change the css property)        
      */
    // this is equal to <h2 class ="text-success"> 
    <h2 [class]="successClass"> Codevolution </h2>
    
    /**
     * Using expression 
     *       if hasError is true 
     *       then render Codevolution with 
     *       css property `text-danger`
     */
    <h2 [class.text-danger] = "hasError"> Codevolution </h2>
    

    /** 
     * Using [ngClass] to 
     *       have multiple css attributes render 
     * The Codevolution will be 
     *       render with text-success and text-special  
     */
    <h2 [ngClass] = "messageClass"> Codevolution </h2>'
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
    public name = "Codevolution";
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
## `@Input` allows Property Binding with different components

`@Input()` :  base component can pass field to child component via `.html`

```html
<!-- Assign value to Child_Variable via Father's Method-->
<TAG [Child_field] = Father.Method> ... </TAG>

<!-- Assign value to CHild_Variable via Father's Attribute --> 
<TAG [Child_field] = Father.Attribute> ... </TAG>
```

**Child component needs to have `@input` annotation to receive value passed by Base Component.**

Base component ,  `app.component.ts` 
```typescript
import { Task } from "./model/task";

export class AppComponent implements OnInit {
  task: Task;
  ngOnInit(): void {
    this.task = new Task("......");
  }
}
```

Child component,  `task.component.ts `
```typescript
/**
 *  to get value from base component
 */
export class TaskComponent implements OnInit {
  /**
    * {@code subject} and {@code state} 
    * will receive the value from Base Component app.component.ts
    */
  @Input() subject: string;
  @Input() state: TaskState;

  constructor() {}

  ngOnInit(): void {}
}
```

via setter method to do binding
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

```html
<app-task [subject]="task.subject" [state]="task.state"></app-task>
```

## `@Output`


In contrast with `@input` we can also pass the value in child component to base component


first base component needs to listen for child component event,
so the child component uses `EventEmitter<Object>` to output the value to Base component 

```typescript
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
  
  /**
   *  receive the value from base
   */
  @Input()  name: string;

  /**
   *  pass the value to base
   */
  @Output() onVoted = new EventEmitter<boolean>();
  voted = false;
 
  vote(agreed: boolean) {
    /**
      * emit (output) the value
      */
    this.onVoted.emit(agreed);
    this.voted = true;
  }
}
```

Base Component

Too receive the value passed by child in html
```    
(child_component_emitter_name) = (method_in_base_component($emitted_event_from_value))
  |                                                    ^ 
  +----------------------------------------------------+
```

```typescript
import { Component }      from '@angular/core';

@Component({
  selector: 'app-vote-taker',
  template:'
    <h2>Should mankind colonize the Universe?</h2>
    <h3>Agree: {{agreed}}, Disagree: {{disagreed}}</h3>
    <app-voter *ngFor="let voter of voters"
    [name]="voter"
    (onVoted)="onVoted($event)">
    </app-voter>
  '
})
export class VoteTakerComponent {
  agreed = 0;
  disagreed = 0;
  
  voters = ['Mr. IQ', 'Ms. Universe', 'Bombasto'];
 
  onVoted(agreed: boolean) {
    agreed ? this.agreed++ : this.disagreed++;
  }
}
```