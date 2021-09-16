# `ElementRef`, `@ViewChild` and `AfterViewInit`

```html
<h1>Angular 10 Example with ViewChild, AfterViewInit and ElementRef</h1>

<div #myDiv>  <!-- -->
</div>
```

```typescript
import { Component, ViewChild, ElementRef, AfterViewInit } from '@angular/core';

@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  styleUrls: [ './app.component.css' ]
})
export class AppComponent implements AfterViewInit {

  /**
   * Access the nativeElement object 
   * that represent the DOM element in the browser.
   */
  @ViewChild("myDiv") divView: ElementRef;

  ngAfterViewInit(){

    console.log(this.divView);

    this.divView.nativeElement.innerHTML = "Hello Angular 10!";

  }
}
```
- `ElementRef` allows direct access to the DOM which could risk your app to XSS attacks. 


## ElementRef with Directive

```html
<!-- .... -->

<div appMakered>
  Add the <code>appMakered</code> attribute to change the background color to red.
</div>
```

```typescript
import { Directive, ElementRef, OnInit } from '@angular/core';

@Directive({
  selector: '[appMakered]',
})
export class MakeredDirective {
  constructor(
    private elementRef: ElementRef
  ) { }

  ngOnInit() {
    this.elementRef.nativeElement.style.backgroundColor = 'red';
  }

}
```

- To manipulate the DOM using `ViewChild` and `ElementRef` but it's not actually safe to do that.


## Accessing the DOM with `Renderer2`, `ElementRef` and Angular Directives

Let's now see how to safely access and manipulate our DOM elements using Renderer2 combined to `ElementRef` but without using the `nativeElement` interface for direct access.

```typescript
import { Directive, ElementRef, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appMakered]',
})
export class MakeredDirective {

  constructor(private elementRef: ElementRef, 
              private renderer: Renderer2) 
  {
      this.renderer.setStyle(this.elementRef.nativeElement, 'background-color', 'red');
  }
}
```

```typescript
abstract class Renderer2 {
  abstract get data: {...}
  destroyNode: ((node: any) => void) | null
  abstract destroy(): void
  abstract createElement(name: string, namespace?: string | null): any
  abstract createComment(value: string): any
  abstract createText(value: string): any
  abstract appendChild(parent: any, newChild: any): void
  abstract insertBefore(parent: any, newChild: any, refChild: any): void
  abstract removeChild(parent: any, oldChild: any): void
  abstract selectRootElement(selectorOrNode: string | any): any
  abstract parentNode(node: any): any
  abstract nextSibling(node: any): any
  abstract setAttribute(el: any, name: string, value: string, namespace?: string | null): void
  abstract removeAttribute(el: any, name: string, namespace?: string | null): void
  abstract addClass(el: any, name: string): void
  abstract removeClass(el: any, name: string): void
  abstract setStyle(el: any, style: string, value: any, flags?: RendererStyleFlags2): void
  abstract removeStyle(el: any, style: string, flags?: RendererStyleFlags2): void
  abstract setProperty(el: any, name: string, value: any): void
  abstract setValue(node: any, value: string): void
  abstract listen(target: 'window' | 'document' | 'body' | any, eventName: string, callback: (event: any) => boolean | void): () => void
}
```