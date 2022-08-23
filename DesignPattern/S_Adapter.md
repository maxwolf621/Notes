###### tags: `Design Pattern`

# Adapter-Muster
[Another Example](https://github.com/iluwatar/java-design-patterns/tree/master/adapter)  

> Konzept  
![](https://i.imgur.com/U8q6gUs.png)  
![image](https://user-images.githubusercontent.com/68631186/126260564-e78ee25e-725c-4cca-b818-c02387c06122.png)  

```java
/**
  * Geflügel/poultry interface
  */
public interface Duck{
    public void quack();
    public void fly();
}
public interface Turkey{
   // Turkey cant quack
   public void gobble();
   public void fly();
}

public class MallardDuck implements Duck {
	public void quack() {
		System.out.println("Quack");
	}
 
	public void fly() {
		System.out.println("I'm flying");
	}
}

public class WildTurkey implements Turkey {
	public void gobble() {
		System.out.println("Gobble gobble");
	}
 
	public void fly() {
		System.out.println("I'm flying a short distance");
	}
}

/**
  * Adapter (Turkey adapts to Duck)
  *     Duck can have Turkey Behaviour
  *     e.g. Turkey can quack 
  */
public class TurkeyAdapter implements Duck {
	Turkey turkey;
 
	public TurkeyAdapter(Turkey turkey) {
		this.turkey = turkey;
	}
    
    /**
      * now turkey can quack
      */
    public void quack() {
        turkey.gobble();
    }

    /**
      * turkey can fly longer like duck
      */
    public void fly() {
        for(int i=0; i < 5; i++) {
            turkey.fly();
        }
    }
}

/**
  * Test The Adapter
  */
public class DuckTestDrive {
	public static void main(String[] args) {
		Duck duck = new MallardDuck();
		Turkey turkey = new WildTurkey();
		
        /**
          * turkey -> adapter -> duck
          */
        Duck turkeyAdapter = new TurkeyAdapter(turkey);

		testDuck(duck);
		testDuck(turkeyAdapter);
	}

	static void testDuck(Duck duck) {
		duck.quack();
		duck.fly();
	}
}
```
![image](https://user-images.githubusercontent.com/68631186/126261010-34f7bfb3-2b8f-4b30-8237-e5429556e40d.png)


Es gibt zwei Arten von Adaptern
1. Objekt-Adapter
2. Klassen-Adapter

Objekt- und Klassen-Adapter verwenden zwei verschiedene Mittel, um etwas zu adaptieren(Komposition bzw. Verbung).  

## Objekt-Adapter

> Muster  
![](https://i.imgur.com/bMEvDzn.png)  
## Klassen-Adapter

**Java kann den nicht implementen**  
> Muster  
![](https://i.imgur.com/tf0toQx.png)

### Objekt- oder Klassen nutzet ?
(Das Beispiel steht auf die Seite 246)  

![](https://i.imgur.com/DkDzLFd.png)  

#### Mit dem Klassen-Adapter
![](https://i.imgur.com/N114x9r.png)  
- Das Adapierte hat nicht die gliechen Methoden wie Ente aber **der Adapter kann Methodenaufrufe für Ente annehmen unwandeln und an ihrer Stelle Methoden auf Truthahn aufrufen**

#### Mit dem Object-Adapter
![](https://i.imgur.com/zoZCVaQ.png)
- Dank des Adapters erhält Truthahn die Aufrufe, die der Client auf der Scnittstelle Ent macht

## Verwendung eines einfachen Adapters

[Source Code](https://github.com/bethrobson/Head-First-Design-Patterns/tree/master/src/headfirst/designpatterns/adapter/iterenum)

Unser Ziel ist dass Enumeration nach Iteratoren adaptiert  

Enumerations  
![](https://i.imgur.com/2yLn0FK.png)  
Iteratoren  
![](https://i.imgur.com/hCoeErm.png)  

>![](https://i.imgur.com/FBaKQR7.png)  
> Wir müssen herauszufiden, welche Methode wir auf der adaptierten Klasse aufrufen müssen, wenn der Client eine Methode auf dem Ziel aufragt  

> UML  
> ![](https://i.imgur.com/Lu5XEbi.png)  

Wir muss Enumeration **an** Iterator anpassen in unserem Adapter, so implementiert unser Adapter das Interface Iterator
```java
public class EnumerationIterator implements Iterator<Object> {
    
    /**
      * Wir verwenden Komposition<?>, 
      *     speichern unser Referenz 
      *     also in einer Instanzvariablen 
      */
    Enumeration<?> enumeration;
    
 
    public EnumerationIterator(Enumeration<?> enumeration){
        this.enumeration = enumeration;
    }

    /**
      * {@code hastNext()} von Iterator wird
      *    an die {@code hasMoreElements()} 
      *    von Enumeration delegiert
      */
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }
    
    /**
      * {@code next()} von Iterator adaptiere 
      *   {@code nextElement()] von Enumeration
      */
    public Object next() {
        return enumeration.nextElement();
    }
    
    /**
      * Wir können die {@code remove()} von Iterator nicht unterstützen 
      *     und müssen deswegen meckern, indem wir eine Exception auslösen
      * @throws UnsupportedOperationException()
      */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
```



## Review von Musters

![](https://i.imgur.com/FI9LvjG.png)

