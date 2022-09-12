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

|        |Throws exception	|Returns special value|
| ---    |      ---         |      ----           |
|Insert	 |`boolean` add(e)	|`boolean` offer(e)   |
|Remove	 |remove()	        | poll()              |
|Examine |element()	        | peek()              |

```java
boolean	offer(E e)  boolean	add(E e)
E peek()            E element()
E poll()            E remove()

// Collections
boolean	add(E e)
boolean	addAll(Collection<? extends E> c)

void clear()
int	size()
boolean	isEmpty()

boolean	equals(Object o)
int	hashCode()

boolean	contains(Object o)
boolean	containsAll(Collection<?> c)
boolean	retainAll(Collection<?> c)

E remove()
boolean	remove(Object o)
boolean	removeAll(Collection<?> c)
default boolean	removeIf(Predicate<? super E> filter)

default Stream<E> stream()
default Stream<E> parallelStream()
default void forEach(Consumer<? super T> action)

Iterator<E>	iterator()
default Spliterator<E> spliterator()

Object[] toArray()
<T> T[]	toArray(T[] a)
```



### LinkedList (QUEUE)

```java
import java.util.LinkedList;
import java.util.Queue;
  
public class QueueExample {
  
    public static void main(String[] args)
    {
        Queue<Integer> q = new LinkedList<>();
  
        // queue :  {0, 1, 2, 3, 4}
        for (int i = 0; i < 5; i++) q.add(i);
  
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

```java
PriorityQueue()
PriorityQueue(Collection<? extends E> c)
PriorityQueue(int initialCapacity)
PriorityQueue(int initialCapacity, Comparator<? super E> comparator)
PriorityQueue(PriorityQueue<? extends E> c)
PriorityQueue(SortedSet<? extends E> c)

boolean	offer(E e)
E peek()
E poll()
boolean	add(E e)
int	size()
boolean	remove(Object o)
void clear()

Comparator<? super E> comparator()
boolean	contains(Object o)
Iterator<E>	iterator()
Object[] toArray()
<T> T[]	toArray(T[] a)


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
PriorityQueue<Integer> queue = new PriorityQueue<>(
                            10, Collections.reverseOrder());
// ...
```