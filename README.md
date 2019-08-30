# SpringCloud 学习笔记
* [SpringCloud官方地址](https://spring.io/projects/spring-cloud)
* [Dubbo和Spring Cloud微服务架构对比](https://blog.csdn.net/zhangweiwei2020/article/details/78646252)

微服务主要包括：
* 网关：路由转发、请求过滤
* 服务发现和注册：服务调用者和被调用方的信息维护
* 配置中心：管理配置，动态更新配置
* 链路追踪：分析调用链路耗时
* 负载均衡：分发负载
* 熔断：保护自己和被调用方

SpringCloud微服务组件

    通信方式------http restful
    网关----------zuul/spring-cloud-gateway
    注册中心------eruka/consul
    配置中心------config
    断路器--------hystrix
    分布式追踪系统--sleuth+zipkin

## 一、注册中心和服务调用
注册中心：服务管理，核心是有个服务注册表，心跳机制动态维护。

分布式CAP原理：一致性（C:数据同步）、可用性(A:正常响应时间)、分区容错性（P:机器数）三者不可同时获取

> zookeeper: CP设计，保证一致性，集群搭建时，某个节点失效，会从剩下的节点中重新选择一个。或半数以上节点不可用则无法提供服务，因此可用性无法满足

> Eureka: AP原则，无主从节点，一个节点挂了，自动切换其他节点可以使用，去中心化。

分布式系统中P必须保证，即多节点部署；只能在CA中二选一。

### eureka注册中心
添加maven依赖（建议使用idea进行自定构建项目）

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.7.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>top.vchar.demo.spring</groupId>
    <artifactId>springcloud-eureka-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>eureka-server</name>
    <description>eureka注册中心</description>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR2</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

配置：

    server:
      port: 8761
    eureka:
      instance:
        hostname: localhost
      client:
        # 关闭客服端配置
        register-with-eureka: false
        fetch-registry: false
        service-url:
          default-zone: http://${eureka.instance.hostname}:${server.port}/eureka/
    spring:
      application:
        name: eureka-register-center
在启动类上添加如下注解：

    @EnableEurekaServer            
### 服务端注册服务到eureka注册中心 
添加maven依赖：

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.7.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>top.vchar.demo.spring</groupId>
    <artifactId>product-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>product-server</name>
    <description>商品服务</description>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR2</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

添加配置信息：

    server:
      port: 8771
    # 配置注册中心地址
    eureka:
      client:
        service-url:
          default-zone: http://localhost:8761/eureka/
    # 配置应用名称
    spring:
      application:
        name: product-service    
        
service接口和controller写法和普通的无区别，这里不做举例了  
### 使用ribbon调用服务
ribbon和httpClient等类似

添加maven：

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.7.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>top.vchar.dem.spring</groupId>
    <artifactId>springcloud-ribbon</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springcloud-ribbon</name>
    <description>服务调用</description>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR2</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

修改配置：

    server:
      port: 8781
    # 配置注册中心地址
    eureka:
      client:
        service-url:
          default-zone: http://localhost:8761/eureka/
    # 配置应用名称
    spring:
      application:
        name: order-service

    # 自定义策略配置，默认是轮询的，建议使用默认的    
    product-service:
      ribbon:
        NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
#### 使用：
注入一个配置的bean

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
  
在service中调用

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.cloud.client.ServiceInstance;
    import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;
    import top.vchar.dem.spring.pojo.ProductOrder;
    import top.vchar.dem.spring.service.OrderService;
    
    import java.util.Date;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.UUID;
    @Service
    public class OrderServiceImpl implements OrderService {
    
        private static Map<Integer, ProductOrder> orderMap = new HashMap<>();
    
        @Autowired
        private RestTemplate restTemplate;
    
        @Autowired
        private LoadBalancerClient loadBalancerClient;
    
        @Override
        public ProductOrder save1(int userId, int productId) {
            System.out.println("方式一");
            Map<String, Object> product = restTemplate.getForObject("http://product-service/api/v1/product/find?id="+productId, Map.class);
            return save(userId, productId, product);
        }
    
        @Override
        public ProductOrder save2(int userId, int productId) {
            System.out.println("方式二");
            ServiceInstance serviceInstance = loadBalancerClient.choose("product-service");
            String url = String.format("http://%s:%s/api/v1/product/find?id="+productId, serviceInstance.getHost(), serviceInstance.getPort());
            RestTemplate restTemplate1 = new RestTemplate();
            Map<String, Object> product = restTemplate1.getForObject(url, Map.class);
            return save(userId, productId, product);
        }
    
        private ProductOrder save(int userId, int productId, Map<String, Object> product){
            ProductOrder productOrder = new ProductOrder();
            productOrder.setUserId(userId);
            productOrder.setCreateTime(new Date());
            productOrder.setTradeNo(UUID.randomUUID().toString());
            productOrder.setProductId(productId);
            productOrder.setId(orderMap.size());
            productOrder.setProductName(product.get("name").toString());
            productOrder.setPrice(Integer.parseInt(product.get("price").toString()));
            orderMap.put(productOrder.getId(), productOrder);
            return productOrder;
        }
    }
#### 说明    
@LoadBalanced会从注册中心获取节点信息，然后从中选择一个节点给RestTemplate使用
### feign：伪RPC客户端
feign已经集成了ribbon

添加maven依赖：

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
在启动类上添加如下注解

    @EnableFeignClients
    
使用：

定义一个接口供其他service调用

    @FeignClient(name = "product-service")//调用服务名称
    public interface ProductClient {
    
        //路径
        @GetMapping("/api/v1/product/find")
        String findProductById(@RequestParam(value = "id") int id);
    
    }
    
service中注入使用  

    @Service
    public class ProductServiceImpl implements ProductService {
    
        @Autowired
        private ProductClient productClient;
    
        @Override
        public ProductOrder findProductById(int id) {
            String result = productClient.findProductById(id);
            JsonNode jsonNode = JsonUtil.str2JsonNode(result);
            ProductOrder productOrder = new ProductOrder();
            productOrder.setId(jsonNode.findValue("id").asInt());
            productOrder.setProductName(jsonNode.findValue("name").asText());
            productOrder.setPrice(jsonNode.findValue("price").asInt());
            return productOrder;
        }
    } 
> 注意：
> 1. 路径必须和服务的一致；
> 2. 使用RequestBody时，必须使用postMapping
> 3. 多个参数时，通过@RequestParam来指定参数，名称要和服务的一样           

## 二、服务降级熔断
系统负载过高，突发流量或网络等各种异常情况，常用解决方案

1. 熔断：为了防止整个系统故障，停止出现问题的服务的访问
2. 降级：抛弃一些非核心的接口和数据
3. 熔断和降级互相交集：
    * 相同点：从可用性和可靠信息出发，为防止系统崩溃；最终让用户体验到的是某些功能暂时不可用
    * 不同点：服务熔断一般是下游服务故障导致，而服务降级一般是从整个系统负荷考虑，有调用方控制
    
### Hystrix （豪猪）
添加maven依赖

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
 
在启动类上添加注解

    @EnableCircuitBreaker   
   
#### 使用示例    
在方法上加@HystrixCommand(fallbackMethod = "xxx")注解

    @RestController
    @RequestMapping("/api/v3/order")
    public class IndexController {
    
        @Autowired
        private OrderService orderService;
    
        @RequestMapping("/update")
        @HystrixCommand(fallbackMethod = "updateOrderFail")
        public String update(@RequestParam("id") int id, @RequestParam("name")String name){
            return orderService.update(id, name);
        }
    
        //这里一定要和HystrixCommand注解中的方法一致，且参数也必须一致；当服务异常时会调用此方法
        private String updateOrderFail(int id, String name){
            System.out.println("服务异常： "+id+" "+name);
            return "{'code':'-1', 'msg':'当前访问人数过多，请稍后再试'}";
        }
    }
     
##### 对于Feign

    @FeignClient(name = "product-service", fallback = ProductClientFallback.class)
    public interface ProductClient {
    
        @GetMapping("/api/v1/product/find")
        String findProductById(@RequestParam(value = "id") int id);
    
    }
 
fallback中配置的类必须实现这个接口，并且注入微spring的bean

    @Component
    public class ProductClientFallback implements ProductClient {
    
        @Override
        public String findProductById(int id) {
            System.out.println("Feign 调用服务异常");
            return null;
        }
    }

同时在配置文件中开启 Hystrix

    # 开启  feign的  hystrix支持
    feign:
      hystrix:
        enabled: true    
     