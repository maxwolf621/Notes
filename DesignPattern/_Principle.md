# Principal

- [Principal](#principal)
  - [單一職責原則(Single Responsibility Principle)](#單一職責原則single-responsibility-principle)
          - [tags `術業有專攻`，`降低Class的設計複雜度`，`提高readability以及maintainability`，`只要是Modular design，都能依據SRP的設計原則 (e.g. Classes, Methods, ... etc)`](#tags-術業有專攻降低class的設計複雜度提高readability以及maintainability只要是modular-design都能依據srp的設計原則-eg-classes-methods--etc)
  - [開閉原則(Open Closed Principle)](#開閉原則open-closed-principle)
          - [Tags : `對於擴充(新的feature)是開放的，但對於修改(原有)是封閉的`](#tags--對於擴充新的feature是開放的但對於修改原有是封閉的)
  - [依賴反轉原則(**Dependency** Inversion Principle)](#依賴反轉原則dependency-inversion-principle)
          - [tags : `High-Level Module and low-Level Module should depend on abstractions`](#tags--high-level-module-and-low-level-module-should-depend-on-abstractions)
  - [里氏替換原則(Liskov Substitution Principle)](#里氏替換原則liskov-substitution-principle)
          - [tags : `企鵝老鷹定律`，`子類盡量不要複寫父類非 Abstraction Methods`](#tags--企鵝老鷹定律子類盡量不要複寫父類非-abstraction-methods)
    - [KEY](#key)
  - [介面隔離原則(Interface Segregation`[/ˌseɡ.rɪˈɡeɪ.ʃən/]` Principles)](#介面隔離原則interface-segregationˌseɡrɪˈɡeɪʃən-principles)
          - [tags : `Clients should not be forced to depend on methods that they do not use`](#tags--clients-should-not-be-forced-to-depend-on-methods-that-they-do-not-use)
  - [合成/聚合複用原則(Composite/Aggregate Reuse Principle)](#合成聚合複用原則compositeaggregate-reuse-principle)
          - [tags : `apply use-relationship first, has-relationship after`](#tags--apply-use-relationship-first-has-relationship-after)
  - [最少知識原則(Least Knowledge Principle)/迪米特法則(Law of Demeter)](#最少知識原則least-knowledge-principle迪米特法則law-of-demeter)
          - [tags : `只依賴應該依賴的對象，只開放應該開放的方法`，`降低類與類間的耦合(盡量減少不必要的Dependency)`](#tags--只依賴應該依賴的對象只開放應該開放的方法降低類與類間的耦合盡量減少不必要的dependency)
## 單一職責原則(Single Responsibility Principle)  

[SourceCode](https://github.com/maxwolf621/DesignPattern/new/main#%E5%84%AA%E9%BB%9E)  

**一個類別專注做一件事，不應該將不相關的功能放在同一個程式碼內，使整體程式碼中的每個部分都與自己實作的功能相關**

###### tags `術業有專攻`，`降低Class的設計複雜度`，`提高readability以及maintainability`，`只要是Modular design，都能依據SRP的設計原則 (e.g. Classes, Methods, ... etc)`   

例如購物車內有確認訂購內容、確認訂購人資料以及付款。   
很明顯付款跟與其他兩者的功能不同，所以不應該放在同一組程式碼內。  
![image](https://user-images.githubusercontent.com/68631186/126635035-1975564b-c10b-4fb5-b981-1c64ee6eebf6.png) 

![image](https://user-images.githubusercontent.com/68631186/126635090-ff5a6e26-4b91-4ac3-ba75-de93b080ab74.png)


## 開閉原則(Open Closed Principle)  
[SourceCode](https://ithelp.ithome.com.tw/articles/10235421)  
###### Tags : `對於擴充(新的feature)是開放的，但對於修改(原有)是封閉的`

**系統需要被擴充時，應該藉由新增新的程式碼來擴充新功能，而非修改原本的程式碼來擴充新功能**  

For Example : 
銀行付款系統，從以前金融卡、信用卡到現在電子支付的付款方式。  
每當增加新的付款方式，應該要新增的類別來實作，而不是在原本的程式碼內做新增。  
![image](https://user-images.githubusercontent.com/68631186/126636795-c8999756-f2a9-4c72-be88-784b8b37271f.png)  

```java
/**
 * abstract class payment : 對外是擴充的
 */
abstract class Payment {
    private String msg;
    
    public void pay(){
        this.msg = setPaymentType();
        System.out.println(msg);
    }
    
    public abstract String setPaymentType();
}

/**
 * 擴充Feature -> new class 
 */

class CreditCard extends Payment {
    @Override
    public String setPaymentType() {
        return "選擇信用卡付錢！！";
    };
}

class DebtCard extends Payment{
    @Override
    public String setPaymentType(){
      //...
    }
}

class EasyCard extends Payment{
    @Override
    public String setPaymentType(){
      //...
    }
}

public class Bank {
    public static void main(String args[]) {
        CreditCard creditCard = new CreditCard();
        creditCard.pay();
    }
}
```
  
  
## 依賴反轉原則(**Dependency** Inversion Principle)

[Reference](https://ithelp.ithome.com.tw/articles/10236359)  
[Another Example](https://www.jyt0532.com/2020/03/24/dip/)  

###### tags : `High-Level Module and low-Level Module should depend on abstractions`

High-level modules should not depend on *low-level* modules. 
Both should depend on abstractions (e.g., interfaces).

Abstractions should not depend on details.  
*Details (concrete implementations)* should depend on abstractions.  

```java
        Bank               (HIGH)
         '
         '
         ⇣
   +-----------+
   | PayMethod |
   +-----------+
     ^       ^
     '       ' 
     '       '
     '       '    
  VisaCard  Epay            (LOW)
```

For example : 
用E-pay或visa card付款，程式內不應該將visa card付款Hard Coding在Bank的方法內，而是**應該建立一個抽象類(PayMethod)提供visa card以及E-pay實作**，再將此抽象類放入 Bank 邏輯內。

**往後增加新的付款方式則不再需要更動Bank的內部邏輯，只需要增加抽象類 PayMethod 的實作。**

```java
public class Bank{
  /**
    * 
    * 遵守 Dependency Inversion Principal
    * 遵守 Open Closed Principal         
    * 
    * 無須更改Bank類別內原有的程式,
    * 使用Abstraction PayMethod作為Object Type
    * 未來只需要新增PayMethod的Implementation界能更改Bank的付款方式 </p>
    */
  private PayMethod payMethod;
}

/**
  * 基於 Open Closed Principle 
  * <strong>擴充是開放原則</strong>
  * Interface PayMethod來實做
  * <pre> visaCard </pre> 或
  * <pre> E-pay </pre>支付
  */
public interface PayMethod{
  //abstract methods
}

/**
  * 以下為ConcretePaySystem 
  */
public class VisaCard implements PayMethod{
  // implements the method from interface PaySystem
}

/** 
* <p> 擴充新的功能: 電子支付
*     只須implements interface 
*     並應用在Class Bank內 </p>
*/
public class Epay implements PayMethod{
  //...
}
``` 

- 利用 Abstractions 作為類別的設計原則相較於 Details(Concrete Implementation) 的多變性來說相對穩定
  Abstractions不涉及**任何具體(concrete)方法(Method/Function)的細節(Details)操作**，而是交給(Concrete Implementation)實做來實現這些abstract methods
- Low-level modules需要有abstractions(例如visa-card類別以及E-pay類別需要一個Interface PaySystem類別)
- 如果是使用繼承則得遵守Liskov Substitution Principle 

## 里氏替換原則(Liskov Substitution Principle)
[SourceCode](https://ithelp.ithome.com.tw/articles/10235629)  

###### tags : `企鵝老鷹定律`，`子類盡量不要複寫父類非 Abstraction Methods`

**子類別可以擴充套件父類別的功能,但不改變父類別原有的功能**  
當我們使用Inheritance時會造成侵入性的問題，減少Program的可移植性，增加類別與類別間的耦合性 

For example : 
Class A做為Class B的derived Class時，當修改Class A時，必须考慮到所有繼承它的子類，否則可能會造成子類中的方法無法使用，如老鷹跟企鵝都是鳥，但老鷹會飛，企鵝不會。  
應該再分成會飛的鳥及不會飛的鳥來替代覆寫。

![image](https://user-images.githubusercontent.com/68631186/126634446-f163e543-eafd-465d-8d66-4702110a9f98.png)   
![image](https://user-images.githubusercontent.com/68631186/126634086-f7520690-c703-4962-a9da-95eb0daedd12.png)   
 

### KEY
- 依據Liskov Substitution Principle，在使用繼承時，**子類盡量不要Override非父Abstraction Methods**
- 繼承會造成耦合性增加，需要藉由Dependency(Aggregation, Composition)減少耦合
- **重載(Overload)或者實現父類別的方法的parameter要比原本方法parameter還要大(原本的parameter是`HashMap`，子類時的parameter就得比它大或相等(e.g `Map`)**
- 覆蓋或者實現父類別的方法時輸出結果可以被縮小  

## 介面隔離原則(Interface Segregation`[/ˌseɡ.rɪˈɡeɪ.ʃən/]` Principles)
[SourceCode](https://ithelp.ithome.com.tw/articles/10236094)  
**不應該強迫某類別依賴不該使用的其他類別，介面以及方法**    

###### tags : `Clients should not be forced to depend on methods that they do not use`

例如 : 強迫Junior做簡報，但實際上只需要Senior做   
![image](https://user-images.githubusercontent.com/68631186/126616775-1f22aa5e-0041-4ccb-ae9f-cddfa40bd16a.png)  
![image](https://user-images.githubusercontent.com/68631186/126616821-fe3601f5-b55d-4816-8262-39ab79bb23b1.png)  


## 合成/聚合複用原則(Composite/Aggregate Reuse Principle) 
[SourceCode](https://ithelp.ithome.com.tw/articles/10236782)  

###### tags : `apply use-relationship first, has-relationship after`

在開發中，一定會遇到套件重複使用的問題。  
**而應該先考慮使用Composite/Aggregate的方式(use-relationship)，其次才是繼承(has-relationship)**。  

For example :  
汽車有汽油車及電動車，其中又有黑色及白色。  
若用繼承的方式會產生黑汽油車、白汽油車、黑電動車、白電動車。若再增加顏色，則類別會更多，耦合性會更高。  
所以應該將顏色抽出，這樣只需要選好車種後，再將顏色帶入，降低整體耦合及提高程式靈活性  
![image](https://user-images.githubusercontent.com/68631186/126638371-fc919b11-d7b7-4c41-b553-44f93e1effb0.png)  

藉由Composite/Aggregate Reuse Principle  
![image](https://user-images.githubusercontent.com/68631186/126638444-1a126ac6-dfb6-4d1c-93f9-ce55ff58817c.png)  


## 最少知識原則(Least Knowledge Principle)/迪米特法則(Law of Demeter) 
[sourceCode](https://ithelp.ithome.com.tw/articles/10237285)  
[菜雞與物件導向 (15): 最少知識原則](https://igouist.github.io/post/2020/12/oo-15-least-knowledge-principle/)  

###### tags : `只依賴應該依賴的對象，只開放應該開放的方法`，`降低類與類間的耦合(盡量減少不必要的Dependency)`

**一個Class對於其他類的資訊了解的愈少愈好, 只與自己直接有關係的類別溝通**   
如教授想了解班級平均成績，只需要問**助教**，不需要透過參與該課程的其他同學。   
```java
/** 
 * no needing to ask each students
 */
for(int i = 0 ; i < students.length() ; i++) 
  Professor.GetStudents(i).myScores();

// instead 
Professor.askTA();
```

- 優點
類別間的耦合度低  
提高模組獨立性  
提高類別的重複使用頻率及系統的擴張性  

- 缺點 : **產生大量的中介類別，系統複雜性提升**
