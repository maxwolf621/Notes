

## Static Member

需要經由建構物件的過程，而是直接從類別本身提供的屬性與方法，皆稱之為靜態屬性與方法，又被稱為靜態成員（Static Members）。

因此，靜態成員具有一個很重要的特點：不管物件被建立多少次，靜態成員只會有一個版本 —— 這也符合靜態的概念：固定、單一版本、不變的原則等等。

通常會使用靜態成員的狀況：
- 靜態成員不會隨著物件建構的不同而隨之改變
- 靜態成員可以作為類別本身提供的工具，不需要經過建構物件的程序；換句話說：類別提供之靜態成員本身就是可被操作的介面

To call Static Member
```typescript
ClassName.staticMember;
ClassName.staticMethod();
```


## static And Access Modifiers

- 類別的靜態成員可以被設定不同的存取模式 —— 包含 `public`、`private` 以及 `protected` 模式