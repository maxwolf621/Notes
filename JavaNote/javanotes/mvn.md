# maven command    
[Ref](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)     
[Definition](https://www.vogella.com/tutorials/ApacheMaven/article.html)  


## Check the mvn version
```console
mvn --version
# or 
mvn -version
# It shows
Apache Maven 3.6.0
Maven home: /usr/share/maven
Java version: 11.0.11, vendor: Raspbian, runtime: /usr/lib/jvm/java-11-openjdk-armhf
Default locale: de_DE, platform encoding: UTF-8
OS name: "linux", version: "5.10.17-v7l+", arch: "arm", family: "unix"
```

## Creating a Project
  
```console
# archetype:generate [parameters] : create a project
#   -DgroupId = Your_Java_Project_Name
#   -DartifactId= Dirctory Name
#   -DarchetypeArtifactId=maven-archetype-quickstart 
#   -DarchetypeVersion=1.4 
#   -DinteractiveMode=false
mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```

The file structure 
```
my-app
|-- pom.xml
`-- src
    |-- main
    |   `-- java
    |       `-- com
    |           `-- mycompany
    |               `-- app
    |                   `-- App.java
    `-- test
        `-- java
            `-- com
                `-- mycompany
                    `-- app
                        `-- AppTest.java
```

## Compile the source 

```console
# It resolves the dependencies, loads the JUnit artifact and built the sources. It also executes the JUnit test.
mvn compile
```

