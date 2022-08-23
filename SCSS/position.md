# Position
Position using `top|left|bottom|right` adjust the position
<html>
<head>
<style>
#block {
    position: relative;
    top: 10px;
    left: 20px;
    width: 200px;
    height: 200px;
    background-color: #f50000;
}
#out_block {
    position: relative;
    top: 100px;
    left: 50px;
    width: 300px;
    height: 300px;
    background-color: black;
}
#in_block {
    position: absolute;
    top: 100px;
    left: 50px;
    width: 100px;
    height: 100px;
    background-color: #0000ff;
}
#out_block1 {
    height: 3000px;
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
</style>
</head>
<body>
<div id="block">
</div>

<div id="out_block">
    <div id="in_block">
    </div>
    <div id="fixed_block">
        FIXED
    </div>
    <div id="sticky_block">
        sticky
    </div>
</div>
</body>
</html>




