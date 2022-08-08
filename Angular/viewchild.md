# Access Child Component Data 

- [Access Child Component Data](#access-child-component-data)
  - [`@ViewChild(selector , read, static)`](#viewchildselector--read-static)
    - [Metadata Properties:](#metadata-properties)
  - [`ViewChildren(selector, read)`](#viewchildrenselector-read)
    - [Metadata Properties:](#metadata-properties-1)
    - [Syntax](#syntax)
    - [In Action](#in-action)
      - [Directive](#directive)

## `@ViewChild(selector , read, static)`

### Metadata Properties:

| propName | description |
| -------- | ----------- |
| selector | The directive type or the name used for querying.
| read     | Used to read a different token from the queried elements.
| static   | True to resolve query results before change detection runs, false to resolve after change detection. Defaults to false.

The following selectors are supported.

1. Any class with the `@Component` or `@Directive` decorator
2. A template reference variable as a `string`  
e.g. `query <my-component #cmp></my-component>` with `@ViewChild('cmp')`
3. Any provider defined in the child component tree of the current component  
e.g. `@ViewChild(SomeService) someService: SomeService`
4.  Any provider defined through a string token  
e.g. `@ViewChild('someToken') someTokenVal: any`
5. A `TemplateRef`  
e.g. query `<ng-template> ... </ng-template>` with `@ViewChild(TemplateRef) template;`

The following values are supported by read:
- Any class with the `@Component` or `@Directive` decorator
- Any provider defined on the injector of the component that is matched by the selector of this query
- Any provider defined through a string token  
`e.g. {provide: 'token', useValue: 'val'}`
- `TemplateRef`, `ElementRef`, and `ViewContainerRef`

```typescript 
import {Component, Directive, Input, ViewChild} from '@angular/core';

@Directive({selector: 'pane'})
export class Pane {
  @Input() id!: string;
}

@Component({
    selector: 'example-app',
    template: `
        <pane id="1" *ngIf="shouldShow"></pane>
        <pane id="2" *ngIf="!shouldShow"></pane>

        <button (click)="toggle()">Toggle</button>

        <div>Selected: {{selectedPane}}</div>
    `,
})
export class ViewChildComp {
      
      @ViewChild(Pane)
      set pane(v: Pane) {
        setTimeout(() => {
            this.selectedPane = v.id;
        }, 0);
    }

    selectedPane: string = '';
    shouldShow = true;
    
    toggle() {
        this.shouldShow = !this.shouldShow;
    }
}
```

## `ViewChildren(selector, read)`

**Use to get the QueryList of elements or directives from the view DOM**. 

Any time a child element is added, removed, or moved, the query list will be updated, and the changes observable of the query list will emit a new value.

View queries are set before the ngAfterViewInit callback is called.

### Metadata Properties:

| propName | description |
| -------  | ----------- |
| selector | The directive type or the name used for querying. |
|  read    | Used to read a different token from the queried elements.|

The following selectors are supported.

1. Any class with the `@Component` or `@Directive` decorator
2. A template reference variable as a `string`  
e.g. `query <my-component #cmp></my-component>` with `@ViewChildren('cmp')`
3. Any provider defined in the child component tree of the current component  
e.g. `@ViewChildren(SomeService) someService!: SomeService`
4.  Any provider defined through a string token  
e.g. `@ViewChildren('someToken') someTokenVal!: any`
5. A TemplateRef  
e.g. `query <ng-template></ng-template>` with `@ViewChildren(TemplateRef) template;`
**In addition, multiple string selectors can be separated with a comma.**   
**e.g. `@ViewChildren('cmp1,cmp2')`**

The following values are supported by read:
- Any class with the `@Component` or `@Directive` decorator
- Any provider defined on the injector of the component that is matched by the selector of this query
- Any provider defined through a string token   
e.g. `{provide: 'token', useValue: 'val'}`
- `TemplateRef`, `ElementRef`, and `ViewContainerRef`

### Syntax

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

### In Action 


#### Directive

```typescript
import {AfterViewInit, Component, Directive, Input, QueryList, ViewChildren} from '@angular/core';

@Directive({selector: 'pane'})
export class Pane {
  @Input() id!: string;
}

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
