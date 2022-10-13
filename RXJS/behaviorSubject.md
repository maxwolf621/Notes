# BehaviorSubject

[[stackoverflow] BehaviorSubject-vs-observable](https://stackoverflow.com/questions/39494058/behaviorsubject-vs-observable)

- [BehaviorSubject](#behaviorsubject)
  - [features of BehaviorSubject](#features-of-behaviorsubject)
  - [`Observable` and `BehaviorSubject`](#observable-and-behaviorsubject)
  - [AsObservable](#asobservable)
  - [Usage in Angular](#usage-in-angular)
      - [Angular BehaviorSubject](#angular-behaviorsubject)
      - [Step 1 Service](#step-1-service)
      - [Step 2 Component who uses service to fetch data](#step-2-component-who-uses-service-to-fetch-data)


BehaviorSubject is a type of subject, **a subject is a special type of observable** so you can subscribe to messages like any other observable. 

Observable is a Generic, and BehaviorSubject is technically a sub-type of Observable because BehaviorSubject is an observable with specific qualities.

## features of BehaviorSubject

- BehaviorSubject is needed to be initialized to ensure that you got the last updated data
- it always returns the latest value of the subject using the `getValue()` method.
- **in addition to being an observable so you can also send values to a subject in addition to subscribing to it.**
- **we can get an observable value from `BehaviorSubject` using the `asObservable()` method**


Using `Subject<T>()`
```typescript
let subject = new Subject(); 

subject.next("b");

// ----- subscription started
subject.subscribe(value => {
  console.log("Subscription got", value); 
  // Subscription wont get 
  // anything at this point
});

subject.next("c"); // Subscription got c
subject.next("d"); // Subscription got d
```

Using `BehaviorSubject<T>(V)`
```typescript
// initial value (return last value) is required
let bSubject = new BehaviorSubject<string>("a"); 

/** 
 * 
 * ... no subscription ... 
 * 
 * **/

bSubject.next("b"); // emit observable

/** subscriber subscribed **/
bSubject.subscribe(value => {
  console.log("Subscription got", value); // subscriber retrieved b
});

bSubject.next("c"); // subscriber retrieved c 
bSubject.next("d"); // subscriber retrieved d
```


## `Observable` and `BehaviorSubject`

**`BehaviorSubject` is bi-directional**. 
- it's observer can assign value to observable via `next(...)` and `BehaviorSubject` (or `Subject` ) stores observer details, runs the code only once and gives the result to all observers. 

`observable` creates **copy of data** for each (subscriber)observer. so using `observable` may cause inefficiency if there were multiple observers     

```typescript
import { BehaviorSubject } from 'rxjs';

const subject = new BehaviorSubject(123);

subject.subscribe(
  (value) => console.log(`subscriber1 : ${value}`)); // 123,444,456,789

subject.next(444);

subject.subscribe(
  (value) => console.log(`subscriber2 : ${value}`)); // 444,456,789

subject.next(456);

subject.subscribe(
  (value) => console.log(`subscriber3 : ${value}`)); // 456,789

subject.next(789);

// output: 
subscriber1 : 123
subscriber1 : 444
subscriber2 : 444
subscriber1 : 456
subscriber2 : 456
subscriber3 : 456
subscriber1 : 789
subscriber2 : 789
subscriber3 : 789
```
- Observables : Observables are lazy collections of multiple values over time.
- **BehaviorSubject: A Subject that requires an initial value and emits(`.next`) its (last) current value to new subscribers.**

```typescript
const subject = new BehaviorSubject(123);

const currentBehaviorSubject = subject.asObservable();

let subscription1 = currentBehaviorSubject
    .subscribe(val => {
        console.log("A :" + val)
    });

subject.next(456);
subject.next(789);

setTimeout(() => {
  let subscription1 = currentBehaviorSubject.subscribe(val => {
           console.log("C :" + val)
      });

},0);

subject.next(101112);
```

## AsObservable

```typescript
//messageService.ts
private behaviorSubject: BehaviorSubject<number> = new BehaviorSubject<number>(1);

// keep the current observable in behaviorSubject which is 1
currentBehaviorSubject = this.behaviorSubject.asObservable();

updateBehaviorSubject() {
    this.behaviorSubject.next(2);
}

//RxjsSubjectBComponent.ts
this.messageService.updateBehaviorSubject();

// RxjsSubjectAComponent.ts 
let subscription1 = this.messageService.currentBehaviorSubject
    .subscribe(val => {
        console.log("RxjsSubjectAComponent :" + val)
    });

// RxjsSubjectCComponent.ts
setTimeout(() => {
    let subscription1 = this.messageService.currentBehaviorSubject
        .subscribe(val => {
             console.log("RxjsSubjectCComponent :" + val)
        });

}, 5000);
```



## Usage in Angular
#### Angular BehaviorSubject
- [How to Implement `BehaviorSubject` using `service.ts`](https://stackoverflow.com/questions/57355066/how-to-implement-behavior-subject-using-service-in-angular-8)    
- [SoruceCode](https://dev.to/juliandierkes/two-ways-of-using-angular-services-with-the-httpclient-51ef)   
- [Using BehaviorSubject To Handle **Asynchronous** Loading In Ionic](https://eliteionic.com/tutorials/using-behaviorsubject-to-handle-asynchronous-loading-in-ionic/)

#### Step 1 Service

1. Define a `BehaviorOSubject<DataType>` instance
2. `HttpClient.get()` fetches the data from backend and push it to `BehaviorSubject` to update last value via `.next(dataValues)`
3. Create Getter for `BehaviorSubject` instance

```typescript
@Injectable({
  providedIn: 'root'
})
export class ShoppingListPushService {

  ITEMS_URL = '/assets/items.json';

  /**
   * create BehaviorSubject and its observable data (type)
   */
  // private readonly items$ : BehaviorSubject<SomeType[]> = 
  //                           new BehaviorSubject<SomeType[]>([]);
  // private readonly items$ = new BehaviorSubject([]);
  private readonly items$: BehaviorSubject<ShoppingItem[]> = 
                           // empty array (initial value)
                           new BehaviorSubject<ShoppingItem[]>([]);

  constructor(private httpClient: HttpClient) {}

  /**
   * Fetch the data from backend 
   * and the returned Items are pushed to item$(behaviorSubject)
   * and it emits the value via .next()
   */
  fetchList() {
    this.httpClient.get<ShoppingItem[]>(this.ITEMS_URL).subscribe(
          // update lasted data
          receivedItems => this.items$.next(receivedItems)
    );
  }

  // getter for BehaviorSubject
  get items(): Observable<ShoppingItem[]> {
    return this.items$.asObservable();
  }
}
```
- This kind of service can easily be used with the Angular async pipe.
- **No need to subscribe to or unsubscribe from anything.**


#### Step 2 Component who uses service to fetch data

To present the information for frontend 
```typescript
@Component({
  selector: 'app-push',
  template: `
      <div *ngFor="let item of shoppingListService.items | async">
          - {{item.quantity}} {{item.name}}
      </div>
  `
})
export class PushComponent implements OnInit {

  constructor(private shoppingListService: ShoppingListPushService) {}

  ngOnInit(): void {
    this.shoppingListService.fetchList();
  }
```



