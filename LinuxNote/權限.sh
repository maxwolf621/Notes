
# create a .txt file 
vim abc.txt

# set up mod 
# chmod user_who_owns_this_file|group_for_members_of_the_file_group|other filename
# read : 4
# write : 2
# execute : 1
chmod 000 abc.txt
# group can r-ead w-rite e-x-ecute
chmod 070 abc.txt

## change owner
chown user:group filename

