# quickReview

- [quickReview](#quickreview)
  - [Template Variable `#`](#template-variable-)
  - [ViewChildren && ViewChild](#viewchildren--viewchild)
  - [ngTemplate + ngTemplateOutlet + viewChild/ren](#ngtemplate--ngtemplateoutlet--viewchildren)
  - [ng-container](#ng-container)


## Template Variable `#`

```html
<input #phone placeholder="phone number" />

<!-- lots of other elements -->

<!-- phone refers to the input element; pass its `value` to an event handler -->
<button type="button" (click)="callPhone(phone.value)">Call</button>
```

## ViewChildren && ViewChild

```typescript
import {AfterViewInit, Component, Directive, Input, QueryList, ViewChildren} from '@angular/core';

// directive
@Directive({selector: 'pane'})
export class Pane {
  @Input() id!: string;
}

// component
@Component({
  selector: 'example-app',
  template: `

    <pane id="1"></pane>
    <pane id="2"></pane>
    <pane id="3" *ngIf="shouldShow"></pane>

    <button (click)="show()">Show 3</button>

    <div>panes: {{serializedPanes}}</div>
  `,
})
export class ViewChildrenComp implements AfterViewInit {
  
  @ViewChildren(Pane) panes!: QueryList<Pane>;
  serializedPanes: string = '';

  shouldShow = false;

  show() {
    this.shouldShow = true;
  }

  ngAfterViewInit() {
    this.calculateSerializedPanes();
    this.panes.changes.subscribe((r) => {
      this.calculateSerializedPanes();
    });
  }

  calculateSerializedPanes() {
    setTimeout(() => {
      this.serializedPanes = this.panes.map(p => p.id).join(', ');
    }, 0);
  }
}
```
## ngTemplate + ngTemplateOutlet + viewChild/ren

```html
<ng-template #NameOfTemplate>
  4544                \
</ng-template>         \ 
                        \
<div *ngTemplateOutlet = "NameOfTemplate">
</div>

<!-- it woks like this -->
<div *ngTemplateOutlet = "NameOfTemplate">
    4544
</div>
```



```html
<ngTemplate selectorName let-parameterOfTemplate ="outletParameter" > 
    <.. [...] = parameterOfTemplate>1 </...> 
</ngTemplate>

<ngTemplate selectorName> 
    <.. [...] = parameterOfTemplate>2 </...> 
</ngTemplate>

<ngTemplate selectorName> 
    <.. [...] = parameterOfTemplate>3 </...> 
</ngTemplate>
                         

<div *ngTemplateOutlet = "NameOfTemplate" context : { outletParameter : fieldInComponent}>
</div>

<!-- it works like this-->
<div *ngTemplateOutlet = "NameOfTemplate">
    <!-- one of ngTemplate -->
</div>
```

Directive
```typescript
@Directive({
  selector: '[selectorName]'
})
export class ngTemplateDirective {
    constructor(templateRef : TemplateRef<any>)
}
```

Component which controlled display of template
```typescript 
export class AppComponent implements AfterViewInit {

  @ViewChildren(ngTemplateDirective)
  ngTemplateDirective: QueryList<CarouselPageDirective>;

  NameOfTemplate: TemplateRef<any>;

  setTemplate(index) {
    // assign TemplateRef to NameOfTemplate
    this.NameOfTemplate = this.carouselPages.find(
      (item, index) => index === this.index
    ).templateRef;
  }

  ngAfterViewInit() {
    this.setTemplate(this.index);
  }
```



## ng-container

Allow multiple structural directive

```html
<ul>
  <ng-container *ngFor="let item of list; let odd = odd">
    <li *ngIf="odd">{{ item }}</li>
  </ng-container>
</ul>
```


Using `ng-container` to hide `*ngFor` Structural Directives to solve CSS rendering problem
```css
.body > .content {
  color: red;
}
```
```html
<!-- it wont work 
    body's next is not content
-->
<div class="body">
  <div *ngFor="let item of list; let odd = odd">
    <div class="content" *ngIf="odd">{{ item }}</div>
  </div>
</div>


<div class="body">
  <ng-container *ngFor="let item of list; let odd = odd">
    <div class="content" *ngIf="odd">{{ item }}</div>
  </ng-container>
</div>

<!-- equal -->
<div class="body">
    <div class="content" *ngIf="odd">{{ item }}</div>
</div>
```