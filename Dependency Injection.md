###### tags: `Angular`
# Dependency Injection, DI
[TOC]


## `@Injection`

[Note From](https://ithelp.ithome.com.tw/articles/10249405)

via `@Injection` in service component to tell Angular register this component to AppMoudle 

#### Syntax
```typescript=
@Injectable({
    //...
})
export class MeIsService {}
```




Before Angular 6 only **`@NgModule`'s provider attribute** can define our DI
```typescript=
@NgModule({
  imports: [CommonModule, HttpClientModule],
  //...
  providers: [MeIsService],
  //...
})
export class TaskModule {}
```

:::danger  
Dependency Injection for a Service Component is Singleton(shared by Components)
:::


## An Example

Create A Count that shared by Components who will use it
```typescript=
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class CounterService {
  count = 0;
  constructor() {}
  
  add(): void {
    this.count++;
  }
}
```

In AppComponent.ts
```typescript=
export class AppComponent implements OnInit {
  constructor(public counterService: CounterService) {}
}
```

In AppComponent.html
```htmlembedded=
<div>
  <strong>AppComponent</strong>
  <button type="button" (click)="counterService.add()">
    Add {{ counterService.count }}
  </button>
</div>
```

## `@Component`

> Angular 會依注入器為範圍來建立依賴實體，所以如果需要讓 `AppComponent` 與 `TaskListComponent` 各自的Count不同，可以在 `TaskListComponent` 中加入提供者 (providers) 的設定。

```typescript=
@Component({
  //... 
  providers: [CounterService],
  //..
})
export class TaskListComponent implements OnInit {
  tasks$ : Observable<Task[]>;

  constructor(
    private taskService: TaskRemoteService,
    public counterService: CounterService
  ) {}

  ngOnInit(): void {
    this.tasks$ = this.taskService.getData();
  }
}

```

```typescript=
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";

import { TaskState } from "../../enum/task-state.enum";
import { Task } from "../../model/task";

@Injectable({
  providedIn: "root",
})
export class TaskLocalService {
  private _tasks: Task[];

  // return different tasks
  getData(): Observable<Task[]> {
    console.log("from TaskLocalService");
    return of(this._tasks);
  }
}
@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    TaskModule,
    UiModule,
  ],
  declarations: [AppComponent],
  providers: [{ 
      provide: TaskRemoteService, 
      useClass: TaskLocalService 
  }],
  bootstrap: [AppComponent],
})
export class AppModule {}
```