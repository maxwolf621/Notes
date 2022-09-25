# Position

[Position 基本用法](https://reurl.cc/AOKkZ8)

Position using `top|left|bottom|right` properties to adjust the position

## fixed vs sticky

- **有FIXED ELEMENT不管放哪裡都會擺在顯示畫面的最上層**
- **有STICKY ELEMENT則是在指定位置做固定(便利貼)**
- Fixed跟Sticky都會跟著畫面走
## in Action

<html>
<head>
<style>
#block {
    position: relative;
    top: 10px;
    left: 20px;
    width: 200px;
    height: 100px;
    background-color: grey;
}
#out_block {
    position: relative;
    top: 100px;
    left: 50px;
    width: 300px;
    height: 300px;
    background-color: black;
}
#absolute_block {
    position: absolute;
    top: 500px;
    left: 50px;
    width: 100px;
    height: 100px;
    display: none;
}
#out_block1 {
    height: 3000px;
}
#out_block:hover #absolute_block{
    display: block;
}
#fixed_block {
    position: fixed;
    top: 0px;
    background-color: yellow;
}
#sticky_block {
    position: sticky;
    top: 0px;
    background-color: #ff0000;
}
#staticBlock{
    position : static;
    background-color: grey;
}
</style>
</head>
<body>

<div id="sticky_block">sticky1</div>

<div id="block">position-relative</div>

<div id="fixed_block">fixed1-on-top</div>

<div id="block">position-relative</div>

<br/>

<div id="sticky_block">sticky2 here</div>

<div id="block">position-relative</div>

<div id="out_block">
    BLOCK-ORIGIN
    <div id="sticky_block">
        sticky in block 1
    </div>
    <div id="staticBlock">static_block</div>
    <div id="absolute_block"> 
        absolute 是搭配 relative 做使用的，只要在設置 relative 的區塊內建立一個設置為 absolute 的內層區塊
    </div>
    <div id="fixed_block">
        fixed element inside in relative block
    </div>
    BLOCK-MIDDLE
    <div id="sticky_block">
        sticky in block 2
    </div>
    BLOCK-DESTINATION
</div>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>


</body>
</html>

