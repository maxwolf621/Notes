###### tags: `Docker`

# Build Docker Image from Container


https://morioh.com/p/d8d9e7732952  
https://medium.com/bright-days/basic-docker-image-dockerfile-sql-server-with-custom-prefill-db-script-8f12f197867a  

```bash
sudo docker container ps

# it shows
CONTAINER ID   IMAGE                                        COMMAND                  CREATED        STATUS       PORTS                                       NAMES
8e5d97559e24   mcr.microsoft.com/mssql/server:2019-latest   "/opt/mssql/bin/perm…"   23 hours ago   Up 2 hours   0.0.0.0:1433->1433/tcp, :::1433->1433/tcp   db
```

`commit` create New Docker Image 
```bash=
sudo docker commit 8e5d97559e24

# if commit successfully then
sha256:12312reoguwejf9erjejbioeiuhew9pgj384uwit3479yju249rj
```

docker images
```bash=
sudo docker images
REPOSITORY                       TAG           IMAGE ID       CREATED         SIZE
<none>                           <none>        4ddee1f68792   4 minutes ago   3.27GB
````

docker tag the image
```bash=
#          image-id     repository          : version
docker tag 4ddee1f68792 maxwolf621/docke-sql:initialD

# if execute successfully
sudo docker images
REPOSITORY                       TAG           IMAGE ID       CREATED         SIZE
maxwolf621/docke-sql             initialD      4ddee1f68792   8 minutes ago   3.27GB
```

better way to skip `docker tag` step
```bash
docker commit <container_id_or_name> <image-name | repository> :<version>
docker commit 8e5d97559e24           maxwolf621/docke-sql      :initialD
```

push the docker image
```bash=
docker login --username=yourusername
$ Password: <Enter you password>

#           repository             :version
docker push yourusername/docke-sql:initialD
```
![](https://i.imgur.com/QwY5UWT.png)
