// Type Tuple Length
type Length<T extends { length : number}[]> =  T['length'];

type tesla = ['tesla', 'model 3', 'model X', 'model Y']
type spaceX = ['FALCON 9', 'FALCON HEAVY', 'DRAGON', 'STARSHIP', 'HUMAN SPACEFLIGHT']
type teslaLength = Length<tesla>  // expected 4
type spaceXLength = Length<spaceX> // expected 5

/**
 * Tuple of Object
 */
const tuple = ['tesla', 'model 3', 'model X', 'model Y'] as const

const l = typeof tuple
// const l: "string" | "number" | "bigint" | "boolean" | "symbol" | "undefined" | "object" | "function"

type resultTupleToObject = TupleToObject<typeof tuple> 
// expected { tesla: 'tesla', 'model 3': 'model 3', 'model X': 'model X', 'model Y': 'model Y'}

//                 Only Allow readonly tuple 
type TupleToObject<T extends readonly any[]> = {
//        iteration
    [P in T[number]]: P
}

/**
 * Exclude
 */
type MyExclude<T, K>  = T extends K ? never : T;
type ResultMyExclude = MyExclude<'a' | 'b' | 'c', 'a'>  // 'b' | 'c'

/**
 * Extract
 */
 type Person = {
  name: string;
  age: number;
  address: string;
}

// 'age'|'address'
type ExtractResult = ExtractImp<keyof Person, 'age'|'address'|'sex'>
type ExtractImp<T, K> = T extends K ? T : never;

/**
 * Readonly 
 */
interface Todo {
  title: string
  description: string
}

const todo: MyReadonly<Todo> = {
  title: "Hey",
  description: "foobar"
}

// todo.title = "Hello"  Error: cannot reassign a readonly property
// todo.description = "barFoo" Error: cannot reassign a readonly property

type MyReadonly<T> = {
    +readonly [K in keyof T] : T[K];
}


/**
 * PUSH
 */ 
type push<T extends any[], K> = [...T, K];
type resPush  = push<[1,2] , "s">; 


/**
 * Function Parameter
 */
const add = (a: number, b: string): void => {};
const a = typeof add;

type resMyParameter = MyParameters<typeof add>  // [number, string]

//                          (a: number , b: string) => void
type MyParameters<T> = T extends (...args: infer R) => any ? R : never
// type MyParameters<T extends (...args: any[]) => any> = T extends (...args: infer R) => any ? R : never 


/**
 * Record
 */
type keys = 'Cat'|'Dot'
type Animal = {
  name: string;
  age: number;
}

type Expected = {
  Cat: {
    name: string;
    age: number;
  };
  Dog: {
    name: string;
    age: number;
  }
}

// Expected
type RecordResult = Re<keys, Animal>

type Re<K extends keyof any, T> = {
  [P in K]: T
}


/**
 * Function ReturnType
 */
function getRandom (): number {
  return Math.random()
}
//                                () => number
type resReturnType = fnReturnType<typeof getRandom> // number

//                                if T is function type
type fnReturnType<T> = T extends (...args: any) => infer R ? R : never


/**
 * Trim String
 */
type strTrimLeft = TrimLeft<' str'>  // 'str'
type strTrimRight = TrimRight<'str '> // 'str'
type strTrim = Trim<' str '>     //'str'

type Space = ' ' | '\n' | '\t'
//                                S extends    " "    "Hello"      recursively
type TrimLeft<S extends string> = S extends `${Space}${infer R}` ? TrimLeft<R> : S
type Trim<S extends string> = S extends (`${Space}${infer R}` | `${infer R}${Space}`) ? Trim<R> : S
type TrimRight<S extends string> = S extends `${infer R}${Space}` ? TrimRight<R> : S




/**
 * Look Up
 */
 interface Cat {
  type: "cat";
  breeds: "Abyssinian" | "Shorthair" | "Curl" | "Bengal";
}

interface Dog {
  type: "dog";
  breeds: "Hound" | "Brittany" | "Bulldog" | "Boxer";
  color: "brown" | "white" | "black";
}

type MyDog = LookUp<Cat | Dog, "dog">; // expected to be `Dog`

type LookUp<
  U extends { type: string; },
  T extends string
> = U extends { type: T } ? U : never



type inT<T extends new (...args: any[]) => any> = T extends new (...args: any[]) => infer R ? R : any;

class TestClass {
  constructor(public name: string, public age: number) {}
}

type Params = ConstructorParameters<typeof TestClass>; // [string, number]

type Instance = inT<typeof TestClass>; // TestClass


/**
 * TupleToUnion
 */
// result'1' | '2' | '3'
type TupleToUnionResult = TupleToUnion<['1', '2', '3']>

// way1: T[number]
// T[number] iterates each array element
// type TupleToUnion<T extends readonly any[]> = T[number]

//                 constraint : tuple only
type TupleToUnion<T extends readonly any[]> = 
T extends [infer R, ...infer args]
? R | TupleToUnion<args>
: never

/*
1th itr
const R = '1'
const args = ['2', '3']
const result = '1' | TupleToUnion<args>

2nd itr
const R = '2'
const args = ['3']
const result = '1' | '2' | TupleToUnion<args>

3rd itr
const R = '3'
const args = ['']
const result = '1' | '2' | '3'
*/

/**
 * last element
 */
type result = Last<[1, 2, 3]> // 3
type Last<T extends any[]> = T extends [...any, infer R] ? R : never;

// Add new element at the first index
type LastAddELement<T extends any[]> = [any, ...T][T['length']]
/**
const T = [1, 2, 3]
const arr = [any, 1, 2, 3]
const result = arr[T['length']] 
*/

/**
 * POP
 */
type resultPop = Pop<[1,2,3]> // [1,2]
type Pop<T extends any[]> =  T extends [...infer Rest, infer L]
? Rest
: never


