[grep](https://blog.gtwang.org/linux/linux-grep-command-tutorial-examples/)

grep : 搜尋關鍵字 

## Search keyword in the file contents

```bash
# grep keyword_in_files file1 file2 ...
grep Ubuntu /etc/os-release
# display everything related keyword Ubuntu
NAME="Ubuntu"
PRETTY_NAME="Ubuntu 18.04.3 LTS"
```

grep 亦可搭配萬用字元（*）同時搜尋多個檔案，例如在 /etc/ 目錄之下所有 *.conf 檔案中，尋找 network 這個字眼：

# 在 /etc/*.conf 中搜尋 network 關鍵字
```bash
grep network /etc/*.conf
# file's position : descriotion of this file
/etc/dhcpcd.conf:# Respect the network MTU. This is applied to DHCP routes.
/etc/nsswitch.conf:networks:       files
/etc/sysctl.conf:# Additional settings - these settings can improve the network
/etc/sysctl.conf:# security of the host and prevent against some network attacks
/etc/sysctl.conf:# redirection. Some network environments, however, require that these
```
- 搜尋多個檔案時，在輸出中會標示資料來源是哪一個檔案。

## search specific files 

#### 篩選含有 network 關鍵字的檔案名稱
ls /etc/ | grep network
network
networkd-dispatcher
networks
- grep 預設會區分字母的大小寫，如果希望以不分大小寫的方式搜尋，可以加上 -i 參數：
- 若要標示匹配文字的行號，可以加上 -n 參數：

## 反向匹配
若想要將匹配的資料排除，只顯示出沒有關鍵字的那幾行資料，可以加上 -v 參數。例如顯示不包含 Ubuntu 關鍵字的那幾行：

#### 顯示不包含 Ubuntu 關鍵字的行
```bash
grep -v Ubuntu /etc/os-release
```

## 遞迴搜尋檔案
如果想要在指定目錄與其子目錄下所有的檔案中，搜尋指定的關鍵字，可以加上 -r 參數：

#### 在 /etc/ 下所有檔案中搜尋 ubuntu
```bash
grep -r ubuntu /etc/
```
如果只想要從特定的檔案中尋找關鍵字，可以使用 -r 搭配 --include 指定檔案類型：

#### 在/etc/底下所有 *.conf 中尋找 ubuntu
```
grep -r --include="*.conf" ubuntu /etc/
```
- 如果自己的權限沒辦法讀取所有的檔案，就會出現某些檔案無法讀取的錯誤訊息，這時候可以將這種錯誤訊息導向 `/dev/null`，只看正常訊息就好：

### 不顯示錯誤訊息
grep -r ubuntu /etc/ 2>/dev/null

## 顯示前後幾行
有時候只顯示匹配成功那一行，不容易看出是否是我們想要找的資料，這時候可以加上 -A（After）、-B（Before）或-C（Context），指定要顯示的前後行數：
```bash
grep -A 1 Ubuntu /etc/os-release # Display 1 line after the line containing `Ubuntu`
grep -B 1 Ubuntu /etc/os-release # Display 1 line before the line containing `Ubuntu`
grep -C 1 Ubuntu /etc/os-release # Display 1 line before and after the line contaning `Ubuntu`
```

## `color`

```bash
grep --color=never keyword file
grep --color=always keyword file
grep --color=auto keyword file
```

## Whildcard

### `^`
find the keyword that starts with a
```bash
ls | grep "^a"
```
# `$`
find the keyword taht ends with b
```bash
ls | grep "b$"
```
# `[..]`

```bash
ls | grep "^[ab]" #find the keyword that starts with a or b
ls | grep "[ab]$" #find the keyword that ends with a or b
```

限制Keyword's character出現次數的指定：
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
