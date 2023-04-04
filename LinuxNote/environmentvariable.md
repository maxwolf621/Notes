# Environment Variable

[what-does-the-export-path-line-in-bashrc-do](https://askubuntu.com/questions/833922/what-does-the-export-path-line-in-bashrc-do)  
[source-vs-export-vs-export-ld-library-path](https://askubuntu.com/questions/862236/source-vs-export-vs-export-ld-library-path)  
[what-does-export-path-somethingpath-mean](https://askubuntu.com/questions/720678/what-does-export-path-somethingpath-mean)  
[export](https://dotblogs.com.tw/grayyin/2019/06/25/142934)  


## export


**`export` is a Bash builtin means that the variable that you declare after, it will be accessible to child processes.**


In other words, processes will be able to access the variable declared after the `export` keyword through the shell's environment.   

For example  
```bash 
$ export FOO="Foo Fighter"
$ echo $FOO
Foo Fighter
```

- In short `export` only means to make that PATH VARIABLE(`$PATH`) also available for other programs you run from bash.  

## PATH VARIABLE `$PATH`
- [`$PATH` and `export`](https://opensource.com/article/17/6/set-path-linux)

**PATH variable lets you type custom commands for scripts without typing the full directory(e.g, `ls`, `cat` , etc ...).**  
It is an environment variable that contains an ordered list of paths that Linux will search for executables when running a command.  

It is marked for `export` by default**, so this line doesn't have to be rewritten (like above `export FOO = "Foo Fighter"`), you can simply use `echo $PATH`.
```bash!
# list of all path variables
echo $PATH
/home/<USERNAME>/.nvm/versions/node/v18.15.0/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:/snap/bin
```

### Custom Commands

Sometimes, you may wish to install programs into other locations on your computer, but be able to execute them easily without specifying their exact location.  
It can be done easily by adding a directory to your `$PATH`.   

Let's say you wrote a little shell script called `hello.sh` and have it located in a directory called `/home/pi/Desktop/ownfile`.   

This script provides some useful function to all of the files in your current directory, that you'd like to be able to execute no matter what directory you're in.

Then just add it to 
```bash
export PATH=$PATH:/home/pi/Desktop/ownfile
```

```bash
hello.sh
HELLO WORLD
So 15. Aug 12:40:50 CST 2020
pi       tty1         2020-08-15 03:47
pi       tty7         2020-08-15 03:47 (:0)
```
It should now be able to execute the script anywhere on your system by just typing in its shell name, without having to include the full path as you type it.  

## Set your Custom Command/Execution permanently

The above `export` addition to the path will be gone after computer reboot or a new terminal instance is created 

By Design, the `$PATH` is set by your shell every time it launches, but you can set it so that it always includes your new path with every new shell you open. 

The exact way to do this depends on which shell you're running. 
Check the shell version 
```bash=
# `$0` represents the zeroth segment of a command
echo $0

# it might list
# `bash`, `dash`, `zsh`, `tcsh`, `ksh`, and `fish`.
# depends on your shell 
bash
```

For Bash, you simply need to add the line from above, `export PATH=$PATH:/home/pi/Desktop/hello.sh`, to the appropriate file that will be read when your shell launches. 

**There are a few different places where you could conceivably set the variable `name:` potentially in a file called `~/.bash_profile`, `~/.bashrc`, or `~/.profile` for bash user.**  

For example  
`export directory` in `~/.profile`(apply after restarting the computer)
```bash
export PATH="$PATH":/home/bin
# or 
export PATH=${PATH}:/home/bin
```
`export` directory in `~/.bashrc` (apply after closing the terminal)
```bash
export PATH="$PATH":/home/bin
# or 
export PATH=${PATH}:/home/bin
```


For other shells, you'll want to find the appropriate place to set a configuration at start time; `ksh` configuration is typically found in `~/.kshrc`, `zsh` uses `~/.zshrc`. 



## `/etc/environment`

在`/etc/environment`加入要export的directory，這檔案裡面包含原本PATH變數的資料, 要增加請在最後面用`:`加上你要加入的路徑即可
```bash
PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin"
```

## The `:` between both paths
It just separates the directories.  

The console would not recognize the commands it receives without `:`.  
For example  
```bash=
export PATH=$HOME/.local/bin:$HOME/.local/usr/bin:$PATH
```
- Those three places are the three directories that are most commonly used for `scripts/command` files to be stored and therefore should be accessible by the terminal without having to write out the full path to the file.
- `export` sets the environment variable on the left side of the assignment to the value on the right side of the assignment;such environment variable is visible to the process that sets it and to all the subprocesses spawned in the same environment,   
   > i.e. **In this case to the Bash instance that sources `~/.profile` and to all the subprocesses spawned in the same environment (which may include e.g. also other shells, which will in turn be able to access it).**

## source
[`Source_command`](https://bash.cyberciti.biz/guide/Source_command)   
[`source`是什麼](https://dotblogs.com.tw/newmonkey48/2015/04/22/151119)   

When you call a shell script the normal way (say with `./myscript.sh`), **it will be executed in its own process context (a new process environment)**, so all variables set in the script will not be available in the calling shell.  

**The `source` command can be used to load any functions file into the current shell script or a command prompt.**  
If environment commands that are not stored in entries of `$PATH` 


## `$HOME` in the PATH variable

At the beginning of the path that is assigned to the PATH variable, `$HOME` is declared. 

This means that the computer will pretty much grab the value stored in `HOME` and copy-paste it in front of the rest of the line when reading it.
