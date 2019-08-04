# Spring 全家桶学习笔记

<a href="#SpringMvc">一、SpringMvc搭建</a><br>
<a href="#Springboot">二、Springboot使用</a>

---

## <span id="desc"/>简要说明
使用的是springboot2.x

* spring官网地址：https://spring.io
* spring项目快速构建：https://start.spring.io
* springbootGitHub地址：https://github.com/spring-projects/spring-boot
* springboot官方文档：https://spring.io/guides/gs/spring-boot/

项目包说明:

    |---springboot-demo1                 springboot启动类，使用不使用spring-boot-starter-parent作为父级依赖
    |---springboot-demo2                 springboot启动类，使用使用spring-boot-starter-parent作为父级依赖
    |---springboot-demo3                 springboot的controller相关注解说明
    |---springboot-dev-demo              springboot 热部署
    |---springboot-test-demo             springboot 测试类demo
    |---springboot-customer-banner-demo  自定义springboot启动样式  
    |---springboot-thymeleaf-demo        使用thymeleaf做页面渲染引擎
    |---springboot-freemarker-demo       使用freemarker做页面渲染引擎
    |---springboot-filter-demo           filter、listener、interceptor的使用
    |---springboot-war-demo              springboot 打war包
    |---springboot-timer-demo            springboot 定时任务
    |---springboot-exception-demo        springboot 异常出来demo
    |---springboot-mybatis-demo          springboot 中使用mybatis
    |---springboot-redis-demo            springboot 中使用redis
    |---springboot-elasticsearch-demo    springboot 中使用elasticsearch
    |---springboot-mq-demo               springboot 中使用activemq
    |---springboot-rocketMQ-demo         springboot 中使用rocketMQ
    |---springboot-more-environment      springboot 多环境配置
    |---springboot-webflux-demo          springboot webflux响应式编程
    |---springboot-actuator-demo         springboot 监控
        
项目源代码： https://github.com/vcharfred/spring-demo.git

---

## 一、<span id="SpringMvc"/>SpringMvc
TODO 

---

## 二、<span id="Springboot"/>Springboot
### 1、 简单Demo
#### 1.1 添加maven依赖

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
#### 1.2 编写启动类

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
  
      

---
### 2、springboot的controller相关注解说明
#### 2.1 @RestController 
这个注解相比于@Controller可以让我们在返回json等数据类型时，不用再方法上加@ResponseBody注解

    package top.vchar.demo.spring.controller;
    
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    /**
     * <p> @RestController使用 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/7 23:43
     */
    @RestController
    public class DemoController {
        
        //返回String
        @RequestMapping("/")
        public String home(){
            return "hello word";
        }
        
        /**
         * 使用bean对象传参
         * @param member 用户信息
         * @return
         */
        @RequestMapping("/v1/save_user")
        public String saveUser1(Member member){
            if(null==member){
                return "save fail";
            }
            System.out.println(member.toString());
            return "save success";
        }
        
    } 
    
#### 2.2 获取POST请求的body信息 @RequestBody 
注意事项：
    
    a.需要指定http头为Content-Type为application/json;
    b.必须使用body传送参数


    /**
     * 使用bean对象传参
     * 注意：a.需要指定http头为Content-Type为application/json
     *      b.使用body传送参数
     * @param member 用户信息
     * @return
     */
    @RequestMapping("/v2/save_user")
    public String saveUser2(@RequestBody Member member){
        if(null==member){
            return "save fail";
        }
        System.out.println(member.toString());
        return "save success";
    }    
  
#### 2.3 @GetMapping和@PostMapping
    package top.vchar.demo.spring.controller;
    
    import org.springframework.web.bind.annotation.*;
    
    /**
     * <p> 只允许GET请求 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/7 23:48
     */
    @RestController
    public class GetController {
        /**
         * 只允许GET请求
         */
        @RequestMapping(path = "/get/v1/user/{id}", method = RequestMethod.GET)
        public String findUserById1(@PathVariable("id") String uid){
            if("123456".equals(uid)){
                return "find it";
            }
            return "no this user";
        }
    
        /**
         * 只允许GET请求
         */
        @GetMapping(path = "/get/v2/user/{id}")
        public String findUserById2(@PathVariable("id") String uid){
            if("123456".equals(uid)){
                return "find it";
            }
            return "no this user";
        }
    }    
>@PathVariable可以将使其接收到url上动态拼接的参数，默认是必传

其他类似的

    @GetMapping(path = "/get/v2/user/{id}")==@RequestMapping(path = "/get/v1/user/{id}", method = RequestMethod.GET)
    @PostMapping(path = "/post/v2/user/{id}")==@RequestMapping(path = "/post/v1/user/{id}", method = RequestMethod.POST)
    @PutMapping(path = "/put/v2/user/{id}")==@RequestMapping(path = "/put/v1/user/{id}", method = RequestMethod.PUT)
    ...
    
#### 2.4 @RequestHeader获取http头信息
使用这个注解可以获取请求头信息

    /**
     * 获取http头信息
     */
    @RequestMapping("/get_header")
    public String saveUser3(@RequestHeader(value = "access_token", required = false) String accessToken){
        return accessToken;
    }

   
### 3、springboot官方推荐的目录规范

    src/main/java：存放代码
    src/main/resources
        static: 存放静态文件，比如 css、js、image, （访问方式 http://localhost:8080/js/main.js）
        templates:存放静态页面jsp,html,tpl
        config:存放配置文件
        resources:
>同个文件的加载顺序,静态资源文件
 	 Spring Boot 默认会挨个从
 	 META/resources > resources > static > public  里面找是否存在相应的资源，如果有则直接返回。
>可以配置resources.static-locations修改，多个使用英文逗号分隔 	         
* 页面模板引擎 Thymeleaf加入如下maven依赖

        <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

* 页面模板引擎 freemarker加入如下maven依赖

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>      
### 4、热部署
加入如下依赖：

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
    
* 指定文件不进行热部署 spring.devtools.restart.exclude=static/**,public/**
* 手工触发重启 spring.devtools.restart.trigger-file=trigger.txt 改代码不重启，通过一个文本去控制,里面可以填写版本号等配置
### 5、注入配置文件    
#### 5.1 直接注入属性值
* 首先需要在类上加上 @PropertySource({"classpath:resource.properties"})指定需要加载的配置文件
* 定义一个属性值，在上面加上@Value注解

        @RestController
        @PropertySource({"classpath:resource.properties"})
        public class AutoConfigController {
        
            @Value("${soft.version}")
            private String version;
        
            @GetMapping("/get/soft_version")
            public String getSoftVersion(){
                System.out.println(version);
                return version;
            }
        }
#### 5.2 注入实体类配置文件
直接注入

    @Configuration
    @PropertySource(value="classpath:resource.properties")
    public class ServerConfig2 {
    
        @Value("${soft.domain}")
        private String domain;
        @Value("${soft.name}")
        private String name;
    
        public String getDomain() {
            return domain;
        }
    
        public void setDomain(String domain) {
            this.domain = domain;
        }
    
        public String getSoftname() {
            return name;
        }
    
        public void setSoftname(String softname) {
            this.name = softname;
        }
    
        @Override
        public String toString() {
            return "ServerConfig{" +
                    "domain='" + domain + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    
使用前缀注入;需要引入如下依赖

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>

列子：

    @Configuration
    @PropertySource(value="classpath:resource.properties")
    @ConfigurationProperties(prefix = "soft")
    public class ServerConfig {
    
        private String domain;
    
        private String name;
    
        public String getDomain() {
            return domain;
        }
    
        public void setDomain(String domain) {
            this.domain = domain;
        }
    
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ServerConfig{" +
                    "domain='" + domain + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }  
    
    
    
      
### 6、springboot测试
#### 6.1 单元测试
引入如下依赖

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

列子：

    @RunWith(SpringRunner.class)  //底层用junit  SpringJUnit4ClassRunner
    @SpringBootTest(classes={StartApplication.class})//启动整个springboot工程
    public class TestDemo {
        
        //测试方法运行前执行
        @Before
        public void beforeTest(){
            System.out.println("=======before====");
        }
    
        @Test
        public void demo(){
            System.out.println("========OK=====");
            TestCase.assertEquals(1, 1);
        }
    
        //测试方法运行后执行
        @After
        public void afterTest(){
            System.out.println("=======after====");
        }
    }   

#### 6.2 MockMvc测试，模拟请求

    @RunWith(SpringRunner.class)
    @SpringBootTest(classes = StartApplication.class)
    @AutoConfigureMockMvc
    public class MockMvcTest {
    
        @Autowired
        private MockMvc mockMvc;
    
        @Test
        public void apiGETest() throws Exception {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/get/version"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
    
            System.out.println(mvcResult.getResponse().getContentAsString());
        }
    
    } 
### 7、使用servlet3.0注解配置Filter
主要用于权限控制、用户登录等; Filter优先级

    Ordered.HIGHEST_PRECEDENCE
    Ordered.LOWEST_PRECEDENCE
    低位值意味着更高的优先级 Higher values are interpreted as lower priority
    自定义Filter，避免和默认的Filter优先级一样，不然会冲突
SpringBoot启动默认加载的Filter 
    characterEncodingFilter
    hiddenHttpMethodFilter
    httpPutFormContentFilter
    requestContextFilter    
#### 7.1 使用servlet3.0注解配置自定义Filter
* 使用Servlet3.0的注解进行配置
* 启动类里面增加 @ServletComponentScan，进行扫描
* 新建一个Filter类，implements Filter，并实现对应的接口
* @WebFilter 标记一个类为filter，被spring进行扫描；urlPatterns：拦截规则，支持正则
* 控制chain.doFilter的方法的调用，来实现是否通过放行；不放行，web应用resp.sendRedirect("/index.html");


    import javax.servlet.*;
    import javax.servlet.annotation.WebFilter;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    
    /**
     * <p> 过滤器 </p>
     *
     *  拦截以api开头的请求
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/17 21:09
     */
    @WebFilter(urlPatterns = "/api/*", filterName = "loginFilter")
    public class LoginFilter implements Filter {
    
        //初始化: 容器加载时调用
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            System.out.println("初始化 LoginFilter");
        }
    
        //请求被拦截的时候进行调用
        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            System.out.println("执行 LoginFilter");
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
    
            String token = request.getParameter("token");
            if("1234".equals(token)){
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                System.out.println("LoginFilter 拦截请求");
                response.sendRedirect("/no_auth");
            }
    
        }
    
        //容器被销毁时调用
        @Override
        public void destroy() {
            System.out.println("销毁 LoginFilter");
        }
    }
### 8、 使用servlet3.0注解配置自定义原生的Servlet
现在都是使用基本都是spring等框架，都是自动将参数处理后直接传入给我们的controller；

    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    
    /**
     * <p> 自定义Servlet </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/17 21:32
     */
    @WebServlet(name = "userServlet", urlPatterns = "/v1/*")
    public class UserServlet extends HttpServlet {
    
        @Override
        public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println("执行userServlet Get");
            resp.getWriter().println("custom servlet");
            resp.getWriter().flush();
            resp.getWriter().close();
        }
    
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println("执行userServlet Post");
            this.doGet(req, resp);
        }
    }
### 9、 使用servlet3.0注解配置自定义Listener
执行顺序：

    Listener.requestInitialized-->>Controller--->>Listener.requestDestroyed

代码实现：

    import javax.servlet.ServletRequestEvent;
    import javax.servlet.ServletRequestListener;
    import javax.servlet.annotation.WebListener;
    
    /**
     * <p> 自定义ServletRequestListener监听器 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/17 21:42
     */
    @WebListener
    public class RequestListener implements ServletRequestListener {
        
        @Override
        public void requestInitialized(ServletRequestEvent sre) {
            System.out.println("=====请求初始化 RequestListener====");
        }
    
        @Override
        public void requestDestroyed(ServletRequestEvent sre) {
            System.out.println("====请求销毁 RequestListener=====");
        }
    }
    
### 10、SpringBoot拦截器Interceptor
2.x以后注册Interceptor

    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
    
    /**
     * <p> 2.x以后注册Interceptor </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/17 21:59
     */
    @Configuration
    public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/v2/*");
            WebMvcConfigurer.super.addInterceptors(registry);
        }
    }
   
2.x以前注册Interceptor
          
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
    
    /**
     * <p> 2.x以前注册Interceptor  </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/17 22:00
     */
    @Configuration
    public class CustomOldWebMvcConfigurer extends WebMvcConfigurerAdapter {
    
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/v2/*");
    
        }
    }    
    
拦截器实现
 
    import org.springframework.web.servlet.HandlerInterceptor;
    import org.springframework.web.servlet.ModelAndView;
    
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    
    /**
     * <p> 拦截器 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/17 22:09
     */
    public class LoginInterceptor implements HandlerInterceptor {
    
        //进入controller方法前
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            System.out.println("=====LoginInterceptor preHandle======");
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
    
        //调用完Controller之后，视图渲染之前，如果控制器Controller出现了异常，则不会执行此方法
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            System.out.println("=====LoginInterceptor postHandle======");
            HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        }
    
        //整个完成后，通常用于资源清理
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            System.out.println("=====LoginInterceptor afterCompletion======");
            HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        }
    } 
>Filter是基于函数回调 doFilter()，而Interceptor则是基于AOP思想
>Filter在只在Servlet前后起作用，而Interceptor够深入到方法前后、异常抛出前后等
>依赖于Servlet容器即web应用中，而Interceptor不依赖于Servlet容器所以可以运行在多种环境。
>在接口调用的生命周期里，Interceptor可以被多次调用，而Filter只能在容器初始化时调用一次。
>Filter和Interceptor的执行顺序
>过滤前->拦截前->action执行->拦截后->过滤后  
### 11、使用freemarker做页面渲染引擎
添加依赖

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-freemarker</artifactId>
    </dependency>

配置

    spring:
      freemarker:
        # 编码
        charset: UTF-8
        # 本地开发关闭缓存
        cache: false
        content-type: text/html
        # 文件后缀
        suffix: .ftl
        # 文件目录，这个是默认的
        template-loader-path: classpath:/templates/
    
### 12、使用thymeleaf做页面渲染引擎
添加依赖

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

配置
    
    spring:
      thymeleaf:
        # 本地开发关闭缓存
        cache: false
        encoding: UTF-8
        suffix: .html
        prefix: classpath:/templates/  
        
### 13、mybatis配置
添加maven依赖

    <!--mybatis-boot-->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>2.1.0</version>
    </dependency>
    <!--MySQL的JDBC驱动包-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    <!--第三方数据源druid-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>1.1.10</version>
    </dependency>
    
或使用druid-spring-boot-starter可以简化druid配置

        <!--这个可以简化druid配置-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
在启动类上加入 @MapperScan("包名")设置扫描dao的包；如：

    @MapperScan("top.vchar.demo.spring.mapper")
    # 有多个不同路径时
    @MapperScan(value = {"top.vchar.demo.spring.mapper", "com.xx.dao"})
        
dao使用示例，不用再在xml文件中写sql了, 直接在方法上写：

public interface DemoMapper {
    
    //keyProperty--Java对象属性，keyColumn--数据库字段
    @Insert("INSERT INTO user(name, age) VALUES(#{name}, #{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User user);  

>推荐使用#{}； 不建议使用${},因为存在sql注入风险    

开启事务：在service的实现方法上加入如下注解：

    @Transactional(propagation = Propagation.REQUIRED)//开启事务，设置事务级别(若不配置propagation则使用数据库默认的级别)
    public int addUser(User user) {
        //TODO something
 
    }    
### 14、redis使用
添加依赖：

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
 
配置：

    spring:
      redis:
        # 链接地址
        host: 127.0.0.1
        # 认证密码
        password: 123456
        # 数据库序号
        database: 0
        # 链接超时，单位毫秒
        timeout: 2000
        # 这个比jedis性能更好
        lettuce:
          pool:
            # 池中最大活跃数，-1表示不限制
            max-active: 10
            # 池中最小空闲链接
            min-idle: 1
            # 池中最大空闲链接
            max-idle: 8
            # 最大等待时间，单位毫秒，-1表示不限
            max-wait: 1000
                
简单使用：
     
    @RestController
    public class IndexController {
         @Autowired
         private StringRedisTemplate redisTemplate;
     
         @GetMapping("/redis_test")
         public String redisTest() {
             String val = redisTemplate.opsForValue().get("test:demo");
             //key建议都使用一个前缀加上 : 这样在查看redis库时会方便许多，同时也便于管理
             redisTemplate.opsForValue().set("test:demo", "this is test");
             return val;
         }
    }
### 15、springboot 使用定时任务和异步执行
在启动类上加上开启定时的注解

    @EnableScheduling 
使用示例：    

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.scheduling.annotation.Scheduled;
    import org.springframework.stereotype.Component;
    
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.concurrent.ExecutionException;
    import java.util.concurrent.Future;
    
    /**
     * <p> 定时任务 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/16 23:31
     */
    @Component
    public class TestScheduling {
    
        @Autowired
        private DmoTask dmoTask;
    
        //每隔2s执行一次
        @Scheduled(fixedDelay = 2000)//支持core表达式
        public void demo() throws ExecutionException, InterruptedException {
            //TODO 
            System.out.println("开始执行定时任务");
        }
    }
@Scheduled配置说明：

* cron 定时任务表达式 @Scheduled(cron="*/1 * * * * *") 表示每秒
    1）crontab 工具  https://tool.lu/crontab/
* fixedRate: 定时多久执行一次（上一次开始执行时间点后xx秒再次执行；）
* fixedDelay: 上一次执行结束时间点后xx秒再次执行
* fixedDelayString:  字符串形式，可以通过配置文件指定    
            
在启动类上加上开启异步执行的的注解；当某几个方法执行顺序无关联影响，又比较耗时时，则可以使用异步执行，这样也就可以提高系统速度 
    
    @EnableAsync
  
示例：异步执行该方法，并等待结果返回。

    //有这个注解的即表示异步执行方法（放在类上则类中所有方法都是异步方法），若不配做线程池，则springboot会使用默认的 SimpleThreadPoolTaskExecutor (每调用一次异步方法，都会创建一个线程去执行)
    @Async 
    public Future<List<Integer>> pullInfo(int pageIndex, int total){
        List<Integer> list = new ArrayList<>();
        do{
            List<Integer> ids = demoService.demo(pageIndex);
            if(ids.size()>0){
                list.addAll(ids);
            }
            pageIndex++;
        }while (pageIndex<=total);
        return AsyncResult.forValue(list);
    }
### 16、elasticsearch使用

查看es数据

    查看索引信息：http://localhost:9200/_cat/indices?v
    查看某个索引库结构：http://localhost:9200/blog
    查看某个对象：http://localhost:9200/{indexName}/{type}/1  
    
[elasticsearch官网地址](https://www.elastic.co/products/elasticsearch)   

#### 安装问题：
1. elasticsearch不能以root用户运行；


    #添加一个组
    groupadd -g 1024 bigdata
    # 在组中添加一个用户
    useradd -g bigdata es
    # 进入elasticsearch安装目录修改创建用户的权限
    chown -R es:bigdata .


2. 提示vm.max_map_count太小，需要修改系统配置

    
    #打开系统配置文件
    vi  /etc/sysctl.conf
在里面添加如下配置：

    vm.max_map_count=262144
3. 开启外网访问：	
		
将elasticsearch安装目录下的config目录下面elasticsearch.yml修改为 network.host: 0.0.0.0

添加maven依赖

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
    </dependency>
    
> 注意：请注意当前引入的spring-boot-starter-data-elasticsearch支持的elasticsearch的版本，若版本不一样可能会出现一些问题，不要出现大版本的不同。

配置：

    spring:
      data:
        elasticsearch:
          cluster-name: elasticsearch
          cluster-nodes: 127.0.0.1:9300
          repositories:
            enabled: true
使用：

定义文档映射：

    import org.springframework.data.elasticsearch.annotations.Document;
    
    import java.io.Serializable;
    
    /**
     * <p> 实体类（表映射）:火车站点 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/22 23:41
     */
    //indexName==db_name, type=table
    @Document(indexName = "train", type = "station")
    public class TrainStation implements Serializable {
    
        private String id;
        private String stationName;
        private int hot;
        private int priority;
        private String match;
        private String stationCode;
        //TODO GET SET method...
        
    }

定义文档访问接口    

    import org.springframework.data.elasticsearch.annotations.Document;
    import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
    import org.springframework.stereotype.Component;
    import top.vchar.demo.spring.pojo.TrainStation;
    
    /**
     * <p>  文档访问接口（相当于dao数据库访问） </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/22 23:47
     */
    @Component
    @Document(indexName = "train", type = "station", shards = 1, replicas = 0)
    public interface TrainStationRepository extends ElasticsearchRepository<TrainStation, String> {
    }    

调用：  
  
    @RestController
    public class IndexController {
  
      @Autowired
      private TrainStationRepository trainStationRepository;
        
        //保存
      @PostMapping("/save_train")
      public String esSaveDemo(String stationStr){
          if(null!=stationStr){
              TrainStation trainStation = JSONObject.parseObject(stationStr, TrainStation.class);
              if(trainStation.getId()!=null){
                  trainStationRepository.save(trainStation);
                  System.out.println("保存成功");
                  return "200";
              }
          }
          System.out.println("保持失败");//INSTANCE
          return "401";
      }
  
       //搜索
      @GetMapping("/search_train")
      public String esSearchDemo(String key){
          QueryBuilder queryBuilder = QueryBuilders.matchQuery("stationName", key);
          List<TrainStation> list = new ArrayList<>();
          Iterable<TrainStation> search = trainStationRepository.search(queryBuilder);
          search.forEach(list::add);
          return JSONObject.toJSONString(list);
      }   
### 17、activemq使用
添加maven依赖：

    <!-- 整合消息队列ActiveMQ -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-activemq</artifactId>
        </dependency>

使用的springboot2.1.x以上的使用这个连接池

    <!-- 如果配置线程池则加入: -->
        <dependency>
            <groupId>org.messaginghub</groupId>
            <artifactId>pooled-jms</artifactId>
        </dependency>
              
使用的springboot2.0.x以下的使用这个连接池 
        
        <!-- 如果配置线程池则加入: -->
        <dependency>  
            <groupId>org.apache.activemq</groupId>  
            <artifactId>activemq-pool</artifactId>  
        </dependency>
     
#### 消息队列使用--点对点模式
点对点模式：可以有多个接收方和发送方，消息会根据发送时间进行排队，先发送的先处理；每条消息只会有一个消息接收者收到。同时当没有消息接收者监听消息，那么这些消息会存储在队列里面，
直到有消息接收者启动

示例：

在springboot启动类上添加 `````@EnableJms```注解开启jms支持

    //注入队列，交给spring管理；也可以不做这个操作，只是后面发消息时每次都需要创建
    @Bean
    public Queue queue(){
        //指定队列名称
        return new ActiveMQQueue("common.queue");
    }

消息生产者：

    @Service
    public class ProducerServiceImpl implements ProducerService {
        
        @Autowired
        private JmsMessagingTemplate jmsMessagingTemplate;
        
        //注入上面注入的bean
        @Autowired
        private Queue queue;
        
        /**
         * 发送消息
         * @param destination 指定发送到的队列，手动创建
         * @param message 待发送的消息
         */
        @Override
        public void sendMessage(Destination destination, String message) {
            jmsMessagingTemplate.convertAndSend(destination, message);
        }
    
        /**
         * 发送消息, 使用注入的队列发消息
         * @param message 待发送的消息
         */
        @Override
        public void sendMessage(String message) {
            jmsMessagingTemplate.convertAndSend(this.queue, message);
        }
    }

调用：

    @RestController
    @RequestMapping("/api/v1")
    public class OrderController {

        @Autowired
        private ProducerService producerService;
    
        @GetMapping("/order")
        public String order(String msg){
            //生成队列地址；这里手动创建
            Destination destination = new ActiveMQQueue("order.queue");
            producerService.sendMessage(destination, msg);
            return "ok";
        }
    
        @GetMapping("/common")
        public String common(String msg){
            System.out.println("common: "+msg);
            producerService.sendMessage(msg);
            return "ok";
        }
    }    

消息接收者

    @Component
    public class OrderConsumer {
    
        @JmsListener(destination = "order.queue")
        public void orderConsumer(String text){
            System.out.println("order.queue接收到消息："+text);
        }
    
    }



#### 消息队列使用--发布订阅模式
发布订阅模式：可以有多个接收方和发送方，每条消息会被所有的接收方收到，若没有接收方那么这条消息也会被认为是发送成功的，后面将不会再收到该消息。   

修改配置文件，开启发布订阅模式

    jms:
        #开启发布订阅模式
        pub-sub-domain: true

消息发布者：

    @Autowired
    private Topic topic;
    @Override
    public void publish(String msg){
        jmsMessagingTemplate.convertAndSend(this.topic, msg);
    }

消息订阅者：

    @Component
    public class VideoTopicSub {
        @JmsListener(destination = "video.topic")
        public void video(String text){
            System.out.println("video.topic接收到消息："+text);
        }
    }

> 注意：springboot只能同时支持点对点模式或发布订阅模式中的一个，若要都支持需要指定链接工厂。

自己手动注入Topic工厂

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory) {//idea可能会报找不到activeMQConnectionFactory的bean错误提升。不用管这是可以正常使用的
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);//开启订阅发布模式
        bean.setConnectionFactory(activeMQConnectionFactory);
        return bean;
    }
同时需要关闭配置文件中
    
        jms:
            #关闭发布订阅模式，默认就是关闭状态
            pub-sub-domain: false    

消息订阅者需要设置监听的链接工厂，使其不和点对点的消息队列冲突
 
    @Component
    public class VideoTopicSub {
        @JmsListener(destination = "video.topic", containerFactory="jmsListenerContainerTopic")
        public void video(String text){
            System.out.println("video.topic接收到消息："+text);
        }
    }            
### 17、RocketMQ使用  
* 官网地址：[http://rocketmq.apache.org/](http://rocketmq.apache.org/)
* 学习资源1：[http://jm.taobao.org/2017/01/12/rocketmq-quick-start-in-10-minutes/](http://jm.taobao.org/2017/01/12/rocketmq-quick-start-in-10-minutes/)
* 学习资源2：[https://www.jianshu.com/p/453c6e7ff81c](https://www.jianshu.com/p/453c6e7ff81c)


RocketMQ是一款高性能、高吞吐量的分布式消息中间件的阿里开源中间件；
特点：

    1)在高压下1毫秒内响应延迟超过99.6％。
    2)适合金融类业务，高可用性跟踪和审计功能。
    3)支持发布订阅模型，和点对点
    4）支持拉pull和推push两种消息模式
    5)单一队列百万消息
    6)支持单master节点，多master节点，多master多slave节点
    ...
概念:

    Producer:消息生产者
    Producer Group:消息生产者组，发送同类消息的一个消息生产组
    Consumer:消费者
    Consumer Group:消费同个消息的多个实例
    Tag:标签，子主题（二级分类）,用于区分同一个主题下的不同业务的消息
    Topic:主题
    Message：消息
    Broker：MQ程序，接收生产的消息，提供给消费者消费的程序
    Name Server：给生产和消费者提供路由信息，提供轻量级的服务发现和路由
    		


#### 安装RocketMQ
官网提供了源码（带source文件名的）安装和二进制安装，源码安装需要自己去编码；这里直接下载二进制安装包[http://rocketmq.apache.org/release_notes/release-notes-4.4.0/](http://rocketmq.apache.org/release_notes/release-notes-4.4.0/)

1. 将下载的压缩解压即可；
2. 进入解压包中中bin目录下，修改runserver.sh和runbroker.sh这个2文件；
    
    
    #将Java虚拟机参数设置小点，避免启动时报内存不足的错误；参考如下：
    JAVA_OPT=”${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn125m....
3. 启动服务，执行如下命令：
 
    
    nohup sh mqnamesrv & 
启动后按Ctrl+c退出后，输入如下命令查看是否启动成功

    tail -f nohup.out
结尾：The Name Server boot success. serializeType=JSON 表示启动成功
4. 启动broker; 这里由于我的不是本地服务器而是阿里云的服务，因此需要开启外网访问。
修改conf下的配置文件broker.conf

    
    vi conf/broker.conf
在里面添加如下配置：

    namesrvAddr=外网IP:9876
    brokerIP1=外网IP

保存后执行启动命令：

    nohup sh bin/mqbroker -c conf/broker.conf &

#### springboot使用 
添加maven依赖

    <dependency>
        <groupId>org.apache.rocketmq</groupId>
        <artifactId>rocketmq-client</artifactId>
        <version>4.5.1</version>
    </dependency>
    <dependency>
        <groupId>org.apache.rocketmq</groupId>
        <artifactId>rocketmq-common</artifactId>
        <version>4.5.1</version>
    </dependency>


添加配置

    server:
      # 设置端口号
      port: 8080
      servlet:
        # 配置部署的路径
        context-path: /
    apache:
      rocketmq:
        consumer:
        #消费者
          push-consumer: orderConsumer
        producer:
        # 生产者
          producer-group: orderProducer
        # 服务地址  
        namesrv-addr: 127.0.0.1:9876   

生产者：

    /**
     * <p> 消息生产者 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/29 22:43
     */
    @Component
    public class MsgProducer {
    
    
        /**
         * 生产者的组名
         */
        @Value(value = "${apache.rocketmq.producer.producer-group}")
        private String producerGroup;
    
        /**
         * NameServer 地址
         */
        @Value(value = "${apache.rocketmq.namesrv-addr}")
        private String namesrvAddr;
    
        private DefaultMQProducer producer ;
    
    
        public DefaultMQProducer getProducer(){
            return this.producer;
        }
    
        @PostConstruct
        public void defaultMQProducer() {
            //生产者的组名
            producer = new DefaultMQProducer(producerGroup);
            //指定NameServer地址，多个地址以 ; 隔开
            //如 producer.setNamesrvAddr("192.168.100.141:9876;192.168.100.142:9876;192.168.100.149:9876");
            producer.setNamesrvAddr(namesrvAddr);
            producer.setVipChannelEnabled(false);
    
            try {
                /**
                 * Producer对象在使用之前必须要调用start初始化，只能初始化一次
                 */
                producer.start();
    
            } catch (Exception e) {
                e.printStackTrace();
            }
    
            // producer.shutdown();  一般在应用上下文，关闭的时候进行关闭，用上下文监听器
    
        }
    
    }
消费者：
    
    /**
     * <p> 消息消费者 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/7/29 22:43
     */
    @Component
    public class MsgConsumer {
    
        /**
         * 消息消费者
         */
        @Value("${apache.rocketmq.consumer.push-consumer}")
        private String pushConsumer;
    
        /**
         * NameServer 地址
         */
        @Value("${apache.rocketmq.namesrv-addr}")
        private String namesrvAddr;
    
        @PostConstruct
        public void defaultMQPushConsumer(){
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(pushConsumer);
            consumer.setNamesrvAddr(namesrvAddr);
            try {
                //设置consumer所订阅的Topic和Tag, *代表所有的Tag
                consumer.subscribe("testTopic", "*");
    
                // CONSUME_FROM_LAST_OFFSET 默认策略。从该队列最尾开始消费，跳过历史消息
                // CONSUME_FROM_FIRST_OFFSET, 从队列最开始开始消费，即历史消息（还存在broker的）全部消费一遍
                // CONSUME_FROM_TIMESTAMP;//根据时间消费
                consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
    
    //            MessageListenerOrderly 有序的
                //MessageListenerConcurrently无序的，效率更高
                consumer.registerMessageListener((MessageListenerConcurrently)(list, context)->{
                    try{
                        for(MessageExt messageExt:list){
                            //打印消息内容
                            System.out.println("messageExt: "+messageExt);
                            String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                            //输出消息内容
                            System.out.println("消费响应msgId: "+messageExt.getMsgId()+", msgBody: "+messageBody);
                        }
    
                    }catch (Exception e){
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;//稍后再试
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;//消费成功
                });
                consumer.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    
    }

测试发送消息：

    @RestController
    public class OrderController {

        @Autowired
        private MsgProducer msgProducer;
    
        @GetMapping("/order")
        public String order(String msg, String tag) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
            Message message = new Message("testTopic", tag, msg.getBytes(StandardCharsets.UTF_8));
            SendResult result = msgProducer.getProducer().send(message);
            System.out.println("发送响应：MsgId: "+result.getMsgId()+", 发送状态："+result.getSendStatus());
            return result.toString();
        }    
    }    
### 18、多环境配置
在resource目录下的config目录；分别创建各个环境的配置文件

    application.yml # 主配置文件，里面指定环境

方式如下：
    
    spring:
      profiles:
        active: test

    application-dev.yml # 测试环境
    application-local.yml # 本地环境
    ....
### 19、webFlux响应式编程
[reactive-streams学习资料](http://www.reactive-streams.org/)
[web-flux spring官方介绍](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#spring-webflux)

响应式编程特点：

1. 是Spring Framework 5.0中引入的新的反应式Web框架   
2. 应用程序不严格依赖于Servlet API，因此它们不能作为war文件部署，也不能使用src/main/webapp目录
3. 完全异步和非阻塞    
4. 启动方式默认是Netty

需要的maven依赖：(加入依赖，如果同时存在spring-boot-starter-web，则会优先用spring-boot-starter-web)

    <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
使用：

    import org.springframework.http.MediaType;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;
    import reactor.core.publisher.Flux;
    import reactor.core.publisher.Mono;
    import top.vchar.demo.spring.pojo.Member;
    
    import java.time.Duration;
    import java.util.Collection;
    import java.util.HashMap;
    import java.util.Map;
    
    /**
     * <p> 测试 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/8/3 16:04
     */
    @RestController
    public class IndexController {
    
        @GetMapping("/demo1")
        public String demo1(){
            return "demo-1";
        }
    
        //Mono用于返回0或1个元素
        @GetMapping("/demo2")
        public Mono<String> demo2(){
            return Mono.just("demo2");
        }
    
    
        private static Map<String, Member> map = new HashMap<>();
        static {
            for(int i=0; i<10; i++){
                map.put(""+i, new Member(i, "demo-"+i));
            }
        }
    
        //Flux返回0或N个元素
        @GetMapping("/demo3")
        public Flux<Member> demo3(){
          Collection<Member> members = map.values();
          return Flux.fromIterable(members);
        }
    
        //Mono用于返回0或1个元素
        @GetMapping("/demo4")
        public Mono<Member> demo4(final String id){
            return Mono.justOrEmpty(map.get(id));
        }
    
        /**
         * 分批次返回
         *
         * 这里每隔2s返回一个对象；webflux是字符串，需要做特殊设置
         */
        @GetMapping(value = "/demo5", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
        public Flux<Member> demo5(){
            Collection<Member> members = map.values();
            //每个延迟2秒返回
            return Flux.fromIterable(members).delayElements(Duration.ofSeconds(2));
        }
    }

>Mono 是响应流 Publisher 具有基础 rx 操作符，可以成功发布元素或者错误    
>
>Flux 是响应流 Publisher 具有基础 rx 操作符，可以成功发布 0 到 N 个元素或者错误。Flux 其实是 Mono 的一个补充

响应式编程使用的数据库需要保证访问速度的快速，即通常使用redis等数据库，否则和普通的方式区别不会太大。    

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

    /**
     * <p> webflux客户端Webclient测试 </p>
     *
     * @author vchar fred
     * @version 1.0
     * @create_date 2019/8/4 9:48
     */
    public class WebClientTest {
    
        @Test
        public void testBase(){
            Mono<String> result = WebClient.create().get()
                    .uri("http://127.0.0.1:8080//demo4?id={id}", 2)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().bodyToMono(String.class);
            System.out.println(result.block());//阻塞等待结果返回
        }
    
    }
### 20、springboot监控actuator
加入如下依赖：

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

访问监控地址：

    http://127.0.0.1:8080/actuator
    http://127.0.0.1:8080/actuator/health
    http://127.0.0.1:8080/actuator/info
    http://127.0.0.1:8080/actuator/metrics	查看应用基本指标列表
    http://127.0.0.1:8080/actuator/metrics/{name}		通过上述列表，查看具体 查看具体指标

出于安全考虑，除/health和/info之外的所有执行器默认都是禁用的；若需要打开如做如下配置

    management:
      endpoints:
        web:
          exposure:
            # 开启全部
            include: '*'
            # 开启某个
            #include: metrics
            # 关闭某个
            #exclude: metrics
           
           