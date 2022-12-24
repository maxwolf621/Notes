# Basic Shell Command

- [Basic Shell Command](#basic-shell-command)
  - [User Password Information](#user-password-information)
  - [Virtual File System](#virtual-file-system)
  - [`;` and `&&`](#-and-)
  - [ls](#ls)
    - [`-a`](#-a)
    - [`-l`](#-l)
    - [`-F`](#-f)
    - [`-R`](#-r)
  - [File Globing](#file-globing)
  - [`touch`](#touch)
    - [touch -a](#touch--a)
  - [`cp`](#cp)
    - [cp -R](#cp--r)
  - [ln file `[soft|hard]`](#ln-file-softhard)
    - [Soft Link `ln -s`](#soft-link-ln--s)
    - [ls -i](#ls--i)
    - [Hard Link](#hard-link)
  - [`mv`](#mv)
  - [mkdir](#mkdir)
    - [mkdir -p](#mkdir--p)
  - [rmdir & rm](#rmdir--rm)
  - [file file](#file-file)
  - [cat file](#cat-file)
    - [cat -t](#cat--t)
  - [more , less , tail](#more--less--tail)
    - [tail -f](#tail--f)
## User Password Information

The Information for users are in the ... 
```bash
/etc/passwd 
```

To find the current workplace
```bash
$ pwd 
```
- `$` : wait for user input

**Man**ual of the each command
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

## `;` and `&&`

```bash
# stop doing command operation
# if any of commands goes wrong
cd /Am_I_Existing && pwd && ls && cd
-bash: cd: /Am_I_Existing : No such file or directory

# ; : do each commands orderly
cd /NNNNNNNNN ; pwd ; ls
-bash: cd: /NNNNNNNNN: No such file or directory
/home
jian
```

## ls 

### `-a`
to show up the hidden file
### `-l`
List each files **INFORMATION** of user, group such as .. `421` ..etc ..
### `-F`

List directory and files clearly often with `-d` (list directory only)
- `/` means it's directory not a file 
```bash
pi@ji:~ $ ls -F
 backup.php*          Downloads/    MagPi/       Pictures/    test/
'#blockchian.org#~'   file/         mu_code/     Public/      text.txt~
 blockchian.org~      get-pip.py    Music/       s.py~        Videos/
 data/                HistoShift/   myproject/   Templates/
 Desktop/             home.html~    noip/        termp/
```
### `-R`
recursively to show up files and directory until no other directories exists




## File Globing

such as `?`, `*`,`[]` (metacharacter wildcards)

```bash
ls -l f[a-i]ll # - : in the range of  a,b,c,....h, i
-rw-rw-r-- 1 christine christine 0 May 21 13:44 fall 
-rw-rw-r-- 1 christine christine 0 May 21 13:44 fell 
-rw-rw-r-- 1 christine christine 0 May 21 13:44 fill

# list all the files name except fall 
ls -l f[!a]ll 
```

## `touch` 
To create a file
### touch -a

To change the access time 

## `cp`

```bash
cp -i sour dest

# for example 
cp -i doc.txt /home/test/Documents/
```
### cp -R
> To copy all the files in the directory to other directory
```bash
# Copy All the files in Script/ to Mode_script/
cp -R Script/ Mod_scripts
```

using `*` copy all the files end with something 
```bash
# all file end with script copy into directory/ 
$cp *script directory/

```

## ln file `[soft|hard]`

### Soft Link `ln -s`
```bash
# two different file link together
$ls -l data_file 
-rw-rw-r-- 1 christine christine 1092 May 21 17:27 data_file

#                soft file
$ln -s data_file sl_data_file

$ls -l *data_file 
-rw-rw-r-- 1 christine christine 1092 May 21 17:27 data_file
lrwxrwxrwx 1 christine christine 9 May 21 17:29 sl_data_file -> data_file $ 
```
Everyone can access `sl_data_file`

### ls -i

using `ls -i` to check identifier of the file (inode)
```bash
1234 data_file 1235 sl_data_file
```

### Hard Link

Just like Reference in Cpp, for example

For example : 
```bash
$ln code_file hl_code_file
# now code_file and hl_code_file would have the same inode
$ls -i *code_file
410765 code_file  410765 hl_code_file
```



## `mv` 

1. To rename the file name 
```bash
# move directory Mod_Script to New_script
# but actually it just change the file's name
mv Mod_script New_script
```

2. To move the whole directory 
```bash
# move fzll to Picture/
mv fzll Picture/
```



## mkdir
To make a directory

### mkdir -p
```bash
# To create a directory and many sub directories at same time
mkdir -p New_Dir/Sub_Dir/Under_Dir

# New_Dir tree structure
ls -R New_Dir
New Dir:
Sub Dir

Sub Dir:
Under_Dir
```

## rmdir & rm
**Only available for empty directory by default**

```bash
# clean the files in directory
# -i : make sure to delete it or not
rm -i New_Dir/my_file
# remove the empty file
rmdir New_Dir
```

if you want to delete all the files and directories inside a directory at once
```bash
# -f force 
rm -rf My_Dir
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

View the file content 

with `-n` giving the content of each line with number such as ..
```vim
1 Hello
2 This Second
3 What 
4 You
...
...
```
### cat -t

```python
That we'll to   test the it

# with -T it will become
That we'll to^Itest the cat command.
```

## more | less | tail

```bash
$more file #you can see content of the file with pages
$less file #like `more` but more flexible
$head file #check the first 10 line of the content by default
$tail file #see the last 10 line of the content by default

# with -n 
$tail -n 2 log_file  
# only check last 2 line of the content from log_file
```

### tail -f 


**To constantly update the file's status. This are made for programs that need to be monitored for always.**

```bash
tail -f file
``` 


