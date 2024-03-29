###### tags: `Linux`


# BASIC Shell Script

https://blog.techbridge.cc/2019/11/15/linux-shell-script-tutorial/

[TOC]


## `;` separator

Using `;` to gather the commands in one line in the terminal;
```console
date ; who

# it shows
Wed Apr  5 05:18:11 PM CST 2021
issac    tty2         2021-04-05 02:37 (tty2)
```

## shell script

Use a shell script to avoid the troublesome to type the commands in the terminal repeatedly.  


Create a shell script
```console=
vim script.sh
```

A shell script(`*.sh`) should declare `# !/bin/bash` in the first line to tell the system which shell(`bash` here) allows to execute this script.  
```bash
# !/bin/bash
# 
# This scirpt display the date and who's logged on
date
who
```

Set up the priority and executable of the file after creating the script
```console
chmod u+x script.sh
```

Execute the shell script
```console
./script.sh

# it shows
So 11. Aug 09:38:46 CST 2020
pi       tty1         2021-08-15 03:47
pi       tty7         2021-08-15 03:47 (:0)
```
- it creates a child shell while `./script.sh` is executed and the child shell can not use the variable in the script from father shell


:::danger
Some Linux OS put `$HOME/bin` into `$PATH` environment, it supplies every user a file that the shell can find the executable commands in the `/HOME/*` directory
:::


## `$`variable


We can define local variable in a shell script
```console
var1=10
var2=-57
var3=testing
var4="more than one words"
```

if we want to show up `$15` dollars , add `\` escape character before `$15`
```console
echo "it cost \$15 usa dollars"
printf "%s\n" "it cost \$15 usa dollar"
```


#### Operator variables and `$(variable)`

By giving command `date` to a local variable
```console
# assign `replace` to present the command `date`
replace=$(date) 
echo "The date and time in the system is :" $replace

# copy the /usr/bin directory listing to a log file
# format as %y%m%d

today=$(date+%y%m%d)
ls /usr/bin -al > log.$today
```

## `>` output
Using `>` logs the output of `command` to other script in the specific file 

syntax
```console
command > outputfile
```

If the script `test6` doesn't exist then create one;  
else it overwrites.   
```console
pi@Ji:~ $ date > test6
pi@Ji:~ $ ls - l test6
-rw-r--r-- 1 user user 29 Feb 10 17:56 test6
pi@Ji:~ $ cat test6
Thu Feb 10 17:56:58 EDT 2014
```

#### using `>>` to avoid **overwriting(überschriben)**

```console=
pi@Ji:~ $ who >> test6
pi@Ji:~ $ cat test6
Thu Feb 10 18:02:14 EDT 2014 #this is date > test6
user pts/0 Feb 10 17:55      #this is who >> test6
```

## `<` redirect backward (Input)

```console
command < inputfile
```

using redirect backward to find out the information of inputfile via command `date`  
for example  
```console
date < inputfile
```
- To show up inpufile's date and time

## Pipe

Without Pipe
```console
# store the command `rpm -qa` outut information into `rmp.list`
pi@JianMayer:~/Desktop $ rpm -qa > rmp.list 
# get information of `rmp.list` that would display sorted by order of the character
pi@JianMayer:~/Desktop $ sort < rmp.list    
```
- `-qa` to show up installed package list
- `sort` to help rmp.list sort by order of character

Now we can use `piping` to make it simpler 
```console
command1 | command2
```

To simplize the above commands
```console
pi@mayer:~ $ rpm -qa | sort
pi@mayer:~ $ -qa | sort | more         #with pages
pi@mayer:~ $ rmp -qa| sort > rmp.list  # To output (store) the information to the file rmp.list
pi@mayer:~ $ more rmp.list
```

## Mathmatical Operator

#### command `expr`
```diff
a1 | a2 
a1 & a2 
a1 < a2
a1 % a2
STRING : REGEXP           # if STRING and REGEXP match
match STRING REGEXP       # if STRING and REGECP match
substr STRING POS LENGTH  # LENGTH of substr in STRING
index STRING CHARS        # Find character in the string
length STRING             # LENGTH of STRING
+ TOKEN                   # keyword
(EXPRESSION)              # return value of EXPRESSION
```

command `expr --help`
```
  ARG1 | ARG2       ARG1, wenn es weder null noch 0 ist, sonst ARG2

  ARG1 & ARG2       ARG1, wenn kein Argument null oder 0 ist, sonst 0

  ARG1 < ARG2       ARG1 ist kleiner als ARG2
  ARG1 <= ARG2      ARG1 ist kleiner oder gleich ARG2
  ARG1 = ARG2       ARG1 ist gleich ARG2
  ARG1 != ARG2      ARG1 ist ungleich ARG2
  ARG1 >= ARG2      ARG1 ist größer oder gleich ARG2
  ARG1 > ARG2       ARG1 ist größer ARG2

  ARG1 + ARG2       arithmetische Summe von ARG1 und ARG2
  ARG1 - ARG2       arithmetische Differenz von ARG1 und ARG2

  ARG1 * ARG2       arithmetisches Produkt von ARG1 und ARG2
  ARG1 / ARG2       arithmetischer Quotient von ARG1 geteilt durch ARG2
  ARG1 % ARG2       arithmetischer Rest von ARG1 geteilt durch ARG2

  ZKETTE : REGEXP   verankerte Mustererkennung von REGEXP in ZKETTE

  match ZKETTE REGEXP       dasselbe wie ZEICHENKETTE : REGEXP
  
  substr ZKETTE POS LENGTH  Teilzeichenkette von ZKETTE, POS beginnt mit 1
  index ZKETTE ZEICHEN      Index in ZKETTE, wo eines der ZEICHEN auftritt,
                              sonst 0
  length ZEICHENKETTE       Länge der ZEICHENKETTE
  + TOKEN                  TOKEN als Zeichenkette interpretieren, auch wenn
                              es ein Schlüsselwort wie match oder ein
                              Operator wie / ist

```
- **sapce btw each interger and operator is needed**


### ERROR of `*` 
To handle such problem add `\` bfore `*`
```console
pi@mayer:~ $ expr 5 * 2
expr: syntax error: unexpected argument \u201e0_rUDkeFO-5GS-t5Vb.png\u201c
pi@mayer:~ $ expr 5 \* 2
10
```

## status of Execution (Ausführung)

if the command return `0` => then it quicks successfully else return postive nums => it fails
for example 
```console
pi@JianMayer:~/Desktop $ asdf
bash: asdf: Kommando nicht gefunden.
pi@JianMayer:~/Desktop $ $?
bash: 127: Kommando nicht gefunden.
```
- `0` : successful  
- `1` : unkonwn ERROR  
- `2` : not a shell command  
- `126`: the command cant not be executed  
- `127`: Cant not find the command   
- `128`: Unvalid quit argument 
- `228+x` : linux has fatal error related with signal x  
- `130`: exit by ctrl+c  

You can make your own exit code of the shell script
```bash
#!/bin/bash
...
...
exit 5
```
- or using `exit $variable`
- The range of exit code number is btw `0 ~ 255` so if there is exit `300` it would actually means `abs(256-300)=44` 

## Condition

```console
# if
if command
then
  commands
fi


# if and else
if command 
then 
  commands 
else 
  commands 
fi
```

For example 
- If User `mayer` is online then ...
```bash
#!/bin/bash 
#testing multiple commands in the then section 

testuser=mayer 
 
if grep $testuser /etc/passwd 
then 
  echo "This is my first command" 
  echo "This is my second command" 
  echo "I can even put in other commands besides echo:"
  ls -a /home/$testuser/.b* 
fi
```

### nested if else

```bash
#!/bin/bash 
#Testing nested ifs 

testuser=NoSuchUser 

if grep $testuser /etc/passwd 
then 
  echo "The user $testuser  exists on this system." 
else 
  echo "The user $testuser does not exist on this system."
  if ls -d /home/$testuser/ 
      then 
          echo "However, $testuser has a directory." 
  fi 
fi
```

you can also use `if` .. `then` ..  `elif`
```console
if command1 
then 
  commands 
elif 
  command2 
then 
  more commands 
fi
```

## `test`  

```console
variable=""
if test $variable
then
  commands
else
  commands
fi
```

or use `[]` instead of `test`  
`[` and `]` must separate with space between `condition` as the following  
```console
if[ condition ]
then
  command
fi
```

What commands `test` can handle?
1. compare values **(only integer)**
2. compare strings (`!=,<,>,-n,-z`)
3. compare files

## Operators
```diff
n1 -eq n2  
n1 -ge n2  
n1 -gt n2  
n1 -le n2  
n1 -lt n2  
n1 -ne n2  
```

```bash
#!/bin/bash
value1=10
balue2=11
if [ $value1 -eq $value2 ]
then 
  ...
else
  ...
fi
```
