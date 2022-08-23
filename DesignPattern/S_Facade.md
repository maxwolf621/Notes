###### Tags : `Design Pattern`
# Fassade-Muster

[another example(more elegant)](https://github.com/iluwatar/java-design-patterns/tree/master/facade)  

Mit dem Fascade-Muster können wir **ein komplexes Bassisytem nehmen und seine Verwendung vereinfach**  
- **The Facade Pattern provides a unified interface to a set of interfaces in a subsystem.**
- Facade defines a higher-level interface that makes the subsystem easier to use.  
- **Facade pattern provides a simplified interface to a complex subsystem.**  

![](https://i.imgur.com/mvSPFnt.png)  


## z.B Einen Film anschauen, HomeTheaterFacade

Um einen Film zu schauen, müssen wir ein paar einfach Aufgaben erliegen (komplexe Angelegenheit)  
![](https://i.imgur.com/FXj8hPv.png)  
- **Es wäre ein Chaos ohne dem hilf des Fassade-musters**  


![](https://i.imgur.com/F6c6CVx.png)
- verwaltet alle komponenten des Basissystems für den Client.    
- sie hält den Client einfach und flexibel   

```java
public class HomeTheaterFacade {
    Amplifier amp;
    Tuner tuner;
    DvdPlayer dvd;
    CdPlayer cd;
    Projector projector;
    TheaterLights lights;
    Screen screen;
    PopcornPopper popper;

    public HomeTheaterFacade(Amplifier amp, 
                 Tuner tuner, 
                 DvdPlayer dvd, 
                 CdPlayer cd, 
                 Projector projector, 
                 Screen screen,
                 TheaterLights lights,
                 PopcornPopper popper) {

        this.amp = amp;
        this.tuner = tuner;
        this.dvd = dvd;
        this.cd = cd;
        this.projector = projector;
        this.screen = screen;
        this.lights = lights;
        this.popper = popper;
    }

    /**
      * <p> The Facade Pattern provides 
      *     a unified interface to
      *     a set of interfaces in a subsystem. </p>
      */
    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        /**
         * <p> set of interfaces </p>
         */  
        popper.on();
        popper.pop();
        lights.dim(10);
        screen.down();
        projector.on();
        projector.wideScreenMode();
        amp.on();
        amp.setDvd(dvd);
        amp.setSurroundSound();
        amp.setVolume(5);
        dvd.on();
        dvd.play(movie);
    }


    public void endMovie() {
        System.out.println("Shutting movie theater down...");
        /**
         * <p> set of interfaces </p>
         */ 
        popper.off();
        lights.on();
        screen.up();
        projector.off();
        amp.off();
        dvd.stop();
        dvd.eject();
        dvd.off();
    }

    public void listenToCd(String cdTitle) {
        System.out.println("Get ready for an audiopile experence...");
        /**
         * <p> set of interfaces </p>
         */ 
        lights.on();
        amp.on();
        amp.setVolume(5);
        amp.setCd(cd);
        amp.setStereoSound();
        cd.on();
        cd.play(cdTitle);
    }

    public void endCd() {
        System.out.println("Shutting down CD...");
        /**
         * <p> set of interfaces </p>
         */ 
        amp.off();
        amp.setCd(cd);
        cd.eject();
        cd.off();
    }

    public void listenToRadio(double frequency) {
        System.out.println("Tuning in the airwaves...");
        /**
         * <p> set of interfaces </p>
         */ 
        tuner.on();
        tuner.setFrequency(frequency);
        amp.on();
        amp.setVolume(5);
        amp.setTuner(tuner);
    }

    public void endRadio() {
        System.out.println("Shutting down the tuner...");
        /**
         * <p> set of interfaces </p>
         */ 
        tuner.off();
        amp.off();
    }
} 

/**
  * <p> Client </p>
  */
public class HomeTheaterTestDrive {
	public static void main(String[] args) {
            Amplifier amp = new Amplifier("Amplifier");
            Tuner tuner = new Tuner("AM/FM Tuner", amp);
            StreamingPlayer player = new StreamingPlayer("Streaming Player", amp);
            CdPlayer cd = new CdPlayer("CD Player", amp);
            Projector projector = new Projector("Projector", player);
            TheaterLights lights = new TheaterLights("Theater Ceiling Lights");
            Screen screen = new Screen("Theater Screen");
            PopcornPopper popper = new PopcornPopper("Popcorn Popper");

            HomeTheaterFacade homeTheater = 
                    new HomeTheaterFacade(amp, tuner, player, 
                            projector, screen, lights, popper);
            /**
              * <p> Das Prinzip der Fascade-Muster </p>
              * <p> ein komplexes Bassisytem nehmen und seine Verwendung vereinfach </p>
              * <p> {@code homeTheater.watchMovie} und {@code homeTheater.endMovie} 
              *     vereinfacht ein komplexes Bassistem </p>
              */
            homeTheater.watchMovie("Raiders of the Lost Ark");
            homeTheater.endMovie();
    }
}
```



Wir können mehrere Klassen einpacken, aber der Zweck einer Fassade ist es, die Schnittstelle zu **vereinfachen**, der Zweck eines Adapters hingegen, die Schnittstelle in eine andere **umzuwandeln**  
> Fascade is similar to the Adapter Pattern and the Decorator Pattern. An adapter wraps an object to change its interface, a decorator wraps an object to add new behaviors and responsibilities, and a facade “wraps” a set of objects to unify and simplify.
##### Key `Fassade : vereinfachen` , `Adapter : umzuwandeln`  

## [Das Prinzip der Verschwiegenheit](https://github.com/maxwolf621/DesignPattern/blob/main/Principle.md#%E6%9C%80%E5%B0%91%E7%9F%A5%E8%AD%98%E5%8E%9F%E5%89%87least-knowledge-principle%E8%BF%AA%E7%B1%B3%E7%89%B9%E6%B3%95%E5%89%87law-of-demeter)

![](https://i.imgur.com/TUoAoZ5.png)

Dieses Prinzip hält uns davon ab, Entwürfe zu erstellen, in denen eine große Anzahl von Klassen so aneinander gekoppelt sind, dass sich Änderungen in einem Teil des System auf andere Teile auswirken.

**Wenn du viele Abhängigkeiten zwischen deinen Klassen aufbauen, baust du ein zerbrechenliches System auf, dessen Wartung teuer ist und das für andere sehr schwer zu verstehen ist**
```java
public float getTemperatur()
{
    Thermometer thermometer = station.getThermomter();
    /*ruft selbst die Methode */
    retrun thermometer.getTemperatur();
}

/**
  * <p> wir fügen der Klasse Station eine Methode hinzu, 
  *     die für uns die Anfrage an das Termometer richtet </p>
  * <p> Das Verringert die Anzahl der Klassen, von denen wir abhängig sind </p>
  */
public float getTempratur()
{
    return station.getTemperatur();
}
```


das Prinzip sagt uns dann,dass wir von jeder Methode in diesem Objekt nur Methoden aufrufen sollen, die
1. zum Objekt selbst
2. zu Objekten, die der Methode als Parameter übergebben
3. zu Objekten, die die Methode erstellt (Keine Methoden auf Objekten aufzurufen, die von Aufrufen anderer Methoden zurückgeliefert wurden)
4. zu Komponenten des Objekts  (HAT-EINE-Beziehung)
```java
public class Auto{
    Motor motor;
    
    // andere Instanzvariablen ...
    
    public Auto(){/** objekt `motor` initialisieren **/}
    
    public void start(Key key){
        
        /**
          * <p> #3 </p>
          * <p> ein neues Objekt erstellen </p>
          * <strong> HARD CODE </strong>
          */
        CarDoor carDoor = new CarDoor();
        

        /**
          * <p> #2 </p>
          * @Description
          *    die Methoden auf einem Objekt aufrufen <p>
          *    {@code key.drehen} als Parameter übergeben wurde
          */
        boolean autorisiert = key.drehen();
        if(autorisiert){
            
            /**
              * <p> #4 </p>
              * <p> Eine Methode 
              *     auf einer Komponente 
              *     des Objekts aufrufen </p>
              */
            motor.starten();
            
            /**
              * <p> Eine Lokale Methode 
              *     des Objekts aufrufen </p>
              */
            cockpitAnzeigeAkutualisieren();
            
            /**
              * <p> eine Methode 
              *     auf eiem Objekt anfrufen,
              *     das du erstellt 
              *     oder instaiiert hast </p>
              */
            carDoor.schließen();
        }
    }
    pubic void cockpitAnzeigeAkutualisieren(){
        //....
    }
} 
```


