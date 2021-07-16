# Sturctural Directives

With `*` Angular自動將裡面的內容儲存成一個ng-template並且操縱它，
```html
<div *ngIf="hero" >{{hero.name}}</div>
```
`*` 會悄悄的讓這個directive成為structure的directive
```html
<!--
`*`號將該ngIf改為一個Attribute Bind的元素，並綁定一個ng-template
-->
<ng-template [ngIf]="hero">
  <!-- 
  剩下的部分`<div>`，包括它的class屬性，移到了`<ng-template>`元素之下 
  -->
  <div>{{hero.name}}</div>
</ng-template>
```



> `*ngIf`隱藏掉的物件，和我們使用css去show、hide在意義上是完全不同的。因為它已經不在dom之上，是沒辦法被操作的。

## 套用Multiple Sturctural Directives在Component裡

如果我們需要在show、hide物件的同時執行一些特殊的指令，可以用Lifecycle Hooks來撰寫此時要做的事情。 

> 上面有提到，我們可以將許多屬性指令寫在同一個host element上，但同一個host element只能夠有一個結構指令。 

所以在一般的狀態下，**如果需要兩個標籤，我們會將HTML利用一些不會影響結構的標籤來做多層的Structural Directives控制**  
```html
<!-- 
利用HTML tag進行多層Structural Directives( *ngIf 以及 *ngFor ) 控制
-->
<div *ngIf="hero"><span *ngFor="hero of heroes">{{hero.name}} </span></div>
```

但有時候該狀況不允許任何多餘的標籤在裡面，例如下拉選單select。
若有一個區域選單select裡面的option內容要由`*ngFor`來產生，但是當`city`未選擇時，select又希望不要有任何下拉選單，
這時後我們在option上的確同時會需要放許多個結構指令，但ANGULAR不允許同一個標籤上放兩個結構指令。
如果我們多包一層span去包裡面的內容，會發現因為select內不允許span標籤，會造成讀不到option下拉選單，即便已經選擇了city
```html 
<div>
  Pick your favorite hero
  (<label><input type="checkbox" checked (change)="showSad = !showSad">show sad</label>)
</div>
<select [(ngModel)]="hero">
  <span *ngFor="let h of heroes">
    <span *ngIf="showSad || h.emotion !== 'sad'">
      <option [ngValue]="h">{{h.name}} ({{h.emotion}})</option>
    </span>
  </span>
</select>

<!-- 
這時候就可以改用<ng-container>
-->
<select [(ngModel)]="hero">
  <ng-container *ngFor="let h of heroes">
    <ng-container *ngIf="showSad || h.emotion !== 'sad'">
      <option [ngValue]="h">{{h.name}} ({{h.emotion}})</option>
    </ng-container>
  </ng-container>
</select>
```

## Custom sturctural Directive

```typescript
import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';

/**
 * Add the template content to the DOM unless the condition is true.
 */
@Directive({ selector: '[appUnless]'})
export class UnlessDirective {
  private hasView = false;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef) { }

  @Input() set appUnless(condition: boolean) {
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
<p *appUnless="condition" class="unless a">
  (A) This paragraph is displayed because the condition is false.
</p>
```
