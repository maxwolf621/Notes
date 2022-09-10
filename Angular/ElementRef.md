# ElementRef & TempalteRef & View Ref & ViewContainerRef

[透過 ViewContainerRef 了解 Angular DOM 修改機制](https://jimmylin212.github.io/post/0013_exploring_angular_dom_manipulation/)
[Angular 動態元件與應用實例](https://medium.com/@rtw0913/angular-%E5%8B%95%E6%85%8B%E5%85%83%E4%BB%B6%E8%88%87%E6%87%89%E7%94%A8%E5%AF%A6%E4%BE%8B-c398e99bc59d)

- [ElementRef & TempalteRef & View Ref & ViewContainerRef](#elementref--tempalteref--view-ref--viewcontainerref)
  - [ElementRef](#elementref)
    - [Read Specific Token(ref) For Multiple `TemplateRef`s](#read-specific-tokenref-for-multiple-templaterefs)
    - [XSS injection](#xss-injection)
  - [TemplateRef](#templateref)
  - [ViewRef](#viewref)
    - [ComponentFactoryResolver動態產生Component](#componentfactoryresolver動態產生component)
  - [ViewContainerRef](#viewcontainerref)
    - [Method of ViewContainerRef](#method-of-viewcontainerref)
      - [Insert](#insert)
      - [createComponent](#createcomponent)

## ElementRef

The DOM objects are created and maintained by the Browser. 

To manipulate the DOM using the `ElementRef`, we need to get the reference to the DOM element in the component/directive.

Angular provides a lot of directives like Class Directive or Style directive. to Manipulate their styles etc.


To get the reference to DOM elements in the component
1. Create a template reference variable for the element in the component/directive.
2. Use the template variable(`templateRef`) to inject the element(DI) into component class using the `@ViewChild` or `@ViewChildren`.

```html
<!--
    referenceRef : #hello
-->
<div #ReferenceName>This is Ref</div>
```

Inject the ElementRef via Decoration `@ViewChild` or `@ViewChildren`
```typescript
@ViewChild('hello', { static: false }) divHello: ElementRef;
```

For example

using ElementRef to get Element's textContnet
```typescript
@Component({
    selector: 'sample',
    template: `
        <span #tref>I am span</span>
    `
})
export class SampleComponent implements AfterViewInit {
    @ViewChild("tref", {read: ElementRef}) tref: ElementRef;

    ngAfterViewInit(): void {
        // outputs `I am span`
        console.log(this.tref.nativeElement.textContent);
    }
}
```

Using DI to get ElementRef
```typescript
@Component({
    selector: 'sample',
})
export class SampleComponent{
    constructor(private hostElement: ElementRef) {
        //outputs <sample>...</sample>
        console.log(this.hostElement.nativeElement.outerHTML);
    }
}
```


### Read Specific Token(ref) For Multiple `TemplateRef`s

```typescript
<input #nameInput [(ngModel)]="name">

//ViewChild returns ElementRef i.e. input HTML Element
@ViewChild('nameInput',{static:false, read: ElementRef}) elRef;
 
//ViewChild returns NgModel associated with the nameInput
@ViewChild('nameInput',{static:false, read: NgModel}) inRef;
```

```typescript
import { Component,ElementRef, ViewChild, AfterViewInit } from '@angular/core';
 
@Component({
  selector: 'app-root',
  template:  '<div #hello>Hello</div>'
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit {
    @ViewChild('hello', { static: false }) divHello: ElementRef;
 
    ngAfterViewInit() {
        this.divHello.nativeElement.innerHTML = "Hello Angular";
        this.divHello.nativeElement.className="someClass";
        this.divHello.nativeElement.style.backgroundColor="red";
    }
}
```

### XSS injection

Improper use of ElementRef can result in an XSS Injection attack, the following code inject a new script to the current element reference variable (DOM)

```typescript
constructor(private elementRef: ElementRef) {
    const s = document.createElement('script');
    s.type = 'text/javascript';
    s.textContent = 'alert("Hello World")';
    this.elementRef.nativeElement.appendChild(s); 
}
```

## TemplateRef

HTML5 原生支援了 template tag，Angular use `TemplateRef` to get the instance of template variable reference

TemplateRef 是一個簡單的 class，他存著與其 host element 的關聯在 elementRef 內，而且提供`Template#createEmbededView`方法，用這個 method 可以新增view，並且return `ViewRef` type

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
        // outputs `` (empty)
        console.log(elementRef.nativeElement.textContent);
    }
}
```

Angular 從 DOM 移除了 template element，並且新增了一段註解進去，render 的結果如下：
```typescript
<sample>
    <!-- -->
</sample>
```


## ViewRef

**在 Angular 的世界中，View 是一個應用程式 UI 的基本組成。他是最小的 element 組成單位，在同一個 view 中的 element 會同時被新增或同時被摧毀 (destroyed)。**

**Angular 建議開發者把 UI 視為 Views 的組合，而不是 HTML Tag 樹狀結構的一部分。Angular 支援兩種不同類型的 View：**
1. Embeded Views 連結到 Template
2. Host Views 連結到 Component


View 可以透過`Elemetnef#createEmbededView(null)` 從 template 初始化。
```typescript
ngAfterViewInit() {
    // tempalteRef#createEmbeddedView
    let view = this.tpl.createEmbeddedView(null);
}
```
### ComponentFactoryResolver動態產生Component

**Host view 在 component 初始化時同時被產生**，利用 `ComponentFactoryResolver` 可以動態產生 component。

```typescript
constructor(private injector: Injector, private r: ComponentFactoryResolver) {

    let factory = this.r.resolveComponentFactory(ColorComponent);

    // component bind with injector
    let componentRef = factory.create(injector);

    let view = componentRef.hostView;
}
```
**在 Angular 中，每一個 component 都與特定的 injector 綁定**，因此當新增一個 component 時，可以把目前的 injector instance 傳進去。最重要的一點是，當 component 是動態產生的時候，一定要把這個 component 加入到 EntryComponents 裡面。

一旦產生 view，這個 view 就可以使用 `ViewContainer` 被加入到 DOM 裡面去。

## ViewContainerRef

**ViewContainer 代表一個 Container 可以 attach 一到多個 view**。

首先要先知道任何的 DOM 都可以被用來當作 view container。

利用 vc (`ViewContainerRef`) 與 `<ng-container></ng-container>` 綁定，而且在render的時候是 render 註解，因此不會產生多餘的 HTML。
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
ViewContainerRef 提供許多方便的 API 讓開發者可以操作 DOM：
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

    createComponent(componentFactory...): ComponentRef<C> // insert Component's view
    createEmbeddedView(templateRef...): EmbeddedViewRef<C>  
    ...
}
```


#### Insert
下面的例子展現了如何從 template 新增一個 embeded view 並且新增到 ng-container element 中：
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
        let view = this.tpl.createEmbeddedView(null);

        // insert ng-temaplte's view in ngContainer
        this.vc.insert(view);
    }
}
```
產生出來的 html 如下：
```html
<sample>
    <span>I am first span</span>
    <!-- -->
    <span>I am span in template</span>

    <span>I am last span</span>
    <!-- -->
</sample>
```
#### createComponent

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

  // step 2 使用 viewchild 獲取 directive 指令
  @ViewChild(ContainerDirective, { static: true }) ContainerDirective: ContainerDirective;

  // step 5 初始化 ComponentFactoryResolver
  constructor(
    public ComponentFactoryResolver: ComponentFactoryResolver
  ) { }

  ngOnInit(): void {

    const newComponent = this.ComponentFactoryResolver.resolveComponentFactory(ExampleComponent)
    
    const targetRef = this.ContainerDirective.viewContainerRef.createComponent(newComponent)
  }
}
```