# 過濾

[RxJS 過濾類型 Operators (4) - distinct / distinctUntilChanged / distinctUntilKeyChanged](https://ithelp.ithome.com.tw/articles/10251309)

- [過濾](#過濾)
  - [filter](#filter)
  - [distinct](#distinct)
  - [distinctUntilChanged](#distinctuntilchanged)

## filter

Rxjs的filter與array的filter非常類似
```typescript
data$ = this.searchControl.valueChanges.pipe(
  debounceTime(300), // 當 300 毫秒沒有新資料時，才進行搜尋
  distinctUntilChanged(), // 當「內容真正有變更」時，才進行搜尋
  filter(keyword => keyword.length >= 3), // 當關鍵字大於 3 個字時，才搜尋
  switchMap(keyword => this.httpClient.get(`localhost:4200/search/?q=${keyword}`))
);
```

## distinct

`distinct(?callBackFunction. ?clearObservable)`
```typescript
from([1, 2, 3, 3, 2, 1, 4, 5])
  .pipe(distinct())

(1   2   3   3   2   1   4   5)
distinct()
(1   2   3               4   5)


// with object types
const students = [
  { id: 1, score: 70 },
  { id: 2, score: 80 },
  { id: 3, score: 90 },
  { id: 1, score: 100 },
  { id: 2, score: 100 }
];
from(students)
  .pipe(distinct(student => student.id))



// emit an clear observable
const source$ = new Subject<{id: number, score: number}>();
const sourceFlushes$ = new Subject();
source$
  .pipe(distinct(student => student.id, sourceFlushes$))
  .subscribe(student => {
    console.log(`distinct 示範 (3): ${student.id} - ${student.score}`);
  });

setTimeout(() => source$.next({ id: 1, score: 70 }), 1000);
setTimeout(() => source$.next({ id: 2, score: 80 }), 2000);
setTimeout(() => source$.next({ id: 3, score: 90 }), 3000);
setTimeout(() => source$.next({ id: 1, score: 100 }), 4000);
// emit flush observable
// clear history of emitted Observable (e.g. here id 1,2,3)
setTimeout(() => sourceFlush$.next(), 4500);
setTimeout(() => source$.next({ id: 2, score: 100 }), 5000);
// distinct 示範 (3): 1 - 70
// distinct 示範 (3): 2 - 80
// distinct 示範 (3): 3 - 90
// (第四秒發生 {id: 1, score: 100}，因為重複，所以事件不發生)
// (清空紀錄資料重複物件)
// distinct 示範 (3): 2 - 100 (id: 2 有發生過，但紀錄已被清空，因此事件會發生)
```

 

## distinctUntilChanged

`distinctUntilChanged(?compareFn)`

```typescript
(1   1    2    3    3    1)
distinctUntilChanged()
(1        2    3         1)
    ^ 事件值跟上次一樣，不顯示
          ^ 事件值跟上次不一樣，顯示

from([1, 1, 2, 3, 3, 1]).pipe(
  distinctUntilChanged()
)

const students = [
  { id: 1, score: 70 },
  { id: 1, score: 80 }, // bypass
  { id: 2, score: 90 },
  { id: 3, score: 100 } 
];


from(students).pipe(
  distinctUntilChanged(
    (studentA, studentB) => studentA.id === studentB.id
  )
)
// same as
from(students).pipe(
  distinctUntilKeyChanged(
    'id',
    (idA, idB) => idA === idB
  )
)
```





