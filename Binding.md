###### tags: `Angular`
# [Binding](https://ithelp.ithome.com.tw/articles/10241010)  

由於Angular Application主要是利用元件所組合而成  
我們可以利用Bind將來操控各個Component內的Attirbutes  

## Interpolation `{{ ... }}`

Bind class's methods and attributes in HTML TAG上  

使用方式就是在 HTML TAG加上 
```html
{{ component.method }}  
{{ component.attribute }}
```

FOR EXAMPLE  
```typescript
import { TaskState } from "../../enum/task-state.enum";
export class TaskComponent implements OnInit {
  task: Task;
 
  // ngOnInit() to instantiate object 
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

## Event Binding 
- Class's methods are called via `<button>` in html

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
In this component HTML file
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

- Attribute Binding
  > Defined By HTML
- Property Binding
  > Defined By 文件物件模型 (Document Object Model, DOM) 
- Attribute and Property並非是互相對應的，且名稱也不一定會相同  
  > 例如, `<td>`標籤內的 `colspan` 屬性 (Attribute) 所對應的 DOM 屬性 (Property) 是 `HTMLTableCellElement.colSpan`，因此在使用的時候還是先查詢一下MDN文件。

```html
<!-- 
(DOM) Property Binding 
-->
[property]="FieldInComponent"

<!--
(HTML) Attribute Binding 
-->
[attr.name]="ValuePassedToAttribute"
```
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
<!-- style指的是css 
     CssProperty指的是該css file內某個attribute 
-->

<!-- 
attirbute_OR_method_In_Component.ts表示
利用Component內的attribute或者method 
來control CSS內指定的Attribute之值
-->
[style.CssProperty] = "attirbute_OR_method_In_Component.ts"`
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

## class Binding
[Source](https://www.youtube.com/watch?v=Y6OP-lPJxgs)

To bind different classes use
```html
<!-- 
Assign指定的CSS_Attribute透過 Attribute in the Component
-->
[class.CSS_Attribute]="Attribute_In_This_Component"

<!--
use Method/Attribute defined in Component 
    which can assign a CSS Attribute
    It's same as 
    <tag class="css_attribute"> ... </tag>
--> 
[class] = "Method_OR_Attribute_IN_Component"

<!-- 
assign multiple CSS attributes from Component's Array
-->
[ngclass] = "Array_Containing_Css_Attriute_IN_Component"
```

```typescript
@Component({
template:'
    <h2 class = "text-success"> Codevolution </h2>    
    
    /** 
      * via attribute in component to assign a class
      *   this is euqal to <h2 class ="text-success"> 
      */
    <h2 [class]="successClass"> Codevolution </h2>
    
    /**
     * Using expression 
     *       if hasError is true 
     *       then redirect Codevolution via text-danger
     */
    <h2 [class.text-danger] = "hasError"> Codevolution </h2>
    
    /** 
     * Using [ngClass] to 
     *       have mutiple css attributes redirect 
     * The Codevolution will be 
     *       redirected with text-success and text-special  
     */
    <h2 [ngClass] = "messageClass"> Codevolution </h2>
',
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


## `@Input` and Property Binding

`@Input()` 裝飾器用來定義*子*元件屬性是可從*父*元件接收值，而在*父*元件則可以利用屬性繫結 (`Property Binding`) 來傳入資料給*子*元件內的field    

```html
<!-- Assign value to Child_Variable via Father's Method-->
<TAG [Child_Variable] = Father.Method> ... </TAG>

<!-- Assign value to CHild_Variable via Father's Attribute --> 
<TAG [Child_Variable] = Father.Attribute> ... </TAG>
```

父 `app.component.ts` 
```typescript
import { Task } from "./model/task";

export class AppComponent implements OnInit {
  task: Task;
  ngOnInit(): void {
    this.task = new Task("頁面需要顯示待辦事項主旨");
  }
}
```

子`task.component.ts `
```typescript
// ... 

export class TaskComponent implements OnInit {
  /**
    * {@code subject} and {@code state} 
    * will receive the value from 父 app.component.ts
    */
  @Input() subject: string;
  @Input() state: TaskState;

  constructor() {}

  ngOnInit(): void {}
}
```

Via Property Binding to binding two classes(父 and 子)together
```html
<app-task [subject]="task.subject" [state]="task.state"></app-task>
```
