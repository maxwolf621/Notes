# StepVerifier

StepVerifier透過builder的方式，在create內傳入需要驗證的publisher，expectNext就是onNext，預期下一個data是否符合，也能夠預期錯誤發生，最後verify就是像subscribe一樣，當作一個啟動開始測試。


- [StepVerifier](#stepverifier)
  - [exceptXXX](#exceptxxx)
  - [verifyXXX](#verifyxxx)
  - [`StepVerifier.withVirtualTime`時間控制](#stepverifierwithvirtualtime時間控制)

## exceptXXX

```java
public <T> Flux<T> appendBoomError(Flux<T> source) {
  return source.concatWith(Mono.error(new IllegalArgumentException("boom")));
}

@Test
public void testAppendBoomError() {
  Flux<String> source = Flux.just("package1", "package2");

  StepVerifier.create(
          appendBoomError(source))
      .expectNext("package1")
      .expectNext("package2")
      .expectErrorMessage("boom")
      .verify();
}
```


- `expectNext`：下一個item與傳入的相同。
- `expectNextMatches(Predicate)`：比較複雜的判斷可以傳入`Predicate`
- `assertNext(Consumer)`：直接傳入assertions
- `expectNextCount(long)`：在下一個訊號之前的item數量
- `expectNextSequence(Iterable)`：一次比對多個item


## verifyXXX

`verifyComplete()`：publisher結束，傳入已完成的訊號(completion signal)
`verifyError()`: publisher結束，但是因為有錯誤發生(unspecified error)
`verifyErrorMessage()`：多加上判斷message

```java
//DefaultStepVerifierBuilder.java Source code
@Override
public Duration verifyError() {
 return expectError().verify();
}
@Override 
public Duration verifyComplete() { 
    return expectComplete().verify(); 
}
@Override 
public Duration verifyErrorMessage(String errorMessage) { 
    return expectErrorMessage(errorMessage).verify(); 
}
```

```java
@Test
public void verify(){
  Flux<Integer> integers = Flux.range(3,7);

  StepVerifier.create(integers)
      //expectNextSequence一次比對五個item
      .expectNextSequence(Arrays.asList(3,4,5,6,7))
      //
      .expectNextMatches(d -> (d / 2 ) == 4)
      .expectNextCount(1)
      .verifyComplete();
}
```

## `StepVerifier.withVirtualTime`時間控制

- `thenAwait(Duration)`：暫停期望評估(expectation evaluation)，停下來等相對就是時間增加(可以順便反思人生不進則退，時光飛逝......)
- `expectNoEvent(Duration)`：在傳入的Duration期間內預期沒有任何事情發生，相對也是一個時間推進的概念。要注意subscription也被視做一個event，如果一開始預期就沒有event發生，建議使用expectSubscription().expectNoEvent(duration) ，要不然都會是錯誤。
要注意在使用withVirtualTime的同時必須要用到上述的兩種方法去增加時間，否則時間就將會停滯。


```java
StepVerifier.withVirtualTime(
    () -> Mono.delay(Duration.ofDays(1)))
    .expectSubscription()
    .expectNoEvent(Duration.ofDays(1))
    .expectNext(0L)
    .verifyComplete();
```

```java
@Test
public void test() {
  StepVerifier.withVirtualTime(() -> 
        Flux.just(1, 2, 3, 4).delayElements(Duration.ofSeconds(1)))
        .expectSubscription() //t == 0
//move the clock forward by 1s, and check nothing is emitted in the meantime
        .expectNoEvent(Duration.ofSeconds(1))
//so this effectively verifies the first value is delayed by 1s:
        .expectNext(1)
//and so on...
        .expectNoEvent(Duration.ofSeconds(1))
        .expectNext(2)
//or move the clock forward by 2s, allowing events to take place,
//and check last 2 values where delayed
        .thenAwait(Duration.ofSeconds(2))
        .expectNext(3, 4)
        .expectComplete()
//trigger the verification and check that in realtime it ran in under 200ms
        .verify(Duration.ofMillis(200));
}
```