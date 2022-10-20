#<p> Open mysql in command line <p> 
mysql -u root -p demo -h localhost -P 3306
#<li> -u : username</li>
#<li> -p : db name </li>
#<li> -h : host    </li>
#<li> -P : IP      </li>

#<p> Import external sql file while opening mysql </p>
#    {@link https://stackoverflow.com/questions/17666249/how-do-i-import-an-sql-file-using-the-command-line-in-mysql}
mysql -u root -p demo < /path/file.sql  
#<li> Import filename into database demo  <li>

#<p> Import external sql file in mysql system </p>
source /path/file.sql

#<p> use specific database </p>
show databases;
use databasename;

#<p> To view the complete warning message, type the following command </p> 
show warnings;

# <h1> SSH </h1>
# 查詢mysql database的密碼策略
SHOW VARIABLES LIKE 'validate_password%';
#<p> 禁止使用當前User的名稱作為密碼一部份: validate_password_check_user_name 
#</p>
#<p> 驗證password強度的dictionary file's path: validate_password_dictionary_file 
#</p>
#<p> password的最小長度 : validate_password_length 
#</p>
#<p> 密碼至少要包含的lowercasee字母x個以及uppercase字母x個 : validate_password_mixed_case_count 
#</p>
#<p> password至少要包含x個數字 : validate_password_number_count 
#</p> 
#<p> 管理者設定的密碼至少要包含的special char(特殊符號) : validate_password_special_char_count
#<p> 密碼強度檢查 : policyvalidate_password_policy   
#    有下以幾種模式
#</p>
#     <li> 0/LOW：check 長度 </li>
#     <li> 1/MEDIUM：check 長度、數字、大小寫、特殊符號 </li>
#     <li> 2/STRONG：check 長度、數字、大小寫、特殊符號 </li>
# <p> For example <p>
# 更改密碼策略為LOW
set global validate_password_policy=0;
# 更改密碼長度 最小長度為4
set global validate_password_length=4;

# Allow IP address <pre> 192.168.188.106 </pre> root user to access the wabg database's tables
# Identify/Authenticate this user <pre> root@192.168.188.106 </pre> with password 123456 
grant all PRIVILEGES on wabg.* to  root@'192.168.188.106' identified by '123456' WITH GRANT OPTION;
# Allow MYUSER to access the any database's tables authenticate with password <pre> mypassword </pre>
grant all PRIVILEGES on *.* to  'MYUSER'@'%'  identified by 'mypassword' WITH GRANT OPTION;
# flush the configuration 
flush privileges;

