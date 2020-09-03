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

### 相关注解说明

#### @EnableAutoConfiguration

这个注解代表了spring boot中的核心功能，auto configuration的核心思想，其实就是只要我们引入一个starter类的依赖，自动会根据引入了什么依赖然后来判断要干什么事情，接着就自动完成所有需要的配置；
比如说spring-boot-starter-web，引入了这个依赖然后同时启用了auto configuration，此时就会根据引入了starter-web这个依赖，spring认为要开发一个web系统，
此时就会自动进行auto configuration，完成web系统需要的所有的配置；包括了所有依赖包的引入，spring mvc的配置，web.xml的配置，spring mvc+spring的整合，tomcat的配置；
auto configuration要实现的就是，尽量的自动按照约定搞定一些事情，不需要我们去手动大量的配置xml。

#### @RestController

它就是一个spring mvc的注解，@RestController就表明说，这个是一个spring mvc的controller，
@RestController的意思是说，就仅仅提供RESTful接口，返回结果给浏览器，不会走传统读渲染模板视图页面。

#### @Configuration 

该注解是可以用来替代XML文件。

以前我们配置bean时，都是写在applicationContext.xml文件中的。

有了这个注解后，我们就可以编写一个类在其上面加上该注解。即配置类。

在配置类中可以在方法上加@Bean注解定义其中的Bean

而且通常比较好的实践是，就将用来放main方法的Application类，作为Configuration类，所以一般会给Application类加@Configuration注解
 
如果不想将所有的配置都放在Application类中，也可以使用@Import注解将其他的Configuration类引入进来，或者是依靠@ComponentScan注解自动扫描其他的Configuration类
 
即使一定要使用一个xml配置文件，建议是用@ImportResource来导入一个xml配置文件

#### @ComponentScan

启用自动扫描所有的spring bean，同时搭配好@Autowired注解来进行自动装配。将Application类放在最顶层的包结构中，那么使用了@ComponentScan之后，
就可以自动扫描和搜索到所有的spring bean，包括了：@Component、@Contorller、@Service、@Repository

#### @SpringBootApplication

大多数的spring boot项目，都会在main类上标注：@Configuration、@EnableAutoConfiguration、@ComponantScan，因为这个实在是太过于常见了，
所以spring boot提供了一个@SpringBootApplication注解，这个注解就相当于是上面3个注解的综合。
实际上在开发的时候，一般在main类上加一个@SpringBootApplication注解即可
