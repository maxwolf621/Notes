 # Renderer (Proxy of ElementRef)

- [Renderer2 Example: Manipulating DOM in Angular](https://www.tektutorialshub.com/angular/renderer2-angular/#creating-new-element-createelement-appendchild)

- [Renderer (Proxy of ElementRef)](#renderer-proxy-of-elementref)
  - [Methods](#methods)

The Renderer2 allows us to manipulate the DOM elements, without accessing the DOM directly unlike `elementRef`.

**It provides a layer of abstraction between the DOM element and the component code to prevent from XSS injection attack** 

[code examples](https://reurl.cc/LM5X9y)
## Methods

```typescript
export abstract class Renderer2 {
  /**
   * @viewChild('reference') el : elementRef
   */

  /**
   * Create HTML Element 
   */
  abstract createElement(name: string, namespace?: string|null): any;
  createElement() {
    const div = this.renderer.createElement('div');
    const text = this.renderer.createText('Inserted at bottom');
    this.renderer.appendChild(div, text);
    this.renderer.appendChild(this.el.nativeElement, div);
  }

  /**
   * Create Content in Element
   */
  abstract createText(value: string): any;
  /**
  <div #divCreateText> </div>
  <button (click)="createText()">Create Text</button>
  **/
  @ViewChild('divCreateText', { static: false }) divCreateText: ElementRef;
  createText() {
  const text = this.renderer.createText('Example of Create Text');
  this.renderer.appendChild(this.divCreateText.nativeElement, text);
  }
  
  abstract setAttribute(el: any, name: string, value: string, namespace?: string|null): void;
  abstract removeAttribute(el: any, name: string, namespace?: string|null): void;
  /** view 
  <h2>Add/ Remove Attributes </h2>
  <input #inputElement type='text'>
  <button (click)="addAttribute()">Set Attribute</button>
  <button (click)="removeAttribute()">Remove Attribute</button>
  **/
  //Component
  @ViewChild('inputElement', { static: false }) inputElement: ElementRef;
  
  addAttribute() {
    this.renderer.setAttribute(this.inputElement.nativeElement, 'value', 'name' );
  }
  removeAttribute() {
    this.renderer.removeAttribute(this.inputElement.nativeElement, 'value');
  }


  abstract addClass(el: any, name: string): void;
  addClass() {
  this.renderer.addClass(this.elementRef.nativeElement, 'CssClassName' );
  }

  abstract removeClass(el: any, name: string): void;
  removeClass() {
    this.renderer.removeClass(this.elementRef.nativeElement, 'CssClassName');
  }

  abstract setStyle(el: any, style: string, value: any, flags?: RendererStyleFlags2): void;
  setStyle() {
    this.renderer.setStyle(this.elementRef.nativeElement, 'color', 'blue');
  }

  abstract removeStyle(el: any, style: string, flags?: RendererStyleFlags2): void;
  removeStyle() {
      this.renderer.removeStyle(this.elementRef.nativeElement, 'color');
  }

  
  abstract setProperty(el: any, name: string, value: any): void;
  abstract setValue(node: any, value: string): void;
  
  abstract listen(target: 'window'|'document'|'body'|any, 
                  eventName: string, 
                  callback: (event: any) => boolean | void): () => void;
}
```


