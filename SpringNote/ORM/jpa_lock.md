# JPA Lock


樂觀鎖定（Optimistic locking）樂觀的認為資料很少發生同時存取的問題，通常在資料庫層級上設為read-commited隔離層級，並實行樂觀鎖定。

在read commited隔離層級之下，允許交易讀取另一個交易已COMMIT的資料，但可能有unrepeatable read與lost update的問題存在，例如：
交易A讀取欄位1
交易B讀取欄位1
交易A更新欄位1並COMMIT
交易B更新欄位1並COMMIT

交易B可能基於舊的資料來更新欄位，使得交易A的資料遺失，或者是：
交易A讀取欄位1、2
交易B讀取欄位1、2
交易A更新欄位1、2，欄位1是新資料，欄位2是舊資料，並COMMIT
交易A更新欄位1、2，欄位1是舊資料，欄位2是新資料，並COMMIT

為了維護正確的資料，樂觀鎖定使用應用程式上的邏輯實現版本控制的解決。

對於lost update的問題，可以有幾種選擇：
先更新為主（First commit wins）
交易A先COMMIT，交易B在COMMIT時會得到錯誤訊息，表示更新失敗，交易B必須重新取得資料，嘗試進行更新。
後更新的為主（Last commit wins）
交易A、B都可以COMMIT，交易B覆蓋交易A的資料也無所謂。
合併衝突更新（Merge conflicting update）
先更新為主的變化應用，交易A先COMMIT，交易B要更新時會得到錯誤訊息，提示使用者檢查所有欄位，選擇性的更新沒有衝突的欄位。

JPA中透過版本號檢查來實現先更新為主，在資料庫中加入一個version欄位記錄，在讀取資料時 連同版本號一同讀取，並在更新資料時比對版本號與資料庫中的版本號，如果等於資料庫中的版本號則予以更新，並遞增版本號，如果小於資料庫中的版本號就丟出 例外（OptimisticLockingException），版本號可以是數字或時間戳記，通常使用數字。

若要定義Entity上版本號欄位對應的屬性，則可以使用@Version，例如：
package onlyfun.caterpillar;

public class User {
    private Long id;
    @Version
    private Long version; // 增加版本屬性
    ....
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
 }

在EntityManager上有個lock()方法，可以讓您主動對Entity進行鎖定，lock()有兩種模式： LockModeType.READ與LockModeType.WRITE。前者允許另一使用者讀取，但不允許更新、刪除，可避免Dirty read、Non-repeatable read，後者則不允許另一使用者讀取、更新、刪除。使用lock()方法，Entity上必須有版本屬性，且必須在Managed狀態，否則無法取得鎖 定，會丟出javax.persistence.PersistenceException，EntityManager會嘗試將lock()轉為資料庫 的鎖定指令。