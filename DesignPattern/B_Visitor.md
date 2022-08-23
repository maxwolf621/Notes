###### tags : `Design Pattern`
# Visitor
[Guru](https://refactoring.guru/design-patterns/visitor)   
[Example from iluwater](https://github.com/iluwatar/java-design-patterns/tree/master/visitor)  
[Example From Cyc](https://github.com/CyC2018/CS-Notes/blob/master/notes/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%20-%20%E8%AE%BF%E9%97%AE%E8%80%85.md)

The Visitor pattern suggests that you place the new behavior into a separate class called `visitor`, instead of trying to integrate it(new methods ...etc) into existing classes.  

> Pattern UML
> ![image](https://user-images.githubusercontent.com/68631186/126866473-92b3419c-a77b-442e-a1e6-d861c94e1a82.png)
> The concrete element **accepts** the visitors   
> The concrete visitor **visits** the elements to operate something on them    
  
- **Each Concrete Visitor implements several versions of the same behaviors, tailored for different concrete element classes**  
- The Element interface declares a method for **accepting** visitors. 
  > _This method should have one parameter declared with the type of the visitor interface._
- **Each Concrete Element must implement the `accept` method.** 
  > The purpose of this method is to redirect the call to the proper visitor’s method corresponding to the current element class.   
  > Be aware that even if a base element class implements this method, all subclasses must still override this method in their own classes and call the appropriate method on the visitor object.  
- The Client usually represents a collection or some other complex object (for example, a Composite tree). 
  > Usually, clients aren’t aware of all the concrete element classes because they work with objects from that collection via some abstract interface.  

> #### Generally, this pattern defines operations or new behaviours(methods on `Visitor`) that can be performed on the nodes of the data structure(existing class `Element`s).  
> ![image](https://user-images.githubusercontent.com/68631186/126937355-34f049af-f008-4a9e-b9b4-6222c41c27e1.png)  

**The original object(existing class `Element`s) that had to perform the behavior is now passed to one of the visitor’s methods as an argument `accept(Object originalObject)`**, providing the method access to all necessary data contained within the object.   

> #### A good insurance agent(**Visitor**) is always ready to offer different policies to various types of organizations(**Elements**).
> ![image](https://user-images.githubusercontent.com/68631186/126867888-0c08a793-6615-44fa-955a-612d7483a5dc.png)
  
  

