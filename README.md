# spring cloud 阿里套件

使用阿里巴巴相关的spring cloud 套件做微服务，其根本就是替换掉我们以前用的其他的spring cloud的组件，相关依赖配置的替换而已，Spring Cloud的整体的

## 相关文档资料

* [nacos文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)
* [nacos Github](https://github.com/alibaba/nacos/releases)
* [示例项目建表SQL](建表SQL.sql)

## 一、注册中心nacos

部署一个服务端；注意分 集群和单机模式，集群模式下必须配置正确，否则将影响服务注册。可以使用docker部署

### 使用安装包安装

在Windows上直接解压后，进入bin目录中双击`startup.cmd`运行文件即可

Linux/Unix/Mac
> 启动命令(standalone代表着单机模式运行，非集群模式):

    sh startup.sh -m standalone

如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：

    bash startup.sh -m standalone

访问[http://127.0.0.1:8848/nacos](http://127.0.0.1:8848/nacos)即可，账号和密码默认为`nacos`

### docker部署nacos

## 二、注册服务到nacos

1. 在项目中添加如下依赖：


    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    
> 这个依赖用于替换eureka的客户端依赖包 `spring-cloud-starter-netflix-eureka-client`

2. 在启动类上添加 `@EnableDiscoveryClient`注解；其实使用eureka做注册中心时，也建议使用此注解，方便后期更换注册中心。

3. 在application配置文件中添加nacos的配置


    spring:
      cloud:
        nacos:
          discovery:
            # nacos的地址
            server-addr: 127.0.0.1:8848

成功后即可在



pom依赖示例如下：

父工程pim.xml

    <dependencyManagement>
        <dependencies>
            <!-- spring boot依赖约束 -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.1.14.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            
            <!-- spring cloud alibaba依赖约束 -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.1.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            
            <!-- spring cloud 依赖约束 -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.SR5</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            
            <!-- 数据库依赖 -->
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>3.3.2</version>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>8.0.20</version>
            </dependency>
            
            <!-- 业务依赖包 -->
            <dependency>
                <groupId>top.vchar</groupId>
                <artifactId>common-dto</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

子工程依赖：

    <parent>
        <artifactId>alibaba-micro-services</artifactId>
        <groupId>top.vchar</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>order-center</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>order-center</name>
    <description>订单中心</description>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        
        <!-- nacos 服务注册客户端依赖 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>

        <!-- lombok 注解，自动生成get/set方法，开发工具上需要安装lombok插件 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        
        <!-- 数据库依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
        </dependency>

        <!-- 业务依赖 -->
        <dependency>
            <groupId>top.vchar</groupId>
            <artifactId>common-dto</artifactId>
        </dependency>
    </dependencies>

## 三、服务调用

### 3.1 使用RestTemplate调用

#### 注入RestTemplate的bean

    /**
     * 加上 @LoadBalanced使其使用ribbon的负载均衡
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

#### 方式一：直接写服务地址调用服务

这种方式就不需要注册中心了, 直接像普通的接口请求即可

    GoodsDetailDTO goodsDetailDTO = restTemplate.getForObject("http://127.0.0.1:8093/goods/detail/"+createOrderDTO.getGoodsNo(), GoodsDetailDTO.class);

#### 方式二：通过`org.springframework.cloud.client.discovery.DiscoveryClient`来获取服务信息

`DiscoveryClient` 对象中保存有注册到注册中心的服务信息，包括服务的名称、IP、端口号等信息。通过服务名称可以获取到该服务的信息。

    // discoveryClient.getInstances 返回的是一个list列表，因此可以基于此可以自己去实现负载均衡；  
    ServiceInstance goodsServer = discoveryClient.getInstances("goods-server").get(0);
    GoodsDetailDTO goodsDetailDTO = restTemplate.getForObject("http://"+goodsServer.getHost()+":"+goodsServer.getPort()+"/goods/detail/"+createOrderDTO.getGoodsNo(), GoodsDetailDTO.class);

#### 方式三：在restTemplate bean注入的地方添加 @LoadBalanced注解；会自动使用ribbon来实现负载均衡

    // 在restTemplate bean注入的地方添加 @LoadBalanced注解；会自动使用ribbon来实现负载均衡
    GoodsDetailDTO goodsDetailDTO = restTemplate.getForObject("http://goods-server/goods/detail/"+createOrderDTO.getGoodsNo(), GoodsDetailDTO.class);

> 默认的负载均衡规则是轮询的方式；可以在配置文件中指定服务使用那种方式；示例如下：

    # 服务名称
    goods-server:
      ribbon:
        # 使用随机的方式
        NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule

### 3.2 使用feign调用服务

1.添加feign依赖
       
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

2.在启动类上添加`@EnableFeignClients`注解，启动feign功能

3.编写feign客户端

    import org.springframework.cloud.openfeign.FeignClient;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import top.vchar.goods.dto.GoodsDetailDTO;
    
    /**
     * <p> 商品服务 feign客户端 </p>
     *
     * value 就是服务的名称
     * @author vchar fred
     * @version 1.0
     * @create_date 2020/6/15
     */
    @FeignClient(value = "goods-server")
    public interface GoodsFeignClient {
    
        /**
         * 通过商品编号获取商品
         * @param goodsNo 商品编号
         * @return 返回结果
         */
        @GetMapping("/goods/detail/{goodsNo}")
        GoodsDetailDTO findGoodsDetailByGoodsNo(@PathVariable("goodsNo") String goodsNo);
    }

4.业务代码中调用    
    
    GoodsDetailDTO goodsDetailDTO = goodsFeignClient.findGoodsDetailByGoodsNo(createOrderDTO.getGoodsNo());
    
