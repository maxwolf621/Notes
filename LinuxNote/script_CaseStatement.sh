#! /bin/bash
read -p "Are you 21 or over? Y/N " ANSWER 
case "$ANSWER" in 
 [yY] | [yY][eE][sS]) # enter y or Y or Yes, yes, yEs ...  
   echo "You can have a beer :)" 
   ;; 
 [nN] | [nN][oO])  
   echo "Sorry, no drinking" 
   ;; 
 *) 
   echo "Please enter y/yes or n/no" 
   ;; 
esac
