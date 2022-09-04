# box model margin and padding

## Example
- [padding](https://www.w3schools.com/css/css_padding.asp)
- [box model](#box-model)
  - [Parameter (Shorthand Property)](#parameter-shorthand-property)
  - [margin special parameter](#margin-special-parameter)
    - [`auto`](#auto)
    - [`inherit`](#inherit)


![圖 1](../images/50ef21da376b8b7f58c417d4c305e52bd0142413af5f86cf3368a5c6643d127e.png)  
- Content :The content of the box, where text and images appear   
- Padding(**transparent**) : Clears an area around the content.  
- Border : A border that goes around the padding and content  
- Margin(**transparent**) : Clears an area outside the border.   


## Parameter (Shorthand Property)

```scss
padding|margin : top , right , bottom, left
```

When `[padding|margin]` has three values : `top, right , bottom`
When `[padding|margin]` has two values `1,2` means `top=1, right=2`
When `[padding|margin]` has one values `1` : `top=1` , `right=1` , `bottom=1`, `left=1`


<!DOCTYPE html>
<html>
<head>
<style>
div.padding {
  width: 300px;
  padding: 100px;
  box-sizing: border-box;
  background-color: grey;
}
</style>
</head>
<body>

<h2>Padding Example<h2>
<div class="padding">The width of this div remains at 300px, in spite of the 50px of total left and right padding, because of the box-sizing: border-box property.
</div>

</body>
</html>



## margin special parameter (inherit and auto)


<html>
<head>
<style>
#auto {
  width: 300px;
  margin: auto;
  border: 1px solid yellow;
}
div.parent{
  border : 1px solid red;
  margin-left : 100px;
  margin-top : 50px;
}
p.child{
  border: 1px solid gold;
  padding-top : 20px;
  margin-left : inherit;
  margin-top : inherit;
}
</style>
</head>
<body>

<h2>Use of margin: auto</h2>
<p>You can set the margin property to auto to horizontally center the element within its container. The element will then take up the specified width, and the remaining space will be split equally between the left and right margins:</p>

<div id="auto">
This div will be horizontally centered because it has margin: auto;
</div>

<h2>Use of the inherit value</h2>
<p>Let the left margin be inherited from the parent element:</p>


<div class="parent">
<p class="child">
This paragraph has an inherited left margin (from the div element).
</p>
</div>


</body>
</html>

