###### tags: `Design Pattern`
# Memento  
[Example From tutorialspoint](https://www.tutorialspoint.com/design_pattern/memento_pattern.htm)  
[Example From Guro](https://refactoring.guru/design-patterns/memento)  
[Example From Iluwater](https://github.com/iluwatar/java-design-patterns/tree/master/memento/src/main/java/com/iluwatar/memento)  

Memento is a behavioral design pattern that lets you **save and restore the previous state of an object** without revealing the details of its implementation.

![](https://i.imgur.com/RnlS0sH.png)  


Originator (Role)  
- Creates memento objects capturing the originator's internal states(restore its previous states.)  
Caretaker (take care of state from Role)
- Responsible for keeping records of the memento(s) for Originator  
- **The memento is opaque(opak) to the caretaker, and the caretaker must not operate on it.**  

> ### Pattern UML    
> ![](https://i.imgur.com/f9V8vYW.png)    
> - class `CareTaker` has array `Memento[]`    
> ![](https://i.imgur.com/pFQSIql.png)    

## Example  
Record/Get the sate of A character/Role's HP and EXP using via Memento pattern  
[soruceCode](http://corrupt003-design-pattern.blogspot.com/2017/02/memento-pattern.html)    

![](https://i.imgur.com/rm5qCcZ.png)  


```java
/**
 * <p> Originator </p>
 * Via CateTaker Originator's state
 * will be stored
 */
public class GamePlayer {
   
    /**
     * <p> States </p>
     */
    private int mHp;
    private int mExp;

    public GamePlayer(int hp, int exp)
    {
        mHp = hp;
        mExp = exp;
    }

    /**
     * @Description
     *   Save the current State
     */
    public GameMemento saveToMemento()
    {
        return new GameMemento(mHp, mExp);
    }

    /**
     * @Description
     *   restore the previous state
     * @param memento 
     */
    public void restoreFromMemento(GameMemento memento)
    {
        mHp = memento.getGameHp();
        mExp = memento.getGameExp();
    }

    /**
     * @Description
     *    Record role's damage hitted by Eenemy 
     */
    public void play(int hp, int exp)
    {
        mHp = mHp - hp;
        mExp = mExp + exp;
    }
}

/**
 * Memento
 */
public class GameMemento {
    private int mGameHp;
    private int mGameExp;

    public GameMemento(int hp, int exp)
    {
        mGameHp = hp;
        mGameExp = exp;
    }
    
    
    public int getGameHp()
    {
        return mGameHp;
    }

    public int getGameExp()
    {
        return mGameExp;
    }
}

/**
 *  <p> Caretaker </p>  
 *  it stores Our State and operates Memento
 */
public class GameCaretaker {

    /** 
     * <p> To store multiple States </p>
     * {@code stack<GameMemento>}
     * {@code List<GameMemento>} 
     */
    private List<GameMemento> mementoList = New ArrayList<GameMemento>();
    private GameMemento gameMemento;

    /**
     * @Description
     *   Get Memento
     */
    public GameMemento getMemento()
    {
        return gameMemento;
    }
    
    /**
     * @Description
     *   Get Memento from list
     */
    public GameMemento getMementos(int index){
        return mementoList.get(index);
    }
    
    /**
     * @Description
     *   Save only one state
     */
    public void setMemento(GameMemento memento)
    {
        mMemento = memento;
    }
    
    /**
     * @Description
     *   Add the sate to list
     */
    public void addMemento(GameMemento memento)
    {
        mMementos.add(memento);
    }
}

/**
 * <p> Test/Client </p>
 */
public class Demo {
    public static void main(String[] args)
    {
        /**
         * Originator
         */
        GamePlayer player = new GamePlayer(100, 0);

        /**
         * careTaker stores states
         */
        GameCaretaker caretaker = new GameCaretaker();
        
        /**
         * @param player.saveTomemento 
         *        returns new GameMemnto(hp, exp)
         */
        caretaker.setMemento(player.seveToMemento());

        /**
         *  Wasted
         */
        player.play(-100, 10);
        

        /**
         * <p> Reloading the game </p>
         *  Restore <pre> player </pre> 
         *    the state that store 
         *    in <pre> careTaker </pre>
         */
        player.restoreFromMemento(caretaker.getMemento());
        
        
        player.play(-10, 30);
        
        /**
         * player saves current state in the mementoList
         */
        careTaker.addMemento(caretaker.saveToMemento);
        
        
    }
}
```

