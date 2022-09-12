# Stack From Vector

- [Stack From Vector](#stack-from-vector)
  - [Reference](#reference)
  - [Stack](#stack)
  - [ArrayDeque & LinkedList](#arraydeque--linkedlist)
    - [ArrayDeque vs LinkedList](#arraydeque-vs-linkedlist)
    - [Deque](#deque)
  - [Linked List](#linked-list)

Stack class in Java is a legacy class and inherits from Vector in Java.   

It is a **thread-safe class** and hence involves overhead.     
Use `deque` when we do not need thread safety instead.

![圖 1](../../images/1fef51ac296b998d1523e0e130d217cabc0959a6c886bfbf8766b64e5c3c9b3d.png)  
![圖 4](../images/278f13baf8d21fa522f728785943075c7a3952d86f055fabf10187deb1cdf047.png) 

![圖 2](../../images/d6d10331410504c53fb7a559a5435a5f89452bb56d4404e6c2afb51d1190d3b0.png)  

## Reference

[geekForGeeks](https://www.geeksforgeeks.org/stack-class-in-java/)
[deque](https://ithelp.ithome.com.tw/articles/10229634)   
[Java ArrayDeque](https://www.cainiaojc.com/java/java-arraydeque.html)

## Stack 

```java
Stack stk = new Stack();  
//Or
Stack<T> stk = new Stack<T>();  
```

```java 
// STACK Methods
E pop()
E push(E item)

// Queue 8 Stack Method
E peek()

boolean	empty()
// It determines whether an object exists in the stack. 
// If the element is found, 
// It returns the position of the element 
// from the top of the stack. Else, it returns `-1`.
int	search(Object o)

// Vector
boolean	add(E e)
void add(int index, E element)
boolean	addAll(Collection<? extends E> c)
boolean	addAll(int index, Collection<? extends E> c)

void copyInto(Object[] anArray)
void setSize(int newSize)
int	capacity()

// XXXelement
void addElement(E obj)
void insertElementAt(E obj, int index)
void setElementAt(E obj, int index)
E elementAt(int index)
Enumeration<E> elements()
E firstElement()
E lastElement()
boolean removeElement(Object obj)
void removeElementAt(int index)
void removeAllElements()


// List
void ensureCapacity(int minCapacity)
void trimToSize()

int	size()
boolean	isEmpty()
void clear()
E get(int index)
Object clone()

boolean	equals(Object o)
int	hashCode()

boolean	retainAll(Collection<?> c)
boolean	contains(Object o)
boolean	containsAll(Collection<?> c)

E remove(int index)
boolean	remove(Object o)
boolean	removeAll(Collection<?> c)
protected void removeRange(int fromIndex, int toIndex)

int	indexOf(Object o)
int	indexOf(Object o, int index)
int	lastIndexOf(Object o)
int	lastIndexOf(Object o, int index)

Iterator<E>	iterator()
ListIterator<E>	listIterator()
ListIterator<E>	listIterator(int index)

List<E>	subList(int fromIndex, int toIndex)
Object[] toArray()
<T> T[]	toArray(T[] a)
String toString()
```


## ArrayDeque & LinkedList

It is recommended to use `ArrayDeque` for stack implementation as it is more efficient in a single-threaded environment.

### ArrayDeque vs LinkedList

LinkedList supoorts `null` value. ArrayDeque Does not
LinkedList needs more capacity to store the data so ArrayDeque might faster than it

### Deque

`add/remove/poll/peek/offer/get-First/Last`


It has Queue Methods and Stack Methods
| Queue Method |	Equivalent Deque Method  |
|--------------| --------------------------|
| add(e)	     |  addLast(e)
| offer(e)	   | offerLast(e)
| remove()	   | removeFirst()
| poll()	     | pollFirst()
| element()	   | getFirst()
| peek()	     | peekFirst()           

|Stack Method |	Equivalent Deque Method |
|-------| ------------------------------|
|push(e)| addFirst(e)                   |
|pop()	| removeFirst()                 |
|peek()	| peekFirst()                   |


```java
E pop()
void push(E e)

int	size()
boolean	contains(Object o)
Iterator<E>	descendingIterator()

E getFirst()
E getLast()

// Retrieves, but does not remove, 
// the head of the queue represented by this deque 
// (in other words, the first element of this deque).
E element()

boolean add(E e)
void addFirst(E e)
void addLast(E e)

boolean offer(E e)
boolean offerFirst(E e)
boolean offerLast(E e)

E peek()
E peekFirst()
E peekLast()

E poll()
E pollFirst()
E pollLast()

E remove()
boolean remove(Object o)
E removeFirst()
E removeLast()

// Removes the first occurrence of the specified element from this deque.
boolean	removeFirstOccurrence(Object o)
// Removes the last occurrence of the specified element from this deque.
boolean	removeLastOccurrence(Object o)
```

## Linked List

```java
boolean	add(E e)
void add(int index, E element)
boolean	addAll(Collection<? extends E> c)
boolean	addAll(int index, Collection<? extends E> c)
void addFirst(E e)  
void addLast(E e)

// Stack 
E pop()
void push(E e)
E element()
void clear()
Object clone()

E get(int index)
E getFirst() // if null NoSuchElementException
E getLast()  // if null NoSuchElementException

Iterator<E>	descendingIterator()
ListIterator<E>	listIterator(int index)

boolean contains(Object o)

int	indexOf(Object o)
int	lastIndexOf(Object o)

boolean	offer(E e)
boolean	offerFirst(E e)
boolean	offerLast(E e)

E peek()
E peekFirst()
E peekLast()

E poll()
E pollFirst()
E pollLast()

boolean remove(Object o)
E remove()
E remove(int index)
E removeFirst()
E removeLast()

boolean	removeFirstOccurrence(Object o)
boolean	removeLastOccurrence(Object o)

Object[] toArray()
<T> T[] toArray(T[] a)
```