###### Tags : `Design Pattern`
# Bridge 
[Details for property of BridgePattern](https://refactoring.guru/design-patterns/bridge)  
[Example from iluwater](https://github.com/iluwatar/java-design-patterns/tree/master/bridge)  

Bridge is a structural design pattern that lets you **split a large class or a set of closely related classes into two separate hierarchies—abstraction** and implementation—which can be developed independently of each other.

Usage 
- Variable Weapon with Variable Enchantments
- Remote Controller with devices

> Pattern UML
> ![image](https://user-images.githubusercontent.com/68631186/126677359-5440bdcc-15b5-4c6b-953b-68444ee7ac46.png)  

![image](https://user-images.githubusercontent.com/68631186/126677744-11ece831-7b71-40ce-8c2b-2a7a9f5dd156.png)  

## Example 

![image](https://user-images.githubusercontent.com/68631186/126678728-dfc85f30-c7f0-4316-bb93-2bc3efa48347.png)

```java
// X bridge Y

class X {
  protected Y y;
  
  X x(Y y){
    this.y = y;
  }
  
  // ...
}

// similarity with decoration design pattern
X x = new X(new Y());

```

```java
 /**
   * <p> The "abstraction" defines the interface for the <strong> control </strong> 
   *     part of the two class hierarchies. </p>
   * <p> It maintains a reference to an object of the "implementation" hierarchy 
   *     and delegates all of the real work to this object. </p>
   */
class abstract RemoteControl{ 
    protected Device device;

    public RemoteControl(Device device){
        // DI principle
        this.device = device;
    }
    public void togglePower()
        if (device.isEnabled()){
            device.disable();
        }
        else{
            device.enable();
        }
    public volumeDown(){
        device.setVolume(device.getVolume() - 10);
    }
    public volumeUp(){
        device.setVolume(device.getVolume() + 10);
    }
    public channelDown(){
        device.setChannel(device.getChannel() - 1);
    }
    public channelUp(){
        device.setChannel(device.getChannel() + 1);
    }


/**
  * <p> We can extend classes from the abstraction hierarchy
  *     independently from device classes.
  */
class AdvancedRemoteControl extends RemoteControl{
    public void mute(){
        device.setVolume(0);
    }
}


/**
  *　<p> The <strong> implementation </strong> interface declares methods common to allconcrete implementation classes. </p>
  *  <p> It doesn't have to match the abstraction's interface.  </p> 
  *  <p> In fact, the two interfaces can be entirely different. </p>
  *  <p> Typically the implementation interface provides only primitive operations, 
  *       while the abstraction defines higher-level operations based on those primitives. </p>
public interface Device{
    public isEnabled()        ;
    public enable()           ;
    public disable()          ;
    public getVolume()        ;
    public setVolume(int percent) ;
    public getChannel()       ;
    public setChannel(int channel);
}

/**
  * <p> All devices follow the same interface. </p>
  */
class Tv implements Device{
    // method implementations ...
}
class Radio implements Device{
   // method implementations ...
}

/**
  * <p> Client </p>
  */
public class Test{
  public static void main(String[] args){
    
    // remote of TV
    tv = new Tv();
    remote = new RemoteControl(tv);
    remote.togglePower();
    
    // remote of Radio
    radio = new Radio();
    remote = new AdvancedRemoteControl(radio);
  }
}
```


