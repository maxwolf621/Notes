# mat-progress

- [mat-progress](#mat-progress)
  - [`mode` property](#mode-property)
  - [mat-progress-spinner (two modes only)](#mat-progress-spinner-two-modes-only)
    - [mat-spinner](#mat-spinner)
    - [mat-progress-spinner bar's thickness `[strokeWidth]`](#mat-progress-spinner-bars-thickness-strokewidth)
    - [mat-progress-spinner size `[diameter]`](#mat-progress-spinner-size-diameter)


```html
<mat-progress-bar [value]="progress"></mat-progress-bar>
<button mat-raised-button (click)="progress = progress - 10">-10</button>
<button mat-raised-button (click)="progress = progress + 10">+10</button>  
```
![](images/03-mat-progress-bar-with-animation.gif)


## `mode` property


determinate (default) : progress bar is controlled by `<mat-progress-bar [value]="....">`


buffer
```html
<!--                                 blueBar     greyBar-->
<mat-progress-bar mode="buffer" [value]="...." bufferValue="...."></mat-progress-bar>   
```
![](images/05-buffer-progress-bar.gif)


indeterminate 
```html
<mat-progress-bar mode="indeterminate"></mat-progress-bar>
```
![](images/04-indeterminate-progress-bar.gif)
query
```html
<mat-progress-bar mode="query"></mat-progress-bar>
```
![](images/06-query-progress-bar.gif)

indeterminate & query don't have `value` and `bufferValue` properties



## mat-progress-spinner (two modes only)

```html
<h4>Progress Spinner</h4>
<mat-progress-spinner [value]="progress"></mat-progress-spinner>
<button mat-raised-button (click)="progress = progress - 10">-10</button>
<button mat-raised-button (click)="progress = progress + 10">+10</button>
```

![](images/08-mat-progress-spinner-basic.gif)

two modes only
indeterminate and determinate

### mat-spinner

```html
<mat-spinner> is same as <mat-progress-spinner mode="indeterminate">
```

```html
<ng-template #loading>
  <mat-grid-tile colspan="2">
    <mat-spinner></mat-spinner>
  </mat-grid-tile>
</ng-template>

<ng-container *ngIf="posts$ | async as posts; else loading">
  <mat-grid-tile *ngFor="let post of posts; let index = index" rowspan="6">
    ...
  </mat-grid-tile>
</ng-container>
```
![](images/14-mat-spinner-before-load-content.gif)



### mat-progress-spinner bar's thickness `[strokeWidth]`


```html
<h4>Spinner Stroke Width</h4>
<mat-progress-spinner value="60" [strokeWidth]="strokeWidth"></mat-progress-spinner>
<button mat-raised-button (click)="strokeWidth = strokeWidth - 1">-1</button>
{{ strokeWidth }}
<button mat-raised-button (click)="strokeWidth = strokeWidth + 1">+1</button> 
```
![](images/10-progress-spinner-stroke-width.gif)
### mat-progress-spinner size `[diameter]`

```html
<h4>Spinner Stroke Width</h4>
<mat-progress-spinner value="60" [diameter]="diameter"></mat-progress-spinner>
<button mat-raised-button (click)="diameter = diameter - 10">-10</button>
{{ diameter }}
<button mat-raised-button (click)="diameter = diameter + 10">+10</button>
```
![](images/11-progress-spinner-diameter.gif)



