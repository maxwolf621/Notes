# ngContent

- [ngContent](#ngcontent)
    - [with `select` attribute](#with-select-attribute)

Allow Parent to manipulate view of Child.

Child
```typescript
import { Component } from '@angular/core';

@Component({
    selector: 'exe-greet',
    template: `
    <div class="border"> 
        <h1>I'm Child</h1>
        <ng-content></ng-content> 
    </div>
    `,
})
export class GreetComponent { }
```
Parent
```typescript
import { Component } from '@angular/core';

@Component({
  selector: 'my-app',
  template: `
    <h1>I'm Parent</h1>
    <exe-greet>
      <p>A new View Is Rendered For Child Via Parent</p>  <!--HTML element from Parent -->
    </exe-greet>
  `,
})
export class AppComponent { }
```

HTML will look like this
```html
<h1>I'm Parent</h1>
<div class="border"> 
    <h1>I'm Child</h1>
    <p>A new View Is Rendered For Child Via Parent</p>  <!--HTML element from Parent -->
</div>
```


### with `select` attribute

```typescript
<ng-content select= #referenceValue | "cssClass" | "HostElementName" >
```


```typescript
import { Component } from '@angular/core';

@Component({
    selector: 'exe-greet',
    template: `
    <div class="border">
        <p>Greet Component</p>  
        <div>
            <div>
                <ng-content select="header"></ng-content> 
            </div>
        
            <div>
                <ng-content select=".card_body"></ng-content> 
            </div>
        
            <div>
                <ng-content select="footer"></ng-content> 
            </div>
       </div>
    </div>
    `,
})
export class GreetComponent{ }
```

```typescript
import { Component } from '@angular/core';
@Component({
  selector: 'my-app',
  template: `
    <h1>I'm Parent</h1>
    <exe-greet>
      <header>Card Header</header>
      <div class="card_body">Card Body</div>
      <footer>Card Footer</footer>
    </exe-greet>
  `,
})
export class AppComponent { }
```

HTML looks like this
```html
<h1>I'm Parent</h1>
<div class="border">
    <p>Greet Component</p>  
    <div>
        <div>
            <header>Card Header</header>
        </div>
        <div>
            <div class="card_body">Card Body</div>
        </div>
        <div>
            <footer>Card Footer</footer>
        </div>
   </div>
</div>
```