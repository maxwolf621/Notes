[MaxwellAlexius](https://ithelp.ithome.com.tw/articles/10216794)

## Union Types `|`

```typescript
// value of `union` may be `A` 、 `B` or **` A+B`**
type union = A | B; 

// value type as strig or array 
var value: string | string[] = 'test'
console.log(value.length) 
```

## Intersection Types `&`

An intersection type represents an entity that is of all types. For example:

```typescript
function extend <A, B> (a: A, b: B): A & B {
  
  Object.keys(b).forEach(key => {
    a[key] = b[key]
  })

  return a as A & B
}
```

##  Primitive Types Union & Intersection

There might have problem for such situation
```typescript
type T = number & string;
```
- the intersection of `number` and `string` is `never`


Union with `never`
```typescript
type WontBeNever = T | never;
// => WontBeNever: T
```

Intersection with `never`
```typescript
type MustBeNever = U & never;
// => MustBeNever: never
```
