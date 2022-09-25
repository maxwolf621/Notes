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
div#parent1 {
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
div#parent2 {
  float: left;
  padding: 15px; 
}
.divP1 {
  background: red;
}
.divP2 {
  background: yellow;
}
.divP3 {
  background: green;
}
div.normalDiv{
  border : 1px solid red;
  text-align : center;
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

<h3> overflow : auto </h3>
<div class="overflow-auto common">You can use the overflow property when you want to have better control of the layout. The overflow property specifies what happens if content overflows an element's box.</div>

<h3> overflow : hidden </h3>
<div class="overflow-hidden common">You can use the overflow property when you want to have better control of the layout. The overflow property specifies what happens if content overflows an element's box.</div>

<h3> overflow : scroll </h3>
<div class="overflow-scroll common">You can use the overflow property when you want to have better control of the layout. The overflow property specifies what happens if content overflows an element's box.</div>

<h3>overflow :overflow-x and overflow-y setup </h3>
<div class="overflow-xy common">You can use the overflow property when you want to have better control of the layout. The overflow property specifies what happens if content overflows an element's box.</div>

<h3>float Property</h3> 

<p><b>float:left</b> - The element floats to the left of its container</p>
<p><b>float:right</b> - The element floats to the right of its container
</p>
<p><b>float:none</b>The element does not float (will be displayed just where it occurs in the text). This is default</p>
<p><b>inherit</b> - The element inherits the float value of its parent</p>

<h4>Example</h4>
<strong> sentence sentence sentence</strong>
<div id="parent1" class="div1">float:right 1</div>
<div id="parent1" class="div2">Div 2</div>
<div id="parent1" class="div3">Div 3</div>
<strong> sentence sentence sentence sentence sentence sentence sentence sentence sentence sentence sentence sentence sentence sentence sentence sentence sentence sentence </strong>
<br>
<div id="parent2" class="divP1">float:left 1</div>
<div id="parent2" class="divP2">Div 2</div>
<div id="parent2" class="divP3">Div 3</div>
<content> sentence sentence sentence sentence sentence sentence  sentence sentence sentence sentence sentence sentence sentence</content>
<br>
<br>
<div class="normalDiv">Div 1</div>
<div class="normalDiv">Div 2</div>
<div class="normalDiv">Div 3</div>
</body>
</html>


