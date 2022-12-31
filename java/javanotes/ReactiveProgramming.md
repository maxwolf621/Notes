# Reactive Programming 

[Reactive Programming](https://ithelp.ithome.com.tw/articles/10220286)        
[Def of Reactive](https://www.gushiciku.cn/pl/pC60/zh-tw)      
[RxJs](https://jonny-huang.github.io/angular/training/27_rxjs_2/)     
[30 天精通 RxJS ](https://ithelp.ithome.com.tw/articles/10186465)   
[[Android 十全大補] RxJava](https://ithelp.ithome.com.tw/articles/10223619)   
[RxJava2](https://www.vogella.com/tutorials/RxJava/article.html)     
[Operators RxJS](https://rxjs.dev/api/operators)  
[RxJava VS Reactor](https://www.educba.com/rxjava-vs-reactor/#:~:text=RxJava%202%20separates%20both%20the,error%20related%20to%20backtrack%20pressure.)  
[Operators for Reactor](https://blog.csdn.net/FightingITPanda/article/details/119177837)  
[Reactor with Diagram](https://ithelp.ithome.com.tw/articles/10269758?sc=iThomeR)    

- [Reactive Programming](#reactive-programming)
  - [Functional Programming](#functional-programming)
  - [RxJS](#rxjs)
  - [LINQ](#linq)
  - [Observable](#observable)
  - [Roles in Reactive Programming](#roles-in-reactive-programming)
      - [Publisher](#publisher)
      - [Subscriber](#subscriber)
      - [Subscription](#subscription)
      - [Hot and Cold](#hot-and-cold)
      - [Processor(deprecate)](#processordeprecate)
  - [backpressure](#backpressure)
      - [backpressure Strategies](#backpressure-strategies)
  - [Java Reactor](#java-reactor)
    - [Operators for Creating Data Stream](#operators-for-creating-data-stream)
      - [zip](#zip)
      - [never](#never)
      - [generate && create](#generate--create)
      - [Backpressure handling strategies](#backpressure-handling-strategies)
        - [`create` vs `generate`](#create-vs-generate)
      - [delay && interval](#delay--interval)
      - [justOrEmpty](#justorempty)
      - [defaultIfEmpty](#defaultifempty)
  - [operator for converting](#operator-for-converting)
      - [`mergeXXX` and `concateXXX`](#mergexxx-and-concatexxx)
      - [flatMap](#flatmap)
      - [zip , zipWith , zipMap](#zip--zipwith--zipmap)
      - [join](#join)
      - [take](#take)
    - [blockFirst and blockLast](#blockfirst-and-blocklast)
      - [defer](#defer)
      - [Buffer and Window](#buffer-and-window)
      - [filter and distinct](#filter-and-distinct)
      - [reduce](#reduce)
  - [Operators while converting data stream](#operators-while-converting-data-stream)
      - [error](#error)
      - [thenXXX](#thenxxx)
  - [Reactor Thread](#reactor-thread)
    - [`subscribeOn` and `publisherOn`](#subscribeon-and-publisheron)
      - [subscribeOn](#subscribeon)
        - [publishOn](#publishon)
    - [Condition of `SubscribeOn` and `publishOn`](#condition-of-subscribeon-and-publishon)
  - [scheduler for `flatMap`](#scheduler-for-flatmap)
    - [Parallel Flux](#parallel-flux)


Reactive programming is an **asynchronous** programming paradigm concerned with data streams and the propagation of change.      
> 基於事件流(STREAM)、無阻塞(NON-BLOCK)、非同步的(ASYNC)，使用反應式程式設計不需要編寫底層的並行程式碼。並且由於其宣告式編寫程式碼的方式，使得非同步程式碼易讀且易維護。

- 反應式程式設計與Java `Stream`有眾多相似之處，且提供了相互轉化的API，但是**Reactive Programming更加強調非同步非阻塞，通過`onComplete`等註冊監聽的方式避免阻塞，同時支援`delay`、`interval`等特性。而`Stream`本質上是對集合的並行(Concurrency)處理，並不是NON-BLOCKING**

It becomes possible to express static (e.g. arrays) or dynamic (e.g. event emitters) data streams **with ease(easily)** via the employed programming language(s) using Reactive Programming.
- For Example :: 
`a + b = c`的場景，在傳統程式設計方式下如果a、b發生變化，那麼我們需要重新計算a+b來得到c的新值。 而Reactive programming中，我們不需要重新計算，a、b的變化事件會觸發c的值**自動更新**(藉由訂閱`subscribe`)   


Reactive Programming類似於Observer Pattern (PUSH (PUBLISHER) /SUBSCRIBE模式)  
- 藉由Publisher PUSH 事件，程式碼邏輯作為 Subscriber 基於事件(EVENT)進行處理(Subscribe)，並且是**非同步處理**的。  

反應式程式設計中，最基本的處理單元是Data Streams(**Streams are actually immutable data types，對Stream進行操作只會返回新的Stream**)中的事件。**Stream中的事件包括正常事件(物件代表的資料、Stream結束標識(`|`))和異常事件(異常物件，例如Exception)**。同時，只有當訂閱者第一次釋出者(when subscriber subscribes the events)，釋出者釋出的Event才會被消費(Consume)，後續的訂閱者只能從訂閱點開始消費，但是我們可以通過背壓、流控等方式控制消費。
```
Publisher -------event_1 -- event_2 -- event_3 -- | 
              ^
              | 
           subscribe
              |
Subscriber ---' --- event_1 -- event_2 -- event_3 -- |
```
 
## Functional Programming

可以**簡單把 functional定義為輸入相同會造成相同輸出，沒有SIDE AFFECT的產生**

- SIDE EFFECT : 在這裡我們可以想成任何會讓**輸出不固定（非預期）的行為**，像是發送 network 請求（請求可能失敗、錯誤、超時）、File IO ... etc ...

## RxJS
- RxJS最初是由 Microsoft 開源
  > The Reactive Extensions (Rx) is a library for composing asynchronous and event-based programs using observable sequences and LINQ-style query operators

## LINQ

LINQ（Language Intergrated Query）是一套由程式語言定義的查詢  

在程式當中時常有需要**查詢、整合、統計、過濾等對資料進行一連串操作的需求**，但同時也會遇到一些問題：
> **資料來源不一，hard-coded 程式碼難以復用**下`SQL`的話在程式中難以除錯（因為都是字串），而且只有資料庫的資料才能被SQL查詢所以MS推出的這套技術，可以透過一連串的operator來操作資料  

For example ::
```java
// string collection
IList<string> stringList = new List<string>() { 
    "C# Tutorials",
    "VB.NET Tutorials",
    "Learn C++",
    "MVC Tutorials" ,
    "Java" 
};

// VIA LINQ Query Syntax
var result = stringList.Where(s => s.Contains("Tutorials"));

```
- 而這個概念被推廣後變成了 ReactiveX，除了 JavaScript 之外還有多個語言實作版本，像是 RxJava RxSwift 等等。
- 透過RxJS，我們可以很方便地管理資料流。

## Observable
從前端的角度來想，其實 UI 的操作也有點像資料庫，我們查詢、過濾有興趣的事件（`click`, `onChange` ...），對事件作操作與轉換（`click` -> ajax call），最後反應到 UI 上。

**整個過程並不一定是同步的**，例如我們並不知道使用者何時會按下按鈕，何時會有offline event，所以想要像陣列一樣可以透過各種operator來簡化操作的話，勢必就要一層抽象。

這一連串的操作可以抽象成`Observable` object，當有Event發生時，`Observable`會作出對應的Operators。

For Example :: 在`onClick`當中，使用RxJS可以這麼寫：
```typescript
Observable.fromEvent(document, 'click')
          .filter(e => e.target === myButton)
          .switchMap(() => ajax('/api/list').map(/* operation */))
```
- 將所有的資料來源（不管是事件、請求、單純的陣列等）**用同一個介面操作(減少HARD CODE)**，大幅簡化了針對特定型別處理的時間。

```typescript
/**
  * RxJS 6 已經全部改用 pipe 的方式來組織 Observable，但概念一樣是相通的。
  */
const input$ = Observable.fromEvent(input, 'input');
input$
  .filter(e => e.target.value.length > 3)
  .debounceTime(300) 
  .switchMap(e => ajax('/api/search?q=' + e.target.value) 
    .map(res => res.data) 
  )
  .retry(4) 
  .subscribe(list => {  //實際讓input$開始傳送資料
     // render your data
  })
```
- 這個 `Observable` object透過一連串的 operators 完成了幾件事：
  1. 接收 onInput 事件
  2. 過濾任何長度小於 3 的字串(`e.target.value.length > 3`)
  3. debounce 300 毫秒(`debounceTime(300)`)
  4. 轉換成另一個`Observable`發送請求。（在 rxjs 透過 switchMap 等 high order observable 可以幫你將 Observable 打平）
  5. 將Response `map` 成想要的資料
  6. 如果有錯誤會`retry(4)`次
  7. `subscribe`訂閱`input$`資料
## Roles in Reactive Programming
#### Publisher

The `Publisher` interface emits events to subscribers based on the request sent.

```java
public interface Publisher<T> 
{
    public void subscribe(Subscriber<? super T> s);
}
```

#### Subscriber

The `Subscriber` interface listens and receives events from the Publisher interface.      
- It has four methods to handle the response from the Publisher interface.    
```java
public interface Subscriber<T> 
{
    public void onSubscribe(Subscription s);
    public void onNext(T t);
    public void onError(Throwable t);
    public void onComplete();
}
```

#### Subscription

The `Subscription` interface defines a one-to-one **RELATIONSHIP between the `Publisher` and `Subscriber` interfaces.**
```java
public interface Subscription<T> 
{
    public void request(long n);
    public void cancel();
}
```

#### Hot and Cold 

The Rx family of reactive libraries distinguishes two broad categories of reactive sequences HOT and COLD. 

> **This distinction mainly has to do with how the reactive stream reacts to subscribers**

A Cold sequence starts anew for each Subscriber, **including at the source of data.** 
- FOR EXAMPLE, if the source wraps an HTTP call, a new HTTP request is made for each subscription.

A Hot sequence does not start from scratch for each Subscriber. Rather, **late subscribers receive signals emitted after they subscribed.**

Note, however, that some hot reactive streams can `cache` or `replay` the history of emissions totally or partially.    

From a general perspective, **a hot sequence can even emit when no subscriber is listening** (an exception to the **nothing happens before you subscribe rule**).

#### Processor(deprecate)

The Processor interface represents the processing stage containing both the `Publisher` and the `Subscriber`.
```java
public interface Processor<T, R> extends Subscriber<T>, Publisher<R>{

}
```

## backpressure

- **[What is backpressure](https://medium.com/@jayphelps/backpressure-explained-the-flow-of-data-through-software-2350b3e77ce7)**
- **[stackoverflow](https://stackoverflow.com/questions/46518006/what-does-the-term-backpressure-mean-in-rxjava)**

Due to the non-blocking nature of Reactive Programming, **the server doesn't send the complete stream at once.** It can push the data concurrently as soon as it is available.  

**This will cause emitters of information overwhelm consumers with data they are not able to process such situation can be called backpressure.**

#### backpressure Strategies

To handle backpressure we might use these methods

- **Control** the producer (slow down/speed up is decided by consumer)
  - the best option
- **Buffer** (accumulate incoming data spikes temporarily)
  - buffering is dangerous if unbounded
- **Drop** (sample a percentage of the incoming data)
  - often combined with buffering too

## Java Reactor 

Reactor is an implementation of the Reactive Programming paradigm

[METHODS Example](https://www.gushiciku.cn/pl/g08e/zh-tw)

### Operators for Creating Data Stream 

`just`, `fromIterable`, `empty`
```java
/**
 * {@code just} 
 * Create data stream  
 */
Flux<String> seq1 = Flux.just("foo", "bar", "foobar");

// or 
List<String> iterable = Arrays.asList("foo", "bar", "foobar");
Flux<String> seq2 = Flux.fromIterable(iterable);


// empty，建立一個不包含任何資料的data stream，不會無限執行。
Mono<String> mono = Mono.empty();
```

```java
/**
 * <p> error，建立一個訂閱後立刻返回異常的資料流 </p>
 */
Mono.just("mono")
      //連線一個包含異常的Mono
      .concatWith(Mono.error(new Exception("myExc")))
      //異常監聽
      .doOnError(error -> System.out.println("錯誤: "+ error))
      //在發生異常時將其'-excRet'傳遞給訂閱者
      .onErrorReturn("-excRet")
    .subscribe(System.out::println);
    
    /*
      最終輸出: 
      mono
      錯誤: java.lang.Exception: myExc-excRet
    */
```

```java
/**
 * {@code create}
 * <p> Dynamically create mono / flux</p>
 */
Flux.create(sink -> {
    for (int i = 0; i < 10; i++) {
        sink.next("現在的次數:" + i);
    }

    sink.complete();
}).subscribe(System.out::println);
```


#### zip

將多個STREAMS合併為一個STREAM，STREAM中的EVENTS會一一對應

```java
/**
  * 1 -> ab 
  * 2 -> cde
  * 3 -> FGHIJ
  */
final Flux<Integer> nums = Flux.just(1, 2, 3);     
final Flux<String> strs = Flux.just("ab", "cde", "fghij");

// flux that zip two fluxes
final Flux<Double> doubles = Flux.zip(
		nums,
		strs,
		(n, s) -> n * s.length() * 2.0
);
```

```java
Flux<String> fnameFlux = Flux.just("Ramesh","Amit","Vijay");
Flux<String> lnameFlux = Flux.just("Sharma","Kumar","Lamba");
Flux<String> deptFlux = Flux.just("Admin","IT","Acc.");

Flux<User> userFlux = Flux.zip(fnameFlux, lnameFlux, deptFlux)
                          .flatMap(dFlux -> 
                                   Flux.just(new User(dFlux.getT1(), dFlux.getT2(), dFlux.getT2()))
                          );

userFlux.subscribe(x -> System.out.println(x));
```

#### never

Create a Flux that will never signal any data, error or completion signal.

```java
Flux.never()
     .doOnEach(signal -> System.out.println(signal.getType()))
     .defaultIfEmpty("DEFAULT")
     .doOnNext(System.out::println)
     .subscribe();
/**
  *  [No output]
  */
```

#### generate && create

`generate`
- 只能用來處理同步(ASYNC)問題，如果你想要在**某特定事件發生**時，再進行 `next` 的話，這類非同步操作就不能使用 `generate`
- 主要有兩個參數
  1. `state` (mutable) 初始值
  2. `sink` 產生邏輯執行`next()`、`error(Throwable)` 或是 `complete()`。
```java
Flux.generate(sink -> {

    sink.next(System.currentTimeMillis());
    /**
     * next第二次會報錯:
     * java.lang.IllegalStateException: More than one call to onNext
     */
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
}).subscribe(out::println);
```
```java
Flux<String> flux =
    Flux.generate( () -> 0,
        (state, sink) -> {
          sink.next("3 x " + state + " = " + 3 * state);
          if (state == 5) sink.complete();
          return state + 1;
        });

flux.subscribe(System.out::println);
```

`create`
![圖 1](images/6a4e9ac77145c19efb689ff740cbd75b91d5d85149f95ebdfd5b77a3a46a8685.png)  

#### Backpressure handling strategies

- IGNORE：完全無視下游的Request，照自己的節奏產生資料，有可能會導致`IllegalStateException`，
- ERROR：無法consume publisher所產生資料的第一時間就會IllegalStateException，與IGNORE相似但更為極端，- IGNORE本身可能會根據operators會有一些少少的buffer，還有一點點可能性不會發生錯誤。
- DROP：無法消化的資料都拋棄。
- LATEST：只保留最新的資料。
- BUFFER：預設使用，會將無法消化的資料保存，如果資料量差距太大有可能會導致OutOfMemoryError。

```java
// Keep On emitting O …. N elements to the downstream subscribers. 
// Each subscriber would get an instance of FluxSink to emit elements (Cold subscribers).
Flux<Integer> integerFlux = Flux.create((FluxSink<Integer> fluxSink) -> {
    IntStream.range(0, 5)
            .peek(i -> System.out.println("going to emit - " + i))
            .forEach(fluxSink::next);
});

// subscribers
integerFlux.delayElements(Duration.ofMillis(1)).subscribe(i -> System.out.println("First :: " + i));

integerFlux.delayElements(Duration.ofMillis(2)).subscribe(i -> System.out.println("Second:: " + i));
```

##### `create` vs `generate`

- [oopenhome.cc](https://openhome.cc/Gossip/Spring/Sink.html)
- [create vs generate](https://stackoverflow.com/questions/49951060/difference-between-flux-create-and-flux-generate)


| Create      | Generate          |   
| -------     |  -------          |
| `FluxSink`  | `SynchronousSink` |
| Consumer 只會執行一次且產生[0-n]筆資料 | Consumer 執行多次每次只產生1筆資料   |
| 無法知道速率所以需要提供OverflowStrategy | 根據subscriber的要求來決定產生資料 |
| 沒有`state`控制 | 可以根據`state`來決定處理邏輯 |


#### delay && [interval](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#interval-java.time.Duration-)

delay, method of Mono   
![圖 2](images/6e051959ad37e3401acd719c037d009af3b311af980c93e54e76ae6b1b8f74b5.png)  


interval，method of flux，用於指定流中各個元素產生時間的間隔(包括第一個元素產生時間的延遲)  
![圖 4](images/e9f67e560d53c8e1b595f61dc7f83bb1afaf17daa822f89bd417d258b8fd30a9.png)  


#### justOrEmpty

Create a new Mono that emits the specified item if `Optional.isPresent()` otherwise only emits onComplete.


####  [defaultIfEmpty](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html#defaultIfEmpty-T-)
- Mono方法，用於指定當Stream中**Event為空時產生的預設值**
```java
private Mono<Authentication> currentAuthentication() {
  return ReactiveSecurityContextHolder.getContext()
      .map(SecurityContext::getAuthentication)
      .defaultIfEmpty(ANONYMOUS_USER_TOKEN);
}
```


## operator for converting 

#### `mergeXXX` and `concateXXX`

[geeksforgeeks](https://www.geeksforgeeks.org/rxjava-operator-concat-and-merge/)

- `concat`
    - Concatenates the emissions of two or more Observables **without interleaving them**
- `merge` 
    - Merges the emissions of multiple Observables (same data type) to create a single Observable. 
    - **It will not keep the order while emitted things.**

```java
Observable.concat(gfgObs1, gfgObs2)
    .subscribe(new Observer<String>() {
        @Override public void onSubscribe(Disposable d)
        {
            // ...
        }
        @Override public void onNext(String value)
        {
            // ...
        }
        @Override public void onError(Throwable e)
        {
            // ...
        }
        @Override public void onComplete()
        {
            // ...
        }
  });
```

`mergeDelayError` :: it allows error-free items to continue emitting
```java
TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    
Observable.mergeDelayError(
  Observable.from(new String[] { "hello", "world" }),
  Observable.error(new RuntimeException("Some exception")),
  Observable.from(new String[] { "rxjava" })
).subscribe(testSubscriber);

testSubscriber.assertValues("hello", "world", "rxjava");
testSubscriber.assertError(RuntimeException.class);
/**
 * hello
 * world
 * rxjava
 */

```

#### flatMap

- [map and flatMap](https://stackoverflow.com/questions/49115135/map-vs-flatmap-in-reactor)   
    - [code example](https://www.jianshu.com/p/48527941fa08)
- [merge snf flatMap](https://medium.com/swlh/understanding-reactors-flatmap-operator-a6a7e62d3e95)

![圖 5](images/09fd905a812a8084307dafd27b613599f16dd2e505d047f8e0cb81fe071bcbaa.png)  

```java
Flux.just(
  // Diagram's Circles 
  // We can call each array as element
  Arrays.asList(1,2,3),
  Arrays.asList("a","b","c"),
  Arrays.asList(7,8,9))
  .doOnNext(element -> System.out.println(element))
    // Squares in Diagram 
    // Create new Stream Out of every element 
    // in the original stream and then merges all of these sub-streams
    .flatMap(event -> // white squares
      Flux.formIterable(event)
        .doOnSubscribe(
          subscription -> system.out.println("Subscribe each newly created stream")
        )
    // print the new stream (after merging)
    ).subscribe(System.out::println);

```
- flatMap will do
  1. **CREATE NEW STREAM** for each element (squares)
  2. **SUBSCRIBE** to each newly created stream EAGERLY(`subscribe` green and yellow squares)
    > I cannot guarantee you when I’ll return, but what I can guarantee you is that I’ll return for sure, at some point in future
  3. **FLAT EACH STREAM'S EVENTS** :: Dynamically merge all the newly created streams as they emit.


#### [zip , zipWith , zipMap](http://reactivex.io/documentation/operators/zip.html)


![圖 6](images/6a679524310a1c38fad3ab7b148cb5f4924b934e8ae1269155afcc26d8ed3289.png)  
- The zip extension method brings together two sequences of values as pairs 
```java
Flux<String> s1 = Flux.just("A", "B","C","D","E");
Flux<String> s2 = Flux.just("1", "2","3","4");
// event "E" in stream s1 will be thrown away
s1.zipWith(s2)
    // subscribe 
    // new stream : [A1, B2, C3, D4]
    .subscribe(
      tuple -> System.out.println(tuple.getT1() + " -> " + tuple.getT2())
    );
```
- [merge and zip](https://www.baeldung.com/rxjava-combine-observables)  

#### join

![圖 9](images/445a32de56e38e543e36d28ce396703c264f933ea3c6a5fc2bce7dcdda7b30cc.png)  
- 將當前流和另一個流合併為一個流，流中的元素不是一一對應的關係，**而是根據產生時間進行合併**

#### take

![圖 7](images/06c11f246b9a3469e481192db770659437affcb104532c99ca5cd828f71dc5c8.png)  
- only take first few events in data stream not all

```java
Flux<String> just = Flux.just("1", "2", "3");

//take first two events from the flux to create new flux 
Flux<String> take = just.take(2);

// 1,2
take.subscribe(System.out::print);

// 1,2,3
just.subscribe(System.out::print);
```


### blockFirst and blockLast

```java
Flux<String> flux = Flux.create(skin -> {
    for(int i=0;i<2;++i){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 0 , 1 
        skin.next("Emit "+ i + "th event");
    }
    skin.complete();
});

//flux訂閱者所有操作都是無副作用的,即不會改變原flux物件資料
//阻塞式訂閱,只要有一個元素進入Flux
String first = flux.blockFirst();
//輸出:  Emit 0th event
System.out.println(first);
//還是輸出: Emit 0th event
System.out.println(flux.blockFirst());

//輸出: Emit 1th event
System.out.println(flux.blockLast());
//還是輸出: Emit 1th event
System.out.println(flux.blockLast());
```

#### [defer](https://stackoverflow.com/questions/55007243/what-does-defer-do-in-rxjava)

- [RxJs](http://reactivex.io/documentation/operators/defer.html) 

Without `defer`
```java
AtomicInteger index = new AtomicInteger();

Flowable<String> source =
    Flowable.just("a", "b", "c", "d", "e", "f")
    .map(v -> index.incrementAndGet() + "-" + v);

source.subscribe(System.out:println);

source.subscribe(System.out:println);
/**
1-a
2-b
3-c
4-d
5-e
6-f
7-a
8-b
9-c
10-d
11-e
12-f
*/
```


```java
Flowable<String> source =
    Flowable.defer(() -> {
        AtomicInteger index = new AtomicInteger(); // <--- each subscriber can get its own source sequence

        return Flowable.just("a", "b", "c", "d", "e", "f")
               .map(v -> index.incrementAndGet() + "-" + v);
    })
    ;
source.subscribe(System.out:println);

source.subscribe(System.out:println);

/**
----- first subscriber
1-a
2-b
3-c
4-d
5-e
6-f
----- second subscriber
1-a
2-b
3-c
4-d
5-e
6-f
*/
```

#### [Buffer and Window](https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Window.html)

Buffer
![圖 2](images/39c48527ec116466fec67648a5358d90190e6956915ad6a9cecc7319ab3c0790.png)  
- `buffer`，用於將流中的events 按照時間、邏輯規則分組為多個元素**集合**，並且這些元素集合組成一個元素型別為集合的New Stream

Window
![](https://mcxiaoke.gitbooks.io/rxdocs/content/images/operators/window.C.png)
- It's like buffer, but emits a nested Observable instead of an array.
```
click  : -----------c----------c------------c--
source : ----0----1----2----3----4----5----6---..
                    window(click)
example: o----------o----------o------------o--
         \          \          \
          ---0----1-|--2----3--|-4----5----6|
                    switch()
       : ----0----1----2----3----4----5----6---... 
```

#### filter and distinct
```java
Flux.just(1,2,3,4,5,6,7,8,9)
    // if(i%2 == 0)
    .filter(i->i%2==0)
      // 2, 4, 6, 8
      .subscribe(System.out::print);

Flux.just(1,1,2,2,3,3)
    // no duplicates
    .distinct()
      // [1,2,3]
      .subscribe(System.out::print);
```

#### [reduce](http://reactivex.io/documentation/operators/reduce.html)
- [rxjs reduce](https://www.learnrxjs.io/learn-rxjs/operators/transformation/reduce)
```java
Observable.just(1, 1, 2, 3, 5)
        .reduce { total, next -> total + next }
        // Received 1+1+2+3+5 = it
        .subscribe { println("Received: $it") }

Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    .count()
    .subscribe { s -> println("Received: $s") }

```
- The seed value should be immutable (`collect()` or `seedWith()` should be used for mutables


- `.count()` : count the events in data stream
- `.contains(event)` : check whether event is ever emitted 
- `.any { condition }` : check whether at least one emission meets a specific criterion and return a `Single<Boolean>`
- `.all {condition}` : verifies that each emission qualifies with a specified condition and return a `Single<Boolean>`
## Operators while converting data stream

`doOnXXX`，當流發生XXX時間時的回撥方法，可以有多個，類似於監聽。XXX包括`Subscribe`、`Next`、`Complete`、`Error`等。

#### error

[Code Example](https://openhome.cc/Gossip/Spring/ReactorError.html)

`onErrorContinue` :: No Error messages will be sent to subscriber
```java
Flux.just(-2, -1, 0, 1, 2)
      .map(n -> 10 / n)
      .onErrorContinue((err, n) -> 
        out.println("Bad Data: " + n))
    .subscribe(out::println);
```
`onErrorResume` :: If exception then change the publisher
```java
Flux.just(-2, -1, 0, 1, 2)
    .map(n -> 10 / n)
    // change publisher
    .onErrorResume(e -> Flux.just(10, 20))
    .subscribe(out::println);
```

`onErrorReturn` :: Publish another stream if exception thrown
```java
Flux.just(-2, -1, 0, 1, 2)
    .map(n -> 10 / n)
    .onErrorReturn(Integer.MAX_VALUE)
    .subscribe(out::println);
```

`onErrorMap` :: Convert Exception to other Exception
```java
Flux.just(-2, -1, 0, 1, 2)
    .map(n -> 10 / n)
    .onErrorMap(cause -> new BadDataException(cause))
    .subscribe(out::println, err::println);
```

`retry` :: Re-subscribes to this Flux sequence if it signals any error, indefinitely.
- [examples](https://www.woolha.com/tutorials/project-reactor-using-retry-and-retrywhen-examples)  

```java
Flux.interval(Duration.ofMillis(250))
    .map(input -> {
      if (input < 3) 
        return "tick " + input;
      throw new RuntimeException("boom");})
    .retry(1)
    .elapsed() 
    .subscribe(System.out::println, System.err::println); 
```
#### [thenXXX](https://stackoverflow.com/questions/48254774/what-is-then-thenempty-thenmany-and-flatmapmany-in-spring-webflux)  

All the `thenXXX` methods on Mono have one semantic in common: 
- they ignore the source `onNext` signals and react on completion signals (`onComplete` and `onError`), continuing the sequence at this point with various options. 
- `Completable` represents a deferred computation without any value but only indication for completion (`onComplete`) or exception (`onError`). 

`then` will just replay the source terminal signal, resulting in a `Mono<Void>` to indicate that this never signals any `onNext`.    
![圖 1](images/17dde2e7d4496c08030e94b1a14de54a68bc39d9cb517e038f9eeae5dfb78044.png)  

**`thenEmpty` not only returns a `Mono<Void>`, but it takes a `Mono<Void>` as a parameter.**   
![圖 2](images/1ad077925ca54a29702fd3b1e3a37c0d62aaefebffbd8747568d6c78c6ab0312.png)  

`thenMany` waits for the source to complete then plays all the signals from its `Publisher<R>` parameter, resulting in a Flux<R> that will "pause" until the source completes, then emit the many elements from the provided publisher before replaying its completion signal as well.  
![圖 3](images/a6947987c02756374e1ad7af057dbfad974e2d69597ed35cd37196b296c208ac.png)  

`thenReturn`   
![圖 4](images/46517a7b14df907f2861d1504dfc6b02362c0401c932a3d1a6cf8454ece5c5f4.png)  


## Reactor Thread
- [Scheduler(Thread Handling)](https://zhuanlan.zhihu.com/p/142731958)
- [Scheduler and runOn](https://medium.com/@cheron.antoine/reactor-java-4-how-to-take-control-over-the-execution-of-mono-and-flux-ead31dc066)

We can say Reactor uses Scheduler to manage thread pool. 
- It was handled by Subscriber not the Publisher like `Mono` or `Flux`

The 4 Schedulers are 
- single
  - a one worker thread scheduler
- immediate
  - a scheduler that computes the stream in the thread where the call to the method configuring it is done.
- parallel
  - **a scheduler that has as many workers as your CPU has cores** (or threads if supporting hyper threading). 
  - The method it uses to get the amount of workers to use is `Runtime.getRuntime().availableProcessors()`
- elastic
  - **a scheduler that dynamically creates threads when needed, with no up limit**. A thread is released after 60 non-working seconds.

Scheduler Methods
- `Schedulers.immediate()`
  - 基本上不會用到這個方法，他不會做任何的操作，可以當成是`null`，有可能的使用場景是某個api需要傳入Schedulers，**但你並不想要更換Thread，這時候就可以傳入`Schedulers.immediate()`**   

- `Schedulers.single()`
  - 只有**一條**且**重複**使用的`Thread`   

- `Schedulers.elastic()` _DEPRECATED_
  - 會彈性的增加Thread with no up limit，適用於需要較長時間處理的任務(task)，像是呼叫阻斷(blocking)的服務或是I/O，但可能會導致太多的Thread或是一些backpressure的問題，再推出`Schedulers.boundedElastic()`後就不建議使用（Deprecated）。

- `Schedulers.boundedElastic()`
  - 如同`elastic()`，只是**加上了一些限制(bound)來避免產生過多Thread的問題**，有一個worker pool，預設閒置(IDLE)60 SECONDS就會RELESE Thread。

- `Schedulers.parallel()`
  - 適用於快速且non-blocking的TASK，**根據CPU CORS數來產生Thread的數量**


### `subscribeOn` and `publisherOn`

To configure a Mono or Flux's Scheduler via `subscribeOn` and `publisherOn`

- [code](https://medium.com/netifi/scheduling-with-rx-and-reactor-5b2277af2c3e)   
- [it邦](https://ithelp.ithome.com.tw/articles/10272511)

#### subscribeOn

```java
/*************************
  * JDK8 
  */
ForkJoinPool.commonPool()
    .submit(
        () -> {
          for (int i = 0; i < 10; i++) {
            System.out.println(
                "count - "
                    + i
                    + " - "
                    + Thread.currentThread()
                    + " - number: "
                    + ThreadLocalRandom.current().nextLong());
          }
        })
    .join();

/*************************
  * reactor
  */
Flux.range(0, 10)
      .doOnNext(i ->
        System.out.println(
          "count - "
              + i
              + " - "
              + Thread.currentThread()
              + " - number: "
              + ThreadLocalRandom.current().nextLong()))
    .subscribeOn(Schedulers.parallel())
    .blockLast();
```
- `subscribeOn` is telling reactor to **subscribe on a worker provided by the parallel scheduler.** 
- `Schedulers.parallel()` 
  - the subscriber of the publisher can choose to run it on different threads independent of the publisher’s creator.
- The `blockLast` operator is called instead of `subscribe` 
  - Because the code is running in a different thread we must wait until it’s completed or the JVM would exit.

Reactor with `observable` object
```java
Flux<Integer> observable 
    = Flux
        .range(0, 10)
        .doOnNext(
            i ->
                System.out.println(
                    "count - "
                        + i
                        + " - "
                        + Thread.currentThread()
                        + " - number: "
                        + ThreadLocalRandom.current().nextLong()
                )
          );

observable.subscribeOn(Schedulers.parallel())
          .blockLast();
```
- the `observable` object was returned by a method. 
  - The subscriber could either run on the current thread or do what the example did and run it using a scheduler on potentially another thread.



##### publishOn
```java
Flux.range(0, 10)
    .publishOn(Schedulers.parallel())
    .map(
        i -> {
          long rnd = ThreadLocalRandom.current().nextLong();
          System.out.println(
              Thread.currentThread() + " - count - " + i + " - number: " + rnd);
          return rnd;
        })
    .publishOn(Schedulers.parallel())
    .reduce(
        (integer, integer2) -> {
          long total = integer + integer2;
          System.out.println(Thread.currentThread() + " - current total: " + total);
          return total;
        })
    .doOnNext(total -> System.out.println(Thread.currentThread() + " - total -> " + total))
    .block();
```
- The `map` operator takes an integer generated by range, and then returns the random number that is generated. This is then passed to the `reduce` operator. 

In this case we are just adding two numbers, and it will add all the different random number as they are generated. At the end we call block to wait until everything is done.

- The first `publishOn` method is before the `map` operator. 
  - This makes the `map` operators happen on another thread. 

- The second `publishOn` right after the `map` operator and right before a `reduce` operator. 
  - This will cause all the `reduce` operations to also happen on a different thread. 

- There isn’t another `publishOn` before the `doOnNext`, so it happens on the same thread of the reduce operator.


### Condition of `SubscribeOn` and `publishOn`
`SubscribeOn` cant not be overrided
```java
Mono.just(“1”)
    .subscribeOn(Schedulers.single()) // ------- Start SINGLE
      .map(Integer::valueOf)
      .subscribeOn(Schedulers.elastic()) // will no activate
    .subscribe(System.out::println)
```
- **If the method get called multiple times on a stream, only the first `subscribeOn` will be taken in account.**


`publishOn` will override the scheduler set with the `subscribeOn` method.
```java
Mono.just(“1”).
  subscribeOn(Schedulers.single()) // ------- Start SINGLE
    .map(Integer::valueOf). 
  publishOn(Schedulers.parallel()). // ------ END SINGLE, Start PARALLEL
    map(x -> x * x).                
  subscribe(System.out::println)   
```

## scheduler for `flatMap` 

- The stream can have its own scheduler set. 
- The scheduler won’t be overridden by any of the `publishOn` or `subscribeOn` method of the parent stream, but by default, it will run on the same scheduler as the parent.

**It allows you to set different schedulers depending on what happens in your `flatMap`.** 
- For example, in case of error, you might want the `Mono.error` to run on the same scheduler as the parent, but in case of success, to run on a different scheduler than the parent. This configuration is up to you.


### Parallel Flux

`Flux` **runs sequentially by default**. 
- To make a Flux parallel, there exists a `.parallel()` method in the class. This method returns an instance of `ParallelFlux`. Conversely, it is possible to turn a parallel flux into a sequential flux with the method `.sequential()`.

**`ParallelFlux` has no method `subscribeOn` or `publishOn`.**    
- Instead, It has a `runOn(Scheduler scheduler)` method that acts exactly as `publishOn`.   
By default, not calling `runOn` will run the stream in the thread where you subscribed to it.


```java
private <T> T identityWithThreadLogging(T el, String operation) {
  System.out.println(operation + " -- " + el + " -- " + 
    Thread.currentThread().getName());
  return el;
}
/**
 * <p> flatMap without changing the scheduler </p>
 */
@Test
public void flatMapWithoutChangingScheduler() {
  Flux.range(1, 3).
    map(n -> identityWithThreadLogging(n, "map1").
    flatMap(n -> Mono.just(n).map(nn -> identityWithThreadLogging(nn, "mono")).
    subscribeOn(Schedulers.parallel()).
    subscribe(n -> {
      this.identityWithThreadLogging(n, "subscribe");
      System.out.println(n);
    });
}
```


with `.subscribeOn(Schedulers.elastic())`

```java
@Test
public void complexCase() {
  Flux.range(1, 4).
    subscribeOn(Schedulers.immediate()).
      map(n -> identityWithThreadLogging(n, "map1")).
    flatMap(n -> {
      if (n == 1) return createMonoOnScheduler(n, Schedulers.parallel());
      if (n == 2) return createMonoOnScheduler(n, Schedulers.elastic());
      if (n == 3) return createMonoOnScheduler(n, Schedulers.single());
      
      // n != 1 or 2 or 3
      return Mono.error(new Exception("error"))
                 .subscribeOn(Schedulers.newSingle("error-thread"));
    }).
    map(n -> identityWithThreadLogging(n, "map2")).
    subscribe(
      success -> System.out.println(identityWithThreadLogging(success, "subscribe")),
      error -> System.err.println(identityWithThreadLogging(error, "subscribe, err").getMessage())
    );
}
```
![](https://miro.medium.com/max/700/1*HOK2BeiBaMLfEauXRhSztQ.png)


with `.publishOn(Schedulers.single())`
![](https://miro.medium.com/max/700/1*yWQyEJuAGU3HkaoMXbJGQw.png)

with `.runOn(...)`


with Combining parallel and sequential flux.

```java
@Test
public void combiningParallelAndSequentialFlux() {
  Flux.range(1, 4).
    subscribeOn(Schedulers.parallel()).
      map(n -> identityWithThreadLogging(n, "map1")).
      parallel().
    runOn(Schedulers.elastic()).
      map(n  -> identityWithThreadLogging(n, "parallelFlux")).
      sequential().
      map(n -> identityWithThreadLogging(n, "map2")).
    subscribe(n -> identityWithThreadLogging(n, "subscribe"));
}
```
- 4 threads are used

![](https://miro.medium.com/max/700/1*mi5Ys13Pb-e5yDjv-aBqPA.png)  


