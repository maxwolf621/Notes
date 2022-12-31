# Subscribe and Publisher


- [Day 14 Reactive Programming -Reactor(COLD VS HOT) -PART 1](https://ithelp.ithome.com.tw/articles/10273777)

依據subscribe的時間點分成兩種
```
Assembly Time --> Subscribe --> Subscription
```
- Data Stream在`subscribe`未觸發前都不會運作

## Assembly Time 在訂閱之前 

宣告其data stream，例如
```java
// Flux 是 immutable
Flux<Integer> flux = Flux.range(1,10);
flux.filter ( n -> n % 2 == 0 )
    .map(...);   
```

## Subscription Time 在訂閱之後

訂閱的方式有三種
1. subscribe
2. block：call `Flux.blockFirst`
3. hot publisher

## Publisher 分成兩種

Hot Publisher 
- (live)直播影片，訂閱之後訂閱者都是從當前最新部分開始

```java
Sinks.Many<String> hotSource = Sinks.unsafe().many().multicast().directBestEffort();

Flux<String> hotFlux = hotSource.asFlux().map(String::toUpperCase); 

hotFlux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: "+d)); // -- subscriber 1 subscribes starts 

hotSource.emitNext("blue", FAIL_FAST);  
hotSource.tryEmitNext("green").orThrow();  

hotFlux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: "+d)); // --- subscriber 2 subscribes starts

hotSource.emitNext("orange", FAIL_FAST); 
hotSource.emitNext("purple", FAIL_FAST); 
hotSource.emitComplete(FAIL_FAST);

/*
Subscriber 1 to Hot Source: BLUE 
Subscriber 1 to Hot Source: GREEN 
Subscriber 1 to Hot Source: ORANGE 
Subscriber 2 to Hot Source: ORANGE  <--- 2
Subscriber 1 to Hot Source: PURPLE 
Subscriber 2 to Hot Source: PURPLE  <--- 2
*/
```

Cold Publisher
- Youtube影片，訂閱之後訂閱者都是從頭開始

```java
Flux<String> source =
    Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
        .map(String::toUpperCase);

source.subscribe(d -> System.out.println("Subscriber 1: " + d));
source.subscribe(d -> System.out.println("Subscriber 2: " + d));
/*
Subscriber 1: BLUE
Subscriber 1: GREEN
Subscriber 1: ORANGE
Subscriber 1: PURPLE

Subscriber 2: BLUE
Subscriber 2: GREEN
Subscriber 2: ORANGE
Subscriber 2: PURPLE 
*/ 
```

## ConnectableFlux

- use `publish` and `replay` to convert `Flux` to `ConnectableFlux`
- `publish` and `replay` are hot publisher

```java
Flux<Integer> source = 
    Flux.range(1, 5) 
        .doOnSubscribe(s -> System.out.println("subscribed to source")); 

ConnectableFlux<Integer> co = source.publish(); 

co.subscribe(System.out::println, e -> {}, () -> {}); 

System.out.println("done subscribing"); 
Thread.sleep(2000); 

System.out.println("will now connect"); 
co.connect(); 

co.subscribe(System.out::println, e -> {}, () -> {}); 
/*
done subscribing 
will now connect 
subscribed to source
1 
2 
3 
4 
5 
*/
```

### methods

ConnectableFlux提供了幾個方法來控制publisher是否需要推送資料，而不是單純像是一般的publisher是透過subscribe觸發。

connect()：手動控制，可以自訂需求邏輯判斷已經有足夠的訂閱數再透過connect()來讓publisher開始推送資料。
autoConnect(n)：自動控制，傳入參數n為訂閱數，當訂閱數等於n則開始推送資料。
refCount(n)：另外一個方向的控制，剩餘還在訂閱的數量若小於參數N，則停止推送資料。
refCount(int, Duration)：類似於grace period，有使用過k8s的人可能比較好理解，就是當訂閱數小於N之後再經過傳入的時間都沒有再讓訂閱數大於N才會停止推送資料。


## cache

- hot publisher
- `cache` ~= `replay().autoConnect(1)`

#### difference btw cache and replay

1. `replay`根據需求選擇connect的時機點或是其他`ConnectableFlux`所提供的方法來控制邏輯，`cache`則是當有第一個訂閱者就自動`connect`
2. `replay`的訂閱者就只能拿到最新的資料(Hot sequence)而不會是全部資料，反之`replay`則是可以根據傳入的參數決定要保存多少資料。