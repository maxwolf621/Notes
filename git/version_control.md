# Version Control

透過 Version Control 追蹤特定BUG的歷史紀錄，查出特定 BUG 真正發生的原因。

- [Version Control](#version-control)
  - [Principal](#principal)
  - [修正 `commit` 歷史紀錄的理由](#修正-commit-歷史紀錄的理由)
  - [修正`commit`歷史紀錄的注意事項](#修正commit歷史紀錄的注意事項)
  - [`git revert`](#git-revert)
    - [Commit a revert commit](#commit-a-revert-commit)
  - [`git cherry-pick`](#git-cherry-pick)
    - [參數 -x, -e, -n](#參數--x--e--n)
  - [`git reset`](#git-reset)
  - [Changing the Last Commit `git commit --amend`](#changing-the-last-commit-git-commit---amend)
  - [`git rebase`](#git-rebase)
  - [Usage of git rebase](#usage-of-git-rebase)
      - [Alter the order of commits](#alter-the-order-of-commits)
      - [Amend Commit Message](#amend-commit-message)
  - [`git merge`](#git-merge)
  - [Fast Forward Mode (Rebase)](#fast-forward-mode-rebase)
  - [Conflict](#conflict)
      - [Best Solution For Conflict](#best-solution-for-conflict)
  - [`git merge` vs `git rebase`](#git-merge-vs-git-rebase)
    - [Use `merge` to integer the branches](#use-merge-to-integer-the-branches)
    - [Use `rebase` instead of `merge`](#use-rebase-instead-of-merge)
      - [Diagram of merge and rebase](#diagram-of-merge-and-rebase)
  - [Difference btw `rebase` and `merge`](#difference-btw-rebase-and-merge)
    - [Clear History](#clear-history)
    - [It’s only the history that is different.](#its-only-the-history-that-is-different)
  - [Head detached](#head-detached)
  - [`git pull –rebase`](#git-pull-rebase)
    - [`git merge` fast forward mode and No Fast Mode](#git-merge-fast-forward-mode-and-no-fast-mode)

## Principal

當 Project 只要有做細小的修改(modified)就建立版本(commit)並附上言簡意賅的註解，未來才容易追蹤變更，盡量不要一次commit Project多處部分的修改，確保相關的版本修正可以**按順序提交(commit)**，這樣才便於追蹤(e.g 利用`git log`)。

## 修正 `commit` 歷史紀錄的理由

**多數的 git 操作都著重在 Local Repository，也就是在工作目錄下的Version Control，這個Repository就位於`.git/`目錄下** ， 但 Remote Repository 的應用，就牽涉一個TEAM Members不同版本的管控，在git version control 中，**只要同一份儲存庫有多人共用的情況下，若任一人可任意竄改版本，那麼 Git 版本控管一樣會無法正常運作**  

For example
```java
[A] -> [B] -> [C] 
```
可能會發生以下情況
1. `[C]`版本你發現`commit`錯了，必須刪除這一版本所有變更
2. **(測試程式碼)** `commit`了之後才發現`[C]`這個版本其實只有測試程式碼，想刪除他
3. **(錯字)** 其中有些版本的紀錄訊息有錯字，想修改訊息文字，但不影響檔案的變更歷程
4. **(更改commit版本順序)** 想把這些版本的`commit`順序調整為 `[A] -> [C] -> [B]`，讓版本演進更有邏輯性
5. **(某個版本遺漏的重要檔案)** 發現`[B]`這個版本忘了加入一個重要的檔案就`commit`了，你想事後補救這次變更
6. 在你打算分享分支出去時，發現了程式碼有瑕疵，你可以修改完後再分享出去

## 修正`commit`歷史紀錄的注意事項

- **分享 Git 原始碼的最小單位是以 Branch 為單位**
- 一個 Repository 可以有許多 Branches，預設分支為 main
- 你可以任意修改某個 Branch 上的版本，只要你還沒**分享**給其他人
- **當你享特定 Branch 給其他人之後，這些已分享的版本歷史紀錄就別再改了**

## `git revert`


```bash
git revert commit_id
```
- 藉由新增一個`commit`來 **反轉(Revert)/撤銷(Withdraw)** 某一個 commit ，被 `revert` 的 commit 還是會保留在歷史紀錄中，主要用於多人協作的專案。
- 使用 `revert` 會增加一個新的 commit ，通常用於已經推出去的 commit ，或是不允許使用 `reset` 或 `rebase` 修改歷史紀錄的指令的 Scenario。
- 個人的專案，使用 `revert` 有點雞肋，所以大部份都是直接使用 `reset` 。   


```bash
# commit 
pi@JianMayer:~/Desktop/diffExample $ echo 123 > revert.txt
pi@JianMayer:~/Desktop/diffExample $ git add revert.txt
pi@JianMayer:~/Desktop/diffExample $ git commit -m "add revert.txt"
[master b29b246] add revert.txt
 1 file changed, 1 insertion(+)
 create mode 100644 revert.txt

# check the newest commit with -1
pi@JianMayer:~/Desktop/diffExample $ git log -1
commit b29b2464feec76d5f5a792bed4aabddd0492a793 (HEAD -> master)
Author: Maxwolf621 <1234@gmail.com>
Date:   Sun Aug 1 01:56:48 2020 +0800

    add revert.txt

# to revert the commit b29b2464
pi@JianMayer:~/Desktop/diffExample $ git revert b29b2464
[master e659d5e] Revert "add revert.txt" Execute git revert b29b2364
 1 file changed, 1 deletion(-)
 delete mode 100644 revert.txt
```

After `git revert b29b2464` command, terminal shows up the follows (probably vim mode)
```python
Revert "add revert.txt"
Execute git revert b29b2364

This reverts commit b29b2464feec76d5f5a792bed4aabddd0492a793.

# Bitte geben Sie eine Commit-Beschreibung f\u00fcr Ihre \u00c4nderungen ein. Zeilen,
# die mit '#' beginnen, werden ignoriert, und eine leere Beschreibung
# bricht den Commit ab.
#
# Auf Branch master
# Zum Commit vorgemerkte \u00c4nderungen:
#       gel\u00f6scht:       revert.txt
#
# Unversionierte Dateien:
#       asbacvsd
#
```

```python
# commit a revert  commit 
pi@JianMayer:~/Desktop/diffExample $ git log -1
commit e659d5ecd2a1e0cd17e5d7e304e1a08e0d4c8b3a (HEAD -> master)
Author: Maxwolf621 <1234@gmail.com>
Date:   Sun Aug 1 01:58:27 2021 +0800

    Revert "add revert.txt"
    Execute git revert b29b2364
    
    This reverts commit b29b2464feec76d5f5a792bed4aabddd0492a793.
```

```python
pi@JianMayer:~/Desktop/diffExample $ git show b29b2464
commit b29b2464feec76d5f5a792bed4aabddd0492a793
Author: Maxwolf621 <jervismayer@gmail.com>
Date:   Sun Aug 1 01:56:48 2021 +0800

    add revert.txt

diff --git a/revert.txt b/revert.txt
new file mode 100644
index 0000000..190a180
--- /dev/null
+++ b/revert.txt
@@ -0,0 +1 @@
+123
```
- **如果有發生Conflict則無法使用`git revert`**

### Commit a revert commit 

`git revert -n commitId`
- revert該commitId之後不會自動提交，要提交得用`git revert --continue`
- 如果要放棄這次的revert操作，則得用`git revert --abort`

`git revert --continue`
- 表已經完成所有操作，並且提交**一個新修改後的版本**，與`git commit`類似。

`git revert --abort`
- 表準備放棄這次revert的動作，執行這個命令會讓所有變更狀態還原，也就是刪除的檔案又會被加回來。

## `git cherry-pick`

- [Note Taking](https://github.com/doggy8088/Learn-Git-in-30-days/blob/master/zh-tw/21.md)    

**使用`git cherry-pick`時，work directory必須是乾淨，工作目錄下的Stage Area不能有任何準備要 commit 的檔案 (staged files) 在裡面，否則將會無法執行。**

```java
git cherry-pick commit_id
```
- **從某分支挑幾個自己想要的( commits )到自己的分支**。
 
For example, to pick a commit from branch and main
```bash
main_A---main_B--------------------------------main_C
     '
     '---branch1_A---branch1_B---branch1_C

git log branch1 -3 　# only show up first three logs of branch1 
brach1_A-->branch1_B-->brach1_C
```

pick specific commit `branch1_B` and copy it to `main` branch  
Assume `branch1_B` commit Id is `dc07017....f2e5`  
```bash
git checkout main
git cherry-pick dc07017
```

After `main cherry-picks branch1_B` 
```java
main_A--main_B------------------------------->main_C-->main_branch1_B
     '
     '--branch1_A-->branch1_B-->branch1_C
```


### 參數 -x, -e, -n

![image](https://user-images.githubusercontent.com/68631186/127745456-aa5a7270-6fcd-41f8-a2b3-7ce16b9e8089.png)
- `git cherry-pick 2c33a -x` 則新增版本會提交`cherry picked from commit 2c33a....`訊息
- `git cherry-pick 2c33a -e`提交之前輸入自己要的訊息
- `git cherry-pick 2c33a -n`不會自動提交,待使用者自己更改`2c33a`的內容後並`git commit`,此時該新增版本`git log`會顯示該使用者的Author & Date 資訊



## `git reset`

For example，我們有三個版本0
![image](https://user-images.githubusercontent.com/68631186/127735620-a25ecda4-3c0a-462c-baf8-a3e071fca411.png)  

使用`git reset --hard HEAD^` 或 `git reset 9e5e6a4`  
![image](https://user-images.githubusercontent.com/68631186/127735671-591b07c9-b69d-4a5a-928b-8adcac4b9c73.png)  

使用 `git reset --hard ORIG_HEAD` 還原 master 上一次指的位置

`reset` 的Mode
- 復原修改過的索引的狀態(mixed)
- 徹底取消最近的提交(hard)
- 只取消提交(soft)

| Mode    | HEAD的位置| INDEX |	Working Directory |
|---------|-----------|-------|----------|
| soft	  |  修改	    | 不修改|  不修改  |
| mixed	  |  修改	    | 修改	|  不修改  |
| hard	  |  修改	    | 修改	|  修改    |

## Changing the Last Commit `git commit --amend`

[More Details](https://git-scm.com/book/en/v2/Git-Tools-Rewriting-History#_git_amend)  

如果不小心執行了 `git commit` 動作，但還有些檔案忘了`git add`或 commit message 寫錯，想重新補上的話，直接執行 `git commit --amend` 即可。  
- **`--amend`，會把目前紀錄在索引中的變更檔案，全部添加到當前最新版之中，並且要求你修改原本的 commit message**  


Add a new file in commit-version `b533ea`
```bash
pi@JianMayer:~/Desktop/diffExample $ git log
commit b533ea4a2c6b5221efe2a6105e84c54b67397c85 (HEAD -> master)
Author: Maxwolf621 <asdf@gmail.com>
Date:   Sat Jul 31 05:32:29 2020 +0800

    using master

commit 13fe2431967e98aaf0d35c00901f671c6553a4d3
Merge: 9cdb14a 10b0d16
Author: Maxwolf621 <asdf@gmail.com>
Date:   Sat Jul 31 05:30:03 2020 +0800

    using ex.txt from master
```

To amend commit (e.g, add a new file, modify file ....)
```bash
$ echo AddFileb533ea >> amebash
$ git add amend.txt
# amend a commit
$ git commit --amend
```

Vim Mode to edit the commit
```bash
[master c48b2e1] using master AMENDING this COMMIT-VERSION BY ADDING NEW FILE NAMED amend.txt
 Date: Sat Jul 31 05:32:29 2021 +0800
 2 files changed, 1 insertion(+), 4 deletions(-)
 create mode 100644 amend.txt
```

After amending commit
```bash
$ git log
commit c48b2e19f3b139566648a7fa7024a6a24a7331cf (HEAD -> master)
Author: Maxwolf621 <asdf@gmail.com>
Date:   Sat Jul 31 05:32:29 2020 +0800

    using master
    AMENDING this COMMIT-VERSION BY ADDING NEW FILE NAMED amend.txt
```

## `git rebase`
[git-scm](https://git-scm.com/book/zh/v2/Git-%E5%88%86%E6%94%AF-%E5%8F%98%E5%9F%BA)  
[doggy8088](https://github.com/doggy8088/Learn-Git-in-30-days/blob/master/zh-tw/22.md)  

Copy the commits in a branch to other branch 
```bash
(topic) $ git rebase main
```
![image](https://user-images.githubusercontent.com/68631186/127741064-2d8f2263-a555-4e0e-8393-679df2b1c8f2.png)
- rebase branch point `169a6` branch main


[Error `bash: $'\302\203git': command not found` for checking remote branches (in the remote repo)](https://newbedev.com/shell-bash-302-203git-command-not-found-code-example)   
```bash
$ git remote -r
bash: $'\302\203git': command not found
```
After that we push current local  commit to remote repository's `main`  
```bash
$ git push origin remotes/origin/main

Total 0 (delta 0), reused 0 (delta 0), pack-reused 0
To https://github.com/maxwolf621/SpringBootFrontend.git
 * [new reference]   origin/main -> origin/main
```


## Usage of git rebase

[範例](https://reurl.cc/zrqgMa)    

```java
 id     tree    branch    message

1617    *        master   Update a.txt to 3 
        |                                     
1415    | *      branch_1 Add d.txt           
        | |                                 
1213    | *               Update c.txt to asdf 
        | |                                 
1011    | *               Add c.txt                  
        | |                                 
789     | *               Add b.txt                  
        |/                                  
456     *                 Update a.txt to 2   +branch point
        |                                   
123     *                 Initial commit (a.txt)     123
```
- Alter the order of commits
- Amend a commit message
- Insert a commit
- Modify a commit
- 拆解一個 commit
- 壓縮一個 commit ，且保留訊息紀錄
- 壓縮一個 commit ，但丟棄版本紀錄
- Delete a commit

#### Alter the order of commits 

Change the order of commits 
```bash
$ git checkout branch_1
$ git rebase 456 -i
```

vim editor pops up
```bash
# pick commit_id commit_message
pick 789  Add b.txt
pick 1011 Add c.txt
pick 1213 Update c.txt to asdf
pick 1415 Add d.txt 
```

```bash 
# change order of 1011 and 780
pick 1011 Add c.txt
pick 789 Add b.txt
pick 1213 Update c.txt to asdf
pick 1415 Add d.txt
```

```java
h123    *     branch_1  Add d.txt  
        |                         
42oe    *               Update c.txt to asdf         
        |                       
op44    *               Add b.txt    
        |                       
ff21    *               Add c.txt                  
        |                       
fe17    | *   master    Update a.txt to 3             
        |/                      
f53     *               Update a.txt to 2  
        |                      
e13     *               Initial commit (a.txt) 
```

#### Amend Commit Message

```bash
# git rebase branch_pont_id -i
$ git rebase f53 -i 

# enter vim 
pick ff21 Add c.txt
pick op44 Add b.txt
pick 42oe Update c.txt to asdf
pick h123 Add d.txt

# reword the commit message of a commit 
pick ff21 Add c.txt
pick op44 Add b.txt
reword 42oe Update c.txt to asdf
pick h123 Add d.txt 

# enter vim for editing commit 42oe 

Update c.txt to asdf

# .....
# ...
# Changes to be committed
# ....
# 
# modified : c.txt 
```

## `git merge`
[關於合併的基本觀念與使用方式](https://github.com/doggy8088/Learn-Git-in-30-days/blob/master/zh-tw/17.md)  

使用`git merge`的情境，假設 分支main 為開發版本， 分支stable 是客戶使用穩定版本則
1. 當 `stable` 需要Hot Fix 會使用`cherry-pick main` 緊急的修改到 `stable` 或是直接在 `stable` 上直接處理BUG。
2. 為了降低未來 merge 的複雜度使分支版本不要相異太大，利用master `git merge stable`併到 `main`
3. 短期內無緊急出新版壓力的時候，`stable` 要 `git merge master`，避免兩者差異愈來愈大。


![image](https://user-images.githubusercontent.com/68631186/127764834-c8774d82-c305-4ec9-9ed9-6b4f265e8ad3.png)

```bash
$ git checkout -b feature
Zu neuem Branch 'feature' gewechselt

# CHECK ALL BRANCHES
$ git branch
  f2e
* feature
  master

# CREATE MODIFIED AND COMMIT
$ echo 24 > ex.txt
$ echo 25 > branchFeature.txt
$ git add .

# Commit 
$ git commit -m "Branch Features"
[feature 10b0d16] Branch Features
 2 files changed, 2 insertions(+), 1 deletion(-)
 create mode 100644 branchFeature.txt

# switch branch MASTER
$ git checkout master
Zu Branch 'master' gewechselt

# CREATE/MODIFY/COMMIT
$ echo new Line >> a.txt
$ echo new Line >> ex.txt
$ echo for master >> ex.txt
$ cat ex.txt
4
new Line
for master

$ git add .
$ git commit -m "from master"
```
- `git merge`之前，要清楚自己目前在哪個分支
- `git merge`之前，要確保 Work Directory 是乾淨的
- **合併的過程會自動提交一個新Commit**

## Fast Forward Mode (Rebase)

Fast Forward Mode 是將 main 指向要合併的分支，但在這種模式下進行分支合併將會丟失分支的Information，造成不能在分支歷史上看出個別分支訊息。

要避免這種狀況，可以在`merge`時候加上 `--no-ff` 禁用 Fast Forward，再額外加上訊息資訊`-m "[MESSAGE]"` 來產生一個合併提交 Commit     
```bash
git merge --no-ff -m "merge with no-ff" dev   
```
<img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/image-20191208203639712.png"/> 

## Conflict 

![image](https://user-images.githubusercontent.com/68631186/127732570-470a1cc0-4a1a-4d0e-87e1-660ec34f7e75.png)

當合併不同分支時各分支版本不同所造成的衝突，例如 `master` 及 `feature` 同時改到 `ex.txt` 的第一行，則在進行 `git merge feature` 時候會造成 Conflict。
```bash
$ git merge feature
error: Mergen ist nicht m\u00f6glich, weil Sie nicht zusammengef\u00fchrte Dateien haben.
Hinweis: Korrigieren Sie dies im Arbeitsverzeichnis, und benutzen Sie
Hinweis: dann 'git add/rm <Datei>', um die Aufl\u00f6sung entsprechend zu markieren
Hinweis: und zu committen.
fatal: Beende wegen unaufgel\u00f6stem Konflikt.
```

利用 `git diff` 查詢 CONFLICT
```bash
$ git diff
diff --cc ex.txt
index e8c8a77,a45fd52..0000000
--- a/ex.txt
+++ b/ex.txt
@@@ -1,3 -1,1 +1,7 @@@
++<<<<<<< HEAD
 +4
 +new Line
 +for master
++=======
+ 24
++>>>>>>> feature
```

利用 `git status` 查詢 CONFLICT
```bash
$ git status
Auf Branch master
Sie haben nicht zusammengef\u00fchrte Pfade.
 (beheben Sie die Konflikte und f\u00fchren Sie "git commit" aus)
  (benutzen Sie "git merge --abort", um den Merge abzubrechen)

Zum Commit vorgemerkte \u00c4nderungen:

	neue Datei:     branchFeature.txt

Nicht zusammengef\u00fchrte Pfade:
  (benutzen Sie "git add/rm <Datei>...", um die Aufl\u00f6sung zu markieren)

	von beiden ge\u00e4ndert:    ex.txt
```

利用 `gi ls-files` 查詢 CONFLICT
```bash
$ git ls-files -u
100644 b8626c4cff2849624fb67f87cd0ad72b163671ad 1	ex.txt
100644 e8c8a77933921240dfef482db46379e468d4b3e4 2	ex.txt
100644 a45fd52cc5891570d6299fab38643103c3955474 3	ex.txt
```
- 最爛的解決方法就是直接`git add .`後在`git commit`
  > 但會造成conflict的問題訊息也會一併`git commit`(可利用`git reset --hard ORIG_HEAD`回到`git commit`之前的一版)  

#### Best Solution For Conflict
手動解決，選擇是要來自 `feature` 分支的 `ex.txt` 內容還是 `master` 分支的`ex.txt`內容
```bash
++<<<<<<< HEAD
 +4
 +new Line
 +for master
++=======
+ 24
++>>>>>>> feature
```
如果是選擇`master`的就刪除
```html
+ 24
++>>>..feature
++<<<...HEAD
```
保留 
```html
+4
+new Line
+for master
```
刪除完儲存完後在`git add ex.txt`以及`git commit -m "choose master-commit-version"`  

## `git merge` vs `git rebase`

![image](https://user-images.githubusercontent.com/68631186/127736291-d26756fc-3ab8-4fba-adea-4c51ed836c37.png)   

### Use `merge` to integer the branches

It performs a three-way merge between the two latest branch snapshots (`C3` and `C4`) and the most recent common ancestor of the two (`C2`), creating a new snapshot (and `commit`).   

![image](https://user-images.githubusercontent.com/68631186/127736382-d3ec1d77-ac2b-4eab-bd1e-537d6d42c0a3.png)  

### Use `rebase` instead of `merge`

**rebase : lost the original branch information**

With `rebase` you can take the patch of the change that was introduced in `C4` and reapply it on top of `C3`.  

With `git rebase` command, **you can take all the changes that were committed on one branch and replay them on a different branch.**  

For this example, you (Owner of Local Repo) would check out the experiment branch, and then rebase it onto the master branch as follows:
```bash
$ git checkout experiment # Go branch experiments
$ git rebase master       # experiments take master as its base branch
First, rewinding head to replay your work on top of it...
Applying: added staged command
```
![image](https://user-images.githubusercontent.com/68631186/127736475-4f95bbcc-5442-4ec2-b73e-efef990a8a02.png)   
**This operation works by going to the common ancestor of the two branches**  (the branch you’re on (`experiment`) and the one you’re rebasing onto(`master`)), **getting the diff introduced by each commit of the branch you're on, saving those diffs to temporary files**, resetting the current branch to the same commit as the branch you are rebasing onto(`C3`), and finally applying each change(temporary files) IN TURN.

At this point, you (maintainer) can go back to the master branch and do a `fast-forward` merge.
```bash
$ git checkout master
$ git merge experiment
```
![image](https://user-images.githubusercontent.com/68631186/127736624-de257a44-dbe7-49a6-8c8b-6772116b57fa.png)

#### Diagram of merge and rebase

`bugFix` commits a new version `C6` that merges what `master` and `bugFix*` refs to  
![image](https://user-images.githubusercontent.com/68631186/127771473-eaf43412-5841-4c4a-ad03-8f1192450adb.png)


在做`rebase`時，實際動作是複製`C2`與`C3`這兩個 commit 並將它們依序接到 `master` 上，再標註`C3'`是為Branch `bugFix`
![image](https://user-images.githubusercontent.com/68631186/127771480-56575fed-0915-44a7-961f-23c59234f7fc.png)



- The snapshot pointed to by `C4'` is exactly the same as the one that was pointed to by `C5` in the merge example. (is same snapshot )  

`(master) git merge -squash bugfix` 把 `branch_X` 每個 commits 都**存在一個節點**並 merge 到 branch `master` 上  
![image](https://user-images.githubusercontent.com/68631186/132136587-4ab304d0-1f8b-4365-8c04-b2b5aa90f9e2.png)   

`git rebase -i`   
![image](https://user-images.githubusercontent.com/68631186/132136600-93bfa772-b2db-4c1e-a4fc-c704bc0f8afa.png)


## Difference btw `rebase` and `merge` 

### Clear History

Good about Rebasing is that it makes for a cleaner history. 
- **It looks like a linear history in `git log`**
- It appears that all the work happened in series, even when it originally happened in parallel.

**Doing this to make sure your commits apply cleanly on a remote branch** — perhaps in a project to which you’re trying to contribute but that you don’t maintain.   
(**一般我們這樣做的目的是為了確保在向Remote Branches推送時能保持提交歷史的整潔**——例如向某個被其他人維護的項目貢獻程式碼時)

In this case, you’d do your work in a branch and then rebase your work onto `origin/master` when you were ready to submit your patches to the main project. That way, the maintainer doesn't have to do any integration work — just a `fast-forward` or a clean apply.   
(在這種情況下，我們會先在自己的分支裡進行開發，當開發完成時會先將commit `git rebase` 到 `origin/master` 上，然後再向 main 提交修改(`git commit`)。  
這樣的話，該項目的維護者就不再需要進行整合工作，只需要快進合並(`fast forward`)便可)  

### It’s only the history that is different.

Rebasing replays changes from one line of work onto another in the order they were introduced, whereas merging takes the endpoints and merges them together.  
**(`rebase` 是把某 Branch的 commits 有`時間順序性`的依次加入到我們要 rebase 的 Branch 上，而 `merge` 是將 Branches 的 Endpoints 結合在(同時ref到)一個新的commit node)**。

rebase
```java 
            'master
a--b--c--d--g
'--h--j--k--l
            'r

(r) rebase master
          
            master
            '                                           'r
a--b--c--d--g--copy_of_h--copy_of_j--copy_of_k--copy_of_l
'--h--j--k--l
            'r
```


Merging
```java
           'master    
a--b--c--d-g
'--h--j--k--l
            'r        

(r) merge master     
                      (r, master)
                      '
a--b--c--d-g----NEW_NODE
'--h--j--k--l-----'
```

## Head detached

It means pointer `Head` does not point to any node, it might often happen when we `git checkout` the remote branch.
To solve such problem by `checkout` a branch or `checkout -b` a new branch

## `git pull –rebase` 

![image](https://user-images.githubusercontent.com/68631186/132130880-e2dca919-6423-4fe3-a961-0fd4ec41794a.png)
1.  take  commits out and put them to stash to keep work directory clean
2. `pull` the branch from remote repository 
3. `merge` the branch from remote repository and the these we put in the stat 


Error `Cannot rebase: You have unstaged changes`
1. use `git status` to check if there are any modified files which haven't been committed
2. use `git add -A` instead of `git add .` to add all the files (`git add -u` : keep only modified and deletion files (No new created files allow)) 





### `git merge` fast forward mode and No Fast Mode 

- [`git merge` fast forward mode and No Fast Mode](https://reurl.cc/KX8zgy)   

**no fast mode `--no-ff` 額外會在提交一個merge commit(purple node)**
![image](https://user-images.githubusercontent.com/68631186/127765463-8b5884a8-45f3-475c-b9d7-cbf89198677e.png)   


![image](https://user-images.githubusercontent.com/68631186/127765603-849ade88-a225-4df0-b23f-202dc6b2689b.png)
```bash
# Switch to master branch
git checkout master
# Master itself Commit C8 Version
(master) git merge stable 

# Switch to stable 
git checkout stable
# Ref To C8 (stable*)
(stable)　git merge master 
```

![image](https://user-images.githubusercontent.com/68631186/127765616-e18bd517-e69c-4984-92fc-f638b12ae27e.png)  
```bash
git checkout master
# commit C8 version
git merge stable 

(master)git checkout stable
# commit C9 version and Ref to it (stable*)
(stable) git merge master --no--ff 
```
