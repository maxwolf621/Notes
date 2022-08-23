###### tags: `Design Pattern` 
# Proxy 
[Other Proxies](http://corrupt003-design-pattern.blogspot.com/2016/10/proxy-pattern.html)  
[Example from cyc](https://github.com/CyC2018/CS-Notes/blob/master/notes/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%20-%20%E4%BB%A3%E7%90%86.md)   
[Example from iluwater](https://github.com/iluwatar/java-design-patterns/tree/master/proxy)  

Proxy is a structural design pattern that lets you provide a substitute or placeholder for another object.  
- A proxy controls access to the original object, allowing you to perform something either before or after the request gets through to the original object  

> Das Muster  
> ![](https://i.imgur.com/gBFwn55.png)    
> ![image](https://user-images.githubusercontent.com/68631186/126905330-0b920c6c-7591-4504-8927-b346f7f18409.png)  
- The Service Interface declares the interface of the Service. 
    > **The proxy must follow this interface to be able to disguise itself as a service object**.
- The Service is a class that provides some useful business logic.
- **The Proxy class has a reference field that points to a service object**. 
    > After the proxy finishes its processing (e.g., lazy initialization, logging, access control, caching, etc.), 
    > it passes the request to the service object. **Usually, proxies manage the full lifecycle of their service objects.**
- **The Client should work with both services and proxies via the same interface.** This way you can pass a proxy into any code that expects a service object.


## Usage
> ![image](https://user-images.githubusercontent.com/68631186/126905142-44b5392e-e923-4949-baab-4288b341f6d5.png)  
> ![image](https://user-images.githubusercontent.com/68631186/126905293-b93f2591-71e0-4a71-9cbc-c867ddcc8ff6.png)  

> ![image](https://user-images.githubusercontent.com/68631186/126905236-d3a493a3-8bb0-4c71-89e1-8d5e58623fb1.png)  
> A credit card is a proxy for a bank account, which is a proxy for a bundle of cash.  
> Both implement the same interface: they can be used for making a payment. 
>> - A consumer feels great because there’s no need to carry loads of cash around. 
>> - A shop owner is also happy since the income from a transaction gets added electronically to the shop’s bank account without the risk of losing the deposit or getting robbed on the way to the bank.

## Remote Proxy  
![](https://i.imgur.com/Bw4YsvT.png)  

Remote Proxies provide a local representation of another remote object or resource.  
- Remote proxies are responsible not just for representation but also for some maintenance work.  
    > Such work could include connecting to a remote machine and maintaining the connection, encoding and decoding characters obtained through networking traffic, parsing, etc. For example   
    > ![image](https://user-images.githubusercontent.com/68631186/126907269-36eb41c4-1f89-4309-b7d0-cfbec879f4fb.png)  
    > [Example For Remote Proxy](https://fjp.at/design-patterns/proxy)     
    > [How to use `rim` package](https://github.com/maxwolf621/JavaNote/blob/main/rim.md)    

```java
import java.rmi.*;
public class GumballMachineRemote extends Remote{
    /**
      * all return type need to be 
      * <strong> primitve </strong> or 
      * <strong> serializable </strong>
      */
    public int getCount() throws RemoteException;
    public int getLocation() throws RemoteException;
    public int getState() throws RemoteException;
}


import java.io.*;
public interface State extends Serializable{
   //...
}

public class NoQuarterState extends State{
    private static final long serialVersionUID = 2L;

    /**
      *<p> We dont wannt entire gumball machine serialized 
      *    and transferred with the state object </p>
      */
    transient GumballMachine gumballMachine;

    //...
}

/**
  * <p> Concrete Remote Server </p>
  * <p> {@code UnicastRemoteObject} let {@code GumballMachine} to act as server </p>
  * <p> Implement {@code GumballMachineRemote} to have remote server object's implementation methods 
  */
import java.rmi.*;
import java.rmi.server.*;
 public class GumballMachine
        extends UnicastRemoteObject implements GumballMachineRemote 
{
    private static final long serialVersionUID = 2L;

    //..

    /**
      * <p> @throws RemoteException is needed
      *     because super class does so </p>
      */
    public GumballMachine(String location, int numberGumballs) throws RemoteException{
      //..
    }

    //...
}

/**
  * <p> Registering our concrete remote server </p> 
  * <p> Fire up the server </p>
  */
import java.rmi.*;

public class GumballMachineTestDrive {
    public static void main(String[] args) {
        GumballMachineRemote gumballMachine = null;
        int count;

        if (args.length < 2) {
            System.out.println("GumballMachine <name> <inventory>");
            System.exit(1);
        }

        try {
            count = Integer.parseInt(args[1]);

            gumballMachine = new GumballMachine(args[0], count);

            /**
              * <p> Publish our server to RMI registry 
              *     so the client can communicate with server </p>
              * <strong> client <-> RMI registry <-> server </strong> 
              */
            Naming.rebind("//" + args[0] + "/gumballmachine", gumballMachine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
* client 
*/
import java.rmi.*;

public class GumballMonitor {
    GumballMachineRemote machine;

    public GumballMonitor(GumballMachineRemote machine) {
        this.machine = machine;
    }

    public void report() {
            try {
                System.out.println("Gumball Machine: " + machine.getLocation());
                System.out.println("Current inventory: " + machine.getCount() + " gumballs");
                System.out.println("Current state: " + machine.getState());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
     }
}


/**
  * Run the program 
  */
import java.rmi.*;
 
public class GumballMonitorTestDrive {
 
        public static void main(String[] args) {
            String[] location = {"rmi://santafe.mightygumball.com/gumballmachine",
                                 "rmi://boulder.mightygumball.com/gumballmachine",
                                 "rmi://seattle.mightygumball.com/gumballmachine"}; 

            if (args.length >= 0)
            {
                location = new String[1];
                location[0] = "rmi://" + args[0] + "/gumballmachine";
            }

            GumballMonitor[] monitor = new GumballMonitor[location.length];



            for (int i=0;i < location.length; i++) {
                try {
                    GumballMachineRemote machine = 
                            (GumballMachineRemote) Naming.lookup(location[i]);
                    monitor[i] = new GumballMonitor(machine);
                    System.out.println(monitor[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for(int i=0; i < monitor.length; i++) {
                monitor[i].report();
            }
         }
}
```
#### Flow of Remote Proxy


> ![image](https://user-images.githubusercontent.com/68631186/126908323-fb665482-425f-4f96-bdb3-8fb8e3953298.png)  
> CEO   
> - runs the monitor     
> - graps(`lookup`) the proxies to the remote service  
> - calls remote server object methods(`getState`, `getCount` and `getLocation`)     



> ![image](https://user-images.githubusercontent.com/68631186/126908328-bc717ead-2ce8-428b-8dfb-1e7c5cb47156.png)  
> - `getState()` is called on proxy, which forwards the call to the remote service  
> - the skeleton receives the request and then forwards it to the gumballmachine   


> ![image](https://user-images.githubusercontent.com/68631186/126908338-a89caeef-4577-4968-8fcf-5967e5c4ddc7.png)  
> - the gumballmachine returns the state to the skeleton  
> - the skeleton serializes it and transfers it back over the wire to the proxy/stub  
> - the proxy deserializes it and returns it as an object to the monitor  

By the flow we can clearly know the definition of proxy pattern  
- Use the Proxy Pattern to create a representative object that controls access to another object, which may be remote, expensive to create, or in need of securing.  


## Virtual Proxy

**The Virtual Proxy often defers the creation of the object until it is needed.**  
![](https://i.imgur.com/nEo580y.png)  
 

#### An example downloading a CD cover using Virtual Proxy

[sourceCode](https://github.com/bethrobson/Head-First-Design-Patterns/tree/master/src/headfirst/designpatterns/proxy/virtualproxy)

[Java Icon](https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html)

![](https://i.imgur.com/v7ZVpWQ.png)  

> How virtual proxy of the example working with illustration ?
> --- 
>>`ImageProxy` will initialize a instance of `ImageIncon` and use it to get image from the `Server`
>>![](https://i.imgur.com/dsTQKGq.png)  
>>> When instance of `ImageIcon` gets a complete image from the `Server` then it would display it directly on client's screen without `ImageProxy`'s help  
>>> ![](https://i.imgur.com/9l7FU9x.png)  


 
```java
import java.net.*;     // thread
import java.awt.*;       
import javax.swing.*;  // Icon

class ImageProxy implements Icon 
{ 
    /** 
     * <p> Create A instance of realSubject.</p>
     */
    volatile ImageIcon imageIcon;
    
    
    final URL imageURL;
    
    /**
     *  For downloading the Image
     */
    Thread retrievalThread;
    
    boolean retrieving = false;

    /**
     * @param url 
     * where the image can be downloded
     */
    public ImageProxy(URL url) { imageURL = url; }

    public int getIconWidth()  {/*skip*/}
    public int getIconHeight() {/*skip*/}

    synchronized void setImageIcon(ImageIcon imageIcon)
    {
    //To protect reads (volatile) 
    //     and weites (syncronized setter method)
        this.imageIcon = imageIcon;

    }

    // To paint the icon on the screen
    public void paintIcon(final Component c, 
                  Graphics  g, int x,  int y)
    {
        if (imageIcon != null) 
        {
        //** Display Image directly
        //          Using realSubject
            imageIcon.paintIcon(c, g, x, y);
        } 
        else 
        {
            g.drawString(
                "Loading album cover, please wait...",
                x+300, y+190);
            if (!retrieving) 
            {
                retrieving = true;
                retrievalThread = new Thread(new Runnable() 
                {
                    public void run() 
                    {
                        try
                        {// Using a instance of ImageIcon
                         //    NOT instance of ImageProx itself
                            setImageIcon(new ImageIcon
                            (imageURL, "Album Cover"));
                            c.repaint();
                        }
                        catch (Exception e) 
                        {
                            e.printStackTrace();
                        }
                    }
                });

                /* using Lambda
                retrievalThread = new Thread(() -> {
                        try 
                        {
                            setImageIcon(new ImageIcon
                            (imageURL, 
                            "Album Cover"));
                            c.repaint();
                        } 
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                });
                */
                
                // starting to download the image icon
                retrievalThread.start();
            }
        }
    }
}
```


## Protect Proxy (from the java API's dynamic Proxy)

[SourceCode](https://github.com/bethrobson/Head-First-Design-Patterns/tree/master/src/headfirst/designpatterns/proxy/javaproxy)

The Difference of Protect Proxy from the other proxies is
- The proxy will be generated by Java itself and implements the entire Subject interface, so all we need to handle is supply a `handler` that knows what to do when a method is called on it

> Pattern
> : ![](https://i.imgur.com/DaQEktc.png)

Steps
1. InvocationHandlers(concrete ones and interface one) implement the behaviour of the proxy
2. Wrap the objects of InvocationHandler with the appropriate proxy (e.g. InvocationHandler_2 and InvocationHandler_1 ...)
3. Creating invoke() in Concrete InvocationHandler
    ![](https://i.imgur.com/hF5ozyX.png)
4. Creating the Proxy class and instantiating the Proxy object in class InvocationHandler
    ![](https://i.imgur.com/ixlMgOL.png)


