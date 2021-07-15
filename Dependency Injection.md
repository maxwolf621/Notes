###### tags: `Angular`
# Dependency Injection, DI
[TOC]


## `@Injection`

[Note From](https://ithelp.ithome.com.tw/articles/10249405)


#### Syntax
via `@Injection` in service component to tell Angular register this component to `AppMoudle` 
```typescript
@Injectable({
    providedIn: "root",
})
export class XXXXService {}
```

Before Angular 6 only **`@NgModule`'s provider attribute** can define the DI
```typescript
@NgModule({
  imports: [CommonModule, HttpClientModule],
  //...
  providers: [MeIsService],
  //...
})
export class TaskModule {}
```

:::danger  
Dependency Injection for a Service Component is **Singleton**(shared by Components)
:::


## Example of DI

Create `CounterService.ts`
```typescript
/**
 * Create A Count Service that shared by 
 *   Components who will use it
 */
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class CounterService {
  count = 0;
  constructor() {}
  
  add(): void {
    this.count++;
  }
}
```

In `AppComponent.ts`
```typescript
export class AppComponent implements OnInit {
  constructor(public counterService: CounterService) {}
}
```

In `AppComponent.html`
```htmlembedded
<div>
  <strong>AppComponent</strong>
  <button type="button" (click)="counterService.add()">
    Add {{ counterService.count }}
  </button>
</div>
```

## DI is only used by specific Component

在 Angular 框架中，有兩個注入器層次結構，第一種稱為 `ModuleInjector`，可以利用 `@Injectable` 裝飾器或是定義 `@NgModule()` 的 `providers` 陣列進行配置；另一種是 Angular 為每一個 `DOM` 元素隱含建立的 `ElementInjector`，可以利用 `@Component` 裝飾器中的 `providers` 屬性來配置服務。再加上，Angular 會依注入器為範圍來建立依賴實體，所以如果需要讓 `AppComponent` 與 `TaskListComponent` 各自的Count不同，可以在 `TaskListComponent` 中加入providers的設定。  

```typescript
@Component({
  selector: "app-task-list",
  templateUrl: "./task-list.component.html",
  styleUrls: ["./task-list.component.css"],
  /**
   * TaskListComponent has it own
   *   CounterService
   */
  providers: [CounterService],
})
export class TaskListComponent implements OnInit {
  tasks$: Observable<Task[]>;

  constructor(
    private taskService: TaskRemoteService,
    public counterService: CounterService
  ) {}

  ngOnInit(): void {
    this.tasks$ = this.taskService.getData();
  }
}
```

```typescript
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
```

```typescript
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
      /**
       * register
       */
      provide: TaskRemoteService, 
      useClass: TaskLocalService 
  }],
  bootstrap: [AppComponent],
})
export class AppModule {}
```