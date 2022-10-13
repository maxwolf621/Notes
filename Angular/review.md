# Review

- [Review](#review)
  - [ngTemplateOutlet](#ngtemplateoutlet)
  - [Directive](#directive)
    - [Attribute directives `[ngXXXXX]`](#attribute-directives-ngxxxxx)
      - [ngStyle & ngClass](#ngstyle--ngclass)
  - [ElementRef & TemplateRef & View Ref & ViewContainerRef](#elementref--templateref--view-ref--viewcontainerref)
  - [ContentChild(ren) / ViewChild(ren)](#contentchildren--viewchildren)

## ngTemplateOutlet 

`ngTemplateOut = TemplateRef + @ViewChild + viewCOntainerRef + ComponentFactoryResolver`

```html 
<ng-template #TemplateReference DIRECTIVE let-input="$Implicit" let-variable="variable">
  ...                ^           ^
  ....                \         /
</ng-template>         \       / 
                        \     /
<ele *ngTemplateOutlet = "OBJ; context: obj" ></ele>
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
`TemplateRef` is an reference to HTML element with `#` `<ng-template #xyz></ng-template>`   
`ViewContainerRef` is an reference to `@Directive` component

Directive of ViewContainerRef
```typescript
@Directive({
  selector: '[appDynamicComponentHost]'
})
export class DynamicComponentHostDirective {
  constructor(public viewContainerRef: ViewContainerRef) { }
}
```

Usage 
```html
<element DynamicComponentHostDirective></element>
```

Inject ng-template
```html
<ng-container #vc></ng-container>
<ng-template #tpRef>
  <span>I am span in template</span>
</ng-template>
```

```typescript
@ViewChild('tpRef') tplRef!: TemplateRef<any>;
@ViewChild('vc', { read: ViewContainerRef }) vc!: ViewContainerRef;

ngAfterViewInit() {
  const elem = this.tplRef.createEmbeddedView(null);
  this.vc.insert(elem)
```

## ContentChild(ren) / ViewChild(ren)

- `<ng-content>` : Child Template 內的`<ng-content></ng-content>` = Parent Template要嵌入的地方     

- `@ViewChild` : 從View裡取得特定Component/Template variable Reference的實體給Component用      

- `@ContentChild` : Child Component可以操作`<ng-content></ng-content>` Parent Template嵌入的元素  


```typescript 
<input #nameInput [(ngModel)]="name">

@ContentChild('nameInput',{static:false, read: NgModel}) 
nameVarAsNgModel;

@ContentChild('nameInput',{static:false, read: ElementRef})
nameVarAsElementRef;

@ContentChild('nameInput', {static:false, read: ViewContainerRef })
nameVarAsViewContainerRef;
```


