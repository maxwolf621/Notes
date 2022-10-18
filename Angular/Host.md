# HostListener and HostBinding
- [HostListener and HostBinding](#hostlistener-and-hostbinding)
  - [CSS properties directive](#css-properties-directive)
  - [HostBinding Component](#hostbinding-component)

- `HostBinding` binds a Host element property to a property in the directive or component
- `HostListener` Decorator listens to the DOM event on the host element

[Code Example](https://reurl.cc/YXNbba)
[Code Example](https://angular-hu4bvx.stackblitz.io)

## CSS properties directive

Bind css properties in some components' view Via `@HostBinding` Directive
```typescript
@Directive({
  selector: '[app-pstyle]',
})
export class PstyleComponent implements OnInit {
  @HostBinding('style.background-color') bgColor: String;
  @HostBinding('style.border') border: String;

  @HostListener('mouseover')
  onMouseOver() {
    this.border = '5px solid red';
    this.bgColor = 'red';
    console.log('Mouse over');
  }

  @HostListener('mouseleave')
  onMouseLeave() {
    this.border = '';
    this.bgColor = '';
    console.log('Mouse Leave');
  }
```

`PstyleComponent` is now a directive that supports CSS properties
```html
<p app-pstyle>This is style.property binding</p>
```

## HostBinding Component 

```typescript
@Component({
  selector: 'hello',
  template: `<h1>Hello {{name}}!</h1>
  <button (click)='activate()'>Activate</button>`,
  styles: [
    `
   h1 { font-family: montserrat;}
   `,
  ],
})
export class HelloComponent {
  @Input() name: string;

  toggle: boolean;

  @HostBinding('class.isActive') get t() {
    // getter of toggle
    return this.toggle;
  }

  activate() {
    this.toggle = !this.toggle;
    //console.log(this.toggle);
  }
}
```



