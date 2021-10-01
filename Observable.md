###### tags: `Angular`
# [Observable](https://blog.huli.tw/2017/12/08/introduction-to-rxjs-observable/)  

在 Reactive Programming 裡面，最重要的兩個東西叫做 Observable 跟 Observer

Observable 以及 Observer
![](https://i.imgur.com/f98nz07.png)    

Observable : 後端每一個資料
```typescript

Export class PostService{

    //....

    // Each PostPayload[] dataType is observable from resource backend_URL
    getAllPost():Observable<PostPayLoad[]>{
        return this.http.post<PostPayLoad[]>('${backend_URL}`);
    }
}
```
Observer : 取得某特定後端資料的人
```typescript

// inject dependency
constructor(private postService : PostService){}

//...

// observer 
getAllPostFromService(){
    this.postService.getAllPosts().subscribe
    (
        (posts)=>{
            //...
        },(error)=>{
            //...
        }
    )
}
```

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

