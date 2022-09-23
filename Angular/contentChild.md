# ContentChild & ContentChildren

[Reference](https://www.tektutorialshub.com/angular/contentchild-and-contentchildren-in-angular/)

- [ContentChild & ContentChildren](#contentchild--contentchildren)
    - [Read Token](#read-token)
  - [In Action](#in-action)

Use to get the first ELEMENT or the DIRECTIVE matching the selector from the **content DOM**.    
If the **content DOM** changes, and a new child matches the selector, the PROPERTY will be updated.   

Syntax
```typescript
@ContentChild(selector: string | Function | Type<any>, 
             opts: { read?: any; static: boolean; }): any
```
### Read Token

```html
<input #nameInput [(ngModel)]="name">
```
The following code returns the `input` element as `elementRef`
```typescript
@ContentChild('nameInput',{static:false}) nameVar;
```

Make use of the read token, to ask ContentChild to return the correct type Instead
```typescript
@ContentChild('nameInput',{static:false, read: NgModel}) nameVarAsNgModel;
@ContentChild('nameInput',{static:false, read: ElementRef}) nameVarAsElementRef;
@ContentChild('nameInput', {static:false, read: ViewContainerRef }) nameVarAsViewContainerRef;
```

## In Action

```typescript
@Component({
  selector: 'card',
  template: `
    <div class="card">
      <ng-content select="header"></ng-content>
      <ng-content select="content"></ng-content>
      <ng-content select="footer"></ng-content>
    </div>  
  `,
  styles: [
    ` .card { min- width: 280px;  margin: 5px;  float:left  } 
    `
  ]
})
export class CardComponent {

  @ContentChild("header") cardContentHeader: ElementRef;
  @ViewChild("header") cardViewHeader: ElementRef;

  constructor(private renderor:Renderer2) {
    console.log("CardComponent ->constructor "+this.cardContentHeader)
  }

  ngOnChanges() {
    //first time returns undefine 
    console.log("CardComponent ->ngOnChanges "+this.cardContentHeader)
  }

  ngOnInit() {
    //returns undefine
    console.log("CardComponent ->ngOnInit "+this.cardContentHeader)
  }

  ngDoCheck() {
    //first time returns undefined 
    console.log("CardComponent ->ngDoCheck "+this.cardContentHeader)
  }
  
  ngAfterContentInit() {
    //cardContentHeader is available here
    console.log("CardComponent ->ngAfterContentInit-contentHeader "+this.cardContentHeader)

    this.renderor.setStyle(this.cardContentHeader.nativeElement,"font-size","20px")

    //this.cardContentHeader.nativeElement.innerHTML="<h1>Test</h1>"
  }

  ngAfterContentChecked() {
    //cardContentHeader is available here
    console.log("CardComponent ->ngAfterContentInit-contentHeader "+this.cardContentHeader)
  }

  ngAfterViewInit() {
    console.log("CardComponent ->ngAfterViewInit-viewHeader "+this.cardViewHeader)
  }

  ngAfterViewChecked() {
    console.log("CardComponent ->ngAfterViewChecked-viewHeader "+this.cardViewHeader)
  }
}
```

```typescript
@Component({
  selector: 'card-list',
  template: `
  
  <h1> Card List</h1>

      <card>
        <header #header><h1>Angular</h1></header>
        <content>One framework. Mobile & desktop.</content>
        <footer><b>Super-powered by Google </b></footer>
      </card>
        
      <card>
        <header #header><h1 style="color:red;">React</h1></header>
        <content>A JavaScript library for building user interfaces</content>
        <footer><b>Facebook Open Source </b></footer>
      </card>

      <card>
        <header #header> <h1>Typescript</h1> </header>
        <content><a href="https://www.tektutorialshub.com/typescript-tutorial/"> Typescript</a> is a javascript for any scale</content>
        <footer><i>Microsoft </i></footer>
      </card>

  `,
})
export class CardListComponent {
}
```