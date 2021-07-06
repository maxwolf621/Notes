###### tags: `Angular`

# Module 

[TOC]

## The @NgModule

Define **components** that will be used in this Module

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

To import modules that we will use in this Module

 
```typescript=
// rowserModule, AppRoutingModule 
//     that will be used in This Module
@NgModule({
    imports:[
        BrowserModule, 
        AppRoutingModule
    ],
})
```

### declarations

To declare the Component that this module will use


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
    // ...
    bootstrap:[AppComponent],
})
```
- `bootstrap` Attribute can only setup in the Root Component，在`bootstrap`內定義的元件則會在 Angular Application啟動後首先被執行。

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

```bash
ng generate -m <nameOfModule> [params]
```

例如,將某個Component放在TaskModule內，並要在 AppComponent 中使用；
```bash
ng g task -m app
# `g`  : generates 
# `-m` : module
# `task` : Directory Name, and File Prefix Name
```
> 在這裡使用task而不是taskModule原因是Angular CLI會自動依據指令-m/-c判對檔案名稱為Module還是Component  
> 所以如果是打`ng g m taskModule -m app`則建立的檔案名稱會變成`taskModuleModule`  
> 如沒有指定`--flat`參數，則會先建立`task`目錄，在此目錄下建立 TaskModule  


## Generate Module via VsCode Extension Package
Using `Angular Schematics` Extension Package to generate modules we want  
![](https://i.imgur.com/RCvCLc1.png)  
