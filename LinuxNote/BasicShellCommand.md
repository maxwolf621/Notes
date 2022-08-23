# Basic Shell Command

- [Basic Shell Command](#basic-shell-command)
  - [Get Some Information](#get-some-information)
- [Commands for the File System](#commands-for-the-file-system)
    - [ls](#ls)
  - [File Globbing](#file-globbing)
    - [`touch`](#touch)
    - [`cp`](#cp)
    - [linking the file with `ln -s`](#linking-the-file-with-ln--s)
      - [soft link](#soft-link)
      - [hard link](#hard-link)
  - [`mv` Source Dstination](#mv-source-dstination)
  - [rm file](#rm-file)
  - [mkdir](#mkdir)
  - [rmdir](#rmdir)
  - [file file](#file-file)
  - [cat file](#cat-file)
  - [more , less , tail](#more--less--tail)
## Get Some Information

The Information for users are in the ... 
```bash
/etc/passwd 
```
> `$` at terminal means wait for user input


To find the current workplace
```bash
$ pwd 
```



To find **Man**ual of the each command
```bash
man command
#for example
man xterm
# forget the command name could do such 
man -k keyword
# find all information related with terminal
man -k terminal
# To find the certain section using
man section# topic
# find the hostname of section #7
man 7 hostname
```

# Commands for the File System 
ref : https://refspecs.linuxfoundation.org/FHS_3.0/fhs/index.html
ref : https://www.linuxnix.com/linux-directory-structure-lib-explained/

Managment of File System via **virtual directory** 
> ![](https://i.imgur.com/1u7xG8m.png)


mount point : directory for external device (e.g. USB, driver...)
> /etc/fstab
> : Contains file system mounting information and we can edit this file to mount file system permanently and delete mount points.


- /
    > : root of virtual dirctory 
- /etc 
    > : /etc is a folder which contain all your system configuration files in it.
- /home
    > : place for the users (e.g. jian, zhang, Domi …)
- /mnt 
    > : for external device
- /opt 
    > Third parts packages
- /proc 
    > : process information
- /run
    > : system information data when system is on runing 
- /tmp
    >  : temporary files
- /usr
    > : contains by far the largest share of data on a system. Hence, this is one of the most important directories in the system as it contains all the userbinaries, their documentation, libraries, header files, etc
- /lib 
    >: **contains all helpful library files used by the system e.g, the commands we used **


### ls 
- with `-F` 
    > to list directory and files clearly often with `-d` (list directory only)
    ```bash
    pi@ji:~ $ ls -F
     backup.php*          Downloads/    MagPi/       Pictures/    test/
    '#blockchian.org#~'   file/         mu_code/     Public/      text.txt~
     blockchian.org~      get-pip.py    Music/       s.py~        Videos/
     data/                HistoShift/   myproject/   Templates/
     Desktop/             home.html~    noip/        termp/
    ```
    - `/` means it's directory not a file 
- with `-a`
    > to show up the hidden file
- with `-R`
    > recursively to show up files and directory until no other directories exists
- with `-l`
    >to list each files **INFORMATION** of user, group such as .. `421` ..etc ..


## File Globbing

such as `?`, `*`,`[]` (metacharacter wildcards)

```bash
#list all the files name with f_ll,
# _ : in the range of  a,b,c,....h, i
$ ls -l f[a-i]ll 
-rw-rw-r-- 1 christine christine 0 May 21 13:44 fall 
-rw-rw-r-- 1 christine christine 0 May 21 13:44 fell 
-rw-rw-r-- 1 christine christine 0 May 21 13:44 fill

#list all the files name except fall 
$ls -l f[!a]ll 
```

### `touch` 
To create a file

with -a
> To change the access time 
### `cp`
To copy the file, with `i` to confirm
> `cp -i sour dest`

To copy file to other direcotory
> `cp -i testone /home/jian/Documents/`

with -R
> To copy all the files in the directory to other directory
```bash
# All the files in Sciprt/ would copy to Mode_script/
cp -R Script/ Mod_scripts
```

using `*` copy all the files end with something 
```bash
$cp *script dirctory/
# all file end with script would copy into dirctory/ 
```

### linking the file with `ln -s`
file1 ---> file2

#### soft link
```bash
#two different file link together
$ls -l data_file 
-rw-rw-r-- 1 christine christine 1092 May 21 17:27 data_file
$ 
$ln -s data_file sl_data_file
$ 
$ls -l *data_file 
-rw-rw-r-- 1 christine christine 1092 May 21 17:27 data_file
lrwxrwxrwx 1 christine christine 9 May 21 17:29 sl_data_file -> data_file $ 
```
- everyone can access sl_data_file

using `ls -i` to check identifier of the file (inode)
```bash
1234 data_file 1235 sl_data_file
```

#### hard link
> Concept : Just like Reference in Cpp

Say File1 -> Virtual FileX then
> FileX has the content of File1

For example : 
```bash
$ln code_file hl_code_file
# now code_file and hl_code_file would have the same inode
$ls -i *code_file
410765 code_file  410765 hl_code_file
```
:::info
A suggestion  : Do Not use soft-link
:::

## `mv` Source Dstination

1. To rename the file name 
2. To move the whole directory 
```bash
# move directory Mod_sscript to New_script
# but acutally it just change the file's name
mv Mod_script New_script

# move fzll to Picture/
mv fzll Picture/
```


## rm file
To remove file
- f : force to delet
- i : to Comfirm

## mkdir
To make a directory

With -p
```bash=
# To create a directory and many sub directories at same time
$ mkdir -p New_Dir/Sub_Dir/Under_Dir
# Recursely showing up New_dir/
$ls -R New_Dir
New Dir:
Sub Dir

Sub Dir:
Under_Dir
```

## rmdir
**Only avaible for empty directory by deafult**

if you want to really delete all the files inside the directory then
```bash
rm -i New_Dir/my_file
rmdir New_Dir
# or using -r
rm -ri My_Dir
```

if you want to delte all the files and directories inside a directory at once
```bash
rm -rf Directory
```

## file file
To know file's information, such as the file ..
```
It's Code as ASCII ?
It's A link or not ?
It's a text file type ?
It's LSB ?
IT's for GNU/LINUX 2.6.24 ? 
etc... 
```


## cat file 

To watch the file content 

with `-n` giving the content of each line with number
such as ..
```
1 Hello
2 This Second
3 What 
4 You
.
.
```

with `-T` it would turn the cotent such as
```cpp
That we'll to   test the it
# with -T it will become
That we'll to^Itest the cat command.
```

## more , less , tail

```bash
$more file #you can see cotent of the file with pages
$less file #like `more` but more flexible
$head file #check the first 10 line of the cotent by default
$tail file #see the last 10 line of the content by default
```

```bash
# with -n 
$tail -n 2 log_file  
# only check last 2 line of the content from log_file
```

:::success
```bash=
tail -f file
``` 
> To constantly update the file's status
> 
> This are made for programs that need to be monitored for always 
:::


