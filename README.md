# Inhaltsverzeichnis

- [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [Typescript](#typescript)
  - [Angular Basics](#angular-basics)
    - [Bind data with Template, HTML and Component & CSS](#bind-data-with-template-html-and-component--css)
    - [Angular Reactive Forms](#angular-reactive-forms)
  - [Router](#router)
  - [Material](#material)
  - [RXJS](#rxjs)
  - [ERROR](#error)
  - [Reference](#reference)

## Typescript

- [Iterators and Generators](iter.md)
- [Enums](enums.md)
- [typeof](typeof.md)

- [Types](Typescript/Types.md)
  - [Object Types](Typescript/Object%20Types.md)
  - [Special Types](specialType.md) 
- [Function](Typescript/Function.md)
- [Class](Typescript/Class.md)
- [Module](module.md)
- [NameSpace](namespace.md)
- [Typescript Questions](tyInterview.md)

## Angular Basics

- [Install and Uninstall Angular](Install&UninstallAngular.md)  
- [Typescript In Angular](Typescript/TypeScriptAndAngular.md)
- [Angular CLI](AngularCLI.md)  
  - [CLI command](https://blog.poychang.net/note-angular-cli/)    
- [Root Module and Root Component](ApplicationActs.md)  
- [Component](Component.md)  
- [Module](ngModule.md)  
- **[Life hooks](lifeHooks.md)**  
### Bind data with Template, HTML and Component & CSS 

[angular.io Template Overview](https://angular.io/guide/template-overview)

- [DOM properties and HTML attributes](domPropHTMLattr.md) 
- **[Template Reference Variable](hashtag.md)**
- [Template expression operators](templateOperator.md)
- **[ViewChild and ViewChildren](viewchild.md)**
- **[Bind](Binding.md)**
- **[Two Way Binding (`[(ngMOdel)]`, `@input` and `@Output`)](TwoWayBinding.md)**
- **[Structural Directives](Structural%20Directives.md)**  
  - [`ng-template`](ng-template.md)
  - [`ng-container`](ng-container.md)
  - [`ng-templateOutlet`](ngTemplateOutlet.md)

### Angular Reactive Forms
- **[ReactiveFormsModule](ReactiveFormsModule.md)** 
- **[Custom Form Validator](Validator.md)**
- [JWT](JWT.md)    
- [Login Example](https://jasonwatmore.com/post/2020/07/18/angular-10-user-registration-and-login-example-tutorial)    


## Router

- **[Router](Router.md)**   
- **[RouterForChild](RouterForChild.md)**
- **[Route Guard](Route_Guard.md)**  
- **[Router For Content](RouterForContentLoading.md)** 

## Material 

- [CSS Flexbox — 伸縮自如的排版|基礎觀念](https://reurl.cc/YXjDnl)

**All the Material's class name is same as their material's name** for example `<img mat-card-image>` is `<img mat-card-image class="mat-card-image">`

- [button](button.md)
- [mat-ripple]
- [side nav](sidenav.md)
- [mat-list](matlist.md)
- [menu](menu.md)
- [grid-list](gridlist.md)
- [mat-card](car.md)
- [mat-progress](matprogress.md)
- [tag](tag.md)
- [mat-expansion-panel](matexpansionpanel.md)
- [mat-tab-group](matTabGroup.md)
- [CDK](cdk.md)
## RXJS

`Reactive Programming = Observer Pattern + Iterator pattern + Functional Programming`

![圖 1](images/d5969d2ade5869a5374d925c1ec5b53c668f772778f7a54d999bb024032d61c3.png)  
- Create (`Observable` DATA)&Listen (OBSERVER `Subscribe`) **[Observable & Observer](Observable&Observer.md)** 
- Combine (`Operator` OBSERVABLE) **[RXJS Operators](RXJS_Operators.md)** 
- **[AsyncPipe](AsyncPipe.md)**
- **[behaviorSubject](behaviorSubject.md)**
- [HttpClient.md](HttpClient.md)   
- **[Interceptor](Interceptor.md)**  

## ERROR 
[`An unhandled exception occurred: Cannot find module '@angular-devkit/build-angular/package.json'`](https://reurl.cc/d270nV)

## Reference
[AJAX](https://wcc723.github.io/development/2020/10/01/about-ajax-2/)   
[Frontend Framework](https://developer.mozilla.org/zh-TW/docs/Learn/Tools_and_testing/Client-side_JavaScript_frameworks/Introduction)