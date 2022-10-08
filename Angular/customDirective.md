# Custom Directive

[How to Create & Use Custom Directive In Angular](https://www.tektutorialshub.com/angular/custom-directive-in-angular/#creating-custom-attribute-directive)

- [Custom Directive](#custom-directive)
  - [Custom Attribute Directive (ElementRef)](#custom-attribute-directive-elementref)
  - [Custom Structural Directive (ViewContainerRef & TemplateRef)](#custom-structural-directive-viewcontainerref--templateref)

## Custom Attribute Directive (ElementRef)

```typescript
import { Directive, ElementRef, Input, OnInit } from '@angular/core'
 
@Directive({
  selector: '[ttClass]',
})
export class ttClassDirective implements OnInit {

  @Input() ttClass: string;
 
  // el : host element e.g <button [ttClass]="'blue'">
  // el is button
  constructor(private el: ElementRef) {
  }
 
  ngOnInit() {
    this.el.nativeElement.classList.add(this.ttClass);
  }
}
```

```typescript
// equal class = blue
<button [ttClass]="'blue'">Click Me</button>

// Css setup
.blue {
  background-color: lightblue;
}

```

## Custom Structural Directive (ViewContainerRef & TemplateRef)

```typescript
// Directive
import { Directive, ViewContainerRef, TemplateRef, Input } from '@angular/core';
 
@Directive({ 
  selector: '[ttIf]' 
})
export class ttIfDirective  {
  _ttif: boolean;
    
  constructor(private _viewContainer: ViewContainerRef,
              private templateRef: TemplateRef<any>) {
  }
  
  @Input()
  set ttIf(condition) {
      this._ttif = condition
      this.updateView();
  }
  
  updateView() {
    if (this._ttif) {
      this._viewContainer.createEmbeddedView(this.templateRef);
    }else {
      this._viewContainer.clear();
    }
  }
}

// Component
import { Component } from '@angular/core';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
    title: string = "Custom Directives in Angular";
    isShow = true;
}
```

```html
<h1> {{title}} </h1>

<input type="checkbox" [(ngModel)]="isShow">
 
<!-- if show is true then display the following elements -->
<div *ttIf="isShow">
  Using the ttIf directive
</div>
<div *ngIf="isShow">
  Using the ngIf directive
</div>
 
```
- use the `*` to tell Angular that we have a structural directive and we will be manipulating the DOM. It basically tells angular to inject the `TemplateRef`.  
To inject the `templateRef`, the Angular needs to locate the template. The `*` tells the Angular to locate the template and inject its reference as `templateRef`



