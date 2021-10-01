# BehaviorSubject

[BehaviorSubject-vs-observable](https://stackoverflow.com/questions/39494058/behaviorsubject-vs-observable)

BehaviorSubject is a type of subject, a subject is a special type of observable so you can subscribe to messages like any other observable. 

The unique features of BehaviorSubject are

#### 1. It Always has a returned value

It needs an initial value as it must always return a value on subscription even if it hasn't received a `next()` Upon subscription,
  > It returns the last value of the subject. 

```typescript
// Behavior Subject

/**
 * @description {@code a} is an initial value. 
 * if there is a subscription after this, 
 * it would get {@code a} value immediately
 */
let bSubject = new BehaviorSubject("a"); 


bSubject.next("b");

// behaviorSubject always got a least updated data
bSubject.subscribe(value => {
  console.log("Subscription got", value); // Subscription got b, 
                                          // ^ This would not happen 
                                          // for a generic observable 
                                          // or generic subject by default
});

bSubject.next("c"); // Subscription got c
bSubject.next("d"); // Subscription got d
```


A regular observable only triggers when it receives an onnext
at any point, **you can retrieve the last value of the subject in a non-observable code using the `getValue()` method.**
```typescript
// Regular Subject

// we don't need a initial value
let subject = new Subject(); 

subject.next("b");

subject.subscribe(value => {
  console.log("Subscription got", value); // Subscription wont get 
                                          // anything at this point
});

subject.next("c"); // Subscription got c
subject.next("d"); // Subscription got d
```

#### 2. It can be observer and observable (subscribe and send)

Unique features of a subject compared to an observable are: it is an `observer` **in addition to being an observable so you can also send values to a subject in addition to subscribing to it.**

- We can get an **observable value** from `BehaviorSubject` using the `asObservable()` method on BehaviorSubject.


#### 3. It a subtype of `observable`

`observable` is a Generic, and **`BehaviorSubject` is technically a sub-type of Observable** because `BehaviorSubject` is an observable with specific qualities.

#### 4. `BehaviorSubject` ensures that you got the last updated data

- Use `BehaviorSubject` for a data service as an angular service often initializes before component and **behavior subject ensures that the component consuming the service receives the last updated data** even if there are no new updates since the component's subscription to this data. 

## Difference btw `Observable` and `BehaviorSubject`

-  Observer of `Observable` can not assign value via `.next(...)` to` observable`(origin/master).
   - `BehaviorSubject` is bi-directional. it's observer can assign value to observable via `next(...)`
- **`BehaviorSubject` (or `Subject` ) stores observer details, runs the code only once and gives the result to all observers .**    
   - `observable` creates **copy of data** for each observer. so using `observable` may cause inefficiency if there were multiple observers  

```typescript
// RxJS v6+
import { BehaviorSubject } from 'rxjs';

const subject = new BehaviorSubject(123);

//two new subscribers will get initial value => output: 123, 123
subject.subscribe(console.log); // subscriber 1
subject.subscribe(console.log); // subscriber 2

//two subscribers will get new value => output: 456, 456
subject.next(456); // send value to subject

//new subscriber will get latest value (456) => output: 456
subject.subscribe(console.log); // subscriber 3

//all three subscribers will get new value => output: 789, 789, 789
subject.next(789);

// output: 123, 123, 456, 456, 456, 789, 789, 789
```
- Observables : Observables are lazy collections of multiple values over time.
- **BehaviorSubject: A Subject that requires an initial value and emits its current value to new subscribers.**


## Usage

#### Update `@input` property for both child and parent component

[Ref](https://stackoverflow.com/questions/46047854/how-to-update-a-component-without-refreshing-full-page-angular)

[](https://stackoverflow.com/questions/42962394/angular-2-how-to-detect-changes-in-an-array-input-property)

## Angular BehaviorSubject

[How to Implement `BehaviorSubject` using `service.ts`](https://stackoverflow.com/questions/57355066/how-to-implement-behavior-subject-using-service-in-angular-8)


[SoruceCode](https://dev.to/juliandierkes/two-ways-of-using-angular-services-with-the-httpclient-51ef)   

#### Step 1 Service
To create `BehaviorOSubject` in Service we need
- define a `BehaviorOSubject<DataType>` instance
- `HttpClient` fetches the data from  backend and push it to `BehaviorSubject` to emit via `.next(dataValues)`
- create getter for `BehaviorSubject` instance

```typescript
@Injectable({
  providedIn: 'root'
})
export class ShoppingListPushService {

  ITEMS_URL = '/assets/items.json';

  /**
   * create BehaviorSubject and its observable data (type)
   */
  // private readonly items$ : BehaviorSubject<SomeType[]> = new BehaviorSubject<SomeType[]>([]);
  // private readonly items$ = new BehaviorSubject([]);
  private readonly items$: BehaviorSubject<ShoppingItem[]> = 
                           new BehaviorSubject<ShoppingItem[]>([]);

  constructor(private httpClient: HttpClient) {}

  /**
   * @description 
   * Fetch the data from backend and the `returnedItems` 
   * are pushed to item$(behaviorSubject)
   * and it emits the value via .next
   */
  fetchList() {
    this.httpClient.get<ShoppingItem[]>(this.ITEMS_URL)
      .subscribe(
          // update the data
          receivedItems => this.items$.next(receivedItems)
    );
  }

  // getter for BehaviorSubject
  get items(): Observable<ShoppingItem[]> {
    return this.items$.asObservable();
  }
}
```
-This kind of service can easily be used with the Angular async pipe.
   - **No need to subscribe to or unsubscribe from anything.**


#### Step 2 Component who uses service to fetch data

One of Component who uses service to subscribe the data
- Fetch the data at `ngOnInit` life hook

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

  constructor(private shoppingListService: ShoppingListPushService) {
  }

  ngOnInit(): void {
    this.shoppingListService.fetchList();
  }
```

- [Other one](https://eliteionic.com/tutorials/using-behaviorsubject-to-handle-asynchronous-loading-in-ionic/)

