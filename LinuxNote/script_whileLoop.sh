 #! /bin/bash
 
 # read from file
 file=/home/pi/Desktop/whileloop.txt
 while IFS= read -r line #read each line in $file
 do
     echo $line
 done < "$file" #cmmand < file (execute the file via command)  
 
# loop for Welcome for 5 times 

n=1
while (( $n <= 5 )) # instead of [ $n lt 5 ]
do
	echo "Welcome $n times."
	n=$(( n+1 ))	
done

# Read File with Fields
file=/etc/passwd
# set field delimiter to : 
# read all 7 fields into 7 vars 
while IFS=: read -r user enpass uid gid desc home shell
do
    # only display if UID >= 500 
	  (( $uid -ge 500 )) && echo "User: $user ($uid) assigned \"$home\" home directory with $shell shell."
done < "$file"

 
 
