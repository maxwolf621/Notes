# Branch Ref & Tag 
[YueLinHo - 認識 Git 物件的一般參照與符號參照](https://github.com/doggy8088/Learn-Git-in-30-days/blob/master/zh-tw/11.md)  

- [Branch Ref \& Tag](#branch-ref--tag)
  - [Tag](#tag)
    - [Annotated Tag](#annotated-tag)
  - [參照(Ref)](#參照ref)
    - [git cat-file -p ref](#git-cat-file--p-ref)
  - [symref](#symref)
    - [特別的符號參照](#特別的符號參照)
    - [Custom ref](#custom-ref)
  - [相對名稱表示](#相對名稱表示)

## Tag 
[第 15 天：標籤 - 標記版本控制過程中的重要事件](https://github.com/doggy8088/Learn-Git-in-30-days/blob/master/zh-tw/15.md)

Tag有兩種種類
1. 輕量標籤 (lightweight tag)也就是參照Ref
2. 標示標籤 (annotated tag)

- Git 物件包含 4 種物件類型，分別是 `Blob`, `Tree`, `Commit` 與 `Tag` 物件  
- Tag的用途就是用來 MARK 某一個「版本」或稱為「commit 物件」，以一個「好記的名稱」來幫助我們記憶某個版本。
- **Tag 物件會儲存在 Git 的物件儲存區當中 (`.git/objects/`)，並且會關聯到另一個 commit 物件，建立 Annotated Tag 時還能像建立 commit 物件時一樣包含版本訊息**
- 在大部分的使用情境下，我們都會用「標示標籤」來建立「標籤物件」並且給予「版本訊息」，因為這種「標籤」才是 Git 儲存庫中「永久的物件」。  
  **儲存到物件儲存庫(`.git/objects/`)中的 Git Objects 都是不變的，只有 Index 才是變動的**  

```bash
$ git tag tagName # lightweight tag
$ git tag [tagname] -d # delete lightweight tag

# for example 
$ git tag first-Tag 
$ ls .git/refs/tags # check tags   
first-Tag

$ git tag #show all tags
```

### Annotated Tag

```bash
# tag for current head 
git tag [tagname] -a -m "Message Information"

# tag for certain object id
git tag [tagname] [object_id]
```
- 預設 `git tag [tagname] -a` 會將當前的 HEAD 版本建立成「標籤物件」，如果要將其他特定物件建立為標籤的用法為`git tag [tagname] [object_id]` 。
- Annotated Tag must have `-m` message information


```bash
git tag 1.0.0-beta -a -m "beta-version-tag"

# 返回 tag 物件類型
git cat-file -p 1.0.0-beta
```
- annotated tag顯示的物件內容跟 commit 物件稍稍有點不同。
- 當我們執行 git cat-file -p 1.0.0-beta 時，你從內容看到的 type 講的是上一行 object 的物件類型，這代表你也可以把任何 Git 物件建立成一個標籤物件。


## 參照(Ref)

Ref 幫 Branch 取個好記的名稱(Aliases)。
**Ref 可以指向任意 Git物件，並非只有 COMMITTED 物件不可**。

`.git/ref`資料夾下儲存分支跟Tag的參照資料
- Local  分支 : `.git/refs/heads/`
- Remote 分支 : `.git/refs/remotes/`
- Tag : `.git/refs/tags/`

### git cat-file -p ref

```bash
# Show commit via commit_id
git cat-file -p commit_id
# Show commit via ref name
git cat-file -p refName
# Show commit via absolute path
git cat-file -p ref/[heads | remotes | tags]/[branch_name]
# Show all ref 
git show-ref 
```

```bash
$ git branch f2e      # Create new Local Branch f2e
$ ls .git/refs/heads  # Check branches
f2e  main

# Show All the Refs
$ git show-ref
# ref                                    branch name
6b5748f8ef5623fa7de7ae0f1fcb238e653c0c93 refs/heads/f2e
6b5748f8ef5623fa7de7ae0f1fcb238e653c0c93 refs/heads/main
0f1743fb02b71ee57c87412122a32612b13c1685 refs/remotes/origin/HEAD
0f1743fb02b71ee57c87412122a32612b13c1685 refs/remotes/origin/main
```


Check commit via commit-id
```bash
$ git cat-file -p 6b5748
tree 3a29533eb4820417cd550499986afb110ed44de4
parent 5466c9af32165b815bbcaf6c9c44b06d1793e7b5
author maxwolf621 <5321@gmail.com> 1656255732 +0800
committer maxwolf621 <5321@gmail.com> 1656315772 +0800

updated
here is amend a new commit
```

Check commit with absolute path
```bash
$ git cat-file -p refs/heads/f2e
tree 3a29533eb4820417cd550499986afb110ed44de4
parent 5466c9af32165b815bbcaf6c9c44b06d1793e7b5
author maxwolf621 <5321@gmail.com> 1656255732 +0800
committer maxwolf621 <5321@gmail.com> 1656315772 +0800

updated
here is amend a new commit
```

Check commit via branch/ref
```bash
$ git cat-file -p f2e
tree 3a29533eb4820417cd550499986afb110ed44de4
parent 5466c9af32165b815bbcaf6c9c44b06d1793e7b5
author maxwolf621 <5321@gmail.com> 1656255732 +0800
committer maxwolf621 <5321@gmail.com> 1656315772 +0800

updated
here is amend a new commit
```

Check which commit ref points to
```bash
# git show where f2e's ref points to
$ git show f2e
commit 6b5748f8ef5623fa7de7ae0f1fcb238e653c0c93 (HEAD -> main, f2e)
Author: maxwolf621 <5321@gmail.com>
Date:   Sun Jun 26 23:02:12 2022 +0800

    updated
    here is amend a new commit

diff --git a/create.md b/create.md
new file mode 100644
index 0000000..b4c191b
--- /dev/null
+++ b/create.md
@@ -0,0 +1 @@
+create new one
```

`git cat-file -p [REF]`查找流程
```bash
$ git cat-file -p f2e
```
```java
.git/f2e --> 找不到此檔案
 .git/refs/f2e --> 找不到此檔案
 .git/refs/tags/f2e --> 找不到此檔案
 .git/refs/heads/f2e --> 找到了參照名稱，以下就不繼續搜尋
 .git/refs/remotes/f2e
 .git/refs/remotes/f2e/HEAD
```

## symref

`symref` references to `ref`

### 特別的符號參照

- `HEAD` : 永遠會**指向Work Directory中所設定的「分支」當中的 newest commit**，每次`git commit` 後，這個 `HEAD` 也會指向該分支最新的 Commit 物件。
- `ORIG_HEAD` : 指向目前Commit物件的前一版，經常用來復原上一次的版本變更。
- `FETCH_HEAD` : 使用 Remote Repository 時，可能會使用 `git fetch` 取回所有 Remote Repository 的物件。    
  `FETCH_HEAD`則會**記錄 Remote Repository 中每個分支的`HEAD`的絕對名稱**。
- `MERGE_HEAD` : 執行合併工作時，**合併來源的 Commit物件 絕對名稱會被記錄在`MERGE_HEAD`中**

### Custom ref 


```bash
# create custom ref that point to certain commit id
git update-ref CUSTOM_REF CommitId
```

```bash
$ git log
commit 2bba4398958622eaed6554fddc11c42a111e14ee (HEAD -> master, f2e)
Author: Maxwolf621 <5321@gmail.com>
Date:   Sat Jul 31 00:52:54 2020 +0800

    2th version

commit 2a0a50f7822ab3b82015f6d9f8d25d86ac32820b
Author: Maxwolf621 <5321@gmail.com>
Date:   Sat Jul 31 00:51:47 2020 +0800

    the first version

# CREATE A CUSTOM REF 
# ./git/InitialCommit
# it points to 2a0a
$ git update-ref InitialCommit 2a0a 

$ ls .git 
branches        config       HEAD   index  InitialCommit  objects
COMMIT_EDITMSG  description  hooks  info   logs           refs

$ git cat-file -p InitialCommit
tree 54dec82800354e58e7979533aa5c56ef0d606c39
author Maxwolf621 <5321@gmail.com> 1627663907 +0800
committer Maxwolf621 <5321@gmail.com> 1627663907 +0800

the first version
```

## 相對名稱表示
- [認識 Git 物件的相對名稱](https://github.com/doggy8088/Learn-Git-in-30-days/blob/master/zh-tw/12.md)  
- [`git reflog`](https://www.atlassian.com/git/tutorials/refs-and-the-reflog)


比較常見的 Git Repository，預設只會有一個 *Root Commit 物件*，也就是最一開始建立的那個版本，又稱`Initial Commit`   
在一個 Git Repository 中，所有的 Commit物件 中，除了第一個Commit物件(即Root Commit)外，任何其他 Commit物件 一定都會有一個以上的上層Commit物件(Parent commit)    
![image](https://user-images.githubusercontent.com/68631186/127764031-90df1eba-72f0-4f0b-a40b-d9c1b7d8ac08.png)  

**為什麼可能有多個Parent Commit物件? 因為你很有可能會合併兩個以上的分支到另一個分支裡，所以合併完成後的那個 Commit物件 就會有多個parent commit 物件**
  
- [According to this](https://stackoverflow.com/questions/2221658/whats-the-difference-between-head-and-head-in-git)   
`~` : your ancestor  
`^` : your parents  


```java
- G   H   I   J
   \ /     \ /
-   D   E   F
     \  |  / \
      \ | /   |
       \|/    |
-       B     C
         \   /
          \ /
-          A

A =      = A^0
B = A^   = A^1     = A~1
C = A^2
D = A^^  = A^1^1   = A~2
E = B^2  = A^^2
F = B^3  = A^^3
G = A^^^ = A^1^1^1 = A~3
H = D^2  = B^^2    = A^^^2  = A~2^2
I = F^   = B^3^    = A^^3^
J = F^2  = B^3^2   = A^^3^2
```
- 如果要找到`HEAD`的前一版本，我們會使用 `HEAD~` 或 `HEAD~1`
- 如果要找出另一個`f2e`分支的前兩個版本 (不含`f2e`的`HEAD`版本)，你則可以用`f2e~2`或用`f2e~~`

Both `~` and `^` on their own refer to the parent of the commit (`~~` and `^^` both refer to the grandparent commit, etc.)  

But they differ in meaning when they are used with numbers  
- `~2` means up two levels in the hierarchy, via the first parent if a commit has more than one parent  
- `^2` means the second parent where a commit has more than one parent (i.e. because it's a merge)  

如果你有一個ref為`C`，若要找到它的第一個上層 commit 物件
```diff
C^
C^1
C~
C~1
```
- 在沒有**分支**與**合併**的儲存庫中，關於`^1`與`~1`所表達的意思是完全相同的，都代表「前一版」，**在有分支與合併的儲存庫中，他們則有不同的意義**。

如果你要找到`C`的第二個上層 Commit物件 (在沒有合併的狀況下)
```diff
C^^
C^1^1
C~2
C~~
C~1~1
```
- 但你不能用`C^2`來表達*第二個上層 Commit物件* 原因是在沒有合併的情況下，這個`C`只有一個上層物件而已，你只能用`C^2`代表｢your second parent 」

![image](https://user-images.githubusercontent.com/68631186/127706540-5ac47971-4c72-4aea-a778-44935e3351b7.png)  
![image](https://user-images.githubusercontent.com/68631186/127763923-ef3f28c6-61ab-4271-8638-54db6fa51cb2.png)  

