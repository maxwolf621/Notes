# ngTemplate (TempalateRef&CiewContainerRef)

A template is an HTML snippet. 
The template does not render itself on DOM.

- [ngTemplate (TempalateRef&CiewContainerRef)](#ngtemplate-tempalaterefciewcontainerref)
  - [Reference](#reference)
  - [Syntax](#syntax)
  - [Display](#display)
    - [TemplateRef](#templateref)
    - [ViewContainerRef](#viewcontainerref)
    - [ComponentFactoryResolver](#componentfactoryresolver)
  - [ng-template with `[ngForOf]` and `[ngForTrackBy]`](#ng-template-with-ngforof-and-ngfortrackby)
  - [ng-template with `[ngSwitch]`, `[ngSwitchCase]` and `ngSwitchDefault`](#ng-template-with-ngswitch-ngswitchcase-and-ngswitchdefault)

## Reference
[How to use ng-template & TemplateRef in Angular](https://www.tektutorialshub.com/angular/ng-template-in-angular/)

## Syntax

ngTemplate is controlled by directive of component
```html
<ngTemplate directiveSelectorName>  ... </ngTemplate>
```

ngTemplate is controlled by TemplateName
```html
<ngTemplate #TemplateName> ... </ngTemplate>
```

Pass parameter to this ngTemplate
```html
<ngTemplate ... let-templateParameter = "parameter-NameFrom-*TemplateOutlet" ,
                let-XXX =".."> 
      <div>{{ templateParameter | json }}</div>
      <div>{{ XXX | json }}</div>
</ngTemplate>
```

## Display 

1. Using the `ngTemplateOutlet` directive.
2. Using the `TemplateRef` & `ViewContainerRef`

- The `TemplateRef` holds the reference template defined by ng-template.
- `ViewContainerRef`, when injected to via DI holds the reference to the host element, that hosts the component (or directive).

### TemplateRef

TemplateRef is a class and the way to reference the ng-template in the component or directive class. 

Using the TemplateRef we can manipulate the template from component code.
- `ng-template` is a bunch of HTML tags enclosed in a HTML element `<ng-template>`
```html
<ng-template #sayHelloTemplate>
  <p> Say Hello</p>
</ng-template>
```

`@ViewChild('ngTemplate-Ref_Name') variable : TemplateRef<any>;`
```typescript
@ViewChild('sayHelloTemplate', 
          )
sayHelloTemplate:TemplateRef; // or TemplateRef<any>
```

### ViewContainerRef

To tell Angular where to render it. The way to do is to use the `ViewContainerRef`.

The `ViewContainerRef` is also similar to `TemplateRef`.    
Both hold the reference to part of the view.   
 
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
```

## ng-template with `[ngIf]`, `[ngIfThen]` and `[ngIfElse]`

```html
<h2>Using ngTemplate with ngIf then & else</h2>
 
<div *ngIf="selected; 
      then thenBlock1 else elseBlock1">
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