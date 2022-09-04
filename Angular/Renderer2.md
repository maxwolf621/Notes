# Renderer (Proxy of ElementRef)

- [Renderer2 Example: Manipulating DOM in Angular](https://www.tektutorialshub.com/angular/renderer2-angular/#creating-new-element-createelement-appendchild)

- [Renderer (Proxy of ElementRef)](#renderer-proxy-of-elementref)
  - [Methods](#methods)
  - [Create Child Element](#create-child-element)

**The Renderer2 allows us to manipulate the DOM elements, without accessing the DOM directly unlike `elementRef`.**   
**It provides a layer of abstraction between the DOM element and the component code to prevent from XSS injection attack** 

[renderer methods examples](https://reurl.cc/LM5X9y)

## Methods

```typescript
/***********************
 * @method setStyle/removeStyle
 */

//Template
<div #hello>Hello !</div>
//Component
@ViewChild('hello', { static: false }) divHello: ElementRef;

setStyle() {
    //                          element                 property , attribute
    this.renderer.setStyle(this.divHello.nativeElement, 'color', 'blue');
}
removeStyle() {
    this.renderer.removeStyle(this.divHello.nativeElement, 'color');
}


/**********************
 * @method addClass/removeClass 
 */
//Component
@ViewChild('hello', { static: false }) divHello: ElementRef;
addClass() {
  this.renderer.addClass(this.divHello.nativeElement, 'blackborder' );
}
removeClass() {
  this.renderer.removeClass(this.divHello.nativeElement, 'blackborder');
}


/*************
 * @method addAttribute/removeAttribute
 *************/
//Template
<h2>Add/ Remove Attributes </h2>
<input #inputElement type='text'>
<button (click)="addAttribute()">Set Attribute</button>
<button (click)="removeAttribute()">Remove Attribute</button>
 
//Component
@ViewChild('inputElement', { static: false }) inputElement: ElementRef;
 
addAttribute() {
  this.renderer.setAttribute(this.inputElement.nativeElement, 'value', 'name' );
}
 
removeAttribute() {
  this.renderer.removeAttribute(this.inputElement.nativeElement, 'value');
}

/**********************
 * @method createText
 **********************/
//Template
<div #divCreateText> </div>
<button (click)="createText()">Create Text</button>


@ViewChild('divCreateText', { static: false }) divCreateText: ElementRef;
createText() {
 const text = this.renderer.createText('Example of Create Text');
 this.renderer.appendChild(this.divCreateText.nativeElement, text);
}
```
## Create Child Element

`Renderer2#createElement('ElementName)`
`Renderer2#createText('Text Name')`

```typescript
//Template
<h2>Renderer2 Create Element</h2>

<div #div style="border: 1px solid black;">
    This is a div
</div>

<button (click)="createElement()">Create Element</button>
<button (click)="createElement2()">Create Element 2</button>

// Component
@ViewChild('div', { static: false }) div: ElementRef; // instance of reference to Host-Element #dive
constructor(private el: ElementRef,  // instance of reference to this component
            private renderer:Renderer2) {
}

createElement() {
  const div = this.renderer.createElement('div');
  const text = this.renderer.createText('Inserted at bottom');
  this.renderer.appendChild(div, text);
  this.renderer.appendChild(this.el.nativeElement, div);
}
createElement2() {
  const div = this.renderer.createElement('div');
  const text = this.renderer.createText('Inserted inside div');
  this.renderer.appendChild(div, text);
  this.renderer.appendChild(this.div.nativeElement, div);
}
```