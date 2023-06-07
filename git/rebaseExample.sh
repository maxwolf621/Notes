#######################################################################
# Using command $ git push origin master it display an error message. #
#######################################################################

# ! [rejected]        main -> main (fetch first)
# error: Fehler beim Versenden einiger Referenzen nach 'https://github.com/maxwolf621/OauthUser'

#---To handle Such Problem---

git fetch origin main
git merge origin main

#IF receiv new erro : non-fast-forward then
git fetch origin master:tmp

# display like the following
# remote: Enumerating objects: 5, done.
# remote: Counting objects: 100% (5/5), done.
# remote: Compressing objects: 100% (3/3), done.
# remote: Total 3 (delta 1), reused 0 (delta 0), pack-reused 0
# Entpacke Objekte: 100% (3/3), Fertig.
# Von https://github.com/maxwolf621/OauthUser
# * [neuer Branch]    main       -> tmp
#   3d29b28..5c81650  main       -> origin/main

#-------- REBASE -----------

git rebase tmp
# display like the following
# Zun\u00e4chst wird der Branch zur\u00fcckgespult, um Ihre \u00c4nderungen darauf neu anzuwenden...
# Wende an: remove file mvn/wrapper

# push local(origin) to remote 
git push origin HEAD:main

#* Delete our tmporary branch
git branch -D tmp
