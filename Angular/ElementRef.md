# ElementRef

- [ElementRef](#elementref)
  - [Read Specific Token(ref) For Multiple `TemplateRef`s](#read-specific-tokenref-for-multiple-templaterefs)
    - [XSS injection](#xss-injection)

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

## Read Specific Token(ref) For Multiple `TemplateRef`s

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