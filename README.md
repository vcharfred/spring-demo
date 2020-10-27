# Spring Cloud Hoxton版本的demo

[![build status](https://img.shields.io/badge/build-Hoxton-red)]()
[![jdk](https://img.shields.io/badge/jdk-1.8-green)]()
[![mybatis--plus](https://img.shields.io/badge/mybatis--plus-3.4.0-green)](https://mp.baomidou.com/guide/)
[![spring--boot](https://img.shields.io/badge/spring--boot-2.3.3-green)]()
[![spring--cloud](https://img.shields.io/badge/spring--cloud-Hoxton.SR8-green)]()
[![spring--cloud](https://img.shields.io/badge/注册中心-eureka-green)]()
[![spring--cloud](https://img.shields.io/badge/mysql-8.0-green)]()

### 根项目依赖锁定

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.3.3.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR8</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>1.2.73</version>
            </dependency>
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>3.4.0</version>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>8.0.21</version>
            </dependency>
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-pool2</artifactId>
                <version>2.8.1</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    

## 一、eureka注册中心

#### 添加maven依赖

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>com.netflix.ribbon</groupId>
                    <artifactId>ribbon-eureka</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>com.github.ben-manes.caffeine</groupId>
            <artifactId>caffeine</artifactId>
        </dependency>
    </dependencies>
 
#### 创建启动类

    @EnableEurekaServer
    @SpringBootApplication
    public class EurekaApplication {
    
        public static void main(String[] args) {
            new SpringApplicationBuilder(EurekaApplication.class).web(WebApplicationType.SERVLET).run(args);
        }
    }
#### application.yml配置文件
> 单机版配置

    server:
      port: 8761
    eureka:
      instance:
        hostname: localhost
      client:
        registerWithEureka: false
        fetchRegistry: false
        serviceUrl:
          defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
          
### 注册服务到eureka中

#### 添加maven依赖
 
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--eureka注册中心客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>com.netflix.ribbon</groupId>
                    <artifactId>ribbon-eureka</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!--Spring Cloud LoadBalancer is currently working with the default cache. You can switch to using Caffeine cache, by adding it to the classpath.-->
        <dependency>
            <groupId>com.github.ben-manes.caffeine</groupId>
            <artifactId>caffeine</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
        </dependency>
    </dependencies>

#### 创建启动类

    @EnableDiscoveryClient
    @SpringBootApplication
    public class UserApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(UserApplication.class, args);
        }
    }

controller、service等代码此处省略    

访问：`http://127.0.0.1:8761/` 查看是否注册成功

由于 Ribbon load-balancer 现在处于维护模式，spring官方建议使用Spring Cloud LoadBalancer；在配置文件中添加如下配置：

    spring.cloud.loadbalancer.ribbon.enabled=false

eureka注册中心客户端的依赖中排除如下依赖

    <!--eureka注册中心客户端-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
            </exclusion>
            <exclusion>
                <groupId>com.netflix.ribbon</groupId>
                <artifactId>ribbon-eureka</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

关于`Spring Cloud LoadBalancer is currently working with the default cache. You can switch to using Caffeine cache, by adding it to the classpath.` 的警告添加如下依赖即可：

    <dependency>
        <groupId>com.github.ben-manes.caffeine</groupId>
        <artifactId>caffeine</artifactId>
    </dependency>

## 二、网关

### 添加maven依赖

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        <!--eureka注册中心客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>com.netflix.ribbon</groupId>
                    <artifactId>ribbon-eureka</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>com.github.ben-manes.caffeine</groupId>
            <artifactId>caffeine</artifactId>
        </dependency>
    </dependencies>

### 添加application配置

    server:
      port: 9000
    spring:
      application:
        name: gateway-server
      cloud:
        gateway:
          discovery:
            locator:
              enabled: true
          routes:
            - id: user-server
              uri: lb://user-server
              predicates:
                - Path=/user/**
        loadbalancer:
          ribbon:
            enabled: false
    eureka:
      client:
        serviceUrl:
          defaultZone: http://localhost:8761/eureka/

### 创建启动类

    @EnableDiscoveryClient
    @SpringBootApplication
    public class GatewayApplication {
    
        public static void main(String[] args) {
            new SpringApplicationBuilder(GatewayApplication.class).web(WebApplicationType.REACTIVE).run(args);
        }
    }

启动所有服务验证是否成功：`http://127.0.0.1:9000/user/base?id=1`

### 网关鉴权

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
    </dependency>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
    </dependency>

> @Configuration 类似以前写在xml中配置bean，可以认为这就是一个xml配置文件

## 三、常用中间件

### elasticsearch搜索

spring文档：https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
    </dependency>

> 引入es包版本最好和使用的elasticsearch版本相同，避免出现问题

根据关键词查询站点信息：

es的DLS语句：

    GET /train_station_name/_search
    {
      "query": {
        "bool": {
          "should": [
            {
              "prefix": {
                "cnName.keyword": {
                  "value": "beib"
                }
              }
            },
            {
              "match_phrase_prefix": {
                "pinyin": "beib"
              }
            }
          ]
        }
      }
    }

spring封装的ElasticsearchRestTemplate实现：

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    
    public Flux<TrainStationNameDTO> findTrainStationName(String keywords) {

        BoolQueryBuilder boolQuery = new BoolQueryBuilder();
        QueryBuilder cnNameQuery = new PrefixQueryBuilder("cnName.keyword", keywords);
        boolQuery.should(cnNameQuery);
        QueryBuilder pinyinQuery = new MatchPhrasePrefixQueryBuilder("pinyin", keywords);
        boolQuery.should(pinyinQuery);
        Query query = new NativeSearchQuery(boolQuery);
        return Flux.fromIterable(elasticsearchRestTemplate.search(query, TrainStationNameDTO.class)).map(SearchHit::getContent);
    }

或者：

    @Component
    @Document(indexName = "train_station_name")
    public interface TrainStationNameRepository extends ElasticsearchRepository<TrainStationNameDTO, Long> {
    
        /**
         * 查询以keywords开头的站点信息
         * @param cnName 中文
         * @param pinyin 拼音
         * @return 返回结果
         */
        List<TrainStationNameDTO> findByCnNameStartingWithOrPinyinStartingWith(String cnName, String pinyin);
    
    }    

或者使用client

    @Autowired
    private RestHighLevelClient client;
    private void useClient(String keywords) {
        SearchRequest request = new SearchRequest("train_station_name");
        request.source(SearchSourceBuilder.searchSource()
                .query(QueryBuilders.boolQuery()
                        .should(QueryBuilders.prefixQuery("cnName.keyword", keywords))
                        .should(QueryBuilders.matchPhrasePrefixQuery("pinyin", keywords))
                )
        );
        try {
            SearchResponse search = client.search(request, RequestOptions.DEFAULT);
            log.info(JSONObject.toJSONString(search.getHits()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

### RocketMQ

#### 基本概念说明

    Producer:消息生产者
    Producer Group:消息生产者组，发送同类消息的一个消息生产组
    Consumer:消费者
    Consumer Group:消费同个消息的多个实例
    Tag:标签，子主题（二级分类）,用于区分同一个主题下的不同业务的消息
    Topic:主题
    Message：消息
    Broker：MQ程序，接收生产的消息，提供给消费者消费的程序
    Name Server：给生产和消费者提供路由信息，提供轻量级的服务发现和路由

#### 使用rocketmq封装好了的spring-boot启动类

添加依赖：

        <!-- rocketmq-->
        <dependency>
            <groupId>org.apache.rocketmq</groupId>
            <artifactId>rocketmq-spring-boot-starter</artifactId>
            <version>2.1.1</version>
        </dependency>

在application.yml配置文件中添加如下配置：

    rocketmq:
       # name server地址，有多个的时候用逗号分开
      name-server: 192.168.111.63:9876
      producer:
        ## 这个是消息分组，必须配置
        group: pay_server

代码中使用：

    private final RocketMQTemplate rocketMQTemplate;
    
        public PayOrderServiceImpl(RocketMQTemplate rocketMQTemplate) {
            this.rocketMQTemplate = rocketMQTemplate;
        }
    
        /**
         * 支付成功订单处理
         * @param payDTO 支付订单信息
         * @return 返回处理结果
         */
        @Override
        public Mono<String> pay(PayDTO payDTO) {
            // 存储支付订单
            log.info("存储订单信息:{}", payDTO);
    
            return Mono.defer(()->Mono.just(savePayOrder(payDTO))).flatMap(p->{
                if(p){
                    // 向业务系统推送支付成功通知
                    log.info("推送rocket mq 消息");
                    this.rocketMQTemplate.convertAndSend("pay:pay_success", payDTO);
                    return Mono.just("操作成功");
                }
                return Mono.just("数据库异常");
            });
        }    
    }

> 目前定制的RocketMQTemplate工具感觉还不太全面，参数也不明确，建议还是使用原始的依赖包

#### 使用原始的rocketmq的包



