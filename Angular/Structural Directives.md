# Structural Directive `*`

- [[Angular 大師之路] Day 16 - 自己的樣板語法自己做 (Structural Directives)](https://ithelp.ithome.com.tw/articles/10207012)
- [`*` Structural Directives](https://ithelp.ithome.com.tw/articles/10195273)
- [https://angular.io/guide/structural-directives](https://angular.io/guide/structural-directives)

- [Structural Directive `*`](#structural-directive-)
  - [Custom Structural Directive](#custom-structural-directive)

Angular利用`*`來實現Structural Directive  

```html
<div *ngIf="hero" >{{hero.name}}</div>
```
is equivalent to
```html
<!-- Selector ngiF -->
<ng-template [ngIf]="hero">
  <div>{{hero.name}}</div>
</ng-template>
```
- `*`會將`ngIf`改為一個Attribute Bind(`[..] = ".."`) 
- `*ngIf`隱藏掉的物件，和我們使用CSS去`show`以及`hide`在意義上是完全不同的
  - 如果我們需要在show或hide物件的同時執行一些特殊的指令，可以用Lifecycle Hooks來撰寫此時要做的事情。
  - **若單純使用css去hide、show元素(只是隱藏而已)，所有的監聽器、物件依舊會在背景執行，這會讓效能變得不佳**
- `ng-template`並不會一開始就顯示在畫面上，而是通過Directive操作裡面的DOM並將要顯示的template view添加在DOM之中


## Custom Structural Directive

```typescript
import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
@Directive({ selector: '[appUnless]' })
export class UnlessDirective {

  private hasView = false;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) {}

  @Input()
  set appUnless(condition: boolean) {
    if (!condition && !this.hasView) {
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.hasView = true;
    } else if (condition && this.hasView) {
      this.viewContainer.clear();
      this.hasView = false;
    }
  }
}
```
```html
<p *appUnless="condition">
  This paragraph is displayed because the condition is false.
</p>
```
