###### tags: `Angular`
# Obserable 
- [Observable](https://blog.huli.tw/2017/12/08/introduction-to-rxjs-observable/)  

Relationship between Observable and Observer    
![](https://i.imgur.com/f98nz07.png)    
- `Observer` : Subscriber who fetchs data from stream
- `Observable` : Data in the Stream
- Comparing with array, `Observable` has `time` dimension  

```typescript
// Observable data 
Export class PostService{
    //....

    getAllPost(): Observable<PostPayLoad[]>{
        // Each PostPayload[] Type is observable from resource backend_UR
        return this.http.post<PostPayLoad[]>('${backend_URL}`);
    }
}
```
```typescript
// Observer subscribs 
Export class SubscribeComponent{
    constructor(private postService : PostService){}
 
    //...

    getAllPostFromService(){
        // observer subscribs
        this.postService.getAllPosts().subscribe
        (
            (posts)=>{
                //...
            },(error)=>{
                //...
            }
        )
    }
}
```
 
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

