# Scss

- [Scss](#scss)
  - [`$` variable](#-variable)
  - [`@for` and `@if`](#for-and-if)
  - [variable scope](#variable-scope)
  - [global variable (`!global`)](#global-variable-global)
  - [@mixin and @include](#mixin-and-include)
  - [@extend](#extend)
  - [@import](#import)
  - [operator](#operator)
  - [functions(min, lighten, darken)](#functionsmin-lighten-darken)

- [exercise](https://gist.github.com/fredsiika/2958726da1f94a9bd447f4f7bd03a852#use-if-and-else-to-add-logic-to-your-styles)

## `$` variable

```scss
/**
  * define variable with $
  */
$varName


// example 
$main-fonts: Arial, sans-serif;
$headings-color: red;

h1 {
  font-family: $main-fonts;
  color: $headings-color;
}
```

## `@for` and `@if`

```scss
/**
  * loop 
  */
@for $v from x through y {}

// for example 
@for $x from 1 through 70 {
  .font-size-#{$x} {
    font-size: 0px + $x;
  }
}
```

```scss
/**
  * Condition
  */
@if condition {...} 
@else if condition {...} 
@else {...}

// for example 
$bg: pink;
$bg-mobile: red;
p {
  @if $bg == pink {
    color: blue;
  } @else if $bg-mobile == red {
    color: green;
  } @else {
    color: grey;
  }
}
```

## variable scope

```scss
// global variable
$myColor: red;

h1 {
  // local variable
  $myColor: green;
  
  //...
  
  // green
  color: $myColor;
}
p {
  // red
  color: $myColor;
}
```

## global variable (`!global`)

```scss
$myColor: red;

h1 {
  $myColor: green !global;

  // green
  color: $myColor;
}

p {
  // green
  color: $myColor;
}
```

## @mixin and @include

```scss
// $params default val : 10px
@mixin fontSize ($params: 10px){
  font-size: $params;
}

.header{
  @include fontSize(20px);
}

.sub-header{
  @include fontSize(20px);
}

// Compiled CSS
.header {
  font-size: 20px;
}

.sub-header {
  font-size: 20px;
}

```

## @extend

`@extend Attribute`: I use that attribute setup

```scss
// SCSS
.header {
  color: grey;
}

.sub-header {
  @extend .header;
  font-size: 40px;
}
```

Compiled CSS
```css
.header, .sub-header {
  color: grey;
}

.sub-header {
  font-size: 40px;
}
```

## @import 

SCSS allow you to import other SCSS stylesheet into a SCSS file using @import

```scss
@import "button.scss"
```
or
```scss
@import "button"
```

## operator 

Addition(+)
Subtraction(-)
Division(/)
Multiplication(*) e.t.c
```scss
// SCSS
@mixin top-margin ($margin){
  margin-top: 30px + $margin;
}

.container{
  width: 800px - 80px;
  @include top-margin(10px);
}

//Compiled CSS

.container {
  width: 720px;
  margin-top: 40px;
}
```

## functions(min, lighten, darken)

`mix(firstColor, secondColor, proportion_of_firstColor)`
```scss
mix(blue, grey, 30%) /* 30% blue and 70% grey*/
``` 

```scss
// lighten color #ff000 30%
lighten(#ff0000, 30 ) /*results #ff9999*/
// darken color #ff000 30%
darken(#ff0000, 30 ) /*results #660000*/
```
