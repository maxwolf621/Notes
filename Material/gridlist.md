# Grid List

- [Grid List](#grid-list)
  - [mat-grid-list](#mat-grid-list)
  - [set each tile's height (`rowHeight`)](#set-each-tiles-height-rowheight)
  - [rowspan and colspan of tile](#rowspan-and-colspan-of-tile)
  - [gap of each tile](#gap-of-each-tile)
  - [mat-grid-tile-header && mat-grid-tile-footer](#mat-grid-tile-header--mat-grid-tile-footer)


## mat-grid-list

```html
<!-- 3 cols in a row -->
<mat-grid-list cols="3">
  <mat-grid-tile style="background: red">
    Tile 1
  </mat-grid-tile>
  <mat-grid-tile style="background: green">
    Tile 2
  </mat-grid-tile>
  <mat-grid-tile style="background: blue">
    Tile 3
  </mat-grid-tile>
<!-- to second row -->
 <mat-grid-tile style="background: yellow">
    Tile 4
  </mat-grid-tile>
</mat-grid-list>
```
![圖 7](images/1bad1160f786e73ec962513a020298c5d0a737fd6fbd6a0f845427e91ddc33dd.png)  


## set each tile's height (`rowHeight`)

```html
<mat-grid-list cols="3" rowHeight="100px">
```
![圖 8](images/6693be4b51963bfb3010c5fefd60bdabca0254df6de8de12e4aaceb441365e14.png)  


## rowspan and colspan of tile

```html
<mat-grid-tile style="background: red" colspan="2">
  Tile 1(橫幅廣告)
</mat-grid-tile>
<mat-grid-tile style="background: green" rowspan="5">
  Tile 2(右邊清單資訊)
</mat-grid-tile>
<mat-grid-tile style="background: blue" colspan="2" rowspan="3">
  Tile 3
</mat-grid-tile>
<mat-grid-tile style="background: yellow" colspan ="2">
  Tile 4(下方橫幅廣告)
</mat-grid-tile>
```
![圖 11](images/e1e413b7f83ac0b287a41f7d70831967187c4582aae83fce295cda38abfebe05.png)  


## gap of each tile

```html
<mat-grid-list cols="3" rowHeight="100px" gutterSize="20px">
```
![圖 9](images/0e324255e3ce8d9ccd222a33bc6bf7212218469ac15361928248a3ae75a85456.png)  


## mat-grid-tile-header && mat-grid-tile-footer

```html
<mat-grid-tile style="background: green" rowspan="5">
  <mat-grid-tile-header>
    <h3 mat-line>功能清單</h3>
    <span mat-line>選擇你要的</span>
    <mat-icon>list</mat-icon> <!-- place at the right -->
  </mat-grid-tile-header>

  <mat-grid-tile-footer>
    <span mat-line>生是IT人</span>
    <span mat-line>死是IT魂</span>
    <span mat-line>但我不想死</span>
    <mat-icon>thumb_up</mat-icon> 
  </mat-grid-tile-footer>
  Tile 2(右邊清單資訊)
</mat-grid-tile>
```
![圖 10](images/7395708e31663a44057fdbde2da602a6dc301c8da925cb3f3dda98b166a33a5d.png)  
