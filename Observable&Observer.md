###### tags: `Angular`
# Observable & Observer
- [Observable](https://blog.huli.tw/2017/12/08/introduction-to-rxjs-observable/)  

- [Observable & Observer](#observable--observer)
  - [Observable & Observer in HTTP Action](#observable--observer-in-http-action)
  - [Syntax](#syntax)
  - [How Data are handled by Observable](#how-data-are-handled-by-observable)

Relationship between Observable and Observer    
![](https://i.imgur.com/f98nz07.png)    
- `Observer` : Subscriber who fetchs data from stream
- `Observable` : Data in the Stream to be subscribed 
  - `Observable` has `time` dimension comparing with array.

## Observable & Observer in HTTP Action

```typescript
/**************************************
 * Obsevables (DATA) to be subscibed  *
 **************************************/
Export class PostService{
  
    //....

    // Observable"s" 
    getAllPost(): Observable<PostPayLoad[]>{
        // Each PostPayload[] Type is observable from resource backend_UR
        return this.http.post<PostPayLoad[]>('${backend_URL}`);
    }
}

/************************
 * Observer subscibes *
 ************************/
Export class SubscribeComponent{
    constructor(private postService : PostService){}
 
    //...

    getAllPostFromService(){
        // observer(subscriber) subscribs observable(getAllPost)
        this.postService.getAllPosts().subscribe
        (
            (obsevablePosts)=>{
                //...
            },(error)=>{
                //...
            }, () =>{
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

