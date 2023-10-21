# SpringBoot 3.x 使用示例说明文档

[![build status](https://img.shields.io/badge/build-3.0-green)]()
[![jdk](https://img.shields.io/badge/jdk-17-green)]()
[![spring--boot](https://img.shields.io/badge/spring--boot-3.1.5-green)]()

## <span id="desc"/>简要说明
使用的是springboot3.x

* spring官网地址：https://spring.io
* spring项目快速构建：https://start.spring.io
* springboot GitHub地址：https://github.com/spring-projects/spring-boot
* springboot官方文档：https://spring.io/guides/gs/spring-boot/

项目包说明:
```
|---parent-custom-demo   springboot启动类，使用自己的父级依赖后引入spring boot的父级依赖
|---parent-spring-demo   springboot启动类，使用使用spring-boot-starter-parent作为父级依赖

```
项目源代码： https://github.com/vcharfred/spring-demo.git

---

## pom父级依赖使用说明
通过spring boot 提供父级的maven依赖可以用很方便的帮助我们管理依赖的jar包版本；有2种方式，分别是直接将spring-boot提供的作为父级，还有一种是我们有自己的父级依赖。maven依赖配置如下：

* 使用spring-boot的作为父级依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <!-- 从Spring Boot导入依赖关系管理 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.5</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>top.vchar</groupId>
    <artifactId>parent-spring-demo</artifactId>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!--spring boot web 包-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!--spring boot 打包工具-->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

* 使用自己的父级依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>top.vchar.demo.spring</groupId>
        <artifactId>spring-demo</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <groupId>top.vchar</groupId>
    <artifactId>parent-custom-demo</artifactId>
    <packaging>jar</packaging>
    <description>使用自己的作为父级</description>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <!-- 从Spring Boot导入依赖关系管理 -->
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>3.1.5</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <dependencies>
        <!--spring boot web 包-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <!--spring boot 打包工具-->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```
