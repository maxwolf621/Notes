 #!/bin/bash
 # *example `test` command shell script
 value1=10
 value2=20
 if [ $value1 -lt $value2 ];
 then
     echo "$value1 is less then $value2"
     exit 231
 else
     echo "$value2 is greater than $value1"
     exit 212
 fi
