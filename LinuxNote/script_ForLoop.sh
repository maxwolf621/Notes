#! /bin/bash

#SIMPLE FOR LOOP
NAMES="Brad Kevin Alice Mark"
for NAME in $NAMES
 do
   echo "Hello $NAME"
done

#FOR LOOP TO RENAME FILES
FILES=$(ls *.txt)
PRE="$(date)"
for FILE in $FILES
 do
   echo "Renaming $FILE to $PRE-$FILE"
   mv $FILE $PRE-$FILE
done

# Hello Brad
# Hello Kevin
# Hello Alice
# Hello Mark
# Renaming sl-hl_ln1.txt to Fr 18. Jun 17:20:19 CST 2021-sl-hl_ln1.txt
# mv: das angegebene Ziel '2021-sl-hl_ln1.txt' ist kein Verzeichnis
# Renaming sl-ln1.txt to Fr 18. Jun 17:20:19 CST 2021-sl-ln1.txt
# mv: das angegebene Ziel '2021-sl-ln1.txt' ist kein Verzeichnis
# Renaming sl-sf_ln1.txt to Fr 18. Jun 17:20:19 CST 2021-sl-sf_ln1.txt
# mv: das angegebene Ziel '2021-sl-sf_ln1.txt' ist kein Verzeichnis
