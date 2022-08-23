# Queue
- [Queue](#queue)
  - [Reference](#reference)
  - [Class of Queue](#class-of-queue)
  - [Methods](#methods)
    - [LinkedList (QUEUE)](#linkedlist-queue)
  - [PriorityQueue (Min Heap Implementation)](#priorityqueue-min-heap-implementation)
    - [Iteration](#iteration)
    - [PriorityQueue (Max Heap Implementation)](#priorityqueue-max-heap-implementation)


![](../images/queueJava.png)

## Reference
[geeksforgeeks](https://www.geeksforgeeks.org/queue-interface-java/)  
[jenkov](http://tutorials.jenkov.com/java-collections/queue.html#:~:text=The%20Java%20Queue%20interface%2C%20java.&text=Queue%20represents%20a%20data%20structure,of%20the%20Java%20Collection%20interface.)  
[PriorityQueue](https://www.geeksforgeeks.org/priority-queue-class-in-java/)
[priorityQueue TimeComplexity](https://stackoverflow.com/questions/12719066/priority-queue-remove-complexity-time)

## Class of Queue

`Queue` is an INTERFACE, so objects cannot be created of the type queue.
```java
// Obj is the type of the object to be stored in Queue 
Queue<Obj> queue = new PriorityQueue<Obj> (); 
```
Being an interface the queue needs a concrete class   for the declaration and the most common classes are the `PriorityQueue` and `LinkedList` (**both the implementations are not thread safe.**) in Java.
 
- `PriorityBlockingQueue` is one alternative implementation if thread safe implementation is needed.


## Methods

Methods from `interface java.util.Collection`
```java
// Collections
boolean	add(E e)
boolean	addAll(Collection<? extends E> c)
void	clear()
boolean	contains(Object o)
boolean	containsAll(Collection<?> c)
boolean	equals(Object o)
int	hashCode()
boolean	isEmpty()
Iterator<E>	iterator()
default Stream<E> parallelStream()
boolean	remove(Object o)
boolean	removeAll(Collection<?> c)
boolean	retainAll(Collection<?> c)
int	size()
default Stream<E> stream()
Object[] toArray()
<T> T[]	toArray(T[] a)
default void	forEach(Consumer<? super T> action)

---

Eelement()
boolean	offer(E e)
E peek()
E poll()
E remove()
default Spliterator<E>	spliterator()
default boolean	removeIf(Predicate<? super E> filter)
```

|        |Throws exception	|Returns special value|
| ---    |      ---         |      ----           |
|Insert	 |`boolean` add(e)	|`boolean` offer(e)   |
|Remove	 |remove()	        | poll()              |
|Examine |	element()	    | peek()              |

### LinkedList (QUEUE)

```java
import java.util.LinkedList;
import java.util.Queue;
  
public class QueueExample {
  
    public static void main(String[] args)
    {
        Queue<Integer> q = new LinkedList<>();
  
        // queue :  {0, 1, 2, 3, 4}
        for (int i = 0; i < 5; i++)
            q.add(i);
  
        // display contents of the queue.
        System.out.println("Elements of queue "
                           + q);
  
        // remove the head of queue
        // 0
        int removedele = q.remove();
  
        // view the head of queue
        // 1
        int head = q.peek();
        
        // Queue Size
        // 4
        int size = q.size();
    }
}
```
## PriorityQueue (Min Heap Implementation)

### Iteration

```java
Iterator<E> iter = myPriorityQueue.iterator();

while (iter.hasNext()) {
    current = iter.next();
    // ...
}
// or
for (E e : myQueue) {
        // ...
}
```

### PriorityQueue (Max Heap Implementation)

```java
PriorityQueue<Integer> queue = new PriorityQueue<>(10, Collections.reverseOrder());
queue.offer(1);
queue.offer(2);
queue.offer(3);

// ...
```