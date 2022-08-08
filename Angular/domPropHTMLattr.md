# Dom and HTML

HTML attributes 經過解析後，就會產生相對應的 DOM properties


- [Dom and HTML](#dom-and-html)
  - [Reference](#reference)
  - [Javascript Methods operating with HTML attribute](#javascript-methods-operating-with-html-attribute)
  - [In Action](#in-action)


## Reference

- [HTML Attributes 與 DOM Properties 的區別](https://jimmyswebnote.com/html-attributes-and-dom-properties/)
- [HTML Attributes 與 DOM Properties 的區別](https://ithelp.ithome.com.tw/articles/10211553)

## Javascript Methods operating with HTML attribute
```javascript
elem.hasAttribute(name)
elem.getAttribute(name)
elem.setAttribute(name, value) // add new attribute
elem.removeAttribute(name) 
elem.attributes // return attributes[]
```

## In Action 
```html
<input type="text" id="inputBox" value="Hello!">
```
```typescript
const inputBox = document.getElementById('inputBox'); 

// attributes of inputBox
const attrs = inputBox.attributes; 

for(var i = attrs.length - 1; i >= 0; i--) { 
  console.log(`${attrs[i].name} ==>  ${attrs[i].value}`);
} 
// id ==>  inputBox
// value ==>  Hello!
// type ==>  text
```
　
```typescript
const inputBox = document.getElementById('inputBox');

console.log(
  inputBox.id === inputBox.getAttribute('id') // true
);

console.log(
  inputBox.type === inputBox.getAttribute('type') // true
);

setTimeout(() => {

    // HTML ATTRIBUTE
    console.log(
        inputBox.value === inputBox.getAttribute('value') // false if value changed
    );

    // DOM properties
    console.log(
        `inputBox.value is ${inputBox.value} and inputBox.getAttribute('value') is ${inputBox.getAttribute('value')}`);
}, 5000);
```