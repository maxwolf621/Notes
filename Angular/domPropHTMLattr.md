# Dom and HTML

- [HTML Attributes 與 DOM Properties 的區別](https://jimmyswebnote.com/html-attributes-and-dom-properties/)
- [HTML Attributes 與 DOM Properties 的區別](https://ithelp.ithome.com.tw/articles/10211553)

**HTML attributes -> parse -> DOM properties**   
- DOM : Model of Tree Structure HTML  
```typescript
<div id="static-block"></div>
const id = document.getElementById("static-block")
```

```html
<input type="text" id="inputBox" value="Hello!">
```
```typescript
const inputBox = document.getElementById('inputBox'); 

/**
 * #attributes
 */
const attrs = inputBox.attributes; 
for(var i = attrs.length - 1; i >= 0; i--) { 
  console.log(`${attrs[i].name} ==>  ${attrs[i].value}`);
} 
// id ==>  inputBox
// value ==>  Hello!
// type ==>  text

/**
 * #getAttribute('attrName')
 */
console.log(
  inputBox.id === inputBox.getAttribute('id') 
  // true
);

console.log(
  inputBox.type === inputBox.getAttribute('type') 
  // true
);

console.log(
  inputBox.value === inputBox.getAttribute('value') 
  // false if value changed
);

setTimeout(() => {
    console.log(
        `inputBox.value : ${inputBox.value}.
         inputBox.getAttribute('value'): ${inputBox.getAttribute('value')}`
    );
}, 5000);
```
　
