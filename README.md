# Inhaltsverzeichnis

- [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [Reference](#reference)
  - [Typescript](#typescript)
  - [Kotlin](#kotlin)
  - [Angular](#angular)
    - [Basics](#basics)
    - [Bind data with Template, HTML and Component & CSS](#bind-data-with-template-html-and-component--css)
    - [Angular Reactive Forms](#angular-reactive-forms)
    - [Router](#router)
    - [Material](#material)
    - [RXJS](#rxjs)
  - [CSS && SASS](#css--sass)

## Reference
[AJAX](https://wcc723.github.io/development/2020/10/01/about-ajax-2/)   
[Frontend Framework](https://developer.mozilla.org/zh-TW/docs/Learn/Tools_and_testing/Client-side_JavaScript_frameworks/Introduction)
## Typescript
Properties of Typescript 
Type inference : types can be determined from the context, making type annotations optional.

[Typing objects](https://exploringjs.com/tackling-ts/ch_typing-objects.html)
[TypeScript: Static or Dynamic?](https://itnext.io/typescript-static-or-dynamic-64bceb50b93e)

- [Typescript Instruction](Typescript/TypescriptIntroduction.md)
- [Iterators and Generators](Typescript/iter.md)
- [Enums](Typescript/enums.md)
- [typeof](Typescript/typeof.md)
- [Types](Typescript/Types.md)
  - [Object Types](Typescript/Object%20Types.md)
  - [Special Types](Typescript/specialType.md) 
- [Function](Typescript/Function.md)
- [Class](Typescript/Class.md)
- [Module](Typescript/module.md)
- [NameSpace](Typescript/namespace.md)


## Kotlin

## Angular
### Basics
- [Install and Uninstall Angular](Angular/Install&UninstallAngular.md)  
- [Typescript In Angular](Typescript/TypeScriptAndAngular.md)
- [Angular CLI](AngularCLI.md)  
  - [CLI command](https://blog.poychang.net/note-angular-cli/)    
- [Root Module and Root Component](ApplicationActs.md)  
- [Component](Angular/Component.md)  
- [Module](Angular/ngModule.md)  
- **[Life hooks](Angular/lifeHooks.md)**  
### Bind data with Template, HTML and Component & CSS 
[angular.io Template Overview](https://angular.io/guide/template-overview)

- [DOM properties and HTML attributes](Angular/domPropHTMLattr.md) 
- **[Template Reference Variable](Angular/templateVariable.md)**
- [Template expression operators](Angular/templateOperator.md)
- **[ViewChild and ViewChildren](Angular/viewchild.md)**
- **[Bind](Angular/Binding.md)**
- **[Two Way Binding (`[(ngMOdel)]`, `@input` and `@Output`)](Angular/TwoWayBinding.md)**
- **[Structural Directives](Angular/Structural%20Directives.md)**  
  - [`ng-template`](Angular/ng-template.md)
  - [`ng-container`](Angular/ng-container.md)
  - [`ng-templateOutlet`](Angular/ngTemplateOutlet.md)

### Angular Reactive Forms
[Login Example](https://jasonwatmore.com/post/2020/07/18/angular-10-user-registration-and-login-example-tutorial)    

- **[ReactiveFormsModule](Angular/ReactiveFormsModule.md)** 
- **[Custom Form Validator](Angular/Validator.md)**
- [JWT](Angular/JWT.md)    

### Router

- **[Router](AngularRouter/Router.md)**   
- **[RouterForChild](AngularRouter/RouterForChild.md)**
- **[Route Guard](AngularRouter/Route_Guard.md)**  
- **[Router For Content](AngularRouter/RouterForContentLoading.md)** 

### Material 

**All the Material's class name is same as their material's name** for example `<img mat-card-image>` is `<img mat-card-image class="mat-card-image">`

- [button](Material/button.md)
- [mat-ripple]
- [side nav](Material/sidenav.md)
- [mat-list](Material/matlist.md)
- [menu](Material/menu.md)
- [grid-list](Material/gridlist.md)
- [mat-card](Material/matCard.md)
  ```html
   <mat-grid-tile>
    
    <!-- EACH mat-card setUp -->
    <mat-card>
        <mat-card-tile-group> or <mat-card-header>
            <mat-card-title></mat-card-title>
            <mat-subtitle></mat-subtitle>
            <img mat-card-avatar/>
        </mat-card-tile-group> or </mat-card-header>
        <img mat-card-image/>
          <mat-card-content></mat-card-content>
          <mat-card-actions>
            <button mat-button></button>
          </mat-card-actions>
    <mat-card-footer></mat-card-footer>
    </mat-card>
  </mat-grid-tile>
  ```
- [mat-progress](Material/matprogress.md)
- [tag](Material/tag.md)
- [mat-expansion-panel](Material/matexpansionpanel.md)
- [mat-tab-group](Material/matTabGroup.md)
- [CDK](Material/cdk.md)
### RXJS

`Reactive Programming = (Observer Pattern + Iterator pattern) + Functional Programming`   
[Observable = Observer Pattern + Iterator pattern](https://ithelp.ithome.com.tw/articles/10186832)

![圖 1](images/d5969d2ade5869a5374d925c1ec5b53c668f772778f7a54d999bb024032d61c3.png)  
- Create (`Observable` DATA)&Listen (OBSERVER `Subscribe`) **[Observable & Observer](RXJS/Observable&Observer.md)**  
![](images/c642d93d99c835876a635ba71e0a45f31703f47de9b8823f71445a5a00be8954.png)
- Combine (`Operator` OBSERVABLE) **[RXJS Operators](RXJS_Operators.md)** 
- **[AsyncPipe](RXJS/AsyncPipe.md)**
- **[behaviorSubject](RXJS/behaviorSubject.md)**
- [HttpClient.md](Angular/HttpClient.md)   
- **[Interceptor](Angular/Interceptor.md)**  


## CSS && SASS

[CSS Flexbox — 伸縮自如的排版|基礎觀念](https://reurl.cc/AORWkK)
[Day 5 : HTML - 網頁排版超強神器，CSS Flex到底是什麼？](https://ithelp.ithome.com.tw/articles/10267398)  
justify-content : justify-content是用來控制Flex「水平對齊」的位置，也就是主軸方向
以主軸的「起點」和「終點」來做依據
```css
  ustify-content:flex-start | flex-end | center | space-between | space-around | space-evenly;
```
[SCSS Cheatsheet](https://dev.to/finallynero/scss-cheatsheet-7g6)
- [SCSS usage](SCSS/scssUsage.md)

