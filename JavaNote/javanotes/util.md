
## Collection

```java
boolean	add(E e)
boolean	addAll(Collection<? extends E> c)

boolean	contains(Object o)
boolean	containsAll(Collection<?> c)

int	size()
boolean	isEmpty()
void clear()
boolean	equals(Object o)
int	hashCode()

boolean	remove(Object o)
boolean	removeAll(Collection<?> c)
default boolean	removeIf(Predicate<? super E> filter)
["a","b","c","d"].removeIf(e -> e.contains("a")); // remove elements which contains "a"

boolean	retainAll(Collection<?> c) // retain all array2 the elements if array1 contains these.
[1,2,3].retainAll([2,3,4])  // [2,3]

Iterator<E>	iterator()
default Spliterator<E> spliterator()

default Stream<E> parallelStream()
default Stream<E> stream()
```


## Interface List

Interface List supports methods from collection with `int index` arg

Some new methods : `set`, `get`, `removeRange`, `replaceAll`, `indexOf` and `lastIndexOf`

```java
E set(int index, E element)
void add(int index, E element) 
E get(int index)

boolean addAll(int index, Collection<? extends E> c)
[1,2].addAll(1,[1,2,3]); // [1, 1, 2, 3, 2] 

[1,1,2,3,2].remove(1); // [1, 2, 3, 2]

[5,2,3,2].set(0, 5); // replace 0th element with 5 

List<E> subList(int fromIndex_Inclusive, int toIndex_Exclusive)

// use them if necessary
E remove(int index)
protected void removeRange(int fromIndex, int toIndex_exclusive)

default void replaceAll(UnaryOperator<E> operator)
[1,2,3,4,5].replaceAll(num -> num * 2); // [2,3,6,8,10]

ListIterator<E>	listIterator()
ListIterator<E>	listIterator(int index)

default void sort(Comparator<? super E> c)

int	indexOf(Object o)
int	lastIndexOf(Object o)
```

## Interface queue

|        |Throws exception	|Returns special value|
| ---    |      ---         |      ----           |
|Insert	 |`boolean add(E e)`|`boolean offer(E e)` |
|Remove	 |`E remove()`	    |`E poll()`       |
|Examine |`E element()`	    |`E peek()`       |

## Interface Deque

`Daque = Collection + List`.
Deque supports methods `add/remove/poll/peek/offer/get` + `First/Last`

It has Queue Methods and Stack Methods
| Queue Method |	Equivalent Deque Method|
|--------------| --------------------------|
| add(e)	   | addLast(e)              |
| offer(e)	   | offerLast(e)            |
| remove()	   | removeFirst()           |
| poll()	   | pollFirst()             |
| element()	   | getFirst()              |
| peek()	   | peekFirst()             |      

|Stack Method |	Equivalent Deque Method |
|-------| ------------------------------|
|push(e)| addFirst(e)                   |
|pop()	| removeFirst()                 |
|peek()	| peekFirst()                   |


```java
E getFirst()
E getLast()

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

E remove() // poll
E removeFirst()
E removeLast()

// Removes the first occurrence of the specified element from this Deque.
boolean	removeFirstOccurrence(Object o)
// Removes the last occurrence of the specified element from this Deque.
boolean	removeLastOccurrence(Object o)
```

```java
E pop()
void push(E e)

E element() // peek
```

## Interface subSet

```java
E ceiling(E e)
E floor(E e)

E higher(E e)
E lower(E e)

E pollFirst()
E pollLast()

Iterator<E> iterator()
Iterator<E> descendingIterator()
NavigableSet<E> descendingSet()

SortedSet<E> headSet(E toElement)
NavigableSet<E> headSet(E toElement, boolean inclusive)

NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)
SortedSet<E> subSet(E fromElement, E toElement)

SortedSet<E> tailSet(E fromElement)
NavigableSet<E> tailSet(E fromElement, boolean inclusive)
```

## Interface TreeSet

```java
E last()
E first()

boolean add(E e)
boolean addAll(Collection<? extends E> c)

void clear()
Object clone()
Comparator<? super E> comparator()
boolean contains(Object o)

boolean isEmpty()
Iterator<E>	iterator()

boolean remove(Object o)
int size()
```