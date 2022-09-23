# Inhaltsverzeichnis

- [AJAX](https://wcc723.github.io/development/2020/10/01/about-ajax-2/)   

- [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [Git & Linux](#git--linux)
  - [Java&Spring & DesignPattern](#javaspring--designpattern)
  - [Mysql](#mysql)
  - [LeetCode](#leetcode)
  - [Typescript](#typescript)
    - [Methods](#methods)
    - [Basics](#basics)
  - [Kotlin](#kotlin)
  - [Angular](#angular)
    - [Basics](#basics-1)
    - [Bind data with Template, HTML and Component & CSS](#bind-data-with-template-html-and-component--css)
    - [Directive](#directive)
    - [Angular Reactive Forms](#angular-reactive-forms)
    - [Router](#router)
    - [Material](#material)
  - [RXJS](#rxjs)
    - [Create](#create)
    - [Combine](#combine)
  - [CSS && SASS](#css--sass)
    - [CSS & SCSS cheatSheet](#css--scss-cheatsheet)


## Git & Linux
- [git](git/README.md)

- [Basic Shell Command](LinuxNote/BasicShellCommand.md)
- [Grep](LinuxNote/grep.md)
- [Basic Shell Script](LinuxNote/BasicShellScript.md)
- [Environment Path](LinuxNote/environmentPath.md)
## Java&Spring & DesignPattern

- [Java](JavaNote/README.md)
- [Spring](SpringNote/README.md)
- [Design Pattern Java](DesignPattern/README.md)

## Mysql

- [Database](database/README.md)

## LeetCode 

- [LeetCode Exercises](LeetCode/README.md)
## Typescript


[What is `new() :T`](https://reurl.cc/O4Anb9)   
[Exercises](https://exercism.org/tracks/typescript/exercises)   
[Cheat Sheet](https://rmolinamir.github.io/typescript-cheatsheet/)     
[TypeScript: Static or Dynamic?](https://itnext.io/typescript-static-or-dynamic-64bceb50b93e)      
[Do ✅ and Don't ❌ ](https://www.typescriptlang.org/docs/handbook/declaration-files/do-s-and-don-ts.html)  

### Methods
[Utility Types](https://www.typescriptlang.org/docs/handbook/utility-types.html#thisparametertypetype)  
[Template Literal Types](https://www.typescriptlang.org/docs/handbook/2/template-literal-types.html)
[Typing objects](https://exploringjs.com/tackling-ts/ch_typing-objects.html)     


### Basics

Type inference : types can be determined from the context, making type annotations optional.  
Function Type expression : `functionName : ( parameters ) => returnType`

[Typescript Exercises](Typescript/exercises.md)

- [Typescript Instruction](Typescript/TypescriptIntroduction.md)
- [Iterators and Generators](Typescript/iter.md)
- [Enums](Typescript/enums.md)
- [typeof & keyof](Typescript/typeof.md)
- [What is the difference between type and class in Typescript?](Typescript/classVStype.md)
- [Dictionary](Typescript/dictionary.md)
- [Generics](Typescript/Generic.md) 
- [Types](Typescript/Types.md)
  - [Object Types/Interface](Typescript/Object%20Types.md)
  - [Tuple](Typescript/tuple.md)
  - [Special Types](Typescript/specialType.md) 
  - [Indexed Access Types](Typescript/indexedAccessTypes.md)
  - [Conditional Types](Typescript/conditionalType.md)
  - [Mapped Types](Typescript/mappedTypes.md)
  - [Template Literal Type](Typescript/templateLiteralType.md)
- [Function](Typescript/Function.md)
- [Class](Typescript/Class.md)
  - [Static](Typescript/static.md) 
  - **[this](Typescript/this.md)**
- [Module](Typescript/module.md)
- [NameSpace](Typescript/namespace.md)
## Kotlin
[](kotlin.md)

[Kotlin Cheat Sheet (IMAGE)](https://blog.kotlin-academy.com/kotlin-cheat-sheet-1137588c75a)
[Day 07. Kotlin 語言學習 - Nullable 安全性](https://ithelp.ithome.com.tw/articles/10203705)    
[Collections](https://medium.com/mobile-app-development-publication/kotlin-collection-functions-cheat-sheet-975371a96c4b)   
- [References](kotlin/reference.md)
- [New](kotlin/new.md)
- [Class](kotlin/class.md)
- [Kotlin & Java](Kotlin/kotlinAndJava.md)
## Angular

[Tutorial](https://www.tektutorialshub.com/angular-tutorial/#pipes)

### Basics

- [Install and Uninstall Angular](Angular/Install&UninstallAngular.md)  
- [Angular CLI](AngularCLI.md)  
  - [CLI command](https://blog.poychang.net/note-angular-cli/)    
  ```bash
  ng g component componentName # prefix-componentName 
  ng g directive directiveName # prefix-directiveName
  ng g pipe my-new-pipe # 產生 Pipe 管道程式碼
  ng g service my-new-service #產生 Service 服務程式碼
  ng g class my-new-class #產生 Class 程式碼
  ng g interface my-new-interface #產生 Interface 介面程式碼
  ng g enum my-new-enum #產生 Enum 程式碼
  ng g module my-module #產生 Module 模組程式碼
  ng g guard my-guard #產生 Guard 守衛程式碼
  ng g app-shell [ --universal-app <universal-app-name>] [ --route <route>] 建立 App Shell
  ```
- [Root Module and Root Component](ApplicationActs.md)  
  ```typescript
  main.ts#platformBrowserDynamic().bootstrapModule(AppModule)
  '---AppModule#@NgModule.bootstrap[AppComponent]
    '---AppComponent#@Component.Selector:<app-root>
  ```
- [Component](Angular/Component.md)  
- [Module](Angular/ngModule.md)  
- [Providers Array And Dependency Injection](Angular/DependencyInjection.md)
- [Life hooks](Angular/lifeHooks.md)
### Bind data with Template, HTML and Component & CSS 
- [DOM properties and HTML attributes](Angular/domPropHTMLattr.md) 
- [Template Reference Variable(`#`)](Angular/templateVariable.md)
- [Template expression operators](Angular/templateOperator.md)
- [Bind](Angular/Binding.md)
- [Two Way Binding (`[(ngMOdel)]`, `@input` and `@Output`)](Angular/TwoWayBinding.md)
  ```typescript
  <childComponent [input] = "value" (outputEvent) = getEvent($Event)></childComponent>>
  ```
### Directive
- [Angular Directive](Angular/directive.md)
- [Custom Directive](Angular/customDirective.md)
- [ElementRef & TemplateRef & ViewRef & ViewContainerRef](Angular/ElementRef.md)
- [Renderer2(Proxy of ElementRef)](Angular/Renderer2.md)
- [HostBinding and HostListener](Angular/Host.md)   


`<ng-content>` : Child Template 內的`<ng-content></ng-content>` = Parent Template要嵌入的地方      
`@ViewChild` : 從View裡取得特定Component/Template variable Reference的實體給Component用        
`@ContentChild` : Child Component 可以操作`<ng-content></ng-content>` Parent Template嵌入的元素    
- [ViewChild and ViewChildren](Angular/viewchild.md)
- [ContentChild and ContentChildren](Angular/contentChild.md)
- [Structural Directives](Angular/Structural%20Directives.md)
  - [`ng-Content`](Angular/ngContent.md)
  - [`ng-template` with TemplateRef & ViewContainerRef](Angular/ng-template.md)
  - [`ng-container`](Angular/ng-container.md)
  - [`ng-templateOutlet`](Angular/ngTemplateOutlet.md)
### Angular Reactive Forms

- [Login Example](https://jasonwatmore.com/post/2020/07/18/angular-10-user-registration-and-login-example-tutorial)    
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
## RXJS

- [Functional Programming](functionalProgram.md)

`Reactive Programming = (Observer Pattern + Iterator pattern) + Functional Programming`   
[Observable = Observer Pattern + Iterator pattern](https://ithelp.ithome.com.tw/articles/10186832)

![圖 1](images/d5969d2ade5869a5374d925c1ec5b53c668f772778f7a54d999bb024032d61c3.png)  

### Create 
![](images/c642d93d99c835876a635ba71e0a45f31703f47de9b8823f71445a5a00be8954.png)
**[Observable & Observer](RXJS/Observable&Observer.md)**  
### Combine
- **[RXJS Operators](RXJS/RXJS_Operators.md)**
- [time](RXJS/delay.md)
- [filter](RXJS/filter.md)
- [Subject](RXJS/subject.md)
- [Error](RXJS/Error.md)
- **[AsyncPipe](RXJS/AsyncPipe.md)**
- **[behaviorSubject](RXJS/behaviorSubject.md)**

- [HttpClient.md](Angular/HttpClient.md)   
  ```typescript
  this.httpClient.get(BackendURL).pipe(
    tap( ... ),
    retry(...),
    catchError(...),
    finalize(... )
  )
  ```
- **[Interceptor](Angular/Interceptor.md)**  


## CSS && SASS

- [CSS POSITION](SCSS/position.md)
- [CSS PADDING AND MARGIN](SCSS/boxModel.md)
- [CSS BOARD/BOX SIZE](SCSS/board.md)
- [CSS OVERFLOW&FLOW](SCSS/overflow&FLOW.md)
- [CSS Grid](SCSS/grid.md)
- [CSS FLEX vs Angular Flex-Layout](SCSS/flex.md)
- [SELECTORS](SCSS/selector.md)
- [Display](https://reurl.cc/V1Y8EY)
- [SCSS Notes](SCSS/scssUsage.md)
  
### CSS & SCSS cheatSheet
- [CSS Cheat Sheet](https://reurl.cc/W10D1y)   
- [CSS quickReview](https://reurl.cc/MN3RNL)  
- [SCSS Cheat Sheet](https://dev.to/finallynero/scss-cheatsheet-7g6)
