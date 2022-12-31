 # Sink and Processor
[note from `robertwang`](https://ithelp.ithome.com.tw/articles/10272949)

**Sinks : Reactor優化過後的Processors**
-  在Reactor中，這些功能大部分其實透過Publisher的operator就能做到，而其中剩下比較特別的部分，就還是需要使用Processor來特別處理  

- Processor同樣身為Subscriber所以可以直接使用`onNext`, `onComplete` and `onError`
  > 而這樣的行為是比較危險，所以Reactor在3.4.0的版本後完全棄用了Processor，取而代之的是Sinks。


### Type of Sinks
- `Sinks.One`：僅可以傳送一個資料，類似`Mono`。
- `Sinks.Many`：可以傳送多筆資料，類似`Flux`
  1. `.multicast()` : 可以有多個訂閱者，每個訂閱者並不會都拿到全部且一樣的資料，而是只會取得訂閱後開始最新的(Hot sequence)
  2. `.unicast()` : 只能有一個Subscriber -> 透過buffer來處理backpressure
  3. `.replay()` : via cache fetch the data have subscribers get the same data
- `Sinks.Empty`：沒有資料，僅能傳送終結訊號(terminal signal)，類似`Mono.empty()`。
- `Sinks.unsafe()` : 不保證thread-safe的版本，如果能夠確保使用情境會是thread-safe的，使用`Sinks.unsafe()`可以相對增加效能。
  1. `Sinks.unsafe().one()`
  2. `Sinks.unsafe().empty()`
  3. `Sinks.unsafe().Many()`


### Methods
- `multicast()`
    1. `onBackpressureBuffer()` : 是第一個訂閱者訂閱之前的暫存，之後的訂閱者就只會收到最新的資料。
    2. `onBackpressureBuffer(int bufferSize, boolean autoCancel)` : 設定可以傳入buffer的大小(`bufferSize`)，並且當所有訂閱者都取消訂閱後是否自動清除buffer(`autoCancel`)。
    3. `directAllOrNothing()` : 只要有一個訂閱者變慢(無法consume更多的資料)，則所有subscriber都會停止，直到恢復正常。
    4. `directBestEffort()` 相較於上者，只會停止推送給無法接受資料的訂閱者，其他則正常。
- `unicast()`
    1. `onBackpressureBuffer()` 這個buffer是用來存唯一訂閱者訂閱資料已經推送出去的資料，這樣才能確保訂閱者可以拿到全部，預設是沒有上限所以可能會有OOM的風險，Reactor也提供傳入自訂Queue來限制上限`onBackpressureBuffer(Queue)`，超過的部分就會捨棄掉。

- `replay()`
    1. `limit(int elementsSize)` : Limit size of the element，因為replay會保存資料讓所有訂閱者都能接受到一樣的資料。
    2. `all()`：所有資料都保存
    3. `limit(Duration)`：保存某個時間的資料
    4. `limit(int, Duration)`：結合時間跟數量的限制
    5. `latest()`：只保存最後一筆
    6. `latestOrDefault(T)`：保存最後一筆或是預設值

- `Sinks.One`
    1. `emitValue(T value)` 等於 `onNext(value)` + `onComplete()`。
    2. `emitEmpty()` : 等於`onComplete()`，基本上就是`Mono.empty()`。
    3. `emitError(Throwable t)` 等於`onError(t)`
