# DeadLock and LiveLock

- [DeadLock and LiveLock](#deadlock-and-livelock)
  - [DeadLock](#deadlock)
    - [Method to Avoiding Deadlocks](#method-to-avoiding-deadlocks)
      - [ReentrantLock#tryLock();](#reentrantlocktrylock)
      - [Thread#join()](#threadjoin)
  - [Livelock](#livelock)
  - [Difference btw Livelock and Deadlock](#difference-btw-livelock-and-deadlock)
  - [Starvation](#starvation)

## DeadLock 

A deadlock occurs when two or more threads WAIT forever for a lock or resource held by another of the threads. 

```java
public class DeadlockExample{

    private Lock lockOP1 = new ReentrantLock(true);
    private Lock lockOP2 = new ReentrantLock(true);

    public static void main(string[] args){
        DeadLockExample deadlockExample = 
            new DeadLockExample();

        new Thread(deadLockExample::operation1, "thread_1").start();

        new Thread(deadLockExample::operation2,"thread_2").start();

    } 


    public void operation1(){
        lockOP1.lock();

        sleep(50);

        lockOP2.lock();

        lockOP2.unlock();
        lockOP1.unlock();
    }

    public void operation2(){
        lockOP2.lock():

        sleep(50);

        lockOP1.lock();

        lockOP1.unlock();
        lockOP2.unlock();
    }
}
```
To avoid the need for acquiring multiple locks for a thread

If a thread does need multiple locks
We should avoid any cyclic dependency in lock acquisition.

### Method to Avoiding Deadlocks

#### ReentrantLock#tryLock();

`boolean ReentrantLock#tryLock();`
- Acquires the lock if it is available and returns immediately with the value `true`. If the lock is not available then this method will return immediately with the value `false`.

`boolean tryLock(long time, TimeUnit unit) throws InterruptedException;`
- The specified waiting time elapses [more details](https://segmentfault.com/q/1010000005602326)

We can also use timed lock attempts, like the tryLock method in the Lock interface, to make sure that a thread does not block infinitely if it is unable to acquire a lock.

#### Thread#join()

A deadlock usually happens when one thread is waiting for the other to finish. In this case, we can use join with a maximum time that a thread will take.

## Livelock

- [examples](https://stackoverflow.com/questions/1036364/good-example-of-livelock)

To avoid a livelock, we need to look into the condition that is causing the livelock and then come up with a solution accordingly.

For example, if we have two threads that are repeatedly acquiring and releasing locks, resulting in livelock, **we can design the code so that the threads retry acquiring the locks at random intervals.**

This will give the threads a fair chance to acquire the locks they need.


## Difference btw Livelock and Deadlock

Deadlock are stuck indefinitely and do not make any state change. 

Livelock change their resource state continuously.

The notable point is that the resource state change has no effect and does not help the processes make any progress in their task

## Starvation

- Starvation can occur due to deadlock, livelock, or caused by another process.

As we have seen in the event of a deadlock or a live lock a process competes with another process to acquire the desired resource to complete its task. 

However, due to the deadlock or livelock scenario, it failed to acquire the resource and generally starved for the resource.

Further, it may occur that a process repeatedly gains access to a shared resource or use it for a longer duration while other processes wait for the same resource. Thus, the waiting processes are starved for the resource by the greedy process.

One of the possible solutions to prevent starvation is to use a resource scheduling algorithm with a priority queue that also uses the aging technique. 

Aging is a technique that periodically increases the priority of a waiting process. With this approach, any process waiting for a resource for a longer duration eventually gains a higher priority. And as the resource sharing is driven through the priority of the process, no process starves for a resource indefinitely.

Another solution to prevent starvation is to follow the **round-robin pattern** while allocating the resources to a process. In this pattern, the resource is fairly allocated to each process providing a chance to use the resource before it is allocated to another process again.