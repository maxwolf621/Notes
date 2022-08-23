# Iterator 

It can be used in the enhanced `For-each loop` (also in Iterator pattern) by implementing iterable interface 

Syntax for Iterator 
```java
import java.util.Iterator;
class iterator_ptr<TYPE> implements Iterator<TYPE> 
{        
    CustomIterator<>(CustomDataStructure obj) { 
        // initialize cursor 
    } 
     
    public boolean hasNext() { 
      // if the element exists after this element
    } 
      
    public T next() { 
      // next element
    } 
      
    /**
      * <p> 
      * Used to remove an element.
      * Implement only if needed.
      * </p>
      */
    public void remove() { 
        // Default throws UnsupportedOperationException. 
    } 
} 
```


## Linked List with iterator

A Node 
```java
class Node<T> { 
    T data; 
    Node<T> next; 
    
    public Node(T data, Node<T> next) 
    { 
        this.data = data; 
        this.next = next; 
    } 
    
    /**
      * @Description
      * setter for node.data
      */
    public void setData(T data) 
    { 
        this.data = data; 
    } 
    
    /**
     * @Description
     * setter for node.next
     */
    public void setNext(Node<T> next) 
    { 
        this.next = next; 
    } 
    
    /**
      * @Description
      * getter for node.data
      */
    public T getData() 
    { 
        return data; 
    } 
    
    /**
      * @Description
      * get next node
      */
    public Node<T> getNext() 
    { 
        return next; 
    } 
}
```

Let list iterable
```java
/**
  * <p> you will have two iterator_ptrs </p>
  * <li> 1. head ptr </li>  
  * <li> 2. tail ptr </li>
  * <p> 
  * this Iterable List has these two ptr 
  * which first pointer to head and latter pointer to tail
  * </p>
  */
class List<T> implements Iterable<T>
{
  Node<T> head, tail;
  
  /**
   * @Description
   * Add a new node next to this.node
   */
  public void add(T data) 
  {   
      // | data | next | 
      Node<T> node = new Node<>(data, null); 
     
      if (head == null) 
          /**
            * <p> if the head is null </p>
            */
          tail = head = node; 
      else {
          /*
           | Linked List | 
                 '-> |data | next | 
                              '--> null
          */
          tail.setNext(node); 
          tail = node; 
      } 
  } 

  /**
    * @return list.head
    */
  public Node<T> getHead() 
  { 
      return head; 
  } 

  /**
    * @return list.tail
    */
  public Node<T> getTail() 
  { 
      return tail; 
  } 

  /** 
    * @Description
    * use {@code Iterator} for this object T
    */
  public Iterator<T> iterator() 
  { 
      /**
        * <p> 
        * Create a iterator points to head of this.list
        * </p>
        * @param this : {@code List<T>} this.list
        */
      return new ListIterator<T>(this);
  } 
}
```

A class Iterator for An iterable List
```java
/**
  * <p> 
  * List-Iterator for 
  *  An Iterable List of Node
  * With {@code current_ptr} points to nodes in iterable list
  * </p>
  */
class ListIterator<T> implements Iterator<T> 
{ 
    Node<T> current_ptr; 
      
    /**
      * <p> 
      * initialize the pointer to head of the list 
      * </p>
      */
    public ListIterator(List<T> list) 
    { 
        current_ptr = list.getHead(); 
    } 
      
    /**
      * @return  {@code false} 
      * if next element does not exist 
      */
    public boolean hasNext() 
    { 
        return current_ptr != null; 
    } 
      
    /**
      * @return current data 
      * <p> and move current_ptr to next node </p>
      */
    public T next() 
    { 
        T data = current_ptr.getData(); 
        // pointer to next
        current = current_ptr.getNext(); 
        return data; 
    } 
      
    // if needed 
    public void remove() 
    { 
        throw new UnsupportedOperationException(); 
    } 
} 
```

```java
class Main { 
    public static void main(String[] args) 
    { 
        // Create Linked List 
        List<String> myList = new List<>(); 
          
        myList.add("abc"); 
        myList.add("mno"); 
        myList.add("pqr"); 
        myList.add("xyz"); 
          
        // Iterate through the list using For Each Loop 
        for (String string : myList) 
            System.out.println(string); 
    } 
} 

```
