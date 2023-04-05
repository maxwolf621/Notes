###### tags: `Linux`
# grep 

[Linux 匹配文字 grep 指令用法教學與範例](https://blog.gtwang.org/linux/linux-grep-command-tutorial-examples/)

**`grep` is case intensive by default, and can be disable with `-i`  parameter.**

## Search keyword content within files

```bash
grep keyword_in_files file1 file2 ...

# For Example
grep Ubuntu /etc/os-release
# display everything related keyword Ubuntu
NAME="Ubuntu"
PRETTY_NAME="Ubuntu 18.04.3 LTS"
```

```bash 
# search keyword 'network'
# all files named *.conf under /etc/
grep network /etc/*.conf

# it lists 
/etc/appstream.conf:# over data provided from a network source.
/etc/nsswitch.conf:networks:       files
/etc/rygel.conf:# List of network interfaces to attach rygel to. You can also use network IP or
/etc/rygel.conf:# even ESSID for wireless networks on Linux. Leave it blank for dynamic
/etc/sudo.conf:# By default, sudo will probe the system's network interfaces and
/etc/sysctl.conf:# Additional settings - these settings can improve the network
/etc/sysctl.conf:# security of the host and prevent against some network attacks
/etc/sysctl.conf:# redirection. Some network environments, however, require that these
```

## Search Specific files 

Find the folders named `network` under `/etc`
```bash
ls /etc/ | grep network

# it lists
network
networkd-dispatcher
networks
```


## exclude the keyword

`grep` excludes the keyword with `-v` parameter
```bash
# list the rows without Ubuntu within 
grep -v Ubuntu /etc/os-release
```

## recursion

```bash
grep -r Ubuntu /etc/


# only grep the files *.conf recusively with keyword Ubuntu 
grep -r --include="*.conf" Ubuntu /etc/
```


如果自己的權限沒辦法讀取所有的檔案，就會出現某些檔案無法讀取的錯誤訊息，這時候可以將這種錯誤訊息導向 `/dev/null`，只看正常訊息就好。
```bash
grep -r ubuntu /etc/ 2>/dev/null
```

## Show before row and after row of keyword row

```bash
grep -A 1 Ubuntu /etc/os-release 
# Display 1 line after the line containing `Ubuntu`

grep -B 1 Ubuntu /etc/os-release 
# Display 1 line before the line containing `Ubuntu`

grep -C 1 Ubuntu /etc/os-release 
# Display 1 line before and after the line containing `Ubuntu`
```

### `grep --color=[never | always | auto]`

```bash
grep --color=never  keyword file
grep --color=always keyword file
grep --color=auto   keyword file
```

## Symbol `^|$|[]|?`

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

### Keyword's character limitation
```bash
# ab or ablxode4f , abs , asbcccc , ..
ls | grep "^ab*"
# ab, abc , aba , abe , ....
ls | grep "^ab?"

# ab , abb , abbbb ...
ls | grep "^ab+"

# ab or cd
ls | grep "ab|cd"
ls | grep -E "ab|cd"

# 如果只想要精準篩選出 net 這個單字，可以這樣寫：
# 含有 net 這個單字
ls | grep "<net>"
```
