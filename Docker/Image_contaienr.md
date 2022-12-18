# Image & container

- [Image \& container](#image--container)
  - [Image](#image)
  - [Container](#container)
  
Reference
[Docker 基本教學](https://ithelp.ithome.com.tw/articles/10199339)


## Image

```bash
#             image
docker search centos

#           IMAGE
docker pull centos
docker push

# lIST IMAGES
$ docker images
REPOSITORY                       TAG           IMAGE ID       CREATED        SIZE     
userName/hybris-db               latest        e340f074eb5e   7 weeks ago    5.16GB   
userName/hybris-xyo              latest        0eac2688b7c7   7 weeks ago    3.14GB   
mcr.microsoft.com/mssql/server   2019-latest   4885f6112d74   3 months ago   1.47GB


#      移除 Image-ID , rmi means remove image
docker rmi 615cb40d5d19


docker build -t member:1 .
docker login docker.okborn.com
```


## Container


```bash
# 啟動container
# t : pseudo-tty
# d : execution in background
# -name : container name
# -p : port
#      執行 輸入輸出  image  使用linux terminal bin/bash
docker run  -it      centos /bin/bash # if centos not exist then pull first

# create new container and enters
docker exec -it  container-id /bin/bash

#            container-ID
docker start a12345678
docker stop  a12345678

# list containers
docker -ps a
CONTAINER ID   IMAGE                                        COMMAND                  CREATED      STATUS                    PORTS     NAMES    
0e329df98681   username/hybris-db-2208:latest               "/opt/mssql/bin/perm…"   2 days ago   Exited (0) 2 days ago               hybirsdb                    
a3f503da3bf0   username/hybris-2208:latest                  "bash"                   2 days ago   Exited (137) 2 days ago             eloquent_cohen               d10f60a9bdd2   username/hybris-2208:latest                  "bash"                   2 days ago   Exited (0) 2 days ago               hybris-exitImmediately       8b70e1f4a808   mcr.microsoft.com/mssql/server:2019-latest   "/opt/mssql/bin/perm…"   2 days ago   Exited (0) 2 days ago               test1808   
#-- if container's state is running the column ports will 
#-- show up the specific port

# log of the container
docker -logs -f a12345678
```

