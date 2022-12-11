# Hibernate XML
- [Hibernate XML](#hibernate-xml)
  - [Database Configuration And ORM](#database-configuration-and-orm)
  - [Model and Database XML Configuration](#model-and-database-xml-configuration)
  - [XML files configuration in JAVA's Configuration Class](#xml-files-configuration-in-javas-configuration-class)
  - [Dynamically update hibernate configuration at runtime](#dynamically-update-hibernate-configuration-at-runtime)

## Database Configuration And ORM

`hibernate.cfg.xml` is default configuration xml name created under the `src/java/resources`

```xml
<hibernate-configuration>      
  <session-factory>
    <!-- Database connection setUp -->
    <!--
        hibernate.show_sql = true
        hibernate.format_sql = true
        hibernate.dialect = org.hibernate.dialect.MySQLDialect 
        hibernate.connection.driver_class = com.mysql.jdbc.Driver 
        hibernate.connection.url = jdbc:mysql://localhost/demo
        hibernate.connection.username = caterpillar 
        hibernate.connection.password = 123456
    -->
    <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
    <property name="connection.url">jdbc:mysql://localhost:3306/xmlpractice</property>
    <property name="connection.username">root</property>
    <property name="connection.password">P@ssw0rd</property>
    
    <!-- Console show SQL --> 
    <property name="show_sql">true</property> 
    <!-- format SQL syntax for viewing-->
    <property name="format_sql">true</property>


    <!-- 
        spring.jpa.hibernate.ddl-auto=
        [create | create-drop | update | validate | none ]
    -->
    <property name="hibernate.hbm2ddl.auto">create</property>   
    <!-- 
        dialect : [Oracle8iDialect | MySQL5InnoDBDialect] 
    -->
    <property name="dialect">org.hibernate.dialect.MySQL5Dialect</property>
        
    <!-- 
        Mapping entities (class) 
    -->
    <mapping class="net.example.model.Book" />
    <mapping class="net.example.model.Product" />
    <mapping resource="../model/User.hbm.xml">
     
  </session-factory>
</hibernate-configuration>
```
- The `<mapping>` element specifies a Java model entity class needs to be mapped to table in database

## Model and Database XML Configuration

Map Relationship between Entity and Database's Table

`ModelName.hbm.xml`
```xml
<?xml version="1.0" encoding="utf-8"?> 
<!DOCTYPE hibernate-mapping 
 PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN" 
 "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd"> 

<hibernate-mapping> 
    <class name="[package.model.entityName]" table="[database_table_name]"> 
        <!-- primary key -->
        <id name="id" column="id"> 
            <generator class="native"/> 
        </id> 

        <property name="[field_name_in_model]" column="[column_name_in_database_table]"/> 
        
        </class> 
</hibernate-mapping>
```

For example, Create hibernate-mapping `User.hbm.xml` and add it in hibernate-configuration
```xml
<hibernate-mapping> 
    <class name="com.model.User" table="T_USER"> 
        <id name="id" column="id"> 
            <generator class="native"/> 
        </id> 
        <property name="name" column="name"/> 
        <property name="age" column="age"/> 
    </class> 
</hibernate-mapping>

<hibernate-configuration> 
    <session-factory> 
        <!--
            Database setup  
        -->

        <!-- 
            Map Entity and Database
        -->
        <mapping resource="com/model/User.hbm.xml"/> 
    </session-factory> 
</hibernate-configuration> 
```

## XML files configuration in JAVA's Configuration Class

1. Configure XML-file Configuration via `Configuration()`
2. `Configuration#buildSessionFactory()` : A Thread Safe Session Factory for this XML Configuration

```java
Configuration config = new Configuration();
 
Configuration config = new Configuration()
                            .configure()
                            .addResource() // add XML file

SessionFactory sessionFactory = config.buildSessionFactory();
```


## Dynamically update hibernate configuration at runtime

```java
Configuration config = new Configuration();
// database setUp via Java 
config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/databaseName");
config.setProperty("hibernate.connection.username", "user_Name");
config.setProperty("hibernate.connection.password", "password");
config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
// find annotation entity class
config.addAnnotatedClass(ModelClass1.class);
config.addAnnotatedClass(ModelClass2.class);
config.addAnnotatedClass(ModelClass3.class);
// find xxx.hbm.xml
config.addClass(ModelClass1.class);
config.addClass(ModelClass2.class);
config.addClass(ModelClass3.class);

try {
    SessionFactory sessionFactory = config.buildSessionFactory();  
    Session session = sessionFactory.openSession();

    Users user = session.get(Users.class, 1);  
    
    session.close();
    sessionFactory.close();
} catch (Exception ex) {
     ex.printStackTrace();
}
```