# Template Variable 

hashtag is a syntax used to declare DOM element as variable and these templates render as an HTML file.

- [Template Variable](#template-variable)
  - [Reference](#reference)
  - [Angular Symbol Review](#angular-symbol-review)
  - [How Angular assigns values to template variables](#how-angular-assigns-values-to-template-variables)
  - [Variable specifying a name](#variable-specifying-a-name)
  - [Template variable scope](#template-variable-scope)
  - [`<ng-template>` Template input variable](#ng-template-template-input-variable)

## Reference

- [Template variables](https://ithelp.ithome.com.tw/articles/10261389?sc=pt)

## Angular Symbol Review
```typescript
#     variable declaration
()    event binding
[]    property binding
[()]  two-way property binding
{{}}  interpolation
```

In the template, you use the hash symbol, `#`, to declare a template variable.  for example
```html
<input #phone placeholder="phone number" />

<!-- lots of other elements -->

<!-- phone refers to the input element; pass its `value` to an event handler -->
<button type="button" (click)="callPhone(phone.value)">Call</button>
```

## How Angular assigns values to template variables

- If you declare the variable on a component, the variable refers to the component instance.
- If you declare the variable on a standard HTML tag (`e.g. <tag #templateVariable>`), the variable refers to the element.
- If you declare the variable on an `<ng-template>` element, the variable refers to a `TemplateRef` instance which represents the template.

## Variable specifying a name

If the variable specifies a name on the right-hand side, such as `#var="ngModel"`, the variable refers to the directive or component on the element with a matching `exportAs` name.

## Template variable scope

**Structural directives such as `*ngIf` and `*ngFor`, or `<ng-template>` declarations create a new nested template scope**

An inner template can access template variables that the outer template defines.
```html
<input #ref1 type="text" [(ngModel)]="firstExample" /> <!-- parent -->
<span *ngIf="true">Value: {{ ref1.value }}</span> <!-- child-->
```


`ref2` is declared in the child scope created by `*ngIf`, and is not accessible from the parent template.
```html
<input *ngIf="true" #ref2 type="text" [(ngModel)]="secondExample" /> <!-- child -->
<span>Value: {{ ref2?.value }}</span> <!-- parent -->
```

## `<ng-template>` Template input variable
syntax
```html
<ng-template let-xxx = "value">
    <!-- xxx is used-->
</ng-template>
```
- When an `<ng-template>` is instantiated, multiple named values can be passed which can be bound to different template input variables.
- The right-hand side of the `let-` declaration of an input variable can specify which value should be used for that (passed)variable.

```html
<ul>
  <ng-template ngFor let-hero [ngForOf]="heroes">
    <li>{{hero.name}}
  </ng-template>
</ul>
```
- The `NgFor` directive will instantiate this once for each hero in the heroes array, and will set the hero variable for each instance accordingly.

`NgFor` for example also provides access to the index of each hero in the array:
```html
<ul>
  <ng-template ngFor let-hero let-i="index" [ngForOf]="heroes">
    <li>Hero number {{i}}: {{hero.name}}
  </ng-template>
</ul>
```