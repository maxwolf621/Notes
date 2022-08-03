# ng-container

- [ng-container](#ng-container)
  - [Binding Multiple Structural Directives `*ng..`](#binding-multiple-structural-directives-ng)
  - [Invisibility of ng-container for CSS](#invisibility-of-ng-container-for-css)
## Binding Multiple Structural Directives `*ng..`

`<ng-container ..>` helps us to bind multiple `*ng....`.

It causes error `Template parse error` without ng-container to bind multiple structural directives, for example
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
              Nested Condition for render
              <.. class = "body">
                <... class = "content">
                  ....
                </...>
              </..>
*/

.body > .content {
  color: red;
}
```
It might cause some problem, because there is a extra layer `<div *ngFor=...>...</div>` btw `<div class="body"> ... </div>` and `<div class="content"> ... </div>`

Using `ng-container` to hide `*ngFor` Structural Directives 
```html
<div class="body">
  <ng-container *ngFor="let item of list; let odd = odd">
    <div class="content" *ngIf="odd">{{ item }}</div>
  </ng-container>
</div>

<!-- what page is displayed -->
<div class="body">
    <div class="content" *ngIf="odd">{{ item }}</div>
</div>
```