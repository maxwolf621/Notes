# Function Programming

- [Function Programming](#function-programming)
  - [higher order function](#higher-order-function)
  - [命令式 (Imperative) v.s 宣告式 (Declarative)](#命令式-imperative-vs-宣告式-declarative)
  - [Curry Function (lazy evaluation)](#curry-function-lazy-evaluation)
  - [Compose](#compose)
  - [Pipe](#pipe)
  - [tap](#tap)

```typescript
const data = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

// sum will be affect by sumEvenSquare
let sum = {
  value: 0
};

const sumEvenSquare = () => {
  for (let i = 0; i < data.length; ++i) {
    const value = data[i];
    if (value % 2 === 0) {
      sum.value += value * value;
    }
  }
};

sumEvenSquare();
console.log(sum.value); // 220
 
// 
const sumEvenSquare = (dataArray: number[]) => {
    // 把計算總和的物件從外部移動到內部
    let innerSum = {
        value: 0
    };

    // ...

    // 回傳這個總和的內部物件
    return innerSum;
}

// 每次傳入陣列時，都先複製一份新的陣列，避免資料被改變
const sum1 = sumEvenSquare([...data]);
console.log(sum1.value); // 220
const sum2 = sumEvenSquare([...data]);
console.log(sum2.value); // 220
```

## higher order function

```typescript
const data = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

const sum = (processFn: (input: number) => number) => {
  return (inputArray: number[]) => {
    let result = 0;
    for (let i = 0; i < inputArray.length; ++i) {
      result += processFn(inputArray[i]);
    }
    return result;
  };
};

// 計算偶數平方，奇數回傳 0
const evenSquare = (item: number) => {
  return item % 2 === 0 ? item * item : 0;
};

// 先得到一個 function
const mySumEvenSquare = sum(evenSquare);

// 再以剛才得到的 function 實際進行運算
const myResult1 = mySumEvenSquare(data);
console.log(myResult1); // 220

// anonymous function in function
const sumResult2 = sum(item => (item % 2 === 0 ? item * item : 0))(data);
console.log(sumResult2); // 220

```

## 命令式 (Imperative) v.s 宣告式 (Declarative)

Imperative
```typescript
for (let i = 0; i < data.length; ++i) {
  const value = data[i];
  if (value % 2 === 0) {
    sum.value += value * value;
  }
```

Declarative (Encapsulation)
```typescript
// wrap each operator function even, square, sum
const evenDat even(data);
const evenSquareData = square(evenData);
const sumResult = sum(evenSquareData);

console.log(sumResult);

// same as 
console.log(sum(square(even(data))));
```


## Curry Function (lazy evaluation)

Currying is the process of transforming a function that takes multiple arguments into a series of functions that take one argument at a time
```typescript
const add = (num, data) => num + data;

// or
const add = (num) => {
  return (data) => num + data;
};
// or
const add = (num : number) => (data : number) => num + data
```


```typescript
const plusOne = add(1); // 一個用來 +1 的 function


const result  = add(1)(2)  // 3  

const data = 2;
const plusTwoResult = plusTwo(data); // 3
```

## Compose

Before
```typescript
const sumEvenPower = powerFn => {
  return inputArray => {
    const evenData = even(inputArray);
    const powerData = powerFn(evenData);
    const sumResult = sum(powerData);
    return sumResult;
  };
};
// or
const sumEvenPower = powerFn => {
  return inputArray => {
    return sum(powerFn(even(inputArray)));
  };
};
```

After
```typescript
const compose = (...fns) => {
  return data => {
    let result = data;

    // 從最後一個 function 開始執行
    for (let i = fns.length - 1; i >= 0; --i) {
      result = fns[i](result);
    }
    return result;
  };
};
const sumEvenPower = powerFn => {
  //         <---------------first
  return compose(sum,powerFn,even);
};
const sumEvenSquare = sumEvenPower(square);
const sumEvenCube = sumEvenPower(cube);
```

## Pipe

```typescript
const pipe = (...fns) => {
  return data => {
    let result = data;
    // 原本 compose 的 for 迴圈是從最後一個開始執行
    // pipe 內改為從第一個 function 開始執行
    for (let i = 0; i < fns.length; ++i) {
      result = fns[i](result);
    }
    return result;
  };
};

const sumEvenPower = powerFn => {
  //          first------------>
  return pipe(even,powerFn,sum,);
};

```

## tap

```typescript
const tap = (fn) => {
  return (data) => {
    // 呼叫傳入的 function
    fn(data);
    // 直接回傳資料
    return data;
  }
```