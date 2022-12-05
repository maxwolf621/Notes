# grep 搜尋關鍵字

- [grep 搜尋關鍵字](#grep-搜尋關鍵字)
  - [Search keyword in the file contents](#search-keyword-in-the-file-contents)
  - [Search Specific files](#search-specific-files)
  - [反向匹配 `grep -v`](#反向匹配-grep--v)
  - [遞迴搜尋 `grep -r`](#遞迴搜尋-grep--r)
    - [顯示關鍵字的前後幾行 `grep [-A | -B | -C]`](#顯示關鍵字的前後幾行-grep--a---b---c)
    - [`grep --color=[never | always | auto]`](#grep---colornever--always--auto)
  - [Wildcard](#wildcard)
    - [start with `^`](#start-with-)
    - [end with `$`](#end-with-)
    - [range of x to y`[x-y]`](#range-of-x-to-yx-y)
    - [限制Keyword's character出現次數的指定](#限制keywords-character出現次數的指定)

[linux-grep-command-tutorial-examples](https://blog.gtwang.org/linux/linux-grep-command-tutorial-examples/)

## Search keyword in the file contents

```bash
grep keyword_in_files file1 file2 ...
```
For example 
```bash
grep Ubuntu /etc/os-release
# display everything related keyword Ubuntu
NAME="Ubuntu"
PRETTY_NAME="Ubuntu 18.04.3 LTS"
```


`/etc/*.conf` 中搜尋 `network` 關鍵字
```bash 
grep network /etc/*.conf

/etc/dhcpcd.conf:# Respect the network MTU. This is applied to DHCP routes.
/etc/nsswitch.conf:networks:       files
/etc/sysctl.conf:# Additional settings - these settings can improve the network
/etc/sysctl.conf:# security of the host and prevent against some network attacks
/etc/sysctl.conf:# redirection. Some network environments, however, require that these
```
- grep輸出格式 : File's Position : Description of The file

## Search Specific files 

篩選在`/etc/`資料夾下含有 `network` keyword的檔案名稱
```bash
$ ls /etc/ | grep network
network
networkd-dispatcher
networks
```
- **grep 預設會區分字母的大小寫**，如果希望以不分大小寫的方式搜尋，可以加上 `-i` 參數; 若要標示匹配文字的行號，可以加上 `-n` 參數

## 反向匹配 `grep -v`
若想要將匹配的資料排除，只顯示出沒有關鍵字的那幾行資料，可以加上 `-v` 參數。

例如顯示不包含 Ubuntu 關鍵字的那幾行：
```bash
# os-release內沒有出現Ubuntu關鍵字的行
grep -v Ubuntu /etc/os-release
```

## 遞迴搜尋 `grep -r`

如果想要在指定目錄與其子目錄下所有的檔案中，搜尋指定的關鍵字。
```bash
grep -r ubuntu /etc/
```

**如果只想要從特定的檔案中尋找關鍵字，可以使用 `grep -r --include` 指定檔案類型**，例如在`/etc/*.conf` 中尋找 `ubuntu`。
```
grep -r --include="*.conf" ubuntu /etc/
```


如果自己的權限沒辦法讀取所有的檔案，就會出現某些檔案無法讀取的錯誤訊息，這時候可以將這種錯誤訊息導向 `/dev/null`，只看正常訊息就好。
```bash
grep -r ubuntu /etc/ 2>/dev/null
```

### 顯示關鍵字的前後幾行 `grep [-A | -B | -C]`
有時候只顯示匹配成功那一行，不容易看出是否是我們想要找的資料，這時候可以加上 `-A`（After）、`-B`（Before）或 `-C`（Context），指定要顯示的前後行數：
```bash
grep -A 1 Ubuntu /etc/os-release # Display 1 line after the line containing `Ubuntu`
grep -B 1 Ubuntu /etc/os-release # Display 1 line before the line containing `Ubuntu`
grep -C 1 Ubuntu /etc/os-release # Display 1 line before and after the line containing `Ubuntu`
```

### `grep --color=[never | always | auto]`

```bash
grep --color=never  keyword file
grep --color=always keyword file
grep --color=auto   keyword file
```

## Wildcard

### start with `^`
find the keyword that starts with `a`
```bash
ls | grep "^a"
```
### end with `$`
find the keyword that ends with `b`
```bash
ls | grep "b$"
```
### range of x to y`[x-y]`

```bash
ls | grep "^[ab]" #find the keyword that starts with a or b
ls | grep "[ab]$" #find the keyword that ends with a or b
```

### 限制Keyword's character出現次數的指定
```bash
# a 開頭，接著 b 出現零次以上
ls | grep "^ab*"

# a 開頭，接著 b 出現零次或一次
ls | grep "^ab?"

# a 開頭，接著 b 出現一次以上
ls | grep "^ab+"

# 含有 ab 或 cd
ls | grep "ab|cd"
ls | grep -E "ab|cd"

# 如果只想要精準篩選出 net 這個單字，可以這樣寫：
# 含有 net 這個單字
ls | grep "<net>"
```
