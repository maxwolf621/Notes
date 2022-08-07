# Template expression operators

## The non-null assertion operator (!)

Angular's non-null assertion operator to prevent TypeScript from reporting that a property is `null` or `undefined`.
```html
<!-- Assert color is defined, even if according to the `Item` type it could be undefined. -->
<p>The item's color is: {{item.color!.toUpperCase()}}</p>
```

## The $any() type cast function

Sometimes a binding expression triggers a type error during AOT compilation.
Use the `$any()` cast function to cast the expression to the any type.

The following example uses `$any()` prevents TypeScript from reporting that `bestByDate` is not a member of the item object.
```html
<p>The item's undeclared best by date is: {{$any(item).bestByDate}}</p>
```

The `$any()` cast function also works with `this` to allow access to undeclared members of the component.
```html
<p>The item's undeclared best by date is: {{$any(this).bestByDate}}</p>
```