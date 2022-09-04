# Grid 


[Css Grid 概念介紹及使用教學](https://ballaediworkshop.blogspot.com/2019/10/css-grid-introduction-and-tutorial.html)

- [Grid](#grid)
    - [Template Layout](#template-layout)
      - [設定Container內的line分界線](#設定container內的line分界線)
      - [設定item大小](#設定item大小)
    - [Template Area](#template-area)
  - [Align](#align)
    - [gap](#gap)
    - [justify, align, place](#justify-align-place)
    - [justify (x-axis + height 100%)](#justify-x-axis--height-100)
    - [align (y-axis + width 100%)](#align-y-axis--width-100)
    - [place-content](#place-content)

Css Grid 的組成，一個 Grid 有兩個部分：Container (容器或框架) 與 Item (元件)。
```typescript
<div class="container">
    <div class="item c">A</div>
    <div class="item b">B</div>
    <div class="item c">C</div>
</div>
 ```

### Template Layout

container 設定各虛擬線間之距離，item 個別設定區域大小
 
![圖 2](../images/fea733759667188721601809160e1050bbbd1bee202e1dc099251fbcbbfd06ea.png)  


#### 設定Container內的line分界線
```css
grid-template-columns: 40px 50px auto 50px 40px;
grid-template-rows: 25% 100px auto;
```

Alias for each line
```css
grid-template-columns: [first] 40px [line2] 50px [line3] auto [col4-start] 50px [five] 40px [end];
grid-template-rows: [row1-start] 25% [row1-end] 100px [third-line] auto [last-line];
```

repeat function
```css
/* 20px [col-start] 20px [col-start] 20px [col-start] */
grid-template-columns: repeat(3, 20px [col-start]);

/* (col-start的第2條) */
grid-column-start: col-start 2;
```

#### 設定item大小
![圖 3](../images/4e9dd64b8e034b7602d3317e2b8ff2a21dedb280d657a1936786343749ee45f6.png)  

```css
grid-column-start: 2; (2nd line)
grid-column-end:  5; (5th line)
grid-row-start: 1; (1st line)
grid-row-end: 3; (3rd line)


/* 更簡單的方式 */
grid-column: 2 / 5;    /* (<startLine> / <endLine>) */
grid-row: 1 / 3;    /* (<startLine> / <endLine>) */

/** 一次設定 **/
/* <row-start> / <column-start> / <row-end> / <column-end> */
grid-area: 1 / col4-start / last-line / 6;


grid-column : 2 /*  相當於 2 / 3  */

/** 用line的alias name **/
grid-column: line2 / five;
grid-row: row1-start / third-line;

/*
 * grid-column 或 grid-row : span startLine , span endLine語法
 * startLine 會往前一條(startLine - 1) 往左或上擴張
 * endLine會往後一條(endLine + 1) 往右或下
 */
grid-column: span 3 / span 4;
/*          
    == 2 / 5        
    == span line3 / span col4-start
    == line2 / five    
*/
```


### Template Area 
 
container 設定區域及區域名稱，item 只需設定 container 設定好的名字


```typescript
grid-template-areas: "<grid-area-name> | . | none | ..."
```


![圖 4](../images/4534e8269e5fef1680b5ce93dd692ecc28d7aa4f7178b107c48520ec6c54c037.png)  

```
 grid-template-areas:
    "header header header header"
    "main main . sidebar"
    "footer footer footer footer";

grid-area: header;
grid-area: main;
grid-area: sidebar;
grid-area: footer;
```

## Align 

### gap 

Each Item's gap

```typescript
grid-column-gap: 10px
grid-row-gap: 15px

/* grid-gap: <grid-row-gap> <grid-column-gap> */
grid-gap   : 15px           10px;
```

### justify, align, place

`-items` 結尾的為 container 一次設定所有 items
`-self` 結尾的為 特定item 個別設定的對齊方式

```typescript
container :
justify-items:
align-items:
place-items:
```

```typescript
Item:
justify-self:
align-self:
place-self:
```

### justify (x-axis + height 100%)

```css
justify-items|self : start  ; 
justify-items|self : end    ;
justify-items|self : center ;
justify-items|self : stretch;  /** default **/
```

### align (y-axis + width 100%)

```css
align-items|self : start  ; 
align-items|self : end    ;
align-items|self : center ;
align-items|self : stretch;  /** default **/
```

### place-content

整合align-content & justify-content

```css
/*  <align-content> / <justify-content>  */
place-content: space-around;
```