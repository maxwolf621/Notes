###### tags: `Angular`

# Binding

[TOC]

[Brief TypeScript](/OffOQAeBRraLWjiSHY5tPg)

[Note from](https://ithelp.ithome.com.tw/articles/10241010)

> Angular 應用程式主要是利用元件所組合而成。




To create **A to do list** webpage
On the Terminal

`ng generate module directory`

Genreate a enum type named task-state in the `enum/ ...` directory
```bash=
ng g enum enum/task-state 
#** OR **#
ng g e enum/task-state
```

Generate a class named task in the `model/task` directory
```bash=
ng g class model/task --skipTests
# or 
ng g cl model/task --skipTests
```

To define our Enum task-state.ts
```typescript=
export enum TaskState{
    None,
    Doing,
    Finish,
}
```


To define our task.component
```typescript=
import { TaskState } from "../enum/task-state.enum";

export class Task{
    constructor(
        // string subject
        public subject : string,
        // TaskState state by default
        public state : TaskState = TaskState.None
    ){}
}
```


## Inter-polation

Bind class's methods and attributes in HTML TAG上  

使用方式就是在 HTML TAG加上 
- `{{ component.method }}`  
- `{{ component.attribute }}`

For example  
```typescript=
import { TaskState } from "../../enum/task-state.enum";
export class TaskComponent implements OnInit {
  task: Task;
 
  // Void ngOnInit
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

```htmlembedded=
<div class="card">
  <div class="content">
    <span>{{ task.subject }}</span>
    <span>{{ getStateDesc() }}</span>
  </div>
</div>
```

## Event Binding 
- Class's methods are called via `<button>` in html
> `(event)="method_In_Component.ts"`
> : 在等號左邊指定目標事件，而右邊則指定在事件觸發後要call的Component's Function。

```typescript=
import { TaskState } from "../../enum/task-state.enum";

export class TaskComponent implements OnInit {
  // ... 
  task : Task;
  TaskState = TaskState;
  
  // To define the state
  onSetTaskState(state: TaskState): void {
    this.task.state = state;
  }
}
```

```htmlembedded=
<div class="card">
  <div class="content">
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

Attribute Binding來將_Variable_繫結在HTML _Attribute_

- Attribute
    > Defined By HTML
- Property
    > 文件物件模型 (Document Object Model, DOM) 的節點屬性。
    > 而這兩者(Attribute and Property)並非是互相對應的，且名稱也不一定會相同  
    > 例如, `<td>`標籤內的 `colspan` 屬性 (Attribute) 所對應的 DOM 屬性 (Property) 是 `HTMLTableCellElement.colSpan`，因此在使用的時候還是先查詢一下MDN文件。


Property Binding `[property]="FieldInComponent.ts"` 

Attribute Binding `[attr.name]="ValuePassedToAttribute"`

```htmlembedded=
<div class="content">
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

- Style Binding 是針對 HTML 中 style 屬性的 `CSS` 樣式進行資料繫結
    > `[style.CssProperty] = "attirbute_OR_method_In_Component.ts"`
```typescript=
getStateColor(): string {
    switch (this.task.state) {
          case TaskState.Doing:
            return "green";
          case TaskState.Finish:
            return "blue";
    }
}
```

```htmlembedded
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
1. `[class.CSS_Attribute]="Expression with Attribute_In_Component"`
2. `[class] = "Attribute_IN_Component that corresponds to Css_Attribute"`
3. `[ngclass] = "Array_In_Component"`
```typescript
@Component({
template:'

    <h2 class = "text-success"> Codevolution </h2>    
    <!-- via attribute in comonent to assign a class -->
    <!-- this is euqal to <h2 class ="text-success"> -->
    <h2 [class]="successClass"> Codevolution </h2>
    
    <!-- Using expression -->
    <!-- if hasError is ture then redirect Codevolution via text-danger-->
    <h2 [class.text-danger] = "hasError"> Codevolution </h2>
    
    <!-- Using [ngClass] to have mutiple css attributes redirect -->
    <!-- the Codevolution will be redirected with text-success and text-special --> 
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


## @Input and Property Binding

`@Input()` 裝飾器用來定義元件屬性是可從*父*元件接收值，而在*父*元件則可以利用屬性繫結 (Property Binding) 來傳入資料。
```htmlembedded
<!-- Assign value to Child_Variable via Father's Method-->
<TAG [Child_Variable] = Father.Method> ... </TAG>

<!-- Assign value to CHild_Variable via Father's Attribute --> 
<TAG [Child_Variable] = Father.Attribute> ... </TAG>
```

in app.component.ts (Father)
```typescript=
import { Task } from "./model/task";

export class AppComponent implements OnInit {
  task: Task;
  ngOnInit(): void {
    this.task = new Task("頁面需要顯示待辦事項主旨");
  }
}
```

task.component.ts (Child)
```typescript=
// ... 
export class TaskComponent implements OnInit {
  // variable subject and state 
  //     will receive the value from app.component.ts
  @Input() subject: string;
  @Input() state: TaskState;

  constructor() {}

  ngOnInit(): void {}
}
```

Using Property Binding to binding two classes(father and son) together
```htmlembedded=
<app-task [subject]="task.subject" [state]="task.state"></app-task>
```
