# ElementRef & TemplateRef & ViewRef & ViewContainerRef

[透過 ViewContainerRef 了解 Angular DOM 修改機制](https://reurl.cc/QbgGpb)   
[Angular 動態元件與應用實例](https://reurl.cc/ERyaka)   

- [ElementRef & TemplateRef & ViewRef & ViewContainerRef](#elementref--templateref--viewref--viewcontainerref)
  - [ElementRef](#elementref)
    - [Using DI to get ElementRef from HTML element](#using-di-to-get-elementref-from-html-element)
    - [XSS injection](#xss-injection)
  - [`TemplateRef` & `<ng-Template>`](#templateref--ng-template)
  - [ViewRef](#viewref)
    - [Embedded View](#embedded-view)
    - [Host Views (ComponentFactoryResolver)](#host-views-componentfactoryresolver)
  - [ViewContainerRef & `<ng-Container>`](#viewcontainerref--ng-container)
    - [Method of ViewContainerRef](#method-of-viewcontainerref)
      - [Insert](#insert)
    - [ComponentFactoryResolver](#componentfactoryresolver)
  - [ngTemplateOutlet & ngComponentOutlet](#ngtemplateoutlet--ngcomponentoutlet)

## ElementRef


`ElementRef` type field is a reference to the DOM element (e.g `<div>`)in the component/directive.


```html
<div #TemplateRef>This is Ref</div>
```

Reference to DOM Element
```typescript
@ViewChild('TemplateRef', { static: false }) 
elementFieldName: ElementRef;
```

For example, using `ElementRef` to get Element's content (`ElementRef#textContent`)
```typescript
@Component({
    selector: 'sample',
    template: `
        <div #templateRef>
        <span> I'm span </span>
        <br />
        <span> I'm span </span>
        </div>
    `
    style:`
        .className{
            background-color : red;
        }
    `
})
export class SampleComponent implements AfterViewInit {
    @ViewChild("tref", {read: ElementRef}) tref: ElementRef;
    @ViewChild('templateRef') elementRef!: ElementRef;
    @ViewChild('input', { read: NgModel }) ngModel;
    ngAfterViewInit() {
        let element = this.elementRef.nativeElement;
        // I'm span I'm span
        console.log(element.textContent);
        //console.log(element.outerHTML);
        //console.log(this.ngModel);

        element.innerHTML = "I'm ElementRef#InnerHTML";
        // Insert CSS Class Name
        element.className = 'className';
    }
}
```

### Using DI to get ElementRef from HTML element
```typescript
@Component({
    selector: 'sample',
})
export class SampleComponent{
    constructor(private hostElement: ElementRef) {
        // <sample> </sample>
        console.log(this.hostElement.nativeElement.outerHTML);
    }
}
```


### XSS injection

Improper use of `ElementRef` can result in an XSS Injection attack, the following code inject a new script to the current element reference variable (DOM)

```typescript
constructor(private elementRef: ElementRef) {
    const s = document.createElement('script');
    s.type = 'text/javascript';
    s.textContent = 'alert("Hello World")';
    this.elementRef.nativeElement.appendChild(s); 
}
```

## `TemplateRef` & `<ng-Template>`

Angular provides `TemplateRef` to reference to template variable reference in HTML host element

- `TemplateRef` 是一個簡單的 class，內含host element的`elementRef`的關聯(Reference)，提供`TemplateRef#createEmbeddedView`方法，用這個 method 可以控制view，並且 return `ViewRef` type

```typescript
@Component({
    selector: 'sample',
    template: `
        <ng-template #tpl>
            <span>I am span in template</span>
        </ng-template>
    `
})
export class SampleComponent implements AfterViewInit {
    @ViewChild("tpl") tpl: TemplateRef<any>;

    ngAfterViewInit() {
        let elementRef = this.tpl.elementRef;
        // console.log : I am span in template
        console.log(elementRef.nativeElement.textContent);
    }
}
```

Angular 從 DOM 移除了 template element，並且新增了一段註解進去，render 的結果如下：
```html
<sample>
    <!-- -->
</sample>
```


## ViewRef

View在Angular是一個應用程式UI的基本組成
- View是最小的 element 組成單位，在同一個 view 中的 element 會同時被新增或被摧毀

**Angular 建議開發者把 UI 視為 Views 的組合，而不是 HTML Tag 樹狀結構的一部分。Angular 支援兩種不同類型的 View：**
1. Embedded Views 連結到 Template
2. Host Views 連結到 Component

### Embedded View

View 可以透過`ElementRef#createEmbeddedView(null)`進行初始化
```typescript
ngAfterViewInit() {
    let view = this.elementRef.createEmbeddedView(null);
}
```
### Host Views (ComponentFactoryResolver)

**Host view 在 component 初始化時同時被產生**，利用 `ComponentFactoryResolver` 可以動態產生 component。

Initialize Component Factory : `ComponentFactoryResolver#resolveComponentFactory(component)`



```typescript
constructor(private injector: Injector, 
            private componentFactoryResolver: ComponentFactoryResolver) {

    let factory = this.componentFactoryResolver.resolveComponentFactory(ColorComponent);

    // component bind with injector
    let componentRef = factory.create(injector);

    // view can be used via ViewContainer
    let view = componentRef.hostView;
}
```
**在 Angular 中，每一個 component 都與特定的 injector 綁定**，因此當新增一個 component 時，可以把目前的 injector instance 傳進去。最重要的一點是，當 component 是動態產生的時候，一定要把這個 component 加入到 `EntryComponents` 裡面。

一旦產生 view，這個 view 就可以使用 `ViewContainer` 被加入到 DOM 裡面去。

## ViewContainerRef & `<ng-Container>`

**ViewContainer 代表一個 Container 可以 attach 一到多個 view**。

首先要先知道任何的 DOM 都可以被用來當作 view container。

利用 `ViewContainerRef` 與 `<ng-container></ng-container>` 綁定，而且在render的時候是 render 註解，因此不會產生多餘的 HTML。
```typescript
@Component({
    selector: 'sample',
    template: `
        <span>I am first span</span>
            <ng-container #vc></ng-container>
        <span>I am last span</span>
    `
})
export class SampleComponent implements AfterViewInit {
    @ViewChild("vc", {read: ViewContainerRef}) vc: ViewContainerRef;

    ngAfterViewInit(): void {
        // outputs ``
        console.log(this.vc.element.nativeElement.textContent);
    }
}
```

### Method of ViewContainerRef

```typescript
class ViewContainerRef {

    element: ElementRef
    length: number

    clear() : void
    insert(viewRef: ViewRef, index?: number) : ViewRef  // insert templateRef's view
    get(index: number) : ViewRef
    indexOf(viewRef: ViewRef) : number
    detach(index?: number) : ViewRef
    move(viewRef: ViewRef, currentIndex: number) : ViewRef

    // insert Component's view 
    createComponent(componentFactory...): ComponentRef<C> 
    // insert ng-Template's view
    createEmbeddedView(templateRef...): EmbeddedViewRef<C>  
    
    ...
}
```


#### Insert

從 template 新增一個 embedded view 並且新增到 ng-container element 中：
```typescript 
@Component({
    selector: 'sample',
    template: `
        <span>I am first span</span>
        <ng-container #vc></ng-container>
        <span>I am last span</span>
        
        <ng-template #tpl>
            <span>I am span in template</span>
        </ng-template>
    `
})
export class SampleComponent implements AfterViewInit {
    @ViewChild("vc", {read: ViewContainerRef}) vc: ViewContainerRef;
    @ViewChild("tpl") tpl: TemplateRef<any>;

    ngAfterViewInit() {
        // initialize view
        let view = this.tpl.createEmbeddedView(null);

        // insert ng-template's view in ngContainer
        this.vc.insert(view);
    }
}
```

```html
<sample>
    <span>I am first span</span>
    <!-- -->
    <span>I am span in template</span>
    <!-- -->
    <span>I am last span</span>
</sample>
```
### ComponentFactoryResolver

```typescript
//  ComponentFactoryResolver to inject the instance of component to containerRef#createComponent
import { Component, ComponentFactoryResolver, ViewChild } from '@angular/core';
// the component that will be dynamically insert in View
import { ExampleComponent } from './example/example.component';
// directive of containerRef
import { ContainerDirective } from './container.directive';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  @ViewChild(ContainerDirective, { static: true }) ContainerDirective: ContainerDirective;

  // 初始化 ComponentFactoryResolver
  constructor(public ComponentFactoryResolver: ComponentFactoryResolver){ 

  }

  ngOnInit(): void {

    @Deprecated
    const newComponent = 
    this.ComponentFactoryResolver.resolveComponentFactory(ContainerDirective)
    
    const targetRef = 
    this.ContainerDirective.viewContainerRef.createComponent(newComponent)
  }
}
```

## ngTemplateOutlet & ngComponentOutlet

They are Syntactic candy for Host view and embedded view

```typescript
<ng-container *ngComponentOutlet="ComponentName"></ng-container>
```

```html
<span>I am first span</span>
<ng-container *ngTemplateOutlet="tpl"></ng-container>
<span>I am last span</span>

<ng-template #tpl>
    <span>I am span in template</span>
</ng-template>
```