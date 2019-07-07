# Spring 全家桶学习笔记
* spring官网地址：https://spring.io
* spring项目快速构建：https://start.spring.io

## 一、springboot
## 1、 简单Demo
### 1.1 添加maven依赖

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
    
        <!--首先依赖spring boot 父级maven 包-->
        <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>2.1.6.RELEASE</version>
        </parent>
    
        <artifactId>springboot-demo2</artifactId>
        <groupId>top.vchar.demo.spring</groupId>
        <version>1.0-SNAPSHOT</version>
        <packaging>jar</packaging>
        <description>使用springboot作为父级</description>
    
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
或
>当项目中有自己的父级依赖时，就不能使用spring boot的父级依赖了,则使用下面这种

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <parent>
            <artifactId>spring-demo</artifactId>
            <groupId>top.vchar.demo.spring</groupId>
            <version>1.0-SNAPSHOT</version>
        </parent>
        <modelVersion>4.0.0</modelVersion>
    
        <artifactId>springboot-demo1</artifactId>
        <packaging>jar</packaging>
        <description>使用自己的作为父级</description>
    
        <dependencyManagement>
            <dependencies>
                <dependency>
                    <!-- 从Spring Boot导入依赖关系管理 -->
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-dependencies</artifactId>
                    <version>2.1.6.RELEASE</version>
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
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </build>
    </project>
### 1.2 编写启动类

    package top.vchar.demo.spring;
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    /**
     * <p> springboot启动类，使用@SpringBootApplication注入相关配置 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/7 23:14
     */
    @SpringBootApplication
    @RestController
    public class StartDemo1Application {
    
        public static void main(String[] args){
            SpringApplication.run(StartDemo1Application.class);
        }
    
        @RequestMapping("/")
        public String home(){
            return "hello word";
        }
    
    }
    
或(推荐使用上面那种)

    package top.vchar.demo.spring;
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    /**
     * <p>springboot启动类，不使用@SpringBootApplication注入相关配置; 建议使用@SpringBootApplication，它已经包含这些注解了 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/7 23:17
     */
    @RestController //相当于Controller+ResponseBody;即自动是ajax的请求
    @EnableAutoConfiguration //spring 会自动装配相关的配置,这个是必须有的
    @ComponentScan //根据spring的相关注解注入bean
    public class StartDemo2Application {
    
        public static void main(String[] args){
            SpringApplication.run(StartDemo1Application.class);
        }
    
        @RequestMapping("/demo2")
        public String home(){
            return "hello word demo2";
        }
    
    }    
    
>@EnableAutoConfiguration注解主要用于告诉spring boot根据当前引用的配置和jar包，自动启用相关的配置。
 这个注解只能扫描到它所属的类的相关注解配置。因此需要添加@ComponentScan注解。当不用注入过多的信息时会使用到这种方式

> @SpringBootApplication包含了EnableAutoConfiguration；更多的配置可以查看源码

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan(
        excludeFilters = {@Filter(
        type = FilterType.CUSTOM,
        classes = {TypeExcludeFilter.class}
    ), @Filter(
        type = FilterType.CUSTOM,
        classes = {AutoConfigurationExcludeFilter.class}
    )}
    )
    public @interface SpringBootApplication {
    
        //排除自启动项
        @AliasFor(annotation = EnableAutoConfiguration.class)
        Class<?>[] exclude() default {};
        
        //排除自动启动的beanName
        @AliasFor(annotation = EnableAutoConfiguration.class)
        String[] excludeName() default {};
    
        //扫描包
        @AliasFor(annotation = ComponentScan.class,attribute = "basePackages")
        String[] scanBasePackages() default {};
    
        //扫描类
        @AliasFor(annotation = ComponentScan.class,attribute = "basePackageClasses")
        Class<?>[] scanBasePackageClasses() default {};
    }  
运行main方法即可，访问 http://localhost:8080/  
  
      

## 2、      