# 註解 ^__<

[這個寫得很清楚](https://tec-blog.cuore.jp/?p=1558)   
[這個寫得也很清楚](https://snipers.biz/blog/index.php/2021/06/28/00/28/88/)   

我們從SSL更新用のクライアントツールを配置說起

### 切換至最高權限使用者
```bash
sudo su
```
- `su` : supervisor 

### 在伺服器內建立備份檔案

```bash
mkdir -p /var/fujissl/backup
```
- `/var`這個文件夾通常就是我們網站(apache)伺服器內的配置(css,php, database, ... etc)每個文件夾代表一個目錄(這些目錄有些存放`index.html` .. etc之類der)
- `mkdir`表示建立一個文件檔叫做**backup**(備份)    

`-p`的話意思就是說如果`/var/fujissl/`這個`fujissl`文件夾不存在的話則在建立`backup`的時候也一併建立,會向下面這樣   
```diff
var
'----backup
'     '------backup
'----其他文件夾
'----其他文件夾2
'----其他文件夾3
```

### 移動到資料夾`/var/fujissl/`底下

```bash
cd /var/fujissl/
```
- cd表示我們移動到位置`var/fujissl/`底下

從網站`ssl-store.jp`那邊下載一個名為`sslstore-client-tool-0.0.1.zip`
```console
wget https://www.ssl-store.jp/system/files/sslstore-client-tool-0.0.1.zip
```

### 做解壓縮(拿裡面資料夾出來)
```bash
unzip sslstore-client-tool-0.0.1.zip
```
- 解壓縮`sslstore-client-tool-0.0.1.zip`,解壓完的資料夾會是`sslstore-client-tool-0.0.1`


### 更改權限
```bash
chmod -R 0644 sslstore-client-tool-0.0.1
```
- `chmod` 更改文件權限(
- `R` : recursive 遞迴 意思就是這個資料夾底下的其他資料夾都要把權限設成( 只有該資料擁有者有讀寫(4+2)權力 其他人只有讀的權力)


### 在`/var/fujissl/sslstore-client-tool-0.0.1`中生成配置檔config
```bash
cd /var/fujissl/sslstore-client-tool-0.0.1
```
- `cd` 移動到`sslstore-client-tool-0.0.1`資料夾內

```bash
php ./bin/sslstore-client create site_ssl
```
- `php` : 利用`php`指令來生成我們的ssl配置檔案
- `.` : 指你當前所處的資料夾
- `site_ssl`我們配置檔案的檔名,自己取(或問客戶)

你按enter後他應該會跑這樣的東西出來
```
Created the /var/fujissl/sslstore-client-tool-0.0.1/.lock directory.
Created the /var/fujissl/sslstore-client-tool-0.0.1/.logs directory.
設定ファイルを作成しました。(/var/fujissl/sslstore-client-tool-0.0.1/conf/site_ssl.conf)
```

你現在`sslstore-client-tool-0.0.1`的資料夾裡面應該會多一個`conf`的資料夾裡面放了一個檔案叫做`site_ssl.conf`

### 編輯配置檔案

這時候我們要進行編輯把配置的內容丟進去
```bash
sudo vi /var/fujissl/sslstore-client-tool-0.0.1/conf/site_ssl.conf
```
- `vi` 相當於文字介面的window上的`文字文件.txt`那樣

進去之後就是先按`i`進入修改模式
把你的配置內容貼上去(貼上的快捷鍵跟一般我們按得不一樣)你就直接按右鍵然後貼上
```bash
config:
appid: APP-XXXXXXXXXXXXXXX ※SSLストアで確認
ordid: ORD-XXXXXXXXXXXXXXX ※SSLストアで確認
fqdn: www.hogehoge.com ※ドメインを設定
document_root: /var/www/html ※apacheのrootディレクトリを設定した
backup_dir: /var/fujissl/backup
private_key: /etc/pki/tls/private/ssl.pk
certificate: /etc/httpd/conf/ssl.crt/certificate.crt
ca_bundle: /etc/httpd/conf/ssl.crt/bundle.crt
```
用好後按`ESC`退出修改模式,之後按`:`屆時左下方會有欄位讓你輸入   
再輸入`wq` (進行退出以及寫入存檔), 現在你就完成`site_ssl.conf`的配置檔案了   


## 証明書の登録

```bash
~$ sudo su
~$ cd /var/fujissl/sslstore-client-tool-0.0.1
~$ php ./bin/sslstore-client regist -c site_ssl -f conf/site_ssl.conf
```
- 客戶網站全名`FDQN`例如(`https://google.com`...這種) 
  > 這裡有Domain一些簡單解釋[點我](https://its-okay.medium.com/%E6%90%9E%E6%87%82-ip-fqdn-dns-name-server-%E9%BC%A0%E5%B9%B4%E5%85%A8%E9%A6%AC%E9%90%B5%E4%BA%BA%E6%8C%91%E6%88%B0-05-aa60f45496fb)

你打完`php`那行指令後之後應該會跑出這幾行
```diff
Create /var/www/html/.well-known/pki-validation/fileauth.txt file.
Create /var/fujissl/backup/ssl.pk file.
The certificate registed was successful.
Register the following command in cron:
/usr/bin/php /var/fujissl/sslstore-client-tool-0.0.1/bin/sslstore-client autorenew -c www.hogehoge.com -f conf/site_ssl.conf
```

如果發生
```
PHP Fatal error: (E2001) Can not access the authentication URL. '(http|https)://foo.jp/.well-known/pki-validation/fileauth.txt' in /var/sslautorenew/sslstore-client-tool-0.0.1/bin/sslstore-client on line 61
```

你要去`/var`內的`hppd.config`內改一下,最簡單最危險的方式就下面那樣再`/`根目錄直接Allow from 認證商IP位址或domain位址
```
#
# Each directory to which Apache has access can be configured with respect
# to which services and features are allowed and/or disabled in that
# directory (and its subdirectories). 
#
# First, we configure the "default" to be a very restrictive set of 
# features.  
#
<Directory "/">
    Options FollowSymLinks
    AllowOverride None
    Order deny,allow
    Deny from all          
    Allow from 要取得你資訊的認證商IP位址
</Directory>
```

##  設定自動更新排程[crontab](https://blog.gtwang.org/linux/linux-crontab-cron-job-tutorial-and-examples/)  

- `crontab`這個指令簡單來說就是很像我們電腦設定自動進行更新的排程(什麼時候我們的ssl憑證需要被自動更新)
```bash
crontab -e
```
- `-e` : edit

之後就可以進入我們排程檔案裡面了
```bash
SHELL=/bin/bash
PATH=/sbin:/bin:/usr/sbin:/usr/bin
MAILTO=root
HOME=/

# For details see man 4 crontabs

# Example of job definition:
# .---------------- minute (0 - 59)
# |  .------------- hour (0 - 23)
# |  |  .---------- day of month (1 - 31)
# |  |  |  .------- month (1 - 12) OR jan,feb,mar,apr ...
# |  |  |  |  .---- day of week (0 - 6) (Sunday=0 or 7) OR sun,mon,tue,wed,thu,fri,sat
# |  |  |  |  |
# *  *  *  *  * user-name command to be executed

  0  15 *  *  * root /usr/bin/php /var/sslautorenew/sslstore-client-tool-0.0.1/bin/sslstore-client autorenew -c www.foo.jp -f /var/sslautorenew/sslstore-
client-tool-0.0.1/conf/foo.conf
```

上面其實就是這樣
`0  15 *  *  * root /usr/bin/php /var/sslautorenew/sslstore-client-tool-0.0.1/bin/sslstore-client autorenew -c 你客戶的網站FDQN -f /var/sslautorenew/sslstore-
client-tool-0.0.1/conf/我們SSL配置的檔名.conf`

