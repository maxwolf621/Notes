############################################################################# 
#[reference](https://opensource.com/article/19/7/create-pull-request-github)#
#############################################################################

#########################
#Downloading the Project# 
#########################

#**
# git clone https://github.com/<YourUserName>/<Your_Downloading_ProjectName_From_Github>
git clone https://github.com/maxwolf621/OauthUser


######################################################################
# Once the repo is cloned, you need to do two things to creat a Fork #
######################################################################

# 1. Create a new branch by issuing the command: 
git checkout -b new_branch
# Now you are in new_branch

# 2. Create a new remote for the `upstream repo` with the command:
#    In this case, "upstream repo" refers to the original repo you created your fork from.
git remote add upstream https://github.com/maxwolf621/OauthUser
#----Now you can make changes to the code.--- 

#################################################################################################################
# delete file and push it to github                                                                             #
# [reference](https://stackoverflow.com/questions/2047465/how-can-i-delete-a-file-from-a-git-repository/2047477)#
#################################################################################################################


#* To remove the file from the Git repository and the filesystem, use:
git rm fileName.filetype
git commit -m "remove"
git push origin branch_name

# To remove the file only from the Git repository 
# and not remove it from the filesystem, use `--cached` (Clear Index)
git rm --cached fileName.filetype
git commit -m "remove"

# And to push changes to remote repo
git push origin branch_name


#################################################################################################################################################
# IF accidently remove the files                                                                                                                #
# [reference](Note that git reset --hard HEAD destroys any useful changes you have made in parent directories of the current working directory.)#
#################################################################################################################################################

it reset HEAD
#* Should do it. If you don't have any uncommitted changes that you care about, then
git reset --hard HEAD  # this should forcibly reset everything to your last commit. 

#* Coz `git reset --hard HEAD` destroys any useful changes you have made in parent directories of the current working directory.
#* If you do have uncommitted changes, but the first command doesn't work, then save your uncommitted changes with git stash:
git stash

git reset --hard HEAD
git stash pop
