
# Prototype

https://hackmd.io/@Edixon/B1X6M0Mdn

[TOC]


> Prototype is a creational design pattern that lets you **copy existing objects** without making your code **dependent** on their classes.

## Pattern UML
![image](https://user-images.githubusercontent.com/68631186/126876603-d9e916e2-e589-4c67-9ce3-d1927cee5422.png)
- The Prototype interface declares the cloning methods. In most cases, it’s a single clone method.
- The Concrete Prototype class implements the cloning method `clone()`. In addition to copying the original object’s data to the clone, this method may also handle some edge cases of the cloning process related to cloning linked objects, untangling recursive dependencies, etc.
- The Client can produce a copy of any object that follows the prototype interface.

## Code Examples UML
![prototype](https://user-images.githubusercontent.com/68631186/126891786-ad4eaf43-e259-4c44-9961-95ee6d951ab8.png)


## References
[Example from cyc](https://github.com/CyC2018/CS-Notes/blob/master/notes/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%20-%20%E5%8E%9F%E5%9E%8B%E6%A8%A1%E5%BC%8F.md)   
[Example from iluwater](https://github.com/iluwatar/java-design-patterns/tree/master/prototype)  