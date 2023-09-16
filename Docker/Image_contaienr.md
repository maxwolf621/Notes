# Image & container

- [Image \& container](#image--container)
  - [Image](#image)
  - [Container](#container)
  
Reference  
[Docker 基本教學](https://ithelp.ithome.com.tw/articles/10199339)   
[Container內部及外部的執行](https://joshhu.gitbooks.io/dockercommands/content/Containers/ContainersBasic.html)   

# Docker Introduction

## What is inside Docker? 
- image
    - executable file (e.g. `setup.exe`) READ_ONLY
- container
    - (executable file after installation) program.
    - Allow to `READ/WRTIE/EXECUTABLE`
- repository
    - space that stores images

Repository :arrow_right: `docker pull` :arrow_right: image :arrow_right: `docker run` :arrow_right: container :arrow_left: :arrow_right: HOST

## Useful Commands 

### OPTION for commands

:high_brightness: Enter Inside(Interactive with) the Container (for example terminal mode)
```bash 
-it 
```
- `i` : interactive 
- `t` : tty


:high_brightness: Detach
```bash 
-d
```
Run Container in Background

:high_brightness: Publish Container Port to HOST Port
```bash 
-p
# for example
# host 8080 to container 80 
-p 8080:80
```

### docker run

Run a container from the image
```bash 
# create and run a NEW container
# t : pseudo-tty
# d : execution in background
# -name : container name
# -p : port
#      執行 輸入輸出  image  使用linux terminal bin/bash
docker run [options] IMAGE  [COMMAND] [ARGS]
docker run  -it      centos /bin/bash 
```
- If image `centos` does not exist then it will pull the image first
    - It looks for the local repository first if not found, search remote repository.  


Parameter `-rm`
- Often Apply for testing
```bash 
docker run --rm --name redisContainer redis
```
- Remove the container after it exits/terminates

### docker ps

Check Information on Container
```bash 
# check all the process status
docker ps -a
```
1. `ps` : proccess status
2. `a`  : all

![](https://hackmd.io/_uploads/r1wlEg7k6.png)
- If `num` of `Exited(num)` in `Status` Column is not `0` which supposes `something wrong`  
- `CONTAINER ID` & `NAMES` Columns' data are unique

## docker rm & docker rmi

:arrow_down: Remove Specific Container
```bash 
docker rm <CONTAINER-ID> 
# or
docker rm <CONTAINER-NAME>

# FOR EXAMPLE 
#     Remove multiple containers
docker rm container_1 container_2
```

:arrow_down: Remove image in local Repository
```bash 
docker rmi <repository-name>
docker rmi <image-id>
```
- Remove the container first if the one created by the will-be removed image

## docker images

:arrow_down: Check image in local repository
```bash 
docker images <ImageName>

# FOR EXAMPLE
docker images redis
```
![](https://hackmd.io/_uploads/SJ5I-bXyT.png)
- This implies that you have image redis in local repository

### docker search

:arrow_down: Check if image can be downloaded from remote repository
```bash 
docker search <keyword>
```
![](https://hackmd.io/_uploads/ry8H1Z71T.png)


### docker create 

Create a container  
```bash 
docker create [OPTIONS] IMAGE [COMMAND] [ARG...]
# for example
#            [OPTIONS]                       IMAGE
docker create -it      --name redisContainer redis
```

### docker start & docker stop 

Start or Stop container  
```bash 
docker <start | stop> <container-id | container-name>
docker start a12345678
docker stop  a12345678
```


## Image

```bash
#             image
docker search centos
#           IMAGE
docker pull centos
docker push
```


## Container

```bash
# EXECUTE created container and enters
docker exec [options] CONTAINER [COMMAND] [ARGS]
docker exec -it  container-id /bin/bash

a3f503da3bf0   username/hybris-2208:latest                  "bash"                   2 days ago   Exited (137) 2 days ago             eloquent_cohen               d10f60a9bdd2   username/hybris-2208:latest                  "bash"                   2 days ago   Exited (0) 2 days ago               hybris-exit immediately       8b70e1f4a808   mcr.microsoft.com/mssql/server:2019-latest   "/opt/mssql/bin/perm…"   2 days ago   Exited (0) 2 days ago               test1808   
#-- If the container's state is running the column ports will 
#-- Show up the specific port

# Log of the container
docker -logs -f a12345678
```

