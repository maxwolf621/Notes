###### tags: `Angular`
# Observable
[TOC]

[Tutorial](https://blog.huli.tw/2017/12/08/introduction-to-rxjs-observable/)  
![](https://i.imgur.com/f98nz07.png)  


## Difference btw Observable and Array
Observable has time dimension

## Syntax

![](https://i.imgur.com/207aMtD.png)
Observable.subscribe( observer ) 
> **Observer can be list of functions or object.**
```typescript=
Observable.subscribe(

 (value) =>{ ..} ,
 (error) =>{ ..},
 () => {..}
) 
```

## How Data are handled by Observable
![](https://i.imgur.com/vonIVzP.png)

