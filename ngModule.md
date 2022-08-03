###### tags: `Angular`
# Module
- [Module](#module)
  - [Generate Module in CLI](#generate-module-in-cli)
  - [Module內的 `@NgModule`](#module內的-ngmodule)
    - [`imports` (Modules)](#imports-modules)
  - [`declarations` (Components)](#declarations-components)
  - [exports (Component) to other module to use](#exports-component-to-other-module-to-use)
  - [providers](#providers)
  - [bootstrap](#bootstrap)
    - [entryComponents](#entrycomponents)

## Generate Module in CLI
```bash
ng generate -m <nameOfModule> [params]

# 例如 :: 
# 將某個Component放在`TaskModule`內，並在`AppComponent`中使用
ng g task -m app
# g  : generates 
# -m : module
# task : Directory Name and Module's Prefix Name
# ng g task[Module] -m app[Component] 
```
- CLI使用`task`而不是`taskModule`建立`TaskModule`原因是Angular CLI會自動依據指令`-m`以及`-c`判斷檔案名為`Module`或`Component`  
    - 對於Angular CLI建立Module,Component,Service ...etc...只需要輸入前綴名即可 
- 如果是打`ng g m taskModule -m app`則建立的檔案名稱會變成`taskModuleModule`  
- 沒有指定`--flat`參數，則會先建立`task`目錄，在此目錄下建立 `TaskModule`  

## Module內的 `@NgModule`

### `imports` (Modules)  

Import符合當前需求的Modules(來使用Module內提供的功能)   
- 例如`ngModel` ，就是在 `FormsModule`裡面所提供的一個Directive，此時我們就必須在`@NgModule` 的 `imports: []` 中加入`FormsModule`  
- 在一般使用 Angular CLI建立的`xxxxyyy.ts`，我們可以看到在`AppModule.ts`中的`@NgModule`包含了`imports: [BrowserModule]`，代表的就是針對瀏覽器開發的常用程式都來自於`BrowserModule`中


## `declarations` (Components) 
我們會將**可宣告(declarable)** 的類別放在這個`declarations: [ ..., ... , ...]`內即Template顯示有關的程式  

簡單來說，在 HTML 上可能會看到某個元件(component)的selector、某個元素上掛了一個指令(directive)，或是使用管線(pipe)來改變呈現內容，這些都是跟Templates有關的，當 Angular 在編譯這些Templates時，就會在相關的模組中，尋找`declarations: []`內是否有加入這些類別，如果有，就執行相關的程式並且改變畫面的行為
- For example
```typescript
@NgModule({
  declarations: [AppComponent, 
                 HighlightDirective, 
                 PasswordPipe]
})
export class AppModule { }
```
- 這些與顯示相關的程式類別都只能存在於一個Module之中，如果存在多個不同Modules中時，編譯程式時就會發生ERROR  
因此如果我希望將某個元件封裝在某個模組中，但在別的模組中的元件要能夠使用時，就需要使用另一個設定：`exports`

## exports (Component) to other module to use

Angular中的與渲染畫面相關的類別必須放在`declarations: []`之中，且每個與畫面相關的程式類別只能存在於一個模組內；但我們很有可能在A模組寫了個Component_A，卻想在B模組中的另一個元件中使用Component_A  

- For example :: `MainModule` 以及 `AppModule` 同時都會用到 `MainComponent`
```typescript
@Component({
  selector: 'app-main',
  template: 'Hello'
})
export class MainComponent { }

@NgModule({
  declarations: [MainComponent]
})
export class MainModule { }
```

```typescript
// AppComponent uses MainComponent
@Component({
  selector: 'app-root',
  template: '<app-main></app-main>'
})
export class AppComponent { }

@NgModule({
  imports: [BrowserModule, MainModule],
  declarations: [AppComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
```
- 此時在編譯`MainComponent`的樣板時，會Error

即時在`imports:[]`中加入了`MainModule` ，但依然無法使用 `MainComponent`，這是因為這些在樣板顯示用的程式類別，若要被其他Module使用，必需放在所屬Module的`exports: []`之中
```typescript
@NgModule({
  // MainModule uses MainComponent  
  declarations: [MainComponent],
  // Allow MainComponent to be used by other Module
  exports: [MainComponent]
})
export class MainModule { }
```

**在共用模組時，我們可以自行決定哪些程式允許外部使用，哪些程式是不需要被外部使用的，維持最小知識原則，避免不必要的元件被外部程式誤用**  

## providers

`providers: []` 主要是用來決定哪些服務(service)允許被Injected，在 Angular 6 之後替`@Service`加上了`providedIn`設定，讓服務不一定非要放在`providers: []`之中，**但在很多時候要改變注入行為時，這裡依然是很重要的設定點**  

## bootstrap

Angular Application開啟後`bootstrap : [ ... , ... ]`裡面的元件會自動被啟動
- `bootstrap: []`的元件，會自動被視為放入`entryComponents: []` 之中  

### entryComponents   
在顯示一個元件時，有動態載入與靜態載入兩種方法  
1. 動態載入：代表不是在某個樣板中使用`<app-xxx></app-xxx>`的方法載入元件，**而是透過我們手動撰寫程式，將元件載入在畫面(`xxx.html`)中的某個地方**  
2. 靜態載入：剛好與動態組入相反，也就是**主動**在樣板中使用`<app-xxx></app-xxx>`的方式來載入元件  

**由於動態載入元件時不會在樣板中宣告使用元件，因此在打包程式時，會因為不知道何時被使用，為了減少打包程式的大小，而將元件去掉，反而造成無法載入的問題，** 因此 Angular 設計 `entryComponents: []` (e.g dialog 小視窗 ...)，只要元件在程式中需要被動態載入，可以透過放入 `entryComponents: [] `來避免程式在打包時被移除  
