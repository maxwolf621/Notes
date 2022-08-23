###### tags : `Design Pattern`
# Chain Of Responsibility

[Example from iluwater](https://github.com/iluwatar/java-design-patterns/tree/master/chain-of-responsibility)  
[Example from Cyc](https://github.com/CyC2018/CS-Notes/blob/master/notes/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%20-%20%E8%B4%A3%E4%BB%BB%E9%93%BE.md)

Chain of Responsibility is a behavioral design pattern that lets you pass requests along a chain of handlers.  
**Upon receiving a request, each handler decides either to process the request or to pass it to the next handler in the chain.**
![image](https://user-images.githubusercontent.com/68631186/126875251-4b1c6d1e-f001-43eb-8831-09ac35ecee79.png)
 

## Usage 
![image](https://user-images.githubusercontent.com/68631186/126875235-e4c2181d-c315-4f95-80d5-24ed57b134d7.png)


> Pattern UML  
> ![image](https://user-images.githubusercontent.com/68631186/126875634-4b6ec747-f292-4b4b-ae0e-436b693f3f75.png)

- The Handler declares the interface, common for all concrete handlers. 
  > It usually contains just a single method for handling requests, but sometimes it may also have another method for setting the next handler on the chain.  
- The Base Handler is an optional class where you can put the boilerplate code that’s common to all handler classes.
  > Usually, this class defines a field for storing a reference to the next handler. The clients can build a chain by passing a handler to the constructor or setter of the previous handler.  
  > The class may also implement the default handling behavior: it can pass execution to the next handler after checking for its existence.   
- Concrete Handlers contain the actual code for processing requests.  
  > Upon receiving a request, each handler must decide whether to process it and, additionally, whether to pass it along the chain.   
  > Handlers are usually self-contained and immutable, accepting all necessary data just once via the constructor.  

- **The Client may compose chains just once or compose them dynamically**, depending on the application’s logic. 
  >**A request can be sent to any handler in the chain—it doesn’t have to be the first one.**



## Example 

[reference from guru](https://refactoring.guru/design-patterns/chain-of-responsibility)
