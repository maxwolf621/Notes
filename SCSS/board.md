# board-style

```html
<html>
    <head>
        <style>
        p.dotted {border-style: dotted;}
        p.dashed {border-style: dashed;}
        p.solid {border-style: solid;}
        p.double {border-style: double;}
        p.groove {border-style: groove;}
        p.ridge {border-style: ridge;}
        p.inset {border-style: inset;}
        p.outset {border-style: outset;}
        p.none {border-style: none;}
        p.hidden {border-style: hidden;}
        p.mix {border-style: dotted dashed solid double;}
        </style>
    </head>
    <body>
        <p class="dotted">A dotted border.</p>
        <p class="dashed">A dashed border.</p>
        <p class="solid">A solid border.</p>
        <p class="double">A double border.</p>
        <p class="groove">A groove border.</p>
        <p class="ridge">A ridge border.</p>
        <p class="inset">An inset border.</p>
        <p class="outset">An outset border.</p>
        <p class="none">No border.</p>
        <p class="hidden">A hidden border.</p>
        <p class="mix">A mixed border.</p>
    </body>
</html>
```

<html>
<head>
<style>
p.dotted {border-style: dotted;}
p.dashed {border-style: dashed;}
p.solid {border-style: solid;}
p.double {border-style: double;}
p.groove {border-style: groove;}
p.ridge {border-style: ridge;}
p.inset {border-style: inset;}
p.outset {border-style: outset;}
p.none {border-style: none;}
p.hidden {border-style: hidden;}
p.mix {border-style: dotted dashed solid double;}
</style>
</head>
<body>

<p class="dotted">A dotted border.</p>
<p class="dashed">A dashed border.</p>
<p class="solid">A solid border.</p>
<p class="double">A double border.</p>
<p class="groove">A groove border.</p>
<p class="ridge">A ridge border.</p>
<p class="inset">An inset border.</p>
<p class="outset">An outset border.</p>
<p class="none">No border.</p>
<p class="hidden">A hidden border.</p>
<p class="mix">A mixed border.</p>

</body>
</html>


## box-size

By default, the width and height of an element is calculated like this:
```
width + padding + border = actual width of an element
height + padding + border = actual height of an element
```

The box-sizing property allows us to include the padding and border in an element's total width and height.


`box-sizing: border-box` : Defines how the width and height of an element are calculated: should they include padding and borders, or not

<html>
<head>
<style> 
.div1 {
  width: 300px;
  height: 100px;
  border: 1px solid blue;
}
.div2 {
  width: 300px;
  height: 100px;  
  padding: 50px;
  border: 1px solid red;
}
.div3 {
  width: 300px;
  height: 100px;
  border: 1px solid blue;
  box-sizing: border-box;
}
.div4 {
  width: 300px;
  height: 100px;  
  padding: 50px;
  border: 1px solid red;
  box-sizing: border-box;
}
</style>
</head>
<body>

<h3>Without box-sizing</h3>

<div class="div1">This div is smaller (width is 300px and height is 100px).</div>
<br>
<div class="div2"> width is also 300px and height is 100px. with specifying the padding 50px box is bigger</div>

<h3>With box-sizing</h3>

<div class="div3">Both divs are the same size now!</div>
<br>
<div class="div4">with padding 50px box still remain its size</div>

</body>
</html>



