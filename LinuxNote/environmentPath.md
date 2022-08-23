###### tags: `Linux`
# Environment Variable

[export Path](https://askubuntu.com/questions/833922/what-does-the-export-path-line-in-bashrc-do)  
[soruce And export](https://askubuntu.com/questions/862236/source-vs-export-vs-export-ld-library-path)  
[export meaning](https://askubuntu.com/questions/720678/what-does-export-path-somethingpath-mean)  

## [`export`](https://dotblogs.com.tw/grayyin/2019/06/25/142934) 
- It's a Bash builtin

The `export` means that the variable that you declare after **it will be accessible to child processes.**
In other words, **processes will be able to access the variable declared after the `export` keyword through the shell's environment.**

For example  
```console 
pi@Mayer:~ $ export FOO="Foo Fighter"
pi@Mayer:~ $ echo $FOO
Foo Fighter
```
In short   
> **`export` only means to make that PATH variable also available for other programs you run from bash.**

## [`$PATH` and `export`](https://opensource.com/article/17/6/set-path-linux)
**The PATH variable(`$PATH`) is an environment variable that contains an ordered list of paths** that Linux will search for executables when running a command.

- **Using these paths means that we do not have to specify an absolute path when running a command.**  
  > Linux will search these ordered list of paths for executables when running a command(e.g. `ls`, `cat` , ... etc)    

- **PATH variable lets you type custom commands for scripts without typing the full directory.**
  > **It is marked for `export` by default**, so this line doesn't have to be rewritten.

For example
```console
# To see list of paths
pi@JianMayer:~ $ echo $PATH
/home/pi/.config/nvm/versions/node/v16.4.2/bin:/home/pi/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/games:/usr/games:/snap/bin
```

## Set Our Own PATH
Sometimes, you may wish to install programs into other locations on your computer, but be able to execute them easily without specifying their exact location. You can do this easily by adding a directory to your `$PATH`. 

Let's say you wrote a little shell script called `hello.sh` and have it located in a directory called `/home/pi/Desktop/ownfile`.   

This script provides some useful function to all of the files in your current directory, that you'd like to be able to execute no matter what directory you're in.

So we can add `/home/pi/Desktop/ownfile` to the PATH variable `$PATH`  
```console
pi@JianMayer:~/Desktop $ export PATH=$PATH:/home/pi/Desktop/ownfile
pi@JianMayer:~/Desktop $ hello.sh
HELLO WORLD
So 15. Aug 12:40:50 CST 2020
pi       tty1         2020-08-15 03:47
pi       tty7         2020-08-15 03:47 (:0)
```
- It should now be able to execute the script anywhere on your system by just typing in its name, without having to include the full path as you type it.

## Set your PATH permanently
But what happens if you restart your computer or create a new terminal instance? Your addition to the path is gone! This is by design. 

The `$PATH` is set by your shell every time it launches, but you can set it so that it always includes your new path with every new shell you open. 

The exact way to do this depends on which shell you're running.  

Check the shell version 
```console
echo $0
```
- `$0` represents the zeroth segment of a command
- Usually this is the Bash shell, although there are others, including `Dash`, `Zsh`, `Tcsh`, `Ksh`, and `Fish`.

For Bash, you simply need to add the line from above, `export PATH=$PATH:/home/pi/Desktop/ownfile`, to the appropriate file that will be read when your shell launches. There are a few different places where you could conceivably set the variable `name:` potentially in a file called `~/.bash_profile`, `~/.bashrc`, or `~/.profile`. 

For other shells, you'll want to find the appropriate place to set a configuration at start time; `ksh` configuration is typically found in `~/.kshrc`, `zsh` uses `~/.zshrc`. Check your shell's documentation to find what file it uses.


## Different Way to Add directory to `$PATH`

`export` directory in terminal 
```console
export PATH="$PATH":/home/bin
export PATH=${PATH}:/home/bin
```
- 輸入之後可以使用export指令來查看環境變數是否有輸入進去。
- 此修改重開機後，就必須再作一次

Add `export directory` in the `~/.profile`
```console
export PATH="$PATH":/home/bin
# or 
export PATH=${PATH}:/home/bin
```
- 此修改必須在重開機之後，才會有作用

在`~/.bashrc`加入要export的directory
```console
export PATH="$PATH":/home/bin
或
export PATH=${PATH}:/home/bin
```
- 此修改只需關掉Terminal在開啟後，就都會被設定

在`/etc/enviroment`加入要export的directory  
這檔案裡面包含原本PATH變數的資料, 要增加請在最後面用:加上你要加入的路徑即可
```console
PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin"
```


## `$HOME` in the PATH variable

At the beginning of the path that is assigned to the PATH variable, `$HOME` is declared. 
This means that the computer will pretty much grab the value stored in `HOME` and copy-paste it in front of the rest of the line when reading it.

## The `:` between both paths
It just separates the directories.  
Without those directories, the console would not recognize the commands it receives.    

For example
```bash
export PATH=$HOME/.local/bin:$HOME/.local/usr/bin:$PATH
```
- Those three places are the three directories that are most commonly used for `scripts/command` files to be stored and therefore should be accessible by the terminal without having to write out the full path to the file.
- `export` sets the environment variable on the left side of the assignment to the value on the right side of the assignment;such environment variable is visible to the process that sets it and to all the subprocesses spawned in the same environment,   
   > i.e. **In this case to the Bash instance that sources `~/.profile` and to all the subprocesses spawned in the same environment (which may include e.g. also other shells, which will in turn be able to access it).**

## [`source`](https://bash.cyberciti.biz/guide/Source_command)

[`source`是什麼](https://dotblogs.com.tw/newmonkey48/2015/04/22/151119)

When you call a shell script the normal way (say with `./myscript.sh`), **it will be executed in its own process context (a new process environment)**, so all variables set in the script will not be available in the calling shell.  

- **The `source` command can be used to load any functions file into the current shell script or a command prompt.** 
  > If environment commands that are not stored in entries of `$PATH` 





