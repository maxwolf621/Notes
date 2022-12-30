# JPA XML

- [JPA XML](#jpa-xml)
  - [JPA Deployment](#jpa-deployment)

Reference
- [Deploy JPA](https://openhome.cc/Gossip/EJB3Gossip/DeployJPA.html)

## JPA Deployment

部署JPA時，必須存在persistence.xml設定檔，其必須位於類別路徑中META-INF資料夾之中
在Java EE模組中，Persistence Unit會有個獨一無二名稱，容器會以該名稱建立`EntityManagerFactory`，並用其建立
`EntityManager`，一個`persistence.xml`中可以定義多個Persistence Unit，以名稱作為區隔。
```
Persistence.xml
+---Persistence Unit A
    + EntityManagerFactory
        +-- EntityManager
+---Persistence Unit B
    + EntityManagerFactory
        +-- EntityManager
...
+---Persistence Unit N
    + EntityManagerFactory
        +-- EntityManager            
```

