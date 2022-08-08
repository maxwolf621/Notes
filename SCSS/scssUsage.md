# ScssUsage

```scss
/**
  * loop and condition
  */
@for $v from x through y {}
@if condition {} @else if condition {} @else {}

/**
  * variable with $
  */
$variable
```


## @mixin and @include

```scss
// SCSS 

// allowing parameter and default val
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