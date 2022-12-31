# Vorstellungsgespräch

[Questions](https://medium.com/%E7%A7%91%E6%8A%80%E7%A0%94%E7%A9%B6%E9%99%A2/java%E9%9D%A2%E8%A9%A6%E9%A1%8C%E6%95%B4%E7%90%86-ed44d4b10437)  
[[ANDY LEE] Java 面試考題](https://hackmd.io/@a110605/rkNKolQVz?type=view#%E8%AA%AA%E6%98%8Estack%E8%B7%9Fheap)

- [Vorstellungsgespräch](#vorstellungsgespräch)
  - [`Final`、`Finally` and `Finalize`三者不同?](#finalfinally-and-finalize三者不同)
  - [String`==`與`.equals( ... )`?](#string與equals--)
  - [Arrays跟ArrayList的差異?](#arrays跟arraylist的差異)
  - [stack跟heap區別?](#stack跟heap區別)
  - [`throw`與`throws`的區別?](#throw與throws的區別)
  - [`Int`以及`Integer`何者會占用更多記憶體?](#int以及integer何者會占用更多記憶體)
  - [兩個相同的物件是否會擁有不同的hashcode?](#兩個相同的物件是否會擁有不同的hashcode)
  - [`a=a+b`與`a+=b`的區別為何?](#aab與ab的區別為何)
  - [是否可以將int強制轉型為byte?](#是否可以將int強制轉型為byte)
  - [是否能保證GC的執行?](#是否能保證gc的執行)
  - [說明集合List與Set區別?](#說明集合list與set區別)
  - [例外相關問題](#例外相關問題)
  - [物件導向的特性](#物件導向的特性)
  - [值跟址意義?](#值跟址意義)
  - [`1/0` , `0/0` ?](#10--00-)
  - [執行續中同步`Synchronized`](#執行續中同步synchronized)
  - [IO四大父類別](#io四大父類別)
  - [覆寫Overriding 過載Overloading的區別](#覆寫overriding-過載overloading的區別)
  - [MVC 是什麼?以及MVC架構的優點與缺點?](#mvc-是什麼以及mvc架構的優點與缺點)
  - [`sendRedirect()` 跟 `forward(request,response)`的差別](#sendredirect-跟-forwardrequestresponse的差別)

## `Final`、`Finally` and `Finalize`三者不同?

`Final` keyword
- `Final VAR`為Read-Only
- `Final method`表該方法不能被Override
- `Final Class`表該類別不能被Inherited 

`Finally`
- 是一個例外處理關鍵字，寫在catch之後，保證一定會執行，主要功用是做**資源釋放**

`Finalize`
- 是一種Object類的方法，因此是所有類別都有的方法，(所有類繼承Object)，當物件要被銷毀前，會執行的方法，此外可以透過`System.gc`呼叫資源回收。

## String`==`與`.equals( ... )`?

`==`
- 是比較儲存的值，基本型別是儲存在Stack中，因此值是相同的。
- 字串是儲存在字串池(String Pool)中，在Stack當中存的是Reference的值，
- 採用`==`時比對就是Reference的值(Reference to same object)

`.equals(...)`
- 是String `@override`後的e`equals`方法，比較的是內容值(CONTENT)。
- Java的字串有String Pool機制，當宣告一個新的字串時，Java會先去String Pool中尋找是否有相同的字串，如果有則會使用原本舊有的字串，沒有時才會新增一個新的字串。 如此一來，如果都是用`String s1="Hello World";` 這種方式宣告新的字串，`String s2="Hello World";`的址的值會與`s1`一樣。
- 如果是用`new`來宣告，e.g. `String s3 = new String("Hello World")`;這種方式，則`s3`這個字串會存在Heap中，與`s1`、`s2`的址的值是不同的，這時候使用`==`就會是false。

## Arrays跟ArrayList的差異?
- Arrays可包含原始(primitive/primitv/primitif)及物件(object)，ArrayList只允許物件
- Arrays大小固定，ArrayList可以動態調整
- ArrayList提供較多方法，如`removeAll`、`iterator`

## stack跟heap區別?

stack 
- 可被預測生命週期的變數或函數資訊都放在stack, 例如區域變數(local variable)、物件或陣列的返回位址(function/method return address)等資訊  

heap
- 動態(Dynamic)配置的記憶體空間   
放置被`new`出來的物件以及內含的成員變數及方法   
 
一般來說stack爆掉是因為有太多區域變數或recursive function跑太深，heap爆掉則是GC沒有做好物件無法被回收

## `throw`與`throws`的區別?
`throw`用於Method**內部拋出異常**，`throws`用於**Method聲明上拋出異常**   
- `throw`後面只能有一個Exception(`throw new exceptionX`)
- `throws`可以聲明多個Exceptions(`exception1, exception2, ...`)   

`throws`
- `throws`通常被應用在聲明Method時，放在Method的大括號前，用來拋出異常，多個異常可以使用逗號(`exception1 , exception2, ...)`隔開   
後續Caller要呼叫該Method時必須要拋出異常或者使用`try ... catch ...`語句處理異常    

`throw`
- `throw`通常用在設計方法中`{ throw .. }`，預先宣告可能會產生的例外，後續方法使用者需要使用`try ... catch ...`處理例外，或者使用`throws`再拋出例外            

## `Int`以及`Integer`何者會占用更多記憶體?

`Integer`會占用較多記憶體，`Integer`是一個Object，會在Heap中儲存，並儲存址(Reference)的值於Stack中，而Int只會保存Value在Stack中，因此Int占用資源較少  

## 兩個相同的物件是否會擁有不同的hashcode?

Nope, 根據hashcode這是不可能的。

## `a=a+b`與`a+=b`的區別為何?
`a += b`會先將資料型別提升至少至`int`型別，而`a=a+b`則不會，因此如果今天將a與b都宣告為byte型別，相加超過127時就會產生錯誤。

## 是否可以將int強制轉型為byte?
可以，可以使用`b=(byte)a`來進行強制轉換，但是超過範圍的部分會被丟棄。

## 是否能保證GC的執行?

Nope，GC機制程式設計師無法保證，但可以透過`System.gc()`調用。
## 說明集合List與Set區別?

List:
1. 有順序性(索引值)
2. 可重複
4. ArrayList : 插入、刪除速度慢，走訪速度快
5. LinkedList : 插入、刪除速度快，走訪速度慢，雙向連結

Set:
1. 無順序性(搭配迭代器)
2. 不可重複、**搜尋速度快**
4. HashSet : 無順序性，查找速度快
5. LinkedHashSet : 有順序性
6. TreeSet : 有排序性(依字母)

Map:
1. 有元素鍵值(Key-Value)，搜尋快
2. 元素可重複，鍵值如果重複新加入值會覆蓋(update)舊有值
3. HashMap : 查找速度慢，插入刪除速度快
4. TreeMap : 有排序性

## 例外相關問題
1
.Throwable為所以例外的共同父類別
2.RuntimeException及其下方子族群為不一定要處理的例外，其他為一定要處理的例外
3.父類別必須至於子類別之後，否則會執行不到
程式設計師需要進行RuntimeCheck具體處理方式不一定
需要根據不同情況處理。

## 物件導向的特性

封裝-繼承-多型

- 封裝 : 資料隱藏、存取限制
  > **封裝有助於提高系統安全性**，可以把自己的資料和方法讓可以信任的類別或物件操作， 對於不可信任的類別可以做資訊的隱藏。 封裝使物件的設計者與物件的使用者分開。

四個存取修飾子:
private 該內別內部
default(預設) 同套件底下類別
protected 同套件底下及其子類別
public 所有類別

- 繼承
  > 建立一個新的類別時，可以從先前定義的類別中繼承資料和函式， **主要目的為提高程式的重複使用性**  
  > 子類別繼承父類別後，可以加入新方法也可以Override   
  > **Java不支援多重繼承，一個子類別只能extends一個父類別**

- 多型 : **繼承是多型得以實現的基礎**，**多型是一種型別表現出多種狀態**(BMW，AUDI)。 將一個方法呼叫同這個方法所屬的主體，關聯起來較做繫節，有分前期後期。

## 值跟址意義?
- Java是Pass by Value傳值   
pass-by-value的意思是將參數值複製後傳遞 
pass-by-reference的意思是傳遞原本的參數值

## `1/0` , `0/0` ?

- 如果整數相除，分母為`0`會產生例外`ArithmeticException`
- 如果是浮點數，分母為`0.0`，不會有例外不會進到`catch`，結果為Infinite

## 執行續中同步`Synchronized`

使用原因: 在程式的某些危險區域，不同執行續可能同時**存取同一份資源而產生衝突或重複修改**
使用目的: 控制每次只能有一個thread再使用同一份resource，其他要使用此resource就得等其thread完成後才能進行存取

- 在Method宣告中加入synchronized關鍵字
- 或程式BLOCK以synchronized標示
- 或類別資料以synchronized標示

## IO四大父類別
Java資料流類別內建於四個抽象父類別
`Inputstream, Outputstream, Reader, Writer`

```diff
Inputstream
Outputstream 
```
- 以8bits為基礎，處理中文有困難


```diff
Reader
Writer 
```
- 存取以16bits為基礎的Char來處理Unicode，JDK1.1後新增。

## 覆寫Overriding 過載Overloading的區別

- Overriding 再繼承的情況下, sub class將Base Class方法重寫，也是實現多型的必須步驟  
- Overloading 再同一個類別中定義了一個以上相同名稱但型購不同的方法。同一類別不允許定義多於 一個相同形構的方法。

## MVC 是什麼?以及MVC架構的優點與缺點?
MVC是程式設計的一種架構方法，主要涵蓋模型（Model）、視圖（View）和控制器（Controller）。

- 模型（Model） — 程式設計師編寫程式應有的功能（實現演算法等等）、資料庫專家進行資料管理和資料庫設計(可以實現具體的功能)。Model 負責資料存取。
- 視圖（View） — 介面設計人員進行圖形介面設計。View 負責顯示資料。
- 控制器（Controller）- 負責轉發請求，對請求進行處理。Controller 負責處理訊息。

優點
1.好維護、方便擴充
2.控制器與 Model 和 View 保持相對獨立，所以可以方便的改變應用程式的資料層和業務規則。
3.多個 View 能共享一個 Model，大幅度提高程式重複使用性。

缺點
1.開發時間長
**2.分層細**

## `sendRedirect()` 跟 `forward(request,response)`的差別

`forward()`
1. 定義在RequestDispatcher的介面，由request.getRequestDispatcher呼叫
2. 內部轉址,url不會顯示程式名稱(可設定成參數)
3. 可透過setAttribute傳遞參數(因為是內部傳址)
4. 效率高
5. 適用於權限管理轉頁時使用

`sendRedirect()`
- 定義在HttpServletResponse
- 直接外部呼叫另一支程式，會顯示程式名稱
- **效率較低(因為client會在request一次)**
- **適用於跳到外部網站或回主畫面使用**

