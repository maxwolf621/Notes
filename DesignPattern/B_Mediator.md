###### tags : `Design Pattern`
# Mediator

[Example from CyC](https://github.com/CyC2018/CS-Notes/blob/master/notes/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%20-%20%E4%B8%AD%E4%BB%8B%E8%80%85.md)    
[Example from iluwater](https://github.com/iluwatar/java-design-patterns/tree/master/mediator)  
[Example from IT邦](https://ithelp.ithome.com.tw/m/articles/10225660)  

Mediator is a behavioral design pattern that lets you reduce chaotic dependencies between objects.  
The pattern restricts direct communications between the objects and forces them to collaborate only via a mediator object.  
- Mediator decouples a set of classes by forcing their communications flow through a mediating object. For Example  
  > ![image](https://user-images.githubusercontent.com/68631186/126874316-c247dfb9-7831-434b-8b15-7c042607096e.png)  

> Mediator UML  
> ![image](https://user-images.githubusercontent.com/68631186/126874348-a229b6c4-1812-43ab-a3d5-54e5af4cdebd.png)  
> ![image](https://user-images.githubusercontent.com/68631186/126875054-1d264560-4464-4865-a918-ba95c2a661ca.png)


- Components are various classes that contain some business logic. Each component has a reference to a mediator, declared with the type of the mediator interface. 
  > **The component isn’t aware of the actual class of the mediator**, so you can reuse the component in other programs by linking it to a different mediator.
- The Mediator interface declares methods of communication with components, which usually include just a single notification method. 
  > Components may pass any context as arguments of this method, including their own objects, but only in such a way that no coupling occurs between a receiving component
and the sender’s class.
- Concrete Mediators encapsulate relations between various components. 
  > **Concrete mediators often keep references to all components** they manage and sometimes even manage their lifecycle.
- **Components must not be aware of other components.** 
  > They only notify the mediator.  
  >> When the mediator receives the notification, it can easily identify the sender, which might be just enough to decide what component should be triggered in return.  
- **From a component’s perspective, it all looks like a total black box. The sender doesn’t know who’ll end up handling its request, and the receiver doesn’t know who sent the request in
the first place**.



