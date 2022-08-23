###### tags : `Design Pattern`
# Flyweight
[Example From Iluwater](https://github.com/iluwatar/java-design-patterns/tree/master/flyweight)  

Flyweight is a structural design pattern that lets you fit more objects into the available amount of RAM **by sharing common parts of state between multiple objects instead of keeping all
of the data in each object.**  
- It is used to minimize memory usage or computational expenses by sharing as much as possible with similar objects.


## Why needs this pattern ? 
![image](https://user-images.githubusercontent.com/68631186/127101526-547e60ca-5a5b-4f70-9b54-a6acfc0871a7.png)  
You created a game and sent the friend for a test drive. :
Although the game was running flawlessly on your machine, your friend wasn’t able to play for long.   

On his computer, the game kept crashing after a few minutes of gameplay.   
After spending several hours digging through debug logs, you discovered that the game crashed because of an insufficient amount of RAM.  

It turned out that your friend’s rig(Desktop-Computer) was much less powerful than your own computer, and that’s why the problem emerged so quickly on his machine.  

The actual problem was related to your particle system.   
Each particle, such as a bullet, a missile or a piece of shrapnel was represented by a separate object containing plenty of data.  
At some point, when the carnage on a player’s screen reached its climax, newly created particles no longer fit into the remaining RAM, so the program crashed.  

On closer inspection of the `Particle` class, it comsumes a lot more memory than other fields.  

### Intrinsic/Extrinsic State

![image](https://user-images.githubusercontent.com/68631186/127105488-c1b3e3f2-962f-46bb-a2db-b3f0781e2265.png)   
- The data represents the always changing context in which the particle exists, while the `color` and `sprite` remain constant for each particle.  
  > This constant data of an object is usually called the **intrinsic state**. It lives within the object; other objects can only read it,not change it.     
- The rest of the object’s state, often **altered from the outside** by other objects, is called the **extrinsic state**.  

The Flyweight pattern suggests that you stop storing the `extrinsic state` inside the object.
- we store extrinsic state in the interface 
Instead, you should pass this state to specific methods which rely on it.   
Only the `intrinsic state` stays within the object, letting you reuse it in different `context`s.      
![image](https://user-images.githubusercontent.com/68631186/127105688-525c26ae-dc71-4b03-97b0-564a2b30cd08.png)


> Pattern UML   
> ![image](https://user-images.githubusercontent.com/68631186/127119598-bd90a88c-4cd3-4a5a-91fd-ca1ef3e2279d.png)   

1. The Flyweight pattern is merely an optimization. 
   > **Before applying it, make sure your program does have the RAM consumption problem related to having a massive number of similar objects in memory at the same time.** 
   > Make sure that this problem can’t be solved in any other meaningful way.

2. **The `Flyweight` class contains the portion of the original object’s state that can be shared between multiple objects.**
   > **The same flyweight object can be used in many different contexts.**  
   > The state stored inside a flyweight is called `intrinsic`. The state passed to the flyweight’s methods is called `extrinsic`.

3. The Context class contains the extrinsic state, unique across all original objects. 
   - When a context is paired with one of the flyweight objects, it represents the full state of the original object.  

4. USUALLY, **the behavior of the original object remains in the flyweight class**. 
   > In this case, whoever calls a flyweight’s method must also pass appropriate bits of the `extrinsic state` into the method’s parameters.  
   > On the other hand, the behavior can be moved to the context class, which would use the linked flyweight merely as a data object.

5. The Client calculates or stores the extrinsic state of flyweights. 
   > **From the client’s perspective, a flyweight is a template object which can be configured at runtime by passing some contextual data into parameters of its methods.**

6. The Flyweight Factory manages a pool of existing flyweights. **With the factory, clients don’t create flyweights directly.** 
   > Instead, they call the factory, passing it bits of the `intrinsic state` of the desired flyweight.   
   > The factory looks over previously created flyweights and either returns an existing one that matches search criteria or creates a new one if nothing is found.


