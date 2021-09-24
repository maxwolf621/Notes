# ng-container


What for? 

For example :: if we got `*ngFor` and `*ngIf` in the same line. it causes error `Template parse error`
```html
<ul>
  <li *ngFor="let item of list; let odd = odd" * ngIf="odd">
    {{ item }}
  </li>
</ul>
```

What if there is a way to show if item is odd and come in visible when the item is not?   
that is what `ng-container` about

The power of `<ng-container ..>` is it help us to do multiple `*ngXX` template binding and hides itself.
```html
<ul>
  <ng-container *ngFor="let item of list; let odd = odd">
    <li *ngIf="odd">{{ item }}</li>
  </ng-container>
</ul>
```
Now it only shows up `<li>{{item}}</li>` while the `item` is `odd`      
and `ng-container` will also hide itself    


Another advance is css-render    
for example    
```html
<div class="body">
  <div *ngFor="let item of list; let odd = odd">
    <div class="content" *ngIf="odd">{{ item }}</div>
  </div>
</div>
```
For rendering it by CSS we have to know how many `class="content"` was wrapped start from `class="body"`.   
with `ng-container` it simply solves such problem.

