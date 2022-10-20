# Java List of lists

- [Array Based 2D array](https://stackoverflow.com/questions/26726366/convert-nested-list-to-2d-array)   
- [Java List of lists](https://www.baeldung.com/java-list-of-lists)
- [How to create an 2D ArrayList in java? [duplicate]](https://stackoverflow.com/questions/16956720/how-to-create-an-2d-arraylist-in-java)
- [2D Array List in Java](https://iq.opengenus.org/2d-array-list-java/)
```java
Array-based: List<T>[] = new ArrayList[size]
List-based: List<List<T>> = new ArrayList<ArrayList<T>>(size)
```
- List is slower than Array on `get/set` operations. But some List implementations, such as ArrayList, are internally based on arrays. 

```java 
ArrayList<ArrayList<String>> listOLists = new ArrayList<ArrayList<String>>();

ArrayList<String> singleList = new ArrayList<String>();
singleList.add("hello");
singleList.add("world");

listOLists.add(singleList);

ArrayList<String> anotherList = new ArrayList<String>();
anotherList.add("this is another list");
listOLists.add(anotherList);
```