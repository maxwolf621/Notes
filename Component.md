###### tags: `Angular`
# Component

[Code Example](https://ithelp.ithome.com.tw/articles/10240474)

- [Component](#component)
  - [指派Component到指定的Module](#指派component到指定的module)
  - [`export` component到指定的module](#export-component到指定的module)
  - [Component的裝飾器](#component的裝飾器)
    - [Selector](#selector)
    - [Web Page 以及 Template](#web-page-以及-template)
  - [`@Component.selector`](#componentselector)

## 指派Component到指定的Module

**每個 Angular Component皆需要定義在 Angular Modules內**，否則是無法被使用   

透過Angular CLI建立Component時，**CLI會從新增元件所在的目錄向上層找尋最近的模組，並將此元件(Component)定義至該模組內**  
- 例如，當執行 `ng g c demo1` 命令時，由於在根目錄 `src/app` 下建立元件，而最接近的模組是`AppModule`，故此元件會加入在 `AppModule` 內  

若要將元件建立在特定模型之下，可以使用絕對路徑  
```bash
# 將Component建立在`src/app/task`目錄下
# 加入task/TaskModule模組內(如果存在TaskModule否則加入到appModule內)
ng g c task/demo1
```
- 也可以利用`--module`參數來明確指定要加入的模組；不過需要注意，此方法會讓模組位置與檔案位置兩者不一致  

## `export` component到指定的module

當Component需要給其他的Module使用時，就要將該元件加入在指定Module的`exports`屬性內

例如有個`TaskComponent`需要讓`AppComponent`使用
```bash 
#由於在根目錄 src/app下建立元件，而最接近的模組是AppModule，故此元件會加入在 AppModule 內
ng g c demo1 

# 若要將元件demo1建立在特定模型之下，可以明確指定路徑
# 元件建立在 src/app/task
ng g c task/demo1  

ng g c task/task --export
```
- 透過 `--export`讓CLI在 `TaskModule` 將`TaskComponent`加入至`export`屬性內。

## Component的裝飾器
如同module一樣，Component透過裝飾器`@Component` 可以決定此元件在執行期間該如何處理、實體化與使用  
  

### Selector 

`task.component`被`app.component.html`使用
![](https://i.imgur.com/Tt080Iu.png)  

```typescript
task.component.ts
@Component({
    selector : 'app-task',
    template : './task.component.html',
    styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
    // ...
}

app.component.html use this component
<task></task>
```

### Web Page 以及 Template
當 Angular Component的頁面檢視寫在 HTML 檔案內，頁面Template放在 CSS 等樣式檔案時，會將 HTML 檔案與樣式檔案路徑定義在`@Component`中的 `templateUrl`與`styleUrls`  

```typescript
@Component({
    selector:'app-task'
    templateUrl: '/Path_Of_The_HTML_FILE.html',
    sytleUrls: ['/Path_Of_The_CSS_FILE.css']
})
```
- 若在Terminal建立Component時，指定了`--inlineStyle`與 `--inlineTemplate`，則會在`template`與`styles`兩屬性下撰寫頁面與樣式的程式碼而不是`templateUrl`與`styleUrls`  

## `@Component.selector`

使其他Components的HTML內可以使用此元件緒染畫面  
- **當透過Router的方式來載入的元件時，不會使用到選擇器(Selector)的設定，此時可以在建立元件時使用`--skipSelector`**  

`@Component`的`selector`命名上，開頭會使用統一的前字元 (prefix)，其後則為元件名稱  
> 而前字元可以在建立專案(`ng new`)或建立元件(`ng g c`)時，利用 `--prefix` 參數指定  
- **需要注意的是，若Project與Component使用(prefix)前字元不同時，除非變更Lint規則，否則會出現警告訊息**  
