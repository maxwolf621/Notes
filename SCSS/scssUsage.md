# Scss

- [Scss](#scss)
  - [variable scope](#variable-scope)
  - [global variable (`!global`)](#global-variable-global)
  - [@mixin and @include](#mixin-and-include)
  - [@extend](#extend)
  - [@import](#import)
  - [operator](#operator)
  - [functions](#functions)
  - [loops](#loops)
    - [for .. from .. through ...](#for--from--through-)
  - [condition (@if {} @else if {} @else )](#condition-if--else-if--else-)
```scss
/**
  * loop 
  */
@for $v from x through y {}

/**
  * Condition
  */
@if condition {} @else if condition {} @else {}

/**
  * define variable with $
  */
$variable
```

## variable scope

```scss
$myColor: red;

h1 {
  // local variable
  $myColor: green;
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
// SCSS 

// allowing parameter and default value
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

```scss
// SCSS
.header {
  color: grey;
}

/** sub-header extend header **/
.sub-header {
  @extend .header;
  font-size: 40px;
}

// Compiled CSS
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
@import “button.scss” or @import “button”
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

## functions

mix(firstColor, secondColor, proportionOfFirstColor)

```scss
mix(blue, grey, 30%) /*results 30% blue and 70% grey*/

// lighten color #ff000 30%
lighten(#ff0000, 30 ) /*results #ff9999*/

darken(#ff0000, 30 ) /*results #660000*/
```

## loops

### for .. from .. through ...

```scss
@for $x from 1 through 70 {
  .font-size-#{$x} {
    font-size: 0px + $x;
  }
}
```

## condition (@if {} @else if {} @else )


```scss
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