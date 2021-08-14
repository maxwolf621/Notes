###### tags: `Angular`
# [Observable](https://blog.huli.tw/2017/12/08/introduction-to-rxjs-observable/)  

在 Reactive Programming 裡面，最重要的兩個東西叫做 Observable 跟 Observer

每一個Observable就是一個Stream也就是下圖的`[data]`
每一個`[data]`的內容都不太一樣   
![](https://i.imgur.com/f98nz07.png)    


## Difference btw Observable and Array
Observable has `time` dimension

## Syntax
![](https://i.imgur.com/207aMtD.png)  
`Observable.subscribe( observer )` 

**Observer can be list of functions or object.**
```typescript
Observable.subscribe(

 (value) =>{ ..} ,
 (error) =>{ ..},
 () => {..}
) 
```

## How Data are handled by Observable
![](https://i.imgur.com/vonIVzP.png)

