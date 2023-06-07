# Git Data Structure

Git Auf Grundlage zurückblicken

- [Git Data Structure](#git-data-structure)
  - [Centralized Version Control \&  Distributed Version Control](#centralized-version-control---distributed-version-control)
    - [Zentrale Versionsverwaltung](#zentrale-versionsverwaltung)
    - [Verteilte Versionsverwaltung](#verteilte-versionsverwaltung)
  - [Branching Workflows \& Data Structure](#branching-workflows--data-structure)
      - [git add](#git-add)
      - [git commit -m](#git-commit--m)
      - [git reset](#git-reset)
      - [git checkout](#git-checkout)
      - [git commit -a](#git-commit--a)
  - [Branch](#branch)
      - [git branch BranchName](#git-branch-branchname)
      - [git commit -m](#git-commit--m-1)
  - [git stash](#git-stash)
  - [SSH 連線](#ssh-連線)
  - [.gitignore](#gitignore)
  - [git diff](#git-diff)
    - [git diff HEAD](#git-diff-head)
    - [git diff --cached](#git-diff---cached)
    - [git diff commitID1 commitID2](#git-diff-commitid1-commitid2)

## Centralized Version Control &  Distributed Version Control

[Zentrale Versionsverwaltung und Verteilte Versionsverwaltung](https://git-scm.com/book/de/v2/Erste-Schritte-Was-ist-Versionsverwaltung%3F)    


- Git : Verteilte Versionsverwaltung   
- SVN : Zentrale Versionsverwaltung  
![image](https://user-images.githubusercontent.com/68631186/127731972-f44b02bd-493f-408a-b991-9a83a5ec3eda.png)


### Zentrale Versionsverwaltung
- **Sicherheitsproblem**, mit dem sich viele Leute dann konfrontiert sahen
- If the server goes down for an hour, then during that hour nobody can collaborate at all or save versioned changes to anything they're working on. 
- If the hard disk the central database is on becomes corrupted, and proper backups haven't been kept, you lose absolutely everything 

### Verteilte Versionsverwaltung

Auf diese Weise kann, wenn ein Server beschädigt wird, jedes beliebige Repository von jedem beliebigen Anwenderrechner zurückkopiert werden und der Server so wiederhergestellt werden. Jede Kopie, ein sogenannter Klon (engl. `clone`), ist ein vollständiges Backup der gesamten Projektdaten.  


## Branching Workflows & Data Structure
- [Branch Einführung](https://backlog.com/git-tutorial/tw/stepup/stepup1_1.html)  
- [Git Objects](https://github.com/doggy8088/Learn-Git-in-30-days/blob/master/zh-tw/07.md)   

Wenn wir ein Git Repository anlegen
```bash
cd workSpace # work directory
git init
git clone # clone project from Remote Repo
```
![image](https://user-images.githubusercontent.com/68631186/131378392-cf52c378-b7f0-43a3-9eec-a1ccfb549f10.png)

![image](https://user-images.githubusercontent.com/68631186/127678754-c72c04c1-ddf4-4696-a408-29f238a2e6af.png)  


Die vier oben in der Grafik erwähnten Kommandos kopieren Dateien zwischen dem **Arbeitsverzeichnis(Work Directory)**, dem **Index (Stage)** und dem **Projektarchiv (history)**.   
![image](https://user-images.githubusercontent.com/68631186/127700556-02dd0d20-bfae-4f9a-9207-3dd2d8a012dd.png)


#### git add

`git add file/directories` 

kopiert die Dateien aus dem Arbeitsverzeichnis in ihrem aktuellen Zustand in den Index.

![image](https://user-images.githubusercontent.com/68631186/127679125-d613cd95-08e6-4c31-932a-b71ee17f491e.png)  
- The `git add` command takes a path name for either a file or a directory; if it’s a directory, the command adds all the files in that directory recursively.  

#### git commit -m 

`git commit -m "message"` 把Staged的Modified `commit` 到Current Branch

Es speichert einen Schnappschuss(snapshot) des Indexes als Commit im Projektarchiv.

提交之後，Stage Area會將Stage File(s)清除。


#### git reset 

**es entfernt geänderte Dateien aus dem Index**; dazu werden die Dateien des letzten Commits in den Index kopiert.  

Damit kannst du ein `git add` Dateien rückgängig machen.  
**Mit `git reset` kannst du alle geänderten Dateien aus dem Index entfernen.**

#### git checkout

`git checkout -- files` 使用Stage Area的Modified更新Work Directory撤銷(withdraw)本地Modified

`git checkout -- files` kopiert Dateien aus dem Index in das Arbeitsverzeichnis.  
Damit kannst du die Änderungen im Arbeitsverzeichnis verwerfen. 

- [`git status`](https://github.com/doggy8088/Learn-Git-in-30-days/blob/master/zh-tw/07.md#git-status)

#### git commit -a

Es ist auch möglich, den Index zu überspringen und Dateien direkt aus dem Archiv (History) auszuchecken oder Änderungen im Arbeitsverzeichnis direkt zu committen

![image](https://user-images.githubusercontent.com/68631186/127697166-273744d4-d796-44d6-b71d-65ec07831035.png)

```bash
git commit -a 
```
- Es ist gleichbedeutend mit `git add` auf allen **im letzten Commit bekannten Dateien**, gefolgt von einem `git commit`.

---

```bash
git commit files
```
- Es erzeugt einen neuen Commit mit dem Inhalt aller aufgeführten Dateien aus dem Arbeitsverzeichnis. Zusätzlich werden die Dateien in den Index kopiert.

---

```bash
git checkout HEAD --files 
```
- Es kopiert die Dateien vom letzten Commit sowohl in den Index als auch in das Arbeitsverzeichnis.

## Branch 
- [Branch Usage](BranchUsage.md)  
- [Branch](https://medium.com/i-think-so-i-live/git%E4%B8%8A%E7%9A%84%E4%B8%89%E7%A8%AE%E5%B7%A5%E4%BD%9C%E6%B5%81%E7%A8%8B-10f4f915167e)
- [doggy8088](https://github.com/doggy8088/Learn-Git-in-30-days/blob/master/zh-tw/08.md)  

使用Pointer將每個 Commit 連接成一條時間線，其中 HEAD 指向Current Branch   
<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/image-20191208203219927.png"/> </div><br>

#### git branch BranchName

`git Branch newBranch` 新建一個Ref指向最後(最新的)一個commit  
<img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/image-20191208203142527.png"/>

#### git commit -m
`git commit -m "message"`會讓Current Branch 的 Ref 向前移動(其他Branch的ref不做改變)
<img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/image-20191208203112400.png"/>

## git stash

>>> To store or hide something, especially a large amount

當在某個 `分支X` 上進行維護時，未能將Modified Files做commit時，就進行切換至另一個 `分支Y`，會導致 `分支Y` 也能看到 `分支X` 上面的 Modified Files，**這是因為所有Branches 都共用一个Work Directory**  

可以使用 `git stash` 將當前 `分支X` 的上未 commit 的Modified Files 做 Stashing，此時 Work directory 的所有Modified Files 都將會被存到 Stack 中而不是Work Directory內，就可以安全的切换到別的 Branch 上   

For example，Assume 目前正在 `分支dev` 上進行開發，但此時 `分支main` 上有個 Bug 需要處理，但 `分支dev` 上的tasks目前未完成，又不想立即提交，我們可以在切换 `分支main` 之前利用 `git stash` 將 dev 上未完成還沒有 commit 的Modified Data stash起來  　　
```bash
$ git stash

Saved working directory and index state 
\ "WIP on master: 049d078 added the index file"
HEAD is now at 049d078 added the index file 
(To restore them type "git stash apply")
```

## SSH 連線
Git Repository 和 Github 中 Repository 透過SSH協定做傳輸。

如果Work Directory不存在`.ssh`目錄，或`.ssh`文件下沒有`id_rsa`和`id_rsa.pub`的文件則可以利用該command建立ssh key   
```bash
ssh-keygen -t rsa -C "maxwolf@gmail.com"
```
- 將該指令所生成的public key `id_rsa.pub`的内容複製到Github Account settings中的SSH Keys中即可建立SSH連線

## .gitignore
[.gitignore](https://www.atlassian.com/git/tutorials/saving-changes/gitignore)    
[.gitignore Templates](https://github.com/github/gitignore)   

告訴 Git 忽略(ignore) 以下文件：
1. O.S (自動產的Hidden System)的文件。
   e.g `DS_Store` and `thumbs.db` ...etc
2. Compiled Code.
   e.g Java `.class`, Python `.pyc` ...etc
3. personal IDE config files.
   e.g. `.idea/workspace.xml` ...etc
4. files generated at runtime.
   e.g. `.log`, `.lock` or `.tmp`
5. dependency caches
   e.g. the contents of `/node_modules` or `/packages`
6. Build output directories
   e.g. `/bin`, `/out`, or `/target`

  ## git diff
- [第 09 天：比對檔案與版本差異](https://reurl.cc/ymRo5a)
- [第 10 天：認識 Git 物件的絕對名稱](https://github.com/doggy8088/Learn-Git-in-30-days/blob/master/zh-tw/10.md)

Es gibt verschiedene Möglichkeiten, **die Unterschiede zwischen Commits anzuzeigen.**

Nachfolgend ein paar Beispiele.  
![image](https://user-images.githubusercontent.com/68631186/127685366-ca3dd94b-285a-428a-b6a3-f1d3fc9c096b.png)


### git diff HEAD

`git diff HEAD` allows you to compare the version in the working directory with the version last committed in the remote repository.

### git diff --cached 


`git diff --cached` 與 `git diff --staged` 是完全一樣的結果。
- `--staged` 只是 `--cached` 的Alias。

`git diff --cached` 與 `git diff --cached HEAD` 執行時也是完全一樣的結果，最後的 `HEAD` 可以省略。

`git diff --cached HEAD` **表「當前的索引狀態」與「當前分支的最新版」進行比對** ，不會去比對「工作目錄」的檔案內容，而是直接去比對「索引」與「目前最新版」之間的差異，助於在 `git commit` 之前找出那些變更的內容。


### git diff commitID1 commitID2

`git diff commit1 commit2` 最常用的是 `git diff HEAD HEAD^` 這個命令可以**跳過「索引」與「工作目錄」的任何變更**，而是直接比對特定兩個版本，**Git 是比對特定兩個版本 commit物件 內的那個 tree 物件。**  

```bash
$ echo 1 > ex.txt
$ echo 2 > ex2.txt
$ git add .
$ git commit "the first version"
$ echo 3 > ex.txt
$ echo 4 > ex.txt
$ git add .
$ git commit -m "2th version"

# using git log to get commit id
$ git log
commit 2bba4398958622eaed6554fddc11c42a111e14ee (HEAD -> master)
Author: asdf <1234@gmail.com>
Date:   Sat Jul 31 00:52:54 2020 +0800

    2th version

commit 2a0a50f7822ab3b82015f6d9f8d25d86ac32820b
Author: asdf <1234@gmail.com>
Date:   Sat Jul 31 00:51:47 2020 +0800

    the first version

$ git diff 2bba sa0a
diff --git a/ex.txt b/ex.txt
index d00491f..b8626c4 100644
--- a/ex.txt
+++ b/ex.txt
@@ -1 +1 @@
-1
+4

# --- : the older ex.txt
# +++ : the newer ex.txt
# @@ -1 +1 @@: 
#    - : older 
#    + : newer 
# 1 : Total of the headlines  
```


![image](https://user-images.githubusercontent.com/68631186/175825708-6ff42f8f-8eb1-4ceb-a5d6-df870cf0c429.png)