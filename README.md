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
        