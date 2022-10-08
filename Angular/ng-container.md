# ng-container

- [ng-container](#ng-container)
  - [`<select></select>`](#selectselect)
  - [Invisibility of ng-container for CSS](#invisibility-of-ng-container-for-css)

**`<ng-container ..>` allow programmer to bind multiple structural directive (`*`)**
e.g. `*ngFor` ... etc   

For example the following causes `Template parse error` without `<ng-container>` to bind multiple structural directives
```html
<!-- Error -->
<ul>
  <li *ngFor="let item of list; let odd = odd" * ngIf="odd">
    {{ item }}
  </li>
</ul>

<!-- using ng-container bind multiple structural directive-->
<ul>
  <ng-container *ngFor="let item of list; let odd = odd">
    <li *ngIf="odd">{{ item }}</li>
  </ng-container>
</ul>
```
The above code actually looks like the following when it is executed
```html
<!-- <ng-container> is invisible -->
<ul>
    <li *ngIf="odd">{{ item }}</li>
</ul>
```

## `<select></select>`

在`<select> ... </select>`內多遷入多個`<span *Structural_Directive> ... </span>`，會因為`<select>`內不允許`<span>`存在，造成`<option>`下拉選單讀不到

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


Multiple Structural Directives via `<ng-container>`
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

## Invisibility of ng-container for CSS 

```html
<div class="body">
  <div *ngFor="let item of list; let odd = odd">
    <div class="content" *ngIf="odd">{{ item }}</div>
  </div>
</div>
```
```css
/*
  Nested Class 
  <.. class = "body">
    <... class = "content"></...>
  </..>
*/
.body > .content {
  color: red;
}
```
It might cause some problem, because there is a extra HTML element `<div *ngFor=...>` btw `<div class="body">` and `<div class="content">`. 

By using `ng-container` to hide `*ngFor` Structural Directives to avoid extra HTML element
```html
<div class="body">
  <ng-container *ngFor="let item of list; let odd = odd">
    <div class="content" *ngIf="odd">{{ item }}</div>
  </ng-container>
</div>

<!-- page actually displays -->
<div class="body">
    <div class="content" *ngIf="odd">{{ item }}</div>
</div>
```

