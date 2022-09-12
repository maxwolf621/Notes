## Basics
- [JavaBasic](javanotes/Basic.md)
- [Static](javanotes/static.md)
- [Generic](javanotes/Generic.md)
- [Exception](javanotes/exception.md)
- [HashCode and Equals](javanotes/hashCodeAndEquals.md)  
- [Garbage Collection and Object Copies](javanotes/GCandCopy.md)  
- [Iterator](javanotes/Iterator.md) 
- [Java 8](javanotes/java8.md)
- [Functional Programming](javanotes/functionalProgramming.md)
## Network
- [Serialization](javanotes/Serialization.md)  
- [Session](javanotes/Session.md)  
- [Cookie](javanotes/cookie.md)  
- [Base64](javanotes/Base64.md)  
- [HTTP and Java Servlet](javanotes/HttpServlet.md)    
- [Concept IO](javanotes/IO.md)   
- [Thread](javanotes/Thread.md)   
    - [Java Dead Lock](javanotes/Deadlock.md) 
    - [Thread Safe Class](javanotes/ThreadSafe.md)  
    - [Reentrant vs Thread-safe](javanotes/Compares.md)   
    - [Concurrency and Parallel](javanotes/concurrency&parallel.md)  

## Reactor Java
- [Reactive Programming && Reactor](javanotes/ReactiveProgramming.md)
- [Sink](javanotes/sink.md)
- [Subscribe](javanotes/subscribe.md)
- [ReactorLog](javanotes/ReactorLog.md)
- [Callback Hell](javanotes/CallBackHell.md)
- [rxjava vs reactor](https://nurkiewicz.com/2019/02/rxjava-vs-reactor.html)
    - [rxJava](javanotes/rxJava.md)

## UTIL
![圖 1](images/63a996fabec2ccf491ee0ad618bc0d5ff4fbb0b5b033cb48029dedffb5de9d49.png) 
![圖 3](images/dc563b068bcbf5a3d7c08d6ddad0426b59abb6eef83470eaddede660cd58cc91.png)  

Collection
`stream` and `parallelStream`
```java
boolean	add(E e)
boolean	addAll(Collection<? extends E> c)
void clear()
int	size()
boolean	isEmpty()

boolean	contains(Object o)
boolean	containsAll(Collection<?> c)
boolean	retainAll(Collection<?> c)
boolean	remove(Object o)
boolean	removeAll(Collection<?> c)
default boolean	removeIf(Predicate<? super E> filter)

boolean	equals(Object o)
int	hashCode()

Iterator<E>	iterator()
default Spliterator<E> spliterator()

default Stream<E>	parallelStream()
default Stream<E>	stream()

Object[] toArray()
<T> T[]	toArray(T[] a)
```

List
```java
// insertion,get, clear, size, replace, retain
// isEmpty
boolean	add(E e)
void add(int index, E element)
boolean	addAll(Collection<? extends E> c)
boolean	addAll(int index, Collection<? extends E> c)
E set(int index, E element)
E get(int index)
void clear()
boolean	isEmpty()
int	size()
E remove(int index)
boolean	remove(Object o)
boolean	removeAll(Collection<?> c)
default void replaceAll(UnaryOperator<E> operator)
boolean	retainAll(Collection<?> c)

// search 
int	indexOf(Object o)
int	lastIndexOf(Object o)
boolean	contains(Object o)
boolean	containsAll(Collection<?> c)

// hashCode and equals
int	hashCode()
boolean	equals(Object o)

// literator
Iterator<E>	iterator()
default Spliterator<E> spliterator()
ListIterator<E>	listIterator()
ListIterator<E>	listIterator(int index)

default void sort(Comparator<? super E> c)

List<E>	subList(int fromIndex, int toIndex)
Object[] toArray()
<T> T[]	toArray(T[] a)
```

- [List](javanotes/util.md)  
- [Stack(ArrayDeque&LinkedList)](javanotes/stack.md)
- [Queue](javanotes/queue.md)
- [MAP 設計概念](javanotes/mapConcept.md)
  - [Methods of Map](javanotes/mapUsage.md)
- [SET](javanotes/Set.md)
- [String](javanotes/String.md)
- [Stream](javanotes/Stream.md)
- [Record](javanotes/record.md)
- [Collectors](javanotes/collectors.md)
- [Optional](javanotes/Optional.md)
- [Stream & Optional](javanotes/streamOptional.md)
## java library 
- [lombok](lomBok.md)   
- [`MapStruct`](mapStruct.md)

---

- [MVN Command ON Linux](mvn.md)
- [rim](javanotes/rim.md)
- [time](javanotes/.md)