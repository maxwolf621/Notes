###### tags : `Design Pattern`
# Abstract Factory
[Example from cyc](https://github.com/CyC2018/CS-Notes/blob/master/notes/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%20-%20%E6%8A%BD%E8%B1%A1%E5%B7%A5%E5%8E%82.md)  
[SourceCode for HeaderFirstDesignPattern](https://fjp.at/design-patterns/factory#abstract-factory)  
[Example from iluwater](https://github.com/iluwatar/java-design-patterns/tree/master/abstract-factory)

Abstract Factory pattern lets us produce families of related objects without specifying their concrete classes.   

> Pattern  UML  
>![](https://i.imgur.com/0DMqDUd.png)    
>![](https://i.imgur.com/gWUWtKV.png)    

|Prodcut   | concrete product  | 
|----------| ------------------| 
|Pizza     |HamAndMushRoomPizza|
|          | DeluxePizza       |
|          | HawaiianPizza     | 

```cpp
/**
  * Interface Product
  */
class Pizza {
public:
	virtual int getPrice() const = 0;
	virtual ~Pizza() {};  //without this, no destructor for derived Pizza's will be called.
};

/**
  * Concrete Product
  */
class HamAndMushroomPizza : public Pizza {
public:
	virtual int getPrice() const { return 850; };
	virtual ~HamAndMushroomPizza() {};
};

class DeluxePizza : public Pizza {
public:
	virtual int getPrice() const { return 1050; };
	virtual ~DeluxePizza() {};
};

class HawaiianPizza : public Pizza {
public:
	virtual int getPrice() const { return 1150; };
	virtual ~HawaiianPizza() {};
};


/**
  * Factory contains 
  * a method to create different type of pizza (using enum)
  */ 
class PizzaFactory {
public:
	enum PizzaType {
		HamMushroom,
		Deluxe,
		Hawaiian
	};
	static unique_ptr<Pizza> createPizza(PizzaType pizzaType) {
		switch (pizzaType) {
			case HamMushroom: return make_unique<HamAndMushroomPizza>();
			case Deluxe:      return make_unique<DeluxePizza>();
			case Hawaiian:    return make_unique<HawaiianPizza>();
		}
		throw "invalid pizza type.";
	}
};

/**
  * client
  */
int main()
{
  /*Create all available pizzas and print their prices*/
  void pizza_information(PizzaFactory::PizzaType pizzatype)
  {
    unique_ptr<Pizza> pizza = PizzaFactory::createPizza(pizzatype);
    cout << "Price of " << pizzatype << " is " << pizza->getPrice() << std::endl;
  }
	pizza_information(PizzaFactory::HamMushroom);
	pizza_information(PizzaFactory::Deluxe);
	pizza_information(PizzaFactory::Hawaiian);
}
```
