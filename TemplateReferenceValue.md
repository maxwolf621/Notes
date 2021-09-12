###### tags: `Angular`
# Template Reference Value

To pass input text via button to component's `.ts` file
```html
<h2>
    {{title}}
</h2>
<input #passVal type = "text">
<button (click) = "showConsoleLog(passVal)"
```

```typescript
//..
export class example implement OnInit{
    public title = "Template Reference Value"
    constructor(){}
    
    ngOnInit(){
        
    }
    
    showConsoleLog(passVal){
        //display passVal in the console
        console.log(passVal)
    }
}
```