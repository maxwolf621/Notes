# Flex

[CSS FLEXBOX — 伸縮自如的排版|基礎觀念](https://reurl.cc/AORWkK)    
[Day 5 : HTML - 網頁排版超強神器，CSS Flex到底是什麼？](https://reurl.cc/8pDq37)      

- [Flex](#flex)
  - [css flex](#css-flex)
    - [fxLayoutAlign](#fxlayoutalign)
    - [flex-grow & flex-shrink](#flex-grow--flex-shrink)
  - [angular flex-layout](#angular-flex-layout)
    - [Controls Items In Container](#controls-items-in-container)
    - [Items within Containers (`fxFlex`-)](#items-within-containers-fxflex-)
  - [special responsive features](#special-responsive-features)

## css flex
[flex help](https://flexbox.help/)   
[w3 flex-box](https://www.w3schools.com/css/css3_flexbox_container.asp)   

elements in container
```html
<div id="container" class="flex-container">
  <div id="item1">1</div>
  <div id="item2">2</div>
  <div id="item3">3</div>  
</div>
```

Flex-direction
- [flex-direction: column](https://www.w3schools.com/css/tryit.asp?filename=trycss3_flexbox_flex-direction_column)
- [flex-direction: column-reverse](https://www.w3schools.com/css/tryit.asp?filename=trycss3_flexbox_flex-direction_column-reverse)
- [flex-direction: row](https://www.w3schools.com/css/tryit.asp?filename=trycss3_flexbox_flex-direction_row)
- [flex-direction: row-reverse](https://www.w3schools.com/css/tryit.asp?filename=trycss3_flexbox_flex-direction_row-reverse)


### fxLayoutAlign

FLEX-WRAP : whether items wrap to the next row (**only applies if combined width of items is greater than container's**)   
Justify-content : alignment along the x axis       
align-items : alignment along the y axis(單列items)     
align-content :only applies if there is more than one row of items (一次處理Container內所有Item(每一列的Items))  
align-self : 對單一特定item進行(Y-axis)    
### flex-grow & flex-shrink

![圖 5](../images/05cc62c847e83f3608d2549e57c17c7ab17e9689061b88eb30cc4adb382a581b.png)  
```typescript
Container's width : 600px
Each Items's width : 100px
Total Items : 4。
4 Items' flex-grow are 1, 2, 3, 4

600–100*4/10=20px

Each Items can grow：20px, 40px, 60px, 80px
```

![圖 6](../images/461d948e28c2f88f98bdd6d133bc39ea1ea1e6732fda1020431326ee415c858b.png)  


## angular flex-layout

- [使用 Angular Flex-Layout 輔助版面布局](https://blog.poychang.net/use-angular-flex-layout-package/)
- [Containers](https://tburleson-layouts-demos.firebaseapp.com/#/docs)

HTML API 可以分出以下三類：
1. 容器類 Container
2. 子元素類 Items (Child Elements Within Container)
3. 特殊響應功能 Special Responsive Features

### Controls Items In Container

```
flexLayout 
fxLayoutGap
dir
```


```html
<!--          order of each items -->
<div fxLayout="row | column">  
  ....
</div>

<!-- 
  move each item from 
  right to left | left to right   
-->
<div dir = "rtl | ltl">
...
</div>

<!-- each items gap -->
<div fxLayoutGap = "20px">
<!--x-axis-->
fxLayoutAlign="space-evenly | space-around | space-between | center | start | end
<!--y-axis-->
start | end | center | stretch" >
</div>
```

### Items within Containers (`fxFlex`-)

`fxFlex` constrols item size
```html
<!--         grow shrink basis-->
<div fxFlex="1    2      calc(15em + 20px)"></div>
```
- unit : `%、px、vw、vh`

`fxFlexOrder` : order of each item
```html
<div fxFlexOrder="2">
  fxFlexOrder = int number
</div>
```  

`fxFlexOffset` offset of item  
```html
<div fxFlexOffset="% | px | vw | vh"></div>
```

`fxFlexAlign` (對應grid's `align-self`) : Align specific item
```html
<div fxFlexAlign="start | baseline | center | end "></div>
```

`fxFlexFill` : stretch each item's width and height 100%
```html
 `<div fxFlexFill></div>`
```

## special responsive features

```bash
xs : extra small < 600px
sm : small 600px
md : medium 960px
lg : large 1280px
xl : extra large 1920px
```
```typescript
xs	width < 600px
sm	600px <= width < 960px
md	960px <= width < 1279px
lg	1280px <= width < 1919px
xl	1920px <= width < 5000px
```

lt : less than
gt : great than
```typescript
lt-sm	width < 600px
lt-md	width < 960px
lt-lg	width < 1280px
lt-xl	width < 1920px

gt-xs	width >= 600px 
gt-sm	width >= 960px
gt-md	width >= 1280px
gt-lg	width >= 1920px
```