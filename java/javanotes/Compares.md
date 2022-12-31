# Reentrant vs Thread-safe

[MagicJackTing](https://magicjackting.pixnet.net/blog/post/113860339)   
[Liu, An-Chi 劉安齊](https://tigercosmos.xyz/post/2021/05/system/reentrant-and-thread-safe-code/)  

我們會把 reentrancy 和 thread-safety 搞混是因為它們的狀況近似, 問題都發生在一個函數 (或者是一小段程式碼) 執行時間重疊 (此一行為隱含了函數中可能使用了共用的變數或共用資源, 此即二個問題的共同根源). 但是一個 thread-safe 函數不見得就一定是 reentrant. 舉例來說, 某個函數可以用一個 mutex 把原本的函數整個包裹起來 (如此可以避掉多執行緒環境引起的問題), 但是如果中斷服務程式 ISR 也使用到這個函數, 那它就可能在那兒苦苦的等不到 mutex 被先前鎖住它的執行緒把它釋放出來.

所以結論是 reentrancy 主要是檢討在 ISR 中函數庫裡的哪一些函數是可以呼叫的, 以及自己寫的函數可不可以在 ISR 中使用, 及如何撰寫才可以共用. 而 thread-safe 則是如何確保共用的資料/資源在多個執行緒之間 (不包含 ISR) 可以如預期的被使用. 當然, 你也有可能遇到某一段程式即要保證可以 reentrant, 也需要做到 thread-safe 的情況. 但是只要你可以搞清楚 (是 reentrancy 的問題, 或者是 thread-safe 的問題, 還是二者同時存在), 就可以對症下藥, 把問題解決.


註一: 類似 read-modify-write 的動作還有 test-and-set, fetch-and-add, 和 compare-and-swap. 這一類的動作我們通常是希望一氣呵成, 中間不要被打斷. 這一類的指令是多核 CPU 必備的指令, 否則它就無法解決核與核之間對於共用資源 (周邊設備或者變數) 之間存取競爭的問題. 請參看 Wiki Atomic Operation 相關說明

註二: 在多核 CPU 的環境下, 如果 Thread1 和 Thread2 各自佔用一核是有可能產生這個狀況的.


實作上的要點
Reentrant 實作上比較簡單, 要注意(註三):

不要使用共用資源 (global variables and static variable); 或者也可以在寫入共用變數之前, 把數值暫存在區域變數中, 使用完畢後回存.
不修改自身的程式碼.(註四)
不呼叫 non-reentrant 函數. 例如: clib 中的 strtok(), rand(), srand() 都是 non-reentrant, 對應的 reentrant 版本是 strtok_r(), rand_r(), srand_r().
註三: 如果你是在尋找如何解決 ISR 和主程式共用資源問題的人, 請先不要發火: 這裡要解決的問題是使 non-reentrant 函數變成 reentrant 函數, 所以不會有你要的答案.

註四: 使用狀態機 (state machine) 雖然沒有改變自身程式碼, 但卻有一樣/類似的效果, 也是禁止的. 不過在歸類上使用狀態機是屬於上一條: 使用了共用資源 (狀態變數). 要解決這個問題, 只要一點小修正把共用的狀態變數改為非共同即可, 意即各自使用自己的狀態變數, 互不相干.
另外遞迴函式 (recursion) 感覺上你會以為他也是 reentrant 函式 (可以自己呼叫自己嘛), 可是它也有類似的狀態機 的問題要處理.

Thread-safe 實作的方法有很多, Wiki 網站提到 thread-safe 實作方法上可分為二類:

避免發生共用
使用可重入 (Reentrancy) 技術: 把靜態變數及全域變數全部改為區域變數 (區域變數通常放在 stack 區, 可以順利避免共用).
使用執行緒自身的儲存空間 (TLS, Thread Local Storage): 所以每一個執行緒都不同, 都有自己的一份拷貝. (C11 支援加上 keyword _Thread_local 來將變數移到 TLS; C++11 改用 keyword thread_local; gnu 或者其他 C++ compiler 則用 keyword __thread)
當無法避免共用時, 採用鎖定 (同步) 機制
使用互斥鎖 (Mutex, Mutual Exclusion): 利用序列化機制 (serialization) 來保證任一時間點都只有一個執行緒讀或寫共用資料. 但是多個 mutex 一起運作時需要小心仔細的對待, 不恰當的實作可能引起一些負作用, 如: deadlocks, livelocks, resource starvation.
使用原子操作 (AO, Atomic Operations): 在存取共用資料時禁止被其他執行緒打斷. 一般實作上需要一些新的硬體指令來支援, 它是實作執行緒鎖定的元件, 也是前一項 mutex 實作的基礎. 現代的多核 CPU (x86, MIPS, ARMv6 and later) 都至少有支援一對指令可以協助完成 AO 動作 (可能只支援部份 AO). 單核 CPU 在 OS 核心的部份可以用中斷鎖定 (disable/enable interrupt) 來支援 AO 的需求. 但是如果是在用戶空間 (user-space) 卻是不可以可能是不可行, 詳細請參考: Emulated atomic operations and real-time scheduling.
使用不可變物件 (immutable objects): 物件構建 (construct) 之後即無法改變內容. 要實作改變時, 是以重新構建 (re-construct) 來取代修改現有之內容.