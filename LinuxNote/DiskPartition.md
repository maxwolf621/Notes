# Disk Partition

- [Linux分區技巧](http://blog.shiami.info/peterliu/24-linux%E5%88%86%E5%8D%80%E6%8A%80%E5%B7%A7/)

## Virtual File System 
ref : https://refspecs.linuxfoundation.org/FHS_3.0/fhs/index.html   
ref : https://www.linuxnix.com/linux-directory-structure-lib-explained/   

**Virtual Directory of Linux** 
![](https://i.imgur.com/1u7xG8m.png)

- `/`    : root of virtual directory 
- `/etc` : contain all the system/application CONFIGURATION(`.conf`) files
- `/home`. `/home/username` : the users files (e.g. tom, alex)
- `/opt` : Third parts packages
- `/run` : system information data when system is on running 
- `/tmp` : temporary files
- `/lib` : 共享庫。 Cntains all helpful library files used by the system. e.g, the commands we used
- `/usr` : **: contains by far the largest share of data on a system. Hence, this is one of the most important directories in the system as it contains all the userbinaries, their documentation, libraries, header files, etc**
- `media`, `/mnt` : Mount Point for external device 
- `/boot` : bootloader
- `var`, `srv` : Server, sql database


## Bin

- `/bin`, `/usr/bin`, `/usr/local/bin` : Executable files for User & System Administrator
- `/sbin`, `/usr/sbin`, `/usr/local/sbin` : Executable files for System Administrator

`/bin`是系統的一些指令，存放系統執行檔,e.g. `cat`、`cp`、`chmod` `df`、`dmesg`、`gzip`、`kill`、`ls`、`mkdir`、`more`、`mount`、`rm`、`su`、`tar`. etc.   
`/sbin`一般是指超級用戶指令，主要放置一些系統管理的必備程式例如:`cfdisk`、`dhcpcd`、`dump`、`e2fsck`、`fdisk`、`halt`、`ifconfig`、`ifup`、 `ifdown`、`init`、`insmod`、`lilo`、`lsmod`、`mke2fs`、`modprobe`、`quotacheck`、`reboot`、`rmmod`、 `runlevel`、`shutdown`. etc.


`/usr/bin` 安裝的軟體的`.sh`。主要放置一些應用軟體工具的必備執行檔例如c++、g++、gcc、chdrv、diff、dig、du、eject、elm、free、gnome*、gip、htpasswd、kfm、ktop、last、less、locale、m4、make、man、mcopy、ncftp、 newaliases、nslookup passwd、quota、smb*、wget. etc.

`/usr/sbin` 放置一些用戶安裝的系統管理的必備程式。例如:dhcpd、httpd、imap、in.*d、inetd、lpd、named、netconfig、nmbd、samba、sendmail、squid、swap、tcpd、tcpdump等。

如果新裝的系統，運行一些很正常的諸如：shutdown，fdisk的命令時，提示：bash:command not found。那麼首先就要考慮root的$PATH裏是否已經包含了這些環境變量。
可以查看PATH，如果是：PATH=$PATH:$HOME/bin則需要添加成如下： `PATH=$PATH:$HOME/bin:/sbin:/usr/bin:/usr/sbin`

如果是用戶和管理員必備的二進制文件，就會放在`/bin`；如果是系統管理員必備，但是一般用戶根本不會用到的二進制文件，就會放在`/sbin`；
如果不是用戶必備的二進制文件，多半會放在`/usr/bin`；如果不是系統管理員必備的工具，如網絡管理命令，多半會放在`/usr/sbin`；



