###### tags : `JAVA`
# java.rmi.*

[reference from luckyqiao](https://segmentfault.com/a/11900000165980690)  
[`java.rim.*` is used for remote proxy's example](https://github.com/maxwolf621/DesignPattern/blob/main/S_Proxy.md)  
## What is RMI

[source](https://examples.javacodegeeks.com/java-rmi-java-remote-method-invocation-example/)  

Remote Method Invocation is an object-oriented way of RPC (Remote Procedure Call) to realize communication between distributed objects in a distributed computing environment. 

- **`java.rmi.*` packages allows a Java program running on one Java virtual machine (`client`) to invoke methods on another Java virtual machine (`server`).**   
  > **Java RMI is basically a client-server model**.   
  > Especially it supports transferring **serialized** Java objects from machine to machine over the network which makes automatic management of distributed objects come true.  


## Classes in `java.rimi.*` 

![image](https://user-images.githubusercontent.com/68631186/126907371-4269a28e-53b8-4500-936b-be33fb74caf2.png)  
![image](https://user-images.githubusercontent.com/68631186/126902902-ed82808d-9d30-4445-baa0-e37123ef265e.png)  


```java
/**
  * <p> Remote </p>
  * <p> The Remote interface serves to identify interfaces whose methods may be invoked from a non-local virtual machine. 
  *     Any object that is a remote object must directly or indirectly implement this interface. 
  *     Only those methods specified in a "remote interface", 
  *     an interface that extends java.rmi.Remote are available remotely. </p>
  * <p> the constructor of concrete remote must @throws RemoteException </p>
  * ----------------------------------
  * <p> Naming </p>
  * <p> The Naming class provides methods for storing and obtaining references to remote objects in a remote object registry. 
  *     Each method of the Naming class takes as one of its arguments a name that is in URL format of the form:
  *      <pre> //host:port/name </pre>
  *------------------------------------
  * <p> Registry </p> 
  * <p> Registry is a remote interface to a simple remote object registry 
  *     that provides methods for storing and retrieving remote object 
  *     references bound with arbitrary string names. 
  *     The {@code bind}, {@code unbind}, and {@code rebind} methods are used 
  *     to alter the name bindings in the registry, 
  *     and the {@code lookup} and {@code list} methods are used to query the current name bindings. </p>
  *-------------------------------------
  *<p> LocateRegistry </p>
  *<p> LocateRegistry is used to obtain a reference to a bootstrap remote object registry 
  *    on a particular host (including the local host), 
  *    or to create a remote object registry that accepts calls on a specific port.
  *    <strong> Note that a {@code getRegistry} call does not actually make a connection to the remote host. </strong>
  *    <strong> It simply creates a local reference to the remote registry 
  *    and will succeed even if no registry is running on the remote host. </strong> 
  *    Therefore, a subsequent method invocation to a remote registry returned as a result of this method may fail. </p>
  *--------------------------------------
  *<p> class b extends UnicastRemoteObject </p>
  *<p> Give the subclass b the ability to act as a remote service </p>
  */
```


![image](https://user-images.githubusercontent.com/68631186/126903297-f904b2c2-d5e3-4909-ba39-e0854a0b3560.png)  
```java

/**
  * <p> Remote Server Interface </p>
  * <p> Abstract methods are needed for 
  *     an concrete Remote Server </p>
  */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteHello extends Remote {
    String sayHello(String name) throws RemoteException;
}

/**
  * <p> Concrete Remote Server </p>
  * <p> Remote Server Implementation </P>
  */
import java.rmi.RemoteException;

public class RemoteHelloImpl implements RemoteHello {
    public String sayHello(String name) throws RemoteException {
        return String.format("Hello, %s!", name);
    }
}


/**
  * <p> Create Mediator(RMI Registry) </p>
  * <p> RMI Registry is a mediator 
  *     handle communication btw client and server </p>
  */
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

public class RegistryServer {
    public static void main(String[] args) throws InterruptedException{
        try {
            LocateRegistry.createRegistry(8000); // registry host : 8000
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        CountDownLatch latch=new CountDownLatch(1);
        latch.await(); // thread
    }
}

/**
  * create Server
  */ 
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {

    public static void main(String[] args) {

        RemoteHello remoteHello = new RemoteHelloImpl();
        try {
            /**
              * <p> {@code UnicastRemoteObject.exportObject(Remote obj, int port)} 
              *     Exports the remote object {@code remoteHello} 
              *     to make it available to receive incoming calls, 
              *     using the particular supplied port </p>
              */
            RemoteHello stub = (RemoteHello) UnicastRemoteObject.exportObject(remoteHello, 4000); 
            /**
              * <p> Returns a reference to the 
              *     remote object Registry 
              *     on the specified host and port. <p>
              */
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 8000); 
            /**
              *<p> Binds a remote reference to the specified name in this registry. </p>
              */
            registry.bind("hello", stub); 
        } catch (AlreadyBoundException | IOException e) {
            e.printStackTrace();
        }

    }
}
```


### client get stub object
![image](https://user-images.githubusercontent.com/68631186/126907506-e76dfd6a-0119-4907-88fb-9ec604de3b0d.png)
1. Client does a lookup on the RMI registry `Naming.lookup("rmi://127.0.0.1/RemoteHello");`
2. RMI registry returns the stub object (as the return value of the lookup method) and RMI deserializes the stub automatically.
3. Client invokes a method on the stub, as if the stub IS the real service
```java
/**
  * create Client 
  */
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) {
        try {
            /**
              * <p> get local reference to the remote object {@code Registry} </p>
              */
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 8000);
            /**
              * <p> Look up a rmote server object {@code RemoteHello} 
              *     in remote object {@code Registry} </p>
              */
            RemoteHello remoteHello = (RemoteHello) registry.lookup("hello"); 
            
            /**
              * <p> call server methods
              *     if remote server object is existing </p>
              */
            System.out.println(remoteHello.sayHello("World")); 
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
```
