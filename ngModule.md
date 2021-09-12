 ###### tags: `Angular`
# Module
[TOC]

## To generate Module in CLI

```bash
ng generate -m <nameOfModule> [params]
```

例如,將某個Component放在TaskModule內，並要在AppComponent中使用
```bash
ng g task -m app
# `g`  : generates 
# `-m` : module
# `task` : Directory Name, and File Prefix Name
#  ng g task[Module] -m app[Component] 
```
- 在這裡使用task而不是taskModule原因是Angular CLI會自動依據指令`-m/-c`判斷檔案名稱為Module還是Component  
- 所以如果是打`ng g m taskModule -m app`則建立的檔案名稱會變成`taskModuleModule`  
- 如沒有指定`--flat`參數，則會先建立`task`目錄，在此目錄下建立 `TaskModule`  

:::danger  
對於CLI在操作Module,Component,Service...etc 只需要輸入前綴名稱就行了 
:::  

## Generate Module via VSCode Extension Package
Using `Angular Schematics` Extension Package to generate modules we want  
![](https://i.imgur.com/RCvCLc1.png)  


# Module內的`@NgModule`

## imports (Modules) to use 
代表要使用哪些模組所提供的功能，例如我們常常使用的`ngModel` ，就是在 `FormsModule`裡面所提供的一個 directive，此時我們就必須在`@NgModule `的`imports: []`中加入`FormsModule`  

- 在一般使用 Angular CLI建立的`.ts`，我們可以看到在`AppModule.ts`中的`@NgModule`包含了`imports: [BrowserModule]`，代表的就是針對瀏覽器開發的常用程式都來自於`BrowserModule`中  
> 簡單的說，當有使用到其他Module的需求時，就需要使用`imports: [] `將該Module加入。

## declarations(Components) to use
在`declarations: [] `設定中，我們會將**可宣告(declarable)** 的類別放在這個設定中，什麼是可宣告的類別呢？簡單來說就是跟Template顯示有關的程式，都屬於這裡面的設定。

簡單來說，在 HTML 上可能會看到某個元件(component)的selector、某個元素上掛了一個指令(directive)，或是使用管線(pipe)來改變呈現內容，這些都是跟樣板有關的，而當 Angular 在編譯這些樣板時，就會在相關的模組中，尋找是否在`declarations: []`有加入這些類別，如果有，就執行相關的程式並且改變畫面的行為。如下程式：
```typescript
@NgModule({
  declarations: [AppComponent, HighlightDirective, PasswordPipe]
})
export class AppModule { }
```
- 這些與顯示相關的程式類別都只能存在於一個模組之中，如果存在多個Different Modules中時，編譯程式時就會發生錯誤！
    > 因此如果我希望將某個元件封裝在某個模組中，但在別的模組中的元件要能夠使用時，就需要使用另一個設定：`exports`。

## exports (Component) to other module to use
我們已經知道 Angular 中的與畫面相關的程式類別必須放在`declarations: []`之中，且每個與畫面相關的程式類別只能存在於一個模組內；但我們很有可能在A模組寫了個Component_A，卻想在B模組中的另一個元件中使用Component_A  
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
// AppComponent will use MainComponent
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
此時在編譯 MainComponent 的樣板時，會得到以下錯誤：

![](https://wellwind.idv.tw/blog/2018/10/20/mastering-angular-05-knowning-ng-module/01.jpg)

儘管在`imports:[]`中加入了`MainModule` ，但依然無法使用 MainComponent，這是因為這些在樣板顯示用的程式類別，若要被其他模組使用，必需放在所屬模組的`exports: []`之中
```typescript
@NgModule({
  // only use for this Module
  declarations: [MainComponent],
  // allow used by other Module
  exports: [MainComponent]
})
export class MainModule { }
```
- 在共用模組時，我們可以自行決定哪些程式允許外部使用，哪些程式是不需要被外部使用的，維持最小知識原則，避免不必要的元件被外部程式誤用  

## providers
`providers: []` 主要是用來決定哪些服務(service)允許被Injected，在 Angular 6 之後替`@Service`加上了`providedIn`設定，讓服務不一定非要放在`providers: []`之中，**但在很多時候要改變注入行為時，這裡依然是很重要的設定點 [Dependency Injection](/8AQani2_TCKWNUsp5ob5ng)**  


## bootstrap
- `bootstrap : [] `裡面的元件會自動被啟動。
    > 放在` bootstrap: []` 中的元件，會自動被視為放入 `entryComponents: []` 之中  
    > Boost Angular Application -> bootstrap內的component會自動啟用 

## entryComponents (動態載入 e.g. (彈出的小視窗`dialog`))
在顯示一個元件時，有動態載入與靜態載入兩種方法：

1. 動態載入：代表不是在某個樣板中使用`<app-xxx></app-xxx>`的方法載入元件，**而是透過我們手動撰寫程式，將元件載入在畫面(`xxx.html`)中的某個地方**  
2. 靜態載入：剛好與動態組入相反，也就是**主動**在樣板中使用`<app-xxx></app-xxx>`的方式來載入元件  

:::info  
由於動態載入元件時不會在樣板中宣告使用元件，因此在打包程式時，會因為不知道何時被使用，為了減少打包程式的大小，而將元件去掉，反而造成無法載入的問題，因此 Angular 設計了 `entryComponents: []`，只要元件在程式中需要被動態載入，可以透過放入 `entryComponents: [] `來避免程式在打包時被移除  
:::  