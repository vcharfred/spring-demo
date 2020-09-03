# SpringBoot2.x学习笔记

## 一、SpringBoot开始

#### 1.1 添加maven依赖：

    <!--首先依赖spring boot 父级maven 包-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.3.RELEASE</version>
    </parent>

    <dependencies>
        <!--spring boot web 包-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

或有自己项目的父级工程时，使用下面这种方式

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
     
#### 1.2 编写启动类

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    
    /**
     * <p> 启动类 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/8/4 11:45
     */
    @SpringBootApplication
    public class SpringbootStartDemoApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(SpringbootStartDemoApplication.class);
        }
    }

编写一个Controller路由    
    
    @RestController
    public class IndexController {
    
        @RequestMapping("/home")
        public String home(){
            return "spring boot2 start ok";
        }
    }   
    
直接运行main方法即可启动SpringBoot，默认使用的端口是8080; 访问：http://127.0.0.1:8080/home查看结果

