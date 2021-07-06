###### tags: `Angular`
# Component
[TOC]

## 指定 Component 所屬模組(Module)
**每個 Angular Component皆需要定義在 Angular Modules內**，否則是無法被使用;
因此透過 Angular CLI 建立Component時，**CLI 會從新增元件所在的目錄向上層找尋最近的模組，並將此元件定義至該模組內**

> 例如，當執行 `ng g c demo1` 命令時，由於在根目錄 `src/app` 下建立元件，而最接近的模組是 AppModule，故此元件會加入在 `AppModule` 內。


若要將元件建立在特定模型之下，可以使用絕對路徑
```bash
ng g c task/demo1
```
就可將元件建立在 `src/app/task` 目錄下，並加入
至 `TaskModule` 模組內  
也可以利用 --module 參數來明確指定要加入的模組；不過需要注意，此方法會讓模組位置與檔案位置兩者不一致  

---
當Component需要給其他的Module使用時，就要將該元件加入在指定Module的`exports`屬性內

> 例如有個 TaskComponent 需要讓 AppComponent 使用，因此執行 `ng g c task/task --export` 命令來建立；透過 `--export`讓 CLI 在 `TaskModule` 將TaskComponent加入至`export`屬性內。

## @Component 
如同模組一樣，Angular 元件是個帶有 `Component` 裝飾器的類別，透過 `@Component` 裝飾器可以決定此元件在執行期間該如何處理、實體化與使用  

例如下圖  
- task.component被app.component.html使用
![](https://i.imgur.com/Tt080Iu.png)


### Redirect Web Page AND Template
當 Angular Component的頁面檢視寫在 HTML 檔案內，頁面Template放在 CSS 等樣式檔案時，會將 HTML 檔案與樣式檔案路徑定義在`@Component`中的 `templateUrl`與`styleUrls`

```typescript=
@Component({
    selector:'app-task'
    templateUrl: '/Path_Of_The_HTML_FILE.html',
    sytleUrls: ['/Path_Of_The_CSS_FILE.css']
})
```

- 若在Terminal建立Component時，指定了`--inlineStyle`與 `--inlineTemplate`，則會在`template`與`styles`兩屬性下撰寫頁面與樣式的程式碼而不是`templateUrl`與`styleUrls`。

### selector

- 用來定義在其他元件內使用此元件

- **當透過Router的方式來載入的元件時，不會使用到選擇器的設定，此時可以在建立元件時使用`--skipSelector`**


元件的選擇器命名上，開頭會使用統一的前字元 (prefix)，其後則為元件名稱  
> 而前字元可以在建立專案(`ng new`)或建立元件(`ng g c`)時，利用 `--prefix` 參數指定。

**需要注意的是，若在專案與元件所使用前字元不同時，除非變更 Lint 規則，否則會出現警告訊息**