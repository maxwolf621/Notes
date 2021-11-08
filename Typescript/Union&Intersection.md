
[Maxwell Alexius](https://ithelp.ithome.com.tw/articles/10216794)

## union `|`


```typescript
type union = A | B;
```
`union` can as A or B or A + B


## intersection `&`

```typescript
type intersection = A & B;
```
members in `intersection` should exist in both A and B

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
