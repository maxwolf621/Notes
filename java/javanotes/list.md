
# List

- [List](#list)
  - [Reference](#reference)
- [List Interface](#list-interface)
    - [asList(E[] e) && toArray(E[] e)](#asliste-e--toarraye-e)
  - [Interface list](#interface-list)
  - [List Methods](#list-methods)
  - [ArrayList](#arraylist)
  - [Vector](#vector)
  - [LinkedList](#linkedlist)
  - [Array of Array](#array-of-array)

![](https://i.imgur.com/B0TSLRj.png)  

## Reference 
- [Util methods](https://jax-work-archive.blogspot.com/2015/02/java-setlistmap.html)
- [Reference](https://github.com/CyC2018/CS-Notes/blob/master/notes/Java%20%E5%AE%B9%E5%99%A8.md)
- [Container Examples](https://www.bookstack.cn/read/java-se6/docs-ch13.md)
- [Container](https://www.bookstack.cn/read/java-se6/docs-ch13.md#%E7%AC%AC%2013%20%E7%AB%A0%20%E7%89%A9%E4%BB%B6%E5%AE%B9%E5%99%A8%EF%BC%88Container%EF%BC%89)

# List Interface
`List` interface is implemented by various classes like `ArrayList`, `Vector`, `Stack`.

- [Copy a List to Another List in Java](https://www.baeldung.com/java-copy-list-to-another)  
- [ObjectMapper](https://kucw.github.io/blog/2020/6/java-jackson/)    
- [`List<Object>` to `List<Class>`](https://stackoverflow.com/questions/30463443/convert-listobject-to-listcustomclass)   
- [5 Different Ways To Copy List To Another In Java](https://medium.com/codex/the-different-ways-to-copy-list-to-another-in-java-8db4ead985d8)  
- [Geeksforgeeks](https://www.geeksforgeeks.org/list-interface-java-examples/)


### asList(E[] e) && toArray(E[] e)

```java
Object[] toArray()
<T> T[] toArray(T[] a)

List<String> animals = new ArrayList<>(Arrays.asList("Cat", "Cow", "Dog"));

// toArray(E[] array)
String[] arr = new String[animals.size()];
animals.toArray(arr);

// array to list 
String[] arr = {"Dog", "Cat", "Horse"};
ArrayList<String> animals = new ArrayList<>(Arrays.asList(arr));

// toString
String str = animals.toString();
```

## List Methods

```java
// delete extra unused-space in the Array 
void trimToSize()

// ensureCapacity 
// instead of dynamically allocating the space 
void ensureCapacity(int minCapacity)

// SORT
void sort(Comparator<? super E> c)
arrayList.sort(Comparator c)
arrayList.sort(Comparator.naturalOrder()); 
arrayList.sort(Comparator.reverseOrder());
```

## ArrayList

`ArrayList` provides us with **Dynamic arrays in Java**.  
1. It can not be used for Primitive Types, 
2. To Increase Capacity. **We Need to call `Arrays.copyOf()` (copy older Array to a new on which increases huge cost)**
3. Using method `remove()` (also increase cost)

- **ArrayList中的資料是以連續**的方式儲存在記憶體，可支援隨機存取及循序存取(e.g 取得element via `A[index]`)，**所以循序讀取或排序(sort)時的效能好**。
  > 每個節點不用另外儲存下一個節點的Reference，也因此，每個節點所占用的記憶體較少，但也**因為是線性儲存於記憶體，所以在如果要對之中的元素進行插入或刪除節點等動作時效能較差**，因為我們必須移動大量節點(當刪掉/新增一個元素，其他元素的位置都會被迫改變)。

## Vector

The primary **difference between a `Vector` and an `ArrayList` is that a `Vector` is `synchronized` and an `ArrayList` is non-synchronized**.
## LinkedList

- **LinkedList中的資料以不連續**的方式儲存，因此不需使用連續的記憶體空間，每個節點都會記錄著下個節點的指標，所以**在串列中插入或刪除節點時只需修改相關節點的指標，所以插入或刪除特定元素時的效能較好**。但它的缺點是**因非線性儲存，所以我們在讀取時無法快速索引到該節點(因為不知道位置)，只能以循序存取讀取每一個節點的指標**，所以讀取的效能較差。另外，**因為每個元素還要儲存下一個元素的指標，因此占記憶體容量比較大。**


New Methods : `peek` , `poll` and `offer`

```java
private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;
}

// Adds the specified element as the tail (last element) of this list.
boolean	offer(E e)
boolean	offerFirst(E e)
boolean	offerLast(E e)

// Retrieves, but does not remove, the head (first element) of this list.
// return null if this list is empty
E peek()
E peekFirst()
E peekLast()

// Retrieves and removes the head (first element) of this list.
// return null if this list is empty
E poll()
E pollFirst()
E pollLast()

// Returns the element at the specified position in this list.
E get(int index)
E getFirst()
E getLast()


// Appends the specified element to the end of this list.
boolean	add(E e)
void add(int index, E element)
void addFirst(E e)
void addLast(E e)
``` 

## Array of Array

- [Arr of Arr](https://stackoverflow.com/questions/8559092/how-can-i-create-an-array-of-arraylists)

```java
List< List<T> > arr = new ArrayList< List<T> >(LENGTH);
// or 
List<T>[] arr = new ArrayList[LENGTH];

// for example
List<List<Integer>> group = new ArrayList<List<Integer>>(4);
List<Integer>[] group = new ArrayList[4];
```
