# CallBack Hell

[CallBackHell](https://stackoverflow.com/questions/25098066/what-is-callback-hell-and-how-and-why-does-rx-solve-it)   
[](https://ithelp.ithome.com.tw/articles/10206555)

The problem in Javascript is that the only way to "freeze" a computation and have the "rest of it" execute latter (asynchronously) is to put "the rest of it" inside a callback.  
```javascript
x = getData();
y = getMoreData(x);
z = getMoreData(y);
```

What happens if now I want to make the `getData` functions **asynchronous** ? 

In Javascript, the only way would be to rewrite everything that touches an async computation using continuation passing style:
```javascript
getData(function(x){
    getMoreData(x, function(y){
        getMoreData(y, function(z){ 
            ...
        });
    });
});
```

For Example 
```javascript
getPerson()
  .map(person => getPlanet(person))
  .map(planet => getGalaxy(planet))
  .mergeAll()
  .subscribe(galaxy => console.log(galaxy));

//With the mergeMap AKA flatMap operator you could make it more succinct  
getPerson()
  .mergeMap(person => getPlanet(person))
  .mergeMap(planet => getGalaxy(planet))
  .subscribe(galaxy => console.log(galaxy));
```

**Each callback is nested. Each inner callback is dependent on its parent. This leads to the "pyramid of doom" style of callback hell.**
- `promise`s are another way to avoid callback hell, but `promise`s are eager, not lazy like observables and (generally speaking) you cannot cancel them as easily.

## When (in what kind of settings) does the "callback hell problem" occur?

When you have lots of callback functions in your code, **It gets harder to work with them the more of them you have in your code and it gets particularly bad when you need to do loops**, try-catch blocks and things like that.

For example, in JavaScript the only way to execute a series of asynchronous functions where one is run after the previous returns is using a recursive function. 
You can't use a for loop.
```javascript
// we would like to write the following
for(var i=0; i<10; i++){
    doSomething(i);
}
blah();

// Instead, we might write as Reactive Programming
function loop(i, onDone){
    if(i >= 10){
        onDone()
    }else{
        doSomething(i, function(){
            loop(i+1, onDone);
        });
     }
}
loop(0, function(){
    blah();
});
```

### Why does it occur ?

It occurs because in JavaScript the only way to delay a computation so that it runs after the asynchronous call returns is to put the delayed code inside a callback function. 

You cannot delay code that was written in traditional synchronous style so you end up with nested callbacks everywhere.

### Can "callback hell" occur also in a single threaded application?

- **Asynchronous programming has to do with concurrency while a single-thread has to do with parallelism.** 

The two concepts are actually not the same thing.   

- You can still have concurrent code in a single threaded context. 
    > In fact, JavaScript, the queen of callback hell, is single threaded.


In Python we can implement that previous loop example with something along the lines of:
```python
def myLoop():
    for i in range(10):
        doSomething(i)
        yield

myGen = myLoop()
```
- the `yield` pauses our for loop until someone calls `myGen.next()`. 
    - The important thing is that we could still write the code using a for loop, without needing to turn out logic "inside out" like we had to do in that recursive loop function.