# Cluster


References
- [redis cluster的hash slot算法和一致性 hash 算法、普通hash算法的介绍](https://www.cnblogs.com/mrmirror/p/13665018.html)
- [](https://ithelp.ithome.com.tw/articles/10195191)
- [wsl中redis Cluster](https://www.jianshu.com/p/7fe101dba5d0)
- [AOF file](https://blog.csdn.net/kfengqingyangk/article/details/53454309)

## redis.conf

default : `/etc/redis/redis.conf`

## Create Redis Cluster

```bash
# Create Cluster fold
mkdir cluster-test
cd cluster-test

# at least 6 nodes
# 3 for masters(7001 7002 7003)
# 3 for slaves (7003 7004 7005)
mkdir 7000 7001 7002 7003 7004 7005
```

Create `redis.conf` for each node

for example, port 7000 would be
```python
port 7000
# pidfile /var/run/redis_7000.pid
# logfile /var/log/redis_7000.log
#dir /var/lib/redis/7000
cluster-enabled yes
cluster-config-file nodes-7000.conf
cluster-node-timeout 5000
appendonly yes

# appendfilename "xxxx.aof"
```

## Accessing

```bash
# accessing redis
redis-cli -h 127.0.0.1 -p 7000 
127.0.0.1:7000> set hello world
OK
127.0.0.1:7000> get hello
"world"
127.0.0.1:7000> exit

# accessing slave of 7000 
redis-cli -c -p 7004
```


## Error Record

- [Redis Server Cluster Not Working](https://stackoverflow.com/questions/37206993/redis-server-cluster-not-working)

```bash
redis-server create --replicas 1 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005
```
Error
>>> `Creating cluster [ERR] Sorry, can't connect to node 127.0.0.1:7000`

```bash
$redis-cli -p 7000
127.0.0.1:7000> flushall
127.0.0.1:7000> cluster reset
127.0.0.1:7000> exit
```