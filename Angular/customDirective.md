# Custom Directive

[How to Create & Use Custom Directive In Angular](https://www.tektutorialshub.com/angular/custom-directive-in-angular/#creating-custom-attribute-directive)

- [Custom Directive](#custom-directive)
  - [Custom Attribute Directive](#custom-attribute-directive)
  - [Custom Structural Directive](#custom-structural-directive)


## Custom Attribute Directive

```typescript
import { Directive, ElementRef, Input, OnInit } from '@angular/core'
 
@Directive({
  selector: '[ttClass]',
})
export class ttClassDirective implements OnInit {
 
  @Input() ttClass: string;
 
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

## Custom Structural Directive

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
        this._updateView();
    }
    
    _updateView() {
        if (this._ttif) {
        this._viewContainer.createEmbeddedView(this.templateRef);
        }
        else {
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
    show = true;
}

// Template
<h1> {{title}} </h1>
Show Me

<input type="checkbox" [(ngModel)]="show">
 
<!-- if show is true then display the following elements -->
<div *ttIf="show">
  Using the ttIf directive
</div>
<div *ngIf="show">
  Using the ngIf directive
</div>
 
```

- use the `*` to tell Angular that we have a structural directive and we will be manipulating the DOM. It basically tells angular to inject the `TemplateRef`.  
To inject the `templateRef`, the Angular needs to locate the template. The `*` tells the Angular to locate the template and inject its reference as `templateRef`



