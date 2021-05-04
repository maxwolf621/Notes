###### tags: `Angular`

# Module 

[TOC]

## The @NgModule
> To define **components** in the Module


Attributes in `@NgModule`
```typescript=
@NgModule({
    imports:[],
    declarations:[],
    providers:[],
    bootstrap:[],
})
```

### `imports` 

When we need other module we will add module name into import array

In AppModule.ts 
```typescript=
@NgModule({
    imports:[BrowserModule, AppRoutingModule],
})
```

### declarations

我們在開發 Angular 的各種元件，都需要宣告在特定且唯一的模組之中，否則即使是相同模組，也會無法在其他元件內使用，因此會在 declarations 屬性加入該模組所擁有的元件 (Component)
```typescript=
@NgModule({
    declarations:[AppComponent],
    //...
})
```

### Providers 

For Dependency Injection [More Details](/8AQani2_TCKWNUsp5ob5ng)

### bootstrap
```typescript=
@NgModule({
    ...
    bootstrap:[AppComponent],
})
```
- This define the whole Angular Application's Root Component
- This Attribute只有在根模組才會設定，在定義的元件會在 Angular 應用程式啟動後首先被執行。

## `exports`
Only **export** the necessary component to other Modules
```json=
@NgModule({
    imports:[],
    declarations:[],
    providers:[],
    bootstrap:[],
})
exports class AppModule{}
```


## To generate Module in CLI
To less the Type-Errors **by using `ng generate <nameOfModule> [params]` to create Module**

例如,將某個Component 放在 TaskModule內，並要在 AppComponent 中使用；**因此在generate TaskModule時，在CommandLine輸入 `ng g m task -m app`**

> 在這裡使用task而不是taskModule原因是Angular CLI會自動依據指令（指定類型）自動加上關鍵字
> 如果是打`ng g m taskModule -m app`則會變成`taskModuleModule`

如沒有指定`--flat`參數，則會先建立`task`目錄，在此目錄下建立 TaskModule。

流程
1. CommandLine `ng g m task -m app`
2. Create A Directory called `task`
3. Generate the `taskModule` in the `task`


## Generate Module via VsCode Extension Package

Using `Angular Schematics` Extension Package to generate modules we want

![](https://i.imgur.com/RCvCLc1.png)
