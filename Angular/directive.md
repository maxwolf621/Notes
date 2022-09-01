# Directive

[ngStyle](https://www.tektutorialshub.com/angular/angular-ngstyle-directive/)  
[ngClass](https://www.tektutorialshub.com/angular/angular-ngclass-directive/)  

- [Directive](#directive)
  - [Component Directive](#component-directive)
  - [Structural directives](#structural-directives)
  - [Attribute directives](#attribute-directives)
    - [ngModel](#ngmodel)
    - [ngClass](#ngclass)
      - [Static Css Class Binding](#static-css-class-binding)
      - [Dynamical Css Binding](#dynamical-css-binding)
      - [ngStyle](#ngstyle)

The Angular directive helps us to manipulate the DOM. 
You can change the appearance, behavior, or layout of a DOM element using the Directives. 

## Component Directive 

[Component Directive Tutorial](https://www.tektutorialshub.com/angular/angular-component/)
```bash
app.component.css
app.component.ts
app.component.html
```

## Structural directives   

All structural Directives are preceded by Asterisk(`*`) symbol


`*ngFor = "let x of Xs;"`
```html
<tr *ngFor="let customer of customers;">
    <td>{{customer.customerNo}}</td>
    <td>{{customer.name}}</td>
    <td>{{customer.address}}</td>
    <td>{{customer.city}}</td>
    <td>{{customer.state}}</td>
</tr>
```


`[ngSwitch]="Switch_Expr"`
`ngSwitchCase="matchExpr"`
`ngSwitchDefault?`
```html
<div [ngSwitch]="Switch_Expression"> 
    <div *ngSwitchCase="MatchExpression1"> First Template</div>
    <div *ngSwitchCase="MatchExpression2">Second template</div> 
    <div *ngSwitchDefault?>Default Template</div>
</div>
```

`*ngIf`
```html
<div *ngIf="condition"> 
    ...
</div>
```


## Attribute directives

An Attribute or style directive can change the appearance or behavior of an element.

### ngModel

### ngClass

Using the `ngClass` can create CSS dynamic/static styles in HTML pages, for example binding css to
```css
.red { color: red; }
.size20 { font-size: 20px; }
```

#### Static Css Class Binding

Css Class String binding `"'class1 class2'"`
```html
<div [ngClass]="'red size20'"> Red Text with Size 20px </div>


<div class="row">     
    <div ngClass='red size20'>Red Text with Size 20px </div> 
</div>
```

Css Class Array Binding `"['cssClass1', 'cssClass2']"`
```html
<div [ngClass]="['red','size20']">Red Text with Size 20px </div>
```

Css Class Object Binding `"{'cssClass1': true, 'cssClass2': true}"`
```html
<div class="row">     
  <div [ngClass]="{'red':true,'size20':true}">Red Text with Size 20px</div>
</div>
```

#### Dynamical Css Binding

```typescript
cssStringVar: string= 'red size20';

cssArray:string[]=['red','size20']; 

class CssClass {
  red: boolean= true;
  size20: boolean= true; 
}
cssClass: CssClass = new CssClass();
```
```html
<div class="row">     
    <div [ngClass]="cssStringVar">
        ....
    </div> 
</div>

<div class="row">
  <div [ngClass]="cssArray">
    ...
  </div>
</div>

<div class="row">     
  <div [ngClass]="cssClass"> 
    ...
  </div> 
</div>
```

#### ngStyle

ngStyle is used to change the multiple style properties of our HTML elements.

We can also bind these properties to values that can be updated by the user or our components.


```html
<div [ngStyle]="{'background-color':status === 'error' ? 'red' : 'blue' }"></<div>

<p [ngStyle]="{'color': 'purple',
               'font-size': '20px',
               'font-weight': 'bold'}">
     Multiple styles
</p>
```


Bind property with ngStyle and NgModel dynamically
```html
<!--
    color is property from component
    color: string= 'red';   
-->

<input [(ngModel)]="color" /> 

<div [ngStyle]="{'color': color}">Change my color</div>
```

Bind Object with ngStyle
```typescript
Class object {
    'color' : string = 'blue';
    'font-size-px' : number = 10;
    'font-weight' : string = 'bold';
}

styleClass: StyleClass = new StyleClass();

// bind with ngStyle
<div [ngStyle]="styleClass">size & Color</div>
```