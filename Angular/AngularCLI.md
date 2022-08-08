###### tags: `Angular`
# Basic Angular CLI   

- [Basic Angular CLI](#basic-angular-cli)
  - [Reference](#reference)
  - [`ng [new|n]` A project](#ng-newn-a-project)
    - [Dependency Tree Problem](#dependency-tree-problem)
  - [project's `src` directory](#projects-src-directory)
    - [`package-lock.json`](#package-lockjson)
    - [`angular.json`](#angularjson)
    - [`tsconfig.json`](#tsconfigjson)
      - [POTENTIAL PROBLEM](#potential-problem)
    - [Page and Template for Angular](#page-and-template-for-angular)
  - [Router](#router)
  - [Generate a `enum` type](#generate-a-enum-type)
  - [`test` the Application](#test-the-application)
  - [`e2e` test](#e2e-test)
  - [Angular Package Management](#angular-package-management)
  - [ng serve](#ng-serve)
  - [Generate Component](#generate-component)
  - [Compile the Application to `ECMAScript`](#compile-the-application-to-ecmascript)
  - [Deploy our Application](#deploy-our-application)
  - [Install dependencies](#install-dependencies)
    - [update packages](#update-packages)
  - [Linting](#linting)

## Reference
- [basic Setting in CLI](https://ithelp.ithome.com.tw/articles/10238044)  

##  `ng [new|n]` A project
```bash
ng new <project> [params]

# or `n` instead of `new`
ng n <project> [params]
```
- parameter `--help` to get more info for command `new` or `n`
- `--createApplication=false` 可以建立一個Empty的 Angular WorkSpace，再依照需求建立應用程式 (application) 或函式庫 (library)。
- 若想要透過如`yarn`等其他套件管理工具來安裝所需的套件，可以利用 `--packageManager` 參數指定。
- `--skipInstall=false` 取消套件的安裝
- `--skipGit=false` and `--commit=false` 取消Git版本control與commit。

### Dependency Tree Problem
[Solution](https://stackoverflow.com/questions/64573177/unable-to-resolve-dependency-tree-error-when-installing-npm-packages)  

Dependency Tree Error while `ng new <project>`   
```bash
cd project
npm install --legacy-peer-deps
```

## project's `src` directory 

after `new` a WorksSpace of Angular, it will create a root directory `src` 
> All dependencies (files, pictures ... etc) will be stored under the `src/`  

```typescript 
target
e2e
src
 +----- app
        +------ 
 +----- assets
 +----- environments
 +----- styles.scss
 +----- main.ts
 +----- index.html
 +----- favicon.ico
_.css
_.html
angular.json
package-lock.json
package.json
tslint.json
tsconfig.json
...
....
```

### `package-lock.json`

紀錄Application的基本資訊，例如Application的Name、Version，還有Application會使用到的相依套件(Dependencies)等等資訊  

When `npm install` is executed , then it will look up `package-lock.json` to install the packages that `package-lock.json` lists  


### `angular.json`
To set up **configurations** of the angular application

For example a work-space of angular application would have multiple projects files
- `newProjectRoot` attribute indicates where the new files will be stored at 
- `defaultProject` attribute 當執行Angular CLI Command時(`ng serve`)，若不指定Application(Our Project)，就會依此處設定的Application為執行對象。


### `tsconfig.json`

將Typescript編譯成Javascript時的編譯設定
#### POTENTIAL PROBLEM  
一般開發網頁程式為了減少程式檔案大小，來降低程式傳輸至客戶端的時間，常常會將 JavaScript 進行程式碼壓縮；但也因此讓程式人員無法在系統錯誤時，直接知道哪一行程式拋出的錯誤訊息  

所以我們可以設定`SourceMap : true`即用來記錄程式碼在壓縮前後位置對應的檔案，讓程式人員可以更方便地除錯  
```json
{
  "compileOnSave": true,
  "compilerOptions": {
    "module": "system",
    "target": "es5",
    "noImplicitAny": true,
    "removeComments": true,
    "preserveConstEnums": true, // 單行註解
    "outFile": "./dist/app-build.js", // 全部整合在這一隻 js
    "sourceMap": true
  }
  /**
   * ......
   */
}
```
- [More Details](https://yiyingloveart.blogspot.com/2016/07/typescript-tsconfigjson.html)   

### Page and Template for Angular
**Angular以元件(Component)來開發單位**，封裝了應用程式的頁面、樣式與邏輯，而預設上 Angular 元件會將這三部份拆分至 HTML、CSS 與 TypeScript 三種檔案之內  

- `--inlineTemplate=true` 與 `--inlineStyle=true`可以讓頁面與樣式都放在`ts`檔案中  
- Angular supports Template CSS by default, but we can use `--style` set template as SCSS、SASS、LESS or stylus files  
- Angular CLI 的互動模式（`--interactive=true`）下，若未指定此參數時，Angular CLI 會在執行命令前進行詢問  
- Static files will stored under `assets/`  
- Define global template in the `style.css` or `style.scss`

## Router

Angular is Single-Page Application **(SPA)**  

- Swap web pages by `Routing`  

using `--routing=true` to create route module   

- 單一頁面應用程式 (Single-Page Application, SPA) 不同於早期網頁程式，整個應用程式只有一個頁面，透過`Ajax`與伺服器溝通並更新部分網頁資料  

## Generate a `enum` type 

```bash
ng g enum directory/enum_name
```

For example 
```bash
ng g enum file/task-state 
# or 
ng g e enum/task-state
```

Use `enum` in a component  
```typescript
import { TaskState } from "../enum/task-state.enum";

export class Task{
    constructor(
        // string subject
        public subject : string,
        // TaskState state by default
        public state : TaskState = TaskState.None
    ){}
}
```

## `test` the Application

```bash
# the following cli command
# it will look up `test.ts` 
ng test <project > [parameter]
ng t <project> [parameter]
```
- For testing components validity, Angular will create correspond testing packaged while creating a workspace.
- By default testing (specification) files format is  `.spec.ts`

we also can use 
1. `--minimal = true` to disable the default testing framework, and manually create desire testing framework by yourself   
2. `--skipTests=false` to disable creating new spec file while creating new component   

## `e2e` test

End To End Testing
```bash
ng e2e <Project> [Parameter]
ng e <Project> [Parameter]
```

## Angular Package Management 

```bash
# add the package (module)
np add <Name_Of_Package> [Parameter]

# for example
ng add @angular/material

# update the package
np update @angular/material
```
## ng serve

- [Dynamically Boost up Angular Application](https://www.howtoing.com/install-angular-cli-on-linux)    
- [More Cli](https://ithelp.ithome.com.tw/articles/10238044)   

To open Application (e.g Web Application ...etc )  
```bash
ng serve <Name_Of_Project> [parameter]
# or serve with s
ng s <Name_Of_Project> [parameter]

# In Application Directory
ng serve 
ng s 
```
- According to the setting of `angular.json` to boost application dynamically 
- Via default path `http://localhost:4200` to browser websites

We also can use
1. using `--host` or `--port` to access specified host and port
2. using `--open` to open Web application(e.g. chromium ...) and access the application automatically 
3. using `--watch=false` 與 `--liveReload=false` 兩個參數則是可以取消檔案監控與重新載入應用程式頁面。


We will have at least a root-module as the first boost up module While Boosting the Angular Application
- The root module is stored in the `src/main.ts`


`bootstrap:[AppCompnent] `in `app.module.ts` will replace `<app-root>` in `index.html`
```typescript
if(environment.production){
    enableProMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
    .catch(err => console.error(err));
```

## Generate Component 

```bash
ng generate <schematic> [parameter]
ng g <schematic> [parameter]

# For example create Angular Material's table
ng generate @angular/material:table <component-name>
```
- `new`、`add` 與 `generate` 三個命令主要都會新增修改相關的程式檔案，在需要確認命令是否正確的話，可以透過 `--dryRun=true` 參數來確認命令的執行結果是不是自己所想要的

## Compile the Application to `ECMAScript`

當應用程式開發完後，會使用此命令將應用程式編繹成  `dist/該專案的名稱` 目錄下，就可以將其佈署到正式環境上

```bash
ng build <Name_Of_Project> [parameter]
ng b <Name_Of_Project> [parameter]
```


[difference btw `ng b` and `ng s`](https://stackoverflow.com/questions/47150724/what-is-difference-between-ng-build-and-ng-serve)  


## Deploy our Application 

不同於 build 命令將應用程式編譯至輸出目錄，可以利用該此命令將程式編譯並佈署至特定環境下。
- e.g. Deploy Complied Application to specified Environment (e.g. `Azure` ... etc) 

```bash
# Deploy our Application to Azure
np deploy @azure/np-deploy
```
## Install dependencies

We often use `npm install` to install frontend's packages.  
- These installed packages will be recorded in `dependencies` or `devDependencies` attribute.  

For example, to install A bootstrap
```bash
cd project
npm install -save bootstrap
```

### update packages


```bash
ng update [parameter]
```


## Linting

在專案中加入如 TsLint 或 ESLint 等工具，來統一程式風格。此命令就是在檢查專案下的程式，是否有依 Lint 的規則進行撰寫。

```bash
ng lint <nameOfProject> [parameter]

# lint with l
ng l <nameOfProject> [parameter]
```