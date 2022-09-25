# Selector


- [Selector](#selector)
  - [Reference](#reference)
  - [`*` all](#-all)
  - [`,` and](#-and)
  - [` ` inside](#--inside)
  - [`>` inside](#-inside)
  - [`+` next to](#-next-to)
  - [`xy` more specific](#xy-more-specific)
  - [checkBox `: checked | disabled | enabled |empty | focus`](#checkbox--checked--disabled--enabled-empty--focus)
  - [`[x$^|*~=y]`](#xy)
  - [`:`](#)
    - [`x:first-xxxx`](#xfirst-xxxx)
    - [`x:only|last-child`](#xonlylast-child)
    - [`x:nth-child(x)`](#xnth-childx)
  - [type](#type)
    - [`tag:first|last|only-of-type`](#tagfirstlastonly-of-type)
    - [`x:nth-of-type(x)`](#xnth-of-typex)

## Reference

- [CSS 選擇器](https://reurl.cc/gMRZMQ)
- [SELECTORS](https://reurl.cc/GEneEG)


## `*` all

`*` select all elements

## `,` and

`x , y` : select x and y

## ` ` inside 

`x y` : select all y inside x

## `>` inside

`x > y` : selects all y elements where the parent is a x element.

## `+` next to

`x + y`
```html
<x>...</x>
<y>
    select me, im next to x
</y>
```

## `xy` more specific

`x.className` or `x#id`
```css
p.className{
    /****/
}
p#idName{
    /****/
}
```
```html
<p class="className"></p>

<p id="idName"></p>
```
## checkBox `: checked | disabled | enabled |empty | focus`

select all the `check | disabled | enabled |empty | focus` FORM ELEMENT

## `[x$^|*~=y]`

`[AttributeName]` : select all AttributeName
```css
[id]{
    /**
        Select All id
    **/
}
```

select x name equals y
```css
[id=my-Address]{}
```

`^=y`: starting with 
```css
[id^=L]{
}
```

`$=y` : ending with y
```css
[id$=xyz]{
    /**
        end with xyz
    **/
}
```

`x|=y` attribute `x` = `y` | `x` is starting with `y` | `x` is following by a hyphen `-` (`y-`)
```css
[id|=my]{
    /**
     my
     my1234
     my-xyz
    **/
}
```

`[x~=y]`
```css
[title~=beautiful]{
    /**
      *
      */
}
```
```html
<p lang="it" title="Hello beautiful">Ciao bella</p>
```

## `:`

`:root` : select root element

### `x:first-xxxx`
```css
p:first-child
p::first-letter
p::first-line
```

### `x:only|last-child`

Select b if b is only-child
Select p if p is last-child
```css
b:only-child{}
p:last-child{}
```
```html
<p>
    <b>I'm An Only Child</b>
</p>

<div>
    <p>Im first Child</p>
    <p>Im last Child</p>
</div>
```

`x:hover` : select x only when it is hovered
```css
h1:hover{
    /**
        When cursor is hovering on element
    **/
}
```

### `x:nth-child(x)`

```scss
li:nth-child(x){
    /**
    select x order of li
    **/
}
tr:nth-child(even){
    /**
    select even order of tr
    **/
}
tr:nth-child(odd){
    
}
li:nth-last-child(x){
    /**
    select x counting from end of li
    **/
}
```


## type

Parent can have different children type inside.
```html
<div>
    <h1> ... </h1>  
    <p>  ... </p>
    <b>  ... </b>
</div>
```
- `h1`, `p` and `b` both are first of type

### `tag:first|last|only-of-type`

Select all the first children in element
```css
div:first-of-type{}
/** same as **/
div:nth-of-type(1)
```
```html
<div class="group">
	<h1 class="A">Select first h1</h1>
	<div class="A">Select first div</div>
	<span class="B">Select first span</span>
	<div class="A">second one of span</div>
	<span class="B">second one of span</span>
</div>
```

select only first child of `h1` inside `element.className`
```css
div.group h1:first-of-type{}
```
```html
<div class="group">
	<h1 class="A">first of h1</h1>
	<div class="A">first type of div</div>
	<span class="B">first type of span</span>
	<div class="A">second one of span</div>
	<span class="B">second one of span</span>
</div>
```


the fist type child elements appear in element
```css
.wrap .A:only-of-type{
	background: red;
}
```
```html
<div class="wrap">
	<span class="A">Im only type in A</span>
	<div class="A">div B</div>
	<div class="A">div D</div>
</div>
```

The Following Selects Nothing, coz there is no only-type element existing
```html
<div class="wrap">
    <!-- 2*span , 2*div -->
	<span class="A">span A</span>
	<div class="B">div B</div>
	<span class="C">span C</span>
	<div class="D">div D</div>
</div>
```

### `x:nth-of-type(x)`

- [nth-of-type example](https://developer.mozilla.org/en-US/docs/Web/CSS/:nth-of-type)

```html
<div>
  <span>This is a span.</span>
  <span>This is another span.</span>
  <em>This is emphasized.</em>
  <span>THIS SPAN GETS LIMED</span>
  <del>This is struck through.</del>
  <span>Here is one last span.</span>
</div>
```
```css
span:nth-last-of-type(2) {
  background-color: lime;
}
```