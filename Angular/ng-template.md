# ngTemplate (TemplateRef&ViewContainerRef) & `*ngTemplateOutlet`

- [Codes](https://ngtemplate-ngcontainer-directive-vdpqhj.stackblitz.io)

**A template is an HTML snippet.**   
The template does not render itself on DOM.  

- [ngTemplate (TemplateRef&ViewContainerRef) & `*ngTemplateOutlet`](#ngtemplate-templaterefviewcontainerref--ngtemplateoutlet)
  - [Reference](#reference)
  - [Syntax](#syntax)
  - [Display ngTemplate Element in View](#display-ngtemplate-element-in-view)
    - [TemplateRef](#templateref)
    - [ViewChild](#viewchild)
    - [ViewContainerRef](#viewcontainerref)
    - [ComponentFactoryResolver](#componentfactoryresolver)
  - [ng-template with `[ngIf]`, `[ngIfThen]` and `[ngIfElse]`](#ng-template-with-ngif-ngifthen-and-ngifelse)
  - [ng-template with `[ngForOf]` and `[ngForTrackBy]`](#ng-template-with-ngforof-and-ngfortrackby)
  - [ng-template with `[ngSwitch]`, `[ngSwitchCase]` and `ngSwitchDefault`](#ng-template-with-ngswitch-ngswitchcase-and-ngswitchdefault)
  - [`*ngTemplateOutlet`](#ngtemplateoutlet)
    - [Syntax](#syntax-1)
  - [Outlet傳入多個參數到Template](#outlet傳入多個參數到template)

## Reference
[How to use ng-template & TemplateRef in Angular](https://www.tektutorialshub.com/angular/ng-template-in-angular/)

## Syntax

ngTemplate is controlled by Directive
```html
<ngTemplate ComponentDirective>  ... </ngTemplate>
```

ngTemplate is controlled by TemplateName
```html
<ngTemplate #TemplateName> ... </ngTemplate>
```

Pass parameter to this ngTemplate
```html
<ngTemplate #templateRefName
            let-templateParameter1 = "yyy" , 
            let-templateParameter1 = "xxx"> 
      <div>{{ templateParameter1 | json }}</div>
</ngTemplate>
```
```typescript
context = {
  $implicit : ... ,
  xxx : x : ...
  yyy : { a : ... , b : ....},
}
```

## Display ngTemplate Element in View

1. Using the `ngTemplateOutlet` directive.
2. Using the `TemplateRef` & `ViewContainerRef`
   - The `TemplateRef` holds the reference template defined by ng-template.
   - `ViewContainerRef`, when injected to via DI holds the reference to the host element, that hosts the component (or directive).

### TemplateRef

`TemplateRef` is a class and the way to reference the Template in the component or directive class. 

Using the `TemplateRef` we can manipulate the Template in component.
```html
<ng-template #sayHelloTemplate>
  <p> Say Hello</p>
</ng-template>
```

### ViewChild

Get the element in Template and use in the Component

`@ViewChild('templateRefName') field : TemplateRef<Type>;`
```typescript
@ViewChild('sayHelloTemplate')
sayHelloTemplate:TemplateRef; // or TemplateRef<any>
```

### ViewContainerRef

**The `ViewContainerRef` is also similar to `TemplateRef`. Both hold the reference to part of the view.**   

It helps us manipulate the Template from Component   
Once, we have `ViewContainerRef`, we can use the `createEmbeddedView` method to add the template to the component.   
```typescript
@ViewChild('sayHelloTemplate', { read: TemplateRef })
sayHelloTemplate:TemplateRef<any>;

constructor(private viewContainerRef : ViewContainerRef) {}

ngAfterViewInit() {
  this.viewContainerRef.createEmbeddedView(this.sayHelloTemplate);
}
```

or 

```typescript
import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appDynamicComponentHost]'
})
export class DynamicComponentHostDirective {
  constructor(public viewContainerRef: ViewContainerRef) { }
}

// Get instance of appDynamicComponentHost template 
@ViewChild(DynamicComponentHostDirective) dynamicComponentLoader: DynamicComponentHostDirective;
```

### ComponentFactoryResolver

```typescript
export class AppComponent implements OnInit {
  
  @ViewChild(DynamicComponentHostDirective) dynamicComponentLoader: DynamicComponentHostDirective;

  private _chooseForm = 'A';

  get chooseForm() {
    return this._chooseForm;
  }

  set chooseForm(value) {
    this._chooseForm = value;
    this.setDynamicComponent();
  }

  mapping = new Map<string, any>(
    [
      ['A', ComponentAComponent],
      ['B', ComponentBComponent],
      ['C', ComponentCComponent],
    ]
  );

  constructor(private componentFactoryResolver: ComponentFactoryResolver) { }

  ngOnInit() {
    this.setDynamicComponent();
  }
  
  setDynamicComponent() {
    const targetComponent = this.mapping.get(this.chooseForm);

    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(targetComponent);

    const viewContainerRef = this.dynamicComponentLoader.viewContainerRef;
    
    viewContainerRef.clear();
      
    const componentRef = viewContainerRef.createComponent(componentFactory);
  }
}
```

## ng-template with `[ngIf]`, `[ngIfThen]` and `[ngIfElse]`

```html
<h2>Using ngTemplate with ngIf then & else</h2>
 
<div *ngIf="selected; 
      then thenBlock1 
      else elseBlock1">
  <p>This content is not shown</p>
</div>
 
<ng-template #thenBlock1>
  <p>content to render when the selected is true.</p>
</ng-template>
 
<ng-template #elseBlock1>
  <p>content to render when selected is false.</p>
</ng-template>
```

The above `ngif` can be written using the ng-template syntax.   
`<ng-template [ngIf]="..." [ngIfThen]="..." [ngIfElse]="...">`

```html
<ng-template
  [ngIf]="selected" 
  [ngIfThen]="thenBlock2" 
  [ngIfElse]="elseBlock2">
  <div>
    <p>This content is not shown</p>
  </div>
</ng-template>
 
<ng-template #thenBlock2>
    ...
</ng-template>
 
<ng-template #elseBlock2>
    ...
</ng-template>
```

## ng-template with `[ngForOf]` and `[ngForTrackBy]`

```html
<ul>
   <li *ngFor="let movie of movies; 
        let i=index ; 
        let even=even; 
        trackBy: trackById">
     {{ movie.title }} - {{movie.director}}
   </li>
</ul>
 
<ul>
    <ng-template 
      ngFor let-movie [ngForOf]="movies" 
      let-i="index" 
      let-even="even"
      [ngForTrackBy]="trackById">
  
    <li>
        {{ movie.title }} - {{movie.director}}
    </li>
    
    </ng-template>
</ul>
```

## ng-template with `[ngSwitch]`, `[ngSwitchCase]` and `ngSwitchDefault`
```html
<input type="text" [(ngModel)]="num">
 
<div [ngSwitch]="num">
  <div *ngSwitchCase="'1'">One</div>
  <div *ngSwitchCase="'2'">Two</div>
  <div *ngSwitchCase="'3'">Three</div>
  <div *ngSwitchCase="'4'">Four</div>
  <div *ngSwitchCase="'5'">Five</div>
  <div *ngSwitchDefault>This is Default</div>
</div>


<!-- instead -->
<div [ngSwitch]="num">
    <ng-template [ngSwitchCase]="'1'">
        <div>One</div>
    </ng-template>
    
    <ng-template [ngSwitchCase]="'2'">
        <div>Two</div>
    </ng-template>
    
    <ng-template [ngSwitchCase]="'3'">
        <div>Three</div>
    </ng-template>
    
    <ng-template [ngSwitchCase]="'4'">
        <div>Four</div>
    </ng-template>
    
    <ng-template [ngSwitchCase]="'5'">
        <div>Five</div>
    </ng-template>

    <ng-template ngSwitchDefault>
        <div>This is default</div>
    </ng-template>
</div>
 ```

 ## `*ngTemplateOutlet`

[Reference :: [Angular 大師之路] Day 12 - *ngTemplateOutlet 與 ng-template 的完美組合](https://ithelp.ithome.com.tw/articles/10205829)

- [*ngTemplateOutlet](#ngtemplateoutlet)
    - [Syntax](#syntax)
  - [Outlet傳入多個參數到Template](#outlet傳入多個參數到template)

**The ngTemplateOutlet, is a structural directive, which renders the template.**
- 透過 `*ngTemplateOutlet="TemplateRefName | Directive | Component"` 來顯示(OutLet)某個特定Template，減少Template重複
### Syntax

```html 
<ng-template #TemplateReferenceName>
  ...                      ^
  ....                      \
</ng-template>               \ 
                              \
<element *ngTemplateOutlet = "TemplateReferenceName">
</element>
```
## Outlet傳入多個參數到Template 

```html
<ng-template #data let-input="$Implicit" let-x="x" let-y = "y">
</ng-template>

<div *ngTemplateOutlet="data; 
      context: {
                $implicit: {property: value , ... , ...}, 
                x        : {propertyX : value , .. , ..}
      },"
></div>
```
- `$implicit` : default Field

For example
```html
<div *ngTemplateOutlet="data; 
      context: {$implicit: {value: 1}, 
                another: {value: '1', value2 : '2' }"
></div>

<ng-template #data let-input="$Implicit" let-another="another">
  <div>{{ input | json }}</div>
  <div>{{ another.value | json }}</div>
  <div>{{ another.value2 | json}}</div>
</ng-template>
```
