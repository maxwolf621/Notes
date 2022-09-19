

## ngTemplateOutlet & ngTemplate

```html 
<ng-template #TemplateReference DIRECTIVE>
  ...                ^          ^
  ....                \         /
</ng-template>         \       / 
                        \     /
<div *ngTemplateOutlet = "OBJ">
</div>
```

```html
<ng-template #data let-input="$Implicit" let-variable="variable">
  ...
  ...
</ng-template>

<div *ngTemplateOutlet="data;  context: obj"
></div>
```
```typescript
let obj = {
    // variable : values
    $implicit : {     
        fieldA : a
        fieldB : b
        fieldC : C },
    variable : "otherProperty"
}
```

## Directive

**You can change the appearance, behavior, or layout of a DOM element using the Directives.**

Dynamical Attribute Binding : Bind html element's properties to properties in Component.  

Static Attribute Binding : Bind html element's properties with string values directly


### Attribute directives `[ngXXXXX]`

#### ngStyle & ngClass

ngClass : bind HTML element with CSS class    
ngStyle : bind HTML element with CSS properties   

Static Binding 
```html
<element [ngClass] = "'cssClass1 cssClass2 ... cssClassN"> String </element>
<element [ngClass] = 'cssClass1 cssClass2 ... cssClassN'>String </element>
<element [ngClass] = "['cssClass1', 'cssClass2']"> Array </element>
<element [ngClass] = "{'cssClass1': true, 'cssClass2': true}"> Object </element>
```

Dynamically Binding
```html
<element [ngClass]= obj></element>
```
```typescript
let obj = {
    'cssClass1',
    'cssClass1',
    ...
}
```

## ElementRef & TemplateRef & View Ref & ViewContainerRef

`ElementRef` is an reference to HTML element `<element> .. </element>`

`TemplateRef` is an reference to HTML element with `#` `<element #xyz></element>`

`ViewContainerRef` is an reference to `@Directive` component
```typescript
@Directive({
  selector: '[appDynamicComponentHost]'
})
export class DynamicComponentHostDirective {
  constructor(public viewContainerRef: ViewContainerRef) { }
}
```


## ContentChild(ren) / ViewChild(ren)

- `<ng-content>` : Child Template 內的`<ng-content></ng-content>` = Parent Template要嵌入的地方     

- `@ViewChild` : 從View裡取得特定Component/Template variable Reference的實體給Component用      

- `@ContentChild` : Child Component 可以操作`<ng-content></ng-content>` Parent Template嵌入的元素  

```typescript 
XXXXChild(selector: string | Function | Type<any>, 
             opts: { read?: any; static: boolean; }): any
```

selector : **the change detector looks for the first element that matches the selector and updates the component property with the reference to the element.**

static : `true`  the query is initialized before first change detection , `false` for query to be resolved after every change detection

token : return the correct type from HTML element
```typescript 
<input #nameInput [(ngModel)]="name">

@ContentChild('nameInput',{static:false, read: NgModel}) 
nameVarAsNgModel;

@ContentChild('nameInput',{static:false, read: ElementRef})
nameVarAsElementRef;

@ContentChild('nameInput', {static:false, read: ViewContainerRef })
nameVarAsViewContainerRef;
```


## ComponentFactoryResolver

Dynamically load only needed component

```typescript
export class AppComponent implements OnInit {
  
  @ViewChild(DynamicComponentHostDirective) dynamicComponentLoader: DynamicComponentHostDirective;

  // Default chooseComponent A
  private _chooseComponent = 'A';

  get chooseComponent() {
    return this._chooseComponent;
  }

  set chooseComponent(value) {
    this._chooseComponent = value;
    this.setDynamicComponent();
  }

  // needed Component
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
    
    const targetComponent = this.mapping.get(this.chooseComponent);

    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(targetComponent);

    const viewContainerRef = this.dynamicComponentLoader.viewContainerRef;
    
    viewContainerRef.clear();
      
    const componentRef = viewContainerRef.createComponent(componentFactory);
  }
}
```