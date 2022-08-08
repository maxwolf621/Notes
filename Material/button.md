# Button 
[[Angular Material完全攻略] Day 04 - MatButton、MatButtonToggle和MatRipple](https://ithelp.ithome.com.tw/articles/10193055)

- [Button](#button)
  - [mat-button](#mat-button)
  - [mat-raised-button](#mat-raised-button)
  - [Button with mat-icon](#button-with-mat-icon)
  - [mat-icon-button](#mat-icon-button)
  - [mat-icon-button with mat-raised-button](#mat-icon-button-with-mat-raised-button)
  - [mat-fab](#mat-fab)
    - [mat-mini-fab](#mat-mini-fab)
  - [mat-button-toggle](#mat-button-toggle)

## mat-button

```html
<button mat-button color=".." >

<button mat-button color="primary">Primary</button>
<button mat-button color="accent">Accent</button>
<button mat-button color="warn">Warn</button>
<button mat-button disabled>Disabled</button>
<a mat-button>Link</a> 
```

![image](images/02-mat-button-color.gif)

## mat-raised-button

```html
<button mat-raised-button color="primary">Primary</button>
<button mat-raised-button color="accent">Accent</button>
<button mat-raised-button color="warn">Warn</button>
<button mat-raised-button disabled>Disabled</button>
<a mat-button>Link</a>
```

![gif](images/03-mat-raised-button.gif)


## Button with mat-icon

```html
<button mat-raised-button color="primary"><mat-icon>thumb_up</mat-icon> 我有Icon</button>
```

![圖 1](images/8591e3c84c5973ac5e77b88f2d264c2f96913ba0c9072157872c2b1f93f8419c.png)  


## mat-icon-button

```html
<button mat-icon-button color="primary"><mat-icon>thumb_up</mat-icon></button>
```
![圖 2](images/535c65341e1ff801342f45694d0fe3cc2d3c4569615ec1f928ea05905a6d9245.png)  


## mat-icon-button with mat-raised-button

```html
<button mat-raised-button 
        mat-icon-button 
        color="primary">
                <mat-icon>thumb_up</mat-icon>
</button>
```
![圖 3](images/9d942399bcae6d023262fed8d96d887ff2da3efa15f2c63e304f07f378081304.png)  




## mat-fab

```html
<button mat-fab>
  <mat-icon>thumb_up</mat-icon>
</button>
<button mat-fab color="primary">
  <mat-icon>thumb_up</mat-icon>
</button>
<button mat-fab color="accent">
  <mat-icon>thumb_up</mat-icon>
</button>
<button mat-fab color="warn">
  <mat-icon>thumb_up</mat-icon>
</button>
<button mat-fab disabled>
  <mat-icon>thumb_up</mat-icon>
</button>
```

![](images/08-mat-fab.gif)

### mat-mini-fab

```html
<button mat-mini-fab>
  <mat-icon>thumb_up</mat-icon>
</button>
<button mat-mini-fab color="primary">
  <mat-icon>thumb_up</mat-icon>
</button>
<button mat-mini-fab color="accent">
  <mat-icon>thumb_up</mat-icon>
</button>
<button mat-mini-fab color="warn">
  <mat-icon>thumb_up</mat-icon>
</button>
<button mat-mini-fab disabled>
  <mat-icon>thumb_up</mat-icon>
</button>
```


## mat-button-toggle

```html
<mat-button-toggle-group #formatAlignGroup="matButtonToggleGroup">
  
  <!-- formatAlignGroup.value= left/center/right/justify-->
  <mat-button-toggle value="left">
    <mat-icon>format_align_left</mat-icon>
  </mat-button-toggle>
  
  <!-- 預設被選取 -->
  <mat-button-toggle value="center" checked="true">
    <mat-icon>format_align_center</mat-icon>
  </mat-button-toggle>
  
  <mat-button-toggle value="right">
    <mat-icon>format_align_right</mat-icon>
  </mat-button-toggle>

  <!-- disabled button toggle -->
  <mat-button-toggle value="justify" disabled>
    <mat-icon>format_align_justify</mat-icon>
  </mat-button-toggle>
</mat-button-toggle-group>
<div>對齊方式為：{{ formatAlignGroup.value }}</div>

<!-- 加上multiple，則裡面的mat-buttong-toggle可以複選 -->
<!-- 加上vertical="true", 改變排列方式 -->
<mat-button-toggle-group multiple vertical="true">
  <mat-button-toggle value="bold" #buttonToggleBold>
    <mat-icon>format_bold</mat-icon>
  </mat-button-toggle>
  <mat-button-toggle value="italic" checked="true" #buttonToggleItalic>
    <mat-icon>format_italic</mat-icon>
  </mat-button-toggle>
  <mat-button-toggle value="underlined" checked="true" #buttonToggleUnderlined>
    <mat-icon>format_underlined</mat-icon>
  </mat-button-toggle>
</mat-button-toggle-group>
<div>粗體：{{ buttonToggleBold.checked }}、斜體：{{ buttonToggleItalic.checked }}、底線：{{ buttonToggleUnderlined.checked }}</div>
```


![](images/11-button-toggle-group.gif)