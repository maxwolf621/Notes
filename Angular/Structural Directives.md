# Structural Directive `*`

[[Angular 大師之路] Day 16 - 自己的樣板語法自己做 (Structural Directives)](https://ithelp.ithome.com.tw/articles/10207012)

- [Structural Directive `*`](#structural-directive-)
  - [Reference](#reference)
  - [在HTML套用Multiple Structural Directives](#在html套用multiple-structural-directives)
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

## Reference
- [`*` Structural Directives](https://ithelp.ithome.com.tw/articles/10195273)
- [https://angular.io/guide/structural-directives](https://angular.io/guide/structural-directives)

## 在HTML套用Multiple Structural Directives 

**我們可以將多個Attribute Directives寫在同一個Host Element上，但同一個Host Element只能夠有一個Structural Directives**   
所以在一般的狀態下,**利用會利用一些不會影響畫面的Element tag來做多層的Structural Directives控制**   
```html
<div *ngIf="hero">
    <span *ngFor="hero of heroes">{{hero.name}} </span>
</div>
```

但有時候狀況不允許任何多餘的Neted Element Tag, e.g. 下拉式選單  

例如在`<select> ... </select>`內多遷入多個`<span> ... </span>`來搭配Structural Directives，會發現因為`<select> ... </select>`內不允許`<span> ... </span>`，會造成讀不到`option`下拉選單
```html 
<div>
  Pick your favorite hero
  (<label>
    <input type="checkbox" checked(change)="showSad = !showSad">
    show sad
  </label>)
</div>

<select [(ngModel)]="hero">
  <!-- two span tags-->
  <span *ngFor="let h of heroes">
    <span *ngIf="showSad || h.emotion !== 'sad'">
      <option [ngValue]="h">{{h.name}} ({{h.emotion}})</option>
    </span>
  </span>
</select>
```
![image](https://user-images.githubusercontent.com/68631186/129431539-5f8ffb4c-92e4-4dd8-b8eb-b78a687f6ba6.png)

這時候就可以改用`<ng-container *ng...=".." >`來實現多層Multiple Structural Directives 
```html
<select [(ngModel)]="hero">
  <ng-container *ngFor="let h of heroes">
    <ng-container *ngIf="showSad || h.emotion !== 'sad'">
      <option [ngValue]="h">
        {{h.name}} ({{h.emotion}})
      </option>
    </ng-container>
  </ng-container>
</select>
```
![image](https://user-images.githubusercontent.com/68631186/129431979-33264c35-0cf9-4998-aaaa-c03294e83fc4.gif)

## Custom Structural Directive

```typescript
import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
/**
 * Add the template content to the DOM 
 * unless the condition is true.
 */
@Directive({ selector: '[appUnless]'})
export class UnlessDirective {
  private hasView = false;

  constructor(
    private templateRef: TemplateRef<any>,

    private viewContainer: ViewContainerRef) { }

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
