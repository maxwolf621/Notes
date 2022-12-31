# `equals(Object obj)` and `hashCode()`

[Code Examples](https://www.jitendrazaa.com/blog/java/what-is-the-need-to-override-hashcode-and-equals-method/)      
**[Effective Java Item11 - 覆蓋equals時總要覆蓋hashCode](https://www.jyt0532.com/2018/06/23/always-override-hashcode-when-you-override-equals/)**
[Java equals() and hashCode() Contracts](https://www.baeldung.com/java-equals-hashcode-contracts)  


- [`equals(Object obj)` and `hashCode()`](#equalsobject-obj-and-hashcode)
  - [equals(Object obj)](#equalsobject-obj)
    - [String#equals(Object o)](#stringequalsobject-o)
  - [`hashCode()`](#hashcode)
  - [A class with without/with overriding `hashCode()`](#a-class-with-withoutwith-overriding-hashcode)

## equals(Object obj)

Violating equals() Symmetry With Inheritance

```java
public boolean equals(Object o){
    // address
    if(this == o) return true;
    // type 
    if(! o instanceof this) return false;

    // convert Object to (this) Object
    thisObject other = (thisObject) o
    // object checking 
    boolean valueEquals = (this.value == null && other.value == null)
          || (this.value != null && this.value.equals(other.value));
    // primitive type checking 
    boolean b = this.b == other.b;
    return a && b;
}
```


### String#equals(Object o)

```java
public boolean equals(Object anObject) {  
    
    if (this == anObject) { 
    // reference to same address ?  
        return true;  
    }  
    
    if (anObject instanceof String) { 
        
        String anotherString = (String) anObject; 
        
        // this object's count
        int n = count;  
        if (n == anotherString.count) {  
            
            char v1[] = value;  
            char v2[] = anotherString.value;
            
            // start at the index 0
            int i = offset;  
            int j = anotherString.offset;  
            while (n-- != 0) {  
                if (v1[i++] != v2[j++])  
                    return false;  
            }  
            return true;  
        }  
    }  
    return false;  
```

## `hashCode()`
It is not necessary that two different object(an entry) must have different hashcode values, it might be possible that they share common index(bucket) 

**Hash Code is used to narrow the search result**

Same object has same hash code but same hash code has not 100% of the same object
```java
// FOR EXAMPLE 
// entry_1_1 and -> entry_1_2 has the same bucket(index1)
public int hashCode()

     |-------|
     |index_1| ---> entry_1_1 -> entry_1_2 -> 
     |index_2| 
     |index_3| ---> entry_3_1 
     |index_4| 
     |index_5| ---> entry_5_1 -> entry_5_2 -> entry_5_3
     |-------|
```
 


**If we don't use hash map to compare our object's content, we might have time complexity `O(n)` to compare each elements**
- List is sequence elements  
- Set is not **sequence**   


![](https://i.imgur.com/MDJt9WL.png)  
- Every object is placed in Hash bucket depending on the hashcode they have

When a key is inserted in HashMap
- First it checks if any other objects present with same hashcode (same bucket)  
- If `true` then it checks for the `equal()` method (compare content)  
- **if two objects are same then HashMap will not add that key instead it will replace the old value by new one(update)**

## A class with without/with overriding `hashCode()`

```java
/**
  * A project without overriding 
  * {@code equal} and {@code hashCode} 
  */
class movie{

    private String name, actor;
    private int releaseYr
    public movie(name,actor,releaseYr){
        this.name = name;
        this.actor = actor;
        this.releaseYr = releaseYr;
    }
    
    // getters and setters
}

public class demo{
    public static void main(String[] args){
        HashMap<movie, String> movieMap ;
        movie m0 = new movie("A","james",2001);
        movie m1= new movie("B","Tom"  ,1995);
        /**
          * without the {@code hashCode} even 
          * there is the same one in the buckets.
          * It wont update 
          */
        movie m2= new movie("A","james",2001);
       
        movieMap.put(m0,"James");
        movieMap.put(m1,"Tom");
        movieMap.put(m2,"Duplicate James");
        
        /**
         * Print out the following : 
         *   James
         *   Tom
         *   Duplicate James
         */
        for(movie m : movieMap.keySet()){
            System,out.println(movieMap.get(m).toString);
        }
        
        /**
          * Create new movie m3
          * which is exactly same content as m
          */
        movie m3= new movie("A","james",2001);
        if(movieMap.get(m3) == null){
          /**
            * Without Overriding {@code hashcode()}
            * we are not able to get object m
            */
          system.out.println("move" + m3.toString() + "not found");
        }else{
            //..
        }
    }
}

/**  
  * A project that overrides 
  * {@code equal} and {@code hashcode} method 
  */
class movie {
    // attributes , constructors

    @Override
    public boolean equals(Object o) {
    // Compare the movie o and this movie 
        Movie m = (movie) o;
        return m.actor.equals(this.actor) &&
               m.name.equals(this.name) && 
               m.releaseYr == this.releaseYr;
    }
    
    @Override
    public int hashCode() {
        return actor.hashCode() + 
               name.hashCode() + 
               releaseYr;
    }

    // setters and getters 
}

public class demo{   
    private satic void main(String[] args){
        HashMap<movie, String> movieMap ;
        movie m0= new movie("A","james",2001);
        movie m1= new movie("B","Tom"  ,1995);
        movie m2= new movie("A","james",2001);
        
        movieMap.put(m0,"James");
        movieMap.put(m1,"Tom");
        /**
          * via {@code put} 
          * key-value pair (m1,james) will be 
          * update from James to Duplicate James
          */
        movieMap.put(m2,"Duplicate James");
        
       /**
         * Print out the following
         *  Tom 
         *  Duplicate James
         */
        for(movie mm : movieMap.keySet()){
            System,out.println(movieMap.get(mm).toString);
        } 
        
        /**
          * with {@code equals()} and {@code hashcode()}
          * java knows movie m3 has same contnet as 
          * movie m2 in HashMap movieMap
          */
        movie m3= new movie("A","james",2001);       
        if(movieMap.get(m3) == null){
            // print out object not found ...
        }else{
            //@return {@code String} Duplicate Jame 
            System.out.println(movieMap.get(m3).toString());
        }
    }
}
```