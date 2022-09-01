# HostListener and HostBinding

[@HostBinding and @HostListener in Angular](https://www.tektutorialshub.com/angular/hostbinding-and-hostlistener-in-angular/)

- [HostListener and HostBinding](#hostlistener-and-hostbinding)
  - [Host Binding](#host-binding)
  - [HostListener](#hostlistener)
  - [Attatching Css Class](#attatching-css-class)

HostListener listens to host events, while HostBinding allows us to bind to a property of the host element.

This feature allows us to manipulate the host styles or take some action whenever the user performs some action on the host element via component/directive.


```html
<host-element directive></host-element>

<component-selector></component-selector>
```

```typescript
@HostBinding('directiveName') xxx : T;


```

## Host Binding

Host Binding binds a Host element property to a variable in the directive or component

```typescript
import { Directive, HostBinding, OnInit } from '@angular/core'
 
@Directive({
  selector: '[appHighLight]',
})
export class HighLightDirective implements OnInit {
 
  @HostBinding('style.border') border: string;
 
  ngOnInit() {
    this.border="5px solid blue"
  }
    
}
```

Apply appHighLight directive to a host element in `theNameOfTemplate.component.html`
```html
<div>
  <h2>appHighLight Directive</h2>
  <p appHighLight>
    This Text has blue Border
  </p>
</div>
```

## HostListener

HostListener Decorator listens to the DOM event on the host element


```typescript
import { Directive, HostBinding, OnInit, HostListener } from '@angular/core'
 
@Directive({
  selector: '[appHighLight]',
})
export class HighLightDirective implements OnInit {
    @HostBinding('style.border') border: string;
    
    ngOnInit() {
    }
    
    @HostListener('mouseover') 
    onMouseOver() {
        this.border = '5px solid green';
        console.log("Mouse over")
    }
    
    @HostListener('mouseleave') 
    onMouseLeave() {
        this.border = '';
        console.log("Mouse Leave")
    }
}
```

```html
<div>
  <h2>appHighLight Directive</h2>
  <!-- directive -->
  <p appHighLight>
    This Text has blue Border
  </p>
</div>
```


## Attatching Css Class 

```typescript
@Component({
  selector: 'app-box',
  template: `
    <h2> This is Box Component</h2> `,
  styles: [
    `
    .highlight {
      color:green;
      display: block;
    } 
    
    .box {
      border: 1px dashed green;
    }
    `
  ],
 
 
})
export class BoxComponent {
  title = 'hostBinding';
 
  @HostBinding('class.highlight') get hasHighlight() { return true; }
  @HostBinding('class.box') get hasBox() { return true }
}
```

add component in `xxx.component.ts` and `xxx.component.css`
```html
<app-box></app-box>
```

```css
.highlight {
  color:blue;
  display: block;
} 
 
.box {
  border: 1px solid red;
} 
```