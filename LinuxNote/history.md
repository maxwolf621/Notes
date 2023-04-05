# The History Command 

###### tags: `Linux`

## history command 
`history` To Show up the Command you have been typed
```bash=
History_id  Command You have been entered
1986        javac Checkpoint.java
1987        java Checkpoint
1988        ls
1989        ls -l
1990        cd Desktop
1991        vim test.cpp
```

using `!History_id` to execute the command
```bash=
$!1990
# !1990 woukd execute : $cd Desktop
```

with `ctrl + r`
> search the keyword
```bash=
(reverse-i-search)`apt': sudo apt-get upgrade
```


with `history -c` clear the history
with `| more` displaying page-like

### ENV GLOBAL VARIABLE of Linux Script for command `history` 

`HISTTIMEFORMAT` FORMATS the showing the informations 
```bash=
# Do remember add whilespace in front of " at the end
HISTTIMEFORMAT="%y-%m-%d_%H:%M:%S "
```

`HISTFILESIZE` and `HISTSIZE`
```bash
# ABLE TO STORE 10000 commands 
HISTFILESIZE = 100000
HISTSIZE = 2000
```
> Def of HISTFIKESIZE and HISTSIZE
> ref : https://stackoverflow.com/questions/19454837/bash-histsize-vs-histfilesize

#### HISTCONTROL

Save the command if 
```bash=
# Wont record the command when command is starting with whitespace
# such as $ ls -al
HISTCONTROL=ignorespace
```

No duplicate Command
```bash=
HISTCONTROL=ignoredups
```

Add options
```bash=
# ignorespace + ignoredups
HISTCONTROL=ignoreboth
```


## source the ENV VARIABLES

To source the ENV VARAIBLE in somewhere in LINUX..
```bash=
# debian base might be /etc/[bash.]bashrc
$vim /etc/[*]bashrc
$source /etc/[*]bashrc
```
