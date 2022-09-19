# Directive

[ngStyle](https://www.tektutorialshub.com/angular/angular-ngstyle-directive/)  
[ngClass](https://www.tektutorialshub.com/angular/angular-ngclass-directive/)  

- [Directive](#directive)
  - [Component Directive](#component-directive)
  - [Structural Directive(`*`)](#structural-directive)
  - [Attribute directives](#attribute-directives)
    - [ngClass](#ngclass)
      - [Static Css Class Binding](#static-css-class-binding)
      - [Dynamical Css Binding](#dynamical-css-binding)
    - [ngStyle & ngModel](#ngstyle--ngmodel)

**The Angular directive helps us to manipulate(change) the appearance, behavior, or layout of a DOM element using the Directives.**

There are two way to bind html element's properties :   
- Dynamical Attribute Binding : Bind html element's properties to properties in Component.  
- Static Attribute Binding : Bind html element's properties with string values directly


## Component Directive 

- [Examples](https://angular.io/api/core/Directive)

```typescript
@Component({
  // ....
})
export class component{
  //...
}

@Directive({
  standalone: true,
  selector: 'my-directive',
})
class MyDirective {}
```
- Angular directives marked as standalone do not need to be declared in an NgModule

## Structural Directive(`*`)

**All structural Directives are preceded by Asterisk(`*`) symbol**

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

`*ngIf`
```html
<div *ngIf="condition"> 
  ....
</div>
```

`[ngSwitch]="Switch_Expr"`   
`ngSwitchCase="matchExpr"`   
`ngSwitchDefault?`   
```html
<div [ngSwitch]="Switch_Expression"> 
    <div *ngSwitchCase="MatchExpression1">First Template</div>
    <div *ngSwitchCase="MatchExpression2">Second template</div> 
    <div *ngSwitchDefault?>Default Template</div>
</div>
```


## Attribute directives

**An Attribute or style directive can change the appearance or behavior of an HTML element.**

Static Binding : Assign String Type Value directly to HTML Element    
Dynamical Binding : Bind properties in HTML Element to values that can be updated by the user or our components.   

### ngClass

Using the `ngClass` to create CSS dynamic/static styles in HTML pages, for example 
```css
.red { color: red; }
.size20 { font-size: 20px; }
```

#### Static Css Class Binding

String : `[ngClass] = "'class1 class2 ... classN'"`
```html
<div [ngClass]="'red size20'"> Red Text with Size 20px </div>
```

String : `[ngClass] = 'class1 class2 ... classN'`
```html
<div class="row">     
    <div ngClass='red size20'>Red Text with Size 20px </div> 
</div>
```

Array : `[ngClass] = "['cssClass1', 'cssClass2']"`
```html
<div [ngClass]="['red','size20']">Red Text with Size 20px </div>
```

Object Type : `[ngClass] = "{'cssClass1': true, 'cssClass2': true}"`
```html
<div class="row">     
  <div [ngClass]="{'red':true,'size20':true}">Red Text with Size 20px</div>
</div>
```

#### Dynamical Css Binding
```typescript
// string 
cssStringVar: string  = 'red size20';

// array
cssArray    : string[] =['red','size20']; 

// object
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

### ngStyle & ngModel

**`ngStyle` is used to change the multiple CSS style properties of our HTML elements.**


Bind string type values to HTML element statically.
```html
<div [ngStyle]="{'background-color':status === 'error' ? 'red' : 'blue' }"></<div>

<p [ngStyle]="{'color': 'purple',
               'font-size': '20px',
               'font-weight': 'bold'}">
     Multiple styles
</p>
```


Bind property with ngStyle and NgModel dynamically.
```html
<input [(ngModel)]="color" /> 

<div [ngStyle]="{'color': color}">Change my color</div>
```
 

```typescript
Class StyleClass {
    'color' : string = 'blue';
    'font-size-px' : number = 10;
    'font-weight' : string = 'bold';
}
styleClass: StyleClass = new StyleClass();
```
```html
<div [ngStyle]="styleClass">size & Color</div>
```