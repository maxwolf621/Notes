# Overflow & flow

[w3 overflow](https://www.w3schools.com/css/css_overflow.asp)
[w3 flow](https://www.w3schools.com/css/css_float.asp)
- [Overflow & flow](#overflow--flow)

<html>
<head>
<style>
div.common{
    width: 300px;
    height: 80px;
    border: 1px solid black;
    margin-top : 10px;
}
div.overflow-auto{
    overflow: auto;
}
div.overflow-hidden{
    overflow : hidden;
}
div.overflow-scroll{
    overflow: scroll;
}
div.overflow-xy {
    overflow-x: auto; /* Hide horizontal scrollbar */
    overflow-y: auto; /* Add vertical scrollbar */
}
div.overflow-visible {
  width: 200px;
  height: 65px;
  border: 1px solid;
  overflow: visible;
}
div#parent {
  float: right;
  padding: 15px; 
}
.div1 {
  background: red;
}
.div2 {
  background: yellow;
}
.div3 {
  background: green;
}
</style>
</head>
<body>

<h3>overflow : visible (default) </h3>
<div class="overflow-visible">
You can use the overflow property when you want to have better control of the layout. The overflow property specifies what happens if content overflows an element's box.
</div>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

<h3> overflow : auto </h3>
<div class="overflow-auto common">You can use the overflow property when you want to have better control of the layout. The overflow property specifies what happens if content overflows an element's box.</div>

<h3> overflow : hidden </h3>
<div class="overflow-hidden common">You can use the overflow property when you want to have better control of the layout. The overflow property specifies what happens if content overflows an element's box.</div>

<h3> overflow : scroll </h3>
<div class="overflow-scroll common">You can use the overflow property when you want to have better control of the layout. The overflow property specifies what happens if content overflows an element's box.</div>

<h3>overflow :overflow-x and overflow-y setup </h3>
<div class="overflow-xy common">You can use the overflow property when you want to have better control of the layout. The overflow property specifies what happens if content overflows an element's box.</div>

<h3>Float Next To Each Other</h3>

<p>In this example, the three divs will float next to each other.</p>
<p>left - The element floats to the left of its container</p>
<p>right - The element floats to the right of its container
</p>
<p>none - The element does not float (will be displayed just where it occurs in the text). This is default</p>
inherit - The element inherits the float value of its parent</p>

<strong> this is sentence above element with flow property</strong>
<div id="parent" class="div1">Div 1</div>
<div id="parent" class="div2">Div 2</div>
<div id="parent" class="div3">Div 3</div>
<strong> this is the sentence under element with flow property </strong>
<p> this is the sentence under element with flow property </p>
<p> this is the sentence under element with flow property </p>
<p> this is the sentence under element with flow property </p>

</body>
</html>


