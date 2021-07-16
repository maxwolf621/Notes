###### tags: `Angular`
# Angular CLI 
[basic Setting in CLI](https://ithelp.ithome.com.tw/articles/10238044)

## new A project
```bash
np new <project> [params]
# ** OR ** #
np n <project> [params]

# --help to get more info for `new` or `n`
```
- `--createApplication=false` 可以建立一個Empty的 Angular workspace，再依照需求建立應用程式 (application) 或函式庫 (library)。
- 若想要透過如 yarn 等其他套件管理工具來安裝所需的套件，可以利用 `--packageManager` 參數指定。
- `--skipInstall=false` 取消套件的安裝
- `--skipGit=false` and `--commit=false` 取消Git版本control與commit。

#### Dependency Tree Problem
[Ref](https://stackoverflow.com/questions/64573177/unable-to-resolve-dependency-tree-error-when-installing-npm-packages)

Dependency Tree Error while `ng new <project>`   
```bash
cd project
npm install --legacy-peer-deps
```

### `src` directory 
after `new` a workspace of Angular, it will create a root directory `src` 
> All dependencies (files, pictures ... etc) will be stored under the `src/`  

### Page and Template for Angular

**Angular以元件(Component)來開發單位**，封裝了應用程式的頁面、樣式與邏輯，而預設上 Angular 元件會將這三部份拆分至 HTML、CSS 與 TypeScript 三種檔案之內  

- `--inlineTemplate=true` 與 `--inlineStyle=true`可以讓頁面與樣式都放在 TypeScript 檔案中  
- Angular supports Template CSS by default, but we can use `--style` set template as SCSS、SASS、LESS or stylus files  

> Angular CLI 的互動模式（`--interactive=true`）下，若未指定此參數時，Angular CLI 會在執行命令前進行詢問  

> Static files will stored under `assets/`  

> Define global template in the style.css

### Route
Angular is Single-Page Application **(SPA)**  
We swap web pages by `Routing`  

using `--routing=true` to create route module   

> 單一頁面應用程式 (Single-Page Application, SPA) 不同於早期網頁程式，整個應用程式只有一個頁面，透過`Ajax`與伺服器溝通並更新部分網頁資料  


## `test` the Application

```bash
# the following cli command
# it will look up `test.ts` 
ng test <project > [parameter]
ng t <project> [parameter]
```
- For testing components validity, Angular will create correspond testing packaged while creating a work-space.
- By default testing (specification) files format is  `.spec.ts`

we can use 
1. `--minimal = true` to disable the default testing framework, and manually create desire testing framework by yourself 
2. `--skipTests=false` to disable creating new spec file while creating new component 

### `e2e` test

End To End Testing
```bash
ng e2e <Project> [Parameter]
ng e <Project> [Parameter]
```

## Angular Package Management 

```bash
# add the package
np add <Name_Of_Package> [Parameter]

# for example
ng add @angular/material

# update the package
np update @angular/material
```


### Dynamically Boost up Angular Application

[Reference](https://www.howtoing.com/install-angular-cli-on-linux)  
[More Cli](https://ithelp.ithome.com.tw/articles/10238044)  


To open Application (e.g Web Application ...etc )  
```bash
ng serve <Name_Of_Project> [parameter]
# **OR** #
ng s <Name_Of_Project> [parameter]
```
- According to the setting of `angular.json` to boost application dynamically 
- Via default path `http://localhost:4200` to browser websites

1. using `--host` or `--port` to access specified host and port
2. using `--open` to open Web application(e.g. chromium ...) and access the application automatically 
3. using `--watch=false` 與 `--liveReload=false` 兩個參數則是可以取消檔案監控與重新載入應用程式頁面。


We will have at least a root-module as the first boost up module While Boosting the Angular Application
The root module is stored in the `src/main.ts`

`bootstrap:[AppCompnent] `in `app.module.ts` will replace `<app-root>` in `index.html` while is booting
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

`new`、`add` 與 `generate` 三個命令主要都會新增修改相關的程式檔案，在需要確認命令是否正確的話，可以透過 `--dryRun=true` 參數來確認命令的執行結果是不是自己所想要的


## Compile the Application to `ECMAScript`

```bash
ng build <Name_Of_Project> [parameter]
ng b <Name_Of_Project> [parameter]
```
After compiling successfully it will be stored in the `dist/` + `ProjectName` 

## Deploy our Application 
Deploy Complied Application to specified Environment (e.g. Azure ...) 

```bash
np deploy @azure/np-deploy
```

## Create a To-do list

```bash
np new todo
```

## `package-lock.json`

紀錄專案的基本資訊，例如專案的名稱、版本，還有專案會使用到的相依套件等等資訊  
- When `npm install` is executed , then it will look up `package-lock.json` to install the packages that `package-lock.json` lists  

### Install dependencies

We often use `npm install` to install 前端的packages.  
These installed packages will be recorded in `dependencies` or `devDependencies` attribute.  

For example, to install A bootstrap
```bash
cd project
npm install -save bootstrap
```

## `angular.json`
To set up **configurations** of the angular application

For example a work-space of angular application would have multiple projects files
- The `newProjectRoot` attribute indicates where the new files will be stored at 
> `defaultProject` 屬性用來描述整個工作環境下，預設的專案名稱；當執行 Angular CLI 指令時，若不指定專案，就會依此處設定的專案為執行對象。

## `tsconfig.json`

將Typescripe編譯成Javascript時的編譯設定

潛在問題  
- 一般開發網頁程式為了減少程式檔案大小，來降低程式傳輸至客戶端的時間，常常會將 JavaScript 進行程式碼壓縮；但也因此讓程式人員無法在系統錯誤時，直接知道哪一行程式拋出的錯誤訊息  
    > 解決: 利用`SourceMap`即用來記錄程式碼在壓縮前後位置對應的檔案，讓程式人員可以更方便地除錯  

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
  /*
   * 多行註解
   */
}
```
[More Details](https://yiyingloveart.blogspot.com/2016/07/typescript-tsconfigjson.html)